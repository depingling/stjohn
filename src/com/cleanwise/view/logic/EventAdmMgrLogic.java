package com.cleanwise.view.logic;

import java.beans.*;
import java.io.*;
import java.rmi.*;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.naming.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.struts.upload.*;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.dao.blob.storage.util.BlobStorageAccess;
import com.cleanwise.service.api.process.variables.ExtProcessVariable;
import com.cleanwise.service.api.eventsys.*;
import com.cleanwise.service.api.eventsys.EventData;
import com.cleanwise.service.api.eventsys.EventDataVector;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.session.Process;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.utils.ObjectUtil;
import com.cleanwise.view.utils.validators.EmailValidator;

public class EventAdmMgrLogic {
    private static final String className = "EventAdmMgrLogic";
    static private final int TIMEOUT = 600;
    private final static String DATE_FORMAT = "MM/dd/yyyy";
    private final static String TIME_FORMAT = "HH:mm";
    private final static int MAX_LENGTH_OF_STRING = 4000;
    private final static SimpleDateFormat sdFormat = new SimpleDateFormat(DATE_FORMAT);
    private final static SimpleDateFormat stFormat = new SimpleDateFormat(TIME_FORMAT);

    public static ActionErrors init(HttpServletResponse response, HttpServletRequest request, ActionForm form)
            throws Exception {

        ActionErrors errors = new ActionErrors();
        HttpSession session = request.getSession();
        
        EventAdmMgrForm eventForm = (EventAdmMgrForm) form;
        if (!eventForm.isInit()) {
            APIAccess factory = new APIAccess();
            Event eventEjb = factory.getEventAPI();

            eventForm.setEventCount(eventEjb.getEventCount(null, null, true));

            Process process = factory.getProcessAPI();
            eventForm.setTemplateProcesses(process.getAllTemplateProcesses());

            List<String> eventStatusCds = new ArrayList<String>();
            eventStatusCds.add(Event.STATUS_FAILED);
            eventStatusCds.add(Event.STATUS_HOLD);
            eventStatusCds.add(Event.STATUS_IGNORE);
            eventStatusCds.add(Event.STATUS_IN_PROGRESS);
            eventStatusCds.add(Event.STATUS_LIMITED);
            eventStatusCds.add(Event.STATUS_PROC_ERROR);
            eventStatusCds.add(Event.STATUS_PROCESSED);
            eventStatusCds.add(Event.STATUS_READY);
            eventStatusCds.add(Event.STATUS_REJECTED);
            eventStatusCds.add(Event.STATUS_DELETED);

            SelectableObjects selectedObjects = new SelectableObjects(eventStatusCds,
                                                                      eventStatusCds,
                                                                      new Comparator() {
                                                                        public int compare(Object o1, Object o2) {
                                                                            return ((String) o1).compareTo((String) o2);
                                                                        }
                                                                      });
            eventForm.setSearchEventStatuses(selectedObjects);
            eventForm.setTaskProperties(new TreeMap(process.getAllTaskProperties()));
            
            eventForm.setInit(true);
            session.setAttribute("EVENT_ADM_CONFIG_FORM", eventForm);
        }

        return errors;
    }

