package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import java.text.SimpleDateFormat;
import com.cleanwise.service.api.util.*;
import java.math.BigDecimal;

/**
 * Formats the order extraction into a EDI document, the format specified by Lagase.
 * This is a direct decendant of Outbound810_JCP, except for migration concerns this is an updated version.
 * @author Deping
 */
public class Outbound810_JCP_v4030 extends Outbound810Super
{
	static final BigDecimal ZERO = new BigDecimal(0);
	
	public Outbound810_JCP_v4030(){
		mReviseOrderDate = true;
	}	

	/** builds segment BIG that is part of the Header
	 *<br>Beginning Segment for Invoice used
	 *<br>To indicate the beginning of an invoice transaction set and transmit identifying numbers and dates
	 *param inTable table containing this segment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildHeaderBIG(Table inTable)
	throws OBOEException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyyMMdd");
		Segment segment = inTable.createSegment("BIG");
		inTable.addSegment(segment);  DataElement de;
		de = (DataElement) segment.buildDE(1);  // 373 Date
		de.set(dateFormatter.format(currInvoice.getInvoiceDate()));
		de = (DataElement) segment.buildDE(2);  // 76 Invoice Number
		//For a credit jcpenney does not want a reference to original number,
		//they just want the original number as the credit number.
		if (creditFlag){
			de.set(currInvoice.getOriginalInvoiceNum());
		}else{
			de.set(currInvoice.getInvoiceNum());
		}

		de = (DataElement) segment.buildDE(3);  // 373 Date
		de.set(originalDateOrdered);
		de = (DataElement) segment.buildDE(4);  // 324 Purchase Order Number
		if ( null != currOrder.getRequestPoNum() ) {
			String t = currOrder.getRequestPoNum();

			de.set(stripEPRO(t));
		}

	}

	/** builds segment N1 that is part of the Header
	 *<br>Name used
	 *<br>To identify a party by type of organization, name, and code
	 * @param inTable table containing this segment
	 * @throws OBOEException - most likely segment not found
	 */
	public void buildHeaderN1(Table inTable, String code)
	throws OBOEException
	{
		if (!code.equals("RE") && !code.equals("BT")){
			return;
		}

		Loop loop = inTable.createLoop("N1");
		inTable.addLoop(loop);
		Segment segment = loop.createSegment("N1");
		loop.addSegment(segment);
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 98 Entity Identifier Code
		de.set(code);
		de = (DataElement) segment.buildDE(2);  // 93 Name
		if (code.equals("RE")){
			de.set("CLEANWISE");
		}
		else if (code.equals("BT")){
			de.set(mCustBillAddr.getAddress1());// customer ship name
			de = (DataElement) segment.buildDE(3);  // 66 Identification Code Qualifier
			de.set("92");
			de = (DataElement) segment.buildDE(4);  // 67 Identification Code

			String sname ;
			if(Utility.isSet(currOutboundReq.getCustomerBillingUnit())){
				sname = currOutboundReq.getCustomerBillingUnit();
			}else{
				sname = Utility.getEDIToken(currOrder.getOrderSiteName());
			}
			de.set(sname);
		}
	}

	/** builds segment ITD that is part of the Header
	 *<br>Terms of Sale/Deferred Terms of Sale used
	 *<br>To specify terms of sale
	 * @param inTable table containing this segment
	 * @throws OBOEException - most likely segment not found
	 */
	public void buildHeaderITD(Table inTable)
	throws OBOEException
	{
		Segment segment = inTable.createSegment("ITD");
		inTable.addSegment(segment);
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 336 Terms Type Code
		de.set("14");
		de = (DataElement) segment.buildDE(2);  // 333 Terms Basis Date Code
		de.set("");
		de = (DataElement) segment.buildDE(3);  // 338 Terms Discount Percent
		de.set("");
		de = (DataElement) segment.buildDE(4);  // 370 Terms Discount Due Date
		de.set("");
		de = (DataElement) segment.buildDE(5);  // 351 Terms Discount Days Due
		de.set("");
		de = (DataElement) segment.buildDE(6);  // 446 Terms Net Due Date
		de.set("");
		de = (DataElement) segment.buildDE(7);  // 386 Terms Net Days
		de.set("20");
		de = (DataElement) segment.buildDE(8);  // 362 Terms Discount Amount
		de.set("");
		de = (DataElement) segment.buildDE(9);  // 388 Terms Deferred Due Date
		de.set("");
		de = (DataElement) segment.buildDE(10);  // 389 Deferred Amount Due
		de.set("");
		de = (DataElement) segment.buildDE(11);  // 342 Percent of Invoice Payable
		de.set("");
		de = (DataElement) segment.buildDE(12);  // 352 Description
		de.set("NET 20");
	}


