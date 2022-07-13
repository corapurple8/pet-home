package cn.itsource.pethome.order.mapper;

import cn.itsource.pethome.basic.mapper.BaseMapper;
import cn.itsource.pethome.domain.OrderAddress;
import cn.itsource.pethome.query.OrderAddressQuery;

public interface OrderAddressMapper extends BaseMapper<OrderAddress, OrderAddressQuery> {
    /**
     * 根据订单号查询用户订单地址
     * @param orderSn
     * @return
     */
    OrderAddress findByOrderSn(String orderSn);
}
