package com.vamsi.TimeManagementService.dataaccess;


import com.vamsi.TimeManagementService.databasemodels.MeetingDocument;
import com.vamsi.TimeManagementService.mongorepositories.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("meetingStore")
public class MeetingStore {

    @Autowired
    MeetingRepository meetingRepository;

    public void insert(MeetingDocument meetingDocument) {
        meetingRepository.insert(meetingDocument);
    }

    public List<MeetingDocument> findAll() {
        return meetingRepository.findAll();
    }


    public MeetingDocument findByMeetingId(int meetingId) {
        return meetingRepository.findByMeetingId(meetingId);
    }

    public void save(MeetingDocument meetingDocument) {
        meetingRepository.save(meetingDocument);
    }

    public void delete(MeetingDocument meetingDocument) {
        meetingRepository.delete(meetingDocument);
    }

}
