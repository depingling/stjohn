/**
 *  Title: TradingPartnerMgrForm Description: This is the Struts ActionForm
 *  class for tranding partner UI service
 *  Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     Yuriy Kupershmidt
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.Utility;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 *  Form bean for the catalog manager configuration page.
 *
 *@author     tbesser
 *@created    October 14, 2001
 */
public final class TradingPartnerMgrForm extends ActionForm {
    //Control
    private String _contentPage = "";
    //Search parameters
    private int _searchType = SearchCriteria.BEGINS_WITH_IGNORE_CASE;
    private String _searchBusEntityId = "";
    private String _searchBusEntityName = "";
    private String _searchPartnerId = "";
    private String _searchPartnerName = "";
    private String _searchPartnerType = "";
    private String _searchTradingType = "";
    private String _searchPartnerStatus = "";
    private String _searchTraidingTypeCD = "";
    private TradingPartnerViewVector _tradingPartners = null;

    //Trading partner
    private TradingPartnerData _tradingPartner = TradingPartnerData.createValue();
    private String _newBusEntityIdString = "";
    private String _newBusEntityDesc = "";
    private BusEntityDataVector _busEntities = new BusEntityDataVector();
    private TradingPartnerAssocDataVector _tradingPartnerAssocies = new TradingPartnerAssocDataVector();
    private String _busEntityIdToDelete = "";

    //Trading profile
    private TradingProfileData _tradingProfile = TradingProfileData.createValue();
    //   private String _newPassword = "";
    private String _segmentTerminator = "";
    private String _elementTerminator = "";
    private String _subElementTerminator = "";
    private String _acknowledgment = "";
    private String[] _timeZoneIDs = null;
    private int []  _selectedPropertyMappingIds=new int[0];
    private FormFile _importFile = null;

    public int[] getSelectedPropertyMappingIds() {
        return _selectedPropertyMappingIds;
    }
    public void setSelectedPropertyMappingIds(int[] selectedPropertyMappingIds) {
        this._selectedPropertyMappingIds = selectedPropertyMappingIds;
    }

   //Control
   //   private String _contentPage = "";
    public void setContentPage(String pValue) {_contentPage = pValue;}
    public String getContentPage(){return _contentPage;}

    //Search
    public void setSearchType(int pValue) {_searchType = pValue;}
    public int getSearchType(){return _searchType;}

    public void setSearchPartnerType(String pValue) {_searchPartnerType = pValue;}
    public String getSearchPartnerType(){return _searchPartnerType;}

    public void setSearchBusEntityId(String pValue) {_searchBusEntityId = pValue;}
    public String getSearchBusEntityId(){return _searchBusEntityId;}

    public void setSearchBusEntityName(String pValue) {_searchBusEntityName = pValue;}
    public String getSearchBusEntityName(){return _searchBusEntityName;}

    public void setSearchPartnerId(String pValue) {_searchPartnerId = pValue;}
    public String getSearchPartnerId(){return _searchPartnerId;}

    public void setSearchPartnerName(String pValue) {_searchPartnerName = pValue;}
    public String getSearchPartnerName(){return _searchPartnerName;}

    public void setSearchTradingType(String pValue) {_searchTradingType = pValue;}
    public String getSearchTradingType(){return _searchTradingType;}

    public void setSearchPartnerStatus(String pValue) {_searchPartnerStatus = pValue;}
    public String getSearchPartnerStatus(){return _searchPartnerStatus;}
    
    public void setSearchTraidingTypeCD(String pValue) {_searchTraidingTypeCD = pValue;}
    public String getSearchTraidingTypeCD(){return _searchTraidingTypeCD;}    

    public void setAcknowledgment(String pValue) {_acknowledgment = pValue;}
    public String getAcknowledgment(){return _acknowledgment;}

    public void setTradingPartners(TradingPartnerViewVector pValue) {_tradingPartners = pValue;}
    public TradingPartnerViewVector getTradingPartners(){return _tradingPartners;}

