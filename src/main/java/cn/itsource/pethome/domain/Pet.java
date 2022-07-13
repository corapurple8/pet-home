package cn.itsource.pethome.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Pet {
    /**宠物id*/
    private Long id;
    /**寻主信息*/
    private Adopt adopt;
    /**宠物名字*/
    private String name;
    /**图片*/
    private String resources;
    /**售价*/
    private BigDecimal saleprice;
    /**下架时间*/
    private Date offsaletime;
    /**上架时间*/
    private Date onsaletime;
    /**状态*/
    private Integer state;
    /**成本价*/
    private BigDecimal costprice;
    /**生成时间*/
    private Date createtime= new Date();
    /**宠物类型*/
    private PetType petType;
    /**出售商家*/
    private Shop shop;
    /**购买人普通用户*/
    private User user;
    /**宠物详情*/
    private PetDetail petDetail;
}
