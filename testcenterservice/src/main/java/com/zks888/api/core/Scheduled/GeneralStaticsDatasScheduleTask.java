package com.zks888.api.core.Scheduled;

import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import com.google.common.collect.Maps;
import com.zks888.api.entity.*;
import com.zks888.api.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Component
public class GeneralStaticsDatasScheduleTask implements SimpleJob {

    @Autowired(required = false)
    private ApicasesReportstaticsMapper apicasesReportstaticsMapper;
    @Autowired(required = false)
    private ExecuteplanMapper executeplanMapper;
    @Autowired(required = false)
    private StaticsDeployunitandcasesMapper staticsDeployunitandcasesMapper;
    @Autowired(required = false)
    private StaticsPlanandcasesMapper staticsPlanandcasesMapper;
    @Autowired(required = false)
    private DeployunitMapper deployunitMapper;

    @Override
    public void execute(ShardingContext shardingContext) {
        //log.info("开始执行收集统计数据任务");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        int Year = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH) + 1;
        int Day = calendar.get(Calendar.DAY_OF_MONTH);
        String StaticsDay = Year + "-" + MONTH + "-" + Day;
        String StartStaticsDay = Year + "-" + MONTH + "-" + Day + " 00:00:00";
        String EndStaticsDay = Year + "-" + MONTH + "-" + Day + " 23:59:59";
        StaticsPlanCase(StaticsDay, StartStaticsDay, EndStaticsDay);
        StaticsDeployUnitCase(StaticsDay, StartStaticsDay, EndStaticsDay);
        log.info("执行收集统计数据任务完成");
    }

    private void StaticsPlanCase(String StaticsDate, String StartStaticsDate, String EndStaticsDate) {
        //log.info("开始执行收集统计计划用例数据任务");
        List<StaticsPlanandcases> staticsPlanandcasesList = new ArrayList<>();
        HashMap<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("startTime", StartStaticsDate);
        paramMap.put("endTime", EndStaticsDate);
        List<ApicasesReportstatics> apicasesReportstaticsList = apicasesReportstaticsMapper.getapicasesreportstatics(paramMap);
        for (ApicasesReportstatics apicasesReportstatics : apicasesReportstaticsList) {
            Long PlanID = apicasesReportstatics.getTestplanid();
            Executeplan executeplan = executeplanMapper.findexplanWithid(PlanID);
            StaticsPlanandcases staticsPlanandcases = new StaticsPlanandcases();
            Long TotalCase = apicasesReportstatics.getTotalcases();
            Long TotalPassCase = apicasesReportstatics.getTotalpasscases();
            staticsPlanandcases.setTotalcases(apicasesReportstatics.getTotalcases());
            staticsPlanandcases.setTotalpasscases(apicasesReportstatics.getTotalpasscases());
            staticsPlanandcases.setTotalfailcases(apicasesReportstatics.getTotalfailcases());
            staticsPlanandcases.setRuntime(apicasesReportstatics.getRuntime());
            staticsPlanandcases.setTestplanname(executeplan.getExecuteplanname());
            double PassRate = TotalPassCase.doubleValue() / TotalCase.doubleValue();
            DecimalFormat df = new DecimalFormat("######0.00");
            df.format(PassRate * 100);
            staticsPlanandcases.setPassrate(Double.parseDouble(df.format(PassRate * 100)));
            staticsPlanandcases.setTestplanid(PlanID);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
            Date date = null;
            try {
                date = simpleDateFormat.parse(StaticsDate);
            } catch (ParseException e) {
                log.info("执行收集统计计划用例数据任务异常" + e.getMessage());
            }
            staticsPlanandcases.setStaticsDate(date);
            staticsPlanandcasesList.add(staticsPlanandcases);
        }
        if (staticsPlanandcasesList.size() > 0) {
            staticsPlanandcasesMapper.savestaticsplanandcases(staticsPlanandcasesList);
        }
        log.info("执行收集统计计划用例数据任务完成");
    }

    private void StaticsDeployUnitCase(String StaticsDate, String StartStaticsDate, String EndStaticsDate) {
        log.info("------开始执行收集统计发布单元用例数据任务------------");
        List<StaticsDeployunitandcases> staticsDeployUnitandcasesList = new ArrayList<>();
        HashMap<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("startTime", StartStaticsDate);
        paramMap.put("endTime", EndStaticsDate);
        List<ApicasesReportstatics> apicasesReportstaticsList = apicasesReportstaticsMapper.getdeployunitapicasesreportstatics(paramMap);
        for (ApicasesReportstatics apicasesReportstatics : apicasesReportstaticsList) {
            Long DeployUnitID = apicasesReportstatics.getDeployunitid();
            Deployunit deployunit = deployunitMapper.findDeployUnitWithid(DeployUnitID);
            StaticsDeployunitandcases staticsDeployunitandcases = new StaticsDeployunitandcases();
            Long TotalCase = apicasesReportstatics.getTotalcases();
            Long TotalPassCase = apicasesReportstatics.getTotalpasscases();
            staticsDeployunitandcases.setTotalcases(apicasesReportstatics.getTotalcases());
            staticsDeployunitandcases.setTotalpasscases(apicasesReportstatics.getTotalpasscases());
            staticsDeployunitandcases.setTotalfailcases(apicasesReportstatics.getTotalfailcases());
            staticsDeployunitandcases.setRuntime(apicasesReportstatics.getRuntime());
            staticsDeployunitandcases.setDeployunitname(deployunit.getDeployunitname());
            double PassRate = TotalPassCase.doubleValue() / TotalCase.doubleValue();
            DecimalFormat df = new DecimalFormat("######0.00");
            df.format(PassRate * 100);
            staticsDeployunitandcases.setPassrate(Double.parseDouble(df.format(PassRate * 100)));
            staticsDeployunitandcases.setDeployunitid(DeployUnitID);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
            Date date = null;
            try {
                date = simpleDateFormat.parse(StaticsDate);
            } catch (ParseException e) {
                log.info("执行收集统计计划用例数据任务异常" + e.getMessage());
            }
            staticsDeployunitandcases.setStaticsDate(date);
            staticsDeployUnitandcasesList.add(staticsDeployunitandcases);
        }
        if (staticsDeployUnitandcasesList.size() > 0) {
            staticsDeployunitandcasesMapper.savestaticsdeployunitandcases(staticsDeployUnitandcasesList);
        }
        log.info("---------执行收集统计发布单元用例数据任务完成-----------");
    }
}
