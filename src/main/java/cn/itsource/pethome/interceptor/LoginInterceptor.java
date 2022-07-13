package cn.itsource.pethome.interceptor;

import cn.itsource.pethome.constant.Constant;
import cn.itsource.pethome.util.AjaxResult;
import cn.itsource.pethome.util.RedisUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 登录拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //放行询问是否能跨域的请求
        if("OPTIONS".equals(request.getMethod().toUpperCase())) {
            return true;
        }
        //先判断用户是否登录
        //获取请求头
        String userToken = request.getHeader(Constant.USER_TOKEN);
        //进行判断
        if (!StringUtils.isNotBlank(userToken)){//为空
            //没有请求头
            //调用没有用户的方法
            noUserWrite(request,response);
            return false;
        }else {
            //存在该消息头 从数据库中查询是否存在该令牌 令牌是唯一的 所对应的值是用户信息json字符串
            String loginUserRedis = RedisUtils.INSTANCE.get(userToken);
            //再进行判断
            if (!StringUtils.isNotBlank(loginUserRedis)){//存在redis中的用户已经过期
                //调用没有用户的方法
                noUserWrite(request,response);
                return false;
            }
            //获取到了用户
            //重新设置redis中的时间
            RedisUtils.INSTANCE.set(userToken,loginUserRedis,Constant.USER_EXPIRE_TIME*1000);
            return true;
        }

    }

    public void noUserWrite(HttpServletRequest request,HttpServletResponse response){
        //返回结果
        AjaxResult ajaxResult = AjaxResult.me().setSuccess(false).setMsg("noUser");
        //转换为json对象前端才能识别
        String jsonResult = JSON.toJSONString(ajaxResult);
        PrintWriter writer = null;
        try {
            //发出错误信息 写出页面
            response.setContentType("application/json;charset=utf-8");
            //获取写出流后写出
            writer = response.getWriter();
            writer.write(jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (writer!=null){
                //关流
                writer.close();
            }
        }
    }
}
