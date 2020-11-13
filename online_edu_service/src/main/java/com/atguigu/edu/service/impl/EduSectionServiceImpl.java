package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduSection;
import com.atguigu.edu.mapper.EduSectionMapper;
import com.atguigu.edu.service.EduSectionService;
import com.atguigu.edu.service.VideoServiceFeign;
import com.atguigu.exception.EduException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程小节 服务实现类
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-09
 */
@Service
public class EduSectionServiceImpl extends ServiceImpl<EduSectionMapper, EduSection> implements EduSectionService {
    @Autowired
    private VideoServiceFeign videoServiceFeign;
    /**
     * 添加小节
     * @param section
     */
    @Override
    public void addSection(EduSection section) {
        //1.判断是否存在小节
        QueryWrapper<EduSection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",section.getCourseId());
        queryWrapper.eq("chapter_id",section.getChapterId());
        queryWrapper.eq("title",section.getTitle());
        EduSection existSection = baseMapper.selectOne(queryWrapper);
        if(existSection==null){
            baseMapper.insert(section);
        }else{
            throw new EduException(20001,"存在重复的小节");
        }
    }

    /**
     * 删除小节
     * @param id
     */
    @Override
    public void deleteSection(String id) {
        EduSection section = baseMapper.selectById(id);
        String videoSourceId = section.getVideoSourceId();
        if (StringUtils.isNotEmpty(videoSourceId)){
            //远程调用删除视频
            videoServiceFeign.deleteSingleVideo(videoSourceId);
        }
        baseMapper.deleteById(id);
    }

    /**
     * 根据课程id删除小节
     * @param courseId
     */
    @Override
    public void deleteSectionByCourseId(String courseId) {
        //根据课程id查询所有小节
        QueryWrapper<EduSection> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduSection> sectionList = baseMapper.selectList(wrapper);
        List<String> videoIdList = new ArrayList<>();
        //迭代所有小节
        for (EduSection section : sectionList) {
            String videoSourceId = section.getVideoSourceId();
            if (StringUtils.isNotEmpty(videoSourceId)){
                videoIdList.add(videoSourceId);
            }
        }
        // 远程调用video微服务批量删除
        videoServiceFeign.deleteMultiVideo(videoIdList);
        //删除小节信息
        baseMapper.delete(wrapper);
    }
}
