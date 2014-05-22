/*
 * Inbound856_V2.java
 */

package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;

import java.util.*;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.apps.dataexchange.InboundTranslate;
import com.cleanwise.service.apps.dataexchange.Translator;

/**
 *
 * @author SSharma
 */
public class Inbound856_V2  extends InboundEdiSuper
{
	private static final Logger log = Logger.getLogger(Inbound856_V2.class);

	// Processing variables
	protected EdiInp856View ediInp856Vw = null;
	protected EdiInp856ItemView ediInp856ItemVw = null;
	// XXX: This one tells us whether the SAC segment was found, in
	// which case the freight charge should be set.  Currently we're
	// not using it.

	/**
	 * Extract all segments included in ST - SE Segment
	 */
	public void extract()
	throws OBOEException
	{
		ediInp856Vw = EdiInp856View.createValue();
		ediInp856Vw.setItems(new EdiInp856ItemViewVector());

		Table table = ts.getHeaderTable();

		if (table != null)
		{
			extractHeaderBSN(table);
		}
		table = ts.getDetailTable();
		if (table != null)
		{
			int HLLoopSize = table.getLoopCount("HL");
			for(int i=0; i<HLLoopSize; i++) {
				Loop loop = table.getLoop("HL", i);
				Segment segment = loop.getSegment("HL");
				String levelCode =
					getField(segment,3,true,"Missing Hierarchical Level Code in segment HL*"+i);
				if(levelCode==null) {
					errorMsgs.add("Level Code is null");
					setValid(false);
					continue;
				}
				if(i == 0){
					if(!"S".equals(levelCode)) {
						errorMsgs.add("Invalid Hierarchical Level Code in segment HL*0 : "+levelCode);
						setValid(false);
						continue;
					}
					extractShipmentLevelREF(loop);
					extractShipmentDTM(loop);
					extractShipmentTD5(loop);
					extractShipByN1(loop);
				}else if (i == 1){
					if(!"O".equals(levelCode)) {
						errorMsgs.add("Invalid Hierarchical Level Code in segment HL*1 : "+levelCode);
						setValid(false);
						continue;
					}
					extractPurchaseOrderReferencePRF(loop);
					extractReferenceIdentificationREF(loop);
				}else{
					if("O".equals(levelCode)) {
						extractPurchaseOrderReferencePRF(loop);
						extractReferenceIdentificationREF(loop);
					}else if(!"I".equals(levelCode)) {
						errorMsgs.add("Invalid value of Hierarchical Level Code in segment HL *"+ i +" : "+levelCode);
						setValid(false);
						continue;
					}else{
						ediInp856ItemVw = EdiInp856ItemView.createValue();
						ediInp856Vw.getItems().add(ediInp856ItemVw);
						extractItemIdentificationLIN(loop);
						extractItemShipmentDetailSN1(loop);
						extractItemIdentificationREF(loop);
					}
				}
			}
		}
		table = ts.getSummaryTable();
		if (table != null)
		{
			//extractHeaderTDS(table);
			//extractHeaderSAC(table);
		}
	}

