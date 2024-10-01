package com.example.qlud.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/home")
    public String getHome(Model model){
        return "home";
    }

    @GetMapping("/crawl")
    public String showCrawlPage(Model model){
        return "crawl";
    }

}
