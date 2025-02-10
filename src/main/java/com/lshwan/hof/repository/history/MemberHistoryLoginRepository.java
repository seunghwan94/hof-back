package com.lshwan.hof.repository.history;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lshwan.hof.domain.entity.history.MemberHistoryLogin;

public interface MemberHistoryLoginRepository extends JpaRepository<MemberHistoryLogin,Long>{
    
}
