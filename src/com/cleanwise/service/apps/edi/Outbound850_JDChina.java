package com.cleanwise.service.apps.edi;

import com.americancoders.edi.DataElement;
import com.americancoders.edi.OBOEException;
import com.americancoders.edi.Segment;
import com.americancoders.edi.Table;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.TradingPartnerData;

/**
 * @author Deping
 */
public class Outbound850_JDChina extends Outbound850 {
	public void buildHeaderBEG(Table inTable)
    throws OBOEException {

        Segment segment = inTable.createSegment("BEG");
        inTable.addSegment(segment);

        DataElement de;
        de = (DataElement)segment.buildDE(1); // 353 Transaction Set Purpose Code
        de.set("00");
        de = (DataElement)segment.buildDE(2); // 92 Purchase Order Type Code
        String qual = getValueFromMappingByPropertyTypeCd("PurchaseOrderTypeCode");
        de.set("DS");
        de = (DataElement)segment.buildDE(3); // 324 Purchase Order Number
        
        currOutboundReq.getPurchaseOrderD().setOutboundPoNum(mErpPoNum);
        OrderItemDataVector orderItemDV = currOutboundReq.getOrderItemDV();
        for (int i = 0; orderItemDV != null && i < orderItemDV.size(); i++) {
            OrderItemData orderItemD = (OrderItemData) orderItemDV.get(i);
            orderItemD.setOutboundPoNum(mErpPoNum);
        }
        de.set(mErpPoNum);


        de = (DataElement)segment.buildDE(4); // 328 Release Number
        de.set("");
        de = (DataElement)segment.buildDE(5); // 373 Date
        de.set(mPoDateTime);
    }
	protected void validateStateProvinceCode(String stateOrProv) throws Exception{
    	if (Utility.isSet(stateOrProv) && (stateOrProv.length() > 3))
    		throw new Exception("Maximum of 3 digit of State/Province code is required - " + stateOrProv);
    }
}
