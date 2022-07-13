package cn.itsource.pethome.user.service;

import cn.itsource.pethome.basic.service.IBaseService;
import cn.itsource.pethome.domain.User;
import cn.itsource.pethome.dto.UserDto;
import cn.itsource.pethome.query.UserQuery;
import cn.itsource.pethome.util.AjaxResult;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


public interface IUserService extends IBaseService<User, UserQuery> {

    /**
     * 根据手机号查找某个用户
     * @param phone
     * @return
     */
    AjaxResult findByPhone(String phone);

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    AjaxResult sendMobileCode(String phone);

    /**
     * 手机号注册方法
     * @param userDto
     * @return
     */
    AjaxResult userPhone(UserDto userDto);

    /**
     * 根据邮箱号查找用户
     * @param email
     * @return
     */
    AjaxResult findByEmail(String email);

    /**
     * 邮箱用户注册
     * @param user
     */
    void userEail(User user);

    /**
     * 激活邮箱用户
     * @param id
     */
    void userActivity(Long id);

    /**
     * 登录辅助
     * 根据用户名/邮箱/电话查找用户或管理员
     * @param userDto
     * @return
     */
    AjaxResult logByUsernameOrEmailOrPhone(UserDto userDto);

    /**
     * 通过手机验证码登录
     * @param userDto
     * @return
     */
    AjaxResult logByPhone(UserDto userDto);

    /**
     * 发送手机登录验证码
     * @param phone
     * @return
     */
    AjaxResult sendPhoneCodeForLog(String phone);

    /**
     * 检查用户名是否被注册
     * @param username
     * @return
     */
    AjaxResult checkUsername(String username);

    /**
     * 用户登出方法
     * @param request
     * @return
     */
    AjaxResult logout(HttpServletRequest request);
}
