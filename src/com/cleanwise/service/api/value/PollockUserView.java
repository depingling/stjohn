package com.cleanwise.service.api.value;

/**
 * Title:        PollockUserView
 * Description:  This is a ViewObject class for UI.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;

/**
 * <code>PollockUserView</code> is a ViewObject class for UI.
 */
public class PollockUserView
extends ValueObject
{
   
    private static final long serialVersionUID = 6123436666666654531L;
	private String versionCd;
	private String actionCd;
	private String storeId;
	private String storeName;
	private String accountRefNum;
	private String accountName;
	private String siteName;
	private String siteRefNum;
	private String username;
	private String password;
	private String updatePassword;
	private String preferredLanguage;
	private String firstName;
	private String lastName;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String postalCode;
	private String country;
	private String phone;
	private String email;
	private String fax;
	private String mobile;
	private String approver;
	private String needsApproval;
	private String wasApproved;
	private String wasRejected;
	private String wasModified;
	private String corporateUser;
	private String receiveInventoryMisEmail;
	private String onAccount;
	private String creditCard;
	private String otherPayment;
	private String poNumRequired;
	private String showPrice;
	private String contractItemsOnly;
	private String browseOnly;
	private String noReporting;
	private String orderDetailNotification;
	private String shippingNotification;
	private String workOrderCompletedNotifi;
	private String workOrderAcceptedNotifi;
	private String workOrderRejectedNotifi;
	private String groupId;


    /**
     * Constructor.
     */
    public PollockUserView ()
    {
		versionCd = "";
		actionCd = "";
		storeId = "";
		storeName = "";
		accountRefNum = "";
		accountName = "";
		siteName = "";
		siteRefNum = "";
		username = "";
		password = "";
		updatePassword = "";
		preferredLanguage = "";
		firstName = "";
		lastName = "";
		address1 = "";
		address2 = "";
		city = "";
		state = "";
		postalCode = "";
		country = "";
		phone = "";
		email = "";
		fax = "";
		mobile = "";
		approver = "";
		needsApproval = "";
		wasApproved = "";
		wasRejected = "";
		wasModified = "";
		corporateUser = "";
		receiveInventoryMisEmail = "";
		onAccount = "";
		creditCard = "";
		otherPayment = "";
		poNumRequired = "";
		showPrice = "";
		contractItemsOnly = "";
		browseOnly = "";
		noReporting = "";
		orderDetailNotification = "";
		shippingNotification = "";
		workOrderCompletedNotifi = "";
		workOrderAcceptedNotifi = "";
		workOrderRejectedNotifi = "";
		groupId = "";
    }

    /**
     * Constructor. 
     */
    public PollockUserView(String parm1, String parm2, String parm3, String parm4,String parm5,String parm6,String parm7,String parm8,String parm9,String parm10,String parm11,String parm12,String parm13,String parm14,String parm15,String parm16,String parm17,String parm18,String parm19,String parm20,String parm21,String parm22,String parm23,String parm24,String parm25,String parm26,String parm27,String parm28,String parm29,String parm30,String parm31,String parm32,String parm33,String parm34,String parm35,String parm36,String parm37,String parm38,String parm39,String parm40,String parm41,String parm42,String parm43,String parm44,String parm45)
    {
        versionCd = parm1;
		actionCd = parm2;
		storeId = parm3;
		storeName = parm4;
		accountRefNum = parm5;
		accountName = parm6;
		siteName = parm7;
		siteRefNum = parm8;
		username = parm9;
		password = parm10;
		updatePassword = parm11;
		preferredLanguage = parm12;
		firstName = parm13;
		lastName = parm14;
		address1 = parm15;
		address2 = parm16;
		city = parm17;
		state = parm18;
		postalCode = parm19;
		country = parm20;
		phone = parm21;
		email = parm22;
		fax = parm23;
		mobile = parm24;
		approver = parm25;
		needsApproval = parm26;
		wasApproved = parm27;
		wasRejected = parm28;
		wasModified = parm29;
		corporateUser = parm30;
		receiveInventoryMisEmail = parm31;
		onAccount = parm32;
		creditCard = parm33;
		otherPayment = parm34;
		poNumRequired = parm35;
		showPrice = parm36;
		contractItemsOnly = parm37;
		browseOnly = parm38;
		noReporting = parm39;
		orderDetailNotification = parm40;
		shippingNotification = parm41;
		workOrderCompletedNotifi = parm42;
		workOrderAcceptedNotifi = parm43;
		workOrderRejectedNotifi = parm44;
		groupId = parm45;

        
    }

    /**
     * Creates a new PollockUserView
     *
     * @return
     *  Newly initialized PollockUserView object.
     */
    public static PollockUserView createValue () 
    {
        PollockUserView valueView = new PollockUserView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PollockUserView object
     */
    public String toString()
    {
        return "[" + "versionNumber=" + versionCd + "actionCd=" + actionCd +  "storeID=" + storeId + "storeName=" +
			storeName + "accountRefNum=" + accountRefNum + "accountName=" + accountName + "siteName=" + siteName + "siteRefNum=" + siteRefNum 
			+ "username=" + username + "password=" + "password" + "updatePassword" + updatePassword + "preferredLanguage" + preferredLanguage + 
			"firstName=" + firstName + "lastName=" + lastName + "address1=" + address1 + "address2="  + address2 + "city=" + city + "state=" + state + "postalCode=" + postalCode +
			"country=" + country + "phone=" + phone + "email=" + email +"]";
    }


    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public PollockUserView copy()  {
      PollockUserView obj = new PollockUserView();
		obj.setversionCd(versionCd);
		obj.setactionCd(actionCd);
		obj.setstoreId(storeId);
		obj.setstoreName(storeName);
		obj.setaccountRefNum(accountRefNum);
		obj.setaccountName(accountName);
		obj.setsiteName(siteName);
		obj.setsiteRefNum(siteRefNum);
		obj.setusername(username);
		obj.setpassword(password);
		obj.setupdatePassword(updatePassword);
		obj.setpreferredLanguage(preferredLanguage);
		obj.setfirstName(firstName);
		obj.setlastName(lastName);
		obj.setaddress1(address1);
		obj.setaddress2(address2);
		obj.setcity(city);
		obj.setstate(state);
		obj.setpostalCode(postalCode);
		obj.setcountry(country);
		obj.setphone(phone);
		obj.setemail(email);
		obj.setfax(fax);
		obj.setmobile(mobile);
		obj.setapprover(approver);
		obj.setneedsApproval(needsApproval);
		obj.setwasApproved(wasApproved);
		obj.setwasRejected(wasRejected);
		obj.setwasModified(wasModified);
		obj.setcorporateUser(corporateUser);
		obj.setreceiveInventoryMisEmail(receiveInventoryMisEmail);
		obj.setonAccount(onAccount);
		obj.setcreditCard(creditCard);
		obj.setotherPayment(otherPayment);
		obj.setpoNumRequired(poNumRequired);
		obj.setshowPrice(showPrice);
		obj.setcontractItemsOnly(contractItemsOnly);
		obj.setbrowseOnly(browseOnly);
		obj.setnoReporting(noReporting);
		obj.setorderDetailNotification(orderDetailNotification);
		obj.setshippingNotification(shippingNotification);
		obj.setworkOrderCompletedNotifi(workOrderCompletedNotifi);
		obj.setworkOrderAcceptedNotifi(workOrderAcceptedNotifi);
		obj.setworkOrderRejectedNotifi(workOrderRejectedNotifi);
		obj.setgroupId(groupId);
      
      return obj;
    }

    
   public void setversionCd(String versionCd){
	this.versionCd = versionCd;
	}

	public String getversionCd() {
	return versionCd;
	}


		
	public void setactionCd(String actionCd){
	this.actionCd = actionCd;
	}

	public String getactionCd(){
	return actionCd;
	}


	public void setstoreId(String storeId){ 
	this.storeId = storeId;
	}

	public String getstoreId() {
	return storeId;
	}


	public void setstoreName(String storeName){
	this.storeName = storeName;
	}

	public String getstoreName() {
	return storeName;
	}

	
	public String getaccountRefNum() { 
	return accountRefNum;
	}
	
	
	public void setaccountRefNum(String accountRefNum){
	this.accountRefNum = accountRefNum;
	}
	
	
	public String getaccountName() { 
	return accountName;
	}
	
	public void setaccountName(String accountName){
	this.accountName = accountName;
	}
	
	public String getsiteName() { 
	return siteName;
	}
	
	public void setsiteName(String siteName){
	this.siteName = siteName;
	}
	
	
	public String getsiteRefNum() { 
	return siteRefNum;
	}
	
	public void setsiteRefNum(String siteRefNum){
	this.siteRefNum = siteRefNum;
	}
	
	
	public String getusername() { 
	return username;
	}
	
	public void setusername(String username){
	this.username = username;
	}
	
	public String getpassword() { 
	return password;
	}
	
	public void setpassword(String password){
	this.password = password;
	}
	
	public String getupdatePassword() { 
	return updatePassword;
	}
	
	public void setupdatePassword(String updatePassword){
	this.updatePassword = updatePassword;
	}
	
	
	public String getpreferredLanguage() { 
	return preferredLanguage;
	}
	
	public void setpreferredLanguage(String preferredLanguage){
	this.preferredLanguage = preferredLanguage;
	}
	
	public String getfirstName() {
	return firstName;
	}
	
	public void setfirstName(String firstName){
	this.firstName = firstName;
	}
	
	
	public String getlastName() { 
	return lastName;
	}
	
	
	public void setlastName(String lastName){
	this.lastName = lastName;
	}
	
	public String getaddress1() {
	return address1;
	}
	
	public void setaddress1(String address1){
	this.address1 = address1;
	}
	
	public String getaddress2() { 
	return address2;
	}
	
	public void setaddress2(String address2){
	this.address2 = address2;
	}
	
	public String getcity() { 
	return city;
	}

	public void setcity(String city){
	this.city = city;
	}
	
	public String getstate() { 
	return state;
	}
	
	public void setstate(String state){
	this.state = state;
	}
	
	public String getpostalCode() { 
	return postalCode;
	}
	
	public void setpostalCode(String postalCode){
	this.postalCode = postalCode;
	}
	
	public String getcountry() { 
	return country;
	}
	
	public void setcountry(String country){
	this.country = country;
	}
	
	public String getphone() {
	return phone;
	}
	
	public void setphone(String phone){
	this.phone = phone;
	}
	
	public String getemail() { 
	return email;
	}
	
	public void setemail(String email){
	this.email = email;
	}
	
	public String getfax() { 
	return fax;
	}
	
	public void setfax(String fax){
	this.fax = fax;
	}
	
	public String getmobile() { 
	return mobile;
	}
	
	public void setmobile(String mobile){
	this.mobile = mobile;
	}
	
	public String getapprover() { 
	return approver;
	}
	
	public void setapprover(String approver){
	this.approver = approver;
	}
	
	public String getneedsApproval() { 
	return needsApproval;
	}
	
	public void setneedsApproval(String needsApproval){
	this.needsApproval = needsApproval;
	}
	
	
	public String getwasApproved() { 
	return wasApproved;
	}
	
	public void setwasApproved(String wasApproved){
	this.wasApproved = wasApproved;
	}
	
	public String getwasRejected() { 
	return wasRejected;
	}
	
	public void setwasRejected(String wasRejected){
	this.wasRejected = wasRejected;
	}
	
	
	public String getwasModified() { 
	return wasModified;
	}
	
	public void setwasModified(String wasModified){
	this.wasModified = wasModified;
	}
	
	public String getcorporateUser() { 
	return corporateUser;
	}
	
	public void setcorporateUser(String corporateUser){
	this.corporateUser = corporateUser;
	}
	
	public String getreceiveInventoryMisEmail() { 
	return receiveInventoryMisEmail;
	}
	
	public void setreceiveInventoryMisEmail(String receiveInventoryMisEmail){
	this.receiveInventoryMisEmail = receiveInventoryMisEmail;
	}
	
	public String getonAccount() { 
	return onAccount;
	}
	
	public void setonAccount(String onAccount){
	this.onAccount = onAccount;
	}
	
	public String getcreditCard() { 
	return creditCard;
	}
	
	public void setcreditCard(String creditCard){
	this.creditCard = creditCard;
	}
	
	public String getotherPayment() { 
	return otherPayment;
	}
	
	public void setotherPayment(String otherPayment){
	this.otherPayment = otherPayment;
	}
	
	public String getpoNumRequired() {
	return poNumRequired;
	}
	
	public void setpoNumRequired(String poNumRequired){
	this.poNumRequired = poNumRequired;
	}
	
	public String getshowPrice() { 
	return showPrice;
	}
	
	public void setshowPrice(String showPrice){
	this.showPrice = showPrice;
	}
	
	public String getcontractItemsOnly() { 
	return contractItemsOnly;
	}
	
	public void setcontractItemsOnly(String contractItemsOnly){
	this.contractItemsOnly = contractItemsOnly;
	}
	
	public String getbrowseOnly() { 
	return browseOnly;
	}
	
	public void setbrowseOnly(String browseOnly){
	this.browseOnly = browseOnly;
	}
	
	
	public String getnoReporting() { 
	return noReporting;
	}
	
	public void setnoReporting(String noReporting){
	this.noReporting = noReporting;
	}
	
	public String getorderDetailNotification() { 
	return orderDetailNotification;
	}
	
	public void setorderDetailNotification(String orderDetailNotification){
	this.orderDetailNotification = orderDetailNotification;
	}
	
	public String getshippingNotification() { 
	return shippingNotification;
	}
	
	public void setshippingNotification(String shippingNotification){
	this.shippingNotification = shippingNotification;
	}
	
	public String getworkOrderCompletedNotifi() { 
	return workOrderCompletedNotifi;
	}
	
	public void setworkOrderCompletedNotifi(String workOrderCompletedNotifi){
	this.workOrderCompletedNotifi = workOrderCompletedNotifi;
	}
	
	public String getworkOrderAcceptedNotifi() { 
	return workOrderAcceptedNotifi;
	}
	
	public void setworkOrderAcceptedNotifi(String workOrderAcceptedNotifi){
	this.workOrderAcceptedNotifi = workOrderAcceptedNotifi;
	}
	
	public String getworkOrderRejectedNotifi() { 
	return workOrderRejectedNotifi;
	}
	
	public void setworkOrderRejectedNotifi(String workOrderRejectedNotifi){
	this.workOrderRejectedNotifi = workOrderRejectedNotifi;
	}
	
	public String getgroupId() { 
	return groupId;
	}
	
	public void setgroupId(String groupId){
	this.groupId = groupId;
	}
	
    
}
