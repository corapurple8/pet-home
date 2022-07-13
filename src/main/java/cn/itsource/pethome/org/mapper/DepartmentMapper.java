package cn.itsource.pethome.org.mapper;

import cn.itsource.pethome.basic.mapper.BaseMapper;
import cn.itsource.pethome.domain.Department;
import cn.itsource.pethome.query.DepartmentQuery;
import org.apache.ibatis.annotations.Mapper;

/**
 * 部门mapper接口
 */
public interface DepartmentMapper extends BaseMapper<Department,DepartmentQuery> {
    /**
     * 根据名称查询一个部门
     * @param name
     * @return
     */
    Department getDepartmentByName(String name);
}
