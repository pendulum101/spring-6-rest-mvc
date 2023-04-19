package com.scormican.spring6restmvc.bootstrap;

import com.scormican.spring6restmvc.entities.Beer;
import com.scormican.spring6restmvc.entities.Customer;
import com.scormican.spring6restmvc.model.BeerCSVRecord;
import com.scormican.spring6restmvc.model.BeerStyle;
import com.scormican.spring6restmvc.repositories.BeerRepository;
import com.scormican.spring6restmvc.repositories.CustomerRepository;
import com.scormican.spring6restmvc.services.BeerCsvService;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;


@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    private final BeerCsvService beerCsvService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCsvData();
        loadCustomerData();
    }

    private void loadCsvData() throws FileNotFoundException {
        if (beerRepository.count() < 10) {
            File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

            List<BeerCSVRecord> recordList = beerCsvService.convertCSV(file);

            recordList.forEach(beerCSVRecord -> {
                BeerStyle beerStyle = switch (beerCSVRecord.getStyle()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                        BeerStyle.ALE;
                    case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                    case "American Porter" -> BeerStyle.PORTER;
                    case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                    case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                    case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                    case "English Pale Ale" -> BeerStyle.PALE_ALE;
                    default -> BeerStyle.PILSNER;
                };
                beerRepository.save(Beer.builder()
                    .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 50))
                    .beerStyle(beerStyle)
                    .price(BigDecimal.TEN)
                    .upc(beerCSVRecord.getRow().toString())
                    .quantityOnHand(beerCSVRecord.getCount())
                    .build());
            });
        }
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
