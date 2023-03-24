## EU VAT rate API

### Table of contents

- [Introduction](#introduction)
- [App structure](#structure)
- [Installation](#installation)
- [API endpoints](#api-endpoints)
  - [Swagger UI](#swagger-ui)
  - [From browser](#from-browser)
  - [Postman](#postman)
- [Author](#author)
- [Contact](#contact)




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

Cloning:

```shell
git clone https://github.com/miz092/eu-vat-rates.git
```

Go to the root of the file:
```shell
cd eu-vat-rates
```
Installation:

```shell
mvn clean install 
```

```shell
mvn spring-boot:run     
```

## API endpoints

While the program runs, we can test/access the given endpoints in several ways:  

### From browser

[Top standard rates](http://localhost:8080/euvat/topstandardrates)

[The 3 lowest reduced rates](http://localhost:8080/euvat/lowestreducedrates)

### Swagger UI

This link only works when the app is running: 

[SwaggerUI](http://localhost:8080/swagger-ui/index.html)

### Postman

#### Connect to the API using Postman (or any other API platform) on localhost:8080

Send a get request with the endpoints detailed below: 

#### | GET | /euvat/topstandardrates | To retrieve 3 top standard rates |

#### | GET | /euvat/lowestreducedrates | To retrieve 3 lowest reduced rates|

## Technologies, libraries used

#### Java 17
#### Maven
#### Spring Boot 
#### Spring Boot Starter Web
#### Spring Boot Starter Cache
#### Project Lombok



### Author

* [Zoltán Mihályfi](https://github.com/miz092)

### Contact

* [LinkedIn](https://www.linkedin.com/in/mi-zo/)
* Email: mihalyfi.zoltan@gmail.com

