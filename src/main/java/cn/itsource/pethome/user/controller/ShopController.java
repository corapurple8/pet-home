package cn.itsource.pethome.user.controller;

import cn.itsource.pethome.config.CrossAndRest;
import cn.itsource.pethome.domain.Shop;
import cn.itsource.pethome.user.service.IShopService;
import cn.itsource.pethome.query.ShopQuery;
import cn.itsource.pethome.util.AjaxResult;
import cn.itsource.pethome.util.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商铺控制类
 */
@RestController
@Api(value = "商铺处理器",tags = "商铺处理器")//类上注解生成api文档
public class ShopController {
    @Autowired
    private IShopService shopService;

    /**
     * 添加商铺
     * @param shop
     * @return
     */
    @PutMapping("/shop")//使用restful风格发送请求
    @ApiOperation(value = "添加商铺方法",tags = "添加商铺方法")
    public AjaxResult save(@RequestBody Shop shop){
        //前后端分离 非简单请求参数应该用注解@requestBody
        try {
            shopService.save(shop);
            return AjaxResult.me().setMsg("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("添加失败").setSuccess(false);
        }
    }

    /**
     * 根据id删除商铺
     * @param id
     * @return
     */
    @ApiOperation(value = "删除商铺方法",tags = "删除商铺方法")
    @DeleteMapping("/shop/{id}")//使用restful风格发送请求
    public AjaxResult delete(@PathVariable("id") Long id){
        //简单参数直接用注解路径参数@pathvariable
        try {
            shopService.delete(id);
            return AjaxResult.me().setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除失败").setSuccess(false);
        }
    }

    /**
     * 根据id删除商铺
     * @param list
     * @return
     */
    @ApiOperation(value = "批量删除商铺方法",tags = "批量删除商铺方法")
    @DeleteMapping("/shops/{list}")//使用restful风格发送请求
    public AjaxResult delete(@PathVariable("list") String list){
        //简单参数直接用注解路径参数@pathvariable
        try {
            //方法重载
            shopService.delete(list);
            return AjaxResult.me().setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除失败").setSuccess(false);
        }
    }

    /**
     * 修改商铺
     * @param shop
     * @return
     */
    @PostMapping("/shop")//使用restful风格发送请求
    @ApiOperation(value = "修改商铺方法",tags = "修改商铺方法")
    public AjaxResult update(@RequestBody Shop shop){
        try {
            shopService.update(shop);
            return AjaxResult.me().setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("修改失败").setSuccess(false);
        }
    }

    /**
     * 根据id查询一个商铺
     * @param id
     * @return
     */
    @GetMapping("/shop/{id}")//使用restful风格发送请求
    @ApiOperation(value = "查询一个商铺方法",tags = "查询一个商铺方法")
    public AjaxResult findOne(@PathVariable("id") Long id){
        try {
            Shop shop = shopService.findOne(id);
            return AjaxResult.me().setMsg("查询成功").setData(shop);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }


    /**
     * 高级查询所有商铺
     * @param shopQuery
     * @return
     */
    @ApiOperation(value = "查询所有商铺方法",tags = "查询你所有商铺方法")
    @GetMapping("/shops")//使用restful风格发送请求
    public AjaxResult findAll(ShopQuery shopQuery){
        try {
            List<Shop> shopList = shopService.findAll(shopQuery);
            return AjaxResult.me().setMsg("查询成功").setData(shopList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }

    /**
     * 高级分页查询所有商铺
     * @param shopQuery
     * @return
     */
    @PatchMapping("/shops")//使用restful风格发送请求
    @ApiOperation(value = "分页查询商铺方法",tags = "分页查询商铺方法")
    public AjaxResult findPage(@RequestBody ShopQuery shopQuery){
        try {
            Page<Shop> rows = shopService.findPage(shopQuery);
            return AjaxResult.me().setMsg("查询成功").setData(rows);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }

    /**
     * 入驻一个商铺
     * @param shop
     * @return
     */
    @PostMapping("/shop/settledIn")//使用restful风格发送请求
    @ApiOperation(value = "入驻商铺方法",tags = "入驻商铺方法")
    public AjaxResult settledIn(@RequestBody Shop shop){
        try {
            shopService.settledIn(shop);
            return AjaxResult.me().setMsg("入驻成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("入驻失败").setSuccess(false);
        }
    }

    /**
     * 审核商铺
     * @param id
     * @return
     */
    @GetMapping("/shopAudit/{id}")//使用restful风格发送请求
    @ApiOperation(value = "审核商铺方法",tags = "审核商铺方法")
    public AjaxResult shopAudit(@PathVariable("id") Long id){
        try {
            shopService.shopAudit(id);
            return AjaxResult.me().setMsg("审核成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("审核失败").setSuccess(false);
        }
    }

    /**
     * 商铺激活
     * @param id
     * @return
     */
    @GetMapping("/shopActivity/{id}")//使用restful风格发送请求
    @ApiOperation(value = "审核商铺方法",tags = "审核商铺方法")
    public AjaxResult shopActivity(@PathVariable("id") Long id){
        try {
            shopService.shopActivity(id);
            return AjaxResult.me().setMsg("审核成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("审核失败").setSuccess(false);
        }
    }


}
