package com.lshwan.hof.service.note;

import java.util.List;

import org.springframework.stereotype.Service;

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
  public Reply add(Long nno, Long mno, String content, Long parentReplyId) {
    Note note = noteRepository.findById(nno)
                    .orElseThrow(() -> new EntityNotFoundException("Note not found"));

    Member member = memberRepository.findById(mno)
            .orElseThrow(() -> new EntityNotFoundException("Member not found"));

    Reply parentReply = null;
    int depth = 0;

    if (parentReplyId != null) {
        parentReply = replyRepository.findById(parentReplyId)
                .orElseThrow(() -> new EntityNotFoundException("Parent Reply not found"));
        depth = parentReply.getDepth() + 1;
    }

    Reply reply = Reply.builder()
                    .note(note)
                    .member(member)
                    .parentReply(parentReply)
                    .depth(depth)
                    .content(content)
                  .build();

    return replyRepository.save(reply);
  }

  // 댓글 목록 조회
  @Override
  public List<Reply> findList(Long nno) {
    return replyRepository.findByNoteNnoOrderByReplyOrderAsc(nno);
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
