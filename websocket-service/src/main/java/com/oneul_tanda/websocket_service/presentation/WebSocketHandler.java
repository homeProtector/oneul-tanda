package com.oneul_tanda.websocket_service.presentation;

import com.oneul_tanda.websocket_service.infrastructure.kafka.ReservationHeldEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = (String) session.getAttributes().get("userId");

        userSessions.putIfAbsent(userId, session);

        session.sendMessage(new TextMessage("알림서비스 접속 성공"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userId = (String) session.getAttributes().get("userId");
        if (userSessions != null) {
            userSessions.remove(userId);
        }
    }

    public void sendMessageToUser(ReservationHeldEvent event) {
        WebSocketSession session = userSessions.get(event.getData().getUserId());
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(event.getEventType() + ":" + event.getData().getFlightId()));
            } catch (IOException e) {
                log.error("알림 메세지 전송 실패", e.getMessage(), e);
                // 로그 처리
            }
        }
    }
}

