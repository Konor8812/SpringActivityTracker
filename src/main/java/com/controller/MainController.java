package com.controller;


import com.entity.User;
import com.service.ActivityService;
import com.service.ActivityUserService;
import com.service.UserService;
import com.validator.UserRegAndLogValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ActivityTracker")
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    ActivityService activityService;

    @Autowired
    ActivityUserService activityUserService;

    @GetMapping("")
    public String testMethod() {
        return "index/welcome";
    }

    @PostMapping("/reg")
    public String registrationCheck(@RequestParam(name = "name") String name,
                                    @RequestParam(name = "password") String pass,
                                    @RequestParam(name = "email", required = false) String email,
                                    Model model) {
        User user = new User(name, pass, email);

        if (!UserRegAndLogValidator.validateLoginInput(name, pass, email)) {
            model.addAttribute("regErrorInputNotValid", true);
            return "index/welcome";
        }

        if (userService.saveUser(user)) {
            model.addAttribute("currentUser", user);
            return "user/mainUser";
        } else {
            model.addAttribute("regErrorUserExists", true);
            return "index/welcome";
        }
    }

    @PostMapping("/login")
    public String loginCheck(@RequestParam(name = "name") String name,
                             @RequestParam(name = "password") String pass,
                             Model model) {
        if (!UserRegAndLogValidator.validateLoginInput(name, pass, null)) {
            model.addAttribute("logErrorInputNotValid", true);
            return "index/welcome";
        }
        User user = userService.findByNameAndPass(name, pass);
        if (user != null) {
            return getPageByRole(user.getRole());
        }
        model.addAttribute("logErrorWrongInput", true);
        return "index/welcome";
    }

    private String getPageByRole(String userRole){
        if(userRole.equals("user")){
            return "user/mainUser";
        }
        return "admin/mainAdmin";
    }
}
