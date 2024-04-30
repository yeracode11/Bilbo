package com.example.billboardproject.controller;

import com.example.billboardproject.model.Billboard;
import com.example.billboardproject.model.Order;
import com.example.billboardproject.model.Role;
import com.example.billboardproject.model.User;
import com.example.billboardproject.security.SecurityConfig;
import com.example.billboardproject.service.OrderService;
import com.example.billboardproject.service.impl.BillboardServiceImpl;
import com.example.billboardproject.service.impl.CityServiceImpl;
import com.example.billboardproject.service.impl.LocationServiceImpl;
import com.example.billboardproject.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private BillboardServiceImpl billboardService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CityServiceImpl cityService;
    @Autowired
    private LocationServiceImpl locationService;
    @Autowired
    private SecurityConfig securityConfig;

    @GetMapping(value = "/")
    public String authPage() {
        return "redirect:/auth/";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/mainPage")
    public String profilePage(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("billboards", billboardService.getAllActiveBillboards());
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("locations", locationService.getAllLocations());
        if (user.getRole() == Role.MANAGER) return "redirect:/admin/incomingOrders";
        return "main2";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/basket")
    public String basketPage(Model model) {
        User currentUser = userService.getUserData();
        model.addAttribute("orders", orderService.getAllOrdersByUserId(currentUser.getId()));
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("locations", locationService.getAllLocations());
        return "basket";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/detailBillboard/{billboard_id}")
    public String detailBillboardPage(Model model,
                                      @PathVariable(name = "billboard_id") Long id) {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        String dateString = dateFormat.format(new Date());

        int currentMonth = Integer.parseInt(dateString);
        Billboard billboard = billboardService.getBillboardById(id);
        List<Order> billboardOrders = orderService.getAllOrdersByBillboardId(billboard.getId());

        List<String> dates = new ArrayList<>();
        List<String> anotherDates = new ArrayList<>();

        for (Order order : billboardOrders) {
            dates.add(order.getStartDate());
            anotherDates.add(order.getEndDate());
        }

        List<Integer> months = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d");
        for (int i = 0; i < dates.size() || i < anotherDates.size(); i++) {
            if (i < dates.size()) {
                LocalDate localDate = LocalDate.parse(dates.get(i), formatter);
                int month = localDate.getMonthValue();
                months.add(month);
            }
            if (i < anotherDates.size()) {
                LocalDate localDate = LocalDate.parse(anotherDates.get(i), formatter);
                int month = localDate.getMonthValue();
                months.add(month);
            }
        }

        List<Integer> months1 = new ArrayList<>(months);
        Set<Integer> uniqueMonths = new HashSet<>(months1);

        List<Integer> orderedMonths = new ArrayList<>();

        for (int i : uniqueMonths) {
            if (i >= currentMonth) {
                orderedMonths.add(i);
            }
        }

        model.addAttribute("billboardOrders", billboardOrders);
        model.addAttribute("billboard", billboard);
        model.addAttribute("orderedMonths", orderedMonths);
        model.addAttribute("currentMonth", currentMonth);
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("locations", locationService.getAllLocations());
        return "details";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/editOrViewProfile")
    public String editOrViewProfilePage(Model model) {
        User user = userService.getCurrentUser();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (user.getBirthday() != null) {
            Date myDate;
            try {
                myDate = dateFormat.parse(user.getBirthday());
                model.addAttribute("currentUserBirthday", myDate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        model.addAttribute("currentUser", user);
        return "editProfile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/changePassword")
    public String changePassword(@RequestParam(name = "reg_password") String password,
                                 @RequestParam(name = "userId") Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            user.setPassword(securityConfig.passwordEncoder().encode(password));
            userService.updateUser(user);
        }
        return "redirect:/editOrViewProfile/?successful";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/addAdditionalInfo")
    public String addAdditionalInfo(@RequestParam(name = "gender") String gender,
                                    @RequestParam(name = "birthday") String birthday,
                                    @RequestParam(name = "address") String address,
                                    @RequestParam(name = "city") String city,
                                    @RequestParam(name = "phone") String phone,
                                    @RequestParam(name = "userId") Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            user.setAddress(address);
            user.setBirthday(birthday);
            user.setCity(city);
            user.setGender(gender);
            user.setPhone(phone);
            userService.updateUser(user);
        }
        return "redirect:/editOrViewProfile/?additional";
    }
}
