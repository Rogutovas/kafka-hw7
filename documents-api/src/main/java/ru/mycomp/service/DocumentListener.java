package ru.mycomp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.mycomp.dto.ValidatedOrder;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentListener {
    @KafkaListener(topics = "validated_order_topic")
    public void onMessageReceive(@Payload ValidatedOrder validatedOrder) {
        log.info("onMessageReceive: {}", validatedOrder.toString());

        if (validatedOrder.getStatus().equals("ACCC")) {
            if (verify(validatedOrder.getSignature())) {
                doPayment();
            }
        } else {
            log.info("Заказ номер: " + validatedOrder.getOrder().getId() +
                    " отклонён. Причина: " + validatedOrder.getMessage());
        }
    }

    private boolean verify(String signature) {
        return signature != null && !signature.isBlank();
    }

    private void doPayment() {
        log.info("Платёж проведён!!!");
    }
}
