package Omatase.omatase.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // null値を除きます。
public class InquiryDTO {

    private Long id; // お問い合わせID追加

    private String title;

    private String content;

    private String responseContent; // 管理者応答内容

    private boolean isDeleted; // 削除の可否

    private LocalDateTime createdAt;
}
