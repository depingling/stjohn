package com.cleanwise.service.api.eventsys;

import org.jboss.system.ServiceMBean;

public interface EventServiceMBean extends ServiceMBean {
  void setJndiName(String jndiName) throws Exception;
  String getJndiName();
  void setThreadCount(int threadCount);
  void setInterval(int interval);
  void setPriorityLevel(int priorityLevel);
  void setExtraThreadCount(int extraThreadCount);
  void setStartEventSystem(boolean startEventService);
  void setEventType(String eventType);
}
