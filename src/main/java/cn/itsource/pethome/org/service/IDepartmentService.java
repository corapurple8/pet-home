package cn.itsource.pethome.org.service;

import cn.itsource.pethome.basic.service.IBaseService;
import cn.itsource.pethome.domain.Department;
import cn.itsource.pethome.query.DepartmentQuery;


/**
 * 部门业务接口
 */
public interface IDepartmentService extends IBaseService<Department,DepartmentQuery> {

    /**
     * 根据名称查询一个部门
     * @param name
     * @return
     */
    Department getDepartmentByName(String name);

}
