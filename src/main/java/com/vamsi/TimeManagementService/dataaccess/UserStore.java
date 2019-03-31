package com.vamsi.TimeManagementService.dataaccess;

import com.vamsi.TimeManagementService.databasemodels.UserDocument;
import com.vamsi.TimeManagementService.mongorepositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userStore")
public class UserStore {

    @Autowired
    UserRepository userRepository;

    public void insert(UserDocument userDocument) {
        userRepository.insert(userDocument);
    }

    public UserDocument findByUsername(UserDocument userDocument) {
        return userRepository.findByUserName(userDocument.getUserName());
    }

    public void save(UserDocument userDocument) {
        userRepository.save(userDocument);
    }
}
