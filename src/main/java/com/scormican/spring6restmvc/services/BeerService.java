package com.scormican.spring6restmvc.services;

import com.scormican.spring6restmvc.model.Beer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<Beer> listBeers();

    Optional<Beer> getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);

    void updateBeerById(UUID beerId, Beer beer);

    void delById(UUID beerId);

    void updateBeerPatchById(UUID beerId, Beer beer);
}
