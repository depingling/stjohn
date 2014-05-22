package com.avalara.avatax.services.base;

/**
 *
 * Used to encapsulate security details used to authenticate the client,
 * for example the user's account name and license number.
 * This object is used to hold the information neccessary to support two
 * types of logins, each requiring two pieces of information:
 * <ul>
 * <li> Account & license number login
 * <li> User name & password login
 * </ul>
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
public class Security
{

    /**
     * Initializes a new instance of the class.
     */
    public Security()
    {
        account = "";
        license = "";
        userName = "";
        password = "";
    }

    /**
     * Account name, for account and license number login.
     * @return Account name
     */
    public String getAccount()
    {
        return account;
    }

    /**
     * Account name, for account and license number login.
     * @param account
     */
    public void setAccount(String account)
    {
        this.account = account;
    }

    /**
     * License number, for account and license number login.
     *
     * @return License Number
     */
    public String getLicense()
    {
        return license;
    }

    /**
     * License number, for account and license number login.
     *
     * @param license
     */
    public void setLicense(String license)
    {
        this.license = license;
    }

    /**
     * UserName, for UserName and password login.
     *
     * @return  User Name
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * UserName, for UserName and password login.
     *
     * @param userName
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    /**
     * password, for UserName and password login.
     *
     * @return  password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * password, for UserName and password login.
     *
     * @param password
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    private String account;
    private String license;
    private String userName;
    private String password;

}
