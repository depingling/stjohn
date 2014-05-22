/**
 * AddressSvcLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.address;

import org.apache.axis.configuration.XMLStringProvider;

import java.net.URL;
import java.net.MalformedURLException;

/**
 * Class that implements the {@link AddressSvc} interface and is used to create a local proxy
 * ({@link AddressSvcSoap interface}) to interact with Avalara's Address Web Service.
 * <p><b>Example:</b><br>
 * <pre>
 * [Java]
 *  EngineConfiguration config = new FileProvider("avatax4j.wsdd");
 *  AddressSvcLocator svcLoc = new AddressSvcLocator(config);
 *
 *  AddressSvcSoap svc = svcLoc.getAddressSvcSoap(new URL("http://www.avalara.com/services/"));
 *
 *  // Set the profile
 *  Profile profile = new Profile();
 *  profile.setClient("AddressSvcTest,4.0.0.0");
 *  svc.setProfile(profile);
 *
 *  // Set security
 *  Security security = new Security();
 *  security.setAccount("account");
 *  security.setLicense("license number");
 *  svc.setSecurity(security);
 *
 *  PingResult result = svc.ping("");
 * </pre>
 *
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class AddressSvcLocator extends org.apache.axis.client.Service implements com.avalara.avatax.services.address.AddressSvc {

    /**
     * Initializes a new instance of the class.
     */
    public AddressSvcLocator() {
    }


    /**
     * Initializes a new instance of the class.
     *
     * @param config
     */
    public AddressSvcLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    /**
     * Initializes a new instance of the class.
     *
     * @param wsdlLoc
     * @param sName
     * @throws javax.xml.rpc.ServiceException
     */
    public AddressSvcLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AddressSvcSoap
    private java.lang.String AddressSvcSoap_address = "http://localhost/avatax.services/address/addresssvc.asmx";

    /**
     * Gets the current default URL that any address service proxy (implementing
     * {@link AddressSvcSoap} interface)
     * created by this object will use to find a corresponding Address Web Service
     * to contact.
     *
     * @return String representation of the default Address Service URL.
     */
    public java.lang.String getAddressSvcSoapAddress() {
        return AddressSvcSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AddressSvcSoapWSDDServiceName = "AddressSvcSoap";

    /**
     * Gets the WSDD Service Name.
     * This method is used internally by the adapter
     * and not intended to be used by external implementation code.
     * @return WSDD Service Name
     */
    public java.lang.String getAddressSvcSoapWSDDServiceName() {
        return AddressSvcSoapWSDDServiceName;
    }

    /**
     * Sets the WSDD Service Name;
     * This method is used internally by the adapter
     * and not intended to be used by external implementation code.
     *
     * @param name
     */
    public void setAddressSvcSoapWSDDServiceName(java.lang.String name) {
        AddressSvcSoapWSDDServiceName = name;
    }

    /**
     * Gets the AddressService Proxy object ({@link AddressSvcSoap}) for the default
     * URL.
     *
     * @return Address Service Proxy object.
     * @throws javax.xml.rpc.ServiceException
     */
    public com.avalara.avatax.services.address.AddressSvcSoap getAddressSvcSoap() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AddressSvcSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAddressSvcSoap(endpoint);
    }

    /**
     * Gets the AddressService Proxy object ({@link AddressSvcSoap}) that communicates
     * with Avalara's Address Web service at the URL specified.
     *
     * @param portAddress
     * @return Address Service Proxy object.
     * @throws javax.xml.rpc.ServiceException
     */
    public com.avalara.avatax.services.address.AddressSvcSoap getAddressSvcSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            // BEGIN CUSTOM
            String path = portAddress.toString();
            if (!path.endsWith(".asmx"))
            {
                if (!path.endsWith("/"))
                {
                    path += "/";
                }
                path += "address/addresssvc.asmx";
                portAddress = new URL(path);
            }
            // END CUSTOM
            com.avalara.avatax.services.address.AddressSvcSoapStub _stub = new com.avalara.avatax.services.address.AddressSvcSoapStub(portAddress, this);
            _stub.setPortName(getAddressSvcSoapWSDDServiceName());
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
    public void setAddressSvcSoapEndpointAddress(java.lang.String address) {
        AddressSvcSoap_address = address;
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
            if (com.avalara.avatax.services.address.AddressSvcSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.avalara.avatax.services.address.AddressSvcSoapStub _stub = new com.avalara.avatax.services.address.AddressSvcSoapStub(new java.net.URL(AddressSvcSoap_address), this);
                _stub.setPortName(getAddressSvcSoapWSDDServiceName());
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
        if ("AddressSvcSoap".equals(inputPortName)) {
            return getAddressSvcSoap();
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
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     *
     * @return Service Name
     */
    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://avatax.avalara.com/services", "AddressSvc");
    }

    private java.util.HashSet ports = null;

    /**
     * This method is used internally by the adapter
     * and not intended to be used by external implementation code.
     *
     * @return Iterator over the collection of ports.
     */
    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "AddressSvcSoap"));
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
        if ("AddressSvcSoap".equals(portName)) {
            setAddressSvcSoapEndpointAddress(address);
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
