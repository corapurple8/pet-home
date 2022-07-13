package cn.itsource.pethome.user.controller;

import cn.itsource.pethome.config.CrossAndRest;
import cn.itsource.pethome.domain.User;
import cn.itsource.pethome.dto.UserDto;
import cn.itsource.pethome.query.UserQuery;
import cn.itsource.pethome.user.service.IUserService;
import cn.itsource.pethome.util.AjaxResult;
import cn.itsource.pethome.util.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制类
 */
@RestController
@Api(value = "用户处理器",tags = "用户处理器")//类上注解生成api文档
public class UserController {
    @Autowired
    private IUserService userService;

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PutMapping("/user")//使用restful风格发送请求
    @ApiOperation(value = "添加用户方法",tags = "添加用户方法")
    public AjaxResult save(@RequestBody User user){
        //前后端分离 非简单请求参数应该用注解@requestBody
        try {
            userService.save(user);
            return AjaxResult.me().setMsg("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("添加失败").setSuccess(false);
        }
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @ApiOperation(value = "删除用户方法",tags = "删除用户方法")
    @DeleteMapping("/user/{id}")//使用restful风格发送请求
    public AjaxResult delete(@PathVariable("id") Long id){
        //简单参数直接用注解路径参数@pathvariable
        try {
            userService.delete(id);
            return AjaxResult.me().setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除失败").setSuccess(false);
        }
    }

    /**
     * 根据id删除用户
     * @param list
     * @return
     */
    @ApiOperation(value = "批量删除用户方法",tags = "批量删除用户方法")
    @DeleteMapping("/users/{list}")//使用restful风格发送请求
    public AjaxResult delete(@PathVariable("list") String list){
        //简单参数直接用注解路径参数@pathvariable
        try {
            //方法重载
            userService.delete(list);
            return AjaxResult.me().setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除失败").setSuccess(false);
        }
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @PostMapping("/user")//使用restful风格发送请求
    @ApiOperation(value = "修改用户方法",tags = "修改用户方法")
    public AjaxResult update(@RequestBody User user){
        try {
            userService.update(user);
            return AjaxResult.me().setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("修改失败").setSuccess(false);
        }
    }
    /**
     * 根据id查询一个用户
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")//使用restful风格发送请求
    @ApiOperation(value = "查询一个用户方法",tags = "查询一个用户方法")
    public AjaxResult findOne(@PathVariable("id") Long id){
        try {
            User user = userService.findOne(id);
            return AjaxResult.me().setMsg("查询成功").setData(user);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }


    /**
     * 高级查询所有用户
     * @param userQuery
     * @return
     */
    @ApiOperation(value = "查询所有用户方法",tags = "查询你所有用户方法")
    @GetMapping("/users")//使用restful风格发送请求
    public AjaxResult findAll(UserQuery userQuery){
        try {
            List<User> userList = userService.findAll(userQuery);
            return AjaxResult.me().setMsg("查询成功").setData(userList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }

    /**
     * 高级分页查询所有用户
     * @param userQuery
     * @return
     */
    @PatchMapping("/users")//使用restful风格发送请求
    @ApiOperation(value = "分页查询用户方法",tags = "分页查询用户方法")
    public AjaxResult findPage(@RequestBody UserQuery userQuery){
        try {
            Page<User> rows = userService.findPage(userQuery);
            return AjaxResult.me().setMsg("查询成功").setData(rows);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }


}
