package com.atguigu.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @ClassName MemberCenterVo
 * @Description:
 * @Author xiaoxionghui
 * @Date 2020/11/18 19:30
 * @Version V1.0
 **/
@Data
public class MemberCenterVo {


    @ApiModelProperty(value = "会员id")
    private String id;
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "用户头像")
    private String avatar;

}
