package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AcceptMq {


    @RabbitListener(queues = RabbitConstant.RECEIVE_PROCESS_EXE_QUEUE)
    public void accept(Message msg) throws IOException {
        System.out.println(msg);
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.readValue(msg.getBody(), String.class);
        System.out.println(result);
    }
}
