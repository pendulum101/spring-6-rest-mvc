package com.scormican.spring6restmvc.mappers;

import com.scormican.spring6restmvc.entities.Customer;
import com.scormican.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

/**
 * Created by jt, Spring Framework Guru.
 */
@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);

}
