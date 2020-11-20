package com.atguigu.edu.controller.front;


import com.atguigu.edu.entity.EduBanner;
import com.atguigu.edu.entity.EduCourse;
import com.atguigu.edu.entity.EduTeacher;
import com.atguigu.edu.service.EduBannerService;
import com.atguigu.edu.service.EduCourseService;
import com.atguigu.edu.service.EduTeacherService;
import com.atguigu.response.RetVal;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/edu/banner")
@CrossOrigin
public class FrontBannerController {
    @Autowired
    private EduBannerService bannerService;
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;


    /**
     * 热点数据讲师课程查询
     * @return
     */
    @Cacheable(value = "teacherCourse",key = "'teacher'")
    @GetMapping("queryTeacherAndCourse")
    public RetVal queryTeacherAndCourse(){
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> courseList = courseService.list(wrapper);

        QueryWrapper<EduTeacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("sort");
        teacherQueryWrapper.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(teacherQueryWrapper);
        return RetVal.success().data("courseList",courseList).data("teacherList",teacherList);

    }

    /**
     * 前台banner查询
     * @return
     */
    @Cacheable(value = "banner",key = "'indexInfo'")
    @GetMapping("getAllBanner")
    public RetVal getAllBanner(){
        List<EduBanner> banners = bannerService.list(null);
        return RetVal.success().data("bannerList",banners);
    }
}

