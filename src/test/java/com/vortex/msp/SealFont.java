package com.vortex.msp;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SealFont {
    private String text;
    private String family;
    private Integer size;
    private Boolean bold;
    private Double space;
    private Integer margin;

    public String getFamily() {
        return family == null ? "宋体" : family;
    }

    public boolean getBold() {
        return bold == null || bold;
    }

    public SealFont append(String text) {
        this.text += text;
        return this;
    }
}

