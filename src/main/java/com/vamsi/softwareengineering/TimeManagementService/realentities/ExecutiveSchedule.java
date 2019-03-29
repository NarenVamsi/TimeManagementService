package com.vamsi.softwareengineering.TimeManagementService.realentities;

import lombok.Builder;

import java.util.List;

@Builder
public class ExecutiveSchedule {
    private List<DailySchedule> dailySchedules;
}
