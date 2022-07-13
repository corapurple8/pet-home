package cn.itsource.pethome.dto;

import cn.itsource.pethome.domain.WxUser;
import lombok.Data;

@Data
public class WxUserDto extends WxUser {
    private String username;
    private String password;
    private String access_token;
    private String province;
    private String city;
    private String country;
    private String errcode;
    private String errmsg;



}
