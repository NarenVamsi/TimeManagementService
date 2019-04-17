package com.vamsi.TimeManagementService.webcontrollers.webmodels;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserProfile {
    private String firstName;
    private String lastName;
    private Long phoneNumber;
    private String email;
}
