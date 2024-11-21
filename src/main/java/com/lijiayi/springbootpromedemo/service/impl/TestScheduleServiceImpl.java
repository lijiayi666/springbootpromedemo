package com.lijiayi.springbootpromedemo.service.impl;

import com.lijiayi.springbootpromedemo.entity.ScheduleEctity;
import com.lijiayi.springbootpromedemo.schedule.MyJob;
import com.lijiayi.springbootpromedemo.service.TestScheduleService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Jiayi Li
 */
@Service
public class TestScheduleServiceImpl implements TestScheduleService {
    @Autowired
    @Qualifier("myScheduler")
    private Scheduler scheduler;

    private static final String triggerGroup = "app-mon-trigger";
    private static final String jobGroup = "app-mon-job";
    @Override
    public void addTask(ScheduleEctity scheduleEctity) {
        try {

            // 启动调度器
            scheduler.start();

            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("params", scheduleEctity);
            // 创建JobDetail
            JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                    .withIdentity(scheduleEctity.getId(), jobGroup)
                    .usingJobData(jobDataMap)
                    .build();

            // 创建Trigger
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(scheduleEctity.getId(), triggerGroup)
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(scheduleEctity.getCron()))
                    .build();

            // 将JobDetail和Trigger绑定到调度器
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTask(ScheduleEctity scheduleEctity) {
        try {
            // 获取旧的Trigger
            JobKey jobKey = new JobKey(scheduleEctity.getId(), jobGroup);

            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("params", scheduleEctity);
            // 创建JobDetail
            JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                    .withIdentity(scheduleEctity.getId(), jobGroup)
                    .usingJobData(jobDataMap)
                    .build();

            // 创建Trigger
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(scheduleEctity.getId(), triggerGroup)
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(scheduleEctity.getCron()))
                    .build();
            // 替换旧的Trigger为新的Trigger
            scheduler.deleteJob(jobKey);
            scheduler.scheduleJob(jobDetail, trigger);
            // todo 更新数据库表 证明该任务已被创建
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
