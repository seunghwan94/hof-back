package com.lshwan.hof.domain.entity.prod;

import java.util.List;

import com.lshwan.hof.domain.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder.Default;

@Entity
@Getter
@Setter
@Table(name = "tbl_prod")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Prod extends BaseEntity{
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno; // 상품 번호 (Primary Key)

    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "cno", nullable = false)
    private ProdCategory cno; 

    @Column(nullable = false)
    private String title; // 상품 제목

    @Column(columnDefinition = "LONGTEXT")
    private String content; // 상품 내용
    
    @Default
    @Column(nullable = false)
    private int price = 0; // 가격 (기본값 0)
    
    @Default
    @Column(nullable = false)
    private int stock = 0; // 재고 (기본값 0)

    // @OneToMany(mappedBy = "prod", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "prod",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdOptionMap> optionMaps; // 옵션 매핑 리스트
}
