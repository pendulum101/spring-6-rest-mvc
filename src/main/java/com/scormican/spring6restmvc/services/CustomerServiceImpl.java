package com.scormican.spring6restmvc.services;

import com.scormican.spring6restmvc.model.Customer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();
        Customer first = Customer.builder()
            .id(UUID.randomUUID())
            .customerName("Barry Scott")
            .version("1")
            .createdDate(LocalDateTime.now())
            .modifiedDate(LocalDateTime.now())
            .build();

        Customer second = Customer.builder()
            .id(UUID.randomUUID())
            .customerName("Jared")
            .version("2")
            .createdDate(LocalDateTime.now())
            .modifiedDate(LocalDateTime.now())
            .build();
        this.customerMap.put(first.getId(), first);
        this.customerMap.put(second.getId(), second);
    }

    @Override
    public List<Customer> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customerMap.get(id);
    }

    @Override
    public Customer addCustomer(Customer newCust) {
        Customer savedCust = Customer.builder()
            .id(UUID.randomUUID())
            .customerName(newCust.getCustomerName())
            .version("10")
            .createdDate(LocalDateTime.now())
            .modifiedDate(LocalDateTime.now())
            .build();

        customerMap.put(savedCust.getId(), savedCust);
        return savedCust;
    }

    @Override
    public void updateCustomerById(UUID customerId, Customer cust) {
        Customer existingCustomer = customerMap.get(customerId);
        existingCustomer.setCustomerName(cust.getCustomerName());
        existingCustomer.setVersion(cust.getVersion());
        existingCustomer.setModifiedDate(LocalDateTime.now());
        customerMap.put(customerId, existingCustomer);
    }
}
