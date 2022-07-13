package cn.itsource.pethome.test;

import cn.itsource.pethome.domain.Department;
import cn.itsource.pethome.org.mapper.DepartmentMapper;
import cn.itsource.pethome.query.DepartmentQuery;
import cn.itsource.pethome.test.util.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class DepartmentMapperTest extends BaseTest {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Test
    public void testSave() {
        Department department = new Department();
        department.setName("test");
        department.setSn("004");
        department.setState(1);
        departmentMapper.save(department);
    }


    @Test
    public void testDelete() {
        departmentMapper.delete(10L);
        departmentMapper.delete(11L);
        departmentMapper.delete(12L);
    }

    @Test
    public void testFindOne() {

        System.out.println(departmentMapper.findOne(3L));
    }

    @Test
    public void testFindAll() {
        departmentMapper.findAll(new DepartmentQuery()).forEach(System.out::println);
    }
}