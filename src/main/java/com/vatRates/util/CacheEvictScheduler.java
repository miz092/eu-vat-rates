package com.vatRates.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class CacheEvictScheduler {
    private final VatRateCache vatRateCache;

    @Autowired
    public CacheEvictScheduler(VatRateCache vatRateCache) {
        this.vatRateCache = vatRateCache;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void clearCache() {
        System.out.println("Clearing vatRatesCache...");
        vatRateCache.clearCache();
    }

}
