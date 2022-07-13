package cn.itsource.pethome.query;

import cn.itsource.pethome.basic.query.BaseQuery;
import lombok.Data;

@Data
public class OrderAdoptQuery extends BaseQuery {
    /**状态*/
    private Integer state;
}
