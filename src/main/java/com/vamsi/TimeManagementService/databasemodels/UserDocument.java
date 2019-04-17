package com.vamsi.TimeManagementService.databasemodels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "users")
public class UserDocument {
    @Id
    private String id;
    private String employeeCode;
    private String employeeId;
    private String email;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private Long phoneNumber;


}
