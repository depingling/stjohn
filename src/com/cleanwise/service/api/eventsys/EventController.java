package com.cleanwise.service.api.eventsys;

import org.apache.log4j.Logger;
import com.cleanwise.service.api.session.Event;

public class EventController extends Thread{
  public static final Logger log = Logger.getLogger(EventController.class);
  private EventData  eventData = null;
  private int num = 0;

  public EventController(ThreadGroup group, int num, EventData eventData) {
    super(group, group.getName() + "#" + num);
    this.eventData = eventData;
    this.num = num;
  }

  public void run() {
      try {
        log.info("run: " + this.getName() + ". start.");
        if (eventData != null) {
          runHandler(eventData);
        }
      } catch (Exception ex) {
        log.error(this.getName(), ex);
      } finally {
        log.info("run: " + this.getName() + ". stop.");
      }
    }
    private void runHandler(EventData eventData) throws EventHandlerException {
    try {
      log.info("EventController HHHHHHHHHHHHHHHHHHHH event type: " + eventData.getType());
      if (Event.TYPE_EMAIL.equals(eventData.getType())) {
        EmailEventHandler handler = new EmailEventHandler(eventData);
        handler.run();
      }
      if (Event.TYPE_DISTRIBUTOR_DELIVERY.equals(eventData.getType())) {
        DistributorDeliveryEventHandler handler = new DistributorDeliveryEventHandler(eventData);
        handler.run();
      }
      if (Event.TYPE_PROCESS.equals(eventData.getType())) {
        ProcessEventHandler handler = new ProcessEventHandler(eventData);
        handler.run();
      }
    }
    catch (Exception e) {
      throw new EventHandlerException(e.getMessage());
    }
  }

  public int getNum() {
    return this.num;
  }

  public int getEventId() {
      return this.eventData.getEventId();
  }
}
