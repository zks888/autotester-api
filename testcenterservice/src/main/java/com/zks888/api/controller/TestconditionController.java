package com.zks888.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zks888.api.core.response.Result;
import com.zks888.api.core.response.ResultGenerator;
import com.zks888.api.entity.Testcondition;
import com.zks888.api.service.*;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author zks888
 * @date 2021/05/31
 */
@RestController
@RequestMapping("/testcondition")
public class TestconditionController {
    @Resource
    private TestconditionService testconditionService;
    @Resource
    private ConditionDbService conditionDbService;
    @Resource
    private ConditionScriptService conditionScriptService;
    @Resource
    private ConditionApiService conditionApiService;
    @Resource
    private ConditionOrderService conditionOrderService;


    @PostMapping
    public Result add(@RequestBody Testcondition testcondition) {

        if(testcondition.getObjecttype().equalsIgnoreCase("调试用例"))
        {
            Condition con=new Condition(Testcondition.class);
            con.createCriteria().andCondition("objecttype = '" + testcondition.getObjecttype() + "'")
                    .andCondition("conditionname = '" + testcondition.getConditionname() + "'")
                    .andCondition("conditiontype = '" + testcondition.getConditiontype() + "'");
            if(testconditionService.ifexist(con)>0)
            {
                return ResultGenerator.genFailedResult("该计划或者用例已经存在相同的条件");
            }
            else {
                testconditionService.save(testcondition);
                return ResultGenerator.genOkResult();
            }
        }
        else
        {
            Condition con=new Condition(Testcondition.class);
            con.createCriteria().andCondition("objecttype = '" + testcondition.getObjecttype() + "'")
                    .andCondition("objectid = " + testcondition.getObjectid() )
                    .andCondition("conditiontype = '" + testcondition.getConditiontype() + "'");
            if(testconditionService.ifexist(con)>0)
            {
                return ResultGenerator.genFailedResult("该测试集合或者测试用例已经存在前置条件");
            }
            else {
                testconditionService.save(testcondition);
                return ResultGenerator.genOkResult();
            }
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        testconditionService.deleteById(id);
        conditionApiService.deletesubconditionbyconid(id);
        conditionDbService.deletesubconditionbyconid(id);
        conditionScriptService.deletesubconditionbyconid(id);
        conditionOrderService.deleteconditionorderbyconid(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Testcondition testcondition) {
        testconditionService.update(testcondition);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Testcondition testcondition = testconditionService.getById(id);
        return ResultGenerator.genOkResult(testcondition);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Testcondition> list = testconditionService.listAll();
        PageInfo<Testcondition> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final Testcondition testcondition) {
        Condition con=new Condition(Testcondition.class);
        con.createCriteria().andCondition("objecttype = '" + testcondition.getObjecttype() + "'")
                .andCondition("objectid = " + testcondition.getObjectid() )
                .andCondition("conditiontype = '" + testcondition.getConditiontype() + "'")
                .andCondition("id <> " + testcondition.getId());
        if(testconditionService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("该计划或者用例已经存在相同的条件");
        }
        else {

            this.testconditionService.updateTestcondition(testcondition);
            return ResultGenerator.genOkResult();
        }
    }


    @GetMapping("/getalltestcondition")
    public Result getalltestcondition() {
        List<Testcondition> list = testconditionService.getallTestcondition();
        return ResultGenerator.genOkResult(list);
    }

    @GetMapping("/getalltestconditionbytype")
    public Result getallconditionbytype(@RequestParam String objecttype) {
        List<Testcondition> list = testconditionService.getallTestconditionByType(objecttype);
        return ResultGenerator.genOkResult(list);
    }

    @GetMapping("/gettestconditionforscripyanddelay")
    public Result gettestconditionforscripyanddelay(@RequestParam String objecttype) {
        List<Testcondition> list = testconditionService.gettestconditionforscripyanddelay(objecttype);
        return ResultGenerator.genOkResult(list);
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<Testcondition> list = this.testconditionService.findtestconditionWithName(param);
        final PageInfo<Testcondition> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
