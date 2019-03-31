package com.vamsi.TimeManagementService.mongorepositories;

import com.vamsi.TimeManagementService.databasemodels.MeetingDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends MongoRepository<MeetingDocument, String> {

    @Query("{'meetingId':?0}")
    MeetingDocument findByMeetingId(int meetingId);

}
