package com.scormican.spring6restmvc.services;

import com.scormican.spring6restmvc.model.Beer;
import java.util.List;
import java.util.UUID;

public interface BeerService {

    List<Beer> listBeers();

    public Beer getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);

    void updateBeerById(UUID beerId, Beer beer);
}
