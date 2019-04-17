package com.vamsi.TimeManagementService.mongorepositories;

import com.vamsi.TimeManagementService.databasemodels.ConflictMeetingDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConflictMeetingsRepository extends MongoRepository<ConflictMeetingDocument, String> {

    @Query("{'meetingIdList':?0}")
    ConflictMeetingDocument findByMeetingId(int meetingId);
}
