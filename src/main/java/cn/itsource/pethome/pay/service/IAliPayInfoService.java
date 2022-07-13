package cn.itsource.pethome.pay.service;

import cn.itsource.pethome.basic.service.IBaseService;
import cn.itsource.pethome.domain.AlipayInfo;
import cn.itsource.pethome.query.AlipayInfoQuery;

public interface IAliPayInfoService extends IBaseService<AlipayInfo, AlipayInfoQuery> {
    /**
     * 根据订单号查询到ali支付的信息
     * @param orderSn
     * @return
     */
    AlipayInfo findByorderSn(String orderSn);
}
