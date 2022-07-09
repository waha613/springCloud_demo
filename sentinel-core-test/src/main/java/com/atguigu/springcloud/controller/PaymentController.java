package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private ApolloDataSource<List<FlowRule>> flowRuleApolloDataSource ;

    @RequestMapping("/payment/consul")
    @SentinelResource(value = "test1",blockHandler = "block")
    public String paymentZk() throws Exception {
        String s = flowRuleApolloDataSource.readSource();
        System.out.println(s);
        return "paymentConsul";
    }

    public String block(BlockException ex){

        return "block";
    }

}
