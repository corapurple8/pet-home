package cn.itsource.pethome.quartz.service.impl;

import cn.itsource.pethome.quartz.domain.QuartzJobInfo;
import cn.itsource.pethome.quartz.jobs.TimeOutAdoptJob;
import cn.itsource.pethome.quartz.service.IQuartzService;
import cn.itsource.pethome.util.QuartzUtils;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class QuartzServiceImpl implements IQuartzService {
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public void addJob(QuartzJobInfo quartzJobInfo) {
        //调用工具类方法
        //满足添加任务的各个参数
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        //调度器 人物名 任务类class 参数，直接用该info 里面map封装了orderSn crons表达式 由info自动生成
        QuartzUtils.addJob(scheduler,quartzJobInfo.getJobName(), TimeOutAdoptJob.class,quartzJobInfo,quartzJobInfo.getCronj());
    }

    @Override
    public void removeJob(String jobName) {
        //调用工具类方法
        //满足添加任务的各个参数
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        System.out.println("取消定时器："+jobName);
        QuartzUtils.removeJob(scheduler,jobName);
    }
}
