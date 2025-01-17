package ru.mycomp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidatedOrderRq {
    private String orderId;
    private String managerFinDep;
    private String status;
    private String message;
}
