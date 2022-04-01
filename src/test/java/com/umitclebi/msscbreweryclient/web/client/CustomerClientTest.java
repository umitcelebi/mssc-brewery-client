package com.umitclebi.msscbreweryclient.web.client;

import com.umitclebi.msscbreweryclient.web.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerClientTest {

    @Autowired
    CustomerClient client;

    @Test
    void getCustomerById() {
        CustomerDto customerDto= client.getCustomerById(UUID.randomUUID());
    }

    @Test
    public void saveNewCustomer() {
        CustomerDto customerDto=CustomerDto.builder().name("Umit").build();

        URI uri= client.saveNewCustomer(customerDto);
        assertNotNull(uri);

        System.out.println(uri);
    }

    @Test
    void updateCustomer() {
        CustomerDto customerDto=CustomerDto.builder().name("Ümit").build();
        client.updateCustomer(UUID.randomUUID(),customerDto);
    }

    @Test
    void deleteCustomer() {
        client.deleteCustomer(UUID.randomUUID());
    }
}