package com.atguigu.edu.controller;


import com.atguigu.edu.service.DailyStatisticsService;
import com.atguigu.response.RetVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.PipedReader;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-13
 */
@RestController
@RequestMapping("/daily/statistics")
@CrossOrigin
public class DailyStatisticsController {
    @Autowired
    private DailyStatisticsService statisticsService;

    /**
     * 生成指定日期数据
     * @param day
     * @return
     */
    @GetMapping("generateData/{day}")
    public RetVal generateData(@PathVariable("day") String day){
        statisticsService.generateData(day);
        return RetVal.success();
    }

    /**
     *显示数据
     * @param dateType
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("showStatistics/{dateType}/{beginTime}/{endTime}")
    public RetVal showStatistics(@PathVariable("dateType") String dateType,
                                 @PathVariable("beginTime")String beginTime,
                                    @PathVariable("endTime") String endTime){

        Map<String,Object> showMap = statisticsService.showStatistics(dateType,beginTime,endTime);
        return RetVal.success().data(showMap);
    }
}

