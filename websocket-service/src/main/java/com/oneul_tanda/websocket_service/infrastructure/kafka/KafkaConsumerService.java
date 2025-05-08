package com.oneul_tanda.websocket_service.infrastructure.kafka;

import com.oneul_tanda.websocket_service.presentation.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private WebSocketHandler webSocketHandler;

    @KafkaListener(topics = KafkaTopics.WEBSOCKET_MESSAGE, groupId = "websocket_group")
    public void handleDeliveryStatusChanged(ReservationPendingEvent event) {
        log.info("알림 메세지 수신: {}", event);

        try {
            webSocketHandler.sendMessageToUser(event);
            log.info("알림 메세지 발송: {}", event);
        } catch(Exception e) {
            log.error("알림 메세지 전송 실패", e.getMessage(), e);
        }
    }
}