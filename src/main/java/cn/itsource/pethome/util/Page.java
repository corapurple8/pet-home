package cn.itsource.pethome.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页查询的数据返回封装类
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {
    /**当前页 默认为1*/
    private Integer pageNo;

    /**每页条数 默认为10*/
    private Integer pageSize;

    /**查询到的数据数组*/
    private List<T> rows;

    /**总数*/
    private Long total;
}
