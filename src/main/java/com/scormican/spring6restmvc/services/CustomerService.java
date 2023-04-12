package com.scormican.spring6restmvc.services;

import com.scormican.spring6restmvc.model.CustomerDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {


    List<CustomerDTO> listCustomers();

    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO addCustomer(CustomerDTO newCust);

    void updateCustomerById(UUID customerId, CustomerDTO cust);

    void delCustById(UUID customerId);
}
