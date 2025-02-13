package com.lshwan.hof.service.history;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.entity.history.mongo.HistorySearch;
import com.lshwan.hof.repository.mongo.HistorySearchRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class HistorySearchServiceImpl implements HistorySearchService{

  @Autowired
  private HistorySearchRepository historySearchRepository;
  @Override
  public String add(HistorySearch search) {
        return historySearchRepository.save(search).getId();
  }

  @Override
  public HistorySearch findBy(String id) {
    return historySearchRepository.findById(id).orElse(null);
  }

  @Override
  public List<HistorySearch> findList() {
    return historySearchRepository.findAll();
  }

  @Override
public String modify(HistorySearch search) {


    return historySearchRepository.save(search).getId();
}

  @Override
  public boolean remove(String id) {
    return historySearchRepository.findById(id)
    .map(existingHistory -> {
        historySearchRepository.delete(existingHistory);
        return true;
    })
    .orElse(false);
  }
  
}
