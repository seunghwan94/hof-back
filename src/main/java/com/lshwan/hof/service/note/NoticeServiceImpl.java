package com.lshwan.hof.service.note;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.dto.note.NoticeDto;
import com.lshwan.hof.domain.entity.common.FileMaster;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.notice.Notice;
import com.lshwan.hof.repository.common.FileMasterRepository;
import com.lshwan.hof.repository.member.MemberRepository;
import com.lshwan.hof.repository.note.NoticeRepository;
import com.lshwan.hof.service.login.MemberService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NoticeServiceImpl implements NoticeService{

  @Autowired
  private NoticeRepository noticeRepository;
  @Autowired
  private  FileMasterRepository fileMasterRepository;
  @Autowired
  private MemberService memberService;
  @Override
  @Transactional
  public Long add(NoticeDto noticeDto) {
    Member member = memberService.findBy(noticeDto.getMemberId());
    if (member == null) {
      throw new IllegalArgumentException("존재하지 않는 회원입니다.");
    }

    Notice notice = Notice.builder()
        .member(member)
        .title(noticeDto.getTitle())
        .content(noticeDto.getContent())
        .clickUrl(noticeDto.getClickUrl())
        .backgroundColor(noticeDto.getBackgroundColor())
        .build();

    noticeRepository.save(notice);


    //    if (noticeDto.getFileUrl() != null) {
    //     FileMaster file = FileMaster.builder()
    //             .uuid(UUID.randomUUID().toString())
    //             .notice(notice)
    //             .url(noticeDto.getFileUrl())
    //             .fileType(FileMaster.FileType.prod_main)
    //             .build();

    //     fileMasterRepository.save(file);
    // }
       // ✅ FileMaster의 notice_no 업데이트 (fileUrl로 조회)
    if (noticeDto.getFileUrl() != null) {
      Optional<FileMaster> fileMasterOpt = fileMasterRepository.findByUrl(noticeDto.getFileUrl());
      if (fileMasterOpt.isPresent()) {
          FileMaster fileMaster = fileMasterOpt.get();
          fileMaster.setNotice(notice); 
          fileMasterRepository.save(fileMaster);
      } else {
          System.out.println("❌ 파일마스터 없음 (fileUrl: " + noticeDto.getFileUrl() + ")");
      }
  }


    return notice.getNo();
  }

  @Override
  public NoticeDto findBy(Long nno) {
        Notice notice = noticeRepository.findById(nno)
                .orElseThrow(() -> new IllegalArgumentException("공지사항이 존재하지 않습니다."));

        // 단일 파일 조회
        Optional<FileMaster> fileMaster = fileMasterRepository.findByNotice_No(nno).stream().findFirst();
        String fileUrl = fileMaster.map(FileMaster::getUrl).orElse(null);


        return todto(notice,fileUrl);
  }

  @Override
  public List<NoticeDto> findList() {
      return noticeRepository.findAll().stream()
      .map(notice -> {
           Optional<FileMaster> fileMaster = fileMasterRepository.findByNotice_No(notice.getNo()).stream().findFirst();
           String fileUrl = fileMaster.map(FileMaster::getUrl).orElse(null);

           return todto(notice, fileUrl);
      })
      .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public Long modify(NoticeDto noticeDto) {
        Notice notice = noticeRepository.findById(noticeDto.getNo())
        .orElseThrow(() -> new IllegalArgumentException("공지사항이 존재하지 않습니다."));

        Member member = memberService.findBy(noticeDto.getMemberId());
        if (member == null) {
        throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        notice = Notice.builder()
            .no(noticeDto.getNo())
            .member(member)
            .title(noticeDto.getTitle())
            .content(noticeDto.getContent())
            .clickUrl(noticeDto.getClickUrl())
            .backgroundColor(noticeDto.getBackgroundColor())
            .build();

        noticeRepository.save(notice);

        // 기존 파일이 있으면 삭제 후 새로운 파일 저장
        if (noticeDto.getFileUrl() != null) {
        fileMasterRepository.findByNotice_No(noticeDto.getNo()).forEach(fileMasterRepository::delete);

        FileMaster file = FileMaster.builder()
                .uuid(UUID.randomUUID().toString())
                .notice(notice)
                .url(noticeDto.getFileUrl())
                .fileType(FileMaster.FileType.prod_main)
                .build();

        fileMasterRepository.save(file);
        }

    return notice.getNo();
}
  @Override
  @Transactional
  public boolean remove(Long nno) {
    if (noticeRepository.existsById(nno)) {
      
      fileMasterRepository.findByNotice_No(nno).forEach(fileMasterRepository::delete);
      noticeRepository.deleteById(nno);
      return true;
  }
  return false;
}
  
}
