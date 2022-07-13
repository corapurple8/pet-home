package cn.itsource.pethome.pay.service.impl;

import cn.itsource.pethome.config.AlipayConfig;
import cn.itsource.pethome.domain.AlipayInfo;
import cn.itsource.pethome.domain.OrderAdopt;
import cn.itsource.pethome.pay.mapper.AlipayInfoMapper;
import cn.itsource.pethome.pay.service.IPayService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements IPayService {
    @Autowired
    private AlipayInfoMapper alipayInfoMapper;

    @Override
    public String jumpToPay(OrderAdopt orderAdopt) {
        //先查询出商铺所需的所有alipay信息
        AlipayInfo alipayInfo = alipayInfoMapper.findByShopId(orderAdopt.getShop().getId());
        System.out.println(alipayInfo);
        //以下是支付宝自己的接口 将关键信息替换
        AlipayClient alipayClient =  new DefaultAlipayClient(AlipayConfig.gatewayUrl, alipayInfo.getAppid(), alipayInfo.getMerchant_private_key(), AlipayConfig.fomat, AlipayConfig.charset, alipayInfo.getAlipay_public_key(), AlipayConfig.sign_type);  //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest =  new  AlipayTradePagePayRequest(); //创建API对应的request
        alipayRequest.setReturnUrl( AlipayConfig.return_url );
        alipayRequest.setNotifyUrl( AlipayConfig.notify_url); //在公共参数中设置回跳和通知地址
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = orderAdopt.getOrderSn();
        //付款金额，必填
        String total_amount = orderAdopt.getPrice().toString();
        //订单名称，必填
        String subject = orderAdopt.getDigest();
        //商品描述，可空
        String body = "";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String formHtml = null;
        try {
            formHtml = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return formHtml;
    }
}
