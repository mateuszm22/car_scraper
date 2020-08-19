package excelHelpers;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import model.Car;
import utils.TimeUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ExcelHelper {

    ResourceBundle bundle = ResourceBundle.getBundle("projectprivate");
    XSSFWorkbook workbook;
    Sheet sheet;
    private final String xlsFilePath = bundle.getString("xlsxFilePath");
    private static final String[] headerData = {"Name", "Id", "Short Info", "Price", "City", "Link"};
    private static final List<Car> currentCarsData = new ArrayList<>(Car.carsData);
    private static final List<Car> previousScanCarsData = new ArrayList<>();

    public void openXlsFile() throws IOException {
        workbook = new XSSFWorkbook(new FileInputStream(xlsFilePath));

    }

    public void createNewSheet(){
        sheet = workbook.createSheet(TimeUtils.getActualTime());
    }

    public void prepareHeaderAndStyles() {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.DARK_RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headerData.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headerData[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }

    public CellStyle prepareStyleForHyperLink() {
        CellStyle hyperLinkStyle = workbook.createCellStyle();
        Font hyperLinkFont = workbook.createFont();
        hyperLinkFont.setUnderline(Font.U_SINGLE);
        hyperLinkFont.setColor(IndexedColors.BLUE.getIndex());
        hyperLinkStyle.setFont(hyperLinkFont);
        return hyperLinkStyle;
    }

    public void resizeColumns() {
        for (int i = 0; i < headerData.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public void saveAndCloseOutputFile() throws IOException {
        FileOutputStream fileOut = new FileOutputStream(xlsFilePath);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }

    public void saveCarsDataToExcel() throws Exception {
        openXlsFile();
        //getPreviousScanData();
        createNewSheet();
        prepareHeaderAndStyles();
        int rowNum = 1;
        for (Car car : currentCarsData) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(car.getCarTitle());
            row.createCell(1).setCellValue(car.getCarId());
            row.createCell(2).setCellValue(car.getCarShortInfo());
            row.createCell(3).setCellValue(car.getCarPrice());
            row.createCell(4).setCellValue(car.getCarCity());
            Cell cell = row.createCell(5);
            cell.setCellValue(car.getCarLink());
            cell.setCellStyle(prepareStyleForHyperLink());
            Hyperlink href = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
            href.setAddress(car.getCarLink());
            cell.setHyperlink(href);
        }
        resizeColumns();
        saveAndCloseOutputFile();
    }

  
}
