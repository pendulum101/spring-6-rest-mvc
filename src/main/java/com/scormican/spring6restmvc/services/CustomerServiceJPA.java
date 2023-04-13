package com.scormican.spring6restmvc.services;

import com.scormican.spring6restmvc.mappers.CustomerMapper;
import com.scormican.spring6restmvc.model.CustomerDTO;
import com.scormican.spring6restmvc.repositories.CustomerRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

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
        return customerMapper.customerToCustomerDto(customerRepository
            .save(customerMapper.customerDtoToCustomer(newCust)));
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO cust) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
        customerRepository.findById(customerId).ifPresentOrElse(customer -> {
                customer.setCustomerName(cust.getCustomerName());
                customer.setModifiedDate(LocalDateTime.now());
                atomicReference.set(Optional.of(customerMapper.customerToCustomerDto(customer)));
            },
            () -> {
                atomicReference.set(Optional.empty());
            });

        return atomicReference.get();
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO cust) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
        customerRepository.findById(customerId).ifPresentOrElse(customer -> {
                if (StringUtils.hasText(cust.getCustomerName())) {
                    customer.setCustomerName(cust.getCustomerName());
                }
                customer.setModifiedDate(LocalDateTime.now());
                atomicReference.set(Optional.of(customerMapper.customerToCustomerDto(customer)));
            },
            () -> {
                atomicReference.set(Optional.empty());
            });

        return atomicReference.get();
    }

    @Override
    public Boolean delCustById(UUID customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }
}
