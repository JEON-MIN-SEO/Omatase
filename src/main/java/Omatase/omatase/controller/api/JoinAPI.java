package Omatase.omatase.controller.api;

import Omatase.omatase.DTO.UserDTO;
import Omatase.omatase.exception.ApiResponse;
import Omatase.omatase.exception.CustomException;
import Omatase.omatase.service.JoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/join")
public class JoinAPI {

    private final JoinService joinService;

    public JoinAPI(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/user")
    public ResponseEntity<ApiResponse<String>> JoinProcess(UserDTO userDTO) {
        try {
            joinService.JoinProcess(userDTO);
            ApiResponse<String> response = new ApiResponse<>("사용자  회원가입에 성공했습니다.");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ApiResponse<String> errorResponse = new ApiResponse<>(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<ApiResponse<String>> AdminJoinProcess(UserDTO userDTO) {
        try {
            joinService.AdminJoinProcess(userDTO);
            ApiResponse<String> response = new ApiResponse<>("관리자 회원가입에 성공했습니다.");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ApiResponse<String> errorResponse = new ApiResponse<>(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
