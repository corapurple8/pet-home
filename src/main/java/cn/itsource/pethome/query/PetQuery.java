package cn.itsource.pethome.query;

import cn.itsource.pethome.basic.query.BaseQuery;
import lombok.Data;

@Data
public class PetQuery extends BaseQuery {
    private String name;
    private Long shop_id;
    private Integer state;
}
