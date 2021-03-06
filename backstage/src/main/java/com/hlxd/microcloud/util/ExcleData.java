package com.hlxd.microcloud.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/***
 * -读取excle文件
 * @version 1.0
 * @author SmallOath
 * @date 2019年12月11日
 */
public class ExcleData {
	
	/***
	 * -导出模板
	 * @param headers
	 * @param fileName
	 * @param response
	 * @throws IOException
	 */
	public void export(String[] headers, String fileName, HttpServletResponse response) throws IOException {
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("导入数据");
        HSSFRow row = sheet.createRow(0);
        // 设置单元格格式为文本格式
        HSSFCellStyle textStyle = workbook.createCellStyle();
        HSSFDataFormat format = workbook.createDataFormat();
        textStyle.setDataFormat(format.getFormat("@"));
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            //设置单元格格式为"文本"
            sheet.setColumnWidth(0, 2000);  
            sheet.setDefaultColumnStyle(i, textStyle);
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
	}
	
	/***
	 * -解析excle文件
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public List<String[]> getExcelData(MultipartFile file) throws IOException{
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<String[]>();
        if(workbook != null){
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    continue; 
                }
                //获得当前sheet的开始行
                int firstRowNum  = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行的所有行
                for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if(row == null){
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    int lastCellNum = row.getLastCellNum();
                    String[] cells = new String[row.getLastCellNum()];
                    //循环当前行
                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = cell.toString();
                    }
                    list.add(cells);
                }
            }
        }
        return list;
    }
	
	public Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith("xls")){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith("xlsx")){
                //2007 及2007以上
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return workbook;
    }
	
	/***
	 * -判断输入字符串是否为科学计数法
	 * @param input
	 * @return
	 */
	public String isENum(String input) {
		Pattern pattern = Pattern.compile("(-?\\d+\\.?\\d*)[Ee]{1}[\\+-]?[0-9]*");
		DecimalFormat ds = new DecimalFormat("0");
		if (pattern.matcher(input).matches()) {
            String sPhone = ds.format(Double.parseDouble(input)).trim();
            return sPhone;
        }
        return input;
    }
}
