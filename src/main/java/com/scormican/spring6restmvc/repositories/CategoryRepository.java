package com.scormican.spring6restmvc.repositories;


import com.scormican.spring6restmvc.entities.Category;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

}
