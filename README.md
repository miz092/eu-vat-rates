## EU VAT rate API

- [Introduction](#introduction)
- [App structure](#structure)
- [Installation](#installation)
- [API endpoints](#api-endpoints)
## Introduction

EU VAT Rate is a Spring Boot microservice that fetches VAT rates for EU countries from an external API (https://euvatrates.com/rates.json), caches the
data, and provides methods to retrieve the top three countries with the highest standard VAT rates and the three countries with the lowest reduced VAT
rates.

## Structure
```text
+---java
   \---com
       \---vatRates
           |   VatRateApplication.java
           |   
           +---configuration
           |       TemplateConfig.java  //provides template for requests
           |       
           +---controller
           |       VATController.java   //endpoints defined here
           |       
           +---model                    //model classes for serialization and possible future DB use
           |       CountryVatRate.java 
           |       VatRateWrapper.java
           |       
           +---service                  
           |       VATService.java      //service class to handle fetching, caching and filtering data
           |       
           \---util
                   CacheEvictScheduler.java    // class for scheduled cache clearing
                   CountryVatRateDeserializer.java  // class to retrieve double/boolean data from the json
                   JsonDeserializer.java
                   VatRateCache.java      // simple caching implemented here
```

## Installation

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

## API endpoints
Browser: 
[Top standard rates](http://localhost:8080/euvat/topstandardrates)

[Lowest reduced rates](http://localhost:8080/euvat/lowestreducedrates)

# Connect to the API using Postman on 'localhost:8080'

  | GET | /euvat/topstandardrates | To retrieve 3 top standard rates |
  | GET | /euvat/lowestreducedrates | To retrieve 3 lowest reduced rates|

### Author
* [Zoltán Mihályfi](https://github.com/miz092)
