package com.vamsi.softwareengineering.TimeManagementService.databasemodels;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ConflictMeetingDocument {
    private MeetingDocument meeting;
    private List<ExecutiveOpinionDocument> executiveOpinions;
    private int minimumDuration;
}
