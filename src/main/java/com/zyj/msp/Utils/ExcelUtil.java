package com.zyj.msp.Utils;

import com.alibaba.excel.EasyExcel;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ExcelUtil {

    public static String getExcel(Class classType, List list) {
        String path = String.format("D:\\Users\\Desktop\\%s.xlsx", System.currentTimeMillis());
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        EasyExcel.write(path, classType).sheet("模板").doWrite(list);
        return path;

    }


}
