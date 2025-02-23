// package com.lshwan.hof.repository;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import com.lshwan.hof.repository.note.ReplyRepository;

// import lombok.extern.log4j.Log4j2;

// @SpringBootTest
// @Log4j2
// public class ReplyRepositoryTests {
//   @Autowired
//   private ReplyRepository repository;

//   @Test
//   public void findAll() {
//     log.info(repository.findAll());
//   }
//   @Test
//   public void findByNoteNnoOrderByReplyOrderAscTests() {
//     log.info(repository.findByNoteNnoOrderByReplyOrderAsc(1L));
//   }
//   @Test
//   public void findByMemberMnoTests() {
//     log.info(repository.findByMemberMno(24L));
//   }
//   @Test
//   public void findByParentReplyNoTests() {
//     log.info(repository.findByParentReplyNo(2L));
//   }
//   @Test
//   public void findByIsDeletedFalseAndNoteNnoTests() {
//     log.info(repository.findByIsDeletedFalseAndNoteNno(1L));
//   }
// }
