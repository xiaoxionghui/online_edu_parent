package com.atguigu.edu.controller;


import com.atguigu.edu.entity.EduCourse;
import com.atguigu.edu.entity.EduTeacher;
import com.atguigu.edu.service.EduCourseService;
import com.atguigu.request.CourseCondition;
import com.atguigu.request.CourseInfoVO;
import com.atguigu.request.TeacherCondition;
import com.atguigu.response.EduCourseConfirmVo;
import com.atguigu.response.RetVal;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-08
 */
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 保存课程信息
     * @param courseInfoVO
     * @return
     */
    @PostMapping("saveCourseInfo")
    public RetVal saveCourseInfo(CourseInfoVO courseInfoVO){
        String courseId = eduCourseService.saveCourseInfo(courseInfoVO);
        return RetVal.success().data("courseId",courseId);
    }

    /**
     * 根据条件分页查询课程信息
     * @param pageNum
     * @param pageSize
     * @param courseCondition
     * @return
     */
    @GetMapping("queryCoursePageByCondition/{pageNum}/{pageSize}")
    public RetVal queryCoursePageByCondition(@PathVariable("pageNum") long pageNum,
                                              @PathVariable("pageSize") long pageSize,
                                              CourseCondition courseCondition){
        Page<EduCourse> coursePage = new Page<>(pageNum, pageSize);
        eduCourseService.queryCoursePageByCondition(coursePage,courseCondition);
        long total = coursePage.getTotal();
        List<EduCourse> records = coursePage.getRecords();
        return RetVal.success().data("total",total).data("courseList",records);
    }

    /**
     * 根据id查询课程信息
     * @param id
     * @return
     */
    @GetMapping("getCourseById/{id}")
    public RetVal getCourseById(@PathVariable String id){
        CourseInfoVO courseInfoVO = eduCourseService.getCourseById(id);
        return RetVal.success().data("courseInfoVO",courseInfoVO);
    }

    /**
     * 修改课程信息
     * @param courseInfoVO
     * @return
     */
    @PutMapping("updateCourseInfo")
    public RetVal updateCourseInfo(CourseInfoVO courseInfoVO){
        eduCourseService.updateCourseInfo(courseInfoVO);
        return RetVal.success();
    }

    /**
     * 发布课程确认
     * @param courseId
     * @return
     */
    @GetMapping("getCourseConfirmInfo/{courseId}")
    public RetVal getCourseConfirmInfo(@PathVariable String courseId){
        EduCourseConfirmVo courseConfirmVo = eduCourseService.getCourseConfirmInfo(courseId);
        return RetVal.success().data("courseConfirmVo",courseConfirmVo);
    }

    /**
     * 课程发布
     * @param courseId
     * @return
     */
    @GetMapping("publishCourse/{courseId}")
    public RetVal publishCourse(@PathVariable String courseId){
        EduCourse course = new EduCourse();
        course.setId(courseId);
        course.setStatus("Normal");
        eduCourseService.updateById(course);
        return RetVal.success();
    }

    /**
     * 删除课程信息
     * @param courseId
     * @return
     */
    @DeleteMapping("{courseId}")
    public RetVal deleteCourseByCourseId(@PathVariable String courseId){
        eduCourseService.deleteCourseByCourseId(courseId);
        return RetVal.success();
    }

}

