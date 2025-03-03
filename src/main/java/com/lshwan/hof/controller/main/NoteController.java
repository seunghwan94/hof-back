package com.lshwan.hof.controller.main;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lshwan.hof.domain.dto.note.NoteDto;
import com.lshwan.hof.service.note.NoteService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@AllArgsConstructor
@RequestMapping("main/notes")
@Log4j2
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
    public ResponseEntity<?> getNote(@PathVariable(name = "nno") Long nno) {
        NoteDto note = noteService.findBy(nno);
        return ResponseEntity.ok(note);
    }

    // 게시글 작성
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createNote(
            @RequestParam(name = "mno") Long mno,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "content") String content,
            @RequestParam(name = "imageUrls", required = false) List<String> imageUrls) {
    
        NoteDto noteDto = new NoteDto();
        noteDto.setMno(mno);
        noteDto.setTitle(title);
        noteDto.setContent(content);
        noteDto.setImageUrls(imageUrls != null ? imageUrls : new ArrayList<>()); // ✅ Null 방지 처리
    
        log.info("noteDto : " + noteDto);
    
        NoteDto createdNote = noteService.add(noteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }



  // 게시글 수정
  @PutMapping("/{nno}")
  public ResponseEntity<?> updateNote(@PathVariable(name = "nno") Long nno,
                                          @RequestParam(name = "title") String title,
                                          @RequestParam(name = "content") String content) {
    NoteDto updatedNote = noteService.modify(nno, title, content);
    return ResponseEntity.ok(updatedNote);
  }

  // 게시글 삭제 (Soft Delete)
  @DeleteMapping("/{nno}")
  public ResponseEntity<?> deleteNote(@PathVariable(name = "nno") Long nno) {
    noteService.remove(nno);
    return ResponseEntity.noContent().build();
  }
}
