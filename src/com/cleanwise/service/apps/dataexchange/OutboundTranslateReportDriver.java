package com.cleanwise.service.apps.dataexchange;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;

import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.value.IntegrationRequestsVector;
import com.cleanwise.service.api.value.TradingPartnerDescView;
import com.cleanwise.service.api.value.TradingProfileConfigData;
import com.cleanwise.service.api.value.TradingProfileData;
import com.cleanwise.service.api.value.TradingPropertyMapDataVector;

/**
 * Report driver for the outbound translator.  Mostly contains sudo methods for various
 * methods that don't really do anything.  
 * @see OutboundTranslatorReport
 */
public class OutboundTranslateReportDriver extends OutboundTranslate{

	/**
	 * Overidden from the super class so that exception can be thrown.  There is no implementation under it.
	 * @throws Exception
	 */
	public OutboundTranslateReportDriver() throws Exception {
        super();
    }
	
	/**
	 * Overidden from the super class to set this data.
	 */
	public void setTradingPropertyMapDataVector(TradingPropertyMapDataVector pTradingPropertyMapDataVector){
		mTradingPropertyMapDataVector = pTradingPropertyMapDataVector;
	}
	
	  /**
	   *Output is handled totally differently for reports.  This method does nothing.  If implementing builders
	   *call this method (if for example they are trying to write multiple files out for each order) that functionality 
	   *will be lost.
	   */
	  public void openOutputStream() throws IOException {
	    //do nothing
	  }

	  /**
	   *Output is handled totally differently for reports.  This method does nothing.  If implementing builders
	   *call this method (if for example they are trying to write multiple files out for each order) that functionality 
	   *will be lost.
	   */
	  public void initializeOutputStream(OutboundTransaction pBuilder) throws Exception {
		  //do nothing
	  }
	
	 /**
	   * Stubbed method that uses the data that is existing
	   */
	  public TradingProfileConfigData getOutboundTradingProfileConfig(
	    String erpNum, int busEntityId, int incommingProfileId, String setType) throws
	    RemoteException, DataNotFoundException {

	    return getTradingPartnerDescView().getTradingProfileConfigData();
	    
	  }
	  
	  /**
	   * Stubbed method that uses the data that is existing
	   */
	  public TradingProfileData getTradingProfile(int profileId) throws
	    RemoteException, DataNotFoundException {
		return getTradingPartnerDescView().getTradingProfileData();
	    
	  }
	  
	  /**
	   * Stubbed method that does NOT process the update requests.  Method implemention just returns true.
	   */
	  protected boolean processIntegrationRequests(IntegrationRequestsVector reqs,String erpNum, InputStream workingInputStream)
		throws Exception {
		  return true;
	  }
}
