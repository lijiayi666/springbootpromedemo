package com.lijiayi.springbootpromedemo.service;

import com.lijiayi.springbootpromedemo.entity.ScheduleEctity;

/**
 * @author Jiayi Li
 */
public interface TestScheduleService {

    void addTask(ScheduleEctity scheduleEctity);

    void updateTask(ScheduleEctity scheduleEctity);
}
