package cn.itsource.pethome.basic.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页查询的基础查询
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseQuery {
    /**当前页 默认为1*/
    private Integer pageNo =1;

    /**每页条数 默认为10*/
    private Integer pageSize =10;

    /**排序字段*/
    private String orderField ="id";

    /**排序顺序 默认降序*/
    private String orderType ="desc";

    /**
     * bean属性的获取 起始下标
     * @return 分页起始下标
     */
    public Integer getBegin(){
        return this.pageSize*(this.pageNo-1);
    }
}
