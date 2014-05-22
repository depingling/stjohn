package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.math.BigDecimal;

import org.apache.log4j.Logger;



/**
 * @author Deping
 */
public class Outbound850DistributorV2 extends OutboundSuper {
	private static final Logger log = Logger.getLogger(Outbound850DistributorV2.class);
    private static final BigDecimal ONE_HUNDERED = new BigDecimal(100);
    protected static final BigDecimal ZERO = new BigDecimal(0);
    protected String mPoDateTime;
    protected OrderAddressData mShipAddr;
    protected FreightHandlerView mShipVia;
    String mEdiRoutingCd;
    String mReqshipdate;
    //the following variables may change if we are doing any conversions
    BigDecimal mCurrItemAdjustedCost;
    int mCurrItemAdjustedQuantity;
    String mCurrItemAdjustedUom;
    private OrderAddressData currBillingAddress;
    private CreditCardTransData ccTransData = null;
    protected boolean removeNoneNumCharFormPostalCode = false;


    /** constructor for class Outbound850
     *@throws OBOEException - most likely transactionset not found
     */
    public Outbound850DistributorV2()
    throws OBOEException {
    }

    /** builds segment BEG that is part of the Header
     *<br>Beginning Segment for Purchase Order used
     *<br>To indicate the beginning of the Purchase Order Transaction Set and transmit identifying numbers and dates
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildHeaderBEG(Table inTable)
    throws OBOEException {

        Segment segment = inTable.createSegment("BEG");
        inTable.addSegment(segment);

        DataElement de;
        de = (DataElement)segment.buildDE(1); // 353 Transaction Set Purpose Code
        de.set("00");
        de = (DataElement)segment.buildDE(2); // 92 Purchase Order Type Code
        String qual = getValueFromMappingByPropertyTypeCd("PurchaseOrderTypeCode");
        if ( null != qual && qual.length() > 0 ) {
            de.set(qual);
        } else {
            de.set("DS");
        }
        de = (DataElement)segment.buildDE(3); // 324 Purchase Order Number
        if (Utility.isSet(currOutboundReq.getOrderD().getRequestPoNum()))
        	de.set(currOutboundReq.getOrderD().getRequestPoNum()); 
        else
        	de.set("N/A");       

        de = (DataElement)segment.buildDE(4); // 328 Release Number
        de.set("");
        de = (DataElement)segment.buildDE(5); // 373 Date
        de.set(mPoDateTime);
    }

    /** builds segment REF that is part of the Header
     *<br>Reference Identification used
     *<br>To specify identifying information
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildHeaderREF(Table inTable)
    throws Exception {
    	String poOutFin = currItem.getErpPoNum();
		addREFSegment(inTable, "ZZ", poOutFin); 			// eSpendwise PO Number
		currOutboundReq.getPurchaseOrderD().setOutboundPoNum(poOutFin);
        OrderItemDataVector orderItemDV = currOutboundReq.getOrderItemDV();
        for (int i = 0; orderItemDV != null && i < orderItemDV.size(); i++) {
            OrderItemData orderItemD = (OrderItemData) orderItemDV.get(i);
            orderItemD.setOutboundPoNum(poOutFin);
        }
        
		log.info("currOutboundReq.getOrderCreditCard()="+currOutboundReq.getOrderCreditCard());
		ccTransData = null;
		if (currOutboundReq.getOrderCreditCard() != null){
			
			OrderCreditCardDescData occDesc = getOrderCreditCardDescData(currOutboundReq.getOrderCreditCard().getOrderCreditCardId());
			if (occDesc != null){
				ccTransData = occDesc.getApprovalTransactionData();
				addREFSegment(inTable, "8V", ccTransData.getPaymetricTransactionId()); 	// XiPay Transaction ID 
				addREFSegment(inTable, "PSM", currOutboundReq.getOrderCreditCard().getEncryptedCreditCardNumber()); 	// Tokenized Credit Card Number 
				addREFSegment(inTable, "BB", ccTransData.getAuthCode()); 				// Credit Card Authorization Number 
				addREFSegment(inTable, "8X", getCreditCardName(currOutboundReq.getOrderCreditCard().getCreditCardType())); 	// Credit Card Type 
				addREFSegment(inTable, "93", ccTransData.getAmount()+""); 	// Credit Card Authorization Amount 
				addREFSegment(inTable, "65", ccTransData.getAmount()+""); 	// Credit Card Authorization Amount 
				addREFSegment(inTable, "XY", currOutboundReq.getOrderCreditCard().getCurrency()); 	// Currency 
				addREFSegment(inTable, "QL", ccTransData.getTransactionReference()); 	// Credit Card Reference Code
				addREFSegment(inTable, "ME", ccTransData.getPaymetricAuthMessage()); 	// Credit Card Message 
				addREFSegment(inTable, "YD", currOutboundReq.getOrderCreditCard().getName()); 	// Credit Card Holder Name 
				addREFSegment(inTable, "SF", currOutboundReq.getOrderCreditCard().getAvsAddress()); 	// Credit Card AVS Address 
				addREFSegment(inTable, "K0", currOutboundReq.getOrderCreditCard().getAvsStatus()); 	// Credit Card AVS Code 
				addREFSegment(inTable, "JX", currOutboundReq.getOrderCreditCard().getAvsZipCode()); 	// Credit Card AVS Zip	
				addREFSegment(inTable, "4N", ccTransData.getPaymetricResponseCode()); 	// Credit Card Respose (Order property)
			}			
		}
    }
    
    private String getCreditCardName(String cardName){
    	cardName = cardName.toUpperCase();
    	if (cardName.indexOf("VISA") >= 0)
    		return "VISA";
    	if (cardName.indexOf("AMERICAN") >= 0)
    		return "AMEX";
    	if (cardName.indexOf("DISCOVER") >= 0) 
    		return "DISC";
    	if (cardName.indexOf("MASTER") >= 0)
    		return "MC";
    	return cardName;    	
    }
    
    int cacheOrderCreditCardId = 0;
    OrderCreditCardDescData cacheOrderCreditCardDescData = null;
    private OrderCreditCardDescData getOrderCreditCardDescData(int orderCreditCardId) throws Exception{
       if(cacheOrderCreditCardId == orderCreditCardId){
            return cacheOrderCreditCardDescData;
       }
       APIAccess mApiAccess = new APIAccess();
       cacheOrderCreditCardDescData = mApiAccess.getOrderAPI().getOrderCreditCardDesc(orderCreditCardId);
       cacheOrderCreditCardId = orderCreditCardId;
       return cacheOrderCreditCardDescData;
    }
    
    /** builds segment PER that is part of the TableHeader
     *<br>Administrative Communications Contact used 
     *<br>To identify a person or office to whom administrative communications should be directed
     * @param inTable table containing this segment
     * @return segment object PER
     * @throws OBOEException - most likely segment not found
     */
     public void buildHeaderPER(Table inTable)
       throws OBOEException
     {
     }

        
    /** builds segment TD5 that is part of the Header
     *<br>Carrier Details (Routing Sequence/Transit Time) used
     *<br>To specify the carrier and sequence of routing and provide transit time information
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildHeaderTD5(Table inTable)
    throws OBOEException {
        if (mEdiRoutingCd == null || mEdiRoutingCd.length() == 0) {
            return;
        }
        try {
            Segment segment = inTable.createSegment("TD5");
            inTable.addSegment(segment);

            DataElement de;
            de = (DataElement)segment.buildDE(1); // 133 Routing Sequence Code
            de.set("");
            de = (DataElement)segment.buildDE(2); // 66 Identification Code Qualifier
            de.set("");
            de = (DataElement)segment.buildDE(3); // 67 Identification Code
            de.set("");
            de = (DataElement)segment.buildDE(4); // 91 Transportation Method/Type Code
            de.set("");
            de = (DataElement)segment.buildDE(5); // 387 Routing

            if (mEdiRoutingCd != null && mEdiRoutingCd.length() > 0) {
                de.set(mEdiRoutingCd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *Returns the tax location qualifier.  This is either the zip code (first 5 digits) or 00000 if there was no zip found
     */
    private String getTaxLocationIdentifier(){
        if(mShipAddr != null){
            return Utility.subString(mShipAddr.getPostalCode(),5);
        }else{
            return "00000";
        }
    }

