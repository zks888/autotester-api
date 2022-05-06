package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.dto.StaticsDataForLine;
import com.zoctan.api.entity.StaticsDeployunitandcases;
import com.zoctan.api.service.StaticsDeployunitandcasesService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author zks888
 * @date 2021/04/15
 */
@RestController
@RequestMapping("/statics/deployunitandcases")
public class StaticsDeployunitandcasesController {
    @Resource
    private StaticsDeployunitandcasesService staticsDeployunitandcasesService;

    @PostMapping
    public Result add(@RequestBody StaticsDeployunitandcases staticsDeployunitandcases) {
        staticsDeployunitandcasesService.save(staticsDeployunitandcases);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        staticsDeployunitandcasesService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody StaticsDeployunitandcases staticsDeployunitandcases) {
        staticsDeployunitandcasesService.update(staticsDeployunitandcases);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        StaticsDeployunitandcases staticsDeployunitandcases = staticsDeployunitandcasesService.getById(id);
        return ResultGenerator.genOkResult(staticsDeployunitandcases);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<StaticsDeployunitandcases> list = staticsDeployunitandcasesService.listAll();
        PageInfo<StaticsDeployunitandcases> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @GetMapping("/getdeployunitstatics")
    public Result getdeployunitstatics() {
        Condition deployunitstaticscond = new Condition(StaticsDeployunitandcases.class);
        Date startDate = DateUtils.addDays(new Date(), -15);
        String startDateStr = new SimpleDateFormat("yyyy-MM-dd").format(startDate.getTime());
        deployunitstaticscond.createCriteria().andCondition("statics_date >= '" + startDateStr + " 00:00:00'");
        List<StaticsDeployunitandcases> list = staticsDeployunitandcasesService.listByCondition(deployunitstaticscond);
        List<StaticsDataForLine> staticsDataForLineList = new ArrayList<>();
        HashMap<String, List<Double>> tmp = new HashMap<>();
        for (StaticsDeployunitandcases staticsDeployunitandcases : list) {
            if (!tmp.containsKey(staticsDeployunitandcases.getDeployunitname())) {
                List<Double> planstaticsdatelist = new ArrayList<>();
                planstaticsdatelist.add(staticsDeployunitandcases.getPassrate());
                tmp.put(staticsDeployunitandcases.getDeployunitname(), planstaticsdatelist);
            } else {
                tmp.get(staticsDeployunitandcases.getDeployunitname()).add(staticsDeployunitandcases.getPassrate());
            }
        }
        for (String PlanName : tmp.keySet()) {
            StaticsDataForLine staticsDataForLine = new StaticsDataForLine();
            staticsDataForLine.setExecPlanName(PlanName);
            staticsDataForLine.setPassPecent(tmp.get(PlanName));
            staticsDataForLineList.add(staticsDataForLine);
        }
        return ResultGenerator.genOkResult(staticsDataForLineList);
    }
}
