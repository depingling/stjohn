package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;
import com.americancoders.edi.x12.*;
import java.util.Vector;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.Utility;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Date;
import java.util.Hashtable;
import java.util.Enumeration;
import java.math.*;
import java.rmi.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.apps.dataexchange.OutboundTranslate;

/**
 * Formats the order extraction into a EDI document, the format specified by Lagase.
 *
 * @author Deping
 */
public abstract class Outbound810Super extends OutboundSuper
{
  protected OrderAddressData mCustBillAddr;
  //static final String FREIGHT_SAC = "D500";
  //changed to D240 (freight) per JCPenny request
  static final String FREIGHT_SAC = "D240";
  static final String SALES_TAX_SAC = "H740";
  protected String originalDateOrdered;
  protected boolean creditFlag = false;
  protected String freightChargeType;
  protected boolean
    mReviseOrderDate = false,
    mSendZeroDollar810 = true
    ;

  protected BigDecimal
    mTotalInvoiced = new BigDecimal(0),
    mTotalCredited = new BigDecimal(0)
    ;

    protected Hashtable
    mTotalInvoicedTable = new Hashtable(),
    mTotalCreditedTable = new Hashtable()
    ;

    /** builds segment BIG that is part of the Header
     *<br>Beginning Segment for Invoice used
     *<br>To indicate the beginning of an invoice transaction set and transmit identifying numbers and dates
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildHeaderBIG(Table inTable)
    throws OBOEException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyyMMdd");
        Segment segment = inTable.createSegment("BIG");
        inTable.addSegment(segment);  DataElement de;
        de = (DataElement) segment.buildDE(1);  // 373 Date
        de.set(dateFormatter.format(currInvoice.getInvoiceDate()));
        de = (DataElement) segment.buildDE(2);  // 76 Invoice Number
        if(RefCodeNames.INVOICE_TYPE_CD.IN.equals(currInvoice.getInvoiceType())){
            de.set(currInvoice.getInvoiceNum());
        }else{
            de.set(currInvoice.getInvoiceType() + currInvoice.getInvoiceNum());
        }
        de = (DataElement) segment.buildDE(3);  // 373 Date
        de.set(originalDateOrdered);
        de = (DataElement) segment.buildDE(4);  // 324 Purchase Order Number
        de.set(Utility.subString(currOrder.getRequestPoNum(), 12));
        if (!creditFlag)
          return;

        de = (DataElement) segment.buildDE(5);  // 328 Release Number
        de.set("");
        de = (DataElement) segment.buildDE(6);  // 327 Change Order Sequence Number
        de.set(RefCodeNames.INVOICE_TYPE_CD.CR);
        de = (DataElement) segment.buildDE(7);  // 640 Transaction Type Code
    }


    /** builds segment CUR that is part of the Header (Currency)
     *<br>Reference Identification used
     *<br>To specify identifying information
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildHeaderCUR(Table inTable) throws OBOEException {
    };

    /** builds segment REF that is part of the Header
     *<br>Reference Identification used
     *<br>To specify identifying information
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    // Subcasses must override if they need it
    public void buildHeaderREF(Table inTable) throws OBOEException {};

    /** builds segment REF that is part of the Header
     *<br>Reference Identification used
     *<br>To specify identifying information
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    // Subcasses must override if they need it
    public void buildHeaderREF1(Table inTable) throws OBOEException {};

    /** builds segment REF that is part of the Header
     *<br>Reference Identification used
     *<br>To specify identifying information
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    // Subcasses must override if they need it
    public void buildHeaderREF2(Table inTable) throws OBOEException {};


    /** builds segment PER that is part of the Header (Administrative Contact)
     *<br>Reference Identification used
     *<br>To specify identifying information
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildHeaderPER(Table inTable) throws OBOEException {
    };

    /** builds segment PER that is part of the Header (Administrative Contact)
     *<br>Reference Identification used
     *<br>To specify identifying information
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildHeaderPER1(Table inTable) throws OBOEException {
    };

    /** builds segment N1 that is part of the Header
     *<br>Name used
     *<br>To identify a party by type of organization, name, and code
     * @param inTable table containing this segment
     * @throws OBOEException - most likely segment not found
     */
    // Subcasses must override if they need it
    public void buildHeaderN1(Table inTable, String code) throws OBOEException {}


    /** builds segment ITD that is part of the Header
     *<br>Terms of Sale/Deferred Terms of Sale used
     *<br>To specify terms of sale
     * @param inTable table containing this segment
     * @throws OBOEException - most likely segment not found
    */
    // Subcasses must override if they need it
    public void buildHeaderITD(Table inTable) throws OBOEException {}

