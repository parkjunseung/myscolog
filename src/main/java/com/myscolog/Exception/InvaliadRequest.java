package com.myscolog.Exception;

import lombok.Getter;

/**
 *  status -> 400
 */

@Getter
public class InvaliadRequest extends MyscoException {

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvaliadRequest() {
        super(MESSAGE);
    }

    public InvaliadRequest(String fieldName , String message) {
        super(MESSAGE);
        addValidation(fieldName , message);
    }

    public int getStatusCode() {
        return 400;
    }
}