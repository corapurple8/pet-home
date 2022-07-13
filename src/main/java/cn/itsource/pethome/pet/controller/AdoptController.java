package cn.itsource.pethome.pet.controller;

import cn.itsource.pethome.constant.Constant;
import cn.itsource.pethome.domain.Adopt;
import cn.itsource.pethome.domain.Employee;
import cn.itsource.pethome.domain.Shop;
import cn.itsource.pethome.pet.service.IAdoptService;
import cn.itsource.pethome.query.AdoptQuery;
import cn.itsource.pethome.user.service.IShopService;
import cn.itsource.pethome.util.AjaxResult;
import cn.itsource.pethome.util.Page;
import cn.itsource.pethome.util.RedisUtils;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 送养信息控制类
 */
@RestController
@Api(value = "送养信息处理器",tags = "送养信息处理器")//类上注解生成api文档
public class AdoptController {
    @Autowired
    private IAdoptService adoptService;

    @Autowired
    private IShopService shopService;

    /**
     * 添加送养信息
     * @param adopt
     * @return
     */
    @PutMapping("/adopt")//使用restful风格发送请求
    @ApiOperation(value = "添加送养信息方法",tags = "添加送养信息方法")
    public AjaxResult save(@RequestBody Adopt adopt, HttpServletRequest request){
        //前后端分离 非简单请求参数应该用注解@requestBody
        try {
            return adoptService.save(adopt,request);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("添加失败").setSuccess(false);
        }
    }

    /**
     * 根据id删除送养信息
     * @param id
     * @return
     */
    @ApiOperation(value = "删除送养信息方法",tags = "删除送养信息方法")
    @DeleteMapping("/adopt/{id}")//使用restful风格发送请求
    public AjaxResult delete(@PathVariable("id") Long id){
        //简单参数直接用注解路径参数@pathvariable
        try {
            adoptService.delete(id);
            return AjaxResult.me().setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除失败").setSuccess(false);
        }
    }

    /**
     * 根据id删除送养信息
     * @param list
     * @return
     */
    @ApiOperation(value = "批量删除送养信息方法",tags = "批量删除送养信息方法")
    @DeleteMapping("/adopts/{list}")//使用restful风格发送请求
    public AjaxResult delete(@PathVariable("list") String list){
        //简单参数直接用注解路径参数@pathvariable
        try {
            //方法重载
            adoptService.delete(list);
            return AjaxResult.me().setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除失败").setSuccess(false);
        }
    }

    /**
     * 修改送养信息
     * @param adopt
     * @return
     */
    @PostMapping("/adopt")//使用restful风格发送请求
    @ApiOperation(value = "修改送养信息方法",tags = "修改送养信息方法")
    public AjaxResult update(@RequestBody Adopt adopt){
        try {
            adoptService.update(adopt);
            return AjaxResult.me().setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("修改失败").setSuccess(false);
        }
    }

    /**
     * 根据id查询一个送养信息
     * @param id
     * @return
     */
    @GetMapping("/adopt/{id}")//使用restful风格发送请求
    @ApiOperation(value = "查询一个送养信息方法",tags = "查询一个送养信息方法")
    public AjaxResult findOne(@PathVariable("id") Long id){
        try {
            Adopt adopt = adoptService.findOne(id);
            return AjaxResult.me().setMsg("查询成功").setData(adopt);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }

    /**
     * 高级查询所有送养信息
     * @param adoptQuery
     * @return
     */
    @ApiOperation(value = "查询所有送养信息方法",tags = "查询你所有送养信息方法")
    @GetMapping("/adopts")//使用restful风格发送请求
    public AjaxResult findAll(AdoptQuery adoptQuery){
        try {
            List<Adopt> adoptList = adoptService.findAll(adoptQuery);
            return AjaxResult.me().setMsg("查询成功").setData(adoptList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }

    /**
     * 高级分页查询所有送养信息
     * @param adoptQuery
     * @return
     */
    @PatchMapping("/adopts")//使用restful风格发送请求
    @ApiOperation(value = "分页查询送养信息方法",tags = "分页查询送养信息方法")
    public AjaxResult findPage(@RequestBody AdoptQuery adoptQuery,HttpServletRequest request){
        try {
            String userToken = request.getHeader(Constant.USER_TOKEN);
            String type = request.getHeader(Constant.USER_TYPE);
            String loginUserStr = RedisUtils.INSTANCE.get(userToken);
            //是商家用户自己
            Employee employee = JSON.parseObject(loginUserStr, Employee.class);
            Shop shop = shopService.findByAdminId(employee.getId());

            //将未处理状态和商铺id传入
            adoptQuery.setShop_id(shop.getId());
            Page<Adopt> rows = adoptService.findPage(adoptQuery);
            return AjaxResult.me().setMsg("查询成功").setData(rows);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }
}
