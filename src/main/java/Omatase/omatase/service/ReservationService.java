package Omatase.omatase.service;

import Omatase.omatase.DTO.ReservationDTO;
import Omatase.omatase.repository.ReservationRepository;
import Omatase.omatase.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    public List<ReservationDTO> getList(Long userId) {
    }

    public void addList(ReservationDTO reservationDTO) {
    }


}