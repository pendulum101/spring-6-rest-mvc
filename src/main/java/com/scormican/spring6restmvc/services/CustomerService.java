package com.scormican.spring6restmvc.services;

import com.scormican.spring6restmvc.model.CustomerDTO;
import java.util.List;
import java.util.UUID;

public interface CustomerService {


    List<CustomerDTO> listCustomers();

    public CustomerDTO getCustomerById(UUID id);

    public CustomerDTO addCustomer(CustomerDTO newCust);

    void updateCustomerById(UUID customerId, CustomerDTO cust);

    void delCustById(UUID customerId);
}
