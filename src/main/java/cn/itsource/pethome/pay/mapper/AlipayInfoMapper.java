package cn.itsource.pethome.pay.mapper;

import cn.itsource.pethome.basic.mapper.BaseMapper;
import cn.itsource.pethome.domain.AlipayInfo;
import cn.itsource.pethome.query.AlipayInfoQuery;

public interface AlipayInfoMapper extends BaseMapper<AlipayInfo, AlipayInfoQuery> {
    /**
     * 根据商铺查询ali支付信息
     * @param id
     * @return
     */
    AlipayInfo findByShopId(Long id);

    /**
     * 据订单号查询到ali支付的信息
     * @param orderSn
     * @return
     */
    AlipayInfo findByorderSn(String orderSn);
}
