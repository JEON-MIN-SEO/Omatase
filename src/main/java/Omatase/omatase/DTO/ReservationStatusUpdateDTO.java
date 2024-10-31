package Omatase.omatase.DTO;

import Omatase.omatase.enums.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationStatusUpdateDTO {

    private Long reservationId; // 변경할 예약의 ID

    private ReservationStatus status; // 새로운 상태

    private LocalDateTime selectedDateTime; // 선택한 날짜 (AVAILABLE 상태로 변경 시)

}
