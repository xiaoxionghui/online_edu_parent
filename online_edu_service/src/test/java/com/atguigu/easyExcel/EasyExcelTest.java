package com.atguigu.easyExcel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName EasyExcelTest
 * @Description:
 * @Author xiaoxionghui
 * @Date 2020/11/6 19:51
 * @Version V1.0
 **/
public class EasyExcelTest {

    @Test
    public void testWriteExcel(){
        String filePath = "D:\\work\\one.xlsx";
        List<Object> data = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            Student student = new Student();
            student.setStuName("老王"+i);
            student.setStuNum(i);
            data.add(student);
        }
        EasyExcel.write(filePath,Student.class).sheet("学生列表").doWrite(data);
    }

    @Test
    public void testReadExcel(){
        String filePath = "D:\\work\\one.xlsx";
        EasyExcel.read(filePath,Student.class,new ReadStudentListener()).doReadAll();
    }
}
