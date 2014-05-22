package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;

/**
 * Each distributor may have 1 or more branches.  Each branch
 * may have its own list of contacts.
 */
public class DistBranchData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -899234911945167217L;

    public DistBranchData(AddressData pAddrData) {
	mBranchAddress = pAddrData;
    }

    private AddressData mBranchAddress;
    private ContactViewVector mContacts;

    public AddressData getBranchAddress() {
	return mBranchAddress;
    }
    public void setBranchAddress(AddressData v) {
	mBranchAddress = v;
    }
    public ContactViewVector getContactsCollection() {
	return mContacts;
    }
    public void setContactsCollection( ContactViewVector v) {
	mContacts = v;
    }
}
