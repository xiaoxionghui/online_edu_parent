package com.atguigu.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName EduException
 * @Description:自定义异常
 * @Author xiaoxionghui
 * @Date 2020/11/2 21:05
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("自定义异常类")
public class EduException extends RuntimeException{
    @ApiModelProperty(value = "异常状态码")
    private Integer code;
    @ApiModelProperty(value = "异常状态信息")
    private String message;

}
