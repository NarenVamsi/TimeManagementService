package com.vamsi.TimeManagementService.webcontrollers.webmodels;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
public class ViewStatisticsForm {
    private Date startDate;
    private Date endDate;
    private String executiveId;

}
