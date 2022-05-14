package com.zks888.api.core.Scheduled;

import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import com.zks888.api.dto.Testplanandbatch;
import com.zks888.api.entity.*;
import com.zks888.api.mapper.ExecuteplanbatchMapper;
import com.zks888.api.mapper.PlanbantchexeclogMapper;
import com.zks888.api.service.ExecuteplanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Slf4j
@Component
public class SomeDayExecScheduleTask implements SimpleJob {
    @Autowired(required = false)
    private ExecuteplanService executeplanService;
    @Autowired(required = false)
    private ExecuteplanbatchMapper executeplanbatchMapper;
    @Autowired(required = false)
    private PlanbantchexeclogMapper planbantchexeclogMapper;

    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            Calendar cal = Calendar.getInstance();
            int Month = cal.get(Calendar.MONTH) + 1;
            int DATE = cal.get(Calendar.DATE);
            int Hour = cal.get(Calendar.HOUR_OF_DAY);
            int Minitues = cal.get(Calendar.MINUTE);
            String MonthData = FinishZERO(Month);
            String DateData = FinishZERO(DATE);
            String HourData = FinishZERO(Hour);
            String MinitesData = FinishZERO(Minitues);
            String CurrentTime = cal.get(Calendar.YEAR) + "-" + MonthData + "-" + DateData + " " + HourData + ":" + MinitesData + ":00";
            log.info("【某天定时执行任务】-============CurrentTime=======================" + CurrentTime);
            List<Executeplanbatch> executeplanbatchList = executeplanbatchMapper.getbatchbyexectype("某天定时");
            for (Executeplanbatch ex : executeplanbatchList) {
                String ExecDate = ex.getExecdate();
                log.info("【某天定时执行任务】-============ExecDate=======================" + ExecDate);
                if (CurrentTime.equals(ExecDate)) {
                    log.info("【某天定时执行任务】-============ExecDate=CurrentTime======================" + ExecDate);
                    List<Planbantchexeclog> planbantchexeclogList = planbantchexeclogMapper.GetPlanExecLog(ex.getId(), ExecDate);
                    if (planbantchexeclogList.size() == 0)//日志表不存在,表示还没执行
                    {
                        List<Testplanandbatch> testplanandbatchList = new ArrayList<>();
                        Testplanandbatch testplanandbatch = new Testplanandbatch();
                        testplanandbatch.setBatchname(ex.getBatchname());
                        testplanandbatch.setPlanid(ex.getExecuteplanid());
                        testplanandbatchList.add(testplanandbatch);
                        String memo = "";
                        try {
                            log.info("【某天定时执行任务】-============开始执行某天的用例======================" + CurrentTime);
                            ExecPlanCase(testplanandbatchList);
                            log.info("【某天定时执行任务】-============完成执行某天的用例======================" + CurrentTime);
                        } catch (Exception exp) {
                            memo = exp.getMessage();
                        }
                        planbantchexeclogMapper.SaveExecLog(ex.getId(), ExecDate, memo);
                        log.info("【某天定时执行任务】-============完成执行用例保存log记录表======================" + CurrentTime);
                    }
                }
            }
        } catch (Exception ex) {
            log.info("【某天定时执行任务异常: " + ex.getMessage());
        }
    }

    private String FinishZERO(int Nums) {
        String MonthDate = "";
        if (Nums < 10) {
            MonthDate = "0" + Nums;
        } else {
            MonthDate = String.valueOf(Nums);
        }
        return MonthDate;
    }

    private void ExecPlanCase(List<Testplanandbatch> testplanlist) {
        executeplanService.executeplancase(testplanlist, "某天定时");
    }
}