    /** builds segment TAX that is part of the Header
     *<br>Tax Reference used
     *<br>To provide data required for proper notification/determination of applicable taxes applying to the transaction or business described in the transaction
     * @param inTable table containing this segment
     * @returns segment object TAX
     * @throws OBOEException - most likely segment not found
     */
    public void buildHeaderTAX(Table inTable)
    throws OBOEException {
        if(RefCodeNames.STORE_TYPE_CD.MLA.equals(currOutboundReq.getStoreType())){
            return;
        }
        String taxCode = getTaxExemptCode(true);
        if(taxCode == null){
            //do not generate record
            return;
        }
        Segment segment = inTable.createSegment("TAX");
        inTable.addSegment(segment);
        DataElement de;
        de = (DataElement) segment.buildDE(1);  // 325 Tax Identification Number
        de.set("");//not required
        de = (DataElement) segment.buildDE(2);  // 309 Location Qualifier
        de.set("PS");//not required
        de = (DataElement) segment.buildDE(3);  // 310 Location Identifier
        de.set(getTaxLocationIdentifier()); // not requiered
        de = (DataElement) segment.buildDE(4);  // 309 Location Qualifier
        de.set("");//not required
        de = (DataElement) segment.buildDE(5);  // 310 Location Identifier
        de.set("");//not required
        de = (DataElement) segment.buildDE(6);  // 309 Location Qualifier
        de.set("");//not required
        de = (DataElement) segment.buildDE(7);  // 310 Location Identifier
        de.set("");//not required
        de = (DataElement) segment.buildDE(8);  // 309 Location Qualifier
        de.set("");//not required
        de = (DataElement) segment.buildDE(9);  // 310 Location Identifier
        de.set("");//not required
        de = (DataElement) segment.buildDE(10);  // 309 Location Qualifier
        de.set("");//not required
        de = (DataElement) segment.buildDE(11);  // 310 Location Identifier
        de.set("");//not required
        de = (DataElement) segment.buildDE(12);  // 441 Tax Exempt Code
        de.set(taxCode);//not required
        //de = (DataElement) segment.buildDE(12);  // 1179 Customs Entry Type Group Code
        //de.set("");//not required
    }

    /**
     *Returns the tax code or null if this is not taxexempt.  Two method really on this, one that actually uses the tax code and the
     *other that simply uses it to check if the order is taxexempt or not.
     *
     *a taxCode of 1 is exempt, anything else is taxable.  Null is returned if tax information should not be included at all.
     */
    private String getTaxExemptCode(boolean headerLevel){
        if(RefCodeNames.STORE_TYPE_CD.MLA.equals(currOutboundReq.getStoreType())){
            return null;
        }
        String taxCode = null;

        //first check if this po has any tax, if so then taxcode is 2
        if(Utility.isSet(currOutboundReq.getPurchaseOrderD().getTaxTotal())){
            taxCode = "2";
            //if calculating at item level header may have tax whereas item may not
            if(!headerLevel){
                if (!Utility.isTaxableOrderItem(currItem)){
                    taxCode = "1";
                }
            }
        }else{
            taxCode = "1";
        }

        return taxCode;
    }


    /** builds segment TXI that is part of the Header
     *<br>Tax Information used
     *<br>To specify tax information
     * @param inTable table containing this segment
     * @returns segment object TXI
     * @throws OBOEException - most likely segment not found
     */
    public void buildHeaderTXI(Table inTable)
    throws OBOEException {
        if(RefCodeNames.STORE_TYPE_CD.MLA.equals(currOutboundReq.getStoreType())){
            return;
        }
        String taxCode = getTaxExemptCode(true);
        if(taxCode == null){
            //do not generate header
            return;
        }
        Segment segment = inTable.createSegment("TXI");
        inTable.addSegment(segment);
        DataElement de;
        de = (DataElement) segment.buildDE(1);  // 963 Tax Type Code
        de.set("SU"); //SU = Sales and Use
        de = (DataElement) segment.buildDE(2);  // 782 Monetary Amount
        if(this.currOutboundReq.getPurchaseOrderD().getTaxTotal() == null){
            de.set("0");//not required
        }else{
            de.set(this.currOutboundReq.getPurchaseOrderD().getTaxTotal().toString());//not required
        }
        //segment.useDefault();
    }

