package com.vamsi.TimeManagementService.databasemodels;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class DailyScheduleDocument {
    private Date date;
    private List<Integer> meetingId;
}
