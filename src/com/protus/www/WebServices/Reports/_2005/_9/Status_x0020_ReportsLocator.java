/**
 * Status_x0020_ReportsLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 19, 2006 (02:31:34 GMT+00:00) WSDL2Java emitter.
 */

package com.protus.www.WebServices.Reports._2005._9;

public class Status_x0020_ReportsLocator extends org.apache.axis.client.Service implements com.protus.www.WebServices.Reports._2005._9.Status_x0020_Reports {

/**
 * Status Reports is one of Protus IP Solutions Web Services. Please,
 * contact sales at sales@protus.com for a full documentation of this
 * Web Service.
 */

    public Status_x0020_ReportsLocator() {
    }


    public Status_x0020_ReportsLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Status_x0020_ReportsLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Status_x0020_ReportsSoap
    private java.lang.String Status_x0020_ReportsSoap_address = "https://www.protusfax.com/protus/xmlwebservices/xmlreports/statusreports.asmx";

    public java.lang.String getStatus_x0020_ReportsSoapAddress() {
        return Status_x0020_ReportsSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String Status_x0020_ReportsSoapWSDDServiceName = "Status_x0020_ReportsSoap";

    public java.lang.String getStatus_x0020_ReportsSoapWSDDServiceName() {
        return Status_x0020_ReportsSoapWSDDServiceName;
    }

    public void setStatus_x0020_ReportsSoapWSDDServiceName(java.lang.String name) {
        Status_x0020_ReportsSoapWSDDServiceName = name;
    }

    public com.protus.www.WebServices.Reports._2005._9.Status_x0020_ReportsSoap getStatus_x0020_ReportsSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Status_x0020_ReportsSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getStatus_x0020_ReportsSoap(endpoint);
    }

    public com.protus.www.WebServices.Reports._2005._9.Status_x0020_ReportsSoap getStatus_x0020_ReportsSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.protus.www.WebServices.Reports._2005._9.Status_x0020_ReportsSoapStub _stub = new com.protus.www.WebServices.Reports._2005._9.Status_x0020_ReportsSoapStub(portAddress, this);
            _stub.setPortName(getStatus_x0020_ReportsSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setStatus_x0020_ReportsSoapEndpointAddress(java.lang.String address) {
        Status_x0020_ReportsSoap_address = address;
    }


    // Use to get a proxy class for Status_x0020_ReportsSoap12
    private java.lang.String Status_x0020_ReportsSoap12_address = "https://www.protusfax.com/protus/xmlwebservices/xmlreports/statusreports.asmx";

    public java.lang.String getStatus_x0020_ReportsSoap12Address() {
        return Status_x0020_ReportsSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String Status_x0020_ReportsSoap12WSDDServiceName = "Status_x0020_ReportsSoap12";

    public java.lang.String getStatus_x0020_ReportsSoap12WSDDServiceName() {
        return Status_x0020_ReportsSoap12WSDDServiceName;
    }

    public void setStatus_x0020_ReportsSoap12WSDDServiceName(java.lang.String name) {
        Status_x0020_ReportsSoap12WSDDServiceName = name;
    }

    public com.protus.www.WebServices.Reports._2005._9.Status_x0020_ReportsSoap getStatus_x0020_ReportsSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Status_x0020_ReportsSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getStatus_x0020_ReportsSoap12(endpoint);
    }

    public com.protus.www.WebServices.Reports._2005._9.Status_x0020_ReportsSoap getStatus_x0020_ReportsSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.protus.www.WebServices.Reports._2005._9.Status_x0020_ReportsSoap12Stub _stub = new com.protus.www.WebServices.Reports._2005._9.Status_x0020_ReportsSoap12Stub(portAddress, this);
            _stub.setPortName(getStatus_x0020_ReportsSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setStatus_x0020_ReportsSoap12EndpointAddress(java.lang.String address) {
        Status_x0020_ReportsSoap12_address = address;
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
            if (com.protus.www.WebServices.Reports._2005._9.Status_x0020_ReportsSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.protus.www.WebServices.Reports._2005._9.Status_x0020_ReportsSoapStub _stub = new com.protus.www.WebServices.Reports._2005._9.Status_x0020_ReportsSoapStub(new java.net.URL(Status_x0020_ReportsSoap_address), this);
                _stub.setPortName(getStatus_x0020_ReportsSoapWSDDServiceName());
                return _stub;
            }
            if (com.protus.www.WebServices.Reports._2005._9.Status_x0020_ReportsSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.protus.www.WebServices.Reports._2005._9.Status_x0020_ReportsSoap12Stub _stub = new com.protus.www.WebServices.Reports._2005._9.Status_x0020_ReportsSoap12Stub(new java.net.URL(Status_x0020_ReportsSoap12_address), this);
                _stub.setPortName(getStatus_x0020_ReportsSoap12WSDDServiceName());
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
        if ("Status_x0020_ReportsSoap".equals(inputPortName)) {
            return getStatus_x0020_ReportsSoap();
        }
        else if ("Status_x0020_ReportsSoap12".equals(inputPortName)) {
            return getStatus_x0020_ReportsSoap12();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("https://www.protus.com/WebServices/Reports/2005/9", "Status_x0020_Reports");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Reports/2005/9", "Status_x0020_ReportsSoap"));
            ports.add(new javax.xml.namespace.QName("https://www.protus.com/WebServices/Reports/2005/9", "Status_x0020_ReportsSoap12"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Status_x0020_ReportsSoap".equals(portName)) {
            setStatus_x0020_ReportsSoapEndpointAddress(address);
        }
        else 
if ("Status_x0020_ReportsSoap12".equals(portName)) {
            setStatus_x0020_ReportsSoap12EndpointAddress(address);
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
