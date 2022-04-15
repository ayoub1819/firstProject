package com.example.projectge.models;

public enum EOrdre {
    LOGICIEL("logiciel"),MATERIEL("materiel");

    private final String displayValue;

    EOrdre(String displayValue) {
        this.displayValue = displayValue;
    }
    public String getDisplayValue() {
        return displayValue;
    }
}
