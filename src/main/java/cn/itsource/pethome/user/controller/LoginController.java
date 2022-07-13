package cn.itsource.pethome.user.controller;

import cn.itsource.pethome.domain.User;
import cn.itsource.pethome.dto.UserDto;
import cn.itsource.pethome.dto.WxUserDto;
import cn.itsource.pethome.user.service.IUserService;
import cn.itsource.pethome.user.service.IWxUserService;
import cn.itsource.pethome.util.AjaxResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录控制器
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired//注入用户业务类
    private IUserService userService;

    @Autowired
    private IWxUserService wxUserService;

    /**
     * 账户登录
     * @param userDto
     * @return
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody UserDto userDto){
        return userService.logByUsernameOrEmailOrPhone(userDto);
    }

    /**
     *
     * @param userDto
     * @return
     */
    @PostMapping("/loginByPhone")
    public AjaxResult loginByPhone(@RequestBody UserDto userDto){
        return userService.logByPhone(userDto);
    }

    /**
     * 检查手机号是否已被注册
     * @param phone
     * @return
     */
    @GetMapping("/checkPhoneNumber/{phone}")//使用restful风格发送请求
    @ApiOperation(value = "根据手机查询用户方法",tags = "根据手机查询用户方法")
    public AjaxResult checkPhoneNumber(@PathVariable("phone") String phone){
        try {
            AjaxResult me = userService.findByPhone(phone);
            return me;
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("网络问题").setSuccess(false);
        }
    }

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    @GetMapping("/sendMobileCode/{phone}")//使用restful风格发送请求
    @ApiOperation(value = "发送手机验证码",tags = "发送手机验证码")
    public AjaxResult sendMobileCode(@PathVariable("phone") String phone){
        try {
            AjaxResult me = userService.sendMobileCode(phone);
            return me;
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("网络问题").setSuccess(false);
        }
    }

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    @GetMapping("/sendPhoneCodeForLog/{phone}")//使用restful风格发送请求
    @ApiOperation(value = "发送手机验证码",tags = "发送手机验证码")
    public AjaxResult sendPhoneCodeForLog(@PathVariable("phone") String phone){
        try {
            AjaxResult me = userService.sendPhoneCodeForLog(phone);
            return me;
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("网络问题").setSuccess(false);
        }
    }


    /**
     * 手机注册用户
     * @param userDto
     * @return
     */
    @PostMapping("/userPhone")//使用restful风格发送请求
    @ApiOperation(value = "手机用户注册方法",tags = "手机用户注册方法")
    public AjaxResult userPhone(@RequestBody UserDto userDto){
        return userService.userPhone(userDto);
    }

    /**
     * 检查邮箱是否已被注册
     * @param email
     * @return
     */
    @GetMapping("/checkEmail/{email}")//使用restful风格发送请求
    @ApiOperation(value = "根据手机查询用户方法",tags = "根据手机查询用户方法")
    public AjaxResult checkEmail(@PathVariable("email") String email){
        try {
            AjaxResult me = userService.findByEmail(email);
            return me;
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("网络问题").setSuccess(false);
        }
    }

    /**
     * 邮箱注册用户
     * @param user
     * @return
     */
    @PostMapping("/userEmail")//使用restful风格发送请求
    @ApiOperation(value = "邮箱用户注册方法",tags = "邮箱用户注册方法")
    public AjaxResult userEmail(@RequestBody User user){

        try {
            userService.userEail(user);
            return AjaxResult.me().setMsg("注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("注册失败");
        }
    }

    /**
     * 用户激活
     * @param id
     * @return
     */
    @GetMapping("/userActivity/{id}")//使用restful风格发送请求
    @ApiOperation(value = "激活用户方法",tags = "激活用户方法")
    public AjaxResult userActivity(@PathVariable("id") Long id){
        try {
            userService.userActivity(id);
            return AjaxResult.me().setMsg("激活成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("激活失败").setSuccess(false);
        }
    }

    /**
     *微信登录方法
     * @param code
     * @return
     */
    @GetMapping("/wechatLogin/{code}")//使用restful风格发送请求
    @ApiOperation(value = "微信登录方法",tags = "微信登录方法")
    public AjaxResult wechatLogin(@PathVariable("code") String code){
        try {
            return wxUserService.wechatLogin(code);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("微信登录失败"+e.getMessage()).setSuccess(false);
        }
    }

    @PostMapping("/wxReg")
    @ApiOperation(value = "微信绑定注册方法",tags = "微信绑定注册方法")
    public AjaxResult wxReg(@RequestBody WxUserDto wxUserDto){
        try {
            return wxUserService.wxReg(wxUserDto);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("微信绑定失败"+e.getMessage()).setSuccess(false);
        }
    }

    /**
     * 检查用户名是否已被注册
     * @param username
     * @return
     */
    @GetMapping("/checkUsername/{username}")//使用restful风格发送请求
    @ApiOperation(value = "根据手机查询用户方法",tags = "根据手机查询用户方法")
    public AjaxResult checkUsername(@PathVariable("username") String username){
        try {
            AjaxResult me = userService.checkUsername(username);
            return me;
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("网络问题"+e.getMessage()).setSuccess(false);
        }
    }

    /**
     * 登出方法
     * @param request
     * @return
     */
    @GetMapping("/logout")//使用restful风格发送请求
    @ApiOperation(value = "用户登出方法",tags = "用户登出方法")
    public AjaxResult logout(HttpServletRequest request){
        try {
            AjaxResult me = userService.logout(request);
            return me;
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("网络问题"+e.getMessage()).setSuccess(false);
        }
    }
}
