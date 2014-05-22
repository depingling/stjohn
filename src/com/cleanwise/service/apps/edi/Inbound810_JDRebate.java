package com.cleanwise.service.apps.edi;

import java.math.BigDecimal;

import org.apache.log4j.Logger;


import com.americancoders.edi.DataElement;
import com.americancoders.edi.Loop;
import com.americancoders.edi.OBOEException;
import com.americancoders.edi.Segment;
import com.americancoders.edi.Table;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.TransRebateProcessedView;
import com.cleanwise.service.api.value.TransRebateProcessedViewVector;


/** code to parse 810 transaction
 *<br>class 810 Advanced Ship Notice
 *<br>
 *@author Deping
 */
public class Inbound810_JDRebate extends InboundEdiSuper
{
	private TransRebateProcessedViewVector transRebateProcessedV = null;
	private TransRebateProcessedView currTRProcessedData = null;
	
	protected Logger log = Logger.getLogger(this.getClass());
	public Inbound810_JDRebate(){
	}
	
	public void extract()
	throws OBOEException
	{
		transRebateProcessedV = new TransRebateProcessedViewVector();
		currTRProcessedData = TransRebateProcessedView.createValue();
		currTRProcessedData.setConnectionStatus("PENDING");

		Table table = ts.getHeaderTable();
		if (table != null)
		{
			extractHeaderBIG(table);
		}
		table = ts.getDetailTable();
		if (table != null)
		{
			extractDetailIT1(table);
		}
		table = ts.getSummaryTable();
		if (table != null)
		{
			//extractHeaderTDS(table);
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
		
		de = segment.getDataElement(2);  // 76 Invoice Number
		if (!Utility.isSet(de.get())) {//????????????????
			errorMsgs.add("Missing Invoice Number in segment BIG");
			setValid(false);
		} else {
			currTRProcessedData.setInvoiceNumber(de.get());
		}

		de = segment.getDataElement(4);  // 324 Purchase Order Number
		if (de==null || !Utility.isSet(de.get())) {
			errorMsgs.add("Missing Rebate Number in segment BIG");
		} else {
			currTRProcessedData.setRebateNumber(de.get());
		}
		
		de = segment.getDataElement(7); //credit segment
		if(de != null && Utility.isSet(de.get())){
			currTRProcessedData.setPaymentMethod(de.get());
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
		}

		for (int i = 0; i <  numberInVector; i++)
		{
			loop = inTable.getLoop("IT1", i);
			if (loop == null) return;
			if (i > 0){
				TransRebateProcessedView temp = TransRebateProcessedView.createValue();
				temp.setRebateNumber(currTRProcessedData.getRebateNumber());
				temp.setConnectionStatus(currTRProcessedData.getConnectionStatus());
				temp.setPaymentMethod(currTRProcessedData.getPaymentMethod());	
				temp.setInvoiceNumber(currTRProcessedData.getInvoiceNumber());
				currTRProcessedData = temp;
			}
			transRebateProcessedV.add(currTRProcessedData);

			segment = loop.getSegment("IT1");
			DataElement de;
			de = segment.getDataElement(1);       // 350 Assigned Identification
			


			// Set the quantity to an invalid value.
			currTRProcessedData.setQuantity("-9999");

			de = segment.getDataElement(2);       // 358 Quantity Invoiced
			if (Utility.isSet(de.get())) {
				String qty = de.get();
				qty = qty.trim();
				currTRProcessedData.setQuantity(qty);
			}

			de = segment.getDataElement(3);       // 355 Unit or Basis for Measurement
			if (Utility.isSet(de.get())) {
				currTRProcessedData.setUom(de.get());
			}

			de = segment.getDataElement(4);       // 212 Unit Price
			if (de != null && Utility.isSet(de.get())) {
				currTRProcessedData.setRebatePaid(new BigDecimal(de.get()));
			}

			String distItemSkuNum = null;
			
			de = segment.getDataElement(8); // 235 Product/Service ID Qualifier
			String code = de.get();
			if (code.equals("VN")) {  //vendor item number
				de = segment.getDataElement(9);
				currTRProcessedData.setProductSku(de.get());
			}
			if (!Utility.isSet(currTRProcessedData.getProductSku())){
				errorMsgs.add("Missing Product Sku in element REF07 for itemSku:" + currTRProcessedData.getProductSku());
				setValid(false);
			}
			extractDetailIT1PID(loop);
			extractDetailIT1REF(loop);
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
			StringBuffer itemDesc = new StringBuffer(currTRProcessedData.getProductDesc());
			for (int i = 0; i <  numberInVector; i++) {
				loop = inLoop.getLoop("PID", i);
				if (loop == null) return;
				Segment segment = loop.getSegment("PID");
				DataElement de;
				// Don't validate item description type.  Should always be 'F'
				// for 'Free Form Text'.
				de = segment.getDataElement(1);       // 349 Item Description Type
				de = segment.getDataElement(5);       // 352 Description
				if (Utility.isSet(de.get())) {
					itemDesc.append(de.get());
					itemDesc.append(" ");
				}
			}
			currTRProcessedData.setProductDesc(itemDesc.toString());
		} catch (OBOEException oe) { return; }
	}

	/** extract data from segment REF that is part of the TableDetailLoopIT1
	*<br>Reference Identification used 
	*<br>To specify identifying information
	* @param inLoop Loop containing this segment
	*@throws OBOEException - most likely segment not found
	*/
	public void extractDetailIT1REF(Loop inLoop)  throws OBOEException
	{
		Segment segment;
		int numberOfSegmentsInVector = inLoop.getCount("REF");
		
		for (int i = 0; i <  numberOfSegmentsInVector; i++)
		{
			segment = inLoop.getSegment("REF", i);
			if (segment == null)
				return;
			DataElement de;
			de = segment.getDataElement(1);       // 128 Reference Identification Qualifier
			if (de != null){
				if (de.get().equals("CR")){ // Contract Number
					de = segment.getDataElement(2);       // 127 Reference Identification						
					currTRProcessedData.setContractNumber(de.get());
					de = segment.getDataElement(3);
					if (de != null)
						currTRProcessedData.setContractDesc(segment.getDataElement(3).get());
				}else if (de.get().equals("SO")){ // Customer Ref Number (Credit Memo)
					de = segment.getDataElement(2);       // 127 Reference Identification						
					currTRProcessedData.setCustomerRefNumber(de.get());
				}else if (de.get().equals("PO")){ // PO Number (Rebate Number)
					de = segment.getDataElement(2);       // 127 Reference Identification
					currTRProcessedData.setRebateNumber(de.get());
				}
			}			
		}
			
		if (!Utility.isSet(currTRProcessedData.getContractNumber())){
			errorMsgs.add("Missing Contract Number in segment REF for itemSku:" + currTRProcessedData.getProductSku());
			setValid(false);
		}
		if (!Utility.isSet(currTRProcessedData.getCustomerRefNumber())){
			errorMsgs.add("Missing Customer Ref Number in segment REF for itemSku:" + currTRProcessedData.getProductSku());
			setValid(false);
		}
	}
	public void processTransaction() throws Exception
	{
		for(int i = 0; i < transRebateProcessedV.size(); i++){
			currTRProcessedData = (TransRebateProcessedView) transRebateProcessedV.get(i);
			ediHandler.appendIntegrationRequest(currTRProcessedData);
		}
		
	}

}
