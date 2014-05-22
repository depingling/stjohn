/*
 * Translator.java
 *
 * Created on October 18, 2002, 5:43 PM
 */

package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.session.Process;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

import java.io.*;
import java.util.Date;
import java.util.Map;
import java.rmi.*;
import java.util.Iterator;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 *
 * @author  bstevens
 */
public abstract class Translator {
	protected Logger log = Logger.getLogger(this.getClass());
	String mClassName = this.getClass().getName();
	boolean interactive = true; //indicates we were called from some command line
	private static Event eventEjb = null;
	private static Process processEjb = null;
	private static Report reportEjb = null;
	private static PropertyService propertyEjb = null;
	private static Account accountEjb;
	protected static IntegrationServices intSvc;
	protected static TradingPartner partnerSvc;
	private TradingPartnerDescView tradingPartnerDescView;

	final static String FG850 = "PO";
	final static String FG855 = "PR";
	final static String FG856 = "SH";
	final static String FG810 = "IN";
	final static String FG997 = "FA";
	final static String FG824 = "AG";
	final static String FG867 = "SS";

	TradingProfileData profile;
	public TradingProfileData getProfile(){
		return profile;
	}
	public void setProfile(TradingProfileData pProfile) throws Exception{
		profile = pProfile;
	}
	TradingPartnerData partner;
	public TradingPartnerData getPartner(){
		return partner;
	}

	public void setPartner(TradingPartnerData pPartner){
		partner = pPartner;
	}

	/** Creates a new instance of Translator */
	protected Translator() throws Exception {
		intSvc= APIAccess.getAPIAccess().getIntegrationServicesAPI();
		partnerSvc = APIAccess.getAPIAccess().getTradingPartnerAPI();
	}

	private String mInputFilename = null;
	public String getInputFilename() {
		return mInputFilename;
	}
	public void setInputFilename(String pInputFilename) {
		mInputFilename = pInputFilename;
	}

	private String mPartnerKey = null;
	public String getPartnerKey() {
		return mPartnerKey;
	}
	public void setPartnerKey(String pPartnerKey) {
		mPartnerKey = pPartnerKey;
	}

	private String mOutputFileName; // generated output filename
	public String getOutputFileName() {
		return mOutputFileName;
	}
	public void setOutputFileName(String pOutputFileName) {
		if (mOutputFileName != pOutputFileName) {
			mOutputFileName = pOutputFileName;
		}
	}

	/**
	 * Marks the reference time for the download, used on anything that needs a timestamp,
	 */
	String mDateTimeAsString;
	public void setDateTimeAsString (String lDateTimeAsString) {
		mDateTimeAsString = lDateTimeAsString;
	}
	public String getDateTimeAsString () {
		return mDateTimeAsString;
	}

	/**
	 * Open the output streams and record the file objects in an
	 * internal Vector. Later, this vector will provide the names of
	 * the files to be deleted or archived.  <P> Here is the tricky
	 * part, these are merely names for the temporary files used in
	 * the process of creating the documents. If the files are later
	 * archived they will be named according to the official names
	 * created in GenerateOutputFilename, and those official names
	 * will also be used in the manifest of the jar file.
	 *
	 * @exception IOException
	 */
	protected ByteArrayOutputStream mOutputStream = null;
	public void openOutputStream() throws IOException {
		if(getOutputStream() != null){
			getOutputStream().flush();
			closeOutputStream();
		}

		setOutputStream(new ByteArrayOutputStream());
	}
	public void closeOutputStream()  {
		try{
			getOutputStream().flush();
			getOutputStream().close();
		}catch (IOException e){
			logError("Could not close output stream");
		}
	}

	/**
	 *Gets the current output stream
	 */
	public ByteArrayOutputStream getOutputStream() {
		return mOutputStream;
	}
	/**
	 *Sets the output stream
	 */
	public void setOutputStream(ByteArrayOutputStream pOutputStream){
		mOutputStream = pOutputStream;
	}

	/**
	 *Writes the string data to the current output stream.  This method preforms a flush
	 *when it is done.
	 */
	public void writeOutputStream(String pData) throws IOException{
		OutputStreamWriter pw = new OutputStreamWriter(getOutputStream(), "UTF-8");
		pw.write(pData);
		pw.flush();
		//log.info(getOutputStream().toString("UTF-8"));
	}

