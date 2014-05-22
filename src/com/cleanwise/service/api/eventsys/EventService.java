package com.cleanwise.service.api.eventsys;

import javax.naming.*;

import org.jboss.naming.*;
import org.jboss.system.*;

/**
 * JBoss specific MBean implementation for configuring, starting, and
 * binding to JNDI a Event System instance.
 */
public class EventService extends ServiceMBeanSupport implements  EventServiceMBean {

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Data members.
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    private EventDispatcher dispatcher;

    private String jndiName;

    private String propertiesFile;

    private boolean error;

    /*
    * If true, the Event Processing will be started. If false, the Event System is initailized
    * (and available) but start() is not called - it will not process events.
    */
    private boolean startEventSystem = true;

    private int threadCount = 10;

    private int extraThreadCount = 1;

    private int interval = 60; // in seconds

    private int priorityLevel = 100;

    private String eventType = "";
    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Constructors.
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    public EventService() {
        // default JNDI name for Event System
        jndiName = "EventSystem";
    }

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Interface.
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    public void setJndiName(String jndiName) throws Exception {
        String oldName = this.jndiName;
        this.jndiName = jndiName;

        if (super.getState() == STARTED) {
            unbind(oldName);

            try {
                rebind();
            } catch (NamingException ne) {
                log.error("Failed to rebind Event System", ne);

                throw new Exception(
                        "Failed to rebind Event System - ", ne);
            }
        }
    }

    public String getJndiName() {
        return jndiName;
    }

    public String getName() {
        return "EventService(" + jndiName + ")";
    }

    public void setStartEventSystem(boolean startEventSystem) {
        this.startEventSystem = startEventSystem;
    }

    public boolean getStartEventSystem() {
        return startEventSystem;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setExtraThreadCount(int extraThreadCount) {
        this.extraThreadCount = extraThreadCount;
    }

    public int getExtraThreadCount() {
        return extraThreadCount;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getInterval() {
        return interval;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void createService() throws Exception {
        log.info("Create EventService(" + jndiName + ")...");
        dispatcher = new EventDispatcher();
        dispatcher.setThreadCount(threadCount);
        dispatcher.setPollInterval(interval);
        dispatcher.setEventType(eventType);
        dispatcher.setPriorityLevel(priorityLevel);
        dispatcher.setExtraThreadCount(extraThreadCount);
        log.info("EventService(" + jndiName + ") created.");
    }

    public void destroyService() throws Exception {
        log.info("Destroy EventsService(" + jndiName + ")...");
        dispatcher = null;
        log.info("EventService(" + jndiName + ") destroyed.");
    }

    public void startService() throws Exception {
        log.info("Start EventService(" + jndiName + ")...");
        try {
          rebind();
        } catch (NamingException ne) {
          log.error("Failed to rebind Events System", ne);
          throw new Exception("Failed to rebind Event System - ", ne);
        }
        dispatcher.start();
        if (startEventSystem) {
          dispatcher.startDispatcher();
          log.info("EventService(" + jndiName + ") started.");
        } else {
          log.info("Skipping starting the Event System (will not process events).");
        }
    }

    public void stopService() throws Exception {
        log.info("Stop EventService(" + jndiName + ")...");
        dispatcher.stopDispatcher();
        unbind(jndiName);
        log.info("EventService(" + jndiName + ") stopped.");
    }

    private void rebind() throws NamingException {
        InitialContext rootCtx = null;
        try {
            rootCtx = new InitialContext();
            Name fullName = rootCtx.getNameParser("").parse(jndiName);
            NonSerializableFactory.rebind(fullName, dispatcher, true);
        } finally {
            if (rootCtx != null) {
                try {
                    rootCtx.close();
                } catch (NamingException ignore) {}
            }
        }
    }

    private void unbind(String jndiName) {
        InitialContext rootCtx = null;
        try {
            rootCtx = new InitialContext();
            rootCtx.unbind(jndiName);
            NonSerializableFactory.unbind(jndiName);
        } catch (NamingException e) {
            log.warn("Failed to unbind Even System with jndiName: " + jndiName, e);
        } finally {
            if (rootCtx != null) {
                try {
                    rootCtx.close();
                } catch (NamingException ignore) {}
            }
        }
    }
}
