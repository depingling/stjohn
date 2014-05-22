package com.avalara.avatax.services.base;

import org.apache.axis.message.SOAPHeaderElement;
import com.avalara.avatax.services.base.wss.Password;
import com.avalara.avatax.services.base.wss.SecurityHeader;
import com.avalara.avatax.services.base.wss.UsernameToken;

import javax.xml.soap.SOAPException;

/**
 *
 * Base class for web service interface classes -- Not creatable;
 * Create instead {@link com.avalara.avatax.services.address.AddressSvcSoapStub} or
 * {@link com.avalara.avatax.services.tax.TaxSvcSoapStub} objects via an Axis Service proxy
 * factory class: (@link com.avalara.avatax.services.address.AddressSvc} or
 * {@link com.avalara.avatax.services.tax.TaxSvc}.
 *
 * <pre>
 * <b>Example:</b>
 * [Java]
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
public class BaseSvcSoapStub extends org.apache.axis.client.Stub
        implements com.avalara.avatax.services.base.BaseSvcSoap
{
    public static final String WSSE_NAMESPACE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
    public static final String PASSWORD_TEXT_TYPE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText";

    protected BaseSvcSoapStub()
    {
        profile = new Profile();
        security = new Security();
    }

    /**
     * Provides access to the request profile data; Should be set prior to making a request.
     * <pre>
     * <b>Example:</b>
     * [Java]
     * // Set the profile
     * Profile profile = new Profile();
     * profile.setClient("AddressSvcTest,4.0.0.0");
     * svc.setProfile(profile);
     * <pre>
     *
     * @param profile
     * @throws SOAPException
     */
    public void setProfile(Profile profile) throws SOAPException
    {
        this.profile = profile;

        SOAPHeaderElement profileHeader = profile.getSOAPHeaderElement();
        this.setHeader(profileHeader);
    }

    /**
     * Provides access to the request profile data; Should be set prior to making a request.
     * <pre>
     * <b>Example:</b>
     * [Java]
     * // Set the profile
     * Profile profile = new Profile();
     * profile.setClient("AddressSvcTest,4.0.0.0");
     * svc.setProfile(profile);
     * <pre>
     *
     *
     * @return Profile object
     */
    public Profile getProfile()
    {
        return profile;
    }

    /**
     * Provides access to Security details (e.g., account name and license #).
     * <pre>
     * <b>Example:</b>
     * [Java]
     * // Set security
     * Security security = new Security();
     * security.setAccount("account");
     * security.setLicense("license number");
     * svc.setSecurity(security);
     *

     * PingResult result = svc.ping("");
     * </pre>
     *
     * @param value
     */
    public void setSecurity(Security value)
    {
        security = value;
        String userName = security.getUserName();
        String passwordText = security.getPassword();
        String account = security.getAccount();
        if (userName != null && userName.trim().length() != 0)
        {
            // If no password is given and the userName contains "@" and an account is given, then
            // append the account to the username (e.g. joe@1234567890).
            if (passwordText == null || passwordText.length() == 0)
            {
                if (userName.indexOf("@") < 0 && account != null && account.length() > 0)
                {
                    userName += "@" + account;
                }
            }
        }
        else if (account.trim().length() != 0)
        {
            userName = security.getAccount().trim();
            passwordText = security.getLicense().trim();
        }
        else
        {
            throw new IllegalStateException("Must supply either a " +
                    "valid Account or UserName in the " +
                    "avatax4j.properties file");
        }

        SecurityHeader securityHeader = new SecurityHeader();
        UsernameToken usernameToken = new UsernameToken();
        usernameToken.setUsername(userName);
        Password password = new Password();
        password.setType(PASSWORD_TEXT_TYPE);
        password.set_value(passwordText);
        usernameToken.setPassword(password);
        securityHeader.setUsernameToken(usernameToken);
        setHeader(WSSE_NAMESPACE, "Security", securityHeader);
    }


    /**
     * Provides access to Security details (e.g., account name and license #).
     * <pre>
     * <b>Example:</b>
     * [Java]
     * // Set security
     * Security security = new Security();
     * security.setAccount("account");
     * security.setLicense("license number");
     * svc.setSecurity(security);
     *
     * PingResult result = svc.ping("");
     * </pre>
     *
     * @return Security object.
     */
    public Security getSecurity()
    {
        return security;
    }

    // Private members

    private Profile profile;
    private Security security;
}
