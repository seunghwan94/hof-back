package com.lshwan.hof.service.note;

import java.util.List;

import com.lshwan.hof.domain.entity.note.Note;

public interface NoteService {

  Long add(Note note); 

  Note findBy(Long nno);

  List<Note> findList();

  Long modify(Note note);

  boolean remove(Long nno);
}
