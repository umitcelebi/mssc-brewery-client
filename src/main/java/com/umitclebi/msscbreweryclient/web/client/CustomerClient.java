package com.umitclebi.msscbreweryclient.web.client;

import com.umitclebi.msscbreweryclient.web.model.CustomerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties("sfg.customer")
public class CustomerClient {

    private String apiHost;
    private final String CUSTOMER_PATH_V1="/api/v1/customer";

    private RestTemplate restTemplate;

    public CustomerClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public CustomerDto getCustomerById(UUID uuid){
        return restTemplate.getForObject(apiHost+CUSTOMER_PATH_V1+"/"+uuid,CustomerDto.class);
    }

    public URI saveNewCustomer(CustomerDto customerDto){
        return restTemplate.postForLocation(apiHost+CUSTOMER_PATH_V1,customerDto);
    }

    public void updateCustomer(UUID uuid,CustomerDto customerDto){
        restTemplate.put(apiHost+CUSTOMER_PATH_V1+"/"+uuid,customerDto);
    }

    public void deleteCustomer(UUID uuid){
        restTemplate.delete(apiHost+CUSTOMER_PATH_V1+"/"+uuid);
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }
}
