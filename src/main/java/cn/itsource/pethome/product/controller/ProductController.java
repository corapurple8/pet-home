package cn.itsource.pethome.product.controller;

import cn.itsource.pethome.domain.Product;
import cn.itsource.pethome.domain.ProductDetail;
import cn.itsource.pethome.dto.ProductDto;
import cn.itsource.pethome.product.service.IProductService;
import cn.itsource.pethome.query.ProductQuery;
import cn.itsource.pethome.util.AjaxResult;
import cn.itsource.pethome.util.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品控制类
 */
@RestController
@Api(value = "商品处理器",tags = "商品处理器")//类上注解生成api文档
public class ProductController {
    @Autowired
    private IProductService productService;

    /**
     * 添加商品
     * @param productDto
     * @return
     */
    @PutMapping("/product")//使用restful风格发送请求
    @ApiOperation(value = "添加商品方法",tags = "添加商品方法")
    public AjaxResult save(@RequestBody ProductDto productDto){
        //前后端分离 非简单请求参数应该用注解@requestBody
        try {
            productService.save(productDto);
            return AjaxResult.me().setMsg("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("添加失败").setSuccess(false);
        }
    }

    /**
     * 根据id删除商品
     * @param id
     * @return
     */
    @ApiOperation(value = "删除商品方法",tags = "删除商品方法")
    @DeleteMapping("/product/{id}")//使用restful风格发送请求
    public AjaxResult delete(@PathVariable("id") Long id){
        //简单参数直接用注解路径参数@pathvariable
        try {
            productService.delete(id);
            return AjaxResult.me().setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除失败").setSuccess(false);
        }
    }

    /**
     * 根据id删除商品
     * @param list
     * @return
     */
    @ApiOperation(value = "批量删除商品方法",tags = "批量删除商品方法")
    @DeleteMapping("/products/{list}")//使用restful风格发送请求
    public AjaxResult delete(@PathVariable("list") String list){
        //简单参数直接用注解路径参数@pathvariable
        try {
            //方法重载
            productService.delete(list);
            return AjaxResult.me().setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除失败").setSuccess(false);
        }
    }
    /**
     * 根据id批量上架商品
     * @param list
     * @return
     */
    @ApiOperation(value = "批量上架商品方法",tags = "批量上架商品方法")
    @GetMapping("/onProducts/{list}")//使用restful风格发送请求
    public AjaxResult onSale(@PathVariable("list") String list){
        //简单参数直接用注解路径参数@pathvariable
        try {
            //方法重载
            Integer state = 0;
            String[] strings = list.split(",");
            Long[] ids = new Long[strings.length];
            for (int i = 0; i <strings.length ; i++) {
                ids[i] = Long.valueOf(strings[i]);
            }
            productService.changeSale(ids,state);
            return AjaxResult.me().setMsg("上架成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("上架失败").setSuccess(false);
        }
    }
    /**
     * 根据id批量下架商品
     * @param list
     * @return
     */
    @ApiOperation(value = "批量下架商品方法",tags = "批量下架商品方法")
    @GetMapping("/offProducts/{list}")//使用restful风格发送请求
    public AjaxResult offSale(@PathVariable("list") String list){
        //简单参数直接用注解路径参数@pathvariable
        try {
            //状态为下架
            Integer state = -1;
            String[] strings = list.split(",");
            Long[] ids = new Long[strings.length];
            for (int i = 0; i <strings.length ; i++) {
                ids[i] = Long.valueOf(strings[i]);
            }
            productService.changeSale(ids,state);
            return AjaxResult.me().setMsg("下架成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("下架失败").setSuccess(false);
        }
    }

    /**
     * 修改商品
     * @param productDto
     * @return
     */
    @PostMapping("/product")//使用restful风格发送请求
    @ApiOperation(value = "修改商品方法",tags = "修改商品方法")
    public AjaxResult update(@RequestBody ProductDto productDto){
        try {
            productService.update(productDto);
            return AjaxResult.me().setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("修改失败").setSuccess(false);
        }
    }

    /**
     * 根据id查询一个商品
     * @param id
     * @return
     */
    @GetMapping("/product/{id}")//使用restful风格发送请求
    @ApiOperation(value = "查询一个商品方法",tags = "查询一个商品方法")
    public AjaxResult findOne(@PathVariable("id") Long id){
        try {
            ProductDetail productDetail = productService.findProductDetail(id);
            return AjaxResult.me().setMsg("查询成功").setData(productDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }

    /**
     * 高级查询所有商品
     * @param productQuery
     * @return
     */
    @ApiOperation(value = "查询所有商品方法",tags = "查询你所有商品方法")
    @GetMapping("/products")//使用restful风格发送请求
    public AjaxResult findAll(ProductQuery productQuery){
        try {
            List<Product> productList = productService.findAll(productQuery);
            return AjaxResult.me().setMsg("查询成功").setData(productList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }

    /**
     * 高级分页查询所有商品
     * @param productQuery
     * @return
     */
    @PatchMapping("/products")//使用restful风格发送请求
    @ApiOperation(value = "分页查询商品方法",tags = "分页查询商品方法")
    public AjaxResult findPage(@RequestBody ProductQuery productQuery){
        try {
            Page<Product> rows = productService.findPage(productQuery);
            return AjaxResult.me().setMsg("查询成功").setData(rows);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }
}
