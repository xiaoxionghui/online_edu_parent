package com.atguigu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.edu.entity.EduSubject;
import com.atguigu.edu.entity.ExcelSubject;
import com.atguigu.edu.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName MySubjectListener
 * @Description:
 * @Author xiaoxionghui
 * @Date 2020/11/6 20:47
 * @Version V1.0
 **/
public class MySubjectListener extends AnalysisEventListener<ExcelSubject> {
    @Autowired
    private EduSubjectService eduSubjectService;

    @Override
    public void invoke(ExcelSubject excelSubject, AnalysisContext analysisContext) {
        //(1).读取到的数据分为两列 第一列是parent 第二列是child
        String parentSubjectName = excelSubject.getParentSubjectName();
        //(2).保存一级分类判断数据库中是否已经存在
        EduSubject eduSubject = eduSubjectService.existSubject("0", parentSubjectName);
        if (eduSubject==null){
            EduSubject eduSubject1 = new EduSubject();
            eduSubject1.setParentId("0");
            eduSubject1.setTitle(parentSubjectName);
            eduSubjectService.save(eduSubject1);
        }
        //(3).二级分类的parentId=一级分类的id
        String parentId = eduSubject.getParentId();
        String childSubjectName = excelSubject.getChildSubjectName();
        EduSubject childSubject = eduSubjectService.existSubject(parentId, childSubjectName);
        if (childSubject==null){
            childSubject = new EduSubject();
            childSubject.setTitle(childSubjectName);
            childSubject.setParentId(parentId);
            eduSubjectService.save(childSubject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("read over");
    }
}
