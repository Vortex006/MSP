package easyExcel2;

import com.alibaba.excel.EasyExcel;
import org.junit.jupiter.api.Test;

import java.util.*;

public class excelTest {
    @Test
    public void simpleWrite() {
        String path = "D:\\Users\\Desktop\\abc.xlsx";
        EasyExcel.write(path, demo.class).sheet("模板").doWrite(data());
    }

    private List<demo> data() {
        List<demo> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            demo d = new demo();
            d.setUserCreate(i);
            d.setUserUpdate(i+1);
            list.add(d);
        }
        return list;
    }
}
