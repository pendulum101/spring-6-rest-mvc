package com.scormican.spring6restmvc.services;

import com.scormican.spring6restmvc.model.BeerDTO;
import com.scormican.spring6restmvc.model.BeerStyle;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface BeerService {

    Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber,
        Integer pageSize);

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

    Boolean delById(UUID beerId);

    Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer);
}
