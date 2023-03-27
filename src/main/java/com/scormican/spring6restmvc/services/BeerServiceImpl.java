package com.scormican.spring6restmvc.services;

import com.scormican.spring6restmvc.model.Beer;
import com.scormican.spring6restmvc.model.BeerStyle;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    private Map<UUID, Beer> beerMap;

    public BeerServiceImpl(){
        this.beerMap = new HashMap<>();

        Beer one=Beer.builder()
            .id(UUID.randomUUID())
            .version(1)
            .beerName("tangerine")
            .beerStyle(BeerStyle.PALE_ALE)
            .upc("125")
            .price(new BigDecimal("5"))
            .quantityOnHand(50)
            .createdDate(LocalDateTime.now())
            .modifiedDate(LocalDateTime.now())
            .build();

        Beer two = Beer.builder()
            .id(UUID.randomUUID())
            .version(1)
            .beerName("rising")
            .beerStyle(BeerStyle.PALE_ALE)
            .upc("987")
            .price(new BigDecimal("7"))
            .quantityOnHand(80)
            .createdDate(LocalDateTime.now())
            .modifiedDate(LocalDateTime.now())
            .build();

        beerMap.put(one.getId(), one);
        beerMap.put(two.getId(), two);
    }
    @Override
    public Beer getBeerById(UUID id){

        log.debug("logging in the beerserviceimpl");
        return Beer.builder()
            .id(id)
            .version(1)
            .beerName("roboto")
            .beerStyle(BeerStyle.PALE_ALE)
            .upc("12345")
            .price(new BigDecimal("5"))
            .quantityOnHand(50)
            .createdDate(LocalDateTime.now())
            .modifiedDate(LocalDateTime.now())
            .build();
    }
}
