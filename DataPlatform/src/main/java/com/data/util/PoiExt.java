package com.data.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiExt {
	
	/**
	 * 创建一个Excel
	 * @Description: 
	 * @author ZHAOLIANG
	 * @date 2015年9月17日
	 * @param title
	 * @param headers
	 * @return
	 */
	public XSSFWorkbook createExcel(String title, String[] headers) {
        // 声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
		XSSFSheet sheet = workbook.createSheet(title);
        // 生成并设置一个样式
		XSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成一个字体
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style.setFont(font);

        // 产生表格标题行
        XSSFRow row = sheet.createRow((int) 0);
        XSSFCell cell = null;
        for (int i = 0; i < headers.length; i++) {  
            cell = row.createCell(i);  
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, headers[i].getBytes().length*2*256);//计算字符串长度以便设置列宽
        }
        return workbook;
    }
	
	/**
	 * 读取文件流，返回WorkBook
	 * @Description: 
	 * @author wanghz
	 * @date 2015年5月7日
	 * @param fileInput
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public Workbook readExcel(InputStream fileInput,String fileName) throws IOException{
		Workbook workbook = null;
		if (fileName.endsWith(".xlsx")) {
			workbook = new XSSFWorkbook(fileInput);
		} else {
			workbook = new HSSFWorkbook(fileInput);//只要不是.xlsx，统一以.xls解析
		}
		return workbook;
	}
	
	public void insertCell(HSSFRow row,int i,Object object){
        if(object==null){  
            row.createCell(i).setCellValue("");  
        }else{  
            row.createCell(i).setCellValue(object.toString());  
        }
    }
	
	public String getCellFormatValue(Cell cell){
		String cellValue = "";
		if(cell!=null){
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				cellValue = String.valueOf(cell.getNumericCellValue());
			case HSSFCell.CELL_TYPE_FORMULA:
				// 判断当前的cell是否为Date
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                	cellValue = cell.getDateCellValue().toString();
                }else{
                	cellValue = String.valueOf(cell.getNumericCellValue());
                }
				break;
			default:
				cellValue = cell.getStringCellValue();
				break;
			}
		}
		return cellValue;
	}
}
