/**
 * BackorderLoaderRequest.java
 *
 * Created on Nov 12, 2008
 */
package com.cleanwise.service.api.value;

import java.util.Date;

import com.cleanwise.service.api.framework.ValueObject;

/**
 * @author ssharma
 *
 */
public class BackorderLoaderRequest {
	
	String orderEntryDate;
	private String orderBranchCode;
	private int orderNum;
	private int orderDistributionNum;
	private int orderShipmentNum;
	private String custPartCode;
	private String location;
	private String shipFromBranchCode;
	private String itemDesc;
	private String uom;
	private int backorderQty;
	private String custIndicatorCode;
	private String itemDesc2;
	String estimatedInStock;
	private int orderQty;
	private int shipQty;
	private String custPONum;
	private String itemStatus;
	
	private String continueOnError;
	private boolean loadOnlyIfData;
	private boolean loadOnlyForActiveItem;

	public String getOrderEntryDate() {
		return orderEntryDate;
	}
	public void setOrderEntryDate(String orderEntryDate) {
		this.orderEntryDate = orderEntryDate;
	}
	
	public String getOrderBranchCode() {
		return orderBranchCode;
	}
	public void setOrderBranchCode(String orderBranchCode) {
		this.orderBranchCode = orderBranchCode;
	}
	
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	
	public int getOrderDistributionNum() {
		return orderDistributionNum;
	}
	public void setOrderDistributionNum(int orderDistributionNum) {
		this.orderDistributionNum = orderDistributionNum;
	}
	
	public int getOrderShipmentNum() {
		return orderShipmentNum;
	}
	public void setOrderShipmentNum(int orderShipmentNum) {
		this.orderShipmentNum = orderShipmentNum;
	}
	
	public String getCustPartCode() {
		return custPartCode;
	}
	public void setCustPartCode(String custPartCode) {
		this.custPartCode = custPartCode;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getShipFromBranchCode() {
		return shipFromBranchCode;
	}
	public void setShipFromBranchCode(String shipFromBranchCode) {
		this.shipFromBranchCode = shipFromBranchCode;
	}
	
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getItemDesc2() {
		return itemDesc2;
	}
	public void setItemDesc2(String itemDesc2) {
		this.itemDesc2 = itemDesc2;
	}
	
	public String getUOM() {
		return uom;
	}
	public void setUOM(String uom) {
		this.uom = uom;
	}
	
	public int getBackorderQty() {
		return backorderQty;
	}
	public void setBackorderQty(int backorderQty) {
		this.backorderQty = backorderQty;
	}
	
	public String getCustIndicatorCode() {
		return custIndicatorCode;
	}
	public void setCustIndicatorCode(String custIndicatorCode) {
		this.custIndicatorCode = custIndicatorCode;
	}
	
	public String getEstimatedInStock() {
		return estimatedInStock;
	}
	public void setEstimatedInStock(String estimatedInStock) {
		this.estimatedInStock = estimatedInStock;
	}
	
	public int getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}
	
	public int getShipQty() {
		return shipQty;
	}
	public void setShipQty(int shipQty) {
		this.shipQty = shipQty;
	}
	
	public String getCustPONum() {
		return custPONum;
	}
	public void setCustPONum(String custPONum) {
		this.custPONum = custPONum;
	}
	
	public String getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}
	
	/*

	private int itemNum; //check
	private String itemStatus; //check
	private String itemDesc;
	private String mfgItemNum;

	private int orderQty;
	private String orderQtyUOM;
	private int shipQty;
	private int backorderQty;//check
	private String backorderQtyUOM;

	private int orderNum;
	Date orderEntryDate;
	private String poNum;//check

	Date estimatedInStock;//check
	Date systemAcceptedDate;
	private String location;//check

	
	public int getItemNum() {
		return itemNum;
	}
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public String getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getMfgItemNum() {
		return mfgItemNum;
	}
	public void setMfgItemNum(String mfgItemNum) {
		this.mfgItemNum = mfgItemNum;
	}

	public int getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}

	public String getOrderQtyUOM() {
		return orderQtyUOM;
	}
	public void setOrderQtyUOM(String orderQtyUOM) {
		this.orderQtyUOM = orderQtyUOM;
	}

	public int getBackorderQty() {
		return backorderQty;
	}
	public void setBackorderQty(int backorderQty) {
		this.backorderQty = backorderQty;
	}

	public int getShipQty() {
		return shipQty;
	}
	public void setShipQty(int shipQty) {
		this.shipQty = shipQty;
	}

	public String getBackOrderQtyUOM() {
		return backorderQtyUOM;
	}
	public void setBackOrderQtyUOM(String backorderQtyUOM) {
		this.backorderQtyUOM = backorderQtyUOM;
	}

	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getPONum() {
		return poNum;
	}
	public void setPONum(String poNum) {
		this.poNum = poNum;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public Date getOrderEntryDate() {
		return orderEntryDate;
	}
	public void setOrderEntryDate(Date orderEntryDate) {
		this.orderEntryDate = orderEntryDate;
	}

	public Date getEstimatedInStock() {
		return estimatedInStock;
	}
	public void setEstimatedInStock(Date estimatedInStock) {
		this.estimatedInStock = estimatedInStock;
	}

	public Date getSystemAcceptedDate() {
		return systemAcceptedDate;
	}
	public void setSystemAcceptedDate(Date systemAcceptedDate) {
		this.systemAcceptedDate = systemAcceptedDate;
	}
*/
	
	public String getContinueOnError() {
		return continueOnError;
	}
	public void setContinueOnError(String continueOnError) {
		this.continueOnError = continueOnError;
	}

	public boolean isLoadOnlyIfData(){
		return loadOnlyIfData;
	}
	public void setLoadOnlyIfData(boolean loadOnlyIfData){
		this.loadOnlyIfData = loadOnlyIfData;
	}

	public boolean isLoadOnlyForActiveItem(){
		return loadOnlyForActiveItem;
	}
	public void setLoadOnlyForActiveItem(boolean loadOnlyForActiveItem){
		this.loadOnlyForActiveItem = loadOnlyForActiveItem;
	}

    public String toString(){
        StringBuffer buf = new StringBuffer();
        buf.append("Outbound PO Num="+getCustPONum()+" ");
        buf.append("Location="+getLocation()+" ");
        buf.append("Item num="+getCustPartCode()+" ");
        buf.append("Order qty="+getOrderQty()+" ");
        buf.append("Ship Qty="+getShipQty()+",");
        buf.append("Backorder Qty="+getBackorderQty()+",");
        buf.append("Estimated in stock="+getEstimatedInStock()+",");
        return buf.toString();
    }


}
