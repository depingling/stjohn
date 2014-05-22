package com.cleanwise.view.forms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.cleanwise.service.api.eventsys.EventData;
import com.cleanwise.service.api.eventsys.EventDataVector;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.value.LoggingDataVector;
import com.cleanwise.service.api.value.ProcessData;
import com.cleanwise.service.api.value.ProcessDataVector;
import com.cleanwise.view.utils.SelectableObjects;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

public class EventAdmMgrForm extends ActionForm {
    public final static int MAX_PREVIEW_BLOB_LENGTH = 1 * 1024;
    //Count statistic
    private int eventCount;

    private int eventEmailCountFailed;
    private int eventEmailCountInProgress;
    private int eventEmailCountProcessed;
    private int eventEmailCountReady;
    private int eventEmailCountRejected;

    private int eventEmailCountUnActive;

    private int eventProcessCountFailed;
    private int eventProcessCountInProgress;
    private int eventProcessCountProcessed;
    private int eventProcessCountReady;
    private int eventProcessCountRejected;

    private int eventProcessCountUnActive;

    //search attributes
    private String searchEventType;
    private String searchEventStatus;
    private int searchEventNumber;
    private boolean searchEventIsActive = true;
    private String searchDateFrom;
    private String searchDateTo;
    private String searchTimeFrom;
    private String searchTimeTo;
    private String searchParamValue;
    private String searchParamType;
    private String searchActive;
    private String searchFullText;
    private String searchContainsText;
    private String searchBlobText;
    private int searchBlobTextMaxRows;

    private int searchCount;
    private EventDataVector searchEvents;

    //update Attributes
    private EventData updateEventData;
    private Date[] eventPropertyDateVector;
//    private EventData createEventData = new EventData();
    private com.cleanwise.service.api.value.EventData createEventData = new com.cleanwise.service.api.value.EventData();
    private FormFile[] newattachments;
    private FormFile[] uploadBlobs;
    private String[] downloadFileName;
    private ProcessDataVector templateProcesses;
    private ProcessDataVector templateProcessesAlphabetized;
    private ProcessDataVector eventProcesses;
    private LoggingDataVector eventLogs;
    private Map taskProperties;
    private String attrType;
//    private String attrFilterType;
    private String attrFilterInteger;
    private String attrFilterIntegerValue1;
    private String attrFilterIntegerValue2;
    private String attrFilterString = "" + SearchCriteria.BEGINS_WITH_IGNORE_CASE;
    private String attrFilterStringValue1;
    private String attrFilterStringValue2;
    private String attrFilterDate;
    private String attrFilterDateValue1;
    private String attrFilterDateValue2;

    private boolean systemRunning = false;
    private boolean stoppingFile = false;
    private ArrayList threadsStatistic = new ArrayList();
    private String eventComment;
    private HashMap<Integer, LoggingDataVector> eventProcessLogs;
    private String activeLog;
    private SelectableObjects searchEventStatuses;
    private boolean init = false;
    private String processOnDate;
    private String processOnTime;

