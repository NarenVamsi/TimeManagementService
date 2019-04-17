package com.vamsi.TimeManagementService.dataaccess;

import com.vamsi.TimeManagementService.databasemodels.UserDocument;
import com.vamsi.TimeManagementService.mongorepositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userStore")
public class UserStore {

    @Autowired
    UserRepository userRepository;

    public void insert(UserDocument userDocument) {
        userRepository.insert(userDocument);
    }

    public UserDocument findByUsername(String userName) {
        return userRepository.findByUserName(userName);
    }

    public List<UserDocument> findAll() {
        return userRepository.findAll();
    }

    public void save(UserDocument userDocument) {
        userRepository.save(userDocument);
    }
}
