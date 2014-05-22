package com.cleanwise.service.apps.edi;

import java.math.BigDecimal;

import com.americancoders.edi.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.OrderAddOnChargeData;
import com.cleanwise.service.api.value.OrderAddOnChargeDataVector;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.TradingPartnerData;

import org.apache.log4j.Logger;



/**
 * Formats the order extraction into a EDI document, the format specified by Lagase.
 *
 * @author Deping
 */
public class Outbound850_Network extends Outbound850 {
	protected Logger log = Logger.getLogger(this.getClass());
	
	interface CHECKOUT_FIELD {
        public static final String
        	PROJECT_CHARGE_NUMBER = "PROJECT",
        	BUYERS_NAME = "BUYERS NAME",
        	BUYERS_EMPLOYEE_NUMBER = "EMPLOYEE",
        	BRANCH_NUMBER = "BRANCH";
    }
	
	public void buildTransactionContent()
    throws Exception {
		super.buildTransactionContent();
		Table detailtable = ts.getDetailTable();
		buildDetailPO1AddOnCharges(detailtable);
    }

    
	// set request date to null so that MSG segment will not include the requested date
    public void buildHeaderN9(Table inTable, String idCode) throws OBOEException {        
    	mReqshipdate = null;
    	super.buildHeaderN9(inTable, idCode);
    }
    
    public void buildDetailPO1AddOnCharges(Table inTable)
    throws Exception {
    	String distErpNum = ((OrderItemData)currOutboundReq.getOrderItemDV().get(0)).getDistErpNum();
    	int distId = getBusEntityIdByEntityTypeAndErpNum(RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR, distErpNum);
        APIAccess mApiAccess = new APIAccess();
    	Distributor distEjb = mApiAccess.getDistributorAPI();
    	OrderAddOnChargeDataVector addOnCharges = distEjb.getOrderAddOnCharges(distId, currOrder.getOrderId());
    	
    	if(addOnCharges!=null && addOnCharges.size()>0){
    		for(int i=0; i<addOnCharges.size(); i++){
    			String addOnSku = null;    	    	
    			OrderAddOnChargeData charge = (OrderAddOnChargeData)addOnCharges.get(i);
    			String chargeType = charge.getDistFeeChargeCd();
    			BigDecimal amount = charge.getAmount();
    			if(amount.doubleValue()>0.009999 || amount.doubleValue()<-0.009999) {
	    			if (chargeType.equals(RefCodeNames.CHARGE_CD.DISCOUNT)){
	    				addOnSku = "998813";
	    				if (amount != null && amount.longValue() > 0) {
	    	    			amount = amount.negate();
	    	    		}
	    			} else if (chargeType.equals(RefCodeNames.CHARGE_CD.FUEL_SURCHARGE)){
	    				addOnSku = "998100";
	    	    	} else if (chargeType.equals(RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE)){
	    	    		addOnSku = "998812";
	    	    	}
	    			buildDetailPO1AddOnCharge(inTable, addOnSku, amount, chargeType);
    			}
    		}    		
    	}
    }
    
    public void buildDetailPO1AddOnCharge(Table inTable, String addOnSku, BigDecimal amount, String description)
    throws OBOEException {
        Loop loop = inTable.createLoop("PO1");
        inTable.addLoop(loop); 
        Segment segment = loop.createSegment("PO1");
        loop.addSegment(segment); 


        DataElement de;
        de = (DataElement)segment.buildDE(1); // 350 Assigned Identification
        de.set("");
        de = (DataElement)segment.buildDE(2); // 330 Quantity Ordered
        de.set(Integer.toString(1));
        de = (DataElement)segment.buildDE(3); // 355 Unit or Basis for Measurement Code
        de.set("EA");
        de = (DataElement)segment.buildDE(4); // 212 Unit Price
        de.set(amount.toString());
        de = (DataElement)segment.buildDE(5); // 639 Basis of Unit Price Code
        de.set("PE");
        de = (DataElement)segment.buildDE(6); // 235 Product/Service ID Qualifier

        // Part number qualifier can be determined by the setting
        // in the tradding partner for the following trading partner property.
        // Network need this in order to switch their 850 processing to the
        // network sku numbers.  durval
        String qual = getValueFromMappingByPropertyTypeCd("PartNumQualifier");
        if ( null != qual && qual.length() > 0 ) {
            de.set(qual);
        } else {
            de.set("VN");
        }

        de = (DataElement)segment.buildDE(7); // 234 Product/Service ID
        de.set(addOnSku);
        de = (DataElement)segment.buildDE(8); // 235 Product/Service ID Qualifier   
        buildDetailPO1PIDAddOnCharge(loop, description);
    }
    
