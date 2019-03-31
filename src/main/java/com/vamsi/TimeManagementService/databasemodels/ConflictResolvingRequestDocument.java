package com.vamsi.TimeManagementService.databasemodels;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Setter
@Getter
@Document(collection = "conflictResolvingRequests")
public class ConflictResolvingRequestDocument {
    @Id
    private String id;
    private int meetingId;
    private String meetingInitiator;
    private ConflictMeetingDocument conflictMeetingDocument;
    private Boolean status;
    private Timestamp startTime;
    private Timestamp endTime;
    private String description;
}
