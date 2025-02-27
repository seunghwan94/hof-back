package com.lshwan.hof.service.login;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import com.lshwan.hof.domain.dto.member.AdminMemberDto;
import com.lshwan.hof.domain.dto.member.MemberDto;
import com.lshwan.hof.domain.entity.email.EmailVerification;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.member.MemberDetail;
import com.lshwan.hof.mapper.MemberMapper;
import com.lshwan.hof.repository.email.EmailVerificationRepository;
import com.lshwan.hof.repository.member.MemberDetailRepository;
import com.lshwan.hof.repository.member.MemberRepository;

@Service
@AllArgsConstructor
@Log4j2
@Transactional
public class MemberServiceImpl implements MemberService {

  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private MemberDetailRepository memberDetailRepository;
  @Autowired
  private EmailVerificationRepository emailVerificationRepository;

  private final MemberMapper memberMapper;
  @Autowired
  private PasswordEncoder passwordEncoder;

  private static Pattern ID_PATTERN = Pattern.compile("^[a-z0-9]{5,20}$");
  private static Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");// 영문, 숫자 포함, 8자 이상
  private static Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{1,7}$");


  @Override
  @Transactional
  public Long write(Member originMember) { 
    // 1. 아이디와 이메일 중복 체크
    validateMemberInfo(originMember, originMember.getMemberDetail()); // memberDetail을 `member`에서 가져옵니다.

    // 2. 이메일 인증 상태 확인
    if (!isEmailVerified(originMember.getMemberDetail().getEmail())) {
        throw new IllegalArgumentException("이메일 인증이 완료되지 않았습니다. 인증 후 회원가입을 진행해주세요.");
    }

    Optional<EmailVerification> memberEmail = emailVerificationRepository.findByEmail(originMember.getMemberDetail().getEmail());
    if(!memberEmail.isPresent()){
      throw new IllegalArgumentException("잘못된 회원가입 방법 진행중.");
    }
    EmailVerification emailVerification = memberEmail.get();
    Long mdno = emailVerification.getMemberDetail().getMdno();

    Optional<MemberDetail> md = memberDetailRepository.findById(mdno);
    if(!md.isPresent()){
      throw new IllegalArgumentException("잘못된 회원가입 방법 진행중.");
    }
    MemberDetail memberDetail = md.get();
    // 3. 비밀번호 암호화
    Optional<Member> memberOpt = memberRepository.findById(memberDetail.getMember().getMno());
    if(!memberOpt.isPresent()){
      throw new IllegalArgumentException("잘못된 회원가입 방법 진행중.");
    }
    Member member = memberOpt.get();

    // 이메일 인증 상태 확인 (이메일이 인증된 경우에만 처리)
    if (!emailVerification.isVerified()) {
        throw new IllegalArgumentException("이메일 인증이 완료되지 않았습니다. 인증 후 회원가입을 진행해주세요.");
    }

    memberDetail.setGender(originMember.getMemberDetail().getGender());
    memberDetail.setPrivacyConsent(originMember.getMemberDetail().isPrivacyConsent());
    memberDetail.setMarketingConsent(originMember.getMemberDetail().isMarketingConsent());
    memberDetail.setAllowNotification(originMember.getMemberDetail().isAllowNotification());

    member.setMno(memberDetail.getMember().getMno());
    member.setPw(passwordEncoder.encode(originMember.getPw()));
    member.setId(originMember.getId());
    member.setName(originMember.getName());
    
    
    // 7. Member 저장
    Member savedMember = memberRepository.save(member);


    log.info("회원 저장 후, mno: {}", savedMember.getMno());

    // 8. MemberDetail 저장
    // memberDetail.setMember(savedMember);  // Member 설정
    memberDetailRepository.save(memberDetail);

    return savedMember.getMno(); // 저장된 Member의 mno 반환

}


  @Override
  @Transactional
  public MemberDetail verificationBefore(String email) { 
    try {
      // 1. 이메일 인증 여부 확인 (EmailVerification 테이블에서 확인)
      Optional<EmailVerification> emailVerification = emailVerificationRepository.findByEmail(email);
      if (emailVerification.isPresent() && emailVerification.get().isVerified()) {
          // 이미 인증된 이메일이라면 예외 처리
          throw new IllegalArgumentException("이미 인증된 이메일입니다.");
      }

      // 2. 이메일이 이미 회원가입에 사용된 이메일인지 확인 (MemberDetail 테이블에서 확인)
      if (memberDetailRepository.findByEmail(email).isPresent()) {
          throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
      }

      // // 이메일 인증 여부 확인 (이메일이 이미 인증된 경우 예외 처리)
      // if (emailVerificationRepository.findByEmail(email).isPresent()) {
      //   throw new IllegalArgumentException("이미 인증 요청한 이메일입니다.");
      // }
    // 7. Member 저장 (필요한 값 설정 후 저장)
    Member savedMember = memberRepository.save(Member.builder().build());
    log.info("회원 저장 후, mno: {}", savedMember.getMno());

    // 8. MemberDetail에 Member 설정 및 mno 설정
    MemberDetail memberDetail = MemberDetail.builder()
                                            
                                             .email(email)
                                             .member(savedMember) // savedMember 설정
                                             .build();

    // 9. MemberDetail 저장
    MemberDetail savedMemberDetail = memberDetailRepository.save(memberDetail);

    return savedMemberDetail;
    } catch (Exception e) {
        log.error("회원 저장 중 예외 발생: {}", e.getMessage(), e);
        throw e;
    }
  }