    public static ActionErrors search(HttpServletResponse response, HttpServletRequest request, ActionForm form)
            throws Exception {

        ActionErrors errors = new ActionErrors();
        EventAdmMgrForm eventForm = (EventAdmMgrForm) form;
        APIAccess factory = new APIAccess();
        Event eventEjb = factory.getEventAPI();
        EventDataVector eventDataVector;
        HttpSession session = request.getSession();
        Process process = factory.getProcessAPI();
        eventForm.setTemplateProcesses(process.getAllTemplateProcesses());
        eventForm.setTaskProperties(new TreeMap(process.getAllTaskProperties()));
        int eventId = eventForm.getSearchEventNumber();
//        eventForm.setSearchEvents(new EventDataVector());
        try{
            if(false) {//eventId!=0){
                EventData eventData = eventEjb.getEvent(eventId);
                eventDataVector = new EventDataVector();

                if(eventData!=null){
                    eventForm.setSearchCount(1);
                    eventDataVector.add(eventData);
                }else{
                    eventForm.setSearchCount(0);
                }

            }else{

                String type = request.getParameter("type");
                if("eventEmailFailed".equals(type)){
                    eventForm.setSearchEventStatus(Event.STATUS_FAILED);
                    eventForm.setSearchEventType(Event.TYPE_EMAIL);
                    eventForm.setSearchEventIsActive(true);

                    eventDataVector = eventEjb.getEventV(
                            Event.STATUS_FAILED,
                            Event.TYPE_EMAIL,
                            true
                    );

                }else if("eventEmailInProgress".equals(type)){
                    eventForm.setSearchEventStatus(Event.STATUS_IN_PROGRESS);
                    eventForm.setSearchEventType(Event.TYPE_EMAIL);
                    eventForm.setSearchEventIsActive(true);

                    eventDataVector = eventEjb.getEventV(
                            Event.STATUS_IN_PROGRESS,
                            Event.TYPE_EMAIL,
                            true
                    );

                }else if("eventEmailProcessed".equals(type)){
                    eventForm.setSearchEventStatus(Event.STATUS_PROCESSED);
                    eventForm.setSearchEventType(Event.TYPE_EMAIL);
                    eventForm.setSearchEventIsActive(true);

                    eventDataVector = eventEjb.getEventV(
                            Event.STATUS_PROCESSED,
                            Event.TYPE_EMAIL,
                            true
                    );

                }else if("eventEmailReady".equals(type)){
                    eventForm.setSearchEventStatus(Event.STATUS_READY);
                    eventForm.setSearchEventType(Event.TYPE_EMAIL);
                    eventForm.setSearchEventIsActive(true);

                    eventDataVector = eventEjb.getEventV(
                            Event.STATUS_READY,
                            Event.TYPE_EMAIL,
                            true
                    );

                }else if("eventEmailRejected".equals(type)){
                    eventForm.setSearchEventStatus(Event.STATUS_REJECTED);
                    eventForm.setSearchEventType(Event.TYPE_EMAIL);
                    eventForm.setSearchEventIsActive(true);

                    eventDataVector = eventEjb.getEventV(
                            Event.STATUS_REJECTED,
                            Event.TYPE_EMAIL,
                            true
                    );

                }else if("eventEmailUnActive".equals(type)){
                    eventForm.setSearchEventStatus("");
                    eventForm.setSearchEventType(Event.TYPE_EMAIL);
                    eventForm.setSearchEventIsActive(false);

                    eventDataVector = eventEjb.getEventV(
                            "",
                            Event.TYPE_EMAIL,
                            false
                    );

                }else if("eventProcessFailed".equals(type)){
                    eventForm.setSearchEventStatus(Event.STATUS_FAILED);
                    eventForm.setSearchEventType(Event.TYPE_PROCESS);
                    eventForm.setSearchEventIsActive(true);

                    eventDataVector = eventEjb.getEventV(
                            Event.STATUS_FAILED,
                            Event.TYPE_PROCESS,
                            true
                    );

                }else if("eventProcessInProgress".equals(type)){
                    eventForm.setSearchEventStatus(Event.STATUS_IN_PROGRESS);
                    eventForm.setSearchEventType(Event.TYPE_PROCESS);
                    eventForm.setSearchEventIsActive(true);

                    eventDataVector = eventEjb.getEventV(
                            Event.STATUS_IN_PROGRESS,
                            Event.TYPE_PROCESS,
                            true
                    );

                }else if("eventProcessProcessed".equals(type)){
                    eventForm.setSearchEventStatus(Event.STATUS_PROCESSED);
                    eventForm.setSearchEventType(Event.TYPE_PROCESS);
                    eventForm.setSearchEventIsActive(true);

                    eventDataVector = eventEjb.getEventV(
                            Event.STATUS_PROCESSED,
                            Event.TYPE_PROCESS,
                            true
                    );

                }else if("eventProcessReady".equals(type)){
                    eventForm.setSearchEventStatus(Event.STATUS_READY);
                    eventForm.setSearchEventType(Event.TYPE_PROCESS);
                    eventForm.setSearchEventIsActive(true);

                    eventDataVector = eventEjb.getEventV(
                            Event.STATUS_READY,
                            Event.TYPE_PROCESS,
                            true
                    );

                }else if("eventProcessRejected".equals(type)){
                    eventForm.setSearchEventStatus(Event.STATUS_REJECTED);
                    eventForm.setSearchEventType(Event.TYPE_PROCESS);
                    eventForm.setSearchEventIsActive(true);

                    eventDataVector = eventEjb.getEventV(
                            Event.STATUS_REJECTED,
                            Event.TYPE_PROCESS,
                            true
                    );

                }else if("eventProcessUnActive".equals(type)){
                    eventForm.setSearchEventStatus("");
                    eventForm.setSearchEventType(Event.TYPE_PROCESS);
                    eventForm.setSearchEventIsActive(false);

                    eventDataVector = eventEjb.getEventV(
                            "",
                            Event.TYPE_PROCESS,
                            false
                    );

                } else if ("search".equals(type)){
                      checkParams(request, errors, eventForm);
                      if (errors.size() > 0) {
                        return errors;
                      }
                      String datePattern = I18nUtil.getDatePattern(ClwI18nUtil.getUserLocale(request));
                      Date dateFrom = Utility.parseDate(eventForm.getSearchDateFrom(), datePattern, false);
                      Date dateTo = Utility.parseDate(eventForm.getSearchDateTo(), datePattern, false);
                      Date timeFrom = null;
                      if (Utility.isSet(eventForm.getSearchTimeFrom())) {
                          timeFrom = Utility.parseDate(eventForm.getSearchTimeFrom(), TIME_FORMAT, false);
                      }
                      Date timeTo = null;
                      if (Utility.isSet(eventForm.getSearchTimeTo())) {
                          timeTo = Utility.parseDate(eventForm.getSearchTimeTo(), TIME_FORMAT, false);
                      }
/*
                      IdVector ids = null;
                      if (eventForm.getSearchFullText() != null && !"".equals(eventForm.getSearchFullText())) {
                        // fulltext search ids
// uncomment this block
                        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                        String sDateFrom = dateFrom!=null?sdf.format(dateFrom):"2000-01-01";
                        String sDateTo = dateTo!=null?sdf.format(dateTo):"2050-12-12";
                        CompassDetachedHits hits = CompassSearchHelper.search(eventForm.getSearchFullText(),
                                                                              CompassSearchHelper.EVENT_INDEX,
                                                                              sDateFrom,
                                                                              sDateTo);
                        ids = new IdVector();
                        HashMap map = new HashMap();
                        if (hits.getLength() != 0) {
                          for (int i = 0; i < hits.getLength(); i++) {
                            Integer id = new Integer(hits.resource(i).getValue("EVENT_ID"));
                            if (map.get(id) == null) {
                              ids.add(id);
                              map.put(id, id);
                            }
                          }
                          if (ids.size() > 1000) {
                              eventForm.setSearchCount(hits.getLength());
                              errors.add("Checking parameters", new ActionMessage("error.simpleGenericError", "Results size too large"));
                              return errors;
                          }
                        } else {
                          eventForm.setSearchCount(0);
                          eventForm.setSearchEvents(new EventDataVector());
                          return errors;
                        }
// end
                      }
*/
                      String filter = null;
                      String filterValue1 = null;
                      String filterValue2 = null;
                      if ("integer".equals(eventForm.getAttrType())) {
                        filter = eventForm.getAttrFilterInteger();
                        filterValue1 = eventForm.getAttrFilterIntegerValue1();
                        filterValue2 = eventForm.getAttrFilterIntegerValue2();
                      }
                      else if ("string".equals(eventForm.getAttrType())) {
                        filter = eventForm.getAttrFilterString();
                        filterValue1 = eventForm.getAttrFilterStringValue1();
                        filterValue2 = eventForm.getAttrFilterStringValue2();
                      }
                      else if ("date".equals(eventForm.getAttrType())) {
                        filter = eventForm.getAttrFilterDate();
                        filterValue1 = eventForm.getAttrFilterDateValue1();
                        filterValue2 = eventForm.getAttrFilterDateValue2();
                      }
                      
                      List<String> eventStatuses = new ArrayList<String>();
                      eventStatuses = eventForm.getSearchEventStatuses().getCurrentlySelected();
                      eventDataVector = eventEjb.getEventV(eventForm.getSearchEventNumber(),
                                                           eventForm.getSearchActive(),
                                                           eventForm.getSearchEventType(),
                                                           eventStatuses,
                                                           dateFrom, dateTo,
                                                           timeFrom, timeTo,
                                                           eventForm.getSearchParamType(),
                                                           eventForm.getAttrType(),
                                                           filter,
                                                           filterValue1,
                                                           filterValue2,
                                                           null, //eventForm.getSearchContainsText(),
                                                           null, //ids,
                                                           eventForm.getSearchBlobText(),
                                                           eventForm.getSearchBlobTextMaxRows());
                      eventForm.setSearchCount(eventDataVector.size());
                      eventForm.setSearchEvents(eventDataVector);
//                    }
//                } else {
//                    eventForm.setSearchCount(0);
//                    eventForm.setSearchEvents(null);
                }
            }
        }catch(RemoteException e){
            errors.add("Error while searching", new ActionMessage("error.simpleGenericError", e.getMessage()));
        }
        session.setAttribute("EVENT_ADM_CONFIG_FORM", eventForm);
        return errors;
    }

