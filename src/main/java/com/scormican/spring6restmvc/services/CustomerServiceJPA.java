package com.scormican.spring6restmvc.services;

import com.scormican.spring6restmvc.mappers.CustomerMapper;
import com.scormican.spring6restmvc.model.CustomerDTO;
import com.scormican.spring6restmvc.repositories.BeerRepository;
import com.scormican.spring6restmvc.repositories.CustomerRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final BeerRepository beerRepository;

    @Override
    public List<CustomerDTO> listCustomers() {
        return customerRepository.findAll()
            .stream()
            .map(customerMapper::customerToCustomerDto)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository.findById(id)
            .orElse(null)));
    }

    @Override
    public CustomerDTO addCustomer(CustomerDTO newCust) {
        return null;
    }

    @Override
    public void updateCustomerById(UUID customerId, CustomerDTO cust) {

    }

    @Override
    public void delCustById(UUID customerId) {

    }
}
