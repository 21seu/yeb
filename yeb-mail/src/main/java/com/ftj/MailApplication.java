package com.ftj;

import com.ftj.server.constants.MailConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by fengtj on 2021/8/27 1:21
 */
@SpringBootApplication
public class MailApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }

//    @Bean
//    public Queue queue() {
//        return new Queue(MailConstants.MAIL_QUEUE_NAME);
//    }
}
