package com.myscolog.request;

import jakarta.validation.constraints.NotBlank;
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
}
