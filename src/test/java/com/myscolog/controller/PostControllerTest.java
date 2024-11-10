package com.myscolog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myscolog.Repository.PostRepository;
import com.myscolog.domain.Post;
import com.myscolog.request.PostCreate;
import com.myscolog.request.PostEdit;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청")
    void test() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("컨텐츠입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ) //application json
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 title 값은 필수다")
    void test2() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .content("컨텐츠입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ) //application json
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 DB 데이터 저장")
    void test3() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("컨텐츠입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ) //application json
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertEquals(1, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("컨텐츠입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        //given
        Post post = Post.builder()
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(post);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                ) //application json
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("foo"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());

    }

    @Test
    @DisplayName("글 여러개 조회")
    void test5() throws Exception {
        //given
        List<Post> requestPosts = IntStream.range(1,31)
                .mapToObj(i ->Post.builder()
                            .title("미스코 제목 " + i)
                            .content("드라코미스코 " + i)
                            .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=1&size=10")
                        .contentType(MediaType.APPLICATION_JSON)
                ) //application json
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(10)))
                .andExpect(jsonPath("$[0].title").value("미스코 제목 30"))
                .andExpect(jsonPath("$[0].content").value("드라코미스코 30"))
                .andDo(print());

    }

    @Test
    @DisplayName("페이지 0을 요청하면 첫페이지")
    void test6() throws Exception {
        //given
        List<Post> requestPosts = IntStream.range(1,31)
                .mapToObj(i -> Post.builder()
                            .title("미스코 제목 " + i)
                            .content("드라코미스코 " + i)
                            .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON)
                ) //application json
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(10)))
                .andExpect(jsonPath("$[0].title").value("미스코 제목 30"))
                .andExpect(jsonPath("$[0].content").value("드라코미스코 30"))
                .andDo(print());

    }


    @Test
    @DisplayName("글 제목 수정")
    void test7() throws Exception {
        //given
        Post requestPost = Post.builder()
                .title("미스코")
                .content("드라코미스코")
                .build();
        postRepository.save(requestPost);

        PostEdit postEdit = PostEdit.builder()
                .title("드라코")
                .content("드라코미스코")
                .build();

        //expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/{postId}", requestPost.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                ) //application json
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("게시글 삭제")
    void test8() throws Exception {
        //given
        Post requestPost = Post.builder()
                .title("미스코")
                .content("드라코미스코")
                .build();
        postRepository.save(requestPost);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{postId}", requestPost.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                ) //application json
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 조회")
    void test9() throws Exception {
        //expected
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                ) //application json
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 수정")
    void test10() throws Exception {
        PostEdit postEdit = PostEdit.builder()
                .title("드라코")
                .content("드라코미스코")
                .build();

        //expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                ) //application json
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 작성시 제목에 '바보'포함 불가")
    void test11() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("미스코바보")
                .content("뉴플라코")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ) //application json
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

}

// API 문서 생성
// Spring RestDocs
// 운영코드에 영향이 없음