    /** builds segment DTM that is part of the Header
     *<br>Date/Time Reference used
     *<br>To specify pertinent dates and times
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
    */
    public void buildHeaderDTM(Table inTable) throws OBOEException {}

    /** builds segment FOB that is part of the Header
     *<br>F.O.B. Related Instructions used
     *<br>To specify transportation instructions relating to shipment
     * @param inTable table containing this segment
     * @throws OBOEException - most likely segment not found
     */
    // Subcasses must override if they need it
    public void buildHeaderFOB(Table inTable) throws OBOEException {}

    /** builds segment PID that is part of the Header
     *<br>PID Product Item Description
     *<br>To Specify Fair Labor Standards compliance for all products on invoice.
     * @param inTable table containing this segment
     * @throws OBOEException - most likely segment not found
     */
    // Subcasses must override if they need it
    public void buildHeaderPID(Table inTable) throws OBOEException{}

    /** builds segment N9 that is part of the Header
     *<br>Identification Reference used
     *<br>To transmit identifying information as specified by the Reference Identification Qualifier
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
    */
    public void buildHeaderN9(Table inTable) throws OBOEException {}

    /** builds segment IT1 that is part of the Detail
     *<br>Baseline Item Data (Invoice) used
     *<br>To specify the basic and most frequently used line item data
     *for the invoice and related transactions
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    // Subcasses must provide implementation
    abstract public void buildDetailIT1(Table inTable,
                                        String customerLineNumber)
    throws OBOEException;

    /** builds segment PID that is part of the DetailIT1
     *<br>Product/Item Description used
     *<br>To describe a product or process in coded or free-form format
     * @param loop the loop containing this segment
     * @throws OBOEException - most likely segment not found
     */
    // Subcasses must override if they need it (and also call it)
    public void buildDetailIT1PID(Loop inLoop) throws OBOEException {}

    /** builds segment REF that is part of the DetailIT1
     *<br>Reference Identification used
     *<br>To specify identifying information
     *param inSegment segment containing this subsegment
     *throws OBOEException - most likely segment not found
     */
    // Subcasses must override if they need it (and also call it)
    public void buildDetailIT1REF(Loop inLoop)  throws OBOEException {};

    /** builds segment TDS that is part of the Summary
     *<br>Total Monetary Value Summary used
     *<br>To specify the total invoice discounts and amounts
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildSummaryTDS(Table inTable)
    throws OBOEException {
        Segment segment = inTable.createSegment("TDS");
        inTable.addSegment(segment);

        DataElement de;
        de = (DataElement)segment.buildDE(1);  // 610 Amount

        BigDecimal bigAmount = currInvoice.getNetDue().setScale(2, BigDecimal.ROUND_HALF_UP);
        bigAmount = bigAmount.multiply(new BigDecimal(100));
        int amount = bigAmount.intValue();
        de.set(""+amount);

    }


    /** builds segment TXI (Sales Tax) that is part of the Summary
     *<br>TSales Tax Summary used
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildSummaryTXI(Table inTable)
    throws OBOEException {
    }

     /** builds segment CAD that is part of the Summary
     *<br>Carrier Detail used
     *<br>To specify transportation details for the transaction
     * @param inTable table containing this segment
     * @throws OBOEException - most likely segment not found
     */
    // Subcasses must override if they need it
    public void buildSummaryCAD(Table inTable) throws OBOEException {}

