package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;
import com.americancoders.edi.x12.*;
import java.util.*;
import com.cleanwise.service.api.value.*;
import java.math.BigDecimal;
import com.cleanwise.service.api.util.*;


/** code to parse 810 transaction
 *<br>class 810 Advanced Ship Notice
 *<br>
 *@author Deping
 */
public class Inbound810Super extends InboundSuper
{
	// Processing variables
	protected InvoiceDistData currInvoice = InvoiceDistData.createValue();
	protected InvoiceDistDetailData currDetail;
	protected InvoiceDistDetailDataVector currDetails = new InvoiceDistDetailDataVector();
	protected String distributorCompanyCode;
	protected BigDecimal  controlTotalSumInvoice=new  BigDecimal(0);
	// XXX: This one tells us whether the SAC segment was found, in
	// which case the freight charge should be set.  Currently we're
	// not using it.
	protected boolean SACPresent = false;
	protected boolean currInvoiceIsACredit = false;
	protected String distSkuType = null; // can be set to VN, VP, PN... in the extended class
	protected String[] excludedSKUs = null; // list of sku be excluded from calculation
	protected BigDecimal excludedTotal = new  BigDecimal(0);

	/**
	 * Extract all segments included in ST - SE Segment
	 */
	public void extract()
	throws OBOEException
	{
		currInvoiceIsACredit = false;
		controlTotalSumInvoice=new BigDecimal(0);

		Table table = ts.getHeaderTable();
		if (table != null)
		{
			extractHeaderBIG(table);
			extractHeaderREF(table);
			extractHeaderN1(table);
		}
		table = ts.getDetailTable();
		if (table != null)
		{
			extractDetailIT1(table);
		}
		table = ts.getSummaryTable();
		if (table != null)
		{
			extractHeaderTDS(table);
			extractHeaderSAC(table);
			extractHeaderTXI(table);
		}
	}

	/** extract data from segment BIG that is part of the Header
	 *<br>Beginning Segment for Invoice used
	 *<br>To indicate the beginning of an invoice transaction set and transmit identifying numbers and dates
	 *@param inTable table containing this segment
	 *@throws OBOEException - most likely segment not found
	 */
	public void extractHeaderBIG(Table inTable)
	throws OBOEException
	{
		Segment segment = inTable.getSegment("BIG");
		if (segment == null){
			errorMsgs.add("Segment BIG missing");
			setValid(false);
			return;
		}
		DataElement de = segment.getDataElement(1);  // 373 Date
		if (!Utility.isSet(de.get())) {
			errorMsgs.add("Missing Invoice Date in segment BIG");
		} else {
			currInvoice.setInvoiceDate(stringToDate(de.get(), "yyyyMMdd", null));
		}

		de = segment.getDataElement(2);  // 76 Invoice Number
		if (!Utility.isSet(de.get())) {
			errorMsgs.add("Missing Invoice Number in segment BIG");
			setValid(false);
		} else {
			currInvoice.setInvoiceNum(de.get());
		}

		de = segment.getDataElement(4);  // 324 Purchase Order Number
		if (!Utility.isSet(de.get())) {
			errorMsgs.add("[Inbound810Super] Missing Cleanwise Purchase Order Number in segment BIG");
			setValid(false);
		} else {
			int ix = de.get().lastIndexOf("/");
			if (ix > 0)
			{
				currInvoice.setErpPoNum(de.get().substring(0, ix));
			}
			else {
				currInvoice.setErpPoNum(de.get());
			}
		}

		// XPEDX use element 5 and rest use element 7 for credit
		de = segment.getDataElement(5); //credit segment
		if(de != null && Utility.isSet(de.get())){
			if("CR".equalsIgnoreCase(de.get())){
				currInvoiceIsACredit = true;
				System.out.println("Setting currInvoiceIsACredit = "+currInvoiceIsACredit+" CR FOUND");
			}
		}

		if (!currInvoiceIsACredit){
			de = segment.getDataElement(7); //credit segment
			if(de != null && Utility.isSet(de.get())){	
				if("CR".equalsIgnoreCase(de.get())){
					currInvoiceIsACredit = true;
					System.out.println("Setting currInvoiceIsACredit = "+currInvoiceIsACredit+" CR FOUND");
				}
			}
		}
	}

