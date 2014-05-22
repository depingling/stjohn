package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;

/**
 * Formats the order extraction into a EDI document, the format specified by Lagase.
 *
 * @author Deping
 */
public class Outbound855 extends OutboundSuper
{
	static final String IA = "IA"; // item accepted
	static final String IR = "IR"; // item rejected
	static final String AD = "AD"; // Acknowledge - With Detail and no change
	static final String RD = "RD"; // Reject with Detail


	/** builds segment BAK that is part of the Header
	 *<br>Beginning Segment for Purchase Order Acknowledgment used
	 *<br>To indicate the beginning of the Purchase Order Acknowledgment Transaction Set and transmit identifying numbers and dates
	 *param inTable table containing this segment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildHeaderBAK(Table inTable)
	throws OBOEException
	{
		Segment segment = inTable.createSegment("BAK");
		inTable.addSegment(segment);  DataElement de;
		de = (DataElement) segment.buildDE(1);  // 353 Transaction Set Purpose Code
		de.set("00");
		de = (DataElement) segment.buildDE(2);  // 587 Acknowledgment Type
		if (currOrder.getOrderStatusCd().equals(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED)){
			de.set(AD);
		}
		else{
			de.set(RD);
		}

		de = (DataElement) segment.buildDE(3);  // 324 Purchase Order Number
		de.set(Utility.subString(currOrder.getRequestPoNum(), 12));
		de = (DataElement) segment.buildDE(4);  // 373 Date
		de.set(currOutboundReq.getOriginalDateOrdered());

		de = (DataElement) segment.buildDE(5);  // 328 Release Number
		de.set("");
		de = (DataElement) segment.buildDE(6);  // 326 Request Reference Number
		de.set("");
		de = (DataElement) segment.buildDE(7);  // 367 Contract Number
		de.set("");
		de = (DataElement) segment.buildDE(8);  // 127 Reference Identification
		de.set(currOrder.getOrderNum());
	}

	/** builds segment PER that is part of the Header
	 *<br>Administrative Communications Contact used
	 *<br>To identify a person or office to whom administrative communications should be directed
	 *param inTable table containing this segment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildHeaderPER(Table inTable)
	throws OBOEException
	{
		String phoneNum = Utility.getPhoneNumberByRemoveSeperator(currOrder.getOrderContactPhoneNum());
		if (!Utility.isSet(currOrder.getOrderContactName()) || !Utility.isSet(phoneNum)){
			return;
		}
		Segment segment = inTable.createSegment("PER");
		inTable.addSegment(segment);  DataElement de;
		de = (DataElement) segment.buildDE(1);  // 366 Contact Function Code
		de.set("OC");
		de = (DataElement) segment.buildDE(2);  // 93 Name
		de.set(currOrder.getOrderContactName());
		de = (DataElement) segment.buildDE(3);  // 365 Communication Number Qualifier
		de.set("TE");
		de = (DataElement) segment.buildDE(4);  // 364 Communication Number
		de.set(phoneNum);
	}

	/** builds segment N9 that is part of the Header
	 *<br>Identification Reference used
	 *<br>To transmit identifying information as specified by the Reference Identification Qualifier
	 *param inTable table containing this segment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildHeaderN9(Table inTable)
	throws OBOEException
	{
		Loop loop = inTable.createLoop("N9");
		Segment segment = loop.createSegment("N9");
		loop.addSegment(segment);
		inTable.addLoop(loop);  // for (i = 0; i < multipletimes; i++)
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 128 Reference Identification Qualifier
		de.set("43");
		de = (DataElement) segment.buildDE(2);  // 127 Reference Identification

		String orderSourceCd = currOrder.getOrderSourceCd();
		String method = RefCodeNames.METHOD_TYPE_CD.OTHER;
		if (orderSourceCd.equals(RefCodeNames.ORDER_SOURCE_CD.EDI_850)){
			method = RefCodeNames.METHOD_TYPE_CD.EDI_850;
		}
		else if (orderSourceCd.equals(RefCodeNames.ORDER_SOURCE_CD.FAX)){
			method = RefCodeNames.METHOD_TYPE_CD.FAX;
		}
		else if (orderSourceCd.equals(RefCodeNames.ORDER_SOURCE_CD.MAIL)){
			method = RefCodeNames.METHOD_TYPE_CD.MAIL;
		}
		else if (orderSourceCd.equals(RefCodeNames.ORDER_SOURCE_CD.TELEPHONE)){
			method = RefCodeNames.METHOD_TYPE_CD.TELEPHONE;
		}
		else if (orderSourceCd.equals(RefCodeNames.ORDER_SOURCE_CD.WEB)){
			method = RefCodeNames.METHOD_TYPE_CD.WEB;
		}
		else if (orderSourceCd.equals(RefCodeNames.ORDER_SOURCE_CD.LAW)){
			method = RefCodeNames.METHOD_TYPE_CD.LAW;
		}
		de.set(method);
	}

	/** builds segment PO1 that is part of the Detail
	 *<br>Baseline Item Data used
	 *<br>To specify basic and most frequently used line item data
	 *param inTable table containing this segment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildDetailPO1(Table inTable, int origQty, int ackQty, String ackType)
	throws OBOEException
	{
		Loop loop = inTable.createLoop("PO1");
		inTable.addLoop(loop);
		Segment segment = loop.createSegment("PO1");
		loop.addSegment(segment);

		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 350 Assigned Identification
		de.set(""+currItem.getCustLineNum());
		de = (DataElement) segment.buildDE(2);  // 330 Quantity Ordered
		de.set(""+origQty);
		de = (DataElement) segment.buildDE(3);  // 355 Unit or Basis for Measurement Code
		de.set(Utility.getActualUom(currOutboundReq.getStoreType(), currItem));
		
		de = (DataElement) segment.buildDE(4);  // 212 Unit Price
		de.set("");
		de = (DataElement) segment.buildDE(5);  // 639 Basis of Unit Price Code
		de.set("");
		de = (DataElement) segment.buildDE(6);  // 235 Product/Service ID Qualifier
		de.set("SW");
		de = (DataElement) segment.buildDE(7);  // 234 Product/Service ID
		de.set(""+Utility.getActualSkuNumber(currOutboundReq.getStoreType(), currItem));

		buildDetailPO1ACK(loop, ackType, ackQty);
		buildDetailPO1N1(loop);
	}

	/** builds segment ACK that is part of the DetailPO1
	 *<br>Line Item Acknowledgment used
	 *<br>To acknowledge the ordered quantities and specify the ready date for a specific line item
	 *param inSegment segment containing this subsegment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildDetailPO1ACK(Loop inLoop, String ackType, int ackQty)  throws OBOEException
	{
		Loop loop = inLoop.createLoop("ACK");
		Segment segment = loop.createSegment("ACK");
		loop.addSegment(segment);
		inLoop.addLoop(loop);
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 668 Line Item Status Code
		de.set(ackType);
		de = (DataElement) segment.buildDE(2);  // 380 Quantity
		de.set(""+ackQty);
		de = (DataElement) segment.buildDE(3);  // 355 Unit or Basis for Measurement Code
		de.set(""+Utility.getActualUom(currOutboundReq.getStoreType(), currItem));
	}

	/** builds segment N1 that is part of the DetailPO1
	 *<br>Name used
	 *<br>To identify a party by type of organization, name, and code
	 *param inSegment segment containing this subsegment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildDetailPO1N1(Loop inLoop)  throws OBOEException
	{
		Loop loop = inLoop.createLoop("N1");
		inLoop.addLoop(loop);
		Segment segment = loop.createSegment("N1");
		loop.addSegment(segment);

		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 98 Entity Identifier Code
		de.set("ST");
		de = (DataElement) segment.buildDE(2);  // 93 Name
		de.set(mCustShipAddr.getAddress1());
		de = (DataElement) segment.buildDE(3);  // 66 Identification Code Qualifier
		de.set("ZZ");
		de = (DataElement) segment.buildDE(4);  // 67 Identification Code
		String sname = Utility.getEDIToken(currOrder.getOrderSiteName());
		de.set(sname);

		String streetAddr = "";
		String addr1 = null;
		String addr2 = null;
		if (Utility.isSet(mCustShipAddr.getAddress4())){
			streetAddr = mCustShipAddr.getAddress4();
			addr1 = mCustShipAddr.getAddress2();
			addr2 = mCustShipAddr.getAddress3();
		}
		else if (Utility.isStreetAddress(mCustShipAddr.getAddress2())){
			streetAddr = mCustShipAddr.getAddress2();
			addr1 = mCustShipAddr.getAddress3();
		}
		else if (Utility.isStreetAddress(mCustShipAddr.getAddress3())){
			streetAddr = mCustShipAddr.getAddress3();
			addr1 = mCustShipAddr.getAddress2();
		}
		else{
			streetAddr = mCustShipAddr.getAddress1();
			addr1 = mCustShipAddr.getAddress2();
			addr2 = mCustShipAddr.getAddress3();
		}

		if (Utility.isSet(addr1))
			buildDetailPO1N1N2(loop, addr1);
		if (Utility.isSet(addr2))
			buildDetailPO1N1N2(loop, addr2);
		buildDetailPO1N1N3(loop, streetAddr);

		buildDetailPO1N1N4(loop);
		buildDetailPO1N1REF(loop);
	}

	/** builds segment N2 that is part of the DetailPO1N1
	 *<br>Additional Name Information used
	 *<br>To specify additional names or those longer than 35 characters in length
	 *param inSegment segment containing this subsegment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildDetailPO1N1N2(Loop inLoop, String address)  throws OBOEException
	{
		Segment segment = inLoop.createSegment("N2");
		inLoop.addSegment(segment);  DataElement de;
		de = (DataElement) segment.buildDE(1);  // 93 Name
		de.set(address);
	}

	/** builds segment N3 that is part of the DetailPO1N1
	 *<br>Address Information used
	 *<br>To specify the location of the named party
	 *param inSegment segment containing this subsegment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildDetailPO1N1N3(Loop inLoop, String address)  throws OBOEException
	{
		Segment segment = inLoop.createSegment("N3");
		inLoop.addSegment(segment);  DataElement de;
		de = (DataElement) segment.buildDE(1);  // 166 Address Information
		de.set(address);
	}

	/** builds segment N4 that is part of the DetailPO1N1
	 *<br>Geographic Location used
	 *<br>To specify the geographic place of the named party
	 *param inSegment segment containing this subsegment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildDetailPO1N1N4(Loop inLoop)  throws OBOEException
	{
		Segment segment = inLoop.createSegment("N4");
		inLoop.addSegment(segment);
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 19 City Name
		de.set(mCustShipAddr.getCity());
		de = (DataElement) segment.buildDE(2);  // 156 State or Province Code
		de.set(mCustShipAddr.getStateProvinceCd());
		de = (DataElement) segment.buildDE(3);  // 116 Postal Code
		de.set(Utility.getNumStringByRemoveNoneNumChars(mCustShipAddr.getPostalCode()));
	}

	/** builds segment REF that is part of the DetailPO1N1
	 *<br>Reference Identification used
	 *<br>To specify identifying information
	 *param inSegment segment containing this subsegment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildDetailPO1N1REF(Loop inLoop)  throws OBOEException
	{
		Segment segment = inLoop.createSegment("REF");
		inLoop.addSegment(segment);
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 128 Reference Identification Qualifier
		de.set("CT");
		de = (DataElement) segment.buildDE(2);  // 127 Reference Identification
		de.set("483083-01-B-0679");
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
		Loop loop = inTable.createLoop("CTT");
		inTable.addLoop(loop);
		Segment segment = loop.createSegment("CTT");
		loop.addSegment(segment);
		inTable.addLoop(loop);
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 354 Number of Line Items
		de.set(""+items.size());
	}

	public void buildTransactionContent()
	throws Exception
	{
		currOrder = currOutboundReq.getOrderD();
		items = currOutboundReq.getOrderItemDV();
		if (items.size() < 0)
			return;
		
		mCustShipAddr = currOutboundReq.getCustShipAddr();
		Table table = ts.getHeaderTable();
		buildHeaderBAK(table);
		buildHeaderPER(table);
		buildHeaderN9(table);
		table = ts.getDetailTable();
		for (int i=0; i < items.size(); i++ ) {
			currItem = (OrderItemData)items.get(i);
			OrderItemActionData itemActionD = (OrderItemActionData) currOutboundReq.getOrderItemActionDV().get(i);
			int origQty = currItem.getTotalQuantityOrdered();
			int ackQty = itemActionD.getQuantity();
			String ackCd = translateOrderAcknowlegmentCode(itemActionD.getActionCd());
			
			buildDetailPO1(table,origQty, ackQty,ackCd);
			itemActionD.setStatus(RefCodeNames.EDI_TYPE_CD.T855_SENT);
			appendIntegrationRequest(itemActionD); // for update the status
		}
		OrderPropertyDataVector opDV = currOutboundReq.getOrderPropertyDV();
		if(opDV!=null && opDV.size()>0) {
			appendIntegrationRequests(opDV);
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
	
	/**
     * translates the codes from Human readable to 2 digit ACK code for EDI file. 
     * @param the human readable RefCodeNames value
     * @return the 2 digit ACK code
     */
    private String translateOrderAcknowlegmentCode(String code){
    	
    	
    	if (code.equals(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACK_BACKORDERED))  {
    		return "IB";
    	}else if (code.equals(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACK_ACCEPTED)) {
            return  "IA";
        }else if (code.equals(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACK_ACCEPTED_CHANGES_MADE)) {
        	return "IC";
        }else if (code.equals(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACK_DELETED)) {
            return "ID";
        }else if (code.equals(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACK_ON_HOLD)) {
        	return "IH";
        }else if (code.equals(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACK_ACCEPTED_QUANTITY_CHANGED)) {
            return "IQ";
        }else if (code.equals(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACK_REJECTED)) {
            return "IR";
        }else if (code.equals(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACK_ACCEPTED_SUBSTITUTION)) {
            return "IS";
        }else if (code.equals(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACK_ON_HOLD)) {
            return "AH";
        } else {
            return "IA";
        }
    }
}