    public void buildHeaderN9(Table inTable, String idCode) throws OBOEException {        
        Loop loop  = null;
        Segment segment  = null;

        String msg = currOrder.getComments();
        //This is specifically here for GCA, but left in for upgrade reasons.
        if(!RefCodeNames.STORE_TYPE_CD.MLA.equals(currOutboundReq.getStoreType()) && Utility.isSet(mReqshipdate)){
    		msg = "Deliver before: "+mReqshipdate;
    		if(Utility.isSetForDisplay(currOrder.getComments())){
    			msg = msg + " "+currOrder.getComments();
        	}
        }
        PropertyData pd = Utility.getProperty(currOutboundReq.getSiteProperties(),
                RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG);
        String siteComments = null;
        if(pd != null){
            siteComments = pd.getValue();
        }
        if (!Utility.isSet(msg)){
            msg = siteComments;
        }else{
            if(Utility.isSet(siteComments)){
                msg = msg + ". " + siteComments;
            }
        }
        
        boolean msgInSeg = isSegmentInMapping("MSG");
        if ( ! isConfigForShipToMsgSegments() ) {

	    if (!Utility.isSet(msg) && !msgInSeg){
		return;
	    }
	}

        if ( null == segment ) {
            loop = inTable.createLoop("N9");
            inTable.addLoop(loop);
            segment = loop.createSegment("N9");
            loop.addSegment(segment);
        }

        DataElement de = (DataElement)segment.buildDE(1);
        de.set("ZZ");
        de = (DataElement)segment.buildDE(2);
        de.set("ABCD");  //just add some random text as some trading partners
        //(Lagasse for example) will not
        //pick this up unless there is some junk in here
        if(Utility.isSet(msg)){
            buildHeaderMSG(loop, msg);
        }
        if(msgInSeg){
            buildMappedHeaders(loop,"MSG");           ///
        }

        if ( isConfigForShipToMsgSegments() ) {
            buildShipToInfoInMsg(loop);
        }
    }

    private boolean isConfigForShipToMsgSegments() {
        // set the ship to info in msg segment is this order is being rounted
        // and the trading partnet is set up.
        log.info("end of buildHeaderN9");
        if (mEdiRoutingCd != null &&
                mEdiRoutingCd.length() > 0 &&
                Utility.isTrue(getTranslator().getConfigurationProperty
                (RefCodeNames.ENTITY_PROPERTY_TYPE.SET_SHIP_TO_FH_ADDRESS))) {
            return true;
        }
        return false;
    }

    public void buildShipToInfoInMsg(Loop inLoop) throws OBOEException {

        log.info("buildShipToInfoInMsg 1");
        addMsgSegment(inLoop, "--- CUSTOMER SHIP TO INFO -------------");


        if (Utility.isSet(currOrder.getOrderContactName())) {
            String contact;
            if(Utility.isSet(currOrder.getOrderContactPhoneNum())){
                contact = currOrder.getOrderContactName() + "/" + currOrder.getOrderContactPhoneNum();
            }else{
                contact = currOrder.getOrderContactName();
            }
            addMsgSegment(inLoop, contact);

        }

        if ( Utility.isSet(mShipAddr.getAddress1())) {
            addMsgSegment(inLoop, Utility.subString(mShipAddr.getAddress1(), 60));
        }

        if ( Utility.isSet(mShipAddr.getAddress2())) {
            addMsgSegment(inLoop, Utility.subString(mShipAddr.getAddress2(), 60));
        }
        if ( Utility.isSet(mShipAddr.getAddress3())) {
            addMsgSegment(inLoop, Utility.subString(mShipAddr.getAddress3(), 60));
        }
        if ( Utility.isSet(mShipAddr.getAddress4())) {
            addMsgSegment(inLoop, Utility.subString(mShipAddr.getAddress4(), 60));
        }

        if ( Utility.isSet(mShipAddr.getCity())) {
            addMsgSegment(inLoop, Utility.subString(mShipAddr.getCity(), 60));
        }

        if ( Utility.isSet(mShipAddr.getStateProvinceCd())) {
            addMsgSegment(inLoop, Utility.subString(mShipAddr.getStateProvinceCd(), 60));
        }

        if ( Utility.isSet(mShipAddr.getPostalCode())) {
            addMsgSegment(inLoop, getPostalCode(mShipAddr.getPostalCode()));
        }

        addMsgSegment(inLoop, "---------------------------------------");

        log.info("buildShipToInfoInMsg 12");
    }

    public void addMsgSegment(Loop inLoop, String pMsg)
    throws OBOEException {
    	Segment segment = inLoop.createSegment("MSG");
    	inLoop.addSegment(segment);
        DataElement de = (DataElement)segment.buildDE(1);
        de.set(pMsg);
    }

    private String getSTAddressName() {
        String t;
        if ( isConfigForShipToMsgSegments() ) {
            // Get the ship to address info from the freight handler.
            t = mShipVia.getPrimaryAddress().getName1();
        } else {
            t = mShipAddr.getShortDesc();
        }
        if (null == t ) t = ".";
        return t;
    }
    
    private String getSTAddress1() {
        String t ;
        if ( isConfigForShipToMsgSegments() ) {
            // Get  the ship to address info from the freight handler.
            t = mShipVia.getBusEntityData().getShortDesc();
            t += " " + mShipVia.getPrimaryAddress().getAddress1();
            return t;
        }

        t = mShipAddr.getAddress1();
        if (null == t ) t = ".";

	String acctName=currOutboundReq.getPoAccountName();
	if ( null != acctName && acctName.length() > 0 ) {
	    // Prefix all site names with the account name provided.
	    return acctName + "/" + t;
	}
        return t;

    }

