package com.atguigu.edu.service;

import com.atguigu.edu.entity.EduCourse;
import com.atguigu.request.CourseCondition;
import com.atguigu.request.CourseInfoVO;
import com.atguigu.response.CourseDetailInfo;
import com.atguigu.response.EduCourseConfirmVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-08
 */
public interface EduCourseService extends IService<EduCourse> {
    /**
     * 保存课程信息
     * @param courseInfoVO
     * @return
     */
    String saveCourseInfo(CourseInfoVO courseInfoVO);

    /**
     * 根据条件分页查询课程信息
     * @param coursePage
     * @param courseCondition
     */
    void queryCoursePageByCondition(Page<EduCourse> coursePage, CourseCondition courseCondition);

    /**
     * 根据id查询课程信息
     * @param id
     * @return
     */
    CourseInfoVO getCourseById(String id);

    /**
     * 修改课程信息
     * @param courseInfoVO
     */
    void updateCourseInfo(CourseInfoVO courseInfoVO);

    /**
     * 发布课程
     * @param courseId
     * @return
     */
    EduCourseConfirmVo getCourseConfirmInfo(String courseId);

    /**
     * 删除课程
     * @param courseId
     */
    void deleteCourseByCourseId(String courseId);

    /**
     * 根据讲师id查询课程信息
     * @param teacherId
     * @return
     */
    List<EduCourse> queryCourseById(String teacherId);

    /**
     * 根据id查询课程详情
     * @param courseId
     * @return
     */
    CourseDetailInfo queryCourseDetailById(String courseId);
}
