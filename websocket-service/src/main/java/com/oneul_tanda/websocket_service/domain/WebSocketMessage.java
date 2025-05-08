package com.oneul_tanda.websocket_service.domain;

public record WebSocketMessage(
        String sender,
        String content
) {
    @Override
    public String toString() {
        return sender + ": " + content;
    }
}
