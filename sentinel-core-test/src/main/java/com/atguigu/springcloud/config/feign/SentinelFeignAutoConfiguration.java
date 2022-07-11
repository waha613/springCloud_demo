package com.atguigu.springcloud.config.feign;

import com.alibaba.csp.sentinel.SphU;
import feign.Feign;
import feign.Feign.Builder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ConditionalOnClass({SphU.class, Feign.class})
public class SentinelFeignAutoConfiguration {
    public SentinelFeignAutoConfiguration() {
    }

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
            name = {"feign.sentinel.enabled"}
    )
    public Builder feignSentinelBuilder() {
        return SentinelFeign.builder();
    }
}
