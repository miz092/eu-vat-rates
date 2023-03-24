package com.vatRates.varateapp.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class VATService {

    private final RestTemplate restTemplate;
    private static final String uri = "https://euvatrates.com/rates.json";


    @Autowired
    public VATService( RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String apiData() {

        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        return response.getBody();
    }


}
