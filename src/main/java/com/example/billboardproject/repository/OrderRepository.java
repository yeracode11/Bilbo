package com.example.billboardproject.repository;

import com.example.billboardproject.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "select * from order_for_billboards where user_id=:id order by status, order_date DESC", nativeQuery = true)
    List<Order> findOrdersByUserId(Long id);
    @Query(value = "select * from order_for_billboards where billboard_id=:id order by status, order_date DESC", nativeQuery = true)
    List<Order> findOrdersByBillboardId(Long id);
    @Query(value = "select * from order_for_billboards order by status, order_date DESC", nativeQuery = true)
    List<Order> findOrdersBySorting();
}