    public void buildDetailPO1PIDAddOnCharge(Loop inLoop, String description)
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
        de.set(description);
    }
    
    //Add on charges - discount, small order fee, fuel surcharge
    // do noting since this has been taking cared in method buildDetailPO1AddOnCharges
    public void buildHeaderSAC(Table inTable) throws Exception {    	
    }
    
    /** builds segment REF that is part of the Header
     *<br>Reference Identification used
     *<br>To specify identifying information
     *param inTable table containing this segment
     *throws OBOEException - most likely segment not found
     */
    public void buildHeaderREF(Table inTable)
    throws Exception {
    	super.buildHeaderREF(inTable);
    	OrderPropertyDataVector opdv = currOutboundReq.getOrderPropertyDV();
        for (int i = 0; opdv != null && i < opdv.size(); i++) {
            OrderPropertyData orderProp = (OrderPropertyData)opdv.get(i);
            if (orderProp.getOrderPropertyTypeCd().equals(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CHECKOUT_FIELD_CD)){
            	if (orderProp.getShortDesc().toUpperCase().indexOf(CHECKOUT_FIELD.PROJECT_CHARGE_NUMBER) >= 0){
            		Segment segment = inTable.createSegment("REF");
                    inTable.addSegment(segment);
                    DataElement de;
                    de = (DataElement)segment.buildDE(1); // 128 Reference Identification Qualifier
                    de.set("JB"); // Job (Project) Number
                    de = (DataElement)segment.buildDE(2); // 127 Reference Identification
                    de.set(Utility.subString(orderProp.getValue(), 30));
            	}
            }
        }
    }
    
    /** builds segment PER that is part of the TableHeader
     *<br>Administrative Communications Contact used 
     *<br>To identify a person or office to whom administrative communications should be directed
     * @param inTable table containing this segment
     * @return segment object PER
     * @throws OBOEException - most likely segment not found
     */
     public void buildHeaderPER(Table inTable)
       throws OBOEException
     {
    	 OrderPropertyDataVector opdv = currOutboundReq.getOrderPropertyDV();
         for (int i = 0; opdv != null && i < opdv.size(); i++) {
             OrderPropertyData orderProp = (OrderPropertyData)opdv.get(i);
             if (orderProp.getOrderPropertyTypeCd().equals(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CHECKOUT_FIELD_CD)){
            	 String code = null;
             	if (orderProp.getShortDesc().toUpperCase().indexOf(CHECKOUT_FIELD.BUYERS_NAME) >= 0){
             		code = "BD"; // Buyer Name or Department
             	}else if (orderProp.getShortDesc().toUpperCase().indexOf(CHECKOUT_FIELD.BUYERS_EMPLOYEE_NUMBER) >= 0){
             		code = "IC"; // Information Contact
             	}else if (orderProp.getShortDesc().toUpperCase().indexOf(CHECKOUT_FIELD.BRANCH_NUMBER) >= 0){
             		code = "OD"; // Order Department
             	}else{
             		continue;
             	}
             	
             	Segment segment = inTable.createSegment("PER");
                inTable.addSegment(segment);
                DataElement de = (DataElement) segment.buildDE(1);  // 366 Contact Function Code
                de.set(code);
                de = (DataElement) segment.buildDE(2);  // 93 Name
                de.set(Utility.subString(orderProp.getValue(), 60));
             }
         }
     }
}
