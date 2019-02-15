package Common;


import Bean.CaseInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaseHelper {
    //根据excel的map 转换为数组 第一个为 入参 map 第二个为用例说明,第三个参数为执行用例的预置条件
    public static Object[] getObjArrByMap(Map<String, String> caseExcelMap) {
        Map<String, String> caseParam = new HashMap<String, String>();
        Map<String, String> caseDesc = new HashMap<String, String>();
        Map<String, String> casePreset = new HashMap<String, String>();
        CaseInfo caseInfo = new CaseInfo();
        for (String key : caseExcelMap.keySet()) {
            if (key.indexOf("{$d}") == 0) {
                caseDesc.put(key.replace("{$d}", ""), caseExcelMap.get(key));
            } else if (key.indexOf("{$p}") == 0) {
                casePreset.put(key.replace("{$p}", ""), caseExcelMap.get(key));
            } else {
                String strValue = caseExcelMap.get(key);
                if (!strValue.equals("")) {
                    caseParam.put(key, strValue);
                }
            }
        }
        caseInfo.setCaseDesc(caseDesc);
        caseInfo.setCaseParam(caseParam);
        caseInfo.setCasePreset(casePreset);

        return new Object[]{caseInfo};
    }

    ///根据excel获取的list转换为  Object[][]
    public static Object[][] getObjArrByList(List<Map<String, String>> caseExcelList) {
        List<Map<String, String>> caseExcuteList = getExcuteList(caseExcelList);
        Object[][] objArray = new Object[caseExcuteList.size()][];
        for (int i = 0; i < caseExcuteList.size(); i++) {
            objArray[i] = getObjArrByMap(caseExcuteList.get(i));
        }
        return objArray;

    }

    ///筛选出需要执行的用例
    private static List<Map<String, String>> getExcuteList(List<Map<String, String>> caseExcelList) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (Map<String, String> m : caseExcelList) {
            String str = m.get("{$d}isExcute").trim().toLowerCase();
            if (str.equals("y")) {
                list.add(m);
            }
        }
        return list;
    }

}