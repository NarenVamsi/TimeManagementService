package com.vamsi.TimeManagementService.dataaccess;

import com.vamsi.TimeManagementService.mongorepositories.ScheduleRepository;
import com.vamsi.TimeManagementService.databasemodels.ScheduleDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("scheduleStore")
public class ScheduleStore {

    @Autowired
    ScheduleRepository scheduleRepository;

    public void insert(ScheduleDocument scheduleDocument){
        scheduleRepository.insert(scheduleDocument);
    }

    public void save(ScheduleDocument scheduleDocument) {
        scheduleRepository.save(scheduleDocument);
    }

    public ScheduleDocument findByEmployeeId(String employeeId) {
        return scheduleRepository.findByEmployeeId(employeeId);
    }
}
