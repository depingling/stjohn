/*
 * TaxQuery.java
 *
 * Created on September 13, 2005, 9:47 PM
 *
 * Copyright September 13, 2005 Cleanwise, Inc.
 */

package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.ValueObject;
import java.math.BigDecimal;

/**
 *
 * @author bstevens
 */
public class TaxQuery extends ValueObject{
    
    /** Creates a new instance of TaxQuery */
    public TaxQuery() {
    }
    
    /** Creates a new instance of TaxQuery */
    public TaxQuery(BigDecimal amount, SiteData pSite, AccountData pAccount, StoreData pStore) {
        setAmount(amount);
        setSite(pSite);
        setAccount(pAccount);
        setStore(pStore);
    }
    
    /** Creates a new instance of TaxQuery */
    public TaxQuery(BigDecimal amount, int pSiteId, int pAccountId, int pStoreId) {
        setAmount(amount);
        setSiteId(pSiteId);
        setAccountId(pAccountId);
        setStoreId(pStoreId);
    }

    /**
     * Holds value of property siteId.
     */
    private int siteId;

    /**
     * Getter for property siteId.
     * @return Value of property siteId.
     */
    public int getSiteId() {

        return this.siteId;
    }

    /**
     * Setter for property siteId.
     * @param siteId New value of property siteId.
     */
    public void setSiteId(int siteId) {

        this.siteId = siteId;
    }

    /**
     * Holds value of property accountId.
     */
    private int accountId;

    /**
     * Getter for property accountId.
     * @return Value of property accountId.
     */
    public int getAccountId() {

        return this.accountId;
    }

    /**
     * Setter for property accountId.
     * @param accountId New value of property accountId.
     */
    public void setAccountId(int accountId) {

        this.accountId = accountId;
    }

    /**
     * Holds value of property storeId.
     */
    private int storeId;

    /**
     * Getter for property storeId.
     * @return Value of property storeId.
     */
    public int getStoreId() {

        return this.storeId;
    }

    /**
     * Setter for property storeId.
     * @param storeId New value of property storeId.
     */
    public void setStoreId(int storeId) {

        this.storeId = storeId;
    }

    /**
     * Holds value of property store.
     */
    private StoreData store;

    /**
     * Getter for property store.
     * @return Value of property store.
     */
    public StoreData getStore() {

        return this.store;
    }

    /**
     * Setter for property store.
     * @param store New value of property store.
     */
    public void setStore(StoreData store) {

        this.store = store;
    }

    /**
     * Holds value of property account.
     */
    private AccountData account;

    /**
     * Getter for property account.
     * @return Value of property account.
     */
    public AccountData getAccount() {

        return this.account;
    }

    /**
     * Setter for property account.
     * @param account New value of property account.
     */
    public void setAccount(AccountData account) {

        this.account = account;
    }

    /**
     * Holds value of property site.
     */
    private SiteData site;

    /**
     * Getter for property site.
     * @return Value of property site.
     */
    public SiteData getSite() {

        return this.site;
    }

    /**
     * Setter for property site.
     * @param site New value of property site.
     */
    public void setSite(SiteData site) {

        this.site = site;
    }

    /**
     * Holds value of property amount.
     */
    private BigDecimal amount;

    /**
     * Getter for property amount.
     * @return Value of property amount.
     */
    public BigDecimal getAmount() {

        return this.amount;
    }

    /**
     * Setter for property amount.
     * @param amount New value of property amount.
     */
    public void setAmount(BigDecimal amount) {

        this.amount = amount;
    }

    /**
     * Holds value of property shipTo.
     */
    private AddressData shipTo;

    /**
     * Getter for property shipTo.
     * @return Value of property shipTo.
     */
    public AddressData getShipTo() {

        return this.shipTo;
    }

    /**
     * Setter for property shipTo.
     * @param shipTo New value of property shipTo.
     */
    public void setShipTo(AddressData shipTo) {

        this.shipTo = shipTo;
    }

    /**
     * Setter for property shipTo.
     */
    public void setShipTo(String pShippingCity, String pShippingState, String pShippingPostalCode, String pShippingCountry){
        AddressData shipTo = AddressData.createValue();
        shipTo.setCity(pShippingCity);
        shipTo.setStateProvinceCd(pShippingState);
        shipTo.setPostalCode(pShippingPostalCode);
        shipTo.setCountryCd(pShippingCountry);
        setShipTo(shipTo);
    }
}


