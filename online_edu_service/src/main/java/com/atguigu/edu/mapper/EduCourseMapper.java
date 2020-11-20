package com.atguigu.edu.mapper;

import com.atguigu.edu.entity.EduCourse;
import com.atguigu.response.CourseDetailInfo;
import com.atguigu.response.EduCourseConfirmVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-08
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    EduCourseConfirmVo getCourseConfirmInfo(String courseId);

    /**
     * 查询课程详情
     * @param courseId
     * @return
     */
    CourseDetailInfo queryCourseDetailById(String courseId);
}
