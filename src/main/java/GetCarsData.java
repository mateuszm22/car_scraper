import config.AppConfig;
import pages.SearchResultsPage;

public class GetCarsData {

    public static void main(String[] args) throws Exception {

        AppConfig.startApplication();
        SearchResultsPage searchResultsPage = new SearchResultsPage(AppConfig.getDriver());
        searchResultsPage.initializeSearchResultsPage();
        searchResultsPage.scanAllCars();
        searchResultsPage.saveToExcel();
        AppConfig.getDriver().quit();
    }
}
