package org.example.orderservice.web.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

@Service
public class RabbitMQListener {

    private final JsonMapper objectMapper;

    public RabbitMQListener(JsonMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    //    @RabbitListener(queues = "${orders.new-orders-queue}")
    //    public void handleNewOrder(OrderCreatedEvent payload) {
    //       // String json = objectMapper.writeValueAsString(payload.content());
    //        System.out.println("New order: " + payload.orderNumber());
    //    }

    @RabbitListener(queues = "${orders.delivered-orders-queue}")
    public void handleDeliveredOrder(MyPayload payload) {
        String json = objectMapper.writeValueAsString(payload.content());
        System.out.println("Delivered order: " + json);
    }
}
