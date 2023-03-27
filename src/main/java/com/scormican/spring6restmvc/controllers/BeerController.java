package com.scormican.spring6restmvc.controllers;

import com.scormican.spring6restmvc.model.Beer;
import com.scormican.spring6restmvc.services.BeerService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
@AllArgsConstructor
@Controller
public class BeerController {
    private final BeerService beerService;

    public Beer getBeerById(UUID id){
        return beerService.getBeerById(id);
    }
}
