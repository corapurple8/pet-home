package cn.itsource.pethome.pay.service.impl;

import cn.itsource.pethome.basic.service.impl.BaseServiceImpl;
import cn.itsource.pethome.domain.AlipayInfo;
import cn.itsource.pethome.pay.mapper.AlipayInfoMapper;
import cn.itsource.pethome.pay.service.IAliPayInfoService;
import cn.itsource.pethome.query.AlipayInfoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AliPayInfoServiceImpl extends BaseServiceImpl<AlipayInfo, AlipayInfoQuery> implements IAliPayInfoService {

    @Autowired//注入ali支付mapper
    private AlipayInfoMapper alipayInfoMapper;

    /**
     * 据订单号查询到ali支付的信息
     * @param orderSn
     * @return
     */
    @Override
    public AlipayInfo findByorderSn(String orderSn) {
        return alipayInfoMapper.findByorderSn(orderSn);
    }
}
