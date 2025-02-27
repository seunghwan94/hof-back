package com.lshwan.hof.domain.entity.common;

import com.lshwan.hof.domain.entity.BaseEntityRegDate;
import com.lshwan.hof.domain.entity.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Getter
@Table(name = "tbl_likes", uniqueConstraints = {
    @UniqueConstraint(name = "tbl_likes_index_0", columnNames = {"mno", "target_no", "target_tpye"})
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Likes extends BaseEntityRegDate {

    @Embeddable
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LikesId implements Serializable {
        private Long member;
        private Long targetNo;
        
        @Enumerated(EnumType.STRING)
        @Column(name = "target_type")
        private TargetType targetType;
    }

    @EmbeddedId
    private LikesId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("member")
    @JoinColumn(name = "mno", nullable = false, foreignKey = @ForeignKey(name = "fk_likes_member"))
    private Member member; 

    public enum TargetType {
        NOTE, PROD, REVIEW, FAV
    }
}
