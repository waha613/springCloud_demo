package com.atguigu.springcloud.taskTest;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.FixedRateTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TaskTest implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        Map<String, TaskEntity> taskEntityMap = TaskConfig.getTaskEntityMap();
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setExecCount(0);
        taskEntityMap.put("myTask",taskEntity);
        ScheduledTask myTask1 = scheduledTaskRegistrar.scheduleFixedRateTask(new FixedRateTask((Runnable) () -> {
            TaskEntity myTask = TaskConfig.getTaskEntityMap().get("myTask");
            myTask.setExecCount(myTask.getExecCount() + 1);
            System.out.println(">>>>>>>>>>>>>>>>>" + myTask.getExecCount());
            if (myTask.getExecCount() >= 5) {
                System.out.println("任务结束");
                myTask.getTask().cancel();
            }
        }, 5 * 1000, 2 * 1000));
        taskEntity.setTask(myTask1);
    }
}
