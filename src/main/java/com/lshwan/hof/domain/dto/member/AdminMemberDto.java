package com.lshwan.hof.domain.dto.member;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminMemberDto {
  private Long mno;
  private String id;
  private String name;
  private String role;
  private String regDate;
  private String modDate;
  private String email;
  private String gender;
  private boolean privacyConsent;
  private boolean marketingConsent;
  private boolean allowNotification;
  private String zipcode;
  private String roadAddr;
  private String detailAddr;
  private boolean isDefault;

}
