package cn.itsource.pethome.pay.mapper;

import cn.itsource.pethome.basic.mapper.BaseMapper;
import cn.itsource.pethome.domain.PayBill;
import cn.itsource.pethome.query.PayBillQuery;

public interface PayBillMapper extends BaseMapper<PayBill, PayBillQuery> {
    /**
     * 根据订单编号查询支付单
     * @param orderSn
     * @return
     */
    PayBill findByOrderSn(String orderSn);
}
