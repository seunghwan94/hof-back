package com.lshwan.hof.domain.entity.admin;

import com.lshwan.hof.domain.entity.BaseEntityRegDate;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "금지단어index",example = "1")
    private Long fno; 
    @Schema(description = "금지단어 내용",example = "비속어")
    @Column(nullable = false, unique = true)
    private String content; // 금지 단어

    @Column(name = "is_active", nullable = false)
    @Default
    private Boolean isActive = true;
}

