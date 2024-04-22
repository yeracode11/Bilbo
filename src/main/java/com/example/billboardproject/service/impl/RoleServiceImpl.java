package com.example.billboardproject.service.impl;

import com.example.billboardproject.model.Role;
import com.example.billboardproject.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {


    @Override
    public Role getRoleByRoleName(String role) {
        if (Role.MANAGER.name().equals(role)) return Role.MANAGER;
        return Role.USER;
    }


}
