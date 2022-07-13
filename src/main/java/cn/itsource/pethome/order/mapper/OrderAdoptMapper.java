package cn.itsource.pethome.order.mapper;

import cn.itsource.pethome.basic.mapper.BaseMapper;
import cn.itsource.pethome.domain.OrderAdopt;
import cn.itsource.pethome.query.OrderAdoptQuery;

public interface OrderAdoptMapper extends BaseMapper<OrderAdopt, OrderAdoptQuery> {
    OrderAdopt findByOrderSn(String orderSn);
}
