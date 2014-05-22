package com.cleanwise.service.apps.dataexchange;

import java.net.URI;
import com.cleanwise.service.apps.ClientServicesAPI;
import com.cleanwise.service.apps.dataexchange.Translator;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.framework.*;

/**
 *
 * @author Deping
 */
public abstract class InterchangeSuper extends ClientServicesAPI
{
	// constant to define the status of a transaction set
	public static int PEND_MSG = 0;
	public static int SEND_MSG = 1;
	public static int ACKA_MSG = 3;
	public static int ACKE_MSG = 4;
	public static int ACKR_MSG = 5;
	public static int RECA_MSG = 6;
	public static int RECE_MSG = 7;
	public static int RECR_MSG = 8;
	public static int RECN_MSG = 9;
	public static int NORT_MSG = 10;
	public static int DUPL_MSG = 11;
	public static String[] status = {"Transaction waits to be sent out", "Transaction has been created", " ",
		"Transaction has been acknowledge with accepted status", "Transaction has been acknowledge with accepted, but errors were noted status",
		"Transaction has been acknowledge with rejected status", "Transaction has been accepted",
		"Transaction has been received with acceptable error", "Transaction has been received with fatal error",
		"Transaction received is not supported", "Transaction received, Trading Partner profile not set up",
	"Duplicate 997 transaction"};

	protected Translator translator;
	private InterchangeRequestDataVector reqInterchanges = new InterchangeRequestDataVector();
	private IntegrationRequestsVector integrationRequests = new IntegrationRequestsVector();
	protected InterchangeData interchangeD;
	protected ElectronicTransactionData transactionD;
	private InterchangeRequestData reqInterchange;
	protected TradingProfileData profile;
	private String setType = null;	
	private String groupType = null;
	private boolean fail = false;
	protected Vector errorMsgs = new Vector(); // any parsing or validation error
		
	public ElectronicTransactionData getTransactionObject() {
		return transactionD;
	}

	public void setProfile(TradingProfileData pProfile){
		profile = pProfile;
	}
	public TradingProfileData getProfile(){
		return profile;
	}

	// account number or vendor number depend on partner type;
	public String getErpNum () {
		return translator.getErpNum();
	}
	/**
	 * Property GroupType is the functional identifier code, used
	 * in the group control header to indicate the type of transaction
	 * sets included.  The only value we know of is "PO".
	 */
	public String getGroupType () {
		String v = "";
		if ( null != groupType ) {
			v = groupType;
		} else {
			v = translator.getGroupType();
		}
		return v;
	}

	public void setGroupType (String v) {
		groupType = v;
	}

	public void  setSetType (String v) {
		setType = v;
	}

	public String getSetType () {
		if ( null != setType ) {
			return setType;
		}
		return translator.getSetType();
	}

	public InterchangeRequestDataVector getInterchanges() {
		return reqInterchanges;
	}

	public IntegrationRequestsVector getIntegrationRequests () {
		return integrationRequests;
	}
	public void appendIntegrationRequests (ArrayList pRequests) {
		integrationRequests.addAll(pRequests);
	}
	public void appendIntegrationRequest (ValueObject obj) {
		integrationRequests.add(obj);
	}

	// list of objects that need to be updated to database
	public IntegrationRequestsVector getRequestsToProcess(){
		IntegrationRequestsVector requests = new IntegrationRequestsVector();
		requests.addAll(getIntegrationRequests());
		translator.setErpNum(getErpNum());
		if (getInterchanges().size() > 0) {
			InterchangeRequestDataVector irDV = getInterchanges();
			requests.addAll(irDV);
		}
		return requests;
	}

	public boolean isFail() {
		return fail;
	}
	public void setFail(boolean pFail) {
		fail=pFail;
	}
	
	/**
	 * create ElectronicTransactionData record for current transaction
	 * @return ElectronicTransactionData
	 */
	public ElectronicTransactionData createTransactionObject() {
		transactionD = ElectronicTransactionData.createValue();
		transactionD.setGroupType(getGroupType());
		transactionD.setGroupSender(profile.getGroupSender());
		transactionD.setGroupReceiver(profile.getGroupReceiver());
		transactionD.setGroupControlNumber(profile.getGroupControlNum());
		transactionD.setSetType(getSetType());
		reqInterchange.addTransaction(transactionD);
		return transactionD;
	}

