package com.vatRates.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vatRates.model.CountryVatRate;
import com.vatRates.service.VATService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("euvat")
public class VATController {

    private final VATService vatService;
    @Autowired
    public VATController(VATService vatService) {
        this.vatService = vatService;
    }

    @GetMapping("/topstandardrates")
    public  List<CountryVatRate> getTopVats() throws JsonProcessingException {
        return vatService.topThreeStandardRates();
    }

    @GetMapping("/lowestreducedrates")
    public List<CountryVatRate> getLowestReducedVats() throws JsonProcessingException {
        return vatService.lowestReducedRates();
    }



}
