package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;
import com.americancoders.edi.x12.*;
import java.io.*;
import java.util.Vector;
import java.util.Date;
import java.util.Iterator;
import com.cleanwise.service.apps.ClientServicesAPI;
import com.cleanwise.service.apps.dataexchange.Translator;
import java.util.Properties;


import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.DataNotFoundException;
import java.util.ArrayList;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.framework.*;

/**
 *
 * @author Deping
 */
public class Edi extends ClientServicesAPI
{
    Translator translator;

    // constant to define the status of a transaction set
    static int PEND_MSG = 0;
    static int SEND_MSG = 1;
    static int ACKA_MSG = 3;
    static int ACKE_MSG = 4;
    static int ACKR_MSG = 5;
    static int RECA_MSG = 6;
    static int RECE_MSG = 7;
    static int RECR_MSG = 8;
    static int RECN_MSG = 9;
    static int NORT_MSG = 10;
    static int DUPL_MSG = 11;
    static String[] status = {"Transaction waits to be sent out", "Transaction has been created", " ",
			      "Transaction has been acknowledge with accepted status", "Transaction has been acknowledge with accepted, but errors were noted status",
			      "Transaction has been acknowledge with rejected status", "Transaction has been accepted",
			      "Transaction has been received with acceptable error", "Transaction has been received with fatal error",
			      "Transaction received is not supported", "Transaction received, Trading Partner profile not set up",
			      "Duplicate 997 transaction"};

    static int INBOUND = 0;
    static int OUTBOUND = 1;
    int direction;  // transaction is inbound or outbound
    protected InterchangeData interchangeD;
    ElectronicTransactionData transactionD;
    protected CwX12Envelope env = null;
    protected FunctionalGroup fg = null;
    protected TransactionSet ts = null;
    private InterchangeRequestData reqInterchange;
    protected OrderData currOrder;
    protected OrderItemData currItem;
    protected OrderItemDataVector items = null;
    protected String filename997 = null;
    protected ArrayList transactionDetails = null;


    TradingPartnerData partner; // incomming interchange profile
    TradingProfileData profile;

    public ElectronicTransactionData getElectronicTransactionData() {
      return transactionD;
    }

