/**
 * Created by macos on 2018/8/9.
 */

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class ExcelUtil {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public ExcelUtil() {

    }

    private static Workbook getWorkbook(InputStream in, File file) {
        Workbook workbook = null;
        try {
            if (file.getName().endsWith(EXCEL_XLS)) {  //Excel 2003
                workbook = new HSSFWorkbook(in);
            } else if (file.getName().endsWith(EXCEL_XLSX)) {  // Excel 2007/2010
                workbook = new XSSFWorkbook(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

    private static void checkExcelVaild(File file) throws Exception {
        if (!file.exists()) {
            throw new Exception("文件不存在！");
        }
        if (!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))) {
            throw new Exception("不是Excel类型文件");
        }

    }

    public List loadExcel(String fileName, String sheetName, int rowNum) throws Exception {
        File file = new File(fileName);
        checkExcelVaild(file);
        InputStream inputStream = new FileInputStream(file);
        Workbook workbook = ExcelUtil.getWorkbook(inputStream, file);
        Sheet sheet = workbook.getSheet(sheetName);
        int lastRowNum = sheet.getLastRowNum();
//        System.out.println(lastRowNum);
        List<Map> list = new ArrayList<Map>();
        List<String> list_temp = new ArrayList<String>();
        int count = 0;
        for (Row row : sheet) {
            int lastColNum = row.getLastCellNum();
            Map<String, Cell> rowMap = new LinkedHashMap<String, Cell>();
            if (count == 0) {
                for (int i = 0; i < lastColNum; i++) {
                    list_temp.add(row.getCell(i).toString());
                }
//                System.out.println(list_temp);
            }
            if (count < rowNum) {
                count++;
                continue;
            }
            for (int i = 0; i < lastColNum; i++) {
                rowMap.put(list_temp.get(i), row.getCell(i));
            }
            list.add(rowMap);
        }
//        System.out.println(rowMap);
//        System.out.println(rowMap.get("活动ID"));
//        System.out.print(list);
        if(inputStream != null){
            inputStream.close();
        }
        return list;
    }

    public Sheet write2Excel(String fileName, String sheetName) throws Exception {
        File file = new File(fileName);
        checkExcelVaild(file);
        InputStream inputStream = new FileInputStream(file);
        Workbook workbook = ExcelUtil.getWorkbook(inputStream, file);
        Sheet sheet = workbook.getSheet(sheetName);



        return sheet;
    }

}



