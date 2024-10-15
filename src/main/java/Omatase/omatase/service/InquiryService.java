package Omatase.omatase.service;


import Omatase.omatase.DTO.InquiryDTO;
import Omatase.omatase.entity.InquiryEntity;
import Omatase.omatase.entity.UserEntity;
import Omatase.omatase.exception.CustomException;
import Omatase.omatase.repository.InquiryRepository;
import Omatase.omatase.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;

    public InquiryService(InquiryRepository inquiryRepository, UserRepository userRepository) {
        this.inquiryRepository = inquiryRepository;
        this.userRepository = userRepository;
    }

    // 특정 사용자의 모든 문의 조회
    public List<InquiryDTO> getUserInquiries(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(1001, "User not found"));

        return inquiryRepository.findAllByUserAndDeletedIsFalse(user).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private InquiryDTO convertToDTO(InquiryEntity inquiry) {
        InquiryDTO inquiryDTO = new InquiryDTO();
        inquiryDTO.setTitle(inquiry.getTitle());
        inquiryDTO.setContent(inquiry.getContent());
        inquiryDTO.setResponseContent(inquiry.getResponseContent());
        return inquiryDTO;
    }

    // 문의 작성
    public void createInquiry(Long userId, InquiryDTO inquiryDTO) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(1001, "User not found"));

        InquiryEntity inquiry = new InquiryEntity();
        inquiry.setUser(user);
        inquiry.setTitle(inquiryDTO.getTitle());
        inquiry.setContent(inquiryDTO.getContent());

        inquiryRepository.save(inquiry);
    }

    // 문의 삭제 (논리적 삭제)
    public void deleteInquiry(Long inquiryId) {
        InquiryEntity inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new CustomException(1004, "Inquiry not found"));

        // 논리적 삭제 처리
        inquiry.setDeleted(true);
        inquiryRepository.save(inquiry);
    }

}
