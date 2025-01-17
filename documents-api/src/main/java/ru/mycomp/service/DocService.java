package ru.mycomp.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.stereotype.Service;
import ru.mycomp.dto.Order;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocService {
    private final KafkaOperations<String, Order> operations;
    private static final AtomicInteger counter = new AtomicInteger();
    private final String DOCUMENT_TOPIC = "order_topic";

    public String orderCreate(Order order) {
        UUID uuid = UUID.randomUUID();
        operations.send(DOCUMENT_TOPIC,uuid.toString(), order).thenAccept(action -> {
            log.info(action.getProducerRecord().toString());
        });
        return "ok";
    }

    @SneakyThrows
    public String generate() {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(100);

            Order order = Order.builder()
                    .product("Продукт " + counter.get())
                    .ctime(ZonedDateTime.now())
                    .amount((long)(Math.random() * 1000))
                    .id(""+counter.get())
                    .manager(gerRandomManager()).build();

            orderCreate(order);

            counter.incrementAndGet();
        }

        return "ok";
    }

    private String gerRandomManager() {
        List<String> list = List.of("Иванов В.", "Зайцева А.", "Смирнова В.","Спицина А.", "Нестеров М.",
                "Викторова В.", "Соколов С.", "Воробьёв В.", "Воронов А.", "Дятлов Д.");

        int a = (int)(Math.random() * 10);

        return list.get(a);
    }
}
