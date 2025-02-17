package com.lshwan.hof.service.common;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.dto.QnaDto;
import com.lshwan.hof.domain.entity.common.Qna;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.member.Member.MemberRole;
import com.lshwan.hof.repository.common.QnaRepository;
import com.lshwan.hof.repository.member.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
@Transactional
public class QnaServiceTests {
    @Autowired
    private QnaService service;

    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member existingMember;
    private Qna parentQna;

    @BeforeEach
    @Transactional
    void setUp() {
        // 기존 Member를 찾고 없으면 하나 저장
        Optional<Member> optionalMember = memberRepository.findById(5L);
        if (optionalMember.isPresent()) {
            existingMember = optionalMember.get();
        } else {
            existingMember = memberRepository.save(Member.builder()
                    .id("existingUser")
                    .pw("1234")
                    .name("테스트 사용자")
                    .role(MemberRole.admin)
                    .build());
        }

        // 부모 Qna를 미리 저장
        parentQna = qnaRepository.save(Qna.builder()
                .member(existingMember)
                .content("부모 문의입니다.")
                .status(Qna.QnaStatus.처리전)
                .depth(0)
                .build());
    }

    // @Test
    // void testAddQna() {
    //      QnaDto qnaDto = new QnaDto(null, existingMember.getId(), "테스트 문의입니다.", Qna.QnaStatus.처리전, null);
    // // ✅ add() 호출 시 QnaDto와 Member 객체를 함께 전달
    //     Long savedId = service.add(qnaDto, existingMember);
    //     assertTrue(savedId > 0);

    //     Qna savedQna = service.findBy(savedId);
    //     assertNotNull(savedQna);
    //     assertEquals("테스트 문의입니다.", savedQna.getContent());
    // }

    // @Test
    // void testAddReplyToQna() {
    //     // ✅ 답글용 QnaDto 생성
    //     QnaDto replyDto = new QnaDto(null, existingMember.getId(), "이것은 답글입니다.", Qna.QnaStatus.처리전, parentQna.getNo());

    //     // ✅ add() 호출 시 QnaDto와 Member 객체를 함께 전달
    //     Long replyId = service.add(replyDto, existingMember);
    //     assertTrue(replyId > 0);

    //     Qna savedReply = service.findBy(replyId);
    //     assertNotNull(savedReply);
    //     assertEquals("이것은 답글입니다.", savedReply.getContent());
    //     assertEquals(parentQna.getNo(), savedReply.getParentQna().getNo());
    //     assertEquals(parentQna.getDepth() + 1, savedReply.getDepth());
    // }


    // @Test
    // void testFindBy() {
    //     Qna qna = Qna.builder()
    //             .member(existingMember)
    //             .content("문의 내용")
    //             .status(Qna.QnaStatus.처리전)
    //             .depth(0)
    //             .build();

    //     Long savedId = service.add(qna);
    //     Qna foundQna = service.findBy(savedId);
    //     assertNotNull(foundQna);
    //     assertEquals("문의 내용", foundQna.getContent());
    // }

    // @Test
    // void testFindList() {

    //     service.add(new QnaDto(null, existingMember.getId(), "문의1", Qna.QnaStatus.처리전, null), existingMember);
    //     service.add(new QnaDto(null, existingMember.getId(), "문의2", Qna.QnaStatus.처리전, null), existingMember);

    //     List<QnaDto> list = service.findList();
    //     assertTrue(list.size() > 0);
    // }
    // @Test
    // void testModify() {
    //     Qna qna = Qna.builder()
    //             .member(existingMember)
    //             .content("초기 문의")
    //             .status(Qna.QnaStatus.처리전)
    //             .depth(0)
    //             .build();

    //     Long savedId = service.add(qna);
    //     Qna foundQna = service.findBy(savedId);

    //     foundQna = Qna.builder()
    //             .no(foundQna.getNo()) // 기존 ID 유지
    //             .member(foundQna.getMember())
    //             .content("수정된 문의")
    //             .status(Qna.QnaStatus.처리후)
    //             .depth(foundQna.getDepth())
    //             .build();

    //     service.modify(foundQna);
    //     Qna modifiedQna = service.findBy(savedId);

    //     assertEquals("수정된 문의", modifiedQna.getContent());
    //     assertEquals(Qna.QnaStatus.처리후, modifiedQna.getStatus());
    // }

    // @Test
    // void testRemove() {
    //     Qna qna = Qna.builder()
    //             .member(existingMember)
    //             .content("삭제할 문의")
    //             .status(Qna.QnaStatus.처리전)
    //             .depth(0)
    //             .build();

    //     Long savedId = service.add(qna);
    //     boolean isDeleted = service.remove(savedId);
    //     Qna deletedQna = service.findBy(savedId);

    //     assertTrue(isDeleted);
    //     assertNull(deletedQna);
    // }
}
