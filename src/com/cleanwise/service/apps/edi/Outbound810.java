package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.apache.log4j.Logger;

/**
 * Formats the order extraction into a EDI document, the format specified by Lagase.
 *
 * @author Deping
 */
public class Outbound810 extends Outbound810Super
{
    private static final Logger log = Logger.getLogger(Outbound810.class);
    /** builds segment SAC that is part of the Summary
     *<br>Service, Promotion, Allowance, or Charge Information used
     *<br>To request or identify a service, promotion, allowance, or charge;
     * to specify the amount or percentage for the service, promotion,
     * allowance, or charge
     * @param inTable table containing this segment
     * @param chargeType string indicating the type of charge to be printed
     * @throws OBOEException - most likely segment not found
     */
    //JCI is goofy as they have a special tax segment
    public void buildSummarySAC(Table inTable, String chargeType)
    throws OBOEException
    {
        BigDecimal amount = null;
        String description = null;
        // We call it "misc_charges" when we are actually representing this to the customer as
        // a form of freight.  The code (contents of FREIGHT_SAC) dictates how the customer will
        // deal with this charge.
        if (FREIGHT_SAC.equals(chargeType)) {
            BigDecimal frt = Utility.addAmt(currInvoice.getMiscCharges(), currInvoice.getFreight());
            log.info("frt="+frt);
            if (frt != null && frt.compareTo(new BigDecimal(BigInteger.ZERO)) != 0) {
                amount = frt;
                description = "FREIGHT CHARGE";
            }

        } else if (SALES_TAX_SAC.equals(chargeType)) {
            //special for JCI
            return;
        } else {
            throw new OBOEException("Unrecognized charge type for SAC segment: "
                                    + chargeType);
        }

        // Don't want to generate this segment if there's not a charge
        if (amount == null) {
            return;
        }

        Loop loop = inTable.createLoop("SAC");
        inTable.addLoop(loop);
        Segment segment = loop.createSegment("SAC");
        loop.addSegment(segment);

        DataElement de;
        de = (DataElement) segment.buildDE(1);  // 248 Allowance or Charge Indicator
        de.set("C");
        de = (DataElement) segment.buildDE(2);  // 1300 Service, Promotion, Allowance, or Charge Code
        de.set(chargeType);
        de = (DataElement) segment.buildDE(3);  // 559 Agency Qualifier Code
        de.set("");
        de = (DataElement) segment.buildDE(4);  // 1301 Agency Service, Promotion, Allowance, or Charge Code
        de.set("");
        de = (DataElement) segment.buildDE(5);  // 610 Amount
        int amountOut = (int)(amount.doubleValue() * 100);
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


    protected void buildHeaderREFSegments(Table table){
        return;
    }

    protected void buildHeaderPERSegments(Table table){
        return;
    }


    /** builds segment TXI (Sales Tax) that is part of the Summary
     *<br>TSales Tax Summary used
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildSummaryTXI(Table inTable)
    throws OBOEException {
        Segment segment = inTable.createSegment("TXI");
        inTable.addSegment(segment);

        DataElement de;
        de = (DataElement)segment.buildDE(1);  // 963 code
        de.set("TX");
        de = (DataElement)segment.buildDE(2);  // 782 Amount
        BigDecimal bigAmount = currInvoice.getSalesTax();
        if(bigAmount == null){
            bigAmount = new BigDecimal(0.00);
        }
        bigAmount = bigAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
        de.set(bigAmount.toString());
    }



    /** builds segment IT1 that is part of the Detail
     *<br>Baseline Item Data (Invoice) used
     *<br>To specify the basic and most frequently used line item data for the invoice and related transactions
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildDetailIT1(Table inTable, String customerLineNumber)
    throws OBOEException {
// YR OBOE        Segment segment = inTable.createSegment("IT1");
        Loop loop = inTable.createLoop("IT1");
// YR OBOE        inTable.addSegment(segment);
        inTable.addLoop(loop);
        Segment segment = loop.createSegment("IT1");
        loop.addSegment(segment);

        DataElement de;
        de = (DataElement) segment.buildDE(1);  // 350 Assigned Identification
        de.set(customerLineNumber);
        de = (DataElement) segment.buildDE(2);  // 358 Quantity Invoiced
        de.set(""+currInvoiceItem.getItemQuantity());
        de = (DataElement) segment.buildDE(3);  // 355 Unit or Basis for Measurement Code
        TradingPartnerData tp = getTranslator().getPartner();
        de.set(""+Utility.getActualUom(currOutboundReq.getStoreType(), currItem));
        de = (DataElement) segment.buildDE(4);  // 212 Unit Price
        de.set(""+currInvoiceItem.getCustContractPrice());
        de = (DataElement) segment.buildDE(5);  // 639 Basis of Unit Price Code
        de.set("");
        de = (DataElement) segment.buildDE(6);  // 235 Product/Service ID Qualifier
        if(tp.getSkuTypeCd().equals(RefCodeNames.SKU_TYPE_CD.CUSTOMER) && Utility.isSet(currItem.getCustItemSkuNum())){
          de.set("CB");
        }else{
          de.set("VP");
        }
        de = (DataElement) segment.buildDE(7);  // 234 Product/Service ID
        de.set(Utility.getActualSkuNumber(currOutboundReq.getStoreType(),currItem));

        // JCP: that's the end of this segment but now we need an IT1
        // segment to go along with it (which USPS doesn't use)
        buildDetailIT1PID(loop);
    }

    /** builds segment PID that is part of the DetailIT1
     *<br>Product/Item Description used
     *<br>To describe a product or process in coded or free-form format
     * @param inLoop loop containing this subsegment
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
        de.set("");
        de = (DataElement) segment.buildDE(3);  // 559 Agency Qualifier Code
        de.set("");
        de = (DataElement) segment.buildDE(4);  // 751 Product Description Code
        de.set("");
        de = (DataElement) segment.buildDE(5);  // 352 Description
        // Try for customer description but take generic description otherwise.
        // Truncate at 80 characters.
        String description = currItem.getCustItemShortDesc();
        if (!Utility.isSet(description)) {
            description = currInvoiceItem.getItemShortDesc();
        }
        description = Utility.removeSpecialCharachters(description);
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
        de.set("T");
        de = (DataElement) segment.buildDE(2);  // 206 Equipment Initial
        de.set("");
        de = (DataElement) segment.buildDE(3);  // 207 Equipment Number
        de.set("");
        de = (DataElement) segment.buildDE(4);  // 140 Standard Carrier Alpha Code
        // JCP doesn't read the SCAC
        de.set("");
        de = (DataElement) segment.buildDE(5);  // 387 Routing
        de.set("Own Truck");
    }

    /** builds segment CUR that is part of the Header (Currency)
     *<br>Reference Identification used
     *<br>To specify identifying information
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildHeaderCUR(Table inTable) throws OBOEException {
        Segment segment = inTable.createSegment("CUR");
        inTable.addSegment(segment);  DataElement de;
        de = (DataElement) segment.buildDE(1);  // 373 Date
        //Code identifying an organizational entity, a physical location, property or an individual
        de.set("SE");  //SE = seller BY = buyer
        de = (DataElement) segment.buildDE(2);  // 76 Invoice Number
        //hardcoded for US Dollars
        de.set("USD");
    };



    /** builds segment N1 that is part of the Header
     *<br>Name used
     *<br>To identify a party by type of organization, name, and code
     * @param inTable table containing this segment
     * @throws OBOEException - most likely segment not found
     */
    public void buildHeaderN1(Table inTable, String code)
      throws OBOEException
    {
      if (code.equals("ST")){
// YR OBOE          Segment segment = inTable.createSegment("N1");
          Loop loop = inTable.createLoop("N1");
          buildHeaderN1N3(loop, code);
          buildHeaderN1N4(loop, code);
//          inTable.addSegment(segment);
          inTable.addLoop(loop);
          Segment segment = loop.createSegment("N1");
          loop.addSegment(segment);
          DataElement de;
          de = (DataElement) segment.buildDE(1);  // 98 Entity Identifier Code
          de.set(code);
          de = (DataElement) segment.buildDE(2);  // 93 Name
          de.set(currInvoice.getShippingName());
      }
    }


    /** builds segment N3 that is part of the Header
     *<br>Name used
     *<br>To identify a party by type of organization, name, and code
     * @param loop loop containing this segment
     * @throws OBOEException - most likely segment not found
     */
    public void buildHeaderN1N3(Loop loop, String code)
      throws OBOEException
    {
      if (code.equals("ST")){
// YR OBOE          Segment segment = inSegment.createSegment("N3");
          Segment segment = loop.createSegment("N3");
          loop.addSegment(segment);
          DataElement de;
          de = (DataElement) segment.buildDE(1);  // 98 Entity Identifier Code
          if(Utility.isStreetAddress(currInvoice.getShippingAddress1())){
            de.set(currInvoice.getShippingAddress1());
          }else if(Utility.isStreetAddress(currInvoice.getShippingAddress2())){
              de.set(currInvoice.getShippingAddress2());
          }else if(Utility.isStreetAddress(currInvoice.getShippingAddress3())){
              de.set(currInvoice.getShippingAddress3());
          }else if(Utility.isStreetAddress(currInvoice.getShippingAddress4())){
              de.set(currInvoice.getShippingAddress4());
          }else{
              de.set(currInvoice.getShippingAddress1());
          }
      }
    }

    /** builds segment N4 that is part of the Header
     *<br>Name used
     *<br>To identify a party by type of organization, name, and code
     * @param loop loop containing this segment
     * @throws OBOEException - most likely segment not found
     */
    public void buildHeaderN1N4(Loop loop, String code)
      throws OBOEException
    {
      if (code.equals("ST")){
// YR OBOE          Segment segment = inSegment.createSegment("N4");
          Segment segment = loop.createSegment("N4");
          loop.addSegment(segment);
          DataElement de;
          de = (DataElement) segment.buildDE(1);  // 98 Entity Identifier Code
          de.set(currInvoice.getShippingCity());
          de = (DataElement) segment.buildDE(2);
          de.set(currInvoice.getShippingState());
          de = (DataElement) segment.buildDE(3);
          de.set(currInvoice.getShippingPostalCode());
          de = (DataElement) segment.buildDE(4);
          de.set(Utility.getCountryCodeFromCountry(currInvoice.getShippingCountry()));
      }
    }



}

