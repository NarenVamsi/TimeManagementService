package com.vamsi.TimeManagementService.dataaccess;


import com.vamsi.TimeManagementService.databasemodels.MeetingDocument;
import com.vamsi.TimeManagementService.mongorepositories.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("meetingStore")
public class MeetingStore {

    @Autowired
    MeetingRepository meetingRepository;

    public void insert(MeetingDocument meetingDocument) {
        meetingRepository.insert(meetingDocument);
    }

    public MeetingDocument findByMeetingId(MeetingDocument meetingDocument) {
        return meetingRepository.findByMeetingId(meetingDocument.getMeetingId());
    }

    public void delete(MeetingDocument meetingDocument) {
        meetingRepository.delete(findByMeetingId(meetingDocument));
    }

}
