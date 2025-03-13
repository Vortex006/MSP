package com.vortex.msp.Enum;

public enum AppointmentStates {
    ALL(0),
    NORMAL(1),
    RETURN(2),
    CANCEL(3);

    private final int stateId;

    AppointmentStates(int stateId) {
        this.stateId = stateId;
    }

    public int getCode() {
        return stateId;
    }
}
