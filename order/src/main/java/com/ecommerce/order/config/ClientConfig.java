package com.ecommerce.order.config;

import com.ecommerce.order.clients.CustomerClient;
import com.ecommerce.order.clients.PaymentClient;
import com.ecommerce.order.clients.ProductClient;
import com.ecommerce.order.clients.feign.CustomerClientFeign;
import com.ecommerce.order.clients.feign.PaymentClientFeign;
import com.ecommerce.order.clients.feign.ProductClientFeign;
import com.ecommerce.order.clients.rest.ProductClientRest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    ProductClient productClient(ProductClientFeign productClientFeign) {
        return productClientFeign;
    }

    @Bean
    CustomerClient customerClient(CustomerClientFeign customerClientFeign) {
        return customerClientFeign;
    }

    @Bean
    PaymentClient paymentClient(PaymentClientFeign paymentClientFeign) {return paymentClientFeign;}
}
