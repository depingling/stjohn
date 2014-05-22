package com.cleanwise.service.api.session;

import java.rmi.RemoteException;
import java.util.List;

import com.cleanwise.service.api.dto.AccountDto;
import com.cleanwise.service.api.dto.ManufacturerDto;
import com.cleanwise.service.api.dto.SiteDto;
import com.cleanwise.service.api.dto.UserDto;
import com.cleanwise.service.api.util.SearchCrit;

public interface SOAP extends javax.ejb.EJBObject {

	public List<SiteDto> getSites(SearchCrit searchCrit, int storeId) throws RemoteException;

	public List<AccountDto> getAccounts(SearchCrit searchCrit, int storeId) throws RemoteException;

	public List<ManufacturerDto> getManufacturers(SearchCrit searchCrit, int storeId) throws RemoteException;

	public List<UserDto>getUsers(SearchCrit searchCrit, int storeId) throws RemoteException;

	public int save(Object object, int assocId) throws RemoteException;
	
}
