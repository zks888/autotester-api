package com.zks888.api.core.Scheduled;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zks888.api.core.service.HttpHeader;
import com.zks888.api.core.service.Httphelp;
import com.zks888.api.entity.*;
import com.zks888.api.mapper.*;
import com.zks888.api.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Condition;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class FunctionDispatchScheduleTask implements SimpleJob {
    @Value("${spring.conditionserver.serverurl}")
    private String conditionserver;
    @Autowired(required = false)
    private SlaverMapper slaverMapper;
    @Autowired(required = false)
    private DispatchMapper dispatchMapper;
    @Autowired(required = false)
    private ExecuteplanMapper executeplanMapper;
    @Autowired(required = false)
    private ExecuteplanbatchMapper executeplanbatchMapper;
    @Autowired(required = false)
    private TestconditionReportMapper testconditionReportMapper;
    @Autowired(required = false)
    private TestconditionService testconditionService;
    @Autowired(required = false)
    private ConditionApiService conditionApiService;
    @Autowired(required = false)
    private ConditionDbService conditionDbService;
    @Autowired(required = false)
    private ConditionScriptService conditionScriptService;
    @Autowired(required = false)
    private TestconditionReportService testconditionReportService;
    @Autowired(required = false)
    private DispatchService dispatchService;

    //3.添加定时任务,处理功能测试任务
    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            Dispatch dispatch = dispatchMapper.getrecentdispatchbyusetype("待分配", "功能");
            if (dispatch != null) {
                Long PlanID = dispatch.getExecplanid();
                String BatchName = dispatch.getBatchname();
                //判断计划的所有前置条件是否已经完成，并且全部成功，否则更新Dispatch状态为前置条件失败
                boolean flag = ConditionRequest(PlanID, BatchName, dispatch);   //IsConditionFinish(PlanID,BatchName);
                if (flag) {
                    List<Dispatch> SlaverIDList = dispatchMapper.getdistinctslaverid("待分配", "功能", PlanID, BatchName);
                    if (SlaverIDList.size() > 0) {
                        //try {
                        for (Dispatch dispatch1 : SlaverIDList) {
                            Slaver slaver = new Slaver();
                            List<Dispatch> SlaverDispathcList = new ArrayList<>();
                            try {
                                Long Slaverid = dispatch1.getSlaverid();
                                log.info("调度服务【功能】测试定时器..................PlanID:" + PlanID + " BatchName:" + BatchName + " slaverid:" + Slaverid);
                                slaver = slaverMapper.findslaverbyid(Slaverid);
                                log.info("调度服务【功能】执行机 SlaverIP:" + slaver.getIp() + " 状态：" + slaver.getStatus());
                                //检测slaver是否运行，如果异常认为已经挂了，
                                CheckAliveSlaver(slaver);
                                if ("空闲".equals(slaver.getStatus())) {
                                    SlaverDispathcList = dispatchMapper.getfunctiondispatchsbyslaverid(Slaverid, "待分配", "功能", PlanID, BatchName);
                                    log.info("调度服务【功能】测试定时器 slaver:" + slaver.getSlavername() + " 获取dispatch数-：" + SlaverDispathcList.size());
                                    if (SlaverDispathcList.size() > 0) {
                                        String params = JSON.toJSONString(SlaverDispathcList);
                                        log.info("调度服务【功能】测试定时器-============执行机id：" + slaver.getId() + "  执行机名：" + slaver.getSlavername() + " 执行的dispatch：" + params);
                                        HttpHeader header = new HttpHeader();
                                        String ServerUrl = "http://" + slaver.getIp() + ":" + slaver.getPort() + "/exectestplancase/execfunctiontest";
                                        String response = Httphelp.doPost(ServerUrl, params, header, 30000);
                                        if (response.contains("未找到IP为")) {
                                            throw new Exception(response);
                                        }
                                        log.info("调度服务【功能】测试定时器-============请求slaver响应结果：" + response);
                                    }
                                }
                            } catch (Exception ex) {
                                String ErrorMessage = "";
                                if (ex.getMessage().contains("未找到IP为")) {
                                    ErrorMessage = "未找到用例所分配的执行机：" + slaver.getSlavername() + "已被删除，请重新注册";
                                } else {
                                    ErrorMessage = "用例所分配的执行机:" + slaver.getSlavername() + "已下线，请检查此slaver是否正常运行！";
                                }
                                //自动更换到可用的slaver上，如果没有可用的slaver再把dispatch状态更新为调度失败
                                CompensateAfterFail(ErrorMessage, dispatch, PlanID, SlaverDispathcList);
                                log.info("调度服务【功能】测试定时器请求执行服务异常：" + ex.getMessage());
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.info("调度服务【功能】测试定时器异常=======================" + ex.getMessage());
        }
    }

    private void CompensateAfterFail(String ErrorMessage, Dispatch dispatch, Long PlanID, List<Dispatch> SlaverDispathcList) {
        List<Slaver> allliveslaver = GetAllAliveSlaver();
        if (allliveslaver.size() == 0) {
            dispatchMapper.updatedispatchfail("调度失败", ErrorMessage, dispatch.getSlaverid(), dispatch.getExecplanid(), dispatch.getBatchid());
        } else {
            Executeplan ep = executeplanMapper.findexplanWithid(PlanID);
            if (ep.getRunmode().equalsIgnoreCase("单机运行")) {
                for (Dispatch dis : SlaverDispathcList) {
                    dis.setSlaverid(allliveslaver.get(0).getId());
                    dis.setSlavername(allliveslaver.get(0).getSlavername());
                    dis.setLastmodifyTime(new Date());
                    dispatchMapper.updateByPrimaryKey(dis);
                }
            }
            //平均分配
            else {
                int slaversize = allliveslaver.size();
                List<List<Dispatch>> partitions = Lists.partition(SlaverDispathcList, slaversize);
                for (int i = 0; i < partitions.size(); i++) {
                    for (Dispatch dis : partitions.get(i)) {
                        dis.setSlaverid(allliveslaver.get(i).getId());
                        dis.setSlavername(allliveslaver.get(i).getSlavername());
                        dis.setLastmodifyTime(new Date());
                        dispatchMapper.updateByPrimaryKey(dis);
                    }
                }
            }
        }
    }

    private boolean ConditionRequest(Long PlanID, String BatchName, Dispatch dispatch) throws Exception {
        boolean flag = true;
        List<Testcondition> testconditionList = testconditionService.GetConditionByPlanIDAndConditionType(PlanID, "前置条件");
        if (testconditionList.size() > 0) {
            Long ConditionID = testconditionList.get(0).getId();
            List<ConditionApi> conditionApiList = conditionApiService.GetCaseListByConditionID(ConditionID);
            int ApiConditionNums = conditionApiList.size();
            List<ConditionDb> conditionDbList = conditionDbService.GetCaseListByConditionID(ConditionID);
            int DBConditionNUms = conditionDbList.size();
            List<ConditionScript> conditionScriptList = conditionScriptService.getconditionscriptbyid(ConditionID);
            int ScriptConditionNUms = conditionScriptList.size();
            int SubConditionNums = ApiConditionNums + DBConditionNUms + ScriptConditionNUms;
            //表示有子条件需要处理
            if (SubConditionNums > 0) {
                //获取此计划批次条件报告的结果
                List<TestconditionReport> testconditionReportList = testconditionReportMapper.getunfinishapiconditionnums(PlanID, BatchName);
                //还未产生报告，需要请求条件服务
                if (testconditionReportList.size() == 0) {
                    //todo发请求条件服务,异步请求
                    RequestConditionServiceByPlanId(dispatch);
                    flag = false;
                }
                //已经产生条件报告，需要查看报告结果是成功还是失败
                else {
                    for (TestconditionReport testconditionReport : testconditionReportList) {
                        if (testconditionReport.getConditionstatus().equals(new String("失败"))) {
                            //有子条件已经执行失败，则此计划批次不再执行，更新当前计划批次的所有调度状态为条件失败，更新计划批次状态为条件失败
                            //todo
                            dispatchMapper.updatedispatchstatusbyplanandbatch("条件失败", PlanID, BatchName);
                            log.info("调度服务【功能】条件处理更新当前计划批次的所有调度状态为条件失败,计划： " + dispatch.getExecplanname() + "批次：" + BatchName);
                            executeplanbatchMapper.updatestatusbyplanandbatch("条件失败", PlanID, BatchName);
                            log.info("调度服务【功能】条件处理更新当前计划批次的状态为条件失败,计划： " + dispatch.getExecplanname() + "批次：" + BatchName);
                            flag = false;
                            break;
                        }
                    }
                    List<TestconditionReport> successtestconditionReportList = testconditionReportMapper.getsubconditionnumswithstatus(PlanID, BatchName, "已完成", "成功");
                    if (successtestconditionReportList.size() == SubConditionNums) {
                        //条件报告中已完成，成功的条数等于子条件总条数表示子条件都已成功完成，可以开始执行用例
                        log.info("调度服务【功能】条件报告已完成成功的数量: " + successtestconditionReportList.size() + "  子条件总条数：" + SubConditionNums);
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    private void PlanCondition(Dispatch dispatch, Long PlanID, String BatchName) throws Exception {
        //判断此计划是否有条件需要处理
        Condition testcondition = new Condition(Testcondition.class);
        testcondition.createCriteria().andCondition("objectid = " + PlanID).andCondition("objecttype = '执行计划'");
        if (testconditionService.ifexist(testcondition) > 0) {
            log.info("调度有计划条件需要处理................................");
            //判断planid,batchid在conditionreport表中无记录，才执行，避免多次重复调用
            Condition reportcon = new Condition(TestconditionReport.class);
            reportcon.createCriteria().andCondition("testplanid = " + PlanID).andCondition("batchname = '" + BatchName + "'");
            if (testconditionReportService.ifexist(reportcon) == 0) {
                log.info("条件报告表中无此计划和批次对应的条件结果................................" + dispatch.getExecplanname() + "|" + BatchName);
                //在执行计划用例前增加条件服务器调用，处理计划前置条件
                //RequestConditionServiceByPlanId(dispatch,"execplancondition");
            }
        }
    }

    private void CaseCondition(Dispatch dispatch, Long CaseID, String BatchName) throws Exception {
        //判断此用例是否有条件需要处理
        Condition testconditioncase = new Condition(Testcondition.class);
        testconditioncase.createCriteria().andCondition("objectid = " + CaseID).andCondition("objecttype = '测试用例'");
        if (testconditionService.ifexist(testconditioncase) > 0) {
            log.info("调度有用例条件需要处理................................");
            Condition reportcon = new Condition(TestconditionReport.class);
            reportcon.createCriteria().andCondition("testplanid = " + CaseID).andCondition("batchname = '" + BatchName + "'");
            if (testconditionReportService.ifexist(reportcon) == 0) {
                log.info("条件报告表中无此用例和批次对应的条件结果................................" + dispatch.getExecplanname() + "|" + BatchName);
                //在执行计划用例前增加条件服务器调用，处理计划前置条件
                //RequestConditionServiceByPlanId(dispatch,"execcasecondition");
            }
        }
    }

    private void RequestConditionServiceByPlanId(Dispatch dispatch) throws Exception {
        String params = JSON.toJSONString(dispatch);
        HttpHeader header = new HttpHeader();
        String ServerUrl = conditionserver + "/testcondition/execplancondition";
        log.info("调度处理条件任务请求conditionserver。。。。。。。: " + ServerUrl);
        try {
            log.info("调度处理条件任务请求数据。。。。。。。: " + params);
            String respone = Httphelp.doPost(ServerUrl, params, header, 30000);
            log.info("调度处理条件任务请求条件服务响应: " + respone);
        } catch (Exception ex) {
            log.info("-------------调度处理条件任务请求异常: " + ex.getMessage());
            dispatch.setMemo("无法连接conditionservice：" + ex.getMessage());
            dispatchService.update(dispatch);
        }
    }

    private HashMap<Long, List<Dispatch>> GetSlaverDispatchList(List<Dispatch> PlanDispatchList) {
        HashMap<Long, List<Dispatch>> result = new HashMap<>();
        for (Dispatch dispatch : PlanDispatchList) {
            if (!result.containsKey(dispatch.getSlaverid())) {
                List<Dispatch> tmp = new ArrayList<>();
                tmp.add(dispatch);
                result.put(dispatch.getSlaverid(), tmp);
            } else {
                result.get(dispatch.getSlaverid()).add(dispatch);
            }
        }
        return result;
    }

    private List<Dispatch> GetGroupList(List<Dispatch> dispatchList, String ID) {
        Long ObjectID = 0L;
        HashMap<Long, List<Dispatch>> ResultGroup = new HashMap<>();
        for (Dispatch dispatch : dispatchList) {
            if (!ResultGroup.containsKey(dispatch.getExecplanid())) {
                List<Dispatch> tmp = new ArrayList<>();
                tmp.add(dispatch);
                if (ID.equals(new String("PlanID"))) {
                    ObjectID = dispatch.getExecplanid();
                }
                if (ID.equals(new String("CaseID"))) {
                    ObjectID = dispatch.getTestcaseid();
                }
                if (ID.equals(new String("BatchID"))) {
                    ObjectID = dispatch.getBatchid();
                }
                ResultGroup.put(dispatch.getExecplanid(), tmp);
            } else {
                ResultGroup.get(ObjectID).add(dispatch);
            }
        }
        List<Dispatch> ResultDispatchList = new ArrayList<>();
        for (Long Planid : ResultGroup.keySet()) {
            ResultDispatchList = ResultGroup.get(Planid);
            break;
        }
        return ResultDispatchList;
    }

    private Dispatch GetCaseDispatch(List<Dispatch> dispatchList, Long SlaverID) {
        for (Dispatch dis : dispatchList) {
            if (SlaverID.equals(dis.getSlaverid())) {
                return dis;
            }
        }
        return null;
    }


    public void CheckAliveSlaver(Slaver slaver) throws Exception {
        String IP = slaver.getIp();
        String Port = slaver.getPort();
        String ServerUrl = "http://" + IP + ":" + Port + "/exectestplancase/test";
        ExecuteplanTestcase plancase = new ExecuteplanTestcase();
        String params = JSON.toJSONString(plancase);
        HttpHeader header = new HttpHeader();
        String respon = "";
        try {
            respon = Httphelp.doPost(ServerUrl, params, header, 3000);
            log.info("检测CheckAliveSlaver：" + ServerUrl + "请求响应结果。。。。。。。。。。。。。。。。。。。。。。。。：" + respon);
        } catch (Exception e) {
            slaverMapper.updateSlaverStatus(slaver.getId(), "已下线");
            log.info("检测CheckAliveSlaver：" + ServerUrl + "请求响应结果异常。。。。。。。。。。。。。。。。。。。。。。。。：" + e.getMessage() + "更新slaver" + slaver.getSlavername() + "为已下线");
            throw new Exception("Salver无法连接：" + slaver.getSlavername());
        }
    }

    public List<Slaver> GetAllAliveSlaver() {
        List<Slaver> slaverlist = slaverMapper.findslaveralive("功能", "已下线");
        List<Slaver> slaverlistresult = new ArrayList<>();
        for (Slaver slaver : slaverlist) {
            String IP = slaver.getIp();
            String Port = slaver.getPort();
            String ServerUrl = "http://" + IP + ":" + Port + "/exectestplancase/test";
            ExecuteplanTestcase plancase = new ExecuteplanTestcase();
            String params = JSON.toJSONString(plancase);
            HttpHeader header = new HttpHeader();
            String respon = "";
            try {
                respon = Httphelp.doPost(ServerUrl, params, header, 3000);
                slaverlistresult.add(slaver);
                log.info("检测GetAliveSlaver：" + ServerUrl + "请求响应结果。。。。。。。。。。。。。。。。。。。。。。。。：" + respon);
            } catch (Exception e) {
                log.info("检测GetAliveSlaver：" + ServerUrl + "请求响应结果。。。。。。。。。。。。。。。。。。。。。。。。：" + e.getMessage());
                slaverMapper.updateSlaverStatus(slaver.getId(), "已下线");
            }
        }
        return slaverlistresult;
    }
}
