package com.scormican.spring6restmvc.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scormican.spring6restmvc.model.Customer;
import com.scormican.spring6restmvc.services.CustomerService;
import com.scormican.spring6restmvc.services.CustomerServiceImpl;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CustomerService customerService;

    @Autowired
    ObjectMapper objectMapper;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testDeleteCustomer() throws Exception {
        Customer customer = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(delete("/api/v1/customers/" + customer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(customerService).delCustById(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().equals(customer.getId()));

    }

    @Test
    void testUpdateCustomer() throws Exception {
        Customer customer = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(put("/api/v1/customers/" + customer.getId().toString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
            .andExpect(status().isNoContent());

        verify(customerService).updateCustomerById(any(UUID.class), any(Customer.class));
    }

    @Test
    void testCreateNewCustomer() throws Exception {
        Customer customer = customerServiceImpl.listCustomers().get(0);
        customer.setId(null);
        customer.setVersion(null);

        given(customerService.addCustomer(any(Customer.class))).willReturn(customerServiceImpl.listCustomers().get(0));
        mockMvc.perform(post("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
            .andExpect(status().isCreated());
    }

    @Test
    public void testListCustomers() throws Exception {

        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());

        mockMvc.perform(get("/api/v1/customers").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    public void testgetCustomerbyId() throws Exception {
        Customer testCustomer = customerServiceImpl.listCustomers().get(0);

        given(customerService.getCustomerById(testCustomer.getId())).willReturn(testCustomer);
        mockMvc.perform(get("/api/v1/customers/" + testCustomer.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.customerName", is(testCustomer.getCustomerName())));

    }
}