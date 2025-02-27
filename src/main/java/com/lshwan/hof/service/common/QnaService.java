package com.lshwan.hof.service.common;

import com.lshwan.hof.domain.dto.QnaDto;
import com.lshwan.hof.domain.entity.common.Qna;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.prod.Prod;

import java.util.List;

public interface QnaService {
    Long add(QnaDto dto,Member member,Qna parentQna);
    Qna findBy(Long no);
    List<QnaDto> findList();
    Long modify(Qna qna);
    boolean remove(Long no);
    List<QnaDto> findByProduct(Long pno);

    default QnaDto toDto(Qna qna) {
        return QnaDto.builder()
            .no(qna.getNo())
            .memberId(qna.getMember().getId()) // ID만 저장
            .prodTitle(qna.getPno().getTitle())
            .pno(qna.getPno().getPno())
            .content(qna.getContent())
            .status(qna.getStatus())
            .parentNo(qna.getParentQna() != null ? qna.getParentQna().getNo() : null)
            .build();
    }

    // DTO → Entity 변환
    default Qna toEntity(QnaDto dto, Member member, Qna parentQna,Prod prod) {
        return Qna.builder()
            .member(member)
            .content(dto.getContent())
            .depth((parentQna != null) ? parentQna.getDepth() + 1 : 0)
            .pno(prod)
            .status((parentQna != null) ? Qna.QnaStatus.처리후 : Qna.QnaStatus.처리전)
            .parentQna(parentQna)
            .build();
    }

}