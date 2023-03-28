package com.scormican.spring6restmvc.services;

import com.scormican.spring6restmvc.model.Beer;
import com.scormican.spring6restmvc.model.Customer;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CustomerService {


    List<Customer> listCustomers();

    public Customer getCustomerById(UUID id);

    public Customer addCustomer(Customer newCust);

    void updateCustomerById(UUID customerId, Customer cust);
}
