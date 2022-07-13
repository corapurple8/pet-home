package cn.itsource.pethome.domain;

import lombok.Data;

@Data
public class AlipayInfo {
    /**应用私钥*/
    private String merchant_private_key;
    /**支付宝支付权限的应用id*/
    private String appid;
    /**该信息的id*/
    private Long id;
    /**支付宝支付公钥*/
    private String alipay_public_key;
    /**商铺id*/
    private Long shop_id;
    /**商铺名称*/
    private String shopName;

}
