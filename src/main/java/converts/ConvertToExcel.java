package converts;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pages.Car;
import utils.TimeUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


public class ConvertToExcel {

    private static final String xlsFilePath = "excels/carsData.xlsx";
    private static final String[] headerData = {"Name", "Id", "Short Info", "Price", "City", "Link"};
    private static final List<Car> carsData =  new ArrayList<>(Car.carsData);

    public void saveDataToExcel() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(xlsFilePath));
        Sheet sheet = workbook.createSheet(TimeUtils.getActualTime());

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.DARK_RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);

        for(int i = 0; i < headerData.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headerData[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create Other rows and cells with cars data
        int rowNum = 1;
        for(Car car: carsData) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(car.getCarTitle());
            row.createCell(1).setCellValue(car.getCarId());
            row.createCell(2).setCellValue(car.getCarShortInfo());
            row.createCell(3).setCellValue(car.getCarPrice());
            row.createCell(4).setCellValue(car.getCarCity());
            row.createCell(5).setCellValue(car.getCarLink());
        }

        // Resize all columns to fit the content size
        for(int i = 0; i < headerData.length; i++) {
            sheet.autoSizeColumn(i);
        }

        FileOutputStream fileOut = new FileOutputStream(xlsFilePath);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }
}
