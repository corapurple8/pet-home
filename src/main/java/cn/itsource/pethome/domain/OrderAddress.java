package cn.itsource.pethome.domain;

import lombok.Data;

import java.util.Date;

@Data
public class OrderAddress {
    private Long id;
    private Date createTime;
    private Date updateTime;
    private String orderSn;
    private String contacts;
    private String areaCode;
    private String address;
    private String fullAddress;
    private String phone;
    private String phoneBack;
    private String tel;
    private String postCode;
    private String email;
}
