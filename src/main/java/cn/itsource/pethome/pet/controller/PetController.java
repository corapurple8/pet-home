package cn.itsource.pethome.pet.controller;

import cn.itsource.pethome.constant.Constant;
import cn.itsource.pethome.domain.Pet;
import cn.itsource.pethome.domain.Employee;
import cn.itsource.pethome.domain.Shop;
import cn.itsource.pethome.pet.service.IPetService;
import cn.itsource.pethome.query.PetQuery;
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
 * 宠物控制类
 */
@RestController
@Api(value = "宠物处理器",tags = "宠物处理器")//类上注解生成api文档
public class PetController {
    @Autowired
    private IPetService petService;

    @Autowired
    private IShopService shopService;

    /**
     * 添加宠物
     * @param pet
     * @return
     */
    @PutMapping("/pet")//使用restful风格发送请求
    @ApiOperation(value = "添加宠物方法",tags = "添加宠物方法")
    public AjaxResult save(@RequestBody Pet pet){
        //前后端分离 非简单请求参数应该用注解@requestBody
        try {
            petService.save(pet);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("添加失败").setSuccess(false);
        }
    }

    /**
     * 根据id删除宠物
     * @param id
     * @return
     */
    @ApiOperation(value = "删除宠物方法",tags = "删除宠物方法")
    @DeleteMapping("/pet/{id}")//使用restful风格发送请求
    public AjaxResult delete(@PathVariable("id") Long id){
        //简单参数直接用注解路径参数@pathvariable
        try {
            petService.delete(id);
            return AjaxResult.me().setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除失败").setSuccess(false);
        }
    }

    /**
     * 根据id删除宠物
     * @param list
     * @return
     */
    @ApiOperation(value = "批量删除宠物方法",tags = "批量删除宠物方法")
    @DeleteMapping("/pets/{list}")//使用restful风格发送请求
    public AjaxResult delete(@PathVariable("list") String list){
        //简单参数直接用注解路径参数@pathvariable
        try {
            //方法重载
            petService.delete(list);
            return AjaxResult.me().setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除失败").setSuccess(false);
        }
    }

    /**
     * 修改宠物
     * @param pet
     * @return
     */
    @PostMapping("/pet")//使用restful风格发送请求
    @ApiOperation(value = "修改宠物方法",tags = "修改宠物方法")
    public AjaxResult update(@RequestBody Pet pet){
        try {
            petService.update(pet);
            return AjaxResult.me().setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("修改失败").setSuccess(false);
        }
    }

    /**
     * 根据id查询一个宠物
     * @param id
     * @return
     */
    @GetMapping("/pet/{id}")//使用restful风格发送请求
    @ApiOperation(value = "查询一个宠物方法",tags = "查询一个宠物方法")
    public AjaxResult findOne(@PathVariable("id") Long id){
        try {
            Pet pet = petService.findOne(id);
            return AjaxResult.me().setMsg("查询成功").setData(pet);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }

    /**
     * 高级查询所有宠物
     * @param petQuery
     * @return
     */
    @ApiOperation(value = "查询所有宠物方法",tags = "查询你所有宠物方法")
    @GetMapping("/pets")//使用restful风格发送请求
    public AjaxResult findAll(PetQuery petQuery){
        try {
            List<Pet> petList = petService.findAll(petQuery);
            return AjaxResult.me().setMsg("查询成功").setData(petList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }

    /**
     * 高级分页查询所有宠物
     * @param petQuery
     * @return
     */
    @PatchMapping("/pets")//使用restful风格发送请求
    @ApiOperation(value = "分页查询宠物方法",tags = "分页查询宠物方法")
    public AjaxResult findPage(@RequestBody PetQuery petQuery,HttpServletRequest request){
        try {
            String userToken = request.getHeader(Constant.USER_TOKEN);
            String type = request.getHeader(Constant.USER_TYPE);
            if ("2".equals(type)){
                String loginUserStr = RedisUtils.INSTANCE.get(userToken);
                //是商家用户自己
                Employee employee = JSON.parseObject(loginUserStr, Employee.class);
                Shop shop = shopService.findByAdminId(employee.getId());

                //将未处理状态和商铺id传入
                petQuery.setShop_id(shop.getId());
                Page<Pet> rows = petService.findPage(petQuery);
                return AjaxResult.me().setMsg("查询成功").setData(rows);
            }else {
                Page<Pet> rows = petService.findPage(petQuery);
                return AjaxResult.me().setMsg("查询成功").setData(rows);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("查询失败").setSuccess(false);
        }
    }
    
    /**
     * 根据id批量上架宠物
     * @param list
     * @return
     */
    @ApiOperation(value = "批量上架宠物方法",tags = "批量上架宠物方法")
    @GetMapping("/onPets/{list}")//使用restful风格发送请求
    public AjaxResult onSale(@PathVariable("list") String list){
        //简单参数直接用注解路径参数@pathvariable
        try {
            //方法重载
            Integer state = Constant.NORMAL;
            String[] strings = list.split(",");
            Long[] ids = new Long[strings.length];
            for (int i = 0; i <strings.length ; i++) {
                ids[i] = Long.valueOf(strings[i]);
            }
            petService.onPets(ids,state);
            return AjaxResult.me().setMsg("上架成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("上架失败").setSuccess(false);
        }
    }
    /**
     * 根据id批量下架宠物
     * @param list
     * @return
     */
    @ApiOperation(value = "批量下架宠物方法",tags = "批量下架宠物方法")
    @GetMapping("/offPets/{list}")//使用restful风格发送请求
    public AjaxResult offSale(@PathVariable("list") String list){
        //简单参数直接用注解路径参数@pathvariable
        try {
            //状态为下架
            Integer state = Constant.DISABLED;
            String[] strings = list.split(",");
            Long[] ids = new Long[strings.length];
            for (int i = 0; i <strings.length ; i++) {
                ids[i] = Long.valueOf(strings[i]);
            }
            petService.offPets(ids,state);
            return AjaxResult.me().setMsg("下架成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("下架失败").setSuccess(false);
        }
    }
}
