package com.cleanwise.service.api.eventsys;

import com.cleanwise.service.api.value.EventPropertyDataVector;
import com.cleanwise.service.api.value.EventEmailData;
import com.cleanwise.service.api.value.EventEmailDataViewVector;

import java.util.HashMap;
import java.util.Date;
import java.io.Serializable;

/**
 * Title:        EventData
 * Description:  EventData implementation for Event DB structure
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * @author       Evgeny Vlasov, CleanWise, Inc.
 *
 */
public class EventData implements Serializable {

    private int eventId;
    private String status;
    private String type;
    private String cd;
    private int attempt;
    private String addBy;// SQL type:VARCHAR2
    private Date addDate;// SQL type:DATE, not null
    private String modBy;// SQL type:VARCHAR2
    private Date modDate;// SQL type:DATE, not null
    private String process;
    private int priority;
    private Date processTime;
    private String processName;
    private HashMap properties;
    private String stackTrace;
    private EventPropertyDataVector eventPropertyDataVector;
    private EventEmailDataViewVector eventEmailDataViewVector;
    private String additinalProperty;
    
    public String getAddBy() {
        return addBy;
    }

    public void setAddBy(String addBy) {
        this.addBy = addBy;
    }

    public String getModBy() {
        return modBy;
    }

    public void setModBy(String modBy) {
        this.modBy = modBy;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	public Date getProcessTime() {
		return processTime;
	}
	

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessName() {
		return processName;
	}
	
    public void addProperty(String key, Object value){
        this.properties.put(key, value);
    }

    public Object getProperty(String key){
        return this.properties.get(key);
    }

    public HashMap getProperties() {
        return properties;
    }

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public void decrementAttempt(){
        if(this.attempt>0){
            this.attempt--;
        }
    }

    public EventData(int eventId, String status, String type, String cd, HashMap properties, int attempt) {
        this.eventId = eventId;
        this.status = status;
        this.type = type;
        this.cd = cd;
        this.properties = properties;
        this.attempt = attempt;
        eventEmailDataViewVector = new EventEmailDataViewVector();
        eventPropertyDataVector  = new EventPropertyDataVector();
    }

    public EventData() {
        this.properties = new HashMap();
    }

    public void setProperties(HashMap eventProperties) {
       this.properties=eventProperties;
    }

    public EventPropertyDataVector getEventPropertyDataVector() {
        return eventPropertyDataVector;
    }

    public void setEventPropertyDataVector(EventPropertyDataVector eventPropertyDataVector) {
        this.eventPropertyDataVector = eventPropertyDataVector;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public EventEmailDataViewVector getEventEmailDataViewVector() {
        return eventEmailDataViewVector;
    }

    public void setEventEmailDataViewVector(EventEmailDataViewVector eventEmailDataViewVector) {
        this.eventEmailDataViewVector = eventEmailDataViewVector;
    }

    public String getAdditinalProperty() {
        return additinalProperty;
    }

    public void setAdditinalProperty(String additinalProperty) {
        this.additinalProperty = additinalProperty;
    }
}
