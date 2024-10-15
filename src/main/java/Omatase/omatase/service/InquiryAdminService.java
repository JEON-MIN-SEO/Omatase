package Omatase.omatase.service;

import Omatase.omatase.DTO.InquiryDTO;
import Omatase.omatase.entity.InquiryEntity;
import Omatase.omatase.exception.CustomException;
import Omatase.omatase.repository.InquiryRepository;
import Omatase.omatase.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InquiryAdminService {

    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;

    public InquiryAdminService(InquiryRepository inquiryRepository, UserRepository userRepository) {
        this.inquiryRepository = inquiryRepository;
        this.userRepository = userRepository;
    }

    // 모든 문의 조회 (논리적 삭제된 문의 포함)
    public List<InquiryDTO> getAllInquiries() {
        List<InquiryEntity> inquiries = inquiryRepository.findAll();
        return inquiries.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 문의에 답변 작성
    public void respondToInquiry(Long inquiryId, String responseContent) {
        InquiryEntity inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new CustomException(1002, "Inquiry not found"));
        inquiry.setResponseContent(responseContent); // 답변 설정
        inquiryRepository.save(inquiry);
    }

    // DTO 변환 메서드
    private InquiryDTO convertToDTO(InquiryEntity inquiry) {
        InquiryDTO inquiryDTO = new InquiryDTO();
        inquiryDTO.setId(inquiry.getId());
        inquiryDTO.setTitle(inquiry.getTitle());
        inquiryDTO.setContent(inquiry.getContent());
        inquiryDTO.setResponseContent(inquiry.getResponseContent());
        inquiryDTO.setDeleted(inquiry.isDeleted());
        inquiryDTO.setCreatedAt(inquiry.getCreatedAt());
        return inquiryDTO;
    }

}
