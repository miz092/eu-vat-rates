package com.vatRates.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CacheEvictSchedulerTest {

    @InjectMocks
    private CacheEvictScheduler cacheEvictScheduler;

    @Mock
    private VatRateCache vatRateCache;

    @Test
    void testClearCache() {
        cacheEvictScheduler.clearCache();
        verify(vatRateCache, times(1)).clearCache();
    }
}