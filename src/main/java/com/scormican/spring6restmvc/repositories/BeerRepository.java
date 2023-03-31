package com.scormican.spring6restmvc.repositories;

import com.scormican.spring6restmvc.entities.Beer;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

}
