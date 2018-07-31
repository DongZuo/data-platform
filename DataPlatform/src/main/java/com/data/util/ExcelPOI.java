package com.data.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @param <T> 应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 * @author ZHAOLIANG
 * @version v1.0
 */
public class ExcelPOI<T> {

	private HSSFWorkbook workbook;

	public void exportExcel(String[] headers, List<Map<String, Object>> dataset,
							OutputStream out) {
		exportExcel("定制化报表导出", headers, dataset, out, "yyyy-MM-dd");
	}


	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 *
	 * @param title   表格标题名
	 * @param headers 表格属性列名数组
	 * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *                javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param out     与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */
	@SuppressWarnings("unchecked")
	public void exportExcel(String title, String[] headers,
							List<Map<String, Object>> dataset, OutputStream out, String pattern) {
		workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
				0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("leno");

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		// 遍历集合数据，产生数据行
		Iterator<T> it = (Iterator<T>) dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			T t = (T) it.next();
			Map<String, Object> map = new HashMap<String, Object>();
			map = (Map<String, Object>) t;
			Set<String> keyset = map.keySet();
			String[] fields = new String[keyset.size()];
			int flag = 0;
			for (String string : keyset) {
				fields[flag] = string;
				flag++;
			}

			for (int i = 0; i < keyset.size(); i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);
				String fieldName = fields[i];
				try {
					Object value = map.get(fieldName);
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					// 其它数据类型都当作字符串简单处理
					textValue = value.toString();
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(
									textValue);
							HSSFFont font3 = workbook.createFont();
							font3.setColor(HSSFColor.BLUE.index);
							richString.applyFont(font3);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文件下载
	 *
	 * @param response
	 * @param filePath 文件路径
	 * @param fileName 文件名称
	 */
	public static void download(HttpServletResponse response, String filePath, String fileName) throws IOException {
		_download(response, filePath, fileName, "xlsx");
	}
	
	public static void _download(HttpServletResponse response, String filePath, String fileName, String ext) throws IOException {
		OutputStream os = null;
		try {
			// path是指欲下载的文件的路径。
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(filePath);
			os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			response.addHeader("Content-Disposition", "attachment;filename="
					+ fileName + "Output." + ext);
			response.addHeader("Content-Length", "" + file.length());

			response.setContentType("application/vnd.ms-excel");
			byte[] mybyte = new byte[8192];
			int len = 0;
			while ((len = fis.read(mybyte)) != -1) {
				os.write(mybyte, 0, len);
			}
			os.close();
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * 文件下载
	 *
	 * @param response
	 * @param filePath 文件路径
	 * @param fileName 文件名称
	 */
	public static void downloadByDataRate(HttpServletResponse response, String filePath, String fileName) throws IOException {
		OutputStream os = null;
		try {
			// path是指欲下载的文件的路径。
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(filePath);
			os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			response.addHeader("Content-Disposition", "attachment;filename="
					+ fileName + "output.xls");
			response.addHeader("Content-Length", "" + file.length());

			response.setContentType("application/vnd.ms-excel");
			byte[] mybyte = new byte[8192];
			int len = 0;
			while ((len = fis.read(mybyte)) != -1) {
				os.write(mybyte, 0, len);
			}
			os.close();
		} catch (IOException e) {
			throw e;
		}
	}

}