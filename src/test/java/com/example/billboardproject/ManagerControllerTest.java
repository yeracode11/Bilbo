//package com.example.billboardproject;
//
//
//import com.example.billboardproject.controller.ManagerController;
//import com.example.billboardproject.model.Billboard;
//import com.example.billboardproject.model.Order;
//import com.example.billboardproject.service.BillboardService;
//import com.example.billboardproject.service.FileUploadService;
//import com.example.billboardproject.service.OrderService;
//import com.example.billboardproject.service.impl.CityServiceImpl;
//import com.example.billboardproject.service.impl.LocationServiceImpl;
//import com.example.billboardproject.service.impl.UserServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.ui.Model;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class ManagerControllerTest {
//    @Mock
//    private UserServiceImpl userService;
//
//    @Mock
//    private BillboardService billboardService;
//
//    @Mock
//    private FileUploadService fileUploadService;
//
//    @Mock
//    private CityServiceImpl cityService;
//
//    @Mock
//    private LocationServiceImpl locationService;
//
//    @Mock
//    private OrderService orderService;
//
//    @InjectMocks
//    private ManagerController managerController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testAdminPage() {
//        Model model = mock(Model.class);
//        when(orderService.sumOfIncome()).thenReturn(100);
//        when(orderService.getAllWaitingOrders()).thenReturn(1);
//        when(orderService.getAllActiveOrders()).thenReturn(2);
//        when(orderService.getAllInactiveOrders()).thenReturn(3);
//
//        String result = managerController.incomingOrdersPage(model);
//
//        assertEquals("manager", result);
//        verify(model).addAttribute("income", 100);
//        verify(model).addAttribute("waitingOrders", 1);
//        verify(model).addAttribute("activeOrders", 2);
//        verify(model).addAttribute("inActiveOrders", 3);
//    }
//
//    @Test
//    void testIncomingOrdersPage() {
//        Model model = mock(Model.class);
//        when(billboardService.getAllActiveBillboards()).thenReturn(Collections.emptyList());
//        when(orderService.getAllOrders()).thenReturn(Collections.emptyList());
//        when(cityService.getAllCities()).thenReturn(Collections.emptyList());
//        when(locationService.getAllLocations()).thenReturn(Collections.emptyList());
//
//        String result = managerController.incomingOrdersPage(model);
//
//        assertEquals("order", result);
//        verify(model).addAttribute("billboards", Collections.emptyList());
//        verify(model).addAttribute("orders", Collections.emptyList());
//        verify(model).addAttribute("cities", Collections.emptyList());
//        verify(model).addAttribute("locations", Collections.emptyList());
//    }
//
//    @Test
//    void testChangeStatusOfOrder() {
//        Long orderId = 1L;
//        Order order = new Order();
//        when(orderService.getOrderById(orderId)).thenReturn(order);
//
//        String result = managerController.changeStatusOfOrder(orderId);
//
//        assertEquals("redirect:/admin/incomingOrders/", result);
//        assertEquals(1, order.getStatus());
//        verify(orderService).editOrder(order);
//    }
//
//    @Test
//    void testDetailEditBillboardPage() {
//        Long id = 1L;
//        Billboard billboard = new Billboard();
//        when(billboardService.getBillboardById(id)).thenReturn(billboard);
//
//        Model model = mock(Model.class);
//        String result = managerController.detailEditBillboardPage(model, id);
//
//        assertEquals("detailEditing", result);
//        verify(model).addAttribute("billboard", billboard);
//        verify(model).addAttribute("cities", cityService.getAllCities());
//        verify(model).addAttribute("locations", locationService.getAllLocations());
//    }
//
//    @Test
//    void testAllBillboardsPage() {
//        Model model = mock(Model.class);
//        List<Billboard> billboards = Collections.emptyList();
//        when(billboardService.getAllActiveBillboards()).thenReturn(billboards);
//        when(billboardService.getAllNotActiveBillboards()).thenReturn(Collections.emptyList());
//
//        String result = managerController.allBillboardsPage(model);
//
//        assertEquals("allBilboards", result);
//        verify(model).addAttribute("cities", cityService.getAllCities());
//        verify(model).addAttribute("locations", locationService.getAllLocations());
//        verify(model).addAttribute("billboards", billboards);
//        verify(model).addAttribute("notActiveBillboards", Collections.emptyList());
//    }
//
//    @Test
//    void testAddNewBillboardPage() {
//        Model model = mock(Model.class);
//        when(cityService.getAllCities()).thenReturn(Collections.emptyList());
//        when(locationService.getAllLocations()).thenReturn(Collections.emptyList());
//
//        String result = managerController.addNewBillboardPage(model);
//
//        assertEquals("newBillboards", result);
//        verify(model).addAttribute("cities", Collections.emptyList());
//        verify(model).addAttribute("locations", Collections.emptyList());
//    }
//
//    @Test
//    void testDeleteBillboard() {
//        Long deleteBillboardId = 1L;
//        Billboard billboard = new Billboard();
//        billboard.setActive(true);
//        when(billboardService.getBillboardById(deleteBillboardId)).thenReturn(billboard);
//
//        String result = managerController.deleteBillboard(deleteBillboardId);
//
//        assertEquals("redirect:/admin/allBillboards/", result);
//        assertEquals(false, billboard.isActive());
//        verify(billboardService).updateBillboard(billboard);
//    }
//
//    @Test
//    void testRecoverBillboard() {
//        Long recoverBillboardId = 1L;
//        Billboard billboard = new Billboard();
//        billboard.setActive(false);
//        when(billboardService.getBillboardById(recoverBillboardId)).thenReturn(billboard);
//
//        String result = managerController.recoverBillboard(recoverBillboardId);
//
//        assertEquals("redirect:/admin/allBillboards/", result);
//        assertEquals(true, billboard.isActive());
//        verify(billboardService).updateBillboard(billboard);
//    }
//
//    @Test
//    void testEditBillboard() {
//        Long id = 1L;
//        Long location = 2L;
//        Long city = 3L;
//        String size = "Size";
//        String type = "Type";
//        boolean isHasLightning = true;
//        int price = 100;
//        MultipartFile file = mock(MultipartFile.class);
//
//        Billboard billboard = new Billboard();
//        when(billboardService.getBillboardById(id)).thenReturn(billboard);
//
//        String result = managerController.editBillboard(id, location, city, size, type, isHasLightning, price, file);
//
//        assertEquals("redirect:/admin/allBillboards/", result);
//        assertEquals(city, billboard.getCity_id());
//        assertEquals(price, billboard.getPrice());
//        assertEquals(type, billboard.getType());
//        assertEquals(true, billboard.isActive());
//        assertEquals(location, billboard.getLocation_id());
//        assertEquals(size, billboard.getSize());
//        assertEquals(isHasLightning, billboard.isHasLightning());
//
//        verify(billboardService).updateBillboard(billboard);
//    }
//
//    @Test
//    void testAddBillboard() {
//        Long location = 2L;
//        String size = "Size";
//        boolean isHasLightning = true;
//        int price = 100;
//        String type = "Type";
//        Long city = 3L;
//        MultipartFile file = mock(MultipartFile.class);
//
//        Billboard billboard = new Billboard();
//        when(billboardService.addBillboard(any(Billboard.class))).thenReturn(billboard);
//
//        String result = managerController.addBillboard(location, size, isHasLightning, price, type, city, file);
//
//        assertEquals("redirect:/admin/allBillboards/", result);
//
//
//    }
//}
