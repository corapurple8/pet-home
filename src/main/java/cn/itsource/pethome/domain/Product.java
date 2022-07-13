package cn.itsource.pethome.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/***
 * 商品类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    /**主键*/
    private Long id;
    /**商品名*/
    private String name;
    /**图片资源 多张以逗号隔开*/
    private String resources;
    /**售价*/
    private BigDecimal saleprice;
    /**下架时间*/
    private Date offsaletime;
    /**上架时间*/
    private Date onsaletime = new Date();
    /**状态*/
    private Integer state;
    /**折扣价*/
    private BigDecimal costprice;
    /**生成时间*/
    private Date createtime = new Date();
    /**销量*/
    private Long salecount;
}
