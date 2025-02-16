package com.lshwan.hof.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lshwan.hof.domain.entity.common.FileMaster;
import com.lshwan.hof.repository.common.FileMasterRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class FileMasterRepositoryTests {
  @Autowired
  private FileMasterRepository repository;
  
  @Test
  public void findAll() {
    log.info(repository.findAll());
  }

  @Test
  public void findByProdPnoAndFileTypeTest() {
    log.info(repository.findByProdPnoAndFileType(452L, FileMaster.FileType.prod_main));
  }
}
