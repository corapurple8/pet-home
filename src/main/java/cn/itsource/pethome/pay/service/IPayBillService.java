package cn.itsource.pethome.pay.service;

import cn.itsource.pethome.basic.service.IBaseService;
import cn.itsource.pethome.domain.PayBill;
import cn.itsource.pethome.query.PayBillQuery;

public interface IPayBillService extends IBaseService<PayBill, PayBillQuery> {

    /**
     * 根据订单号查询支付单
     * @param orderSn
     * @return
     */
    PayBill findByOrderSn(String orderSn);
}
