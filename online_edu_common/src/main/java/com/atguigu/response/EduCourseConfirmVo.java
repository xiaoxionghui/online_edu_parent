package com.atguigu.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName EduCourseConfirmVo
 * @Description:
 * @Author xiaoxionghui
 * @Date 2020/11/10 16:23
 * @Version V1.0
 **/
@Data
public class EduCourseConfirmVo {
    @ApiModelProperty(value = "课程id")
    private String courseId;

    @ApiModelProperty(value = "讲师")
    private String teacherName;

    @ApiModelProperty(value = "课程标题")
    private String courseName;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "一级分类名称")
    private String parentSubjectName;

    @ApiModelProperty(value = "一级分类名称")
    private String childSubjectName;
}