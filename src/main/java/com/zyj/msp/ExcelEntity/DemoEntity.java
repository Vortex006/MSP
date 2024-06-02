package com.zyj.msp.ExcelEntity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DemoEntity {

    @ExcelProperty("用户创建时间")
    private int userCreate;
    @ExcelProperty("用户更新时间")
    private int userUpdate;

}
