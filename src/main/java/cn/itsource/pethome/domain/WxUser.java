package cn.itsource.pethome.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxUser {
    /*微信平台查询到的
    openid":"OPENID",
            "nickname":"NICKNAME",
            "sex":1,
            "province":"PROVINCE",
            "city":"CITY",
            "country":"COUNTRY",
            "headimgurl": "https://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
            "privilege":[
            "PRIVILEGE1",
            "PRIVILEGE2"
            ],
            "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"*/
    private Long id;
    private String openid;
    private String nickname;
    private String sex;
    private String address;
    private String headimgurl;
    private String unionid;
    private Long user_id;
}
