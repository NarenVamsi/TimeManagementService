package com.vamsi.TimeManagementService.databasemodels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Document(collection = "meetings")
public class MeetingDocument {
    @Id
    private String id;
    private int meetingId;
    private String meetingInitiator;
    private String meetingName;
    private Timestamp startTime;
    private Timestamp endTime;
    private String description;
    private List<String> executiveIdList;
    private List<String> clientList;
    private List<LogDocument> logDocuments;
}
