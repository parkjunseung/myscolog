package com.myscolog.controller;

import com.myscolog.request.PostCreate;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//SSR -> jsp, thymeleaf, freemarker : html rendering
//SPA -> vue, react : javascript + < - > API
// vue + SSR = nuxt
// react + SSR  = nex

@Slf4j
@RestController
public class PostController {
    // Http Methods
    // GET, PUT, POST, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    // 記事の登録
    // post Methods

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostCreate params, BindingResult result) {
        //データを検証する理由
        // 1. クライアント開発者が誤って値を送信しなかった
        // 2. クライアントのバグで値が欠落したs
        // 3. 外部の悪意のある人物が値を任意に改ざんした
        // 4. データベースに値を保存する際に意図しないエラーが発生
        // 5. サーバー開発者の快適さのため
        if(result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError firstFieldEror = fieldErrors.get(0);
            String fieldName = firstFieldEror.getField();
            String errorMessage =  firstFieldEror.getDefaultMessage();

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);
            return error;
        }

        return Map.of();
    }

    @GetMapping("/posts2")
    public String post() {

        return "확인";
    }
}
