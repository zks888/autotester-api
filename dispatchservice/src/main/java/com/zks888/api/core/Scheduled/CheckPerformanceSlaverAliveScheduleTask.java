package com.zks888.api.core.Scheduled;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zks888.api.core.service.HttpHeader;
import com.zks888.api.core.service.Httphelp;
import com.zks888.api.entity.*;
import com.zks888.api.mapper.DispatchMapper;
import com.zks888.api.mapper.ExecuteplanMapper;
import com.zks888.api.mapper.SlaverMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class CheckPerformanceSlaverAliveScheduleTask implements SimpleJob {

    @Autowired(required = false)
    private SlaverMapper slaverMapper;

    @Autowired(required = false)
    private DispatchMapper dispatchMapper;

    @Autowired(required = false)
    private ExecuteplanMapper executeplanMapper;

    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            CheckAliveSlaver();
        } catch (Exception ex) {
            log.info("调度服务检查性能Slaver是否Alive定时器异常=======================" + ex.getMessage());
        }
    }

    public void CheckAliveSlaver() {
        List<Slaver> SlaverList = slaverMapper.findslaverbytype("性能");
        for (Slaver slaver : SlaverList) {
            if (!slaver.getStatus().equalsIgnoreCase("已下线")) {
                String IP = slaver.getIp();
                String Port = slaver.getPort();
                String ServerUrl = "http://" + IP + ":" + Port + "/exectestplancase/test";
                ExecuteplanTestcase plancase = new ExecuteplanTestcase();
                String params = JSON.toJSONString(plancase);
                HttpHeader header = new HttpHeader();
                String respon = "";
                try {
                    respon = Httphelp.doPost(ServerUrl, params, header, 3000);
                    log.info("调度服务检查性能Slaver检测：" + ServerUrl + "请求响应结果。。。。。。。。。。。。。。。。。。。。。。。。：" + respon);
                } catch (Exception e) {
                    //1.置为已下线
                    slaverMapper.updateSlaverStatus(slaver.getId(), "已下线");
                    log.info("调度服务检查性能Slaver：" + slaver.getSlavername() + " 更新为已下线。。。。。。。。。。。。。。。。。。。。。。。。");
                    List<Dispatch> dispatchList = dispatchMapper.getdispatchsbyslaverid(slaver.getId(), "已分配", "性能");
                    if (dispatchList.size() > 0) {
                        long planid = dispatchList.get(0).getExecplanid();
                        long batchid = dispatchList.get(0).getBatchid();
                        //2.更新dispatch为已取消
                        dispatchMapper.updatedispatchcancel("已取消", planid, batchid, "性能测试执行过程中，有执行机：" + slaver.getSlavername() + " 异常下线，导致性能测试集合执行计划取消");
                        log.info("调度服务检查性能更新集合id： " + planid + " 计划id ：" + batchid + " 状态为已取消。。。。。。。。。。。。。。。。。。。。。。。。");
                    }
                }
            }
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

    public List<Slaver> GetAllAliveSlaver() {
        List<Slaver> slaverlist = slaverMapper.findslaveralive("性能", "已下线");
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
                log.info("调度服务检查性能Slaver检测GetAliveSlaver：" + ServerUrl + "请求响应结果。。。。。。。。。。。。。。。。。。。。。。。。：" + respon);
            } catch (Exception e) {
                log.info("调度服务检查性能Slaver检测GetAliveSlaver：" + ServerUrl + "请求响应结果。。。。。。。。。。。。。。。。。。。。。。。。：" + e.getMessage());
                slaverMapper.updateSlaverStatus(slaver.getId(), "已下线");
            }
        }
        return slaverlistresult;
    }
}
