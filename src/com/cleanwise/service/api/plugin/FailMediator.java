package com.cleanwise.service.api.plugin;


import java.sql.Connection;
import com.cleanwise.service.api.eventsys.EventData;

public interface FailMediator {
	public boolean shouldFail(EventData eD, int waitingOnEventId,String waitingOnEventStatus, Connection conn);
	public String getReasonForFail();
}
