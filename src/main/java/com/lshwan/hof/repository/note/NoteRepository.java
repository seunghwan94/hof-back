package com.lshwan.hof.repository.note;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.note.Note;

public interface NoteRepository extends JpaRepository<Note, Long>{
   
}