	/** builds segment FOB that is part of the Header
	 *<br>F.O.B. Related Instructions used
	 *<br>To specify transportation instructions relating to shipment
	 * @param inTable table containing this segment
	 * @throws OBOEException - most likely segment not found
	 */
	public void buildHeaderFOB(Table inTable)
	throws OBOEException
	{
		Segment segment = inTable.createSegment("FOB");
		inTable.addSegment(segment);
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 146 Shipment Method of Payment
		de.set("PC");
	}

	/** builds segment PID that is part of the Header
	 *<br>PID Product Item Description
	 *<br>To Specify Fair Labor Standards compliance for all products on invoice.
	 * @param inTable table containing this segment
	 * @throws OBOEException - most likely segment not found
	 */
	public void buildHeaderPID(Table inTable)
	throws OBOEException
	{
		Segment segment = inTable.createSegment("PID");
		inTable.addSegment(segment);
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 349 Item Description Type
		de.set("S");
		de = (DataElement) segment.buildDE(2);  // 750 Product Characteristic Code
		de = (DataElement) segment.buildDE(3);  // 559 Agency Qualifier Code
		de.set("VI"); //vics (www.vics.org)
		de = (DataElement) segment.buildDE(4);  // 751 Product Description Code
		de.set("FL"); //Compliant with the federal "Fair Labor Standards Act"
	}

	/** builds segment IT1 that is part of the Detail
	 *<br>Baseline Item Data (Invoice) used
	 *<br>To specify the basic and most frequently used line item data for the invoice and related transactions
	 *param inTable table containing this segment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildDetailIT1(Table inTable, String customerLineNumber)
	throws OBOEException {
		boolean isacredit = false;
		Loop loop = inTable.createLoop("IT1");
		inTable.addLoop(loop);
		Segment segment = loop.createSegment("IT1");
		loop.addSegment(segment);

		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 350 Assigned Identification
		de.set(customerLineNumber);
		de = (DataElement) segment.buildDE(2);  // 358 Quantity Invoiced
		if (currInvoiceItem.getItemQuantity() < 0){
			isacredit = true;
			de.set(""+currInvoiceItem.getItemQuantity()*(-1));
		}else{
			de.set(""+currInvoiceItem.getItemQuantity());
		}
		de = (DataElement) segment.buildDE(3);  // 355 Unit or Basis for Measurement Code
		de.set(""+Utility.getActualUom(currOutboundReq.getStoreType(), currItem));
		de = (DataElement) segment.buildDE(4);  // 212 Unit Price
		if(isacredit && currInvoiceItem.getCustContractPrice().compareTo(ZERO) > 0){
			de.set("-"+currInvoiceItem.getCustContractPrice());
		}else{
			de.set(""+currInvoiceItem.getCustContractPrice());
		}
		de = (DataElement) segment.buildDE(5);  // 639 Basis of Unit Price Code
		de.set("");
		de = (DataElement) segment.buildDE(6);  // 235 Product/Service ID Qualifier
		de.set("IN");
		de = (DataElement) segment.buildDE(7);  // 234 Product/Service ID
		de.set(Utility.getActualSkuNumber(currOutboundReq.getStoreType(),currItem));

		// JCP: that's the end of this segment but now we need an IT1
		// segment to go along with it (which USPS doesn't use)
		buildDetailIT1PID(loop);
	}



	/** builds segment PID that is part of the DetailIT1
	 *<br>Product/Item Description used
	 *<br>To describe a product or process in coded or free-form format
	 * @param inSegment segment containing this subsegment
	 * @throws OBOEException - most likely segment not found
	 */
	public void buildDetailIT1PID(Loop inLoop)  throws OBOEException
	{
		Loop loop = inLoop.createLoop("PID");
		inLoop.addLoop(loop);
		Segment segment = loop.createSegment("PID");
		loop.addSegment(segment);

		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 349 Item Description Type
		de.set("F");
		de = (DataElement) segment.buildDE(2);  // 750 Product/Process Characteristic Code
		de.set("08");
		de = (DataElement) segment.buildDE(3);  // 559 Agency Qualifier Code
		de.set("VI");
		de = (DataElement) segment.buildDE(4);  // 751 Product Description Code
		de.set("");
		de = (DataElement) segment.buildDE(5);  // 352 Description
		// Try for customer description but take generic description otherwise.
		// Truncate at 80 characters.
		String description = currItem.getCustItemShortDesc();
		if (!Utility.isSet(description)) {
			description = currInvoiceItem.getItemShortDesc();
		}
		de.set(Utility.subString(description, 80));
	}

