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

public class FailMediatorPollock implements FailMediator {
	private static final Category log = Category.getInstance(FailMediatorPollock.class);
	String mReasonForFailure = null;
	
	public boolean shouldFail(EventData eD, int waitingOnEventId,
			String waitingOnEventStatus, Connection conn){
		mReasonForFailure = null;
		log.info("shouldFail called");
		boolean shouldFail = false;
		try{
			if (Event.STATUS_REJECTED.equals(waitingOnEventStatus)) {
				String fileName = getEventPropertyStringValue(eD.getEventId(),eD.getEventPropertyDataVector(),"fileName",conn);
				if(fileName == null){
					log.info("fileName was null");
					mReasonForFailure="fileName was null and previous event failed";
					//couldn't find a file name and previous event failed
					shouldFail = true;
				}else if(fileName.toLowerCase().contains("catalog")){
					log.info("fileName contains catalog");
					//file name was catalog keep going
					shouldFail = false;
				}else{
					log.info("fileName did not contain catalog");
					mReasonForFailure="fileName did not contain catalog and previous event failed";
					//file name was found but did not contain catalog, fail
					shouldFail = true;
				}
			}
		}catch(Exception e){
			mReasonForFailure = "Caught exception: "+e.getMessage();
			e.printStackTrace();
		}
		return shouldFail;
	}
	
	public String getReasonForFail(){
		return mReasonForFailure;
	}
	
	
	private String getEventPropertyStringValue(int eventId, EventPropertyDataVector epdv, String val,Connection conn)
	throws SQLException{
		if(epdv != null){
			Iterator<EventPropertyData> it = epdv.iterator();
			while(it.hasNext()){
				EventPropertyData epd = it.next();
				if(val.equals(epd.getShortDesc())){
					return epd.getStringVal();
				}
			}
		}else{
			DBCriteria crit = new DBCriteria();
	        crit.addEqualTo(EventPropertyDataAccess.EVENT_ID, eventId);
	        crit.addEqualTo(EventPropertyDataAccess.SHORT_DESC, val);
	        EventPropertyDataVector epvd = EventPropertyDataAccess.select(conn,crit);
	        if(epvd.size() >= 1){
	        	EventPropertyData epd = (EventPropertyData) epvd.get(0);
	    		return epd.getStringVal();
	        }
		}
		return null;
	}
}