    //trading partner

/*

   public void set..(** pValue) {_.. = pValue;}
   public ** get..(){return _..;}
 */

    //Trading partner paramenter
    public void setTradingPartner(TradingPartnerData pValue) {_tradingPartner = pValue;}
    public TradingPartnerData getTradingPartner(){return _tradingPartner;}

    public void setNewBusEntityIdString(String pValue) {_newBusEntityIdString = pValue;}
    public String getNewBusEntityIdString(){return _newBusEntityIdString;}

    public void setNewBusEntityDesc(String pValue) {_newBusEntityDesc = pValue;}
    public String getNewBusEntityDesc(){return _newBusEntityDesc;}

    public void setBusEntities(BusEntityDataVector pValue) {_busEntities = pValue;}
    public BusEntityDataVector getBusEntities(){return _busEntities;}

    public void setTPAssocies(TradingPartnerAssocDataVector pValue) {_tradingPartnerAssocies = pValue;}
    public TradingPartnerAssocDataVector getTPAssocies() {return _tradingPartnerAssocies;}

    public void setBusEntityIdToDelete(String pValue) {_busEntityIdToDelete = pValue;}
    public String getBusEntityIdToDelete(){return _busEntityIdToDelete;}


    //Trading profile
    public void setTradingProfile(TradingProfileData pValue) {_tradingProfile = pValue;}
    public TradingProfileData getTradingProfile(){return _tradingProfile;}

    //   public void setNewPassword(String pValue) {_newPassword = pValue;}
    //   public String getNewPassword(){return _newPassword;}

    //   private String _segmentTerminator = "";
    public void setSegmentTerminator(String pValue) {_segmentTerminator = pValue;}
    public String getSegmentTerminator(){return _segmentTerminator;}

    //   private String _elementTerminator = "";
    public void setElementTerminator(String pValue) {_elementTerminator = pValue;}
    public String getElementTerminator(){return _elementTerminator;}

    //   private String _subElementTerminator = "";
    public void setSubElementTerminator(String pValue) {_subElementTerminator = pValue;}
    public String getSubElementTerminator(){return _subElementTerminator;}

    //   private ArrayList _timeZoneIDs = null;
    public void setTimeZoneIDs(String [] pValue) {_timeZoneIDs = pValue;}
    public String[] getTimeZoneIDs(){return _timeZoneIDs;}
    boolean CheckUOM;

    /**
     * Get the value of CheckUOM.
     * @return value of CheckUOM.
     */
    public boolean isCheckUOM() {
        return CheckUOM;
    }

    /**
     * Set the value of CheckUOM.
     * @param v  Value to assign to CheckUOM.
     */
    public void setCheckUOM(boolean  v) {
        this.CheckUOM = v;
    }
    boolean CheckAddress;

    /**
     * Get the value of CheckAddress.
     * @return value of CheckAddress.
     */
    public boolean isCheckAddress() {
        return CheckAddress;
    }

    /**
     * Set the value of CheckAddress.
     * @param v  Value to assign to CheckAddress.
     */
    public void setCheckAddress(boolean  v) {
        this.CheckAddress = v;
    }

    boolean ValidateRefOrderNum;

    /**
     * Get the value of ValidateRefOrderNum.
     * @return value of ValidateRefOrderNum.
     */
    public boolean isValidateRefOrderNum() {
        return ValidateRefOrderNum;
    }

    /**
     * Set the value of ValidateRefOrderNum.
     * @param v  Value to assign to ValidateRefOrderNum.
     */
    public void setValidateRefOrderNum(boolean  v) {
        this.ValidateRefOrderNum = v;
    }
    boolean ValidateCustomerSkuNum;

    /**
     * Get the value of ValidateCustomerSkuNum.
     * @return value of ValidateCustomerSkuNum.
     */
    public boolean isValidateCustomerSkuNum() {
        return ValidateCustomerSkuNum;
    }

