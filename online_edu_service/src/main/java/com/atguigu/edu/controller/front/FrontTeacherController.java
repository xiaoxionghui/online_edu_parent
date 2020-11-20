package com.atguigu.edu.controller.front;

import com.atguigu.edu.entity.EduCourse;
import com.atguigu.edu.entity.EduTeacher;
import com.atguigu.edu.service.EduCourseService;
import com.atguigu.edu.service.EduTeacherService;
import com.atguigu.request.CourseInfoVO;
import com.atguigu.response.RetVal;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName FrontTeacherController
 * @Description:
 * @Author xiaoxionghui
 * @Date 2020/11/16 18:11
 * @Version V1.0
 **/
@RestController
@RequestMapping("/edu/front")
@CrossOrigin
public class FrontTeacherController {
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    /**
     * 前台讲师分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/queryTeacherPage/{pageNum}/{pageSize}")
    public RetVal queryTeacherPage(@PathVariable("pageNum") long pageNum,
                                   @PathVariable("pageSize") long pageSize){
        Page<EduTeacher> teacherPage = new Page<>(pageNum,pageSize);
        Map<String,Object> retMap = teacherService.queryTeacherPage(teacherPage);
        return RetVal.success().data(retMap);
    }

    /**
     * 根据讲师id查询讲师详情
     * @param teacherId
     * @return
     */
    @GetMapping("/queryTeacherDetailById/{teacherId}")
    public RetVal queryTeacherDetailById(@PathVariable("teacherId") String teacherId){
        EduTeacher teacher = teacherService.getById(teacherId);
        List<EduCourse> courseList = courseService.queryCourseById(teacherId);
        return RetVal.success().data("teacher",teacher).data("courseList",courseList);
    }
}
