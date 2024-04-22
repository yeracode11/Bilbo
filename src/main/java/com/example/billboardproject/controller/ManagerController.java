package com.example.billboardproject.controller;

import com.example.billboardproject.model.Billboard;
import com.example.billboardproject.model.Order;
import com.example.billboardproject.service.BillboardService;
import com.example.billboardproject.service.FileUploadService;
import com.example.billboardproject.service.OrderService;
import com.example.billboardproject.service.impl.CityServiceImpl;
import com.example.billboardproject.service.impl.LocationServiceImpl;
import com.example.billboardproject.service.impl.UserServiceImpl;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Controller
@RequestMapping(value = "/admin")
public class ManagerController {

    @Value("${loadURL}")
    private String loadURL;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BillboardService billboardService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private CityServiceImpl cityService;

    @Autowired
    private LocationServiceImpl locationService;

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @GetMapping(value = "/main")
    public String adminPage(Model model) {
        model.addAttribute("income", orderService.sumOfIncome());
        model.addAttribute("waitingOrders", orderService.getAllWaitingOrders());
        model.addAttribute("activeOrders", orderService.getAllActiveOrders());
        model.addAttribute("inActiveOrders", orderService.getAllInactiveOrders());
        return "manager";
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @GetMapping(value = "/incomingOrders")
    public String incomingOrdersPage(Model model) {
        model.addAttribute("billboards", billboardService.getAllActiveBillboards());
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("locations", locationService.getAllLocations());
        return "order";
    }
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @PostMapping(value = "/changeStatus")
    public String changeStatusOfOrder(@RequestParam(name = "orderId") Long orderId,
                                      @RequestParam(name = "action") String action) {
        Order changingStatus = orderService.getOrderById(orderId);
        if (changingStatus != null) {
            if ("approve".equals(action)) {
                changingStatus.setStatus(1); // Установка статуса "Принят"
            } else if ("reject".equals(action)) {
                changingStatus.setStatus(2); // Установка статуса "Отклонен"
            }
            orderService.editOrder(changingStatus);
        }
        return "redirect:/admin/incomingOrders/";
    }


    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @GetMapping(value = "/detailEditBillboard/{billboard_id}")
    public String detailEditBillboardPage(Model model,
                                          @PathVariable(name = "billboard_id") Long id) {
        Billboard billboard = billboardService.getBillboardById(id);
        if (billboard != null) {
            model.addAttribute("billboard", billboard);
        }
        System.out.println(billboard.getPrice());
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("locations", locationService.getAllLocations());
        return "detailEditing";
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @GetMapping(value = "/allBillboards")
    public String allBillboardsPage(Model model) {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        String dateString = dateFormat.format(new Date());

        int currentMonth = Integer.parseInt(dateString);
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("currentMonth", currentMonth);
        model.addAttribute("billboards", billboardService.getAllActiveBillboards());
        model.addAttribute("notActiveBillboards", billboardService.getAllNotActiveBillboards());
        return "allBilboards";
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @GetMapping(value = "/addNewBillboard")
    public String addNewBillboardPage(Model model) {
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("locations", locationService.getAllLocations());
        return "newBillboards";
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @PostMapping(value = "/deleteBillboard")
    public String deleteBillboard(@RequestParam(name = "deleteBillboardId") Long deleteBillboardId) {
        Billboard billboard = billboardService.getBillboardById(deleteBillboardId);
        if (billboard.isActive()) {
            billboard.setActive(false);
        }
        billboardService.updateBillboard(billboard);
        //billboardService.deleteBillboard(deleteBillboardId);
        return "redirect:/admin/allBillboards/";
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @PostMapping(value = "/recoverBillboard")
    public String recoverBillboard(@RequestParam(name = "recoverBillboardId") Long recoverBillboardId) {
        Billboard billboard = billboardService.getBillboardById(recoverBillboardId);
        if (!billboard.isActive()) {
            billboard.setActive(true);
        }
        billboardService.updateBillboard(billboard);
        return "redirect:/admin/allBillboards/";
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @PostMapping(value = "/editBillboard")
    public String editBillboard(@RequestParam(name = "id") Long id,
                                @RequestParam(name = "location") Long location,
                                @RequestParam(name = "city") Long city,
                                @RequestParam(name = "size") String size,
                                @RequestParam(name = "type") String type,
                                @RequestParam(name = "isHasLightning") boolean isHasLightning,
                                @RequestParam(name = "price") int price,
                                @RequestParam(name = "billboard_url") MultipartFile file) {
        Billboard billboard = billboardService.getBillboardById(id);
        billboard.setCity_id(city);
        billboard.setPrice(price);
        billboard.setType(type);
        billboard.setActive(true);
        billboard.setLocation_id(location);
        billboard.setSize(size);
        billboard.setHasLightning(isHasLightning);

        if (Objects.equals(file.getContentType(), "image/jpg") || Objects.equals(file.getContentType(), "image/jpeg")) {
            fileUploadService.uploadImg(file, billboard);
        }

        billboardService.updateBillboard(billboard);

        return "redirect:/admin/allBillboards/";

    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @PostMapping(value = "/addBillboard")
    public String addBillboard(@RequestParam(name = "location") Long location,
                               @RequestParam(name = "size") String size,
                               @RequestParam(name = "isHasLightning") boolean isHasLightning,
                               @RequestParam(name = "price") int price,
                               @RequestParam(name = "type") String type,
                               @RequestParam(name = "city") Long city,
                               @RequestParam(name = "billboard_url") MultipartFile file) {
        Billboard billboard = Billboard.builder()
                .location_id(location)
                .price(price)
                .isActive(true)
                .isHasLightning(isHasLightning)
                .type(type)
                .size(size)
                .city_id(city)
                .build();


        billboardService.addBillboard(billboard);
        System.out.println(file.getContentType() + "----");

        if (Objects.equals(file.getContentType(), "image/jpg") || Objects.equals(file.getContentType(), "image/jpeg")) {
            fileUploadService.uploadImg(file, billboard);
        }

        return "redirect:/admin/allBillboards/";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/getAva/{token}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getAva(@PathVariable(name = "token", required = false) String token) throws IOException {
        String pictureUrl = loadURL + "default.jpg";
        if (token != null) {
//            pictureUrl = loadURL + token + ".jpg";
            pictureUrl = loadURL + token;
        }
        System.out.println(token + "--------- " + pictureUrl);
        InputStream in;

        try {
            ClassPathResource resource = new ClassPathResource(pictureUrl);
            in = resource.getInputStream();
        } catch (Exception e) {
            pictureUrl = loadURL + "default.jpg";
            ClassPathResource resource = new ClassPathResource(pictureUrl);
            in = resource.getInputStream();
        }

        return IOUtils.toByteArray(in);
    }


}