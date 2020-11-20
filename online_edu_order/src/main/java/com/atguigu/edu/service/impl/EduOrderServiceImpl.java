package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduOrder;
import com.atguigu.edu.entity.EduPayLog;
import com.atguigu.edu.mapper.EduOrderMapper;
import com.atguigu.edu.service.EduOrderService;
import com.atguigu.edu.service.EduPayLogService;
import com.atguigu.utils.HttpClient;
import com.atguigu.utils.OrderNoUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-18
 */
@Service
public class EduOrderServiceImpl extends ServiceImpl<EduOrderMapper, EduOrder> implements EduOrderService {
    @Value("${wx.pay.app_id}")
    private String WX_PAY_APP_ID;
    @Value("${wx.pay.mch_id}")
    private String WX_PAY_MCH_ID;
    @Value("${wx.pay.spbill_create_ip}")
    private String WX_PAY_SPBILL_IP;
    @Value("${wx.pay.notify_url}")
    private String WX_PAY_NOTIFY_URL;
    @Value("${wx.pay.xml_key}")
    private String WX_PAY_XML_KEY;


    @Autowired
    private EduPayLogService payLogService;
    /**
     * 生成用户支付订单号
     * @param courseId
     * @param userId
     * @return
     */
    @Override
    public String createOrder(String courseId, String userId) {
        //b.生成订单号
        String orderNo = OrderNoUtil.getOrderNo();
        //c.通过RPC的形式调用service微服务获取课程相关的信息 courseId
        //d.通过RPC的形式调用user微服务获取会员相关的信息    userId
        //e.把订单信息保存到数据库中
        EduOrder order = new EduOrder();
        order.setCourseId(courseId);
        order.setOrderNo(orderNo);
        order.setCourseTitle("AYMELON 春秋长袖连衣裙女2020秋季新款时尚气质长款过膝收腰显瘦打底长裙 兰色 M");
        order.setCourseCover("http://img13.360buyimg.com/imgzone/jfs/t1/123480/22/11802/297354/5f533c4fE82e2a847/4ff1f0a9a6d0119b.jpg");
        order.setTeacherName("苍老师");
        order.setMemberId(userId);
        order.setNickName("隔壁老王");
        order.setMobile("110");
        order.setTotalFee(new BigDecimal(0.01));
        order.setPayType(1);
        order.setStatus(0);
        baseMapper.insert(order);
        return orderNo;
    }

    /**
     * 生成支付二维码
     * @param orderNum
     * @return
     */
    @Override
    public HashMap<String, Object> getPayQrCode(String orderNum) {
        QueryWrapper<EduOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNum);
        EduOrder eduOrder = baseMapper.selectOne(wrapper);
        //把参数封装成一个map接口 然后在进行转换为xml
        HashMap<String, String> requestParam = new HashMap<>();
        //公众账号ID
        requestParam.put("appid",WX_PAY_APP_ID);
        //商户号
        requestParam.put("mch_id",WX_PAY_MCH_ID);
        //随机字符串
        requestParam.put("nonce_str", WXPayUtil.generateNonceStr());
        //商品描述
        requestParam.put("body",eduOrder.getCourseTitle());
        //商户订单号
        requestParam.put("out_trade_no",orderNum);
        //标价金额 价格转换为分
        String totalFee = eduOrder.getTotalFee().multiply(new BigDecimal(100)).longValue()+"";
        requestParam.put("total_fee",totalFee);
        //终端IP 错误
        requestParam.put("spbill_create_ip",WX_PAY_SPBILL_IP);
        //通知地址
        requestParam.put("notify_url",WX_PAY_NOTIFY_URL);
        //交易类型
        requestParam.put("trade_type","NATIVE");
        HashMap<String, Object> retMap = null;
        try {
            String signedXml = WXPayUtil.generateSignedXml(requestParam, WX_PAY_XML_KEY);
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //对请求接口添加参数
            client.setXmlParam(signedXml);
            client.setHttps(true);
            client.post();
            //请求之后微信那边给的响应信息
            String content = client.getContent();
            Map<String, String> xmlToMap = WXPayUtil.xmlToMap(content);
            retMap = new HashMap<>();
            retMap.put("codeUrl",xmlToMap.get("code_url"));
            retMap.put("orderNum",orderNum);
            retMap.put("totalFee",eduOrder.getTotalFee());
            retMap.put("courseId",eduOrder.getCourseId());
            System.out.println(eduOrder.getCourseId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retMap;
    }

    /**
     * 查询订单状态
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, String> getPayState(String orderNo) {
        //把参数封装成一个map接口 然后在进行转换为xml
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("appid",WX_PAY_APP_ID);
        requestMap.put("mch_id",WX_PAY_MCH_ID);
        requestMap.put("out_trade_no",orderNo);
        requestMap.put("nonce_str",WXPayUtil.generateNonceStr());
        Map<String, String> xmlToMap = null;
        try {
            //转换成xml
            String signedXml = WXPayUtil.generateSignedXml(requestMap, WX_PAY_XML_KEY);
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            //对请求接口添加参数
            client.setXmlParam(signedXml);
            client.setHttps(true);
            client.post();
            //请求之后微信那边给的响应信息
            String content = client.getContent();
            xmlToMap = WXPayUtil.xmlToMap(content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xmlToMap;
    }

    /**
     * 更新订单状态
     * @param map
     */
    @Override
    public void updateOrderState(Map<String, String> map) {
        //更新状态信息
        QueryWrapper<EduOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",map.get("out_trade_no"));
        EduOrder order = baseMapper.selectOne(wrapper);
        order.setStatus(1);
        baseMapper.updateById(order);
        //插入更新日志
        QueryWrapper<EduPayLog> payLogQueryWrapper = new QueryWrapper<>();
        payLogQueryWrapper.eq("order_no",map.get("out_trade_no"));
        EduPayLog payLog = payLogService.getOne(payLogQueryWrapper);
        if (payLog==null){
            EduPayLog eduPayLog = new EduPayLog();
            eduPayLog.setOrderNo(order.getOrderNo());
            eduPayLog.setTotalFee(order.getTotalFee());
            eduPayLog.setPayTime(new Date());
            eduPayLog.setTradeState(map.get("trade_state"));
            eduPayLog.setPayType(1);
            eduPayLog.setTransactionId(map.get("transaction_id"));
            payLogService.save(eduPayLog);
        }

    }
}
