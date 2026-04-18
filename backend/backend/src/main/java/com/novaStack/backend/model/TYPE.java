package com.novaStack.backend.model;

public enum TYPE {
    EXPENSE("expense"),
    INCOME("income");


    private String type;

    TYPE(String type) {
        this.type = type;
    }

    public static TYPE fromString(String text) {
        for (TYPE categoria : TYPE.values()) {
            if (categoria.type.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("type invalid");
    }
}
