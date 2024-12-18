package Omatase.omatase.entity;


import Omatase.omatase.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "reservation")
public class ReservationEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String restaurant_link;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_RESERVATION_USER"))
    private UserEntity user;

    private int adult_count;

    private int child_count;

    private LocalDateTime primary_date_time;

    private LocalDateTime secondary_date_time;

    private LocalDateTime tertiary_date_time;

    private LocalDateTime available_date_time;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}
