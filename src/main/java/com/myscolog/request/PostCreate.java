package com.myscolog.request;

import com.myscolog.Exception.InvaliadRequest;
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

    @NotBlank(message = "콘텐츠를 입력해 주세요")
    private String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validate() {
        if(title.contains("바보")) {
            throw new InvaliadRequest("title", "제목에 바보를 포함 할 수 없습니다.");
        }
    }

    // 빌더의 장점
    // - 가독성
    // - 값 생성의 유연함
    // - 필요한 값만 받을 수 있다.(오버로딩)
    // - 객체의 불변성

}
