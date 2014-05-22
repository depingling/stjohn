package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;
import com.americancoders.edi.x12.*;
import java.util.Vector;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.rmi.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.apps.dataexchange.OutboundTranslate;

/**
 * Formats the order extraction into a EDI document, the format specified by Lagase.
 *
 * @author Deping
 */
public class Outbound810_JCP extends Outbound810Super
{
    /** builds segment BIG that is part of the Header
     *<br>Beginning Segment for Invoice used
     *<br>To indicate the beginning of an invoice transaction set and transmit identifying numbers and dates
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    // JCP version uses 2-digit years and doesn't have 373 Date
    public void buildHeaderBIG(Table inTable)
    throws OBOEException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyMMdd");
        Segment segment = inTable.createSegment("BIG");
        inTable.addSegment(segment);  DataElement de;
        de = (DataElement) segment.buildDE(1);  // 373 Date
        de.set(dateFormatter.format(currInvoice.getInvoiceDate()));
        de = (DataElement) segment.buildDE(2);  // 76 Invoice Number
        de.set(currInvoice.getInvoiceNum());
        // Date is optional for JCP so we won't use it.  If we have
        // a PO number then we need to set the Date field, then the
        // PO number.  Otherwise if we don't have a PO number we need
        // to end the segment now.
        if (Utility.isSet(currOrder.getRequestPoNum())){
          de = (DataElement) segment.buildDE(3);  // 373 Date
          de.set(""); // Date
          de = (DataElement) segment.buildDE(4);  // 324 Purchase Order Number
          de.set(currOrder.getRequestPoNum());
        }
    }

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
        de = (DataElement) segment.buildDE(1);  // 128 Reference Id Qualifier
        // JCP: Change value from "1V"
        de.set("DP");
        de = (DataElement) segment.buildDE(2);  // 127 Reference Identification
        // JCP: Change from ""+currOrder.getFrontEndOrderNumber()
        de.set("000");
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
        de = (DataElement) segment.buildDE(1);  // 128 Reference Id Qualifier
        // JCP: Changed from SI
        de.set("AE");
        de = (DataElement) segment.buildDE(2);  // 127 Reference Identification
        // JCP: Changed from vendorShipmentId
        de.set(""+currOrder.getRefOrderNum());
    }

    /** builds segment REF that is part of the Header
     *<br>Reference Identification used
     *<br>To specify identifying information
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildHeaderREF2(Table inTable)
    throws OBOEException {
        Segment segment = inTable.createSegment("REF");
        inTable.addSegment(segment);  DataElement de;
        de = (DataElement) segment.buildDE(1);  // 128 Reference Id Qualifier
        de.set("IA");
        de = (DataElement) segment.buildDE(2);  // 127 Reference Identification
        de.set("01828-3");
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
      if (!code.equals("RE") && !code.equals("BY")){
        return;
      }

      Segment segment = inTable.createSegment("N1");
      inTable.addSegment(segment);
      DataElement de;
      de = (DataElement) segment.buildDE(1);  // 98 Entity Identifier Code
      de.set(code);
      de = (DataElement) segment.buildDE(2);  // 93 Name
      if (code.equals("RE")){
        de.set("CLEANWISE");
      }
      else if (code.equals("BY")){
        de.set(mCustShipAddr.getAddress1());// customer ship name
        de = (DataElement) segment.buildDE(3);  // 66 Identification Code Qualifier
        de.set("92");
        de = (DataElement) segment.buildDE(4);  // 67 Identification Code

	String sname = Utility.getEDIToken(currOrder.getOrderSiteName());
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

    /** builds segment IT1 that is part of the Detail
     *<br>Baseline Item Data (Invoice) used
     *<br>To specify the basic and most frequently used line item data for the invoice and related transactions
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildDetailIT1(Table inTable, String customerLineNumber)
    throws OBOEException {
        Loop loop = inTable.createLoop("IT1");
        inTable.addLoop(loop);
// YR OBOE        Segment segment = inTable.createSegment("IT1");
        Segment segment = loop.createSegment("IT1");
// YR OBOE        inTable.addSegment(segment);
        loop.addSegment(segment);

        DataElement de;
        de = (DataElement) segment.buildDE(1);  // 350 Assigned Identification
        de.set(customerLineNumber);
        de = (DataElement) segment.buildDE(2);  // 358 Quantity Invoiced
        de.set(""+currInvoiceItem.getItemQuantity());
        de = (DataElement) segment.buildDE(3);  // 355 Unit or Basis for Measurement Code
        TradingPartnerData tp = ((OutboundTranslate)translator).getPartner();
        if(tp.getSkuTypeCd().equals(RefCodeNames.SKU_TYPE_CD.CUSTOMER) && Utility.isSet(currItem.getCustItemUom())){
          de.set(""+currItem.getCustItemUom());
        }else{
          de.set(""+currItem.getItemUom());
        }
        de = (DataElement) segment.buildDE(4);  // 212 Unit Price
        de.set(""+currInvoiceItem.getCustContractPrice());
        de = (DataElement) segment.buildDE(5);  // 639 Basis of Unit Price Code
        de.set("");
        de = (DataElement) segment.buildDE(6);  // 235 Product/Service ID Qualifier
        de.set("CB");
        de = (DataElement) segment.buildDE(7);  // 234 Product/Service ID
	de.set(Utility.extractPrimarySku(currItem));

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
        Segment segment = inTable.createSegment("ISS");
        inTable.addSegment(segment);
        DataElement de;
        de = (DataElement) segment.buildDE(1);  // 382 Number of Units Shipped
        de.set("1");
        de = (DataElement) segment.buildDE(2);  // 355 Unit or Basis for Measurement Code
        de.set("CA");
    }

  public void buildTransactions(int incomingProfileId)
    throws OBOEException, RemoteException
  {
    OutboundEDIRequestDataVector invoiceReqDV = getOutboundTransactionsToProcess();
    OutboundEDIRequestDataVector tempInvoiceReqDV = new OutboundEDIRequestDataVector();
    OutboundEDIRequestData currInvoiceReqD;

    for (int i = 0; i < invoiceReqDV.size(); i++){
      currInvoiceReqD = (OutboundEDIRequestData)invoiceReqDV.get(i);
      if (currInvoiceReqD.getOrderD().getIncomingTradingProfileId() != incomingProfileId){
        System.out.println("Error should not happen, this logic was moved to builder!");
        tempInvoiceReqDV.add(currInvoiceReqD);
        continue;
      }

      currOrder = currInvoiceReqD.getOrderD();
      currInvoice = currInvoiceReqD.getInvoiceCustD();
      invoiceItems = currInvoiceReqD.getInvoiceDetailDV();
      mCustShipAddr = currInvoiceReqD.getCustShipAddr();
      originalDateOrdered = currInvoiceReqD.getOriginalDateOrdered();
      creditFlag = (currInvoice.getInvoiceType().equalsIgnoreCase(RefCodeNames.INVOICE_TYPE_CD.CR));
      currInvoice.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.CUST_INVOICED);
      appendIntegrationRequest(currInvoice); // for update the status

      buildTransaction();
    }

    ((OutboundTranslate)translator).setOutboundReqOrderDV(tempInvoiceReqDV);
  }
}

