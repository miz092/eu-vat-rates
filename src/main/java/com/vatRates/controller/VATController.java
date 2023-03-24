package com.vatRates.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vatRates.service.VATService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
