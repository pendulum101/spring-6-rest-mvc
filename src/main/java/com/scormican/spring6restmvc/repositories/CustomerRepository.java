package com.scormican.spring6restmvc.repositories;

import com.scormican.spring6restmvc.entities.Customer;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

}
