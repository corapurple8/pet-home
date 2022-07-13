package cn.itsource.pethome.org.controller;

import cn.itsource.pethome.config.CrossAndRest;
import cn.itsource.pethome.domain.Department;
import cn.itsource.pethome.query.DepartmentQuery;
import cn.itsource.pethome.org.service.IDepartmentService;
import cn.itsource.pethome.util.AjaxResult;
import cn.itsource.pethome.util.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门控制类
 */
@RestController
@Api(value = "部门处理器",tags = "部门处理器")//类上注解生成api文档
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;

    /**
     * 添加部门
     * @param department
     * @return
     */
    @PutMapping("/department")//使用restful风格发送请求
    @ApiOperation(value = "添加部门方法",tags = "添加部门方法")
    public AjaxResult save(@RequestBody Department department){
        //前后端分离 非简单请求参数应该用注解@requestBody
        try {
            departmentService.save(department);
            return AjaxResult.me().setMsg("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("添加失败").setSuccess(false);
        }
    }

    /**
     * 根据id删除部门
     * @param id
     * @return
     */
    @ApiOperation(value = "删除部门方法",tags = "删除部门方法")
    @DeleteMapping("/department/{id}")//使用restful风格发送请求
    public AjaxResult delete(@PathVariable("id") Long id){
        //简单参数直接用注解路径参数@pathvariable
        try {
            departmentService.delete(id);
            return AjaxResult.me().setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除失败").setSuccess(false);
        }
    }

    /**
     * 根据id删除部门
     * @param list
     * @return
     */
    @ApiOperation(value = "批量删除部门方法",tags = "批量删除部门方法")
    @DeleteMapping("/departments/{list}")//使用restful风格发送请求
    public AjaxResult delete(@PathVariable("list") String list){
        //简单参数直接用注解路径参数@pathvariable
        try {
            //方法重载
            departmentService.delete(list);
            return AjaxResult.me().setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除失败").setSuccess(false);
        }
    }

    /**
     * 修改部门
     * @param department
     * @return
     */
    @PostMapping("/department")//使用restful风格发送请求
    @ApiOperation(value = "修改部门方法",tags = "修改部门方法")
    public AjaxResult update(@RequestBody Department department){
        try {
            departmentService.update(department);
            return AjaxResult.me().setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("修改失败").setSuccess(false);
        }
    }

    /**
     * 根据id查询一个部门
     * @param id
     * @return
     */
    @GetMapping("/department/{id}")//使用restful风格发送请求
    @ApiOperation(value = "查询一个部门方法",tags = "查询一个部门方法")
    public AjaxResult findOne(@PathVariable("id") Long id){
        try {
            Department department = departmentService.findOne(id);
            return AjaxResult.me().setMsg("查询成功").setData(department);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }

    /**
     * 根据name查询一个部门
     * @param name
     * @return
     */
    @GetMapping("/getDepartmentByName/{name}")//使用restful风格发送请求
    @ApiOperation(value = "根据名称查询一个部门方法",tags = "根据名称查询一个部门方法")
    public AjaxResult getDepartmentByName(@PathVariable("name") String name){
        try {
            Department department = departmentService.getDepartmentByName(name);
            return AjaxResult.me().setMsg("查询成功").setData(department);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }

    /**
     * 高级查询所有部门
     * @param departmentQuery
     * @return
     */
    @ApiOperation(value = "查询所有部门方法",tags = "查询你所有部门方法")
    @GetMapping("/departments")//使用restful风格发送请求
    public AjaxResult findAll(DepartmentQuery departmentQuery){
        try {
            List<Department> departmentList = departmentService.findAll(departmentQuery);
            return AjaxResult.me().setMsg("查询成功").setData(departmentList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }

    /**
     * 高级分页查询所有部门
     * @param departmentQuery
     * @return
     */
    @PatchMapping("/departments")//使用restful风格发送请求
    @ApiOperation(value = "分页查询部门方法",tags = "分页查询部门方法")
    public AjaxResult findPage(@RequestBody DepartmentQuery departmentQuery){
        try {
            Page<Department> rows = departmentService.findPage(departmentQuery);
            return AjaxResult.me().setMsg("查询成功").setData(rows);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }
}
