package org.example.orderservice.web.controller;

import org.example.orderservice.ApplicationProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQDemoController {
    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties properties;

    public RabbitMQDemoController(RabbitTemplate rabbitTemplate, ApplicationProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    @PostMapping("/send")
    public void send(@RequestBody MyMessage myMessage) {
        rabbitTemplate.convertAndSend(properties.orderEventsExchange(), myMessage.routingKey(), myMessage.payload());
    }
}

record MyMessage(String routingKey, MyPayload payload) {}

record MyPayload(Object content) {}
