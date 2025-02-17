package com.lshwan.hof.service.common;

import com.lshwan.hof.domain.entity.common.Qna;
import java.util.List;

public interface QnaService {
    Long add(Qna qna);
    Qna findBy(Long no);
    List<Qna> findList();
    Long modify(Qna qna);
    boolean remove(Long no);
}