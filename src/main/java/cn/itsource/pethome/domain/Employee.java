package cn.itsource.pethome.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 后台员工类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
  /**id*/
  private Long id;

  /**名字*/
  private String username;

  /**邮箱*/
  private String email;
  /**电话*/
  private String phone;

  /**密码*/
  private String password;

  /**年龄*/
  private Integer age;

  /**状态*/
  private Integer state;

  /**经理id*/
  private Integer job_id;
}
