package com.vatRates.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.vatRates.model.ApiDataEmptyException;
import com.vatRates.model.CountryVatRate;
import com.vatRates.util.JsonDeserializer;
import com.vatRates.util.VatRateCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VATService {

    private final JsonDeserializer jsonDeserializer;

    private final RestTemplate restTemplate;
    private final VatRateCache vatRateCache;
    @Value("${euvat.api.uri}")
    private String uri;

    @Autowired
    public VATService(JsonDeserializer jsonDeserializer, RestTemplate restTemplate, VatRateCache vatRateCache) {
        this.jsonDeserializer = jsonDeserializer;
        this.restTemplate = restTemplate;
        this.vatRateCache = vatRateCache;
    }


    public Map<String, CountryVatRate> apiData() throws JsonProcessingException {

        Optional<Map<String, CountryVatRate>> ratesOptional = vatRateCache.getVatRates();
        if (ratesOptional.isPresent()) {
            return ratesOptional.get();
        }

        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        Map<String, CountryVatRate> rates = jsonDeserializer.deserializedRates(response.getBody()).rates();
        vatRateCache.putVatRates(rates);
        return rates;
    }



    public List<CountryVatRate> topThreeStandardRates() throws JsonProcessingException {
        Map<String, CountryVatRate> rates = apiData();

        return rates
                .values()
                .stream()
                .filter(countryVatRate -> countryVatRate.getIsoDuplicateOf() == null)
                .sorted(Comparator.comparingDouble(CountryVatRate::getStandardRate).reversed())
                .limit(3)
                .collect(Collectors.toList());

    }

    public List<CountryVatRate> lowestReducedRates() throws JsonProcessingException {

        Map<String, CountryVatRate> rates = apiData();

        return rates
                .values()
                .stream()
                .filter(countryVatRate -> countryVatRate.getIsoDuplicateOf() == null)
                .filter(countryVatRate -> countryVatRate.getReducedRate() != null)
                .sorted(Comparator.comparingDouble(CountryVatRate::getReducedRate))
                .limit(3)
                .collect(Collectors.toList());
    }


}
