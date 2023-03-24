package com.vatRates.varateapp.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.vatRates.varateapp.model.CountryVatRate;

import java.io.IOException;

public class CountryVatRateDeserializer extends JsonDeserializer<CountryVatRate> {

    @Override
    public CountryVatRate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String country = node.get("country").asText();
        double standardRate = node.get("standard_rate").asDouble();
        String isoDuplicateOf = node.has("iso_duplicate_of") ? node.get("iso_duplicate_of").asText() : null;
        Double reducedRate = parseDoubleOrNull(node, "reduced_rate");
        Double reducedRateAlt = parseDoubleOrNull(node, "reduced_rate_alt");
        Double superReducedRate = parseDoubleOrNull(node, "super_reduced_rate");
        Double parkingRate = parseDoubleOrNull(node, "parking_rate");

        return new CountryVatRate(isoDuplicateOf, country, standardRate, reducedRate, reducedRateAlt,
                superReducedRate, parkingRate);
    }

    private Double parseDoubleOrNull(JsonNode node, String fieldName) {
        return node.get(fieldName).isDouble() ? node.get(fieldName).asDouble() : null;
    }
}
