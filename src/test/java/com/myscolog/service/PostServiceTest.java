package com.myscolog.service;

import com.myscolog.Exception.PostNotFound;
import com.myscolog.Repository.PostRepository;
import com.myscolog.domain.Post;
import com.myscolog.request.PostCreate;
import com.myscolog.request.PostEdit;
import com.myscolog.request.PostSearch;
import com.myscolog.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        postService.write(postCreate);

        // then

        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }


    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        //given
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(requestPost);
        //when
        PostResponse postResponse = postService.get(requestPost.getId());

        //then
        assertNotNull(postResponse);
        assertEquals(1L, postRepository.count());
        assertEquals("foo", postResponse.getTitle());
        assertEquals("bar", postResponse.getContent());
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test3() {
        //given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i -> {
                    return Post.builder()
                            .title("미스코 제목 " + i)
                            .content("드라코미스코 " + i)
                            .build();
                })
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .build();

        //when
        List<PostResponse> posts = postService.getList(postSearch);

        //then
        assertEquals(10L, posts.size());
        assertEquals("미스코 제목 30", posts.get(0).getTitle());
        assertEquals("미스코 제목 26", posts.get(4).getTitle());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test4() {
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

        //when
        postService.edit(requestPost.getId(), postEdit);

        //then
        Post changedPost = postRepository.findById(requestPost.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재 한지 않습니다 id =" + requestPost.getId()));
        assertEquals("드라코", changedPost.getTitle());
    }

    @Test
    @DisplayName("글 내용 수정")
    void test5() {
        //given
        Post requestPost = Post.builder()
                .title("미스코")
                .content("드라코미스코")
                .build();
        postRepository.save(requestPost);

        PostEdit postEdit = PostEdit.builder()
                .title("미스코")
                .content("플라코버드코")
                .build();

        //when
        postService.edit(requestPost.getId(), postEdit);

        //then
        Post changedPost = postRepository.findById(requestPost.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재 한지 않습니다 id =" + requestPost.getId()));
        assertEquals("플라코버드코", changedPost.getContent());
    }

    @Test
    @DisplayName("게시글 삭제")
    void test6() {
        //given
        Post requestPost = Post.builder()
                .title("미스코")
                .content("드라코미스코")
                .build();
        postRepository.save(requestPost);

        //when
        postService.delete(requestPost.getId());

        //then
        assertEquals(0, postRepository.count());
    }

    @Test
    @DisplayName("글 1개 조회 - 존재하지 않는 글")
    void test7() {
        //given
        Post post = Post.builder()
                .title("미스코")
                .content("드라코데빌코")
                .build();
        postRepository.save(post);

        //when
        Assertions.assertThrows(PostNotFound.class, () -> {
            postService.get(post.getId() + 1L);
        });

    }


    @Test
    @DisplayName("게시글 삭제 - 존재하지 않는 글")
    void test8() {
        //given
        Post requestPost = Post.builder()
                .title("미스코")
                .content("드라코미스코")
                .build();
        postRepository.save(requestPost);

        //then
        Assertions.assertThrows(PostNotFound.class, () -> {
            postService.delete(requestPost.getId() + 1L);

        });
    }

    @Test
    @DisplayName("글 내용 수정 - 존재하지 않는 글")
    void test9() {
        //given
        Post requestPost = Post.builder()
                .title("미스코")
                .content("드라코미스코")
                .build();
        postRepository.save(requestPost);

        PostEdit postEdit = PostEdit.builder()
                .title("미스코")
                .content("플라코버드코")
                .build();

        //then
        Assertions.assertThrows(PostNotFound.class, () -> {
            postService.edit(requestPost.getId() + 1L, postEdit);
        });
    }
}