package com.vamsi.TimeManagementService.webcontrollers.webmodels;

import com.vamsi.TimeManagementService.databasemodels.MeetingDocument;
import com.vamsi.TimeManagementService.realentities.Meeting;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.sql.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class MeetingForm {
    private Date date;
    private int startHour;
    private int endHour;
    private int startMinute;
    private int endMinute;
    private int duration;
    private boolean isLeave;
    private String description;
    private List<String> executiveIdList;
    private int meetingId;

    public MeetingForm( MeetingDocument meetingDocument) {
        this.date = new Date(meetingDocument.getDate().getTime());
        this.startHour = meetingDocument.getStartTime().getHours();
        this.startMinute = meetingDocument.getStartTime().getMinutes();
        this.endHour = meetingDocument.getEndTime().getHours();
        this.endMinute = meetingDocument.getEndTime().getMinutes();
        this.description = meetingDocument.getDescription();
        this.isLeave = meetingDocument.isLeave();
        this.meetingId = meetingDocument.getMeetingId();
    }

    public MeetingDocument updateMeetingDocument(MeetingDocument meetingDocument) {
        meetingDocument.setStartTime(this.getStartTime());
        meetingDocument.setEndTime(this.getEndTime());
        meetingDocument.setDescription(this.getDescription());
        return meetingDocument;
    }

    public MeetingDocument toClientMeetingDocument(int meetingId, String userName) {
        MeetingDocument meetingDocument = new MeetingDocument();
        meetingDocument.setLeave(false);
        meetingDocument.setDate(this.date);
        meetingDocument.setMeetingInitiator(userName);
        meetingDocument.setStartTime(new Date(date.getTime() + (this.startHour*60 + this.startMinute)*60*1000));
        meetingDocument.setEndTime(new Date(date.getTime() + (this.endHour*60 + this.endMinute)*60*1000));
        meetingDocument.setMeetingId(meetingId);
        meetingDocument.setDescription(this.description);
        meetingDocument.setExecutiveIdList(executiveIdList);
        return meetingDocument;
    }

    public MeetingDocument toExecutiveMeetingDocument(int meetingId, String userName) {
        MeetingDocument meetingDocument = new MeetingDocument();
        meetingDocument.setLeave(false);
        meetingDocument.setDate(this.date);
        meetingDocument.setMeetingInitiator(userName);
        meetingDocument.setExecutiveIdList(executiveIdList);
        meetingDocument.setStartTime(new Date(date.getTime() + (this.startHour*60 + this.startMinute)*60*1000));
        meetingDocument.setEndTime(new Date(date.getTime() + (this.endHour*60 + this.endMinute)*60*1000));
        meetingDocument.setMeetingId(meetingId);
        return meetingDocument;
    }


    public Date getStartTime() {
        return new Date(date.getTime() + (this.startHour*60 + this.startMinute)*60*1000);
    }

    public Date getEndTime() {
        return new Date(date.getTime() + (this.endHour*60 + this.endMinute)*60*1000);
    }
}
