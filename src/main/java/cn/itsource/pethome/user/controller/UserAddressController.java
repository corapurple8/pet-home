package cn.itsource.pethome.user.controller;

import cn.itsource.pethome.domain.UserAddress;
import cn.itsource.pethome.query.UserAddressQuery;
import cn.itsource.pethome.user.service.IUserAddressService;
import cn.itsource.pethome.util.AjaxResult;
import cn.itsource.pethome.util.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户地址控制类
 */
@RestController
@Api(value = "用户地址处理器",tags = "用户地址处理器")//类上注解生成api文档
public class UserAddressController {
    @Autowired
    private IUserAddressService userAddressService;

    /**
     * 添加用户地址
     * @param userAddress
     * @return
     */
    @PutMapping("/userAddress")//使用restful风格发送请求
    @ApiOperation(value = "添加用户地址方法",tags = "添加用户地址方法")
    public AjaxResult save(@RequestBody UserAddress userAddress){
        //前后端分离 非简单请求参数应该用注解@requestBody
        try {
            userAddressService.save(userAddress);
            return AjaxResult.me().setMsg("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("添加失败").setSuccess(false);
        }
    }

    /**
     * 根据id删除用户地址
     * @param id
     * @return
     */
    @ApiOperation(value = "删除用户地址方法",tags = "删除用户地址方法")
    @DeleteMapping("/userAddress/{id}")//使用restful风格发送请求
    public AjaxResult delete(@PathVariable("id") Long id){
        //简单参数直接用注解路径参数@pathvariable
        try {
            userAddressService.delete(id);
            return AjaxResult.me().setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除失败").setSuccess(false);
        }
    }

    /**
     * 根据id删除用户地址
     * @param list
     * @return
     */
    @ApiOperation(value = "批量删除用户地址方法",tags = "批量删除用户地址方法")
    @DeleteMapping("/userAddresss/{list}")//使用restful风格发送请求
    public AjaxResult delete(@PathVariable("list") String list){
        //简单参数直接用注解路径参数@pathvariable
        try {
            //方法重载
            userAddressService.delete(list);
            return AjaxResult.me().setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除失败").setSuccess(false);
        }
    }

    /**
     * 修改用户地址
     * @param userAddress
     * @return
     */
    @PostMapping("/userAddress")//使用restful风格发送请求
    @ApiOperation(value = "修改用户地址方法",tags = "修改用户地址方法")
    public AjaxResult update(@RequestBody UserAddress userAddress){
        try {
            userAddressService.update(userAddress);
            return AjaxResult.me().setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("修改失败").setSuccess(false);
        }
    }

    /**
     * 根据id查询一个用户地址
     * @param id
     * @return
     */
    @GetMapping("/userAddress/{id}")//使用restful风格发送请求
    @ApiOperation(value = "查询一个用户地址方法",tags = "查询一个用户地址方法")
    public AjaxResult findOne(@PathVariable("id") Long id){
        try {
            UserAddress userAddress = userAddressService.findOne(id);
            return AjaxResult.me().setMsg("查询成功").setData(userAddress);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }

    /**
     * 高级查询所有用户地址
     * @param id
     * @return
     */
    @ApiOperation(value = "查询所有用户地址方法",tags = "查询你所有用户地址方法")
    @GetMapping("/userAddresses/{id}")//使用restful风格发送请求
    public AjaxResult findAll(@PathVariable("id") Long id){
        try {
            List<UserAddress> userAddressList = userAddressService.findAll(id);
            return AjaxResult.me().setMsg("查询成功").setData(userAddressList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }

    /**
     * 高级分页查询所有用户地址
     * @param userAddressQuery
     * @return
     */
    @PatchMapping("/userAddresses")//使用restful风格发送请求
    @ApiOperation(value = "分页查询用户地址方法",tags = "分页查询用户地址方法")
    public AjaxResult findPage(@RequestBody UserAddressQuery userAddressQuery){
        try {
            Page<UserAddress> rows = userAddressService.findPage(userAddressQuery);
            return AjaxResult.me().setMsg("查询成功").setData(rows);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }
}
