package com.atguigu.springcloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.springcloud.config.ThreadPoolExecutorConfig;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.feignTest.DynamicClient;
import com.atguigu.springcloud.feignTest.DynamicService;
import com.atguigu.springcloud.feignTest.DynamicService2;
import com.atguigu.springcloud.service.PaymentFeignService;
import com.atguigu.springcloud.taskTest.TaskConfig;
import com.atguigu.springcloud.taskTest.TaskEntity;
import com.atguigu.springcloud.util.SpringBeanUtil;
import feign.Feign;
import feign.Logger;
import feign.codec.Decoder;
import feign.slf4j.Slf4jLogger;
import org.apache.tomcat.websocket.Util;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.FixedRateTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/consumer")
@EnableScheduling
public class OrderController {

    @Autowired
    private PaymentFeignService paymentFeignService;

    @Qualifier("myExecutor")
    @Autowired
    private ThreadPoolTaskExecutor myExecutor;

    @Qualifier("taskScheduler")
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private DynamicClient dynamicClient;

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Test1> getPaymentById(@PathVariable("id") Long id){
       return (CommonResult<Test1>)paymentFeignService.getPaymentById(id);


    }

    @GetMapping(value = "/payment/testTask")
    public CommonResult<Test1> testTask(){
        ScheduledTaskRegistrar scheduledTaskRegistrar = new ScheduledTaskRegistrar();
        scheduledTaskRegistrar.setTaskScheduler(taskScheduler);
        int size = scheduledTaskRegistrar.getScheduledTasks().size();
        System.out.println(size);

        Map<String, TaskEntity> taskEntityMap = TaskConfig.getTaskEntityMap();
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setExecCount(0);
        String key = "myTask";
        if (taskEntityMap.get("myTask") != null) {
            taskEntityMap.get("myTask").getTask().cancel();
            System.out.println("任务取消");
            taskEntityMap.remove("myTask");
            return null;
        }
        taskEntityMap.put(key,taskEntity);
        ScheduledTask scheduledTask = scheduledTaskRegistrar.scheduleFixedRateTask(new FixedRateTask((Runnable) () -> {
            TaskEntity myTask = TaskConfig.getTaskEntityMap().get("myTask");

            myTask.setExecCount(myTask.getExecCount() + 1);
            for (int i = 0; i < 10000000; i++) {
                System.out.println(Thread.currentThread().getName());
//                try {
//                    Thread.sleep(0);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
            }
            System.out.println(Thread.currentThread().getName());
            if (myTask.getExecCount() >= 5) {
                System.out.println("任务结束");
                myTask.getTask().cancel();
            }
        }, 5 * 1000, 2 * 1000));
        taskEntity.setTask(scheduledTask);
        int size1 = scheduledTaskRegistrar.getScheduledTasks().size();
        System.out.println(size1);
        scheduledTaskRegistrar.afterPropertiesSet();
        return null;


    }

}
