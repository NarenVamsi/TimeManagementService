package com.vamsi.TimeManagementService.webcontrollers.webmodels;

import com.vamsi.TimeManagementService.databasemodels.MeetingDocument;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
public class LeaveForm {
    private Date date;
    private String description;

    public MeetingDocument toMeetingDocument(int meetingId) {
        MeetingDocument meetingDocument = new MeetingDocument();
        meetingDocument.setMeetingId(meetingId);
        meetingDocument.setDescription(this.getDescription());
        meetingDocument.setDate(this.getDate());
        meetingDocument.setLeave(true);
        meetingDocument.setStartTime(new Date(this.getDate().getTime() + 1000*60*60*9));
        meetingDocument.setEndTime(new Date(this.getDate().getTime() + 1000*60*60*17));
        return meetingDocument;
    }
}
