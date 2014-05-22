package com.cleanwise.service.apps.edi;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

import com.americancoders.edi.*;

import java.util.*;
import java.math.BigDecimal;


/**
 * JUnit test cases for Inbound855
 * @author deping
 * @version 1.0
 */
public class Inbound855 extends InboundSuper
{
	private AcknowledgeRequestData reqAcknowledgement;
	private String erpPoNum;
	private String orderNum;
	private String custOrderNum;
	private String vendorOrderNum;
	private String distributorCompanyCode;
	private AckItemData currAckItem;
	private AckItemDataVector ackItems = new AckItemDataVector();



	// extract all segments included in ST - SE Segment
	public void extract()
	throws OBOEException {
		reqAcknowledgement = new AcknowledgeRequestData();
		Table table = ts.getHeaderTable();
		if (table != null) {
			extractHeaderBAK(table);
			extractHeaderREF(table);
			extractHeaderN1(table);
		}
		table = ts.getDetailTable();
		if (table != null) {
			extractDetailPO1(table);
		}
	}


	/** extract data from segment BAK that is part of the Header
	 *<br>Beginning Segment for Purchase Order Acknowledgment used
	 *<br>To indicate the beginning of the Purchase Order Acknowledgment Transaction Set and transmit identifying numbers and dates
	 *@param inTable table containing this segment
	 *@throws OBOEException - most likely segment not found
	 */
	public void extractHeaderBAK(Table inTable)
	throws OBOEException {
		try{
			Segment segment = inTable.getSegment("BAK");
			if (segment == null) {
				errorMsgs.add("Segment BAK missing");
				return;
			}
			DataElement de;

			de = segment.getDataElement(2);  // 587 Acknowledgment Type
			if (de != null && !de.get().equals("")) {
				String ackType = de.get();
				if (ackType.equals("RD") || ackType.equals("AH"))
					;//ackDescription = ((IDDE)de).describe(de.get());
			}

			de = segment.getDataElement(3);  // 324 Purchase Order Number
			if (de == null || de.get().equals(""))
				errorMsgs.add("Missing Cleanwise PO Number in segment BAK");
			else{
				int ix = de.get().lastIndexOf("/");
				if (ix > 0) {
					erpPoNum = de.get().substring(0, ix);
					orderNum = de.get().substring(ix);
				}
				else
					erpPoNum = de.get();
			}
			de = segment.getDataElement(9);  // 373 Date
			if (de == null || !Utility.isSet(de.get())) {
				// use current date
				reqAcknowledgement.setAckDate(new Date());
			} else {
				reqAcknowledgement.setAckDate(stringToDate(de.get(), "yyyyMMdd", null));
			}
		}catch (OBOEException oe) { return; }
	}

	/** extract data from segment REF that is part of the Header
	 *<br>Reference Identification used
	 *<br>To specify identifying information
	 *param inTable table containing this segment
	 *@throws OBOEException - most likely segment not found
	 */
	public void extractHeaderREF(Table inTable)
	throws OBOEException {
		Segment segment;

		try {
			int numberOfSegmentsInVector = inTable.getSegmentCount("REF");
			for (int i = 0; i <  numberOfSegmentsInVector; i++) {
				segment = inTable.getSegment("REF", i);
				DataElement de;
				de = segment.getDataElement(1);       // 128 Reference Identification Qualifier
				if (de == null || de.get().equals("")) {
					continue;
				}
				if (de.get().equals("CR")) {
					de = segment.getDataElement(2);       // 127 Reference Identification
					if (de != null && !de.get().equals("")) {
						custOrderNum = de.get();
					}
				} else if (de.get().equals("D2")) {
					de = segment.getDataElement(2);       // 127 Reference Identification
					if (de != null && !de.get().equals("")) {
						vendorOrderNum = de.get();
					}
				} else if (de.get().equals("DI")) {      // Distributor Identifier, uniquly identifies
					// a distributor for anyone with multiple distributors
					//under 1 umbrella which we want to validate off of.
					de = segment.getDataElement(2);
					if (de != null && !de.get().equals("")) {
						distributorCompanyCode = de.get();
					}
				}
			}
		}catch (OBOEException oe){}
	}

	/** extract data from segment N1 that is part of the Header
	 *<br>Name used
	 *<br>To identify a party by type of organization, name, and code
	 *param inTable table containing this segment
	 *@throws OBOEException - most likely segment not found
	 */
	public void extractHeaderN1(Table inTable)
	throws OBOEException {		

		Loop loop;
		try {
			int numberInVector = inTable.getCount("N1");
			for (int i = 0; i <  numberInVector; i++)
			{
				loop = inTable.getLoop("N1", i);
				if (loop == null) return;
				Segment segment = loop.getSegment("N1");
				DataElement de = segment.getDataElement(1);       // 98 Entity Identifier Code
				String code = de.get();
				if (code.equalsIgnoreCase("BT")) {
				}
				else if (code.equalsIgnoreCase("ST")) {
					de = segment.getDataElement(2);       // 93 Name
					if (de == null || de.get().equals(""))
						errorMsgs.add("Missing Name field in N1 segment for Ship to");
					else {
						extractHeaderN1N3(code, loop);
						extractHeaderN1N4(code, loop);
					}
				}
			}
		}catch (OBOEException oe){}
	}

