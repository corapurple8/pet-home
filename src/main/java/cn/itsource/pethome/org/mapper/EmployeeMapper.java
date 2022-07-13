package cn.itsource.pethome.org.mapper;

import cn.itsource.pethome.basic.mapper.BaseMapper;
import cn.itsource.pethome.domain.Employee;
import cn.itsource.pethome.query.EmployeeQuery;

import java.util.List;

public interface EmployeeMapper extends BaseMapper<Employee,EmployeeQuery> {

    /**
     * 根据manager_id查询员工数组
     * @param job_id
     * @return
     */
    List<Employee> findByJobId(Integer job_id);

    /**
     * 登录：根据用户名/邮箱/手机号查询后台员工
     * @param username
     * @return
     */
    Employee findByUsernameOrEmailOrPhone(String username);
}