  @Override
  public Member findBy(String id) {    
    return memberRepository.findByLoginId(id).orElse(null);
  }

  @Override
  public boolean login(String id, String pw) {
    Member m = findBy(id);
    return m != null && passwordEncoder.matches(pw, m.getPw());
  }

  @Override
  @Transactional
  public List<Member> findList() {
    return memberRepository.findAll();
  }

  @Override
    public List<AdminMemberDto> adminMemberList() {
        return memberMapper.findAllMembers();
    }

  // 아이디 중복 체크
  @Override
  public boolean isIdAvailable(String id) {
    return memberRepository.findByLoginId(id).isEmpty(); // 아이디가 없으면 사용 가능
  }

  private boolean isEmailVerified(String email) {
    // 이메일 인증 상태 확인 (Optional로 반환)
    Optional<EmailVerification> emailVerificationOptional = emailVerificationRepository.findByEmail(email);

    // 인증된 이메일이 존재하고 verified 상태가 true인 경우만 true 반환
    return emailVerificationOptional
      .map(EmailVerification::isVerified)  // 인증 여부를 확인
      .orElse(false); // 인증되지 않았거나, 이메일이 없으면 false 반환
  }


  private void validateMemberInfo(Member member, MemberDetail memberDetail) {
    // 아이디 형식 체크
    if (!ID_PATTERN.matcher(member.getId()).matches()) {
      throw new IllegalArgumentException("아이디는 5~10자의 영문 소문자와 숫자만 가능합니다.");
    }

    // 비밀번호 형식 체크
    if (!PASSWORD_PATTERN.matcher(member.getPw()).matches()) {
      throw new IllegalArgumentException("비밀번호는 영문, 숫자를 포함하여 8자 이상이어야 합니다.");
    }

    // 이메일 형식 체크
    if (!EMAIL_PATTERN.matcher(memberDetail.getEmail()).matches()) {
      throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
    }

    // 아이디 중복 체크
    if (!isIdAvailable(member.getId())) {
      throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
    }

  }
  @Override
  @Transactional
  public Long update(Member member) {
    // 1. 아이디와 이메일 중복 체크
    log.info("member1" + member);
    validateMemberInfo(member, member.getMemberDetail()); // memberDetail을 member에서 가져옵니다.

    // 2. 이메일 인증 상태 확인
    if (!isEmailVerified(member.getMemberDetail().getEmail())) {
      throw new IllegalArgumentException("이메일 인증이 완료되지 않았습니다. 인증 후 회원가입을 진행해주세요.");
    }

    // 3. 비밀번호 암호화
    member.setPw(passwordEncoder.encode(member.getPw()));
    log.info("member2 : " + member);

    // 이메일 인증된 상태의 EmailVerification 찾기
    Optional<EmailVerification> memberEmail = emailVerificationRepository.findByEmail(member.getMemberDetail().getEmail());
    if (!memberEmail.isPresent()) {
      throw new IllegalArgumentException("잘못된 회원가입 방법 진행중.");
    }
    Long mdno = memberEmail.get().getMemberDetail().getMdno();
    Optional<MemberDetail> md = memberDetailRepository.findById(mdno);
    if (!md.isPresent()) {
      throw new IllegalArgumentException("잘못된 회원가입 방법 진행중.");
    }
    log.info("member3" + member);
    log.info("member.getMemberDetail().getEmail() : " + member.getMemberDetail().getEmail());

    // 5. MemberDetail에 Member 설정
    MemberDetail memberDetail = md.get();
    memberDetail.setGender(member.getMemberDetail().getGender());
    memberDetail.setPrivacyConsent(member.getMemberDetail().isPrivacyConsent());
    memberDetail.setMarketingConsent(member.getMemberDetail().isMarketingConsent());
    memberDetail.setAllowNotification(member.getMemberDetail().isAllowNotification());

    // 4. Member 업데이트
    memberRepository.save(member); // 여기서 save는 기존 엔티티를 업데이트 처리합니다.
    
    // 5. MemberDetail 업데이트
    memberDetailRepository.save(memberDetail); // 이미 존재하는 데이터 업데이트
    
    return member.getMno(); // 업데이트된 Member의 mno 반환
  }
}
