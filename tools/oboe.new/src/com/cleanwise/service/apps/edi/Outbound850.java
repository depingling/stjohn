package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;
import com.americancoders.edi.x12.*;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.apps.dataexchange.CompressedAddress;
import com.cleanwise.service.apps.dataexchange.OutboundTranslate;

import java.rmi.*;
import java.io.*;

import java.text.SimpleDateFormat;

import java.util.TimeZone;
import java.util.Vector;
import java.util.Iterator;
import java.math.BigDecimal;


/**
 * Formats the order extraction into a EDI document, the format specified by Lagase.
 *
 * @author Deping
 */
public class Outbound850 extends OutboundSuper {

    private static final BigDecimal ONE_HUNDERED = new BigDecimal(100);
    private static final BigDecimal ZERO = new BigDecimal(0);
    protected String mPoDateTime;
    //protected OrderAddressData mShipAddr;
    protected CompressedAddress mShipAddr;
    protected FreightHandlerView mShipVia;
    String mEdiRoutingCd;
    String mReqshipdate;
    //the following variables may change if we are doing any conversions
    BigDecimal mCurrItemAdjustedCost;
    int mCurrItemAdjustedQuantity;
    String mCurrItemAdjustedUom;
    private CompressedAddress currBillingAddress;

    // ====================================================================================
    //
    public static void main(String[] arg)
    throws Exception {

        Outbound850 ediDoc = new Outbound850();
        System.out.println("End of program");
    }

    /** constructor for class Outbound850
     *@throws OBOEException - most likely transactionset not found
     */
    public Outbound850()
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
        de.set("DS");
        de = (DataElement)segment.buildDE(3); // 324 Purchase Order Number
        //de.set(mErpPoNum + "/" + currOrder.getErpOrderNum());
        TradingPartnerData partner = ((OutboundTranslate)translator).getTradingPartner();
        int orderId = currOrder.getOrderId();
        String poOutFin = Utility.getOutboundPONumber(currEDIRequestD.getStoreType(),currOrder,partner,mErpPoNum);
        PropertyDataVector pdv =  currEDIRequestD.getAccountProperties();
        String distrPoType = RefCodeNames.DISTR_PO_TYPE.SYSTEM;
        for (int i = 0; pdv != null && i < pdv.size(); i++) {
            PropertyData item = (PropertyData) pdv.get(i);
            if (item.getPropertyTypeCd().equals(RefCodeNames.
                    PROPERTY_TYPE_CD.DISTR_PO_TYPE) && item.
                    getShortDesc().equals(RefCodeNames.
                            PROPERTY_TYPE_CD.DISTR_PO_TYPE)) {
                distrPoType = item.getValue();
                break;
            }
        }
        if (RefCodeNames.DISTR_PO_TYPE.REQUEST.equals(distrPoType)) {
            poOutFin = currEDIRequestD.getOrderD().getRequestPoNum();
        } else if (RefCodeNames.DISTR_PO_TYPE.CUSTOMER.equals(distrPoType)) {
            OrderPropertyDataVector opdv = currEDIRequestD.getOrderPropertyDV();
            for (int i = 0; opdv != null && i < opdv.size(); i++) {
                OrderPropertyData item = (OrderPropertyData)opdv.get(i);
                if(item.getOrderId()== orderId) {
					if (RefCodeNames.PROPERTY_TYPE_CD.CUSTOMER_PO_NUM.equals(item.getShortDesc())) {
						String erpPoNum = currEDIRequestD.getPurchaseOrderD().getErpPoNum();
						poOutFin = item.getValue();
						int index = (erpPoNum == null) ? -1 : erpPoNum.indexOf("-");
						if (index != -1) {
							poOutFin = poOutFin + "." + erpPoNum.substring(index + 1);
						}
						break;
					}
				}
            }
        }
        currEDIRequestD.getPurchaseOrderD().setOutboundPoNum(poOutFin);
        OrderItemDataVector orderItemDV = currEDIRequestD.getOrderItemDV();
        for (int i = 0; orderItemDV != null && i < orderItemDV.size(); i++) {
            OrderItemData orderItemD = (OrderItemData) orderItemDV.get(i);
            orderItemD.setOutboundPoNum(poOutFin);
        }
        de.set(poOutFin);


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
    throws OBOEException {

        if (currOrder.getRequestPoNum() == null){
            return;
        }


        Segment segment = inTable.createSegment("REF");
        inTable.addSegment(segment);

        DataElement de;
        de = (DataElement)segment.buildDE(1); // 128 Reference Identification Qualifier
        de.set("CO"); // Customer Order
        de = (DataElement)segment.buildDE(2); // 127 Reference Identification

        String seg1;

        if (currOrder.getRefOrderNum() != null && !currOrder.getRefOrderNum().equals("")){
            seg1 = currOrder.getRequestPoNum() + "/" + currOrder.getRefOrderNum();
        }else{
            seg1 = currOrder.getRequestPoNum();
        }
        de.set(Utility.subString(seg1, 30));


        if(Utility.isSet(currEDIRequestD.getDistributorCompanyCode())){
            segment = inTable.createSegment("REF");
            inTable.addSegment(segment);
            int nextDEIdx = 0;
            de = (DataElement)segment.buildDE(nextDEIdx + 1);
            nextDEIdx++;
            de.set("DI");
            de = (DataElement)segment.buildDE(nextDEIdx + 1);
            nextDEIdx++;
            de.set(currEDIRequestD.getDistributorCompanyCode());
        }

        TradingPartnerData partner = ((OutboundTranslate)translator).getTradingPartner();
        if(RefCodeNames.SITE_IDENTIFIER_TYPE_CD.SEPERATED_SITE_REF_NUMBER.equals(partner.getSiteIdentifierTypeCd()) || RefCodeNames.SITE_IDENTIFIER_TYPE_CD.SEPERATED_ACCOUNT_IN_REF.equals(partner.getSiteIdentifierTypeCd())){
            segment = inTable.createSegment("REF");
            inTable.addSegment(segment);
            int nextDEIdx = 0;
            de = (DataElement)segment.buildDE(nextDEIdx + 1); // 128 Reference Identification Qualifier
            nextDEIdx++;
            de.set("AI");
            de = (DataElement)segment.buildDE(nextDEIdx + 1);
            nextDEIdx++;
            de.set(currEDIRequestD.getAccountIdentifier());
        }
    }

