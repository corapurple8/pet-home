package cn.itsource.pethome.order.controller;

import cn.itsource.pethome.domain.OrderAddress;
import cn.itsource.pethome.order.service.IOrderAddressService;
import cn.itsource.pethome.util.AjaxResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderAddressController {

    @Autowired
    private IOrderAddressService orderAddressService;
    /**
     * 根据id查询一个领养订单
     * @param orderSn
     * @return
     */
    @GetMapping("/orderAddressByOrderSn/{orderSn}")//使用restful风格发送请求
    @ApiOperation(value = "查询一个领养订单方法",tags = "查询一个领养订单方法")
    public AjaxResult findOne(@PathVariable("orderSn") String orderSn){
        try {
            OrderAddress orderAddress = orderAddressService.orderAddressByOrderSn(orderSn);
            return AjaxResult.me().setMsg("查询成功").setData(orderAddress);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }
}
