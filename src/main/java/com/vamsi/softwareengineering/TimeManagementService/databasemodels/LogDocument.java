package com.vamsi.softwareengineering.TimeManagementService.databasemodels;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
public class LogDocument {
    private String documentChanger;
    private Operation operation;
    private List<String> executiveIdList;
    private Timestamp timestamp;
}
