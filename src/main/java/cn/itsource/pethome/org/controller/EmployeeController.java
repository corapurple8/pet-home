package cn.itsource.pethome.org.controller;

import cn.itsource.pethome.config.CrossAndRest;
import cn.itsource.pethome.domain.Employee;
import cn.itsource.pethome.query.EmployeeQuery;
import cn.itsource.pethome.org.service.IEmployeeService;
import cn.itsource.pethome.util.AjaxResult;
import cn.itsource.pethome.util.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工控制器
 */
@RestController
@Api(value = "员工管理",tags = "员工管理")
public class EmployeeController {
    @Autowired//注入员工业务
    private IEmployeeService employeeService;

    /**
     * 添加一个员工
     * @param employee
     * @return
     */
    @PutMapping("/employee")//除了get和delete 直接用请求json体
    public AjaxResult save(@RequestBody Employee employee){
        try {
            employeeService.save(employee);
            return AjaxResult.me().setMsg("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("添加失败").setSuccess(false);
        }
    }

    /**
     * 根据id删除一个员工
     * @param id
     * @return
     */
    @DeleteMapping("/employee/{id}")//delete 用路径参数
    public AjaxResult delete(@PathVariable("id") Long id){
        try {
            employeeService.delete(id);
            return AjaxResult.me().setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除失败").setSuccess(false);
        }
    }

    /**
     * 批量删除员工
     * @param ids
     * @return
     */
    @DeleteMapping("/employees/{ids}")//delete 用路径参数
    public AjaxResult delete(@PathVariable("ids") String ids){
        try {
            employeeService.delete(ids);
            return AjaxResult.me().setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除失败").setSuccess(false);
        }
    }

    /**
     * 修改一个员工
     * @param employee
     * @return
     */
    @PostMapping("/employee")//除了get和delete 直接用请求json体
    public AjaxResult update(@RequestBody Employee employee){
        try {
            employeeService.update(employee);
            return AjaxResult.me().setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("修改失败").setSuccess(false);
        }
    }

    /**
     * 根据id查询一个员工
     * @param id
     * @return
     */
    @GetMapping("/employee/{id}")//get 用路径参数
    public AjaxResult findOne(@PathVariable("id") Long id){
        try {
            Employee employee = employeeService.findOne(id);
            return AjaxResult.me().setMsg("查询成功").setData(employee);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }

    /**
     * 根据job_id查询员工数组
     * @param job_id
     * @return
     */
    @GetMapping("/getEmployeeByJobId/{job_id}")//get 用路径参数
    public AjaxResult getEmployeeByJobId(@PathVariable("job_id") Integer job_id){
        try {
            List<Employee> employees = employeeService.findByJobId(job_id);
            return AjaxResult.me().setMsg("查询成功").setData(employees);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }

    /**
     * 高级查询所有员工
     * @param employeeQuery
     * @return
     */
    @GetMapping("/employees/{employeeQuery}")//get 用路径参数
    public AjaxResult findAll(@PathVariable("employeeQuery") EmployeeQuery employeeQuery){
        try {
            List<Employee> employees = employeeService.findAll(employeeQuery);
            return AjaxResult.me().setMsg("查询成功").setData(employees);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }

    /**
     * 分页查询所有员工
     * @param employeeQuery
     * @return
     */
    @PatchMapping("/employees")//除了get和delete 直接用请求json体
    public AjaxResult findPage(@RequestBody EmployeeQuery employeeQuery){
        try {
            Page<Employee> page = employeeService.findPage(employeeQuery);
            return AjaxResult.me().setMsg("查询成功").setData(page);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }


}
