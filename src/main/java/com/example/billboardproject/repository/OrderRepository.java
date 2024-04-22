package com.example.billboardproject.repository;

import com.example.billboardproject.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrdersByUser_Id(Long id);

    List<Order> findOrdersByBillboard_Id(Long id);

    List<Order> findAll();
}