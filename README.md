# Scrape OtoMoto Data

## Installation

1. Clone repo,

2. Use [maven](http://maven.apache.org/) to install all  dependencies:

```bash
mvn clean install
```

## Usage

* Install proper [chromedriver](https://chromedriver.chromium.org/downloads) according to the your chrome version (put this driver to: src/main/java/utils/).
* Configure base.url in project.properties
* Firstly fill the data in page and paste link to .properties file
* Use xlsx viewer to check the data

## Stack

* Selenium Web Driver
* Java
* Apache Poi

