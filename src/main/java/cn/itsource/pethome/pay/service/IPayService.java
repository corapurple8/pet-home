package cn.itsource.pethome.pay.service;

import cn.itsource.pethome.domain.OrderAdopt;

public interface IPayService {
    String jumpToPay(OrderAdopt orderAdopt);
}
