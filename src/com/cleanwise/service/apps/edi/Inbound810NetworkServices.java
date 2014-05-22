package com.cleanwise.service.apps.edi;

import java.math.BigDecimal;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.americancoders.edi.*;

public class Inbound810NetworkServices extends Inbound810 {
	protected Logger log = Logger.getLogger(this.getClass());

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
					log.info("Caught number format exception...continuing with processing!");
					e.printStackTrace();
				}
			}

			de = segment.getDataElement(3);       // 355 Unit or Basis for Measurement

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
							}else if (code.equals("PL")) {
				                currDetail.setErpPoLineNum(Integer.parseInt(de.get()));
				            }
						}
					}
					//} else if (code.equals("VC")) {  //vendor catalog number
					//    currDetail.setDistItemSkuNum(de.get());
					//}

				}
			}

			if (distItemSkuNum != null){
				BigDecimal quantity=new BigDecimal(currDetail.getDistItemQuantity()).abs();
				BigDecimal lineTotal = currDetail.getItemReceivedCost() == null ? new BigDecimal(0) : currDetail.getItemReceivedCost().multiply(quantity);
				if (Utility.isInArray(distItemSkuNum, excludedSKUs)){
					excludedTotal = excludedTotal.add(lineTotal);
					continue;
				}else{
					currDetail.setDistItemSkuNum(distItemSkuNum);
					currDetail.setInvoiceDistSkuNum(distItemSkuNum);
					controlTotalSumInvoice= controlTotalSumInvoice.add(lineTotal);
				}
			}
			currDetails.add(currDetail);
			log.info("X1.3: currDetail: DistLineNumber=" + currDetail.getDistLineNumber()
					+ ", ItemSkuNum=" + currDetail.getItemSkuNum()
					+ ", DistItemSkuNum=" + currDetail.getDistItemSkuNum()
					+ ", DistItemQuantity=" + currDetail.getDistItemQuantity()
					+ ", ItemReceivedCost=" + currDetail.getItemReceivedCost());
		}
	}

}
