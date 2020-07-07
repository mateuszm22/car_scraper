package pages;

import converts.ConvertToExcel;
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


    String PAGE_NUMBER_XPATH = "//*[@id='body-container']/div[2]/div[2]/ul/li[@class='' or @class='active'][contains(.,'${pageNumber}')]/a";

    @FindBy(xpath = "//*[@id='submit-filters']")
    private WebElement submitFilters;

    @FindBy(className = "icon-arrow_right")
    private WebElement nextPageArrow;

    @FindBy(className = "cookiesBarClose")
    private WebElement closeCookiesBar;

    @FindBy(className = "om-pager rel")
    private WebElement paginationContainer;


    public SearchResultsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void initializeSearchResultsPage() {
        submitFilters.click();
        closeCookiesBar.click();
    }

    public void scanAllCars() throws InterruptedException {
        List<WebElement> listOfPages = driver.findElements(By.xpath("//*[@id='body-container']/div[2]/div[2]/ul/li[@class='' or @class='active']"));
        int currentPage = 0;
        for (int i = 0; i <= listOfPages.size(); i++) {
            currentPage++;
            TimeUnit.SECONDS.sleep(1); // TODO refactor
            List<WebElement> listOfRows = driver.findElements(By.xpath("//div[contains(@class, 'offers list')]/article"));
            String carTitle, carId, carShortInfo, carPrice, carCity, carLink;
            for (WebElement row : listOfRows) {
                carTitle = String.valueOf(row.findElement(By.className("offer-item__title")).getText());
                carId = row.getAttribute("data-ad-id");
                carShortInfo = String.valueOf(row.findElement(By.className("ds-params-block")).getText());
                carPrice = String.valueOf(row.findElement(By.className("ds-price-number")).getText());
                carCity = String.valueOf(row.findElement(By.className("ds-location")).getText());
                carLink = row.getAttribute("data-href");
                Car.carsData.add(new Car(carTitle, carId, carShortInfo, carPrice, carCity, carLink));
                System.out.println("Added new car to file:" + '\n' + carTitle);
            }
            if (currentPage >= listOfPages.size()) {
                System.out.println("No more offers");
                break;
            } else {
                WebElement newPage = driver.findElement(By.xpath(PAGE_NUMBER_XPATH.replace("${pageNumber}", String.valueOf(currentPage + 1))));
                newPage.click();
            }
        }
    }

    public void saveToExcel() throws Exception { //TODO move to other class
        ConvertToExcel excelConverts = new ConvertToExcel();
        excelConverts.saveDataToExcel();
    }
}