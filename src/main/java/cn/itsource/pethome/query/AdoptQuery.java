package cn.itsource.pethome.query;

import cn.itsource.pethome.basic.query.BaseQuery;
import lombok.Data;

@Data
public class AdoptQuery extends BaseQuery {
    /**寻主名称*/
    private String name;
    /**寻主商铺id*/
    private Long shop_id;
    /**状态*/
    private Integer state;
}