    private final static void checkParams(HttpServletRequest request,ActionErrors errors, EventAdmMgrForm eventForm) {
        if(!Utility.isSet(eventForm.getSearchDateFrom()) && eventForm.getSearchEventNumber() == 0){
            errors.add("Checking parameters", new ActionMessage("variable.empty.error", "From Date"));
        }
        if(Utility.isSet(eventForm.getSearchTimeFrom()) && !Utility.isSet(eventForm.getSearchDateFrom())){
            errors.add("Checking parameters", new ActionMessage("variable.requires.field.error", new Object[]{"From Time", "From Date"}));
        }
        if(Utility.isSet(eventForm.getSearchTimeTo()) && !Utility.isSet(eventForm.getSearchDateTo())){
            errors.add("Checking parameters", new ActionMessage("variable.requires.field.error", new Object[]{"To Time", "To Date"}));
        }
        checkDate(request, errors, eventForm.getSearchDateFrom(), "From Date");
        checkDate(request,errors, eventForm.getSearchDateTo(), "To Date");
        String time = "";
        if (Utility.isSet(eventForm.getSearchTimeFrom())) {
            time = eventForm.getSearchTimeFrom().trim().replaceAll("[\\s-:]+", ":");
            if (checkTime(request,errors, time, "From Time")) {
                eventForm.setSearchTimeFrom(time);
            }
        }
        if (Utility.isSet(eventForm.getSearchTimeTo())) {
            time = eventForm.getSearchTimeTo().trim().replaceAll("[\\s-:]+", ":");
            if (checkTime(request,errors, time, "To Time")) {
                eventForm.setSearchTimeTo(time);
            }
        }
        if(Utility.isSet(eventForm.getSearchContainsText())){
                if(!Utility.isSet(eventForm.getSearchDateFrom())){
                        errors.add("Checking parameters", new ActionMessage("error.simpleGenericError", "Date ranage too broad for use with contains text search"));
                }else{
                        try{
                        	    String datePattern = I18nUtil.getDatePattern(ClwI18nUtil.getUserLocale(request));

//                                Date val1 = Utility.parseDate(eventForm.getSearchDateFrom(), DATE_FORMAT, false);
                                Date val1 = Utility.parseDate(eventForm.getSearchDateFrom(), datePattern, false);
                                Date val2;
                                if(Utility.isSet(eventForm.getSearchDateTo())){
                                        val2 = Utility.parseDate(eventForm.getSearchDateTo(), datePattern, false);
                                }else{
                                        val2 = new Date();
                                }
                                Date newDate = Utility.addDays(val1,5); //5 days max search range
                                if(newDate.before(val2)){
                                        errors.add("Checking parameters", new ActionMessage("error.simpleGenericError", "Date ranage too broad for use with contains text search"));
                                }
                        }catch(Exception e){}//delt with below
                }
        }
        if ("date".equals(eventForm.getAttrType())) {
            checkDate(request,errors, eventForm.getAttrFilterDateValue1(), "Attribute Date From");
            checkDate(request,errors, eventForm.getAttrFilterDateValue2(), "Attribute Date To");
        } else if ("integer".equals(eventForm.getAttrType())) {
            if (Utility.isSet(eventForm.getAttrFilterIntegerValue1())) {
                checkInteger(errors, eventForm.getAttrFilterIntegerValue1(), "Attribute '"
                        + eventForm.getAttrFilterIntegerValue1() + "'");
            }
        }
    }
    private static void checkInteger(ActionErrors errors, String intValue, String intParamName) {
        try {
            Integer.parseInt(intValue);
        } catch (Exception e){
            errors.add("Checking parameters", new ActionMessage("error.invalidNumber", intParamName));
        }
    }
    private static void checkDate(HttpServletRequest pRequest, ActionErrors errors, String dateValue, String dateParamName) {
	    if(!Utility.isSet(dateValue)){
			return;
		}
        Date val = null;
        try {
        	String datePattern = I18nUtil.getDatePattern(ClwI18nUtil.getUserLocale(pRequest));
            val = Utility.parseDate(dateValue, datePattern, true);
        } catch (Exception e) {
			errors.add("Checking parameters", new ActionMessage("error.badDateFormat", dateParamName));
		}
    }
    private static boolean checkTime(HttpServletRequest pRequest, ActionErrors errors, String timeValue, String timeParamName) {
        boolean result = true;
        if (Utility.isSet(timeValue)) {   
            try {
                Utility.parseDate(timeValue, TIME_FORMAT, true);
            } catch (Exception e) {
                errors.add("Checking parameters", new ActionMessage("error.badTimeFormat", timeParamName));
                result = false;
            }
        }
        return result;
    }
    public static ActionErrors initStatistic(HttpServletResponse response, HttpServletRequest request, ActionForm form)
            throws Exception {

        ActionErrors errors = new ActionErrors();
        EventAdmMgrForm eventForm = (EventAdmMgrForm) form;
        APIAccess factory = new APIAccess();
        Event eventEjb = factory.getEventAPI();
        HttpSession session = request.getSession();


        eventForm.setEventEmailCountFailed(eventEjb.getEventCount(Event.STATUS_FAILED, Event.TYPE_EMAIL, true));
        eventForm.setEventEmailCountInProgress(eventEjb.getEventCount(Event.STATUS_IN_PROGRESS, Event.TYPE_EMAIL, true));
        eventForm.setEventEmailCountProcessed(eventEjb.getEventCount(Event.STATUS_PROCESSED, Event.TYPE_EMAIL, true));
        eventForm.setEventEmailCountReady(eventEjb.getEventCount(Event.STATUS_READY, Event.TYPE_EMAIL, true));
        eventForm.setEventEmailCountRejected(eventEjb.getEventCount(Event.STATUS_REJECTED, Event.TYPE_EMAIL, true));

        eventForm.setEventEmailCountUnActive(eventEjb.getEventCount(null, Event.TYPE_EMAIL, false));

        eventForm.setEventProcessCountFailed(eventEjb.getEventCount(Event.STATUS_FAILED, Event.TYPE_PROCESS, true));
        eventForm.setEventProcessCountInProgress(eventEjb.getEventCount(Event.STATUS_IN_PROGRESS, Event.TYPE_PROCESS, true));
        eventForm.setEventProcessCountProcessed(eventEjb.getEventCount(Event.STATUS_PROCESSED, Event.TYPE_PROCESS, true));
        eventForm.setEventProcessCountReady(eventEjb.getEventCount(Event.STATUS_READY, Event.TYPE_PROCESS, true));
        eventForm.setEventProcessCountRejected(eventEjb.getEventCount(Event.STATUS_REJECTED, Event.TYPE_PROCESS, true));

        eventForm.setEventProcessCountUnActive(eventEjb.getEventCount(null, Event.TYPE_PROCESS, false));

        session.setAttribute("EVENT_ADM_CONFIG_FORM", eventForm);

        return errors;
    }

