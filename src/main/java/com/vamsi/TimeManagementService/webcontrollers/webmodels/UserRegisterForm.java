package com.vamsi.TimeManagementService.webcontrollers.webmodels;

import com.vamsi.TimeManagementService.databasemodels.UserDocument;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRegisterForm {
    private String email;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private Long phoneNumber;
    private String confirmPassword;

    public UserDocument toUserDocument() {
        UserDocument userDocument = new UserDocument();
        userDocument.setUserName(this.getUserName());
        userDocument.setEmployeeId(this.getUserName());
        userDocument.setEmail(this.getEmail());
        userDocument.setFirstName(this.getFirstName());
        userDocument.setLastName(this.getLastName());
        userDocument.setPassword(this.getPassword());
        userDocument.setPhoneNumber(this.getPhoneNumber());
        userDocument.setEmployeeCode("EXECUTIVE");
        return userDocument;
    }
}