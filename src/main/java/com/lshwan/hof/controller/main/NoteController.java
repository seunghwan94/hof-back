package com.lshwan.hof.controller.main;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lshwan.hof.domain.dto.note.NoteDto;
import com.lshwan.hof.service.note.NoteService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("main/notes")
public class NoteController {
  private final NoteService noteService;

  // 게시글 목록 조회
  @GetMapping
  public ResponseEntity<List<?>> getAllNotes() {
    List<NoteDto> notes  = noteService.findList();
    return ResponseEntity.ok(notes);
  }
  
    // 게시글 단일 조회
    @GetMapping("/{nno}")
    public ResponseEntity<?> getNote(@PathVariable Long nno) {
        NoteDto note = noteService.findBy(nno);
        return ResponseEntity.ok(note);
    }

  // 게시글 작성
  @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public ResponseEntity<?> createNote(@ModelAttribute NoteDto noteDto) {
      NoteDto CreateNote = noteService.add(noteDto);
      return ResponseEntity.status(HttpStatus.CREATED).body(CreateNote);
  }

  // 게시글 수정
  @PutMapping("/{nno}")
  public ResponseEntity<?> updateNote(@PathVariable Long nno,
                                          @RequestParam String title,
                                          @RequestParam String content) {
    NoteDto updatedNote = noteService.modify(nno, title, content);
    return ResponseEntity.ok(updatedNote);
  }

  // 게시글 삭제 (Soft Delete)
  @DeleteMapping("/{nno}")
  public ResponseEntity<?> deleteNote(@PathVariable Long nno) {
    noteService.remove(nno);
    return ResponseEntity.noContent().build();
  }
}
