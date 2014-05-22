package com.cleanwise.service.api.value;

import java.util.Collection;

import com.cleanwise.service.api.framework.ValueObject;
import java.util.Iterator;

public class OrderItemChangeRequestData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 190164564589749895L;
	private OrderItemData orderItemData;
	private Integer newTotalQuantityReceived;
	private Integer newTotalQuantityOrdered;
	
	public int getOrderItemId(){
		if(orderItemData != null){
			return orderItemData.getOrderItemId();
		}
		return 0;
	}
	
	public OrderItemData getOrderItemData() {
		return orderItemData;
	}

	public Integer getNewTotalQuantityReceived() {
		return newTotalQuantityReceived;
	}
	public void setNewTotalQuantityReceived(Integer TotalQuantityReceived) {
		this.newTotalQuantityReceived = TotalQuantityReceived;
	}
	
	/**
	 * Returns the orignal total quantity ordered
	 */
	public int getOriginalTotalQuantityReceived(){
		return orderItemData.getTotalQuantityReceived();
	}
	
	public Integer getNewTotalQuantityOrdered() {
		return newTotalQuantityOrdered;
	}

	public void setNewTotalQuantityOrdered(Integer newTotalQuantityOrdered) {
		this.newTotalQuantityOrdered = newTotalQuantityOrdered;
	}
	
	
	/**
	 * Private constructor
	 */
	private OrderItemChangeRequestData(){}
	
	/**
	 * Constructor
	 */
	public OrderItemChangeRequestData(OrderItemData pOrderItemData){
		this.orderItemData = pOrderItemData;
	}
	
	/**
	 * Searches the list for the OrderItemChangeRequestData specified by the order item id 
	 * @param orderItemChangeRequests Collection of OrderItemChangeRequestData objects
	 * @param orderItemId the order item id that uniquly identifies the request
	 * @return the OrderItemChangeRequestData from the collection with the identified order item id
	 */
	public static OrderItemChangeRequestData getOrderItemChangeRequestData(Collection orderItemChangeRequests, int orderItemId){
		if(orderItemChangeRequests == null){
			return null;
		}
		Iterator it = orderItemChangeRequests.iterator();
		while(it.hasNext()){
			OrderItemChangeRequestData req = (OrderItemChangeRequestData) it.next();
			if(req.getOrderItemData() != null && req.getOrderItemData().getOrderItemId() == orderItemId){
				return req;
			}
		}
		return null;
	}

}
