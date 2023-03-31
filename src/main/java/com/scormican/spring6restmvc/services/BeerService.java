package com.scormican.spring6restmvc.services;

import com.scormican.spring6restmvc.model.BeerDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<BeerDTO> listBeers();

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    void updateBeerById(UUID beerId, BeerDTO beer);

    void delById(UUID beerId);

    void updateBeerPatchById(UUID beerId, BeerDTO beer);
}
