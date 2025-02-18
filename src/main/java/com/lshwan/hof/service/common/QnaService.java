package com.lshwan.hof.service.common;

import com.lshwan.hof.domain.dto.QnaDto;
import com.lshwan.hof.domain.entity.common.Qna;
import com.lshwan.hof.domain.entity.member.Member;

import java.util.List;

public interface QnaService {
    Long add(QnaDto dto,Member member,Qna parentQna);
    Qna findBy(Long no);
    List<QnaDto> findList();
    Long modify(Qna qna);
    boolean remove(Long no);

    default QnaDto toDto(Qna qna) {
        return QnaDto.builder()
            .no(qna.getNo())
            .memberId(qna.getMember().getId()) // ID만 저장
            .content(qna.getContent())
            .status(qna.getStatus())
            .parentNo(qna.getParentQna() != null ? qna.getParentQna().getNo() : null)
            .build();
    }

    // DTO → Entity 변환
    default Qna toEntity(QnaDto dto, Member member, Qna parentQna) {
        return Qna.builder()
            .member(member)
            .content(dto.getContent())
            .status(dto.getStatus())
            .parentQna(parentQna)
            .build();
    }

}