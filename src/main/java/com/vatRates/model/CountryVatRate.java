package com.vatRates.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vatRates.util.CountryVatRateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.Objects;

@Data
@AllArgsConstructor
@JsonDeserialize(using = CountryVatRateDeserializer.class)
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryVatRate that = (CountryVatRate) o;
        boolean isoDuplicateOfEqual = Objects.equals(isoDuplicateOf, that.isoDuplicateOf) ||
                (isoDuplicateOf == null && "null".equals(that.isoDuplicateOf)) ||
                (that.isoDuplicateOf == null && "null".equals(isoDuplicateOf));
        return isoDuplicateOfEqual &&
                Objects.equals(country, that.country) &&
                Objects.equals(standardRate, that.standardRate) &&
                Objects.equals(reducedRate, that.reducedRate) &&
                Objects.equals(reducedRateAlt, that.reducedRateAlt) &&
                Objects.equals(superReducedRate, that.superReducedRate) &&
                Objects.equals(parkingRate, that.parkingRate);
    }

}





