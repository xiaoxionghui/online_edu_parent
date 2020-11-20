package com.atguigu.edu.controller;


import com.atguigu.edu.service.MemberCenterService;
import com.atguigu.response.MemberCenterVo;
import com.atguigu.response.RetVal;
import com.atguigu.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-13
 */
@RestController
@RequestMapping("/member/center")
@CrossOrigin
public class MemberCenterController {
    @Autowired
    private MemberCenterService memberCenterService;

    /**
     * 查询每天注册人数
     * @param day
     * @return
     */
    @GetMapping("queryRegisterNum/{day}")
    public RetVal queryRegisterNum(@PathVariable("day") String day){
        Integer registerNum = memberCenterService.queryRegisterNum(day);
        return RetVal.success().data("registerNum",registerNum);
    }

    /**
     * 根据token令牌获取用户信息
     * @param token
     * @return
     */
    @GetMapping("/getUserInfoByToken/{token}")
    public RetVal getUserInfoByToken(@PathVariable("token") String token){
        MemberCenterVo memberCenterVo = new MemberCenterVo();
        Claims claims = JwtUtils.checkJWT(token);
        String nickname = (String)claims.get("nickname");
        String avatar = (String)claims.get("avatar");
        String id = (String)claims.get("id");
        memberCenterVo.setId(id);
        memberCenterVo.setNickname(nickname);
        memberCenterVo.setAvatar(avatar);
        return RetVal.success().data("memberCenterVo",memberCenterVo);
    }


}

