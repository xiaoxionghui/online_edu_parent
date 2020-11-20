package com.atguigu.edu.mapper;

import com.atguigu.edu.entity.MemberCenter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-13
 */
public interface MemberCenterMapper extends BaseMapper<MemberCenter> {
    /**
     * 查询每天注册人数
     * @param day
     * @return
     */
    Integer queryRegisterNum(String day);
}
