package com.atguigu.springcloud.config;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Component;

@Component
public class SentinelResourceException {
    /**
     * 定义需求:
     * 1,必须为public权限
     * 2,必须为static
     * 3,返回值类型要与@SentinelResource标识的方法返回值保持一致性
     * 4,参数类型为BlockException
     * 5,方法名自己定义即可
     * */
    public static String doBlockHandler(BlockException e){

        return "访问太频繁,请稍等..";
    }

}
