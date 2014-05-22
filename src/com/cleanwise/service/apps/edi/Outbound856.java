package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import java.text.SimpleDateFormat;
import com.cleanwise.service.api.util.*;

/**
 * Formats the order extraction into a EDI document, the format specified by Lagase.
 *
 * @author Deping
 */
public class Outbound856 extends OutboundSuper
{
	protected int hlCount; // keeps track of HL segment number
	protected SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyyMMdd");
	protected SimpleDateFormat timeFormatter = new SimpleDateFormat ("HHmm");
	protected InvoiceDistData currInvoiceDist;


	/** builds segment BSN that is part of the Header
	 *<br>Beginning Segment for Ship Notice used
	 *<br>To transmit identifying numbers, dates, and other basic data relating to the transaction set
	 *param inTable table containing this segment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildHeaderBSN(Table inTable)
	throws OBOEException
	{
		Segment segment = inTable.createSegment("BSN");
		inTable.addSegment(segment);  DataElement de;
		de = (DataElement) segment.buildDE(1);  // 353 Transaction Set Purpose Code
		de.set("00");
		de = (DataElement) segment.buildDE(2);  // 396 Shipment Identification
		de.set(currInvoice.getInvoiceNum()); // shipment id is same as invoice number here
		de = (DataElement) segment.buildDE(3);  // 373 Date
		de.set(getTranslator().getDateTimeAsString().substring(0, 8));
		de = (DataElement) segment.buildDE(4);  // 337 Time
		de.set(getTranslator().getDateTimeAsString().substring(8));
	}

	/**
	 * Builds HL segment for entire shipment.
	 * Hierarchical Level used to identify dependencies among the content
	 * of hierarchically related groups of data segments.
	 * @param inTable table containing this segment
	 * @throws OBOEException - most likely segment not found
	 */
	public void buildShipmentHL(Table inTable)
	throws OBOEException
	{
		Loop loop = inTable.createLoop("HL");
		inTable.addLoop(loop);
		Segment segment = loop.createSegment("HL");
		loop.addSegment(segment);
		buildShipmentHLREF(loop);
		buildHLDTM(loop);
		buildHLN1(loop);
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 628 Hierarchical ID Number
		de.set(String.valueOf(++hlCount));
		de = (DataElement) segment.buildDE(2);  // 734 Hierarchical Parent ID Number
		de.set("");
		de = (DataElement) segment.buildDE(3);  // 735 Hierarchical Level Code
		de.set("S");
	}

	/**
	 * Builds HL segment for a particular order.
	 * Hierarchical Level used To identify dependencies among the content
	 * of hierarchically related groups of data segments.
	 * @param inTable table containing this segment
	 * @throws OBOEException - most likely segment not found
	 */
	public void buildOrderHL(Table inTable)
	throws OBOEException
	{
		Loop loop = inTable.createLoop("HL");
		inTable.addLoop(loop);
		Segment segment = loop.createSegment("HL");
		loop.addSegment(segment);
		buildHLPRF(loop);
		buildOrderHLREF(loop);
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 628 Hierarchical ID Number
		de.set(String.valueOf(++hlCount));
		de = (DataElement) segment.buildDE(2);  // 734 Hierarchical Parent ID Number
		de.set("");
		de = (DataElement) segment.buildDE(3);  // 735 Hierarchical Level Code
		de.set("O");
	}

	/**
	 * Builds HL segment for a particular item in an order.
	 * Hierarchical Level used To identify dependencies among the content
	 * of hierarchically related groups of data segments.
	 * @param inTable table containing this segment
	 * @throws OBOEException - most likely segment not found
	 */
	public void buildItemHL(Table inTable)
	throws OBOEException
	{
		Loop loop = inTable.createLoop("HL");
		inTable.addLoop(loop);
		Segment segment = loop.createSegment("HL");
		loop.addSegment(segment);
		buildHLLIN(loop);
		buildHLSN1(loop);
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 628 Hierarchical ID Number
		de.set(""+(++hlCount));
		de = (DataElement) segment.buildDE(2);  // 734 Hierarchical Parent ID Number
		de.set("");
		de = (DataElement) segment.buildDE(3);  // 735 Hierarchical Level Code
		de.set("I");
	}

