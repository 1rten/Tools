package Common;

import org.apache.poi.ss.formula.functions.T;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DataProviderWithExcel {

    /**
     * 为getData方法传入类名
     * @param tClass 使用testNG->@DataProvider的类名与excel文件名一致
     * @return getData
     */

    @DataProvider(name = "data")
    public Object[][] dataInfo(Class<T> tClass) throws IOException {
        return getData(tClass.getSimpleName());
    }

    //根据组合的文件路径调用ExcelReader获取CaseInfo对象
    protected static Object[][] getData(String fileName){
        Object[][] myObj = null;
        String filePath = "./DataResource/" + fileName + ".xlsx";
        System.out.println(filePath);
        List<Map<String, String>> list = new ExcelPoiReaderUtils().readExcel(filePath);
        myObj = CaseHelper.getObjArrByList(list);
        return myObj;
    }

}
