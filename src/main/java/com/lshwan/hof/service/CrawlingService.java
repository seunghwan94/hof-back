package com.lshwan.hof.service;

import com.lshwan.hof.domain.entity.prod.Prod;
import com.lshwan.hof.domain.entity.prod.ProdCategory;
import com.lshwan.hof.domain.entity.prod.ProdOption;
import com.lshwan.hof.domain.entity.prod.ProdOptionMap;
import com.lshwan.hof.repository.prod.ProdCategoryRepository;
import com.lshwan.hof.repository.prod.ProdOptionRepository;
import com.lshwan.hof.repository.prod.ProdRepository;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.Locale.Category;

@Service
@Log4j2
public class CrawlingService {
    
    @Autowired
    private ProdRepository prodRepository;

    @Autowired
    private ProdCategoryRepository prodCategoryRepository;

    @Autowired
    private ProdOptionRepository prodOptionRepository;

    private static final String CATEGORY_URL = "https://www.coupang.com/np/categories/416250?listSize=60&sorter=bestAsc";
    private static final String BASE_URL = "https://www.coupang.com";
    private int timeoutCount = 0;
    private final Random random = new Random();

    public void fetchCategoryProducts() {
        ChromeOptions options = new ChromeOptions();
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

            // 1️⃣ 카테고리 페이지 접속
            log.info("카테고리 크롤링 시작");
            driver.get(CATEGORY_URL);
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));

            // 2️⃣ 서브 카테고리 URL 가져오기
            List<WebElement> subCategoryLinks = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("/html/body/div[3]/section/form/div/div/div[1]/div[1]/ul/li/a"))
            );

            List<String> subCategoryUrls = new ArrayList<>();
            for (WebElement link : subCategoryLinks) {
                String href = link.getAttribute("href");
                if (href != null && !href.isEmpty()) {
                    if (!href.startsWith("http")) {
                        href = BASE_URL + href;
                    }
                    subCategoryUrls.add(href);
                }
            }

            log.info("서브 카테고리 개수: " + subCategoryUrls.size());

            // 3️⃣ 각 서브 카테고리 크롤링
            for (String subCategoryUrl : subCategoryUrls) {
                List<String> productUrls = fetchProductUrls(driver, subCategoryUrl);
                log.info("상품 URL 개수: " + productUrls.size());

                // 4️⃣ 상품 상세 정보 크롤링 및 DB 저장
                for (String productUrl : productUrls) {
                    if (timeoutCount >= 7) {
                        log.error("❌ 타임아웃 오류 7회 발생. 크롤링 중단!");
                        return;
                    }

                    try {
                        Map<String, Object> productDetails = fetchProductDetails(driver, productUrl);
                        if (productDetails.isEmpty()) continue;

                        saveProductToDB(productDetails);
                    } catch (Exception e) {
                        log.error("상품 크롤링 중 오류 발생: " + e.getMessage());
                    }
                }
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
     * 상품 URL 리스트 가져오기
     */
    private List<String> fetchProductUrls(WebDriver driver, String subCategoryUrl) {
        List<String> productUrls = new ArrayList<>();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get(subCategoryUrl);
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));

            List<WebElement> productElements = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul/li/a"))
            );

            for (WebElement productElement : productElements) {
                String productUrl = productElement.getAttribute("href");
                if (productUrl != null && !productUrl.isEmpty() && productUrl.startsWith("https://www.coupang.com/vp/products/")) {
                    productUrls.add(productUrl);
                }
            }

        } catch (Exception e) {
            log.error("상품 URL 수집 중 오류 발생: " + e.getMessage());
        }

        return productUrls;
    }

    /**
     * 상품 상세 정보 가져오기
     */
    private Map<String, Object> fetchProductDetails(WebDriver driver, String productUrl) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Map<String, Object> productDetails = new HashMap<>();

        try {
            driver.get(productUrl);
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));

            WebElement titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h1[contains(@class, 'prod-buy-header__title')]")
            ));
            productDetails.put("title", titleElement.getText());

            WebElement imageElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//img[contains(@class, 'prod-image__detail')]")
            ));
            productDetails.put("image_url", imageElement.getAttribute("src"));

            try {
                WebElement priceElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[@id=\"contents\"]/div[2]/div[1]/div[3]/div[5]/div[1]/div/div[2]/span")
                ));
                productDetails.put("price", priceElement.getText());
            } catch (NoSuchElementException e) {
                int randomPrice = random.nextInt(150000) + 50000;
                productDetails.put("price", randomPrice + "원");
            }
            // 4️⃣ **옵션 정보 가져오기 (XPath)**
            List<WebElement> optionElements = driver.findElements(By.xpath("//button[contains(@class, 'prod-option__selected')]/span[@class='value']"));
            List<String> options = new ArrayList<>();

            if (!optionElements.isEmpty()) {
                for (WebElement option : optionElements) {
                    options.add(option.getText());
                }
                productDetails.put("options", options);
            } else {
                productDetails.put("options", Collections.singletonList("없음")); // 옵션이 없을 경우 "없음" 저장
            }

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
        List<String> options = (List<String>) productDetails.get("options");


        Optional<Prod> existingProduct = prodRepository.findByTitle(title);
        if (existingProduct.isPresent()) {
            log.info("이미 존재하는 상품: " + title);
            return;
        }

        if (options != null && !options.isEmpty() && !options.contains("없음")) {
            for (String optionValue : options) {
                ProdOption option;
                option = ProdOption.builder()
                        .type("기본") // 기본 옵션 타입 (필요시 변경 가능)
                        .value(optionValue)
                        .addPrice(0)
                        .build();
                prodOptionRepository.save(option);

                // ✅ 옵션과 상품 매핑
                ProdOptionMap optionMap = ProdOptionMap.builder()
                        .option(option)
                        .stock(10) // 기본 재고 설정
                    .build();

                // prodOptionMapRepository.save(optionMap);
            }
            log.info("✅ 옵션 저장 완료: " + options);
        }

        Prod product = Prod.builder()
            .cno(prodCategoryRepository.findById(1L).orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다.")))
            .title(title)
            .content(imageUrl)
            .price(price)
            .stock(10)
        .build();

        prodRepository.save(product);
        log.info("상품 저장 완료: " + title);
    }

    /**
     * 랜덤 User-Agent 생성
     */
    private String getRandomUserAgent() {
        return "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
    }
}
