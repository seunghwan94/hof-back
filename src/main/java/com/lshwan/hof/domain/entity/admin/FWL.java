package com.lshwan.hof.domain.entity.admin;
import com.lshwan.hof.domain.entity.common.BaseEntityRegDate;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;

@Entity
@Table(name = "tbl_fwl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FWL extends BaseEntityRegDate{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fno; 

    @Column(nullable = false, unique = true)
    private String content; // 금지 단어

    @Column(name = "is_active", nullable = false)
    @Default
    private Boolean isActive = true;
}