	/** extract data from segment BIG that is part of the Header
	 *<br>Beginning Segment for Invoice used
	 *<br>To indicate the beginning of an invoice transaction set and transmit identifying numbers and dates
	 *@param inTable table containing this segment
	 *@throws OBOEException - most likely segment not found
	 */
	public void extractHeaderBSN(Table inTable)
	throws OBOEException
	{
		Segment segment;
		//valid = false;
		try { segment = inTable.getSegment("BSN");}
		catch (OBOEException oe) {
			errorMsgs.add("Segment BSN missing");
			setValid(false);
			return;
		}
		String purposeCode =
			getField(segment,1,true,"Missing Purpose Code in segment BSN");
		if(purposeCode==null) {
			String errorStr = "Purpose Code is null";
			errorMsgs.add(errorStr);
			setValid(false);
			return;
		}
		if(!purposeCode.equals("00")) {
			String errorStr = "Invalid value of Purpose Code in segment BSN: "+purposeCode;
			errorMsgs.add(errorStr);
			setValid(false);
			return;
		}
		ediInp856Vw.setPurposeCode(purposeCode);

		String shipmentIdentifier =
			getField(segment,2,true,"Missing Shipment Identification in segment BSN");
		if(shipmentIdentifier==null) {
			String errorStr = "Shipment Identifier is null";
			errorMsgs.add(errorStr);
			setValid(false);
			return;
		}
		ediInp856Vw.setTransactionIdentifier(shipmentIdentifier);

		String transactionDateS =
			getField(segment,3,true,"Missing Transaction Date in segment BSN");
		if(transactionDateS==null) {
			String errorStr = "Invalid value of Transaction Date in segment BSN: null";
			errorMsgs.add(errorStr);
			setValid(false);
			return;
		}

		if(transactionDateS.length()!=8){
			String errorStr = "Invalid value of Transaction Date in segment BSN: "+transactionDateS;
			errorMsgs.add(errorStr);
			setValid(false);
			return;
		}

		String transactionTimeS =
			getField(segment,4,true,"Missing Transaction Time in segment BSN");
		if(transactionTimeS==null) {
			String errorStr = "Invalid value of Transaction Time in segment BSN: "+transactionTimeS;
			errorMsgs.add(errorStr);
			setValid(false);
			return;
		}

		SimpleDateFormat sdf = null;
		if(transactionTimeS.length()>=6){
			if(transactionTimeS.length()>6) transactionTimeS = transactionTimeS.substring(0,6);
			sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		}else if(transactionTimeS.length()==4) {
			sdf = new SimpleDateFormat("yyyyMMddHHmm");
		} else {
			String errorStr = "Invalid value of Transaction Time in segment BSN: "+transactionTimeS;
			errorMsgs.add(errorStr);
			setValid(false);
			return;
		}

		String transactionDateTimeS = transactionDateS + transactionTimeS;
		Date transactionDate = null;
		try {
			transactionDate = sdf.parse(transactionDateTimeS);
		} catch (Exception exc) {
			errorMsgs.add("Invalid value of Transaction Date or Transaction Time: "+
					transactionDateS+" "+transactionTimeS);
			setValid(false);
			return;
		}
		if(!transactionDateTimeS.equals(sdf.format(transactionDate))) {
			errorMsgs.add("Invalid value of Transaction Date or Transaction Time: "+
					transactionDateS+" "+transactionTimeS);
			setValid(false);
			return;
		}

		ediInp856Vw.setTransactionDate(transactionDate);
		return;
	}

	protected String getField(Segment pSegment, int pFieldNum, boolean pMandatoryFl, String pErrorMessage) {
		DataElement de = null;
		String val = null;
		String err = null;

		try {
			de = pSegment.getDataElement(pFieldNum);
		} catch (Exception exc) {
			if(pMandatoryFl) {
				err = pErrorMessage+" (1)";
			}
		}
		if(err==null && de==null && pMandatoryFl) {
			err = pErrorMessage+" (2)";
		}
		if(err==null && de!=null) {
			try {
				val =  de.get();
			}catch(Exception exc) {}
			if (!Utility.isSet(val) && pMandatoryFl) {
				err = pErrorMessage+" (3)";
			}
		}
		if(err!=null) {
			errorMsgs.add(err);
		}
		return val;
	}

	public void extractShipmentLevelREF(Loop inLoop)
	throws OBOEException
	{
		Segment segment = null;
		try{
			int numberOfSegmentsInVector = inLoop.getCount("REF");
			for (int i = 0; i <  numberOfSegmentsInVector; i++)
			{
				segment = inLoop.getSegment("REF", i);
				String refIdentificatorQualifier =
					getField(segment,1,true,"Missing Reference Identificator Qualifier in segment REF*0");
				if(refIdentificatorQualifier==null) {
					errorMsgs.add("Reference Identificator Qualifier is null");
					setValid(false);
					return;
				}

				ediInp856Vw.setRefIdentQualif(refIdentificatorQualifier);
				String refIdentificator =
					getField(segment,2,true,"Missing Reference Identificator in segment REF*0");
				if(refIdentificator==null) {
					errorMsgs.add("Reference Identificator is null");
					setValid(false);
					return;
				}
				ediInp856Vw.setRefIdent(refIdentificator);
			}
		}catch (OBOEException oe) { return; }
	}

