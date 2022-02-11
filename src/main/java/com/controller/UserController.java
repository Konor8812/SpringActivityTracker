package com.controller;


import com.dto.ActivityDTO;
import com.entity.ActivityUser;
import com.entity.User;
import com.service.ActivityService;
import com.service.ActivityUserService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    ActivityService activityService;

    @Autowired
    UserService userService;

    @Autowired
    ActivityUserService activityUserService;

    @GetMapping("")
    public String welcomeUser(Model model){
        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ActivityDTO> activities = activityUserService.getAvailableActivities(currentUser.getId());

        model.addAttribute("shouldShowTags", true);
        model.addAttribute("activities", activities);
        return "user/mainUser";
    }

    @GetMapping("reqActivity")
    public String reqActivity(@RequestParam(name = "activityId") long activityId){
        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        activityUserService.reqActivity(activityId, currentUser.getId());

        return "redirect:user/";
    }

    @GetMapping("profile")
    public String getProfile(Model model){
        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ActivityDTO> usersActivities = activityUserService.getUsersActivities(currentUser.getId());
        model.addAttribute("usersActivities", usersActivities);

        return "user/userProfile";

    }

    @GetMapping("profile/completedActivity")
    public String completedActivity(@RequestParam(name="activityId") long activityId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        activityUserService.activityCompleted(activityId, user.getId());

        return "redirect:profile";
    }

    @GetMapping("tags")
    public String changeTagsStatus(@RequestParam(name="show") boolean shouldShow,
                                   Model model){
        model.addAttribute("shouldShowTags", shouldShow);
        return "user/mainUser";
    }
}
