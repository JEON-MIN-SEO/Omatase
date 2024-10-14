package Omatase.omatase.controller.api;

import Omatase.omatase.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationAPI {

    private final ReservationService reservationService;

    public ReservationAPI(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    //특정 사용자의 예약 리스트 조회
    @GetMapping("/{userId}")
    public ResponseEntity<String> getReservationList(@PathVariable("userId") Long userId) {
        List<>reservationService.getList;
    }
}
