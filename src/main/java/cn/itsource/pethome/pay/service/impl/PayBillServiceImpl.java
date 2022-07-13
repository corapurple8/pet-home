package cn.itsource.pethome.pay.service.impl;

import cn.itsource.pethome.basic.service.impl.BaseServiceImpl;
import cn.itsource.pethome.domain.PayBill;
import cn.itsource.pethome.pay.mapper.PayBillMapper;
import cn.itsource.pethome.pay.service.IPayBillService;
import cn.itsource.pethome.query.PayBillQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayBillServiceImpl extends BaseServiceImpl<PayBill, PayBillQuery> implements IPayBillService {
    //注入支付单mapper
    @Autowired
    private PayBillMapper payBillMapper;

    /**
     * 根据订单号查询支付单
     * @param orderSn
     * @return
     */
    @Override
    public PayBill findByOrderSn(String orderSn) {
        return payBillMapper.findByOrderSn(orderSn);
    }
}
