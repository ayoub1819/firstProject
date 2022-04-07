package com.example.projectge.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BesionDemandesController {
    @GetMapping(path = "/besoin")
    public String sendBesoinForm(){
        return "/ofresEtBesoin/besoins";
    }
}
