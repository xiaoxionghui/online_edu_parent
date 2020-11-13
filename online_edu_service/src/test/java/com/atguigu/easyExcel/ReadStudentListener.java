package com.atguigu.easyExcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @ClassName ReadStudentListener
 * @Description:
 * @Author xiaoxionghui
 * @Date 2020/11/6 20:21
 * @Version V1.0
 **/
public class ReadStudentListener extends AnalysisEventListener<Student> {

    /**
     * 一行一行地读取数据
     * @param student
     * @param analysisContext
     */
    @Override
    public void invoke(Student student, AnalysisContext analysisContext) {
        System.out.println(student);
    }

    /**
     * 读取表头信息
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("读取完毕");
    }


    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println(headMap);
    }
}
