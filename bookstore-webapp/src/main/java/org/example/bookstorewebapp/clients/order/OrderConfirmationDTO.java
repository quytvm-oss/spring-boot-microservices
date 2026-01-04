package org.example.bookstorewebapp.clients.order;

public record OrderConfirmationDTO(String orderNumber, OrderStatus status) {}
