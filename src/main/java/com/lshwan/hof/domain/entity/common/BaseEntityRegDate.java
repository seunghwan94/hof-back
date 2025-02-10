package com.lshwan.hof.domain.entity.common;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;

@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@ToString
public abstract class BaseEntityRegDate {
  @CreatedDate
  @Column(name = "reg_date", updatable = false)
  private LocalDateTime regDate;
}
