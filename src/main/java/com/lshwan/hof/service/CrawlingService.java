package com.lshwan.hof.service;

import com.lshwan.hof.domain.entity.prod.Prod;
import com.lshwan.hof.domain.entity.prod.ProdCategory;
import com.lshwan.hof.domain.entity.prod.ProdOption;
import com.lshwan.hof.domain.entity.prod.ProdOptionMap;
import com.lshwan.hof.domain.entity.prod.ProdCategory.CategoryType;
import com.lshwan.hof.service.prod.ProdCategoryService;
import com.lshwan.hof.service.prod.ProdOptionMapService;
import com.lshwan.hof.service.prod.ProdOptionService;
import com.lshwan.hof.service.prod.ProdService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;

@Service
@Log4j2
public class CrawlingService {
  private final Random random = new Random();
  @Autowired
  private ProdService prodService;
  @Autowired
  private ProdCategoryService categoryService;
  @Autowired
  private ProdOptionService optionService;
  @Autowired
  private ProdOptionMapService optionMapService;

  private static final String TARGET_URL = "https://www.coupang.com/np/categories/416250?listSize=60&sorter=bestAsc";
  private static final String BASE_URL = "https://www.coupang.com";
  private int timeoutCount = 0;

  public void crawling() {
    ChromeOptions options = new ChromeOptions();

    options.addArguments("--headless");
    options.addArguments("--disable-blink-features=AutomationControlled");
    options.addArguments("--disable-gpu");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--window-size=1920,1080");
    options.addArguments("--user-agent=" + getRandomUserAgent());

    WebDriver driver = null;

    try {
      driver = new ChromeDriver(options);
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

      // 상품 url
      driver.get(TARGET_URL);
      wait.until(webDriver -> ((JavascriptExecutor) webDriver)
        .executeScript("return document.readyState").equals("complete"));

      // prodUrl list 추출
      List<WebElement> prodUrls = wait.until(
        ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("/html/body/div[3]/section/form/div/div/div[1]/div[1]/ul/li/a"))
      );

      List<String> prodUrlList = new ArrayList<>();
      for (WebElement link : prodUrls) {
        String href = link.getAttribute("href");
        if (href != null && !href.isEmpty()) {
          if (!href.startsWith("http")) {
            href = BASE_URL + href;
          }
          prodUrlList.add(href);
        }
      }

      log.info("리스트 : " + prodUrlList);
      log.info("상품 목록 개수: " + prodUrlList.size());

      // 각 상품 목록 크롤링
      for (String productUrl : prodUrlList) {
        // 상품 상세 정보 크롤링 및 DB 저장
        if (timeoutCount >= 7) {
          log.error("타임아웃 오류 7회 발생. 크롤링 중단!");
          return;
        }

        try {
          Map<String, Object> productDetails = CrawlingProdDetails(driver, productUrl);
          if (productDetails.isEmpty()) continue;
          log.info("상품 데이터 : " + productDetails);
          // saveProductToDB(productDetails);
          break;
        } catch (Exception e) {
          log.error("상품 크롤링 중 오류 발생: " + e.getMessage());
        }
          // }
      }

    } catch (TimeoutException e) {
        log.error("페이지 로딩 시간 초과: " + e.getMessage());
    } catch (Exception e) {
        log.error("크롤링 오류 발생: " + e.getMessage());
    } finally {
        if (driver != null) {
            driver.quit();
        }
    }
  }
  /**
   * 상품 상세 정보 가져오기
   */
  private Map<String, Object> CrawlingProdDetails(WebDriver driver, String productUrl) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    Map<String, Object> productDetails = new HashMap<>();

    try {
      driver.get(productUrl);
      wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

      // 상품 title
      WebElement titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(
        By.xpath("//h1[contains(@class, 'prod-buy-header__title')]")
      ));
      productDetails.put("title", titleElement.getText());

      // 상품 이미지
      WebElement imageElement = wait.until(ExpectedConditions.presenceOfElementLocated(
        By.xpath("//img[contains(@class, 'prod-image__detail')]")
      ));
      productDetails.put("image_url", imageElement.getAttribute("src"));

      // 상품 가격
      try {
        WebElement priceElement = wait.until(ExpectedConditions.presenceOfElementLocated(
          By.xpath("//*[@id=\"contents\"]/div[2]/div[1]/div[3]/div[5]/div[1]/div/div[2]/span")
        ));
        productDetails.put("price", priceElement.getText());
      } catch (NoSuchElementException e) {
        int randomPrice = random.nextInt(150000) + 50000;
        productDetails.put("price", randomPrice + "원");
      }

      // 상품 옵션
      List<WebElement> optionList = driver.findElements(By.xpath("//div[contains(@class, 'prod-option__item')]"));
      List<Map<String, Object>> options = new ArrayList<>();

      if (!optionList.isEmpty()) {
        for (WebElement optionTarget : optionList) {
          try {
            // 옵션 카테고리 명 (예: 색상, 사이즈 등)
            WebElement optionTitleElement = optionTarget.findElement(By.xpath(".//span[@class='title']"));
            String optionTitle = optionTitleElement.getText().trim();
            // 옵션 리스트
            List<WebElement> optionItems = optionTarget.findElements(By.xpath(
              ".//ul[contains(@class, 'prod-option__list')]/li//div[contains(@class, 'prod-option__dropdown-item-title')]/strong"
            ));
            List<String> optionValues = new ArrayList<>();
            for (WebElement item : optionItems) {
              // 옵션 리스트 값
              String optionValue = "";
              try {
                optionValue = item.getText().trim();
              } catch (Exception e) {
                log.warn("옵션 값 가져오기 실패: " + e.getMessage());
              }
          
              // JavaScript로 강제 가져오기 (Selenium이 못 읽을 수도 있음)
              if (optionValue.isEmpty()) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                optionValue = (String) js.executeScript("return arguments[0].innerText;", item);
                optionValue = optionValue.trim();
              }

              if (!optionValue.isEmpty()) {
                optionValues.add(optionValue);
              } else {
                log.warn("옵션명이 비어 있음");
              }
            }
            // 옵션 정보 저장
            Map<String, Object> optionData = new HashMap<>();
            optionData.put("옵션명", optionTitle);
            optionData.put("옵션 리스트", optionValues);

            options.add(optionData);

          } catch (NoSuchElementException e) {
            log.warn("옵션 파싱 실패: " + e.getMessage());
          }
        }
      }
      productDetails.put("options", options);

      // 상품 상세 이미지
      List<WebElement> imageElements = driver.findElements(By.xpath("//div[contains(@class, 'vendor-item')]//img"));
      List<String> imageUrls = new ArrayList<>();

      for (WebElement imgElement : imageElements) {
        String imageUrl = imgElement.getAttribute("src");
        if (imageUrl != null && !imageUrl.isEmpty()) {
          imageUrls.add(imageUrl);
        }
      }
      productDetails.put("image_urls", imageUrls);

      return productDetails;

    } catch (Exception e) {
      log.error("상품 상세 정보 크롤링 실패: " + e.getMessage());
    }

    return productDetails;
  }

  /**
   * 크롤링한 데이터를 DB에 저장
   */
  private void saveProductToDB(Map<String, Object> productDetails) {
    String title = (String) productDetails.get("title");
    int price = Integer.parseInt(((String) productDetails.get("price")).replaceAll("[^0-9]", ""));
    String imageUrl = (String) productDetails.get("image_url");
    List<Map<String, Object>> optionsList = (List<Map<String, Object>>) productDetails.get("options");
    
    Prod existProd = prodService.findByTitle(title);
    if (existProd != null) {
      log.info("이미 존재하는 상품: " + title);
      return;
    }
    
    // ProdCategory 침대(cno)
    ProdCategory prodCategory = categoryService.findBy(1L);

    // Prod 생성 (pno)
    Prod prod = Prod.builder()
      .cno(prodCategory)
      .title(title)
      .content("content test") // 값 가져와야됨
      .price(price)
      .stock(10)
    .build();
    prod = prodService.add(prod);

    // ProdOption (no)
    for (Map<String, Object> optionData : optionsList) {
      String optionType = (String) optionData.get("옵션명");
      List<String> optionValues = (List<String>) optionData.get("옵션 리스트");

      for (String optionValue : optionValues) {
        int randomPrice = ((random.nextInt(9) + 2) * 500);

        ProdOption option = ProdOption.builder()
          .type(optionType) 
          .value(optionValue) 
          .addPrice(randomPrice) 
        .build();

        // ProdOptionMap (pno = pno, option_no = no)
        ProdOption prodOption = optionService.add(option);
        ProdOptionMap prodOptionMap = ProdOptionMap.builder()
          .prod(prod)
          .option(prodOption)
          .stock(100)
        .build();
        optionMapService.add(prodOptionMap);

      }
    }

    // FileMaset (pno = pno)

  }

  /**
   * 랜덤 User-Agent 생성
   */
  private String getRandomUserAgent() {
      return "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
  }

}
