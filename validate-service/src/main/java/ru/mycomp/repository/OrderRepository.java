package ru.mycomp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mycomp.entity.OrderRow;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository  extends JpaRepository<OrderRow, String> {
    List<OrderRow> findByStatusIsNull();

}