	/** extract data from segment REF that is part of the Header
	 *<br>Reference Identification used
	 *<br>To specify identifying information
	 *param inTable table containing this segment
	 *@throws OBOEException - most likely segment not found
	 */
	public void extractHeaderREF(Table inTable)
	throws OBOEException
	{
		Segment segment;
		try {
			int numberOfSegmentsInVector = inTable.getSegmentCount("REF");
			for (int i = 0; i <  numberOfSegmentsInVector; i++)
			{
				segment = inTable.getSegment("REF", i);
				DataElement de;
				de = segment.getDataElement(1);     // 128 Reference Identification Qualifier
				if (Utility.isSet(de.get())) {
					// We only case about the REF segment if the identifier
					// is "SI" indicating the vendor shipment ID.  We'll use
					// it if it's here, otherwise we use the invoice ID as
					// the shipment ID.
					if (de.get().trim().equalsIgnoreCase("SI")) {
						de = segment.getDataElement(2);  // 127 Reference Identification
						if (Utility.isSet(de.get())) {
							currInvoice.setDistShipmentNum(de.get());
						}
					}
					if (de.get().trim().equalsIgnoreCase("DI")) { // Distributor Identifier, uniquly identifies
						// a distributor for anyone with multiple distributors
						//under 1 umbrella which we want to validate off of.

						de = segment.getDataElement(2);  // 127 Reference Identification
						if (Utility.isSet(de.get())) {
							distributorCompanyCode = de.get().trim();
						}
					}
				}
			}
		} catch (OBOEException oe) { return; }
	}

	/** extract data from segment N1 that is part of the Header
	 *<br>Name used
	 *<br>To identify a party by type of organization, name, and code
	 *param inTable table containing this segment
	 *@throws OBOEException - most likely segment not found
	 */
	public void extractHeaderN1(Table inTable)
	throws OBOEException
	{
		Loop loop;
		boolean shipToFound = false, shipFromFound = false, billToFound = false;

		try {
			int numberInVector = inTable.getCount("N1");
			for (int i = 0; i <  numberInVector; i++)
			{
				loop = inTable.getLoop("N1", i);
				if (loop == null) return;
				Segment segment = loop.getSegment("N1");
				DataElement de = segment.getDataElement(1);       // 98 Entity Identifier Code
				if (!Utility.isSet(de.get())) {
					errorMsgs.add("Missing Entity Identifier Code in N1 segment");
					continue;
				}

				String code = de.get();
				de = segment.getDataElement(2);       // 93 Name
				if (Utility.isSet(de.get())) {
					if (code.equalsIgnoreCase("ST")) {
						shipToFound = true;
						currInvoice.setShipToName(de.get());
						extractHeaderN1N3(code, loop);
						extractHeaderN1N4(code, loop);
					} else if (code.equalsIgnoreCase("SF")) {
						shipFromFound = true;
						currInvoice.setShipFromName(de.get());
						extractHeaderN1N4(code, loop);
					}
				}
			}
			if (!shipToFound) {
			}
			if (!shipFromFound) {
			}
		}catch (OBOEException oe) { return; }
	}

	/** extract data from segment N3 that is part of the HeaderN1
	 *<br>Address Information used
	 *<br>To specify the location of the named party
	 *param inSegment segment containing this subsegment
	 *@throws OBOEException - most likely segment not found
	 */
	public void extractHeaderN1N3(String id, Loop inLoop)  throws OBOEException {
		Segment segment;
		int numberOfSegmentsInVector;
		try {
			try{
				numberOfSegmentsInVector = inLoop.getSegmentCount("N3");
			}catch (OBOEException oe){
				if (id.equals("ST")) {
					//errorMsgs.add("Segment N3 missing for Ship to");
				} else if (id.equals("BT")) {
					//errorMsgs.add("Segment N3 missing for Bill to");
				}
				return;
			}
			for (int i = 0; i <  1; i++) {
				segment = inLoop.getSegment("N3", i);
				DataElement de;
				de = segment.getDataElement(1);       // 166 Address Information
				if (!Utility.isSet(de.get())) {
					errorMsgs.add("Missing Address Information in N3 segment for " + id);
				} else {
					if (id.equals("ST")) {
						currInvoice.setShipToAddress2(de.get());
					}

					de = segment.getDataElement(2);   // 166 Address Information
					if (de != null && Utility.isSet(de.get())) {
						if (id.equals("ST")) {
							currInvoice.setShipToAddress3(de.get());
						}
					}
				}
			}
		}
		catch (OBOEException oe) { return; }
	}

