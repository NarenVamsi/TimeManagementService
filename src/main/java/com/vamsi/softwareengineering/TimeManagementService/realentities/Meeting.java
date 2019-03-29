package com.vamsi.softwareengineering.TimeManagementService.realentities;

import lombok.Builder;

import java.sql.Timestamp;
import java.util.List;


@Builder
public class Meeting {
    private int meetingId;
    private String meetingName;
    private Timestamp startTime;
    private Timestamp endTime;
    private String description;
    private List<String> executiveIdList;
    private List<String> clientList;
}
