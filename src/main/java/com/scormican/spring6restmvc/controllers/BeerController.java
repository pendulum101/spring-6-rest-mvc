package com.scormican.spring6restmvc.controllers;

import com.scormican.spring6restmvc.model.BeerDTO;
import com.scormican.spring6restmvc.services.BeerService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity updateBeerPatchById(@PathVariable UUID beerId, @RequestBody BeerDTO beer) {
        beerService.updateBeerPatchById(beerId, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity delById(@PathVariable UUID beerId) {
        if (!beerService.delById(beerId)) {
            throw new NotFoundException();
        }
        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById(@PathVariable UUID beerId, @Validated @RequestBody BeerDTO beer) {
        if (beerService.updateBeerById(beerId, beer).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(/*headers,*/ HttpStatus.NO_CONTENT);
    }

    //    @RequestMapping(method = RequestMethod.POST)
    @PostMapping(BEER_PATH)
    public ResponseEntity handlePost(@Validated @RequestBody BeerDTO beer) {
        BeerDTO savedBeer = beerService.saveNewBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = BEER_PATH, method = RequestMethod.GET)
    public List<BeerDTO> listBeers() {
        log.info("just checking the log");
        return beerService.listBeers();
    }

    @RequestMapping(value = BEER_PATH_ID, method = RequestMethod.GET)

    public BeerDTO getBeerById(@PathVariable UUID beerId) {
        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }
}
