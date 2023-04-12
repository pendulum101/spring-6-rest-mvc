package com.scormican.spring6restmvc.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scormican.spring6restmvc.model.CustomerDTO;
import com.scormican.spring6restmvc.services.CustomerService;
import com.scormican.spring6restmvc.services.CustomerServiceImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;
    @Captor
    ArgumentCaptor<CustomerDTO> customerArgumentCaptor;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testPatchCustomer() throws Exception {
        CustomerDTO customer = customerServiceImpl.listCustomers().get(0);
        Map<String, Object> patch = new HashMap<>();
        patch.put("customerName", "Paltrow");

        mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID, customer.getId()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(patch)))
            .andExpect(status().isNoContent());
        verify(customerService).updateCustomerById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());
        assertThat(uuidArgumentCaptor.getValue().equals(customer.getId()));
        assertThat(customerArgumentCaptor.getValue().getCustomerName().equals(patch.get("customerName").toString()));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        CustomerDTO customer = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, customer.getId())
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
        verify(customerService).delCustById(uuidArgumentCaptor.capture());
        assertThat(uuidArgumentCaptor.getValue().equals(customer.getId()));

    }

    @Test
    void testUpdateCustomer() throws Exception {
        CustomerDTO customer = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, customer.getId().toString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
            .andExpect(status().isNoContent());

        verify(customerService).updateCustomerById(any(UUID.class), any(CustomerDTO.class));
    }

    @Test
    void testCreateNewCustomer() throws Exception {
        CustomerDTO customer = customerServiceImpl.listCustomers().get(0);
        customer.setId(null);
        customer.setVersion(null);

        given(customerService.addCustomer(any(CustomerDTO.class))).willReturn(customerServiceImpl.listCustomers().get(1));
        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
            .andExpect(status().isCreated());
    }

    @Test
    public void testListCustomers() throws Exception {

        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    public void testgetCustomerbyId() throws Exception {
        CustomerDTO testCustomer = customerServiceImpl.listCustomers().get(0);

        given(customerService.getCustomerById(testCustomer.getId())).willReturn(Optional.of(testCustomer));
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.customerName", is(testCustomer.getCustomerName())));

    }
}