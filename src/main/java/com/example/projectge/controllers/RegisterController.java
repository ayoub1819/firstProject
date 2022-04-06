package com.example.projectge.controllers;

import com.example.projectge.DAO.UserRepository;
import com.example.projectge.models.ERole;
import com.example.projectge.models.User;
import com.example.projectge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "/signup")
    public String signup(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("success","get");
        return "/security/register";
    }

    @PostMapping(path = "/signup")
    public String register(@Valid User user,BindingResult bindingResult,Model model){
        var isValid = "fail";
        if (!bindingResult.hasErrors()){
            isValid = "success";
            userService.register(user,ERole.ROLE_FOURNISSEUR);
        }
        model.addAttribute("success", isValid);
        return "/security/register";
    }
}
