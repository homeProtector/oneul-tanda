package com.oneul_tanda.websocket_service.infrastructure.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) {

        String userIdHeader = request.getHeaders().getFirst("X-User-ID");

        if (userIdHeader == null) {
            log.warn("WebSocket handshake failed: Missing X-User-ID header");
            return false; // 헤더 없음
        }

        try {
            UUID userId = UUID.fromString(userIdHeader);
            attributes.put("userId", userId);
            return true;
        } catch (IllegalArgumentException e) {
            log.warn("WebSocket handshake failed: Invalid UUID format for X-User-ID: {}", userIdHeader);
            return false; // 잘못된 UUID 형식
        }
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception exception) {
        // 생략 가능
    }
}

