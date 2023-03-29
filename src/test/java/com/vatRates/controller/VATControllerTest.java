package com.vatRates.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vatRates.VatRateApplication;
import com.vatRates.model.CountryVatRate;
import com.vatRates.service.VATService;
import com.vatRates.util.VatRateCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { VatRateApplication.class })
@AutoConfigureMockMvc
@SpringBootTest
class VATControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VATService vatService;

    @MockBean
    private VatRateCache vatRateCache;

    private List<CountryVatRate> vatRates;

    @BeforeEach
    public void setUp() {
        vatRates = new ArrayList<>();
        vatRates.add(new CountryVatRate(null, "Country1", 20.0, 10.0, null, null, null));
        vatRates.add(new CountryVatRate(null, "Country2", 15.0, 5.0, null, null, null));
    }

    @Test
    public void testGetHighestStandardRates() throws Exception {
        when(vatService.topThreeStandardRates()).thenReturn(vatRates);

        MvcResult result = mockMvc.perform(get("/euvat/topstandardrates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(vatRates.size())))
                .andExpect(jsonPath("$[0].country").value("Country1"))
                .andExpect(jsonPath("$[1].country").value("Country2"))
                .andReturn();

        List<CountryVatRate> returnedVatRates = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<CountryVatRate>>() {});

        assertTrue(returnedVatRates != null && returnedVatRates.containsAll(vatRates) && vatRates.containsAll(returnedVatRates));
    }


    @Test
    public void testGetLowestReducedVats() throws Exception {
        when(vatService.lowestReducedRates()).thenReturn(vatRates);

        MvcResult result = mockMvc.perform(get("/euvat/lowestreducedrates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(vatRates.size())))
                .andExpect(jsonPath("$[0].country").value("Country1"))
                .andExpect(jsonPath("$[1].country").value("Country2"))
                .andReturn();

        List<CountryVatRate> returnedVatRates = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<CountryVatRate>>() {});

        assertTrue(returnedVatRates != null && returnedVatRates.containsAll(vatRates) && vatRates.containsAll(returnedVatRates));
    }
    @Test
    void testGetTopVatsEmptyList() throws Exception {
        when(vatRateCache.getVatRates()).thenReturn(Optional.empty());

        mockMvc.perform(get("/euvat/topstandardrates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testLowestReducedVatsEmptyList() throws Exception {
        when(vatRateCache.getVatRates()).thenReturn(Optional.empty());

        mockMvc.perform(get("/euvat/lowestreducedrates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void topStandardRatesJsonProcessingException() throws Exception {
        when(vatService.topThreeStandardRates()).thenThrow(new JsonProcessingException("Json processing exception occurred.") {});

        mockMvc.perform(get("/euvat/topstandardrates"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
                .andExpect(content().string("Json processing exception occurred."));
    }
    @Test
    public void lowestReducedRatesJsonProcessingException() throws Exception {
        when(vatService.lowestReducedRates()).thenThrow(new JsonProcessingException("Json processing exception occurred.") {});

        mockMvc.perform(get("/euvat/lowestreducedrates"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
                .andExpect(content().string("Json processing exception occurred."));
    }
}