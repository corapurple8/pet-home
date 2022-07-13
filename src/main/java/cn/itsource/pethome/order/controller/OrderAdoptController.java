package cn.itsource.pethome.order.controller;

import cn.itsource.pethome.domain.OrderAdopt;
import cn.itsource.pethome.order.service.IOrderAdoptService;
import cn.itsource.pethome.query.OrderAdoptQuery;
import cn.itsource.pethome.util.AjaxResult;
import cn.itsource.pethome.util.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 领养订单控制类
 */
@RestController
@Api(value = "领养订单处理器",tags = "领养订单处理器")//类上注解生成api文档
public class OrderAdoptController {
    @Autowired
    private IOrderAdoptService orderAdoptService;

    /**
     * 添加领养订单
     * @param orderAdopt
     * @return
     */
    @PutMapping("/orderAdopt")//使用restful风格发送请求
    @ApiOperation(value = "添加领养订单方法",tags = "添加领养订单方法")
    public AjaxResult save(@RequestBody OrderAdopt orderAdopt){
        //前后端分离 非简单请求参数应该用注解@requestBody
        try {
            String formHtml = orderAdoptService.saveOrderAdopt(orderAdopt);
            return AjaxResult.me().setData(formHtml);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("添加失败").setSuccess(false);
        }
    }

    /**
     * 根据id删除领养订单
     * @param id
     * @return
     */
    @ApiOperation(value = "删除领养订单方法",tags = "删除领养订单方法")
    @DeleteMapping("/orderAdopt/{id}")//使用restful风格发送请求
    public AjaxResult delete(@PathVariable("id") Long id){
        //简单参数直接用注解路径参数@pathvariable
        try {
            orderAdoptService.delete(id);
            return AjaxResult.me().setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除失败").setSuccess(false);
        }
    }

    /**
     * 根据id删除领养订单
     * @param list
     * @return
     */
    @ApiOperation(value = "批量删除领养订单方法",tags = "批量删除领养订单方法")
    @DeleteMapping("/orderAdopts/{list}")//使用restful风格发送请求
    public AjaxResult delete(@PathVariable("list") String list){
        //简单参数直接用注解路径参数@pathvariable
        try {
            //方法重载
            orderAdoptService.delete(list);
            return AjaxResult.me().setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除失败").setSuccess(false);
        }
    }

    /**
     * 修改领养订单
     * @param orderAdopt
     * @return
     */
    @PostMapping("/orderAdopt")//使用restful风格发送请求
    @ApiOperation(value = "修改领养订单方法",tags = "修改领养订单方法")
    public AjaxResult update(@RequestBody OrderAdopt orderAdopt){
        try {
            orderAdoptService.update(orderAdopt);
            return AjaxResult.me().setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("修改失败").setSuccess(false);
        }
    }

    /**
     * 根据id查询一个领养订单
     * @param id
     * @return
     */
    @GetMapping("/orderAdopt/{id}")//使用restful风格发送请求
    @ApiOperation(value = "查询一个领养订单方法",tags = "查询一个领养订单方法")
    public AjaxResult findOne(@PathVariable("id") Long id){
        try {
            OrderAdopt orderAdopt = orderAdoptService.findOne(id);
            return AjaxResult.me().setMsg("查询成功").setData(orderAdopt);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }


    /**
     * 高级查询所有领养订单
     * @param orderAdoptQuery
     * @return
     */
    @ApiOperation(value = "查询所有领养订单方法",tags = "查询你所有领养订单方法")
    @GetMapping("/orderAdopts")//使用restful风格发送请求
    public AjaxResult findAll(OrderAdoptQuery orderAdoptQuery){
        try {
            List<OrderAdopt> orderAdoptList = orderAdoptService.findAll(orderAdoptQuery);
            return AjaxResult.me().setMsg("查询成功").setData(orderAdoptList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }

    /**
     * 高级分页查询所有领养订单
     * @param orderAdoptQuery
     * @return
     */
    @PatchMapping("/orderAdopts")//使用restful风格发送请求
    @ApiOperation(value = "分页查询领养订单方法",tags = "分页查询领养订单方法")
    public AjaxResult findPage(@RequestBody OrderAdoptQuery orderAdoptQuery){
        try {
            Page<OrderAdopt> rows = orderAdoptService.findPage(orderAdoptQuery);
            return AjaxResult.me().setMsg("查询成功").setData(rows);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }
}
