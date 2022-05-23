package com.example.projectge.service;

import com.example.projectge.models.Role;
import com.example.projectge.models.User;

import java.util.List;

public interface AccountService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String userName,String role);
    User findUserByUserName(String userName);
    User findUserByEmail(String email);

    List<User> findAll();

    User getById(Long id);

    void delete(User user);
}
