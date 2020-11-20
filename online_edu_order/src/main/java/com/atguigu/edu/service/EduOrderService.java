package com.atguigu.edu.service;

import com.atguigu.edu.entity.EduOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-18
 */
public interface EduOrderService extends IService<EduOrder> {
    /**
     * 生成用户支付订单
     * @param courseId
     * @param userId
     * @return
     */
    String createOrder(String courseId, String userId);

    /**
     * 生成支付二维码
     * @param orderNum
     * @return
     */
    HashMap<String, Object> getPayQrCode(String orderNum);

    /**
     * 查询订单状态
     * @param orderNo
     * @return
     */
    Map<String, String> getPayState(String orderNo);

    /**
     * 更新订单状态
     * @param map
     */
    void updateOrderState(Map<String, String> map);
}
