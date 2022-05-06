package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.ConditionApi;
import com.zoctan.api.entity.ConditionDb;
import com.zoctan.api.entity.ConditionScript;
import com.zoctan.api.service.ConditionOrderService;
import com.zoctan.api.service.ConditionScriptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author zks888
 * @date 2021/11/20
 */
@RestController
@RequestMapping("/condition/script")
public class ConditionScriptController {
    @Resource
    private ConditionScriptService conditionScriptService;
    @Resource
    private ConditionOrderService conditionOrderService;

    @PostMapping
    public Result add(@RequestBody ConditionScript conditionScript) {

        Condition con=new Condition(ConditionScript.class);
        con.createCriteria().andCondition("conditionname = '" + conditionScript.getConditionname()+ "'")
                .orCondition("subconditionname = '" + conditionScript.getSubconditionname().replace("'","''") + "'");
        if(conditionScriptService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("已存在该条件名或子条件名");
        }
        else {
            conditionScriptService.save(conditionScript);
        }
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        ConditionScript conditionScript=conditionScriptService.getBy("id",id);
        conditionOrderService.deleteconditionorderbysubconid(conditionScript.getConditionid(),id,"脚本");
        conditionScriptService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ConditionScript conditionScript) {
        conditionScriptService.update(conditionScript);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ConditionScript conditionScript = conditionScriptService.getById(id);
        return ResultGenerator.genOkResult(conditionScript);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ConditionScript> list = conditionScriptService.listAll();
        PageInfo<ConditionScript> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }


    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final ConditionScript conditionScript) {
        Condition con=new Condition(ConditionScript.class);
        con.createCriteria().andCondition("conditionname = '" + conditionScript.getConditionname() + "'")
                .andCondition("subconditionname = '" + conditionScript.getSubconditionname().replace("'","''") + "'")
                .andCondition("id <> " + conditionScript.getId());
        if(conditionScriptService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("已存在该条件名或子条件名");
        }
        else {
            this.conditionScriptService.updateTestconditionapi(conditionScript);
            return ResultGenerator.genOkResult();
        }
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<ConditionScript> list = this.conditionScriptService.findtestconditionapiWithName(param);
        final PageInfo<ConditionScript> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
