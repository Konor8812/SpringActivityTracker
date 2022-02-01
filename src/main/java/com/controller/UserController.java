package com.controller;


import com.dto.ActivityDTO;
import com.service.ActivityService;
import com.service.ActivityUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    ActivityUserService activityUserService;

    @GetMapping("/activities")
    public String showActivities(Model model){

        List<ActivityDTO> activities = activityService.getAllActivitiesList();
        model.addAttribute("activities", activities);

        return "user/mainUser";
    }

    @GetMapping("")
    public String welcomeUser(){
        return "redirect:activities";
    }

    @PostMapping("reqActivityForUser")
    public String reqActivity(@RequestParam(name = "activityId") long activityId,
                              @RequestParam(name = "userId") long userId){
        activityUserService.reqActivity(activityId, userId);

        return "redirect:activities";
    }

}
