package com.lshwan.hof.service.login;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

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
  // @Autowired
  // private EmailService emailService;
  @Autowired
  private MemberDetailRepository memberDetailRepository;

  // private SignupRequestDto signupRequestDto;

    // 랜덤 6자리 숫자 생성 메소드
  private String generateVerificationCode() {
    Random random = new Random();
    int code = 100000 + random.nextInt(900000);  // 6자리 랜덤 숫자 생성
    return String.valueOf(code);
  }

  private static Pattern ID_PATTERN = Pattern.compile("^[a-z0-9]{5,20}$");
  private static Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");// 영문, 숫자 포함, 8자 이상
  private static Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{1,7}$");


  @Override
  @Transactional
  public Long write(Member member) { 
    // 아이디 검증
    if(!ID_PATTERN.matcher(member.getId()).matches()) {
        throw new IllegalArgumentException("아이디는 5~10자의 영문 소문자와 숫자만 가능합니다."); 
    }

    // 비밀번호 검증
    if (!PASSWORD_PATTERN.matcher(member.getPw()).matches()) {
        throw new IllegalArgumentException("비밀번호는 영문, 숫자를 포함하여 8자 이상이어야 합니다.");
    }

    // 이메일 검증
    if (!EMAIL_PATTERN.matcher(member.getMemberDetail().getEmail()).matches()) {
        throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
    }

    // 1. ID 중복 체크
    if(repository.findByLoginId(member.getId()).isPresent()) {
        throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
    }

    // 2. 이메일 인증 상태 확인
    String email = member.getMemberDetail().getEmail();
    if (!isEmailVerified(email)) {
        throw new IllegalArgumentException("이메일 인증이 완료되지 않았습니다. 인증 후 회원가입을 진행해주세요.");
    }

    // 이메일 인증이 완료되었으면, 이메일 중복 여부 확인
    Optional<MemberDetail> existingMemberDetail = memberDetailRepository.findByEmail(email);
    if (existingMemberDetail.isPresent()) {
        // 이미 이메일이 존재하면 이메일을 건너뛰고 회원가입 처리
        member.setMemberDetail(existingMemberDetail.get());
    }

    // 비밀번호 암호화
    member.setPw(passwordEncoder.encode(member.getPw()));

    // 3. 회원 정보 저장
    Member savedMember = repository.save(member); // 회원 저장 후 mno 값 설정됨

    // 4. 회원의 mno 값 가져오기
    Long mno = savedMember.getMemberDetail().getMno();
    if (mno == null) {
        throw new IllegalStateException("회원의 mno 값이 null입니다. 회원 등록에 실패했습니다.");
    }

    member.getMemberDetail().setMember(savedMember);  // MemberDetail에 Member 설정

    // 5. 이메일 인증 정보 저장 (이미 존재하는 경우 건너뛰기)
    emailVerificationRepository.findByEmail(email)
            .orElseGet(() -> {
                EmailVerification newEmailVerification = new EmailVerification();
                newEmailVerification.setEmail(email);
                newEmailVerification.setVerificationCode(generateVerificationCode());
                newEmailVerification.setExpiresAt(LocalDateTime.now().plusHours(3)); // 만료 시간 설정
                newEmailVerification.setVerified(true); // 이미 인증된 상태로 처리
                newEmailVerification.setMemberDetail(savedMember.getMemberDetail());
                return emailVerificationRepository.save(newEmailVerification);
            });

    // 이메일 인증 성공 후 완료
    return savedMember.getMno();  // 회원의 mno 반환
}
  // public Long write(Member member) { 
  //   // 아이디 
  //   if(!ID_PATTERN.matcher(member.getId()).matches()) {
  //     throw new IllegalArgumentException("아이디는 5~10자의 영문 소문자와 숫자만 가능합니다."); 
  //   }
  //   // 비밀번호
  //   if (!PASSWORD_PATTERN.matcher(member.getPw()).matches()) {
  //     throw new IllegalArgumentException("비밀번호는 영문, 숫자를 포함하여 8자 이상이어야 합니다.");
  //   }  

  //   // 이메일
  //   if (!EMAIL_PATTERN.matcher(member.getMemberDetail().getEmail()).matches()) {
  //     throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
  //   }

  //   // 1. ID 중복 체크
  //   if(repository.findByLoginId(member.getId()).isPresent()) {
  //     throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
  //   }

  //   // 2. 이메일 중복 체크
  //   if (memberDetailRepository.findByEmail(member.getMemberDetail().getEmail()).isPresent()) {
  //     throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
  //   }


  //   // 2. 이메일 인증 상태 확인
  //   String email = member.getMemberDetail().getEmail();
  //   if (!isEmailVerified(email)) {
  //       throw new IllegalArgumentException("이메일 인증이 완료되지 않았습니다. 인증 후 회원가입을 진행해주세요.");
  //   }

  //   // 이메일 인증이 완료되었으면, 이메일 중복 여부 확인
  //   Optional<MemberDetail> existingMemberDetail = memberDetailRepository.findByEmail(email);
  //   if (existingMemberDetail.isPresent()) {
  //       // 이미 이메일이 존재하면 이메일을 건너뛰고 회원가입 처리
  //       member.setMemberDetail(existingMemberDetail.get());
  //   }

  //   // 3. 비밀번호 암호화
  //   member.setPw(passwordEncoder.encode(member.getPw()));

  //   // 4. 회원 저장 후 mno 값 확보
  //   MemberDetail memberDetail = member.getMemberDetail();
  //   memberDetail.setMember(member);  // MemberDetail에 Member를 설정

  //   // Member 저장
  //   Member savedMember = repository.save(member);  // 회원 저장 후 mno 값이 설정됨

  //   // 5. 이메일 인증 토큰 생성 및 저장
  //   // String emailToken = UUID.randomUUID().toString();
  //   String emailVerificationCode = generateVerificationCode();
  //   LocalDateTime expiresAt = LocalDateTime.now().plusHours(1); // 만료시간 설정

  //   // 6. 이메일 인증을 위한 mno 값 설정
  //   Long mno = savedMember.getMemberDetail().getMno(); 
  //   // Long mno = savedMember.getMno(); // 디테일테스트

  //   if (mno == null) {
  //     throw new IllegalStateException("회원의 mno 값이 null입니다. 회원 등록에 실패했습니다.");
  //   }

  //   // EmailVerification 객체 생성 및 설정
  //   EmailVerification emailVerification = new EmailVerification();
  //   emailVerification.setEmail(member.getMemberDetail().getEmail());  // 이메일 설정
  //   emailVerification.setVerificationCode(emailVerificationCode);
  //   emailVerification.setExpiresAt(expiresAt);
  //   emailVerification.setVerified(false);  // 초기 인증 상태는 false
  //   emailVerification.setMemberDetail(savedMember.getMemberDetail());  // MemberDetail 객체 설정

  //   // 이메일 인증 정보 저장
  //   emailVerificationRepository.save(emailVerification);  

  //   // 7. 이메일 인증 링크 발송
  //   // emailService.sendEmail(member.getMemberDetail().getEmail(), "[가구의 집] 이메일 인증 확인", "이메일 인증 링크: " + "http://localhost:8080/api/v1/signup/verify?verificationCode=" + emailToken );
  //   // 7. 이메일 인증 코드 발송
  //   emailService.sendEmail(member.getMemberDetail().getEmail(), "[가구의 집] 이메일 인증 확인", "이메일 인증 코드: " + emailVerificationCode);

  //   return savedMember.getMno();  // 회원의 mno 반환
  // }


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

  // 아이디 중복 체크
  @Override
  public boolean isIdAvailable(String id) {
      return repository.findByLoginId(id).isEmpty(); // 아이디가 없으면 사용 가능
  }
  
  private boolean isEmailVerified(String email) {
    // 이메일 인증 상태 확인 (Optional로 반환)
    Optional<EmailVerification> emailVerificationOptional = emailVerificationRepository.findByEmail(email);

    // 인증된 이메일이 존재하고 verified 상태가 true인 경우만 true 반환
    return emailVerificationOptional
      .map(EmailVerification::isVerified)  // 인증 여부를 확인
      .orElse(false); // 인증되지 않았거나, 이메일이 없으면 false 반환
  }

}
