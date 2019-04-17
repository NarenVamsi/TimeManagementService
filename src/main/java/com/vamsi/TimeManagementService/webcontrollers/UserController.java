package com.vamsi.TimeManagementService.webcontrollers;

import com.vamsi.TimeManagementService.dataaccess.UserStore;
import com.vamsi.TimeManagementService.databasemodels.UserDocument;
import com.vamsi.TimeManagementService.webcontrollers.webmodels.UpdatePasswordForm;
import com.vamsi.TimeManagementService.webcontrollers.webmodels.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    HttpSession httpSession;

    @Autowired
    private UserStore userStore;

    @RequestMapping(value = "/searchusers",method = RequestMethod.GET)
    @ResponseBody
    public List<String> searchUsers(){
        List<String> userList = new ArrayList<>();
        for(UserDocument userDocument : userStore.findAll()) {
            userList.add(userDocument.getUserName());
        }
        return userList;
    }

    @RequestMapping(value = "/userDetails", method = RequestMethod.GET)
    public UserDocument getUser(String userName) {
        UserDocument userDocument = userStore.findByUsername(userName);
        return userDocument;
    }

    @RequestMapping(value = "/userProfile", method = RequestMethod.GET)
    public String getUserProfile(Model model) {
        if( httpSession.getAttribute("user") == null)
            return "redirect:loginform";
        UserDocument userDocument = userStore.findByUsername((String)httpSession.getAttribute("user"));
        model.addAttribute("userDocument",userDocument);
        return "userProfile";
    }

    @RequestMapping(value = "/userProfile", method = RequestMethod.POST)
    public String getUserProfile(@ModelAttribute("updateForm") UserProfile userProfile, Model model) {
        if( httpSession.getAttribute("user") == null)
            return "redirect:loginform";
        UserDocument userDocument = userStore.findByUsername((String)httpSession.getAttribute("user"));

        userDocument.setPhoneNumber(userProfile.getPhoneNumber());
        userDocument.setFirstName(userProfile.getFirstName());
        userDocument.setLastName(userProfile.getLastName());
        userDocument.setEmail(userProfile.getEmail());
        userStore.save(userDocument);
        return "redirect:userProfile";
    }

    @RequestMapping(value = "/updatePassword" , method = RequestMethod.GET)
    public String updatePassword(Model model) {
        if( httpSession.getAttribute("user") == null)
            return "redirect:loginform";
        model.addAttribute("msg", "noError");
        return "updatePassword";
    }

    @RequestMapping(value = "/updatePassword" , method = RequestMethod.POST)
    public String updatePassword(@ModelAttribute("updatePasswordForm") UpdatePasswordForm updatePasswordForm, Model model) {
        if( httpSession.getAttribute("user") == null)
            return "redirect:loginform";
        String msg = "noError";
        UserDocument userDocument = userStore.findByUsername((String)httpSession.getAttribute("user"));
        if(! userDocument.getPassword().equals(updatePasswordForm.getOldPassword())) {
            msg = "Please enter correct old password";
        }
        else if ( ! updatePasswordForm.getNewPassword().equals(updatePasswordForm.getConfirmNewPassword())) {
            msg = "Passwords do not match";
        }
        else {
            userDocument.setPassword(updatePasswordForm.getNewPassword());
            userStore.save(userDocument);
            msg = "Password Updated";
        }
        model.addAttribute("msg", msg);
        return "updatePassword";
    }
}