    private String getSTAddress2() {
        String t;
        if ( isConfigForShipToMsgSegments() ) {
            // Get the ship to address info from the freight handler.
            t = mShipVia.getPrimaryAddress().getAddress2();
        } else {
            t = mShipAddr.getAddress2();
        }
        if (null == t ) t = ".";
        return t;
    }

    private String getSTAddress3() {
        String t;
        if ( isConfigForShipToMsgSegments() ) {
            t = mShipVia.getPrimaryAddress().getAddress3();
        } else {
            t = mShipAddr.getAddress3();
        }
        if (null == t ) t = ".";
        log.info( "getSTAddress3="+t);
        return t;
    }

    private String getSTStateProvinceCode() {
        if ( isConfigForShipToMsgSegments() ) {
            return mShipVia.getPrimaryAddress().getStateProvinceCd();
        }

        return mShipAddr.getStateProvinceCd();
    }

    private String getSTPostalCode() {
        if ( isConfigForShipToMsgSegments() ) {
            return mShipVia.getPrimaryAddress().getPostalCode();
        }

        return mShipAddr.getPostalCode();
    }

    private String getSTCity() {
        if ( isConfigForShipToMsgSegments() ) {
            return mShipVia.getPrimaryAddress().getCity();
        }

        return mShipAddr.getCity();
    }


    /** builds segment N1 that is part of the Header
     *<br>Name used
     *<br>To identify a party by type of organization, name, and code
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildHeaderN1(Table inTable, String idCode)
    throws Exception {

        Loop loop = inTable.createLoop("N1");
        inTable.addLoop(loop);
        Segment segment = loop.createSegment("N1");
        loop.addSegment(segment);
        buildHeaderN1N2(loop, idCode);
        buildHeaderN1N3(loop, idCode);
        buildHeaderN1N4(loop, idCode);

        DataElement de;
        de = (DataElement)segment.buildDE(1); // 98 Entity Identifier Code
        de.set(idCode);
        de = (DataElement)segment.buildDE(2); // 93 Name

       if (idCode.equals("BT")) {
            de.set(Utility.subString(currBillingAddress.getShortDesc(), 60));
            de = (DataElement)segment.buildDE(3);
            de.set("93");
            String accountRefNum = null;
            PropertyDataVector accountPDV =  currOutboundReq.getAccountProperties();
            for (int i = 0; accountPDV != null && i < accountPDV.size(); i++) {
                PropertyData item = (PropertyData) accountPDV.get(i);
                if (item.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCT_REF_NUM)) {
                    accountRefNum = item.getValue();
                    break;
                }
            }
            if (!Utility.isSet(accountRefNum)){
            	accountRefNum = currOrder.getAccountId()+"";
            }
            de = (DataElement)segment.buildDE(4);
            de.set(accountRefNum);
        } else if (idCode.equals("ST")) {
            de.set(Utility.subString(getSTAddressName(), 60));
            de = (DataElement)segment.buildDE(3);
            de.set("92");
            de = (DataElement)segment.buildDE(4);
            TradingPartnerData partner = getTranslator().getPartner();
            if(RefCodeNames.SITE_IDENTIFIER_TYPE_CD.CONCATONATED.equals(partner.getSiteIdentifierTypeCd())){
                String identifier = null;
                try {
                    identifier = Utility.getConcatonatedIdentifier(currOutboundReq.getAccountIdentifier(),
                            getIdForShipTo(partner),translator.getTradingPropertyMapDataVector(),
                            RefCodeNames.EDI_TYPE_CD.T850,"OUT");

                } catch (Exception e) {
                    throw new OBOEException(e.getMessage());
                }
                de.set(identifier);
            }else if(RefCodeNames.SITE_IDENTIFIER_TYPE_CD.SEPERATED_ACCOUNT_IN_REF.equals(partner.getSiteIdentifierTypeCd())){
                de.set(getIdForShipTo(partner));
            }else if(RefCodeNames.SITE_IDENTIFIER_TYPE_CD.SEPERATED_SITE_REF_NUMBER.equals(partner.getSiteIdentifierTypeCd())){
                de.set(getIdForShipTo(partner));
            }else if(RefCodeNames.SITE_IDENTIFIER_TYPE_CD.DIST_SITE_REFERENCE_NUMBER.equals(partner.getSiteIdentifierTypeCd())){
                de.set(getIdForShipTo(partner));
            }
            
        }
    }
  private String getIdForShipTo(TradingPartnerData partner) {
        if ( isConfigForShipToMsgSegments() ) {
            // use the bus entity id of the freight handler.
            return "" + mShipVia.getBusEntityData().getBusEntityId();
        }
        return Utility.getIdForShipTo(partner, currOutboundReq);
    }

    /**
     * builds segment N2 that is part of the Header
     * <br>Name used
     * <br>To identify a party by type of organization, name, and code
     * param inTable table containing this segment
     * throws OBOEException - most likely segment not found
     * @param inSegment
     * @param idCode
     * @throws com.americancoders.edi.OBOEException
     */
    public void buildHeaderN1N2(Loop inLoop, String idCode)
    throws OBOEException {

        if (idCode.equals("ST") && Utility.isSet(currOrder.getOrderContactName())) {
            Segment segment = inLoop.createSegment("N2");
            inLoop.addSegment(segment);

            DataElement de;
            de = (DataElement)segment.buildDE(1); // 166 Address Information
            if ( isConfigForShipToMsgSegments() ) {
                // The contact information has been placed in the msg segments.
                de.set(".");
                return;
            }

            String contact;
            if(Utility.isSet(currOrder.getOrderContactPhoneNum())){
                contact = currOrder.getOrderContactName() + "/" + currOrder.getOrderContactPhoneNum();
            }else{
                contact = currOrder.getOrderContactName();
            }
            de.set(Utility.subString(contact, 30));
        }
    }

