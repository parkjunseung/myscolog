package com.myscolog.controller;

import com.myscolog.domain.Post;
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
    public void post(@RequestBody @Valid PostCreate request) {
        //Case1 - 저장한 데이터 Entity -> response 로 응답
        //Case2 - 저장한 데이터 primary_id -> response로 응답
        //          Client에서는 수신한 id를 글 조회 api를 통해 글 데이터를 수신
        //Case3. 응답 필요없음 -> 클라이언트에서 모든 포스트 데이터 컨텍스트를 관리
        //Bad Case: 서버에서 -> 반드시 이렇게 할껍니다! fix
        //           -> 서버에서는 유연하게 대응하는게 좋다.
        //           -> 한번에 일괄적으로 잘 처리되는 케이스가 없다.
        postService.write(request);
    }

    @GetMapping("/posts/{postId}")
    public Post get(@PathVariable(name = "postId") Long id) {
       Post post = postService.get(id);

       return post;
    }


    @GetMapping("/posts2")
    public String post() {

        return "확인";
    }
}
