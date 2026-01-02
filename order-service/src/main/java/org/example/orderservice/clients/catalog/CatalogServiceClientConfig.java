package org.example.orderservice.clients.catalog;

import java.time.Duration;
import org.example.orderservice.ApplicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class CatalogServiceClientConfig {
    //    @Bean
    //    RestClient restClient(ApplicationProperties properties) {
    //        return RestClient.builder()
    //                .baseUrl(properties.catalogServiceUrl())
    //                .requestFactory(ClientHttpRequestFactories.get(ClientHttpRequestFactorySettings.DEFAULTS
    //                        .withConnectTimeout(Duration.ofSeconds(5))
    //                        .withReadTimeout(Duration.ofSeconds(5))))
    //                .build();
    //    }

    @Bean
    RestClient restClient(ApplicationProperties properties) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(5));
        factory.setReadTimeout(Duration.ofSeconds(5));

        return RestClient.builder()
                .baseUrl(properties.catalogServiceUrl())
                .requestFactory(factory)
                .build();
    }
}