    /** builds segment SAC that is part of the Summary
     *<br>Service, Promotion, Allowance, or Charge Information used
     *<br>To request or identify a service, promotion, allowance, or charge;
     * to specify the amount or percentage for the service, promotion,
     * allowance, or charge
     * @param inTable table containing this segment
     * @param chargeType string indicating the type of charge to be printed
     * @throws OBOEException - most likely segment not found
     */
    // Leave in superclass, it will only create the segment if there's
    // actually a freight charge or a sales tax charge (depending on
    // input argument), neither of which should be present for USPS,
    // so should be benign, though note the format is really laid out
    // according to what JCP wants.
    // XXX: USPS: Should add version in USPS class that calls superclass
    // version and throws an exception if anything but null is returned
    public void buildSummarySAC(Table inTable, String chargeType)
    throws OBOEException
    {
        BigDecimal amount = null;
        String description = null;
        // We call it "misc_charges" when we are actually representing this to the customer as
        // a form of freight.  The code (contents of FREIGHT_SAC) dictates how the customer will
        // deal with this charge.
        if (FREIGHT_SAC.equals(chargeType)) {
            if (currInvoice.getMiscCharges() != null &&
                currInvoice.getMiscCharges().compareTo(new BigDecimal(BigInteger.ZERO)) != 0) {
                amount = currInvoice.getMiscCharges();
                description = "FREIGHT CHARGE";
            }
        } else if (SALES_TAX_SAC.equals(chargeType)) {
            if (currInvoice.getSalesTax() != null &&
                currInvoice.getSalesTax().compareTo(new BigDecimal(BigInteger.ZERO)) != 0) {
                amount = currInvoice.getSalesTax();
                description = "SALES TAX";
                System.out.println(" currInvoice, id="
				   + currInvoice.getInvoiceId()
				   + " sales tax value=" + amount);
            }
        } else {
            throw new OBOEException("Unrecognized charge type for SAC segment: "
                                    + chargeType);
        }

        // Don't want to generate this segment if there's not a charge
        if (amount == null) {
            return;
        }
        amount = amount.multiply(new BigDecimal(100));
        int amountOut = amount.intValue();
	System.out.println(" currInvoice, id="
			   + currInvoice.getInvoiceId()
			   + " amountOut=" + amountOut);
        Loop loop = inTable.createLoop("SAC");
        inTable.addLoop(loop);
        Segment segment = loop.createSegment("SAC");
        loop.addSegment(segment);

        DataElement de;
        de = (DataElement) segment.buildDE(1);  // 248 Allowance or Charge Indicator
        //amount should always be positive, but it is either an allowance (credit) or charge
        if(amountOut < 0){
            de.set("A");
            amountOut = amountOut * -1;
        }else{
            de.set("C");
        }
        de = (DataElement) segment.buildDE(2);  // 1300 Service, Promotion, Allowance, or Charge Code
        de.set(chargeType);
        de = (DataElement) segment.buildDE(3);  // 559 Agency Qualifier Code
        de.set("");
        de = (DataElement) segment.buildDE(4);  // 1301 Agency Service, Promotion, Allowance, or Charge Code
        de.set("");
        de = (DataElement) segment.buildDE(5);  // 610 Amount
        de.set("" + amountOut);
        de = (DataElement) segment.buildDE(6);  // 378 Allowance/Charge Percent Qualifier
        de.set("");
        de = (DataElement) segment.buildDE(7);  // 332 Percent
        de.set("");
        de = (DataElement) segment.buildDE(8);  // 118 Rate
        de.set("");
        de = (DataElement) segment.buildDE(9);  // 355 Unit or Basis for Measurement Code
        de.set("");
        de = (DataElement) segment.buildDE(10);  // 380 Quantity
        de.set("");
        de = (DataElement) segment.buildDE(11);  // 380 Quantity
        de.set("");
        de = (DataElement) segment.buildDE(12);  // 331 Allowance or Charge Method of Handling Code
        de.set("06");
        de = (DataElement) segment.buildDE(13);  // 127 Reference Identification
        de.set("");
        de = (DataElement) segment.buildDE(14);  // 770 Option Number
        de.set("");
        de = (DataElement) segment.buildDE(15);  // 352 Description
        de.set(description);
  }

    /** builds segment ISS that is part of the Summary
     *<br>Invoice Shipment Summary used
     *<br>To specify summary details of total items shipped in terms of
     * quantity, weight, and volume
     * @param inTable table containing this segment
     * @throws OBOEException - most likely segment not found
     */
    // Subcasses must override if they need it
    public void buildSummaryISS(Table inTable) throws OBOEException {}

    /** builds segment CTT that is part of the Summary
     *<br>Transaction Totals used
     *<br>To transmit a hash total for a specific element in the transaction set
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    // Leave in superclass
    public void buildSummaryCTT(Table inTable)
    throws OBOEException {
        Segment segment = inTable.createSegment("CTT");
        inTable.addSegment(segment);
        DataElement de;
        de = (DataElement) segment.buildDE(1);  // 354 Number of Line Items
        de.set(""+invoiceItems.size());
    }

    /**
     *Classes that overide this will need to control the way that the ref segments are
     *called, otherwise the buildHeaderREF, buildHeaderREF1, and buildHeaderREF2 methods will
     *be called.
     */
    protected void buildHeaderREFSegments(Table table){
        buildHeaderREF(table);
        buildHeaderREF1(table);
        buildHeaderREF2(table);                     // Added for JCP, CIT
    }

