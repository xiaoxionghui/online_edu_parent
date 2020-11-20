package com.atguigu.edu.service;

import com.atguigu.edu.entity.MemberCenter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-13
 */
public interface MemberCenterService extends IService<MemberCenter> {
    /**
     * 查询每天注册人数
     * @param day
     * @return
     */
    Integer queryRegisterNum(String day);

    /**
     * 根据openId查询个人数据
     * @param openId
     * @return
     */
    MemberCenter queryUserByOpenId(String openId);
}
