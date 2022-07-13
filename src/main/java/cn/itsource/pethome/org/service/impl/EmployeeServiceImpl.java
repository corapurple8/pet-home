package cn.itsource.pethome.org.service.impl;

import cn.itsource.pethome.basic.service.impl.BaseServiceImpl;
import cn.itsource.pethome.domain.Employee;
import cn.itsource.pethome.org.mapper.EmployeeMapper;
import cn.itsource.pethome.query.EmployeeQuery;
import cn.itsource.pethome.org.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service//员工业务
public class EmployeeServiceImpl extends BaseServiceImpl<Employee, EmployeeQuery> implements IEmployeeService {

    @Autowired//注入员工mapper
    private EmployeeMapper employeeMapper;

    @Override
    public List<Employee> findByJobId(Integer job_id) {
        return employeeMapper.findByJobId(job_id);
    }
}