    public static ActionErrors edit(HttpServletResponse response, HttpServletRequest request, ActionForm form)
            throws Exception {

        ActionErrors errors = new ActionErrors();
        EventAdmMgrForm eventForm = (EventAdmMgrForm) form;
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        int userId = appUser.getUser().getUserId();
        APIAccess factory = new APIAccess();
        Event eventEjb = factory.getEventAPI();
        Process process = factory.getProcessAPI();
        String func = request.getParameter("func");
        int eventId = Utility.parseInt(request.getParameter("eventid"));

        try{
            if("editinit".equals(func)){
                initDetail(request, eventForm, eventEjb, process, eventId, errors);
            } else if ("download".equals(func)) {
                int id = Utility.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                if (name == null || name.trim().length() == 0) {
                    errors.add("Error download file name.", new ActionMessage(
                            "error.simpleGenericError",
                            "Error filename download."));
                } else {
                    EventPropertyData item = eventEjb.getEventProperty(id);
                    if (item != null) {

                        // STJ-6037
                        //------------------------------------------------------
                        Object obj = null;
                        byte[] data = null;
                        if (BlobStorageAccess.STORAGE_FILESYSTEM.equals(item.getStorageTypeCd()) ||
							BlobStorageAccess.STORAGE_NFS.equals(item.getStorageTypeCd()) ||
                            BlobStorageAccess.STORAGE_FTP.equals(item.getStorageTypeCd())) {
                            BlobStorageAccess bsa = new BlobStorageAccess();
                            if (Utility.isSet(item.getBlobValueSystemRef())) {
                                data = bsa.readBlob(item.getStorageTypeCd(),
                                                    item.getBinaryDataServer(),
                                                    item.getBlobValueSystemRef());
                            }
                        } else {
                            data = item.getBlobValue();
                        }
                        if (data != null) {
                            obj = ObjectUtil.bytesToObject(data);
                        }
                        //------------------------------------------------------

                        if (obj instanceof ExtProcessVariable) {
                            obj = ((ExtProcessVariable) obj).getValue();
                        }

                        byte transferData[] = null;
                        if (ObjectUtil.BYTE_ARRAY_CLASS.getName().equals(obj.getClass().getName())) {
                            transferData = (byte[]) obj;
                            response.setContentType("application/octet-stream");
                            response.setHeader("Cache-Control", "no-cache");
                            response.setHeader("Content-disposition",
                                    "attachment; filename=" + name);
                        } else {
                            response.setContentType("text/xml");
                            response.setHeader("Cache-Control", "no-cache");
                            response.setHeader("Content-disposition",
                                    "attachment; filename=" + name);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            XMLEncoder decode = new XMLEncoder(baos);
                            decode
                                    .setPersistenceDelegate(
                                            java.math.BigDecimal.class,
                                            decode
                                                    .getPersistenceDelegate(Integer.class));
                            decode.writeObject(obj);
                            decode.flush();
                            decode.close();
                            transferData = baos.toByteArray();
                        }
                        OutputStream outputStream = response.getOutputStream();
                        outputStream.write(transferData);
                        outputStream.flush();
                        outputStream.close();
                    }
                    return null;
                }
            } else if ("editevent".equals(func)) {
                int attempt = Utility.parseInt(request.getParameter("attempt"));
                String status = request.getParameter("status");
                EventData item = eventEjb.getEvent(eventId);
                if (item != null) {
                	if (item.getStatus().equals(RefCodeNames.EVENT_STATUS_CD.STATUS_HOLD)){
                		if(Utility.isSet(eventForm.getProcessOnTime()) && !Utility.isSet(eventForm.getProcessOnDate())){
                            errors.add("Checking parameters", new ActionMessage("variable.requires.field.error", new Object[]{"Process Time", "Process Date"}));
                        }
                		if (eventForm.getProcessOnDate() != null)
                			checkDate(request, errors, eventForm.getProcessOnDate(), "Process Date");
                		
                		String time = "";
                        if (Utility.isSet(eventForm.getProcessOnTime())) {
                            time = eventForm.getProcessOnTime().trim().replaceAll("[\\s-:]+", ":");
                            if (checkTime(request, errors, time, "Process Time")) {
                                eventForm.setProcessOnTime(time);
                            }
                        }
                        if (!errors.isEmpty())
                			return errors;                		
                	}
                		
                    item.setAttempt(attempt);
                    item.setStatus(status);
                    SessionTool st = new SessionTool(request);
                    CleanwiseUser userData = st.getUserData();
                    String userName = userData.getUser().getUserName();
                    item.setModBy(userName);
                    Date processOn = null;
                    if (Utility.isSet(eventForm.getProcessOnDate())){
                    	if (!Utility.isSet(eventForm.getProcessOnTime())){
                    		eventForm.setProcessOnTime("00:00");
                    	}
                        String datePattern = I18nUtil.getDatePattern(ClwI18nUtil.getUserLocale(request));
                    	processOn = Utility.parseDate(eventForm.getProcessOnDate() + " " + eventForm.getProcessOnTime(), datePattern + " " + TIME_FORMAT, false);
                    }
                	item.setProcessTime(processOn);
                    eventEjb.updateEventData(item, request.getParameter("comment"));
                    initDetail(request, eventForm, eventEjb, process, eventId, errors);
                }
            } else if ("editblob".equals(func)) {
                int id = Utility.parseInt(request.getParameter("id"));
                String data = request.getParameter("data");
                String clazz = request.getParameter("class");
                if (data != null) {
                    EventPropertyData item = eventEjb.getEventProperty(id);
                    if ("java.lang.Integer".equals(clazz)) {
                        try {
                            item.setNumberVal(Integer.parseInt(data));
                            eventEjb.updateEventProperty(item);
                        } catch (Exception e) {
                            errors.add("Error while parsing.",
                                    new ActionMessage(
                                            "error.simpleGenericError",
                                            "Error parse number"));
                        }
                    } else if ("java.lang.String".equals(clazz)) {
                        if (data != null && data.length() > MAX_LENGTH_OF_STRING) {
                            errors.add("Error while parsing.",
                                            new ActionMessage(
                                                    "error.simpleGenericError",
                                                    "Very big string value. Available "
                                                            + MAX_LENGTH_OF_STRING
                                                            + " symbols! Remove "
                                                            + (data.length() - MAX_LENGTH_OF_STRING)
                                                            + " symbols!"));
                        } else {
                            item.setStringVal(data);
                            eventEjb.updateEventProperty(item);
                        }
                    } else if ("java.util.Date".equals(clazz)) {
                        try {
                            item.setDateVal(Utility.parseDate(data,
                                    DATE_FORMAT, true));
                            eventEjb.updateEventProperty(item);
                        } catch (Exception e) {
                            errors.add("Error while parsing.",
                                    new ActionMessage(
                                            "error.simpleGenericError",
                                            "Error parse date"));
                        }
                    } else {
                        try {
                            byte[] blobData = prepareBlob(item, data
                                    .getBytes("utf-8"));
                            eventEjb.updateEventPropertyBlob(item, blobData);
                        } catch (Exception e) {
                            errors.add("Error while parsing.",
                                    new ActionMessage(
                                            "error.simpleGenericError",
                                            "Error parse XML"));
                        }
                    }
                }
                initDetail(request, eventForm, eventEjb, process, eventId, errors);
            } else if ("uploadBlob".equals(func)) {
                int index = Utility.parseInt(request.getParameter("index"));
                int propId = Utility.parseInt(request.getParameter("propId"));
                FormFile files[] = eventForm.getUploadBlobs();
                EventPropertyData item = eventEjb.getEventProperty(propId);
                if (item != null && files != null && index >= 0
                        && index < files.length && files[index] != null) {
                    FormFile uploadFile = files[index];
                    try {
                        // STJ-6037
                        //------------------------------------------------------
                        Object obj = null;
                        byte[] data = null;
                        if (BlobStorageAccess.STORAGE_FILESYSTEM.equals(item.getStorageTypeCd()) ||
							BlobStorageAccess.STORAGE_NFS.equals(item.getStorageTypeCd()) ||
                            BlobStorageAccess.STORAGE_FTP.equals(item.getStorageTypeCd())) {
                            BlobStorageAccess bsa = new BlobStorageAccess();
                            if (Utility.isSet(item.getBlobValueSystemRef())) {
                                data = bsa.readBlob(item.getStorageTypeCd(),
                                                    item.getBinaryDataServer(),
                                                    item.getBlobValueSystemRef());
                            }
                        } else {
                            data = item.getBlobValue();
                        }
                        if (data != null) {
                            obj = ObjectUtil.bytesToObject(data);
                        }
                        //------------------------------------------------------
                        if (obj instanceof ExtProcessVariable) {
                            ((ExtProcessVariable) obj).updateValue(uploadFile.getFileData(), appUser.getUserName());
                        } else {
                            byte[] blobData = prepareBlob(item, uploadFile.getFileData());
                            eventEjb.updateEventPropertyBlob(item, blobData);
                        }
                        initDetail(request, eventForm, eventEjb, process, eventId, errors);
                    } catch (Throwable t) {
                        t.printStackTrace();
                        errors.add("Error while parsing.", new ActionMessage(
                                "error.simpleGenericError",
                                "Error parse upload data."));
                    }
                }
            } else if ("editemail".equals(func)) {
                int id = Utility.parseInt(request.getParameter("id"));
                EventEmailDataViewVector v =  eventEjb.getEmailsByEvent(eventId, null);
                for (int i = 0; v != null && i < v.size(); i++) {
                    EventEmailDataView buffer = (EventEmailDataView) v.get(i);
                    if (buffer.getEventEmailId() == id) {
                        buffer.setToAddress(request.getParameter("to"));
                        buffer.setCcAddress(request.getParameter("cc"));
                        //STJ - 3846
                        if(buffer.getToAddress()!=null) {
                        	buffer.setToAddress(buffer.getToAddress().trim());
                        }
                        if(buffer.getCcAddress()!=null) {
                        	buffer.setCcAddress(buffer.getCcAddress().trim());
                        }
                        EmailValidator.validateEmail(request, errors, "To", buffer.getToAddress());
                        EmailValidator.validateEmail(request, errors, "CC", buffer.getCcAddress());
                        if (errors.size() > 0) {
                            return errors;
                        }
                        buffer.setSubject(request.getParameter("subj"));
                        buffer.setText(request.getParameter("body"));
                        buffer.setFromAddress(request.getParameter("from"));
                        eventEjb.updateEventEmailData(buffer);
                        break;
                    }
                }
                initDetail(request, eventForm, eventEjb, process, eventId, errors);
            } else if ("uploadEmailAttach".equals(func)) {
                int id = Utility.parseInt(request.getParameter("propId"));
                int index = Utility.parseInt(request.getParameter("index"));
                EventEmailDataViewVector v = eventEjb.getEmailsByEvent(eventId,
                        null);
                FormFile formFile = eventForm.getNewattachments()[index];
                if (formFile != null && formFile.getFileData() != null
                        && formFile.getFileData().length > 0) {
                    for (int i = 0; v != null && i < v.size(); i++) {
                        EventEmailDataView buffer = (EventEmailDataView) v
                                .get(i);
                        if (buffer.getEventEmailId() == id) {
                            FileAttach[] oldAttachments = buffer
                                    .getFileAttachments();
                            FileAttach[] newAttachments = new FileAttach[oldAttachments == null ? 1
                                    : oldAttachments.length + 1];
                            for (int ii = 0; oldAttachments != null
                                    && ii < oldAttachments.length; ii++) {
                                newAttachments[ii] = oldAttachments[ii];
                            }
                            newAttachments[newAttachments.length - 1] = new FileAttach(
                                    formFile.getFileName(), formFile
                                            .getFileData(), formFile
                                            .getContentType(), formFile
                                            .getFileSize());
                            buffer.setAttachments(newAttachments);
                            eventEjb.updateEventEmailData(buffer);
                            break;
                        }
                    }
                }
                initDetail(request, eventForm, eventEjb, process, eventId, errors);
            } else if ("deleteEmailAttach".equals(func)) {
                int id = Utility.parseInt(request.getParameter("id"));
                int index = Utility.parseInt(request.getParameter("index"));
                EventEmailDataViewVector v = eventEjb.getEmailsByEvent(eventId,
                        null);
                for (int i = 0; v != null && i < v.size(); i++) {
                    EventEmailDataView buffer = (EventEmailDataView) v.get(i);
                    if (buffer.getEventEmailId() == id) {
                        FileAttach[] oldAttachments = buffer
                                .getFileAttachments();
                        FileAttach[] newAttachments = new FileAttach[oldAttachments == null ? 0
                                : oldAttachments.length - 1];
                        int ik = 0;
                        for (int ii = 0; oldAttachments != null
                                && ii < oldAttachments.length; ii++) {
                            if (ii != index) {
                                newAttachments[ik++] = oldAttachments[ii];
                            }
                        }
                        buffer.setAttachments(newAttachments);
                        eventEjb.updateEventEmailData(buffer);
                        break;
                    }
                }
                initDetail(request, eventForm, eventEjb, process, eventId, errors);
            } else if ("downloadEmailAttach".equals(func)) {
                int id = Utility.parseInt(request.getParameter("id"));
                int index = Utility.parseInt(request.getParameter("index"));
                EventEmailDataViewVector v = eventEjb.getEmailsByEvent(eventId,
                        null);
                FileAttach fileAttach = null;
                for (int i = 0; v != null && i < v.size(); i++) {
                    EventEmailDataView buffer = (EventEmailDataView) v.get(i);
                    if (buffer.getEventEmailId() == id) {
                        fileAttach = buffer.getFileAttachments()[index];
                        break;
                    }
                }
                if (fileAttach != null) {
                    byte[] data = fileAttach.getFileData();
                    response.setContentType(fileAttach.getContentType());
                    response.setHeader("Cache-Control", "no-cache");
                    response.setHeader("Content-disposition",
                            "attachment; filename=" + fileAttach.getName());
                    OutputStream outputStream = response.getOutputStream();
                    outputStream.write(data);
                    outputStream.flush();
                    outputStream.close();
                }
                return null;
            }else if("editcommit".equals(func)){
                if(eventForm.getUpdateEventData()!=null){
                    EventData eventData = eventForm.getUpdateEventData();
                    EventPropertyDataVector eventPropertyDataVector = eventData.getEventPropertyDataVector();
                    int i = 0;
                    if(eventPropertyDataVector!=null){
                        Iterator it = eventPropertyDataVector.iterator();
                        while(it.hasNext()){
                            EventPropertyData eventPropertyData = (EventPropertyData) it.next();
                            eventPropertyData.setDateVal(eventForm.getEventPropertyDateVector()[i]);
                            i++;
                        }
                    }
                }

                eventEjb.updateEventData(eventForm.getUpdateEventData());
                EventData eventData = eventEjb.getEvent(eventForm.getUpdateEventData().getEventId());
                eventForm.setUpdateEventData(eventData);

                //save attachment if it is email
                if ((request.getParameter("iemail")!=null)&&(!request.getParameter("iemail").trim().equals(""))){

                    FormFile[] addAttach = eventForm.getNewattachments();

                    if(addAttach!=null)
                    {
                        int iEmail = Integer.parseInt(request.getParameter("iemail"));
                        FileAttach[] fileAttachesOld = ((EventEmailDataView)(eventForm.getUpdateEventData().getEventEmailDataViewVector().get(iEmail)))
                                .getFileAttachments();
                        FileAttach[] fileAttachesAdd;
                        int fileAttachesAddLength = 0;
                        FileAttach[] fileAttachesNew;

                        //get count for adding
                        for(int fileIt=0; fileIt<addAttach.length; fileIt++)
                        {
                            FormFile addAttachFile = addAttach[fileIt];
                            if(addAttachFile!=null) {
                                fileAttachesAddLength++;
                            }
                        }
                        fileAttachesAdd = new FileAttach[fileAttachesAddLength];

                        //if count > 0 then add attachment
                        if (fileAttachesAddLength>0){
                            fileAttachesNew = new FileAttach[fileAttachesAdd.length + fileAttachesOld.length];

                            int fileAttachesNewIterator=0;
                            //at first add all old files
                            for(int i=0; i<fileAttachesOld.length;i++ ){
                                fileAttachesNewIterator++;
                                fileAttachesNew[fileAttachesNewIterator] = fileAttachesOld[i];
                            }

                            //add additional files from Newattachments
                            for(int fileIt=0; fileIt<addAttach.length; fileIt++)
                            {
                                FormFile addAttachFile = addAttach[fileIt];
                                String fileName = null;
                                if(addAttachFile!=null) {
                                    fileName = addAttachFile.getFileName();
                                    if(Utility.isSet(fileName)) {
                                        fileName = fileName.replace('\\', '/');
                                        addAttachFile.setFileName(fileName);
                                        try {
                                            fileAttachesNewIterator++;
                                            FileAttach file = new FileAttach(addAttachFile.getFileName(),
                                                                             addAttachFile.getFileData(),
                                                                             addAttachFile.getContentType(),
                                                                             addAttachFile.getFileSize());
                                            fileAttachesNew[fileAttachesNewIterator] = file;

                                            if(errors.size()>0) {
                                                return errors;
                                            }
                                        }
                                        catch (FileNotFoundException exc){
                                            exc.printStackTrace();
                                            String mess = "Can't find attachmet file: "+fileName;
                                            errors.add("error",new ActionError("error.simpleGenericError",mess));
                                            return errors;
                                        }
                                        catch(IOException exc) {
                                            exc.printStackTrace();
                                            String mess = "Can't read attachmet file: "+fileName;
                                            errors.add("error",new ActionError("error.simpleGenericError",mess));
                                            return errors;
                                        }
                                        finally{
                                            addAttachFile.destroy();
                                        }
                                    }
                                }
                            }
                            //save new array
                            ((EventEmailDataView)(eventForm.getUpdateEventData().getEventEmailDataViewVector().get(iEmail))).setAttachments(fileAttachesNew);

                            FormFile[] formFiles = new FormFile[1];
                            eventForm.setNewattachments(formFiles);

                        }
                    }
                }

            }else if("deleteeventproperty".equals(func)){
                int eventPrId = Integer.parseInt(request.getParameter("eventpropertyid"));
                EventData eventData = eventForm.getUpdateEventData();
                Iterator itProp = eventData.getEventPropertyDataVector().iterator();
                Object oD = null;

                while(itProp.hasNext()){
                    Object o = itProp.next();
                    EventPropertyData eventPropertyData = (EventPropertyData) o;
                    if(eventPropertyData.getEventPropertyId()==eventPrId){
                        oD = o;
                    }
                }

                if(oD!=null){
                    EventPropertyDataVector epdv = eventData.getEventPropertyDataVector();
                    epdv.remove(oD);
                    eventData.setEventPropertyDataVector(epdv);
                    eventForm.setUpdateEventData(eventData);
                }

                EventPropertyDataVector eventPropertyDataVector = eventData.getEventPropertyDataVector();
                Date[] eventPropertyDateVector = new Date[eventPropertyDataVector.size()];

                int i = 0;
                if(eventPropertyDataVector!=null){
                    Iterator it = eventPropertyDataVector.iterator();
                    while(it.hasNext()){
                        EventPropertyData eventPD = (EventPropertyData) it.next();
                        eventPropertyDateVector[i] = eventPD.getDateVal();
                        i++;
                    }
                }

                eventForm.setEventPropertyDateVector(eventPropertyDateVector);

            }else if("addeventproperty".equals(func)){
                EventData eventData = eventForm.getUpdateEventData();

                EventPropertyData eventPropertyData = EventPropertyData.createValue();
                eventPropertyData.setShortDesc("New Property");
                eventData.getEventPropertyDataVector().add(eventPropertyData);
                eventForm.setUpdateEventData(eventData);

                EventPropertyDataVector eventPropertyDataVector = eventData.getEventPropertyDataVector();
                Date[] eventPropertyDateVector = new Date[eventPropertyDataVector.size()];

                int i = 0;
                if(eventPropertyDataVector!=null){
                    Iterator it = eventPropertyDataVector.iterator();
                    while(it.hasNext()){
                        EventPropertyData eventPD = (EventPropertyData) it.next();
                        eventPropertyDateVector[i] = eventPD.getDateVal();
                        i++;
                    }
                }

                eventForm.setEventPropertyDateVector(eventPropertyDateVector);


            }

        }catch(NumberFormatException e){

        }

        session.setAttribute("EVENT_ADM_CONFIG_FORM", eventForm);

        return errors;
    }

