package Omatase.omatase.controller.api;

import Omatase.omatase.DTO.InquiryDTO;
import Omatase.omatase.exception.ApiResponse;
import Omatase.omatase.exception.CustomException;
import Omatase.omatase.service.InquiryAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/api/inquiries")
public class AdminInquiryAPI {

    private final InquiryAdminService inquiryAdminService;

    public AdminInquiryAPI(InquiryAdminService inquiryAdminService) {
        this.inquiryAdminService = inquiryAdminService;
    }

    // 모든 문의 조회 (논리적 삭제된 문의 포함)
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<InquiryDTO>>> getAllInquiries() {
        try {
            List<InquiryDTO> inquiries = inquiryAdminService.getAllInquiries();
            ApiResponse<List<InquiryDTO>> response = new ApiResponse<>(inquiries);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<InquiryDTO>> errorResponse = new ApiResponse<>(1000, "An error occurred while fetching inquiries.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // 문의에 답변 작성
    @PostMapping("/respond/{inquiryId}")
    public ResponseEntity<ApiResponse<InquiryDTO>> respondToInquiry(@PathVariable("inquiryId") Long inquiryId, @RequestBody InquiryDTO inquiryDTO) {
        try {
            inquiryAdminService.respondToInquiry(inquiryId, inquiryDTO.getResponseContent());
            // 기존 문의 정보를 다시 조회하여 응답
            InquiryDTO updatedInquiry = inquiryAdminService.getInquiryById(inquiryId); // 문의 조회 메서드 추가 필요
            ApiResponse<InquiryDTO> response = new ApiResponse<>(updatedInquiry);
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ApiResponse<InquiryDTO> errorResponse = new ApiResponse<>(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ApiResponse<InquiryDTO> errorResponse = new ApiResponse<>(1000, "An error occurred while responding to the inquiry.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
