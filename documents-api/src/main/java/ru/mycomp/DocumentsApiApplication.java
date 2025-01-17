package ru.mycomp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class DocumentsApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(DocumentsApiApplication.class, args);
    }
}
