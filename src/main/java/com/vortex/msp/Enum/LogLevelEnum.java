package com.vortex.msp.Enum;

public enum LogLevelEnum {
    debug("DEBUG"),
    info("INFO"),
    warn("WARN"),
    error("ERROR");

    private String level;

    LogLevelEnum(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

}
