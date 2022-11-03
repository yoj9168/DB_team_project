package com.example.DB_Team_Project.Controller;

import com.example.DB_Team_Project.Service.SelectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final SelectService selectService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("employees",selectService.select());
        return "index";
    }
}
