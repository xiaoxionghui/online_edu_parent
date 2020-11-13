package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduChapter;
import com.atguigu.edu.entity.EduSection;
import com.atguigu.edu.mapper.EduChapterMapper;
import com.atguigu.edu.service.EduChapterService;
import com.atguigu.edu.service.EduSectionService;
import com.atguigu.exception.EduException;
import com.atguigu.response.ChapterVo;
import com.atguigu.response.SectionVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-09
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduSectionService sectionService;
    /**
     * 删除章节
     * @param chapterId
     * @return
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        //判断是否有小节
        QueryWrapper<EduSection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id",chapterId);
        int count = sectionService.count(queryWrapper);
        //没有小节
        if(count==0){
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }else{
            throw new EduException(20001,"该章节存在小节");
        }
    }

    /**
     * 添加章节
     * @param chapter
     * @return
     */
    @Override
    public boolean saveChapter(EduChapter chapter) {
        EduChapter existChapter = existChapter(chapter.getCourseId(), chapter.getTitle());
        if(existChapter==null){
            int insert = baseMapper.insert(chapter);
            return insert>0;
        }else{
            throw new EduException(20001,"章节已经重复");
        }

    }

    /**
     * 查询章节和小节信息
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> getChapterAndSection(String courseId) {
        //a.查询所有的章节信息
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduChapter> chapters = baseMapper.selectList(wrapper);
        //b.查询所有的小节信息
        QueryWrapper<EduSection> sectionWrapper = new QueryWrapper<>();
        sectionWrapper.eq("course_id",courseId);
        List<EduSection> sectionList = sectionService.list(sectionWrapper);
        //c.把小节封装到章节里面 迭代章节 进行转换
        //1.提取章节信息
        List<ChapterVo> chapterVos = new ArrayList<>();//先创建一个空的章节集合，用来存放章节和小节信息
        for (EduChapter chapter : chapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            //2.迭代小节
            List<SectionVo> sectionVos = new ArrayList<>();
            for (EduSection section : sectionList) {
                //对比小节里面的chapterId与章节id,如果相等就提取小节里面的信息，放到同一个集合中
                if (section.getChapterId().equals(chapter.getId())){
                    SectionVo sectionVo = new SectionVo();
                    BeanUtils.copyProperties(section,sectionVo);
                    sectionVos.add(sectionVo);
                }
            }
            //把该章节下的全部小节放入该章节中
            chapterVo.setChildren(sectionVos);
            chapterVos.add(chapterVo);
        }
        return chapterVos;
    }

    /**
     * 根据课程id删除章节
     * @param courseId
     */
    @Override
    public void deleteChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }

    public EduChapter existChapter(String courseId,String courseName){
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("title",courseName);
        EduChapter eduChapter = baseMapper.selectOne(wrapper);
        return eduChapter;
    }
}
