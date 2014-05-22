package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.*;

/**
 *
 * @author VEpshtein
 */

public class StorePoForm  extends StorePortalBaseForm {
    
    private String mOutboundPoNum = "";
    
    private PurchaseOrderData PurchaseOrderData;
    
    public void setOutboundPoNum(String pVal) {this.mOutboundPoNum = pVal;}
    public String getOutboundPoNum() {return (this.mOutboundPoNum);}
    
    public PurchaseOrderData getPurchaseOrderData() {return PurchaseOrderData;}
    public void setPurchaseOrderData(PurchaseOrderData newPurchaseOrderData) {this.PurchaseOrderData = newPurchaseOrderData;}
    
}
