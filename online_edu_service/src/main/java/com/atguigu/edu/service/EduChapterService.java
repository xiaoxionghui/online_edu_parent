package com.atguigu.edu.service;

import com.atguigu.edu.entity.EduChapter;
import com.atguigu.response.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-09
 */
public interface EduChapterService extends IService<EduChapter> {
    /**
     * 删除章节
     * @param chapterId
     * @return
     */
    boolean deleteChapter(String chapterId);

    /**
     * 添加章节
     * @param chapter
     * @return
     */
    boolean saveChapter(EduChapter chapter);

    /**
     * 查询章节和小节信息
     * @param courseId
     * @return
     */
    List<ChapterVo> getChapterAndSection(String courseId);

    /**
     * 根据课程id删除章节
     * @param courseId
     */
    void deleteChapterByCourseId(String courseId);
}
