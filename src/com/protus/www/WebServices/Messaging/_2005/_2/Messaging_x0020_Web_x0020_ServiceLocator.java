/**
 * Messaging_x0020_Web_x0020_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package com.protus.www.WebServices.Messaging._2005._2;

public class Messaging_x0020_Web_x0020_ServiceLocator extends org.apache.axis.client.Service implements com.protus.www.WebServices.Messaging._2005._2.Messaging_x0020_Web_x0020_Service {

/**
 * Messaging Web Service is one of Protus IP Solutions Web Services.
 * It allows you to send single faxes, fax and voice Broadcasts. Please,
 * contact support support@protus.com for a full documentation of this
 * Web Service. Latest Update Sep 2005.
 */

    public Messaging_x0020_Web_x0020_ServiceLocator() {
    }


    public Messaging_x0020_Web_x0020_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Messaging_x0020_Web_x0020_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Messaging_x0020_Web_x0020_ServiceSoap12
    private java.lang.String Messaging_x0020_Web_x0020_ServiceSoap12_address = "https://www.protusfax.com/protus/xmlwebservices/xmlsubmitter/messaging.asmx";

    public java.lang.String getMessaging_x0020_Web_x0020_ServiceSoap12Address() {
        return Messaging_x0020_Web_x0020_ServiceSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String Messaging_x0020_Web_x0020_ServiceSoap12WSDDServiceName = "Messaging_x0020_Web_x0020_ServiceSoap12";

    public java.lang.String getMessaging_x0020_Web_x0020_ServiceSoap12WSDDServiceName() {
        return Messaging_x0020_Web_x0020_ServiceSoap12WSDDServiceName;
    }

    public void setMessaging_x0020_Web_x0020_ServiceSoap12WSDDServiceName(java.lang.String name) {
        Messaging_x0020_Web_x0020_ServiceSoap12WSDDServiceName = name;
    }

    public com.protus.www.WebServices.Messaging._2005._2.Messaging_x0020_Web_x0020_ServiceSoap getMessaging_x0020_Web_x0020_ServiceSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Messaging_x0020_Web_x0020_ServiceSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMessaging_x0020_Web_x0020_ServiceSoap12(endpoint);
    }

    public com.protus.www.WebServices.Messaging._2005._2.Messaging_x0020_Web_x0020_ServiceSoap getMessaging_x0020_Web_x0020_ServiceSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.protus.www.WebServices.Messaging._2005._2.Messaging_x0020_Web_x0020_ServiceSoap12Stub _stub = new com.protus.www.WebServices.Messaging._2005._2.Messaging_x0020_Web_x0020_ServiceSoap12Stub(portAddress, this);
            _stub.setPortName(getMessaging_x0020_Web_x0020_ServiceSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMessaging_x0020_Web_x0020_ServiceSoap12EndpointAddress(java.lang.String address) {
        Messaging_x0020_Web_x0020_ServiceSoap12_address = address;
    }


    // Use to get a proxy class for Messaging_x0020_Web_x0020_ServiceSoap
    private java.lang.String Messaging_x0020_Web_x0020_ServiceSoap_address = "https://www.protusfax.com/protus/xmlwebservices/xmlsubmitter/messaging.asmx";

    public java.lang.String getMessaging_x0020_Web_x0020_ServiceSoapAddress() {
        return Messaging_x0020_Web_x0020_ServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String Messaging_x0020_Web_x0020_ServiceSoapWSDDServiceName = "Messaging_x0020_Web_x0020_ServiceSoap";

    public java.lang.String getMessaging_x0020_Web_x0020_ServiceSoapWSDDServiceName() {
        return Messaging_x0020_Web_x0020_ServiceSoapWSDDServiceName;
    }

    public void setMessaging_x0020_Web_x0020_ServiceSoapWSDDServiceName(java.lang.String name) {
        Messaging_x0020_Web_x0020_ServiceSoapWSDDServiceName = name;
    }

    public com.protus.www.WebServices.Messaging._2005._2.Messaging_x0020_Web_x0020_ServiceSoap getMessaging_x0020_Web_x0020_ServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Messaging_x0020_Web_x0020_ServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMessaging_x0020_Web_x0020_ServiceSoap(endpoint);
    }

    public com.protus.www.WebServices.Messaging._2005._2.Messaging_x0020_Web_x0020_ServiceSoap getMessaging_x0020_Web_x0020_ServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.protus.www.WebServices.Messaging._2005._2.Messaging_x0020_Web_x0020_ServiceSoapStub _stub = new com.protus.www.WebServices.Messaging._2005._2.Messaging_x0020_Web_x0020_ServiceSoapStub(portAddress, this);
            _stub.setPortName(getMessaging_x0020_Web_x0020_ServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMessaging_x0020_Web_x0020_ServiceSoapEndpointAddress(java.lang.String address) {
        Messaging_x0020_Web_x0020_ServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.protus.www.WebServices.Messaging._2005._2.Messaging_x0020_Web_x0020_ServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.protus.www.WebServices.Messaging._2005._2.Messaging_x0020_Web_x0020_ServiceSoap12Stub _stub = new com.protus.www.WebServices.Messaging._2005._2.Messaging_x0020_Web_x0020_ServiceSoap12Stub(new java.net.URL(Messaging_x0020_Web_x0020_ServiceSoap12_address), this);
                _stub.setPortName(getMessaging_x0020_Web_x0020_ServiceSoap12WSDDServiceName());
                return _stub;
            }
            if (com.protus.www.WebServices.Messaging._2005._2.Messaging_x0020_Web_x0020_ServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.protus.www.WebServices.Messaging._2005._2.Messaging_x0020_Web_x0020_ServiceSoapStub _stub = new com.protus.www.WebServices.Messaging._2005._2.Messaging_x0020_Web_x0020_ServiceSoapStub(new java.net.URL(Messaging_x0020_Web_x0020_ServiceSoap_address), this);
                _stub.setPortName(getMessaging_x0020_Web_x0020_ServiceSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Messaging_x0020_Web_x0020_ServiceSoap12".equals(inputPortName)) {
            return getMessaging_x0020_Web_x0020_ServiceSoap12();
        }
        else if ("Messaging_x0020_Web_x0020_ServiceSoap".equals(inputPortName)) {
            return getMessaging_x0020_Web_x0020_ServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("https://www.protus.com/WebServices/Messaging/2005/2", "Messaging_x0020_Web_x0020_Service");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Messaging/2005/2", "Messaging_x0020_Web_x0020_ServiceSoap12"));
            ports.add(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Messaging/2005/2", "Messaging_x0020_Web_x0020_ServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Messaging_x0020_Web_x0020_ServiceSoap12".equals(portName)) {
            setMessaging_x0020_Web_x0020_ServiceSoap12EndpointAddress(address);
        }
        else 
if ("Messaging_x0020_Web_x0020_ServiceSoap".equals(portName)) {
            setMessaging_x0020_Web_x0020_ServiceSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
