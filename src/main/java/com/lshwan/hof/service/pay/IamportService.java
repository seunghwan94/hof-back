package com.lshwan.hof.service.pay;

import java.util.Map;

public interface IamportService {
  String getAccessToken();
  Map<String, Object> validatePayment(String impUid);
}
