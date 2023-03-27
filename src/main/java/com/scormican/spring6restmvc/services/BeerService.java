package com.scormican.spring6restmvc.services;

import com.scormican.spring6restmvc.model.Beer;
import java.util.UUID;

public interface BeerService {
    public Beer getBeerById(UUID id);
}
