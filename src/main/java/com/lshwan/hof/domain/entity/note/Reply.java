package com.lshwan.hof.domain.entity.note;
import com.lshwan.hof.domain.entity.BaseEntity;
import com.lshwan.hof.domain.entity.member.Member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Entity
@Getter
@Table(name = "tbl_reply")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nno", nullable = false)
    private Note note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno", nullable = false)
    private Member member; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_no")
    private Reply parentReply; 
    
    @Default
    @Column(nullable = false, columnDefinition = "int default 0")
    private int depth = 0; 

    @Column(name = "reply_order")
    private Integer replyOrder; 

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content; 

    @Default
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
}