    /** builds segment N3 that is part of the HeaderN1
     *<br>Address Information used
     *<br>To specify the location of the named party
     *param inSegment segment containing this subsegment
     *throws OBOEException - most likely segment not found
     */
    public void buildHeaderN1N3(Loop inLoop, String idCode)
    throws OBOEException {

        if (idCode.equals("ST") && mShipAddr.getAddress2() == null) {
            return;
        }

        Segment segment = inLoop.createSegment("N3");
        inLoop.addSegment(segment);

        DataElement de;
        de = (DataElement)segment.buildDE(1); // 166 Address Information

        if (idCode.equals("BT")) {
        	String addressLine = Utility.isSet(currBillingAddress.getAddress1()) ? Utility.subString(currBillingAddress.getAddress1(), 55) : ".";
        	de.set(addressLine);

            if(Utility.isSet(currBillingAddress.getAddress2())){
                DataElement de_1 = (DataElement)segment.buildDE(2);
                de_1.set(Utility.subString(currBillingAddress.getAddress2(),  55));                
            }
        } else if (idCode.equals("ST")) {
            de.set(getSTAddress1());
            if (Utility.isSet(getSTAddress2())){
	            DataElement de_1 = (DataElement)segment.buildDE(2);
	            de_1.set(Utility.subString(getSTAddress2(), 55));
            }
        }
    }

    /** builds segment N4 that is part of the HeaderN1
     *<br>Geographic Location used
     *<br>To specify the geographic place of the named party
     *param inSegment segment containing this subsegment
     *throws OBOEException - most likely segment not found
     */
    public void buildHeaderN1N4(Loop inLoop, String idCode)
    throws Exception {
    	String city = idCode.equals("BT") ? currBillingAddress.getCity() : idCode.equals("ST") ? getSTCity() : null;
    	if (!Utility.isSet(city) || city.length() == 1) {
    		return;
    	}    	
    	
        Segment segment = inLoop.createSegment("N4");
        inLoop.addSegment(segment);

        DataElement de;
        de = (DataElement)segment.buildDE(1); // 19 City Name

        if (idCode.equals("BT")) {
            de.set(city);            
            de = (DataElement)segment.buildDE(2); // 156 State or Province Code
            
            String stateOrProv = currBillingAddress.getStateProvinceCd();
            validateStateProvinceCode(stateOrProv);
            de.set(stateOrProv);
            de = (DataElement)segment.buildDE(3); // 116 Postal Code
			if(Utility.isSet(currBillingAddress.getPostalCode()) && !".".equals(currBillingAddress.getPostalCode())){
				de.set(currBillingAddress.getPostalCode());
			}
            //de.set("01752");
        } else if (idCode.equals("ST")) {
            de.set(city);
            de = (DataElement)segment.buildDE(2); // 156 State or Province Code
            String stateOrProv = getSTStateProvinceCode();
            validateStateProvinceCode(stateOrProv);
            de.set(stateOrProv);
            
			String zip = getPostalCode(getSTPostalCode());
			//dont render if zip = "."  This is a placeholder if the country does not support zips and there was some problem with the UI making it mandatory
			if(Utility.isSet(zip) && !".".equals(zip)){
	            de = (DataElement)segment.buildDE(3); // 116 Postal Code
	            de.set(zip);
			}
        }
    }

    /** builds segment PO1 that is part of the Detail
     *<br>Baseline Item Data used
     *<br>To specify basic and most frequently used line item data
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildDetailPO1(Table inTable)
    throws OBOEException {

        Loop loop = inTable.createLoop("PO1");
        inTable.addLoop(loop); // for (i = 0; i < multipletimes; i++)
        Segment segment = loop.createSegment("PO1");
        loop.addSegment(segment); // for (i = 0; i < multipletimes; i++)

        // for (i = 0; i < multipletimes; i++)
        buildDetailPO1PID(loop);

        DataElement de;
        de = (DataElement)segment.buildDE(1); // 350 Assigned Identification
        de.set("" + currItem.getErpPoLineNum());
        de = (DataElement)segment.buildDE(2); // 330 Quantity Ordered
        de.set(Integer.toString(mCurrItemAdjustedQuantity));
        de = (DataElement)segment.buildDE(3); // 355 Unit or Basis for Measurement Code

        de.set(mCurrItemAdjustedUom);


        de = (DataElement)segment.buildDE(4); // 212 Unit Price
        de.set(mCurrItemAdjustedCost.toString());
        de = (DataElement)segment.buildDE(5); // 639 Basis of Unit Price Code
        de.set("PE");
        de = (DataElement)segment.buildDE(6); // 235 Product/Service ID Qualifier

        // Part number qualifier can be determined by the setting
        // in the tradding partner for the following trading partner property.
        // Network need this in order to switch their 850 processing to the
        // network sku numbers.  durval
        String qual = getValueFromMappingByPropertyTypeCd("PartNumQualifier");
        if ( null != qual && qual.length() > 0 ) {
            de.set(qual);
        } else {
            de.set("VN");
        }

        de = (DataElement)segment.buildDE(7); // 234 Product/Service ID
        de.set(currItem.getDistItemSkuNum().trim());
        de = (DataElement)segment.buildDE(8); // 235 Product/Service ID Qualifier
        de.set("BP");
        de = (DataElement)segment.buildDE(9); // 234 Product/Service ID
        de.set("" + currItem.getItemSkuNum());

        buildDetailPO1TAX(loop);
    }

    /** builds segment PID that is part of the DetailPO1
     *<br>Product/Item Description used
     *<br>To describe a product or process in coded or free-form format
     *param inSegment segment containing this subsegment
     *throws OBOEException - most likely segment not found
     */
    public void buildDetailPO1PID(Loop inLoop)
    throws OBOEException {

    	Loop loop = inLoop.createLoop("PID");
    	inLoop.addLoop(loop);
    	Segment segment = loop.createSegment("PID");
    	loop.addSegment(segment);

        DataElement de;
        de = (DataElement)segment.buildDE(1); // 349 Item Description Type
        de.set("F");
        de = (DataElement)segment.buildDE(2); // 750 Product/Process Characteristic Code
        de.set("");
        de = (DataElement)segment.buildDE(3); // 559 Agency Qualifier Code
        de.set("");
        de = (DataElement)segment.buildDE(4); // 751 Product Description Code
        de.set("");
        de = (DataElement)segment.buildDE(5); // 352 Description
        String itemDesc = currItem.getItemShortDesc();
        if (itemDesc.length() > 80){
        	itemDesc = itemDesc.substring(0, 80);
  	    }
        itemDesc = Utility.removeSpecialCharachters(itemDesc, new Character('.'));
        de.set(itemDesc);
    }

