package cn.itsource.pethome.quartz.jobs;

import cn.itsource.pethome.constant.Constant;
import cn.itsource.pethome.constant.PayConstants;
import cn.itsource.pethome.domain.OrderAdopt;
import cn.itsource.pethome.domain.PayBill;
import cn.itsource.pethome.order.mapper.OrderAdoptMapper;
import cn.itsource.pethome.pay.mapper.PayBillMapper;
import cn.itsource.pethome.quartz.domain.QuartzJobInfo;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class TimeOutAdoptJob implements Job {
    @Autowired
    private OrderAdoptMapper orderAdoptMapper;

    @Autowired
    private PayBillMapper payBillMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        //取出参数及进行取消订单的逻辑代码
        //取出对应的任务详情（里面装了任务的各种设置和参数）拿出数据map 取出键为params的值
        //该键是由QuartzUtils设置添加的 该变量名是params 取出object类型需要强转
        QuartzJobInfo quartzJobInfo = (QuartzJobInfo) jobExecutionContext.getJobDetail().getJobDataMap().get("params");
        //从info对象中拿出所需的参数作判断
        System.out.println("定时器:"+quartzJobInfo.getJobName());
        System.out.println("定时器:"+jobExecutionContext.getJobDetail().getDescription());
        String orderSn = (String) quartzJobInfo.getParams().get("orderSn");
        String businessType = (String) quartzJobInfo.getParams().get("businessType");
        //进行逻辑判断
        switch (businessType){
            case PayConstants.BUSINESSTYPE_ADOPT://领养订单
                //将订单号查询出支付单和订单
                OrderAdopt orderAdopt = orderAdoptMapper.findByOrderSn(orderSn);
                //修改状态
                orderAdopt.setState(Constant.REJECT);
                orderAdoptMapper.update(orderAdopt);
                break;
            case PayConstants.BUSINESSTYPE_PRODUCT://商品订单
                break;
            default:
                break;
        }
        //订单是公用代码
        PayBill payBill = payBillMapper.findByOrderSn(orderSn);
        payBill.setUpdateTime(new Date());
        payBill.setState(Constant.REJECT);
        payBillMapper.update(payBill);
    }
}
