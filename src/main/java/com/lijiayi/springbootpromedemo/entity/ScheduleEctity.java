package com.lijiayi.springbootpromedemo.entity;

import lombok.Data;

@Data
public class ScheduleEctity {
    private String id;
    private String jobName;
    private String jobGroupName;
    private String triggerName;
    private String triggerGroupName;
    private String cron;
}
