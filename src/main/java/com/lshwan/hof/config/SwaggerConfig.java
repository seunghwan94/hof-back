package com.lshwan.hof.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition( servers = {
            @Server(url = "https://hof.lshwan.com/api/v1", description = "배포 서버"),
            @Server(url = "http://localhost:8080/api/v1", description = "로컬 서버")
        },
        info = @Info(title = "가구의집 API 공식문서",version = "1.0",description = "Spring/React spring boot ver 3.4.2.SnapShot"))
public class SwaggerConfig {
  
}
