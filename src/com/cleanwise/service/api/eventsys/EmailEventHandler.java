package com.cleanwise.service.api.eventsys;

import java.io.File;
import java.util.Iterator;

import javax.naming.NamingException;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.EventEmailDataView;
import com.cleanwise.service.api.value.EventEmailDataViewVector;
import com.cleanwise.service.apps.ApplicationsEmailTool;

//import org.jboss.util.file.Files;

/**
 * Title:        EmailEventHandler
 * Description:  EmailEventHandler implementation for Events which are for emails sending
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 *
 * @author Evgeny Vlasov, CleanWise, Inc.
 */
public class EmailEventHandler extends EventHandler implements Runnable {
    protected Logger log = Logger.getLogger(this.getClass());

    public EmailEventHandler(EventData eventData) throws NamingException, APIServiceAccessException {
        super(eventData);
    }

    public void run() {
        int eventId = getEventData().getEventId();
        MDC.put("EventID", new Integer(eventId));
        log.info("Run emails for event:" + eventId);
        try {
            //if email event is failed than change status to ready for processing
            if (Event.STATUS_FAILED.equals(getEventData().getStatus())) {
                this.setEventStatus(Event.STATUS_READY);
            }
            log.info("Event STATUS : " + getEventData().getStatus());

            //Execute a new process
            if (Event.STATUS_READY.equals(getEventData().getStatus())) {
                try {
//                    setEventStatus(Event.STATUS_IN_PROGRESS);
                    EventEmailDataViewVector emails = eventData.getEventEmailDataViewVector();
                    String emailStatus = (emails == null ) ? null : ((EventEmailDataView)emails.get(0)).getEmailStatusCd();
                    log.info("Event Email STATUS " + emailStatus);
                    if (emailStatus != null && emailStatus.equals(Event.STATUS_READY)){
                        log.info("Process event with email status " + Event.STATUS_READY);
                    } else {
                      emails = this.eventEjb.getEmailsByEvent(getEventData().getEventId(), Event.STATUS_READY);
                    }
                    setEventStatus(Event.STATUS_IN_PROGRESS);
                    log.info("Was found " + emails.size() + " emails with " + Event.STATUS_READY + " status."  );
                    sendEmails(emails);

                    this.eventEjb.deleteEventProperty(eventData.getEventId(), Event.PROPERTY_ERROR);
                    setEventStatus(Event.STATUS_PROCESSED);
                } catch (Exception e) {
                    //set status = REJECTED if it was the last attempt
                    log.error(e, e);
                    if(this.eventData.getAttempt()==1){
                        this.eventData.setAttempt(0);
                        this.eventData.setStatus(Event.STATUS_REJECTED);
                    }else{
                        this.eventData.decrementAttempt();
                        this.eventData.setStatus(Event.STATUS_FAILED);
                    }
                    this.eventEjb.updateEventData(eventData);
                    this.eventEjb.deleteEventProperty(eventData.getEventId(), Event.PROPERTY_ERROR);
                    saveError(e);
                }
            }
        } catch (Exception e) {
            log.error(e, e);
            e.printStackTrace();
        } finally {
            MDC.remove("EventID");
        }
    }

    /**
     * Send all emails from the Event
     * @param emails All emails from the current event
     * @throws Exception
     */
    private void sendEmails(EventEmailDataViewVector emails) throws Exception {

        int errorCount = 0;
        int ik = 0;
        if (emails != null && !emails.isEmpty()) {
            Iterator it = emails.iterator();

            while (it.hasNext()) {
                ik++;
                EventEmailDataView email = (EventEmailDataView) it.next();

                //bug # 4494 : Email should be sent with body text.
                if ( (email.getText()==null || email.getText().length() == 0) && email.getLongText() != null ) {
                    byte[] byteMess = email.getLongText();
                    String longText = new String(byteMess) ;
                    email.setText(longText);
                }
                
                try {
                    String to                = email.getToAddress();
                    String cc                = email.getCcAddress();
                    String subject           = email.getSubject();
                    String message           = email.getText();
                    String from              = email.getFromAddress();
                    if(subject!=null && Utility.isContainsWorld(subject, "null")){
                    	email.setEmailStatusCd(Event.STATUS_REJECTED);
                        email = this.eventEjb.updateEventEmailData(email);
                        errorCount++;
                        continue;
                    }

                    log.info("Trying send email " + ik + "/" + emails.size() + " to " + email.getToAddress());
                    String importance        = email.getImportance();
                    FileAttach[] attachments = email.getFileAttachments();
                    File[] attachs;

                    attachs = (new ApplicationsEmailTool()).fromAttachsToFiles(attachments);

                    email.setEmailStatusCd(Event.STATUS_IN_PROGRESS);
                    email = this.eventEjb.updateEventEmailData(email);

                    if (attachs.length > 0) {
                        ApplicationsEmailTool.sendEmail(to, cc, subject, message, from, importance, attachs);
                    } else {
                        ApplicationsEmailTool.sendEmail(to, cc, subject, message, from, importance);
                    }

                    email.setEmailStatusCd(Event.STATUS_PROCESSED);
                    email = this.eventEjb.updateEventEmailData(email);

                } catch (Exception e) {
                    log.error(e, e);
                    errorCount++;
                    e.printStackTrace();
                    email.setEmailStatusCd(Event.STATUS_FAILED);
                    eventEjb.updateEventEmailData(email);
                }
            }

            if(errorCount>0){
                throw new EventHandlerException();
            }

        }
    }
}
