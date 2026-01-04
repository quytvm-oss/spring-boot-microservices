package org.example.bookstorewebapp.clients;

import java.time.Duration;
import org.example.bookstorewebapp.ApplicationProperties;
import org.example.bookstorewebapp.clients.catalog.CatalogServiceClient;
import org.example.bookstorewebapp.clients.order.OrderServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientsConfig {

    private final ApplicationProperties properties;

    public ClientsConfig(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Bean
    RestClient.Builder restClientBuilder() {

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(5));
        requestFactory.setReadTimeout(Duration.ofSeconds(5));

        return RestClient.builder().baseUrl(properties.apiGatewayUrl()).requestFactory(requestFactory);
    }

    @Bean
    CatalogServiceClient catalogServiceClient(RestClient.Builder builder) {
        return createClient(builder, CatalogServiceClient.class);
    }

    @Bean
    OrderServiceClient orderServiceClient(RestClient.Builder builder) {
        return createClient(builder, OrderServiceClient.class);
    }

    private <T> T createClient(RestClient.Builder builder, Class<T> clientClass) {
        RestClient restClient = builder.build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();
        return factory.createClient(clientClass);
    }
}
