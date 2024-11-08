package com.myscolog.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해 주세요.") // 검증
    private String title;

    @NotBlank(message = "Please enter the content.")
    private String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 빌더의 장점
    // - 가독성
    // - 값 생성의 유연함
    // - 필요한 값만 받을 수 있다.(오버로딩)
    // - 객체의 불변성

}
