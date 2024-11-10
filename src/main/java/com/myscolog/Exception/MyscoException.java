package com.myscolog.Exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class MyscoException extends RuntimeException {

    public final Map<String, String> validation = new HashMap<>();

    public MyscoException(String message) {
        super(message);
    }

    public MyscoException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);

    }

}
