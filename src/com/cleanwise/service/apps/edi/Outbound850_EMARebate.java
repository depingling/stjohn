package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;
import com.americancoders.edi.x12.X12Envelope;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.apps.dataexchange.CompressedAddress;
import com.cleanwise.service.apps.dataexchange.OutboundTransaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.math.BigDecimal;


/**
 * Formats the order extraction into a EDI document, the format specified by Lagase.
 *
 * @author Deping
 */
public class Outbound850_EMARebate extends Outbound850_JDRebate {
	
	/** constructor for class Outbound850_EMARebate
	 *@throws OBOEException - most likely transactionset not found
	 */
	public Outbound850_EMARebate()
	  throws OBOEException
	{	  
	}	
    
    public void buildTransactionContent()
    throws Exception {
    	transRebateApprovedV = currOutboundReq.getTransRebateApprovedDV();
    	currTRApprovedData = (TransRebateApprovedView) transRebateApprovedV.get(0);        
        
        Table hdrtable = ts.getHeaderTable();        
        buildHeaderBEG(hdrtable);
        buildHeaderREF(hdrtable, "75", currTRApprovedData.getSalesOrg());
        buildHeaderREF(hdrtable, "AEM", currTRApprovedData.getDistChannel());
        buildHeaderREF(hdrtable, "19", currTRApprovedData.getDivision());
        buildHeaderREF(hdrtable, "IO", currTRApprovedData.getDistributorNumber());
        buildHeaderREF(hdrtable, "PO", currTRApprovedData.getContractNumber());
        buildHeaderREF(hdrtable, "TD", currTRApprovedData.getReason());
        buildHeaderN1(hdrtable, "ST");


        Table detailtable = ts.getDetailTable();

        for (int i = 0; i < transRebateApprovedV.size(); i++) {
        	currTRApprovedData = (TransRebateApprovedView) transRebateApprovedV.get(i);
            buildDetailPO1(detailtable, i+1);
            //set state to processed
            currTRApprovedData.setConnectionStatus("PROCESSED");
            appendIntegrationRequest(currTRApprovedData); // for update the status
        }

        transactionD.setKeyString("Rebate Number: " + currTRApprovedData.getRebateNumber() +
        		", DistributorNumber: " + currTRApprovedData.getDistributorNumber() +
                ", ContractNumber: " + currTRApprovedData.getContractNumber());
    }
    
    /** builds segment BEG that is part of the Header
     *<br>Beginning Segment for Purchase Order used
     *<br>To indicate the beginning of the Purchase Order Transaction Set and transmit identifying numbers and dates
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildHeaderBEG(Table inTable)
    throws Exception {

        Segment segment = inTable.createSegment("BEG");
        inTable.addSegment(segment);

        DataElement de;
        de = (DataElement)segment.buildDE(1); // 353 Transaction Set Purpose Code
        de.set("00");
        de = (DataElement)segment.buildDE(2); // 92 Purchase Order Type Code
        de.set(currTRApprovedData.getRebateTypeCode());
        de = (DataElement)segment.buildDE(3); // 324 Purchase Order Number        
        de.set(currTRApprovedData.getRebateNumber());

        de = (DataElement)segment.buildDE(4); // 328 Release Number
        de.set("");
        de = (DataElement)segment.buildDE(5); // 373 Date
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMyydd");
        dateFormatter = new SimpleDateFormat("yyyyMMdd");
        de.set(dateFormatter.format(currTRApprovedData.getRebateDate()));
    }
    
    public void buildHeaderREF(Table inTable, String qualifier, String data)
    throws OBOEException {
    	
        Segment segment = inTable.createSegment("REF");
        inTable.addSegment(segment);  DataElement de;
        de = (DataElement) segment.buildDE(1);  // 128 Reference Identification Qualifier
        de.set(qualifier);	
        de = (DataElement) segment.buildDE(2);  // 127 Reference Identification
        de.set(data);
    }

    /** builds segment PO1 that is part of the Detail
     *<br>Baseline Item Data used
     *<br>To specify basic and most frequently used line item data
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildDetailPO1(Table inTable, int lineNum)
    throws OBOEException {

        Loop loop = inTable.createLoop("PO1");
        inTable.addLoop(loop); // for (i = 0; i < multipletimes; i++)
        Segment segment = loop.createSegment("PO1");
        loop.addSegment(segment); // for (i = 0; i < multipletimes; i++)

        buildDetailPO1PO3(loop);
        buildDetailPO1PID(loop);

        DataElement de;
        de = (DataElement)segment.buildDE(1); // 350 Assigned Identification
        de.set(""+lineNum);
        de = (DataElement)segment.buildDE(2); // 330 Quantity Ordered
        de.set(currTRApprovedData.getQuantity());
        de = (DataElement)segment.buildDE(3); // 355 Unit or Basis for Measurement Code

        de.set(currTRApprovedData.getUom());


        de = (DataElement)segment.buildDE(4); // 212 Unit Price
        de.set("" + currTRApprovedData.getUnitPrice());
        de = (DataElement)segment.buildDE(5); // 639 Basis of Unit Price Code
        de.set("PE");
        de = (DataElement)segment.buildDE(6); // 235 Product/Service ID Qualifier
        de.set("BP");
        de = (DataElement)segment.buildDE(7); // 234 Product/Service ID
        de.set(currTRApprovedData.getProductSku().trim());
        de = (DataElement)segment.buildDE(8); // 235 Product/Service ID Qualifier
        de.set("VN");
        de = (DataElement)segment.buildDE(9); // 234 Product/Service ID
        de.set(currTRApprovedData.getDistProductSku().trim());        
    }
    
    public Segment buildDetailPO1PO3(Loop inLoop)  throws OBOEException
    {
    	Segment segment = inLoop.createSegment("PO3");
    	inLoop.addSegment(segment);
    	DataElement de;
    	de = (DataElement) segment.buildDE(1);  // 371 Change Reason Code
    	de.set("AQ");
    	de = (DataElement) segment.buildDE(2);  // 373 Date
    	de.set("");//not required
    	de = (DataElement) segment.buildDE(3);  // 236 Price Identifier Code
    	de.set("");//not required
    	de = (DataElement) segment.buildDE(4);  // 212 Unit Price
    	de.set("" + currTRApprovedData.getRbtAmountAdj());//not required
    	de = (DataElement) segment.buildDE(5);  // 639 Basis of Unit Price Code
    	de.set("PE");//not required
    	de = (DataElement) segment.buildDE(6);  // 380 Quantity
    	de.set("1");
    	de = (DataElement) segment.buildDE(7);  // 355 Unit or Basis for Measurement Code
    	de.set("ZZ");
    	de = (DataElement) segment.buildDE(8);  // 352 Description
    	de.set("Total Rebate for item");
    	return segment;
    }
    
}
