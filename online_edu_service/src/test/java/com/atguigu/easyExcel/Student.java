package com.atguigu.easyExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @ClassName Student
 * @Description:
 * @Author xiaoxionghui
 * @Date 2020/11/6 19:57
 * @Version V1.0
 **/
@Data
public class Student {
    @ExcelProperty(value = "学生编号",index = 1)
    private Integer stuNum;
    @ExcelProperty(value = "学生姓名",index = 0)
    private String stuName;

}
