package com.example.billboardproject.service.impl;

import com.example.billboardproject.model.Order;
import com.example.billboardproject.repository.OrderRepository;
import com.example.billboardproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl  implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrdersByBillboardId(Long id) {
        return orderRepository.findOrdersByBillboard_Id(id);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.getReferenceById(id);
    }

    @Override
    public int sumOfIncome() {
        List<Order> orders = getAllOrders();
        int sum = 0;
        for (Order order : orders) {
            if (order.getStatus() == 1) {
                sum += order.getBillboard().getPrice();
            }
        }
        return sum;
    }

    @Override
    public int getAllActiveOrders() {
        List<Order> orders = getAllOrders();
        int sum = 0;
        for (Order order : orders) {
            if (order.getStatus() == 1) {
                sum += 1;
            }
        }
        return sum;
    }

    @Override
    public int getAllInactiveOrders() {
        List<Order> orders = getAllOrders();
        int sum = 0;
        for (Order order : orders) {
            if (order.getStatus() == 2) {
                sum += 1;
            }
        }
        return sum;
    }

    @Override
    public int getAllWaitingOrders() {
        List<Order> orders = getAllOrders();
        int sum = 0;
        for (Order order : orders) {
            if (order.getStatus() == 0) {
                sum += 1;
            }
        }
        return sum;
    }

    @Override
    public Order editOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getAllOrdersByUserId(Long id) {
        return orderRepository.findOrdersByUser_Id(id);
    }

    @Override
    public boolean addOrder(Order order) {
        boolean check = false;
        if (order != null) {
            orderRepository.save(order);
            check = true;
        }

        return check;
    }
}