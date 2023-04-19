package com.scormican.spring6restmvc.services;

import com.scormican.spring6restmvc.model.BeerCSVRecord;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import static org.assertj.core.api.Assertions.assertThat;
class BeerCsvServiceImplTest {

    BeerCsvService beerCsvService= new BeerCsvServiceImpl();

    @Test
    void convertCSV() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
        List<BeerCSVRecord> records = beerCsvService.convertCSV(file);
        System.out.println(records.size());
        assertThat(records.size()).isGreaterThan(0);
    }
}