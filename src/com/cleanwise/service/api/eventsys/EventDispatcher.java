package com.cleanwise.service.api.eventsys;

import java.util.*;

import org.apache.log4j.Logger;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import java.io.File;
import java.lang.management.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class EventDispatcher  extends Thread{
  public static final Logger log = Logger.getLogger(EventDispatcher.class);
  public static final String STOPPING_FILE_NAME = "event.stop";
  public static final int STOPPING = 0;
  public static final int RUNNING = 1;
  private int state = STOPPING;
  private String threadGroupName = "EventSystem";
  private ThreadGroup threadGroup;
  private int pollInterval = 60; // in seconds
  private int threadCount = 10;
  private int extraThreadCount = 1;
  private ArrayList eventType = new ArrayList();
  private IdVector aliveThreads;
  private int priorityLevel = 100;
  private ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
  private HashMap statistic = new HashMap();
  protected NumberFormat nf = new DecimalFormat("0.0");
  protected NumberFormat nfe = new DecimalFormat("0.000");

  public EventDispatcher(){
    threadGroup = new ThreadGroup(threadGroupName);
  }

  public EventDispatcher(String threadGroupName) {
    this.threadGroupName = threadGroupName;
    threadGroup = new ThreadGroup(threadGroupName);
  }

  public boolean isRunning() {
    return (state == RUNNING) && !isStoppingFileExists();
  }

  public boolean isStoppingFile() {
    return isStoppingFileExists();
  }

  public void setPollInterval(int pollInterval) {
    this.pollInterval = pollInterval;
  }

  public int getPollInterval() {
    return this.pollInterval;
  }

  public void setThreadCount(int threadCount) {
    this.threadCount = threadCount;
  }

  public void setExtraThreadCount(int extraThreadCount) {
    this.extraThreadCount = extraThreadCount;
  }

  public void setPriorityLevel(int priorityLevel) {
    this.priorityLevel = priorityLevel;
  }

  public int getThreadCount() {
    return this.threadCount;
  }

  public void setEventType(String eventTypesStr) {
    if(eventTypesStr==null || eventTypesStr.trim().length()==0) {
            log.info("Event type is not set");
    }
    String[] eventTypesA = Utility.parseStringToArray(eventTypesStr, ",");
    for(int ii=0; ii<eventTypesA.length; ii++) {
        this.eventType.add(eventTypesA[ii].trim());
    }
  }

  public void startDispatcher() {
    if (state == STOPPING && !isStoppingFileExists()) {
      state = RUNNING;
      log.info("dispatcher is started");
    }
  }

  public void stopDispatcher() {
    if (state == RUNNING) {
      state = STOPPING;
      log.info("dispatcher is stopped");
    }
  }

  private boolean isStoppingFileExists() {
    File stop = new File(STOPPING_FILE_NAME);
    return stop.exists();
  }

  public void run() {
    while(true) {
      try {
        if (threadBean.isThreadCpuTimeSupported())
            threadBean.setThreadCpuTimeEnabled(true);
        if (threadBean.isThreadContentionMonitoringSupported())
            threadBean.setThreadContentionMonitoringEnabled(true);
        if (isStoppingFileExists() && state == RUNNING) {
          log.info("'event.stop' file is found");
          stopDispatcher();
        }
        if (state == RUNNING) {
          EventDataVector events = new EventDataVector();
          IdVector newThreads = new IdVector();
          int activeCount = getActiveThreadCount();
          if (activeCount < threadCount) {
            log.info("normal");
            events = getEventForProcessing(threadCount - activeCount, 0);
          } else if (activeCount < threadCount + extraThreadCount) {
            log.info("high");
            events = getEventForProcessing(threadCount + extraThreadCount - activeCount, priorityLevel);
          }
          log.info("events: " + events.size());
          for (Iterator it = events.iterator(); it.hasNext();) {
            for (int i = 1; i <= threadCount + extraThreadCount; i++) {
              if (!aliveThreads.contains(i) && !newThreads.contains(i)) {
                newThreads.add(i);
                EventController thread = new EventController(threadGroup, i, (EventData) it.next());
                thread.start();
                statistic.put(thread.getId(), new Times(thread.getEventId(),
                                                        System.nanoTime(),
                                                        this.getCpuTime(thread.getId())));
                break;
              }
            }
          }
        }        
      } catch (Throwable th) {
        log.error("Throwable occured in Events Dispatcher", th);
      }finally{
    	  try {
			Thread.sleep(1000 * pollInterval);
		} catch (InterruptedException e) {
			log.error("InterruptedException occured in Events Dispatcher", e);
		}
      }
    }
  }

   private int getActiveThreadCount() {
     HashMap newStatistic = new HashMap();
     aliveThreads = new IdVector();
     Thread[] theThreads = new Thread[threadGroup.activeCount()];
     threadGroup.enumerate(theThreads, false);
     for (int i = 0; i < theThreads.length; i++) {
    	 if (theThreads[i] instanceof EventController){
    		 aliveThreads.add(((EventController)theThreads[i]).getNum());
    	 }else{
    		 log.error("Un-expected thread in EventDispatcher.getActiveThreadCount(): " 
    				 + theThreads[i].getClass().getName()+":"+theThreads[i]);
    	 }
       monitoring(theThreads[i].getId());
       newStatistic.put(theThreads[i].getId(), statistic.get(theThreads[i].getId()));
     }
     statistic = newStatistic;
     log.info("aliveThreads: " + aliveThreads.size());
     return aliveThreads.size();
   }

  private EventDataVector getEventForProcessing(int maxRows, int priority) throws Exception {
      APIAccess factory = new APIAccess();
      Event eventEJB = factory.getEventAPI();
          EventDataVector eventDataV;
          try {
        	  eventEJB.updateEventFromHoldToReady();
              eventDataV = eventEJB.getEventVForProcessing(Event.STATUS_READY, eventType, maxRows, priority);
          } catch (java.rmi.RemoteException exc) {
              throw new EventHandlerException(exc.getMessage());
          }
      return eventDataV;
  }

  public ArrayList getThreadsStatistic() {
    ArrayList threadsStatistic = new ArrayList();
    Thread[] theThreads = new Thread[threadGroup.activeCount()];
    threadGroup.enumerate(theThreads);
    for (int i = 0; i < theThreads.length; i++) {
        try {
            if (statistic.get(theThreads[i].getId()) != null) {
                Statistic stat = new Statistic(theThreads[i].getId());
                stat.eventId = ((Times) statistic.get(theThreads[i].getId())).
                               eventId;
                long startAbsTime = ((Times) statistic.get(theThreads[i].getId())).
                                    startAbsTime;
                long startCpuTime = ((Times) statistic.get(theThreads[i].getId())).
                                    startCpuTime;
                ThreadInfo info = threadBean.getThreadInfo(theThreads[i].getId(),
                        Integer.MAX_VALUE);
                stat.name = info.getThreadName();
                stat.state = info.getThreadState().name();
                long currentAbsTime = System.nanoTime();
                long currentCpuTime = getCpuTime(theThreads[i].getId());
                stat.absTime = nf.format((currentAbsTime - startAbsTime) /
                                         1000000000.);
                stat.cpuTime = nfe.format((currentCpuTime - startCpuTime) /
                                          1000000000.);
                if (info.getLockOwnerId() != -1)
                    stat.ext.add("waiting on lock " + info.getLockName() +
                                 " owned by " + info.getLockOwnerId());
                if (info.getBlockedTime() != -1) {
                    stat.ext.add("blocked time: " +
                                 nfe.format(info.getBlockedTime() / 1000.) +
                                 " seconds during " +
                                 info.getBlockedCount() + " blocks");
                }
                stat.ext.add("stack trace:");
                for (int j = 0; j < info.getStackTrace().length; j++) {
                    StackTraceElement e = info.getStackTrace()[j];
                    stat.ext.add(e.getClassName() + "#" + e.getMethodName() + ":" + e.getLineNumber());
                }
                threadsStatistic.add(stat);
            }
        } catch (Throwable th) {
            log.error("Internal error", th);
        }
    }
    return threadsStatistic;
  }


  private void monitoring(long threadId) {
      long stackTraceTime = 300L; // in seconds
      if (statistic.get(threadId) != null) {
          int eventId = ((Times)statistic.get(threadId)).eventId;
          long startAbsTime = ((Times)statistic.get(threadId)).startAbsTime;
          long startCpuTime = ((Times)statistic.get(threadId)).startCpuTime;
          ThreadInfo info = threadBean.getThreadInfo(threadId, Integer.MAX_VALUE);
          log.info(info.getThreadName() + ", id=" + info.getThreadId() + ", eventId=" + eventId + ", state: " + info.getThreadState());
          long currentAbsTime = System.nanoTime();
          long currentCpuTime = getCpuTime(threadId);
          log.info("-----elapsed abs time " + ((currentAbsTime - startAbsTime)/1000000000) + " seconds");
          log.info("-----elapsed cpu time " + ((currentCpuTime - startCpuTime)/1000000000.) + " seconds");
          ((Times)statistic.get(threadId)).currentAbsTime = currentAbsTime;
          ((Times)statistic.get(threadId)).currentCpuTime = currentCpuTime;
          boolean stackTrace = false;
          long serviceTime = ((Times)statistic.get(threadId)).serviceTime;
          if (currentAbsTime - serviceTime  > stackTraceTime*1000000000) {
              stackTrace = true;
              ((Times)statistic.get(threadId)).serviceTime = currentAbsTime;

          }
          if (info.getLockOwnerId() != -1)
              log.info("-----waiting on lock " + info.getLockName() + " owned by " + info.getLockOwnerId());
          if (info.getBlockedTime() != -1) {
              log.info("-----blocked time: " + (info.getBlockedTime() / 1000.) + " seconds during " +
                                 info.getBlockedCount() + " blocks");
          }
          if (stackTrace) {
              log.info("-----stack trace:");
              for (int i = 0; i < info.getStackTrace().length; i++) {
                  StackTraceElement e = info.getStackTrace()[i];
                  log.info(e.getClassName() + "#" + e.getMethodName() + ":" + e.getLineNumber());
              }
          }
      }
  }

  /** Get CPU time in nanoseconds. */
  public long getCpuTime(long threadId) {
      return threadBean.isThreadCpuTimeSupported() ?
          threadBean.getThreadCpuTime(threadId) : System.nanoTime();
  }

  private class Times {
      public int eventId;
      /* time in nanoseconds */
      public long startAbsTime;
      public long startCpuTime;
      public long currentAbsTime;
      public long currentCpuTime;
      public long serviceTime;
      public Times(int eventId, long startAbsTime, long startCpuTime) {
          this.eventId = eventId;
          this.startAbsTime = startAbsTime;
          this.startCpuTime = startCpuTime;
          this.currentAbsTime = startAbsTime;
          this.currentCpuTime = startCpuTime;
          this.serviceTime = startAbsTime;
      }
  }

  public static class Statistic {
      public long threadId;
      public int eventId;
      public String name;
      public String state;
      public String absTime;
      public String cpuTime;
      public ArrayList ext = new ArrayList();
      public Statistic(long threadId) {
          this.threadId = threadId;
      }
      public long getThreadId() {
          return threadId;
      }
      public long getEventId() {
          return eventId;
      }
      public String getName() {
          return name;
      }
      public String getState() {
          return state;
      }
      public String getAbsTime() {
          return absTime;
      }
      public String getCpuTime() {
          return cpuTime;
      }
      public ArrayList getExt() {
          return ext;
      }
  }

}

