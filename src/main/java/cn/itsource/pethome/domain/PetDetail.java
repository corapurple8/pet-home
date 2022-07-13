package cn.itsource.pethome.domain;

import lombok.Data;

@Data
public class PetDetail {
    /**宠物详情id*/
    private Long id;
    /**宠物id*/
    private Long pet_id;
    /**宠物领养须知*/
    private String adoptNotice;
    /**宠物介绍*/
    private String intro;

}
