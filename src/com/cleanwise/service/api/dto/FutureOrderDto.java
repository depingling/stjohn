/**
 * 
 */
package com.cleanwise.service.api.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderInfoBase;

/**
 * @author ssharma
 *
 */
public class FutureOrderDto extends OrderInfoBase{

	private String _orderType;
	private String _schedule;
	private String _scheduleBeginDate;
	private String _scheduleEndDate;
	private String _releaseDate;
	private String _orderContent;
	private int _contentId;
	private String _orderTotal;
	private boolean _userSharedList;
	
	/**
	 * @return the _orderType
	 */
	public final String getOrderType() {
		return _orderType;
	}
	/**
	 * @param orderType the _orderType to set
	 */
	public final void setOrderType(String orderType) {
		_orderType = orderType;
	}
	
	/**
	 * @return the _schedule
	 */
	public final String getSchedule() {
		return _schedule;
	}
	/**
	 * @param schedule the _schedule to set
	 */
	public final void setSchedule(String schedule) {
		_schedule = schedule;
	}
	/**
	 * @return the _scheduleBeginDate
	 */
	public final String getScheduleBeginDate() {
		return _scheduleBeginDate;
	}
	/**
	 * @param scheduleBeginDate the _scheduleBeginDate to set
	 */
	public final void setScheduleBeginDate(String scheduleBeginDate) {
		_scheduleBeginDate = scheduleBeginDate;
	}
	/**
	 * @return the _scheduleEndDate
	 */
	public final String getScheduleEndDate() {
		return _scheduleEndDate;
	}
	/**
	 * @param scheduleEndDate the _scheduleEndDate to set
	 */
	public final void setScheduleEndDate(String scheduleEndDate) {
		_scheduleEndDate = scheduleEndDate;
	}
	/**
	 * @return the _releaseDate
	 */
	public final String getReleaseDate() {
		return _releaseDate;
	}
	/**
	 * @param releaseDate the _releaseDate to set
	 */
	public final void setReleaseDate(String releaseDate) {
		_releaseDate = releaseDate;
	}
	/**
	 * @return the _orderContent
	 */
	public final String getOrderContent() {
		return _orderContent;
	}
	/**
	 * @param orderContent the _orderContent to set
	 */
	public final void setOrderContent(String orderContent) {
		_orderContent = orderContent;
	}
	
	/**
	 * @return the _contentId
	 */
	public final int getContentId() {
		return _contentId;
	}
	/**
	 * @param contentId the _contentId to set
	 */
	public final void setContentId(int contentId) {
		_contentId = contentId;
	}
	/**
	 * @return the _orderTotal
	 */
	public final String getOrderTotal() {
		return _orderTotal;
	}
	/**
	 * @param orderTotal the _orderTotal to set
	 */
	public final void setOrderTotal(String orderTotal) {
		_orderTotal = orderTotal;
	}
	/**
	 * @return the _userSharedList
	 */
	public final boolean getUserSharedList() {
		return _userSharedList;
	}
	/**
	 * @param userSharedList the _userSharedList to set
	 */
	public final void setUserSharedList(boolean userSharedList) {
		_userSharedList = userSharedList;
	}
	
}
