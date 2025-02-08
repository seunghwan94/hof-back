package com.lshwan.hof.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
public abstract class BaseEntity {
  @CreatedDate
  @Column(name = "reg_date", updatable = false)
  private LocalDateTime regDate;

  @LastModifiedDate
  @Column(name = "mod_date")
  private LocalDateTime modDate;
}
