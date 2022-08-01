package com.controller;


import com.dto.ActivityDTO;
import com.entity.Activity;
import com.entity.User;
import com.service.ActivityService;
import com.service.ActivityUserService;
import com.service.UserService;
import com.validator.InputDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
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
    public String welcomeUser(Model model,
                              @RequestParam(name="show", required = false) boolean shouldShowTags,
                              HttpSession session){
        String lang = (String) session.getAttribute("lang");
        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Activity> activities = activityUserService.getAvailableActivities(currentUser.getId(), lang);

        model.addAttribute("shouldShowTags", shouldShowTags);
        model.addAttribute("activities", activities);
        return "user/mainUser";
    }

    @GetMapping("/reqActivity")
    public String requestActivity(@RequestParam(name = "activityId") long activityId, Model model, HttpSession session){
        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        activityUserService.reqActivity(activityId, currentUser.getId());
        return welcomeUser(model, false, session);
    }

    @GetMapping("/profile")
    public String getProfile(Model model,
                             @RequestParam(name = "emailInvalid", required = false) boolean emailInvalid,
                             HttpSession session){
        String lang = (String) session.getAttribute("lang");
        long currentUserId = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User currentUser = userService.findUserById(currentUserId);
        List<ActivityDTO> usersActivities = activityUserService.getUsersActivities(currentUserId, lang);
        if (!usersActivities.isEmpty()){
            model.addAttribute("hasActivities", true);
        }else{
            model.addAttribute("hasActivities", false);
        }

        model.addAttribute("usersActivities", usersActivities);
        model.addAttribute("user", currentUser);
        model.addAttribute("showEmailInvalidField", emailInvalid);
        return "user/userProfile";

    }

    @GetMapping("/profile/completedActivity")
    public String completedActivity(@RequestParam(name="activityId") long activityId, Model model,
                                    HttpSession session){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        activityUserService.activityCompleted(activityId, user.getId());
        return getProfile(model, false, session);
    }

    @GetMapping("/profile/gaveUpActivity")
    public String activityGaveUp(@RequestParam(name = "activityId") long activityId, Model model,
                                 HttpSession session){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        activityUserService.activityGaveUp(activityId, user.getId());
        return getProfile(model, false, session);
    }

    @PostMapping("profile/addEmail")
    public String addUsersEmail(@RequestParam(name="userId") long userId,
            @RequestParam(name = "value") String str,
                                Model model,
                                HttpSession session) {
        boolean emailValid = InputDataValidator.validateEmail(str);
        if (emailValid) {
            userService.updateUserEmail(userId, str);
            return getProfile(model, false, session);
        } else {
            return getProfile(model, true,session);
        }
    }

}