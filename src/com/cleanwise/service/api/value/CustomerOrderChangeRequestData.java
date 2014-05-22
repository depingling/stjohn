package com.cleanwise.service.api.value;

import java.util.Collection;

import com.cleanwise.service.api.framework.ValueObject;

public class CustomerOrderChangeRequestData extends ValueObject{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 1242684879519289635L;
	private OrderData orderData;
	private Collection orderItemChangeRequests;
	private boolean markReceived;
	private String userName;
	private boolean doNotChangeOrderStatusCd = false;
	private String orderSource;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Collection getOrderItemChangeRequests() {
		return orderItemChangeRequests;
	}
	public void setOrderItemChangeRequests(Collection orderItemChangeRequests) {
		this.orderItemChangeRequests = orderItemChangeRequests;
	}
	public OrderData getOrderData() {
		return orderData;
	}
	public void setOrderData(OrderData orderData) {
		this.orderData = orderData;
	}
	private CustomerOrderChangeRequestData(){}
	public static CustomerOrderChangeRequestData createValue (){
		return new CustomerOrderChangeRequestData();
	}
	public boolean isMarkReceived() {
		return markReceived;
	}
	public void setMarkReceived(boolean markReceived) {
		this.markReceived = markReceived;
	}
	public boolean isDoNotChangeOrderStatusCd() {
		return doNotChangeOrderStatusCd;
	}
	public void setDoNotChangeOrderStatusCd(boolean doNotChangeOrderStatusCd) {
		this.doNotChangeOrderStatusCd = doNotChangeOrderStatusCd;
	}
	
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
}
