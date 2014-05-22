/**
 * Messaging_x0020_Web_x0020_ServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package com.protus.www.WebServices.Messaging._2005._2;

public interface Messaging_x0020_Web_x0020_ServiceSoap extends java.rmi.Remote {

    /**
     * Use this web method if you want to send one fax to one recipient.
     */
    public com.protus.www.WebServices.Messaging._2005._2.SendResponse sendSingleFax(java.lang.String xmlDocument) throws java.rmi.RemoteException;

    /**
     * Use this web method if you want to send faxes to many recipients.
     */
    public com.protus.www.WebServices.Messaging._2005._2.SendResponse sendFaxBroadcast(java.lang.String xmlDocument) throws java.rmi.RemoteException;

    /**
     * Use this web method if you want to send voice messages to many
     * recipients.
     */
    public com.protus.www.WebServices.Messaging._2005._2.SendResponse sendVoiceBroadcast(java.lang.String xmlDocument) throws java.rmi.RemoteException;
}
