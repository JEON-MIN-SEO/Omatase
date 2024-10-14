package Omatase.omatase.entity;

import Omatase.omatase.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ReservationEntity extends BaseEntity{

    private String restaurant_link;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user_id;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private AdminEntity admin_id;

    private int adult_count;

    private int child_count;

    private LocalDateTime primary_date_time;

    private LocalDateTime secondary_date_time;

    private LocalDateTime tertiary_date_time;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}
