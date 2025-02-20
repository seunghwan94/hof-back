package com.lshwan.hof.domain.entity.prod;


import java.util.List;

import com.lshwan.hof.domain.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "tbl_prod_option")
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ProdOption extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;

  @Column(nullable = false)
  private String type; // 옵션 종류 (색상, 크기 등)

  @Column(nullable = false)
  private String value; // 옵션 값 (빨강,XL,나무 등)

  @Default
  @Column(nullable = false,name = "add_price")
  private int addPrice = 0; // 추가 가격 (기본값 : 0)

  // @OneToMany(mappedBy = "prod", cascade = CascadeType.ALL, orphanRemoval = true)
  @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProdOptionMap> optionMaps; // 옵션 매핑 리스트
  
  public void updateOption(String type, String value,int addPrice){
    this.type = type;
    this.value = value;
    this.addPrice = addPrice;
  }
}