	public ByteArrayOutputStream getOutputResponseStream(String pSetType)throws IOException{
        try{
        	if(mOutputStream == null){
                log.info("OPENING NEW STREAM");
                OutboundTranslate outTrans = new OutboundTranslate();
                outTrans.setUp(null,0, pSetType, null);
                outTrans.initializeOutputStream(null);
                mOutputStream = outTrans.getOutputStream();
            }
            return mOutputStream;
        }catch(Exception e){
            e.printStackTrace();
            if(e instanceof IOException){
                throw (IOException)e;
            }else{
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        }
    }

	public void initOutputResponseStream(String pSetType)throws IOException{
        try{
            if(mOutputStream == null){
                log.info("OPENING NEW STREAM");
                OutboundTranslate outTrans = new OutboundTranslate();
                outTrans.setUp(null,0, pSetType, null);
                outTrans.initializeOutputStream(null);
                mOutputStream = outTrans.getOutputStream();
            }
        }catch(Exception e){
            e.printStackTrace();
            if(e instanceof IOException){
                throw (IOException)e;
            }else{
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        }
    }
	/**
	 * Marks the reference time for the download, used on anything
	 * that needs a timestamp, such as file names.
	 */
	private Date mTimestamp = null;
	public void setTimestamp(Date lTimestamp) {
		mTimestamp = lTimestamp;
	}
	public Date getTimestamp() {
		if (mTimestamp == null)
			mTimestamp = new Date();
		return mTimestamp;
	}

	/**
	 *Jwp's edi file maintains the GSControl number on a per account basis.
	 */
	public int getNextGSNumber(String erpAccountNumber) throws RemoteException{
		return intSvc.getNextGSNumber(erpAccountNumber);
	}

	/**
	 * Get a trading profile record from Trading_Profile table by match
	 * trading profile id
	 */
	public TradingProfileData getTradingProfile(int pTradingProfileId) throws RemoteException, DataNotFoundException
	{
		return (partnerSvc.getTradingProfile(pTradingProfileId));
	}

	/**
	 * Get a trading profile config data from Trading_Profile_Config table
	 * by matching trading profile id, set type and direction(in or out)
	 */
	public static TradingPartnerDescView getTradingPartnerDescView(
			int tradingProfileId, String setType, String direction)
	throws RemoteException
	{
		TradingPartnerDescView vw = intSvc.getTradingProfileConfig(tradingProfileId, setType, direction);
		return vw;
	}

	/**
     *Returns the TradingProfileConfigData for the currently running configuration
     */
    public TradingProfileConfigData getTradingProfileConfig(){
        if(tradingPartnerDescView == null){
            return null;
        }
        return tradingPartnerDescView.getTradingProfileConfigData();
    }

	public TradingProfileConfigData getTradingProfileConfig(
			int tradingProfileId, String setType, String direction)
	throws RemoteException
	{
		TradingPartnerDescView vw = getTradingPartnerDescView(tradingProfileId, setType, direction);
		if(vw != null){
			TradingProfileConfigData lProfileConfig = vw.getTradingProfileConfigData();
			return lProfileConfig;
		}
		return null;
	}
	public int[] getTradingPartnerBusEntityIds(
			int pTradingPartnerId, String pAssocCode)
	throws RemoteException
	{
		try{
			int[] busEntityIds =
				partnerSvc.getBusEntityIds(pTradingPartnerId,pAssocCode);
			return busEntityIds;
		}catch (Exception exc) {}
		return null;
	}
	
	public OrderData getOrderDataByErpPoNum(String erpPoNum) throws RemoteException {
		return getOrderDataByPoNum(erpPoNum, null, null, RefCodeNames.MATCH_PO_NUM_TYPE_CD.DEFAULT);
	}

	public OrderData getOrderDataByPoNum(String erpPoNum, String siteKey, String accountKey, String poNumType) throws RemoteException {

		OrderData order = null;
		
		// gets tradingPartnerId
		int tradingPartnerId = 0;
		if (this.profile != null) {
			tradingPartnerId = this.profile.getTradingPartnerId();
		} else if (this.partner != null) {
			tradingPartnerId = this.partner.getTradingPartnerId();
		} else if (this.tradingPartnerDescView != null) {
			if (this.tradingPartnerDescView.getTradingPartnerData() != null) {
				tradingPartnerId = this.tradingPartnerDescView.getTradingPartnerData().getTradingPartnerId();
			}
		}
		if (tradingPartnerId > 0) {
			order = intSvc.getOrderDataByPoNum(erpPoNum, tradingPartnerId, siteKey, accountKey, poNumType);
		} else {
			(new Exception("No store ids were specified")).printStackTrace();
		}
		return order;
	}


	public boolean processIntegrationRequests(IntegrationRequestsVector reqs,String erpNum)
	throws Exception {
		return processIntegrationRequests(reqs,erpNum, null);
	}


	protected boolean processIntegrationRequests(IntegrationRequestsVector reqs,String erpNum, InputStream workingInputStream)
	throws Exception {
		try{
			logInfo("  Translator.processIntegrationRequests: " +
					reqs.size() + " erpNum=" + erpNum );

			int tradingPartnerId=0;
			if(this.profile != null){
				tradingPartnerId = this.profile.getTradingPartnerId();
			}else if(this.partner != null){
				tradingPartnerId = this.partner.getTradingPartnerId();
			}else if(this.tradingPartnerDescView != null){
				if(this.tradingPartnerDescView.getTradingPartnerData() != null){
					tradingPartnerId = this.tradingPartnerDescView.getTradingPartnerData().getTradingPartnerId();
				}
			}
			
			for (Object req : reqs){
				if (this instanceof InboundTranslate){
					if (req instanceof OrderRequestData){
						OrderRequestData orderReq = (OrderRequestData)req;
						InboundTranslate translate = (InboundTranslate) this;
						orderReq.setParentEventId(translate.getEventId());
					}
					if (req instanceof InvoiceRequestData){
						InvoiceRequestData invoiceReq = (InvoiceRequestData)req;
						InboundTranslate translate = (InboundTranslate) this;
						invoiceReq.setParentEventId(translate.getEventId());
					}
					if (req instanceof InboundEventData){
						InboundEventData inbEventReq = (InboundEventData)req;
						InboundTranslate translate = (InboundTranslate) this;
						inbEventReq.setParentEventId(translate.getEventId());
					}
				}
				if (req instanceof OutboundEventData){
					OutboundEventData outEventReq = (OutboundEventData)req;
					if (this instanceof InboundTranslate){
						InboundTranslate translate = (InboundTranslate) this;
						outEventReq.setParentEventId(translate.getEventId());
					}else{
						OutboundTranslate translate = (OutboundTranslate) this;
						Integer parentEventId = ((Integer) translate.getSendParameterMap().get("pParentEventId"));  
						int id = parentEventId == null ? 0 : parentEventId.intValue();
						outEventReq.setParentEventId(id);
					}
				}
			}

			intSvc.processIntegrationRequests(reqs, erpNum, tradingPartnerId);
		}catch(Exception e){
			if(getInputFilename() != null){
				logError("ERROR Processing File: " + getInputFilename());
			}

			e.printStackTrace();
			throw e;
		}
		return true;
	}


	private String mErpNum;
	public String getErpNum () {
		return mErpNum;
	}
	public void setErpNum (String pErpNum) {
		if (mErpNum != pErpNum) {
			mErpNum = pErpNum;
		}
	}

	private int busEntityId;
	public int getBusEntityId(){return busEntityId;}
	public void setBusEntityId(int busEntityId){this.busEntityId = busEntityId;}


	/**
	 * Property GroupType is the functional identifier code, used
	 * in the group control header to indicate the type of transaction
	 * sets included.  The only value we know of is "PO".
	 */
	private String mGroupType; // = "PO";
	public String getGroupType () {
		return mGroupType;
	}
	public void setGroupType (String pGroupType) {
		if (mGroupType != pGroupType) {
			mGroupType = pGroupType;
		}
	}

	private String mSetType; // = "850";
	public String getSetType () {
		if (mSetType != null)
			return mSetType;
		else if (getTradingProfileConfig() != null)
			return getTradingProfileConfig().getSetType();
		else
			return null;
	}
	public void setSetType (String pSetType) {
		if (mSetType != pSetType) {
			mSetType = pSetType;
		}
	}

	/**
	 *Returns the mapping data for the currently running configuration
	 */
	/**
     *Returns the mapping data for the currently running configuration
     */
    public TradingPropertyMapDataVector getTradingPropertyMapDataVector(){
        return getTradingPartnerDescView().getTradingPropertyMapDataVector();
    }

	/**
	 *Returns the configuration property value
	 */
	public String getConfigurationProperty(String name){
		Iterator it = getTradingPropertyMapDataVector().iterator();
		while(it.hasNext()){
			TradingPropertyMapData data = (TradingPropertyMapData) it.next();
			if(name.equals(data.getEntityProperty())){
				return data.getHardValue();
			}
		}
		return null;
	}
	
	/**
	 *Returns the configuration property value
	 */
	public String getConfigPropertyByPropertyTypeCd(String propertyTypeCd){
		Iterator it = getTradingPropertyMapDataVector().iterator();
		while(it.hasNext()){
			TradingPropertyMapData data = (TradingPropertyMapData) it.next();
			if(propertyTypeCd.equals(data.getPropertyTypeCd())){
				return data.getHardValue();
			}
		}
		return null;
	}

	public void setTradingPartnerDescView(TradingPartnerDescView mTradingPartnerDescView) {
		this.tradingPartnerDescView = mTradingPartnerDescView;
		profile = mTradingPartnerDescView.getTradingProfileData();
		partner = mTradingPartnerDescView.getTradingPartnerData();
	}
	public TradingPartnerDescView getTradingPartnerDescView() {
		return tradingPartnerDescView;
	}

	public String getIntegrationEmailAddress() throws Exception{
        return intSvc.getIntegrationEmailAddress();
    }
	public static IntegrationServices getIntSvc() throws Exception {
		if (intSvc == null){
			intSvc = APIAccess.getAPIAccess().getIntegrationServicesAPI();
		}
		return intSvc;
	}

	public static TradingPartner getPartnerSvc() throws Exception {
		if (partnerSvc == null){
			partnerSvc = APIAccess.getAPIAccess().getTradingPartnerAPI();
		}
		return partnerSvc;
	}

	/** Get a reference to the Event Bean */
	public Event getEventEjb() throws Exception {
		if (eventEjb == null){
			eventEjb = APIAccess.getAPIAccess().getEventAPI();
		}
		return eventEjb;
	}

	/** Get a reference to the Process Bean */
	public static Process getProcessEjb() throws Exception {
		if (processEjb == null){
			processEjb = APIAccess.getAPIAccess().getProcessAPI();
		}
		return processEjb;
	}

	/** Get a reference to the Process Bean */
	public static Report getReportEjb() throws Exception {
		if (reportEjb == null){
			reportEjb = APIAccess.getAPIAccess().getReportAPI();
		}
		return reportEjb;
	}

	public PropertyService getPropertyServiceEjb() throws Exception {
		if (propertyEjb == null){
			propertyEjb = APIAccess.getAPIAccess().getPropertyServiceAPI();
		}
	     return propertyEjb;
	}

	public Account getAccountBean() throws Exception {
		if (accountEjb == null){
			accountEjb = APIAccess.getAPIAccess().getAccountAPI();
		}
	    return accountEjb;
	}

	public void logInfo(String pLogMsg) {
	    log.info("[Info, " + mClassName + "]    " + pLogMsg);
    }
	public void logError(String pLogMsg) {
	    log.error("[Error, " + mClassName + "]    " + pLogMsg);
    }


	private static String integrationFileLogDirectory = null;
	public static File getIntegrationFileLogDirectory(boolean inbound) throws Exception{
		if(integrationFileLogDirectory == null){
			//integrationFileLogDirectory = Utility.loadProperties("defst.default.properties").getProperty("integrationFileLogDirectory"); YK
			integrationFileLogDirectory = System.getProperty("integrationFileLogDirectory");
		}
		String integrationFileLogDirectoryScratch = integrationFileLogDirectory;
		if(inbound){
			integrationFileLogDirectoryScratch = integrationFileLogDirectoryScratch+"/inbound";
		}else{
			integrationFileLogDirectoryScratch = integrationFileLogDirectoryScratch+"/outbound";
		}
		File dir = new File(integrationFileLogDirectoryScratch);
		if(!dir.exists()){
			dir.mkdirs();
		}
		return dir;
	}
	
	/**
     * Gets the store id we are running for.
     * @return the store id
     * @throws Exception if some error happened, there are no stores associated, or there are more than one 
     * stores associated with this trading partner.
     */
    public int getStoreId() throws Exception{
		//Getting trading partner id
		TradingPartnerData partner = getPartner();
		if(partner == null) {
			throw new IllegalArgumentException("Trading Partner ID cann't be determined");
		}

		int tradingPartnerId = partner.getTradingPartnerId();
		Map assocMap = getPartnerSvc().getMapTradingPartnerAssocIds(tradingPartnerId);
		IdVector storeIds = (IdVector) assocMap.get(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
		if(storeIds == null || storeIds.size() < 1) {
			throw new IllegalArgumentException("No stores present for current trading partner id = " +
					tradingPartnerId);
		} else if(storeIds.size() > 1) {
			throw new Exception("Multiple stores present for current trading partner id = " +
					tradingPartnerId);
		}
		return ((Integer)storeIds.get(0)).intValue();				
    }
}
