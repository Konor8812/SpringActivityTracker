package com.controller;

import com.dto.UserDTO;
import com.entity.Activity;
import com.entity.User;
import com.service.ActivityService;
import com.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    UserService userService;

    @Autowired
    ActivityService activityService;


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
    public String getActivities(Model model , @RequestParam(name="shouldShowTags") boolean shouldShowTags){
    List<Activity> activities = activityService.getAllActivities();
    model.addAttribute("activities", activities);
    model.addAttribute("shouldShowTags", shouldShowTags);
    return "admin/adminActivity";
    }

    @GetMapping("/activities/deleteActivity")
    public String deleteActivity(@RequestParam(name="activityId") long activityId){
        activityService.deleteActivity(activityId);
        return "redirect:admin/activities";
    }

    @GetMapping("/toggleTags")
    public String toggleTags(@RequestParam(name="shouldShow") boolean shouldShowTags){
        return "redirect:admin/activities?shouldShowTags=" + shouldShowTags;

    }
}
