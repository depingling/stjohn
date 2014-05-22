package com.cleanwise.service.api.dto;

import com.cleanwise.service.api.value.OrderGuideStructureDataVector;
import com.cleanwise.service.api.value.ProductDataVector;

/**
 * 
 * Holds the necessary information to calculate the cart amount pertaining to cost centers
 * which need to be shown in the location budget chart
 */
public class CostCenterCartData {
	
	private ProductDataVector _productDV;
	private OrderGuideStructureDataVector _orderGuidesdv;
	
	/**
	 * 
	 * @return the productDV
	 */
	public ProductDataVector getProductDV() {
		return _productDV;
	}
	
	/**
	 * @param productDV the productDV to set
	 */
	public void setProductDV(ProductDataVector productDV) {
		_productDV = productDV;
	}
	
	/**
	 * 
	 * @return the orderGuidesdv
	 */
	public OrderGuideStructureDataVector getOrderGuidesdv() {
		return _orderGuidesdv;
	}
	
	/**
	 * @param orderGuidesdv the orderGuidesdv to set
	 */
	public void setOrderGuidesdv(OrderGuideStructureDataVector orderGuidesdv) {
		_orderGuidesdv = orderGuidesdv;
	}

}
