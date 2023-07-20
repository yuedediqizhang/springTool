package com.example.processexeserver;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Component
public class ProcessAlgorithmService {

    @Value("${execution_file_path}")
    private String exeFilePath;


    @Autowired
    RabbitTemplate rabbitTemplate;


    @RabbitListener(queues = RabbitConstant.REQUEST_PROCESS_EXE_QUEUE)
    public void accept(Message msg, MyUser myUser) throws IOException {
        System.out.println(myUser.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        myUser.setAge(20);
        myUser.setName("李四");
        myUser.setRemark("我正在处理计算业务");
        String param = objectMapper.writeValueAsString(myUser);
        // 重写exe文件的执行命令
        // python 命令如下
        // python xxx.py --p {"name":"张三","age":11,"remark":"这是一个测试"}
//        String[] command = {"python", exeFilePath, "--p", param};
        // WindowProcessExeUtil.execCmd(命令行, 超时时间, 编码格式 window 格式为 GBK);
        String[] command = {"D:\\ptc\\sketchMap\\range1.exe","D:\\ptc\\sketchMap\\Range_in.txt"};
        String result = WindowProcessExeUtil.execCmd(new String[]{}, 3600, "GBK");
        System.out.println(result);
        if (!StringUtils.hasText(result)) {
            result = "执行失败";
        }
        rabbitTemplate.convertAndSend(RabbitConstant.RECEIVE_PROCESS_EXE_EXCHANGE, RabbitConstant.RECEIVE_PROCESS_EXE_RK, result);
    }
}
