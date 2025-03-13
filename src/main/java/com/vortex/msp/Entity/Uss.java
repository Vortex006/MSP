package com.vortex.msp.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Uss {
    private Integer ussId;
    private String ussA;
    private Integer ussB;
}
