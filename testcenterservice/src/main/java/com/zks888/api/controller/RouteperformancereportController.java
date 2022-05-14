package com.zks888.api.controller;

import com.zks888.api.core.response.Result;
import com.zks888.api.core.response.ResultGenerator;
import com.zks888.api.entity.Routeperformancereport;
import com.zks888.api.service.RouteperformancereportService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zks888
 * @date 2022/04/13
 */
@RestController
@RequestMapping("/routeperformancereport")
public class RouteperformancereportController {
    @Resource
    private RouteperformancereportService routeperformancereportService;

    @PostMapping
    public Result add(@RequestBody Routeperformancereport routeperformancereport) {
        routeperformancereportService.save(routeperformancereport);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        routeperformancereportService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Routeperformancereport routeperformancereport) {
        routeperformancereportService.update(routeperformancereport);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Routeperformancereport routeperformancereport = routeperformancereportService.getById(id);
        return ResultGenerator.genOkResult(routeperformancereport);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Routeperformancereport> list = routeperformancereportService.listAll();
        PageInfo<Routeperformancereport> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
