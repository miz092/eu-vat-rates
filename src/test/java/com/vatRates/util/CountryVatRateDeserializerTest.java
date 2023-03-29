package com.vatRates.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.vatRates.model.CountryVatRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class CountryVatRateDeserializerTest {

    private ObjectMapper objectMapper;
    private CountryVatRateDeserializer deserializer;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        deserializer = new CountryVatRateDeserializer();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(CountryVatRate.class, deserializer);
        objectMapper.registerModule(module);
    }

    @Test
    void testDeserialize() throws JsonProcessingException {
        String json = "{\"country\":\"Country1\",\"standard_rate\":20.0,\"reduced_rate\":10.0,\"reduced_rate_alt\":5.0," +
                "\"super_reduced_rate\":null,\"parking_rate\":null,\"iso_duplicate_of\":null}";

        CountryVatRate expected = new CountryVatRate(null, "Country1", 20.0, 10.0, 5.0, null, null);
        CountryVatRate actual = objectMapper.readValue(json, CountryVatRate.class);
        assertEquals(expected, actual);
    }

    @Test
    void testParseDoubleOrNull() throws JsonProcessingException {
        String json = "{\"country\":\"Country1\",\"standard_rate\":20.0,\"reduced_rate\":null,\"reduced_rate_alt\":5.0," +
                "\"super_reduced_rate\":null,\"parking_rate\":null,\"iso_duplicate_of\":null}";

        JsonNode node = objectMapper.readTree(json);

        Double result = deserializer.parseDoubleOrNull(node, "reduced_rate");

        assertNull(result);
    }
}