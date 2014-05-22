/*
 * IntegrationRequestData.java
 *
 * Created on March 7, 2005, 5:24 PM
 */

package com.cleanwise.service.api.value;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.session.Event;

/**
 *
 * @author dling
 */
public class InboundEventData extends ValueObject{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 1791728655106581193L;

    private String fileName;
    private byte[] byteArray;
    private String partnerKey;
    private URI sourceURI = null;
    private String status = Event.STATUS_READY;
    private int parentEventId = 0;
    
    public InboundEventData(){    	
    	try {
			sourceURI = new URI("null");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
    }
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
	public URI getSourceURI(){
        return sourceURI;
    }
    public void setSourceURI(URI pSourceURI){
    	sourceURI = pSourceURI;
    }
    public static InboundEventData generateInboundEvent(String fileName, byte[] byteArray, String partnerKey){
    	return generateInboundEvent(fileName, byteArray, partnerKey, null);
	}
    public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	
    public static InboundEventData generateInboundEvent(String fileName, byte[] byteArray, String partnerKey, URI sourceURI){
		InboundEventData eventData = new InboundEventData();
		eventData.setFileName(fileName);
		eventData.setPartnerKey(partnerKey);
		eventData.setByteArray(byteArray);
		if (sourceURI != null)
			eventData.setSourceURI(sourceURI);
		return eventData;
	}
	public void setParentEventId(int parentEventId) {
		this.parentEventId = parentEventId;
	}
	public int getParentEventId() {
		return parentEventId;
	}	
}
