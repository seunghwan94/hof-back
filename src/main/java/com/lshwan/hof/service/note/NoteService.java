package com.lshwan.hof.service.note;

import java.util.List;

import com.lshwan.hof.domain.dto.note.NoteDto;

public interface NoteService {

  NoteDto add(NoteDto noteDto); 

  NoteDto findBy(Long nno);

  List<NoteDto> findList();

  NoteDto modify(Long nno, String title, String content);

  void remove(Long nno);
}
