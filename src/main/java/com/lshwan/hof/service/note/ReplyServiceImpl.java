package com.lshwan.hof.service.note;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.dto.note.ReplyDto;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.note.Note;
import com.lshwan.hof.domain.entity.note.Reply;
import com.lshwan.hof.repository.member.MemberRepository;
import com.lshwan.hof.repository.note.NoteRepository;
import com.lshwan.hof.repository.note.ReplyRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService {
  
  private final ReplyRepository replyRepository;
  private final NoteRepository noteRepository;
  private final MemberRepository memberRepository;
  
  // 댓글 작성
  @Override
  public ReplyDto add(ReplyDto replyDto) {
    Note note = noteRepository.findById(replyDto.getNno())
                .orElseThrow(() -> new EntityNotFoundException("Note not found"));

        Member member = memberRepository.findById(replyDto.getMno())
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        Reply parentReply = null;
        int depth = 0;

        if (replyDto.getParentReplyId() != null) {
            parentReply = replyRepository.findById(replyDto.getParentReplyId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent reply not found"));
            depth = parentReply.getDepth() + 1;
        }

        Reply reply = Reply.builder()
                .note(note)
                .member(member)
                .parentReply(parentReply)
                .content(replyDto.getContent())
                .depth(depth)
                .build();

        replyRepository.save(reply);

        return ReplyDto.builder()
                .replyId(reply.getNo())
                .nno(note.getNno())
                .mno(member.getMno())
                .memberName(member.getName())
                .content(reply.getContent())
                .createdAt(reply.getRegDate().toString())
                .parentReplyId(parentReply != null ? parentReply.getNo() : null)
                .depth(depth)
                .isDeleted(false)
              .build();
  }

  // 댓글 목록 조회
  @Override
  public List<ReplyDto> findList(Long nno) {
     List<Reply> replies = replyRepository.findByNoteNnoOrdered(nno);

    return replies.stream().map(reply -> ReplyDto.builder()
            .replyId(reply.getNo())
            .nno(reply.getNote().getNno())
            .mno(reply.getMember().getMno())
            .memberName(reply.getMember().getName())
            .content(reply.isDeleted() ? "삭제된 댓글입니다." : reply.getContent())
            .createdAt(reply.getRegDate().toString())
            .parentReplyId(reply.getParentReply() != null ? reply.getParentReply().getNo() : null)
            .depth(reply.getDepth())
            .isDeleted(reply.isDeleted())
          .build()).collect(Collectors.toList());
  }

  // 댓글 삭제 (Soft Delete)
  @Override
  public void remove(Long replyId) {
    Reply reply = replyRepository.findById(replyId)
        .orElseThrow(() -> new EntityNotFoundException("Reply not found"));
    reply.setIsDeleted(true);
    replyRepository.save(reply);
  }
}
