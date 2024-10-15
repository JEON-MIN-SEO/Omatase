package Omatase.omatase.controller.api;

import Omatase.omatase.DTO.ReservationDTO;
import Omatase.omatase.DTO.ReservationStatusUpdateDTO;
import Omatase.omatase.exception.ApiResponse;
import Omatase.omatase.service.ReservationAdminService;
import Omatase.omatase.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api/reservation")
public class AdminReservationAPI {

    private final ReservationAdminService reservationAdminService;

    public AdminReservationAPI(ReservationAdminService reservationAdminService) {
        this.reservationAdminService = reservationAdminService;
    }

    // 모든 사용자 예약 정보 가져오기
    @GetMapping("/all")
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<ReservationDTO> reservations = reservationAdminService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    // 예약 상태 변경 API
    @PostMapping("/update-status")
    public ResponseEntity<String> updateReservationStatus(@RequestBody ReservationStatusUpdateDTO updateDTO) {
        reservationAdminService.updateReservationStatus(updateDTO);
        return ResponseEntity.ok("Reservation status updated successfully");
    }
}
