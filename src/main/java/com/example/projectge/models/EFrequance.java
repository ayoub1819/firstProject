package com.example.projectge.models;

public enum EFrequance {
    RARE("Rare"), FREQUENTE("Fréquente") , PERMANENTE("Permanente");

    private final String displayValue;

    public String getDisplayValue() {
        return displayValue;
    }
    EFrequance(String displayValue) {
        this.displayValue = displayValue;
    }
}
