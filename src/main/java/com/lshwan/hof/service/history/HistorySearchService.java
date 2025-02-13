package com.lshwan.hof.service.history;


import java.util.List;


import com.lshwan.hof.domain.entity.history.mongo.HistorySearch;



public interface HistorySearchService {
  String add(HistorySearch search); 

  HistorySearch findBy(String id);

  List<HistorySearch> findList();

  String modify(HistorySearch search);

  boolean remove(String id);

}