package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.atguigu.springcloud.service.EchoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private ApolloDataSource<List<FlowRule>> flowRuleApolloDataSource ;

    @Autowired
    private EchoService echoService;

    @RequestMapping("/payment/consul")
    @SentinelResource(value = "/payment/consul",blockHandler = "block")
    public String paymentZk() throws Exception {
        String s = flowRuleApolloDataSource.readSource();
        System.out.println(s);
        return "paymentConsul";
    }

    public String block(BlockException ex){

        return "block";
    }

    @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
    String echo(){
        return echoService.echo();
    }

    @RequestMapping("/paramFlow")
    @SentinelResource(value = "paramFlow",blockHandler = "paramFlowBlock")
    public String paramFlow(@RequestParam(value ="name",required = false ) String name,
                            @RequestParam(value ="age",required = false ) String age) throws Exception {
        List<ParamFlowRule> rules = ParamFlowRuleManager.getRules();
        for (ParamFlowRule rule : rules) {
            System.out.println(rule);
        }
        return "paramFlow";
    }

    public String paramFlowBlock(String name,String age,BlockException ex) throws Exception {

        return "paramFlowBlock";
    }

}
