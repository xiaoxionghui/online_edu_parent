package com.atguigu.edu.controller.front;

import com.atguigu.edu.service.EduChapterService;
import com.atguigu.edu.service.EduCourseService;
import com.atguigu.response.ChapterVo;
import com.atguigu.response.CourseDetailInfo;
import com.atguigu.response.RetVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName FrontCourseController
 * @Description:
 * @Author xiaoxionghui
 * @Date 2020/11/16 21:17
 * @Version V1.0
 **/

@RestController
@RequestMapping("/edu/front/course")
@CrossOrigin
public class FrontCourseController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;

    /**
     * 课程详情查询
     * @param courseId
     * @return
     */
    @GetMapping("/queryCourseDetailById/{courseId}")
    public RetVal queryCourseDetailById(@PathVariable("courseId") String courseId){
        List<ChapterVo> chapterAndSection = chapterService.getChapterAndSection(courseId);
        CourseDetailInfo courseDetailInfo = courseService.queryCourseDetailById(courseId);
        return RetVal.success().data("chapterAndSection",chapterAndSection).data("courseDetailInfo",courseDetailInfo);
    }

}
