package com.avalara.avatax.services.base;

import javax.xml.soap.SOAPException;

/**
 * Interface for the basic Web service functionality shared by the Address and Tax Service --
 * essentially setting the connection and authentication information necessary for
 * both service proxies to connect to Avalara's Web services.
 *
 * <pre>
 * <b>Example:</b>
 * EngineConfiguration config = new FileProvider("avatax4j.wsdd");
 *
 * AddressSvcLocator svcLoc = new AddressSvcLocator(config);
 * AddressSvcSoap svc = svcLoc.getAddressSvcSoap(new URL("http://www.avalara.com/services/"));
 *
 * // Set the profile
 * Profile profile = new Profile();
 * profile.setClient("AddressSvcTest,4.0.0.0");
 * svc.setProfile(profile);
 *
 * // Set security
 * Security security = new Security();
 * security.setAccount("account");
 * security.setLicense("license number");
 * svc.setSecurity(security);
 *
 * PingResult result = svc.ping("");
 * </pre>
 *
 * @author brianh
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public interface BaseSvcSoap extends java.rmi.Remote
{
    /**
     * Set the Profile information for the service.
     * @param profile
     * @throws SOAPException
     */
    public void setProfile(Profile profile) throws SOAPException;

    /**
     * Get the Profile information for the service.
     *
     * @return  Profile object
     */
    public Profile getProfile();

    /**
     * Set the authentication information via a {@link Security} object.
     * @param security
     */
    public void setSecurity(Security security);

    /**
     * Get the authentication information via a {@link Security} object.
     *
     * @return Security
     */
    public Security getSecurity();
}
