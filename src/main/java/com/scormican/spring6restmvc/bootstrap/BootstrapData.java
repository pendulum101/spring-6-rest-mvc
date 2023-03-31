package com.scormican.spring6restmvc.bootstrap;

import com.scormican.spring6restmvc.entities.Beer;
import com.scormican.spring6restmvc.entities.Customer;
import com.scormican.spring6restmvc.model.BeerDTO;
import com.scormican.spring6restmvc.model.BeerStyle;
import com.scormican.spring6restmvc.model.CustomerDTO;
import com.scormican.spring6restmvc.repositories.BeerRepository;
import com.scormican.spring6restmvc.repositories.CustomerRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
    }

    private void loadBeerData() {
        if (beerRepository.count() == 0) {
            Beer one = Beer.builder()
                .beerName("New tangerine")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("125")
                .price(new BigDecimal("5"))
                .quantityOnHand(50)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

            Beer two = Beer.builder()
                .beerName("Uprising")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("987")
                .price(new BigDecimal("7"))
                .quantityOnHand(80)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

            beerRepository.save(one);
            beerRepository.save(two);
        }
    }

    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
            Customer first = Customer.builder()
                .customerName("Barry Scott")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

            Customer second = Customer.builder()
                .customerName("Jared")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

            customerRepository.saveAll(Arrays.asList(first, second));
        }
    }
}
