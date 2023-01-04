package com.atguigu.springcloud.taskTest;

import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TaskConfig {
    private static final Map<String,TaskEntity> taskEntityMap = new HashMap<>();

    public static Map<String,TaskEntity> getTaskEntityMap() {
        return taskEntityMap;
    }
}
