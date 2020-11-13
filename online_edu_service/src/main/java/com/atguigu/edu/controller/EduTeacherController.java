package com.atguigu.edu.controller;


import com.atguigu.edu.entity.EduTeacher;
import com.atguigu.edu.service.EduTeacherService;
import com.atguigu.request.TeacherCondition;
import com.atguigu.response.RetVal;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sun.org.apache.bcel.internal.generic.RET;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.ReverbType;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-02
 */
@RestController
@RequestMapping("/edu")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询所有讲师
     * @return
     */
    @GetMapping
    public RetVal queryAllTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);
//        try {
//            int i =1/0;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return RetVal.success().data("teacherList",list);
    }

    /**
     * 根据id删除讲师
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public RetVal deleteTeacherById(@PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if (flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/queryAllTeacherByPage/{pageNum}/{pageSize}")
    public RetVal queryAllTeacherByPage(@PathVariable("pageNum") long pageNum,
                                        @PathVariable("pageSize") long pageSize){
        Page<EduTeacher> page = new Page<>(pageNum, pageSize);
        eduTeacherService.page(page, null);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return RetVal.success().data("total",total).data("teacherList",records);
    }

    /**
     * 分页带条件查询
     * @param pageNum
     * @param pageSize
     * @param teacherCondition
     * @return
     */
    @GetMapping("/queryTeacherPageByCondition/{pageNum}/{pageSize}")
    public RetVal queryTeacherPageByCondition(@PathVariable("pageNum") long pageNum,
                                              @PathVariable("pageSize") long pageSize,
                                              TeacherCondition teacherCondition){
        Page<EduTeacher> teacherPage = new Page<>(pageNum, pageSize);
        eduTeacherService.queryTeacherPageByCondition(teacherPage,teacherCondition);
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();
        return RetVal.success().data("total",total).data("teacherList",records);
    }

    /**
     * 添加讲师
     * @param teacher
     * @return
     */
    @PostMapping
    public RetVal saveTeacher(EduTeacher teacher){
        boolean save = eduTeacherService.save(teacher);
        if (save){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }

    /**
     * 修改讲师
     * @param teacher
     * @return
     */
    @PutMapping
    public RetVal updateTeacher(EduTeacher teacher){
        boolean flag = eduTeacherService.updateById(teacher);
        if (flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }

    /**
     * 根据id查询讲师
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public RetVal queryTeacherById(@PathVariable String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return RetVal.success().data("teacher",teacher);
    }


}

