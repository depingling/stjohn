package com.cleanwise.service.api.process.operations;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.Report;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.Process;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import java.util.Iterator;
import java.util.Date;
import java.text.SimpleDateFormat;


public class ReportSchedule {

	public static final String DEFAULT_USER_NAME_TO_REPORT = "EventBean";

	private static final Logger log = Logger.getLogger(ReportSchedule.class);

    public void startAndSendReport(Integer reportScheduleId) throws Exception {

        log.info(
            "[ReportSchedule.startAndSendReport]  Schedule report. \r\n" +
            "Parameter: \r\n" +
            "    reportScheduleId = " + reportScheduleId);

        if (reportScheduleId == null) {
            throw new Exception("[ReportSchedule.startAndSendReport] The parameter 'reportScheduleId' is not defined.");
        }

        APIAccess factory = new APIAccess();
        Report reportEjb = factory.getReportAPI();



        // get description of the report schedule by ReportScheduleId
        ReportScheduleJoinView report = reportEjb.getReportSchedule(reportScheduleId);

        log.info("Found report to run Category: "+report.getReport().getCategory()+" Name: "+report.getReport().getName());
        // get report owner
        String userName = DEFAULT_USER_NAME_TO_REPORT;
        int userId = 0;
        ReportSchedUserShareViewVector users = report.getUsers();
        Iterator i = users.iterator();
        while (i.hasNext()) {
            ReportSchedUserShareView userView = (ReportSchedUserShareView)i.next();
            if (userView.getReportOwnerFl()) {
              userName = userView.getUserLoginName();
              userId = userView.getUserId();
              log.info("Using userId: "+userId);
              break;
            }
        }

        ReportScheduleDetailDataVector details =  report.getScheduleDetails();
        boolean suppressEmailFlag = false;
        i = details.iterator();
        while (i.hasNext()) {
            ReportScheduleDetailData detail = (ReportScheduleDetailData)i.next();
            String detailCd = detail.getReportScheduleDetailCd();
            if (RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.EMAIL_FLAG.equals(detailCd)){
              String value = detail.getDetailValue();
              suppressEmailFlag = (value != null && value.equals("Yes")) ? true :false;
              break;
            }
        }

        // start report
        ReportResultData reportResultD = reportEjb.processScheduledReport(reportScheduleId, userId, userName);


        // users and groups
        IdVector userIdV = new IdVector();
        i = users.iterator();
        while (i.hasNext()) {
            ReportSchedUserShareView userView = (ReportSchedUserShareView)i.next();
            userIdV.add(new Integer(userView.getUserId()));
        }

        IdVector groupIdV = new IdVector();
        ReportSchedGroupShareViewVector groups = report.getGroups();
        i = groups.iterator();
        while (i.hasNext()) {
            ReportSchedGroupShareView groupView = (ReportSchedGroupShareView)i.next();
            groupIdV.add(new Integer(groupView.getGroupId()));
        }


        // send report notification
        int sentQty = reportEjb.sendReportResult(reportResultD, groupIdV, userIdV, suppressEmailFlag);
        log.info("Sent "+sentQty+" emails.");
    }

    public void createSchReportEvents(String pDate, Integer parentEventId) throws Exception {

        log.info(
            "[ReportSchedule.createScheduleReportEvents]  for Date. \r\n" +pDate);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date schDate = null;
        if (pDate == null || "sysdate".equalsIgnoreCase(pDate) || "today".equalsIgnoreCase(pDate)) {
			schDate = sdf.parse(sdf.format(new Date()));
        } else {
			schDate = sdf.parse(pDate);
		}

        APIAccess factory = new APIAccess();
        Report reportEjb = factory.getReportAPI();
		Process processEjb = factory.getProcessAPI();
		Event eventEjb = factory.getEventAPI();

        IdVector schRepIds = reportEjb.getScheduledReportIds(schDate);
        int processId = processEjb.getActiveTemplateProcessId(RefCodeNames.PROCESS_NAMES.REPORT_SCHEDULE);
        int eventId = parentEventId == null ? 0 : parentEventId.intValue();

		for(Iterator iter=schRepIds.iterator(); iter.hasNext(); ) {
			Integer schIdI = (Integer) iter.next();
            EventData eventData = EventData.createValue();
            eventData.setStatus(Event.STATUS_READY);
            eventData.setType(Event.TYPE_PROCESS);
            eventData.setAttempt(1);
            EventProcessView epv =
						new EventProcessView(eventData, new EventPropertyDataVector(), eventId);
            epv.getProperties().add(Utility.createEventPropertyData("process_id",Event.PROPERTY_PROCESS_TEMPLATE_ID,new Integer(processId),1));
            //epv.getProperties().add(Utility.createEventPropertyData("className",Event.PROCESS_VARIABLE,this.getClass().getName(),1));
            //epv.getProperties().add(Utility.createEventPropertyData("processName",Event.PROCESS_VARIABLE,RefCodeNames.PROCESS_NAMES.REPORT_SCHEDULE,2));
            epv.getProperties().add(Utility.createEventPropertyData("reportScheduleId",Event.PROCESS_VARIABLE,schIdI,3));
            EventProcessView event = eventEjb.addEventProcess(epv, "REPORT_SCH_EVENT_GENERATOR");
			log.info("Inserted request to run report, event: "+event.getEventData().getEventId()+" for schedule id: "+schIdI);
		}
    }


}
