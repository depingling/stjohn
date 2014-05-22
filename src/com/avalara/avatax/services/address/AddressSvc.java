/**
 * AddressSvc.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.address;

/**
 * In the the Axis scheme, interface implemented by the object ({@link AddressSvcLocator})
 * that wraps the actual functional Address Service proxy ({@link AddressSvcSoapStub}).
 *
 * @see AddressSvcLocator
 * @see AddressSvcSoap
 * @see AddressSvcSoapStub
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public interface AddressSvc extends javax.xml.rpc.Service {

    /**
     * Retrieve as a String the default URL set for the Address Service to use.
     * @return Address service URL as a string.
     */
    public java.lang.String getAddressSvcSoapAddress();

    /**
     * Get a proxy for Avalara's Address Web Service (object implementing the {@link AddressSvcSoap}
     * interface) using the default URL as coded in the class or programatically set.
     *<p>
     * <b>Example:</b>
     * <pre>
     * [Java]
     *  EngineConfiguration config = new FileProvider("avatax4j.wsdd");
     *  AddressSvcLocator AddressSvc = new AddressSvcLocator(config);
     *
     *  AddressSvcSoap port = AddressSvc.getAddressSvcSoap();
     *
     *  // Set the profile
     *  Profile profile = new Profile();
     *  profile.setClient("AddressSvcTest,4.0.0.0");
     *  port.setProfile(profile);
     *
     *  // Set security
     *  Security security = new Security();
     *  security.setAccount("account");
     *  security.setLicense("license number");
     *  port.setSecurity(security);
     *
     *  ValidateRequest request = new ValidateRequest();
     *  Address address = new Address();
     *  address.setLine1("900 Winslow Way");
     *  address.setLine2("Suite 130");
     *  address.setCity("Bainbridge Is");
     *  address.setRegion("WA");
     *  address.setPostalCode("98110-2450");
     *  request.setAddress(address);
     *  request.setTextCase(TextCase.Upper);
     *
     *  ValidateResult result;
     *  result = port.validate(request);
     *  Address[] addresses = result.getValidAddresses().getValidAddress();
     *
     * </pre>
     *
     * @see AddressSvcSoapStub
     * @see AddressSvcSoap
     * @return
     * @throws javax.xml.rpc.ServiceException
     */
    public com.avalara.avatax.services.address.AddressSvcSoap getAddressSvcSoap() throws javax.xml.rpc.ServiceException;

    /**
     * Get a proxy for Avalara's Address Web Service using the specified URL.
     *<p>
     * <b>Example:</b>
     * <pre>
     * [Java]
     *  EngineConfiguration config = new FileProvider("avatax4j.wsdd");
     *  AddressSvc = new AddressSvcLocator(config);
     *
     *  AddressSvcSoap port = AddressSvc.getAddressSvcSoap(new URL("Enter Avatax URL"));
     *
     *  // Set the profile
     *  Profile profile = new Profile();
     *  profile.setClient("AddressSvcTest,4.0.0.0");
     *  port.setProfile(profile);
     *
     *  // Set security
     *  Security security = new Security();
     *  security.setAccount("account");
     *  security.setLicense("license number");
     *  port.setSecurity(security);
     *
     *  ValidateRequest request = new ValidateRequest();
     *  Address address = new Address();
     *  address.setLine1("900 Winslow Way");
     *  address.setLine2("Suite 130");
     *  address.setCity("Bainbridge Is");
     *  address.setRegion("WA");
     *  address.setPostalCode("98110-2450");
     *  request.setAddress(address);
     *  request.setTextCase(TextCase.Upper);
     *
     *  ValidateResult result;
     *  result = port.validate(request);
     *  Address[] addresses = result.getValidAddresses().getValidAddress();
     *
     * </pre>
     *
     * @see AddressSvcSoapStub
     * @see AddressSvcSoap
     *
     * @param portAddress URL to use for the AddressService.
     * @return
     * @throws javax.xml.rpc.ServiceException
     */
    public com.avalara.avatax.services.address.AddressSvcSoap getAddressSvcSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
