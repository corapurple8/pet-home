package cn.itsource.pethome.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class PayBill {
    /**
     * ID
     */
    private Long id;
    /**
     * 摘要
     */
    private String digest;
    /**
     * 支付金额
     */
    private BigDecimal money;
    /**
     * 支付状态
     */
    private Integer state;
    /**
     * 最后支付时间
     */
    private Date lastPayTime;
    /**
     * 支付类型
     */
    private Integer payChannel;
    /**
     * 创建时间
     */
    private Date createTime=new Date();
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 前台用户
     */
    private User user;
    /**
     * 支付编号
     */
    private String unionPaySn;
    /**
     * 订单类型
     */
    private String businessType;
    /**
     * 订单主键
     */
    private Long businessKey;
    /**
     * 商户
     */
    private Shop shop;
    /**
     * 订单编号
     */
    private String orderSn;

}