    /**
     * Set the value of ValidateCustomerSkuNum.
     * @param v  Value to assign to ValidateCustomerSkuNum.
     */
    public void setValidateCustomerSkuNum(boolean  v) {
        this.ValidateCustomerSkuNum = v;
    }
    boolean ValidateCustomerItemDesc;

    /**
     * Get the value of ValidateCustomerItemDesc.
     * @return value of ValidateCustomerItemDesc.
     */
    public boolean isValidateCustomerItemDesc() {
        return ValidateCustomerItemDesc;
    }


    boolean allow856Flag;

    public void setAllow856Flag(boolean v) {
        this.allow856Flag = v;
    }

    public boolean getAllow856Flag() {
        return this.allow856Flag;
    }

    String allow856Email;

    public void setAllow856Email(String v) {
        this.allow856Email = v;
    }

    public String getAllow856Email() {
        return this.allow856Email;
    }


    /**
     * Set the value of ValidateCustomerItemDesc.
     * @param v  Value to assign to ValidateCustomerItemDesc.
     */
    public void setValidateCustomerItemDesc(boolean  v) {
        this.ValidateCustomerItemDesc = v;
    }

    private void setFlagsToFalse() {
        ValidateCustomerItemDesc = false;
        ValidateCustomerSkuNum = false;
        ValidateRefOrderNum = false;
        CheckAddress = false;
        CheckUOM = false;
        ProcessInvoiceCredit = false;
        validateContractPrice = false;
        initializeExistingPos = null;
        allow856Flag = false;        
        useInboundAmountForCostAndPrice = false;
        usePoLineNumForInvoiceMatch = false;
        relaxValidateInboundDuplInvoiceNum = false;
        
        setDataExchangeTransactionType(null);
        setDataExchangeClassName(null);
        setDataExchangeDirection(null);
        setDataExchangeInProfileId(null);
        setDataExchangeOutProfileId(null);
        setDataExchangePattern(null);
        setDataExchangeTransactionType(null);
        setDataExchangeDirection(null);
        setAllow856Email(null);
    }

    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        setFlagsToFalse();
        _newBusEntityIdString = "";
        _newBusEntityDesc = "";
        _selectedPropertyMappingIds=new int[0];
    }


    /**
     *  <code>validate</code> method is a stub.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     *@return          an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        // No validation necessary.
        return null;
    }




    Integer InProfileId;

    /**
     * Get the value of InProfileId.
     * @return value of InProfileId.
     */
    public Integer getDataExchangeInProfileId() {
        return InProfileId;
    }

    /**
     * Set the value of InProfileId.
     * @param v  Value to assign to InProfileId.
     */
    public void setDataExchangeInProfileId(Integer  v) {
        this.InProfileId = v;
    }
    Integer OutProfileId;

    /**
     * Get the value of OutProfileId.
     * @return value of OutProfileId.
     */
    public Integer getDataExchangeOutProfileId() {
        return OutProfileId;
    }

    /**
     * Set the value of OutProfileId.
     * @param v  Value to assign to OutProfileId.
     */
    public void setDataExchangeOutProfileId(Integer  v) {
        this.OutProfileId = v;
    }



    /** Holds value of property tradingPartnerInfo. */
    private TradingPartnerInfo tradingPartnerInfo;

    /** Holds value of property contactName. */
    private String toContactName;

    /** Holds value of property fromContactName. */
    private String fromContactName = "CRCFAX";

    /** Holds value of property purchaseOrderFaxNumber. */
    private String purchaseOrderFaxNumber;
    
    /** Holds value of property emailFrom. */
    private String emailFrom;    
    
    /** Holds value of property emailTo. */
    private String emailTo;     
    
    /** Holds value of property emailSubject. */
    private String emailSubject;     
    
    /** Holds value of property emailBodyTemplate. */
    private String emailBodyTemplate;     

    /** Holds value of property purchaseOrderFreightTerms. */
    private String purchaseOrderFreightTerms;

    /** Holds value of property purchaseOrderDueDays. */
    private String purchaseOrderDueDays;

    /** Getter for property tradingPartnerInfo.
     * @return Value of property tradingPartnerInfo.
     */
    public TradingPartnerInfo getTradingPartnerInfo() {
        return this.tradingPartnerInfo;
    }

    /** Setter for property tradingPartnerInfo.
     * @param tradingPartnerInfo New value of property tradingPartnerInfo.
     */
    public void setTradingPartnerInfo(TradingPartnerInfo tradingPartnerInfo) {
        this.tradingPartnerInfo = tradingPartnerInfo;
    }

    /** Getter for property contactName.
     * @return Value of property contactName.
     */
    public String getToContactName() {
        return this.toContactName;
    }

    /** Setter for property contactName.
     * @param contactName New value of property contactName.
     */
    public void setToContactName(String toContactName) {
        this.toContactName = toContactName;
    }

    /** Getter for property fromContactName.
     * @return Value of property fromContactName.
     */
    public String getFromContactName() {
        return this.fromContactName;
    }

    /** Setter for property fromContactName.
     * @param fromContactName New value of property fromContactName.
     */
    public void setFromContactName(String fromContactName) {
        this.fromContactName = fromContactName;
    }

    /** Getter for property purchaseOrderFaxNumber.
     * @return Value of property purchaseOrderFaxNumber.
     */
    public String getPurchaseOrderFaxNumber() {
        return this.purchaseOrderFaxNumber;
    }
    
    /** Getter for property emailFrom.
     * @return Value of property emailFrom.
     */
    public String getEmailFrom() {
        return this.emailFrom;
    }    
    
    /** Getter for property emailTo.
     * @return Value of property emailTo.
     */
    public String getEmailTo() {
        return this.emailTo;
    }    
    
    /** Getter for property emailSubject.
     * @return Value of property emailSubject.
     */
    public String getEmailSubject() {
        return this.emailSubject;
    }     
    
    /** Getter for property emailBodyTemplate.
     * @return Value of property emailBodyTemplate.
     */    
    public String getEmailBodyTemplate() {
        return this.emailBodyTemplate;
    }    

    /** Setter for property emailFrom.
     * @param V New value of property emailFrom.
     */
    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }
    
    /** Setter for property emailTo.
     * @param V New value of property C.
     */
    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }    
    
    /** Setter for property emailSubject.
     * @param V New value of property emailSubject.
     */
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }      
    
    /** Setter for property emailBodyTemplate.
     * @param V New value of property emailBodyTemplate.
     */
    public void setEmailBodyTemplate(String emailBodyTemplate) {
        this.emailBodyTemplate = emailBodyTemplate;
    }     
    
    /** Setter for property purchaseOrderFaxNumber.
     * @param purchaseOrderFaxNumber New value of property purchaseOrderFaxNumber.
     */
    public void setPurchaseOrderFaxNumber(String purchaseOrderFaxNumber) {
        this.purchaseOrderFaxNumber = purchaseOrderFaxNumber;
    }    

    /** Getter for property purchaseOrderFreightTerms.
     * @return Value of property purchaseOrderFreightTerms.
     */
    public String getPurchaseOrderFreightTerms() {
        return this.purchaseOrderFreightTerms;
    }

    /** Setter for property purchaseOrderFreightTerms.
     * @param purchaseOrderFreightTerms New value of property purchaseOrderFreightTerms.
     */
    public void setPurchaseOrderFreightTerms(String purchaseOrderFreightTerms) {
        this.purchaseOrderFreightTerms = purchaseOrderFreightTerms;
    }

    /** Getter for property purchaseOrderDueDays.
     * @return Value of property purchaseOrderDueDays.
     */
    public String getPurchaseOrderDueDays() {
        return this.purchaseOrderDueDays;
    }

    /** Setter for property purchaseOrderDueDays.
     * @param purchaseOrderDueDays New value of property purchaseOrderDueDays.
     */
    public void setPurchaseOrderDueDays(String purchaseOrderDueDays) {
        this.purchaseOrderDueDays = purchaseOrderDueDays;
    }

    boolean ProcessInvoiceCredit;

    /** Holds value of property initializeExistingPos. */
    private String initializeExistingPos;

    /** Holds value of property tradingPropertyMapDataVector. */
    private List tradingPropertyMapDataVector = new ArrayList();

    /** Holds value of property validateContractPrice. */
    private boolean validateContractPrice;
    
    /** Holds value of property validateContractPrice. */
    private boolean useInboundAmountForCostAndPrice;
    
    /** Holds value of property usePoLineNumForInvoiceMatch. */
    private boolean usePoLineNumForInvoiceMatch;
    
    /** Holds value of property relaxValidateInboundDuplInvoiceNum. */
    private boolean relaxValidateInboundDuplInvoiceNum;

    

    /**
     * Holds value of property dataExchangeClassName.
     */
    private String dataExchangeClassName;

    /**
     * Holds value of property dataExchangeDirection.
     */
    private String dataExchangeDirection;

    /**
     * Holds value of property dataExchangeTransactionType.
     */
    private String dataExchangeTransactionType;

    /**
     * Holds value of property dataExchangePattern.
     */
    private String dataExchangePattern;

    /**
     * Holds value of property tradingDataExchanges.
     */
    private TradingProfileConfigDataVector tradingDataExchanges;

    /**
     * Get the value of processInvoiceCredit.
     * @return value of processInvoiceCredit.
     */
    public boolean isProcessInvoiceCredit() {
        return ProcessInvoiceCredit;
    }

    /**
     * Set the value of processInvoiceCredit.
     * @param v  Value to assign to processInvoiceCredit.
     */
    public void setProcessInvoiceCredit(boolean  v) {
        this.ProcessInvoiceCredit = v;
    }

    /** Getter for property initializeExistingPos.
     * @return Value of property initializeExistingPos.
     *
     */
    public String getInitializeExistingPos() {
        return this.initializeExistingPos;
    }

    /** Setter for property initializeExistingPos.
     * @param initializeExistingPos New value of property initializeExistingPos.
     *
     */
    public void setInitializeExistingPos(String initializeExistingPos) {
        this.initializeExistingPos = initializeExistingPos;
    }

    /** Getter for property tradingPropertyMapDataVector.
     * @return Value of property tradingPropertyMapDataVector.
     *
     */
    public List getTradingPropertyMapDataVector() {
        return this.tradingPropertyMapDataVector;
    }

    /** Setter for property tradingPropertyMapDataVector.
     * @param tradingPropertyMapDataVector New value of property tradingPropertyMapDataVector.
     *
     */
    public void setTradingPropertyMapDataVector(List tradingPropertyMapDataVector) {
        this.tradingPropertyMapDataVector = tradingPropertyMapDataVector;
    }


    public TradingPropertyMapDataVector getTradingPropertyMappings(){
        TradingPropertyMapDataVector toReturn = new TradingPropertyMapDataVector();
        Iterator it = tradingPropertyMapDataVector.iterator();
        while(it.hasNext()){
            TradingPropertyMapDescData desc = (TradingPropertyMapDescData) it.next();
            toReturn.add(desc.getTradingPropertyMapData());
        }
        return toReturn;
    }

    public void setTradingPropertyMappings(TradingPropertyMapDataVector pTradingPropertyMapDataVector){
        getTradingPropertyMapDataVector().clear();
        Iterator it = pTradingPropertyMapDataVector.iterator();
        while(it.hasNext()){
            TradingPropertyMapData data = (TradingPropertyMapData) it.next();
            addTradingPropertyMapping(data);
        }
    }

    public void setTradingPropertyMapData(int indx,TradingPropertyMapDescData desc) {
        int len = tradingPropertyMapDataVector.size();
        while(len <= indx){
            addTradingPropertyMapping(TradingPropertyMapData.createValue());
        }
        tradingPropertyMapDataVector.add(indx,desc);
    }

    public TradingPropertyMapDescData getTradingPropertyMapData(int indx) {
        if(indx > tradingPropertyMapDataVector.size()){
            return null;
        }else{
            return (TradingPropertyMapDescData) tradingPropertyMapDataVector.get(indx);
        }
    }

    /** Getter for property validateContractPrice.
     * @return Value of property validateContractPrice.
     *
     */
    public boolean isValidateContractPrice() {
        return this.validateContractPrice;
    }

    /** Setter for property validateContractPrice.
     * @param validateContractPrice New value of property validateContractPrice.
     *
     */
    public void setValidateContractPrice(boolean validateContractPrice) {
        this.validateContractPrice = validateContractPrice;
    }

    /**
     * Getter for property dataExchangeClassName.
     * @return Value of property dataExchangeClassName.
     */
    public String getDataExchangeClassName() {

        return this.dataExchangeClassName;
    }

    /**
     * Setter for property dataExchangeClassName.
     * @param dataExchangeClassName New value of property dataExchangeClassName.
     */
    public void setDataExchangeClassName(String dataExchangeClassName) {

        this.dataExchangeClassName = dataExchangeClassName;
    }

    /**
     * Getter for property dataExchangeDirection.
     * @return Value of property dataExchangeDirection.
     */
    public String getDataExchangeDirection() {

        return this.dataExchangeDirection;
    }

    /**
     * Setter for property dataExchangeDirection.
     * @param dataExchangeDirection New value of property dataExchangeDirection.
     */
    public void setDataExchangeDirection(String dataExchangeDirection) {

        this.dataExchangeDirection = dataExchangeDirection;
    }

    /**
     * Getter for property dataExchangeTransactionType.
     * @return Value of property dataExchangeTransactionType.
     */
    public String getDataExchangeTransactionType() {

        return this.dataExchangeTransactionType;
    }

    /**
     * Setter for property dataExchangeTransactionType.
     * @param dataExchangeTransactionType New value of property dataExchangeTransactionType.
     */
    public void setDataExchangeTransactionType(String dataExchangeTransactionType) {

        this.dataExchangeTransactionType = dataExchangeTransactionType;
    }

    /**
     * Getter for property dataExchangePattern.
     * @return Value of property dataExchangePattern.
     */
    public String getDataExchangePattern() {

        return this.dataExchangePattern;
    }

    /**
     * Setter for property dataExchangePattern.
     * @param dataExchangePattern New value of property dataExchangePattern.
     */
    public void setDataExchangePattern(String dataExchangePattern) {

        this.dataExchangePattern = dataExchangePattern;
    }

    /**
     * Getter for property tradingDataExchanges.
     * @return Value of property tradingDataExchanges.
     */
    public TradingProfileConfigDataVector getTradingDataExchanges() {

        return this.tradingDataExchanges;
    }

    /**
     * Setter for property tradingDataExchanges.
     * @param tradingDataExchanges New value of property tradingDataExchanges.
     */
    public void setTradingDataExchanges(TradingProfileConfigDataVector tradingDataExchanges) {

        this.tradingDataExchanges = tradingDataExchanges;
    }


    public void addTradingPropertyMapping(TradingPropertyMapData data){
        if(data != null){
            getTradingPropertyMapDataVector().add(new TradingPropertyMapDescData(data));
        }
    }

    public void setImportFile(FormFile _importFile) {
		this._importFile = _importFile;
	}
	public FormFile getImportFile() {
		return _importFile;
	}

	public void setUseInboundAmountForCostAndPrice(boolean useInboundAmountForCostAndPrice) {
		this.useInboundAmountForCostAndPrice = useInboundAmountForCostAndPrice;
	}
	public boolean isUseInboundAmountForCostAndPrice() {
		return useInboundAmountForCostAndPrice;
	}

	public void setUsePoLineNumForInvoiceMatch(boolean usePoLineNumForInvoiceMatch) {
		this.usePoLineNumForInvoiceMatch = usePoLineNumForInvoiceMatch;
	}
	public boolean isUsePoLineNumForInvoiceMatch() {
		return usePoLineNumForInvoiceMatch;
	}
	
	public boolean isRelaxValidateInboundDuplInvoiceNum() {
    	return relaxValidateInboundDuplInvoiceNum;
    }

    public void setRelaxValidateInboundDuplInvoiceNum(boolean  v) {
    	this.relaxValidateInboundDuplInvoiceNum = v;
    }


	/**
     *Describes the configuration of the trading property map data.  Deals with setting
     *the compound key itself based off the config data that was passed in.
     */
    public class TradingPropertyMapDescData{
        private TradingProfileConfigData configData;
        public TradingPropertyMapDescData(TradingPropertyMapData src){

            setTradingPropertyMapData(src);
            if(src == null){
                return;
            }
            //find the tradingConfig data based off this map.  If there is not one
            //then we will not set the id, this is still valid.
            Iterator it = getTradingDataExchanges().iterator();
            while(it.hasNext()){
                TradingProfileConfigData config = (TradingProfileConfigData) it.next();
                if(config != null &&
                        isEqual(src.getSetType(),config.getSetType()) &&
                        isEqual(src.getDirection(), config.getDirection()) &&
                        isEqual(src.getPattern(), config.getPattern())
                        ){
                    this.tradingConfigId = Integer.toString(config.getTradingProfileConfigId());
                    this.configData = config;
                }
            }

        }

        private boolean isEqual(Object o, Object o2){
            if(o == null && o2 == null){
                return true;
            }
            if(o==null){
                return false;
            }
            return o.equals(o2);
        }

        private TradingPropertyMapData tradingPropertyMapData;
        private String tradingConfigId;

        /**
         * Getter for property tradingPropertyMapData.
         * @return Value of property tradingPropertyMapData.
         */
        public TradingPropertyMapData getTradingPropertyMapData() {

            return this.tradingPropertyMapData;
        }

        /**
         * Setter for property tradingPropertyMapData.
         * @param tradingPropertyMapData New value of property tradingPropertyMapData.
         */
        public void setTradingPropertyMapData(TradingPropertyMapData tradingPropertyMapData) {

            this.tradingPropertyMapData = tradingPropertyMapData;
        }

        /**
         * Getter for property tradingConfigId.
         * @return Value of property tradingConfigId.
         */
        public String getTradingConfigId() {

            return this.tradingConfigId;
        }

        /**
         * Getter for property tradingConfigId.
         * @return Value of property tradingConfigId.
         */
        public int getTradingConfigIdInt() {
            if(Utility.isSet(tradingConfigId)){
                return Integer.parseInt(tradingConfigId);
            }
            return 0;
        }

        /**
         * Setter for property tradingConfigId.
         * @param tradingConfigId New value of property tradingConfigId.
         */
        public void setTradingConfigId(String tradingConfigId) {
            this.tradingConfigId = tradingConfigId;
            int tradingConfigIdInt;
            try{
                tradingConfigIdInt = Integer.parseInt(tradingConfigId);
            }catch(Exception e){
                return;
            }

            if(tradingConfigId != null){
                Iterator it = getTradingDataExchanges().iterator();
                while(it.hasNext()){
                    TradingProfileConfigData config = (TradingProfileConfigData) it.next();
                    if(config.getTradingProfileConfigId() == tradingConfigIdInt){
                        configData = config;
                        getTradingPropertyMapData().setPattern(config.getPattern());
                        getTradingPropertyMapData().setSetType(config.getSetType());
                        getTradingPropertyMapData().setDirection(config.getDirection());
                    }
                }
            }
        }

        /**
         *Returns the profile config data that this form contains based off the id
         *this object is configured for
         */
        public TradingProfileConfigData getTradingProfileConfig(){
            return configData;
        }
    }
}

