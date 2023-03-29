package com.vatRates.util;

import com.vatRates.model.CountryVatRate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VatRateCacheTest {

    @MockBean
    private CacheManager cacheManager;

    private Cache cache;
    @Autowired
    private VatRateCache vatRateCache;

    private Map<String, CountryVatRate> vatRates;


//

    @BeforeEach
    public void resetCacheMock() {
        vatRates = new HashMap<>();
        vatRates.put("Country1", new CountryVatRate(null, "Country1", 20.0,
                10.0, null, null, null));
        vatRates.put("Country2", new CountryVatRate(null, "Country2", 15.0,
                5.0, null, null, null));
        cache = mock(Cache.class);

        when(cacheManager.getCache("vatRatesCache")).thenReturn(cache);

        Cache.ValueWrapper valueWrapper = mock(Cache.ValueWrapper.class);
        when(valueWrapper.get()).thenReturn(vatRates);
        when(cache.get("apiData")).thenReturn(valueWrapper);

        vatRateCache = new VatRateCache(cacheManager);


        doAnswer((Answer<Void>) invocation -> {
            when(cache.get("apiData")).thenReturn(null);
            return null;
        }).when(cache).clear();
    }

    @Test
    public void testGetVatRates() {

        Optional<Map<String, CountryVatRate>> result = vatRateCache.getVatRates();

        assertTrue(result.isPresent());
        assertEquals(vatRates, result.get());
    }

    @Test
    public void testPutVatRates() {


        Map<String, CountryVatRate> newVatRates = new HashMap<>();
        newVatRates.put("Country3", new CountryVatRate(null, "Country3", 18.0,
                9.0, null, null, null));

        vatRateCache.putVatRates(newVatRates);

        verify(cache).put("apiData", newVatRates);

    }

    @Test
    public void testClearCache() {


        vatRateCache.clearCache();

        Optional<Map<String, CountryVatRate>> result = vatRateCache.getVatRates();

        assertTrue(result.isEmpty());
    }
}

