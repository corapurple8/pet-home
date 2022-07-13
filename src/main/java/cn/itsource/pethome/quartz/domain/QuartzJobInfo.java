package cn.itsource.pethome.quartz.domain;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Data
public class QuartzJobInfo implements Serializable {
    /**类型*/
    private byte type;
    /**定时任务名称*/
    private String jobName;
    /**传进来的参数*/
    private Map<String, Object> params;
    /**crons表达式*/
    private String cronj;
    /**任务启动时间*/
    private Date fireDate;

    public void setFireDate(Date fireDate) {
        this.fireDate = fireDate;
        String[] cronArr = new String[7];
        for (int i = 0; i < cronArr.length; i++) {
            cronArr[i] = "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fireDate);
        int second = calendar.get(Calendar.SECOND);
        int minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        cronArr[0] = second + "";
        cronArr[1] = minute + "";
        cronArr[2] = hour + "";

        cronArr[3] = day + "";
        cronArr[4] = month + "";
        cronArr[5] = "?";
        cronArr[6] = year + "";

        String cron = StringUtils.join(cronArr," ").trim();
        this.setCronj(cron);
    }

}