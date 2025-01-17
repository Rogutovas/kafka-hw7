package ru.mycomp.mapper;

import ru.mycomp.dto.Order;
import ru.mycomp.entity.OrderRow;

public class OrderMapper {
    public static Order mapToOrder (OrderRow row) {
        return Order.builder().id(row.getId())
                .ctime(row.getCtime())
                .amount(row.getAmount())
                .manager(row.getManager())
                .product(row.getProduct())
                .build();
    }

    public static OrderRow mapToOrderRow (Order row) {
        return OrderRow.builder().id(row.getId())
                .ctime(row.getCtime())
                .amount(row.getAmount())
                .manager(row.getManager())
                .product(row.getProduct())
                .build();
    }
}
