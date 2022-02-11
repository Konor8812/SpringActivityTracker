package com.controller;

import com.dto.UserDTO;
import com.entity.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;


    @GetMapping("")
    public String welcomeAdmin(Model model) {
        List<UserDTO> users = userService.getAllUsersList();
        model.addAttribute("users", users);

        return "admin/mainAdmin";
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            model.addAttribute("currentUserEmptyEmail", true);
        }

        return "admin/adminProfile";
    }

    @GetMapping("/changePass")
    public String showUpdatePassField(Model model) {
        model.addAttribute("shouldShowChangePassField", true);
        return "admin/adminProfile";
    }

    @PostMapping("/changePass")
    public String changePass(@RequestParam(name = "newPass") String newPassword) {
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.updateUserPass(user.getId(), newPassword);

        return "redirect:profile";
    }

    @GetMapping("/banUser")
    public String banUser(long userId) {
        userService.updateStatus(userId, "blocked");
        return "redirect:/";
    }


    @PostMapping("/addEmail")
    public String addEmail(@RequestParam(name = "email") String email) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.updateUserEmail(user.getId(), email);
        return "redirect:profile";
    }

    @GetMapping("/activities")
    public String getActivitities(Model model){


        return "admin/adminActivities";
    }
}
