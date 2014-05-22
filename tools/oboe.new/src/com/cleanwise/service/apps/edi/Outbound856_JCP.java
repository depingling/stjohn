package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import java.rmi.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.apps.dataexchange.OutboundTranslate;

/**
 * Formats the order extraction into a EDI document,
 * the format specified by JCP.
 *
 * This is an initial version which has not been fully tested with JCP.
 * durval, 6/14/2006
 *
 *
 * @author Durval
 */
public class Outbound856_JCP extends Outbound856
{

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
		de.set(translator.getDateTimeAsString().substring(0, 8));
		de = (DataElement) segment.buildDE(4);  // 337 Time
		de.set(translator.getDateTimeAsString().substring(8));
		de = (DataElement) segment.buildDE(5);  // structure code for shipment
		// 0001, JCP defined as Pick Pack 856 structure
		de.set("0001");
	}

	public void buildShipperToConfirm(Loop inLoop) throws OBOEException {
		Segment segment = inLoop.createSegment("PID");
		inLoop.addSegment(segment);

		DataElement de;
		de = (DataElement) segment.buildDE(1);
		de.set("S");
		de = (DataElement) segment.buildDE(2);
		de.set("");
		de = (DataElement) segment.buildDE(3);
		de.set("VI");
		de = (DataElement) segment.buildDE(4);
		de.set("FL");

	}

	/**
	 * Builds HL segment for entire shipment.
	 * Hierarchical Level used to identify dependencies among the content
	 * of hierarchically related groups of data segments.
	 * @param inTable table containing this segment
	 * @throws OBOEException - most likely segment not found
	 */
	public void buildShipmentHL(Table inTable)
	throws OBOEException    {

		Loop loop = inTable.createLoop("HL");
		inTable.addLoop(loop);
		Segment segment = loop.createSegment("HL");
		loop.addSegment(segment);
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 628 Hierarchical ID Number
		de.set(String.valueOf(++hlCount));
		de = (DataElement) segment.buildDE(2);  // 734 Hierarchical Parent ID Number
		de.set("");
		de = (DataElement) segment.buildDE(3);  // 735 Hierarchical Level Code
		de.set("S");

		Segment segment1 = loop.createSegment("TD1");
		loop.addSegment(segment1);
		// JCP wants CTN, carton
		de = (DataElement) segment1.buildDE(1);	de.set("CTN25");
		// Total up the items in the invoice.
		de = (DataElement) segment1.buildDE(2);
		de.set(String.valueOf(mTotalItemsCount));
		de = (DataElement) segment1.buildDE(3);	de.set("");
		de = (DataElement) segment1.buildDE(4);	de.set("");
		de = (DataElement) segment1.buildDE(5);	de.set("");
		de = (DataElement) segment1.buildDE(6);	de.set("G");
		// total up the weight in the invoice.
		de = (DataElement) segment1.buildDE(7);
		de.set(String.valueOf(mTotalItemsWeight));
		de = (DataElement) segment1.buildDE(8);	de.set("LB");


		Segment segment2 = loop.createSegment("TD5");
		loop.addSegment(segment2);
		de = (DataElement) segment2.buildDE(1);	de.set("O");
		de = (DataElement) segment2.buildDE(2);	de.set("2");
		// MISC. UNLISTED CARRIERS (XZZZ)
		de = (DataElement) segment2.buildDE(3);	de.set("XZZZ");
		de = (DataElement) segment2.buildDE(4);	de.set("U");
		de = (DataElement) segment2.buildDE(5);	de.set("Cleanwise Inc");

		buildShipmentHLREF(loop);
		buildShipmentHLPER(loop);

		buildHLDTM(loop);
		buildHLN1(loop);
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
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 628 Hierarchical ID Number
		de.set(String.valueOf(++hlCount));
		de = (DataElement) segment.buildDE(2);  // 734 Hierarchical Parent ID Number
		de.set("1");
		de = (DataElement) segment.buildDE(3);  // 735 Hierarchical Level Code
		de.set("O");


		buildHLPRF(loop);
		buildShipperToConfirm(loop);
		buildOrderHLREF(loop);
		buildOrderN1(loop);
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

		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 628 Hierarchical ID Number
		de.set(""+(++hlCount));
		de = (DataElement) segment.buildDE(2);  // 734 Hierarchical Parent ID Number
		de.set("3");
		de = (DataElement) segment.buildDE(3);  // 735 Hierarchical Level Code
		de.set("I");

		buildHLLIN(loop);
		buildHLSN1(loop);
		buildItemTD5(loop);

		System.out.println( "currItemManifest=" + currItemManifest);


	}

	public void buildItemTD5(Loop inLoop) throws OBOEException {
		Segment segment = inLoop.createSegment("TD5");
		inLoop.addSegment(segment);

		DataElement de;
		int i = 0;

		// The 0-4 are null, 5th offset is the shipment/order status code [
		// CC = Shipment Complete
		// BP = Partial, Backorder to Follow
		// ]

		for (; i < 5; i++) {
			de = (DataElement) segment.buildDE(i + 1);
			de.set("");
		}

		if (currItem.getTotalQuantityOrdered()
				- currItem.getTotalQuantityShipped() <= 0) {
			// This line item has shipped in total, the nuber shipped is >=
			// the number of items ordered.
			de = (DataElement) segment.buildDE(1 + i++);
			de.set("CC");
		} else { // Some items are still outstanding.
			de = (DataElement) segment.buildDE(1 + i++);
			de.set("BP");
		}

	}

	public void buildPackHL(Table inTable)
	throws OBOEException
	{

		System.out.println(getClass().getName() + " 0 " +
		" buildPackHL " );

		Loop loop = inTable.createLoop("HL");
		inTable.addLoop(loop);
		Segment segment = loop.createSegment("HL");
		loop.addSegment(segment);

		DataElement de;
		de = (DataElement) segment.buildDE(1); 	de.set(""+(++hlCount));
		de = (DataElement) segment.buildDE(2); 	de.set("2");
		de = (DataElement) segment.buildDE(3); 	de.set("P");

		buildPackMAN(loop);

		System.out.println(getClass().getName() + " 1 " +
		" buildPackHL " );
	}

	public void buildPackMAN(Loop inLoop)
	throws OBOEException
	{

		System.out.println(getClass().getName() + " 0 " + " buildPackMAN " );

		DataElement de;
		try {
			Segment segment = inLoop.createSegment("MAN");
			inLoop.addSegment(segment);

			de = (DataElement) segment.buildDE(1); 	de.set("GM");

			String packageId = "000";
			if ( null != currItemManifest &&
					null != currItemManifest.getManifestItem() ) {
				packageId = String.valueOf
				(currItemManifest.getManifestItem().getPackageId());
			}
			String sscc = Utility.makeSSCC("0018283", packageId);
			String pad = "";
			while ( (pad.length() + sscc.length()) < 20 ) {
				pad = "0" + pad;
			}
			sscc = pad + sscc;

			de = (DataElement) segment.buildDE(2); 	de.set(sscc);
		} catch (Exception e ) {
			e.printStackTrace();
		}
		System.out.println(getClass().getName() + " 1 " +" buildPackMAN " );
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
		de = (DataElement) segment.buildDE(1); 	de.set("");
		de = (DataElement) segment.buildDE(2);  de.set("IN");
		de = (DataElement) segment.buildDE(3);
		de.set(""+Utility.extractPrimarySku(currItem));
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
		inLoop.addSegment(segment);
		DataElement de;

		String vItemUom = "";
		TradingPartnerData tp = ((OutboundTranslate)translator).getPartner();
		if(tp.getSkuTypeCd().equals(RefCodeNames.SKU_TYPE_CD.CUSTOMER) && Utility.isSet(currItem.getCustItemUom())){
			vItemUom = currItem.getCustItemUom();
		}else{
			vItemUom = currItem.getItemUom();
		}

		//	switch CS to CA
		if("CS".equals(vItemUom)){
			vItemUom = "CA";
		}

		de = (DataElement) segment.buildDE(1);  // 350 Assigned Identification
		de.set("");
		de = (DataElement) segment.buildDE(2);  // 382 Number of Units Shipped
		de.set(""+currInvoiceItem.getItemQuantity());
		de = (DataElement) segment.buildDE(3);  // 355 Unit or Basis for Measurement Code
		de.set(vItemUom);


		de = (DataElement) segment.buildDE(4);
		de.set("");
		de = (DataElement) segment.buildDE(5);  // Number of Units Ordered
		de.set(""+currItem.getTotalQuantityOrdered());
		de = (DataElement) segment.buildDE(6);  // 355 Unit or Basis for Measurement Code
		de.set(vItemUom);

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
		de.set(Utility.subString(t, 12));
		de = (DataElement) segment.buildDE(2);
		de.set(getOrderApprovalDateString());
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
		DataElement de;
		Segment segment = null;

		segment = inLoop.createSegment("REF");
		de = (DataElement) segment.buildDE(1);
		de.set("IA");
		de = (DataElement) segment.buildDE(2);
		de.set("018283");
		inLoop.addSegment(segment);

		segment = inLoop.createSegment("REF");
		de = (DataElement) segment.buildDE(1);
		de.set("DP");
		de = (DataElement) segment.buildDE(2);
		de.set("951");
		inLoop.addSegment(segment);

	}

	/**
	 * Builds REF segment that is part of the shipment HL. Reference
	 * Identification used To specify identifying information
	 *
	 * @param inSegment
	 *            segment containing this subsegment
	 * @throws OBOEException -
	 *             most likely segment not found
	 */
	public void buildShipmentHLREF(Loop inLoop)  throws OBOEException
	{
		Segment segment = inLoop.createSegment("REF");
		inLoop.addSegment(segment);  DataElement de;
		de = (DataElement) segment.buildDE(1);  de.set("BM");
		de = (DataElement) segment.buildDE(2);
		//
		// Set the bill of ladding to 0 since we don't have that information.
		// Per Jason and Mary on 7/10/2006.
		// Per Larry at JCP, 1 0 is sufficient.
		String t = "0";
		de.set(t);
	}

	public void buildShipmentHLPER(Loop inLoop)  throws OBOEException
	{
		// PER..IC....EM..EDI@ACME.COM
		Segment segment = inLoop.createSegment("PER");
		inLoop.addSegment(segment);
		DataElement de;
		de = (DataElement) segment.buildDE(1);
		de.set("IC");
		de = (DataElement) segment.buildDE(2);
		de.set("");
		de = (DataElement) segment.buildDE(3);
		de.set("EM");
		de = (DataElement) segment.buildDE(4);
		de.set("edi_issues@cleanwise.com");
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
		de = (DataElement) segment.buildDE(1);
		// 067 is Current Schedule Delivery, which is our
		// best guess at when this will be delivered.
		de.set("067");
		de = (DataElement) segment.buildDE(2);
		de.set(mkDateString(currInvoice.getInvoiceDate()));
		de = (DataElement) segment.buildDE(3);
		de.set(mkHourString(currInvoice.getInvoiceDate()));

	}

	/** builds segment N1 that is part of the HL
	 *<br>Name used
	 *<br>To identify a party by type of organization, name, and code
	 *param inSegment segment containing this subsegment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildHLN1(Loop inLoop) throws OBOEException {
		String sname = Utility.getEDIToken(currOrder.getOrderSiteName());
		buildN1Loop(inLoop,	"ST", "", "92", sname);
		// Ship from info is only needed for imports.
		//inSegment.addSegment(mkN1Segment(inSegment,
		// "SF", "Cleanwise", null,null));
		//inSegment.addSegment(buildHLN1N4Segment(inSegment));
	}

	public void buildOrderN1(Loop inLoop) throws OBOEException {

		String sname = Utility.getEDIToken(currOrder.getOrderSiteName());
		buildN1Loop(inLoop, "Z7", "", "92", sname);
		buildN1Loop(inLoop, "BT", "", "92", sname);
	}

	private Loop buildN1Loop(Loop inLoop, String n1_1, String n1_2,
			String n1_3, String n1_4) {

		Loop loop = inLoop.createLoop("N1");
		inLoop.addLoop(loop);
		Segment segment = loop.createSegment("N1");
		loop.addSegment(segment);
		DataElement de;
		de = (DataElement) segment.buildDE(1);
		de.set(n1_1);
		de = (DataElement) segment.buildDE(2);
		de.set(n1_2);
		if (n1_3 != null) {
			de = (DataElement) segment.buildDE(3);
			de.set(n1_3);
		}
		if (n1_4 != null) {
			de = (DataElement) segment.buildDE(4);
			de.set(n1_4);
		}
		return loop;
	}


	public Segment buildHLN1N4Segment(Loop inLoop) throws OBOEException {

		Segment segment = inLoop.createSegment("N4");
		DataElement de;
		de = (DataElement) segment.buildDE(1); // 19 City Name
		de.set("Marlborough");
		de = (DataElement) segment.buildDE(2); // 156 State or Province Code
		de.set("MA");
		de = (DataElement) segment.buildDE(3); // 116 Postal Code
		de.set("01752");
		return segment;

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
	throws OBOEException
	{
		hlCount = 0;
		Table table = ts.getHeaderTable();
		buildHeaderBSN(table);
		table = ts.getDetailTable();
		buildShipmentHL(table);
		buildOrderHL(table);
		
		currItemManifest = currEDIRequestD.getManifestItemView();
		buildPackHL(table);
		
		for (int i = 0; i < invoiceItems.size(); i++)	    {
			System.out.println("buildTransactionContent 0 inv item i=" + i);
			InvoiceCustDetailRequestData reqDetail = (InvoiceCustDetailRequestData)invoiceItems.get(i);
			
			currInvoiceItem = reqDetail.getInvoiceDetailD();
			currItem = reqDetail.getOrderItemD();
			buildItemHL(table);

			currInvoiceItem.setShipStatusCd(RefCodeNames.SHIP_STATUS_CD.SUCCESS);
			appendIntegrationRequest(currInvoiceItem); // for update the status
		}
		transactionD.setOrderId(currOrder.getOrderId());

		String keyString = new String
		("ErpOrderNum: " + currOrder.getErpOrderNum()
				+ ", FrontEndOrderNumber: " + currOrder.getOrderNum()
				+ ", CustomerPoNumber: " + currOrder.getRequestPoNum()
				+ ", InvoiceNumber: " + currInvoice.getInvoiceNum());

		transactionD.setKeyString(keyString);
	}

	public void buildTransactionTrailer()
	throws OBOEException
	{

		Table table = ts.getSummaryTable();
		buildSummaryCTT(table);
		buildSummarySE(table);
		fg.addTransactionSet(ts);
		
	}

	public void buildTransactions(int incomingProfileId)
	throws OBOEException, RemoteException
	{
		OutboundEDIRequestDataVector invoiceReqDV = getOutboundTransactionsToProcess();
		OutboundEDIRequestDataVector tempInvoiceReqDV = new OutboundEDIRequestDataVector();

		for (int i = 0; i < invoiceReqDV.size(); i++){
			currEDIRequestD = (OutboundEDIRequestData)invoiceReqDV.get(i);
			if (currEDIRequestD.getOrderD().getIncomingTradingProfileId()
					!= incomingProfileId){
				//System.out.println("ERROR, mismatched trading partners 2006-8-29 ");
				tempInvoiceReqDV.add(currEDIRequestD);
				continue;
			}


			currOrder = currEDIRequestD.getOrderD();
			currInvoice = currEDIRequestD.getInvoiceCustD();
			invoiceItems = currEDIRequestD.getInvoiceDetailDV();
			mTotalItemsWeight = 0;
			if ( currEDIRequestD.getManifestItemView() != null) {
				mTotalItemsWeight = ProductData.extractWeightValueInLbs
				(currEDIRequestD.getManifestItemView().getWeightString());
				System.out.println("mTotalItemsWeight=" +
						mTotalItemsWeight );
			}
			totalInvoiceItemsInfo(invoiceItems);

			mCustShipAddr = currEDIRequestD.getCustShipAddr();
			currInvoiceDist = currEDIRequestD.getInvoiceDistD();
			appendIntegrationRequest(currInvoice); // for update the status

			buildTransaction();
		}

		((OutboundTranslate)translator).setOutboundReqOrderDV(tempInvoiceReqDV);
	}

	private void totalInvoiceItemsInfo(java.util.List pInvItems) {
		mTotalItemsCount = 0;
		for (int i = 0; i < pInvItems.size(); i++)	    {

			InvoiceCustDetailRequestData reqDetail =
				(InvoiceCustDetailRequestData)pInvItems.get(i);

			InvoiceAbstractionDetailView currInvItem = reqDetail.getInvoiceDetailD();
			mTotalItemsCount += currInvItem.getItemQuantity();
		}
		System.out.println( "mTotalItemsCount=" + mTotalItemsCount);
	}

	private int mTotalItemsCount = 0, mTotalItemsWeight = 0;
}
