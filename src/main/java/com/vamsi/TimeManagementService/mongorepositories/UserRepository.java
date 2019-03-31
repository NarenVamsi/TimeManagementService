package com.vamsi.TimeManagementService.mongorepositories;

import com.vamsi.TimeManagementService.databasemodels.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserDocument, String> {

    @Query("{'userName' : ?0}")
    UserDocument findByUserName(String userName);
}
