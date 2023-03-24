package com.vatRates.varateapp.util;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vatRates.varateapp.model.VatRateWrapper;
import org.springframework.stereotype.Component;

@Component
public class JsonDeserializer {

    public VatRateWrapper deserializedRates(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, VatRateWrapper.class);
    }
}