    private static void initDetail(HttpServletRequest pRequest, EventAdmMgrForm eventForm, Event eventEjb, Process process, int eventId, ActionErrors errors) throws Exception {
        EventData eventData = eventEjb.getEvent(eventId);
        if (eventData == null) {
            errors.add("Error while searching", new ActionMessage("error.simpleGenericError", "Not found event with ID:" + eventId));
            return;
        }
        //init empty values if email data = null
        if(Event.TYPE_EMAIL.equals(eventData.getType())
                &&((eventData.getEventEmailDataViewVector()==null)||(eventData.getEventEmailDataViewVector().size()==0))){
            EventEmailDataViewVector eventEmailDataViewVector = new EventEmailDataViewVector();
            EventEmailDataView eventEmailDataView = new EventEmailDataView();
            eventEmailDataView.setEventId(eventData.getEventId());

            eventEmailDataViewVector.add(eventEmailDataView);
            eventData.setEventEmailDataViewVector(eventEmailDataViewVector);
        }

        EventPropertyDataVector eventPropertyDataVector = eventData.getEventPropertyDataVector();
        Date[] eventPropertyDateVector = new Date[eventPropertyDataVector.size()];

        if (eventData.getProcessTime() != null){     
        	SimpleDateFormat sdf =  new SimpleDateFormat(I18nUtil.getDatePattern(ClwI18nUtil.getUserLocale(pRequest)));
    		eventForm.setProcessOnDate(sdf.format(eventData.getProcessTime()));
    		eventForm.setProcessOnTime(stFormat.format(eventData.getProcessTime()));
    	}else{
    		eventForm.setProcessOnDate(null);
            eventForm.setProcessOnTime(null);        	
    	}
    	
        eventForm.setEventComment("");
        int i = 0;
        if(eventPropertyDataVector!=null){
            Iterator it = eventPropertyDataVector.iterator();
            while (it.hasNext()) {
                EventPropertyData eventPropertyData = (EventPropertyData) it
                        .next();
                if (Event.PROPERTY_COMMENT.equals(eventPropertyData.getType())) {
                    // STJ-6037
                    Object obj = null;
                    byte[] data = null;
                    if (BlobStorageAccess.STORAGE_FILESYSTEM.equals(eventPropertyData.getStorageTypeCd()) ||
						BlobStorageAccess.STORAGE_NFS.equals(eventPropertyData.getStorageTypeCd()) ||
                        BlobStorageAccess.STORAGE_FTP.equals(eventPropertyData.getStorageTypeCd())) {
                        BlobStorageAccess bsa = new BlobStorageAccess();
                        if (Utility.isSet(eventPropertyData.getBlobValueSystemRef())) {
                            data = bsa.readBlob(eventPropertyData.getStorageTypeCd(),
                                                eventPropertyData.getBinaryDataServer(),
                                                eventPropertyData.getBlobValueSystemRef());
                        }
                    } else {
                        data = eventPropertyData.getBlobValue();
                    }
                    if (data != null) {
                        obj = ObjectUtil.bytesToObject(data);
                    }
                    //----------------------------------------------------------
                    eventForm.setEventComment((String) obj);
                    it.remove();
                } else {
                    eventPropertyDateVector[i] = eventPropertyData.getDateVal();
                    i++;
                }
            }
        }
        if (Event.TYPE_EMAIL.equals(eventData.getType())) {
            FormFile[] formFiles = new FormFile[1];
            if (eventData.getEventEmailDataViewVector() != null
                    && eventData.getEventEmailDataViewVector().size() > 0) {
                formFiles = new FormFile[eventData
                        .getEventEmailDataViewVector().size()];
            }
            eventForm.setNewattachments(formFiles);
        }
        eventForm.setEventPropertyDateVector(eventPropertyDateVector);
        eventForm.setUpdateEventData(eventData);
        int properties = (eventData == null || eventData
                .getEventPropertyDataVector() == null) ? 0 : eventData
                .getEventPropertyDataVector().size();
        eventForm.setUploadBlobs(new FormFile[properties]);
        eventForm.setDownloadFileName(new String[properties]);
        eventForm.setEventProcesses(process.getProcessesByEventId(eventId));
        eventForm.setEventLogs(eventEjb.getLogs(eventId));
        eventForm.setEventProcessLogs(getEventProcessLogs(eventForm.getEventLogs()));
        IdVector processIds = Utility.getProcessIdOnly(eventForm.getEventLogs());
        if (processIds.size() > 0) {
            eventForm.setActiveLog(String.valueOf(processIds.get(processIds.size() - 1)));
        } else {
            eventForm.setActiveLog(String.valueOf(0));
        }

    }