    /**
     *Classes that overide this will need to control the way that the per segments are
     *called, otherwise the buildHeaderPER, and buildHeaderPER1 methods will
     *be called.
     */
    protected void buildHeaderPERSegments(Table table){
        buildHeaderPER(table);                      // Added for JCI
        buildHeaderPER1(table);                     // Added for JCI
    }

    // Leave in superclass for now.
    public void buildTransactionContent() throws OBOEException {

        Table table = ts.getHeaderTable();
        buildHeaderBIG(table);
        buildHeaderCUR(table);                      // Added for JCI
        buildHeaderREFSegments(table);
        buildHeaderPERSegments(table);
        buildAllMappedHeader(table);
        buildHeaderN1(table, "RE");                 // Added for JCP
        buildHeaderN1(table, "BY");                 // Added for JCP, CIT
        buildHeaderN1(table, "BT");                 // Added for JCP v4030
        buildHeaderN1(table, "ST");                 // Added for CIT
        buildHeaderITD(table);                      // Added for JCP
        buildHeaderDTM(table);                      // Added for CIT
        buildHeaderFOB(table);                      // Added for JCP
        buildHeaderPID(table);                      // Added for JCP v4030
        buildHeaderN9(table);                       // Added for CIT
        table = ts.getDetailTable();

      for (int i = 0; i < invoiceItems.size(); i++)
      {
        InvoiceCustDetailRequestData reqDetail = (InvoiceCustDetailRequestData)invoiceItems.get(i);
        currInvoiceItem = reqDetail.getInvoiceDetailD();
        currItem = reqDetail.getOrderItemD();
        buildDetailIT1(table, ""+currItem.getCustLineNum());
        if(!"CIT".equalsIgnoreCase(translator.getErpNum())){
              currInvoiceItem.setInvoiceDetailStatusCd(RefCodeNames.INVOICE_DETAIL_STATUS_CD.CUST_INVOICED);
        }
        appendIntegrationRequest(currInvoiceItem); // for update the status
      }
      transactionD.setOrderId(currOrder.getOrderId());
      transactionD.setReferenceId(currInvoice.getInvoiceCustId());
      transactionD.setReferenceTable(InvoiceCustDataAccess.CLW_INVOICE_CUST);
      transactionD.setKeyString("ErpPoNum: " + currOrder.getErpOrderNum()
            + ", FrontEndOrderNumber: " + currOrder.getOrderNum()
            + ", CustomerPoNumber: " + currOrder.getRequestPoNum()
            + ", InvoiceNumber: " + currInvoice.getInvoiceNum());
      if("CIT".equalsIgnoreCase(translator.getErpNum())){
        transactionD.setKeyString(transactionD.getKeyString() + ", CIT");
      }
    }

    // Leave in superclass for now.
    public void buildTransactionTrailer() throws OBOEException {
      Table table = ts.getSummaryTable();
      buildSummaryTDS(table);
      buildSummaryTXI(table);                       // Added for JCI (Tax information);
      buildSummaryCAD(table);                       // Added for JCP
      buildSummarySAC(table, FREIGHT_SAC);          // Added for JCP, CIT
      buildSummarySAC(table, SALES_TAX_SAC);        // Added for JCP, CIT
      buildSummaryISS(table);                       // Added for JCP, CIT
      buildSummaryCTT(table);
      buildSummarySE(table);
      fg.addTransactionSet(ts);
    }

    protected void setOriginalDateOrdered(String pOrigDate) {
	originalDateOrdered = pOrigDate;
	if (mReviseOrderDate == true) {
	    if ( originalDateOrdered.length() == 6) {
                //XXX should use yyyy format as base, and strip out the 1st 2 digits if
                //they are not necessary not the other way around.  This presents a Y21K problem
		// The date presented is in yymmdd format.
		// Prepend the century.
		// This is a requirement for CIT 810 records.
		// JCP order date fmt is : yymmdd
		// USPS order date fmt is: yyyymmdd
		// CIT order date fmt is : yyyymmdd
		String t = "20" + originalDateOrdered;
		originalDateOrdered = t;
	    }
	}
    }

    private     boolean printout = true;

