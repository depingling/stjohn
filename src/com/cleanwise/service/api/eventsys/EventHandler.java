package com.cleanwise.service.api.eventsys;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.dao.EventDataAccess;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.apps.edi.CwX12DocumentParser;
import com.cleanwise.service.api.util.RefCodeNames;

import javax.naming.NamingException;
import java.util.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;
import com.cleanwise.service.api.value.EventEmailDataViewVector;
import com.cleanwise.service.api.value.EventEmailDataView;
import java.rmi.RemoteException;

/**
 * Title:        EventHandler
 * Description:  Main handler for evens
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 *
 * @author Evgeny Vlasov, CleanWise, Inc.
 */

public class EventHandler {

	static Logger log = Logger.getLogger(EventHandler.class);
    private static final String className = "EventHandler";

    protected EventData eventData;
    protected Event eventEjb;
    protected static LinkedList queue = new LinkedList();
    private static boolean stop = false;
    private static final int THREAD_NUM_DEFAULT = 4;//num events to process at one time
    private static boolean addQueueDone;
    private static final int ROW_NUM_DEFAULT = 100;
    protected static boolean processFl = false;

    /***************************************************************/
    public static class EventHandlerController implements Runnable {

        private static final String className = "EventHandlerController";
        private String name;
        EventData eventData;

        public EventHandlerController(String name) {
            this.name = name;
        }

        public EventHandlerController(String name, EventData eventData) {
            this.name = name;
            this.eventData = eventData;
        }

