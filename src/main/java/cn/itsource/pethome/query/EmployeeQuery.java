package cn.itsource.pethome.query;

import cn.itsource.pethome.basic.query.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeQuery extends BaseQuery {
    private String username;
}
