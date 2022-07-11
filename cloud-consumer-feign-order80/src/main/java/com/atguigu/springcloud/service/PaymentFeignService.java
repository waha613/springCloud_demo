package com.atguigu.springcloud.service;

import com.atguigu.springcloud.controller.Test1;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface PaymentFeignService {
    @GetMapping(value = "payment/get/{id}")
    CommonResult<? extends Object> getPaymentById(@PathVariable("id") Long id);
}
