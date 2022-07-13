package cn.itsource.pethome.domain;

import lombok.Data;

@Data
public class PetType {
    private Long id;
    private String name;
    private String description;
    private Long parentid;
}
