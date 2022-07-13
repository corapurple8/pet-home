package cn.itsource.pethome.dto;

import cn.itsource.pethome.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户注册和登录的辅助
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends User {
    /**注册验证码*/
    private String code;

    /**登录辅助前后台类型 type=1 是前台用户user =2是后台管理员employee*/
    private Integer type;
}
