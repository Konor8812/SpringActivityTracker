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

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("Admin authenticated, id = " + user.getId() + ", name = " + user.getName());
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

    @GetMapping("/changePassword")
    public String showUpdatePassField(Model model) {
        model.addAttribute("shouldShowChangePassField", true);
        return "admin/adminProfile";
    }

    @PostMapping("/changePassword")
    public String changePass(@RequestParam(name = "newPass") String newPassword) {
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.updateUserPass(user.getId(), newPassword);

        return "redirect:admin/profile";
    }

    @GetMapping("/banUser")
    public String banUser(long userId) {
        userService.updateStatus(userId, "blocked");
        return "redirect:admin";
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

    @GetMapping("/userRequests")
    public String getUsersRequests(@RequestParam(name="userId") long userId, Model model){
        List<ActivityDTO> usersActivities = activityUserService.getUsersRequests(userId);
        model.addAttribute("usersActivities", usersActivities);
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);
        return "admin/userRequests";
    }

    @GetMapping("/userRequests/denyOrApproveActivity")
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

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam(name = "userId") long userId){
        userService.deleteUser(userId);
        return "redirect:/admin";
    }


}
