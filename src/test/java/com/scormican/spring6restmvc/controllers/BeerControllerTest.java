package com.scormican.spring6restmvc.controllers;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scormican.spring6restmvc.model.BeerDTO;
import com.scormican.spring6restmvc.services.BeerService;
import com.scormican.spring6restmvc.services.BeerServiceImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;
    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;
    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void testPatchBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);
        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name");

        mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(beerMap))).andExpect(status().isNoContent());

        verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());

    }

    @Test
    void testDeleteBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);

        given(beerService.delById(any())).willReturn(true);

        mockMvc.perform(delete(BeerController.BEER_PATH_ID, beer.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(beerService).delById(uuidArgumentCaptor.capture());
        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());

    }

    @Test
    void testUpdateBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);

        given(beerService.updateBeerById(any(), any())).willReturn(Optional.of(beer));

        mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getId()).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(beer)))
            .andExpect(status().isNoContent());

        verify(beerService).updateBeerById(any(UUID.class), any(BeerDTO.class));
    }

    @Test
    void testCreateNewBeer() throws Exception {
        BeerDTO testBeer = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);
        testBeer.setId(null);
        testBeer.setVersion(null);

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers(null, null, false, 1,
            25).getContent().get(1));

        mockMvc.perform(post("/api/v1/beer").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBeer))).andExpect(status().isCreated())
            .andExpect(header().exists("Location"));
    }

    @Test
    void testCreateNewBeerEmptyName() throws Exception {
        BeerDTO testBeer = BeerDTO.builder().build();

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers(null, null, false, 1,
            25).getContent().get(1));

        MvcResult mvcResult = mockMvc.perform(
                post("/api/v1/beer").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(testBeer)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.length()", is(6))).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testUpdateBeerExceptions() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);
        beer.setBeerName("");

        given(beerService.updateBeerById(any(), any())).willReturn(Optional.of(beer));

        mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getId()).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(beer)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.length()", is(1))).andReturn();
    }

    @Test
    void testGetBeerIdNotFound() throws Exception {
        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());
        mockMvc.perform(get(BeerController.BEER_PATH_ID, UUID.randomUUID()))
            .andExpect(status().isNotFound());
    }

    @Test
    void testGetBeerById() throws Exception {
        BeerDTO testBeer = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);

        given(beerService.getBeerById(testBeer.getId())).willReturn(Optional.of(testBeer));

        mockMvc.perform(get(BeerController.BEER_PATH_ID, testBeer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
            .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
    }

    @Test
    void testListBeers() throws Exception {

        given(beerService.listBeers(any(), any(), any(), any(), any()))
            .willReturn(beerServiceImpl.listBeers(null, null, false,
            1, 2));

        mockMvc.perform(get(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.length()", is(2)));
    }
}