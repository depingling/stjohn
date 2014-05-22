package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;
import com.americancoders.edi.x12.*;
import java.util.*;
import com.cleanwise.service.api.value.*;
import java.math.BigDecimal;

/**
 *<br>class 850 Purchase Order
 *<br>
 *This class is super class for extracting 850 transaction. It contained the class
 *object and method that could be shared between 850 parser for different customer
 *
 *This class has all of the reasponsibilties after the document has been validated
 *to be an 850 Purchase order.  It is the Inbound processor, it needs to extract
 *all of the relevant information, and then verify that it is in fact the correct
 *information.  I.e. if we get an 850 inbound for a widget that is $.50 according
 *to the PO, and $10.00 according to the contract for the account this is a problem.
 *
 *Also we need to determine all of the various attributes that the web application
 *knows about but the edi file has no knowledge of (for example the fulfilment partner
 *for a given item for the contract for the ship to.)
 *
 *This file originated as a generated class from the OBOE package, but there is no
 *mechanism for getting information out of the individual methods without directly
 *modifing the code, as they do not return any value, and in fact break if there
 *are missing data elements that we do not necessarily requiere.
 *
 *TODO:
 *Process the CTT (Transaction totals) and verify that the sent line items matches the number
 *that the edi document believes that it has sent.
 *@author Brook Stevens
 */
abstract class Inbound850Super extends InboundSuper {
    protected static Integer ediMethodFlag = new Integer(2);
    //this represents the actual order that we will be filling, validating, and logging
    protected OrderRequestDataVector mOrders = new OrderRequestDataVector();
    protected OrderRequestData currOrder;
    protected int itemLineNo;
    protected int itemQty;
    protected String itemUOM;
    protected BigDecimal itemUnitPrice;
    protected String itemSKU;
    protected String itemDesc;
    protected String itemPack;
    protected boolean shipDateRequired = false;

    protected boolean validateShipAddress = false;

    /*
      BEG<sep>BEG01<sep>BEG02<sep>BEG03<sep>BEG04<sep>BEG05
      JCPenney BEG example:
      BEG<sep>00<sep>SA<sep>17588601<sep>00000000<sep>20050106
      BEG03=17588601 (customer PO)
      BEG04=00000000 (JCPenney Original Reference Number)
    */
    /** extract data from segment BEG that is part of the Header
     *<br>Beginning Segment for Purchase Order used
     *<br>To indicate the beginning of the Purchase Order Transaction Set and transmit identifying numbers and dates
     *@param inSegment table containing this segment
     *@throws OBOEException - most likely segment not found
     */
    public void extractHeaderBEG(Table inTable)
    throws OBOEException {
        Segment segment;
        try { segment = inTable.getSegment("BEG");}
        catch (OBOEException oe) { return; }
        DataElement de;
        de = segment.getDataElement(1);  // 353 Transaction Set Purpose Code
        if (de == null || de.get().equals("")){
            errorMsgs.add("BEG01 is required");
            setValid(false);
        }

        de = segment.getDataElement(2);  // 92 Purchase Order Type Code
        if (de == null || !(de.get().equals("SA"))){
            errorMsgs.add("BEG02 is required and must equal SA");
            setValid(false);
        }

        de = segment.getDataElement(3);  // 324 Purchase Order Number
        if (de == null || de.get().equals("")){
            errorMsgs.add("BEG03 is required");
            setValid(false);
        } else {
            for (int i=0;i<mOrders.size();i++){
                ((OrderRequestData) mOrders.get(i)).setCustomerPoNumber(de.get());
            }
        }

        de = segment.getDataElement(4);  // 328 Release Number
        if (de == null || de.get().equals("")){
	    // Not required.
        } else {
            for (int i=0;i<mOrders.size();i++){
                ((OrderRequestData) mOrders.get(i)).setCustomerReleaseNumber(de.get());
            }
        }


        de = segment.getDataElement(5);  // 373 Date
        if (de != null && !de.get().equals("")){
            for (int i=0;i<mOrders.size();i++){
                ((OrderRequestData) mOrders.get(i)).setCustomerOrderDate(de.get());
            }
        }

        //this is an optional field
        de = segment.getDataElement(6);  // 367 Contract Number
        if (de != null && !de.get().equals("")){
            for (int i=0;i<mOrders.size();i++){
                ((OrderRequestData) mOrders.get(i)).setOrderRefNumber(de.get());
            }
        }
    }

