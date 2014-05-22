/*
 * ContactInfoView.java
 *
 * Created on May 24, 2005, 1:05 PM
 */

package com.cleanwise.view.utils;

import com.cleanwise.service.api.value.*;

/**
 * Container for all of the elements that go into creating the "contact us" sections of the site
 * @author bstevens
 */
public class ContactUsInfo {

    /**
     * Holds value of property address.
     */
    private AddressData address;

    /**
     * Holds value of property fax.
     */
    private String fax;

    /**
     * Holds value of property phone.
     */
    private String phone;

    /**
     * Holds value of property email.
     */
    private String email;

    /**
     * Holds value of property callHours.
     */
    private String callHours;

    /**
     * Holds value of property nickName.
     */
    private String nickName;

    /**
     * Holds value of property contactName.
     */
    private String contactName;
    
    /** Creates a new instance of ContactInfoView */
    public ContactUsInfo() {
    }

    /**
     * Getter for property address.
     * @return Value of property address.
     */
    public AddressData getAddress() {

        return this.address;
    }

    /**
     * Setter for property address.
     * @param address New value of property address.
     */
    public void setAddress(AddressData address) {

        this.address = address;
    }

    /**
     * Getter for property fax.
     * @return Value of property fax.
     */
    public String getFax() {

        return this.fax;
    }

    /**
     * Setter for property fax.
     * @param fax New value of property fax.
     */
    public void setFax(String fax) {

        this.fax = fax;
    }

    /**
     * Getter for property phone.
     * @return Value of property phone.
     */
    public String getPhone() {

        return this.phone;
    }

    /**
     * Setter for property phone.
     * @param phone New value of property phone.
     */
    public void setPhone(String phone) {

        this.phone = phone;
    }

    /**
     * Getter for property email.
     * @return Value of property email.
     */
    public String getEmail() {

        return this.email;
    }

    /**
     * Setter for property email.
     * @param email New value of property email.
     */
    public void setEmail(String email) {

        this.email = email;
    }

    /**
     * Getter for property callHours.
     * @return Value of property callHours.
     */
    public String getCallHours() {

        return this.callHours;
    }

    /**
     * Setter for property callHours.
     * @param callHours New value of property callHours.
     */
    public void setCallHours(String callHours) {

        this.callHours = callHours;
    }

    /**
     * Getter for property nickName.
     * @return Value of property nickName.
     */
    public String getNickName() {

        return this.nickName;
    }

    /**
     * Setter for property nickName.
     * @param nickName New value of property nickName.
     */
    public void setNickName(String nickName) {

        this.nickName = nickName;
    }

    /**
     * Getter for property contactName.
     * @return Value of property contactName.
     */
    public String getContactName() {

        return this.contactName;
    }

    /**
     * Setter for property contactName.
     * @param contactName New value of property contactName.
     */
    public void setContactName(String contactName) {

        this.contactName = contactName;
    }

    
}