	/** extract data from segment N3 that is part of the HeaderN1
	 *<br>Address Information used
	 *<br>To specify the location of the named party
	 *param inSegment segment containing this subsegment
	 *@throws OBOEException - most likely segment not found
	 */
	public void extractHeaderN1N3(String id, Loop inLoop)  throws OBOEException {
		Segment segment;
		try {
			int numberOfSegmentsInVector = inLoop.getCount("N3");
			for (int i = 0; i <  numberOfSegmentsInVector; i++)
			{
				segment = inLoop.getSegment("N3");
				if (segment == null)
					return;
				DataElement de;
				de = segment.getDataElement(1);       // 166 Address Information
				if (id.equals("ST")) {
					if (de == null || de.get().equals(""))
						errorMsgs.add("Missing Address Information in N3 segment for Ship to");
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
		DataElement de;

		try {
			Segment segment = inLoop.getSegment("N4");
			if (segment == null)
				return;

			if (id.equals("ST")) {
				de = segment.getDataElement(1);  // 19 City Name
				if (de == null || de.get().equals(""))
					errorMsgs.add("Missing City Name in N4 segment for Ship to");

				de = segment.getDataElement(2);  // 156 State or Province Code
				if (de == null || de.get().equals(""))
					errorMsgs.add("Missing State or Province Code in N4 segment for Ship to");

				de = segment.getDataElement(3);  // 116 Postal Code
				if (de == null || de.get().equals(""))
					errorMsgs.add("Missing Postal Code in N4 segment for Ship to");
			}
		}
		catch (OBOEException oe) { return; }
	}

	/** extract data from segment PO1 that is part of the Detail
	 *<br>Baseline Item Data used
	 *<br>To specify basic and most frequently used line item data
	 *param inTable table containing this segment
	 *@throws OBOEException - most likely segment not found
	 */
	public void extractDetailPO1(Table inTable)
	throws OBOEException {
		Loop loop;
		boolean ignoreMissingLineInfo = Utility.isTrue(getTranslator().getConfigurationProperty(RefCodeNames.ENTITY_PROPERTY_TYPE.IGNORE_MISSING_LINE_INFO));

		try{
			int numberInVector = inTable.getCount("PO1");
			if (numberInVector == 0){
				errorMsgs.add("Segment PO1 missing");
				return;
			}
			for (int i = 0; i <  numberInVector; i++)
			{

				loop = inTable.getLoop("PO1", i);
				Segment segment = loop.getSegment("PO1");
				currAckItem = AckItemData.createValue();
				ackItems.add(currAckItem);

				DataElement de;
				String itemId;
				de = segment.getDataElement(1);       // 350 Assigned Identification
				if (de == null || de.get().equals("")){
					itemId = "";
					try {
						currAckItem.setLineNum(Integer.parseInt(itemId));
					}catch(NumberFormatException e){
						System.out.println(" NO itemId in segment: " +
								segment.get() );
					}
				}else{
					itemId = de.get();
				}
				de = segment.getDataElement(2);       // 330 Quantity Ordered
				if ((de == null || de.get().equals("")) && !ignoreMissingLineInfo)
					errorMsgs.add("Missing Quantity Ordered in PO1 segment of id " + itemId);
				else {
					try {
						currAckItem.setQuantity(Integer.parseInt(de.get()));
					}catch(NumberFormatException e){
						System.out.println(" NO qty in segment: " +
								segment.get() );
					}
				}

				de = segment.getDataElement(3);       // 355 Unit or Basis for Measurement Code
				if ((de == null || de.get().equals("")) && !ignoreMissingLineInfo)
					errorMsgs.add("Missing Unit of Measurement Code in PO1 segment of id " + itemId);
				else
					currAckItem.setUom(de.get());

				de = segment.getDataElement(4);       // 212 Unit Price
				if (de == null || de.get().equals("")){
					//errorMsgs.add("Missing Unit Price in PO1 segment of id " + itemId);
				}else{
					currAckItem.setPrice(new BigDecimal(de.get()));
				}

				de = segment.getDataElement(6);       // 235 Product/Service ID Qualifier
				if (de != null && !de.get().equals("")) {
					String id = de.get();
					de = segment.getDataElement(7);       // 234 Product/Service ID
					if (de != null && !de.get().equals("")) {
						if (id.equals("VN"))
							currAckItem.setDistSkuNum(de.get());
						else if (id.equals("BP"))
							try{
								currAckItem.setCwSkuNum(Integer.parseInt(de.get()));
							}catch(NumberFormatException e){
								System.out.println(" NO SKU in segment: " +
										segment.get() );
							}
					}
				}
				de = segment.getDataElement(8);       // 235 Product/Service ID Qualifier
				if (de != null && !de.get().equals("")) {
					String id = de.get();
					de = segment.getDataElement(9);       // 234 Product/Service ID
					if (de != null && !de.get().equals("")) {
						if (id.equals("VN"))
							currAckItem.setDistSkuNum(de.get());
						else if (id.equals("BP"))
							try{
								currAckItem.setCwSkuNum(Integer.parseInt(de.get()));
							}catch(NumberFormatException e){
								System.out.println(" NO SKU in segment: " +
										segment.get() );
							}
					}
				}


				/*if ((currAckItem.getDistSkuNum() == null || currAckItem.getDistSkuNum().equals("")) &&
                    (currAckItem.getCwSkuNum() == 0))
                    errorMsgs.add("Missing Vendor and Cleanwise SKU number in PO1 segment of id " + itemId);*/

				extractDetailPO1ACK(itemId, loop);
			}
		}catch (OBOEException oe) { return; }
	}

	/** extract data from segment ACK that is part of the DetailPO1
	 *<br>Line Item Acknowledgment used
	 *<br>To acknowledge the ordered quantities and specify the ready date for a specific line item
	 *param inSegment segment containing this subsegment
	 *@throws OBOEException - most likely segment not found
	 */
	public void extractDetailPO1ACK(String itemId, Loop inLoop)  throws OBOEException {
		Loop loop;
		try{
			int numberInVector = inLoop.getCount("ACK");
			if (numberInVector == 0){
				errorMsgs.add("Segment ACK missing");
				return;
			}
			for (int i = 0; i <  numberInVector; i++)
			{
				loop = inLoop.getLoop("ACK", i);
				if (loop == null) return;
				Segment segment = loop.getSegment("ACK");

				DataElement de;
				de = segment.getDataElement(1);       // 668 Line Item Status Code
				if (de == null || de.get().equals("")) {
					errorMsgs.add("Missing Line Item Status Code in ACK segment of id " + itemId);
					continue;
				}
				currAckItem.setAction(de.get());

				de = segment.getDataElement(2);       // 380 Quantity
				if (de != null && Utility.isSet(de.get()))
					currAckItem.setQuantity(Integer.parseInt(de.get()));
				de = segment.getDataElement(3);       // 355 Unit or Basis for Measurement Code
				if (de != null && Utility.isSet(de.get()))
					currAckItem.setUom(de.get());

				de = segment.getDataElement(5);       // Action date
				if (de != null && de.get()!= null && de.get().trim().length() != 0) {
					currAckItem.setActionDate(stringToDate(de.get(), "yyyyMMdd", null));
				}

				if (!currAckItem.getAction().equals("IS"))
					continue;

				de = segment.getDataElement(7);       // 235 Product/Service ID Qualifier
				if (de == null || !de.get().equals("RR")) {
					errorMsgs.add("Missing Replacement Qualifier in ACK segment of id " + itemId);
					continue;
				}
				de.get();
				de = segment.getDataElement(8);       // 234 Product/Service ID
				if (de == null || de.get().equals("")) {
					errorMsgs.add("Missing Replacement cleanwise sku number in ACK segment of id " + itemId);
					continue;
				}
				try{
					currAckItem.setCwSkuNum(Integer.parseInt(de.get()));
				}catch(NumberFormatException e){}
			}
		}catch (OBOEException oe) { return; }
	}

	public void processTransaction()
	{
		reqAcknowledgement.setErpPoNum(erpPoNum);
		reqAcknowledgement.setAckItemDV(ackItems);
		reqAcknowledgement.setVendorOrderNum(vendorOrderNum);
		reqAcknowledgement.setDistributorsCompanyCode(distributorCompanyCode);
		OrderData orderD = getTranslator().getOrderDataByErpPoNum(erpPoNum);
		int orderId = 0;
		int storeId = 0;
		if(orderD!=null) {
			orderId = orderD.getOrderId();
			storeId = orderD.getStoreId();
		}
		mHandler.transactionD.setOrderId(orderId);
		mHandler.transactionD.setKeyString("ErpPoNum: " + erpPoNum
				+ ", orderNum: " + orderNum
				+ ", custOrderNum: " + custOrderNum
				+ ", vendorOrderNum: " + vendorOrderNum);
		reqAcknowledgement.setStoreId(storeId);
		mHandler.appendIntegrationRequest(reqAcknowledgement);
	}
}

