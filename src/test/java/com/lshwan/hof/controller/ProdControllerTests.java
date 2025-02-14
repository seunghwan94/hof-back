package com.lshwan.hof.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
public class ProdControllerTests {

  private MockMvc mockMvc;

  // 각각의 테스트 실행전 초기화
  @BeforeEach
  public void init(WebApplicationContext ctx) {
    mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
  }

  @Test
  public void testGetAllProd() throws Exception {
    // GET
    mockMvc.perform(get("/prod")) 
      // 응답 상태
      .andExpect(status().isOk()) 
      // 응답json인지 확인
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)) 
    // 테스트 결과 출력
    .andDo(print()); 
  }

  @Test
  public void testSearchByTitle() throws Exception {
    mockMvc.perform(get("/prod/search")
      // Query String 파라미터
      .param("title", "침대"))
      // 응답 상태태
      .andExpect(status().isOk())
      // 응답json인지 확인
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    // 테스트 결과 출력
    .andDo(print());
  }

  @Test
  public void testSearchByCno() throws Exception {
    mockMvc.perform(get("/prod/search")
        .param("cno", "1"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    .andDo(print());
  }
}
