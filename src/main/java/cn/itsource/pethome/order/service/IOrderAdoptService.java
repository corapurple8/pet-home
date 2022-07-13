package cn.itsource.pethome.order.service;

import cn.itsource.pethome.basic.service.IBaseService;
import cn.itsource.pethome.domain.OrderAdopt;
import cn.itsource.pethome.query.OrderAdoptQuery;

public interface IOrderAdoptService extends IBaseService<OrderAdopt, OrderAdoptQuery> {

    /**
     * 更改订单状态
     * @param businessKey
     * @param trade_no
     */
    void updateState(Long businessKey, String trade_no);

    /**
     * 添加一个领养订单
     * @param orderAdopt
     */
    String saveOrderAdopt(OrderAdopt orderAdopt);
}
