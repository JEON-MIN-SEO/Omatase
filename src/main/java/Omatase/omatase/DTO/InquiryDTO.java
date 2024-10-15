package Omatase.omatase.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // null 값을 제외합니다.
public class InquiryDTO {

    private Long id; // 문의 ID 추가

    private String title;

    private String content;

    private String responseContent; // 관리자 응답 내용

    private boolean isDeleted; // 삭제 여부

    private LocalDateTime createdAt;
}