	public void extractShipmentDTM(Loop inLoop)
	throws OBOEException
	{
		Segment segment = null;
		//valid = false;
		int qty = inLoop.getSegmentCount("DTM");
		for(int ii=0; ii<qty; ii++) {
			try {
				segment = inLoop.getSegment("DTM",ii);
			}catch(Exception exc){}
			if(segment==null){
				errorMsgs.add("Segments DTM missing,"+ii);
				setValid(false);
				return;
			}
			String dateTimeQualifier =
				getField(segment,1,true,"Missing Date/Time Qualifier in segment DTM");
			if(dateTimeQualifier==null) {
				errorMsgs.add("Date/Time Qualifier is null");
				setValid(false);
				return;
			}
			if(!"011".equals(dateTimeQualifier)) {
				errorMsgs.add("Invalid value of Date/Time Qualifier in segment DTM: "+dateTimeQualifier);
				setValid(false);
				return;
			}

			String shipmentDateS =
				getField(segment,2,true,"Missing Shipment Date in segment DTM");
			if(shipmentDateS==null) {
				errorMsgs.add("Shipment Date is null");
				setValid(false);
				return;
			}
			if(shipmentDateS.length()!=8) {
				errorMsgs.add("Invalid value of Shipment Date: "+shipmentDateS);
				setValid(false);
				return;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date shipmentDate = null;
			try {
				shipmentDate = sdf.parse(shipmentDateS);
			} catch (Exception exc) {
				errorMsgs.add("Invalid value of Shipment Date: "+shipmentDateS);
				setValid(false);
				return;
			}
			if(!shipmentDateS.equals(sdf.format(shipmentDate))) {
				errorMsgs.add("Invalid value of Shipment Date: "+shipmentDateS);
				setValid(false);
				return;
			}
			ediInp856Vw.setShipDate(shipmentDate);
		}
		return;
	}

	public void extractShipmentTD5(Loop inLoop)
	throws OBOEException
	{
		Segment segment = null;
		try {
			int numberOfSegmentsInVector = inLoop.getCount("TD5");
			for (int i = 0; i <  numberOfSegmentsInVector; i++)
			{
				segment = inLoop.getSegment("TD5", i);
				if (segment == null)
					return;
				String carrierCode =
					getField(segment,2,true,"Missing Carrier Code"); //may not be right field but not used in integration services
				ediInp856Vw.setCarrierCode(carrierCode);
				String carrierName =
					getField(segment,5,false,"Missing Carrier Name");
				ediInp856Vw.setCarrierName(carrierName);
			}
		}catch(Exception exc){}
		return;
	}

	public void extractShipByN1(Loop inLoop)
	throws OBOEException
	{
		Loop loop = null;
		int numberInVector = inLoop.getCount("N1");
		for (int i = 0; i <  numberInVector; i++)
		{
			loop = inLoop.getLoop("N1", i);
			if (loop == null) return;
			Segment segment = loop.getSegment("N1");
			String qualif =   getField(segment,1,false,null);
			if("ST".equals(qualif)) {
				String shipToName =  getField(segment,2,false,null);
				ediInp856Vw.setShipToName(shipToName);
				String identQualifier =  getField(segment,3,false,null);
				if("01".equals(identQualifier)) {
					String shipToCode =  getField(segment,4,false,null);
					ediInp856Vw.setShipToCode(shipToCode);
				}
				extractShipFromN4(loop);
			} else {
				String distName =  qualif;
				ediInp856Vw.setDistName(distName);
				String distCodeQualifier =  getField(segment,2,false,null);
				ediInp856Vw.setDistIdentCodeQualif(distCodeQualifier);
				if(!Utility.isSet(distName) && !Utility.isSet(distCodeQualifier)) {
					errorMsgs.add("No distributor info in segment N1");
					setValid(false);
					return ;
				}
				if(Utility.isSet(distCodeQualifier)) {
					String distCode = getField(segment,3,false,null);
					ediInp856Vw.setDistIdentCode(distCode);
					if(!Utility.isSet(distCode) && !Utility.isSet(distName)) {
						errorMsgs.add("No distributor info in segment N1");
						setValid(false);
						return ;
					}
				}
			}
		}
	}


	public void extractShipFromN4(Loop inLoop)
	throws OBOEException
	{
		Segment segment = null;
		//valid = true;
		try {
			segment = inLoop.getSegment("N4");
		}catch(Exception exc){}
		if(segment==null){
			// The N4 ship from is optional.
			return;
		}
		String cityName =  getField(segment,1,false,null);
		ediInp856Vw.setShipFromCity(cityName);
		String stateProvinceCode =  getField(segment,2,false,null);
		ediInp856Vw.setShipFromState(stateProvinceCode);
		String postalCode =  getField(segment,3,false,null);
		ediInp856Vw.setShipFromPostalCode(postalCode);
		return;
	}

	public void extractPurchaseOrderReferencePRF(Loop inLoop)
	throws OBOEException
	{
		Segment segment = null;
		try {
			segment = inLoop.getSegment("PRF");
		}catch(Exception exc){}
		if(segment==null){
			return;
		}
		String purchaseOrderNumber =  getField(segment,1,false,null);
		if(Utility.isSet(ediInp856Vw.getDistOrderNum()) && !ediInp856Vw.getDistOrderNum().equals(purchaseOrderNumber)){
			ediHandler.appendIntegrationRequest(ediInp856Vw);
			ediInp856Vw = ediInp856Vw.copy();
			ediInp856Vw.setItems(new EdiInp856ItemViewVector());
		}
		ediInp856Vw.setDistOrderNum(purchaseOrderNumber);
		return;
	}

	public void extractReferenceIdentificationREF(Loop inLoop)
	throws OBOEException
	{
		int qty;
		try{
			qty = inLoop.getSegmentCount("REF");
		}catch(Exception e){
			return;
		}
		Segment segment = null;
		//valid = true;
		for(int ii=0; ii<qty; ii++) {
			try {
				segment = inLoop.getSegment("REF",ii);
			}catch(Exception exc){}
			if(segment==null){
				return;
			}
			String identQualifier =  getField(segment,1,false,null);
			String identification =  getField(segment,2,false,null);
			if("2I".equals(identQualifier) || "CN".equals(identQualifier)) {
				//error if both are sent and we are overwriting one with another
				if(Utility.isSet(ediInp856Vw.getTrackingNum())){
					errorMsgs.add("Attempt to overwrite tracking number:"+ediInp856Vw.getTrackingNum()+" with "+identification);
					setValid(false);
					return;
				}
				ediInp856Vw.setTrackingNum(identification);
			} else if("D2".equals(identQualifier)) {
				ediInp856Vw.setDistOrderNum(identification);
			} else if("ZZ".equals(identQualifier)) {
				ediInp856Vw.setMatchPoNumType(RefCodeNames.MATCH_PO_NUM_TYPE_CD.STORE_ERP_PO_NUM);
				ediInp856Vw.setPurchOrderNum(identification);
			}
		}
		return;
	}

	public void extractItemIdentificationLIN(Loop inLoop)
	throws OBOEException
	{
		Segment segment = null;
		boolean ignoreMissingLineInfo = Utility.isTrue(getTranslator().getConfigurationProperty(RefCodeNames.ENTITY_PROPERTY_TYPE.IGNORE_MISSING_LINE_INFO));
		//valid = false;
		try {
			segment = inLoop.getSegment("LIN");
		}catch(Exception exc){
			exc.printStackTrace();
		}
		if(segment==null && !ignoreMissingLineInfo){
			errorMsgs.add("Segment LIN missing");
			setValid(false);
			return;
		}
		String orderLineNumS =
			getField(segment,1,true,"Missing order line number in LIN segment");
		if(orderLineNumS==null && !ignoreMissingLineInfo) {
			errorMsgs.add("Order Line Number in LIN segment is null");
			setValid(false);
			return;
		}

		int orderLineNum = 0;
		try {
			orderLineNum = Integer.parseInt(orderLineNumS);
		} catch (Exception exc) {
			// JD China will use line num 3.1 for line 3 if order is split for shipment.
			int ix = orderLineNumS.indexOf('.');
			if (ix > 0){
				String orderLineNumSs = orderLineNumS.substring(0, ix);
				try{
					orderLineNum = Integer.parseInt(orderLineNumSs);
				} catch (Exception ex) {	
				}
			}
				
			if (orderLineNum == 0 && !ignoreMissingLineInfo){
				errorMsgs.add("Invalid value of order line number in LIN segment: "+orderLineNumS);
				setValid(false);
				return;
			}
		}

		ediInp856ItemVw.setPurchOrderLineNum(orderLineNum);
		for(int fieldNum =1 ; fieldNum<=7; fieldNum+=2) {
			try {
				String productNumQualifier = getField(segment,1 + fieldNum, false ,"");
				if(productNumQualifier==null) {
					//errorMsgs.add("Product Service Id Qualifier is null");
					//setValid(false);
					//return;
					break;
				}
				String productNum =  getField(segment,1 + fieldNum+1,false,"");
				if("VP".equals(productNumQualifier) || "VN".equals(productNumQualifier)) {
					ediInp856ItemVw.setDistSkuNum(productNum);
				}
			}catch(Exception exc) {
				break;
			}
		}
		return;
	}

	public void extractItemShipmentDetailSN1(Loop inLoop)
	throws OBOEException
	{
		Segment segment = null;
		//valid = false;
		try {
			segment = inLoop.getSegment("SN1");
		}catch(Exception exc){}
		if(segment==null){
			errorMsgs.add("Segment SN1 missing");
			setValid(false);
			return;
		}
		String shippedQtyS =
			getField(segment,2,true,"Missing Number of Units Shipped");
		if(shippedQtyS==null) {
			errorMsgs.add("Number of Units Shipped is null");
			setValid(false);
			return;
		}

		int shippedQty = 0;
		try {
			shippedQty = Integer.parseInt(shippedQtyS);
		} catch (Exception exc) {
			errorMsgs.add("Invalid value of item shipped number: "+shippedQtyS);
			setValid(false);
			return;
		}
		ediInp856ItemVw.setShippedQty(shippedQty);

		String uom =
			getField(segment,3,true,"Missing Unit (Basis) for Measurement Code");
		if(uom==null) {
			errorMsgs.add("UOM is null");
			setValid(false);
			return;
		}
		ediInp856ItemVw.setUom(uom);
		return;
	}


	public void extractItemIdentificationREF(Loop inLoop)
	throws OBOEException
	{
		try{
			int numberOfSegmentsInVector = inLoop.getCount("REF");

			Segment segment = null;

			ArrayList trackingNums = new ArrayList();
			for (int i = 0; i <  numberOfSegmentsInVector; i++)
			{
				segment = inLoop.getSegment("REF", i);
				if (segment == null)
					return;
				String identQualifier =  getField(segment,1,false,null);
				String identification =  getField(segment,2,false,null);
				if("2I".equals(identQualifier) || "CN".equals(identQualifier)) {
					trackingNums.add(identification);
				}
			}
			ediInp856ItemVw.setTrackingNumList(trackingNums);
		}catch(Exception e){
			return;
		}

		return;
	}



	public void processTransaction()
	throws Exception
	{
		InboundTranslate translator = getTranslator();
		TradingProfileData profile = translator.getProfile();
		int[] busEntIds = translator.getTradingPartnerBusEntityIds(profile.getTradingPartnerId(),
				RefCodeNames.TRADING_PARTNER_ASSOC_CD.DISTRIBUTOR);
		String busEntId;
		if(busEntIds.length > 0){
			busEntId= Integer.toString(busEntIds[0]);
		}else{
			busEntId="UNKNOWN";
		}
		OrderData orderD = null;
		boolean matchByVendorOrderNum = ediInp856Vw.getMatchPoNumType().equals(RefCodeNames.MATCH_PO_NUM_TYPE_CD.VENDOR_ORDER_NUM);
		String poNum = matchByVendorOrderNum ? ediInp856Vw.getDistOrderNum() : ediInp856Vw.getPurchOrderNum();
		orderD = getTranslator().getOrderDataByPoNum(poNum, null, null, ediInp856Vw.getMatchPoNumType());
		
		
		if (orderD == null){
			//String errorMess = "No purchase order found with po num: " + purchOrderNum;
			//errorMsgs.add(errorMess);
			//setValid(false);
		}else{		
			getTransactionD().setOrderId(orderD.getOrderId());
			ediInp856Vw.setOrderId(orderD.getOrderId());
		}
		getTransactionD().setKeyString("ErpPoNum: " + ediInp856Vw.getPurchOrderNum() +
				" DistPoNum: " + ediInp856Vw.getDistOrderNum()+ ", Distr Id: " + busEntId);
		
		// Pulling out ICN and GCN: Begin		
		int ICN = ediHandler.getProfile().getInterchangeControlNum(); 
		int GCN = ediHandler.getProfile().getGroupControlNum(); 
		ediInp856Vw.setInterchangeControlNum(ICN); 
		ediInp856Vw.setGroupControlNum(GCN); 
		log.info("ICN = " + ICN);
		log.info("GCN = " + GCN);
		// Pulling out ICN and GCN: End
		
		ediHandler.appendIntegrationRequest(ediInp856Vw);		
	}


}

