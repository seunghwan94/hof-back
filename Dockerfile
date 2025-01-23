# 베이스 이미지로 OpenJDK 사용
FROM eclipse-temurin:17-jdk-alpine

# 작업 디렉토리 설정
WORKDIR /app

# 애플리케이션 jar 파일 복사
COPY build/libs/hof_backend-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose the port your Spring Boot app will run on
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
