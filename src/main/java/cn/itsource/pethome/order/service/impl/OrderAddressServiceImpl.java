package cn.itsource.pethome.order.service.impl;

import cn.itsource.pethome.basic.service.impl.BaseServiceImpl;
import cn.itsource.pethome.domain.OrderAddress;
import cn.itsource.pethome.order.mapper.OrderAddressMapper;
import cn.itsource.pethome.order.service.IOrderAddressService;
import cn.itsource.pethome.query.OrderAddressQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderAddressServiceImpl extends BaseServiceImpl<OrderAddress, OrderAddressQuery> implements IOrderAddressService {
    @Autowired
    private OrderAddressMapper orderAddressMapper;
    /**
     * 根据订单号查询用户订单地址
     * @param orderSn
     * @return
     */
    @Override
    public OrderAddress orderAddressByOrderSn(String orderSn) {
        return orderAddressMapper.findByOrderSn(orderSn);
    }
}
