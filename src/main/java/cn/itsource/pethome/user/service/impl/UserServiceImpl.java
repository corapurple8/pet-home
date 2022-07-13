package cn.itsource.pethome.user.service.impl;

import cn.itsource.pethome.basic.service.impl.BaseServiceImpl;
import cn.itsource.pethome.constant.Constant;
import cn.itsource.pethome.domain.Employee;
import cn.itsource.pethome.domain.User;
import cn.itsource.pethome.dto.UserDto;
import cn.itsource.pethome.org.mapper.EmployeeMapper;
import cn.itsource.pethome.query.UserQuery;
import cn.itsource.pethome.user.mapper.UserMapper;
import cn.itsource.pethome.user.service.IUserService;
import cn.itsource.pethome.util.*;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;

@Service
public class UserServiceImpl extends BaseServiceImpl<User,UserQuery> implements IUserService {
    @Autowired//注入用户mapper
    private UserMapper userMapper;
    @Autowired//注入邮件工具类
    private MailUtils mailUtils;
    @Autowired//注入员工mapper
    private EmployeeMapper employeeMapper;


    /**
     * 根据手机号查找用户
     * @param phone
     * @return
     */
    @Override
    public AjaxResult findByPhone(String phone) {
        User user = userMapper.findByPhone(phone);
        if (user!=null){//若是用户存在
            return AjaxResult.me().setMsg("手机号已被注册").setSuccess(false);
        }else {
            return AjaxResult.me().setSuccess(true);
        }
    }

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    @Override
    public AjaxResult sendMobileCode(String phone) {
        //生成四位验证码
        String code = StrUtils.getRandomString(4);
        //先查看数据库以前是否有key
        String key = Constant.PHONEREG_CODE_KEY +phone;
        String value = null;
        //查询
        String oldValue = RedisUtils.INSTANCE.get(key);
        //调用工具类同时判断非空和非null
        if (StringUtils.isNotBlank(oldValue)){
            System.out.println(1);
            //以前有该键
            //分离出时间戳
            Long oldTime = Long.valueOf(oldValue.split(":")[1]);
            Long newTime  = System.currentTimeMillis();
            //差值
            long time = newTime - oldTime;
            if (time<1000*60){
                //一分钟内重复获取
                return AjaxResult.me().setSuccess(false).setMsg("请勿频繁获取");
            }else if (time<Constant.CODE_EXPIRE_TIME*1000){
                //未过期 直接发送上次的code
                code = oldValue.split(":")[0];
            }
        }
        //加上现在时间戳
        value = code +":"+System.currentTimeMillis();
        //设置进redis
        RedisUtils.INSTANCE.set(key,value,Constant.CODE_EXPIRE_TIME);
        System.out.println(value);
        //发送短信
        //根据短信通平台 注册 获得api接口 添加jar包 封装工具类再进行调用
        try {
            SMSUtil.sendCode(phone,code);
            return AjaxResult.me().setMsg("获取成功，请在五分钟内验证");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.me().setSuccess(false).setMsg("网络故障");
    }

    /**
     * 手机用户注册
     * @param userDto
     * @return
     */
    @Override
    @Transactional
    public AjaxResult userPhone(UserDto userDto) {
        try {
            String code = userDto.getCode();
            String phone = userDto.getPhone();
            String key = Constant.PHONEREG_CODE_KEY +phone;
            //在数据库中获取
            String value = RedisUtils.INSTANCE.get(key);
            if (value==null){
                return AjaxResult.me().setSuccess(false).setMsg("验证码已过期");
            }
            //得到数据库中的验证码
            String codeRedis = value.split(":")[0];
            //判断验证码
            if (!code.equals(codeRedis)){
                return AjaxResult.me().setSuccess(false).setMsg("验证码错误");
            }
            //验证成功添加进数据库
            User user = userDto;
            //直接设置启用正常
            user.setState(Constant.NORMAL);
            //将用户名设置为手机号
            user.setUsername(phone);
            //密码加密的盐值
            String salt = StrUtils.getComplexRandomString(32);
            //密码md5加密
            String password = MD5Utils.encrypByMd5(user.getPassword() + salt);
            user.setSalt(salt);
            user.setPassword(password);
            userMapper.save(user);
            return AjaxResult.me().setMsg("注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMsg("网络故障");
        }
    }

    /**
     * 邮箱用户注册查找
     * @param email
     * @return
     */
    @Override
    public AjaxResult findByEmail(String email) {
        User user = userMapper.findByEmail(email);
        if (user!=null){//若是用户存在
            return AjaxResult.me().setMsg("邮箱已被注册").setSuccess(false);
        }else {
            return AjaxResult.me().setSuccess(true);
        }
    }

    /**
     * 邮箱用户注册
     * @param user
     */
    @Override
    @Transactional
    public void userEail(User user) {
        user.setUsername(user.getEmail());
        //设置为待激活
        user.setState(Constant.ACTIVITY);
        userMapper.save(user);
        //发送邮件给用户 用户必须返回主键 在mapper.xml中添加
        mailUtils.sendActivityMailForUser(user.getEmail(),user.getId());
    }

    /**
     * 商铺激活
     * @param id
     */
    @Override
    @Transactional
    public void userActivity(Long id) {
        //查询商家
        User user = userMapper.findOne(id);
        //管理员和商家设置为正常
        user.setState(Constant.NORMAL);
        userMapper.update(user);
    }

    /**
     * 用户登录方法
     * @param userDto
     * @return
     */
    @Override
    public AjaxResult logByUsernameOrEmailOrPhone(UserDto userDto) {
            try {
                //判断是前后台用户
                if (userDto.getType()==1){//前台用户
                    //先查询用户
                    User user = userMapper.findByUsernameOrEmailOrPhone(userDto.getUsername());
                    //判断用户是否存在
                    if (user!=null){
                        //用户存在则对比密码
                        //String password = MD5Utils.encrypByMd5(userDto.getPassword() + user.getSalt());
                        String password = userDto.getPassword();
                        if (user.getPassword().equals(password)){
                            //密码正确
                            //生成令牌
                            String userToken = UUID.randomUUID().toString();
                            //将用户保存在redis中(后端) 转化为字符串
                            String loginUserJsonStr = JSON.toJSONString(user);
                            //保存在redis中并设置过期时间30分钟
                            RedisUtils.INSTANCE.set(userToken,loginUserJsonStr,Constant.USER_EXPIRE_TIME);
                            //封装成map返回给前端
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("userToken",userToken);
                            map.put("loginUser",loginUserJsonStr);
                            return AjaxResult.me().setMsg("登录成功").setData(map);
                        }
                        return AjaxResult.me().setSuccess(false).setMsg("密码错误");
                    }
                    return AjaxResult.me().setSuccess(false).setMsg("用户名不存在");
                }else {//后台用户
                    //先查询用户
                    Employee employee = employeeMapper.findByUsernameOrEmailOrPhone(userDto.getUsername());
                    //判断用户是否存在
                    if (employee!=null){
                        //用户存在则对比密码
                        //String password = MD5Utils.encrypByMd5(userDto.getPassword());
                        if (employee.getPassword().equals(userDto.getPassword())){
                            //密码正确
                            //生成令牌
                            String userToken = UUID.randomUUID().toString();
                            //将用户保存在redis中(后端) 转化为字符串
                            String loginUserJsonStr = JSON.toJSONString(employee);
                            //保存在redis中并设置过期时间30分钟
                            RedisUtils.INSTANCE.set(userToken,loginUserJsonStr,Constant.USER_EXPIRE_TIME);
                            //封装成map返回给前端
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("userToken",userToken);
                            map.put("loginUser",loginUserJsonStr);
                            return AjaxResult.me().setMsg("登录成功").setData(map);
                        }
                        return AjaxResult.me().setSuccess(false).setMsg("密码错误");
                    }
                    return AjaxResult.me().setSuccess(false).setMsg("用户名不存在");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return AjaxResult.me().setSuccess(false).setMsg("网络故障");
            }
    }

    /**
     * 由手机验证码登录
     * @param userDto
     * @return
     */
    @Override
    public AjaxResult logByPhone(UserDto userDto) {
        if (userDto.getType()==1) {//前台用户
            //先查询用户
            User user = userMapper.findByPhone(userDto.getPhone());
            if (user!=null){//用户存在
                //验证验证码
                String code = userDto.getCode();
                String phone = userDto.getPhone();
                String key = Constant.PHONELOG_CODE_KEY +phone;
                //在数据库中获取
                String value = RedisUtils.INSTANCE.get(key);
                if (value==null){
                    return AjaxResult.me().setSuccess(false).setMsg("验证码已过期");
                }
                //得到数据库中的验证码
                String codeRedis = value.split(":")[0];
                //判断验证码
                if (!code.equals(codeRedis)){
                    return AjaxResult.me().setSuccess(false).setMsg("验证码错误");
                }
                //验证码正确则查询将此user对象添加进登录redis数据库
                //生成令牌
                String userToken = UUID.randomUUID().toString();
                //将用户保存在redis中(后端) 转化为字符串
                String loginUserJsonStr = JSON.toJSONString(user);
                //保存在redis中并设置过期时间30分钟
                RedisUtils.INSTANCE.set(userToken,loginUserJsonStr,Constant.USER_EXPIRE_TIME);
                //封装成map返回给前端
                HashMap<String, Object> map = new HashMap<>();
                map.put("userToken",userToken);
                map.put("loginUser",loginUserJsonStr);
                return AjaxResult.me().setMsg("登录成功").setData(map);
            }
        }
        return AjaxResult.me().setSuccess(false).setMsg("该手机号未注册");
    }

    /**
     * 手机登录发送验证码
     * @param phone
     * @return
     */
    @Override
    public AjaxResult sendPhoneCodeForLog(String phone) {
        //生成四位验证码
        String code = StrUtils.getRandomString(4);
        //先查看数据库以前是否有key
        String key = Constant.PHONELOG_CODE_KEY +phone;
        String value = null;
        //查询
        String oldValue = RedisUtils.INSTANCE.get(key);
        //调用工具类同时判断非空和非null
        if (StringUtils.isNotBlank(oldValue)){
            System.out.println(1);
            //以前有该键
            //分离出时间戳
            Long oldTime = Long.valueOf(oldValue.split(":")[1]);
            Long newTime  = System.currentTimeMillis();
            //差值
            long time = newTime - oldTime;
            if (time<1000*60){
                //一分钟内重复获取
                return AjaxResult.me().setSuccess(false).setMsg("请勿频繁获取");
            }else if (time<Constant.CODE_EXPIRE_TIME*1000){
                //未过期 直接发送上次的code
                code = oldValue.split(":")[0];
            }
        }
        //加上现在时间戳
        value = code +":"+System.currentTimeMillis();
        //设置进redis
        RedisUtils.INSTANCE.set(key,value,Constant.CODE_EXPIRE_TIME);
        System.out.println(value);
        //发送短信
        //根据短信通平台 注册 获得api接口 添加jar包 封装工具类再进行调用
        try {
            SMSUtil.sendCode(phone,code);
            return AjaxResult.me().setMsg("获取成功，请在五分钟内验证");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.me().setSuccess(false).setMsg("网络故障");
    }

    /**
     * 检查用户名是否被注册
     * @param username
     * @return
     */
    @Override
    public AjaxResult checkUsername(String username) {
        User user = userMapper.findByUsername(username);
        //进行判断
        if (user==null){
            //如果查询不到用户说明 该用户名可以被注册
            return AjaxResult.me();
        }
        return AjaxResult.me().setSuccess(false).setMsg("该用户名已被注册");
    }

    /**
     * 登出方法
     * @param request
     * @return
     */
    @Override
    public AjaxResult logout(HttpServletRequest request) {
        //获取请求头
        String userToken = request.getHeader(Constant.USER_TOKEN);
        System.out.println(userToken);
        if (!StringUtils.isNotBlank(userToken)){
            //如果为空
            return AjaxResult.me().setMsg("已经登出");
        }
        //查询到了用户
        //将令牌取出 去redis数据库里删除
        RedisUtils.INSTANCE.del(userToken);
        return AjaxResult.me().setMsg("登出成功");
    }
}
