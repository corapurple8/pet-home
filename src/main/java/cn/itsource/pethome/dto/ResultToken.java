package cn.itsource.pethome.dto;

import lombok.Data;

@Data
public class ResultToken {
    //正确返回数据
    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    //失败返回数据
    private String errcode;//40029错误码
    private String errmsg;
    private String unionid;
}
