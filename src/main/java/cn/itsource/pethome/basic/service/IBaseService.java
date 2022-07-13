package cn.itsource.pethome.basic.service;

import cn.itsource.pethome.basic.query.BaseQuery;
import cn.itsource.pethome.util.Page;

import java.util.List;

public interface IBaseService<T,Q extends BaseQuery> {
    /**
     * 增加一个
     * @param t
     */
    void save(T t);

    /**
     * 根据id删除一个
     * @param id
     */
    void delete(Long id);

    /**
     * 修改一个
     * @param t
     */
    void update(T t);

    /**
     * 根据id查询一个
     * @param id
     * @return
     */
    T findOne(Long id);

    /**
     * 查询所有部门不分页 只条件查询
     * @param q
     * @return
     */
    List<T> findAll(Q q);

    /**
     * 查询所有 分页查询和高级查询
     * @param q
     * @return
     */
    Page<T> findPage(Q q);

    /**
     * 批量删除
     * @param list
     */
    void delete(String list);
}
