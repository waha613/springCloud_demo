package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    public PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "payment/add")
    public CommonResult addPayment(@RequestBody Payment payment){
        int result = paymentService.addPayment(payment);
        log.info("******插入结果：" + result);

        if(result > 0){
            return new CommonResult(200,"success,serverPort:" + serverPort,result);
        }else {
            return new CommonResult(444,"fail,serverPort:" + serverPort,result);
        }
    }

    @GetMapping(value = "payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("*******查询结果：" + payment);

        if(payment != null){
            return new CommonResult(200,"success,serverPort:" + serverPort,payment);
        }else {
            return new CommonResult<Payment>(444,"未找到此id对应的信息,serverPort:" + serverPort,null);
        }
    }
}
