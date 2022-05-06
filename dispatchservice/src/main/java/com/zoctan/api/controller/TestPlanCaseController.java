package com.zoctan.api.controller;

import com.alibaba.fastjson.JSON;
import com.zoctan.api.core.Scheduled.FunctionDispatchScheduleTask;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.core.service.HttpHeader;
import com.zoctan.api.core.service.Httphelp;
import com.zoctan.api.dto.Testplanandbatch;
import com.zoctan.api.entity.*;
import com.zoctan.api.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author zks888
 * @date 2020/04/17
 */
@Slf4j
@RestController
@RequestMapping("/exectestplancase")
public class TestPlanCaseController {
    @Autowired
    private ExecuteplanbatchMapper executeplanbatchMapper;
    @Autowired
    private ExecuteplanTestcaseMapper executeplanTestcaseMapper;
    @Autowired
    private ExecuteplanMapper executeplanMapper;
    @Autowired
    private SlaverMapper slaverMapper;
    @Autowired
    private DispatchMapper dispatchMapper;

    @PostMapping("/exec")
    public Result exec(@RequestBody Testplanandbatch planbatch) throws Exception {
        log.info("开始保存调度用例。。。。。。。。。。。。。。。。。。。。。。。。");
        Long execplanid = planbatch.getPlanid();
        String batchname = planbatch.getBatchname();
        Executeplanbatch epb = executeplanbatchMapper.getbatchidbyplanidandbatchname(execplanid, batchname);
        if (epb == null) {
            return ResultGenerator.genOkResult(" 未找到执行批次名： " + batchname);
        }
        Executeplan ep = executeplanMapper.findexplanWithid(execplanid);
        if (ep == null) {
            return ResultGenerator.genOkResult("未找到此测试集合：" + execplanid);
        }
        String usetype = ep.getUsetype();
        List<ExecuteplanTestcase> caselist = executeplanTestcaseMapper.findcasebytestplanid(execplanid, usetype);
        log.info("测试集合id" + execplanid + " 批次为：" + batchname + " 获取用例数：" + caselist.size());
        if (caselist.size() == 0) {
            return ResultGenerator.genOkResult("此测试集合:" + ep.getExecuteplanname() + " 还没用例，请先装载用例");
        }
        //获取对应计划类型的所有slaver
        List<Slaver> slaverlist = slaverMapper.findslaveralive(usetype, "已下线");
        //增加检测slaver是否正常，在salver的control做个检测的请求返回
        List<List<Dispatch>> dispatchList = new ArrayList<>();
        if (slaverlist.size() == 0) {
            log.info("没有类型" + usetype + "的可用的执行机，请检查slaverservice是否在运行");
            return ResultGenerator.genOkResult("没有类型" + usetype + "的可用的执行机，请检查slaverservice是否在运行");
        } else {
            if ("单机运行".equalsIgnoreCase(ep.getRunmode())) {
                List<Slaver> singleslaverlist = new ArrayList<>();
                singleslaverlist.add(slaverlist.get(0));
                if ("功能".equalsIgnoreCase(usetype)) {
                    dispatchList = FunctionDispatch(singleslaverlist, caselist, ep, epb);
                } else {
                    dispatchList = PerformanceDispatch(singleslaverlist, caselist, ep, epb);
                }
                log.info("单机运行slaver：" + slaverlist.get(0).getSlavername());
            } else {
                log.info("多机并行slaver：");
                if ("功能".equalsIgnoreCase(usetype)) {
                    dispatchList = FunctionDispatch(slaverlist, caselist, ep, epb);
                } else {
                    dispatchList = PerformanceDispatch(slaverlist, caselist, ep, epb);
                }
            }
        }
        for (List<Dispatch> li : dispatchList) {
            dispatchMapper.insertBatchDispatch(li);
            log.info("保存成功调度用例条数：" + li.size());
        }
        log.info("完成保存调度用例。。。。。。。。。。。。。。。。。。。。。。。。");
        return ResultGenerator.genOkResult();
    }