	/** extract data from segment N4 that is part of the HeaderN1
	 *<br>Geographic Location used
	 *<br>To specify the geographic place of the named party
	 *param inSegment segment containing this subsegment
	 *@throws OBOEException - most likely segment not found
	 */
	public void extractHeaderN1N4(String id, Loop inLoop)  throws OBOEException {

		Edi.log ("X1.2 extractHeaderN1N4\n  id=" + id + "\n  Loop=" + inLoop.get() );

		Segment segment = inLoop.getSegment("N4");
		if (segment == null)
		    return;

		DataElement de = segment.getDataElement(1);  // 19 City Name
		if (de == null || !Utility.isSet(de.get())) {
			errorMsgs.add("Missing City Name in N4 segment for " + id);
		} else {
			if (id.equals("ST")) {
				currInvoice.setShipToCity(de.get());
			} else if (id.equals("SF")) {
				currInvoice.setShipFromCity(de.get());
			}
		}

		de = segment.getDataElement(2);  // 156 State or Province Code
		if (de == null || !Utility.isSet(de.get())) {
			errorMsgs.add("Missing State or Province in N4 segment for " + id);
		} else {
			if (id.equals("ST")) {
				currInvoice.setShipToState(de.get());
			} else if (id.equals("SF")) {
				currInvoice.setShipFromState(de.get());
			}
		}

		de = segment.getDataElement(3);  // 116 Postal Code
		if (de == null || !Utility.isSet(de.get())) {
			errorMsgs.add("Missing Postal Code in N4 segment for " + id);
		} else {
			if (id.equals("ST")) {
				currInvoice.setShipToPostalCode(de.get());
			} else if (id.equals("SF")) {
				currInvoice.setShipFromPostalCode(de.get());
			}
		}
	}

