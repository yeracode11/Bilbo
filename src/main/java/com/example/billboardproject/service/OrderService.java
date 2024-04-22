package com.example.billboardproject.service;

import com.example.billboardproject.model.Order;

import java.util.List;

public interface OrderService {

    int sumOfIncome();

    int getAllWaitingOrders();
    int getAllActiveOrders();
    int getAllInactiveOrders();

    boolean addOrder(Order order);

    Order editOrder(Order order);

    Order getOrderById(Long id);

    List<Order> getAllOrdersByUserId(Long id);

    List<Order> getAllOrdersByBillboardId(Long id);

    List<Order> getAllOrders();

}