    /** builds segment PER that is part of the Header
     *<br>Administrative Communications Contact used
     *<br>To identify a person or office to whom administrative communications should be directed
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
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

        System.out.println("1 buildHeaderTD5, mEdiRoutingCd=" +
                mEdiRoutingCd);

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
        if(currEDIRequestD.getShipAddr() != null){
            return Utility.subString(currEDIRequestD.getShipAddr().getPostalCode(),5);
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
        if(RefCodeNames.STORE_TYPE_CD.MLA.equals(currEDIRequestD.getStoreType())){
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
        if(RefCodeNames.STORE_TYPE_CD.MLA.equals(currEDIRequestD.getStoreType())){
            return null;
        }
        String taxCode = null;

        //first check if this po has any tax, if so then taxcode is 2
        if(Utility.isSet(currEDIRequestD.getPurchaseOrderD().getTaxTotal())){
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
        if(RefCodeNames.STORE_TYPE_CD.MLA.equals(currEDIRequestD.getStoreType())){
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
        if(this.currEDIRequestD.getPurchaseOrderD().getTaxTotal() == null){
            de.set("0");//not required
        }else{
            de.set(this.currEDIRequestD.getPurchaseOrderD().getTaxTotal().toString());//not required
        }
        //segment.useDefault();
    }

    public void buildHeaderN9(Table inTable, String idCode) throws OBOEException {

        Edi.log("0 mEdiRoutingCd=" + mEdiRoutingCd +
		" cfg=" + translator.getConfigurationProperty
                (RefCodeNames.ENTITY_PROPERTY_TYPE.SET_SHIP_TO_FH_ADDRESS)
		);

        Loop loop  = null;
        Segment segment  = null;

        String msg = currOrder.getComments();
        System.out.println("PRINTING::"+mReqshipdate);
        if(!RefCodeNames.STORE_TYPE_CD.MLA.equals(currEDIRequestD.getStoreType()) && Utility.isSet(mReqshipdate)){
        	if(!Utility.isSetForDisplay(msg)){
        		msg = "";
        	}
    		msg = "Deliver before: "+mReqshipdate;
        }
        PropertyData pd = Utility.getProperty(currEDIRequestD.getSiteProperties(),
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
        //if the message is don't render the N9 loop UNLESS there is
        //going to be a message segment rendered in the dynamic mapping
        //later on
        //XXX the dynamic mapping stuff needs to be smart enough to figure out
        //loops, but it currently is not.
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
        Edi.log("1 mEdiRoutingCd=" + mEdiRoutingCd +
		" cfg=" + translator.getConfigurationProperty
                (RefCodeNames.ENTITY_PROPERTY_TYPE.SET_SHIP_TO_FH_ADDRESS)
		);
    }

    private boolean isConfigForShipToMsgSegments() {
        // set the ship to info in msg segment is this order is being rounted
        // and the trading partnet is set up.
        Edi.log("end of buildHeaderN9");
        if (mEdiRoutingCd != null &&
                mEdiRoutingCd.length() > 0 &&
                Utility.isTrue(translator.getConfigurationProperty
                (RefCodeNames.ENTITY_PROPERTY_TYPE.SET_SHIP_TO_FH_ADDRESS))) {
            return true;
        }
        return false;
    }

    public void buildShipToInfoInMsg(Loop inLoop) throws OBOEException {

        Edi.log("buildShipToInfoInMsg 1");
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

        if ( Utility.isSet(mShipAddr.getStateProvinceCode())) {
            addMsgSegment(inLoop, Utility.subString(mShipAddr.getStateProvinceCode(), 60));
        }

        if ( Utility.isSet(mShipAddr.getPostalCode())) {
            addMsgSegment(inLoop, Utility.getNumStringByRemoveNoneNumChars
                    (mShipAddr.getPostalCode()));
        }

        addMsgSegment(inLoop, "---------------------------------------");

        Edi.log("buildShipToInfoInMsg 12");
    }

    public void addMsgSegment(Loop inLoop, String pMsg)
    throws OBOEException {
    	Segment segment = inLoop.createSegment("MSG");
    	inLoop.addSegment(segment);
        DataElement de = (DataElement)segment.buildDE(1);
        de.set(pMsg);
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

	String acctName=currEDIRequestD.getPoAccountName();
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
        log( "getSTAddress3="+t);
        return t;
    }

    private String getSTStateProvinceCode() {
        if ( isConfigForShipToMsgSegments() ) {
            return mShipVia.getPrimaryAddress().getStateProvinceCd();
        }

        return mShipAddr.getStateProvinceCode();
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
    throws OBOEException {

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
            //if(RefCodeNames.STORE_TYPE_CD.MLA.equals(currEDIRequestD.getStoreType())){
            de.set(Utility.subString(currBillingAddress.getAddress1(), 60));
            //de.set("Cleanwise, Inc");
        } else if (idCode.equals("ST")) {
            de.set(Utility.subString(getSTAddress1(), 60));
            de = (DataElement)segment.buildDE(3);
            de.set("92");
            de = (DataElement)segment.buildDE(4);
            TradingPartnerData partner = ((OutboundTranslate)translator).getTradingPartner();
            if(RefCodeNames.SITE_IDENTIFIER_TYPE_CD.CONCATONATED.equals(partner.getSiteIdentifierTypeCd())){
                String identifier = null;
                try {
                    identifier = Utility.getConcatonatedIdentifier(currEDIRequestD.getAccountIdentifier(),
                            getIdForShipTo(partner),((OutboundTranslate) translator).getTradingPropertyMapDataVector(),
                            RefCodeNames.EDI_TYPE_CD.T850,"OUT");

                } catch (Exception e) {
                    throw new OBOEException(e.getMessage());
                }
                de.set(identifier);
            }else if(RefCodeNames.SITE_IDENTIFIER_TYPE_CD.SEPERATED_ACCOUNT_IN_REF.equals(partner.getSiteIdentifierTypeCd())){
                de.set(getIdForShipTo(partner));
            }else if(RefCodeNames.SITE_IDENTIFIER_TYPE_CD.SEPERATED_SITE_REF_NUMBER.equals(partner.getSiteIdentifierTypeCd())){
                de.set(getIdForShipTo(partner));
            }
            // Log the ship to address.
            logShipToAddress(mShipAddr);
        }
    }
  private String getIdForShipTo(TradingPartnerData partner) {
        if ( isConfigForShipToMsgSegments() ) {
            // use the bus entity id of the freight handler.
            return "" + mShipVia.getBusEntityData().getBusEntityId();
        }
        if(RefCodeNames.SITE_IDENTIFIER_TYPE_CD.SEPERATED_SITE_REF_NUMBER.equals(partner.getSiteIdentifierTypeCd())){
            String value = Utility.getPropertyValue(currEDIRequestD.getSiteProperties(),RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
            if(Utility.isSet(value)){
                return value;
            }
        }
        return currEDIRequestD.getSiteIdentifier();
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

            de.set(Utility.subString(currBillingAddress.getAddress1(), 60));

            de.set(Utility.subString(currBillingAddress.getAddress2(),  55));
            if(Utility.isSet(currBillingAddress.getAddress3())){
                DataElement de_1 = (DataElement)segment.buildDE(2);
                de_1.set(Utility.subString(currBillingAddress.getAddress3(),  55));
                //no room for address line 3 or 4...go ahead and add them and wait for the exception
                if(Utility.isSet(currBillingAddress.getAddress4())){
                    DataElement de_2 = (DataElement)segment.buildDE(3);
                    de_2.set(Utility.subString(currBillingAddress.getAddress4(), 55));
                }
            }
            //de.set("33 Boston Post Road West, Suite 400");
        } else if (idCode.equals("ST")) {
            de.set(getSTAddress2());
            DataElement de_1 = (DataElement)segment.buildDE(2);
            de_1.set(getSTAddress3());
        }
    }

    /** builds segment N4 that is part of the HeaderN1
     *<br>Geographic Location used
     *<br>To specify the geographic place of the named party
     *param inSegment segment containing this subsegment
     *throws OBOEException - most likely segment not found
     */
    public void buildHeaderN1N4(Loop inLoop, String idCode)
    throws OBOEException {

        Segment segment = inLoop.createSegment("N4");
        inLoop.addSegment(segment);

        DataElement de;
        de = (DataElement)segment.buildDE(1); // 19 City Name

        if (idCode.equals("BT")) {
            de.set(currBillingAddress.getCity());
            //de.set("Marlborough");
            de = (DataElement)segment.buildDE(2); // 156 State or Province Code
            de.set(currBillingAddress.getStateProvinceCode());
            //de.set("MA");
            de = (DataElement)segment.buildDE(3); // 116 Postal Code
            de.set(currBillingAddress.getPostalCode());
            //de.set("01752");
        } else if (idCode.equals("ST")) {
            de.set(getSTCity());
            de = (DataElement)segment.buildDE(2); // 156 State or Province Code
            de.set(getSTStateProvinceCode());
            de = (DataElement)segment.buildDE(3); // 116 Postal Code
            de.set(Utility.getNumStringByRemoveNoneNumChars(getSTPostalCode()));
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
        de.set(Utility.toAsciiSubString(currItem.getItemShortDesc(), 80));
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
            msg = "";
        }

        Segment segment = inLoop.createSegment("MSG");
        inLoop.addSegment(segment);

        DataElement de;
        de = (DataElement)segment.buildDE(1);
        deSet(de,msg,null,59,true);

        if(msg.length() > 59){

        	String msg2 = msg.substring(59,msg.length());
        	System.out.println("=================================MSG======================"+msg);
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



        //disabled 8/17/2004 as we are not doing requested dates the same way anymore

  /*
  if(!Utility.isSet(mReqshipdate)){
      return;
  }
  Segment segment = inTable.createSegment("DTM");
  inTable.addSegment(segment);
  DataElement de;
  de = (DataElement) segment.buildDE(0);  // 374 Date/Time Qualifier
  de.set("010"); //010 = Requested Ship Date
  de = (DataElement) segment.buildDE(1);  // 373 Date
  System.out.println("setting DTM: "+mReqshipdate);
  de.set(mReqshipdate);*/
        //de = (DataElement) segment.buildDE(2);  // 337 Time
        //de.set("");//not required
        //de = (DataElement) segment.buildDE(3);  // 623 Time Code
        //de.set("");//not required
        //de = (DataElement) segment.buildDE(4);  // 1250 Date Time Period Format Qualifier
        //de.set("");//not required
        //de = (DataElement) segment.buildDE(5);  // 1251 Date Time Period
        //de.set("");//not required
        //segment.useDefault();

    }


    public void buildTransactionContent()
    throws OBOEException {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
        //dateFormatter.setTimeZone(TimeZone.getTimeZone(translator.getProfile().getTimeZone()));


        //figure out the requested ship date (only nexessary for non MLA stores)

        if(!RefCodeNames.STORE_TYPE_CD.MLA.equals(currEDIRequestD.getStoreType())){
        	mReqshipdate = OrderStatusDescData.getRequestedShipDate(currEDIRequestD.getOrderMetaDV());
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
		              System.out.println("ERROR BAD REQUESTED SHIP DATE: "+mReqshipdate);
		              mReqshipdate = null;
		            }
		        }
        	}
        }
        Table hdrtable = ts.getHeaderTable();
        currItem = (OrderItemData)items.get(0);
        mErpPoNum = currItem.getErpPoNum();
        mPoDateTime = dateFormatter.format(currItem.getErpPoDate());
        buildHeaderBEG(hdrtable);
        buildHeaderREF(hdrtable);
        addManagedMappedHeader("MSG");
        buildAllMappedHeader(hdrtable);

        //requested shipping date
        buildHeaderDTM(hdrtable);

        // This is for small order handling.  durval 1/21/2003
        // Build the ship via information according to the
        // order routing code.
        Edi.log("ErpOrderNumber: " + currOrder.getErpOrderNum() +
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
            TradingPartnerData partner = ((OutboundTranslate)translator).getTradingPartner();
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

                        System.out.println("curCost::"+curCost);
                        System.out.println("qty::"+ getPoQty(currItem));
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
                        System.out.println("mCurrItemAdjustedCost::"+mCurrItemAdjustedCost);
                        System.out.println("mCurrItemAdjustQuantity::"+mCurrItemAdjustedQuantity);
                    }catch(ArithmeticException e){
                        //just leave the Adjusted values nchanged and let this po be handled
                        //as Cases nd what not by humans at the reciving end.
                        System.out.println("ArithmeticException Caught");
                    }
                }
            }
            //end setup the cost and quantity based off the trading partner configuration

            buildDetailPO1(detailtable);
            //set state to processed
            currItem.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
            appendIntegrationRequest(currItem); // for update the status
        }


        currEDIRequestD.getPurchaseOrderD().setPurchaseOrderStatusCd(
                RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
        appendIntegrationRequest(currEDIRequestD.getPurchaseOrderD());
        transactionD.setOrderId(currOrder.getOrderId());
        transactionD.setKeyString(
                "ErpOrderNumber: " + currOrder.getErpOrderNum() +
                ", ErpPoNumber: " + currItem.getErpPoNum() +
                ", CustomerPoNumber: " + currOrder.getRequestPoNum() +
                ", mEdiRoutingCd: " + mEdiRoutingCd);
    }

    public void buildTransactionTrailer()
    throws OBOEException {

        Table table = ts.getSummaryTable();
        buildSummaryCTT(table);
        buildSummarySE(table);
        fg.addTransactionSet(ts);
    }

    public void buildTransactions(int incomingProfileId)
    throws OBOEException, RemoteException {
        items = new OrderItemDataVector();

        OutboundEDIRequestDataVector tempOrders = new OutboundEDIRequestDataVector();
        OutboundEDIRequestDataVector reqOrderDV = getOutboundTransactionsToProcess();

        for (int i = 0; i < reqOrderDV.size(); i++) {
            currEDIRequestD = (OutboundEDIRequestData)reqOrderDV.get(i);
            currOrder = currEDIRequestD.getOrderD();
            currBillingAddress = new CompressedAddress(currEDIRequestD.getBillAddr(),3);

            /*if (currOrder.getIncomingTradingProfileId() != incomingProfileId){
              tempOrders.add(reqOrderDV.get(i));
              continue;
            }*/
            items = currEDIRequestD.getOrderItemDV();

            if (items.size() > 0) {
                mShipAddr = new CompressedAddress(currEDIRequestD.getShipAddr(),3);
                mShipVia = currEDIRequestD.getShipVia();
                if ( mShipVia == null ) {
                    mEdiRoutingCd = "";
                } else {
                    mEdiRoutingCd = mShipVia.getEdiRoutingCd();
                }
                buildTransaction();
                items.clear();
            }
        }

        ((OutboundTranslate)translator).setOutboundReqOrderDV(tempOrders);
    }

    private BigDecimal getPoCost(OrderItemData oi){
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
}
