
package com.cleanwise.service.api.eventsys;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import javax.naming.*;

import com.cleanwise.service.api.*;
import com.cleanwise.service.api.process.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.session.Process;
import com.cleanwise.service.api.util.IgnoredException;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.Constants;


import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 * Title:        ProcessEventHandler
 * Description:  ProcessEventHandler implementation for Events which are for PROCESS running
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 *
 * @author Evgeny Vlasov, CleanWise, Inc.
 * 
 * Updated by Sergei V. Cher (SVC) 4/16/2009
 *         Added processing of the the exception, received when "negative" 997 form was created.
 *         New event status "STATUS_PROC_ERROR" created
 */
public class ProcessEventHandler extends EventHandler {
    protected Logger log = Logger.getLogger(this.getClass());

    private String className = "ProcessEventHandler";

    public ProcessEventHandler(EventData eventData) throws NamingException, APIServiceAccessException {
        super(eventData);
    }

    /**
     * Create and run Process
     */
    public void run() {
        try {
            MDC.put("EventID", new Integer(getEventData().getEventId()));
            APIAccess factory = new APIAccess();
            Process processProvider = factory.getProcessAPI();
            log.info("run(). start #1");

            //Execute the process if it failed during the running
            if (Event.STATUS_FAILED.equals(getEventData().getStatus())) {

                try {

                    setEventStatus(Event.STATUS_IN_PROGRESS);

                    //get process Id from the Event
                    PairViewVector prop = (PairViewVector) (getEventData().getProperty(Event.PROCESS_ACTIVE_ID));
                    int activeProcessId = 0;
                    if (!prop.isEmpty()) {
                        activeProcessId = Integer.parseInt(((PairView) prop.get(0)).getObject2().toString());
                    }
                    MDC.put("ProcessID", new Integer(activeProcessId));

                    //create Active Process class and run
                    ProcessActive activeProcess = new ProcessActive(processProvider.getProcess(activeProcessId));

                    activeProcess.executeProcess();

                    setEventStatus(Event.STATUS_PROCESSED);
                    this.eventEjb.deleteEventProperty(eventData.getEventId(), Event.PROPERTY_ERROR);
                    
                } catch (Exception e) {
                    /*** old code
                    setEventStatus(Event.STATUS_REJECTED);
                    getEventData().setAttempt(0);
                    this.eventEjb.updateEventData(eventData);
                    this.eventEjb.deleteEventProperty(eventData.getEventId(), Event.PROPERTY_ERROR);
                    saveError(e);
                    ***/
                    /*** SVC: new code ***/
                    log.error(e.getMessage() != null ? e.getMessage() : "Event Exception!", e);
                    Throwable te = getInitialException(e);
                    if (te instanceof ParsingException) {
                    	setEventStatus(Event.STATUS_PROC_ERROR);
                    	this.eventEjb.deleteEventProperty(eventData.getEventId(), Event.PROPERTY_ERROR);
                    } else {
                    	setEventStatus(Event.STATUS_REJECTED);
                        getEventData().setAttempt(0);
                        this.eventEjb.updateEventData(eventData);
                        this.eventEjb.deleteEventProperty(eventData.getEventId(), Event.PROPERTY_ERROR);
                        saveError(e);
                    }
                    /*******************/
                } finally {
                    MDC.remove("ProcessID");
                }
            }

            //Execute a new process
            if (Event.STATUS_READY.equals(getEventData().getStatus())) {

//                log("run() => BEGIN Start ready process");
                log.info("run() => BEGIN Start ready process");

                try {
                    PairViewVector prop = (PairViewVector) (getEventData().getProperty(Event.PROPERTY_PROCESS_TEMPLATE_ID));
                    int templateId = 0;

                    if (!prop.isEmpty()) {
                        templateId = Integer.parseInt(((PairView) prop.get(0)).getObject2().toString());
                    }

                    if (templateId != 0) {

                        setEventStatus(Event.STATUS_IN_PROGRESS);

                        PairViewVector schemas = (PairViewVector) (getEventData().getProperty(Event.PROCESS_SCHEMA));
                        ProcessSchema schema = null;
                        if (schemas!=null && !schemas.isEmpty()) {
                            schema = (ProcessSchema) ((PairView) schemas.get(0)).getObject2();
                        }

                        //Create process according to the template
                        ProcessActive activeProcess = processProvider.createActiveProcess(templateId, schema, className);
                        MDC.put("ProcessID", new Integer(activeProcess.getProcessActiveData().getProcessId()));
                        eventEjb.addProperty(
                                eventData.getEventId(),
                                "process_id",
                                Event.PROCESS_ACTIVE_ID,
                                new Integer(activeProcess.getProcessActiveData().getProcessId()),
                                1);

                        //Create and set parameters
                        PairViewVector processParams = new PairViewVector();

                        Iterator itParIterator = ((PairViewVector) getEventData().getProperty(Event.PROCESS_VARIABLE)).iterator();
                        PairView paramEventId = null;
                        while (itParIterator.hasNext()) {
                            PairView pair = (PairView) itParIterator.next();
                            processParams.add(pair);
                            if (pair.getObject1().equals(Constants.EVENT_ID_PROCESS_PARAM))
                            	paramEventId = pair;
                        }
                        
                        //add parent event id to params
                        PairView paramParentEventId = PairView.createValue();
                        paramParentEventId.setObject1("pParentEventId");
                        paramParentEventId.setObject2(new Integer(eventData.getEventId()));
                        processParams.add(paramParentEventId);
                        
                        //add event id to params
                        if (paramEventId == null){
	                        paramEventId = PairView.createValue();
	                        paramEventId.setObject1(Constants.EVENT_ID_PROCESS_PARAM);
	                        processParams.add(paramEventId);
                        }
                        paramEventId.setObject2(new Integer(eventData.getEventId()));                        
                        
                        //set actual process Id value for pProcessActiveId parameter  
                        for (Iterator iter = processParams.iterator(); iter.hasNext(); ) {
                          PairView param = (PairView) iter.next();
                          if (param.getObject1().equals("pProcessActiveId")){
                            param.setObject2(new Integer(activeProcess.getProcessActiveData().getProcessId()));
                            break;
                          }
                        }
                        activeProcess.initProcessVariable(processParams);

                        //Run process
                        log.info("SVC11: Before execute process");
                        activeProcess.executeProcess();
                        log.info("SVC22: After execute process");
                                                
                        setEventStatus(Event.STATUS_PROCESSED); // SVC => commented temporary

                    } else {
                        setEventStatus(Event.STATUS_REJECTED);
                        getEventData().setAttempt(0);
                        this.eventEjb.updateEventData(eventData);
                    }
                    
                    this.eventEjb.deleteEventProperty(eventData.getEventId(), Event.PROPERTY_ERROR);

                } catch (Exception e) {
                    log.error(e.getMessage() != null ? e.getMessage() : "Event Exception!", e);
                    Throwable te = getInitialException(e);
                    
                    /*** SVC: new code *****************************/
                    if (te instanceof ParsingException) {
                    	setEventStatus(Event.STATUS_PROC_ERROR);
                    	this.eventEjb.deleteEventProperty(eventData.getEventId(), Event.PROPERTY_ERROR);
                    } else {
                    	if (te instanceof IgnoredException) {
                        	setEventStatus(Event.STATUS_IGNORE);
                        }else{
                        	setEventStatus(Event.STATUS_REJECTED);
                        }
                        getEventData().setAttempt(0);
                        this.eventEjb.updateEventData(eventData);
                        this.eventEjb.deleteEventProperty(eventData.getEventId(), Event.PROPERTY_ERROR);
                        saveError(e);
                    }
                    /******************************************/
                    
                    /*** old code
                    if (te instanceof IgnoredException) {
                    	setEventStatus(Event.STATUS_IGNORE);
                    }else{
                    	setEventStatus(Event.STATUS_REJECTED);
                    }
                    getEventData().setAttempt(0);
                    this.eventEjb.updateEventData(eventData);
                    this.eventEjb.deleteEventProperty(eventData.getEventId(), Event.PROPERTY_ERROR);
                    saveError(e);
                    ***/
                } finally {
                    MDC.remove("ProcessID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MDC.remove("EventID");
        }
    }

    private Throwable getInitialException(Throwable e){         
    	if (e instanceof InvocationTargetException) {
    		InvocationTargetException ie = (InvocationTargetException)e;
			if (ie.getTargetException() != null){
				return getInitialException(ie.getTargetException());
			}else
				return ie;
    	}
        
    	if (e.getCause() != null)
    		return getInitialException(e.getCause());
        
    	return e;
    }
}
