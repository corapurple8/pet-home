package cn.itsource.pethome.util;

import org.quartz.*;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Quartz调度管理器
 * 
 */
public class QuartzUtils {
   private static String JOB_GROUP_NAME = "JOB_GROUP_SYSTEM";
   private static String TRIGGER_GROUP_NAME = "TRIGGER_GROUP_SYSTEM";

   /**
    * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
    * 
    * @param sched
    *            调度器
    * 
    * @param jobName
    *            任务名
    * @param cls
    *            任务
    * @param params
    *            任务参数
    * @param crons
    *            时间设置，参考quartz说明文档
    * 
    * @Title: QuartzManager.java
    */
   public static void addJob(Scheduler sched, String jobName, @SuppressWarnings("rawtypes") Class cls, Object params,
         String crons) {
      try {
         JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);// 任务名，任务组，任务执行类
         @SuppressWarnings("unchecked")

         JobDataMap jobDataMap = new JobDataMap();
         jobDataMap.put("params", params);
         JobDetail jobDetail = newJob(cls).withIdentity(jobKey).setJobData(jobDataMap).build();
         TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);// 触发器
         System.out.println(crons);
         Trigger trigger = newTrigger().withIdentity(triggerKey).withSchedule(cronSchedule(crons)).build();// 触发器时间设定
         sched.scheduleJob(jobDetail, trigger);
         if (!sched.isShutdown()) {
            sched.start();// 启动
         }
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   /**
    * @Description: 添加一个定时任务
    * 
    * @param sched
    *            调度器
    * 
    * @param jobName
    *            任务名
    * @param jobGroupName
    *            任务组名
    * @param triggerName
    *            触发器名
    * @param triggerGroupName
    *            触发器组名
    * @param jobClass
    *            任务
    * @param params
    *            任务参数
    * @param crons
    *            时间设置，参考quartz说明文档
    * 
    * @Title: QuartzManager.java
    */
   public static void addJob(Scheduler sched, String jobName, String jobGroupName, String triggerName,
         String triggerGroupName, @SuppressWarnings("rawtypes") Class jobClass,  Object params, String crons) {
      try {
         JobKey jobKey = new JobKey(jobName, jobGroupName);
         JobDataMap jobDataMap = new JobDataMap();
         jobDataMap.put("params", params);
         @SuppressWarnings("unchecked")
         JobDetail jobDetail = newJob(jobClass).withIdentity(jobKey).setJobData(jobDataMap).build();
         // 触发器
         TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroupName);
         Trigger trigger = newTrigger().withIdentity(triggerKey).withSchedule(cronSchedule(crons)).build();
         sched.scheduleJob(jobDetail, trigger);
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   /**
    * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
    * 
    * @param sched
    *            调度器
    * @param jobName
    * @param crons
    * 
    * @Title: QuartzManager.java
    */
   @SuppressWarnings("rawtypes")
   public static void modifyJobTime(Scheduler sched, String jobName, String crons) {
      try {
         TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
         CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
         if (trigger == null) {
            return;
         }
         String oldTime = trigger.getCronExpression();
         if (!oldTime.equalsIgnoreCase(crons)) {
            JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);
            JobDetail jobDetail = sched.getJobDetail(jobKey);
            Class objJobClass = jobDetail.getJobClass();
            Object params = jobDetail.getJobDataMap().get("params");
            removeJob(sched, jobName);
            System.out.println("修改任务：" + jobName);
            addJob(sched, jobName, objJobClass, params,crons);
         }
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   /**
    * @Description: 修改一个任务的触发时间
    * 
    * @param sched
    *            调度器 *
    * @param sched
    *            调度器
    * @param triggerName
    * @param triggerGroupName
    * @param crons
    * 
    * @Title: QuartzManager.java
    */
   public static void modifyJobTime(Scheduler sched, String triggerName, String triggerGroupName, String crons) {
      try {
         TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroupName);
         CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
         if (trigger == null) {
            return;
         }
         String oldTime = trigger.getCronExpression();
         if (!oldTime.equalsIgnoreCase(crons)) {
            // 修改时间
            trigger.getTriggerBuilder().withSchedule(cronSchedule(crons));
            // 重启触发器
            sched.resumeTrigger(triggerKey);
         }
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   /**
    * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
    * 
    * @param sched
    *            调度器
    * @param jobName
    * 
    * @Title: QuartzManager.java
    */
   public static void removeJob(Scheduler sched, String jobName) {
      try {
         TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
         sched.pauseTrigger(triggerKey);// 停止触发器
         sched.unscheduleJob(triggerKey);// 移除触发器
         JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);
         boolean b = sched.deleteJob(jobKey);// 删除任务
         System.out.println(b);
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   /**
    * @Description: 移除一个任务
    * 
    * @param sched
    *            调度器
    * @param jobName
    * @param jobGroupName
    * @param triggerName
    * @param triggerGroupName
    * 
    * @Title: QuartzManager.java
    */
   public static void removeJob(Scheduler sched, String jobName, String jobGroupName, String triggerName,
         String triggerGroupName) {
      try {
         TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroupName);
         sched.pauseTrigger(triggerKey);// 停止触发器
         sched.unscheduleJob(triggerKey);// 移除触发器
         JobKey jobKey = new JobKey(jobName, jobGroupName);
         sched.deleteJob(jobKey);// 删除任务
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   /**
    * @Description:启动所有定时任务
    * 
    * @param sched  调度器
    * 
    * @Title: QuartzManager.java
    */
   public static void startJobs(Scheduler sched) {
      try {
         sched.start();
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   /**
    * @Description:关闭所有定时任务
    * 
    * @param sched
    *            调度器
    * 
    */
   public static void shutdownJobs(Scheduler sched) {
      try {
         if (!sched.isShutdown()) {
            sched.shutdown();
         }
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }
}
