package Omatase.omatase.controller.api;

import Omatase.omatase.DTO.UserDTO;
import Omatase.omatase.exception.ApiResponse;
import Omatase.omatase.exception.CustomException;
import Omatase.omatase.service.JoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinAPI {

    private final JoinService joinService;

    public JoinAPI (JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<String>> UserJoinProcess(UserDTO userDTO) {
        try {
            joinService.UserJoinProcess(userDTO);
            ApiResponse<String> response = new ApiResponse<>("User registered successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ApiResponse<String> errorResponse = new ApiResponse<>(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/join/admin")
    public ResponseEntity<ApiResponse<String>> AdminJoinProcess(UserDTO userDTO) {
        try {
            joinService.AdminJoinProcess(userDTO);
            ApiResponse<String> response = new ApiResponse<>("User registered successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ApiResponse<String> errorResponse = new ApiResponse<>(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
