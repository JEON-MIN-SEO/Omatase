package Omatase.omatase.DTO;

import Omatase.omatase.enums.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationStatusUpdateDTO {

    private Long reservationId; // 変更する予約のID

    private ReservationStatus status; // 新しい状態

    private LocalDateTime selectedDateTime; // 選択した日付(AVAILABLE状態に変更時)

}
