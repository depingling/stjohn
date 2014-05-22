package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.*;

/**
 * Formats the order extraction into a EDI document, the format specified by Lagase.
 *
 * @author Deping
 */
public class Outbound810_USPS extends Outbound810Super
{
    /** builds segment REF that is part of the Header
     *<br>Reference Identification used
     *<br>To specify identifying information
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildHeaderREF(Table inTable)
    throws OBOEException {
        Segment segment = inTable.createSegment("REF");
        inTable.addSegment(segment);  DataElement de;
        de = (DataElement) segment.buildDE(1);  // 128 Reference Identification Qualifier
        de.set("1V");
        de = (DataElement) segment.buildDE(2);  // 127 Reference Identification
        de.set(""+currOrder.getOrderNum());
    }

    /** builds segment REF that is part of the Header
     *<br>Reference Identification used
     *<br>To specify identifying information
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildHeaderREF1(Table inTable)
    throws OBOEException {
        Segment segment = inTable.createSegment("REF");
        inTable.addSegment(segment);  DataElement de;
        de = (DataElement) segment.buildDE(1);  // 128 Reference Identification Qualifier
        de.set("SI");
        de = (DataElement) segment.buildDE(2);  // 127 Reference Identification
        if (creditFlag){
          de.set(currInvoice.getOriginalInvoiceNum());
        }
        else{
          de.set(currInvoice.getInvoiceNum()); // should be shipment id which is same as invoice num
        }

    }

    /** builds segment IT1 that is part of the Detail
     *<br>Baseline Item Data (Invoice) used
     *<br>To specify the basic and most frequently used line item data
     *for the invoice and related transactions
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildDetailIT1(Table inTable, String customerLineNumber)
    throws OBOEException {
    	Loop loop = inTable.createLoop("IT1");
        inTable.addLoop(loop);
        Segment segment = loop.createSegment("IT1");
        loop.addSegment(segment);
        buildDetailIT1REF(loop);

        DataElement de;
        de = (DataElement) segment.buildDE(1);  // 350 Assigned Identification
        de.set(""+currInvoiceItem.getLineNumber());
        de = (DataElement) segment.buildDE(2);  // 358 Quantity Invoiced
        if (currInvoiceItem.getItemQuantity() < 0)
          de.set(""+currInvoiceItem.getItemQuantity()*(-1));
        else
          de.set(""+currInvoiceItem.getItemQuantity());
        de = (DataElement) segment.buildDE(3);  // 355 Unit or Basis for Measurement Code
        de.set(""+Utility.getActualUom(currOutboundReq.getStoreType(), currItem));
        de = (DataElement) segment.buildDE(4);  // 212 Unit Price
        de.set(""+currInvoiceItem.getCustContractPrice());
        de = (DataElement) segment.buildDE(5);  // 639 Basis of Unit Price Code
        de.set("");
        de = (DataElement) segment.buildDE(6);  // 235 Product/Service ID Qualifier
        de.set("SW");
        de = (DataElement) segment.buildDE(7);  // 234 Product/Service ID
        de.set(""+Utility.getActualSkuNumber(currOutboundReq.getStoreType(),currItem));
        de = (DataElement) segment.buildDE(8);  // 235 Product/Service ID Qualifier
        de.set("PL");
        de = (DataElement) segment.buildDE(9);  // 234 Product/Service ID
        de.set(customerLineNumber);
    }

    /** builds segment REF that is part of the DetailIT1
     *<br>Reference Identification used
     *<br>To specify identifying information
     *param inSegment segment containing this subsegment
     *throws OBOEException - most likely segment not found
     */
    public void buildDetailIT1REF(Loop inLoop)  throws OBOEException {
        Segment segment = inLoop.createSegment("REF");
        inLoop.addSegment(segment);  DataElement de;
        de = (DataElement) segment.buildDE(1);  // 128 Reference Identification Qualifier
        
        de.set("CT");
        de = (DataElement) segment.buildDE(2);  // 127 Reference Identification
        de.set("483083-01-B-0679");
    }
}

