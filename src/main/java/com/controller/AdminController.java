package com.controller;

import com.dto.UserDTO;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("ActivityTracker/admin")
public class AdminController {

    @Autowired
    UserService userService;


    @GetMapping("")
    public String welcomeAdmin(Model model) {
        List<UserDTO> users = userService.getAllUsersList();
        model.addAttribute("users", users);

        return "admin/mainAdmin";
    }

    @PostMapping("/logout")
    public String returnToMain(){
        return "redirect:/ActivityTracker";
    }

    @PostMapping("/profile")
    public String getProfile(){

        return "admin/adminProfile";
    }
}
