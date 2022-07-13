package cn.itsource.pethome.user.service.impl;

import cn.hutool.http.HttpUtil;
import cn.itsource.pethome.basic.service.impl.BaseServiceImpl;
import cn.itsource.pethome.constant.Constant;
import cn.itsource.pethome.domain.User;
import cn.itsource.pethome.domain.WxUser;
import cn.itsource.pethome.dto.ResultToken;
import cn.itsource.pethome.dto.WxUserDto;
import cn.itsource.pethome.query.WxUserQuery;
import cn.itsource.pethome.user.mapper.UserMapper;
import cn.itsource.pethome.user.mapper.WxUserMapper;
import cn.itsource.pethome.user.service.IWxUserService;
import cn.itsource.pethome.util.AjaxResult;
import cn.itsource.pethome.util.MD5Utils;
import cn.itsource.pethome.util.RedisUtils;
import cn.itsource.pethome.util.StrUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class WxUserServiceImpl extends BaseServiceImpl<WxUser, WxUserQuery> implements IWxUserService {

    @Autowired//注入微信用户mapper
    private WxUserMapper wxUserMapper;
    @Autowired //注入本站用户mapper
    private UserMapper userMapper;

    /**
     * 微信授权登录方法
     * @param code
     * @return
     */
    @Override
    public AjaxResult wechatLogin(String code) {
        //获取到微信端返回的code后 在后端发送请求获取授权信息通过code获取access_token
        //工具类hutools发送请求 获取access_token等数据
        String result = HttpUtil.get(Constant.ACCESS_TOKEN_URL.replace("APPID", Constant.APPID)
                .replace("CODE", code)
                .replace("SECRET", Constant.SECRET));
        //将获取的结果封装成一个类 将字符串转换成一个对象
        ResultToken resultToken = JSON.parseObject(result, ResultToken.class);
        System.out.println(resultToken);
        //进行判断
        if (StringUtils.isNotBlank(resultToken.getErrcode())||StringUtils.isNotBlank(resultToken.getErrmsg())){
            //拿到错误查询的信息
            return AjaxResult.me().setSuccess(false).setMsg("获取access_token失败");
        }
        //拿到token
        //根据拿到的openid和unionid查询微信用户表
        WxUser wxUser=wxUserMapper.findByOpenidAndUnionid(resultToken.getOpenid(),resultToken.getUnionid());
        //再进行判断
        if (wxUser==null){
            //该微信用户还没有登录过
            //返回给前端让其进行微信绑定注册(带上unionid和openid)
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("openid",resultToken.getOpenid());
            map.put("unionid",resultToken.getUnionid());
            map.put("access_token",resultToken.getAccess_token());
            return AjaxResult.me().setSuccess(false).setMsg("unbind").setData(map);
        }
        //该用户已经登录过本站，拿出该微信用户绑定的用户外键并在用户表中查询
        Long user_id = wxUser.getUser_id();
        User user = userMapper.findOne(user_id);
        //将该用户保存到redis 并生成uuid作为令牌 对象作为值 将对象转为json字符串也返回给前端 封装成map
        //令牌
        String userToken = UUID.randomUUID().toString();
        String loginUser = JSON.toJSONString(user);
        //将令牌-用户 保存到redis 设置过期时间为30分钟
        RedisUtils.INSTANCE.set(userToken,loginUser,Constant.USER_EXPIRE_TIME*1000);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("userToken",userToken);
        map.put("loginUser",loginUser);
        //返回给前端  前端接收到自动保存到localstorage中 作为本地登录凭证
        return AjaxResult.me().setMsg("登录成功").setData(map);
    }

    /**
     * 微信绑定注册方法
     * @param wxUserDto
     * @return
     */
    @Override
    @Transactional
    public AjaxResult wxReg(WxUserDto wxUserDto) {
        //先查询出微信用户 以免出问题浪费资源
        //发送请求
        //工具类hutools发送请求 获取微信用户的各种用户名 地区 性别等数据
        //https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
        String user_info = HttpUtil.get(Constant.GET_USERINFO_URL.replace("ACCESS_TOKEN", wxUserDto.getAccess_token())
                .replace("OPENID", wxUserDto.getOpenid()));
        System.out.println(user_info);
        //加判断
        if (!StringUtils.isNotBlank(user_info)){
            //出错了
            return AjaxResult.me().setSuccess(false).setMsg("请重新扫码");
        }
        //放到wxUserDto
        WxUserDto wxUserDtoInfo = JSON.parseObject(user_info, WxUserDto.class);
        System.out.println(wxUserDtoInfo);
        if (StringUtils.isNotBlank(wxUserDtoInfo.getErrcode())||StringUtils.isNotBlank(wxUserDtoInfo.getErrmsg())){
            return AjaxResult.me().setSuccess(false).setMsg("请重新扫码");
        }
        //将用户信息转换为wxuser对象
        WxUser wxUser = new WxUser();
        BeanUtils.copyProperties(wxUserDtoInfo,wxUser);
        wxUser.setAddress(wxUserDtoInfo.getCountry()+","+wxUserDtoInfo.getProvince()+","+wxUserDtoInfo.getCity());
        System.out.println(wxUser);
        //将绑定信息存储在user中写进user表以及保存在redis 将令牌交给前端
        User user = new User();
        BeanUtils.copyProperties(wxUserDto,user);
        //设置盐值
        String salt = StrUtils.getComplexRandomString(32);
        //md5加密 原始+盐值
        String password = MD5Utils.encrypByMd5(wxUserDto.getPassword()+salt);
        //将密码和盐值设置回user
        user.setPassword(password);
        user.setSalt(salt);
        user.setHeadImg(wxUser.getHeadimgurl());
        //设置为启用
        user.setState(Constant.NORMAL);
        //写进数据库
        userMapper.save(user);//自动递增主键
        //绑定进微信用户的外键
        wxUser.setUser_id(user.getId());
        //写进数据库
        wxUserMapper.save(wxUser);
        //令牌
        String userToken = UUID.randomUUID().toString();
        String loginUser = JSON.toJSONString(user);
        //将令牌-用户 保存到redis 设置过期时间为30分钟
        RedisUtils.INSTANCE.set(userToken,loginUser,Constant.USER_EXPIRE_TIME*1000);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("userToken",userToken);
        map.put("loginUser",loginUser);
        //返回给前端  前端接收到自动保存到localstorage中 作为本地登录凭证
        return AjaxResult.me().setMsg("绑定成功").setData(map);
    }
}
