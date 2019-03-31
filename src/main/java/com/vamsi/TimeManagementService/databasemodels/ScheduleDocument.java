package com.vamsi.TimeManagementService.databasemodels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "schedules")
public class ScheduleDocument {
    @Id
    private String id;
    private String employeeId;
    private List<DailyScheduleDocument> dailyScheduleDocuments;
}
