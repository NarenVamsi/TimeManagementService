package com.vamsi.TimeManagementService.mongorepositories;

import com.vamsi.TimeManagementService.databasemodels.ConflictResolvingRequestDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConflictResolvingRequestsRepository extends MongoRepository<ConflictResolvingRequestDocument, String> {

    @Query("{'meetingIdList':?0}")
    ConflictResolvingRequestDocument findByMeetingId(int meetingId);
}
