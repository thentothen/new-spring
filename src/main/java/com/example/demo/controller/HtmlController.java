package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping("/login.html")
    public String login1(Model model) {
        return "login";
    }

    @GetMapping("/dictionary.html")
    public String dictionary1(Model model) {
        return "dictionary";
    }

}
