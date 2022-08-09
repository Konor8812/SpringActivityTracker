package com.controller;

import com.dto.ActivityDTO;
import com.dto.UserDTO;
import com.entity.Activity;
import com.entity.Description;
import com.entity.User;
import com.service.ActivityService;
import com.service.ActivityUserService;
import com.service.UserService;
import com.util.Util;
import com.validator.InputDataValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    public String welcomeAdmin(Model model, HttpSession session) {
        if (session.getAttribute("lang") == null){
            session.setAttribute("lang", "en");
        }
        List<UserDTO> users = userService.getAllUsersList();
        model.addAttribute("users", users);
        return "admin/mainAdmin";
    }

    @GetMapping("/banUser")
    public String banUser(@RequestParam(name = "shouldBan") boolean ban,
                          @RequestParam(name = "userId") long userId,
                          Model model, HttpSession session) {
        String status;
        if (ban) {
            status = "blocked";
        } else {
            status = "available";
        }
        userService.updateStatus(userId, status);
        return welcomeAdmin(model, session);
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            model.addAttribute("currentUserEmptyEmail", true);
        }

        return "admin/adminProfile";
    }

    @GetMapping("/profile/changePassword")
    public String showUpdatePassField(Model model, boolean isNewPassValid) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            model.addAttribute("currentUserEmptyEmail", true);
        }
        if (!isNewPassValid) {
            model.addAttribute("newValueInvalid", true);
        }
        model.addAttribute("shouldShowChangePassField", true);
        return "admin/adminProfile";
    }

    @PostMapping("/profile/changePassword")
    public String changePass(@RequestParam(name = "newPass") String newPassword, Model model) {
        if (InputDataValidator.validatePassword(newPassword)) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userService.updateUserPass(user.getId(), newPassword);
            return getProfile(model);
        } else {
            return showUpdatePassField(model, false);
        }
    }

    @PostMapping("profile/addEmail")
    public String addEmail(@RequestParam(name = "email") String email, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.updateUserEmail(user.getId(), email);
        return getProfile(model);
    }


    @GetMapping("/activities")
    public String getActivities(Model model,
                                @RequestParam(name = "show", required = false) boolean shouldShowTags,
                                @RequestParam(name = "errorInvalidData", required = false) boolean invalidData,
                                @RequestParam(name = "sortBy", required = false, defaultValue = "name") String sortBy,
                                @RequestParam(name = "orderBy", required = false, defaultValue = "asc") String orderBy,
                                @RequestParam(name= "activitiesPageNumber", required = false) Integer activitiesPageNumber,
                                @RequestParam(name="activities", required = false) String activitiesAsString,
                                HttpSession session) {
        String lang = (String) session.getAttribute("lang");
        List<Activity> activities = Util.parseActivitiesFromString(activitiesAsString, lang);
        int pageNumber;
        if(activitiesPageNumber == null){
            pageNumber = 0;
        }else {
            pageNumber = activitiesPageNumber;
        }
        boolean hasNextPageToShow;
        if(activities == null) {
            activities = activityService.getNextActivities(pageNumber, lang);
        }else {
            if(!sortBy.equals("noSortingNeeded")) {
                activities = Util.sortActivities(activities, sortBy, orderBy);
            }
        }
        hasNextPageToShow = activities.size() == 5 && !activityService.getNextActivities(pageNumber + 1, "en").isEmpty();
        model.addAttribute("activities", activities);
        model.addAttribute("activitiesPageNumber", activitiesPageNumber);
        model.addAttribute("hasNextPageToShow", hasNextPageToShow);
        model.addAttribute("activitiesPageNumber", pageNumber);
        model.addAttribute("shouldShowTags", shouldShowTags);
        model.addAttribute("invalidData", invalidData);
        return "admin/adminActivity";
    }

    @GetMapping("/activities/deleteActivity")
    public String deleteActivity(@RequestParam(name = "activityId") long activityId,
                                 Model model,
                                 HttpSession session) {
        activityUserService.activityDeleted(activityId);
        activityService.deleteActivity(activityId);
        return getActivities(model, false, false, "name", "asc", 0, null, session);
    }

    @PostMapping("/activities/addActivity")
    public String addActivity(@RequestParam(name = "name") String name,
                              @RequestParam(name = "duration") String duration,
                              @RequestParam(name = "reward") double reward,
                              @RequestParam(name = "description") String description,
                              Model model,
                              HttpSession session) {
        if (InputDataValidator.validateActivity(name, duration, reward, description)) {
            Activity activity = new Activity();
            activity.setName(name);
            activity.setDuration(duration);
            activity.setReward(reward);
            activity.setDescription(new Description(description));

            if (activityService.addActivity(activity)) {
                return getActivities(model, false, false, "name", "asc", 0,  null, session);
            }
        }
        return getActivities(model, false, true, "name", "asc", 0, null, session);

    }

    @GetMapping("/usersStats")
    public String getUsersRequests(@RequestParam(name = "userId") long userId,
                                   Model model,
                                   HttpSession session) {
        String lang = (String)session.getAttribute("lang");
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);

        List<ActivityDTO> usersActivities = activityUserService.getUsersActivities(userId, lang);
        model.addAttribute("usersActivities", usersActivities);
        if (usersActivities.isEmpty()) {
            model.addAttribute("hasActivities", false);
        } else {
            model.addAttribute("hasActivities", true);
        }

        return "admin/userStatistics";
    }

    @GetMapping("/usersStats/denyOrApproveActivity")
    public String approveActivity(@RequestParam(name = "userId") long userId,
                                  @RequestParam(name = "activityId") long activityId,
                                  @RequestParam(name = "approve") boolean approve, Model model,
                                  HttpSession session) {
        if (approve) {
            activityUserService.approveActivityForUser(activityId, userId);
        } else {
            activityUserService.denyApproval(activityId, userId);
        }
        return getUsersRequests(userId, model, session);
    }

    @GetMapping("/activities/search")
    public String getSearchPage(@RequestParam(name = "tagName", required = false) String tagName,
                                @RequestParam(name = "activityId", required = false) Long activityId,
                                Model model) {
        if (tagName == null || tagName.isEmpty()) {
            return "admin/adminActivitySearch";
        }
        if (activityId != null) {
            activityService.deleteActivity(activityId);
        }
        List<Activity> activities = activityService.getAllActivitiesWithTag(tagName);
        model.addAttribute("shouldShowSearchResult", true);
        model.addAttribute("activitiesWithSuchTag", activities);
        return "admin/adminActivitySearch";
    }

    @PostMapping("/activities/addTranslation")
    public String addTranslationForActivity(@ModelAttribute(name = "language") String language,
                                            @ModelAttribute(name = "enName") String enName,
                                            @ModelAttribute(name = "translatedName") String translatedName,
                                            @ModelAttribute(name = "translatedDescription") String translatedDescription,
                                            @RequestParam(name = "activityPage", required = false) int activityPageNum,
                                            @RequestParam(name = "activities") List<Activity> activities,
                                            Model model,
                                            HttpSession session) {
        boolean added = false;
        if(InputDataValidator.validateActivityForLocalization(language, enName, translatedName, translatedDescription)){
             added = activityService.addLocalizationForActivity(language, enName, translatedName, translatedDescription);
        }
        if (!added){
            model.addAttribute("translationError", true);
        }
        return getActivities(model, true, false, "name", "asc", activityPageNum,  null, session);
    }

}
