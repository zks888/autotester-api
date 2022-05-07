package com.zoctan.api.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.core.service.HttpHeader;
import com.zoctan.api.core.service.HttpParamers;
import com.zoctan.api.core.service.TestHttp;
import com.zoctan.api.dto.TestResponeData;
import com.zoctan.api.dto.Testplanandbatch;
import com.zoctan.api.entity.*;
import com.zoctan.api.mapper.ExecuteplanMapper;
import com.zoctan.api.mapper.SlaverMapper;
import com.zoctan.api.service.ExecuteplanService;
import com.zoctan.api.service.ExecuteplanbatchService;
import com.zoctan.api.service.RouteperformancereportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zks888
 * @date 2020/10/22
 */
@RestController
@RequestMapping("/executeplanbatch")
public class ExecuteplanbatchController {
    @Resource
    private ExecuteplanbatchService executeplanbatchService;
    @Autowired
    private ExecuteplanService executeplanService;
    @Resource
    private SlaverMapper slaverMapper;
    @Resource
    private ExecuteplanMapper executeplanMapper;
    @Autowired
    private RouteperformancereportService routeperformancereportService;

    @PostMapping
    public Result add(@RequestBody Executeplanbatch executeplanbatch) {
        Condition con = new Condition(Executeplanbatch.class);
        con.createCriteria().andCondition("batchname = '" + executeplanbatch.getBatchname().replace("'", "''") + "'")
                .andCondition("executeplanid = " + executeplanbatch.getExecuteplanid());
        if (executeplanbatchService.ifexist(con) > 0) {
            return ResultGenerator.genFailedResult("该测试集合下已经存在此执行计划");
        } else {
            Long execplanid = executeplanbatch.getExecuteplanid();
            Executeplan executeplan = executeplanMapper.findexplanWithid(execplanid);
            String plantype = executeplan.getUsetype();
            List<Slaver> slaverlist = slaverMapper.findslaveralive(plantype, "已下线");
            if (slaverlist.size() > 0) {
                executeplanbatch.setStatus("待执行");
                executeplanbatch.setSource("平台");
                executeplanbatchService.save(executeplanbatch);
                if ("性能".equalsIgnoreCase(plantype)) {
                    //增加动态表
                    String TableName = "apicases_report_performance_" + executeplan.getId();
                    executeplanService.createNewTable(TableName);
                    //如果是性能测试集合，新增路由表
                    Routeperformancereport routeperformancereport = new Routeperformancereport();
                    routeperformancereport.setExecuteplanid(executeplan.getId());
                    routeperformancereport.setTablename(TableName);
                    routeperformancereportService.save(routeperformancereport);
                }
                return ResultGenerator.genOkResult();
            } else {
                return ResultGenerator.genFailedResult("未找到可用的" + plantype + "类型的执行机，请到调度中心-测试执行机查看是否有类型为" + plantype + "的执行机，并且不是已下线状态");
            }
        }
    }

    //对外接口，CI或者其他触发计划用例执行
    @PostMapping("/TestPlanRun")
    public Result TestPlanRun(@RequestBody final Map<String, Object> param) {
        String TestPlanName = param.get("TestPlanName").toString();
        String BatchName = param.get("BatchName").toString();
        String Source = param.get("Source").toString();
        long PlanID;
        Executeplan executeplan = executeplanService.getBy("executeplanname", TestPlanName);
        if (executeplan != null) {
            Executeplanbatch executeplanbatch = new Executeplanbatch();
            PlanID = executeplan.getId();
            Condition con = new Condition(Executeplanbatch.class);
            con.createCriteria().andCondition("batchname = '" + BatchName + "'")
                    .andCondition("executeplanid = " + PlanID);
            if (executeplanbatchService.ifexist(con) > 0) {
                return ResultGenerator.genFailedResult("该执行计划下已经存在此批次");
            } else {
                executeplanbatch.setStatus("待执行");
                executeplanbatch.setSource(Source);
                executeplanbatch.setBatchname(BatchName);
                executeplanbatch.setExecuteplanid(PlanID);
                executeplanbatch.setExecuteplanname(TestPlanName);
                executeplanbatchService.save(executeplanbatch);

                List<Testplanandbatch> list = new ArrayList<>();
                Testplanandbatch testplanandbatch = new Testplanandbatch();
                testplanandbatch.setBatchname(BatchName);
                testplanandbatch.setPlanid(PlanID);
                list.add(testplanandbatch);
                executeplanService.executeplancase(list, "立即执行");
                return ResultGenerator.genOkResult();
            }
        } else {
            return ResultGenerator.genFailedResult("未找到此测试计划：" + TestPlanName);
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        executeplanbatchService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Executeplanbatch executeplanbatch) {
        executeplanbatchService.update(executeplanbatch);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Executeplanbatch executeplanbatch = executeplanbatchService.getById(id);
        return ResultGenerator.genOkResult(executeplanbatch);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Executeplanbatch> list = executeplanbatchService.getallexplanbatch();
        PageInfo<Executeplanbatch> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PostMapping("/getbatchbyplan")
    public Result getbatchbyplan(@RequestParam Long executeplanid) {
        List<Executeplanbatch> list = executeplanbatchService.getbatchbyplan(executeplanid);
        return ResultGenerator.genOkResult(list);
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        int page = Integer.parseInt(param.get("page").toString());
        int size = Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<Executeplanbatch> list = this.executeplanbatchService.findexplanbatchWithName(param);
        final PageInfo<Executeplanbatch> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
