import config.AppConfig;
import excelHelpers.ConvertToExcel;
import pages.SearchResultsPage;

public class GetCarsData {

    public static void main(String[] args) throws Exception {

        SearchResultsPage searchResultsPage = new SearchResultsPage(AppConfig.getDriver());
        ConvertToExcel convertToExcel = new ConvertToExcel();

        AppConfig.startApplication();
        searchResultsPage.initializeSearchResultsPage();
        searchResultsPage.scanAllCars();
        convertToExcel.saveDataToExcel();
        AppConfig.getDriver().quit();
    }
}