    public static ActionErrors delete(HttpServletResponse response, HttpServletRequest request, ActionForm form)
            throws Exception {

        ActionErrors errors = new ActionErrors();
        EventAdmMgrForm eventForm = (EventAdmMgrForm) form;
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        int userId = appUser.getUser().getUserId();
        APIAccess factory = new APIAccess();
        Event eventEjb = factory.getEventAPI();

        String eventIdS = request.getParameter("eventid");

        if(eventIdS!=null){
            try{
                int eventId = Integer.parseInt(eventIdS);

                EventData eventData = eventEjb.getEvent(eventId);

                eventEjb.deleteEvent(eventData);

            }catch(NumberFormatException e){

            }

        }

        session.setAttribute("EVENT_ADM_CONFIG_FORM", eventForm);

        return errors;
    }

    public static ActionErrors processEvent(HttpServletResponse response, HttpServletRequest request, ActionForm form)
    throws Exception {

		ActionErrors errors = new ActionErrors();
		EventAdmMgrForm eventForm = (EventAdmMgrForm) form;
		HttpSession session = request.getSession();
		APIAccess factory = new APIAccess();
		Event eventEjb = factory.getEventAPI();

		String eventIdS = request.getParameter("eventid");
                
		if (eventIdS != null) {
                    boolean allowProceed = false;
		    try {
		        int eventId = Integer.parseInt(eventIdS);
                        if (eventId <= 0) {
                            throw new NumberFormatException();
                        }
                        String eventStatus = "UNKNOWN";
                        if (Utility.isSet(eventForm.getSearchEvents())) {
                            for (EventData event : (List<EventData>)eventForm.getSearchEvents()) {
                                if (eventId == event.getEventId()) {
                                    eventStatus = event.getStatus();
                                    if (RefCodeNames.EVENT_STATUS_CD.STATUS_LIMITED.equals(eventStatus) ||
                                        RefCodeNames.EVENT_STATUS_CD.STATUS_READY.equals(eventStatus) ||
                                        RefCodeNames.EVENT_STATUS_CD.STATUS_REJECTED.equals(eventStatus)) {
                                            allowProceed = true;
                                            break;
                                    }
                                }
                            }
                        }
                        if (allowProceed) {                            
                            eventEjb.processEvent(eventId);
                        } else {
                            List<String> statuses = new ArrayList();
                            statuses.add(RefCodeNames.EVENT_STATUS_CD.STATUS_LIMITED);
                            statuses.add(RefCodeNames.EVENT_STATUS_CD.STATUS_READY);
                            statuses.add(RefCodeNames.EVENT_STATUS_CD.STATUS_REJECTED);
                            errors.add("Processing Event", new ActionMessage("error.event.wrongStatus", eventStatus, Utility.toCommaSting(statuses, '\'')));
                            
                        }
		    } catch(NumberFormatException e) {
                        errors.add("Processing Event", new ActionMessage("error.store.wrongIdFormat", eventIdS));
		    }

		}

		session.setAttribute("EVENT_ADM_CONFIG_FORM", eventForm);

		return errors;
	}

