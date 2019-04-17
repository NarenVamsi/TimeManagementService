package com.vamsi.TimeManagementService.dataaccess;

import com.vamsi.TimeManagementService.databasemodels.ConflictResolvingRequestDocument;
import com.vamsi.TimeManagementService.mongorepositories.ConflictResolvingRequestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("conflictResolvingRequestStore")
public class ConflictResolvingRequestStore {

    @Autowired
    ConflictResolvingRequestsRepository conflictResolvingRequestsRepository;

    public void insert(ConflictResolvingRequestDocument conflictResolvingRequestDocument) {
        conflictResolvingRequestsRepository.insert(conflictResolvingRequestDocument);
    }

    public void save(ConflictResolvingRequestDocument conflictResolvingRequestDocument) {
        conflictResolvingRequestsRepository.save(conflictResolvingRequestDocument);
    }

    public ConflictResolvingRequestDocument findByMeetingId(int meetingId){
        return conflictResolvingRequestsRepository.findByMeetingId(meetingId);
    }

    public List<ConflictResolvingRequestDocument> findAll() {
        return conflictResolvingRequestsRepository.findAll();
    }

}
