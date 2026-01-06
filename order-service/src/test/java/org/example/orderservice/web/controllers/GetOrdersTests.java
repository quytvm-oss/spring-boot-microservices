package org.example.orderservice.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.orderservice.AbstractIT;
import org.example.orderservice.WithMockOAuth2User;
import org.junit.jupiter.api.Test;

public class GetOrdersTests extends AbstractIT {

    @Test
    @WithMockOAuth2User(username = "quytvm")
    void shouldGetOrdersSuccessfully() throws Exception {
        mockMvc.perform(get("/api/orders")).andExpect(status().isOk());
    }
}
