package com.oneul_tanda.websocket_service.application;

import com.oneul_tanda.websocket_service.domain.WebSocketMessage;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    public String formatMessage(WebSocketMessage message) {
        return message.toString();
    }
}
