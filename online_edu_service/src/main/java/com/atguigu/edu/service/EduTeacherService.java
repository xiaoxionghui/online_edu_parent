package com.atguigu.edu.service;

import com.atguigu.edu.entity.EduTeacher;
import com.atguigu.request.TeacherCondition;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-02
 */
public interface EduTeacherService extends IService<EduTeacher> {
    /**
     * 分页查询讲师（后台管理）
     * @param teacherPage
     * @param teacherCondition
     */
    void queryTeacherPageByCondition(Page<EduTeacher> teacherPage, TeacherCondition teacherCondition);

    /**
     * 分页查询讲师（前台页面）
     * @param teacherPage
     * @return
     */
    Map<String, Object> queryTeacherPage(Page<EduTeacher> teacherPage);
}
