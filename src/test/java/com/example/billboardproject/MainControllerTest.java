package com.example.billboardproject;


import com.example.billboardproject.controller.MainController;
import com.example.billboardproject.model.Billboard;
import com.example.billboardproject.model.Order;
import com.example.billboardproject.model.Role;
import com.example.billboardproject.model.User;
import com.example.billboardproject.service.OrderService;
import com.example.billboardproject.service.impl.BillboardServiceImpl;
import com.example.billboardproject.service.impl.CityServiceImpl;
import com.example.billboardproject.service.impl.LocationServiceImpl;
import com.example.billboardproject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MainControllerTest {
    @Mock
private UserServiceImpl userService;

    @Mock
    private BillboardServiceImpl billboardService;

    @Mock
    private OrderService orderService;

    @Mock
    private CityServiceImpl cityService;

    @Mock
    private LocationServiceImpl locationService;

    @InjectMocks
    private MainController mainController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProfilePage() {
        User user = new User();
        user.setRole(Role.MANAGER);
        when(userService.getCurrentUser()).thenReturn(user);
        when(billboardService.getAllActiveBillboards()).thenReturn(Collections.emptyList());

        Model model = mock(Model.class);
        String result = mainController.profilePage(model);


        assertEquals("redirect:/admin/main", result);
        verify(model).addAttribute(eq("billboards"), anyList());
        verify(model).addAttribute(eq("cities"), anyList());
        verify(model).addAttribute(eq("locations"), anyList());
    }

    @Test
    void testBasketPage() {
        User user = new User();
        user.setId(1L);
        when(userService.getUserData()).thenReturn(user);
        when(orderService.getAllOrdersByUserId(1L)).thenReturn(Collections.emptyList());

        Model model = mock(Model.class);
        String result = mainController.basketPage(model);

        assertEquals("basket", result);
        verify(model).addAttribute(eq("orders"), anyList());
        verify(model).addAttribute(eq("cities"), anyList());
        verify(model).addAttribute(eq("locations"), anyList());
    }

    @Test
    void testDetailBillboardPage() {
        Billboard billboard = new Billboard();
        billboard.setId(1L);
        when(billboardService.getBillboardById(1L)).thenReturn(billboard);

        List<Order> orders = Collections.emptyList();
        when(orderService.getAllOrdersByBillboardId(1L)).thenReturn(orders);

        Model model = mock(Model.class);
        String result = mainController.detailBillboardPage(model, 1L);

        assertEquals("details", result);
        verify(model).addAttribute(eq("billboardOrders"), eq(orders));
        verify(model).addAttribute(eq("billboard"), eq(billboard));
        verify(model).addAttribute(anyString(), anyList());
        verify(model).addAttribute(anyString(), anyInt());
        verify(cityService).getAllCities();
        verify(locationService).getAllLocations();
    }

}