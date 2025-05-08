package com.oneul_tanda.websocket_service.infrastructure.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.UUID;

@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) {

        // 헤더에서 x-userid, x-username, chatRoomId 추출
        UUID userId = UUID.fromString(request.getHeaders().getFirst("X-User-ID"));

        if (userId != null) {
            attributes.put("userId", userId);
            return true;
        } else {
            return false;  // 인증 실패
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

