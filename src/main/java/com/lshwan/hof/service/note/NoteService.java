package com.lshwan.hof.service.note;

import java.util.List;

import com.lshwan.hof.domain.dto.note.NoteDto;
import com.lshwan.hof.domain.entity.note.Note;

public interface NoteService {

  Note add(NoteDto noteDto); 

  Note findBy(Long nno);

  List<Note> findList();

  Note modify(Long nno, String title, String content);

  void remove(Long nno);
}
