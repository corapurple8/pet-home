package cn.itsource.pethome.quartz.config;

import cn.itsource.pethome.quartz.jobs.Myjob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定时器配置类
 */
//@Configuration
public class QuartzConfiguration {

    /**
     * 任务详情
     * @return
     */
    @Bean
    public JobDetail jobDetail(){
        JobDetail jobDetail = JobBuilder.newJob(Myjob.class)
                .withIdentity("定时器任务名称")//名称
                .usingJobData("参数名称", "参数值")//参数
                .storeDurably()//即使没有关联的触发器也可以存在
                .build();//创建任务
        return jobDetail;
    }

    /**
     * 触发器 要用crons表达式
     * @return
     */
    @Bean
    public Trigger trigger(){
        //先准备定时器crons表达式 设置时间表
        CronScheduleBuilder cronSchedule= CronScheduleBuilder.cronSchedule("0/5 * * * * ? *");
        CronTrigger trigger = TriggerBuilder.newTrigger().forJob(this.jobDetail())//添加任务
                .withIdentity("触发器名称")//名称
                .withSchedule(cronSchedule)//时间表
                .build();//创建
        return trigger;
    }
}
