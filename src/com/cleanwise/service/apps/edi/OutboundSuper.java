package com.cleanwise.service.apps.edi;

import java.io.*;
import java.text.SimpleDateFormat;
import java.math.*;
import java.util.*;

import org.apache.log4j.Logger;


import com.americancoders.edi.*;
import com.americancoders.edi.x12.X12Envelope;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.apps.dataexchange.*;

/**
 * Super class for build outbound EDI document for a particular set.
 * This is the super class for all *EDI* documents, all a outbound
 * document must do to be understood is to implement the OutboundTransaction
 * implementation.
 * @author Deping
 */
public abstract class OutboundSuper extends InterchangeOutboundSuper {
	private static final Logger log = Logger.getLogger(OutboundSuper.class);
	protected CwX12Envelope env = null;
	protected FunctionalGroup fg = null;
	protected TransactionSet ts = null;
	protected String mErpPoNum;
	protected ManifestItemView currItemManifest = null;
	protected InvoiceAbstractionView currInvoice = null;
	protected List invoiceItems = null;
	protected InvoiceAbstractionDetailView currInvoiceItem;
	protected OrderAddressData mCustShipAddr;

	private HashSet excludeMappedHeaders = new HashSet();
	private HashSet managedMappedHeaders = new HashSet();
	/** constructor for class OutboundSuper
	 */
	

	public ElectronicTransactionData createTransactionObject() {
		String setControlNumberString = Integer.toString(profile.getGroupControlNum()) + Utility.padLeft(Integer.toString(fg.getTransactionSetCount()+1), '0', 3);

		transactionD = super.createTransactionObject();
		transactionD.setSetControlNumber(Integer.parseInt(setControlNumberString));
		transactionD.setSetStatus(SEND_MSG);
		return transactionD;
	}

	/**
	 *Returns the proper TradingPropertyMapData given the specified criteria.  This finds the
	 *first mapping, if there are multiple only the first is returned
	 */
	TradingPropertyMapData getReferenceTradingPropertyMap(String segment){
		TradingPropertyMapDataVector mapping = ((OutboundTranslate)getTranslator()).getTradingPropertyMapDataVector();
		Iterator it = mapping.iterator();
		while(it.hasNext()){
			TradingPropertyMapData map = (TradingPropertyMapData) it.next();
			if(RefCodeNames.TRADING_PROPERTY_MAP_CD.REFERENCE.equals(map.getTradingPropertyMapCode())){
				if(segment.equals(map.getUseCode())){
					return map;
				}
			}
		}
		return null;
	}

	/**
	 *Checks if a given segment is going to be mapped later on.  This is needed
	 *when a documnet has looping requierments that are not represented in the
	 *mapping table currently.
	 */
	boolean isSegmentInMapping(String pSegmentName){

		TradingPropertyMapDataVector mapping = ((OutboundTranslate)getTranslator()).getTradingPropertyMapDataVector();

		if ( null == mapping ) {
			return false;
		}

		Iterator it = mapping.iterator();
		boolean retValue=false;
		while(it.hasNext()){
			TradingPropertyMapData map = (TradingPropertyMapData) it.next();
			if ( map.getUseCode() == null ) {
				continue;
			}
			if(map.getUseCode().equalsIgnoreCase(pSegmentName)){
				if(RefCodeNames.TRADING_PROPERTY_MAP_CD.DYNAMIC.equals(map.getTradingPropertyMapCode())){
					String value = getValueFromMapping(map);
					if (Utility.isSet(value)){
						retValue = true;
					}
				}
			}
		}
		return retValue;
	}


	/**
	 *If a structure element is managed by the implementing class then that element
	 *should be indicated that it is managed here so the it is not rendered twice.
	 *Depending on where the element is you may or may not need to do this, it depends
	 *on where the element is located.  If it is after the mapped header are rendered
	 *then you will need to make a call to this method.
	 */
	void addManagedMappedHeader(String pUseCode){
		managedMappedHeaders.add(pUseCode);
	}

