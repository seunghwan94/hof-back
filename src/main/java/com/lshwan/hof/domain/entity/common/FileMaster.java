package com.lshwan.hof.domain.entity.common;

import com.lshwan.hof.domain.entity.BaseEntity;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.prod.Prod;
import com.lshwan.hof.domain.entity.note.Note;
import com.lshwan.hof.domain.entity.notice.Notice;
import com.lshwan.hof.domain.entity.order.Review;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "tbl_file_master")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class FileMaster extends BaseEntity {

    @Id
    @Column(length = 255, nullable = false)
    private String uuid; // 파일 고유 식별자 (Primary Key)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno")
    private Member member; // 맴버 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nno")
    private Note note; // 게시판 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno")
    private Prod prod; // 상품 번호

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_no")
    private Review review; // 리뷰 번호
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_no")
    private Notice notice; // 리뷰 번호

    @Column(name = "origin")
    private String origin; // 원본 파일 이름

    @Column(name = "image", columnDefinition = "TINYINT")
    private Boolean image; // 이미지 여부

    @Column(name = "path")
    private String path; // 파일 경로

    @Column(name = "size")
    private Integer size; // 파일 크기 (바이트)

    @Column(name = "mime")
    private String mime; // 파일 유형

    @Column(name = "file_name")
    private String fileName; // 확장자 제외 파일명

    @Column(name = "ext")
    private String ext; // 확장자

    @Column(name = "url")
    private String url; // 실제 파일 URL

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private FileType fileType;

    public enum FileType {
        prod_main , prod_detail 
    }
}