    // 功能caselist平均分配给slaverlist，多余的分给最后一个list
    public List<List<Dispatch>> FunctionDispatch(List<Slaver> slaverlist, List<ExecuteplanTestcase> caselist, Executeplan ep, Executeplanbatch epb) {
        int slavernums = slaverlist.size();
        if (caselist.size() < slavernums) {
            slavernums = caselist.size();
        }
        List<List<Dispatch>> LastDispatchList = new ArrayList<>();
        List<Dispatch> splitdispatchList;
        int sizemode = (caselist.size()) / slavernums;
        int sizeleft = (caselist.size()) % slavernums;
        int j = 0;
        int x = 0;
        for (int i = 0; i < slavernums; i++) {
            splitdispatchList = new ArrayList<>();
            for (j = x; j < (sizemode + x); j++) {
                Long slaverid = slaverlist.get(i).getId();
                String slavername = slaverlist.get(i).getSlavername();
                ExecuteplanTestcase testcase = caselist.get(j);
                Dispatch dis = getdispatch(slaverid, slavername, testcase, ep, epb, testcase.getThreadnum(), testcase.getLoops());
                splitdispatchList.add(dis);
            }
            x = j;
            LastDispatchList.add(splitdispatchList);
        }
        if (sizeleft != 0) {
            for (int y = 1; y < sizeleft + 1; y++) {
                Long slaverid = slaverlist.get(slavernums - 1).getId();
                String slavername = slaverlist.get(slavernums - 1).getSlavername();
                ExecuteplanTestcase testcase = caselist.get(caselist.size() - y);
                Dispatch dis = getdispatch(slaverid, slavername, testcase, ep, epb, testcase.getThreadnum(), testcase.getLoops());
                LastDispatchList.get(LastDispatchList.size() - 1).add(dis);
            }
        }
        return LastDispatchList;
    }

    public Dispatch getdispatch(Long slaverid, String slavername, ExecuteplanTestcase testcase, Executeplan ep, Executeplanbatch epb, Long ThreadNum, Long Loops) {
        Dispatch dis = new Dispatch();
        dis.setExpect(testcase.getExpect());
        dis.setExecplanid(ep.getId());
        dis.setTestcaseid(testcase.getTestcaseid());
        dis.setDeployunitname(testcase.getDeployunitname());
        dis.setStatus("待分配");
        dis.setBatchname(epb.getBatchname());
        dis.setBatchid(epb.getId());
        dis.setCasejmxname(testcase.getCasejmxname());
        dis.setExecplanname(ep.getExecuteplanname());
        dis.setCreator(ep.getCreator());
        dis.setSlaverid(slaverid);
        dis.setSlavername(slavername);
        dis.setTestcasename(testcase.getCasename());
        dis.setPlantype(ep.getUsetype());
        dis.setThreadnum(ThreadNum);
        dis.setLoops(Loops);
        return dis;
    }

    // 性能用例拆分线程和循环
    public List<List<Dispatch>> PerformanceDispatch(List<Slaver> slaverlist, List<ExecuteplanTestcase> caselist, Executeplan ep, Executeplanbatch epb) {
        int slavernums = slaverlist.size();
        List<List<Dispatch>> LastDispatchList = new ArrayList<>();
        List<Dispatch> splitdispatchList = new ArrayList<>();
        for (ExecuteplanTestcase testcase : caselist) {
            Long ThreadNUms = testcase.getThreadnum();
            Long Loops = testcase.getLoops();
            Long Threadmode = ThreadNUms / slavernums;
            Long Loopsmode = Loops / slavernums;
            Long Threadleft = ThreadNUms % slavernums;
            Long Loopleft = Loops % slavernums;
            //拆分每个case线程和循环取模平均分配到slaver
            for (int i = 0; i < slaverlist.size(); i++) {
                Dispatch dis = getdispatch(slaverlist.get(i).getId(), slaverlist.get(i).getSlavername(), testcase, ep, epb, Threadmode, Loopsmode);
                splitdispatchList.add(dis);
            }
            //如果线程数或者循环数取余不为0，则把剩余的都分给最后一个slaver
            if (Threadleft != 0 || Loopleft != 0) {
                Dispatch dis = splitdispatchList.get(slaverlist.size() - 1);
                dis.setThreadnum(dis.getThreadnum() + Threadleft);
                dis.setLoops(dis.getLoops() + Loopleft);
                splitdispatchList.set(slaverlist.size() - 1, dis);
            }
        }
        LastDispatchList.add(splitdispatchList);
        return LastDispatchList;
    }

    @GetMapping("/test")
    public Result gettest(@RequestBody ExecuteplanTestcase plancase) {
        //tpcservice.executeplancase(plancase.getExecuteplanid(),plancase.getTestcaseid());
        return ResultGenerator.genOkResult();
    }

}
