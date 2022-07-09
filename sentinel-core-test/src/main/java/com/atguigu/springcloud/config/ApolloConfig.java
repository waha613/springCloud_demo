package com.atguigu.springcloud.config;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ApolloConfig {

    @Bean
    public Converter<List<FlowRule>, String> flowRuleEntityEncoder() {
        return JSON::toJSONString;
    }

    @Bean
    public Converter<String, List<FlowRule>> flowRuleEntityDecoder() {
        return s -> JSON.parseArray(s, FlowRule.class);
    }

    @Bean
    public ApolloDataSource<List<FlowRule>> FlowRuleApolloDataSource(){
        System.out.println("FlowRuleApolloDataSource:被执行");
        return new ApolloDataSource<List<FlowRule>>("TEST1.flow-config", "sentinel-core-test-flow-rules", "",flowRuleEntityDecoder());
    }

}
