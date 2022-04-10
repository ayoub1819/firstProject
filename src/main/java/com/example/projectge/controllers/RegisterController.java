package com.example.projectge.controllers;

import com.example.projectge.models.User;
import com.example.projectge.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
public class RegisterController {
    @Autowired
    private AccountService accountService;

    @GetMapping(path = "/signup")
    public String signup(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("success","get");
        return "/security/register";
    }

    @PostMapping(path = "/signup")
    public String register(@Valid User user,BindingResult bindingResult,Model model){

        var isValid = "les coordonnés remplis sont invalide";
        if (!bindingResult.hasErrors()){
            boolean emailExist = accountService.findUserByEmail(user.getEmail())!=null;
            boolean userNameExist = accountService.findUserByUserName(user.getUsername())!=null;
            if ( !emailExist && !userNameExist ){
            isValid = "success";
            accountService.saveUser(user);
            accountService.addRoleToUser(user.getUsername(),"FOURNISSEUR");}
            else
                if (emailExist) isValid = "email deja exist";
                else if (userNameExist) isValid = "username deja exist";
        }
        model.addAttribute("success", isValid);
        return "/security/register";
    }
}
