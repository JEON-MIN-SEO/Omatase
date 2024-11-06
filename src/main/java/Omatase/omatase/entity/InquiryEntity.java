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
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_INQUIRY_USER"))
    private UserEntity user;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_deleted")
    private boolean deleted = false; // 論理的削除のためのフィールド

    @Column(columnDefinition = "TEXT")
    private String responseContent; // 管理者応答内容

}

