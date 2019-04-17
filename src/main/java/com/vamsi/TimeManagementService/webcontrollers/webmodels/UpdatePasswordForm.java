package com.vamsi.TimeManagementService.webcontrollers.webmodels;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdatePasswordForm {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
