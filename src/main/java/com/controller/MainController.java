package com.controller;


import com.entity.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ActivityTracker")
public class MainController {

    @Autowired
    UserService service;

    @GetMapping("/")
    public String testMethod(@RequestParam(name="str") String s,
                             Model model){
        User user = new User();
        user.setName(s);
        user.setEmail("sdf@da.com");
        user.setPassword("f321");
        user.setStatus("available");
        user.setRole("admin");

        if(service.saveUser(user)){

            User newUser = service.findByName(user.getName());
            model.addAttribute("user", newUser);
            return "test";
        }
        return "error";
    }
}
