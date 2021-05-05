package com.canknow.cbp.webCommon.adapter.inbound.web.export;

import com.canknow.cbp.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ExportHelper {
    public void export (HttpServletRequest request, HttpServletResponse response, String title, List<?> items, String fileName, Class<?> clz) throws IOException, IllegalAccessException {
        HSSFWorkbook workbook = new HSSFWorkbook ();
        HSSFSheet sheet = workbook.createSheet(fileName);
        sheet.autoSizeColumn(1);

        //根据类类型信息获取导出的excel对应的标题和列宽 key-列号，value-标题和列宽
        HashMap<Integer, ColumnDefine> columnDefineMap= getColumnDefineMap(clz);

        columnDefineMap.forEach((columnIndex, columnDefine) -> {
            if (columnDefine.getWidth() > 0) {
                sheet.setColumnWidth(columnIndex, columnDefine.getWidth() * 256);
            }
        });
        int rowNum = 0;

        HSSFRow headRow = sheet.createRow(rowNum);
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, columnDefineMap.size() - 1);
        sheet.addMergedRegion(cellRangeAddress);
        Row headerRow = sheet.createRow(0);
        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        Cell headerCell = headerRow.createCell(0);
        // 设置单元格内容 标题
        headerCell.setCellValue(title);
        headerCell.setCellStyle(getHeadStyle(workbook));
        setRegionStyle(sheet, cellRangeAddress, getHeadStyle(workbook));
        rowNum++;

        HSSFRow titleRow = sheet.createRow(rowNum);
        HSSFCellStyle titleCellStyle=getCellStyle(workbook,11, true, HSSFColor.BLACK.index);

        columnDefineMap.forEach((columnIndex, columnDefine) -> {
            HSSFCell titleCell = titleRow.createCell(columnIndex);
            titleCell.setCellValue(columnDefine.getTitle());
            titleCell.setCellStyle(titleCellStyle);
        });

        HSSFCellStyle dataStyle = getCellStyle(workbook,11,false,HSSFColor.BLACK.index);

        rowNum++;

        for(Object item: items){
            HSSFRow row=sheet.createRow(rowNum++);
            HashMap<Integer,String> orderValueMap = getValueMap(item);
            orderValueMap.forEach((k,v) ->{
                        HSSFCell cell = row.createCell(k);
                        cell.setCellValue(v);
                        cell.setCellStyle(dataStyle);
                    }
            );
        }

        String downFileName = fileName + ".xls";
        downFileName = URLEncoder.encode(downFileName, "UTF-8");
        response.setContentType("application/vnd.ms-excel; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; " + downFileName);

        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
        workbook.close();
    }

    public void setRegionStyle(Sheet sheet, CellRangeAddress region, CellStyle cs) {
        for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {
            Row row = CellUtil.getRow(i, sheet);

            for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
                Cell cell = CellUtil.getCell(row, (short) j);
                cell.setCellStyle(cs);
            }
        }
    }

    public CellStyle getHeadStyle(Workbook workbook) {
        CellStyle cellStyle;
        // 设置头部样式
        cellStyle = workbook.createCellStyle();
        // 设置字体大小 位置
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        Font font = workbook.createFont();
        //设置字体
        font.setFontName("微软雅黑");
        //字体颜色
        font.setColor(HSSFColor.BLACK.index);// HSSFColor.VIOLET.index
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体增粗
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);//背景白色
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setFont(font);
        return cellStyle;
    }
    private HashMap<Integer, ColumnDefine> getColumnDefineMap(Class<?> clz) {
        HashMap<Integer, ColumnDefine> columnDefineHashMap=new HashMap<>();

        Field[] declaredFields = clz.getDeclaredFields();
        for(Field field:declaredFields) {
            field.setAccessible(true);

            if(field.isAnnotationPresent(ExcelColumn.class)) {
                Integer order=field.getAnnotation(ExcelColumn.class).order();
                String title=field.getAnnotation(ExcelColumn.class).title();
                Integer column=field.getAnnotation(ExcelColumn.class).width();

                ColumnDefine columnDefine=new ColumnDefine(title, column);
                columnDefineHashMap.put(order,columnDefine);
            }
        }

        return columnDefineHashMap;

    }

    public HSSFCellStyle getCellStyle(HSSFWorkbook workbook,int fontSize,boolean isBold, short color){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中

        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);

        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) fontSize);//字号
        font.setColor(color);//颜色
        font.setFontName("微软雅黑");//字体

        if(isBold){
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //字体加粗
        }

        style.setWrapText(true);
        style.setFont(font);

        return style;
    }

    private HashMap<Integer, String> getValueMap(Object object) throws IllegalAccessException {
        HashMap<Integer, String> result=new HashMap<>();

        Class<?> clz=object.getClass();
        Field[] declaredFields = clz.getDeclaredFields();

        for(Field field:declaredFields) {
            field.setAccessible(true);

            if(field.isAnnotationPresent(ExcelColumn.class)) {
                Integer order=field.getAnnotation(ExcelColumn.class).order();
                String value = "";

                Object valueObject=field.get(object);

                if(valueObject!=null) {
                    //日期格式进行特殊处理
                    if(field.getType() == LocalDateTime.class) {
                        String pattern = field.getAnnotation(ExcelColumn.class).pattern();

                        if(StringUtils.isEmpty(pattern)){
                            pattern = "yyyy-MM-dd HH:mm:ss";
                        }
                        value = DateUtils.format((LocalDateTime)valueObject, pattern);
                    }
                    else {
                        value = valueObject.toString(); //其他格式调用toString方法，这里枚举就需要定义自己的toString方法
                    }
                }
                result.put(order, value);
            }
        }
        return result;
    }
}
