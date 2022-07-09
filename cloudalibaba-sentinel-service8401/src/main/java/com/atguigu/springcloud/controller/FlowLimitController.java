package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.atguigu.springcloud.config.SentinelResourceException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ll4859332@hotmail.com
 * @version V1.0
 * @title
 * @description
 * @date 2022-06-17 10:57
 */
@RestController
public class FlowLimitController
{

    @GetMapping("/testA")
    public String testA()  {

        System.out.println("hello");

        return "------testA";
    }

    @GetMapping("/testB")
    @SentinelResource(value = "haha",
            blockHandlerClass = SentinelResourceException.class,
            blockHandler = "doBlockHandler")
    public String testB()
    {
        int a = 1/0;
        System.out.println(a);
        return "------testB";
    }

    public String HandleException(){
        return "hahahahha";
    }
}
