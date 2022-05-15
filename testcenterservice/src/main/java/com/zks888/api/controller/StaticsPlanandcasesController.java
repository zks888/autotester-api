package com.zks888.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zks888.api.core.response.Result;
import com.zks888.api.core.response.ResultGenerator;
import com.zks888.api.dto.StaticsDataForLine;
import com.zks888.api.entity.StaticsPlanandcases;
import com.zks888.api.service.StaticsPlanandcasesService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zks888
 * @date 2021/04/14
 */
@RestController
@RequestMapping("/statics/planandcases")
public class StaticsPlanandcasesController {
    @Resource
    private StaticsPlanandcasesService staticsPlanandcasesService;

    @PostMapping
    public Result add(@RequestBody StaticsPlanandcases staticsPlanandcases) {
        staticsPlanandcasesService.save(staticsPlanandcases);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        staticsPlanandcasesService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody StaticsPlanandcases staticsPlanandcases) {
        staticsPlanandcasesService.update(staticsPlanandcases);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        StaticsPlanandcases staticsPlanandcases = staticsPlanandcasesService.getById(id);
        return ResultGenerator.genOkResult(staticsPlanandcases);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<StaticsPlanandcases> list = staticsPlanandcasesService.listAll();
        PageInfo<StaticsPlanandcases> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @GetMapping("/getlastdays")
    public Result getlastdays() {
        List<String> lastdaylist = new ArrayList<>();
        for (int i = 15; i > 0; i--) {
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, -i);
            int MONTH = calendar.get(Calendar.MONTH) + 1;
            int Day = calendar.get(Calendar.DAY_OF_MONTH);
            lastdaylist.add(MONTH + "-" + Day);
        }
        return ResultGenerator.genOkResult(lastdaylist);
    }

    @GetMapping("/getplanstatics")
    public Result getplanstatics() {
        Condition planstaticscond = new Condition(StaticsPlanandcases.class);
        Date startDate = DateUtils.addDays(new Date(), -15);
        String startDateStr = new SimpleDateFormat("yyyy-MM-dd").format(startDate.getTime());
        planstaticscond.createCriteria().andCondition("statics_date >= '" + startDateStr + "'");
        List<StaticsPlanandcases> list = staticsPlanandcasesService.listByCondition(planstaticscond);

        HashMap<String, Integer> satiates = new HashMap<>();
        satiates.put(startDateStr, 0);
        for (int i=14; i>0; i--) {
            Date date = DateUtils.addDays(new Date(), -i);
            String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date.getTime());
            satiates.put(dateStr, 15-i);
        }

        HashMap<String, List<Double>> tmp = new HashMap<>();
        for (StaticsPlanandcases staticsPlanandcases : list) {
            if (!tmp.containsKey(staticsPlanandcases.getTestplanname())) {
                Double[] tmpArr = new Double[15];
                Arrays.fill(tmpArr, 0.0);
                List<Double> planstaticsdatelist = Arrays.asList(tmpArr);
                Date date = staticsPlanandcases.getStaticsDate();
                String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date.getTime());
                planstaticsdatelist.set(satiates.get(dateStr), staticsPlanandcases.getPassrate());
                tmp.put(staticsPlanandcases.getTestplanname(), planstaticsdatelist);
            } else {
                Date date = staticsPlanandcases.getStaticsDate();
                String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date.getTime());
                tmp.get(staticsPlanandcases.getTestplanname()).set(satiates.get(dateStr), staticsPlanandcases.getPassrate());
            }
        }

        List<StaticsDataForLine> staticsDataForLineList = new ArrayList<>();
        for (String PlanName : tmp.keySet()) {
            StaticsDataForLine staticsDataForLine = new StaticsDataForLine();
            staticsDataForLine.setExecPlanName(PlanName);
            staticsDataForLine.setPassPecent(tmp.get(PlanName));
            staticsDataForLineList.add(staticsDataForLine);
        }
        return ResultGenerator.genOkResult(staticsDataForLineList);
    }

    public static void main(String[] args) throws ParseException {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        int Year = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH) + 1;
        int Day = calendar.get(Calendar.DATE);
        String StaticsDay = Year + "-" + MONTH + "-" + Day;
        System.out.println("MONTH Day is " + StaticsDay);

//        for(int i=15;i>0;i--)
//        {
//            //Date date = new Date();
//            Calendar calendar = Calendar.getInstance();
//            //calendar.setTime(date);
//            calendar.add(Calendar.DATE,-i);
//            int MONTH = calendar.get(Calendar.MONTH)+1;
//            int Day = calendar.get(Calendar.DAY_OF_MONTH);
//            System.out.println("MONTH Day is "+MONTH+"-"+Day);
//        }
    }
}
