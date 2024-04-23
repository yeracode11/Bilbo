package com.example.billboardproject;

import com.example.billboardproject.model.Billboard;
import com.example.billboardproject.model.Order;
import com.example.billboardproject.repository.OrderRepository;
import com.example.billboardproject.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllOrdersByBillboardId() {
        Long billboardId = 1L;
        List<Order> orders = new ArrayList<>();
        when(orderRepository.findOrdersByBillboard_Id(billboardId)).thenReturn(orders);

        List<Order> result = orderService.getAllOrdersByBillboardId(billboardId);

        assertEquals(orders, result);
        verify(orderRepository).findOrdersByBillboard_Id(billboardId);
    }

    @Test
    void testGetOrderById() {
        Long id = 1L;
        Order order = new Order();
        when(orderRepository.getReferenceById(id)).thenReturn(order);

        Order result = orderService.getOrderById(id);

        assertEquals(order, result);
        verify(orderRepository).getReferenceById(id);
    }

    @Test
    void testSumOfIncome() {
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        order1.setStatus(1);
        order1.setBillboard(new Billboard());
        Order order2 = new Order();
        order2.setStatus(2);
        order2.setBillboard(new Billboard());
        orders.add(order1);
        orders.add(order2);

        when(orderRepository.findAll()).thenReturn(orders);

        int result = orderService.sumOfIncome();

        assertEquals(0, result);
    }

    @Test
    void testGetAllActiveOrders() {
        List<Order> orders = new ArrayList<>();
        Order activeOrder1 = new Order();
        activeOrder1.setStatus(1);
        Order activeOrder2 = new Order();
        activeOrder2.setStatus(1);
        Order inactiveOrder = new Order();
        inactiveOrder.setStatus(2);
        orders.add(activeOrder1);
        orders.add(activeOrder2);
        orders.add(inactiveOrder);

        when(orderRepository.findAll()).thenReturn(orders);

        int result = orderService.getAllActiveOrders();

        assertEquals(2, result);
    }

    @Test
    void testGetAllInactiveOrders() {
        List<Order> orders = new ArrayList<>();
        Order activeOrder = new Order();
        activeOrder.setStatus(1);
        Order inactiveOrder1 = new Order();
        inactiveOrder1.setStatus(2);
        Order inactiveOrder2 = new Order();
        inactiveOrder2.setStatus(2);
        orders.add(activeOrder);
        orders.add(inactiveOrder1);
        orders.add(inactiveOrder2);

        when(orderRepository.findAll()).thenReturn(orders);

        int result = orderService.getAllInactiveOrders();

        assertEquals(2, result);
    }

    @Test
    void testGetAllWaitingOrders() {
        List<Order> orders = new ArrayList<>();
        Order waitingOrder1 = new Order();
        waitingOrder1.setStatus(0);
        Order waitingOrder2 = new Order();
        waitingOrder2.setStatus(0);
        Order activeOrder = new Order();
        activeOrder.setStatus(1);
        orders.add(waitingOrder1);
        orders.add(waitingOrder2);
        orders.add(activeOrder);

        when(orderRepository.findAll()).thenReturn(orders);

        int result = orderService.getAllWaitingOrders();

        assertEquals(2, result);
    }

    @Test
    void testEditOrder() {
        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.editOrder(order);

        assertEquals(order, result);
        verify(orderRepository).save(order);
    }

    @Test
    void testGetAllOrders() {
        List<Order> orders = new ArrayList<>();
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(orders, result);
        verify(orderRepository).findAll();
    }

    @Test
    void testGetAllOrdersByUserId() {
        Long userId = 1L;
        List<Order> orders = new ArrayList<>();
        when(orderRepository.findOrdersByUser_Id(userId)).thenReturn(orders);

        List<Order> result = orderService.getAllOrdersByUserId(userId);

        assertEquals(orders, result);
        verify(orderRepository).findOrdersByUser_Id(userId);
    }

    @Test
    void testAddOrder() {
        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);

        boolean result = orderService.addOrder(order);

        assertTrue(result);
        verify(orderRepository).save(order);
    }
}
