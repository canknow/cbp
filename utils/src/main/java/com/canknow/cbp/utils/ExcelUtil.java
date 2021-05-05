package com.canknow.cbp.utils;

import lombok.experimental.UtilityClass;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanWrapperImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ExcelUtil {
    public Sheet getSheet(InputStream inputStream, int index) throws EncryptedDocumentException, InvalidFormatException, IOException {
        Workbook workbook = WorkbookFactory.create(inputStream);
        return workbook.getSheetAt(index);
    }

    /**
     * Excel读取
     *
     * @param inputStream
     *            文件输入流
     * @param startRow
     *            读取起始行
     * @param fields
     *            字段映射
     * @param tClass
     *            转换对象
     * @return
     * @throws EncryptedDocumentException
     * @throws InvalidFormatException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public <T> List<T> read(InputStream inputStream, int startRow, String[] fields, Class<T> tClass)
            throws EncryptedDocumentException, InvalidFormatException, IOException, InstantiationException,
            IllegalAccessException {
        if(fields == null || fields.length <= 0) {
            return new ArrayList<T>();
        }
        Sheet sheet = getSheet(inputStream, 0);
        return read(sheet, startRow, fields, tClass);
    }

    public <T> List<T> read(Sheet sheet, int startRow, String[] fields, Class<T> tClass) throws InstantiationException, IllegalAccessException {
        int lastRow = sheet.getLastRowNum() + 1;
        List<T> result = new ArrayList<T>();

        for (int i = startRow; i < lastRow; i++) {
            Row row = sheet.getRow(i);

            if(row == null) {
                continue;
            }
            T newInstance = tClass.newInstance();

            for (int j = 0; j < fields.length; j++) {
                BeanWrapperImpl instance = new BeanWrapperImpl(newInstance);

                try {
                    instance.setPropertyValue(fields[j], getCellValue(row.getCell(j)));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            result.add(newInstance);
        }
        return result;

    }

    @SuppressWarnings("deprecation")
    public Object getCellValue(Cell cell) {
        if(cell == null) {
            return "";
        }
        String cellValue = "";

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                }
                else {
                    double value = cell.getNumericCellValue();
                    int intValue = (int) value;
                    cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
                }
                break;
            case Cell.CELL_TYPE_STRING:
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK:
            case Cell.CELL_TYPE_ERROR:
                cellValue = "";
                break;
            default:
                cellValue = cell.toString().trim();
                break;
        }
        return cellValue.trim();
    }
}