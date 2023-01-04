package com.atguigu.springcloud.taskTest;

import lombok.Data;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.Task;

@Data
public class TaskEntity {
    private ScheduledTask task;
    private int execCount;
}
