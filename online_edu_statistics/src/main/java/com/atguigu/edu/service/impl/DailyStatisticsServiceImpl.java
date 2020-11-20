package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.DailyStatistics;
import com.atguigu.edu.mapper.DailyStatisticsMapper;
import com.atguigu.edu.service.DailyStatisticsService;
import com.atguigu.edu.service.UserServiceFeign;
import com.atguigu.response.RetVal;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-13
 */
@Service
public class DailyStatisticsServiceImpl extends ServiceImpl<DailyStatisticsMapper, DailyStatistics> implements DailyStatisticsService {
    @Autowired
    private UserServiceFeign userServiceFeign;

    /**
     * 生成指定日期数据
     * @param day
     */
    @Override
    public void generateData(String day) {
        DailyStatistics dailyStatistics = new DailyStatistics();
        RetVal retVal = userServiceFeign.queryRegisterNum(day);
        Integer registerNum= (Integer)retVal.getData().get("registerNum");
        dailyStatistics.setRegisterNum(registerNum);
        dailyStatistics.setLoginNum(RandomUtils.nextInt(300,400));
        dailyStatistics.setVideoViewNum(RandomUtils.nextInt(200,300));
        dailyStatistics.setCourseNum(RandomUtils.nextInt(400,600));
        dailyStatistics.setDateCalculated(day);
        baseMapper.insert(dailyStatistics);
    }

    /**
     * 显示数据
     * @param dateType
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public Map<String, Object> showStatistics(String dateType, String beginTime, String endTime) {
        QueryWrapper<DailyStatistics> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",beginTime,endTime);
        wrapper.orderByAsc("date_calculated");
        List<DailyStatistics> statisticsList = baseMapper.selectList(wrapper);
        List<String> xData = new ArrayList<>();
        List<Integer> yData = new ArrayList<>();
        for (DailyStatistics dailyStatistics : statisticsList) {
            String singleDay = dailyStatistics.getDateCalculated();
            xData.add(singleDay);
            switch (dateType){
                case "register_num":
                    Integer registerNum = dailyStatistics.getRegisterNum();
                    yData.add(registerNum);
                    break;
                case "login_num":
                    Integer loginNum = dailyStatistics.getLoginNum();
                    yData.add(loginNum);
                    break;
                case "video_view_num":
                    Integer videoViewNum = dailyStatistics.getVideoViewNum();
                    yData.add(videoViewNum);
                    break;
                case "course_num":
                    Integer courseNum = dailyStatistics.getCourseNum();
                    yData.add(courseNum);
                    break;
                default:
                    break;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("xData",xData);
        map.put("yData",yData);
        return map;
    }
}
