package com.lshwan.hof.service.login;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import com.lshwan.hof.domain.entity.email.EmailVerification;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.member.MemberDetail;
import com.lshwan.hof.repository.email.EmailVerificationRepository;
import com.lshwan.hof.repository.member.MemberDetailRepository;
import com.lshwan.hof.repository.member.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
  @Autowired
  private MemberRepository repository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private EmailVerificationRepository emailVerificationRepository;
  @Autowired
  private EmailService emailService;
  @Autowired
  private MemberDetailRepository memberDetailRepository;


  @Override
  @Transactional
  public Long write(Member member) { 
  // 1. ID 중복 체크
  if(repository.findByLoginId(member.getId()).isPresent()) {
    throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
  }

  // 2. 이메일 중복 체크
  if (memberDetailRepository.findByEmail(member.getMemberDetail().getEmail()).isPresent()) {
    throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
  }

  // 3. 비밀번호 암호화
  member.setPw(passwordEncoder.encode(member.getPw()));

  // 4. 회원 저장 후 mno 값 확보
  MemberDetail memberDetail = member.getMemberDetail();
  memberDetail.setMember(member);  // MemberDetail에 Member를 설정

  // =멤버디테일저장
  // MemberDetail savedMemberDetail = memberDetailRepository.save(memberDetail);

  // Member 저장
  Member savedMember = repository.save(member);  // 회원 저장 후 mno 값이 설정됨

  // 5. 이메일 인증 토큰 생성 및 저장
  String emailToken = UUID.randomUUID().toString();
  LocalDateTime expiresAt = LocalDateTime.now().plusHours(1); // 만료시간 설정

  // 6. 이메일 인증을 위한 mno 값 설정
  Long mno = savedMember.getMemberDetail().getMno(); 
  // Long mno = savedMember.getMno(); // 디테일테스트

  if (mno == null) {
    throw new IllegalStateException("회원의 mno 값이 null입니다. 회원 등록에 실패했습니다.");
  }

  // EmailVerification 객체 생성 및 설정
  EmailVerification emailVerification = new EmailVerification();
  emailVerification.setEmail(member.getMemberDetail().getEmail());  // 이메일 설정
  emailVerification.setVerificationCode(emailToken);
  emailVerification.setExpiresAt(expiresAt);
  emailVerification.setVerified(false);  // 초기 인증 상태는 false
  emailVerification.setMemberDetail(savedMember.getMemberDetail());  // MemberDetail 객체 설정

  // 이메일 인증 정보 저장
  emailVerificationRepository.save(emailVerification);  

  // 7. 이메일 인증 링크 발송
  emailService.sendEmail(member.getMemberDetail().getEmail(), "[가구의 집] 이메일 인증 확인", "이메일 인증 링크: " + "http://localhost:8080/api/v1/signup/verify?verificationCode=" + emailToken );

  return savedMember.getMno();  // 회원의 mno 반환
}

  //   // // 테스트
  //   // MemberDetail memberDetail = MemberDetail.builder()
  //   //   .email(member.getMemberDetail().getEmail())  // 회원가입 시 전달받은 이메일 설정
  //   //   .privacyConsent(false)  // 기본 값 설정, 실제로는 프론트에서 받아야 함
  //   //   .marketingConsent(false) // 기본 값 설정, 실제로는 프론트에서 받아야 함
  //   //   .allowNotification(true) // 기본 값 설정, 실제로는 프론트에서 받아야 함
  //   //   .gender(MemberDetail.MemberGender.OTHER)  // 기본 값 설정, 실제로는 프론트에서 받아야 함
  //   //   // .member(member) // Member와 연관 설정
  //   //   .build();

  //   // // Member 객체에 MemberDetail 객체 연결
  //   // member.setMemberDetail(memberDetail);
  //   // //

  @Override
  public Member findBy(String id) {    
    return repository.findByLoginId(id).orElse(null);
  }

  @Override
  public boolean login(String id, String pw) {
    Member m = findBy(id);
    return m != null && passwordEncoder.matches(pw, m.getPw());
  }

  @Override
  @Transactional
  public List<Member> findList() {
    return repository.findAll();
  }
  
}