    /** builds segment TAX that is part of the DetailPO1
     *<br>Tax Reference used
     *<br>To provide data required for proper notification/determination of applicable taxes applying to the transaction or business described in the transaction
     * @param inSegment segment containing this subsegment
     * @returns segment object TAX
     * @throws OBOEException - most likely segment not found
     */
    public Segment buildDetailPO1TAX(Loop inLoop)  throws OBOEException {
        String taxCode = getTaxExemptCode(false);
        if(taxCode == null){
            //do not generate detail
            return null;
        }

        Segment segment = inLoop.createSegment("TAX");
        inLoop.addSegment(segment);
        DataElement de;
        de = (DataElement) segment.buildDE(1);  // 325 Tax Identification Number
        de.set("");//not required
        de = (DataElement) segment.buildDE(2);  // 309 Location Qualifier
        de.set("PS");//not required
        de = (DataElement) segment.buildDE(3);  // 310 Location Identifier
        de.set(getTaxLocationIdentifier());  //not requiered
        de = (DataElement) segment.buildDE(4);  // 309 Location Qualifier
        de.set("");//not required
        de = (DataElement) segment.buildDE(5);  // 310 Location Identifier
        de.set("");//not required
        de = (DataElement) segment.buildDE(6);  // 309 Location Qualifier
        de.set("");//not required
        de = (DataElement) segment.buildDE(7);  // 310 Location Identifier
        de.set("");//not required
        de = (DataElement) segment.buildDE(8);  // 309 Location Qualifier
        de.set("");//not required
        de = (DataElement) segment.buildDE(9);  // 310 Location Identifier
        de.set("");//not required
        de = (DataElement) segment.buildDE(10);  // 309 Location Qualifier
        de.set("");//not required
        de = (DataElement) segment.buildDE(11);  // 310 Location Identifier
        de.set("");//not required
        de = (DataElement) segment.buildDE(12);  // 441 Tax Exempt Code
        de.set(taxCode);//not required
        //de = (DataElement) segment.buildDE(13);  // 1179 Customs Entry Type Group Code
        //de.set("");//not required
        return segment;
    }

    /** builds segment CTT that is part of the Summary
     *<br>Transaction Totals used
     *<br>To transmit a hash total for a specific element in the transaction set
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildSummaryCTT(Table inTable)
    throws OBOEException {

        Loop loop = inTable.createLoop("CTT");
        inTable.addLoop(loop);
        Segment segment = loop.createSegment("CTT");
        loop.addSegment(segment);

        DataElement de;
        de = (DataElement)segment.buildDE(1); // 354 Number of Line Items
        de.set("" + items.size());
    }



    /** builds segment MSG - Floating segment that can be insert any place
     *<br>Address Information used
     *<br>To specify the location of the named party
     *param inSegment segment containing this subsegment
     *param msg actural message to be include
     *throws OBOEException - most likely segment not found
     */
    public void buildHeaderMSG(Loop inLoop, String msg)
    throws OBOEException {
        if(msg == null){
            return;
        }
        if("".equals(msg.trim())){
            return;
        }
	
        Segment segment = inLoop.createSegment("MSG");
        inLoop.addSegment(segment);

        DataElement de;
        de = (DataElement)segment.buildDE(1);
        deSet(de,msg,null,59,true);

        if(msg.length() > 59){

        	String msg2 = msg.substring(59,msg.length());
        	buildHeaderMSG(inLoop,msg2);
    	}
    }

    /** builds segment DTM that is part of the Header
     *<br>Date/Time Reference used
     *<br>To specify pertinent dates and times
     * @param inTable table containing this segment
     * @returns segment object DTM
     * @throws OBOEException - most likely segment not found
     */
    public void buildHeaderDTM(Table inTable)
    throws OBOEException {    	
    	if (ccTransData != null){ 		   
    		// Credit card expiration date
    		Segment segment = inTable.createSegment("DTM");
    		inTable.addSegment(segment);
    		DataElement de;
    		de = (DataElement) segment.buildDE(1);  
    		de.set("ABW");
    		de = (DataElement) segment.buildDE(2);  
    		String dateStr = Integer.toString(currOutboundReq.getOrderCreditCard().getExpYear());
    		if (dateStr.length() == 2)
    			dateStr = "20" + dateStr;
    		dateStr+=Utility.padLeft(Integer.toString(currOutboundReq.getOrderCreditCard().getExpMonth()),'0',2);
    		dateStr+="01";//default to first of month as we don't get day for cc expiration
    		de.set(dateStr);

    		// Credit card authorization date and time
    		segment = inTable.createSegment("DTM");
    		inTable.addSegment(segment);
    		de = (DataElement) segment.buildDE(1);  
    		de.set("270");
    		de = (DataElement) segment.buildDE(2);  
    		Date authDate = ccTransData.getPaymetricAuthDate();
    		DateFormat mDateFormat = (new SimpleDateFormat("yyyyMMdd"));
    		de.set(mDateFormat.format(authDate));
    		
    		de = (DataElement) segment.buildDE(3);  
    		Date authTime = ccTransData.getPaymetricAuthTime();
    		mDateFormat = (new SimpleDateFormat("HHmmss"));
    		de.set(mDateFormat.format(authTime));
    	}
    	if (Utility.isSet(mReqshipdate)){ 		   
    		// Requested ship date
    		Segment segment = inTable.createSegment("DTM");
    		inTable.addSegment(segment);
    		DataElement de;
    		de = (DataElement) segment.buildDE(1);  
    		de.set("010");
    		de = (DataElement) segment.buildDE(2);  
    		de.set(mReqshipdate);
    	}
    }

