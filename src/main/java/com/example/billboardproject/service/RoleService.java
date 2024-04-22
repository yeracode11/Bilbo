package com.example.billboardproject.service;

import com.example.billboardproject.model.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role getRoleByRoleName(String role);
}
