package com.rusu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String homepage(){
        return "index";
    }

    @GetMapping("/dashboard/dashboard")
    public String dashBoard(){
        return "/dashboard/dashboard";
    }
 }
