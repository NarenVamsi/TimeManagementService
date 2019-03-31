package com.vamsi.TimeManagementService.databasemodels;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@Document(collection = "conflictMeetings")
public class ConflictMeetingDocument {
    @Id
    private String id;
    private int meetingId;
    private MeetingDocument meeting;
    private List<ExecutiveOpinionDocument> executiveOpinions;
    private int minimumDuration;
}
