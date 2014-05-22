package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.Asset;
import com.cleanwise.service.api.session.Catalog;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.StoreItemMgrForm;
import com.cleanwise.view.forms.StoreServiceMgrForm;
import com.cleanwise.view.forms.StoreAssetConfigForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SelectableObjects;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Comparator;
import java.util.List;
import java.util.Collection;

/**
 * Title:        StoreServiceMgrLogic
 * Description:  Logic of the services management
 * Purpose:      services management
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         08.01.2006
 * Time:         09:33:17
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class StoreServiceMgrLogic {

        static final Comparator ASSET_ID_COMPARE = new Comparator() {
	    public int compare(Object o1, Object o2)
	    {
		int id1 = ((AssetData)o1).getAssetId();
		int id2 = ((AssetData)o2).getAssetId();
		return id2-id1;
	    }
	};

    public static ActionErrors init(HttpServletRequest request, StoreServiceMgrForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        pForm = new StoreServiceMgrForm();
        HttpSession session = request.getSession();
        StoreItemMgrForm mForm = (StoreItemMgrForm) session.getAttribute("STORE_ADMIN_ITEM_FORM");
        if (mForm == null) {
            ae.add("error",
                    new ActionError("error.simpleGenericError", "Main Form is not found in the session scope"));
            return ae;
        }
        ae=checkRequest(request,pForm);
        if(ae.size()>0)  return ae;

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
       /* Asset assetEJB = factory.getAssetAPI();
        AssetSearchCriteria criteria = new AssetSearchCriteria();
        criteria.setStoreIds(appUser.getUserStoreAsIdVector());
        criteria.setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.CATEGORY);
        AssetDataVector parentAssets = assetEJB.getAssetDataByCriteria(criteria);
        pForm.setParentAssets(parentAssets);   */
        pForm.setStoreCatalogId(mForm.getStoreCatalogId());
        ItemDataVector services = catalogInfEjb.getStoreServices(pForm.getStoreCatalogId());
        pForm.setServicesCollection(services);
        session.setAttribute("STORE_SERVICE_MGR_FORM", pForm);
        return ae;
    }

    public static ActionErrors getServiceConfiguration(HttpServletRequest request, StoreServiceMgrForm pForm) throws Exception
    {
        ActionErrors ae=new ActionErrors();
        HttpSession session=request.getSession();
        ae=checkRequest(request,pForm);
        if(ae.size()>0)  return ae;

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();
        Asset assetEJB=factory.getAssetAPI();
        int storeId = storeD.getBusEntity().getBusEntityId();
        AssetDataVector allAssets = new AssetDataVector();
        AssetDataVector configAssets = new AssetDataVector();
        AssetSearchCriteria criteria=new AssetSearchCriteria();
        criteria.setStoreIds(appUser.getUserStoreAsIdVector());
        IdVector serviceIds=new IdVector();
        serviceIds.add(new Integer(pForm.getCurrentServiceId()));
        criteria.setServiceIds(serviceIds);
        configAssets = assetEJB.getAssetDataByCriteria(criteria);

        if(pForm.getShowConfiguredOnlyFl())
        {
         allAssets= (AssetDataVector) configAssets.clone();
        }
        else{
        criteria=new AssetSearchCriteria();
        criteria.setStoreIds(appUser.getUserStoreAsIdVector());
         allAssets = assetEJB.getAssetDataByCriteria(criteria);

        }

        SelectableObjects selectedObjects = new SelectableObjects(configAssets, allAssets, ASSET_ID_COMPARE);
        pForm.setConfigResults(selectedObjects);

        return ae;
    }

   public static ActionErrors updateService(HttpServletRequest request, StoreServiceMgrForm pForm) throws Exception
   {
      ActionErrors ae=new ActionErrors();
      HttpSession session=request.getSession();

      ae=checkRequest(request,pForm);
      if(ae.size()>0)  return ae;

       APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
       CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
       ItemData service = pForm.getService();
       StoreData storeD = appUser.getUserStore();

       if(service!=null)
       {
       if(service.getShortDesc().trim().length()==0)
       {
          ae.add("Service name", new ActionError("variable.empty.error", "Service Name"));
       }
       if(ae.size()>0)  return ae;
       }
       try {
           Catalog catalogEJB=factory.getCatalogAPI();
           catalogEJB.updateServiceData(storeD.getStoreId(),pForm.getStoreCatalogId(),
                                        pForm.getService(),appUser.getUserName());
       } catch (RemoteException e) {
           e.printStackTrace();
           ae.add("error",new ActionError("error.simpleGenericError", e.getMessage()));
           return ae;
       }
    return ae;
   }

    public static ActionErrors createService(HttpServletRequest request, StoreServiceMgrForm pForm) throws Exception
    {
        ActionErrors ae=new ActionErrors();

        if(pForm == null)
        {
            return ae;
        }
        ItemData service = ItemData.createValue();
        service.setEffDate(new java.util.Date(System.currentTimeMillis()));
        service.setItemTypeCd(RefCodeNames.ITEM_TYPE_CD.SERVICE);
        service.setItemStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
        pForm.setService(service);
        pForm.setEditServiceFl(true);
        return ae;
    }

   public static ActionErrors updateServiceConfiguration(HttpServletRequest request, StoreServiceMgrForm pForm) throws Exception {
  ActionErrors ae = new ActionErrors();
        ae = checkRequest(request, pForm);
        if (ae.size() > 0) return ae;
        try {

            SelectableObjects configResults = pForm.getConfigResults();
            HttpSession session = request.getSession();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            Collection assocDelDV=  configResults.getDeselected();
            Iterator iterNew = configResults.getNewlySelected().iterator();
            AssetAssocDataVector newAssoc = new AssetAssocDataVector();
            while (iterNew.hasNext()) {
                AssetAssocData assetAssocDataForService = AssetAssocData.createValue();
                AssetData assetData = (AssetData) iterNew.next();
                String assetAssocCd;
                assetAssocCd = RefCodeNames.ASSET_ASSOC_CD.ASSET_SERVICE;
                assetAssocDataForService.setAssetId(assetData.getAssetId());
                assetAssocDataForService.setItemId(pForm.getCurrentServiceId());
                assetAssocDataForService.setAssetAssocCd(assetAssocCd);
                assetAssocDataForService.setAssetAssocStatusCd(RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE);
                newAssoc.add(assetAssocDataForService);
            }
            APIAccess factory = new APIAccess();
            Asset assetEJB = factory.getAssetAPI();
            if (newAssoc != null && newAssoc.size() > 0) {
                assetEJB.updateAssetAssocDataVector(-1,newAssoc,appUser.getUser().getUserName());
            }
            if (assocDelDV != null && assocDelDV.size() > 0) {
                IdVector assetDelIds=getAssetIdsFromCollection(assocDelDV);
                assetEJB.removeAssetAssoc(assetDelIds,pForm.getCurrentServiceId(),RefCodeNames.ASSET_ASSOC_CD.ASSET_SERVICE);
            }
            ae.add(getServiceConfiguration(request, pForm));

        } catch (NamingException e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            return ae;
        } catch (APIServiceAccessException e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            return ae;
        } catch (RemoteException e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            return ae;
        } catch (Exception e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
            return ae;
        }
        return ae;
   }

    private static IdVector getAssetIdsFromCollection(Collection assetCollection) {
        IdVector ids = new IdVector();
        Iterator iter = assetCollection.iterator();
        while (iter.hasNext()) {
            AssetData asset = ((AssetData) iter.next());
            ids.add(new Integer(asset.getAssetId()));
        }
        return ids;
    }

    public static ActionErrors checkRequest(HttpServletRequest request, StoreServiceMgrForm pForm) throws Exception
    {
       ActionErrors ae=new ActionErrors();
       HttpSession session=request.getSession();
       APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
       CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
       if (appUser == null) {
            ae.add("error",
                    new ActionError("error.simpleGenericError", "No user info"));
            return ae;
        }
        StoreData storeD = appUser.getUserStore();
        if (storeD == null) {
            ae.add("error",
                    new ActionError("error.simpleGenericError", "No store info"));
            return ae;
        }
        if (factory == null) {
            ae.add("error",
                    new ActionError("error.simpleGenericError", "No Ejb access"));
            return ae;
       }
        return ae;
    }

    public static ActionErrors editService(HttpServletRequest request, StoreServiceMgrForm pForm) throws Exception {
       ActionErrors ae=new ActionErrors();

        if(pForm == null)
        {
            return ae;
        }
        ItemData service = getServiceFromCollectionById(pForm.getServicesCollection(), pForm.getCurrentServiceId());
        if(service==null)
        {
            ae.add("error",new ActionError("error.simpleGenericError","Service for the currently service id :"
                                                                        +pForm.getCurrentServiceId()+" not found"));
            return ae;
        }
        pForm.setService(service);
        pForm.setEditServiceFl(true);
        return ae;
    }

    private static ItemData getServiceFromCollectionById(ItemDataVector servicesCollection, int serviceId) {
        if (servicesCollection != null) {
            Iterator iter = servicesCollection.iterator();
            while (iter.hasNext()) {
                ItemData service = ((ItemData) iter.next());
                if (service.getItemId() == serviceId) {
                    return service;
                }
            }
        }
        return null;
    }
}

