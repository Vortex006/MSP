package easyExcel2;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class demo {
    @ExcelProperty("用户创建时间")
    private int userCreate;
    @ExcelProperty("用户更新时间")
    private int userUpdate;

}
