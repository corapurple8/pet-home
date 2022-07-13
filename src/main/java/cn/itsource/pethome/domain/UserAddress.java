package cn.itsource.pethome.domain;

import lombok.Data;

import java.util.Date;
@Data
public class UserAddress {
    private Long id;
    private Date createTime= new Date();
    private Date updateTime;
    private String contacts;
    private String areaCode;
    private String address;
    private User user;
    private String fullAddress;
    private String phone;
    private String phoneBack;
    private String tel;
    private String postCode;
    private String email;
}