  public void buildTransactions(int incomingProfileId)
    throws OBOEException, RemoteException
  {
    OutboundEDIRequestDataVector invoiceReqDV = getOutboundTransactionsToProcess();
    OutboundEDIRequestDataVector tempInvoiceReqDV = new OutboundEDIRequestDataVector();
    OutboundEDIRequestData currInvoiceReqD;

    for (int i = 0; i < invoiceReqDV.size(); i++){
      currInvoiceReqD = (OutboundEDIRequestData)invoiceReqDV.get(i);
      currEDIRequestD = currInvoiceReqD;

      currInvoice = currInvoiceReqD.getInvoiceCustD();

      /*if (currInvoiceReqD.getOrderD().getIncomingTradingProfileId() != incomingProfileId){
        tempInvoiceReqDV.add(currInvoiceReqD);
        continue;
      }*/

      currOrder = currInvoiceReqD.getOrderD();
      invoiceItems = currInvoiceReqD.getInvoiceDetailDV();
      mCustShipAddr = currInvoiceReqD.getCustShipAddr();
      mCustBillAddr = currInvoiceReqD.getCustBillAddr();
      setOriginalDateOrdered(currInvoiceReqD.getOriginalDateOrdered());
      creditFlag = (currInvoice.getInvoiceType().equalsIgnoreCase(RefCodeNames.INVOICE_TYPE_CD.CR));

      if("CIT".equalsIgnoreCase(translator.getErpNum())){
        currInvoice.setCitStatusCd(RefCodeNames.CIT_STATUS_CD.SUCCESS);
        freightChargeType = currInvoiceReqD.getFreightChargeType();
      }
      else{
        currInvoice.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.CUST_INVOICED);
      }

      BigDecimal invamt =
	currInvoice.getNetDue().setScale(2, BigDecimal.ROUND_HALF_UP);

    addAmountToCounters(invamt, currInvoice.getInvoiceDate());

      if (mSendZeroDollar810 == false) {
	if ( invamt.compareTo(new BigDecimal(0)) == 0 ) {
	  // This is a zero dollar invoice to be skipped.
	  System.out.println("    Skipping zero value invoice: " +
			     currInvoice.getInvoiceCustId());
	  currInvoice.setCitStatusCd(RefCodeNames.CIT_STATUS_CD.SUCCESS);
	  appendIntegrationRequest(currInvoice); // to update the status
	  continue;
	}
      }
      appendIntegrationRequest(currInvoice); // to update the status
      buildTransaction();
    }

    ((OutboundTranslate)translator).setOutboundReqOrderDV(tempInvoiceReqDV);

    if ( printout ) {
        // Just print the counters once.
        printCounters();
        printout = false;
    }
  }

  private void printCounters() {

      System.out.println("\n---" +
      "\n--- TotalInvoiced=" + mTotalInvoiced +
      "\n--- TotalCredited=" + mTotalCredited +
      "\n---" );

      Enumeration invDates = mTotalInvoicedTable.keys();
      while ( invDates.hasMoreElements() ) {
          Date d = (Date)invDates.nextElement();
          BigDecimal amt = (BigDecimal)mTotalInvoicedTable.get(d);
          System.out.println(" ++ Invoice Date=" + d +
          " Amount=" + amt);
      }

      Enumeration credDates = mTotalCreditedTable.keys();
      while ( credDates.hasMoreElements() ) {
          Date d = (Date)credDates.nextElement();
          BigDecimal amt = (BigDecimal)mTotalCreditedTable.get(d);
          System.out.println(" -- Credit Date=" + d +
          " Amount=" + amt);
      }
      System.out.println("\n---" );

  }

  private void addAmountToCounters(BigDecimal pAmt, Date pInvDate) {
      if ( pAmt.compareTo(new BigDecimal(0)) > 0  ) {
	mTotalInvoiced = mTotalInvoiced.add(pAmt);
    if ( pInvDate != null ) {
    if (  mTotalInvoicedTable.containsKey(pInvDate) ) {
        BigDecimal t = (BigDecimal)mTotalInvoicedTable.get(pInvDate);
        t = t.add(pAmt);
        mTotalInvoicedTable.put(pInvDate, t);
    }
    else {
        mTotalInvoicedTable.put(pInvDate, pAmt);
    }
    }
      }
      else {
	mTotalCredited = mTotalCredited.add(pAmt);
    if ( pInvDate != null ) {
    if (  mTotalCreditedTable.containsKey(pInvDate) ) {
        BigDecimal t = (BigDecimal)mTotalCreditedTable.get(pInvDate);
        t = t.add(pAmt);
        mTotalCreditedTable.put(pInvDate, t);
    }
    else {
        mTotalCreditedTable.put(pInvDate, pAmt);
    }
    }
      }
}


}


