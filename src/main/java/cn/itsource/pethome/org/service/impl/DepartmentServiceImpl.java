package cn.itsource.pethome.org.service.impl;
import cn.itsource.pethome.basic.service.impl.BaseServiceImpl;
import cn.itsource.pethome.domain.Department;
import cn.itsource.pethome.org.mapper.DepartmentMapper;
import cn.itsource.pethome.org.service.IDepartmentService;
import cn.itsource.pethome.query.DepartmentQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 部门业务实现类
 */
@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department,DepartmentQuery> implements IDepartmentService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired//注入部门mapper
    private DepartmentMapper departmentMapper;

    /**
     * 根据名称查询一个部门
     * @param name
     * @return
     */
    @Override
    public Department getDepartmentByName(String name) {
        return departmentMapper.getDepartmentByName(name);
    }
}
