package com.zyj.msp.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString()
public class FileInfo {
    private String name;
    private long size;
    private int chunks;
    private String md5;
}
