package com.digitalacademy.customer.config;

import com.digitalacademy.customer.api.LoanApi;
import com.digitalacademy.customer.model.response.GetLoanInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Value("${restTemplate.connectionTimeOut}")
    private int connectionTimeOut;

    @Value("${restTemplate.readTimeOut}")
    private int readTimeout;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
