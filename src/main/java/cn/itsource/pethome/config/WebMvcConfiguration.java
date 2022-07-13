package cn.itsource.pethome.config;

import cn.itsource.pethome.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration//声明为mvc配置类
public class WebMvcConfiguration implements WebMvcConfigurer {
    //注入拦截器
    @Autowired
    private LoginInterceptor loginInterceptor;
    //设置添加注册拦截器

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")//匹配全路径
                .excludePathPatterns("/login/**","/fastdfs/**","/alipay/**");//除了登录需求和文件上传
        //文件上传没办法使用axios加消息头 所以直接从后端放行 或者在前端写方法加消息头
    }
}
