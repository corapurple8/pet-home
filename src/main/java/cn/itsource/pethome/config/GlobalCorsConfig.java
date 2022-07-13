package cn.itsource.pethome.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 后端配置解决跨域的配置类  过滤器 在所有的请求进入拦截器之前都加允许跨域的请求头
 */
//@Configuration
public class GlobalCorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        System.out.println("GlobalCorsConfig...................................................");
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //1) 允许跨域访问的域,不要写*，否则cookie就无法使用了
        //前台管理系统的ip和端口 不能加最后的/
        config.addAllowedOrigin("http://127.0.0.1");
        config.addAllowedOrigin("http://localhost");
        config.addAllowedOrigin("http://www.pethome.com");
        //外网域名 虽然没有访问 但必须通行
        config.addAllowedOrigin("http://bugtracker.itsource.cn");
        //后台管理系统的ip和端口
        config.addAllowedOrigin("http://127.0.0.1:8081");
        config.addAllowedOrigin("http://localhost:8081");
        config.addAllowedOrigin("http://admin.pethome.com");

        //2) 是否发送Cookie信息
        config.setAllowCredentials(true);
        //3) 允许的请求方式
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        // 4）允许的头信息 必须是* 因为不止跨域的一个请求头
        config.addAllowedHeader("*");
        //2.添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        //3.返回新的CorsFilter.
        return new CorsFilter(configSource);
    }
}