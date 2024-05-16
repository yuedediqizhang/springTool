package com.example.demo;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class Test {

    @Autowired
    RabbitTemplate rabbitTemplate;


    @GetMapping("/send")
    public void send() {
        MyUser myUser = new MyUser();
        myUser.setAge(11);
        myUser.setName("张三");
        myUser.setRemark("这是一个测试");
        rabbitTemplate.convertAndSend(RabbitConstant.REQUEST_PROCESS_EXE_EXCHANGE, RabbitConstant.REQUEST_PROCESS_EXE_RK, myUser);
    }
}
