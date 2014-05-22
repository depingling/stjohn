package com.cleanwise.service.api.session;

/**
 * Title:        AddressValidator
 * Description:  Remote Interface for AddressValidator Stateless Session Bean
 * Purpose:      Provides access to the table-level AddressValidator methods
 * Copyright:    Copyright (c) 2003
 * Company:      CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;
import com.cleanwise.service.api.value.*;

public interface AddressValidator extends javax.ejb.EJBObject
{
        /**
        *validates the supplied address.  Address are guaranteed to have valid city,
        *stateProvinceCode, and zipOrPostalCode data.  No guaruntee is made about street address
        *@param AddressData to be validated
        *@returns true if the address is valid, and false if it is not or anything is null
        *@throws RemoteException if anything goes wrong
        */
        public boolean validateAddress(AddressData addr) throws RemoteException;

        /**
        *validates the supplied address.  Address are guaranteed to have valid city,
        *stateProvinceCode, and zipOrPostalCode data.  No guaruntee is made about street address
        *@param OrderAddressData to be validated
        *@returns true if the address is valid, and false if it is not or anything is null
        *@throws RemoteException if anything goes wrong
        */
	public boolean validateAddress(OrderAddressData addr) throws RemoteException;

        /**
        *Will repopulate the address validation records with the supplied data.  This method completely
        *replaces the existing data, and is ment to be used with a bulk loader.
        *@param CityPostalCodeDataVector to populate
        *@throws RemoteException if anything goes wrong
        */
	public void repopulateCityPostalCodeData(CityPostalCodeDataVector data) throws RemoteException;
}