    public ArrayList getTransactionDetails() {
      return transactionDetails;
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
    private String mGroupType = null;
    public String getGroupType () {
	String v = "";
	if ( null != mGroupType ) {
	    v = mGroupType;
	} else {
	    v = translator.getGroupType();
	}

	log(" Edi.getGroupType=" + v);
	return v;
    }

    public String getGroupType (FunctionalGroup fg) {
	return fg.getHeader().getDataElement(1).get();
    }

    public void setGroupType (String v) {
	log(" Edi.setGroupType=" + v);
	mGroupType = v;
    }

    private String mSetType = null;
    public void  setSetType (String v) {
	log(" Edi.setSetType=" + v);
	mSetType = v;
    }

    public static void log(String s) {

	if ( System.getProperty("debug") != null &&
	     System.getProperty("debug").equals("y") ) {
	    System.out.println(s);
	}
    }

    public String getSetType () {
	if ( null != mSetType ) {
	    return mSetType;
	}
        return translator.getSetType();
    }

    private InterchangeRequestDataVector mReqInterchanges = new InterchangeRequestDataVector();
    public InterchangeRequestDataVector getInterchanges() {
        return mReqInterchanges;
    }

    public void appendInterchanges (InterchangeRequestDataVector pInterchanges) {
        mReqInterchanges.addAll(pInterchanges);
    }

    private IntegrationRequestsVector mIntegrationReqs = new IntegrationRequestsVector();
    public IntegrationRequestsVector getIntegrationRequests () {
        return mIntegrationReqs;
    }
    public void appendIntegrationRequests (ArrayList pRequests) {
        mIntegrationReqs.addAll(pRequests);
    }
    public void appendIntegrationRequest (ValueObject obj) {
        mIntegrationReqs.add(obj);
    }

    public IntegrationRequestsVector getRequestsToProcess(){
        translator.setErpNum(getErpNum());
        if (getInterchanges().size() > 0) {
            InterchangeRequestDataVector irDV = getInterchanges();
    	    appendIntegrationRequests(irDV);
        }
        return getIntegrationRequests();
    }

    private Vector mLogStrings = new Vector(); // list of string that contain transaction information
    public Vector getLogStrings () {
        return mLogStrings;
    }
    public void setLogStrings (Vector pLogStrings) {
        mLogStrings = pLogStrings;
    }

    private boolean mFail = false;
    public boolean isFail() {
        return mFail;
    }
    public void setFail(boolean pFail) {
        mFail=pFail;
    }





    private String logTransaction(ElectronicTransactionData pTransaction){
	return (Utility.padRight(pTransaction.getGroupSender(), ' ', 16)
		+ Utility.padRight(pTransaction.getGroupReceiver(), ' ', 16)
		+ Utility.padRight(pTransaction.getSetType(), ' ', 10)
		+ Utility.padRight(""+pTransaction.getGroupControlNumber(), ' ', 19)
		+ Utility.padRight(""+pTransaction.getSetControlNumber(), ' ', 17)
		+ status[pTransaction.getSetStatus()] + "\r\n");
    }

    public String getTranslationReport() {
	String str = "";
	String directionStr;
	if (direction == INBOUND){
	    directionStr = "Inbound";
      if (filename997 != null){
          str = "A 997 document that acknowledges inbound transactions in file: \r\n" +
          translator.getInputFilename() +
          "\r\n has been generated and saved to file: \r\n" +
          filename997 + ".\r\n\r\n";
      }
  }
	else
	    directionStr = "Outbound";

	if (this.isFail())
	    {
		return (directionStr + " EDI translation failed");
	    }
	else
	    {
		str = str + directionStr + "EDI translation is success.\r\n";
		if (direction == INBOUND){
			str += "Inbound edi filename: " + translator.getInputFilename() + "\r\n\r\n";
		}else{
			str += "Outbound edi filename: " + translator.getOutputFileName() + "\r\n\r\n";
		}
		str += "Sender          Receiver        Set_Type  Group_Control_Num  Set_Control_Num  Transaction_Status\r\n";
		for (int i = 0; i < getInterchanges().size(); i++) {
		    InterchangeRequestData interchangeReqD = (InterchangeRequestData)getInterchanges().get(i);
		    for (int j = 0; j < interchangeReqD.getTransactionDataVector().size(); j++) {
			ElectronicTransactionData transactionD = (ElectronicTransactionData)interchangeReqD.getTransactionDataVector().get(j);
			str += logTransaction(transactionD);
		    }
		}
	    }
	return str;
    }

    //
    public ElectronicTransactionData createTransactionObject() {
        transactionD = ElectronicTransactionData.createValue();
        transactionD.setGroupType(getGroupType());
        transactionD.setGroupSender(profile.getGroupSender());
        transactionD.setGroupReceiver(profile.getGroupReceiver());
        transactionD.setGroupControlNumber(profile.getGroupControlNum());
        transactionD.setSetType(getSetType());
        if (currOrder != null) {
            transactionD.setOrderId(currOrder.getOrderId());
        }
        transactionDetails = new ArrayList();
        reqInterchange.addTransaction(transactionD, transactionDetails);
        return transactionD;
    }

    /**
     * Set properties value for interchange object
     */
    public InterchangeData createInterchangeObject()
    {
	interchangeD = InterchangeData.createValue();
	interchangeD.setTradingProfileId(profile.getTradingProfileId());
	interchangeD.setInterchangeSender(profile.getInterchangeSender());
	interchangeD.setInterchangeReceiver(profile.getInterchangeReceiver());
	interchangeD.setInterchangeControlNum(profile.getInterchangeControlNum());
	interchangeD.setTestInd(profile.getTestIndicator());
	if (direction == INBOUND)
	    {
		interchangeD.setInterchangeType("INBOUND");
		interchangeD.setEdiFileName(translator.getInputFilename());
	    }
	else
	    {
		interchangeD.setInterchangeType("OUTBOUND");
                System.out.println("translator::"+translator);
                System.out.println("translator.getOutputFileName()::"+translator.getOutputFileName());
		interchangeD.setEdiFileName(translator.getOutputFileName());
	    }

	reqInterchange = InterchangeRequestData.createValue();
	reqInterchange.setInterchangeData(interchangeD);
	getInterchanges().add(reqInterchange);

	return interchangeD;
    }

    public void buildTransactionHeader()
    throws OBOEException
  {
        //try to get the proper xml file based off the version of the tradingProfile
      if(Utility.isSet(translator.getProfile().getVersionNum())){
          //param 1: setType (850, 855, etc.)
          //param 2: search type,
          //  S=Sender Directory
          //  V=EDI Version number
          //  R=Reciever
          //  T=Test Prod
          //The search type param may be strung together, so for example TV would look first at the test
          //prod flag, then at the version number subdirectory.
          //param 3: the version number
          //param 4: sender directoty
          //param 5: receiver directory
          //param 6: test/production directory
          //It seems that the intention is in the documentation and not enforced by the API
          //See http://www.americancoders.com/OpenBusinessObjects/HowToUseOBOE.html for more detail instructions
          ts = TransactionSetFactory.buildTransactionSet(getSetType(),"V",translator.getProfile().getVersionNum(),null,null,null);
      }else{
          ts = TransactionSetFactory.buildTransactionSet(getSetType());
      }
      transactionD = createTransactionObject();
      buildHeaderST();
  }

    /** builds segment ST that is part of the Header
     *<br>Transaction Set Header used
     *<br>To indicate the start of a transaction set and to assign a control number
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildHeaderST()
    throws OBOEException {
        Table table = ts.getHeaderTable();
        Segment segment = table.createSegment("ST");
        table.addSegment(segment);
        DataElement de;
        de = (DataElement) segment.buildDE(1);  // 143 Transaction Set Identifier Code
        de.set(getSetType());
        de = (DataElement) segment.buildDE(2);  // 329 Transaction Set Control Number
        de.set(""+transactionD.getSetControlNumber());
    }



    /**
     * Print the ISA and group headers
     */
    public void buildDocumentHeader(TradingProfileData pProfile)throws DataNotFoundException{
        setProfile(pProfile);
//        env = new X12Envelope();
        env = new CwX12Envelope(EnvelopeFactory.buildEnvelope("x12.envelope", ""));
        interchangeD = createInterchangeObject();

        /** add code here to work with the headers and other envelope control segments */
// YR OBOE        Segment interchange_Control_Header = Interchange_Control_Header.getInstance();
        Segment interchange_Control_Header = env.createInterchange_Header();

///        interchange_Control_Header.useDefault();
///        env.setInterchange_Header(interchange_Control_Header);

        if (profile.getSegmentTerminator() == null)
            profile.setSegmentTerminator("\n");

        env.setDelimiters(profile.getSegmentTerminator()+profile.getElementTerminator()+profile.getSubElementTerminator());
        buildHeaderISA(interchange_Control_Header);
        env.setDelimitersInHeader();
        fg = env.createFunctionalGroup();
        Segment fgHeader = fg.getHeader();

        buildHeaderGS(fgHeader);
        fg.addSegment(fgHeader);

        Segment fgTrailer = fg.getTrailer();
        fgTrailer.useDefault();
        fg.addSegment(fgTrailer);

        env.addFunctionalGroup(fg);
    }

    /**
     * Print the ISA and group trailers.
     */
    public void buildDocumentTrailer()
    throws IOException {
        // Print the interchange trailer
        Segment interchange_Control_Trailer = env.createInterchange_Trailer();
        interchange_Control_Trailer.useDefault();

        for (int i = 0; i < env.getFunctionalGroupCount(); i++) {
	    log(" fg i=" + i);
            for (int j = 0; j < env.getFunctionalGroup(i).getTransactionSetCount(); j++) {
		log( " ts j=" + j + " ts count=" + env.getFunctionalGroup(i).getTransactionSetCount() );
                env.getFunctionalGroup(i).getTransactionSet(j).setTrailerFields();
	    }
            env.getFunctionalGroup(i).setCountInTrailer();
        }
        env.setFGCountInTrailer();


        DocumentErrors errors = new DocumentErrors();
        setFail(false);
        env.validate(errors);
        if (errors.getError() != null){
            setFail(true);
            logError("----LOGGING ERRORS----");
            for (int i = 0; i < errors.getErrorCount(); i++){
                logError(errors.getError()[i]);
            }
            logError("---END LOGGING ERRORS---");
            //closeOutputStream();
            if(com.cleanwise.service.apps.dataexchange.OutboundTranslate.deleteFileOnError){
                throw new IOException("Error in building EDI document");
            }
        }

        System.out.println(env.getFormattedText(env.X12_FORMAT));
	log(" Writing the output file...");
        translator.writeOutputStream(env.getFormattedText(env.X12_FORMAT));
	log(" Done, writing the output file.");

    }

    public void buildHeaderISA(Segment isa) {
        isa.getDataElement(1).set(profile.getAuthorizationQualifier());
        if (profile.getAuthorization() != null && !profile.getAuthorization().equals(""))
            isa.getDataElement(2).set(Utility.padRight(profile.getAuthorization(), ' ', 10));//auth info 10-10
        else
            isa.getDataElement(2).set(Utility.padRight(" ", ' ', 10));

        isa.getDataElement(3).set(profile.getSecurityInfoQualifier());

        if (profile.getSecurityInfo() != null && !profile.getSecurityInfo().equals("")) //sec info qual 2-2
//            isa.getDataElement(4).set(profile.getSecurityInfo());//sec info 10-10
            isa.getDataElement(4).set(Utility.padRight(profile.getSecurityInfo(), ' ', 10));//sec info 10-10
        else
//            isa.getDataElement(4).set(" ");//sec info 10-10
            isa.getDataElement(4).set(Utility.padRight(" ", ' ', 10));//sec info 10-10

        isa.getDataElement(5).set(profile.getInterchangeSenderQualifier());//int sender info qual 2-2
//        isa.getDataElement(6).set(profile.getInterchangeSender());//int sender info 15-15
        isa.getDataElement(6).set(Utility.padRight(profile.getInterchangeSender(), ' ', 15));//int sender info 15-15
        isa.getDataElement(7).set(profile.getInterchangeReceiverQualifier());//int id qual 2-2
//        isa.getDataElement(8).set(profile.getInterchangeReceiver());//int id 15-15
        isa.getDataElement(8).set(Utility.padRight(profile.getInterchangeReceiver(), ' ', 15));//int id 15-15
        System.out.println("Edi.java >>>>translator::"+translator);
        isa.getDataElement(9).set(translator.getDateTimeAsString().substring(2, 8));// int dt 6-6
        isa.getDataElement(10).set(translator.getDateTimeAsString().substring(8));//int tm 4-4
        isa.getDataElement(11).set(profile.getInterchangeStandardsId());//int cntr std id 1-1
        isa.getDataElement(12).set(profile.getInterchangeVersionNum());//int cntrl ver 5-5
        isa.getDataElement(13).set(""+profile.getInterchangeControlNum());// int cntrl number 9-9
        isa.getDataElement(14).set(profile.getAcknowledgmentRequested());//ack req 1-1
        isa.getDataElement(15).set(profile.getTestIndicator());//test ind 1-1
    }

    public void buildHeaderGS(Segment gs) {
        gs.getDataElement(1).set(getGroupType());
        gs.getDataElement(2).set(profile.getGroupSender());
        gs.getDataElement(3).set(profile.getGroupReceiver());
        if (profile.getVersionNum().compareTo("004010") < 0)
            gs.getDataElement(4).set(translator.getDateTimeAsString().substring(2,8));
        else
            gs.getDataElement(4).set(translator.getDateTimeAsString().substring(0,8));
        gs.getDataElement(5).set(translator.getDateTimeAsString().substring(8));
        gs.getDataElement(6).set(""+profile.getGroupControlNum());
        gs.getDataElement(7).set(profile.getResponsibleAgencyCode());
//        gs.getDataElement(8).set(profile.getVersionNum());
        gs.getDataElement(8).set(Utility.padLeft(profile.getVersionNum(), '0', 6));
    }

    public void buildSummarySE(Table inTable)
    throws OBOEException {
        Segment segment = inTable.createSegment("SE");
        inTable.addSegment(segment);  DataElement de;
        de = (DataElement) segment.buildDE(1);  // 96 Number of Included Segments
        de.set("");
        de = (DataElement) segment.buildDE(2);  // 329 Transaction Set Control Number
        de.set("");
    }

    DocumentErrors de;

    public DocumentErrors getDocumentErrors() {

            return de;
    }

}
