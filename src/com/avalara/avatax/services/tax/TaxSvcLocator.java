/**
 * TaxSvcLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.configuration.XMLStringProvider;

import java.net.URL;
import java.net.MalformedURLException;

/**
 * Class that implements the {@link TaxSvc} interface and is used to create a local proxy
 * ({@link TaxSvcSoap interface}) to interact with Avalara's Tax Web Service.
 * <pre>
 * <b>Example:</b>
 * [Java]
 * EngineConfiguration config = new FileProvider("avatax4j.wsdd");
 * TaxSvcLocator taxSvcLoc = new TaxSvcLocator(config);
 *
 * TaxSvcSoap svc = taxSvcLoc.getTaxSvcSoap(new URL("http://www.avalara.com/services/"));
 *
 * // Set the profile
 * Profile profile = new Profile();
 * profile.setClient("TaxSvcTest,4.0.0.0");
 * svc.setProfile(profile);
 *
 * // Set security
 * Security security = new Security();
 * security.setAccount("account");
 * security.setLicense("license number");
 * svc.setSecurity(security);
 *
 * IsAuthorizedResult result = svc.isAuthorized("GetTax,PostTax");
 *
 * </pre>
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class TaxSvcLocator extends org.apache.axis.client.Service implements com.avalara.avatax.services.tax.TaxSvc {

    /**
     * Initializes a new instance of the class.
     */
    public TaxSvcLocator() {
    }

    /**
     * Initializes a new instance of the class.
     *
     * @param config
     */
    public TaxSvcLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    /**
     * Initializes a new instance of the class.
     *
     * @param wsdlLoc
     * @param sName
     * @throws javax.xml.rpc.ServiceException
     */
    public TaxSvcLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TaxSvcSoap
    private java.lang.String TaxSvcSoap_address = "http://localhost/avatax.services/tax/taxsvc.asmx";

    /**
     * Gets the current default URL that any tax service proxy (implementing
     * {@link TaxSvcSoap} interface)
     * created by this object will use to find a corresponding Tax Web Service
     * to contact.
     *
     * @return String representation of the default Tax Service URL.
     */
    public java.lang.String getTaxSvcSoapAddress() {
        return TaxSvcSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TaxSvcSoapWSDDServiceName = "TaxSvcSoap";

    /**
     * Gets the WSDD Service Name;
     * This method is used internally by the adapter
     * and not intended to be used by external implementation code.
     *
     * @return
     */
    public java.lang.String getTaxSvcSoapWSDDServiceName() {
        return TaxSvcSoapWSDDServiceName;
    }

    /**
     * Sets the WSDD Service Name;
     * This method is used internally by the adapter
     * and not intended to be used by external implementation code.
     *
     * @param name
     */
    public void setTaxSvcSoapWSDDServiceName(java.lang.String name) {
        TaxSvcSoapWSDDServiceName = name;
    }

    /**
     * Gets the AddressService Proxy object ({@link TaxSvcSoap}) that communicates
     * with Avalara's Tax Web service at default URL.
     *
     * @return Tax Service Proxy
     * @throws javax.xml.rpc.ServiceException
     */
    public com.avalara.avatax.services.tax.TaxSvcSoap getTaxSvcSoap() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TaxSvcSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTaxSvcSoap(endpoint);
    }

    /**
     * Gets the AddressService Proxy object ({@link TaxSvcSoap}) that communicates
     * with Avalara's Tax Web service at the URL specified.
     *
     * @param portAddress
     * @return  Tax Service Proxy
     * @throws javax.xml.rpc.ServiceException
     */
    public com.avalara.avatax.services.tax.TaxSvcSoap getTaxSvcSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            // BEGIN CUSTOM
            String path = portAddress.toString();
            if (!path.endsWith(".asmx"))
            {
                if (!path.endsWith("/"))
                {
                    path += "/";
                }
                path += "tax/taxsvc.asmx";
                portAddress = new URL(path);
            }
            // END CUSTOM

            com.avalara.avatax.services.tax.TaxSvcSoapStub _stub = new com.avalara.avatax.services.tax.TaxSvcSoapStub(portAddress, this);
            _stub.setPortName(getTaxSvcSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
        // BEGIN CUSTOM
        catch (MalformedURLException e)
        {
            return null;
        }
        // END CUSTOM
    }

    /**
     * This method is used internally by the adapter
     * and not intended to be used by external implementation code.
     *
     * @param address
     */
    public void setTaxSvcSoapEndpointAddress(java.lang.String address) {
        TaxSvcSoap_address = address;
    }

    /**
     * This method is used internally by the adapter
     * and not intended to be used by external implementation code.
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     *
     * @param serviceEndpointInterface
     * @return
     * @throws javax.xml.rpc.ServiceException
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.avalara.avatax.services.tax.TaxSvcSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.avalara.avatax.services.tax.TaxSvcSoapStub _stub = new com.avalara.avatax.services.tax.TaxSvcSoapStub(new java.net.URL(TaxSvcSoap_address), this);
                _stub.setPortName(getTaxSvcSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * This method is used internally by the adapter
     * and not intended to be used by external implementation code.
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     *
     * @param portName
     * @param serviceEndpointInterface
     * @return
     * @throws javax.xml.rpc.ServiceException
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("TaxSvcSoap".equals(inputPortName)) {
            return getTaxSvcSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    /**
     * This method is used internally by the adapter
     * and not intended to be used by external implementation code.
     *
     * @return Iterator over the collection of ports.
     */
    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxSvc");
    }

    private java.util.HashSet ports = null;

    /**
     *
     * @return
     */
    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxSvcSoap"));
        }
        return ports.iterator();
    }

    /**
     * This method is used internally by the adapter
     * and not intended to be used by external implementation code.
     * Set the endpoint address for the specified port name.
     *
     * @param portName
     * @param address
     * @throws javax.xml.rpc.ServiceException
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("TaxSvcSoap".equals(portName)) {
            setTaxSvcSoapEndpointAddress(address);
        }
        else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
     * This method is used internally by the adapter
     * and not intended to be used by external implementation code.
     * Set the endpoint address for the specified port name.
     *
     * @param portName
     * @param address
     * @throws javax.xml.rpc.ServiceException
     */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
