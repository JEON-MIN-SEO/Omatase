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

    // 사용자 ID로 모든 예약 정보를 가져오는 엔드포인트
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<ReservationDTO>>> getReservationsByUserId(@PathVariable Long userId) {
        try {
            List<ReservationDTO> reservations = reservationService.getAllReservationsByUserId(userId);
            ApiResponse<List<ReservationDTO>> response = new ApiResponse<>(reservations);
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ApiResponse<List<ReservationDTO>> errorResponse = new ApiResponse<>(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // 새로운 예약을 추가하는 엔드포인트
    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<String>> addReservation(@PathVariable("userId") Long userId, @RequestBody ReservationDTO reservationDTO) {
        try {
            reservationService.addReservation(userId, reservationDTO);
            ApiResponse<String> response = new ApiResponse<>("Reservation added successfully.");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ApiResponse<String> errorResponse = new ApiResponse<>(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
