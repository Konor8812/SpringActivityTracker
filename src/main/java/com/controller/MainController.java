package com.controller;

import com.entity.User;
import com.service.ActivityService;
import com.service.ActivityUserService;
import com.service.UserService;
import com.validator.InputDataValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    ActivityService activityService;

    @Autowired
    ActivityUserService activityUserService;

    @GetMapping("")
    public String welcomeMethod(Model model) {
        User user = (User) model.getAttribute("currentUser");
        if(user != null) {
            return user.getRole().equals("user") ? "user/mainUser" : "admin/mainAdmin";
        }
        return "welcome";
    }

    @GetMapping("/registration")
    public String getRegistrationForm(){
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationCheck(@RequestParam(name = "name") String name,
                                    @RequestParam(name = "password") String pass,
                                    @RequestParam(name = "email", required = false) String email,
                                    Model model) {

        if (!InputDataValidator.validateLoginInput(name, pass, email)) {
            model.addAttribute("regErrorInputNotValid", true);
            return "registration";
        }

        User user = new User(name, pass, email);

        if (userService.saveUser(user)) {
            model.addAttribute("currentUser", user);
            return "redirect:/";
        } else {
            model.addAttribute("regErrorUserExists", true);
            return "registration";
        }
    }

    @GetMapping("/login")
    public String getLoginForm(){
        return "login";
    }

    @PostMapping("/successfulLogin")
    public String loginCheck(@RequestParam(name = "name") String name,
                             @RequestParam(name = "password") String pass,
                             Model model) {
        if (!InputDataValidator.validateLoginInput(name, pass, null)) {
            model.addAttribute("logErrorInputNotValid", true);
            return "login";
        }
        User user = userService.findByNameAndPass(name, pass);
        if (user != null) {
            model.addAttribute("currentUser", user);
            String role = user.getRole();
            if(role.equals("user")){
                return "redirect:user";
            }else {
                return "redirect:admin";
            }
        }
        model.addAttribute("logErrorNoSuchUserFound", true);
        return "login";
    }

    @PostMapping("/updateUser")
    public String updateUserPassOrEmail(@RequestParam(name="update") String fieldToUpdate,
                                        @RequestParam(name="newValue") String value,
                                        Model model){
        User user = (User) model.getAttribute("currentUser");
        if(fieldToUpdate.equals("password") && InputDataValidator.validatePassword(value)) {
            userService.updateUserPass(user.getId(), value);
            user.setPassword(value);
        } else if(fieldToUpdate.equals("email") && InputDataValidator.validateEmail(value)){
            userService.updateUserEmail(user.getId(), value);
            user.setEmail(value);
        }

        return user.getRole().equals("user") ? "user/userProfile" : "admin/adminProfile";
    }

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Authentication authentication) {
        return "error";
    }

    @GetMapping("/failureLogin")
    public String logError(){
        return "login";
    }
}
