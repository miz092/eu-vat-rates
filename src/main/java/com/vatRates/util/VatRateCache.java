package com.vatRates.util;


import com.vatRates.model.CountryVatRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class VatRateCache {

    private final Cache cache;

    @Autowired
    public VatRateCache(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("vatRatesCache");
    }

    public Map<String, CountryVatRate> getVatRates() {
        Cache.ValueWrapper valueWrapper = cache.get("apiData");
        if (valueWrapper != null) {
            return (Map<String, CountryVatRate>) valueWrapper.get();
        }
        return null;
    }

    public void putVatRates(Map<String, CountryVatRate> vatRates) {
        cache.put("apiData", vatRates);
    }

    public void clearCache() {
        cache.clear();
    }
}
