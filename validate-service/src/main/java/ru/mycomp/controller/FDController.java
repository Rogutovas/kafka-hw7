package ru.mycomp.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import ru.mycomp.dto.Order;
import ru.mycomp.dto.ValidatedOrderRq;
import ru.mycomp.service.FDService;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FDController {

    private final FDService fdService;
    @PostMapping(path = "/order/validate")
    @SneakyThrows
    public String docCreate(@RequestBody ValidatedOrderRq order) {
        return fdService.validateOrder(order);
    }

    @GetMapping(path = "/order/getNotValidated")
    @SneakyThrows
    public List<Order> docCreate() {
        return fdService.getAllOrder();
    }

    @GetMapping(path = "/order/randomValidateOrNo")
    @SneakyThrows
    public String randomValidate() {
        return fdService.randomValidate();
    }
}
