package com.lshwan.hof.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
public class FwlControllerTests {
   private MockMvc mockMvc;

   // 각각의 테스트 실행전 초기화
  @BeforeEach
  public void init(WebApplicationContext ctx) {
    mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
  }

  @Test
  public void testGetAllFWL() throws Exception {
    // GET
    mockMvc.perform(get("/admin/fwl")) 
      // 응답 상태
      .andExpect(status().isOk()) 
      // 응답json인지 확인
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)) 
    // 테스트 결과 출력
    .andDo(print()); 
  }

  @Test
  @Transactional
  public void testinsertFWL() throws Exception {
     String jsonContent = new ObjectMapper().writeValueAsString(Map.of("content", "침대zzzx"));
    mockMvc.perform(post("/admin/fwl")
      .contentType(MediaType.APPLICATION_JSON)
      // Query String 파라미터
      .content(jsonContent))
      .andExpect(status().isOk())
      .andExpect(content().contentType("text/plain;charset=UTF-8"))
      // .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andDo(print());
  }

  @Test
  @Transactional
  public void testUpdateFWL() throws Exception {
    int fno = 50;
    String jsonContent = new ObjectMapper().writeValueAsString(Map.of("content", "침대zzzvv"));
    mockMvc.perform(put("/admin/fwl/"+fno)
    .contentType(MediaType.APPLICATION_JSON)
      .content(jsonContent))
      .andExpect(status().isOk())
      .andExpect(content().contentType("text/plain;charset=UTF-8"))
      // .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    .andDo(print());
  }

  @Test
  @Transactional
  public void testDeleteFWL() throws Exception {
    Long pno = 50L;

    mockMvc.perform(delete("/admin/fwl/"+ pno))
      .andExpect(status().isOk()) // 200 OK 확인
      .andExpect(content().contentType("text/plain;charset=UTF-8"))
      // .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      // .andExpect(jsonPath("$.pno").value(pno)) // 응답 JSON에서 pno 값이 일치하는지 확인
      // .andExpect(jsonPath("$.title").exists()) // title 필드가 존재하는지 확인
      // .andExpect(jsonPath("$.content").exists()) // content 필드가 존재하는지 확인
    .andDo(print());
  }
}
