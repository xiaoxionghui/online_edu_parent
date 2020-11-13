package com.atguigu.edu.service;

import com.atguigu.edu.entity.EduTeacher;
import com.atguigu.request.TeacherCondition;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-02
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void queryTeacherPageByCondition(Page<EduTeacher> teacherPage, TeacherCondition teacherCondition);

}
