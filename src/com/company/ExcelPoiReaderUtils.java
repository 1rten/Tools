package Common;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelPoiReaderUtils {

    /**
     * 读取excel 第1张sheet （xls和xlsx）
     *
     * @param filePath excel路径
     * @return
     */
    public List<Map<String, String>> readExcel(String filePath) {
        Sheet sheet = null;
        Row excelRow = null;
        Row rowTitleRow = null;
        List<Map<String, String>> list = null;
        String cellData = null;
        String cellTitle = null;
        Workbook workBook = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
//        System.out.println(extString);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                workBook = new HSSFWorkbook(inputStream);
            } else if (".xlsx".equals(extString)) {
                workBook = new XSSFWorkbook(inputStream);
            } else {
                workBook = null;
            }
            if (workBook != null) {
                // 用来存放表中数据
                list = new ArrayList<Map<String, String>>();
                // 获取第一个sheet
                sheet = workBook.getSheetAt(0);
                // 获取最大行数
                int rowNum = sheet.getPhysicalNumberOfRows();
//                System.out.println(rowNum);
                // 获取第一行
                rowTitleRow = sheet.getRow(0);
                // 获取最大列数
                int colNum = rowTitleRow.getPhysicalNumberOfCells();
//                System.out.println(colNum);
                for (int i = 1; i < rowNum; i++) {
                    Map<String, String> map = new LinkedHashMap<String, String>();
                    excelRow = sheet.getRow(i);
                    if (excelRow != null) {
                        for (int j = 0; j < colNum; j++) {
                            cellData = (String) getCellFormatValue(excelRow.getCell(j));
                            cellTitle = (String) getCellFormatValue(rowTitleRow.getCell(j));
                            map.put(cellTitle, cellData);
                        }
                    } else {
                        continue;
                    }
                    list.add(map);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取单个单元格数据
     *
     * @param cell
     * @return
     */
    public Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            // 判断cell类型
            switch (cell.getCellType()) {
                case NUMERIC: {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case FORMULA: {
                    // 判断cell是否为日期格式
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // 转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    } else {
                        // 数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }

}

