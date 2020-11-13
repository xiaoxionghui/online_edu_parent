package com.atguigu.edu.controller;


import com.atguigu.edu.entity.EduSubject;
import com.atguigu.edu.service.EduSubjectService;
import com.atguigu.response.RetVal;
import com.atguigu.response.SubjectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-06
 */
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin//解决跨域问题
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    /**
     * 查询所有课程分类信息
     * @return
     * @throws Exception
     */
    @GetMapping("getAllSubject")
    public RetVal getAllSubject() throws Exception {
        List<SubjectVo> allSubject= eduSubjectService.getAllSubject();
        return RetVal.success().data("allSubject",allSubject);
    }

    /**
     * 导入课程分类信息
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("uploadSubject")
    public RetVal uploadSubject(MultipartFile file) throws Exception {
        eduSubjectService.uploadSubject(file);
        return RetVal.success();
    }

    /**
     * 根据id删除节点
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public RetVal deleteSubjectById(@PathVariable String id){
        boolean flag = eduSubjectService.deleteSubjectById(id);
        if (flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }

    /**
     * 添加一级分类
     * @return
     */
    @PostMapping("saveParentSubject")
    public RetVal saveParentSubject(EduSubject eduSubject){
        boolean flag = eduSubjectService.saveParentSubject(eduSubject);
        if (flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }

    /**
     * 添加二级分类
     * @param eduSubject
     * @return
     */
    @PostMapping("saveChildSubject")
    public RetVal saveChildSubject(EduSubject eduSubject){
        boolean flag = eduSubjectService.saveChildSubject(eduSubject);
        if (flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }
}

