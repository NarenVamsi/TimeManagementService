package com.vamsi.TimeManagementService.webcontrollers;

import com.vamsi.TimeManagementService.dataaccess.*;
import com.vamsi.TimeManagementService.databasemodels.ConflictResolvingRequestDocument;
import com.vamsi.TimeManagementService.databasemodels.DailyScheduleDocument;
import com.vamsi.TimeManagementService.databasemodels.MeetingDocument;
import com.vamsi.TimeManagementService.databasemodels.ScheduleDocument;
import com.vamsi.TimeManagementService.webcontrollers.webmodels.LeaveForm;
import com.vamsi.TimeManagementService.webcontrollers.webmodels.MeetingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MeetingController {

    @Autowired
    HttpSession httpSession;

    @Autowired
    private ScheduleStore scheduleStore;

    @Autowired
    private UserStore userStore;

    @Autowired
    private ConflictResolvingRequestStore conflictResolvingRequestStore;

    @Autowired
    private MeetingStore meetingStore;

    @RequestMapping(value = "/updateLeave" , method = RequestMethod.GET)
    public String updateLeave(Model model) {
        if(httpSession.getAttribute("user") == null) {
            return "loginform";
        }
        model.addAttribute("msg","noError");
        return "leaveForm";
    }


    @RequestMapping(value = "/updateLeave", method = RequestMethod.POST)
    public String UpdateLeave(@ModelAttribute("updateLeave") LeaveForm leaveForm, Model model) {
        if(httpSession.getAttribute("user") == null) {
            return "loginform";
        }
        String msg = "noError";
        ScheduleDocument scheduleDocument = scheduleStore.findByEmployeeId((String)httpSession.getAttribute("user"));
        if(!checkIfSlotIsFree(leaveForm.getDate(),new Date(leaveForm.getDate().getTime() + 1000*60*60*9), new Date(leaveForm.getDate().getTime() + 1000*60*60*17))) {
            msg = "You have other schedules on that date";
        }
        else {
            int meetingId = generateNewMeetingId();
            MeetingDocument meetingDocument = leaveForm.toMeetingDocument(meetingId);
            meetingStore.insert(meetingDocument);
            if(scheduleDocument == null) {
                scheduleDocument = new ScheduleDocument();
                scheduleDocument.setEmployeeId((String)httpSession.getAttribute("user"));
                scheduleDocument.setDailyScheduleDocuments(new ArrayList<>());
            }
            DailyScheduleDocument dailyScheduleDocument = null;
            for(DailyScheduleDocument dailySchedule : scheduleDocument.getDailyScheduleDocuments()) {
                if(dailySchedule.getDate().equals(leaveForm.getDate())) {
                    dailyScheduleDocument = dailySchedule;
                }
            }
            if(dailyScheduleDocument == null) {
                dailyScheduleDocument = new DailyScheduleDocument();
                dailyScheduleDocument.setDate(leaveForm.getDate());
                dailyScheduleDocument.setMeetingIdList(new ArrayList<>());
                scheduleDocument.getDailyScheduleDocuments().add(dailyScheduleDocument);
            }
            dailyScheduleDocument.getMeetingIdList().add(meetingId);
            if(scheduleDocument.getId() == null)
                scheduleStore.insert(scheduleDocument);
            else scheduleStore.save(scheduleDocument);
            msg = "Leave is updated";
        }
        model.addAttribute("msg",msg);
        return "leaveForm";
    }

    @RequestMapping(value = "/updateClientMeeting" , method = RequestMethod.GET)
    public String updateClientMeeting(Model model) {
        if(httpSession.getAttribute("user") == null) {
            return "redirect:loginform";
        }
        model.addAttribute("msg","noError");
        return "clientMeetingForm";
    }

    @RequestMapping(value = "/updateClientMeeting" , method = RequestMethod.POST)
    public String updateClientMeeting(@ModelAttribute("meetingForm") MeetingForm meetingForm, Model model) {
        if(httpSession.getAttribute("user") == null) {
            return "loginform";
        }
        int endHour = meetingForm.getEndHour();
        int startHour = meetingForm.getStartHour();
        int startMinute = meetingForm.getStartMinute();
        int endMinute = meetingForm.getEndMinute();
        String userName = (String)httpSession.getAttribute("user");
        String msg = "noError";
        ScheduleDocument scheduleDocument = scheduleStore.findByEmployeeId((String)httpSession.getAttribute("user"));
        if(!checkIfSlotIsFree(userName, meetingForm.getDate(),meetingForm.getStartTime(), meetingForm.getEndTime())) {
            msg = "You have other schedules on that date";
        }
        else if(startHour*60 + startMinute >= endHour*60 + endMinute) {
            msg = "please select timings correctly";
        }
        else {
            int meetingId = generateNewMeetingId();
            MeetingDocument meetingDocument = meetingForm.toClientMeetingDocument(meetingId,userName );
            meetingStore.insert(meetingDocument);
            if(scheduleDocument == null) {
                scheduleDocument = new ScheduleDocument();
                scheduleDocument.setEmployeeId((String)httpSession.getAttribute("user"));
                scheduleDocument.setDailyScheduleDocuments(new ArrayList<>());
            }
            DailyScheduleDocument dailyScheduleDocument = null;
            for(DailyScheduleDocument dailySchedule : scheduleDocument.getDailyScheduleDocuments()) {
                if(dailySchedule.getDate().equals(meetingForm.getDate())) {
                    dailyScheduleDocument = dailySchedule;
                }
            }
            if(dailyScheduleDocument == null) {
                dailyScheduleDocument = new DailyScheduleDocument();
                dailyScheduleDocument.setDate(meetingForm.getDate());
                dailyScheduleDocument.setMeetingIdList(new ArrayList<>());
                scheduleDocument.getDailyScheduleDocuments().add(dailyScheduleDocument);
            }
            dailyScheduleDocument.getMeetingIdList().add(meetingId);
            if(scheduleDocument.getId() == null)
                scheduleStore.insert(scheduleDocument);
            else scheduleStore.save(scheduleDocument);
            msg = "Meeting is updated";
        }
        model.addAttribute("msg",msg);
        return "clientMeetingForm";
    }

    @RequestMapping(value="/updateExecutiveMeeting", method = RequestMethod.GET)
    public String updateExecutiveMeeting(Model model) {
        if(httpSession.getAttribute("user") == null) {
            return "redirect:loginform";
        }
        model.addAttribute("msg","noError");
        return "executiveMeetingForm";
    }

    @RequestMapping(value = "/updateExecutiveMeeting" , method = RequestMethod.POST)
    public String updateExecutiveMeeting(@ModelAttribute("meetingForm") MeetingForm meetingForm, Model model) {
        if(httpSession.getAttribute("user") == null) {
            return "loginform";
        }
        int duration = meetingForm.getDuration();
        System.out.println("duration is " + duration);
        String userName = (String)httpSession.getAttribute("user");
        String msg = "noError";

        Integer startMinute = getStartMinute(meetingForm.getExecutiveIdList(), duration, meetingForm.getDate());
        if(startMinute == null) {
            msg = "Can't create a meeting with this duration because other executives have different schedules";
        }
        else {
            int meetingId = generateNewMeetingId();
            meetingForm.setStartHour(startMinute/60 + 9);
            meetingForm.setStartMinute(startMinute%60);
            meetingForm.setEndHour((startMinute + duration)/60 + 9);
            meetingForm.setEndHour((startMinute + duration)%60);
            MeetingDocument meetingDocument = meetingForm.toExecutiveMeetingDocument(meetingId, userName);
            meetingStore.insert(meetingDocument);
            for(String executive : meetingForm.getExecutiveIdList()) {
                ScheduleDocument scheduleDocument = scheduleStore.findByEmployeeId(executive);
                if (scheduleDocument == null) {
                    scheduleDocument = new ScheduleDocument();
                    scheduleDocument.setEmployeeId(executive);
                    scheduleDocument.setDailyScheduleDocuments(new ArrayList<>());
                }
                DailyScheduleDocument dailyScheduleDocument = null;
                for (DailyScheduleDocument dailySchedule : scheduleDocument.getDailyScheduleDocuments()) {
                    if (dailySchedule.getDate().equals(meetingForm.getDate())) {
                        dailyScheduleDocument = dailySchedule;
                    }
                }
                if (dailyScheduleDocument == null) {
                    dailyScheduleDocument = new DailyScheduleDocument();
                    dailyScheduleDocument.setDate(meetingForm.getDate());
                    dailyScheduleDocument.setMeetingIdList(new ArrayList<>());
                    scheduleDocument.getDailyScheduleDocuments().add(dailyScheduleDocument);
                }
                dailyScheduleDocument.getMeetingIdList().add(meetingId);
                if (scheduleDocument.getId() == null)
                    scheduleStore.insert(scheduleDocument);
                else scheduleStore.save(scheduleDocument);
            }
            msg = "Meeting is created";
        }
        model.addAttribute("msg",msg);
        return "executiveMeetingForm";
    }

    @RequestMapping(value = "/updateCurrentMeeting/{meetingId}", method = RequestMethod.GET)
    public String updateCurrentMeeting(@PathVariable("meetingId") Integer meetingId, Model model) {
        if(httpSession.getAttribute("user") == null)
            return "redirect:/loginform";
        model.addAttribute("msg", "Add New Dates");
        MeetingDocument meetingDocument = meetingStore.findByMeetingId(meetingId);
        if(meetingDocument != null) {
            model.addAttribute("meeting", new MeetingForm(meetingStore.findByMeetingId(meetingId)));
        }
        else {
            return "redirect:/viewMeetings";
        }
        return "updateMeetingForm";
    }

    @RequestMapping(value = "/updateCurrentMeeting", method = RequestMethod.POST)
    public String updateCurrentMeeting(MeetingForm meetingForm, Model model) {
        if(httpSession.getAttribute("user") == null)
            return "redirect:loginform";
        int meetingId = meetingForm.getMeetingId();
        int endHour = meetingForm.getEndHour();
        int startHour = meetingForm.getStartHour();
        int startMinute = meetingForm.getStartMinute();
        int endMinute = meetingForm.getEndMinute();
        String description = meetingForm.getDescription();
        String userName = (String)httpSession.getAttribute("user");
        String msg = "noError";
        if(!checkIfSlotIsFree(userName, meetingId, meetingForm.getDate(),meetingForm.getStartTime(), meetingForm.getEndTime())) {
            msg = "You have other schedules on that date";
        }
        else if(startHour*60 + startMinute >= endHour*60 + endMinute) {
            msg = "please select timings correctly";
        }
        else {
            MeetingDocument meetingDocument = meetingStore.findByMeetingId(meetingId);
            meetingDocument = meetingForm.updateMeetingDocument(meetingDocument);
            meetingStore.save(meetingDocument);
            msg = "Meeting is updated";

        }
        model.addAttribute("meeting",new MeetingForm(meetingStore.findByMeetingId(meetingId)));
        model.addAttribute("msg",msg);
        return "updateMeetingForm";
    }

    @RequestMapping(value = "/deleteMeeting/{meetingId}", method = RequestMethod.GET)
    public String deleteMeeting(@PathVariable("meetingId") int meetingId, Model model) {
        if (httpSession.getAttribute("user") == null)
            return "redirect:loginform";
        String userName = (String)(httpSession.getAttribute("user"));
        ScheduleDocument scheduleDocument = scheduleStore.findByEmployeeId(userName);
        MeetingDocument meetingDocument = meetingStore.findByMeetingId(meetingId);
        for(DailyScheduleDocument dailyScheduleDocument : scheduleDocument.getDailyScheduleDocuments()) {
            if(dailyScheduleDocument.getDate().compareTo(meetingDocument.getDate()) == 0) {
                List<Integer> meetingIdList = dailyScheduleDocument.getMeetingIdList();
                meetingIdList.remove(meetingIdList.indexOf(meetingId));
            }
        }
        scheduleStore.save(scheduleDocument);
        meetingStore.delete(meetingDocument);
        return "redirect:/viewMeetings";
    }

    private boolean checkIfSlotIsFree(String userName, int currentMeetingId, java.sql.Date date, java.sql.Date startTime, java.sql.Date endTime) {
        ScheduleDocument scheduleDocument = scheduleStore.findByEmployeeId(userName);
        if(scheduleDocument == null)
            return true;
        for (DailyScheduleDocument dailyScheduleDocument : scheduleDocument.getDailyScheduleDocuments()) {
            if(dailyScheduleDocument.getDate().equals(date)) {
                System.out.println("date is " + date );
                System.out.println("date is " + dailyScheduleDocument.getDate());
                List<Integer> meetingIds = dailyScheduleDocument.getMeetingIdList();
                for(int meetingId : meetingIds) {
                    if(meetingId != currentMeetingId) {
                        MeetingDocument meetingDocument = meetingStore.findByMeetingId(meetingId);
                        if (!compareDates(meetingDocument.getStartTime(), meetingDocument.getEndTime(), startTime, endTime))
                            return false;
                    }
                }
            }
        }
        return true;
    }

    private Integer getStartMinute(List<String> executiveIdList, int duration, Date date) {
        int minutes[] = new int[8*60];
        for(int i =0 ; i < 8*60 ; i++) {
            minutes[i] = 0;
        }
        for(String executive : executiveIdList) {
            ScheduleDocument scheduleDocument = scheduleStore.findByEmployeeId(executive);
            DailyScheduleDocument dailyScheduleDocument = null;
            for(DailyScheduleDocument dailySchedule : scheduleDocument.getDailyScheduleDocuments()) {
                if(dailySchedule.getDate().equals(date)) {
                    dailyScheduleDocument = dailySchedule;
                }
            }
            if(dailyScheduleDocument != null) {
                for(int meetingId : dailyScheduleDocument.getMeetingIdList()) {
                    MeetingDocument meetingDocument = meetingStore.findByMeetingId(meetingId);
                    int startHour = meetingDocument.getStartTime().getHours();
                    int endHour = meetingDocument.getEndTime().getHours();
                    int startMinute = meetingDocument.getStartTime().getMinutes();
                    int endMinute = meetingDocument.getStartTime().getMinutes();
                    minutes[(startHour - 9)*60 + startMinute] = 1;
                    minutes[(endHour - 9) * 60 + endMinute] = -1;
                }
            }
        }
        for(int i =1 ; i<60*8 ; i++) {
            minutes[i] = minutes[i-1] + minutes[i];
        }
        for(int i = 0 ; i < 60*8 ; i++) {
            int start = -1;
            if(minutes[i] == 0) {
                start = i;
                int end = i;
                for(;i<60*8;i++) {
                    if(minutes[i]!=0) {
                        end = i-1;
                        break;
                    }
                }
                if(end - start >= duration)
                    return start;
            }
            if(start != -1 && i == 60*8 && (60*8 - start) >= duration) {
                return start;
            }
        }
        return null;
    }

    @RequestMapping(value = "/viewMeetings", method = RequestMethod.GET)
    public String viewMeetings(Model model) {
        if(httpSession.getAttribute("user") == null){
            return "redirect:loginform";
        }
        model.addAttribute("msg","select date");
        return "viewMeetings";
    }

    @RequestMapping(value = "/viewMeetings", params = "date", method = RequestMethod.GET)
    public String viewMeetings (@RequestParam("date") String dateInString, Model model) {
        if(httpSession.getAttribute("user") == null){
            return "redirect:loginform";
        }

        System.out.println(dateInString);
        String userName = (String)httpSession.getAttribute("user");
        List<MeetingForm> meetingForms = new ArrayList<>();
        String msg = "select date";
        Date date = null;
        if(dateInString != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                date = formatter.parse(dateInString);
                System.out.println(date);
                System.out.println(formatter.format(date));

            } catch (ParseException e) {
                e.printStackTrace();
            }
            ScheduleDocument scheduleDocument = scheduleStore.findByEmployeeId(userName);
            DailyScheduleDocument userScheduleOnDate = null;
            for(DailyScheduleDocument dailyScheduleDocument : scheduleDocument.getDailyScheduleDocuments()) {
                if(dailyScheduleDocument.getDate().equals(date)) {
                    userScheduleOnDate = dailyScheduleDocument;
                }
            }
            System.out.println("hiiii");
            if(userScheduleOnDate == null || userScheduleOnDate.getMeetingIdList().isEmpty()) {
                msg = "No meetings on that date ";
            } else {
                for(int meetingId : userScheduleOnDate.getMeetingIdList()) {
                    meetingForms.add(new MeetingForm(meetingStore.findByMeetingId(meetingId)));
                }
                msg = "Meeting is present";
                model.addAttribute("meetings", meetingForms);
            }
        }

        model.addAttribute("msg",msg);
        return "viewMeetings";
    }

    private boolean checkIfSlotIsFree(String userName, java.sql.Date date, java.sql.Date startTime, java.sql.Date endTime) {
        ScheduleDocument scheduleDocument = scheduleStore.findByEmployeeId(userName);
        if(scheduleDocument == null)
            return true;
        for (DailyScheduleDocument dailyScheduleDocument : scheduleDocument.getDailyScheduleDocuments()) {
            if(dailyScheduleDocument.getDate().equals(date)) {
                System.out.println("date is " + date );
                System.out.println("date is " + dailyScheduleDocument.getDate());
                List<Integer> meetingIds = dailyScheduleDocument.getMeetingIdList();
                for(int meetingId : meetingIds) {
                    MeetingDocument meetingDocument = meetingStore.findByMeetingId(meetingId);
                    if(!compareDates(meetingDocument.getStartTime(),meetingDocument.getEndTime(),startTime,endTime))
                        return false;
                }
            }
        }
        return true;
    }

    private boolean compareDates(Date startTime, Date endTime, Date startTime1, Date endTime1) {
        if((startTime.compareTo(startTime1) >= 0 && startTime.compareTo(endTime1) <= 0)
        || (endTime.compareTo(startTime1) >= 0 && endTime.compareTo(endTime1) <= 0 )
        || (startTime1.compareTo(startTime) >= 0 && startTime1.compareTo(endTime) <= 0)
            || (endTime1.compareTo(startTime) >= 0 && endTime1.compareTo(startTime) <= 0)
        || (endTime1.compareTo(endTime) == 0 && startTime1.compareTo(endTime) == 0))
            return false;
        return true;
    }


    private int generateNewMeetingId() {
        List<MeetingDocument> meetingDocuments = meetingStore.findAll();
        List<Integer> meetingIds = new ArrayList<>();
        if(meetingDocuments != null) {
            for(MeetingDocument meetingDocument: meetingDocuments) {
                meetingIds.add(meetingDocument.getMeetingId());
            }
        }
        List<ConflictResolvingRequestDocument> conflictResolvingRequestDocuments  = conflictResolvingRequestStore.findAll();
        if(conflictResolvingRequestDocuments != null) {
            for(ConflictResolvingRequestDocument document : conflictResolvingRequestDocuments) {
                meetingIds.add(document.getMeetingId());
            }
        }

        Random random = new Random();
        while(true) {
            int randNumber = random.nextInt(10000);
            if(!meetingIds.contains(randNumber)) {
                return randNumber;
            }
        }
     }

    private boolean checkIfSlotIsFree(Date date, Date startTime, Date endTime) {
        String userName = (String)httpSession.getAttribute("user");
        ScheduleDocument scheduleDocument = scheduleStore.findByEmployeeId(userName);
        if(scheduleDocument == null)
            return true;
        for (DailyScheduleDocument dailyScheduleDocument : scheduleDocument.getDailyScheduleDocuments()) {
            if(dailyScheduleDocument.getDate().equals(date)) {
                return false;
            }
        }
        return true;
    }
}
