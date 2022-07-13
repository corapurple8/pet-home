package cn.itsource.pethome.quartz.service;

import cn.itsource.pethome.quartz.domain.QuartzJobInfo;

public interface IQuartzService {

    /**
     * 添加定时器任务
     * @param quartzJobInfo
     */
    void addJob(QuartzJobInfo quartzJobInfo);

    /**
     * 移除定时器任务
     * @param s
     */
    void removeJob(String s);
}