	/**
	 *Builds table mappings at the header level
	 */
	void buildAllMappedHeader(Table inTable){
		excludeMappedHeaders.addAll(managedMappedHeaders);
		buildMappedHeadersWorker(inTable, (String) null);
		//clear the excluded mappings as at this point everything has been rendered
		//this way we don't skip things necessary on new calls.  This would happen if
		//a mapped header was requiered in one edi transaction doc, and was rendered
		//through the default mechanism in anouther.  Currently there are no examples
		//where this is the case.
		excludeMappedHeaders.clear();
	}

	/**
	 *Builds table mappings at the header level
	 */
	void buildMappedHeaders(Loop inLoop, String pFilter){
		if(pFilter==null){
			throw new NullPointerException("pFilter was null");
		}
		buildMappedHeadersWorker(inLoop, pFilter);
	}

	/**
	 *Builds table mappings at the header level
	 */
	void buildMappedHeaders(Table inTable, String pFilter){
		if(pFilter==null){
			throw new NullPointerException("pFilter was null");
		}
		buildMappedHeadersWorker(inTable, pFilter);
	}

	/**
	 *Builds table mappings at the header level
	 */
	private void buildMappedHeadersWorker(Object inTableOrSegment, String pFilter){
		if(pFilter!=null){
			excludeMappedHeaders.add(pFilter);
		}
		TradingPropertyMapDataVector mapping = ((OutboundTranslate)getTranslator()).getTradingPropertyMapDataVector();
		Iterator it = mapping.iterator();
		while(it.hasNext()){
			TradingPropertyMapData map = (TradingPropertyMapData) it.next();
			//if we are filtering the results for a particular use code then only render those values
			if(pFilter != null && !pFilter.equals(map.getUseCode())){
				continue;
			}
			//if we are rendering all the mapped headers at this time, and a header was mapped
			//previously using the filter then don't render it now.  This is used when we are mapping
			//a value that needs its structure managed outside the data representation of the mappings
			//that is loops and other such structural elements.
			if(pFilter==null && excludeMappedHeaders.contains(map.getUseCode())){
				continue;
			}
			if(RefCodeNames.TRADING_PROPERTY_MAP_CD.DYNAMIC.equals(map.getTradingPropertyMapCode())){
				buildMappedHeader(inTableOrSegment,map);
			}else if(map.getTradingPropertyMapCode() == null){
				throw new NullPointerException("Property tradingPropertyMapCode of TradingPropertyMapData id: "
						+ map.getTradingPropertyMapId() + " cannot be null");
			}
		}

	}




	/**
	 *Create a simple mapped header segment
	 */
	private void buildMappedHeader(Object inTableOrSegment,TradingPropertyMapData pMapping)
	throws OBOEException {
		String value = getValueFromMapping(pMapping);


		if (!Utility.isSet(value)){
			if(Utility.isTrue(pMapping.getMandatory())){
				throw new RuntimeException("Property is mandatory and not present: "+pMapping);
			}else{
				return;
			}
		}
		if(inTableOrSegment instanceof Table){
			Segment segment = ((Table)inTableOrSegment).createSegment(pMapping.getUseCode());
			((Table)inTableOrSegment).addSegment(segment);
			DataElement de;
			//if the qualifier is set then set the qualifier and then the identifier.
			//otherwise assume that the identifier goes in the first position
			if(Utility.isSet(pMapping.getQualifierCode())){
				de = (DataElement) segment.buildDE(1);  // 128 Qualifier
				de.set(pMapping.getQualifierCode());
				de = (DataElement) segment.buildDE(2);  // 127 Identification
				de.set(value);
			}else{
				de = (DataElement) segment.buildDE(1);  // 127 Identification
				de.set(value);
			}
		}else if(inTableOrSegment instanceof Loop){
			Segment segment = ((Loop)inTableOrSegment).createSegment(pMapping.getUseCode());
			((Loop)inTableOrSegment).addSegment(segment);
			DataElement de;
			//if the qualifier is set then set the qualifier and then the identifier.
			//otherwise assume that the identifier goes in the first position
			if(Utility.isSet(pMapping.getQualifierCode())){
				de = (DataElement) segment.buildDE(1);  // 128 Qualifier
				de.set(pMapping.getQualifierCode());
				de = (DataElement) segment.buildDE(2);  // 127 Identification
				de.set(value);
			}else{
				de = (DataElement) segment.buildDE(1);  // 127 Identification
				de.set(value);
			}
		}else{
			throw new RuntimeException("inTableOrSegment must implement Loop or Table");
		}
	}


