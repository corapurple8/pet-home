package cn.itsource.pethome.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 前台用户
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String salt;
    private String password;
    private Integer state;
    private Integer age;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createtime = new Date();
    private String headImg;
}