    public int getEventCount() {
        return eventCount;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    public int getEventEmailCountFailed() {
        return eventEmailCountFailed;
    }

    public void setEventEmailCountFailed(int eventEmailCountFailed) {
        this.eventEmailCountFailed = eventEmailCountFailed;
    }

    public int getEventEmailCountInProgress() {
        return eventEmailCountInProgress;
    }

    public void setEventEmailCountInProgress(int eventEmailCountInProgress) {
        this.eventEmailCountInProgress = eventEmailCountInProgress;
    }

    public int getEventEmailCountProcessed() {
        return eventEmailCountProcessed;
    }

    public void setEventEmailCountProcessed(int eventEmailCountProcessed) {
        this.eventEmailCountProcessed = eventEmailCountProcessed;
    }

    public int getEventEmailCountReady() {
        return eventEmailCountReady;
    }

    public void setEventEmailCountReady(int eventEmailCountReady) {
        this.eventEmailCountReady = eventEmailCountReady;
    }

    public int getEventEmailCountRejected() {
        return eventEmailCountRejected;
    }

    public void setEventEmailCountRejected(int eventEmailCountRejected) {
        this.eventEmailCountRejected = eventEmailCountRejected;
    }

    public int getEventEmailCountUnActive() {
        return eventEmailCountUnActive;
    }

    public void setEventEmailCountUnActive(int eventEmailCountUnActive) {
        this.eventEmailCountUnActive = eventEmailCountUnActive;
    }

    public int getEventProcessCountFailed() {
        return eventProcessCountFailed;
    }

    public void setEventProcessCountFailed(int eventProcessCountFailed) {
        this.eventProcessCountFailed = eventProcessCountFailed;
    }

    public int getEventProcessCountInProgress() {
        return eventProcessCountInProgress;
    }

    public void setEventProcessCountInProgress(int eventProcessCountInProgress) {
        this.eventProcessCountInProgress = eventProcessCountInProgress;
    }

    public int getEventProcessCountProcessed() {
        return eventProcessCountProcessed;
    }

    public void setEventProcessCountProcessed(int eventProcessCountProcessed) {
        this.eventProcessCountProcessed = eventProcessCountProcessed;
    }

    public int getEventProcessCountReady() {
        return eventProcessCountReady;
    }

    public void setEventProcessCountReady(int eventProcessCountReady) {
        this.eventProcessCountReady = eventProcessCountReady;
    }

    public int getEventProcessCountRejected() {
        return eventProcessCountRejected;
    }

    public void setEventProcessCountRejected(int eventProcessCountRejected) {
        this.eventProcessCountRejected = eventProcessCountRejected;
    }

    public int getEventProcessCountUnActive() {
        return eventProcessCountUnActive;
    }

    public void setEventProcessCountUnActive(int eventProcessCountUnActive) {
        this.eventProcessCountUnActive = eventProcessCountUnActive;
    }

    public String getSearchEventType() {
        return searchEventType;
    }

    public void setSearchEventType(String searchEventType) {
        this.searchEventType = searchEventType;
    }

    public String getSearchFullText() {
        return searchFullText;
    }

    public void setSearchFullText(String searchFullText) {
        this.searchFullText = searchFullText;
    }

    public String getSearchEventStatus() {
        return searchEventStatus;
    }

    public void setSearchEventStatus(String searchEventStatus) {
        this.searchEventStatus = searchEventStatus;
    }

    public boolean isSearchEventIsActive() {
        return searchEventIsActive;
    }

    public void setSearchEventIsActive(boolean searchEventIsActive) {
        this.searchEventIsActive = searchEventIsActive;
    }

    public EventDataVector getSearchEvents() {
        return searchEvents;
    }

    public void setSearchEvents(EventDataVector searchEvents) {
        this.searchEvents = searchEvents;
    }

    public int getSearchEventNumber() {
        return searchEventNumber;
    }

    public void setSearchEventNumber(int searchEventNumber) {
        this.searchEventNumber = searchEventNumber;
    }

    public int getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(int searchCount) {
        this.searchCount = searchCount;
    }

//    public void reset(ActionMapping mapping, HttpServletRequest request) {
//        //search attributes
//        this.searchEventType = "";
//        this.searchEventStatus = "";
//
//        this.searchEventIsActive = false;
//        this.searchEvents = new EventDataVector();
//    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        if (request.getParameter("eventSearchAllowReset") != null) { 
            if (searchEventStatuses != null && !searchEventStatuses.getValues().isEmpty()) {
                searchEventStatuses.handleStutsFormResetRequest();
            }
        }
        super.reset(mapping, request);
    }

    public EventData getUpdateEventData() {
        return updateEventData;
    }

    public void setUpdateEventData(EventData updateEventData) {
        this.updateEventData = updateEventData;
    }

    public com.cleanwise.service.api.value.EventData getCreateEventData() {
        return createEventData;
    }

    public void setCreateEventData(com.cleanwise.service.api.value.EventData createEventData) {
        this.createEventData = createEventData;
    }

    public String getSearchDateFrom() {
        return searchDateFrom;
    }

    public void setSearchDateFrom(String searchDateFrom) {
        this.searchDateFrom = searchDateFrom;
    }

    public String getSearchDateTo() {
        return searchDateTo;
    }

    public void setSearchDateTo(String searchDateTo) {
        this.searchDateTo = searchDateTo;
    }

	public String getSearchContainsText() {
        return searchContainsText;
    }

    public void setSearchContainsText(String searchContainsText) {
        this.searchContainsText = searchContainsText;
    }


    public String getSearchParamValue() {
        return searchParamValue;
    }

    public void setSearchParamValue(String searchParamValue) {
        this.searchParamValue = searchParamValue;
    }

    public String getSearchParamType() {
        return searchParamType;
    }

    public void setSearchParamType(String searchParamType) {
        this.searchParamType = searchParamType;
    }

    public Date[] getEventPropertyDateVector() {
        return eventPropertyDateVector;
    }

    public void setEventPropertyDateVector(Date[] eventPropertyDateVector) {
        this.eventPropertyDateVector = eventPropertyDateVector;
    }

    public FormFile[] getNewattachments() {
        return newattachments;
    }

    public void setNewattachments(FormFile[] newattachments) {
        this.newattachments = newattachments;
    }

    public ProcessDataVector getTemplateProcesses() {
        return templateProcesses;
    }

    public ProcessDataVector getTemplateProcessesAlphabetized() {
    	if (templateProcessesAlphabetized == null) {
    		if (templateProcesses != null) {
                    templateProcessesAlphabetized = new ProcessDataVector();
    			Map<String, ProcessData> templateProcessMap = new TreeMap<String, ProcessData>();
                        Iterator<ProcessData> templateProcessIterator = templateProcesses.iterator();                        
    			while (templateProcessIterator.hasNext()) {
    				ProcessData templateProcess = templateProcessIterator.next();
    				templateProcessMap.put(templateProcess.getProcessName(), templateProcess);
    			}
                        
                        templateProcessesAlphabetized.addAll(templateProcessMap.values());
    		}
    	}
        return templateProcessesAlphabetized;
    }

    public void setTemplateProcesses(ProcessDataVector templateProcesses) {
        this.templateProcesses = templateProcesses;
        this.templateProcessesAlphabetized = null;
    }

    public Map getTaskProperties() {
        return taskProperties;
    }

    public void setTaskProperties(Map taskProperties) {
        this.taskProperties = taskProperties;
    }

    public String getAttrFilterDate() {
        return attrFilterDate;
    }

    public void setAttrFilterDate(String attrFilterDate) {
        this.attrFilterDate = attrFilterDate;
    }

    public String getAttrFilterInteger() {
        return attrFilterInteger;
    }

    public void setAttrFilterInteger(String attrFilterInteger) {
        this.attrFilterInteger = attrFilterInteger;
    }

    public String getAttrFilterString() {
        return attrFilterString;
    }

    public void setAttrFilterString(String attrFilterString) {
        this.attrFilterString = attrFilterString;
    }

    public String getAttrFilterDateValue1() {
        return attrFilterDateValue1;
    }

    public void setAttrFilterDateValue1(String attrFilterDateValue1) {
        this.attrFilterDateValue1 = attrFilterDateValue1;
    }

    public String getAttrFilterDateValue2() {
        return attrFilterDateValue2;
    }

    public void setAttrFilterDateValue2(String attrFilterDateValue2) {
        this.attrFilterDateValue2 = attrFilterDateValue2;
    }

    public String getAttrFilterIntegerValue1() {
        return attrFilterIntegerValue1;
    }

    public void setAttrFilterIntegerValue1(String attrFilterIntegerValue1) {
        this.attrFilterIntegerValue1 = attrFilterIntegerValue1;
    }

    public String getAttrFilterIntegerValue2() {
        return attrFilterIntegerValue2;
    }

    public void setAttrFilterIntegerValue2(String attrFilterIntegerValue2) {
        this.attrFilterIntegerValue2 = attrFilterIntegerValue2;
    }

    public String getAttrFilterStringValue1() {
        return attrFilterStringValue1;
    }

    public void setAttrFilterStringValue1(String attrFilterStringValue1) {
        this.attrFilterStringValue1 = attrFilterStringValue1;
    }

    public String getAttrFilterStringValue2() {
        return attrFilterStringValue2;
    }

    public void setAttrFilterStringValue2(String attrFilterStringValue2) {
        this.attrFilterStringValue2 = attrFilterStringValue2;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getSearchActive() {
        return searchActive;
    }

    public void setSearchActive(String searchActive) {
        this.searchActive = searchActive;
    }

    public FormFile[] getUploadBlobs() {
        return uploadBlobs;
    }

    public void setUploadBlobs(FormFile[] uploadBlobs) {
        this.uploadBlobs = uploadBlobs;
    }

    public String[] getDownloadFileName() {
        return downloadFileName;
    }

    public void setDownloadFileName(String[] downloadFileName) {
        this.downloadFileName = downloadFileName;
    }

    public ProcessDataVector getEventProcesses() {
        return eventProcesses;
    }

    public void setEventProcesses(ProcessDataVector eventProcesses) {
        this.eventProcesses = eventProcesses;
    }

    public LoggingDataVector getEventLogs() {
        return eventLogs;
    }

    public void setEventLogs(LoggingDataVector eventLogs) {
        this.eventLogs = eventLogs;
    }

    public boolean getSystemRunning() {
        return systemRunning;
    }

    public void setSystemRunning(boolean systemRunning) {
        this.systemRunning = systemRunning;
    }

    public boolean getStoppingFile() {
        return stoppingFile;
    }

    public void setStoppingFile(boolean stoppingFile) {
        this.stoppingFile = stoppingFile;
    }

    public ArrayList getThreadStatistic() {
        return threadsStatistic;
    }

    public void setThreadsStatistic(ArrayList threadsStatistic) {
        this.threadsStatistic = threadsStatistic;
    }

    public String getSearchBlobText() {
        return searchBlobText;
    }

    public void setSearchBlobText(String searchBlobText) {
        this.searchBlobText = searchBlobText;
    }

    public int getSearchBlobTextMaxRows() {
        return searchBlobTextMaxRows;
    }

    public void setSearchBlobTextMaxRows(int searchBlobTextMaxRows) {
        this.searchBlobTextMaxRows = searchBlobTextMaxRows;
    }

    public String getEventComment() {
        return eventComment;
    }

    public void setEventComment(String eventComment) {
        this.eventComment = eventComment;
    }

    public void setEventProcessLogs(HashMap<Integer, LoggingDataVector> eventProcessLogs) {
        this.eventProcessLogs = eventProcessLogs;
    }

    public HashMap<Integer, LoggingDataVector> getEventProcessLogs() {
        return eventProcessLogs;
    }

    public String getActiveLog() {
        return activeLog;
    }
                     
    public void setActiveLog(String activeLog) {
        this.activeLog = activeLog;
    }

    public String getSearchTimeFrom() {
        return searchTimeFrom;
    }

    public void setSearchTimeFrom(String searchTimeFrom) {
        this.searchTimeFrom = searchTimeFrom;
    }

    public String getSearchTimeTo() {
        return searchTimeTo;
    }

    public void setSearchTimeTo(String searchTimeTo) {
        this.searchTimeTo = searchTimeTo;
    }

    public SelectableObjects getSearchEventStatuses() {
        return searchEventStatuses;
    }

    public void setSearchEventStatuses(SelectableObjects searchEventStatuses) {
        this.searchEventStatuses = searchEventStatuses;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

	public void setProcessOnDate(String processOnDate) {
		this.processOnDate = processOnDate;
	}

	public String getProcessOnDate() {
		return processOnDate;
	}

	public void setProcessOnTime(String processOnTime) {
		this.processOnTime = processOnTime;
	}

	public String getProcessOnTime() {
		return processOnTime;
	}

}