	/** extract data from segment IT1 that is part of the Detail
	 *<br>Baseline Item Data (Invoice) used
	 *<br>To specify the basic and most frequently used line item data for the invoice and related transactions
	 *param inTable table containing this segment
	 *@throws OBOEException - most likely segment not found
	 */
	public void extractDetailIT1(Table inTable)
	throws OBOEException
	{
		Loop loop = null;
		Segment segment;
		int numberInVector=0;

		try {
			numberInVector = inTable.getCount("IT1");    	  
		} catch (OBOEException oe) {
			//errorMsgs.add("Segment IT1 missing");
			//valid = false;
			//return;
		}

		for (int i = 0; i <  numberInVector; i++)
		{
			loop = inTable.getLoop("IT1", i);
			if (loop == null) return;
			currDetail = InvoiceDistDetailData.createValue();

			segment = loop.getSegment("IT1");
			extractDetailIT1PID(loop);
			extractDetailIT1CAD(loop);

			DataElement de;
			de = segment.getDataElement(1);       // 350 Assigned Identification
			if (de != null) {
				if(Utility.isSet(de.get())){
					currDetail.setDistLineNumber(Integer.parseInt(de.get()));
				}else{
					currDetail.setDistLineNumber(0);
				}
			}


			// Set the quantity to an invalid value.
			currDetail.setDistItemQuantity(-9999);

			de = segment.getDataElement(2);       // 358 Quantity Invoiced
			if (Utility.isSet(de.get())) {
				String qty = de.get();
				qty = qty.trim();
				try{
					currDetail.setDistItemQuantity(Integer.parseInt(qty));
				}catch(NumberFormatException e){
					currDetail.setDistItemQuantity(0);
					System.out.println("Caught number format exception...continuing with processing!");
					e.printStackTrace();
				}
			}

			de = segment.getDataElement(3);       // 355 Unit or Basis for Measurement
			if ( de == null ) {
				System.out.println("2005.6.28 (de is null) segment missing "
						+ ", segment=" + segment );

			}

			if (Utility.isSet(de.get())) {
				currDetail.setDistItemUom(de.get());
			}

			de = segment.getDataElement(4);       // 212 Unit Price
			if (de != null && Utility.isSet(de.get())) {
				currDetail.setItemReceivedCost(new BigDecimal(de.get()));
				
				// If quantity is negative (XXX: for Lagasse) then this is
				// a credit item.  We'll change the quantity to a positive
				// value and the amount to a negative value.
				if (currDetail.getDistItemQuantity() < 0) {
					currInvoiceIsACredit = true;
					System.out.println("Setting currInvoiceIsACredit = "+currInvoiceIsACredit+" dist qty");
				}
			}

			String distItemSkuNum = null;
			// The next three data elements are preceded by a "product/service id
			// qualifier" field that indicates what is actually contained in the
			// data field (which follows the qualifier).  Handle these in a loop.
			for (int j = 6; j < segment.getDataElementSize(); j++) { //note the 100 is an aribtrary number
				try{
					de = segment.getDataElement(j++); // 235 Product/Service ID Qualifier
				}catch(Exception e){
					e.printStackTrace();
				}
				if (de != null && Utility.isSet(de.get())) {
					String code = de.get();
					de = segment.getDataElement(j);       // 234 Product/Service ID
					if (de==null || !Utility.isSet(de.get())) {
						;
					} else if (code.equals("BP")) {  //also CB (Buyers Catalog?) IN (Buyers Invantory Number?)
						try{
							currDetail.setItemSkuNum(Integer.parseInt(de.get()));
						}catch (NumberFormatException ne){};
					} else{
						if (distSkuType != null){
							if (distSkuType.equals(code)){
								distItemSkuNum = de.get();
							}
						}else{
							if (code.equals("VN")) {  //vendor item number
								distItemSkuNum = de.get();
							} else if (code.equals("VP")) {  //vendor part number
								distItemSkuNum = de.get();
							}
						}
					}
					//} else if (code.equals("VC")) {  //vendor catalog number
					//    currDetail.setDistItemSkuNum(de.get());
					//}
					/*else if (code.equals("PL")) {
                currDetail.setErpPoLineNum(Integer.parseInt(de.get()));
            }*/
				}
			}
			
			if (distItemSkuNum != null){
				BigDecimal quantity=new BigDecimal(currDetail.getDistItemQuantity()).abs();
				BigDecimal lineTotal = currDetail.getItemReceivedCost() == null ? new BigDecimal(0) : currDetail.getItemReceivedCost().multiply(quantity);	
				if (Utility.isInArray(distItemSkuNum, excludedSKUs)){					
					excludedTotal = excludedTotal.add(lineTotal);
					System.out.println("Excluded Sku=" + distItemSkuNum);							
					continue;
				}else{
					currDetail.setDistItemSkuNum(distItemSkuNum);
					currDetail.setInvoiceDistSkuNum(distItemSkuNum);
					controlTotalSumInvoice= controlTotalSumInvoice.add(lineTotal);
				}
			}
			currDetails.add(currDetail);
			Edi.log("X1.3: currDetail: DistLineNumber=" + currDetail.getDistLineNumber() 
					+ ", ItemSkuNum=" + currDetail.getItemSkuNum() 
					+ ", DistItemSkuNum=" + currDetail.getDistItemSkuNum()
					+ ", DistItemQuantity=" + currDetail.getDistItemQuantity()
					+ ", ItemReceivedCost=" + currDetail.getItemReceivedCost());
		}
	}

	/** extract data from segment PID that is part of the DetailIT1
	 *<br>Product/Item Description used
	 *<br>To describe a product or process in coded or free-form format
	 *param inSegment segment containing this subsegment
	 *@throws OBOEException - most likely segment not found
	 */
	public void extractDetailIT1PID(Loop inLoop)  throws OBOEException
	{
		Loop loop;
		try {
			int numberInVector = inLoop.getCount("PID");
			StringBuffer itemDesc = new StringBuffer(currDetail.getDistItemShortDesc());
			for (int i = 0; i <  numberInVector; i++) {
				loop = inLoop.getLoop("PID", i);
				if (loop == null) return;
				Segment segment = loop.getSegment("PID");
				DataElement de;
				// Don't validate item description type.  Should always be 'F'
				// for 'Free Form Text'.
				de = segment.getDataElement(1);       // 349 Item Description Type
				de = segment.getDataElement(5);       // 352 Description
				if (de != null && Utility.isSet(de.get())) {
					itemDesc.append(de.get());
					itemDesc.append(" ");
				}
			}
			currDetail.setDistItemShortDesc(itemDesc.toString());
		} catch (OBOEException oe) { return; }
	}

