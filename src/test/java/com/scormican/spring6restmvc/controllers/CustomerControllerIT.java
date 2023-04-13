package com.scormican.spring6restmvc.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.scormican.spring6restmvc.entities.Customer;
import com.scormican.spring6restmvc.mappers.CustomerMapper;
import com.scormican.spring6restmvc.model.CustomerDTO;
import com.scormican.spring6restmvc.repositories.CustomerRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    CustomerController customerController;
    @Autowired
    CustomerRepository customerRepository;

    @Rollback
    @Transactional
    @Test
    void testUpdateByIdNotFound() {
        assertThrows(NotFoundException.class, () ->
            customerController.updateById(UUID.randomUUID(),
                customerMapper.customerToCustomerDto(Customer.builder().build()))
        );

    }

    @Rollback
    @Transactional
    @Test
    void testUpdateById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO dto = customerMapper.customerToCustomerDto(customer);
        final String name = "Updated Name";
        dto.setCustomerName(name);
        dto.setId(null);
        dto.setVersion(null);

        ResponseEntity responseEntity = customerController.updateById(customer.getId(),
            dto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customer.getId()).get().getCustomerName())
            .isEqualTo(name);

    }

    @Rollback
    @Transactional
    @Test
    void testPatchById() {
        UUID customerId = customerRepository.findAll().get(0).getId();
        final String name = "patchy";
        CustomerDTO dto = CustomerDTO.builder().customerName(name).build();
        customerController.updateCustomerPatchById(customerId, dto);
        assertThat(customerController.getCustomerById(customerId).getCustomerName()).isEqualTo(name);

    }

    @Rollback
    @Transactional
    @Test
    void testDelById() {
        Customer customer = customerRepository.findAll().get(0);
        ResponseEntity responseEntity = customerController.delById(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }

    @Rollback
    @Transactional
    @Test
    void testDelByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.delById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testHandlePost() {
        Customer cust = Customer.builder().customerName("hello").build();
        ResponseEntity responseEntity = customerController.handlePost(customerMapper.customerToCustomerDto(cust));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        assertThat(customerRepository.findById(savedUUID)).isNotNull();
    }

    @Test
    void testGetCustomerList() {
        assertThat(customerController.getCustomerList().size()).isEqualTo(2);
    }

    @Test
    void testGetCustomerById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }

    @Test
    void testGetCustomerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }
}