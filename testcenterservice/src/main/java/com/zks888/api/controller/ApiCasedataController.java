package com.zks888.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zks888.api.core.response.Result;
import com.zks888.api.core.response.ResultGenerator;
import com.zks888.api.dto.Casedata;
import com.zks888.api.entity.ApiCasedata;
import com.zks888.api.entity.ApiParams;
import com.zks888.api.entity.Apicases;
import com.zks888.api.service.ApiCasedataService;
import com.zks888.api.service.ApiParamsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author zks888
 * @date 2020/09/21
 */
@RestController
@RequestMapping("/api/casedata")
public class ApiCasedataController {
    @Resource
    private ApiCasedataService apiCasedataService;
    @Resource
    private ApiParamsService apiParamsService;

    @PostMapping
    public Result add(@RequestBody Casedata apiCasedata) {
        apiCasedataService.save(apiCasedata);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        apiCasedataService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ApiCasedata apiCasedata) {
        apiCasedataService.update(apiCasedata);
        return ResultGenerator.genOkResult();
    }

    /**
     * 更新BodyData
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final ApiCasedata apiCasedata) {
        if(apiCasedata.getId()==null)
        {
            apiCasedataService.save(apiCasedata);
        }
        else
        {
            if(!apiCasedata.getApiparamvalue().isEmpty())
            {
                this.apiCasedataService.update(apiCasedata);
            }
        }

        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ApiCasedata apiCasedata = apiCasedataService.getById(id);
        return ResultGenerator.genOkResult(apiCasedata);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ApiCasedata> list = apiCasedataService.listAll();
        PageInfo<ApiCasedata> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 根据caseid和参数类型返回参数值
     */
    @PostMapping("/casevalue")
    public Result casevalue(@RequestBody final Map<String, Object> param) {
        final List<ApiCasedata> list = this.apiCasedataService.getparamvaluebycaseidandtype(param);
        final PageInfo<ApiCasedata> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 根据用例值的保存实际是更新参数值操作
     */
    @PostMapping("/updatepropertydata")
    public Result updatepropertydata(@RequestBody List<ApiCasedata> apiCasedataList) {
        for (ApiCasedata apiCasedata :apiCasedataList) {
            apiCasedataService.update(apiCasedata);
        }
        return ResultGenerator.genOkResult();
    }


    /**
     * 根据caseid,apiid,参数类型处理body，如果用例数据没数据，则取参数名
     */
    @PostMapping("/casevalueforbody")
    public Result casevalueforbody(@RequestBody final Map<String, Object> param) {
        final List<ApiCasedata> list = this.apiCasedataService.getparamvaluebycaseidandtype(param);
        String BodyValue="";
        if(list.size()==0)
        {
            Long apiid=Long.parseLong(param.get("apiid").toString());
            String propertytype=param.get("propertytype").toString();
            List<ApiParams> apiParams= apiParamsService.getApiParamsbypropertytype(apiid,propertytype);
            if(apiParams.size()>0)
            {
                BodyValue =apiParams.get(0).getKeyname();
            }
            return ResultGenerator.genOkResult(BodyValue);
        }
        else
        {
            BodyValue=list.get(0).getApiparamvalue();
            return ResultGenerator.genOkResult(BodyValue);
        }
    }

}