    /** extract data from segment PER that is part of the Header
     *To do, where does this map in our system?  Currently this would be in
     *the user, but this is implied on login, and not stored dynamically...
     *maybe create a new user object, and populate that...but then what do
     *we do with it?  Do we even care about this at all?
     *
     *For now all I am doing is validating that the manditory fields are there.
     *<br>Administrative Communications Contact used
     *<br>To identify a person or office to whom administrative communications should be directed
     *param inSegment table containing this segment
     *@throws OBOEException - most likely segment not found
     */
    public void extractHeaderPER(Table inTable)
    throws OBOEException {
        Segment segment;
        try {
            int numberOfSegmentsInVector = inTable.getSegmentCount("PER");
            for (int i = 0; i <  numberOfSegmentsInVector; i++) {
                segment = inTable.getSegment("PER", i);
                DataElement de;
                de = segment.getDataElement(1);       // 366 Contact Function Code
                if (de == null || de.get().equals("")){
                    setValid(false);
                    errorMsgs.add("PER01 is mandatory");
                } else {
                    de.get();
                }
                de = segment.getDataElement(2);       // 93 Name
                String contactName;
                if (de == null || de.get().equals("")){
                    //this.valid = false;
                    //errorMsgs.add("PER02 is mandatory (order contact name)");
                    contactName = "";
                } else {
                    contactName = de.get();
                }
                for (int k=0;k<mOrders.size();k++){
                    ((OrderRequestData) mOrders.get(k)).setOrderContactName(contactName);
                }
                //this section could be slightly more dynamic, as to base it's descion on th
                //name qualifier completly, and be postion independant...special note is
                //that doing a segment.getDataElement(xxx), where xxx does not exsist will
                //result in a null pointer exception.
                //-2 for the fact we are incrementing by 2
                for (int j=2;j<segment.getDataElementSize() - 2;j=j+2){

                    de = segment.getDataElement(1 + j);
                    if (de != null){
                        if(de.get().equals("TE")){
                            de = segment.getDataElement(1 + j + 1);       // 364 Communication Number
                            if (de != null){
                                for (int k=0;k<mOrders.size();k++){
                                    ((OrderRequestData) mOrders.get(k)).setOrderTelephoneNumber(de.get());
                                }
                            }
                        }
                        if(de.get().equals("FX")){
                            de = segment.getDataElement(1 + j + 1);       // 364 Communication Number
                            if (de != null){
                                for (int k=0;k<mOrders.size();k++){
                                    ((OrderRequestData) mOrders.get(k)).setOrderFaxNumber(de.get());
                                }
                            }
                        }
                        if(de.get().equals("EM")){
                            de = segment.getDataElement(1 + j + 1);       // 364 Communication Number
                            if (de != null){
                                for (int k=0;k<mOrders.size();k++){
                                    ((OrderRequestData) mOrders.get(k)).setOrderEmail(de.get());
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (OBOEException oe) { return; }
    }

/** extract data from segment REF that is part of the Header
*<br>Reference Identification used
*<br>To specify identifying information
*param inTable table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractHeaderREF(Table inTable)
  throws OBOEException
{
  Segment segment;
  boolean expenseNumberFound = false;

  try {
    int numberOfSegmentsInVector = inTable.getSegmentCount("REF");
    for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
       segment = inTable.getSegment("REF", i);
       DataElement de;
       de = segment.getDataElement(1);       // 128 Reference Identification Qualifier
       if (de != null && de.get().equals("AE")) {
          de = segment.getDataElement(2);       // 127 Reference Identification
          if (de != null && !de.get().equals("")){
            currOrder.setOrderRefNumber(de.get());
          }
          break;
       }
    }
  }
  catch (OBOEException oe) {
    errorMsgs.add("Could not extract loop REF (this is mandatory): " + oe.toString());
    setValid(false);
  }
}

/** extract data from segment CSH that is part of the Header
*<br>Sales Requirements used
*<br>To specify general conditions or requirements of the sale
*param inTable table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractHeaderCSH(Table inTable)
  throws OBOEException
{
  Segment segment;
  try {
    int numberOfSegmentsInVector = inTable.getSegmentCount("CSH");
    for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
      segment = inTable.getSegment("CSH", i);
      DataElement de;
      de = segment.getDataElement(1);       // 563 Sales Requirement Code
      if (de != null && de.get().equals("P4")){
        shipDateRequired = true;
        return;
      }
    }
  }
  catch (OBOEException oe) {
    errorMsgs.add("Caught Oboe Exception: " + oe.toString());
    setValid(false);
    oe.printStackTrace();
  }
}

/** extract data from segment DTM that is part of the Header
*<br>Date/Time Reference used
*<br>To specify pertinent dates and times
*param inTable table containing this segment
*@throws OBOEException - most likely segment not found
*/
public void extractHeaderDTM(Table inTable)
  throws OBOEException
{
  Segment segment;
  try {
    int numberOfSegmentsInVector = inTable.getSegmentCount("DTM");
    for (int i = 0; i <  numberOfSegmentsInVector; i++)
    {
      segment = inTable.getSegment("DTM", i);
      DataElement de;
      de = segment.getDataElement(1);       // 374 Date/Time Qualifier
      if (de != null && de.get().equals("010")) {
        if(shipDateRequired){
          de = segment.getDataElement(2);       // 373 Date
          if (de != null && !de.get().equals("")){
            String dateString = de.get();

            if (dateString.length() == 6)
              dateString = "20" + dateString;

            currOrder.setOrderRequestedShipDate(stringToDate(dateString, "yyyyMMdd", null));
  System.out.println("Inbound850Super IIIIIIIIIIIIIIIIIIIIIIIIIIII shipDate: "+currOrder.getOrderRequestedShipDate());
          }
          break;
        } else { // If earlist ship date if it was not requested, analyse it aniway but ignore exception
          try {
            de = segment.getDataElement(2);       // 373 Date
            if (de != null && !de.get().equals("")){
              String dateString = de.get();

              if (dateString.length() == 6) dateString = "20" + dateString;
              Date estd = stringToDate(dateString, "yyyyMMdd", null);
              currOrder.setOrderRequestedShipDate(estd);
    System.out.println("Inbound850Super IIIIIIIIIIIIIIIIIIIIIIIIIIII shipDate: "+currOrder.getOrderRequestedShipDate());
              break;
            }
          } catch (Exception exc){}
        }
      }
    }
  }
  catch (OBOEException oe) { return; }
}



     /** extract data from segment N9 that is part of the Header
     *<br>Identification Reference used
     *<br>To transmit identifying information as specified by the Reference Identification Qualifier
     * @param inTable table containing this segment
     *@throws OBOEException - most likely segment not found
     */
	public void extractHeaderN9(Table inTable)
	throws OBOEException
	{
		Loop loop;
		try {
			int numberInVector = inTable.getCount("N9");
			for (int i = 0; i <  numberInVector; i++)
			{
				loop = inTable.getLoop("N9", i);
				if (loop != null){
					extractHeaderN9MTX(loop);
				}
			}
		}
		catch (OBOEException oe) {
			oe.printStackTrace();
			return;
		}
	}

    /**
     *extract data from segment MTX in the N9 loop
     *<br>Message Text used
     *<br>To provide a free-form format that allows the transmission of text information
     * @param inTable table containing this segment
     *@throws OBOEException - most likely segment not found
     */
    //subclass if you need it
    public void extractHeaderN9MTX (Loop inLoop)  throws OBOEException {
        return;
    }

    /** extract data from segment PO1 that is part of the Detail
     *This represents the actual line items of an order.  We will create the item,
     *and then add it to the order.  The only hitch is that there can be multiple
     *"orders" per PO.  Because we assign shipping addresses, billing information
     *and various other things on the order level we are going to have to create
     *multiple orders, and attach the line items to them.  We also need to make
     *sure we don't generate a bunch of extra orders, which could create a great
     *amount of work for us during the order processing stage, and customer service
     *stage of the orders lifetime.
     *<br>Baseline Item Data used
     *<br>To specify basic and most frequently used line item data
     *param inSegment table containing this segment
     *@throws OBOEException - most likely segment not found
     */
    public void extractDetailPO1(Table inTable)
    throws OBOEException {
        Loop loop;
        Segment segment;
        try {
            System.out.println("determining # items to extract");
            int numberOfLoopsInVector = inTable.getLoopCount("PO1");

            System.out.println("extracting items. total: " + numberOfLoopsInVector);
            for (int i = 0; i <  numberOfLoopsInVector; i++) {
                loop = inTable.getLoop("PO1", i);

                /*
                 *This is where things get complicated, as here we need to determine
                 *if this is an exsiting order in the order vector or if it is a
                 *new order that we need to add to the vector.
                 */
                currOrder = extractN1(loop);

                segment = loop.getSegment("PO1");
                DataElement de;
                de = segment.getDataElement(1);       // 350 Assigned Identification
                if (de == null || de.get().equals("")){
                    errorMsgs.add("PO101 is required");
                    setValid(false);
                } else {
                    itemLineNo = Integer.parseInt(de.get());
                }
                de = segment.getDataElement(2);       // 330 Quantity Ordered
                if (de == null || de.get().equals("")){
                    errorMsgs.add("PO102 is required");
                    setValid(false);
                } else {
                    itemQty = (new Integer(de.get())).intValue();
                }
                de = segment.getDataElement(3);       // 355 Unit or Basis for Measurement Code
                if (de == null || de.get().equals("")){
                    errorMsgs.add("PO103 is required");
                    setValid(false);
                } else {
                    itemUOM = de.get();
                }
                de = segment.getDataElement(4);       // 212 Unit Price
                if (de == null || de.get().equals("")){
                    errorMsgs.add("PO104 is required");
                    setValid(false);
                } else {
                    itemUnitPrice = new BigDecimal(de.get());
                }

                de = segment.getDataElement(6);       // 235 Product/Service ID Qualifier
                if (de == null || de.get().equals("")){
                    errorMsgs.add("PO106 is required");
                    setValid(false);
                } else {
                    //not sure what to do with this value.
                }
                de.get();
                de = segment.getDataElement(7);       // 234 Product/Service ID
                if (de == null || de.get().equals("")){
                    errorMsgs.add("PO107 is required");
                    setValid(false);
                } else {
                    itemSKU = de.get();
                }
                DataElement de1 = segment.getDataElement(8);       // 235 Product/Service ID Qualifier
                DataElement de2 = segment.getDataElement(9);       // 234 Product/Service ID
                if (de1 != null && de2 == null){
                    errorMsgs.add("PO1 Segment, Second 235/234 paring, 234 null, 235 exsits");
                    setValid(false);
                } else if (de1 == null && de2 != null){
                    errorMsgs.add("PO1 Segment, Second 235/234 paring, 234 exsits, 235 null");
                    setValid(false);
                } else {
                    //do something with this data
                }

                currOrder.addItemEntry(itemLineNo, itemSKU, itemQty,
                      itemUnitPrice.doubleValue(), itemUOM, itemDesc, itemPack);
            }
        }
        catch (OBOEException oe) {
           errorMsgs.add("Caught Oboe Exception: " + oe.toString());
            setValid(false);
            oe.printStackTrace();
        }
        catch (NumberFormatException e) {
            errorMsgs.add("A value that is supposed to be a number was not a valid number");
            setValid(false);
            e.printStackTrace();
        }
    }
    /** extract data from segment N1 that is part of the DetailPO1
     *For the case of the USPS this contains shipping information that can
     *wind up being more than one order in Cleanwises' eyes.  That is the USPS
     *will place an order for 3 different locations, we then need to create 3
     *seperate orders out of that one order in order for our system to deal with
     *this.
     *Ideally this is a temporary solution that will be changed.  This is a
     *restriction of the Dynamo system, not Lawson.  Lawson has no problems
     *handleing this, it will, however, issue multiple purchase orders out to the
     *distributor, one for each address.
     *<br>Name used
     *<br>To identify a party by type of organization, name, and code
     *param inSegment segment containing this subsegment
     *@throws OBOEException - most likely segment not found
     */
    public OrderRequestData extractN1(Loop inLoop)  throws OBOEException {
        Loop loop;
        Segment segment;
        boolean freeFormatAddr = false;
        try {
            int numberOfLoopsInVector = inLoop.getLoopCount("N1");
            for (int i = 0; i <  numberOfLoopsInVector; i++) {
                loop = inLoop.getLoop("N1", i);
                segment = loop.getSegment("N1");
                DataElement de;
                de = segment.getDataElement(4);       // 67 Identification Code
                if (de == null || de.get().equals("")){
                    errorMsgs.add("N104 is mandatory");
                    setValid(false);
                    return null;
                }

                currOrder = getCurrOrder(de.get());
                currOrder.setSiteName(de.get());

                de = segment.getDataElement(1);       // 98 Entity Identifier Code
                if (!(de.get().equals("ST"))){
                    errorMsgs.add("N101 is not ST (ship to)");
                    setValid(false);
                }
                de = segment.getDataElement(2);       // 93 Name
                if (de == null || de.get().equals("")){
                    errorMsgs.add("N102 is mandatory");
                    setValid(false);
                } else {
                    // Ship name
                    currOrder.getOrderShipAddress().setAddress1(de.get());
                }

                de = segment.getDataElement(6);       // 98 Entity Identifier Code
                if (de != null){
                  if (de.get().equals("Y ")){
                    currOrder.setFreeFormatAddress(true);
                  }
                }
                extractN1N2(loop);
                extractN1N3(loop);
                extractN1N4(loop);
            }
        }
        catch (OBOEException oe) {
            errorMsgs.add("Could not extract loop N1 (this is mandatory): " + oe);
            setValid(false);
        }

        return currOrder;
    }

    /** extract data from segment N2 that is part of the DetailPO1N1
     *<br>Additional Name Information used
     *<br>To specify additional names or those longer than 35 characters in length
     *param inSegment segment containing this subsegment
     *@throws OBOEException - most likely segment not found
     */
    public void extractN1N2(Loop inLoop)  throws OBOEException {
        Segment segment;
        try {
            int numberOfSegmentsInVector = inLoop.getSegmentCount("N2");
            for (int i = 0; i <  numberOfSegmentsInVector; i++) {
                segment = inLoop.getSegment("N2", i);
                DataElement de;
                de = segment.getDataElement(1);       // 93 Name
                if (de == null || de.get().equals("")){
                    setValid(false);
                    errorMsgs.add("N201 in loop N1 must be supplied for loop index: " + i);
                } else {
                    if (i==0){
                        currOrder.getOrderShipAddress().setAddress2(de.get());
                    } else if (i==1){
                        currOrder.getOrderShipAddress().setAddress3(de.get());
                    }
                }
            }
        }
        catch (OBOEException oe) {
            setValid(false);
            errorMsgs.add("problem extracting N2 in loop N1, this is mandatory. " + oe);
        }
    }

    /** extract data from segment N3 that is part of the DetailPO1N1
     *<br>Address Information used
     *<br>To specify the location of the named party
     *param inSegment segment containing this subsegment
     *@throws OBOEException - most likely segment not found
     */
    public void extractN1N3(Loop inLoop)  throws OBOEException {
        Segment segment;
        try {
            int numberOfSegmentsInVector = inLoop.getSegmentCount("N3");
            for (int i = 0; i <  numberOfSegmentsInVector; i++) {
                segment = inLoop.getSegment("N3", i);
                DataElement de;
                de = segment.getDataElement(1);       // 166 Address Information
                if (de == null || de.get().equals("")){
                    setValid(false);
                    errorMsgs.add("N301 in loop N1 must be supplied");
                } else {
                    currOrder.getOrderShipAddress().setAddress4(de.get());
                }
            }
        }
        catch (OBOEException oe) {
            setValid(false);
            errorMsgs.add("problem extracting N3 in loop N1, this is mandatory. " + oe);
        }
    }

    /** extract data from segment N4 that is part of the DetailPO1N1
     *<br>Geographic Location used
     *<br>To specify the geographic place of the named party
     *param inSegment segment containing this subsegment
     *@throws OBOEException - most likely segment not found
     */
    public void extractN1N4(Loop inLoop)  throws OBOEException {
        Segment segment;

        try {
            segment = inLoop.getSegment("N4");
            DataElement de;
            de = segment.getDataElement(1);  // 19 City Name
            if (de == null || de.get().equals("")){
                errorMsgs.add("segment PO1 loop N1 segment N401 de 19 is mandatory");
                setValid(false);
            } else {
                currOrder.getOrderShipAddress().setCity(de.get());
            }
            de = segment.getDataElement(2);  // 156 State or Province Code
            if (de == null || de.get().equals("")){
                errorMsgs.add("N402 is mandatory");
                setValid(false);
            } else {
                currOrder.getOrderShipAddress().setStateProvinceCd(de.get());
            }
            de = segment.getDataElement(3);  // 116 Postal Code
            if (de == null || de.get().equals("")){
                errorMsgs.add("N403 is mandatory");
                setValid(false);
            } else {
                currOrder.getOrderShipAddress().setPostalCode(de.get());
            }
            de = segment.getDataElement(4);  // 26 Country Code (not mandatory)
            if (de != null){
                currOrder.getOrderShipAddress().setCountryCd(de.get());
            }
        }
        catch (OBOEException oe) {
            setValid(false);
            errorMsgs.add("problem extracting N4 in loop N1, this is mandatory. " + oe);
        }
    }

    //*******************above is adapted from the auto generated code******************

    public OrderRequestData getCurrOrder(String siteName)
    {
      OrderRequestData orderD = null;
      boolean orderFound = false;
      Iterator orderI = mOrders.iterator();
      while (orderI.hasNext()) {
        orderD = (OrderRequestData)orderI.next();
        String name = orderD.getSiteName();
        if (name != null && name.equals(siteName)) {
          orderFound = true;
          break;
        }
      }
      if (orderFound == false){
        orderD = OrderRequestData.createValue();
        mOrders.add(orderD);
      }
      return orderD;
    }
  public void processTransaction()
  {
    for (int i = 0; i < mOrders.size(); i++){
      OrderRequestData orderD = (OrderRequestData)mOrders.get(i);
      int[] accountIds = getTranslator().getBusEntityIds();
      if(accountIds.length==1) {
        orderD.setAccountId(accountIds[0]);
      }
      orderD.setSkuTypeCd(getTranslator().getPartner().getSkuTypeCd());
      orderD.setTradingPartnerId(getTranslator().getPartner().getTradingPartnerId());
      orderD.setIncomingProfileId(getTranslator().getProfile().getTradingProfileId());
      mHandler.transactionD.setKeyString("CustomerPoNumber: " + orderD.getCustomerPoNumber());
    }

    mHandler.appendIntegrationRequests(mOrders);
  }
  //************************Constructor******************************
  public Inbound850Super()
  {
  }
}