        public void run() {

            try {
            	if (eventData != null){
            		runHandler(eventData);
            	}else{
	                while (true) {

	                    Object o;

	                    synchronized (queue) {

	                        log.info("EventHandlerController::run [ " + name + " ] => size : " + queue.size());

	                        while ((!addQueueDone && queue.size() == 0) && !stop) { //now it impossible 05.2007
	                            try {
	                                log.info("EventHandlerController::run =>  wait ");
	                                queue.wait();
	                            } catch (InterruptedException e) {
	                                e.printStackTrace();

	                            }
	                        }

	                        if ((addQueueDone && queue.size() == 0) || (stop)) {
	                            log.info("EventHandlerController::run [ " + name + " ] =>  break; ");
	                            break;
	                        }

	                        o = queue.remove(0);
	                        queue.notifyAll();

	                        log.info("EventHandlerController::run [ " + name + " ] =>ready : " + o + " / queue.size : " + queue.size());

	                    }
	                    runHandler((EventData) o);
	                }
            	}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void runHandler(EventData eventData) throws EventHandlerException {

            try {
                if (Event.TYPE_EMAIL.equals(eventData.getType())) {
                    EmailEventHandler handler = new EmailEventHandler(eventData);
                    //handler.setName(eventData.getType()+"@"+eventData.getEventId()+"@"+System.currentTimeMillis());
                    handler.run();
                }

                if (Event.TYPE_DISTRIBUTOR_DELIVERY.equals(eventData.getType())) {
                    DistributorDeliveryEventHandler handler = new DistributorDeliveryEventHandler(eventData);
                    //handler.setName(eventData.getType()+"@"+eventData.getEventId()+"@"+System.currentTimeMillis());
                    handler.run();
                }

                if (Event.TYPE_PROCESS.equals(eventData.getType())) {
                    ProcessEventHandler handler = new ProcessEventHandler(eventData);
                    //handler.setName(eventData.getType() + "@" + eventData.getEventId() + "@" + System.currentTimeMillis());
                    handler.run();
                }
                
                if(RefCodeNames.PROCESS_NAMES.PROCESS_INBOUND_CHUNKS.equals(eventData.getType())){
                	ProcessEventHandler handler = new ProcessEventHandler(eventData);
                    //handler.setName(eventData.getType() + "@" + eventData.getEventId() + "@" + System.currentTimeMillis());
                    handler.run();
                }

                log.info("EventHandlerController::run [ " + name + " ] => go next ");

            } catch (Exception e) {
                throw new EventHandlerException(e.getMessage());
            }
        }
    }
    /***************************************************************/


    public EventHandler(EventData eventData) throws APIServiceAccessException, NamingException {
        this.eventData = eventData;
        APIAccess factory = new APIAccess();
        eventEjb = factory.getEventAPI();
    }

    public EventHandler() throws APIServiceAccessException, NamingException {

    }

    public static void start(int num) throws EventHandlerException {

        if (processFl) {
            return;
        }

        try {

            processFl = true;
            ArrayList threads = new ArrayList();

            while (true) {

                addQueueDone = false;

                try {
                    if (!addDataToQueue(ROW_NUM_DEFAULT)) break;

                    for (int xi = 0; xi < num; xi++) {

                        Thread thread = new Thread(new EventHandlerController("EventHandlerController[" + String.valueOf(xi) + "]"), "EventHandlerController[" + String.valueOf(xi) + "]");
                        threads.add(thread);
                        thread.start();

                    }

                    Iterator it = threads.iterator();
                    while (it.hasNext()) {
                        Thread thread = ((Thread) it.next());
                        thread.join();

                    }

                    threads.clear();
                    if (stop) throw new Exception("loop terminated");

                } catch (Exception e) {
                	e.printStackTrace();
                    throw new EventHandlerException(e.getMessage());
                }
            }
        } finally {
            processFl = false;
        }
    }

    public static void start() throws EventHandlerException {
        start(THREAD_NUM_DEFAULT);
    }

    public static void done() {
        synchronized (queue) {
            addQueueDone = true;
            queue.notifyAll();
        }
    }

    public static void stop() {
        synchronized (queue) {
            stop = true;
            queue.notifyAll();
        }
    }

    protected static boolean addDataToQueue(int numRow) {

        List list;

        synchronized (queue) {
            try {

                list = getEventList(numRow);
                queue.addAll(list);
                done();
                log.info("addDataToQueue() => Collection size: " + list.size() + " / queue.zize : " + queue.size());
                return list.size() > 0;

            } catch (Exception e) {
            	log.fatal("Exception in EventHandler.addDataToQueue()", e);
                //stop();
                return false;
            }
        }

    }

    private static List getEventList(int maxRow) throws Exception {


        APIAccess factory = new APIAccess();
        Event eventEJB = factory.getEventAPI();
        List eventList = new EventDataVector();

        java.util.Properties sysProps = System.getProperties();
        String eventTypesStr = sysProps.getProperty("event.types");
        if(eventTypesStr==null || eventTypesStr.trim().length()==0) {
                return eventList;
        }
        String[] eventTypesA = Utility.parseStringToArray(eventTypesStr, ",");
        ArrayList eventTypeAL = new ArrayList();
        for(int ii=0; ii<eventTypesA.length; ii++) {
            eventTypeAL.add(eventTypesA[ii].trim());
        }
        //get all events with status FAILED and run Handlers for each

/*
        {
            EventDataVector eventDataVector;

            try {
                eventDataVector = eventEJB.getEventV(Event.STATUS_FAILED, maxRow);
                eventList = eventDataVector;
            } catch (java.rmi.RemoteException exc) {
                exc.printStackTrace();
                throw new EventHandlerException(exc.getMessage());
            }
        }
    */
        //get all events with status READY and run Handlers for each
        {
            EventDataVector eventDataVector;
            try {
                eventDataVector = eventEJB.getEventV(Event.STATUS_READY,eventTypeAL, maxRow);
                //if(false) {
                //eventDataVector = eventEJB.getEventV("YK"+Event.STATUS_REJECTED, maxRow);
                //}
                //eventDataVector = new EventDataVector();
                eventList.addAll(eventDataVector);
            } catch (java.rmi.RemoteException exc) {
                throw new EventHandlerException(exc.getMessage());
            }

        }

        return eventList;
    }

    public EventData getEventData() {
        return eventData;
    }

    public void setEventData(EventData eventData) {
        this.eventData = eventData;
    }

    public void setEventStatus(String eventStatus) throws Exception {

        setEventData(this.eventEjb.setEventStatus(this.eventData, eventStatus));

    }

    protected void saveError(Exception e) throws Exception{

      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
      StringBuffer trace = sw.getBuffer();
      this.eventEjb.addProperty(eventData.getEventId(),
                           "stackTrace",
                           Event.PROPERTY_ERROR,
                           trace.toString().getBytes(),
                           1);

      if (e.getMessage() != null) {
        this.eventEjb.addProperty(eventData.getEventId(),
                                  "errorMessage",
                                  Event.PROPERTY_ERROR,
                                  e.getMessage(),
                                  1);
      }
    }

	public static void processEvent(int eventId) throws Exception {
		APIAccess factory = new APIAccess();
        Event eventEJB = factory.getEventAPI();
        EventDataVector eventDataVector;
        try {
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(EventDataAccess.EVENT_ID, eventId);
            eventDataVector = eventEJB.getEventV(dbc);
            if (eventDataVector.size() > 0){
            	EventData eventData = (EventData)eventDataVector.get(0);
            	eventData.setStatus(Event.STATUS_READY);
                EventEmailDataViewVector emailViewVector = eventEJB.getEmailsByEvent(eventData.getEventId(), null);
 //               EventEmailDataViewVector emailViewVector = eventData.getEventEmailDataViewVector();
                if(emailViewVector!=null && emailViewVector.size()>0){
                  EventEmailDataView emailView = (EventEmailDataView) emailViewVector.get(0);
                  emailView.setEmailStatusCd(Event.STATUS_READY);
                  eventData.setEventEmailDataViewVector(emailViewVector);
                }
            	//eventData.setStatus("YK"+Event.STATUS_REJECTED);
            	eventData.setAttempt(1);
            	EventHandlerController controller = new EventHandlerController("EventHandlerController - eventId=" + eventId, eventData);
            	Thread thread = new Thread(controller, "EventHandlerController - eventId=" + eventId);
            	thread.start();

                thread.join();
            }
        } catch (java.rmi.RemoteException exc) {
            exc.printStackTrace();
            throw new EventHandlerException(exc.getMessage());
        }
	}
  /*      public static void processEventTemp(int eventId, MessageResources mr ) throws Exception {
                APIAccess factory = new APIAccess();
        Event eventEJB = factory.getEventAPI();
        EventDataVector eventDataVector;
        try {
                DBCriteria dbc = new DBCriteria();
                dbc.addEqualTo(EventDataAccess.EVENT_ID, eventId);
            eventDataVector = eventEJB.getEventV(dbc);
            if (eventDataVector.size() > 0){
                EventData eventData = (EventData)eventDataVector.get(0);
                eventData.setStatus(Event.STATUS_READY);
                eventData.setAttempt(1);
                EventHandlerController controller = new EventHandlerController("EventHandlerController - eventId=" + eventId, eventData);
                Thread thread = new Thread(controller, "EventHandlerController - eventId=" + eventId);
                thread.start();
                log("start() => Thread " + thread.getName() + " start");

                thread.join();
                log("start() => " + thread.getName() + " join");
            }
        } catch (java.rmi.RemoteException exc) {
            exc.printStackTrace();
            throw new EventHandlerException(exc.getMessage());
        }
        }
*/

}
