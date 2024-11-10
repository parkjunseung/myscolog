package com.myscolog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myscolog.Repository.PostRepository;
import com.myscolog.domain.Post;
import com.myscolog.request.PostCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.myscolog.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class PostControllerDocTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("글 단건 조회 테스트")
    void test1() throws Exception {

        //given
        Post post = Post.builder()
                .title("미스코")
                .content("미스코플라코")
                .build();

        postRepository.save(post);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{postId}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-inquiry",
                            RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("postId").description("게시글 Id")
                            ),
                            PayloadDocumentation.responseFields(
                                    PayloadDocumentation.fieldWithPath("id").description("게시글 Id"),
                                    PayloadDocumentation.fieldWithPath("title").description("게시글 제목"),
                                    PayloadDocumentation.fieldWithPath("content").description("게시글 내용")
                            )
                        ));
    }

    @Test
    @DisplayName("글 등록 테스트")
    void test2() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("미스코")
                .content("뉴플라코")
                .build();

        String json = objectMapper.writeValueAsString(request);
        //expected
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("post-create",
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("title").description("게시글 제목")
                                        .attributes(Attributes.key("constraint").value("좋은 제목을 입력해주세요")),
                                PayloadDocumentation.fieldWithPath("content").description("게시글 내용").optional()
                        )
                ));
    }

}
