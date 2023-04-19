package com.scormican.spring6restmvc.bootstrap;

import static org.assertj.core.api.Assertions.assertThat;

import com.scormican.spring6restmvc.repositories.BeerRepository;
import com.scormican.spring6restmvc.repositories.CustomerRepository;
import com.scormican.spring6restmvc.services.BeerCsvService;
import com.scormican.spring6restmvc.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(BeerCsvServiceImpl.class)
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    BeerCsvService beerCsvService;
    BootstrapData bootstrapData;

    @BeforeEach
    void beforeEach() {
        bootstrapData = new BootstrapData(beerRepository, customerRepository, beerCsvService);
    }

    @Test
    void testRun() throws Exception {
        bootstrapData.run(null);

        assertThat(beerRepository.count()).isEqualTo(2412);
        assertThat(customerRepository.count()).isEqualTo(2);
    }
}