package org.example.notifierservice.events;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import org.example.notifierservice.AbstractIT;
import org.example.notifierservice.ApplicationProperties;
import org.example.notifierservice.domain.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class OrderEventHandlerTests extends AbstractIT {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ApplicationProperties properties;

    Customer customer = new Customer("Siva", "siva@gmail.com", "999999999");
    Address address = new Address("addr line 1", null, "Hyderabad", "TS", "500072", "India");

    @Test
    void shouldHandleOrderCreatedEvent() {
        String orderNumber = UUID.randomUUID().toString();

        var event = new OrderCreatedEvent(
                UUID.randomUUID().toString(), orderNumber, Set.of(), customer, address, LocalDateTime.now());
        rabbitTemplate.convertAndSend(properties.orderEventsExchange(), properties.newOrdersQueue(), event);

        await().atMost(30, SECONDS).untilAsserted(() -> {
            verify(notificationService).sendOrderCreatedNotification(any(OrderCreatedEvent.class));
        });
    }

    @Test
    void shouldHandleOrderDeliveredEvent() {
        String orderNumber = UUID.randomUUID().toString();

        var event = new OrderDeliveredEvent(
                UUID.randomUUID().toString(), orderNumber, Set.of(), customer, address, LocalDateTime.now());
        rabbitTemplate.convertAndSend(properties.orderEventsExchange(), properties.deliveredOrdersQueue(), event);

        await().atMost(30, SECONDS).untilAsserted(() -> {
            verify(notificationService).sendOrderDeliveredNotification(any(OrderDeliveredEvent.class));
        });
    }

    @Test
    void shouldHandleOrderCancelledEvent() {
        String orderNumber = UUID.randomUUID().toString();

        var event = new OrderCancelledEvent(
                UUID.randomUUID().toString(),
                orderNumber,
                Set.of(),
                customer,
                address,
                "test cancel reason",
                LocalDateTime.now());
        rabbitTemplate.convertAndSend(properties.orderEventsExchange(), properties.cancelledOrdersQueue(), event);

        await().atMost(30, SECONDS).untilAsserted(() -> {
            verify(notificationService).sendOrderCancelledNotification(any(OrderCancelledEvent.class));
        });
    }

    @Test
    void shouldHandleOrderErrorEvent() {
        String orderNumber = UUID.randomUUID().toString();

        var event = new OrderErrorEvent(
                UUID.randomUUID().toString(),
                orderNumber,
                Set.of(),
                customer,
                address,
                "test error reason",
                LocalDateTime.now());
        rabbitTemplate.convertAndSend(properties.orderEventsExchange(), properties.errorOrdersQueue(), event);

        await().atMost(30, SECONDS).untilAsserted(() -> {
            verify(notificationService).sendOrderErrorEventNotification(any(OrderErrorEvent.class));
        });
    }
}