    public void buildTransactionContent()
    throws Exception {
    	currOrder = currOutboundReq.getOrderD();
    	items = currOutboundReq.getOrderItemDV();
        
        currBillingAddress = currOutboundReq.getBillAddr();
        
        mShipAddr = currOutboundReq.getShipAddr();
        mShipVia = currOutboundReq.getShipVia();
        if ( mShipVia == null ) {
            mEdiRoutingCd = "";
        } else {
            mEdiRoutingCd = mShipVia.getEdiRoutingCd();
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
        dateFormatter.setTimeZone(TimeZone.getTimeZone(translator.getProfile().getTimeZone()));


        //figure out the requested ship date (only nexessary for non MLA stores)

        if(!RefCodeNames.STORE_TYPE_CD.MLA.equals(currOutboundReq.getStoreType())){
        	mReqshipdate = OrderStatusDescData.getRequestedShipDate(currOutboundReq.getOrderMetaDV());
        	if(Utility.isSet(mReqshipdate)){
		        if(Utility.isSet(mReqshipdate)){
		            //convert the date from "MM/dd/yyyy" format to "yyyyMMdd"
		            SimpleDateFormat dateFormatterFrom = new SimpleDateFormat("MM/dd/yyyy");
		            //dateFormatterFrom.setTimeZone(TimeZone.getTimeZone(translator.getProfile().getTimeZone()));
		            java.util.Date reqDelDate;
		            try{
		              reqDelDate = dateFormatterFrom.parse(mReqshipdate);
		              mReqshipdate = dateFormatter.format(reqDelDate);
		            }catch(Exception e){
		              log.info("ERROR BAD REQUESTED SHIP DATE: "+mReqshipdate);
		              mReqshipdate = null;
		            }
		        }
        	}
        }
        Table hdrtable = ts.getHeaderTable();
        currItem = (OrderItemData)items.get(0);
        mErpPoNum = currItem.getErpPoNum();
        if (currItem.getErpPoTime() != null){
        	if (currItem.getErpPoTime().getTime() >= currItem.getErpPoDate().getTime()){
            	mPoDateTime = dateFormatter.format(currItem.getErpPoTime().getTime());
            }else{
            	mPoDateTime = dateFormatter.format(currItem.getErpPoDate().getTime()+currItem.getErpPoTime().getTime());
            }
        	log.info("XXXXXXXXXX mErpPoNum="+ mErpPoNum + ",orderId=" + currItem.getOrderId());
        	log.info("XXXXXXXXXX currItem.getErpPoDate()="+ currItem.getErpPoDate() + ", " + currItem.getErpPoDate().getTime());
        	log.info("XXXXXXXXXX currItem.getErpPoTime()="+ currItem.getErpPoTime() + ", " + currItem.getErpPoTime().getTime());
        	log.info("XXXXXXXXXX mPoDateTime="+ mPoDateTime);
        }else{
        	log.info("XXXXXXXXXX currItem.getErpPoDate()="+ currItem.getErpPoDate());
        	mPoDateTime = dateFormatter.format(currItem.getErpPoDate());
        	log.info("XXXXXXXXXX mPoDateTime="+ mPoDateTime);
        }
        buildHeaderBEG(hdrtable);
        buildHeaderREF(hdrtable);
        buildHeaderPER(hdrtable);
        addManagedMappedHeader("MSG");
        buildAllMappedHeader(hdrtable);

        buildHeaderSAC(hdrtable);
        
        //requested shipping date
        buildHeaderDTM(hdrtable);

        // This is for small order handling.  durval 1/21/2003
        // Build the ship via information according to the
        // order routing code.
        log.info("ErpOrderNumber: " + currOrder.getErpOrderNum() +
                ", ErpPoNumber: " + currItem.getErpPoNum() +
                ", CustomerPoNumber: " + currOrder.getRequestPoNum() +
                " mEdiRoutingCd=" + mEdiRoutingCd);
        buildHeaderTD5(hdrtable);
        buildHeaderTXI(hdrtable);
        buildHeaderTAX(hdrtable);

        // shipping comments, added in for network 1/21/2004 Brook
        buildHeaderN9(hdrtable, "BT");

        buildHeaderN1(hdrtable, "BT");
        buildHeaderN1(hdrtable, "ST");


        Table detailtable = ts.getDetailTable();

        for (int i = 0; i < items.size(); i++) {
            currItem = (OrderItemData)items.get(i);
            mCurrItemAdjustedCost = getPoCost(currItem);
            mCurrItemAdjustedQuantity = getPoQty(currItem);
            mCurrItemAdjustedUom = currItem.getDistItemUom();
            if(!Utility.isSet(mCurrItemAdjustedUom)){
                mCurrItemAdjustedUom = currItem.getItemUom();
            }
	    // UP case the UOM.  durval 2006-9-14
            // Some customers have been entering their own UOMs in
            // lower case causing EDI generation errors.
	    mCurrItemAdjustedUom = mCurrItemAdjustedUom.toUpperCase();

            //setup the cost and quantity based off the trading partner configuration
            TradingPartnerData partner = getTranslator().getPartner();
            if(RefCodeNames.UOM_CONVERSION_TYPE_CD.CONVERT_UOM_TO_EACH.equals(partner.getUomConversionTypeCd())){
                int pack = 0;
                try{
                    if(Utility.isSet(currItem.getDistItemPack())){
                        pack = Integer.parseInt(currItem.getDistItemPack());
                    }else if(Utility.isSet(currItem.getItemPack())){
                        pack = Integer.parseInt(currItem.getItemPack());
                    }
                }catch(RuntimeException e){}
                if(pack > 1){
                    try{
                        BigDecimal curCost = getPoCost(currItem);

                        curCost = curCost.multiply(ONE_HUNDERED);
                        curCost.setScale(0);
                        BigDecimal packBD = new BigDecimal(pack);
                        packBD.setScale(0);
                        BigDecimal newCost = curCost.divide(packBD,BigDecimal.ROUND_UNNECESSARY);
                        newCost.setScale(2);
                        newCost = newCost.divide(ONE_HUNDERED,2,BigDecimal.ROUND_UNNECESSARY);
                        curCost = curCost.divide(ONE_HUNDERED,2,BigDecimal.ROUND_UNNECESSARY);
                        mCurrItemAdjustedCost = newCost;
                        mCurrItemAdjustedQuantity = pack * getPoQty(currItem);
                        mCurrItemAdjustedUom = "EA";
                    }catch(ArithmeticException e){
                        //just leave the Adjusted values nchanged and let this po be handled
                        //as Cases nd what not by humans at the reciving end.
                        log.info("ArithmeticException Caught");
                    }
                }
            }
            //end setup the cost and quantity based off the trading partner configuration

            buildDetailPO1(detailtable);
            //set state to processed
            currItem.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
            appendIntegrationRequest(currItem); // for update the status
        }


        currOutboundReq.getPurchaseOrderD().setPurchaseOrderStatusCd(
                RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
        appendIntegrationRequest(currOutboundReq.getPurchaseOrderD());
        transactionD.setKeyString("OrderNum: " + currOrder.getOrderNum() +
                ", ErpOrderNumber: " + currOrder.getErpOrderNum() +
                ", ErpPoNumber: " + currItem.getErpPoNum() +
                ", CustomerPoNumber: " + currOrder.getRequestPoNum() +
                ", mEdiRoutingCd: " + mEdiRoutingCd);
    }

    public void buildTransactionTrailer()
    throws Exception {

        Table table = ts.getSummaryTable();
        buildSummaryCTT(table);
        buildSummarySE(table);
        fg.addTransactionSet(ts);
        super.buildTransactionTrailer();
    }

    protected BigDecimal getPoCost(OrderItemData oi){
        if(oi.getDistUomConvCost() != null && oi.getDistUomConvCost().compareTo(ZERO) > 0){
            return oi.getDistUomConvCost();
        }else{
            return oi.getDistItemCost();
        }
    }

    private int getPoQty(OrderItemData oi){
        if(oi.getDistItemQuantity() > 0){
            return oi.getDistItemQuantity();
        }else{
            return oi.getTotalQuantityOrdered();
        }
    }

    //Add on charges - discount, small order fee, fuel surcharge
    public void buildHeaderSAC(Table inTable) throws Exception {
    	
    	
    	String distErpNum = ((OrderItemData)currOutboundReq.getOrderItemDV().get(0)).getDistErpNum();
    	int distId = getBusEntityIdByEntityTypeAndErpNum(RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR, distErpNum);
        APIAccess mApiAccess = new APIAccess();
    	Distributor distEjb = mApiAccess.getDistributorAPI();
    	OrderAddOnChargeDataVector addOnCharges = distEjb.getOrderAddOnCharges(distId, currOrder.getOrderId());
    	
    	if(addOnCharges!=null && addOnCharges.size()>0){    		
    		
    		for(int i=0; i<addOnCharges.size(); i++){
    			Loop loop = inTable.createLoop("SAC");
        		inTable.addLoop(loop);
    			String allowanceOrCharge = null;
    	    	String chargeCode = null; 
    	    	
    			OrderAddOnChargeData charge = (OrderAddOnChargeData)addOnCharges.get(i);
    			String chargeType = charge.getDistFeeChargeCd();
    			BigDecimal amount = charge.getAmount();
    			
    			if(amount.doubleValue()>0.009999 || amount.doubleValue()<-0.009999) {
	    			if (chargeType.equals(RefCodeNames.CHARGE_CD.DISCOUNT)){
	    				allowanceOrCharge = "A"; // Allowance
	    				chargeCode = "F050"; // Other 
	    				if (amount != null && amount.longValue() > 0) {
	    					amount = amount.negate();
	    	    		}
	    			} else if (chargeType.equals(RefCodeNames.CHARGE_CD.FUEL_SURCHARGE)){
	    				allowanceOrCharge = "C"; // Charge
	    	    		chargeCode = "D260"; // Fuel Charge
	    	    	} else if (chargeType.equals(RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE)){
	    	    		allowanceOrCharge = "C"; // Charge
	    	    		chargeCode = "G970"; // Small Order Charge
	    	    	}
	
	    			Segment segment = loop.createSegment("SAC");
	    	        loop.addSegment(segment);
	
	    	        DataElement de;
	    	        de = (DataElement) segment.buildDE(1); // Allowance or Charge Indicator. Optional.
	    	        de.set(allowanceOrCharge); 
	    	        de = (DataElement) segment.buildDE(2); // Service, Promotion, Allowance, or Charge Code. Optional.
	    	        de.set(chargeCode); // F050 = Other 
	    	        de = (DataElement) segment.buildDE(3); // Agency Qualifier Code. Optional.
	    	        de.set("");
	    	        de = (DataElement) segment.buildDE(4); // Agency Service, Promotion, Allowance or Charge Code. Optional.
	    	        de.set("");
	    	        de = (DataElement) segment.buildDE(5); // Discount. Optional.
	    	        de.set(String.valueOf(amount.longValue()));
    			}
    		}    		
    	}
    }
    
    protected void validateStateProvinceCode(String stateOrProv) throws Exception{
    	if (Utility.isSet(stateOrProv) && (stateOrProv.length() != 2))
    		throw new Exception("Zero or two digit of State/Province code is required - " + stateOrProv);
    }
    
    private void addREFSegment(Table inTable, String qualifier, String identification)
    throws OBOEException {

    	if (!Utility.isSet(identification))
    		return;
        Segment segment = inTable.createSegment("REF");
        inTable.addSegment(segment);

        DataElement de;
        de = (DataElement)segment.buildDE(1); // 128 Reference Identification Qualifier
        de.set(qualifier); 
        de = (DataElement)segment.buildDE(2); // 127 Reference Identification        
        de.set(identification);
    }
    
    private String getPostalCode(String postalCode){
    	if (Utility.isSet(postalCode)){
    		postalCode = postalCode.trim();
    		if (removeNoneNumCharFormPostalCode)
        		return Utility.getNumStringByRemoveNoneNumChars(postalCode);    			
    	}
    	return postalCode;    	
    }
}
