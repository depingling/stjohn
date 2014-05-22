package com.cleanwise.service.api.session;

import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.dao.*;

import javax.ejb.CreateException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

public class UiBean extends ApplicationServicesAPI {

    /**
     * Describe <code>ejbCreate</code> method here.
     *
     * @throws javax.ejb.CreateException if an error occurs
     * @throws java.rmi.RemoteException  if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {

    }


    public UiPageView getUiPage(int pUiId, String pTypeCode, String pPageCode) throws RemoteException, DataNotFoundException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getUiPage(conn, pUiId, pTypeCode, pPageCode);
        } catch (DataNotFoundException e) {
            throw new DataNotFoundException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }


    public UiView getUiForGroup(int pGroupId) throws RemoteException, DataNotFoundException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getUiForGroup(conn, pGroupId);
        } catch (DataNotFoundException e) {
            throw new DataNotFoundException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private UiView getUiForGroup(Connection pCon, int pGroupId) throws SQLException, DataNotFoundException {

        UiData uiData = getUiDataForGroup(pCon, pGroupId);

        return getUi(pCon, uiData.getUiId());
    }

    public UiView getUi(int pUiId) throws RemoteException, DataNotFoundException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getUi(conn, pUiId);
        } catch (DataNotFoundException e) {
            throw new DataNotFoundException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

     public UiData getUiDataForGroup(int pGroupId) throws RemoteException, DataNotFoundException{
             Connection conn = null;
        try {
            conn = getConnection();
            return getUiDataForGroup(conn, pGroupId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
     }

    private UiData getUiDataForGroup(Connection pCon, int pGroupId) throws DataNotFoundException, SQLException {

        DBCriteria dbc = new DBCriteria();
        dbc.addJoinCondition(UiDataAccess.UI_ID, UiAssocDataAccess.CLW_UI_ASSOC, UiAssocDataAccess.UI_ID);
        dbc.addEqualTo(UiDataAccess.STATUS_CD, RefCodeNames.UI_STATUS_CD.ACTIVE);
        dbc.addJoinTableEqualTo(UiAssocDataAccess.CLW_UI_ASSOC, UiAssocDataAccess.GROUP_ID, pGroupId);

        UiDataVector uiVector = UiDataAccess.select(pCon, dbc);
        if (uiVector.isEmpty()) {
            throw new DataNotFoundException("Ui for user group not found.pGroupId" + pGroupId);
        }

        if (uiVector.size() > 1) {
            throw new DataNotFoundException("User group associated with multiplle Ui.pGroupId:" + pGroupId + ".Ui(s):" + Utility.toIdVector(uiVector));
        }

        return (UiData) uiVector.get(0);

    }

    private UiView getUi(Connection pCon, int pUiId) throws DataNotFoundException, SQLException {

        UiData uiData = UiDataAccess.select(pCon, pUiId);

        DBCriteria dbc;

        UiPageDataVector uiPages;
        UiAssocDataVector uiAssociations;
        UiControlDataVector uiControls = null;
        UiControlElementDataVector uiControlElements = null;

        dbc = new DBCriteria();
        dbc.addEqualTo(UiAssocDataAccess.UI_ID, uiData.getUiId());
        uiAssociations = UiAssocDataAccess.select(pCon, dbc);

        dbc = new DBCriteria();
        dbc.addEqualTo(UiPageDataAccess.UI_ID, uiData.getUiId());
        uiPages = UiPageDataAccess.select(pCon, dbc);

        if (!uiPages.isEmpty()) {

            IdVector uiPagesIds = Utility.toIdVector(uiPages);

            dbc = new DBCriteria();
            dbc.addOneOf(UiControlDataAccess.UI_PAGE_ID, uiPagesIds);
            uiControls = UiControlDataAccess.select(pCon, dbc);

            if (!uiControls.isEmpty()) {

                IdVector controlIds = Utility.toIdVector(uiControls);

                dbc = new DBCriteria();
                dbc.addOneOf(UiControlElementDataAccess.UI_CONTROL_ID, controlIds);
                uiControlElements = UiControlElementDataAccess.select(pCon, dbc);

            }

        }

        return createUiView(uiData, uiAssociations, uiPages, uiControls, uiControlElements);

    }

    private UiView createUiView(UiData pUiData, UiAssocDataVector pUiAssociations, UiPageDataVector pUiPages, UiControlDataVector pUiControls, UiControlElementDataVector pUiControlElements) {

        HashMap<Integer, UiControlDataVector> uiPageControlsMap = new HashMap<Integer, UiControlDataVector>();
        if (pUiControls != null) {
            for (Object oUiControls : pUiControls) {
                UiControlData uiControl = (UiControlData) oUiControls;
                UiControlDataVector pageControls = uiPageControlsMap.get(uiControl.getUiPageId());
                if (pageControls == null) {
                    pageControls = new UiControlDataVector();
                    uiPageControlsMap.put(uiControl.getUiPageId(), pageControls);
                }
                pageControls.add(uiControl);
            }
        }

        HashMap<Integer, UiControlElementDataVector> uiControlElementsMap = new HashMap<Integer, UiControlElementDataVector>();
        if (pUiControlElements != null) {
            for (Object oUiControlElement : pUiControlElements) {
                UiControlElementData uiControlElement = (UiControlElementData) oUiControlElement;
                UiControlElementDataVector controlElements = uiControlElementsMap.get(uiControlElement.getUiControlId());
                if (controlElements == null) {
                    controlElements = new UiControlElementDataVector();
                    uiControlElementsMap.put(uiControlElement.getUiControlId(), controlElements);
                }
                controlElements.add(uiControlElement);
            }
        }

        UiPageViewVector uiPageViewVector = new UiPageViewVector();
        if (pUiPages != null) {
            for (Object oUiPages : pUiPages) {

                UiPageView uiPageView = UiPageView.createValue();
                UiPageData uiPage = (UiPageData) oUiPages;

                UiControlViewVector controlViewVector = new UiControlViewVector();
                UiControlDataVector pageControls = uiPageControlsMap.get(uiPage.getUiPageId());
                if (pageControls != null) {
                    for (Object oPageControls : pageControls) {
                        UiControlView controlView = UiControlView.createValue();
                        UiControlData pageControl = (UiControlData) oPageControls;
                        UiControlElementDataVector controlElements = uiControlElementsMap.get(pageControl.getUiControlId());
                        if (controlElements == null) {
                            controlElements = new UiControlElementDataVector();
                        }
                        controlView.setUiControlElements(controlElements);
                        controlView.setUiControlData(pageControl);

                        controlViewVector.add(controlView);
                    }
                }

                uiPageView.setUiControls(controlViewVector);
                uiPageView.setUiPage(uiPage);
                uiPageView.setUiData(pUiData);

                uiPageViewVector.add(uiPageView);
            }
        }

        UiView uiView = new UiView();
        uiView.setUiData(pUiData);
        uiView.setUiPages(uiPageViewVector);
        uiView.setUiAssociations(pUiAssociations);

        return uiView;

    }


    private UiPageView getUiPage(Connection pCon, int pUiId, String pTypeCode, String pPageCode) throws Exception {

        UiPageView pageView = UiPageView.createValue();

        UiData uiData = UiDataAccess.select(pCon, pUiId);

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(UiPageDataAccess.UI_ID, uiData.getUiId());
        dbc.addEqualTo(UiPageDataAccess.TYPE_CD, pTypeCode);
        dbc.addEqualTo(UiPageDataAccess.SHORT_DESC, pPageCode);

        UiPageDataVector uiPages = UiPageDataAccess.select(pCon, dbc);
        if (uiPages.isEmpty()) {
            throw new DataNotFoundException("Page not found.UI_ID: " + uiData.getUiId() + ", TYPE: " + pTypeCode + ", PAGE: " + pPageCode);
        }
        if (uiPages.size() > 1) {
            throw new Exception("Multiple pages found.UI_ID: " + uiData.getUiId() + ", TYPE: " + pTypeCode + ", PAGE:" + pPageCode);
        }


        UiPageData uiPage = (UiPageData) uiPages.get(0);

        dbc = new DBCriteria();
        dbc.addEqualTo(UiControlDataAccess.UI_PAGE_ID, uiPage.getUiPageId());
        UiControlDataVector controls = UiControlDataAccess.select(pCon, dbc);
        UiControlViewVector controlVV = new UiControlViewVector();
        for (Object oControl : controls) {

            UiControlData control = (UiControlData) oControl;

            dbc = new DBCriteria();
            dbc.addEqualTo(UiControlElementDataAccess.UI_CONTROL_ID, control.getUiControlId());
            UiControlElementDataVector controlElements = UiControlElementDataAccess.select(pCon, dbc);

            UiControlView controlV = new UiControlView();
            controlV.setUiControlData(control);
            controlV.setUiControlElements(controlElements);

            controlVV.add(controlV);
        }

        pageView.setUiPage(uiPage);
        pageView.setUiControls(controlVV);
        pageView.setUiData(uiData);

        return pageView;

    }


    public UiPageView updateUiPage(UiPageView uiPage, String pUser) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return updateUiPage(conn, uiPage, pUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private UiPageView updateUiPage(Connection pConn, UiPageView pUiPage, String pUser) throws SQLException {

        if (pUiPage.getUiData().getUiId() > 0) {

            UiPageData uiPageData = pUiPage.getUiPage();
            logInfo("saveUiPage => pUiPage:" + uiPageData + ", isDirty: " + uiPageData.isDirty());
            if (uiPageData.isDirty()) {
                uiPageData.setUiId(pUiPage.getUiData().getUiId());
                uiPageData = updateUiPageData(pConn, uiPageData, pUser);
            }

            UiControlViewVector uiControls = pUiPage.getUiControls();
            logInfo("saveUiPage => uiControls size:" + uiControls.size());
            for (Object oUiControl : uiControls) {
                UiControlView uiControl = (UiControlView) oUiControl;
                UiControlData uiControlData = uiControl.getUiControlData();
                logInfo("saveUiPage => uiControlData:" + uiControlData + ", isDirty: " + uiControlData.isDirty());
                if (uiControlData.isDirty()) {
                   uiControlData.setUiPageId(uiPageData.getUiPageId());
                   uiControlData = updateUiControl(pConn, uiControlData, pUser);
                }

                UiControlElementDataVector uiControlElements = uiControl.getUiControlElements();
                logInfo("saveUiPage => uiControlElements size:" + uiControlElements.size());
                for (Object oUiControlElements : uiControlElements) {
                    UiControlElementData uiControlElement = (UiControlElementData) oUiControlElements;
                    logInfo("saveUiPage => uiControlElement:" + uiControlElement + ", isDirty: " + uiControlElement.isDirty());
                    if (uiControlElement.isDirty()) {
                        uiControlElement.setUiControlId(uiControlData.getUiControlId());
                        uiControlElement = updateUiControlElement(pConn, uiControlElement, pUser);
                    }
                }
            }
        }

        return pUiPage;

    }


    private UiData updateUiData(Connection pConn, UiData pUiData) throws SQLException {
        if (pUiData.getUiId() > 0) {
            UiDataAccess.update(pConn, pUiData);
        } else {
            pUiData = UiDataAccess.insert(pConn, pUiData);
        }
        return pUiData;
    }

    private UiPageData updateUiPageData(Connection pConn, UiPageData pUiPageData, String pUser) throws SQLException {
        if (pUiPageData.getUiPageId() > 0) {
            pUiPageData.setModBy(pUser);
            UiPageDataAccess.update(pConn, pUiPageData);
        } else {
            pUiPageData.setAddBy(pUser);
            pUiPageData.setModBy(pUser);
            pUiPageData = UiPageDataAccess.insert(pConn, pUiPageData);
        }
        return pUiPageData;
    }

    private UiControlData updateUiControl(Connection pConn, UiControlData pControl, String pUser) throws SQLException {

        if (!Utility.isSet(pControl.getStatusCd())) {
            pControl.setStatusCd(RefCodeNames.UI_CONTROL_ELEMENT_STATUS_CD.ACTIVE);
        }

        if (pControl.getUiControlId() > 0) {
            pControl.setModBy(pUser);
            UiControlDataAccess.update(pConn, pControl);
        } else {

            pControl.setAddBy(pUser);
            pControl.setModBy(pUser);
            pControl = UiControlDataAccess.insert(pConn, pControl);
        }

        return pControl;
    }

    private UiControlElementData updateUiControlElement(Connection pConn, UiControlElementData pUiControlElement, String pUser) throws SQLException {
        if (pUiControlElement.getUiControlElementId() > 0) {
            pUiControlElement.setModBy(pUser);
            UiControlElementDataAccess.update(pConn, pUiControlElement);
        } else {
            pUiControlElement.setAddBy(pUser);
            pUiControlElement.setModBy(pUser);
            pUiControlElement = UiControlElementDataAccess.insert(pConn, pUiControlElement);
        }
        return pUiControlElement;
    }


    public UiView saveUi(UiView pUi, String pUser) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return saveUi(conn, pUi, pUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private UiView saveUi(Connection pCon, UiView pUi, String pUser) throws SQLException {

        UiData uiData = pUi.getUiData();
        if (uiData.isDirty()) {
            uiData = updateUiData(pCon, uiData);
        }

        UiAssocDataVector uiAsocciations = pUi.getUiAssociations();
        uiAsocciations = updateUiAssociations(pCon, uiData, uiAsocciations, pUser);

        UiPageViewVector pages = pUi.getUiPages();
        pages = updateUiPages(pCon, uiData, pages, pUser);

        pUi.setUiData(uiData);
        pUi.setUiAssociations(uiAsocciations);
        pUi.setUiPages(pages);

        return pUi;

    }

    private UiPageViewVector updateUiPages(Connection pCon, UiData pUiData, UiPageViewVector pUiPages, String pUser) throws SQLException {
        UiPageViewVector uiPageViewVector = new UiPageViewVector();
        if (pUiPages != null) {
            for (Object oUiPage : pUiPages) {
                ((UiPageView) oUiPage).setUiData(pUiData);
                uiPageViewVector.add(updateUiPage(pCon, (UiPageView) oUiPage, pUser));
            }
        }
        return uiPageViewVector;
    }

    private UiAssocDataVector updateUiAssociations(Connection pCon,
                                                   UiData pUiData,
                                                   UiAssocDataVector pUiNewAsocciations,
                                                   String pUser) throws SQLException {

        DBCriteria dbc;

        dbc = new DBCriteria();
        dbc.addEqualTo(UiAssocDataAccess.UI_ID, pUiData.getUiId());
        UiAssocDataVector currentAssociations = UiAssocDataAccess.select(pCon, dbc);

        UiAssocDataVector assocToAdd = new UiAssocDataVector();
        UiAssocDataVector assocToRemove = new UiAssocDataVector();
        UiAssocDataVector assocToUpdate = new UiAssocDataVector();

        if (pUiNewAsocciations.size() == 0 && currentAssociations.size() > 0) {
            assocToRemove.addAll(currentAssociations);
        } else if (pUiNewAsocciations.size() > 0 && currentAssociations.size() == 0) {
            assocToAdd.addAll(pUiNewAsocciations);

        } else {

            Iterator it = currentAssociations.iterator();
            while (it.hasNext()) {
                UiAssocData currUiAssoc = (UiAssocData) it.next();
                Iterator it1 = pUiNewAsocciations.iterator();
                while (it1.hasNext()) {
                    UiAssocData newUiAssoc = (UiAssocData) it1.next();
                    if (newUiAssoc.getUiAssocId() == currUiAssoc.getUiAssocId()) {
                        assocToUpdate.add(newUiAssoc);
                        it1.remove();
                        it.remove();
                        break;
                    }
                }
            }

            assocToAdd.addAll(pUiNewAsocciations);
            assocToRemove.addAll(currentAssociations);
        }

        for (Object oUiAsoc : assocToAdd) {

            UiAssocData assoc = (UiAssocData) oUiAsoc;
            assoc.setUiId(pUiData.getUiId());
            assoc.setAddBy(pUser);
            assoc.setModBy(pUser);
            UiAssocDataAccess.insert(pCon, assoc);
        }

        for (Object oUiAsoc : assocToRemove) {
            UiAssocData assoc = (UiAssocData) oUiAsoc;
            assoc.setModBy(pUser);
            UiAssocDataAccess.remove(pCon, assoc.getUiAssocId());
        }

        for (Object oUiAsoc : assocToUpdate) {
            UiAssocData assoc = (UiAssocData) oUiAsoc;
            assoc.setModBy(pUser);
            UiAssocDataAccess.update(pCon, assoc);
        }

        dbc = new DBCriteria();
        dbc.addEqualTo(UiAssocDataAccess.UI_ID, pUiData.getUiId());
        return UiAssocDataAccess.select(pCon, dbc);

    }


}
