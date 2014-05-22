/**
 * TaxSvc.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 * In the the Axis scheme, interface implemented by the object ({@link TaxSvcLocator})
 * that wraps the actual functional Address Service proxy ({@link TaxSvcSoapStub}).
 *
 * @see TaxSvcLocator
 * @see TaxSvcSoap
 * @see TaxSvcSoapStub
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public interface TaxSvc extends javax.xml.rpc.Service {

    /**
     * Retrieve as a String the default URL set for the Address Service to use.
     * @return Address service URL as a string.
     */
    public java.lang.String getTaxSvcSoapAddress();

    /**
     * Get a proxy for Avalara's Tax Web Service (object implementing the {@link TaxSvcSoap}
     * interface) using the default URL as coded in the class or programatically set.
     * <pre>
     * <b>Example:</b>
     * [Java]
     * EngineConfiguration config = new FileProvider("avatax4j.wsdd");
     * TaxSvcLocator taxSvcLoc = new TaxSvcLocator(config);
     *
     * TaxSvcSoap svc = taxSvcLoc.getTaxSvcSoap();
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
     *
     * @return
     * @throws javax.xml.rpc.ServiceException
     */
    public com.avalara.avatax.services.tax.TaxSvcSoap getTaxSvcSoap() throws javax.xml.rpc.ServiceException;

    /**
     * Get a proxy for Avalara's Address Web Service using the specified URL.
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
     *
     * @param portAddress
     * @return
     * @throws javax.xml.rpc.ServiceException
     */
    public com.avalara.avatax.services.tax.TaxSvcSoap getTaxSvcSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
