package ru.mycomp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "orders")
public class OrderRow {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "ctime")
    private ZonedDateTime ctime;
    @Column(name = "product")
    private String product;
    @Column(name = "manager")
    private String manager;
    @Column(name = "amount")
    private Long amount;
    @Column(name = "manager_fin_dep")
    private String managerFinDep;
    @Column(name = "status")
    private String status;
}
