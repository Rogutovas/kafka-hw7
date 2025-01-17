package ru.mycomp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.stereotype.Service;
import ru.mycomp.dto.Order;
import ru.mycomp.dto.ValidatedOrder;
import ru.mycomp.dto.ValidatedOrderRq;
import ru.mycomp.entity.OrderRow;
import ru.mycomp.mapper.OrderMapper;
import ru.mycomp.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FDService {
    private final KafkaOperations<String, ValidatedOrder> operations;
    private final OrderRepository orderRepository;
    private final String VALIDATED_ORDER_TOPIC = "validated_order_topic";

    public List<Order> getAllOrder() {
        return orderRepository.findByStatusIsNull().stream().map(OrderMapper::mapToOrder).toList();
    }

    public String randomValidate() {
        List<Order> orders = getAllOrder();

        orders.forEach(order -> {
            int a = (int)(Math.random() * 10);

            String status = (a > 7) ? "RJCT" : "ACCC";
            String messages = (a > 7) ? "random error" : "" ;


            ValidatedOrderRq validatedOrderRq = ValidatedOrderRq.builder()
                    .orderId(order.getId())
                    .managerFinDep(gerRandomManager())
                    .message(messages)
                    .status(status)
                    .build();

            validateOrder(validatedOrderRq);
        });

        return "Обработано " + orders.size() + " заказов";
    }

    public String validateOrder(ValidatedOrderRq orderRq) {
        OrderRow orderRow = orderRepository.findById(orderRq.getOrderId()).orElse(null);

        if (orderRow == null) return "NOT FOUND";

        Order order = OrderMapper.mapToOrder(orderRow);
        ValidatedOrder validatedOrder = ValidatedOrder.builder()
                .order(order)
                .status(orderRq.getStatus())
                .message(orderRq.getMessage())
                .managerFinDep(orderRq.getManagerFinDep())
                .signature(sign(order)).build();

        orderRow.setManagerFinDep(orderRq.getManagerFinDep());
        orderRow.setStatus(orderRq.getStatus());

        orderRepository.save(orderRow);

        UUID uuid = UUID.randomUUID();

        operations.send(VALIDATED_ORDER_TOPIC,uuid.toString(), validatedOrder).thenAccept(action -> {
            log.info(action.getProducerRecord().toString());
        });

        return "success";
    }

    public String sign(Order order) {
        return order.toString().getBytes().toString();
    }

    public void saveOrder(Order order) {
        OrderRow o = orderRepository.findById(order.getId()).orElse(null);
        if (o != null) {
            ValidatedOrder validatedOrder = ValidatedOrder.builder().order(order).managerFinDep("none")
                    .message("Order already exists")
                    .status("RJCT").build();

            UUID uuid = UUID.randomUUID();

            operations.send(VALIDATED_ORDER_TOPIC,uuid.toString(), validatedOrder).thenAccept(action -> {
                log.info(action.getProducerRecord().toString());
            });

            return;
        }

        OrderRow orderRow = OrderRow.builder()
                .id(order.getId())
                .ctime(order.getCtime())
                .amount(order.getAmount())
                .manager(order.getManager())
                .product(order.getProduct()).build();

        orderRepository.save(orderRow);
    }

    private String gerRandomManager() {
        List<String> list = List.of("Ветров В.", "Солнцева С.", "Снежкова В.","Весенина А.", "Осенева О.",
                "Лазарев Н.", "Водянкин М.", "Знойный О.", "Теплых Л.", "Холодов Г.");

        int a = (int)(Math.random() * 10);

        return list.get(a);
    }



}
