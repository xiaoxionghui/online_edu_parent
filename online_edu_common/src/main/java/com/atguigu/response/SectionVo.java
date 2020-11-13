package com.atguigu.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SectionVo
 * @Description:
 * @Author xiaoxionghui
 * @Date 2020/11/9 20:45
 * @Version V1.0
 **/
@Data
public class SectionVo {
    @ApiModelProperty(value = "小节ID")
    private String id;
    @ApiModelProperty(value = "节点名称")
    private String title;
}
