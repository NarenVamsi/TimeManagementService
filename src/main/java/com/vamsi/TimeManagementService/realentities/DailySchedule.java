package com.vamsi.TimeManagementService.realentities;

import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
public class DailySchedule {
    private Date date;
    private List<Meeting> meetings;
}
