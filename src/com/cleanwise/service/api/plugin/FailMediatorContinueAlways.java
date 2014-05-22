package com.cleanwise.service.api.plugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import org.apache.log4j.Category;

import com.cleanwise.service.api.dao.EventPropertyDataAccess;
import com.cleanwise.service.api.eventsys.EventData;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.value.EventPropertyData;
import com.cleanwise.service.api.value.EventPropertyDataVector;

public class FailMediatorContinueAlways implements FailMediator {
	private static final Category log = Category.getInstance(FailMediatorContinueAlways.class);
	String mReasonForFailure = "Should never be called";
	
	public boolean shouldFail(EventData eD, int waitingOnEventId,
			String waitingOnEventStatus, Connection conn){
		log.info("shouldFail called");
		return false;
	}
	
	public String getReasonForFail(){
		return mReasonForFailure;
	}
	
	
}
