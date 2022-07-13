package cn.itsource.pethome.test;

import cn.itsource.pethome.org.service.IDepartmentService;
import cn.itsource.pethome.query.DepartmentQuery;
import cn.itsource.pethome.test.util.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DepartmentServiceImplTest extends BaseTest {
    @Autowired
    private IDepartmentService departmentService;

    @Test
    public void testFindAll() {
        departmentService.findAll(new DepartmentQuery()).forEach(System.out::println);
    }

    @Test
    public void testFindPage() {
        DepartmentQuery departmentQuery = new DepartmentQuery();
        departmentQuery.setPageNo(2);
        departmentQuery.setPageSize(2);
        departmentService.findPage(departmentQuery).getRows().forEach(System.out::println);
    }
}