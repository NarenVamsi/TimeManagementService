package com.vamsi.TimeManagementService.webcontrollers;

import com.vamsi.TimeManagementService.dataaccess.ScheduleStore;
import com.vamsi.TimeManagementService.dataaccess.UserStore;
import com.vamsi.TimeManagementService.databasemodels.ScheduleDocument;
import com.vamsi.TimeManagementService.databasemodels.UserDocument;
import com.vamsi.TimeManagementService.webcontrollers.webmodels.Login;
import com.vamsi.TimeManagementService.webcontrollers.webmodels.UserRegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    HttpSession httpSession;

    @Autowired
    private UserStore userStore;

    @Autowired
    private UserController userController;

    @Autowired
    private ScheduleStore scheduleStore;

    @GetMapping("/greeting")
    public String greeting( Model model) {
        if( httpSession.getAttribute("user") == null)
            return "redirect:loginform";
        else model.addAttribute("name", httpSession.getAttribute("user"));
        return "greeting";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@ModelAttribute("login")Login login, Model model) {
        UserDocument userDocument = userStore.findByUsername(login.getUsername());
        if(userDocument == null) {
            model.addAttribute("error","Invalid Username");
            return "loginform";
        }
        else if (!userDocument.getPassword().equals( login.getPassword())) {
            model.addAttribute("error", "Please enter correct password");
            return "loginform";
        }
        else {
            httpSession.setAttribute("user", login.getUsername());
            httpSession.setAttribute("employeeCode", userDocument.getEmployeeCode());
            if(userDocument.getEmployeeCode().equals("EXECUTIVE"))
                return "redirect:greeting";
            else return "redirect:secretaryGreeting";
        }
    }

    @RequestMapping(value = "/loginform", method = RequestMethod.GET)
    public String loginform(Model model) {
        if(httpSession.getAttribute("user") == null)
        {
            model.addAttribute("error","noError");

            return "loginform";
        }

        else {
            UserDocument userDocument = userStore.findByUsername((String)httpSession.getAttribute("user"));
            if(userDocument.getEmployeeCode().equals("EXECUTIVE"))
                return "redirect:greeting";
            else return "redirect:/secretaryGreeting";
        }

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model) {
        httpSession.setAttribute("user",null);
        return "loginform";
    }

    @RequestMapping(value = "/registerform", method = RequestMethod.GET)
    public String registerForm(Model model) {
        if(httpSession.getAttribute("user") == null) {
            model.addAttribute("error", "noError");
            return "registerform";
        }
        else
            return "redirect:greeting";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("register") UserRegisterForm userRegisterForm, Model model) {
        if(userCheck(userRegisterForm).equals("noError")) {
            UserDocument userDocument = userRegisterForm.toUserDocument();
            userStore.insert(userDocument);
            ScheduleDocument scheduleDocument = new ScheduleDocument();
            scheduleDocument.setEmployeeId(userRegisterForm.getUserName());
            scheduleDocument.setDailyScheduleDocuments(new ArrayList<>());
            scheduleStore.insert(scheduleDocument);
            return "registerSuccess";
        }
        else {
            model.addAttribute("error", userCheck(userRegisterForm));
            return "registerform";
        }
    }

    private String userCheck(UserRegisterForm userRegisterForm) {
        List<String> userList = userController.searchUsers();
        for(String userName: userList) {
            if (userName.equals(userRegisterForm.getUserName())) {

                return "Username already exists. Please select another";
            }
        }
        if (!userRegisterForm.getPassword().equals(userRegisterForm.getConfirmPassword()))
            return "Passwords do not match";
        else if (userRegisterForm.getPassword().length()<8)
            return "Password is very short. Please select long password";
        return "noError";

    }
}