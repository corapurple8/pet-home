package cn.itsource.pethome.org.service;

import cn.itsource.pethome.basic.service.IBaseService;
import cn.itsource.pethome.domain.Employee;
import cn.itsource.pethome.query.EmployeeQuery;

import java.util.List;

public interface IEmployeeService extends IBaseService<Employee,EmployeeQuery> {

    /**
     * 根据manager_id查询员工数组
     * @param job_id
     * @return
     */
    List<Employee> findByJobId(Integer job_id);

}
