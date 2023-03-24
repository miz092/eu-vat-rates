## EU VAT rate API

- [Introduction](#introduction)
- [Requirements](#requirements)
- [Usage](#usage)

## Introduction

EU VAT Rate is a Spring Boot microservice that fetches VAT rates for EU countries from an external API (https://euvatrates.com/rates.json), caches the
data, and provides methods to retrieve the top three countries with the highest standard VAT rates and the three countries with the lowest reduced VAT
rates.

## Requirements
## Structure
```
+---java
   \---com
       \---vatRates
           |   VatRateApplication.java
           |   
           +---configuration
           |       TemplateConfig.java  //provides template for requests
           |       
           +---controller
           |       VATController.java   // 
           |       
           +---model
           |       CountryVatRate.java
           |       VatRateWrapper.java
           |       
           +---service
           |       VATService.java
           |       
           \---util
                   CacheEvictScheduler.java
                   CountryVatRateDeserializer.java
                   JsonDeserializer.java
                   VatRateCache.java
```

## Usage

Clone: 
```shell
git clone https://github.com/miz092/eu-vat-rates.git
```

Installation:
```shell
 mvn clean install 
```

```shell
  mvn spring-boot:run     
```