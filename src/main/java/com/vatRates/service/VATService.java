package com.vatRates.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.vatRates.model.CountryVatRate;
import com.vatRates.util.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class VATService {

    private final JsonDeserializer jsonDeserializer;

    private final RestTemplate restTemplate;
    private static final String uri = "https://euvatrates.com/rates.json";


    @Autowired
    public VATService( RestTemplate restTemplate, JsonDeserializer jsonDeserializer) {
        this.jsonDeserializer = jsonDeserializer;
        this.restTemplate = restTemplate;
    }

    public String apiData() throws JsonProcessingException {

        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        Map<String, CountryVatRate> rates =jsonDeserializer.deserializedRates(response.getBody()).rates();
        System.out.println(rates);
        return response.getBody();
    }


}
