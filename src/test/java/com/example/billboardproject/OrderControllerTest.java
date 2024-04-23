package com.example.billboardproject;


import com.example.billboardproject.controller.OrderController;
import com.example.billboardproject.model.Billboard;
import com.example.billboardproject.model.Order;
import com.example.billboardproject.model.User;
import com.example.billboardproject.service.BillboardService;
import com.example.billboardproject.service.OrderService;
import com.example.billboardproject.service.impl.CityServiceImpl;
import com.example.billboardproject.service.impl.LocationServiceImpl;
import com.example.billboardproject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderControllerTest {
    @Mock
    private OrderService orderService;

    @Mock
    private BillboardService billboardService;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private CityServiceImpl cityService;

    @Mock
    private LocationServiceImpl locationService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOrderBillboard_Successful() {
        String startDate = "2024-04-18";
        String endDate = "2024-04-20";
        String phone = "123456789";
        String billboardId = "1";

        User currentUser = new User();
        currentUser.setId(1L);

        Billboard billboard = new Billboard();
        billboard.setId(1L);

        when(userService.getUserData()).thenReturn(currentUser);
        when(billboardService.getBillboardById(1L)).thenReturn(billboard);
        when(orderService.addOrder(any(Order.class))).thenReturn(true);

        Model model = mock(Model.class);

        String result = orderController.orderBillboard(startDate, endDate, phone, billboardId, model);

        assertEquals("redirect:/detailBillboard/1/?successful", result);
        verify(orderService).addOrder(any(Order.class));
        verify(model).addAttribute("cities", cityService.getAllCities());
        verify(model).addAttribute("locations", locationService.getAllLocations());
    }

    @Test
    void testOrderBillboard_Error() {
        String startDate = "2024-04-18";
        String endDate = "2024-04-20";
        String phone = "123456789";
        String billboardId = "1";

        User currentUser = new User();
        currentUser.setId(1L);

        Billboard billboard = new Billboard();
        billboard.setId(1L);

        when(userService.getUserData()).thenReturn(currentUser);
        when(billboardService.getBillboardById(1L)).thenReturn(billboard);
        when(orderService.addOrder(any(Order.class))).thenReturn(false);

        Model model = mock(Model.class);

        String result = orderController.orderBillboard(startDate, endDate, phone, billboardId, model);

        assertEquals("redirect:/detailBillboard/1/?error", result);
        verify(orderService).addOrder(any(Order.class));
        verify(model).addAttribute("cities", cityService.getAllCities());
        verify(model).addAttribute("locations", locationService.getAllLocations());
    }
}
