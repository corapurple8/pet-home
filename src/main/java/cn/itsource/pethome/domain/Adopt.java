package cn.itsource.pethome.domain;

import lombok.Data;

@Data
public class Adopt {
    /**寻主信息id*/
    private Long id;
    /**宠物名*/
    private String name;
    /**售价*/
    private Double price;
    /**年龄*/
    private String age;
    /**公母*/
    private Integer gender;
    /**毛色*/
    private Systemdictionarydetail coatColor;
    /**资源*/
    private String resources;
    /**品种*/
    private PetType petType;
    /**送养人地址*/
    private String address;
    /**状态*/
    private Integer state;
    /**送养标题*/
    private String title;
    /**送养的平台普通用户*/
    private User user;
    /**接单的商家 如果是自己发布就为null*/
    private Shop shop;
}
