package com.atguigu.edu.controller;


import com.atguigu.edu.entity.EduChapter;
import com.atguigu.edu.service.EduChapterService;
import com.atguigu.response.ChapterVo;
import com.atguigu.response.RetVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-09
 */
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    /**
     * 查询章节和小节信息
     * @param courseId
     * @return
     */
    @GetMapping("getChapterAndSection/{courseId}")
    public RetVal getChapterAndSection(@PathVariable String courseId){
        List<ChapterVo> chapterSectionList= chapterService.getChapterAndSection(courseId);
        return RetVal.success().data("chapterSectionList",chapterSectionList);
    }

    /**
     * 添加章节
     * @param chapter
     * @return
     */
    @PostMapping
    public RetVal saveChapter(EduChapter chapter){
        boolean flag=chapterService.saveChapter(chapter);
        if(flag){
            return RetVal.success();
        }else {
            return RetVal.error();
        }
    }

    /**
     * 根据id查询章节
     * @param id
     * @return
     */
    @GetMapping("getChapter/{id}")
    public RetVal getChapter(@PathVariable String id){
        EduChapter chapter = chapterService.getById(id);
        return RetVal.success().data("chapter",chapter);
    }

    /**
     * 修改章节
     * @param chapter
     * @return
     */
    @PutMapping
    public RetVal updateChapter(EduChapter chapter){
        boolean flag = chapterService.updateById(chapter);
        if(flag){
            return RetVal.success();
        }else {
            return RetVal.error();
        }
    }

    /**
     * 删除章节
     * @param chapterId
     * @return
     */
    @DeleteMapping("{chapterId}")
    public RetVal deleteChapter(@PathVariable String chapterId){
        boolean flag = chapterService.deleteChapter(chapterId);
        if(flag){
            return RetVal.success();
        }else {
            return RetVal.error();
        }
    }
}

