package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static config.AppConfig.driver;

public class SearchResultsPage {


    // TODO remove elements below

//    @FindBy(xpath = "//*[@id='param579']/option[@value='${typeName}']")
//    private WebElement typeNameDropdownInput;
//
//    @FindBy(xpath = "//*[@id='param571']/option[@value='${producerName}']")
//    private WebElement producerNameInput;
//
//    @FindBy(xpath = "//*[@id='param573']/option[@value='${modelName}']")
//    private WebElement modelNameDropdownInput;
//
//    @FindBy(xpath = "//*[@id='param3018']/option[@value[contains(.,'${generation}')]]")
//    private WebElement generationValue;
//
//    @FindBy(xpath = "//*[@id='param_fuel_type']/div/div/span/span[1]/span/div/li/span")
//    private WebElement fuelTypeButton;
//
//    @FindBy(xpath = "/*[@id='select2-param581-results']")
//    private WebElement fuelTypeList;
//
//    @FindBy(xpath = "//*[@id='param_group_1']/div/div/a/span[1]/span[1]")
//    private WebElement engineAndGearBoxButton;
//
//    @FindBy(xpath = "//*[@id='layer-filter-group1']/div[2]/div")
//    private WebElement engineAndGearBoxModalContent;
//
//    @FindBy(className = "offers list")
//    private WebElement offersList;
//
//    @FindBy(xpath = "//div[contains(@class, 'offers list')]/article")
//    private WebElement allRows;

    int lastPageValue;
    String ALL_CARS_ROWS_SELECTOR_XPATH = "//div[contains(@class, 'offers list')]/article";
    String CAR_TITLE_SELECTOR_CLASS = "offer-item__title";
    String CAR_ID_SELECTOR_ID = "data-ad-id";
    String CAR_SHORT_INFO_SELECTOR_CLASS = "ds-params-block";
    String CAR_PRICE_SELECTOR_CLASS = "ds-price-number";
    String CAR_CITY_SELECTOR_CLASS = "ds-location";
    String CAR_LINK_SELECTOR_CLASS = "data-href";
    String LIST_OF_PAGES_SELECTOR_XPATH = "//*[@id='body-container']/div[2]/div[2]/ul/li[@class='' or @class='active']";
    String PAGE_NUMBER_SELECTOR_XPATH = "//*[@id='body-container']/div[2]/div[2]/ul/li[@class='' or @class='active'][contains(.,'${pageNumber}')]/a";

    @FindBy(xpath = "//*[@id='submit-filters']")
    private WebElement submitFilters;

    @FindBy(className = "cookiesBarClose")
    private WebElement closeCookiesBar;

    public SearchResultsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void initializeSearchResultsPage() {
        submitFilters.click();
        closeCookiesBar.click();
    }

    public void checkLastPageNumber() {
        List<WebElement> listOfPages = driver.findElements(By.xpath(LIST_OF_PAGES_SELECTOR_XPATH));
        if (listOfPages.size() > 0) {
            String lastPage = listOfPages.get(listOfPages.size() - 1).getText();
            lastPageValue = Integer.parseInt(lastPage);
        } else lastPageValue = 0;
    }

    public void scanPageResultsAndAddToList() throws InterruptedException {
        checkLastPageNumber();
        int currentPage = 0;
        for (int i = 0; i <= lastPageValue; i++) {
            currentPage++;
            TimeUnit.SECONDS.sleep(2); // TODO refactor
            List<WebElement> listOfCarRows = driver.findElements(By.xpath(ALL_CARS_ROWS_SELECTOR_XPATH));
            String carTitle, carId, carShortInfo, carPrice, carCity, carLink;
            for (WebElement row : listOfCarRows) {
                carTitle = String.valueOf(row.findElement(By.className(CAR_TITLE_SELECTOR_CLASS)).getText());
                carId = row.getAttribute(CAR_ID_SELECTOR_ID);
                carShortInfo = String.valueOf(row.findElement(By.className(CAR_SHORT_INFO_SELECTOR_CLASS)).getText());
                carPrice = String.valueOf(row.findElement(By.className(CAR_PRICE_SELECTOR_CLASS)).getText());
                carCity = String.valueOf(row.findElement(By.className(CAR_CITY_SELECTOR_CLASS)).getText());
                carLink = row.getAttribute(CAR_LINK_SELECTOR_CLASS);
                Car.carsData.add(new Car(carTitle, carId, carShortInfo, carPrice, carCity, carLink));
                System.out.println("Added new car to file:" + '\n' + carTitle);
            }
            if (currentPage >= lastPageValue) {
                System.out.println("No more offers");
                break;
            } else {
                WebElement newPage = driver.findElement(By.xpath(PAGE_NUMBER_SELECTOR_XPATH.replace("${pageNumber}", String.valueOf(currentPage + 1))));
                newPage.click();
            }
        }
    }
}