package com.cailangrott.peoplemanager.util;

public class ValorOuDefault {
    public static <T> T getValorOuDefault(T valorDefault, T value) {
        return value == null || (value instanceof String && ((String) value).isBlank()) ? valorDefault : value;
    }
}