	/** builds segment CAD that is part of the Summary
	 *<br>Carrier Detail used
	 *<br>To specify transportation details for the transaction
	 * @param inTable table containing this segment
	 * @throws OBOEException - most likely segment not found
	 */
	public void buildSummaryCAD(Table inTable)
	throws OBOEException
	{
		Segment segment = inTable.createSegment("CAD");
		inTable.addSegment(segment);
		DataElement de;
		/* segment.useDefault(); */
		de = (DataElement) segment.buildDE(1);  // 91 Transportation Method Code
		de.set("");
		de = (DataElement) segment.buildDE(2);  // 206 Equipment Initial
		de.set("");
		de = (DataElement) segment.buildDE(3);  // 207 Equipment Number
		de.set("");
		de = (DataElement) segment.buildDE(4);  // 140 Standard Carrier Alpha Code
		// JCP doesn't read the SCAC
		de.set("CNWS");
		de = (DataElement) segment.buildDE(5);  // 387 Routing
		de.set("CLEANWISE");
	}

	/** builds segment ISS that is part of the Summary
	 *<br>Invoice Shipment Summary used
	 *<br>To specify summary details of total items shipped in terms of
	 * quantity, weight, and volume
	 * @param inTable table containing this segment
	 * @throws OBOEException - most likely segment not found
	 */
	// JCP: Added (hard-coded data that looks -- and is -- bogus
	// but in fact is what we've agreed to send them
	public void buildSummaryISS(Table inTable)
	throws OBOEException
	{
		Loop loop = inTable.createLoop("ISS");
		inTable.addLoop(loop);
		Segment segment = loop.createSegment("ISS");
		loop.addSegment(segment);
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 382 Number of Units Shipped
		de.set("1");
		de = (DataElement) segment.buildDE(2);  // 355 Unit or Basis for Measurement Code
		de.set("CA");
	}


	/** builds segment REF that is part of the DetailIT1
	 *<br>Reference Identification used
	 *<br>To specify identifying information
	 *param inSegment segment containing this subsegment
	 *throws OBOEException - most likely segment not found
	 */
	// Subcasses must override if they need it (and also call it)
	public void buildDetailIT1REF(Segment inSegment)  throws OBOEException {};

	/** builds segment TDS that is part of the Summary
	 *<br>Total Monetary Value Summary used
	 *<br>To specify the total invoice discounts and amounts
	 *param inTable table containing this segment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildSummaryTDS(Table inTable)
	throws OBOEException {
		Segment segment = inTable.createSegment("TDS");
		inTable.addSegment(segment);


		//calculate the gross and net amounts ass defined by JCPenney
		BigDecimal discountAmount = ZERO;
		BigDecimal addOnAmount = ZERO;
		if(currInvoice.getMiscCharges() != null && currInvoice.getMiscCharges().compareTo(ZERO) < 0){
			discountAmount = discountAmount.add(currInvoice.getMiscCharges());
		}else{
			addOnAmount = addOnAmount.add(currInvoice.getMiscCharges());
		}
		if(currInvoice.getSalesTax() != null && currInvoice.getSalesTax().compareTo(ZERO) < 0){
			discountAmount = discountAmount.add(currInvoice.getSalesTax());
		}else{
			addOnAmount = addOnAmount.add(currInvoice.getSalesTax());
		}
		if(currInvoice.getFreight() != null && currInvoice.getFreight().compareTo(ZERO) < 0){
			discountAmount = discountAmount.add(currInvoice.getFreight());
		}else{
			addOnAmount = addOnAmount.add(currInvoice.getFreight());
		}
		BigDecimal netAmount = currInvoice.getSubTotal();
		BigDecimal grossAmount = currInvoice.getSubTotal();
		netAmount = netAmount.add(addOnAmount);
		netAmount = netAmount.add(discountAmount);
		grossAmount = grossAmount.add(addOnAmount);
		grossAmount = convertToNoDecimals(grossAmount);
		netAmount = convertToNoDecimals(netAmount);

		DataElement de;
		de = (DataElement)segment.buildDE(1);  // 610 Amount
		de.set(""+netAmount.intValue());

		de = (DataElement)segment.buildDE(2);  // 610 Amount
		de.set(""+grossAmount.intValue());
	}
	
}

