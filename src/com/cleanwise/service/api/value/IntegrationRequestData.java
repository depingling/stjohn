/*
 * IntegrationRequestData.java
 *
 * Created on March 7, 2005, 5:24 PM
 */

package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.ValueObject;

/**
 *
 * @author bstevens
 */
public class IntegrationRequestData extends ValueObject{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -4579242397802597336L;

    /**
     * Holds value of property message.
     */
    private String message;

    /**
     * Holds value of property error.
     */
    private boolean error;

    /**
     * Holds value of property integrationRequest.
     */
    private Object integrationRequest;
    
    public IntegrationRequestData(Object pIntegrationRequest, String pMessage, boolean isError) {
        error = isError;
        message = pMessage;
        integrationRequest = pIntegrationRequest;
    }
    
    /** Creates a new instance of IntegrationRequestData */
    public IntegrationRequestData(Object pIntegrationRequest, String pMessage) {
        error = false;
        message = pMessage;
        integrationRequest = pIntegrationRequest;
    }

    /**
     * Getter for property message.
     * @return Value of property message.
     */
    public String getMessage() {

        return this.message;
    }

    /**
     * Setter for property message.
     * @param message New value of property message.
     */
    public void setMessage(String message) {

        this.message = message;
    }

    /**
     * Getter for property error.
     * @return Value of property error.
     */
    public boolean isError() {

        return this.error;
    }

    /**
     * Setter for property error.
     * @param error New value of property error.
     */
    public void setError(boolean error) {

        this.error = error;
    }

    /**
     * Getter for property integrationRequest.
     * @return Value of property integrationRequest.
     */
    public Object getIntegrationRequest() {

        return this.integrationRequest;
    }

    /**
     * Setter for property integrationRequest.
     * @param integrationRequest New value of property integrationRequest.
     */
    public void setIntegrationRequest(Object integrationRequest) {

        this.integrationRequest = integrationRequest;
    }
    
}
