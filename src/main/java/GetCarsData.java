import config.AppConfig;
import excelHelpers.ExcelHelper;
import pages.SearchResultsPage;

public class GetCarsData {

    public static void main(String[] args) throws Exception {
        AppConfig.startApplication();
        SearchResultsPage searchResultsPage = new SearchResultsPage(AppConfig.getDriver());
        searchResultsPage.initializeSearchResultsPage();
        searchResultsPage.scanPageResultsAndAddToList();
        ExcelHelper excelHelper = new ExcelHelper();
        excelHelper.saveCarsDataToExcel();
        AppConfig.getDriver().quit();
       // SendEmailUtils sendEmailUtils = new SendEmailUtils();
      //  sendEmailUtils.sendEmail();
    }
}
