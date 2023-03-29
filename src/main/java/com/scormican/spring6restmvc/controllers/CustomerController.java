package com.scormican.spring6restmvc.controllers;

import com.scormican.spring6restmvc.model.Customer;
import com.scormican.spring6restmvc.services.CustomerService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private CustomerService customerService;

    @PutMapping("{customerId}")
    public ResponseEntity updateById(@PathVariable UUID customerId, @RequestBody Customer cust){
        customerService.updateCustomerById(customerId, cust);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customers/" + customerId);
        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity delById(@PathVariable UUID customerId) {
        customerService.delCustById(customerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity handlePost(@RequestBody Customer newCust){
        HttpHeaders headers = new HttpHeaders();
        Customer savedCustomer = customerService.addCustomer(newCust);

        headers.add("Location", "/api/v1/customers" + savedCustomer.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getCustomerList() {
        return customerService.listCustomers();
    }

    @RequestMapping(value = "{custId}", method = RequestMethod.GET )
    public Customer getCustomerById(@PathVariable UUID custId){
        return customerService.getCustomerById(custId);
    }
}
