package com.lshwan.hof.service.note;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.dto.note.NoticeDto;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.repository.common.FileMasterRepository;
import com.lshwan.hof.service.login.MemberService;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class NoticeServiceTests {
  @Autowired
    private NoticeService noticeService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private FileMasterRepository fileMasterRepository;

    @Test
    @Transactional
    public void addTest() {
        Member member = memberService.findBy("hof2");
        assertNotNull(member);


        NoticeDto noticeDto = NoticeDto.builder()
            .memberId(member.getId())
            .title("테스트 공지")
            .content("테스트 공지 내용")
            .clickUrl("https://example.com")
            .backgroundColor("#FFFFFF")
            .fileUrl("https://example.com/image.jpg")
            .build();

        Long nno = noticeService.add(noticeDto);
        assertNotNull(nno);
        log.info("공지사항 추가 완료: nno = {}", nno);
    }

    @Test
    public void findByTest() {

        Long nno = 34L;


        NoticeDto notice = noticeService.findBy(nno);


        assertNotNull(notice);
        assertNotNull(notice.getNo());
        assertNotNull(notice.getTitle());

        log.info("조회된 공지사항: {}", notice);
    }

    @Test
    public void findListTest() {

        List<NoticeDto> notices = noticeService.findList();


        assertNotNull(notices);
        assertTrue(notices.size() > 0);

        log.info("공지사항 리스트: {}", notices);
    }

    @Test
    @Transactional
    public void modifyTest() {

        Member member = memberService.findBy("hof2");
        assertNotNull(member);


        NoticeDto noticeDto = NoticeDto.builder()
            .memberId(member.getId())
            .title("수정 전 공지")
            .content("수정 전 공지 내용")
            .clickUrl("https://before.com")
            .backgroundColor("#000000")
            .fileUrl("https://before.com/image.jpg")
            .build();

        Long nno = noticeService.add(noticeDto);
        assertNotNull(nno);


        NoticeDto updatedNotice = NoticeDto.builder()
            .no(nno)
            .memberId(member.getId())
            .title("수정된 공지")
            .content("수정된 공지 내용")
            .clickUrl("https://after.com")
            .backgroundColor("#FF0000")
            .fileUrl("https://after.com/image.jpg")
            .build();

        Long updatedNno = noticeService.modify(updatedNotice);
        assertNotNull(updatedNno);

        NoticeDto result = noticeService.findBy(updatedNno);
        assertNotNull(result);
        assertTrue(result.getTitle().equals("수정된 공지"));

        log.info("수정된 공지사항: {}", result);
    }

    @Test
    @Transactional
    public void removeTest() {

        Member member = memberService.findBy("hof2");
        assertNotNull(member);


        NoticeDto noticeDto = NoticeDto.builder()
            .memberId(member.getId())
            .title("삭제할 공지")
            .content("삭제할 공지 내용")
            .clickUrl("https://delete.com")
            .backgroundColor("#123456")
            .fileUrl("https://delete.com/image.jpg")
            .build();

        Long nno = noticeService.add(noticeDto);
        assertNotNull(nno);


        boolean isRemoved = noticeService.remove(nno);
        assertTrue(isRemoved);

        log.info("삭제된 공지사항 번호: {}", nno);
    }

    @Test
    public void findRandomTest() {

        NoticeDto randomNotice = noticeService.findRandom();


        assertNotNull(randomNotice);
        assertNotNull(randomNotice.getNo());

        log.info("랜덤 조회된 공지사항: {}", randomNotice);
    }
}
