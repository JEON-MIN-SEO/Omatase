package Omatase.omatase.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "inquiries")
public class InquiryEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_deleted")
    private boolean deleted = false; // 논리적 삭제를 위한 필드

    @Column(columnDefinition = "TEXT")
    private String responseContent; // 관리자 응답 내용

}

