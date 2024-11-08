package com.vortex.msp.Enum;

import lombok.Getter;

@Getter
public enum CardType {
    PatientId(1, "patient_id"),
    CertNo(2, "patient_cert_no"),
    CardNo(3, "patient_card_no");

    private final int id;
    private final String colName;

    CardType(int id, String colName) {
        this.id = id;
        this.colName = colName;
    }

    public static String findColName(int id) {
        for (CardType type : values()) {
            if (type.getId() == id) {
                return type.getColName();
            }
        }
        return "";
    }
}
