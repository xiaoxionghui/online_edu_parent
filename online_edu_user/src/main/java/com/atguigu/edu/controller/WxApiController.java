package com.atguigu.edu.controller;

import com.atguigu.edu.entity.MemberCenter;
import com.atguigu.edu.service.MemberCenterService;
import com.atguigu.response.RetVal;
import com.atguigu.utils.HttpClientUtils;
import com.atguigu.utils.JwtUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @ClassName WxApiController
 * @Description: 微信登录接口
 * @Author xiaoxionghui
 * @Date 2020/11/18 8:56
 * @Version V1.0
 **/

@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {

    @Value("${wx.open.app_id}")
    private String WX_OPEN_APPID;
    @Value("${wx.open.app_secret}")
    private String WX_OPEN_APPSECRET;
    @Value("${wx.open.redirect_url}")
    private String WX_OPEN_REDIRECT_URL;

    @Autowired
    private MemberCenterService memberCenterService;

    /**
     * 生成登录二维码
     * @return
     * @throws IOException
     */
    @GetMapping("login")
    public String qrCode() throws IOException {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        String state = "atguigu";
        //对回调路径进行编码
        String encodeUrl = URLEncoder.encode(WX_OPEN_REDIRECT_URL, "utf-8");
        //拼接字符串
        String qrCodeUrl = String.format(baseUrl, WX_OPEN_APPID, encodeUrl, state);
        return "redirect:"+qrCodeUrl;
    }

//    @GetMapping("callback")
//    public String callBack(String code,String state) throws Exception {
//        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
//                "?appid=%s" +
//                "&secret=%s" +
//                "&code=%s" +
//                "&grant_type=authorization_code";
//        baseAccessTokenUrl = String.format(baseAccessTokenUrl,WX_OPEN_APPID,WX_OPEN_APPSECRET,code);
//        String retVal = HttpClientUtils.get(baseAccessTokenUrl);
//        //把json字符串转换为Java对象
//        Gson gson = new Gson();
//        HashMap map = gson.fromJson(retVal, HashMap.class);
//        String accessToken = (String)map.get("access_token");
//        String openId = (String)map.get("openid");
//        //b.根据access_token和openid获取用户信息
//        String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
//                "?access_token=%s" +
//                "&openid=%s";
//        String format = String.format(userInfoUrl, accessToken, openId);
//        userInfoUrl = HttpClientUtils.get(format);
//        HashMap hashMap = gson.fromJson(userInfoUrl, HashMap.class);
//        String nickname = (String)hashMap.get("nickname");
//        String headimgurl = (String)hashMap.get("headimgurl");
//
//        //c.拿到用户的个人信息 需要存储起来 存之前先判断该用户是否已经在数据库中有了
//        MemberCenter existMember=memberCenterService.queryUserByOpenId(openId);
//        if (existMember==null){
//            MemberCenter memberCenter = new MemberCenter();
//            memberCenter.setOpenid(openId);
//            memberCenter.setNickname(nickname);
//            memberCenter.setAvatar(headimgurl);
//            memberCenterService.save(memberCenter);
//        }
//        //d.返回一个token给前端
//        String token = JwtUtils.geneJsonWebToken(existMember);
//        return "redirect:http://127.0.0.1:3000?token="+ token;
//    }

    /**
     * 微信登录回调
     * @param code
     * @param state
     * @return
     * @throws Exception
     */
    @GetMapping("callback")
    public String callback(String code,String state) throws Exception{
        //a.通过code+appid+appsecret得到用户的access_token和openid
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        //拼接字符串
        baseAccessTokenUrl = String.format(baseAccessTokenUrl, WX_OPEN_APPID, WX_OPEN_APPSECRET, code);
        String retVal = HttpClientUtils.get(baseAccessTokenUrl);
        //把json字符串转换为Java对象
        Gson gson = new Gson();
        HashMap infoMap = gson.fromJson(retVal, HashMap.class);
        String accessToken =(String) infoMap.get("access_token");
        String openId =(String) infoMap.get("openid");
        //b.根据access_token和openid获取用户信息
        String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=%s" +
                "&openid=%s";
        //拼接字符串
        userInfoUrl = String.format(userInfoUrl, accessToken, openId);
        String userInfoJson = HttpClientUtils.get(userInfoUrl);
        HashMap userInfoMap = gson.fromJson(userInfoJson, HashMap.class);
        String nickName =(String) userInfoMap.get("nickname");
        String headImgUrl =(String) userInfoMap.get("headimgurl");
        //c.拿到用户的个人信息 需要存储起来 存之前先判断该用户是否已经在数据库中有了
        MemberCenter existMember=memberCenterService.queryUserByOpenId(openId);
        if(existMember==null){
            existMember = new MemberCenter();
            existMember.setOpenid(openId);
            existMember.setNickname(nickName);
            existMember.setAvatar(headImgUrl);
            memberCenterService.save(existMember);
        }
        //d.返回一个token给前端
        String token = JwtUtils.geneJsonWebToken(existMember);
        return "redirect:http://127.0.0.1:3000?token="+token;
    }

}
