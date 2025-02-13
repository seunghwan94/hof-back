package com.lshwan.hof.repository.mongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lshwan.hof.domain.entity.history.mongo.HistorySearch;

@Repository
public interface HistorySearchRepository extends MongoRepository<HistorySearch, String>{
  
}
