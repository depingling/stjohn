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
public class Outbound850_JDRebate extends OutboundSuper {
	protected TransRebateApprovedViewVector transRebateApprovedV = null;
	protected TransRebateApprovedView currTRApprovedData = null;
	
	/** constructor for class Outbound850_JD
	 *@throws OBOEException - most likely transactionset not found
	 */
	public Outbound850_JDRebate()
	  throws OBOEException
	{	  
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
        de.set("ZZ");
        de = (DataElement)segment.buildDE(3); // 324 Purchase Order Number        
        de.set(currTRApprovedData.getRebateNumber());

        de = (DataElement)segment.buildDE(4); // 328 Release Number
        de.set("");
        de = (DataElement)segment.buildDE(5); // 373 Date
        // set po date to 15's of rebate order month. The example rebate number is JAN09_03242009_000007 and po date string will be 20090115
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMyydd");
        Date poDate = dateFormatter.parse(currTRApprovedData.getRebateNumber().substring(0, 5) + "15");
        dateFormatter = new SimpleDateFormat("yyyyMMdd");
        de.set(dateFormatter.format(poDate));
    }
    
    public void buildTransactionContent()
    throws Exception {
    	transRebateApprovedV = currOutboundReq.getTransRebateApprovedDV();
    	currTRApprovedData = (TransRebateApprovedView) transRebateApprovedV.get(0);        
        
        Table hdrtable = ts.getHeaderTable();        
        buildHeaderBEG(hdrtable);

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

        buildDetailPO1PID(loop);

        DataElement de;
        de = (DataElement)segment.buildDE(1); // 350 Assigned Identification
        de.set(""+lineNum);
        de = (DataElement)segment.buildDE(2); // 330 Quantity Ordered
        de.set(currTRApprovedData.getQuantity());
        de = (DataElement)segment.buildDE(3); // 355 Unit or Basis for Measurement Code

        de.set(currTRApprovedData.getUom());


        de = (DataElement)segment.buildDE(4); // 212 Unit Price
        de.set("" + currTRApprovedData.getRbtAmountAdj());
        de = (DataElement)segment.buildDE(5); // 639 Basis of Unit Price Code
        de.set("PE");
        de = (DataElement)segment.buildDE(6); // 235 Product/Service ID Qualifier
        de.set("VN");
        de = (DataElement)segment.buildDE(7); // 234 Product/Service ID
        de.set(currTRApprovedData.getProductSku().trim());
    }
    
    /** builds segment PID that is part of the DetailPO1
     *<br>Product/Item Description used
     *<br>To describe a product or process in coded or free-form format
     *param inSegment segment containing this subsegment
     *throws OBOEException - most likely segment not found
     */
    public void buildDetailPO1PID(Loop inLoop)
    throws OBOEException {

    	Loop loop = inLoop.createLoop("PID");
    	inLoop.addLoop(loop);
    	Segment segment = loop.createSegment("PID");
    	loop.addSegment(segment);

        DataElement de;
        de = (DataElement)segment.buildDE(1); // 349 Item Description Type
        de.set("F");
        de = (DataElement)segment.buildDE(2); // 750 Product/Process Characteristic Code
        de.set("");
        de = (DataElement)segment.buildDE(3); // 559 Agency Qualifier Code
        de.set("");
        de = (DataElement)segment.buildDE(4); // 751 Product Description Code
        de.set("");
        de = (DataElement)segment.buildDE(5); // 352 Description
        String itemDesc = currTRApprovedData.getProductDesc();
        if (itemDesc.length() > 80){
        	itemDesc = itemDesc.substring(0, 80);
  	    }
        itemDesc = Utility.removeSpecialCharachters(itemDesc, new Character('.'));
        de.set(itemDesc);
    }
    
    /** builds segment N1 that is part of the Header
     *<br>Name used
     *<br>To identify a party by type of organization, name, and code
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildHeaderN1(Table inTable, String idCode)
    throws OBOEException {

        Loop loop = inTable.createLoop("N1");
        inTable.addLoop(loop);
        Segment segment = loop.createSegment("N1");
        loop.addSegment(segment);
        DataElement de;
        de = (DataElement)segment.buildDE(1); // 98 Entity Identifier Code
        de.set(idCode);
        de = (DataElement)segment.buildDE(2); // 93 Name

       if (idCode.equals("ST")) {
            de.set(currTRApprovedData.getContractDesc());
            de = (DataElement)segment.buildDE(3);
            de.set("92");
            de = (DataElement)segment.buildDE(4);
            de.set(currTRApprovedData.getContractNumber());
        }
    }
    
    public void buildTransactionTrailer()
    throws Exception {

        Table table = ts.getSummaryTable();
        buildSummaryCTT(table);
        buildSummarySE(table);
        fg.addTransactionSet(ts);
        super.buildTransactionTrailer();
    }
    
    /** builds segment CTT that is part of the Summary
     *<br>Transaction Totals used
     *<br>To transmit a hash total for a specific element in the transaction set
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildSummaryCTT(Table inTable)
    throws OBOEException {

        Loop loop = inTable.createLoop("CTT");
        inTable.addLoop(loop);
        Segment segment = loop.createSegment("CTT");
        loop.addSegment(segment);

        DataElement de;
        de = (DataElement)segment.buildDE(1); // 354 Number of Line Items
        de.set("" + transRebateApprovedV.size());
    }
    
    
}
