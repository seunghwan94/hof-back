package com.lshwan.hof.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.lshwan.hof.handler.NotificationWebSocketHandler;

@Configuration
@EnableWebSocket
@EnableWebSecurity
public class NotificationWebsocket implements WebSocketConfigurer {
    private final NotificationWebSocketHandler notificationWebSocketHandler;

    // ✅ Spring에서 관리하는 NotificationWebSocketHandler를 주입 받음
    public NotificationWebsocket(NotificationWebSocketHandler notificationWebSocketHandler) {
        this.notificationWebSocketHandler = notificationWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(notificationWebSocketHandler, "/ws/notify")
                .setAllowedOrigins("*"); // 모든 도메인에서 접근 허용
    }
}
