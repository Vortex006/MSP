package com.vortex.msp.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MergeRequest {
    private String hash;
    private String filename;
}
