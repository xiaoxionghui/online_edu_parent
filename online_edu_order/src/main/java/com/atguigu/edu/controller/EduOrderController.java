package com.atguigu.edu.controller;


import com.atguigu.edu.entity.EduOrder;
import com.atguigu.edu.service.EduOrderService;
import com.atguigu.response.RetVal;
import com.atguigu.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-18
 */
@RestController
@RequestMapping("/order")
@CrossOrigin
public class EduOrderController {

    @Autowired
    private EduOrderService orderService;

    /**
     * 生成课程支付订单
     * @param courseId
     * @return
     */
    @GetMapping("/createOrder/{courseId}")
    public RetVal createOrder(@PathVariable("courseId") String courseId, HttpServletRequest request){
        //得到支付用户的用户id
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        //根据用户id和课程id生成订单
        String orderNum = orderService.createOrder(courseId,userId);
        return RetVal.success().data("orderNum",orderNum);

    }

    /**
     * 获取订单详情
     * @param orderNum
     * @return
     */
    @GetMapping("/getOrderInfo/{orderNum}")
    public RetVal getOrderInfo(@PathVariable("orderNum") String orderNum){
        QueryWrapper<EduOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNum);
        EduOrder orderInfo = orderService.getOne(wrapper);
        return RetVal.success().data("orderInfo",orderInfo);
    }

    /**
     * 生成支付二维码
     * @param orderNum
     * @return
     */
    @GetMapping("/getPayQrCode/{orderNum}")
    public RetVal getPayQrCode(@PathVariable("orderNum") String orderNum){
        HashMap<String,Object> map = orderService.getPayQrCode(orderNum);
        return RetVal.success().data(map);
    }

    /**
     * 查询并更新订单状态
     * @param orderNo
     * @return
     */
    @GetMapping("/getPayState/{orderNo}")
    public RetVal getPayState(@PathVariable("orderNo") String orderNo){
        Map<String, String> map = orderService.getPayState(orderNo);
        if (map.get("trade_state").equals("SUCCESS")){
            orderService.updateOrderState(map);
            return RetVal.success().message("payed success");
        }else{
            return RetVal.error().message("payed failed");
        }
    }

}

