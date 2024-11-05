package Omatase.omatase.DTO;

import Omatase.omatase.enums.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // null 값을 제외합니다.
public class ReservationDTO {

    private Long reservationId; // 예약 ID 추가

    private String restaurant_link;

    private int adult_count;

    private int child_count;

    private LocalDateTime primary_date_time;

    private LocalDateTime secondary_date_time;

    private LocalDateTime tertiary_date_time;

    private LocalDateTime available_date_time;

    private ReservationStatus status;

    private LocalDateTime createdAt; // 추가

    private LocalDateTime modifiedAt;

    private Long userId; // 추가

    private String username; // 추가
}
