package cn.itsource.pethome.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class OrderAdopt {
    private Long id;
    private String digest;
    private Integer state;
    private BigDecimal price;
    private String orderSn;
    private String paySn;
    private Date lastPayTime;
    private Date lastConfirmTime;
    private Pet pet;
    private User user;
    private Shop shop;
    private OrderAddress orderAddress;
    private Integer paytype;
}
