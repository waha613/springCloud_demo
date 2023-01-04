package com.atguigu.springcloud.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class DataSourceInitFunc implements InitFunc {

    @Override
    public void init() throws Exception {

        final String flowNameSpace = "TEST1.flow-config";
        final String flowKey = "sentinel-core-test-flow-rules";

        final String paramFlowNameSpace = "TEST1.paramFlow-config";
        final String paramFlowKey = "sentinel-core-test-param-flow-rules";

        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ApolloDataSource<List<FlowRule>>(flowNameSpace, flowKey,"",source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
        System.out.println("资源已注册");
    }
}
