package com.example.billboardproject.service.impl;

import com.example.billboardproject.model.User;
import com.example.billboardproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    private UserServiceImpl underTest;


    @BeforeEach
    void setUp() {
        underTest = new UserServiceImpl(userRepository);
    }

    @Test
    void canGetAllUsers() {
        //when
        underTest.getAllUsers();
        //then
        verify(userRepository).findAll();
    }

    @Test
    void canGetUser() {
        String email = "just@gmail.com";
        //when
        underTest.getUser(email);
        //then
        verify(userRepository).findByEmail(email);
    }

    @Test
    void canAddUser() {
        // given
        User user = User.builder().name("Just").surname("Prostobekov").email("just@gmail.com").build();

        // when
        underTest.addUser(user);

        // then
        ArgumentCaptor<User> studentArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository)
                .save(studentArgumentCaptor.capture());

        User capturedUser = studentArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
    }

}