package com.vamsi.TimeManagementService.webcontrollers;

import com.vamsi.TimeManagementService.dataaccess.MeetingStore;
import com.vamsi.TimeManagementService.dataaccess.ScheduleStore;
import com.vamsi.TimeManagementService.dataaccess.UserStore;
import com.vamsi.TimeManagementService.databasemodels.DailyScheduleDocument;
import com.vamsi.TimeManagementService.databasemodels.MeetingDocument;
import com.vamsi.TimeManagementService.databasemodels.ScheduleDocument;
import com.vamsi.TimeManagementService.databasemodels.UserDocument;
import com.vamsi.TimeManagementService.webcontrollers.webmodels.ViewStatisticsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class SecretaryController {
    @Autowired
    private UserStore userStore;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private ScheduleStore scheduleStore;

    @Autowired
    private MeetingStore meetingStore;

    @RequestMapping(value = "/secretaryGreeting",method = RequestMethod.GET)
    public String getExecutiveGreeting(Model model){
        String employeeCode = (String)httpSession.getAttribute("employeeCode");
        if(httpSession.getAttribute("user") != null && employeeCode.equals("SECRETARY")) {
            return "secretaryGreeting";
        }
        return "redirect:loginform";
    }

    @RequestMapping(value = "/viewStatistics" , method = RequestMethod.GET)
    public String viewStatistics(Model model) {
        String employeeCode = (String)httpSession.getAttribute("employeeCode");
        if(httpSession.getAttribute("user") == null || !employeeCode.equals("SECRETARY")) {
            return "redirect:loginform";
        }
        model.addAttribute("msg", "Select range of dates and executive");
        return "viewStatistics";
    }

    @RequestMapping(value = "/viewStatistics" , method = RequestMethod.POST)
    public String viewStatistics(@ModelAttribute("viewStatisticsForm") ViewStatisticsForm viewStatisticsForm, Model model) {
        String employeeCode = (String)httpSession.getAttribute("employeeCode");
        if(httpSession.getAttribute("user") == null || !employeeCode.equals("SECRETARY")) {
            return "redirect:loginform";
        }
        Date startDate = viewStatisticsForm.getStartDate();
        Date endDate = viewStatisticsForm.getEndDate();
        String executiveId = viewStatisticsForm.getExecutiveId();
        UserDocument userDocument = userStore.findByUsername(viewStatisticsForm.getExecutiveId());
        long meetingMinutes = 0;
        int leaves = 0;
        if(userDocument == null)
            model.addAttribute("msg", "Please select executive");
        else if (endDate.compareTo(startDate)<0) {
            model.addAttribute("msg", "Please select dates correctly");
        }
        else {
            ScheduleDocument scheduleDocument = scheduleStore.findByEmployeeId(viewStatisticsForm.getExecutiveId());
            List<DailyScheduleDocument> dailyScheduleDocuments = scheduleDocument.getDailyScheduleDocuments();
            for(DailyScheduleDocument dailyScheduleDocument : dailyScheduleDocuments) {
                if(dailyScheduleDocument.getDate().compareTo(startDate) >= 0 && dailyScheduleDocument.getDate().compareTo(endDate) <= 0) {
                    for(int meetingId : dailyScheduleDocument.getMeetingIdList()) {
                        MeetingDocument meetingDocument = meetingStore.findByMeetingId(meetingId);
                        if(meetingDocument.isLeave()) {
                            leaves = leaves + 1;
                        }
                        else {
                            Date startTime = meetingDocument.getStartTime();
                            Date endTime = meetingDocument.getEndTime();
                            long instants = endTime.getTime() - startTime.getTime();
                            meetingMinutes += instants/60000;
                        }
                    }
                }
            }
            model.addAttribute("minutes", meetingMinutes);
            model.addAttribute("leaves", leaves);
            model.addAttribute("msg", "noError");
            model.addAttribute("employee", executiveId);
        }
        return "viewStatistics";
    }
}