    public static ActionErrors stop(HttpServletResponse response, HttpServletRequest request, ActionForm form)
            throws Exception {

        ActionErrors errors = new ActionErrors();
        EventAdmMgrForm eventForm = (EventAdmMgrForm) form;
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        int userId = appUser.getUser().getUserId();
        APIAccess factory = new APIAccess();
        Event eventEjb = factory.getEventAPI();

        String eventIdS = request.getParameter("eventid");

        if(eventIdS!=null){
            try{
                int eventId = Integer.parseInt(eventIdS);

                EventData eventData = eventEjb.getEvent(eventId);
                eventData.setAttempt(0);
                eventData.setStatus(Event.STATUS_REJECTED);
                eventEjb.updateEventData(eventData);

            }catch(NumberFormatException e){

            }

        }

        session.setAttribute("EVENT_ADM_CONFIG_FORM", eventForm);

        return errors;
    }

    public static ActionErrors creating(HttpServletResponse response, HttpServletRequest request, ActionForm form) throws NamingException, APIServiceAccessException, RemoteException {

        ActionErrors errors = new ActionErrors();
        EventAdmMgrForm eventForm = (EventAdmMgrForm) form;
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        int userId = appUser.getUser().getUserId();
        APIAccess factory = new APIAccess();
        Event eventEjb = factory.getEventAPI();

        try{
            if((eventForm.getCreateEventData().getStatus()==null)
                ||((eventForm.getCreateEventData().getStatus().trim().equals("")))){
                 errors.add("Error", new ActionError("error.simpleGenericError", "Error Status is null"));
            }else
            if((eventForm.getCreateEventData().getType()==null)
            ||(eventForm.getCreateEventData().getType().trim().equals(""))){
                errors.add("Error", new ActionError("error.simpleGenericError", "Error Type is null"));
            }else
            if(eventForm.getCreateEventData().getAttempt()<0){
                errors.add("Error", new ActionError("error.simpleGenericError", "Error Attempt < 0"));
            }else{

//                EventData eventData = eventEjb.addEventToDB(eventForm.getCreateEventData());
                EventProcessView epv = new EventProcessView(eventForm.getCreateEventData(), null, 0);
                eventEjb.addEventProcess(epv, "EventAdmMgrLogic");

                eventForm.setUpdateEventData(eventEjb.getEvent(epv.getEventData().getEventId()));
//                eventForm.setUpdateEventData(epv.getEventData());
                session.setAttribute("EVENT_ADM_CONFIG_FORM", eventForm);
            }
        } catch(RemoteException e){
            errors.add("Error", new ActionError("error.simpleGenericError", "Error while Creating"));
        }

        return errors;
    }

    public static ActionErrors initCreating(HttpServletResponse response, HttpServletRequest request, ActionForm form) throws NamingException, APIServiceAccessException, RemoteException {
        ActionErrors errors = new ActionErrors();
        EventAdmMgrForm eventForm = (EventAdmMgrForm) form;
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        int userId = appUser.getUser().getUserId();
        APIAccess factory = new APIAccess();
        Event eventEjb = factory.getEventAPI();

        eventForm.setUpdateEventData(new EventData());

        session.setAttribute("EVENT_ADM_CONFIG_FORM", eventForm);

        return errors;
    }

    public static ActionErrors upload(HttpServletResponse response, HttpServletRequest request, ActionForm form) throws NamingException, APIServiceAccessException, IOException {
        ActionErrors errors = new ActionErrors();
        EventAdmMgrForm eventForm = (EventAdmMgrForm) form;
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        int userId = appUser.getUser().getUserId();
        APIAccess factory = new APIAccess();
        Event eventEjb = factory.getEventAPI();

        String func = request.getParameter("func");

        if("get".equals(func)){
            int iAttach, iEmail;
            try{
                iAttach = Integer.parseInt(request.getParameter("iattach"));
                iEmail = Integer.parseInt(request.getParameter("iemail"));
            } catch (Exception e){
                e.printStackTrace();
                errors.add("Error", new ActionError("error.simpleGenericError", "Error while getting the number of attachment"));
                return errors;
            }

            FileAttach fileAttach;
            fileAttach = ((EventEmailDataView)(eventForm.getUpdateEventData().getEventEmailDataViewVector().get(iEmail)))
                    .getFileAttachments()[iAttach];

            int fileSize = fileAttach.getFileSize();
            String fileName = fileAttach.getName();
            byte[] byteData = fileAttach.getFileData();

            try{
                response.setContentLength( fileSize);
                response.setContentType("application/x-file-download");
                response.setHeader("Content-disposition",
                        "attachment; filename="
                                + fileName);
                response.setHeader("Cache-Control",
                        "max-age=" + TIMEOUT);
                ServletOutputStream outStream = response.getOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(outStream);

                for(int i=0;i<byteData.length;i++){
                    bos.write(byteData[i]);
                }
                bos.flush();
                outStream.flush();
            }catch (Exception e){
                e.printStackTrace();
                errors.add("Error", new ActionError("error.simpleGenericError", "Error while writing file to output"));
                return errors;
            }

        } else if("delete".equals(func)){
            int iAttach, iEmail;
            try{
                iAttach = Integer.parseInt(request.getParameter("iattach"));
                iEmail = Integer.parseInt(request.getParameter("iemail"));
            } catch (Exception e){
                e.printStackTrace();
                errors.add("Error", new ActionError("error.simpleGenericError", "Error while getting the number of attachment"));
                return errors;
            }

            try{
                FileAttach[] fileAttaches = ((EventEmailDataView)(eventForm.getUpdateEventData().getEventEmailDataViewVector().get(iEmail)))
                        .getFileAttachments();
                FileAttach[] fileAttaches2 = new FileAttach[fileAttaches.length - 1];
                int j = 0;
                for(int i=0; i<fileAttaches.length;i++){
                    if(i!=iAttach){
                        fileAttaches2[j] = fileAttaches[i];
                    }
                }

                ((EventEmailDataView)(eventForm.getUpdateEventData().getEventEmailDataViewVector().get(iEmail))).setAttachments(fileAttaches2);
                session.setAttribute("EVENT_ADM_CONFIG_FORM", eventForm);
            } catch (Exception e){
                e.printStackTrace();
                errors.add("Error", new ActionError("error.simpleGenericError", "Error while saving the attachment"));
                return errors;
            }
        }

        return errors;
    }
    private final static byte[] prepareBlob(EventPropertyData item, byte[] data)
            throws IOException {
        if (item == null) {
            return null;
        } else {
            Object obj = data;
            if (ObjectUtil.BYTE_ARRAY_CLASS.getName()
                    .equals(item.getVarClass()) == false) {
                ByteArrayInputStream bais = new ByteArrayInputStream(data);
                XMLDecoder decoder = new XMLDecoder(bais);
                obj = decoder.readObject();
            }
            return ObjectUtil.objectToBytes(obj);
        }
    }