	/** extract data from segment TDS that is part of the Header
	 *<br>Total Monetary Value Summary used
	 *<br>To specify the total invoice discounts and amounts
	 *param inTable table containing this segment
	 *@throws OBOEException - most likely segment not found
	 */
	public void extractHeaderTDS(Table inTable)
	throws OBOEException
	{
		Segment segment = inTable.getSegment("TDS");
		if (segment == null){
			errorMsgs.add("Segment TDS missing");
			setValid(false);
			return;
		}
		DataElement de = segment.getDataElement(1);  // 610 Amount
		if (Utility.isSet(de.get())) {
			// Check to make sure value is numeric but doesn't have decimal
			// point by casting to Integer first
			Integer intVal = new Integer(de.get());
			// Now set the total, accounting for the "implied" decimal place
			currInvoice.setSubTotal((new BigDecimal(de.get())).movePointLeft(2).subtract(excludedTotal));
			if(currInvoice.getSubTotal().compareTo(new BigDecimal(0)) < 0)
			{
				currInvoiceIsACredit = true;
				System.out.println("Setting currInvoiceIsACredit = "+currInvoiceIsACredit+" subtotal");
			}
		}
	}


	/**
	 * extract data from segment TDS that is part of the Header <br>
	 * Total Monetary Value Summary used <br>
	 * To specify the total invoice discounts and amounts param inTable table
	 * containing this segment
	 *
	 * @throws OBOEException - when error in parsing happens
	 */
	public void extractHeaderTXI(Table inTable) throws OBOEException {
		int numberOfSegmentsInVector = 0;
		try {
			numberOfSegmentsInVector = inTable.getSegmentCount("TXI");
		} catch (OBOEException oe) {
			return;
		}// just not there, no error

		if (numberOfSegmentsInVector == 0)
			return;

		BigDecimal tax = new BigDecimal(0.00);
		for (int i = 0; i < numberOfSegmentsInVector; i++) {
			Segment segment = inTable.getSegment("TXI", i);
			DataElement de;
			//XXX should de.get(0) be checked?  Codes in IDList963.xml list
			//many difffernt types.  for example LS = Locacl and State, AA = Stadium tax, etc.
			//currently all of these are shoved into the generic "tax" bucket
			de = segment.getDataElement(2); // 610 Amount
			if (Utility.isSet(de.get())) {
				tax = Utility.addAmt(tax, new BigDecimal(de.get()));
				controlTotalSumInvoice = controlTotalSumInvoice.add(tax.abs());
			}
		}
		if (tax.doubleValue() > 0.0){
			if(Utility.isSet(currInvoice.getSalesTax()) && !tax.equals(currInvoice.getSalesTax())){
				errorMsgs.add("Tax arbitration error: "+currInvoice.getSalesTax()+" and "+tax+" both charged");
			}else{
				currInvoice.setSalesTax(tax);
			}
		}
	}


