package com.scormican.spring6restmvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BeerDTO {

    private UUID id;
    @NotBlank
    @NotNull
    private String beerName;
    private Integer version;
    @NotNull
    private BeerStyle beerStyle;
    @NotBlank
    @NotNull
    private String upc;
    private Integer quantityOnHand;
    @NotNull
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
