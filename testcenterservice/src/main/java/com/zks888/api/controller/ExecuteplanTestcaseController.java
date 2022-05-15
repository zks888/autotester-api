package com.zks888.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zks888.api.core.response.Result;
import com.zks888.api.core.response.ResultGenerator;
import com.zks888.api.dto.ApicasewithStatu;
import com.zks888.api.dto.StaticsDataForPie;
import com.zks888.api.entity.Apicases;
import com.zks888.api.entity.ExecuteplanTestcase;
import com.zks888.api.service.ApicasesService;
import com.zks888.api.service.ExecuteplanTestcaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zks888
 * @date 2020/10/12
 */
@RestController
@RequestMapping("/executeplan/testcase")
public class ExecuteplanTestcaseController {
    @Resource
    private ExecuteplanTestcaseService executeplanTestcaseService;
    @Autowired
    private ApicasesService apicaseservice;

    @PostMapping
    public Result add(@RequestBody ExecuteplanTestcase executeplanTestcase) {
        executeplanTestcaseService.save(executeplanTestcase);
        return ResultGenerator.genOkResult();
    }

    @PostMapping("/addcases")
    public Result addcase(@RequestBody final List<ExecuteplanTestcase> executeplanTestcase) {
        executeplanTestcaseService.savetestplancase(executeplanTestcase);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        executeplanTestcaseService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ExecuteplanTestcase executeplanTestcase) {
        executeplanTestcaseService.update(executeplanTestcase);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ExecuteplanTestcase executeplanTestcase = executeplanTestcaseService.getById(id);
        return ResultGenerator.genOkResult(executeplanTestcase);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ExecuteplanTestcase> list = executeplanTestcaseService.listAll();
        PageInfo<ExecuteplanTestcase> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @GetMapping("/getallbyexecuteplanid/{executeplanid}")
    public Result getallbyexecuteplanid(@PathVariable Long executeplanid) {
        List<ExecuteplanTestcase> list = executeplanTestcaseService.getplancasesbyplanid(executeplanid);
        return ResultGenerator.genOkResult(list);
    }

    @GetMapping("/getstaticsplancases")
    public Result getstaticsplancases() {
        List<ExecuteplanTestcase> list = executeplanTestcaseService.getstaticsplancases();
        List<StaticsDataForPie> result = new ArrayList<>();
        for (ExecuteplanTestcase executeplanTestcase : list) {
            StaticsDataForPie staticsDataForPie = new StaticsDataForPie();
            staticsDataForPie.setValue(executeplanTestcase.getId());
            staticsDataForPie.setName(executeplanTestcase.getDeployunitname());
            result.add(staticsDataForPie);
        }
        return ResultGenerator.genOkResult(result);
    }

    public List<Apicases> GetApiCase(final Map<String, Object> param) {
        int page = Integer.parseInt(param.get("page").toString());
        int size = Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        return this.apicaseservice.findApiCasebynameandcasetype(param);
    }

    /**
     * 根据caseid和参数类型返回参数值
     */
    @PostMapping("/getcasebydeployandapi")
    public Result casevalue(@RequestBody final Map<String, Object> param) {
        int page = Integer.parseInt(param.get("page").toString());
        int size = Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        List<ExecuteplanTestcase> plancaselist = this.executeplanTestcaseService.findcasebydeployandapi(param);
        List<Apicases> apicaselist = GetApiCase(param);
        List<ApicasewithStatu> lastresult = new ArrayList<>();
        boolean flag = false;
        for (Apicases ac : apicaselist) {
            flag = false;
            System.out.println("外循环casename...................: " + ac.getCasename());
            ApicasewithStatu et = null;
            for (int i = 0; i < plancaselist.size(); i++) {
                if (plancaselist.get(i).getTestcaseid().equals(ac.getId())) {
                    System.out.println("caseid...................: " + plancaselist.get(i).getTestcaseid());
                    et = new ApicasewithStatu();
                    et.setApiname(ac.getApiname());
                    et.setDeployunitname(ac.getDeployunitname());
                    et.setExpect(ac.getExpect());
                    et.setCasename(ac.getCasename());
                    et.setId(ac.getId());
                    et.setApiid(ac.getApiid());
                    et.setDeployunitid(ac.getDeployunitid());
                    et.setStatus(true);
                    flag = true;
                    lastresult.add(et);
                    break;
                }
            }
            System.out.println("外循环flag...................: " + flag);
            if (!flag) {
                et = new ApicasewithStatu();
                et.setApiname(ac.getApiname());
                et.setDeployunitname(ac.getDeployunitname());
                et.setExpect(ac.getExpect());
                et.setCasename(ac.getCasename());
                et.setId(ac.getId());
                et.setApiid(ac.getApiid());
                et.setDeployunitid(ac.getDeployunitid());
                et.setStatus(false);
                lastresult.add(et);
            }
        }
        PageInfo<ApicasewithStatu> pageInfo = new PageInfo<>(lastresult);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PostMapping("/removecases")
    public Result removeplancase(@RequestBody final List<ExecuteplanTestcase> executeplanTestcase) {
        executeplanTestcaseService.removeexecuteplantestcase(executeplanTestcase);
        return ResultGenerator.genOkResult();
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        int page = Integer.parseInt(param.get("page").toString());
        int size = Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<ExecuteplanTestcase> list = this.executeplanTestcaseService.findexplanWithName(param);
        final PageInfo<ExecuteplanTestcase> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
