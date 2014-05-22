/*
 * IntegrationRequestData.java
 *
 * Created on March 7, 2005, 5:24 PM
 */

package com.cleanwise.service.api.value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cleanwise.service.api.framework.ValueObject;

/**
 *
 * @author dling
 */
public class OutboundEventData extends ValueObject{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 6445837674070183427L;

    private String fileName;
    private byte[] byteArray;
    private String partnerKey;
    private String setType;
    private int parentEventId = 0;
    private Map sendParameterMap;
    private List<ValueObject> eventRelatedData = new ArrayList<ValueObject>();
    
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}
	public byte[] getByteArray() {
		return byteArray;
	}
	public void setPartnerKey(String partnerKey) {
		this.partnerKey = partnerKey;
	}
	public String getPartnerKey() {
		return partnerKey;
	}
	public void setSetType(String setType) {
		this.setType = setType;
	}
	public String getSetType() {
		return setType;
	}
	public void setSendParameterMap(Map sendParameterMap) {
		this.sendParameterMap = sendParameterMap;
	}
	public Map getSendParameterMap() {
		return sendParameterMap;
	}
	public void setEventRelatedData(List<ValueObject> eventRelatedData) {
		this.eventRelatedData = eventRelatedData;
	}
	public List<ValueObject> getEventRelatedData() {
		return eventRelatedData;
	}
	public void setParentEventId(int parentEventId) {
		this.parentEventId = parentEventId;
	}
	public int getParentEventId() {
		return parentEventId;
	}
}
