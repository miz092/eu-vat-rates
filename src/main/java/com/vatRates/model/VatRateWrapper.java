package com.vatRates.model;

import java.util.Map;

public record VatRateWrapper(String last_updated, String disclaimer, Map<String, CountryVatRate> rates) {
}
