package com.example.billboardproject;

import com.example.billboardproject.controller.AuthController;
import com.example.billboardproject.model.User;
import com.example.billboardproject.security.SecurityConfig;
import com.example.billboardproject.service.impl.RoleServiceImpl;
import com.example.billboardproject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class ControllerUTest {

    @Mock
    private UserServiceImpl userService;
    @Mock
    private RoleServiceImpl roleService;



    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private SecurityConfig securityConfig;


    @InjectMocks
    private AuthController authController;



    @BeforeEach
    public void setUp() {
        Mockito.when(securityConfig.passwordEncoder()).thenReturn(passwordEncoder);
    }

    @Test
    public void testRegisterPage() {
        String name = "Aigerim2";
        String surname = "Turazhanova2";
        String email = "aigerim2@gmail.com";
        String password = "123456Ai+";
        String confPassword = "123456Ai+";

        Mockito.when(passwordEncoder.encode(password)).thenReturn(password);

        String result = authController.registerPage(email, password, confPassword, name, surname);

        Mockito.verify(userService, Mockito.times(1)).addUser(Mockito.any(User.class));

        Assertions.assertEquals("redirect:/auth/?accountExist", result);
    }

}
