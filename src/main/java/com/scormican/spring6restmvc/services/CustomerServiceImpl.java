package com.scormican.spring6restmvc.services;

import com.scormican.spring6restmvc.model.CustomerDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();
        CustomerDTO first = CustomerDTO.builder()
            .id(UUID.randomUUID())
            .customerName("Barry Scott")
            .version(1)
            .createdDate(LocalDateTime.now())
            .modifiedDate(LocalDateTime.now())
            .build();

        CustomerDTO second = CustomerDTO.builder()
            .id(UUID.randomUUID())
            .customerName("Jared")
            .version(2)
            .createdDate(LocalDateTime.now())
            .modifiedDate(LocalDateTime.now())
            .build();
        this.customerMap.put(first.getId(), first);
        this.customerMap.put(second.getId(), second);
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.of(customerMap.get(id));
    }

    @Override
    public CustomerDTO addCustomer(CustomerDTO newCust) {
        CustomerDTO savedCust = CustomerDTO.builder()
            .id(UUID.randomUUID())
            .customerName(newCust.getCustomerName())
            .version(10)
            .createdDate(LocalDateTime.now())
            .modifiedDate(LocalDateTime.now())
            .build();

        customerMap.put(savedCust.getId(), savedCust);
        return savedCust;
    }

    @Override
    public void updateCustomerById(UUID customerId, CustomerDTO cust) {
        CustomerDTO existingCustomer = customerMap.get(customerId);
        existingCustomer.setCustomerName(cust.getCustomerName());
        existingCustomer.setVersion(cust.getVersion());
        existingCustomer.setModifiedDate(LocalDateTime.now());
        customerMap.put(customerId, existingCustomer);
    }

    @Override
    public void delCustById(UUID customerId) {
        customerMap.remove(customerId);
    }
}