	/** extract data from segment CAD that is part of the Header
	 *<br>Carrier Detail used
	 *<br>To specify transportation details for the transaction
	 *param inTable table containing this segment
	 *@throws OBOEException - most likely segment not found
	 */
	public void extractDetailIT1CAD(Loop inLoop)
	throws OBOEException
	{
		Segment segment;
		try {
			// XXX: Note we accept multiple header-level CAD segments,
			// which is against the 810 standard.  Lagasse sends a separate
			// CAD segment for each package in the order, and they can't
			// tie them back to line items.
			int numberOfSegmentsInVector = inLoop.getCount("CAD");

			for (int i = 0; i <  numberOfSegmentsInVector; i++)
			{
				segment = inLoop.getSegment("CAD", i);
				DataElement de;

				// XXX: According to what we think we're getting from Lagasse
				// this is the ship-from city.  Not sure why the OBOE code has
				// this labelled "Equipment Number".  Also note that we might
				// get N1,N3,N4 headers from Lagasse for "SF" (ship-from)
				// address data, in which case that should take precedence
				// over whatever we get here.
				/*de = segment.getDataElement(2);  // 207 Equipment Number
        if (Utility.isSet(de.get())) {
            currInvoice.setShipFromCity(de.get());
        }*/
				de = segment.getDataElement(4);  // 140 Standard Carrier Alpha Code
				if (Utility.isSet(de.get())) {
					currInvoice.setScac(de.get());
				}
				de = segment.getDataElement(5);  // 387 Routing
				if (Utility.isSet(de.get())) {
					currInvoice.setCarrier(de.get());
				}

				de = segment.getDataElement(7);  // 128 Reference Identification Qualifier
				if (Utility.isSet(de.get())) {
					currInvoice.setTrackingType(de.get());
				}
				de = segment.getDataElement(8);  // 127 Reference Identification
				if (Utility.isSet(de.get())) {
					// Multiple tracking numbers will be stored in the same
					// field, separated by commas.
					// XXX: Note there is no such logic for the rest of the
					// fields in the rest of this segment.  We assume they
					// will be the same on all CAD segments but we're not
					// verifying, and whatever is in the last CAD segment
					// will be what's stored in the database.
					String currentVal = currInvoice.getTrackingNum();
					if (Utility.isSet(currentVal)) {
						currInvoice.setTrackingNum(currentVal + "," + de.get());
					} else {
						currInvoice.setTrackingNum(de.get());
					}
				}
			}
		} catch (OBOEException oe) { return; }
	}

	/** extract data from segment SAC that is part of the Header
	 *<br>Service, Promotion, Allowance, or Charge Information used
	 *<br>To request or identify a service, promotion, allowance, or charge; to specify the amount or percentage for the service, promotion, allowance, or charge
	 *param inTable table containing this segment
	 *@throws OBOEException - most likely segment not found
	 */
	public void extractHeaderSAC(Table inTable)
	throws OBOEException
	{
		Loop loop;
		try {

			// For Lagasse this segment will only be present if there is
			// actually a freight charge.
			int numberInVector = inTable.getCount("SAC");
			for (int i = 0; i <  numberInVector; i++)
			{
				loop = inTable.getLoop("SAC", i);
				SACPresent = true;
				Segment segment = loop.getSegment("SAC");
				DataElement de;
				de = segment.getDataElement(1); // 248 Allowance or Charge Indicator
				if (Utility.isSet(de.get())) {
					// We're only interested in indicators of "C" (indicating a
					// charge) and codes of D240 (indicating a freight amount),
					// in which case we will pull the amount from the 5th field.
					// XXX: This is in Lagasse's case of course.
					String indicator = de.get();

					//known indicators are:
					//A = Allowance
					//C = Charge
					//N = No allowance or charge
					//P = PromotionQ = Charge Request
					//R = Allowance Request
					//S = Service
					if ("C".equals(indicator) || "A".equals(indicator) || "P".equals(indicator)||
							"Q".equals(indicator) || "R".equals(indicator) || "S".equals(indicator)) {
						de = segment.getDataElement(2); // 1300 Service, Promotion, Allowance, or Charge Code
						if (Utility.isSet(de.get())) {
							String code = de.get();
							if (code.equals(FREIGHT_SAC)) {
								currInvoice.setFreight(getAmountFromHeaderSACSegment(segment,de));
							}else if (code.equals(MISC_SAC)) {
								currInvoice.setMiscCharges(getAmountFromHeaderSACSegment(segment,de));
							}else if (code.equals(SALES_TAX_SAC)) {
								currInvoice.setSalesTax(getAmountFromHeaderSACSegment(segment,de));
							}else
							{   // this error  message  will be added, only if check in InboundEdiHandler.java is not carried out
								errorMsgs.add("Invoice rejected.Unknown type Service, Promotion, Allowance, or Charge Code ( "+code+" )");
								setValid(false);
								System.out.println("Invoice rejected.Unknown type Service, Promotion, Allowance, or Charge Code ( "+code+" )");
								// Edi.log("Error: Unknown type Service, Promotion, Allowance, or Charge Code");
								//throw new OBOEException("Invoice rejected.Unknown type Service, Promotion, Allowance, or Charge Code ( "+code+" )");
							}
						}
					}else{
						if(Utility.isSet(indicator) && !"N".equals(indicator)){
							de = segment.getDataElement(2);
							if(Utility.isSet(de.get())){
								de = segment.getDataElement(5); // 610 Amount
								if (Utility.isSet(de.get())) {
									throw new RuntimeException("Unknown SAC indicator: "+indicator);
								}
							}
						}
					}
				}

			}
		} catch (OBOEException oe) { return; }
	}

