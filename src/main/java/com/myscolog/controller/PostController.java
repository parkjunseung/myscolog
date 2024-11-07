package com.myscolog.controller;

import com.myscolog.request.PostCreate;
import com.myscolog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PostController {
    // Http Methods
    // GET, PUT, POST, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    // 記事の登録
    // post Methods

    private final PostService postService;


    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostCreate request) {
        // db.save(parms)
        postService.write(request);
        return Map.of();
    }

    @GetMapping("/posts2")
    public String post() {

        return "확인";
    }
}
