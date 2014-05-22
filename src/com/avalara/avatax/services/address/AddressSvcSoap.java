/**
 * AddressSvcSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.address;

/**
 * Proxy interface for the Avalara Address Web Service. Requires a
 * Web Service Deployment Descriptor (WSDD) configuration file at creation time named
 * (for example the sample file avatax4j.wsdd) in the same directory as the
 * project is running. The values in the file will be loaded as the default
 * configuration information.
 * <p>
 * <b>Example:</b>
 * <pre>
 * [Java]
 *  EngineConfiguration config = new FileProvider("avatax4j.wsdd");
 *  AddressSvc AddressSvc = new AddressSvcLocator(config);
 *
 *  AddressSvcSoap port = AddressSvc.getAddressSvcSoap(new URL("http://www.avalara.com/services/"));
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
 * </pre>
 * @see org.apache.axis.EngineConfiguration
 * @see org.apache.axis.configuration.FileProvider
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public interface AddressSvcSoap extends com.avalara.avatax.services.base.BaseSvcSoap {

    /**
     * Validates an address and returns a collection of possible
     * {@link ValidAddress} objects in a {@link ValidateResult} object.
     * <p>
     * The <b>ValidateRequest</b> object includes a {@link TextCase}
     * property that determines the casing applied to a validated
     * address.  It defaults to {@link TextCase#Default}.
     * <p><b>Example:</b><br>
     * <pre>
     * [Java]
     *  EngineConfiguration config = new FileProvider("avatax4j.wsdd");
     *  AddressSvcLocator AddressSvc = new AddressSvcLocator(config);
     *
     *  AddressSvcSoap port = AddressSvc.getAddressSvcSoap(new URL("http://www.avalara.com/services/"));
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
     * @param validateRequest a {@link ValidateRequest} object
     * containing address data to be validated.
     * @return  a {@link ValidateResult} object containing a
     * collection of zero or more validated addresses.
     *
     * @throws java.rmi.RemoteException
     */
    public com.avalara.avatax.services.address.ValidateResult validate(com.avalara.avatax.services.address.ValidateRequest validateRequest) throws java.rmi.RemoteException;

    /**
     * Verifies connectivity to the web service and returns version
     * information about the service.
     * <p>
     * <b>NOTE:</b>This replaces TestConnection and is available on
     * every service.
     *
     * @param message for future use
     * @return a {@link PingResult} object
     * @throws java.rmi.RemoteException
     */
    public com.avalara.avatax.services.address.PingResult ping(java.lang.String message) throws java.rmi.RemoteException;

    /**
     * Checks authentication of and authorization to one or more
     * operations on the service.
     * <p>This operation allows pre-authorization checking of any
     * or all operations. It will return a comma delimited set of
     * operation names which will be all or a subset of the requested
     * operation names.  For security, it will never return operation
     * names other than those requested (no phishing allowed).
     * <p><b>Example:</b><br>
     * <code> isAuthorized("GetTax,PostTax")</code>
     *
     * @param operations  a comma-delimited list of operation names
     *
     * @return  {@link IsAuthorizedResult} object
     * @throws java.rmi.RemoteException
     */
    public com.avalara.avatax.services.address.IsAuthorizedResult isAuthorized(java.lang.String operations) throws java.rmi.RemoteException;
}