    public static ActionErrors eventSystemControl(ActionForm form, String action) throws Exception {
        ActionErrors errors = new ActionErrors();
        EventAdmMgrForm eventForm = (EventAdmMgrForm) form;
        EventDispatcher eventDispatcher = null;
        try {
          InitialContext ctx = new InitialContext();
          eventDispatcher = (EventDispatcher) ctx.lookup("EventSystem");
        } catch (NamingException ne) {
          ne.printStackTrace();
          errors.add("Error", new ActionError("error.simpleGenericError", "Failed to lookup EventSystem"));
        }
        if (eventDispatcher != null) {
          if (!"refresh".equals(action)) {
              if ("stop".equals(action)) {
                  eventDispatcher.stopDispatcher();
              } else if ("start".equals(action)) {
                  eventDispatcher.startDispatcher();
              }
          }
          eventForm.setThreadsStatistic(eventDispatcher.getThreadsStatistic());
          eventForm.setSystemRunning(eventDispatcher.isRunning());
          eventForm.setStoppingFile(eventDispatcher.isStoppingFile());
        }
        return errors;
    }

    public static void changeProcessLog(HttpServletRequest request, HttpServletResponse response, ActionForm form) throws Exception {

        String requestedLogStr = request.getParameter("requestedLog");
        EventAdmMgrForm eventForm = (EventAdmMgrForm) request.getSession().getAttribute("EVENT_ADM_CONFIG_FORM");

        Integer requestedLog;
        try {
            requestedLog = Integer.parseInt(requestedLogStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            requestedLog = 0;
        }

        Element logXml;
        if (requestedLog > 0) {
            HashMap<Integer, LoggingDataVector> eventProcessLogs = eventForm.getEventProcessLogs();
            LoggingDataVector log = eventProcessLogs.get(requestedLog);
            logXml = toXml(requestedLog, new Integer(eventForm.getActiveLog()), log);
        } else {
            logXml = toXml(requestedLog, new Integer(eventForm.getActiveLog()), eventForm.getEventLogs());
        }

        response.setContentType("application/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        //response.getWriter().write(logXmlStr);
        OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
        XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
        serializer.serialize(logXml);

        eventForm.setActiveLog(requestedLogStr);

        request.getSession().setAttribute("EVENT_ADM_CONFIG_FORM", eventForm);

    }

    private static Element toXml(Integer requstedProcessId, Integer activeProcessId, LoggingDataVector processLog) {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;

        try {
        	docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
        }
        //Document doc = docBuilder.newDocument();
        Document doc = docBuilder.getDOMImplementation().createDocument("", "Response", null);

        //Element root = doc.createElement("Response");
        Element root = doc.getDocumentElement();

        Element node = doc.createElement("RequstedProcessId");
        node.appendChild(doc.createTextNode(String.valueOf(requstedProcessId)));
        root.appendChild(node);

        node = doc.createElement("ActiveProcessId");
        node.appendChild(doc.createTextNode(String.valueOf(activeProcessId)));
        root.appendChild(node);

        for (Object aProcessLog : processLog) {

            LoggingData logData = (LoggingData) aProcessLog;

            Element log = doc.createElement("Logging");
            log.setAttribute("LoggingId", String.valueOf(logData.getLoggingId()));

            Element el = doc.createElement("ProcessId");
            el.appendChild(doc.createTextNode(String.valueOf(logData.getProcessId())));
            log.appendChild(el);

            String stack = logData.getThrowable();
            boolean useStack = (stack != null && stack.trim().length() > 0);
            if (useStack) {
                stack = stack.trim();
                el = doc.createElement("Throwable");
                stack = stack.trim();
                int partNum = stack.length() / 4096; // for firefox
                for (int i = 0; i <= partNum; i++) {
                    Element throwablePart = doc.createElement("Part");
                    throwablePart.setAttribute("Num", String.valueOf(i + 1));
                    throwablePart.appendChild(doc.createTextNode(stack.substring(i * 4096, i * 4096 + (i == partNum ? stack.length() % 4096 : 4096))));
                    el.appendChild(throwablePart);
                }
                log.appendChild(el);
            }


            if (Utility.isSet(logData.getMessage())) {
                el = doc.createElement("Message");
                el.appendChild(doc.createTextNode(logData.getMessage()));
                log.appendChild(el);
            }

            el = doc.createElement("AddDate");
            el.appendChild(doc.createTextNode(sdf.format(logData.getAddDate())));
            log.appendChild(el);

            root.appendChild(log);

        }

        return root;
    }


    private static HashMap<Integer, LoggingDataVector> getEventProcessLogs(LoggingDataVector eventLogs) {

        HashMap<Integer, LoggingDataVector> eventProcessLogs = new HashMap<Integer, LoggingDataVector>();
        if (eventLogs != null) {
            for (Object oEventLog : eventLogs) {
                LoggingData eventLog = (LoggingData) oEventLog;
                if (eventLog.getProcessId() >= 0) {
                    LoggingDataVector logs = eventProcessLogs.get(eventLog.getProcessId());
                    if (logs == null) {
                        logs = new LoggingDataVector();
                    }
                    logs.add(eventLog);
                    eventProcessLogs.put(eventLog.getProcessId(), logs);
                }

            }
        }

        return eventProcessLogs;
    }
    
    public static ActionErrors setEventStatus(HttpServletRequest request, String eventStatus)
    throws Exception {

		ActionErrors errors = new ActionErrors();
		APIAccess factory = new APIAccess();
		Event eventEjb = factory.getEventAPI();

		String eventIdS = request.getParameter("eventid");
                
		if (eventIdS != null) {
			try {
				int eventId = Integer.parseInt(eventIdS);
				if (eventId <= 0) {
					throw new NumberFormatException();
				}

				EventData eventData = eventEjb.getEvent(eventId);
				eventData.setStatus(eventStatus);
				eventEjb.updateEventData(eventData);

			} catch(NumberFormatException e) {
				errors.add("Processing Event", new ActionMessage("error.store.wrongIdFormat", eventIdS));
			}

		}
		
		return errors;
	}

}
