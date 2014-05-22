package com.cleanwise.view.taglibs;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;


import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ShopTool;

public class FormatPriceCurrency extends TagSupport{
	
	private static final Logger log = Logger.getLogger(FormatPriceCurrency.class);

	private Object _price;
	private int _contractId;
	private int _orderId;
	private String _negativeClass;
	private boolean _checkNegative;
	private String _zeroClass;
	private boolean _checkZero;
	private String _appendSymbol;

	/**
	 * @return the _price
	 */
	public final Object getPrice() {
		return _price;
	}

	/**
	 * @param price the _price to set
	 */
	public final void setPrice(Object price) {
		_price = price;
	}

	/**
	 * @return the _contractId
	 */
	public final int getContractId() {
		return _contractId;
	}

	/**
	 * @param contractId the _contractId to set
	 */
	public final void setContractId(int contractId) {
		_contractId = contractId;
	}

	/**
	 * @return the _orderId
	 */
	public final int getOrderId() {
		return _orderId;
	}

	/**
	 * @param orderId the _orderId to set
	 */
	public final void setOrderId(int orderId) {
		_orderId = orderId;
	}

	/**
	 * @return the _checkNegative
	 */
	public final boolean getCheckNegative() {
		return _checkNegative;
	}

	/**
	 * @param checkNegative the _checkNegative to set
	 */
	public final void setCheckNegative(boolean checkNegative) {
		_checkNegative = checkNegative;
	}

	/**
	 * @return the _checkZero
	 */
	public final boolean getCheckZero() {
		return _checkZero;
	}

	/**
	 * @param checkZero the _checkZero to set
	 */
	public final void setCheckZero(boolean checkZero) {
		_checkZero = checkZero;
	}
	
	/**
	 * @return the _negativeClass
	 */
	public final String getNegativeClass() {
		return _negativeClass;
	}

	/**
	 * @param negativeClass the _negativeClass to set
	 */
	public final void setNegativeClass(String negativeClass) {
		_negativeClass = negativeClass;
	}

	/**
	 * @return the _zeroClass
	 */
	public final String getZeroClass() {
		return _zeroClass;
	}

	/**
	 * @param zeroClass the _zeroClass to set
	 */
	public final void setZeroClass(String zeroClass) {
		_zeroClass = zeroClass;
	}

	/**
	 * @return the _appendSymbol
	 */
	public final String getAppendSymbol() {
		return _appendSymbol;
	}

	/**
	 * @param appendSymbol the _appendSymbol to set
	 */
	public final void setAppendSymbol(String appendSymbol) {
		_appendSymbol = appendSymbol;
	}

	public int doStartTag() throws JspException {
		
		try{
			JspWriter out = pageContext.getOut();
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		
			CleanwiseUser appUser = ShopTool.getCurrentUser(request);
			int userId = appUser.getUserId();
			
			boolean showPrice = appUser.getShowPrice();

			StringBuffer htmlBuffer  = new StringBuffer();
			
			if(showPrice){
				String formattedPrice = ClwI18nUtil.formatAmount(request, getPrice(), getContractId(), getOrderId(), userId);
				
				if(getCheckZero()){
					
					if(isZeroPrice(getPrice())){
						htmlBuffer.append("<span class=\""+getZeroClass()+"\">");
						htmlBuffer.append(formattedPrice);
						if(Utility.isSet(getAppendSymbol())){
							htmlBuffer.append(getAppendSymbol());
						}
						htmlBuffer.append("</span>");
					}else{
						htmlBuffer.append(formattedPrice);
					}
					
				}else if(getCheckNegative()){
					
					if(isNegativePrice(getPrice())){
						htmlBuffer.append("<span class=\""+getNegativeClass()+"\">");
						htmlBuffer.append("-");
						htmlBuffer.append(formattedPrice);
						htmlBuffer.append("</span>");
					}else{
						htmlBuffer.append(formattedPrice);
					}
					
				}else{
					htmlBuffer.append(formattedPrice);
				}
				
			}
			out.print(htmlBuffer.toString());
		}catch(IOException ioe){
			log.error("Unexpected exception in FormatPriceCurrency: ", ioe);
		}catch(Exception e){
			log.error("Unexpected exception in FormatPriceCurrency: ", e);
		}
		return SKIP_BODY;

	}
	
	private boolean isZeroPrice(Object price) throws Exception{
		
		boolean isZero = false;
		if(price instanceof BigDecimal) {
			
			if(((BigDecimal) price).compareTo(new BigDecimal(0.0)) == 0){
				isZero = true;
			}
		} else if(price instanceof Double) {
			
			if(((Double)price).equals(new Double(0.0))){
				isZero = true;
			}
			
		} else if(price instanceof String){
			try{
				Double priceD=Double.parseDouble((String) price);
				if(((Double)priceD).equals(new Double(0.0))){
					isZero = true;
				}
			}catch(NumberFormatException e){
				throw new Exception(e.getMessage());
			}
		}
		
		return isZero;
	}
	
	private boolean isNegativePrice(Object price) throws Exception{
		
		boolean isNegative = false;
		if(price instanceof BigDecimal) {
			
			if(((BigDecimal) price).compareTo(new BigDecimal(0.0)) < 0){
				isNegative = true;
			}
		} else if(price instanceof Double) {
			
			if(((Double)price) < (new Double(0.0))){
				isNegative = true;
			}
			
		} else if(price instanceof String){
			try{
				Double priceD=Double.parseDouble((String) price);
				if(((Double)priceD) < (new Double(0.0))){
					isNegative = true;
				}
			}catch(NumberFormatException e){
				throw new Exception(e.getMessage());
			}
		}
		
		return isNegative;
	}
	
}
