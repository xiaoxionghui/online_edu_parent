package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduCourse;
import com.atguigu.edu.entity.EduCourseDescription;
import com.atguigu.edu.mapper.EduCourseMapper;
import com.atguigu.edu.service.EduChapterService;
import com.atguigu.edu.service.EduCourseDescriptionService;
import com.atguigu.edu.service.EduCourseService;
import com.atguigu.edu.service.EduSectionService;
import com.atguigu.request.CourseCondition;
import com.atguigu.request.CourseInfoVO;
import com.atguigu.response.EduCourseConfirmVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-08
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService descriptionService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduSectionService sectionService;

    @Autowired
    private  EduCourseService courseService;
    /**
     * 保存课程信息
     * @param courseInfoVO
     * @return
     */
    @Override
    public String saveCourseInfo(CourseInfoVO courseInfoVO) {
        //c.保存课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO,eduCourse);
        baseMapper.insert(eduCourse);
        //d.保存课程描述信息
        //e.它们两公用一个主键id
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(eduCourse.getId());
        eduCourseDescription.setDescription(courseInfoVO.getDescription());
        descriptionService.save(eduCourseDescription);
        return eduCourse.getId();
    }

    /**
     * 根据条件分页查询课程信息
     * @param coursePage
     * @param courseCondition
     */
    @Override
    public void queryCoursePageByCondition(Page<EduCourse> coursePage, CourseCondition courseCondition) {
        String title = courseCondition.getTitle();
        String status = courseCondition.getStatus();
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(title)){
            wrapper.like("title",title);
        }
        if (StringUtils.isNotEmpty(status)){
            wrapper.ge("status",status);
        }
        baseMapper.selectPage(coursePage,wrapper);
    }

    /**
     * 根据id查询课程
     * @param id
     * @return
     */
    @Override
    public CourseInfoVO getCourseById(String id) {
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        EduCourse eduCourse = baseMapper.selectById(id);
        BeanUtils.copyProperties(eduCourse,courseInfoVO);
        EduCourseDescription description = descriptionService.getById(id);
        if (description!=null){
            courseInfoVO.setDescription(description.getDescription());
        }
        return courseInfoVO;
    }

    /**
     * 修改课程信息
     * @param courseInfoVO
     */
    @Override
    public void updateCourseInfo(CourseInfoVO courseInfoVO) {
        //c.保存课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO,eduCourse);
        baseMapper.updateById(eduCourse);
        //d.保存课程描述信息
        //e.它们两公用一个主键id
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(eduCourse.getId());
        eduCourseDescription.setDescription(courseInfoVO.getDescription());
        descriptionService.updateById(eduCourseDescription);
    }

    @Override
    public EduCourseConfirmVo getCourseConfirmInfo(String courseId) {
        return baseMapper.getCourseConfirmInfo(courseId);
    }

    /**
     * 删除课程
     * @param courseId
     */
    @Transactional
    @Override
    public void deleteCourseByCourseId(String courseId) {
        //删除章节
        chapterService.deleteChapterByCourseId(courseId);
        //删除小节
        sectionService.deleteSectionByCourseId(courseId);
        //删除课程基本信息
        baseMapper.deleteById(courseId);
        //删除课程对应的描述信息
        descriptionService.removeById(courseId);
    }
}
