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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


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
    public String welcomeMethod(HttpSession session, @ModelAttribute(name="lang") String lang) {
        session.setAttribute("lang", lang);
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
        logger.info("New user was saved, user id = " + user.getId() + "  " + java.time.ZonedDateTime.now());
        return "redirect:login?error=false";
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(name = "error", required = false) boolean error,
                            @RequestParam(name = "banned", required = false) boolean banned,
                            Model model) {
        System.out.println(banned);
        model.addAttribute("userForm", new User());
        if (error) {
            model.addAttribute("logErrorNoSuchUserFound", true);
        }
        model.addAttribute("userIsBlocked", banned);
        return "login";
    }

    @GetMapping("/successfulLogin")
    public String loginCheck( Authentication authResult, Model model) {

        String role = authResult.getAuthorities().toString();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user.getStatus().equals("blocked")) {
            SecurityContextHolder.clearContext();
            model.addAttribute("userIsBlocked", true);
            return "redirect:/login?banned=true";
        }

        if (role.contains("user")) {
            logger.info("User authenticated, id = " + user.getId() + ", name = " + user.getName() + "  " + java.time.ZonedDateTime.now());
            return "redirect:user?userId=" + user.getId() + "&show=false";

        } else if (role.contains("admin")) {
            logger.info("Admin authenticated, id = " + user.getId() + ", name = " + user.getName() + "  " + java.time.ZonedDateTime.now());
            return "redirect:admin";
        }

        logger.error("Can not identify role | " + role + " |, redirect /logout");
        return "redirect:logout";
    }


    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Authentication authentication) {
        return "error";
    }

}
