package com.controller;

import com.entity.User;
import com.service.ActivityService;
import com.service.ActivityUserService;
import com.service.UserService;
import com.validator.InputDataValidator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class MainController {

    private static final Logger logger = Logger.getLogger(MainController.class);

    @Autowired
    UserService userService;

    @Autowired
    ActivityService activityService;

    @Autowired
    ActivityUserService activityUserService;

    @GetMapping("")
    public String welcomeMethod() {
        return "welcome";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationCheck(@RequestParam(name = "login") String login,
                                    @RequestParam(name = "password") String password,
                                    @RequestParam(name = "email", required = false) String email,
                                    Model model) {
        System.out.println("post registration");
        if (!InputDataValidator.validateLoginInput(login, password, email)) {
            model.addAttribute("regErrorInvalidInput", true);
            return "registration";
        }

        User user = new User();
        user.setName(login);
        user.setPassword(password);

        if(!(email == null || email.isEmpty())){
            user.setEmail(email);
        }

        if (!userService.saveUser(user)) {
            model.addAttribute("regErrorUserExists", true);
            return "registration";
        }
        System.out.println("user was saved after registration");
        return "redirect:login?error=false";
    }

    @GetMapping("/login")
    public String getLoginForm(@RequestParam(name = "error") boolean error, Model model) {

        model.addAttribute("userForm", new User());
        if (error) {
            model.addAttribute("logErrorNoSuchUserFound", true);
        }
        return "login";
    }

    @GetMapping("/successfulLogin")
    public String loginCheck(Model model, Authentication authResult) {

        String role = authResult.getAuthorities().toString();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user.getStatus().equals("blocked")) {
            model.addAttribute("userIsBlocked", true);
            return "login";
        }

        if (role.contains("user")) {
            return "redirect:user?userId=" + user.getId() + "&show=false";

        } else if (role.contains("admin")) {
            return "redirect:admin";
        }

        logger.error("Can not identify role, redirect /logout");
        return "redirect:logout";
    }


    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Authentication authentication) {
        return "error";
    }

}