	/**
	 *Based off the current edi request will return the value that is specified by this mapping
	 *data. Order data, site data, site property etc etc.
	 */
	String getValueFromMapping(TradingPropertyMapData pMapping){
		String value=null;
		String prefixToValue = pMapping.getHardValue();
		if ( null == prefixToValue ||
				prefixToValue.length() == 0 ) {
			prefixToValue = "";
		}

		if(RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE.equals(pMapping.getEntityProperty())){
			value = pMapping.getHardValue();
			prefixToValue = "";
		}else if(RefCodeNames.ENTITY_PROPERTY_TYPE.ORDER_META.equals(pMapping.getEntityProperty())){
		    if(currOutboundReq.getOrderMetaDV() != null){
				Iterator it = currOutboundReq.getOrderMetaDV().iterator();
				while(it.hasNext()){
					OrderMetaData prop = (OrderMetaData) it.next();
					if(prop.getName().equalsIgnoreCase(pMapping.getPropertyTypeCd())){
						value = prop.getValue();
						//scrub some specific values
					    if(prop.getName().equals(RefCodeNames.ORDER_PROPERTY_TYPE_CD.REQUESTED_SHIP_DATE)){
							if(value != null){
							    try{
									//reformate db date to our date
									value = mkDateString(Utility.parseDate(value));
								}catch(Exception e){
									e.printStackTrace();
								}
							}
						}
						break;
					}
				}
			}
		}else if(RefCodeNames.ENTITY_PROPERTY_TYPE.ORDER_PROPERTY.equals(pMapping.getEntityProperty())){
			Iterator it = currOutboundReq.getOrderPropertyDV().iterator();
			while(it.hasNext()){
				OrderPropertyData prop = (OrderPropertyData) it.next();
				if(prop.getOrderPropertyTypeCd().equalsIgnoreCase(pMapping.getPropertyTypeCd())){
					value = prop.getValue();
					break;
				}
			}
		}else if(RefCodeNames.ENTITY_PROPERTY_TYPE.SITE_FIELD_PROPERTY.equals(pMapping.getEntityProperty())){
			Iterator it = currOutboundReq.getSiteProperties().iterator();
			while(it.hasNext()){
				PropertyData prop = (PropertyData) it.next();
				if(prop.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD)
						&& prop.getShortDesc().equalsIgnoreCase(pMapping.getPropertyTypeCd())){
					value = prop.getValue();
					break;
				}
			}
		}else if(RefCodeNames.ENTITY_PROPERTY_TYPE.SITE_PROPERTY.equals(pMapping.getEntityProperty())){
			PropertyData prop = Utility.getProperty(currOutboundReq.getSiteProperties(), pMapping.getPropertyTypeCd());
			if(prop != null){
				value = prop.getValue();
			}
		}else if(RefCodeNames.ENTITY_PROPERTY_TYPE.ORDER_COLUMN.equals(pMapping.getEntityProperty())){
			try{
				StringBuffer methodName = new StringBuffer(pMapping.getPropertyTypeCd());
				char c = Character.toUpperCase(methodName.charAt(0));
				methodName.setCharAt(0,c);
				methodName.insert(0,"get");
				OrderData o = currOutboundReq.getOrderD();
				Object obj = o.getClass().getMethod(methodName.toString(),null).invoke(o, null);
				value = obj.toString();
			}catch(Exception e){
				e.printStackTrace();
				value = null;
			}
		}else if(RefCodeNames.ENTITY_PROPERTY_TYPE.ACCOUNT_FIELD_PROPERTY.equals(pMapping.getEntityProperty())){
			if(currOutboundReq.getAccountProperties() == null){
				throw new NullPointerException("Account Properties is null, possibly not implemented for this transaction type.");
			}
			Iterator it = currOutboundReq.getAccountProperties().iterator();
			while(it.hasNext()){
				PropertyData prop = (PropertyData) it.next();
				if(prop.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FIELD_CD)
						&& prop.getShortDesc().equalsIgnoreCase(pMapping.getPropertyTypeCd())){
					value = prop.getValue();
					break;
				}
			}
		}else if(RefCodeNames.ENTITY_PROPERTY_TYPE.ACCOUNT_PROPERTY.equals(pMapping.getEntityProperty())){
			if(currOutboundReq.getAccountProperties() == null){
				throw new NullPointerException("Account Properties is null, possibly not implemented for this transaction type.");
			}
			PropertyData prop = Utility.getProperty(currOutboundReq.getAccountProperties(), pMapping.getPropertyTypeCd());
			if(prop != null){
				value = prop.getValue();
			}
		}

		if(value == null){
			value = "";
		}
		
		if ( null == prefixToValue || prefixToValue.equals(value) ) {
			prefixToValue = "";
		}

		return prefixToValue + value;
	}


	String getValueFromMappingByPropertyTypeCd(String pPropertyTypeCd){

		if ( null == pPropertyTypeCd || pPropertyTypeCd.length() == 0 ) {
			return null;
		}

		TradingPropertyMapDataVector mapping = ((OutboundTranslate)getTranslator()).getTradingPropertyMapDataVector();
		Iterator it = mapping.iterator();
		while(it.hasNext()){
			TradingPropertyMapData map = (TradingPropertyMapData) it.next();
			if ( pPropertyTypeCd.equals(map.getPropertyTypeCd())
					&& map.getHardValue() != null
			) {
				return map.getHardValue();
			}
		}

		return null;
	}

	/**
	 *Sets a DataElement value, trims the value to the desired length by truncating anything
	 *over the desired length.
	 */
	protected void deSet(DataElement pDe, String pStr,int pLength){
		deSet(pDe, pStr, null, pLength, false);
	}

	/**
	 *Sets a DataElement value, trims the value to the desired length by truncating anything
	 *over the desired length.  If the value is not set uses the default value.
	 */
	protected void deSet(DataElement pDe, String pStr, String pDefaultVal,int pLength){
		deSet(pDe, pStr, pDefaultVal, pLength, false);
	}

	/**
	 *Sets a DataElement value, trims the value to the desired length by truncating anything
	 *over the desired length.  If the value is not set uses the default value. Filters out any
	 *bad charachters that are not suitable for EDI transmission.
	 */
	protected void deSet(DataElement pDe, String pStr, String pDefaultVal,int pLength,boolean filterChars){
		//set as default value
		if(pStr == null || pStr.trim().length() == 0){
			pStr = pDefaultVal;
		}

		//filter anything not suitable for EDI
		if(filterChars){
			String[] search = {"\n","\f","\t","\r","\""};
			String[] replace = {"","","","",""};
			for(int j=0; j < search.length;j++){
				pStr = pStr.replaceAll(search[j], replace[j]);
			}
		}
		//set the length
		if(pStr.length() > pLength){
			pStr = pStr.substring(0,pLength);
		}
		//trim any whitespace and set the data element
		pStr = pStr.trim();
		pDe.set(pStr);
	}

	/**
	 *Removes the decimal places from a bigdecimal.  for example if 5.25 is passed in 525 is returned
	 *if 5 is passed in 500 is returned.  This is suitable for edi docs where they do not want any decimals
	 */
	BigDecimal convertToNoDecimals(BigDecimal val){
		val = val.setScale(2, BigDecimal.ROUND_HALF_UP);
		val = val.multiply(new BigDecimal(100));
		return val;
	}

	protected void logShipToAddress(CompressedAddress pShipTo ) {
		String logstr =  currOrder.getSiteId() + "\t";

		String t = currOrder.getUserFirstName();
		if ( null == t ) t = ".";
		logstr += t + "\t";

		t = currOrder.getUserLastName();
		if ( null == t ) t = "..";
		logstr += t + "\t";

		t = pShipTo.getAddress1();
		if ( t.length() == 0 ) {
			t += " ";
		}
		t += "\t";
		logstr += t;

		t = "";
		if ( pShipTo.getAddress2() != null ) {
			t += pShipTo.getAddress2();
		}
		if ( pShipTo.getAddress3() != null ) {
			if ( t.length() > 0 ) t += "/";
			t += pShipTo.getAddress3();
		}
		if ( pShipTo.getAddress4() != null ) {
			if ( t.length() > 0 ) t += "/";
			t += pShipTo.getAddress4();
		}
		if ( t.length() == 0 ) {
			t += " ";
		}
		t += "\t";

		logstr += t;
		logstr += pShipTo.getCity();
		logstr += "\t";
		logstr += pShipTo.getStateProvinceCode();
		logstr += "\t";
		logstr += pShipTo.getPostalCode();
		logstr += "\t";
		logstr += mErpPoNum + "\t";
		logstr += "\t";
		logstr += "EOR" + "\n";

		try {
			t = java.lang.System.getProperty("addressLogFile");
			if ( t != null && t.length() > 0 ) {
				FileOutputStream addrFileOut = new FileOutputStream( new File(t), true );
				addrFileOut.write(logstr.getBytes());
				addrFileOut.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected String mkHourString(java.util.Date pInDate) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("HHmm");
		return dateFormatter.format(pInDate);
	}

	protected String mkDateString(java.util.Date pInDate) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
		return dateFormatter.format(pInDate);
	}

	protected String getOrderApprovalDateString() {
		if ( null == currOrder ) {
			return "";
		}
		// For JCP, the order gets revised when JCP sends us a PO number.
		if ( null != currOrder.getRevisedOrderDate() ) {
			return mkDateString(currOrder.getRevisedOrderDate());
		}

		// Fall back to the order date.
		if ( null != currOrder.getOriginalOrderDate() ) {
			return mkDateString(currOrder.getOriginalOrderDate());
		}

		return "";
	}

	protected String stripEPRO(String pInString) {

		if ( pInString  == null || pInString.length() == 0 ) {
			return pInString;
		}

		if ( !pInString.startsWith("EPRO") ) {
			return pInString;
		}

		String t = pInString;
		// JCP does not want the prefix we have added
		// to the PO number when they get the invoice.
		t = t.replaceFirst("EPRO", "");
		t = t.substring(0,8);
		return t;
	}
	
	public void buildTransactionHeader()
	throws Exception
	{
		super.buildTransactionHeader();// transaction object
		
		//try to get the proper xml file based off the version of the tradingProfile
		if(Utility.isSet(getTranslator().getProfile().getVersionNum())){
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
			ts = TransactionSetFactory.buildTransactionSet(getSetType(),"V",getTranslator().getProfile().getVersionNum(),null,null,null);
		}else{
			ts = TransactionSetFactory.buildTransactionSet(getSetType());
		}
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
	public void buildInterchangeHeader()throws Exception{
		super.buildInterchangeHeader();
		env = new CwX12Envelope(EnvelopeFactory.buildEnvelope("x12.envelope", ""));

		/** add code here to work with the headers and other envelope control segments */
		Segment interchange_Control_Header = env.createInterchange_Header();

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
	public void buildInterchangeTrailer()
	throws Exception {
		// Print the interchange trailer
		Segment interchange_Control_Trailer = env.createInterchange_Trailer();
		interchange_Control_Trailer.useDefault();

		for (int i = 0; i < env.getFunctionalGroupCount(); i++) {
			log.info(" fg i=" + i);
			for (int j = 0; j < env.getFunctionalGroup(i).getTransactionSetCount(); j++) {
				log.info( " ts j=" + j + " ts count=" + env.getFunctionalGroup(i).getTransactionSetCount() );
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
			String errMsg = "----LOGGING ERRORS----";
			for (int i = 0; i < errors.getErrorCount(); i++){
				errMsg += "\r\n" + (errors.getError()[i]);
			}
			
			errMsg += "\r\n---END LOGGING ERRORS---"; 
			log.error(errMsg);
			//closeOutputStream();
			throw new IOException("Error in building EDI document. \r\n" + errMsg);
		}

		log.info(" Writing the output file...");
        env.writeFormattedText(new OutputStreamWriter(translator.getOutputStream(), "UTF-8"), X12Envelope.X12_FORMAT);
		log.info(" Done, writing the output file.");
		super.buildInterchangeTrailer();
	}

	public void buildHeaderISA(Segment isa) {
		isa.getDataElement(1).set(profile.getAuthorizationQualifier());
		if (profile.getAuthorization() != null && !profile.getAuthorization().equals(""))
			isa.getDataElement(2).set(Utility.padRight(profile.getAuthorization(), ' ', 10));//auth info 10-10
		else
			isa.getDataElement(2).set(Utility.padRight(" ", ' ', 10));

		isa.getDataElement(3).set(profile.getSecurityInfoQualifier());

		if (profile.getSecurityInfo() != null && !profile.getSecurityInfo().equals("")) //sec info qual 2-2
			isa.getDataElement(4).set(Utility.padRight(profile.getSecurityInfo(), ' ', 10));//sec info 10-10
		else
			isa.getDataElement(4).set(Utility.padRight(" ", ' ', 10));//sec info 10-10

		isa.getDataElement(5).set(profile.getInterchangeSenderQualifier());//int sender info qual 2-2
		isa.getDataElement(6).set(Utility.padRight(profile.getInterchangeSender(), ' ', 15));//int sender info 15-15
		isa.getDataElement(7).set(profile.getInterchangeReceiverQualifier());//int id qual 2-2
		isa.getDataElement(8).set(Utility.padRight(profile.getInterchangeReceiver(), ' ', 15));//int id 15-15
		isa.getDataElement(9).set(getTranslator().getDateTimeAsString().substring(2, 8));// int dt 6-6
		isa.getDataElement(10).set(getTranslator().getDateTimeAsString().substring(8));//int tm 4-4
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
			gs.getDataElement(4).set(getTranslator().getDateTimeAsString().substring(2,8));
		else
			gs.getDataElement(4).set(getTranslator().getDateTimeAsString().substring(0,8));
		gs.getDataElement(5).set(getTranslator().getDateTimeAsString().substring(8));
		gs.getDataElement(6).set(""+profile.getGroupControlNum());
		gs.getDataElement(7).set(profile.getResponsibleAgencyCode());
//		gs.getDataElement(8).set(profile.getVersionNum());
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
	
	public String getTranslationReport() {
		String str = "";
		if (this.isFail())
		{
			return ("Outbound EDI translation failed");
		}
		else
		{
			str = "Outbound EDI translation is success.\r\n";
			str += "Outbound edi filename: " + getTranslator().getOutputFileName() + "\r\n\r\n";
			str += "Sender          Receiver        Set_Type  Group_Control_Num  Set_Control_Num  Transaction_Status\r\n";
			for (int i = 0; i < getInterchanges().size(); i++) {
				InterchangeRequestData interchangeReqD = (InterchangeRequestData)getInterchanges().get(i);
				for (int j = 0; j < interchangeReqD.getTransactionDataVector().size(); j++) {
					ElectronicTransactionData transactionD = (ElectronicTransactionData)interchangeReqD.getTransactionDataVector().get(j);
					str += Utility.padRight(transactionD.getGroupSender(), ' ', 16)
					+ Utility.padRight(transactionD.getGroupReceiver(), ' ', 16)
					+ Utility.padRight(transactionD.getSetType(), ' ', 10)
					+ Utility.padRight(""+transactionD.getGroupControlNumber(), ' ', 19)
					+ Utility.padRight(""+transactionD.getSetControlNumber(), ' ', 17)
					+ status[transactionD.getSetStatus()] + "\r\n";
				}
			}
		}
		return str;
	}
}