	/**
	 * Builds segment LIN that is part of the item HL.
	 * @param inSegment segment containing this subsegment
	 * @throws OBOEException - most likely segment not found
	 */
	public void buildHLLIN(Loop inLoop)
	throws OBOEException
	{
		Segment segment = inLoop.createSegment("LIN");
		inLoop.addSegment(segment);  DataElement de;
		de = (DataElement) segment.buildDE(1);  // 350 Assigned Identification
		de.set(""+currItem.getCustLineNum());
		de = (DataElement) segment.buildDE(2);  // 235 Product/Service ID Qualifier
		de.set("VP");
		de = (DataElement) segment.buildDE(3);  // 234 Product/Service ID
		de.set(""+Utility.getActualSkuNumber(currOutboundReq.getStoreType(),currItem));
	}

	/**
	 * Builds segment SN1 that is part of the item HL.
	 * Item Detail (Shipment) used
	 * to specify line-item detail relative to shipment
	 * @param inSegment segment containing this subsegment
	 * @throws OBOEException - most likely segment not found
	 */
	public void buildHLSN1(Loop inLoop)
	throws OBOEException
	{
		Segment segment = inLoop.createSegment("SN1");
		inLoop.addSegment(segment);  DataElement de;
		de = (DataElement) segment.buildDE(1);  // 350 Assigned Identification
		de.set("");
		de = (DataElement) segment.buildDE(2);  // 382 Number of Units Shipped
		de.set(""+currInvoiceItem.getItemQuantity());
		de = (DataElement) segment.buildDE(3);  // 355 Unit or Basis for Measurement Code
		de.set(Utility.getActualUom(currOutboundReq.getStoreType(), currItem));
	}

	/**
	 * Builds PRF segment that is part of the order HL.
	 * Purchase Order Reference used
	 * to provide reference to a specific purchase order
	 * @param inSegment segment containing this subsegment
	 * @throws OBOEException - most likely segment not found
	 */
	public void buildHLPRF(Loop inLoop)  throws OBOEException
	{
		Segment segment = inLoop.createSegment("PRF");
		inLoop.addSegment(segment);
		DataElement de;
		de = (DataElement) segment.buildDE(1);    // 324 Purchase Order Number

		// If the requested po number has an EPRO prefix,
		// remove the prefix.
		String t = stripEPRO(currOrder.getRequestPoNum());
		//we don't send alot of these so re-introduce the NA issue that is removed in
		//the integration services as testing for not sending this segment is not really worthwhile
		//for just USPS (only custoemr using this code right now).
		//If we want to use for others just sub-class this for postal and move the logic there
		if(!Utility.isSet(t)){
			t = "N/A";
		}
		de.set(Utility.subString(t, 12));
	}

	/**
	 * Builds REF segment that is part of the order HL.
	 * Reference Identification used
	 * to specify identifying information
	 * @param inSegment segment containing this subsegment
	 * @throws OBOEException - most likely segment not found
	 */
	public void buildOrderHLREF(Loop inLoop)
	{
		Segment segment = inLoop.createSegment("REF");
		inLoop.addSegment(segment);
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 128 Reference Identification Qualifier
		de.set("1V");
		de = (DataElement) segment.buildDE(2);  // 127 Reference Identification
		de.set(""+currOrder.getOrderNum());
	}

	/**
	 * Builds REF segment that is part of the shipment HL.
	 * Reference Identification used
	 * To specify identifying information
	 * @param inSegment segment containing this subsegment
	 * @throws OBOEException - most likely segment not found
	 */
	public void buildShipmentHLREF(Loop inLoop)  throws OBOEException
	{
		Segment segment = inLoop.createSegment("REF");
		inLoop.addSegment(segment);
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 128 Reference Identification Qualifier
		de.set("CN");
		de = (DataElement) segment.buildDE(2);  // 127 Reference Identification
		de.set(""+currOrder.getErpOrderNum());
	}

