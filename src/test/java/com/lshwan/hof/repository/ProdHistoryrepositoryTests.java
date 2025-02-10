package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.history.ProdHistory;
import com.lshwan.hof.domain.entity.history.ProdHistory.ProductAction;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.prod.Prod;
import com.lshwan.hof.repository.history.ProdHistoryRepository;

@SpringBootTest
public class ProdHistoryrepositoryTests {
  @Autowired
  ProdHistoryRepository repository;
  @Test
  public void insertTest(){
    ProdHistory history = ProdHistory.builder()
    .product(Prod.builder().pno(1L).build())
    .admin(Member.builder().mno(5L).build())
    .action(ProductAction.PRICE_UPDATED)
    .prevValue("으헤헤헤")
    .newValue("이제끝났다")
    .build();
    repository.save(history);
  }
  @Test
  public void findAll(){
    repository.findAll();
  }
}
