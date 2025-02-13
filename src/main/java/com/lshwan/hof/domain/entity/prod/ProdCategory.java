package com.lshwan.hof.domain.entity.prod;

import com.lshwan.hof.domain.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "tbl_prod_category")  // 테이블 명 명시
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdCategory extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;  // 카테고리 번호 (Primary Key)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type;  // 알림 유형


    public enum CategoryType {
        침대, 의자, 책상, 수납장, 옷장, 기타
    }

}
