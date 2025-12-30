package org.example.bookstore.catalog.web.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.example.bookstore.catalog.AbstractIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@Sql("/test-data.sql")
public class ProductControllerTest extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnProducts() throws Exception {
        mockMvc.perform(get("/api/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(10)))
                .andExpect(jsonPath("$.totalElements", is(15)))
                .andExpect(jsonPath("$.pageNumber", is(1)))
                .andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(jsonPath("$.isFirst", is(true)))
                .andExpect(jsonPath("$.isLast", is(false)))
                .andExpect(jsonPath("$.hasNext", is(true)))
                .andExpect(jsonPath("$.hasPrevious", is(false)));
    }

    @Test
    void shouldGetProductByCode() throws Exception {
        mockMvc.perform(get("/api/products/{code}", "P100").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("P100")))
                .andExpect(jsonPath("$.name", is("The Hunger Games")))
                .andExpect(jsonPath("$.description", is("Winning will make you famous. Losing means certain death...")))
                .andExpect(jsonPath("$.price", is(34.0)));
    }

    @Test
    void shouldReturnNotFoundWhenProductCodeNotExists() throws Exception {
        String code = "invalid_product_code";

        mockMvc.perform(get("/api/products/{code}", code).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.title", is("Product Not Found")))
                .andExpect(jsonPath("$.detail", is("Product with code " + code + " not found")));
    }
}
