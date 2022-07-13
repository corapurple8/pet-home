package cn.itsource.pethome.query;

import cn.itsource.pethome.basic.query.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部门高级查询
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentQuery extends BaseQuery {
    private String name;
}
