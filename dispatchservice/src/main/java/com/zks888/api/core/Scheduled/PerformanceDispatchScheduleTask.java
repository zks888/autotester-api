package com.zks888.api.core.Scheduled;

import com.alibaba.fastjson.JSON;
import com.zks888.api.core.service.HttpHeader;
import com.zks888.api.core.service.Httphelp;
import com.zks888.api.entity.*;
import com.zks888.api.mapper.DispatchMapper;
import com.zks888.api.mapper.ExecuteplanbatchMapper;
import com.zks888.api.mapper.SlaverMapper;
import com.zks888.api.mapper.TestconditionReportMapper;
import com.zks888.api.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
public class PerformanceDispatchScheduleTask implements SimpleJob {
    @Value("${spring.conditionserver.serverurl}")
    private String conditionserver;
    @Autowired(required = false)
    private SlaverMapper slaverMapper;
    @Autowired(required = false)
    private DispatchMapper dispatchMapper;
    @Autowired(required = false)
    private ExecuteplanbatchMapper executeplanbatchMapper;
    @Autowired(required = false)
    private TestconditionReportMapper testconditionReportMapper;
    @Autowired(required = false)
    private TestconditionService testconditionService;
    @Autowired(required = false)
    private ConditionApiService conditionApiService;
    @Autowired(required = false)
    private ConditionScriptService conditionScriptService;
    @Autowired(required = false)
    private ConditionDbService conditionDbService;

    //3.添加定时任务,处理并行多机并发性能测试任务
    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            Dispatch dispatch = dispatchMapper.getrecentdispatchbyusetype("待分配", "性能");
            if (dispatch != null) {
                Long PlanID = dispatch.getExecplanid();
                String BatchName = dispatch.getBatchname();
                Long caseid = dispatch.getTestcaseid();
                //判断计划的所有前置条件是否已经完成，并且全部成功，否则更新Dispatch状态为前置条件失败
                boolean flag = ConditionRequest(PlanID, BatchName, dispatch);   //IsConditionFinish(PlanID,BatchName);
                if (flag) {
                    List<Dispatch> SlaverIDList = dispatchMapper.getdistinctslaveridandcaaseid("待分配", "性能", PlanID, BatchName, caseid);
                    int SleepSlaverNums = slaverMapper.findbusyslavernums(SlaverIDList, "空闲", "性能");
                    //判断SlaverIDList中的所有slaver都是空闲状态才请求slaver执行，多机并行执行性能的前提
                    if (SlaverIDList.size() == SleepSlaverNums) {
                        try {
                            for (Dispatch dispatch1 : SlaverIDList) {
                                Long Slaverid = dispatch1.getSlaverid();
                                log.info("调度服务【性能】测试定时器..................PlanID:" + PlanID + " BatchName:" + BatchName + " slaverid:" + Slaverid);
                                Slaver slaver = slaverMapper.findslaverbyid(Slaverid);
                                if (slaver != null) {
                                    if (slaver.getStatus().equals("空闲")) {
                                        List<Dispatch> SlaverDispathcList = dispatchMapper.getfunctiondispatchsbyslaverid(Slaverid, "待分配", "性能", PlanID, BatchName);
                                        log.info("调度服务【性能】测试定时器 slaverid:" + slaver + " 获取dispatch数-：" + SlaverDispathcList.size());
                                        if (SlaverDispathcList.size() > 0) {
                                            String params = JSON.toJSONString(SlaverDispathcList.get(0));
                                            log.info("调度服务【性能】测试定时器-============执行机id：" + slaver.getId() + "  执行机名：" + slaver.getSlavername() + " 执行的dispatch：" + params);
                                            HttpHeader header = new HttpHeader();
                                            String ServerUrl = "http://" + slaver.getIp() + ":" + slaver.getPort() + "/exectestplancase/execperformancetest";
                                            String respon = Httphelp.doPost(ServerUrl, params, header, 30000);
                                            log.info("调度服务【性能】测试定时器-============请求slaver响应结果：" + respon);
                                            if (respon.contains("未找到IP为")) {
                                                throw new Exception(respon);
                                            }
                                            if (respon.contains("未找到发布单元为")) {
                                                throw new Exception(respon);
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            dispatchMapper.updatedispatchstatusandmemo("调度异常", ex.getMessage(), dispatch.getSlaverid(), dispatch.getExecplanid(), dispatch.getBatchid(), dispatch.getTestcaseid());
                            log.info("调度服务【性能】测试定时器请求执行服务异常：" + ex.getMessage());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.info("调度服务【性能】测试定时器请求执行服务异常：" + ex.getMessage());
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
        }
    }
}
