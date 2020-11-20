package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduTeacher;
import com.atguigu.edu.mapper.EduTeacherMapper;
import com.atguigu.edu.service.EduTeacherService;
import com.atguigu.request.TeacherCondition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-02
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void queryTeacherPageByCondition(Page<EduTeacher> teacherPage, TeacherCondition teacherCondition) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String beginTime = teacherCondition.getBeginTime();
        String endTime = teacherCondition.getEndTime();
        Integer level = teacherCondition.getLevel();
        String name = teacherCondition.getName();
        if (StringUtils.isNotEmpty(name)){
            wrapper.like("name",name);
        }
        if (level!=null){
            wrapper.eq("level",level);
        }
        if (StringUtils.isNotEmpty(beginTime)){
            wrapper.ge("gmt_create",beginTime);
        }
        if (StringUtils.isNotEmpty(endTime)){
            wrapper.le("gmt_create",endTime);
        }
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(teacherPage, wrapper);

    }

    /**
     * 后台分页查询讲师
     * @param teacherPage
     * @return
     */
    @Override
    public Map<String, Object> queryTeacherPage(Page<EduTeacher> teacherPage) {
        baseMapper.selectPage(teacherPage,null);
        List<EduTeacher> teacherList = teacherPage.getRecords();
        long total = teacherPage.getTotal();
        long size = teacherPage.getSize();
        long current = teacherPage.getCurrent();
        boolean hasNext = teacherPage.hasNext();
        boolean hasPrevious = teacherPage.hasPrevious();
        long pages = teacherPage.getPages();
        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("size",size);
        map.put("currentPage",current);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        map.put("teacherList",teacherList);
        map.put("pages",pages);
        return map;
    }

}
