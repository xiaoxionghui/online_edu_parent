package com.atguigu.edu.service;

import com.atguigu.edu.entity.DailyStatistics;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-13
 */
public interface DailyStatisticsService extends IService<DailyStatistics> {
    /**
     * 生成指定日期数据
     * @param day
     */
    void generateData(String day);

    /**
     * 显示数据
     * @param dateType
     * @param beginTime
     * @param endTime
     * @return
     */
    Map<String, Object> showStatistics(String dateType, String beginTime, String endTime);
}
