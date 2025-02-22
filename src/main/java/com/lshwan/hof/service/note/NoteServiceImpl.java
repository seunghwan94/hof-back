package com.lshwan.hof.service.note;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lshwan.hof.domain.dto.note.NoteDto;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.note.Note;
import com.lshwan.hof.repository.member.MemberRepository;
import com.lshwan.hof.repository.note.NoteRepository;
import com.lshwan.hof.service.S3Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService{
  private NoteRepository noteRepository;
  private MemberRepository memberRepository;
  private final S3Service s3Service;

  // 게시글 작성
  @Override
  @Transactional
  public Note add(NoteDto noteDto) {
    Member member = memberRepository.findById(noteDto.getMno())
          .orElseThrow(() -> new EntityNotFoundException("Member not found"));

    // Note 생성 및 저장
    Note note = Note.builder()
                  .member(member)
                  .title(noteDto.getTitle())
                  .content(noteDto.getContent())
                .build();

    noteRepository.save(note);

    // 이미지 업로드 및 URL 저장
    if (noteDto.getImages() != null && !noteDto.getImages().isEmpty()) {
      List<String> uploadedImageUrls = new ArrayList<>();
      for (MultipartFile image : noteDto.getImages()) {
        String imageUrl = s3Service.settingFile(image, "note", note);
        uploadedImageUrls.add(imageUrl);
      }
      noteDto.setImageUrls(uploadedImageUrls);  // 업로드된 이미지 URL을 DTO에 저장
    }

    return note;
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
