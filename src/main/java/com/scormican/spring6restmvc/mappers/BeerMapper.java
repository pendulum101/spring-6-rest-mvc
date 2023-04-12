package com.scormican.spring6restmvc.mappers;

import com.scormican.spring6restmvc.entities.Beer;
import com.scormican.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDTO dto);
    BeerDTO beerToBeerDto(Beer beer);
}
