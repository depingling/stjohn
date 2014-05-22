package com.cleanwise.service.api.session;

import com.cleanwise.service.api.value.UiPageView;
import com.cleanwise.service.api.value.UiView;
import com.cleanwise.service.api.value.UiData;
import com.cleanwise.service.api.util.DataNotFoundException;

import java.rmi.RemoteException;


public interface Ui extends javax.ejb.EJBObject {

    public UiPageView getUiPage(int pUiId, String pType, String pPage) throws RemoteException, DataNotFoundException;

    public UiPageView updateUiPage(UiPageView pUiPage,String pUser) throws RemoteException;

    public UiView getUi(int pUiId) throws RemoteException, DataNotFoundException;

    public UiView getUiForGroup(int pGroupId) throws RemoteException, DataNotFoundException;

    public UiView saveUi(UiView pUi, String pUser)throws RemoteException;

    public UiData getUiDataForGroup(int pGroupId) throws RemoteException, DataNotFoundException;
}
