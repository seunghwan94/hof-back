package com.lshwan.hof.service.note;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lshwan.hof.domain.dto.note.NoteDto;
import com.lshwan.hof.domain.entity.common.FileMaster;
import com.lshwan.hof.domain.entity.common.Likes;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.note.Note;
import com.lshwan.hof.repository.common.FileMasterRepository;
import com.lshwan.hof.repository.common.LikesRepository;
import com.lshwan.hof.repository.member.MemberRepository;
import com.lshwan.hof.repository.note.NoteRepository;
import com.lshwan.hof.repository.note.ReplyRepository;
import com.lshwan.hof.service.S3Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class NoteServiceImpl implements NoteService{
  private NoteRepository noteRepository;
  private MemberRepository memberRepository;
  private FileMasterRepository fileMasterRepository;
  private final ReplyRepository replyRepository;
  private final LikesRepository likesRepository;

  // 게시글 작성
  @Override
  @Transactional
  public NoteDto add(NoteDto noteDto) {
      Member member = memberRepository.findById(noteDto.getMno())
              .orElseThrow(() -> new EntityNotFoundException("Member not found"));

      // 🔹 Note 생성 및 저장 (nno 생성)
      Note note = Note.builder()
              .member(member)
              .title(noteDto.getTitle())
              .content(noteDto.getContent())
            .build();

      noteRepository.save(note); // 먼저 저장하여 nno 생성
      
      List<String> uploadedImageUrls = noteDto.getImageUrls(); // URL 리스트 직접 사용

      if (uploadedImageUrls != null && !uploadedImageUrls.isEmpty()) {
          for (String imageUrl : uploadedImageUrls) {
              Optional<FileMaster> fileMasterOpt = fileMasterRepository.findByUrl(imageUrl);
              if (fileMasterOpt.isPresent()) {
                  FileMaster fileMaster = fileMasterOpt.get();
                  fileMaster.setNote(note); // Note와 연결
                  fileMasterRepository.save(fileMaster);
              } else {
                  log.warn("파일마스터 없음 (fileUrl: " + imageUrl + ")");
              }
          }
      }
      
      return NoteDto.builder()
              .nno(note.getNno()) // 생성된 nno 반환
              .mno(member.getMno())
              .memberName(member.getName())
              .title(note.getTitle())
              .content(note.getContent())
              .imageUrls(uploadedImageUrls) // 저장된 이미지 URL 반환
              .commentCount(0)
              .likeCount(0)
            .build();
    }

  

  // 게시글 조회 (단일)
  @Override
  public NoteDto findBy(Long nno) {
    Note note = noteRepository.findById(nno)
                .orElseThrow(() -> new EntityNotFoundException("Note not found"));

        // 댓글 수
        int commentCount = replyRepository.countByNoteNno(note.getNno());

        // 좋아요 수
        long likeCount = likesRepository.countByTarget(note.getNno(), Likes.TargetType.NOTE);

        // 이미지 URL 조회
        List<String> imageUrls = fileMasterRepository.findByNote(note).stream()
                .map(FileMaster::getUrl)
                .collect(Collectors.toList());

        return NoteDto.builder()
                .nno(note.getNno())
                .mno(note.getMember().getMno())
                .memberName(note.getMember().getName())
                .title(note.getTitle())
                .content(note.getContent())
                .imageUrls(imageUrls)
                .commentCount(commentCount)
                .likeCount((int) likeCount)
                .build();
  }

  // 게시글 목록 조회
  @Override
  public List<NoteDto> findList() {
    List<Note> notes = noteRepository.findByIsDeletedFalse();

        return notes.stream().map(note -> {
            int commentCount = replyRepository.countByNoteNno(note.getNno());
            long likeCount = likesRepository.countByTarget(note.getNno(), Likes.TargetType.NOTE);

            List<String> imageUrls = fileMasterRepository.findByNote(note).stream()
                    .map(FileMaster::getUrl)
                    .collect(Collectors.toList());

            return NoteDto.builder()
                    .nno(note.getNno())
                    .mno(note.getMember().getMno())
                    .memberName(note.getMember().getName())
                    .title(note.getTitle())
                    .content(note.getContent())
                    .imageUrls(imageUrls)
                    .commentCount(commentCount)
                    .likeCount((int) likeCount)
                    .build();
        }).collect(Collectors.toList());
  }

  // 게시글 수정
  @Override
  @Transactional
  public NoteDto modify(Long nno, String title, String content) {
    Note note = noteRepository.findById(nno)
                .orElseThrow(() -> new EntityNotFoundException("Note not found"));
        note.update(title, content); // Note 엔티티에 update 메서드 필요
        noteRepository.save(note);

        return findBy(nno); // 수정 후 최신 데이터 반환
  }

  // 게시글 삭제 (Soft Delete)
  @Override
  @Transactional
  public void remove(Long nno) {
    Note note = noteRepository.findById(nno)
                .orElseThrow(() -> new EntityNotFoundException("Note not found"));
    note.setIsDeleted(true);
    noteRepository.save(note);
  }
  
}
