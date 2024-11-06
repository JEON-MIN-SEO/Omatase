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

    // すべての問い合わせ照会(論理的に削除された問い合わせを含む)
    public List<InquiryDTO> getAllInquiries() {
        List<InquiryEntity> inquiries = inquiryRepository.findAll();
        return inquiries.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // お問い合わせに回答作成
    public void respondToInquiry(Long inquiryId, String responseContent) {
        InquiryEntity inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new CustomException(1002, "Inquiry not found"));
        inquiry.setResponseContent(responseContent); // 답변 설정
        inquiryRepository.save(inquiry);
    }


    // 特定のお問い合わせIDで照会
    public InquiryDTO getInquiryById(Long inquiryId) {
        InquiryEntity inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new CustomException(1002, "Inquiry not found"));
        return convertToInquiryDTO(inquiry);
    }

    // InquiryEntityをInquiryDTOに変換
    private InquiryDTO convertToInquiryDTO(InquiryEntity inquiry) {
        InquiryDTO inquiryDTO = new InquiryDTO();
        inquiryDTO.setId(inquiry.getId());
        inquiryDTO.setTitle(inquiry.getTitle());
        inquiryDTO.setContent(inquiry.getContent());
        inquiryDTO.setResponseContent(inquiry.getResponseContent());
        inquiryDTO.setDeleted(inquiry.isDeleted());
        inquiryDTO.setCreatedAt(inquiry.getCreatedAt());
        return inquiryDTO;
    }

    // DTO変換メソッド
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
