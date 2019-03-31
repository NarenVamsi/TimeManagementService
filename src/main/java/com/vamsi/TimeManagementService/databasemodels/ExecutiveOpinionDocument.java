package com.vamsi.TimeManagementService.databasemodels;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExecutiveOpinionDocument {
    private String executiveId;
    private Boolean opinion;
    private String reason;
}
