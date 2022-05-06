package com.zoctan.api.core.Scheduled;

import com.alibaba.fastjson.JSON;
import com.zoctan.api.core.config.RedisUtils;
import com.zoctan.api.core.service.HttpHeader;
import com.zoctan.api.core.service.Httphelp;
import com.zoctan.api.entity.*;
import com.zoctan.api.mapper.DispatchMapper;
import com.zoctan.api.mapper.ExecuteplanbatchMapper;
import com.zoctan.api.mapper.SlaverMapper;
import com.zoctan.api.mapper.TestconditionReportMapper;
import com.zoctan.api.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Component
public class PerformanceDispatchScheduleTask {
    @Value("${spring.conditionserver.serverurl}")
    private String conditionserver;
    @Autowired(required = false)
    private SlaverMapper slaverMapper;
    @Autowired(required = false)
    RedisUtils redisUtils;
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
    private String redisKey = "";


    //3.添加定时任务,处理并行多机并发性能测试任务
    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        try {
            // 全局性能任务的redis的key相同，保证全局性能任务同一时刻只有一个线程进入工作
            long redis_default_expire_time = 2000;
            //默认上锁时间为五小时
            //此key存放的值为任务执行的ip，
            // redis_default_expire_time 不能设置为永久，避免死锁
            boolean lock = redisUtils.tryLock(redisKey, "PerformanceDispatchScheduleTask", redis_default_expire_time);
            if (lock) {
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
                } finally {
                    //TODO 执行任务结束后需要释放锁
                    //释放锁
                    redisUtils.deletekey(redisKey);
                    log.info("性能任务-============释放分布式锁成功=======================");
                }
            } else {
                log.info("性能任务-============{}机器上占用分布式锁，正在执行中=======================" + redisKey);
            }
        } catch (Exception ex) {
            log.info("性能任务-调度定时器异常: " + ex.getMessage());
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

    private boolean IsConditionFinish(Long PlanID, String BatchName) {
        boolean flag = true;
        List<Testcondition> testconditionList = testconditionService.GetConditionByPlanIDAndConditionType(PlanID, "前置条件");
        log.info("调度服务【性能】测试定时器前置条件数量..................PlanID:" + PlanID + " BatchName:" + BatchName + testconditionList.size());
        if (testconditionList.size() > 0) {
            long ConditionID = testconditionList.get(0).getId();
            List<ConditionApi> conditionApiList = conditionApiService.GetCaseListByConditionID(ConditionID);
            log.info("调度服务【性能】测试定时器API条件数量..................PlanID:" + PlanID + " BatchName:" + BatchName + conditionApiList.size());
            if (conditionApiList.size() > 0) {
                List<TestconditionReport> testconditionReportList = testconditionReportMapper.getunfinishapiconditionnums(PlanID, BatchName);
                log.info("调度服务【性能】测试定时器条件报告数量..................PlanID:" + PlanID + " BatchName:" + BatchName + testconditionReportList.size());
                //配置了API条件，但是还未执行
                if (testconditionReportList.size() == 0) {
                    flag = false;
                } else {
                    List<TestconditionReport> testconditionstatusReportList = testconditionReportMapper.getunfinishapiconditionnumswithstatus(PlanID, BatchName, "进行中", "");
                    log.info("调度服务【性能】测试定时器条件报告进行中数量..................PlanID:" + PlanID + " BatchName:" + BatchName + testconditionstatusReportList.size());
                    //配置了API条件，开始执行，状态为进行中的条数为0表示都已经执行完成
                    if (testconditionstatusReportList.size() == 0) {
                        flag = true;
                    }
                }
            }
            //配置了条件，但是未配置API条件，认为是无API条件，可以执行用例
            if (conditionApiList.size() == 0) {
                flag = true;
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

    private List<Dispatch> GetGroupList(List<Dispatch> dispatchList, String ID) {
        Long ObjectID = 0L;
        HashMap<Long, List<Dispatch>> ResultGroup = new HashMap<>();
        for (Dispatch dispatch : dispatchList) {
            if (!ResultGroup.containsKey(dispatch.getExecplanid())) {
                List<Dispatch> tmp = new ArrayList<>();
                tmp.add(dispatch);
                if ("PlanID".equals(ID)) {
                    ObjectID = dispatch.getExecplanid();
                }
                if ("CaseID".equals(ID)) {
                    ObjectID = dispatch.getTestcaseid();
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

    @PostConstruct
    public void Init() {
        redisKey = "Performance-dispatch-RedisLock" + new Date();
        log.info("调度服务【性能】测试定时器-redisKey is:" + redisKey);
    }

}
