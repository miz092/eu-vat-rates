package com.vatRates.varateapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryVatRate {



    @JsonProperty("iso_duplicate_of")
    private String isoDuplicateOf;
    @JsonProperty("country")
    private String country;

    @JsonProperty("standard_rate")
    private Double standardRate;

    @JsonProperty("reduced_rate")
    private Double reducedRate;

    @JsonProperty("reduced_rate_alt")
    private Double reducedRateAlt;

    @JsonProperty("super_reduced_rate")
    private Double superReducedRate;

    @JsonProperty("parking_rate")
    private Double parkingRate;
}