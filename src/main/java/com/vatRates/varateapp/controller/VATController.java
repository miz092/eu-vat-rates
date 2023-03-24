package com.vatRates.varateapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vatRates.varateapp.model.CountryVatRate;
import com.vatRates.varateapp.service.VATService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public String getTopVats() throws JsonProcessingException {
        return vatService.apiData();
    }

    @GetMapping("/lowestreducedrates")
    public String getLowestReducedVats() throws JsonProcessingException {
        return vatService.apiData();
    }



}
