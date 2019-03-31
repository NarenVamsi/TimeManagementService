package com.vamsi.TimeManagementService.dataaccess;

import com.vamsi.TimeManagementService.databasemodels.ConflictMeetingDocument;
import com.vamsi.TimeManagementService.mongorepositories.ConflictMeetingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("conflictMeetingStore")
public class ConflictMeetingStore {

    @Autowired
    ConflictMeetingsRepository conflictMeetingsRepository;

    public void insert(ConflictMeetingDocument conflictMeetingDocument) {
        conflictMeetingsRepository.insert(conflictMeetingDocument);
    }

    public void save(ConflictMeetingDocument conflictMeetingDocument) {
        conflictMeetingsRepository.save(conflictMeetingDocument);
    }

    public ConflictMeetingDocument findByMeetingId(int meetingId) {
        return conflictMeetingsRepository.findByMeetingId(meetingId);
    }
}
