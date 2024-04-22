package com.example.billboardproject.service.impl;

import com.example.billboardproject.job.Helper;
import com.example.billboardproject.model.User;
import com.example.billboardproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User temp = (User) authentication.getPrincipal();
        User currentUser = new User();
        if (temp != null) {
            currentUser = getUserById(temp.getId());
        }

        return currentUser;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public boolean addUser(User user) {
        boolean check = false;
        if(user != null) {
            if (userRepository.findByEmail(user.getEmail()) == null) {
                userRepository.save(user);
                check = true;
            }
        }
       return check;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = getUser(username);
        if (foundUser != null) return foundUser;
        throw new UsernameNotFoundException("User not found");
    }
    public void importUserCsv(MultipartFile file) {
        try {
            List<User> users = Helper.csvToUsers(file.getInputStream());
            for (User user: users) {
                if (userRepository.findByEmail(user.getEmail()) == null) {
                    userRepository.save(user);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
