package Omatase.omatase.controller.api;

import Omatase.omatase.DTO.ReservationDTO;
import Omatase.omatase.DTO.ReservationStatusUpdateDTO;
import Omatase.omatase.service.ReservationAdminService;
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

    // すべてのユーザー予約情報の取得
    @GetMapping("/all")
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<ReservationDTO> reservations = reservationAdminService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    // 予約状態変更API
    @PostMapping("/update-status")
    public ResponseEntity<String> updateReservationStatus(@RequestBody ReservationStatusUpdateDTO updateDTO) {
        reservationAdminService.updateReservationStatus(updateDTO);
        return ResponseEntity.ok("Reservation status updated successfully");
    }
}
