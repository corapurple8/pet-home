package cn.itsource.pethome.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Shop {
 private Long id;
 private String name;
 private String tel;
 @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
 private Date registerTime = new Date();
 /**状态默认待审核*/
 //0正常 1待审核 -1禁用 2待激活 3驳回
 private Integer state ;
 private String address;
 private String logo;
 //admin_id
 private Employee admin;
}
