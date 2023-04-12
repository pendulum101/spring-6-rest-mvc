package com.scormican.spring6restmvc.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.scormican.spring6restmvc.entities.Beer;
import com.scormican.spring6restmvc.model.BeerDTO;
import com.scormican.spring6restmvc.repositories.BeerRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGetById() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerController.getBeerById(beer.getId());
        assertThat(beerDTO).isNotNull();
    }

    @Test
    void testBeerIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.getBeerById(UUID.randomUUID());
        });

    }

    @Test
    void testListBeer() {
        List<BeerDTO> dtos = beerController.listBeers();
        assertThat(dtos.size()).isEqualTo(2);
    }

    @Transactional
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();
        List<BeerDTO> dtos = beerController.listBeers();
        assertThat(dtos.size()).isEqualTo(0);
    }
}