package com.vortex.msp;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SealCircle {
    private Integer line;
    private Integer width;
    private Integer height;
}

