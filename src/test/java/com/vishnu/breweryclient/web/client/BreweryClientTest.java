package com.vishnu.breweryclient.web.client;

import com.vishnu.breweryclient.web.model.BeerDTO;
import com.vishnu.breweryclient.web.model.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BreweryClientTest {

    @Autowired
    BreweryClient breweryClient;

    @Test
    void getBeerById() {
        BeerDTO beerDTO = breweryClient.getBeerById(UUID.randomUUID());
        assertNotNull(beerDTO);
    }

    @Test
    void saveNewBeer() {
        BeerDTO beerDTO = BeerDTO.builder().beerName("New Beer").build();
        URI uri = breweryClient.saveNewBeer(beerDTO);
        assertNotNull(uri);
    }

    @Test
    void updateBeer() {
        BeerDTO beerDTO = BeerDTO.builder().beerName("New Beer").build();
        breweryClient.updateBeer(UUID.randomUUID(), beerDTO);
    }

    @Test
    void deleteBeer() {
        breweryClient.deleteBeer(UUID.randomUUID());
    }

    @Test
    void getCustomerById() {
        CustomerDTO customerDTO = breweryClient.getCustomerById(UUID.randomUUID());
        assertNotNull(customerDTO);
    }

    @Test
    void saveNewCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder().name("Vishnu").build();
        URI uri = breweryClient.saveNewCustomer(customerDTO);
        assertNotNull(uri);
    }

    @Test
    void updateCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder().name("Vishal").build();
        breweryClient.updateCustomer(UUID.randomUUID(), customerDTO);
    }

    @Test
    void deleteCustomer() {
        breweryClient.deleteCustomer(UUID.randomUUID());
    }


}