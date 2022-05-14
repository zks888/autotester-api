package com.zks888.api.core.Scheduled;

import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import com.zks888.api.dto.Testplanandbatch;
import com.zks888.api.entity.Executeplanbatch;
import com.zks888.api.entity.Planbantchexeclog;
import com.zks888.api.mapper.PlanbantchexeclogMapper;
import com.zks888.api.service.ExecuteplanService;
import com.zks888.api.service.ExecuteplanbatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Condition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Slf4j
@Component
public class EveryDayExecScheduleTask implements SimpleJob {

    @Autowired(required = false)
    private ExecuteplanService executeplanService;
    @Autowired(required = false)
    private ExecuteplanbatchService executeplanbatchService;
    @Autowired(required = false)
    private PlanbantchexeclogMapper planbantchexeclogMapper;

    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            Calendar cal = Calendar.getInstance();
            int Hour = cal.get(Calendar.HOUR_OF_DAY);
            int Minitues = cal.get(Calendar.MINUTE);

            String HourData = FinishZERO(Hour);
            String MinitesData = FinishZERO(Minitues);
            String CurrentTime = HourData + ":" + MinitesData + ":00";
            log.info("【每天定时执行任务】-============CurrentTime=======================" + CurrentTime);
            Condition con = new Condition(Executeplanbatch.class);
            con.createCriteria().andCondition("exectype = '每天定时'");
            List<Executeplanbatch> executeplanbatchList = executeplanbatchService.listByCondition(con);
            for (Executeplanbatch ex : executeplanbatchList) {
                String ExecDate = ex.getExecdate();
                log.info("【每天定时执行任务】-============ExecDate=======================" + ExecDate);
                if (CurrentTime.equals(ExecDate)) {
                    log.info("【每天定时执行任务】-============ExecDate=CurrentTime======================" + ExecDate);
                    List<Planbantchexeclog> planbantchexeclogList = planbantchexeclogMapper.GetPlanExecLogToday(ex.getId(), ExecDate);
                    //日志表当天不存在,表示还没执行
                    if (planbantchexeclogList.size() == 0) {
                        List<Testplanandbatch> testplanandbatchList = new ArrayList<>();
                        Testplanandbatch testplanandbatch = new Testplanandbatch();
                        testplanandbatch.setBatchname(ex.getBatchname());
                        testplanandbatch.setPlanid(ex.getExecuteplanid());
                        testplanandbatchList.add(testplanandbatch);
                        String memo = "";
                        try {
                            log.info("【每天定时执行任务】-============开始执行当天的用例======================" + CurrentTime);
                            ExecPlanCase(testplanandbatchList);
                            log.info("【每天定时执行任务】-============完成执行当天的用例======================" + CurrentTime);
                        } catch (Exception exp) {
                            memo = exp.getMessage();
                        }
                        planbantchexeclogMapper.SaveExecLog(ex.getId(), ExecDate, memo);
                        log.info("【每天定时执行任务】-============完成执行用例保存log记录表======================" + CurrentTime);
                    }
                }
            }
        } catch (Exception ex) {
            log.info("【每天定时执行任务】异常=======================" + ex.getMessage());
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
        executeplanService.executeplancase(testplanlist, "每天定时");
    }
}
