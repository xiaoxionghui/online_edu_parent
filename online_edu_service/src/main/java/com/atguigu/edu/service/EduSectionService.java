package com.atguigu.edu.service;

import com.atguigu.edu.entity.EduSection;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程小节 服务类
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-09
 */
public interface EduSectionService extends IService<EduSection> {
    /**
     * 添加小节
     * @param section
     */
    void addSection(EduSection section);

    /**
     * 删除小节
     * @param id
     */
    void deleteSection(String id);

    /**
     * 根据课程id删除小节
     * @param courseId
     */
    void deleteSectionByCourseId(String courseId);
}
