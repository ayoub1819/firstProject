package com.example.projectge.service;

import com.example.projectge.DAO.RoleRepository;
import com.example.projectge.DAO.UserRepository;
import com.example.projectge.models.Role;
import com.example.projectge.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountServiceImp implements AccountService{
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public User saveUser(User user) {
        var hashedPass = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashedPass);
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String userName, String role) {
        Role UserRole = roleRepository.findRoleByRoleName(role);
        User user = userRepository.findUserByUsername(userName);
        user.getRole().add(UserRole);
    }

    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findUserByUsername(userName);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
