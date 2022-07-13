package cn.itsource.pethome.order.service;

import cn.itsource.pethome.basic.service.IBaseService;
import cn.itsource.pethome.domain.OrderAddress;
import cn.itsource.pethome.query.OrderAddressQuery;

public interface IOrderAddressService extends IBaseService<OrderAddress, OrderAddressQuery> {
    /**
     * 根据订单号查询用户订单地址
     * @param orderSn
     * @return
     */
    OrderAddress orderAddressByOrderSn(String orderSn);
}
