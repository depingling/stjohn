package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import java.math.BigDecimal;

/**
 *<br>class 850 Purchase Order
 *<br>
 *This class is used to extract inbound 850 transaction from JC Penney.
 *JC Penney will send N1 segment on header level
 * instead of item level like USPS
 *Customer item information(e.g. customer sku, customer product description...)
 *on PO1 loop will be send which is different from most customer that send
 *cleanwise item information. Duplcated order should be prevent for JC Penny order
 */
public class Inbound850_JCP extends Inbound850Super {

    /** extract data from segment PO1 that is part of the Detail
     *This represents the actual line items of an order.  We will create the item,
     *and then add it to the order.  The order object is created during N1 segment
     *parsing for SHIP TO.
     *<br>Baseline Item Data used
     *<br>To specify basic and most frequently used line item data
     *param inSegment table containing this segment
     *@throws OBOEException - most likely segment not found
     */
	public void extractDetailPO1(Table inTable)
	throws OBOEException {
		Loop loop;
		try {
			int numberInVector = inTable.getCount("PO1");
			System.out.println("extracting items. total: "
					+ numberInVector);
			for (int i = 0; i <  numberInVector; i++)
			{
				loop = inTable.getLoop("PO1", i);
				if (loop == null) return;
				Segment segment = loop.getSegment("PO1");
				DataElement de;
				System.out.println("\t\textracting i=" + i + " of " + numberInVector);

				de = segment.getDataElement(1);       // 350 Assigned Identification
				if (de == null || de.get().equals("")){
					itemLineNo = i + 1;
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
					// JCP doesn't necessarily send prices as of 3/7/2002
					// so don't require it.  Set it to zero and see what happens.
					itemUnitPrice = new BigDecimal("0.0");

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

				System.out.println("extractDetailPO1PID");
				extractDetailPO1PID(loop);
				System.out.println("extractDetailPO1PO4");
				extractDetailPO1PO4(loop);

				System.out.println("\naddItemEntry,"
						+ " itemLineNo=" + itemLineNo
						+ " itemSKU=" + itemSKU
						+ " itemQty=" + itemQty
						+ " itemUnitPrice=" + itemUnitPrice
						+ " itemUOM=" + itemUOM
						+ " itemDesc=" + itemDesc
						+ " itemPack=" + itemPack );

				currOrder.addItemEntry(itemLineNo,
						itemSKU, itemQty,
						itemUnitPrice.doubleValue(),
						itemUOM, itemDesc, itemPack);
				System.out.println("\t\tDONE addItemEntry");
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
		if(errorMsgs.size() > 0){
			System.out.println("\n errors now at: " + errorMsgs.size());
		}else{
			System.out.println("Nothing wrong");
		}
	}

    /** extract data from segment PID that is part of the DetailPO1
*<br>Product/Item Description used
*<br>To describe a product or process in coded or free-form format
*param inSegment segment containing this subsegment
*@throws OBOEException - most likely segment not found
*/
	public void extractDetailPO1PID(Loop inLoop)  throws OBOEException
	{
		Loop loop;
		try {
			System.out.println("\n extractDetailPO1PID "
					+ " loop=" + inLoop.getID());
			int numberInVector = inLoop.getCount("PID");
			for (int i = 0; i <  numberInVector; i++)
			{
				loop = inLoop.getLoop("PID", i);
				if (loop == null) return;
				System.out.println("\n extractDetailPO1PID, i=" + i
						+ " loop=" + inLoop.getID());

				Segment segment = loop.getSegment("PID");

				DataElement de;
				de = segment.getDataElement(2); // 750 Product/Process Characteristic Code
				System.out.println("\n   PID[" + i +"] 1=" + de.get() );

				// According to the JCP spec, these are the allowed
				// values for this PID segment.  durval
				if (de == null || (   !de.get().equals("08")
						&& !de.get().equals("75")
						&& !de.get().equals("91") ) ) {
					System.out.println(" PID, segment 2 (index1) "
							+ " description=" + de.getDescription()
							+ " value = "
							+ de.get() + " this is an unexpected value " );
					continue;
				}

				de = segment.getDataElement(5);       // 352 Description
				System.out.println("\n   PID[" + i +"] 4=" + de.get());
				if (de != null && !de.get().equals("")){
					if (i == 0)
						itemDesc = de.get();
					else
						itemDesc = itemDesc + ". " +de.get();
				}
			}
			if (itemDesc.length() > 255)
				itemDesc = itemDesc.substring(0, 255);
		}
		catch (OBOEException oe) {
			oe.printStackTrace();
			return;
		}
	}

/** extract data from segment PO4 that is part of the DetailPO1
*<br>Item Physical Details used
*<br>To specify the physical qualities, packaging, weights, and dimensions relating to the item
*param inSegment segment containing this subsegment
*@throws OBOEException - most likely segment not found
*/
	public void extractDetailPO1PO4(Loop inLoop)  throws OBOEException
	{
		Segment segment;
		try {
			int numberOfSegmentsInVector = inLoop.getCount("PO4");
			for (int i = 0; i <  numberOfSegmentsInVector; i++)
			{
				segment = inLoop.getSegment("PO4", i);
				DataElement de;
				de = segment.getDataElement(1);       // 356 Pack
				// for some reason, it has to check if de.get() is null here
				if (de != null && de.get() != null && !de.get().equals("")){
					itemPack = de.get();
				}
				break;
			}
		}
		catch (OBOEException oe) { return; }
	}

    /** extract data from segment N1. Depending the Entity Identifier Code, It
     *could contain information for Ship to, Buying party or Bill to. A order is
     *created for each Ship to received.
     *
     *<br>Name used
     *<br>To identify a party by type of organization, name, and code
     *param inSegment segment containing this subsegment
     *@throws OBOEException - most likely segment not found
     */
public void extractHeaderN1(Table inTable)  throws OBOEException {
	//this will store the temporary/permenant order we are dealing with.
	//if it turns out that this is an exsiting order we will return that,
	//otherwise we will add this order to the Vector of orders and return
	//this order.

	Loop loop;
	try {
		int numberInVector = inTable.getCount("N1");
		for (int i = 0; i <  numberInVector; i++)
		{
			loop = inTable.getLoop("N1", i);
			if (loop == null) return;
			Segment segment = loop.getSegment("N1");

			DataElement de;
			String code;
			String name;

			de = segment.getDataElement(1);       // 98 Entity Identifier Code
			if (de != null && !de.get().equals(""))
				code = de.get();
			else
				code = "";
			de = segment.getDataElement(2);       // 93 Name
			if (de != null && !de.get().equals(""))
				name = de.get();
			else
				name = null;
			if ((code.equals("ST"))){
				OrderAddressData addr = currOrder.getOrderShipAddress();
				addr.setAddress1(de.get());
				de = segment.getDataElement(4);       // 67 Identification Code
				if (de != null && !de.get().equals(""))
					currOrder.setSiteName(de.get());

				extractHeaderN1N3(code, loop,addr);
				extractHeaderN1N4(code, loop,addr);
			}
			else if ((code.equals("BY") || code.equals("BT"))){
				de = segment.getDataElement(4);       // 67 Identification Code
				String billToId=null;
				if (de != null && !de.get().equals("")){
					billToId = de.get();
				}
				if(billToId!= null && !Utility.isSet(currOrder.getSiteName())){
					currOrder.setSiteName(billToId);
				}
				if(billToId != null && !billToId.equals(currOrder.getSiteName())){
					currOrder.setCustomerBillingUnit(billToId);
				}
			}else if (code.equals("Z7")){     //Mark For Party
				//not sure what we should do here
				de = segment.getDataElement(4);       // 67 Identification Code
				if (de != null && !de.get().equals("")){
					currOrder.addOrderNote("Mark for party set:" + de.get());
				}
			}
		}

		if (currOrder.getOrderShipAddress().getAddress1() == null){
			errorMsgs.add("Segment N1 for ship to missing");
			setValid(false);
		}
		if (currOrder.getSiteName() == null){
			errorMsgs.add("Segment N1 for store number missing");
			setValid(false);
		}
	}
	catch (OBOEException oe) {
		errorMsgs.add("could not extract loop N1 (this is mandatory): " + oe);
		setValid(false);
	}
}

    /** extract data from segment N3 that is part of the extractHeaderN1
     *<br>Address Information used
     *<br>To specify the location of the named party
     *param inSegment segment containing this subsegment
     *@throws OBOEException - most likely segment not found
     */
    public void extractHeaderN1N3(String id, Loop inLoop, OrderAddressData addr)  throws OBOEException {
        Segment segment;
        try {
        	int numberOfSegmentsInVector = inLoop.getCount("N3");
            for (int i = 0; i <  numberOfSegmentsInVector; i++) {
                segment = inLoop.getSegment("N3", i);
                DataElement de;
                String addr1 = segment.getDataElement(1) == null ?
                        null : segment.getDataElement(1).get();       // 166 Address Information
                String addr2 = segment.getDataElement(2) == null ?
                        null : segment.getDataElement(2).get();       // 166 Address Information
                addr.setAddress3(addr2);
                addr.setAddress4(addr1);
            }
        }
        catch (OBOEException oe) {
            if (id.equals("ST")){
                errorMsgs.add("Segment N3 missing for Ship to");
                setValid(false);
            }
            else if (id.equals("BT")){
                errorMsgs.add("Segment N3 missing for Bill to");
                setValid(false);
            }
            else if (id.equals("BY")){
                errorMsgs.add("Segment N3 missing for Bill to");
                setValid(false);
            }
            return;
       }
    }


    /** extract data from segment N4 that is part of the extractHeaderN1
     *<br>Geographic Location used
     *<br>To specify the geographic place of the named party
     *param inSegment segment containing this subsegment
     *@throws OBOEException - most likely segment not found
     */
    public void extractHeaderN1N4(String id, Loop inLoop, OrderAddressData addr)  throws OBOEException {
    	Segment segment;
        DataElement de;
        try {
          int numberOfSegmentsInVector = inLoop.getCount("N4");
          for (int i = 0; i <  numberOfSegmentsInVector; i++)
          {
            segment = inLoop.getSegment("N4", i);

                de = segment.getDataElement(1);  // 19 City Name
                if (de == null || de.get().equals("")){
                    errorMsgs.add("Missing City Name in N4 segment");
                    setValid(false);
                }
                else
                    addr.setCity(de.get());
                de = segment.getDataElement(2);  // 156 State or Province Code
                if (de == null || de.get().equals("")){
                    errorMsgs.add("Missing State or Province Code in N4 segment");
                    setValid(false);
                }
                else
                    addr.setStateProvinceCd(de.get());
                de = segment.getDataElement(3);  // 116 Postal Code
                if (de == null || de.get().equals("")){
                    errorMsgs.add("Missing Postal Code in N4 segment");
                    setValid(false);
                }
                else
                    addr.setPostalCode(de.get());
          }
        }
        catch (OBOEException oe){
            if (id.equals("ST")){
                errorMsgs.add("Segment N4 missing for Ship to");
                setValid(false);
            }
            else if (id.equals("BT")){
                errorMsgs.add("Segment N4 missing for Bill to");
                setValid(false);
            }
            else if (id.equals("BY")){
                errorMsgs.add("Segment N4 missing for Bill to");
                setValid(false);
            }
            return;
        }
    }


    /**
     *Extracts the value of the SAC Segment which is used by JCPenney to send special instructions on a po
     * This is currently not in the XML file, so this should error out the file currently.  None of the test
     * files had this segment specified so this should not be an issue.  According to the docs this is not
     * sent to vendors such as ourselves.
     */
    public void extractHeaderSAC(Table inTable) throws OBOEException {
    	try {
    		int numberInVector = inTable.getCount("SAC");
    		for (int i = 0; i <  numberInVector; i++)
    		{
    			Loop loop = inTable.getLoop("SAC", i);
    			if (loop == null) return;
    			Segment segment = loop.getSegment("SAC");
    			DataElement de;
    			de = segment.getDataElement(5);
    			String value = de.get(); // 610 Amount
    			if(Utility.isSet(value)){
    				currOrder.getOrderNotes().add("Header SAC segment sent value: " + value);
    				currOrder.setOrderStatusCdOveride(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
    			}
    		}
    	}
    	catch (OBOEException oe) {
    		//oe.printStackTrace();
    		//optional segment
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
    public void extractHeaderN9MTX (Loop inLoop)  throws OBOEException {
        try{
        	  int numberOfSegmentsInVector = inLoop.getCount("MTX");
        	  for (int i = 0; i <  numberOfSegmentsInVector; i++)
        	  {
        		 Segment segment = inLoop.getSegment("MTX", i);
                 if(segment != null){
                    DataElement de;
                    de = segment.getDataElement(2);
                    if(de != null){
                        String comments = de.get();
                        //sometimes they send over more than one line.  We want to pretty much ignore the first
                        //line as it always states the supplier agreement:
                        //"GOODS/SERVICES PROVIDED PER EDI ORDERS SUBJECT TO THE TRADING PARTNER AGREEMENT BETWEEN THE PARTIES."
                        if(currOrder.getCustomerComments() != null){
                        	comments = currOrder.getCustomerComments() + comments;
                        }
                        String lowerComments = comments.toLowerCase();
                        String parseText = "CW Order Num ".toLowerCase();
                        int startIdx = lowerComments.indexOf(parseText);
                        if(startIdx >= 0){
                        	startIdx = startIdx + parseText.length();
                        	int endIdx = startIdx;
                        	char[] commentsChr = comments.toCharArray();
                        	for(int j=startIdx,len=commentsChr.length;j<len;j++){
                        		char c = commentsChr[j];
                        		if(Character.isDigit(c)){
                        			endIdx++;
                        		}else{
                        			break;
                        		}
                        	}
                        	String parsedText = comments.substring(startIdx,endIdx);
                        	currOrder.setPunchOutOrderOrigOrderNum(parsedText);
                        }
                        currOrder.setCustomerComments(comments);
                    }
                }
        	  }
        }catch (OBOEException oe) {
          oe.printStackTrace();
          return;
        }
    }

    private String jcpMapORNToDept(String pOrigRefNum) {

	String defdept = "Stock Handling";
	if ( pOrigRefNum == null || pOrigRefNum.length() == 0 ) {
	    return defdept;
	}

	// These are the ORN numbers we have experienced.
	// Note that the data that we receive in the EDI
	// documents have a tralling 0 which JCP did not mention.
	// That is why the code uses the startsWith call to
	// check on the value.

	if (pOrigRefNum.startsWith("6660000")) {
	    return "Visual Work Room";
	}
	if (pOrigRefNum.startsWith("2245000")) {
	    return "Auto Ship";
	}

        // 8884000 - Stock Handling
	// The default department.
	return defdept;
    }

    private void jcpORNMapping() {
	for ( int i = 0; i < mOrders.size(); i++) {
	    OrderRequestData ord = (OrderRequestData) mOrders.get(i);
	    if (ord.getCustomerReleaseNumber() != null &&
		ord.getCustomerReleaseNumber().length() > 0 ) {
		// Prepend the customer PO with EPRO
		// to identify the customer PO.
		// Request from Robert M on 3/30/2005, durval
		// Append the ORN and department name to the
		// customer PO number;
		String tponum = "";
		if ( null != ord.getCustomerPoNumber() ) {
		    tponum = "EPRO" + ord.getCustomerPoNumber();
		    PropertyData prop = PropertyData.createValue();
            prop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.CUSTOMER_PO_NUM);
            prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.CUSTOMER_PO_NUM);
            prop.setValue(ord.getCustomerPoNumber());
            if (ord.getProperties() == null) {
                ord.setProperties(new PropertyDataVector());
            }
            ord.getProperties().add(prop);
		}
		if ( tponum == null ) {
		    System.out.println("jcpORNMapping, null CustomerPoNumber"
				       + " ord=" + ord );
		    tponum = "";
		}
		tponum += "/"
		    + ord.getCustomerReleaseNumber() + "/"
		    + jcpMapORNToDept(ord.getCustomerReleaseNumber());
		System.out.println("jcpORNMapping, new CustomerPoNumber"
				       + " tponum=" + tponum );
		ord.setCustomerPoNumber(tponum);
	    }
	}
    }

    /**
     * extract all segments included in ST - SE Segment that we are intersted in
     */
    public void extract() throws OBOEException {
        Table table = ts.getHeaderTable();
        Table dtable = ts.getDetailTable();

        currOrder = OrderRequestData.createValue();
        mOrders.add(currOrder);

        extractHeaderBEG(table);
        jcpORNMapping();
        extractHeaderSAC(table);
        extractHeaderREF(table);
        extractHeaderCSH(table);
        extractHeaderDTM(table);
        extractHeaderN9(table);
        extractHeaderN1(table);
        this.extractDetailPO1(dtable);
    }

    //************************Constructor******************************
    public Inbound850_JCP() {
    }
}
