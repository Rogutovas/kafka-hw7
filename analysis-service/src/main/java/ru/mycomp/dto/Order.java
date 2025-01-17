package ru.mycomp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private String id;
    private ZonedDateTime ctime;
    private String product;
    private String manager;
    private Long amount;

}
