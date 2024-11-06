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

    // すべての問い合わせ照会(論理的に削除された問い合わせを含む)
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<InquiryDTO>>> getAllInquiries() {
        try {
            List<InquiryDTO> inquiries = inquiryAdminService.getAllInquiries();
            ApiResponse<List<InquiryDTO>> response = new ApiResponse<>(inquiries);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<InquiryDTO>> errorResponse = new ApiResponse<>(1000, "예기치 못한 오류 발생.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // お問い合わせに回答作成
    @PostMapping("/respond/{inquiryId}")
    public ResponseEntity<ApiResponse<InquiryDTO>> respondToInquiry(@PathVariable("inquiryId") Long inquiryId, @RequestBody InquiryDTO inquiryDTO) {
        try {
            inquiryAdminService.respondToInquiry(inquiryId, inquiryDTO.getResponseContent());
            // 既存の問い合わせ情報を再度照会して応答
            InquiryDTO updatedInquiry = inquiryAdminService.getInquiryById(inquiryId);
            ApiResponse<InquiryDTO> response = new ApiResponse<>(updatedInquiry);
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ApiResponse<InquiryDTO> errorResponse = new ApiResponse<>(e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ApiResponse<InquiryDTO> errorResponse = new ApiResponse<>(1000, "예기치 못한 오류 발생.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
