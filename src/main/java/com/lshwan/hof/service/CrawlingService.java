package com.lshwan.hof.service;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class CrawlingService {
  private static final String URL = "https://www.coupang.com/vp/products/8448444218?itemId=24440358238&vendorItemId=82486781053&sourceType=CATEGORY&categoryId=184462&isAddedCart=";

  public String fetchProductTitle() {
    ChromeOptions options = new ChromeOptions();
    
    // 새로운 헤드리스 모드 설정
    options.addArguments("--headless=new");
    
    // User Agent 설정 (헤드리스 문자열 제거된 버전)
    options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
    
    // 기본 옵션 설정
    options.addArguments("--disable-gpu");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--window-size=1920,1080");
    options.addArguments("--start-maximized");
    
    // 자동화 감지 방지
    options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
    options.setExperimentalOption("useAutomationExtension", false);
    
    // 추가 설정
    Map<String, Object> prefs = new HashMap<>();
    prefs.put("profile.default_content_setting_values.notifications", 2);
    prefs.put("credentials_enable_service", false);
    prefs.put("profile.password_manager_enabled", false);
    options.setExperimentalOption("prefs", prefs);

    WebDriver driver = null;

    try {
      driver = new ChromeDriver(options);
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
      
      driver.get("https://www.coupang.com");
      // 실제 상품 페이지 접속
      driver.get(URL);
      // JavaScript 실행 완료 대기
      wait.until(webDriver -> ((JavascriptExecutor) webDriver)
          .executeScript("return document.readyState").equals("complete"));
      
      // 상품명
      WebElement element = null;
      element = wait.until(ExpectedConditions.presenceOfElementLocated(
        By.xpath("//*[@id=\"contents\"]/div[2]/div[1]/div[3]/div[3]/h1")
      ));
      String text = element.getText();

      return text;

    } catch (TimeoutException e) {
      return "크롤링 중 오류 발생: 페이지 구조가 변경되었거나 차단되었을 수 있습니다."; 
    } catch (Exception e) {
      return "크롤링 중 오류 발생: " + e.getMessage();
    } finally {
      if (driver != null) {
        driver.quit();
      }
    }
  }
}