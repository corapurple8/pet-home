package cn.itsource.pethome;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
@MapperScan("cn.itsource.pethome.*.mapper")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
//    @Bean
//    @QuartzDataSource
//    @ConfigurationProperties(prefix = "spring.quartz.properties.org.quartz.datasource")
//    DataSource quartzDataSource(){
//        return DataSourceBuilder.create().build();
//    }
}
