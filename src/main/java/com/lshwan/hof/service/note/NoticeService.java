package com.lshwan.hof.service.note;

import java.util.List;

import com.lshwan.hof.domain.dto.note.NoticeDto;
import com.lshwan.hof.domain.entity.notice.Notice;


public interface NoticeService {
  Long add(NoticeDto NoticeDto); 

  NoticeDto findBy(Long nno);

  List<NoticeDto> findList();

  Long modify(NoticeDto NoticeDto);

  boolean remove(Long nno);

  default NoticeDto todto(Notice notice,String fileurl) {
        return NoticeDto.builder()
                .no(notice.getNo())
                .title(notice.getTitle())
                .memberId(notice.getMember().getId())
                .content(notice.getContent())
                .clickUrl(notice.getClickUrl())
                .backgroundColor(notice.getBackgroundColor())
                .fileUrl(fileurl) 
                .build();
    }
    default NoticeDto todto(Notice notice) {
      return NoticeDto.builder()
              .no(notice.getNo())
              .title(notice.getTitle())
              .memberId(notice.getMember().getId())
              .content(notice.getContent())
              .clickUrl(notice.getClickUrl())
              .backgroundColor(notice.getBackgroundColor())
              .build();
  }

    default Notice toEntity(NoticeDto dto) {
        return Notice.builder()
                .no(dto.getNo())
                .title(dto.getTitle())
                .content(dto.getContent())
                .clickUrl(dto.getClickUrl())
                .backgroundColor(dto.getBackgroundColor())

                .build();
    }
}
