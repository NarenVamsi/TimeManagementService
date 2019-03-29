package com.vamsi.softwareengineering.TimeManagementService.databasemodels;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class ConflictResolvingRequestDocument {
    private String meetingInitiator;
    private ConflictMeetingDocument conflictMeetingDocument;
    private Boolean status;
    private Timestamp startTime;
    private Timestamp endTime;
    private String description;
}
