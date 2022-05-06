package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.ConditionApi;
import com.zoctan.api.entity.ConditionDb;
import com.zoctan.api.service.ConditionDbService;
import com.zoctan.api.service.ConditionOrderService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author zks888
 * @date 2021/05/30
 */
@RestController
@RequestMapping("/condition/db")
public class ConditionDbController {
    @Resource
    private ConditionDbService conditionDbService;
    @Resource
    private ConditionOrderService conditionOrderService;

    @PostMapping
    public Result add(@RequestBody ConditionDb conditionDb) {

        Condition con=new Condition(ConditionDb.class);
        con.createCriteria().andCondition("conditionname = '" + conditionDb.getConditionname()+ "'")
                .andCondition("subconditionname = '" + conditionDb.getSubconditionname().replace("'","''")+ "'");
        if(conditionDbService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("已经存在该子条件名");
        }
        else {
            conditionDbService.save(conditionDb);
        }
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        ConditionDb conditionDb=conditionDbService.getBy("id",id);
        conditionOrderService.deleteconditionorderbysubconid(conditionDb.getConditionid(),id,"数据库");
        conditionDbService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ConditionDb conditionDb) {
        conditionDbService.update(conditionDb);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ConditionDb conditionDb = conditionDbService.getById(id);
        return ResultGenerator.genOkResult(conditionDb);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ConditionDb> list = conditionDbService.listAll();
        PageInfo<ConditionDb> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @GetMapping("/listalldbcondition")
    public Result listalldbcondition() {
        List<ConditionDb> list = conditionDbService.listAll();
        PageInfo<ConditionDb> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final ConditionDb conditionDb) {
        Condition con=new Condition(ConditionApi.class);
        con.createCriteria().andCondition("conditionname = '" + conditionDb.getConditionname()+ "'")
                .andCondition("subconditionname = '" + conditionDb.getSubconditionname().replace("'","''")+ "'")
                .andCondition("id <> " + conditionDb.getId());
        if(conditionDbService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("已经存在该子条件名");
        }
        else {
            this.conditionDbService.updateTestconditiondb(conditionDb);
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
        final List<ConditionDb> list = this.conditionDbService.finddbconditionWithName(param);
        final PageInfo<ConditionDb> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