	/**
	 * Set properties value for interchange object
	 */
	public InterchangeData createInterchangeObject()
	{
		if (profile == null)
			profile = translator.getProfile();
		interchangeD = InterchangeData.createValue();
		interchangeD.setTradingProfileId(profile.getTradingProfileId());
		interchangeD.setInterchangeSender(profile.getInterchangeSender());
		interchangeD.setInterchangeReceiver(profile.getInterchangeReceiver());
		interchangeD.setInterchangeControlNum(profile.getInterchangeControlNum());
		interchangeD.setTestInd(profile.getTestIndicator());
		if (this instanceof InboundTransaction)
		{
			interchangeD.setInterchangeType("INBOUND");
			interchangeD.setEdiFileName(translator.getInputFilename());
		}
		else
		{
			interchangeD.setInterchangeType("OUTBOUND");
			interchangeD.setEdiFileName(translator.getOutputFileName());
		}

		reqInterchange = InterchangeRequestData.createValue();
		reqInterchange.setInterchangeData(interchangeD);
		getInterchanges().add(reqInterchange);

		return interchangeD;
	}	
	
	public void setTranslator(Translator translator) {
		this.translator = translator;
	}

	public Translator getTranslator() {
		return translator;
	}	
	
	/**
     * Get a trading profile record from Trading_Profile table by match group sender id
     * with incoming document group receiver id and group receiver id with incomming
     * group sender id.
     */
    public TradingProfileData getTradingProfile(String groupSender, String groupReceiver, String direction, String setType) 
    throws Exception {
        TradingProfileData lProfile;
        try{
            lProfile = Translator.getPartnerSvc().getTradingProfileByGroupSenderAndReceiver(groupSender, groupReceiver, direction,setType, profile.getTestIndicator());
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
        
        return lProfile;
    }
    
    public TradingProfileData getTradingProfile(HashMap toDomainIds, HashMap fromDomainIds, String direction, String setType) 
    throws Exception {
    	TradingProfileData lProfile;
        try{
        	lProfile = Translator.getPartnerSvc().getTradingProfileByDomainIdentifiers(toDomainIds,
        			fromDomainIds,direction,setType, profile.getTestIndicator());
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
        
        return lProfile;
    }
    
    public InboundEventData generateInboundEvent(String fileName, byte[] byteArray){    	
    	return InboundEventData.generateInboundEvent(fileName, byteArray, null);
	}
	
    public InboundEventData generateInboundEvent(String fileName, byte[] byteArray, URI sourceURI){
		InboundEventData eventData = InboundEventData.generateInboundEvent(fileName, byteArray, translator.getProfile().getGroupReceiver()+"", sourceURI);
		appendIntegrationRequest(eventData);
		return eventData;
	}

	public void setReqInterchange(InterchangeRequestData reqInterchange) {
		this.reqInterchange = reqInterchange;
	}

	public InterchangeRequestData getReqInterchange() {
		return reqInterchange;
	}
	
	// return list of error message that store in a vector
	  public Vector getErrorMsgs()
	  {
	    return errorMsgs;
	  }
	  
	  String mFormatedErrorMsgs = "";
	  public String getFormatedErrorMsgs ()
	  {
	    if (errorMsgs.size() == 0)
	      return "";

	    StringBuilder msg = new StringBuilder(errorMsgs.size() * 100);
	    for (int i = 0; i < errorMsgs.size(); i++)
	      msg.append("**** " + errorMsgs.get(i) + "\r\n");
	    return msg.toString();
	  }
	    
	  public void appendErrorMsgs(String errorMessage){
		  errorMsgs.add(errorMessage);
	  }
	  
	  public void appendErrorMsgs(Exception e){
		  appendErrorMsgs(e.getMessage());
	  }
	  
	  public IdVector getBusEntitysByDBCrit(DBCriteria crit) throws Exception {
		  Connection conn = this.getConnection();
		  
		  try{
			  IdVector busEntityIds = BusEntityDataAccess.selectIdOnly(conn,crit);
			  return busEntityIds;
		  } catch (Exception ex) {
			  throw new Exception(ex);
		  } finally {
			  closeConnection(conn);
		  }
		  
	  }
	  
	  public int getBusEntityIdByEntityTypeAndErpNum(String entityType, String erpNum)
	  throws Exception {
		  DBCriteria crit = new DBCriteria();
		  crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, entityType);
		  crit.addEqualTo(BusEntityDataAccess.ERP_NUM, erpNum);    	
		  IdVector busEntityIds = getBusEntitysByDBCrit(crit);
		  int busEntityId = ((Integer)busEntityIds.get(0)).intValue();
		  return busEntityId;
	  }
}
