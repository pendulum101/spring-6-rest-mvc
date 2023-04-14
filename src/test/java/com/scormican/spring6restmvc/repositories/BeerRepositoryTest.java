package com.scormican.spring6restmvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.scormican.spring6restmvc.entities.Beer;
import com.scormican.spring6restmvc.model.BeerStyle;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
            .beerName("My Beer")
            .beerStyle(BeerStyle.PALE_ALE)
            .upc("12341341")
            .price(new BigDecimal("11.99"))
            .build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

    @Test
    void testSaveBeerNameTooLong() {
        Beer savedBeer = beerRepository.save(Beer.builder()
            .beerName("My Beer012317517350135610123175173501356101231751735013561012317517350135610123175173501356101231751735013561")
            .beerStyle(BeerStyle.PALE_ALE)
            .upc("12341341")
            .price(new BigDecimal("11.99"))
            .build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }
}