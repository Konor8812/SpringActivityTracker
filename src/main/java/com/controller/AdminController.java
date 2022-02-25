package com.controller;

import com.dto.ActivityDTO;
import com.dto.UserDTO;
import com.entity.Activity;
import com.entity.User;
import com.service.ActivityService;
import com.service.ActivityUserService;
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

    @Autowired
    ActivityUserService activityUserService;

    @GetMapping("")
    public String welcomeAdmin(Model model) {
        List<UserDTO> users = userService.getAllUsersList();
        model.addAttribute("users", users);

        return "admin/mainAdmin";
    }

    @GetMapping("/banUser")
    public String banUser(@RequestParam(name = "shouldBan") boolean ban,
                          @RequestParam(name ="userId") long userId,
                          Model model) {
        String status;
        if(ban){
            status = "blocked";
        }else{
            status = "available";
        }
        userService.updateStatus(userId, status);
        return welcomeAdmin(model);
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            model.addAttribute("currentUserEmptyEmail", true);
        }

        return "admin/adminProfile";
    }

    @GetMapping("/changePassword")
    public String showUpdatePassField(Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            model.addAttribute("currentUserEmptyEmail", true);
        }
        model.addAttribute("shouldShowChangePassField", true);
        return "admin/adminProfile";
    }

    @PostMapping("/changePassword")
    public String changePass(@RequestParam(name = "newPass") String newPassword, Model model) {
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.updateUserPass(user.getId(), newPassword);

        return getProfile(model);
    }

    @PostMapping("/addEmail")
    public String addEmail(@RequestParam(name = "email") String email, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.updateUserEmail(user.getId(), email);
        return getProfile(model);
    }


    @GetMapping("/activities")
    public String getActivities(Model model,
                                @RequestParam(name="show", required=false) boolean shouldShowTags){
    List<Activity> activities = activityService.getAllActivities();
    model.addAttribute("activities", activities);
    model.addAttribute("shouldShowTags", shouldShowTags);
    return "admin/adminActivity";
    }

    @GetMapping("/activities/deleteActivity")
    public String deleteActivity(@RequestParam(name="activityId") long activityId, Model model){
        activityService.deleteActivity(activityId);
        return getActivities(model, false);
    }

    @GetMapping("/usersStats")
    public String getUsersRequests(@RequestParam(name="userId") long userId,
                                   Model model)
    {
        List<ActivityDTO> usersActivities = activityUserService.getUsersRequests(userId);
        model.addAttribute("usersActivities", usersActivities);
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);
        return "admin/userStatistics";
    }

    @GetMapping("/usersStats/denyOrApproveActivity")
    public String approveActivity(@RequestParam(name="userId") long userId,
                                  @RequestParam(name="activityId") long activityId,
                                  @RequestParam(name="approve") boolean approve){
        if(approve){
            activityUserService.approveActivityForUser(activityId, userId);
        }else {
            activityUserService.denyApproval(activityId, userId);
        }
        return "redirect:admin/userRequests?userId=" + userId;
    }

    @GetMapping("/usersStats/deleteUser")
    public String deleteUser(@RequestParam(name = "userId") long userId){
        userService.deleteUser(userId);
        return "redirect:admin";
    }


}