	private BigDecimal getAmountFromHeaderSACSegment(Segment segment, DataElement de){
		int segCt = segment.getDataElementSize();
		//getSegmentCount
		for(int subI = 5;subI<=segCt;subI++){
			de = segment.getDataElement(subI); // 610 Amount
			if (de != null && Utility.isSet(de.get())) {
				try{
					Integer.parseInt(de.get());
					BigDecimal amount=new BigDecimal(de.get());
					amount = amount.movePointLeft(2);
					controlTotalSumInvoice = controlTotalSumInvoice.add(amount.abs());
					return amount;
				}
				catch(NumberFormatException e){
					System.out.println("Found something non-numberic at or after position 4");
				}
			}
		}
		return new BigDecimal(0).movePointLeft(2);
	}

	public void processTransaction()
	{
		currInvoice.setInvoiceDistSourceCd(RefCodeNames.INVOICE_DIST_SOURCE_CD.EDI);
		InvoiceRequestData reqInvoice = new InvoiceRequestData();
		reqInvoice.setInvoiceD(currInvoice);
		reqInvoice.setInvoiceDetailDV(currDetails);
		reqInvoice.setSkuTypeCd(getTranslator().getPartner().getSkuTypeCd());
		reqInvoice.setDistributorCompanyCode(distributorCompanyCode);
		reqInvoice.setControlTotalSum(controlTotalSumInvoice);
		OrderData orderD = getTranslator().getOrderDataByErpPoNum(currInvoice.getErpPoNum());
		int orderId = 0;
		int storeId = 0;
		String erpSystemCd = null;
		if(orderD!=null) {
			orderId = orderD.getOrderId();
			storeId = orderD.getStoreId();
			erpSystemCd = orderD.getErpSystemCd();
		}
		mHandler.transactionD.setOrderId(orderId);
		mHandler.transactionD.setKeyString("ErpPoNum: " + currInvoice.getErpPoNum()
				+ ", InvoiceNum: " + currInvoice.getInvoiceNum()
				+ ", DistShipmentNum: " + currInvoice.getDistShipmentNum());
		currInvoice.setStoreId(storeId);
		currInvoice.setErpSystemCd(erpSystemCd);
		//If this was a credit go through and setup the monitary and quantity values such that it is standardized,
		//qty always positive, qty always negative, and subtotal always negative
		if(currInvoiceIsACredit){
			//set the details appropriatly
			Iterator it = currDetails.iterator();
			while(it.hasNext()){
				currDetail = (InvoiceDistDetailData)it.next();
				if(currDetail.getDistItemQuantity() > 0){
					currDetail.setDistItemQuantity(-1 * currDetail.getDistItemQuantity());
				}
				if(currDetail.getItemReceivedCost() == null){
					currDetail.setItemReceivedCost(new BigDecimal(0));
				}
				currDetail.setItemReceivedCost(currDetail.getItemReceivedCost().abs());
			}
			//set the header appropriatly
			if(currInvoice.getSubTotal() != null){
				currInvoice.setSubTotal(currInvoice.getSubTotal().abs().negate());
			}

		}
		
		BigDecimal runingTotal = currInvoiceIsACredit ? reqInvoice.getControlTotalSum().negate() : reqInvoice.getControlTotalSum();
		System.out.println("[Inbound810Super::processTransaction] control total sum = "+ runingTotal);
		System.out.println("[Inbound810Super::processTransaction] sub total = "+currInvoice.getSubTotal());
		System.out.println("[Inbound810Super::processTransaction] difference = "+runingTotal.subtract(currInvoice.getSubTotal()));
		System.out.println("[Inbound810Super::processTransaction] excludedTotal = "+excludedTotal);
		mHandler.appendIntegrationRequest(reqInvoice);
	}

}
