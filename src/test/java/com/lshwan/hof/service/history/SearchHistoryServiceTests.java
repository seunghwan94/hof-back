package com.lshwan.hof.service.history;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.history.mongo.HistorySearch;


import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
@Transactional
public class SearchHistoryServiceTests {

    @Autowired
    private HistorySearchService service;

    @Test
    @Transactional
    void addTest() {


      HistorySearch historySearch = HistorySearch.builder()
      .mno(7L)
      .keyword("가구")
      .cno(4L)
      .build();
      String aaa = service.add(historySearch);


      assertNotNull(aaa); // ID가 null이면 안됨

      HistorySearch savedHistory = service.findBy(aaa);
      assertNotNull(savedHistory); // 저장된 객체가 존재해야 함

      // service.list();
    }
    @Test
    void testlist() {
 // when
    List<HistorySearch> list = service.findList();

    // then
    assertNotNull(list);
    assertTrue(list.size() > 0);
    log.info(list);
    }
    @Test
    void findBy() {

        // HistorySearch historySearch = HistorySearch.builder()
        // .mno(7L)
        // .keyword("가구")
        // .cno(4L)
        // .build();
        // String generatedId = service.add(historySearch);


        HistorySearch found = service.findBy("67ad9c13a83f035231c1d009");

        assertNotNull(found); // 검색 결과가 null이 아니어야 함
    }

    @Test
    @Transactional
    void modify() {
        HistorySearch search = HistorySearch.builder()
        .keyword("주옥같네")
        .mno(1L)
        .cno(1L)
        .build();
        String id = service.add(search);

        HistorySearch modiserch = HistorySearch.builder()
        .id(id)
        .keyword("수정된 키워드")
        .build();
        String modino = service.modify(modiserch);

        HistorySearch modifiedHistory = service.findBy(modino);

        assertNotNull(modifiedHistory);
    }


    @Test
    @Transactional
    void remove() {

        HistorySearch historySearch = HistorySearch.builder()
        .mno(7L)
        .keyword("가구자코코테스트용")
        .cno(4L)
        .build();
        String generatedId = service.add(historySearch);
        boolean isDeleted = service.remove(generatedId);

        assertTrue(isDeleted);
        assertNull(service.findBy(generatedId));
    }

}
