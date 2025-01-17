package ru.mycomp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.mycomp.dto.Order;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentListener {
    private final FDService fdService;
    @KafkaListener(topics = "order_topic")
    public void onMessageReceive(@Payload Order order) {
        log.info("onMessageReceive: {}", order.toString());

        fdService.saveOrder(order);
    }
}
