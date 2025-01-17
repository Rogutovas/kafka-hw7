package ru.mycomp.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import ru.mycomp.dto.Order;
import ru.mycomp.service.DocService;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DocController {

    private final DocService docService;
    @PostMapping(path = "/order/create")
    @SneakyThrows
    public String docCreate(@RequestBody Order order) {
        return docService.orderCreate(order);
    }

    @GetMapping(path = "/order/generate")
    @SneakyThrows
    public String docGenerate() {
        return docService.generate();
    }
}
