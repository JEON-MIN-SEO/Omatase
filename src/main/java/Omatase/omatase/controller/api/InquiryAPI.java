package Omatase.omatase.controller.api;

import Omatase.omatase.DTO.InquiryDTO;
import Omatase.omatase.exception.ApiResponse;
import Omatase.omatase.exception.CustomException;
import Omatase.omatase.service.InquiryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryAPI {

    private final InquiryService inquiryService;

    public InquiryAPI(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    // 특정 사용자의 모든 문의 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<InquiryDTO>>> getUserInquiries(@PathVariable("userId") Long userId) {
        try {
            List<InquiryDTO> inquiries = inquiryService.getUserInquiries(userId);
            ApiResponse<List<InquiryDTO>> response = new ApiResponse<>(inquiries);
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ApiResponse<List<InquiryDTO>> errorResponse = new ApiResponse<>(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // 문의 작성
    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<String>> createInquiry(@PathVariable("userId") Long userId, @RequestBody InquiryDTO inquiryDTO) {
        try {
            inquiryService.createInquiry(userId, inquiryDTO);
            ApiResponse<String> response = new ApiResponse<>("Inquiry created successfully.");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage()));
        }
    }

    // 문의 삭제 엔드포인트
    @DeleteMapping("/{inquiryId}")
    public ResponseEntity<ApiResponse<String>> deleteInquiry(@PathVariable("inquiryId") Long inquiryId) {
        try {
            inquiryService.deleteInquiry(inquiryId);
            ApiResponse<String> response = new ApiResponse<>("Inquiry deleted successfully.");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(e.getMessage()));
        }
    }
}
