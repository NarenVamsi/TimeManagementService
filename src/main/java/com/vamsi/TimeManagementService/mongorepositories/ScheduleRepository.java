package com.vamsi.TimeManagementService.mongorepositories;

import com.vamsi.TimeManagementService.databasemodels.ScheduleDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends MongoRepository<ScheduleDocument, String> {

    @Query("{'employeeId':?0}")
    ScheduleDocument findByEmployeeId(String employeeId);
}
