package com.lijiayi.springbootpromedemo.schedule;

import com.lijiayi.springbootpromedemo.entity.ScheduleEctity;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @author Jiayi Li
 */
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 定时任务的逻辑代码
        ScheduleEctity params = (ScheduleEctity) jobExecutionContext.getJobDetail().getJobDataMap().get("params");
        System.out.println("定时任务执行：" + new Date());
        System.out.println(params.getJobName() + params.getJobGroupName() + params.getTriggerName() + params.getTriggerGroupName());
    }
}
