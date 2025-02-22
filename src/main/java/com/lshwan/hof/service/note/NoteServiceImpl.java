package com.lshwan.hof.service.note;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.note.Note;
import com.lshwan.hof.repository.member.MemberRepository;
import com.lshwan.hof.repository.note.NoteRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService{
  @Autowired
  private NoteRepository noteRepository;
  @Autowired
  private MemberRepository memberRepository;

  // 게시글 작성
  @Override
  @Transactional
  public Note add(Long mno, String title, String content) {
    Member member = memberRepository.findById(mno)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

    Note note = Note.builder()
                  .member(member)
                  .title(title)
                  .content(content)
                .build();

    return noteRepository.save(note);
  }

  // 게시글 조회 (단일)
  @Override
  public Note findBy(Long nno) {
    return noteRepository.findById(nno)
      .orElseThrow(() -> new EntityNotFoundException("Note not found"));
  }

  // 게시글 목록 조회
  @Override
  public List<Note> findList() {
    return noteRepository.findByIsDeletedFalse();
  }

  // 게시글 수정
  @Override
  @Transactional
  public Note modify(Long nno, String title, String content) {
    Note note = findBy(nno);
    note.update(title, content); // Note 엔티티에 update 메서드 추가 필요
    return noteRepository.save(note);
  }

  // 게시글 삭제 (Soft Delete)
  @Override
  @Transactional
  public void remove(Long nno) {
    Note note = findBy(nno);
    note.setIsDeleted(true);
    noteRepository.save(note);
  }
  
}
