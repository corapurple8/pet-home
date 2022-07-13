package cn.itsource.pethome.order.service.impl;

import cn.itsource.pethome.basic.service.impl.BaseServiceImpl;
import cn.itsource.pethome.constant.Constant;
import cn.itsource.pethome.constant.PayConstants;
import cn.itsource.pethome.domain.*;
import cn.itsource.pethome.order.mapper.OrderAddressMapper;
import cn.itsource.pethome.order.mapper.OrderAdoptMapper;
import cn.itsource.pethome.order.service.IOrderAdoptService;
import cn.itsource.pethome.pay.mapper.PayBillMapper;
import cn.itsource.pethome.pay.service.IPayService;
import cn.itsource.pethome.pet.mapper.PetMapper;
import cn.itsource.pethome.quartz.domain.QuartzJobInfo;
import cn.itsource.pethome.quartz.service.IQuartzService;
import cn.itsource.pethome.query.OrderAdoptQuery;
import cn.itsource.pethome.user.mapper.UserMapper;
import cn.itsource.pethome.util.CodeGenerateUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;

@Service
public class OrderAdoptServiceImpl extends BaseServiceImpl<OrderAdopt, OrderAdoptQuery> implements IOrderAdoptService {
    @Autowired
    private OrderAdoptMapper orderAdoptMapper;

    @Autowired
    private PetMapper petMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderAddressMapper orderAddressMapper;

    @Autowired
    private IPayService payService;

    @Autowired
    private PayBillMapper payBillMapper;

    @Autowired
    private IQuartzService quartzService;

    @Transactional
    public String saveOrderAdopt(OrderAdopt orderAdopt) {
        //先将订单中宠物，用户和订单地址分离
        Pet pet = petMapper.findOne(orderAdopt.getPet().getId());
        User user = userMapper.findOne(orderAdopt.getUser().getId());
        //宠物下架
        pet.setState(-1);
        //领主改为现在的user 清空商铺
        pet.setUser(user);
        pet.setShop(null);


        //订单地址
        OrderAddress orderAddress = orderAdopt.getOrderAddress();

        //生成随机订单号
        String orderSn = CodeGenerateUtils.generateOrderSn(user.getId());
        orderAdopt.setOrderSn(orderSn);
        orderAddress.setOrderSn(orderSn);
        //写进数据库得到主键
        orderAddressMapper.save(orderAddress);

        //设置过期时间
        //支付过期 60s
        Date lastPayTime = DateUtils.addSeconds(new Date(),60);
        //订单确认过期 7天确认收货
        Date lastConfirmTime =DateUtils.addDays(new Date(),7) ;
        orderAdopt.setLastPayTime(lastPayTime);
        orderAdopt.setLastConfirmTime(lastConfirmTime);
        //设置订单简述
        orderAdopt.setDigest("宠物领养:"+user.getUsername()+"领养了"+pet.getName());
        //将主键写回
        orderAdopt.setOrderAddress(orderAddress);
        orderAdopt.setState(1);
        //写进数据库
        orderAdoptMapper.save(orderAdopt);
        //创建一个支付订单
        PayBill payBill = this.createPayBill(orderAdopt);
        //存入数据库
        payBillMapper.save(payBill);
        //调用支付接口，返回一个html字符串，可以用来跳转到支付页面
        String formHtml = payService.jumpToPay(orderAdopt);
        System.out.println(formHtml);
        //设置定时任务以确认订单是否支付，若是在60s内未支付要取消订单
        QuartzJobInfo quartzJobInfo = this.createQuartzJobInfo(orderAdopt,payBill.getBusinessType());
        quartzService.addJob(quartzJobInfo);

        //支付成功后将宠物重新写进数据库 本来应该写在二次验签之后
        petMapper.update(pet);

        return formHtml;
    }

    private QuartzJobInfo createQuartzJobInfo(OrderAdopt orderAdopt, String businessType) {
        QuartzJobInfo quartzJobInfo = new QuartzJobInfo();
        quartzJobInfo.setFireDate(orderAdopt.getLastPayTime());//启动时间
        quartzJobInfo.setJobName(businessType+orderAdopt.getOrderSn());//订单类型+订单号
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderSn",orderAdopt.getOrderSn());//订单号
        map.put("businessType",businessType);//订单号
        quartzJobInfo.setParams(map);
        return quartzJobInfo;
    }

    /**
     * 创建一个支付订单
     * @param orderAdopt
     * @return
     */
    private PayBill createPayBill(OrderAdopt orderAdopt) {
        PayBill payBill = new PayBill();
        payBill.setDigest(orderAdopt.getDigest());
        payBill.setMoney(orderAdopt.getPrice());
        //待支付
        payBill.setState(Constant.AUDIT);
        payBill.setLastPayTime(orderAdopt.getLastPayTime());
        payBill.setPayChannel(orderAdopt.getPaytype());
        payBill.setUser(orderAdopt.getUser());
        payBill.setBusinessType(PayConstants.BUSINESSTYPE_ADOPT);
        payBill.setBusinessKey(orderAdopt.getId());
        payBill.setShop(orderAdopt.getShop());
        payBill.setOrderSn(orderAdopt.getOrderSn());
        return payBill;
    }

    /**
     * 更改订单状态
     * @param businessKey
     * @param trade_no
     */
    @Override
    public void updateState(Long businessKey, String trade_no) {
        OrderAdopt orderAdopt = orderAdoptMapper.findOne(businessKey);
        orderAdopt.setState(Constant.DISABLED);//已支付
        orderAdopt.setPaySn(trade_no);
        orderAdopt.setLastPayTime(new Date());
        //写进数据库
        orderAdoptMapper.update(orderAdopt);
    }
}
