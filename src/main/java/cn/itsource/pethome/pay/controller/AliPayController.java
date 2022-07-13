package cn.itsource.pethome.pay.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import cn.itsource.pethome.config.AlipayConfig;
import cn.itsource.pethome.constant.Constant;
import cn.itsource.pethome.constant.PayConstants;
import cn.itsource.pethome.domain.AlipayInfo;
import cn.itsource.pethome.domain.PayBill;
import cn.itsource.pethome.order.service.IOrderAdoptService;
import cn.itsource.pethome.pay.service.IAliPayInfoService;
import cn.itsource.pethome.pay.service.IPayBillService;
import cn.itsource.pethome.quartz.service.IQuartzService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.synth.SynthSpinnerUI;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 二次验签控制类
 */
@RestController
@RequestMapping("/alipay")
public class AliPayController {
    @Autowired//成功后修改支付单状态
    private IPayBillService payBillService;
    @Autowired//成功后修改订单状态
    private IOrderAdoptService orderAdoptService;
    @Autowired
    private IAliPayInfoService aliPayInfoService;
    @Autowired
    private IQuartzService quartzService;

    @RequestMapping("/notify")//异步验证支付宝跳转接口
    public String notify(HttpServletRequest request) {
        System.out.println("进来了这个接口");
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用 现在不需要解决 springboot已经帮我们解决了
            params.put(name, valueStr);
        }
        String orderSn = request.getParameter("out_trade_no");
        //根据订单号查询商铺再查询到阿里支付信息
        AlipayInfo alipayInfo = aliPayInfoService.findByorderSn(orderSn);
        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayInfo.getAlipay_public_key(), AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
            System.out.println(signVerified);

            //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/

            if (signVerified) {//验证成功
                System.out.println("验证成功");
                // 商户订单号
                String out_trade_no = orderSn;
                //支付宝交易号
                String trade_no = request.getParameter("trade_no");
                System.out.println(trade_no);
                // 交易状态
                String trade_status = request.getParameter("trade_status");
                System.out.println(trade_status);
                if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
                    //执行该订单的后续业务流程：比如支付成功后要修改订单状态
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序
                    //验证成功后做处理

                    PayBill payBill = payBillService.findByOrderSn(orderSn);
                    payBill.setState(Constant.DISABLED);//成功支付
                    payBill.setUnionPaySn(trade_no);
                    payBill.setUpdateTime(new Date());
                    //做状态修改
                    payBillService.update(payBill);
                    //根据支付单查询到订单做判断
                    String businessType = payBill.getBusinessType();
                    if (PayConstants.BUSINESSTYPE_ADOPT.equals(businessType)){
                        //修改订单状态
                        orderAdoptService.updateState(payBill.getBusinessKey(),trade_no);
                    }else {
                        //其他订单修改
                    }
                    //取消订单支付定时器任务
                    quartzService.removeJob(businessType+orderSn);
                }
                return "success";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "failure";
    }
}