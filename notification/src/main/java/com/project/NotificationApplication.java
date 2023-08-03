package com.project;

import com.project.event.OrderInfoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

    @KafkaListener(topics = "notificationTopic")
    public void notification(OrderInfoEvent orderInfoEvent) {
        log.info("SENT MAIL" + orderInfoEvent.getOrderNo() + " success");
    }
}
