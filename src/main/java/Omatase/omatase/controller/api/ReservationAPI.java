package Omatase.omatase.controller.api;

import Omatase.omatase.DTO.ReservationDTO;
import Omatase.omatase.exception.ApiResponse;
import Omatase.omatase.exception.CustomException;
import Omatase.omatase.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationAPI {

    private final ReservationService reservationService;

    public ReservationAPI(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // ユーザーIDですべての予約情報を取得するエンドポイント
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<ReservationDTO>>> getReservationsByUserId(@PathVariable("userId") Long userId) {
        try {
            List<ReservationDTO> reservations = reservationService.getAllReservationsByUserId(userId);
            ApiResponse<List<ReservationDTO>> response = new ApiResponse<>(reservations);
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ApiResponse<List<ReservationDTO>> errorResponse = new ApiResponse<>(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // 新しい予約を追加するエンドポイント
    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<String>> addReservation(@PathVariable("userId") Long userId, @RequestBody ReservationDTO reservationDTO) {
        try {
            reservationService.addReservation(userId, reservationDTO);
            ApiResponse<String> response = new ApiResponse<>("예약 성공");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ApiResponse<String> errorResponse = new ApiResponse<>(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // 予約確定API
    @PostMapping("/confirm/{reservationId}")
    public ResponseEntity<ApiResponse<String>> confirmReservation(@PathVariable("reservationId") Long reservationId) {
        try {
            reservationService.confirmReservation(reservationId);
            ApiResponse<String> response = new ApiResponse<>("예약 확정 성공");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getErrorCode(), e.getMessage()));
        }
    }
}
