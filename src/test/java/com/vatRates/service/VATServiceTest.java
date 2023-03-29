package com.vatRates.service;

import com.vatRates.model.CountryVatRate;
import com.vatRates.model.VatRateWrapper;
import com.vatRates.util.JsonDeserializer;
import com.vatRates.util.VatRateCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VATServiceTest {

    @InjectMocks
    private VATService vatService;

    @Mock
    private JsonDeserializer jsonDeserializer;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private VatRateCache vatRateCache;

    private Map<String, CountryVatRate> expectedRates;

    @BeforeEach
    public void setUp() {
        expectedRates = new HashMap<>();
        expectedRates.put("country1", new CountryVatRate(null, "Country1", 20.0, 10.0, null, null, null));
        expectedRates.put("country2", new CountryVatRate(null, "Country2", 15.0, 5.0, null, null, null));
        expectedRates.put("country3", new CountryVatRate(null, "Country3", 10.0, 3.0, null, null, null));
        expectedRates.put("country4", new CountryVatRate(null, "Country4", 7.0, 2.0, null, null, null));

    }

    @Test
    public void testApiData() throws Exception {
        String json = "{\"last_updated\":\"2023-03-10\",\"disclaimer\":\"some text\",\"rates\": {}}";


        when(restTemplate.getForEntity((String) isNull(), eq(String.class))).thenReturn(ResponseEntity.ok(json));
        when(jsonDeserializer.deserializedRates(json))
                .thenReturn(new VatRateWrapper("2023-03-10", "some text", expectedRates));
        when(vatRateCache.getVatRates()).thenReturn(Optional.empty());

        Map<String, CountryVatRate> rates = vatService.apiData();

        assertEquals(expectedRates, rates);
        verify(vatRateCache).putVatRates(expectedRates);
    }




    @Test
    public void testEmptyApiData() throws Exception {
        String json = "{\"last_updated\":\"2023-03-10\",\"disclaimer\":\"some text\",\"rates\": {}}";
        Map<String, CountryVatRate> emptyRates = new HashMap<>();

        when(restTemplate.getForEntity((String) isNull(), eq(String.class))).thenReturn(ResponseEntity.ok(json));
        when(jsonDeserializer.deserializedRates(json))
                .thenReturn(new VatRateWrapper("2023-03-10", "some text", emptyRates));
        when(vatRateCache.getVatRates()).thenReturn(Optional.empty());

        Map<String, CountryVatRate> rates = vatService.apiData();

        assertEquals(emptyRates, rates);
        verify(vatRateCache).putVatRates(emptyRates);
    }

    @Test
    public void testApiData_ApiIsDown() {
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenThrow(new RestClientException("API is down"));

        assertThrows(Exception.class, () -> {
            vatService.apiData();
        });
    }

    @Test
    public void testTopThreeStandardRates() throws Exception {
        when(restTemplate.getForEntity((String) isNull(), eq(String.class))).thenReturn(ResponseEntity.ok("{\"rates\":{}}"));
        when(jsonDeserializer.deserializedRates(any())).thenReturn(new VatRateWrapper("2023-03-10", "some text", expectedRates));

        List<CountryVatRate> topThree = vatService.topThreeStandardRates();

        assertEquals(3, topThree.size());

        List<String> countryNames = topThree.stream().map(CountryVatRate::getCountry).toList();
        assertTrue(countryNames.contains("Country1"));
        assertTrue(countryNames.contains("Country2"));
        assertTrue(countryNames.contains("Country3"));

        assertEquals(20.0, topThree.get(0).getStandardRate());
        assertEquals(15.0, topThree.get(1).getStandardRate());
        assertEquals(10.0, topThree.get(2).getStandardRate());
    }

    @Test
    public void testLowestReducedRates() throws Exception {
        when(restTemplate.getForEntity((String) isNull(), eq(String.class))).thenReturn(ResponseEntity.ok("{\"rates\":{}}"));
        when(jsonDeserializer.deserializedRates(any())).thenReturn(new VatRateWrapper("2023-03-10", "some text", expectedRates));


        List<CountryVatRate> lowestThree = vatService.lowestReducedRates();

        assertEquals(3, lowestThree.size());

        List<String> countryNames = lowestThree.stream().map(CountryVatRate::getCountry).toList();
        assertTrue(countryNames.contains("Country2"));
        assertTrue(countryNames.contains("Country3"));
        assertTrue(countryNames.contains("Country4"));
        assertEquals(2.0, lowestThree.get(0).getReducedRate());
        assertEquals(3.0, lowestThree.get(1).getReducedRate());
        assertEquals(5.0, lowestThree.get(2).getReducedRate());
    }

    @Test
    void shouldUseCachewhenDataIsCached() throws Exception {

        Map<String, CountryVatRate> cachedRates = new HashMap<>();
        cachedRates.put("test",new CountryVatRate(null, "Country1", 7.0, 2.0, null, null, null));

        when(vatRateCache.getVatRates()).thenReturn(Optional.of(cachedRates));


        vatService.apiData();

        verify(vatRateCache, times(1)).getVatRates();
        verify(restTemplate, never()).getForEntity(any(String.class), any(Class.class));
        verify(jsonDeserializer, never()).deserializedRates(any(String.class));
        verify(vatRateCache, never()).putVatRates(any());
    }

    @Test
    void shouldFetchNewDatawhenDataNotCached() throws Exception {

        String jsonResponse = "{\"rates\": {}}";
        ResponseEntity<String> responseEntity = ResponseEntity.ok(jsonResponse);

        when(vatRateCache.getVatRates()).thenReturn(Optional.empty());
        when(restTemplate.getForEntity((String) isNull(), eq(String.class))).thenReturn(responseEntity);
        when(jsonDeserializer.deserializedRates(jsonResponse)).thenReturn(
                new VatRateWrapper("2023-03-10", "some text", expectedRates));


        vatService.apiData();

        verify(vatRateCache, times(1)).getVatRates();
        verify(restTemplate, times(1)).getForEntity((String) isNull(), eq(String.class));
        verify(jsonDeserializer, times(1)).deserializedRates(jsonResponse);
        verify(vatRateCache, times(1)).putVatRates(expectedRates);
    }

}