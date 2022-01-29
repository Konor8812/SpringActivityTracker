package com.controller;


import com.dto.ActivityDTO;
import com.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("ActivityTracker/user")
public class UserController {

    @Autowired
    ActivityService activityService;



    @GetMapping("")
    public String welcomeUser(Model model){

        List<ActivityDTO> activities = activityService.getAllActivitiesList();
        model.addAttribute("activities", activities);


        return "user/mainUser";
    }

    @PostMapping("/logout")
    public String returnToMain(){
        return "redirect:/ActivityTracker";
    }


}