	/** builds segment DTM that is part of the HL
	 *<br>Date/Time Reference used
	 *<br>To specify pertinent dates and times
	 *param inSegment segment containing this subsegment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildHLDTM(Loop inLoop)  throws OBOEException
	{
		Segment segment = inLoop.createSegment("DTM");
		inLoop.addSegment(segment);
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 374 Date/Time Qualifier
		de.set("011");
		de = (DataElement) segment.buildDE(2);  // 373 Date
		de.set(dateFormatter.format(currInvoice.getInvoiceDate()));
	}

	/** builds segment N1 that is part of the HL
	 *<br>Name used
	 *<br>To identify a party by type of organization, name, and code
	 *param inSegment segment containing this subsegment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildHLN1(Loop inLoop)  throws OBOEException
	{
		Loop loop = inLoop.createLoop("N1");
		inLoop.addLoop(loop);
		Segment segment = loop.createSegment("N1");
		loop.addSegment(segment);
		buildHLN1N4(loop);

		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 98 Entity Identifier Code
		de.set("ST");
		de = (DataElement) segment.buildDE(2);  // 93 Name
		de.set(mCustShipAddr.getAddress1());
		de = (DataElement) segment.buildDE(3);  // 66 Identification Code Qualifier
		de.set("AA");
		de = (DataElement) segment.buildDE(4);  // 67 Identification Code

		String sname = Utility.getEDIToken(currOrder.getOrderSiteName());
		de.set(sname);

	}

	public void buildHLN1N4(Loop inLoop)  throws OBOEException
	{
		Segment segment = inLoop.createSegment("N4");
		inLoop.addSegment(segment);  DataElement de;
		de = (DataElement) segment.buildDE(1);  // 19 City Name
		if (currInvoiceDist == null || !Utility.isSet(currInvoiceDist.getShipFromCity())){
			de.set("Marlborough");
		}
		else{
			de.set(currInvoiceDist.getShipFromCity());
		}
		de = (DataElement) segment.buildDE(2);  // 156 State or Province Code
		if (currInvoiceDist == null || !Utility.isSet(currInvoiceDist.getShipFromState())){
			de.set("MA");
		}
		else{
			de.set(currInvoiceDist.getShipFromState());
		}
		de = (DataElement) segment.buildDE(3);  // 116 Postal Code
		if (currInvoiceDist == null || !Utility.isSet(currInvoiceDist.getShipFromPostalCode())){
			de.set("01752");
		}
		else{
			de.set(Utility.getNumStringByRemoveNoneNumChars(currInvoiceDist.getShipFromPostalCode()));
		}
	}

	/** builds segment CTT that is part of the Summary
	 *<br>Transaction Totals used
	 *<br>To transmit a hash total for a specific element in the transaction set
	 *param inTable table containing this segment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildSummaryCTT(Table inTable)
	throws OBOEException
	{
		Segment segment = inTable.createSegment("CTT");
		inTable.addSegment(segment);
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 354 Number of Line Items
		de.set(""+hlCount);
	}

	public void buildTransactionContent()
	throws Exception
	{
		currOrder = currOutboundReq.getOrderD();
		currInvoice = currOutboundReq.getInvoiceData();
		invoiceItems = currOutboundReq.getInvoiceDetailDV();
		mCustShipAddr = currOutboundReq.getCustShipAddr();
		currInvoiceDist = currOutboundReq.getInvoiceDistD();
		currItemManifest = currOutboundReq.getManifestItemView();
		appendIntegrationRequest(currInvoice); // for update the status
		
		Table table = ts.getHeaderTable();
		buildHeaderBSN(table);
		table = ts.getDetailTable();
		buildShipmentHL(table);
		buildOrderHL(table);

		for (int i = 0; i < invoiceItems.size(); i++)
		{
			InvoiceCustDetailRequestData reqDetail = (InvoiceCustDetailRequestData)invoiceItems.get(i);
			currInvoiceItem = reqDetail.getInvoiceDetailD();
			currItem = reqDetail.getOrderItemD();
			buildItemHL(table);
			currInvoiceItem.setShipStatusCd(RefCodeNames.SHIP_STATUS_CD.SUCCESS);
			appendIntegrationRequest(currInvoiceItem); // for update the status
		}
	}

	public void buildTransactionTrailer()
	throws Exception
	{
		Table table = ts.getSummaryTable();
		buildSummaryCTT(table);
		buildSummarySE(table);
		fg.addTransactionSet(ts);
		super.buildTransactionTrailer();
	}

}
