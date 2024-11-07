package com.myscolog.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * {
 *      "code": "400",
 *      "message": "Bad Request",
 *      "validation" : {
 *          "title": "값을 입력해주세요",
 *          "contensts"
 *      }
 * }
 */
@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String,String> validation = new HashMap<String, String>();

    public void addValidation(String field, String message) {
        this.validation.put(field, message);
    }

}
