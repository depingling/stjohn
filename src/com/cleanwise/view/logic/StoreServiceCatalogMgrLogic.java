package com.cleanwise.view.logic;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.Catalog;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.ItemInformation;
import com.cleanwise.service.api.session.Manufacturer;
import com.cleanwise.service.api.session.OrderGuide;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.CatalogAssocData;
import com.cleanwise.service.api.value.CatalogAssocDataVector;
import com.cleanwise.service.api.value.CatalogCategoryData;
import com.cleanwise.service.api.value.CatalogCategoryDataVector;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.DistItemView;
import com.cleanwise.service.api.value.DistItemViewVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.DistributorDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemCatalogAggrView;
import com.cleanwise.service.api.value.ItemCatalogAggrViewVector;
import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.ServiceData;
import com.cleanwise.service.api.value.ServiceView;
import com.cleanwise.service.api.value.ServiceViewVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UploadSkuData;
import com.cleanwise.service.api.value.UploadSkuView;
import com.cleanwise.service.api.value.UploadSkuViewVector;
import com.cleanwise.view.forms.LocateUploadItemForm;
import com.cleanwise.view.forms.StoreServiceCatalogMgrForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionAttributes;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.StringUtils;

/**
 * Title:        StoreServiceMgrLinkLogic
 * Description:  Service manager of the Link
 * Purpose:      Link configuration
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         12.01.2007
 * Time:         22:59:26
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class StoreServiceCatalogMgrLogic {
	  private static final Logger log = Logger.getLogger(StoreServiceCatalogMgrLogic.class);

    public static ActionErrors init(HttpServletRequest request, ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        HttpSession session = request.getSession();
        String theUrl = SessionTool.getActualRequestedURI(request);
        String prevPage =
                (String) session.getAttribute(SessionAttributes.ITEM_MANAGER_PAGE.CURRENT_PAGE);
        String newPage = "catalog-service";
        if (theUrl.indexOf("catalog-service") >= 0) {
            newPage = "catalog-service";
        } else if (theUrl.indexOf("service-catalog") >= 0) {
            newPage = "service-catalog";
        }
        if (!newPage.equals(prevPage)) {
            clearFilters(request, pForm);
            session.setAttribute(SessionAttributes.ITEM_MANAGER_PAGE.CURRENT_PAGE, newPage);
        }

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CatalogInformation catInfEjb = factory.getCatalogInformationAPI();
        Manufacturer manufEjb = factory.getManufacturerAPI();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();
        int storeId = storeD.getBusEntity().getBusEntityId();
        int storeCatalogId = catInfEjb.getStoreCatalogId(storeId);
        CatalogCategoryDataVector storeCategories = catInfEjb.getStoreCatalogCategories(storeCatalogId);
        storeCategories = sortCategories(storeCategories);
        pForm.setStoreCategories(storeCategories);
        return ae;
    }

    public static ActionErrors setItemFilter(HttpServletRequest request, ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm sForm = (StoreServiceCatalogMgrForm) form;
        ServiceViewVector serviceViewVV = sForm.getServiceFilter();
        ServiceView serviceVw = null;
        if (serviceViewVV == null || serviceViewVV.size() == 0) {
            return ae;
        }
        HttpSession session = request.getSession();
        String page =
                (String) session.getAttribute(SessionAttributes.ITEM_MANAGER_PAGE.CURRENT_PAGE);
        if (page == null || page.equals("service-catalog")) {
            serviceVw = (ServiceView) serviceViewVV.get(0);
            ae = loadItem(request, form, serviceVw.getServiceId());
            if (ae.size() > 0) {
                return ae;
            }
            ae = loadAggrData(request, form);
        } else if (page.equals("catalog-service")) {
            if (sForm.getCatalogFilter() == null ||
                    sForm.getCatalogFilter().size() == 0) {
                return ae;
            }
            ae = loadCatalogData(request, form);
            return ae;

        }
        return ae;
    }


    public static ActionErrors loadItem(HttpServletRequest request, ActionForm form, int itemId) throws Exception {

        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        CatalogInformation catInfEjb = factory.getCatalogInformationAPI();

        ServiceData currManagingService = null;
        try {
            currManagingService = catInfEjb.getServiceData(itemId);
        } catch (RemoteException e) {
            String errorMess = e.getMessage();
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        }
        pForm.setCurrManagingService(currManagingService);
        pForm.setNeedToSaveFl(false);

        return ae;
    }


    public static ActionErrors loadAggrData(HttpServletRequest request,
                                            ActionForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        //saveSelectBox(request,form);
        HttpSession session = request.getSession();
        CatalogDataVector catalogDV = pForm.getCatalogFilter();
        DistributorDataVector distDV = pForm.getDistFilter();
        AccountDataVector accountDV = pForm.getAccountFilter();
        ServiceData serviceData = pForm.getCurrManagingService();
        if (serviceData == null ||
                ((catalogDV == null || catalogDV.size() == 0) &&
                        (distDV == null || distDV.size() == 0) &&
                        (accountDV == null || accountDV.size() == 0))
                ) {
            pForm.setItemAggrVector(null);
            pForm.setNeedToSaveFl(false);
            clearInputFields(request, form);
            pForm.setCategories(new ItemDataVector());
            return ae;
        }

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CatalogInformation catInfEjb = factory.getCatalogInformationAPI();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();
        int storeId = storeD.getBusEntity().getBusEntityId();
        int storeCatId = catInfEjb.getStoreCatalogId(storeId);
        IdVector distIdV = new IdVector();
        if (distDV != null) {
            for (Iterator iter = distDV.iterator(); iter.hasNext();) {
                DistributorData dD = (DistributorData) iter.next();
                distIdV.add(new Integer(dD.getBusEntity().getBusEntityId()));
            }
        }

        IdVector accountIdV = new IdVector();
        if (accountDV != null) {
            for (Iterator iter = accountDV.iterator(); iter.hasNext();) {
                AccountData aD = (AccountData) iter.next();
                accountIdV.add(new Integer(aD.getBusEntity().getBusEntityId()));
            }
        }

        IdVector catIdV = new IdVector();
        if (catalogDV != null) {
            for (Iterator iter = catalogDV.iterator(); iter.hasNext();) {
                CatalogData cD = (CatalogData) iter.next();
                catIdV.add(new Integer(cD.getCatalogId()));
            }
        }

        ItemCatalogAggrViewVector itemCatAggrVwV =
                catInfEjb.getItemCatalogMgrSet(storeCatId, serviceData.getItemData().getItemId(),
                        distIdV, catIdV, accountIdV);
        IdVector catalogIds = Utility.toIdVector(pForm.getCatalogFilter());
        Iterator iter2 = itemCatAggrVwV.iterator();
        while (iter2.hasNext()) {
            ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter2.next();
            if (icaVw.getCatalogStatus().equals(RefCodeNames.CATALOG_STATUS_CD.INACTIVE)
                    && !catalogIds.contains(new Integer(icaVw.getCatalogId()))) {
                iter2.remove();
            }
        }
        if(pForm.getControlMultipleCatalogFl())
        {    //removes multiple record from collection .
            setMultipleFilterByCatalog(itemCatAggrVwV);
            clearOrderGuideOfItemCatalogAggrCollection(itemCatAggrVwV);
        }
        String sortField = pForm.getLastSortField();
        if (sortField == null) pForm.setLastSortField("CatalogId");
        pForm.setItemAggrVector(itemCatAggrVwV);
        pForm.setNeedToSaveFl(false);
        clearInputFields(request, form);
        IdVector catalogIdV = new IdVector();
        for (Iterator iter = itemCatAggrVwV.iterator(); iter.hasNext();) {
            ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
            int catalogId = icaVw.getCatalogId();
            catalogIdV.add(new Integer(catalogId));
        }

        ItemDataVector categoryDV = catInfEjb.getCatalogCategories(storeCatId, catalogIdV);
        pForm.setCategories(categoryDV);

        ae = sort(request, form);
        if (ae.size() > 0) {
            return ae;
        }
        return ae;
    }

    public static ActionErrors clearFilters(HttpServletRequest request, ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        pForm.setCurrManagingService(null);
        pForm.setAccountFilter(null);
        pForm.setCatalogFilter(null);
        pForm.setDistFilter(null);
        pForm.setServiceFilter(null);
        pForm.setDistDummy(null);
        clearInputFields(request, form);
        pForm.setNeedToSaveFl(false);
        pForm.setCategories(new ItemDataVector());
        pForm.setSelectedLines(new String[0]);
        pForm.setLastSortField(null);
        clearCatalogFilter(request);
        return ae;
    }

    public static ActionErrors clearCatalogFilter(HttpServletRequest request) throws
            Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        session.removeAttribute("catalogListSelectedCatItems");
        session.removeAttribute("catalogListSelectedItemContract");
        return ae;
    }

    public static ActionErrors clearInputFields(HttpServletRequest request,
                                                ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        pForm.setCategoryIdDummy("");
        pForm.setCostCenterIdDummy("");
        //pForm.setDistDummy(null);
        pForm.setCostDummy("");
        pForm.setPriceDummy("");
        pForm.setCatalogSkuNumDummy("");
        pForm.setDistSkuNumDummy("");
        pForm.setStoreCategoryId(0);
        return ae;
    }


    public static ActionErrors loadCatalogData(HttpServletRequest request, ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        HttpSession session = request.getSession();

        //Get catalog order guides
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CatalogInformation catInfEjb = factory.getCatalogInformationAPI();
        OrderGuide orderGuideEjb = factory.getOrderGuideAPI();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();
        int storeId = storeD.getBusEntity().getBusEntityId();
        int storeCatId = catInfEjb.getStoreCatalogId(storeId);

        CatalogDataVector catalogDV = pForm.getCatalogFilter();
        if (catalogDV != null && catalogDV.size() > 1) {
            String errorMess = "Only one catalog is allowed ";
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            pForm.setCatalogFilter(null);
            return ae;
        }

        CatalogData catalogD = (CatalogData) catalogDV.get(0);
        int catalogId = catalogD.getCatalogId();

        //Get catalog categories
        CatalogCategoryDataVector categories = catInfEjb.getStoreCatalogCategories(catalogId);
        categories = sortCategories(categories);
        ItemDataVector categItemDV = new ItemDataVector();
        for (Iterator iter = categories.iterator(); iter.hasNext();) {
            CatalogCategoryData cD = (CatalogCategoryData) iter.next();
            categItemDV.add(cD.getItemData());
        }
        pForm.setCategories(categItemDV);


        IdVector distIdV = null;
        DistributorDataVector distDV = pForm.getDistFilter();
        if (distDV != null && distDV.size() > 0) {
            distIdV = new IdVector();
            for (Iterator iter = distDV.iterator(); iter.hasNext();) {
                DistributorData dD = (DistributorData) iter.next();
                distIdV.add(new Integer(dD.getBusEntity().getBusEntityId()));
            }
        }

        IdVector itemIdV = null;
        ServiceViewVector serviceVwV = pForm.getServiceFilter();
        if (serviceVwV != null && serviceVwV.size() > 0) {
            itemIdV = new IdVector();
            for (Iterator iter = serviceVwV.iterator(); iter.hasNext();) {
                ServiceView iVw = (ServiceView) iter.next();
                int itemId = iVw.getServiceId();
                itemIdV.add(new Integer(itemId));
            }
        }

        ItemCatalogAggrViewVector itemCatAggrVwV =
                catInfEjb.getItemCatalogMgrSet(storeCatId, catalogId,-1,
                        itemIdV, null, distIdV, RefCodeNames.ITEM_TYPE_CD.SERVICE);

        clearInputFields(request, form);

        String sortField = pForm.getLastSortField();
        if (sortField == null) pForm.setLastSortField("SkuNum");
        pForm.setItemAggrVector(itemCatAggrVwV);
        pForm.setNeedToSaveFl(false);

        ae = sort(request, form);
        if (ae.size() > 0) {
            return ae;
        }
        return ae;
    }

    private static CatalogCategoryDataVector sortCategories(CatalogCategoryDataVector categories) {

        if (categories == null || categories.size() < 2) {
            return categories;
        }
        Object[] categA = categories.toArray();
        for (int ii = 0; ii < categA.length - 1; ii++) {
            boolean exitFl = true;
            for (int jj = 0; jj < categA.length - ii - 1; jj++) {
                CatalogCategoryData ccD1 = (CatalogCategoryData) categA[jj];
                CatalogCategoryData ccD2 = (CatalogCategoryData) categA[jj + 1];
                String shortDesc1 = ccD1.getCatalogCategoryShortDesc();
                String shortDesc2 = ccD2.getCatalogCategoryShortDesc();
                int comp = shortDesc1.compareToIgnoreCase(shortDesc2);
                if (comp > 0) {
                    exitFl = false;
                    categA[jj] = ccD2;
                    categA[jj + 1] = ccD1;
                }
            }
            if (exitFl) break;
        }
        categories.clear();
        for (int ii = 0; ii < categA.length; ii++) {
            CatalogCategoryData ccD1 = (CatalogCategoryData) categA[ii];
            categories.add(ccD1);
        }
        return categories;
    }


    public static ActionErrors sort(HttpServletRequest request,
                                    ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        String sortField = request.getParameter("sortField");
        if (sortField == null || "BBBBBBB".equals(sortField)) {
            sortField = pForm.getLastSortField();
        } else {
            pForm.setLastSortField(sortField);
        }
        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        boolean sortAscFl = pForm.getAscSortOrderFl();
        if (icaVwV != null) {
            if ("DistIdInp".equalsIgnoreCase(sortField)) {
                icaVwV = sortInt(icaVwV, sortField, sortAscFl);
                pForm.setItemAggrVector(icaVwV);
            } else if ("CostInp".equalsIgnoreCase(sortField)||
                    "PriceInp".equalsIgnoreCase(sortField)
                 ) {
                icaVwV = sortBD(icaVwV, sortField, sortAscFl);
                pForm.setItemAggrVector(icaVwV);
            } else {
                icaVwV.sort(sortField, sortAscFl);
            }
        }
        return ae;
    }

    private static ItemCatalogAggrViewVector
    sortInt(ItemCatalogAggrViewVector icaVwV, String sortField, boolean pAscFl) {
        if (icaVwV == null || icaVwV.size() <= 1) {
            return icaVwV;
        }
        int[] valA = new int[icaVwV.size()];
        Object[] icaVwA = icaVwV.toArray();
        if ("DistIdInp".equalsIgnoreCase(sortField)) {
            for (int ii = 0; ii < icaVwA.length; ii++) {
                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) icaVwA[ii];
                valA[ii] = 0;
                try {
                    valA[ii] = Integer.parseInt(icaVw.getDistIdInp());
                } catch (Exception exc) {
                }
            }
        } else if ("GenManufIdInp".equalsIgnoreCase(sortField)) {
            for (int ii = 0; ii < icaVwA.length; ii++) {
                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) icaVwA[ii];
                valA[ii] = 0;
                try {
                    valA[ii] = Integer.parseInt(icaVw.getGenManufIdInp());
                } catch (Exception exc) {
                }
            }
        }
        //sort
        for (int ii = 0; ii < valA.length - 1; ii++) {
            boolean exitFl = true;
            for (int jj = 0; jj < valA.length - 1 - ii; jj++) {
                if ((valA[jj] > valA[jj + 1] && pAscFl) ||
                        (valA[jj] < valA[jj + 1] && !pAscFl)) {
                    exitFl = false;
                    int wInt = valA[jj + 1];
                    ItemCatalogAggrView wIcaVw = (ItemCatalogAggrView) icaVwA[jj + 1];
                    valA[jj + 1] = valA[jj];
                    icaVwA[jj + 1] = icaVwA[jj];
                    valA[jj] = wInt;
                    icaVwA[jj] = wIcaVw;
                }
            }
            if (exitFl) {
                break;
            }
        }
        icaVwV = new ItemCatalogAggrViewVector();
        for (int ii = 0; ii < icaVwA.length; ii++) {
            icaVwV.add(icaVwA[ii]);
        }
        return icaVwV;
    }

    private static ItemCatalogAggrViewVector
    sortBD(ItemCatalogAggrViewVector icaVwV, String sortField, boolean pAscFl) {
        if (icaVwV == null || icaVwV.size() <= 1) {
            return icaVwV;
        }
        double[] valA = new double[icaVwV.size()];
        Object[] icaVwA = icaVwV.toArray();
        if ("CostInp".equalsIgnoreCase(sortField)) {
            for (int ii = 0; ii < icaVwA.length; ii++) {
                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) icaVwA[ii];
                valA[ii] = 0;
                try {
                    valA[ii] = Double.parseDouble(icaVw.getCostInp());
                } catch (Exception exc) {
                }
            }
        } else if ("PriceInp".equalsIgnoreCase(sortField)) {
            for (int ii = 0; ii < icaVwA.length; ii++) {
                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) icaVwA[ii];
                valA[ii] = 0;
                try {
                    valA[ii] = Double.parseDouble(icaVw.getPriceInp());
                } catch (Exception exc) {
                }
            }
        }
        //sort
        for (int ii = 0; ii < valA.length - 1; ii++) {
            boolean exitFl = true;
            for (int jj = 0; jj < valA.length - 1 - ii; jj++) {
                if ((valA[jj] > valA[jj + 1] && pAscFl) ||
                        (valA[jj] < valA[jj + 1] && !pAscFl)) {
                    exitFl = false;
                    double wDouble = valA[jj + 1];
                    ItemCatalogAggrView wIcaVw = (ItemCatalogAggrView) icaVwA[jj + 1];
                    valA[jj + 1] = valA[jj];
                    icaVwA[jj + 1] = icaVwA[jj];
                    valA[jj] = wDouble;
                    icaVwA[jj] = wIcaVw;
                }
            }
            if (exitFl) {
                break;
            }
        }
        icaVwV = new ItemCatalogAggrViewVector();
        for (int ii = 0; ii < icaVwA.length; ii++) {
            icaVwV.add(icaVwA[ii]);
        }
        return icaVwV;
    }

    public static Element getDistItemInfo(HttpServletRequest request,
                                         ActionForm form) throws Exception {
        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Distributor distEjb = factory.getDistributorAPI();

        String index = request.getParameter("index");
        String itemIdS = request.getParameter("itemId");
        int itemId = 0;
        try {
            itemId = Integer.parseInt(itemIdS);
        } catch (Exception exc) {
        }

        String distIdS = request.getParameter("distId");
        int distId = 0;
        try {
            distId = Integer.parseInt(distIdS);
        } catch (Exception exc) {
        }

        if (distId <= 0 || itemId <= 0) {
            return null;
        }

        IdVector itemIdV = new IdVector();
        itemIdV.add(new Integer(itemId));

        DistItemViewVector distItemVwV = null;
        try {
            distItemVwV = distEjb.getDistItems(distId, itemIdV);
        } catch (Exception exc) {
            return null;
        }
        if (distItemVwV == null || distItemVwV.size() == 0) {
            return null;
        }
        DistItemView distItemVw = (DistItemView) distItemVwV.get(0);
        DocumentBuilderFactory docfactory = DocumentBuilderFactory.newInstance();
        docfactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;

        try {
        	docBuilder = docfactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {


        }
        Document doc = docBuilder.getDOMImplementation().createDocument("", "DistItem", null);
        Element root = doc.getDocumentElement();
        Element node;

        node = doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(itemId)));
        root.appendChild(node);

        int skuNum = distItemVw.getSku();
        node = doc.createElement("Sku");
        node.appendChild(doc.createTextNode(String.valueOf(skuNum)));
        root.appendChild(node);

        int retDistId = distItemVw.getDistId();
        if (retDistId == 0) {
            retDistId = distId;
            distItemVw.setDistName(null);
        }
        node = doc.createElement("DistId");
        node.appendChild(doc.createTextNode(String.valueOf(retDistId)));
        root.appendChild(node);

        String distName = distItemVw.getDistName();
        if (Utility.isSet(distName)) {
            node = doc.createElement("DistName");
            node.appendChild(doc.createTextNode(String.valueOf(distName)));
            root.appendChild(node);
        }

        String distItemSku = distItemVw.getDistItemSku();
        if (Utility.isSet(distItemSku)) {
            node = doc.createElement("DistItemSku");
            node.appendChild(doc.createTextNode(String.valueOf(distItemSku)));
            root.appendChild(node);
        }

        node = doc.createElement("index");
        node.appendChild(doc.createTextNode(index));
        root.appendChild(node);

        //String distItemVwXmlStr = root.toString();
        //distItemVwXmlStr = distItemVwXmlStr.replaceAll("\"", "&quot;");
        //return distItemVwXmlStr;
        return root;

    }

    public static ActionErrors saveSelectBox(HttpServletRequest request,
                                             ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        pForm.setSelectedLines(new String[0]);
        if (icaVwV != null) {
            int size = 0;
            for (Iterator iter = icaVwV.iterator(); iter.hasNext();) {
                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
                if (icaVw.getSelectFl()) {
                    size++;
                }
            }
            String[] selectedLines = new String[size];
            int ind = 0;
            for (Iterator iter = icaVwV.iterator(); iter.hasNext();) {
                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
                if (icaVw.getSelectFl()) {
                    int catalogId = icaVw.getCatalogId();
                    int itemId = icaVw.getItemId();
                    selectedLines[ind++] = itemId + ":" + catalogId;
                }
            }
            pForm.setSelectedLines(selectedLines);
        }
        return ae;
    }

    public static ActionErrors clearSelectBox(HttpServletRequest request,
                                              ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        pForm.setSelectedLines(new String[0]);
        return ae;
    }


    public static ActionErrors resetView(HttpServletRequest request,
                                         ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        pForm.setShowDistSkuNumFl(pForm.getShowDistSkuNumFlDef());
        pForm.setShowCustSkuNumFl(pForm.getShowCustSkuNumFlDef());
        return ae;
    }

    public static ActionErrors setCatalogFilter(HttpServletRequest request,
                                                ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        HttpSession session = request.getSession();
        String page = (String) session.getAttribute(SessionAttributes.
                ITEM_MANAGER_PAGE.CURRENT_PAGE);
        CatalogDataVector catalogDV = pForm.getCatalogFilter();
        String sessionParameterName = "catalogListSelectedCatItems";
        if ("service-catalog".equals(page)) {
            sessionParameterName = "catalogListSelectedItemContract";
        }
        if (catalogDV != null && catalogDV.size() > 0) {
            session.setAttribute(sessionParameterName, catalogDV);
        } else {
            session.removeAttribute(sessionParameterName);
        }
        if ("service-catalog".equals(page)) {
            return ae;
        }
        ae = loadCatalogData(request, form);
        return ae;
    }

    public static ActionErrors setDistFilter(HttpServletRequest request,
                                             ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        HttpSession session = request.getSession();
        String page = (String) session.getAttribute(SessionAttributes.ITEM_MANAGER_PAGE.CURRENT_PAGE);
        if ("service-catalog".equals(page)) {
            return ae;
        }
        if (pForm.getCatalogFilter() == null ||
                pForm.getCatalogFilter().size() == 0) {
            return ae;
        }
        ae = loadCatalogData(request, form);
        return ae;
    }

    public static ActionErrors setManufFilter(HttpServletRequest request,
                                              ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        HttpSession session = request.getSession();
        String page = (String) session.getAttribute(SessionAttributes.ITEM_MANAGER_PAGE.CURRENT_PAGE);
        if ("service-catalog".equals(page)) {
            return ae;
        }
        if (pForm.getCatalogFilter() == null) {
            return ae;
        }
        ae = loadCatalogData(request, form);
        return ae;
    }

    public static ActionErrors updateFromXls(HttpServletRequest request,
                                             ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        boolean needToSaveFl = false;
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;

        LocateUploadItemForm luiForm = pForm.getLocateUploadItemForm();
        boolean updateCategoryFl = luiForm.getRetUpdateCategoryFl();
        boolean updateDistSkuFl = luiForm.getRetUpdateDistSkuFl();
        boolean updateDistCostFl = luiForm.getRetUpdateDistCostFl();
        boolean updateCatalogPriceFl = luiForm.getRetUpdateCatalogPriceFl();
        int updateInsert = luiForm.getUpdateInsert();
        boolean insertFl = (updateInsert != LocateUploadItemForm.UPDATE_ONLY) ? true : false;
        boolean updateFl = (updateInsert != LocateUploadItemForm.INSERT_ONLY) ? true : false;

        int distProc = luiForm.getDistProc();
        DistributorDataVector dDV = pForm.getDistFilter();
        ServiceViewVector iVwV = pForm.getServiceFilter();
        if (insertFl &&
                (dDV != null && dDV.size() > 0 ||
                        iVwV != null && iVwV.size() > 0)) {
            String errorMess =
                    "Can't istert items if distributor filter is set";
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        UploadSkuViewVector uploadSkus = luiForm.getUploadSkusToReturn();
        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        IdVector itemsToAddIdV = new IdVector();
        UploadSkuViewVector itemsToAdd = new UploadSkuViewVector();
        if (uploadSkus == null) {
            return ae;
        }
        for (Iterator iter = uploadSkus.iterator(); iter.hasNext();) {
            UploadSkuView usVw = (UploadSkuView) iter.next();
            UploadSkuData usD = usVw.getUploadSku();
            int itemId = usD.getItemId();
            boolean foundFl = false;
            for (Iterator iter1 = icaVwV.iterator(); iter1.hasNext();) {
                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter1.next();
                if (itemId == icaVw.getItemId()) {
                    foundFl = true;
                    if (!updateFl) {
                        continue; // looking only for new items
                    }
                    if (distProc == LocateUploadItemForm.DISTRIBUTOR_EXCEPTION) {
                        if (icaVw.getDistId() > 0 && usD.getDistId() > 0) {
                            if (icaVw.getDistId() != usD.getDistId()) {
                                String errorMess = "Distributor doesn't match. Sku: " +
                                        icaVw.getSkuNum();
                                ae.add("error",
                                        new ActionError("error.simpleGenericError", errorMess));
                                return ae;
                            }
                        } else {
                            continue; //Do not do anithig if one distributor is 0
                        }
                    }
                    if (distProc == LocateUploadItemForm.DISTRIBUTOR_MATCH) {
                        if (icaVw.getDistId() == 0 ||
                                usD.getDistId() == 0 ||
                                icaVw.getDistId() != usD.getDistId()) {
                            continue; //Distributor doesn't match
                        }
                    }
                    if (distProc == LocateUploadItemForm.DISTRIBUTOR_ASSIGN) {
                        if (usD.getDistId() > 0) {
                            needToSaveFl = true;
                            icaVw.setDistIdInp("" + usD.getDistId());
                        }
                    }
                    if (updateDistSkuFl) {
                        needToSaveFl = true;
                        icaVw.setDistSkuNumInp(usD.getDistSku());
                    }

                    if (updateDistCostFl) {
                        needToSaveFl = true;
                        icaVw.setCostInp(usD.getDistCost());
                    }
                    if (updateCatalogPriceFl) {
                        needToSaveFl = true;
                        icaVw.setPriceInp(usD.getCatalogPrice());
                    }
                    if (updateCategoryFl) {
                        if (usVw.getCategoryId() > 0) {
                            needToSaveFl = true;
                            icaVw.setCategoryIdInp("" + usVw.getCategoryId());
                        }
                    }
                    break;
                }
            }
            if (!foundFl && insertFl) {
                needToSaveFl = true;
                itemsToAddIdV.add(new Integer(usD.getItemId()));
                itemsToAdd.add(usVw);
            }
        }

        CatalogDataVector catalogDV = pForm.getCatalogFilter();
        CatalogData catalogD = (CatalogData) catalogDV.get(0);

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        ItemInformation itemEjb = factory.getItemInformationAPI();
        CatalogInformation catInfEjb = factory.getCatalogInformationAPI();
        Catalog catalogEjb = factory.getCatalogAPI();
        Manufacturer manufEjb = factory.getManufacturerAPI();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants. APP_USER);
        StoreData storeD = appUser.getUserStore();
        int storeId = storeD.getBusEntity().getBusEntityId();
        int storeCatId = catInfEjb.getStoreCatalogId(storeId);

        ItemCatalogAggrViewVector itemCatAggrVwV =
                catInfEjb.getItemCatalogMgrSet(storeCatId, catalogD.getCatalogId(),
                        -1, itemsToAddIdV, null, null, RefCodeNames.ITEM_TYPE_CD.SERVICE);

        int index = 0;
        HashSet categIdHS = new HashSet();
        for (Iterator iter = itemCatAggrVwV.iterator(); iter.hasNext();) {
            ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
            int itemId = icaVw.getItemId();
            for (Iterator iter1 = itemsToAdd.iterator(); iter1.hasNext();) {
                UploadSkuView usVw = (UploadSkuView) iter1.next();
                UploadSkuData usD = usVw.getUploadSku();
                if (itemId == usD.getItemId()) {
                    icaVw.setCatalogFlInp(true);
                    icaVw.setContractFlInp(true);
                    icaVw.setCostInp(usD.getDistCost());
                    icaVw.setPriceInp(usD.getCatalogPrice());
                    if (usD.getDistId() > 0) {
                        icaVw.setDistIdInp("" + usD.getDistId());
                        icaVw.setDistName(usD.getDistName());
                        icaVw.setDistSkuNumInp(usD.getDistSku());

                    }

                    if (usVw.getCategoryId() > 0) {
                        icaVw.setCategoryIdInp("" + usVw.getCategoryId());
                        Integer categoryIdI = new Integer(usVw.getCategoryId());
                        if (!categIdHS.contains(categoryIdI)) {
                            categIdHS.add(categoryIdI);
                        }
                    }
                    icaVwV.add(index++, icaVw);
                    break;
                }
            }
        }

        //add categories to the catalog
        String userName = appUser.getUser().getUserName();
        IdVector catalogIdV = new IdVector();
        catalogIdV.add(new Integer(catalogD.getCatalogId()));
        boolean reloadCategFl = false;

        ItemDataVector categoryV = pForm.getCategories();
        for (Iterator iter = categIdHS.iterator(); iter.hasNext();) {
            Integer categIdI = (Integer) iter.next();
            int categId = categIdI.intValue();
            boolean foundFl = false;
            for (Iterator iter1 = categoryV.iterator(); iter1.hasNext();) {
                ItemData categD = (ItemData) iter1.next();
                if (categId == categD.getItemId()) {
                    foundFl = true;
                    break;
                }
            }
            if (!foundFl) {
                catalogEjb.addCategoryToCatalogs(categId, catalogIdV, userName);
                reloadCategFl = true;
            }
        }
        if (reloadCategFl) {
            ItemDataVector imDV = catInfEjb.getCatalogCategories(storeCatId,
                    catalogIdV);
            pForm.setCategories(imDV);
        }
        ItemDataVector categDV = pForm.getCategories();
        HashMap categHM = new HashMap();
        for (Iterator iter = categDV.iterator(); iter.hasNext();) {
            ItemData categ = (ItemData) iter.next();
            categHM.put(new Integer(categ.getItemId()), categ.getShortDesc());
        }
        for (Iterator iter = itemCatAggrVwV.iterator(); iter.hasNext();) {
            ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
            try {
                int categId = Integer.parseInt(icaVw.getCategoryIdInp());
                String categName = (String) categHM.get(new Integer(categId));
                if (categName != null) {
                    icaVw.setCategoryName(categName);
                }
            } catch (Exception exc) {
            }
        }

        if (needToSaveFl) {
            pForm.setNeedToSaveFl(true);
        }
        return ae;
    }

    public static ActionErrors resetSelectBox(HttpServletRequest request,
                                              ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        if (icaVwV != null) {
            String[] selectedLines = pForm.getSelectedLines();
            for (Iterator iter = icaVwV.iterator(); iter.hasNext();) {
                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
                icaVw.setSelectFl(false);
                int itemId = icaVw.getItemId();
                int catalogId = icaVw.getCatalogId();
               String key = itemId + ":" + catalogId ;
                for (int ii = 0; ii < selectedLines.length; ii++) {
                    if (key.equals(selectedLines[ii])) {
                        icaVw.setSelectFl(true);
                        break;
                    }
                }
            }
        }
        pForm.setSelectedLines(new String[0]);
        return ae;
    }

    public static ActionErrors cleanAssignDist(HttpServletRequest request,
                                               ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        pForm.setDistDummy(null);
        return ae;
    }

    public static ActionErrors setViewOrderGuides(HttpServletRequest request, StoreServiceCatalogMgrForm sForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        try {
            HttpSession session = request.getSession();
            APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
            CatalogInformation catInfEjb = factory.getCatalogInformationAPI();
            ItemCatalogAggrViewVector itemCatAggrVwV = null;
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
                    APP_USER);
            StoreData storeD = appUser.getUserStore();
            int storeId = storeD.getBusEntity().getBusEntityId();

            if(sForm!=null)
            {
                if(sForm.getControlMultipleCatalogFl())
                {   //removes multiple record from collection .
                    itemCatAggrVwV =sForm.getItemAggrVector();
                    if(itemCatAggrVwV!=null){
                        setMultipleFilterByCatalog(itemCatAggrVwV);
                        clearOrderGuideOfItemCatalogAggrCollection(itemCatAggrVwV);
                    }
                }
                else
                {
                    int storeCatId = catInfEjb.getStoreCatalogId(storeId);
                    itemCatAggrVwV =
                            catInfEjb.getItemCatalogMgrSet(storeCatId,sForm.getCurrManagingService().getItemData().getItemId(),
                                Utility.toIdVector(sForm.getDistFilter()),Utility.toIdVector(sForm.getCatalogFilter()),
                                    Utility.toIdVector(sForm.getAccountFilter()));
                    sForm.setItemAggrVector(itemCatAggrVwV);
                }
            }
        } catch (APIServiceAccessException e) {
            ae = StringUtils.getUiErrorMess(e);
        } catch (RemoteException e) {
            ae = StringUtils.getUiErrorMess(e);
        }
        return ae;
    }



    public static ActionErrors propagateAll(HttpServletRequest request, ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;

        String categoryIdS = pForm.getCategoryIdDummy();
        int categoryId = 0;
        try {
            categoryId = Integer.parseInt(categoryIdS);
        } catch (Exception exc) {
        }
        if (categoryId > 0) {
            ae = propagateCategoryId(request, pForm);
            if (ae.size() > 0) return ae;
        }

        if (pForm.getDistDummy() != null) {
            ae = propagateDistId(request, pForm);
            if (ae.size() > 0) return ae;
        }

        if (Utility.isSet(pForm.getCostDummy())) {
            ae = propagateCost(request, pForm);
            if (ae.size() > 0) return ae;
        }

        if (Utility.isSet(pForm.getPriceDummy())) {
            ae = propagatePrice(request, pForm);
            if (ae.size() > 0) return ae;
        }

        //ae = StoreItemCatalogMgrLogic.propagateCostCenterId(request,sForm);

        if (pForm.getShowCustSkuNumFlDef() &&
                Utility.isSet(pForm.getCatalogSkuNumDummy())) {
            ae = propagateCatalogSkuNum(request, pForm);
            if (ae.size() > 0) return ae;
        }

        if (pForm.getShowDistSkuNumFlDef() &&
                Utility.isSet(pForm.getDistSkuNumDummy())) {
            ae = propagateDistSkuNum(request, pForm);
            if (ae.size() > 0) return ae;
        }
        return ae;
    }

    public static ActionErrors propagateCategoryId(HttpServletRequest request,
                                                   ActionForm form) throws
            Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        String categoryIdS = pForm.getCategoryIdDummy();
        int categoryId = 0;
        try {
            categoryId = Integer.parseInt(categoryIdS);
        } catch (Exception exc) {
            String errorMess = "Category id is not numeric: " + categoryIdS;
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        ItemDataVector categoryDV = pForm.getCategories();
        String categoryName = null;
        if (categoryDV != null) {
            for (Iterator iter = categoryDV.iterator(); iter.hasNext();) {
                ItemData categoryD = (ItemData) iter.next();
                if (categoryD.getItemId() == categoryId) {
                    categoryName = categoryD.getShortDesc();
                    break;
                }
            }
        }
        if (categoryName == null) {
            String errorMess = (categoryId == 0) ?
                    "No category selected" :
                    "Category not found. Category id: " + categoryIdS;
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        if (icaVwV != null) {
            for (Iterator iter = icaVwV.iterator(); iter.hasNext();) {
                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
                if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
                    icaVw.setCategoryIdInp("" + categoryId);
                    icaVw.setCategoryName(categoryName);
                    pForm.setNeedToSaveFl(true);
                }
            }
        }
        return ae;
    }

    public static ActionErrors propagateCostCenterId(HttpServletRequest request,
                                                     ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;

        return ae;
    }

    public static ActionErrors propagateCatalogSkuNum(HttpServletRequest request,
                                                      ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        String catalogSkuNum = pForm.getCatalogSkuNumDummy();
        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        if (icaVwV != null) {
            for (Iterator iter = icaVwV.iterator(); iter.hasNext();) {
                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
                if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
                    icaVw.setCatalogSkuNumInp(catalogSkuNum);
                    pForm.setNeedToSaveFl(true);
                }
            }
        }
        return ae;
    }

    public static ActionErrors propagateDistId(HttpServletRequest request,
                                               ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Distributor distEjb = factory.getDistributorAPI();
        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        DistributorData dD = pForm.getDistDummy();
        if (dD == null) {
            if (icaVwV != null) {
                for (Iterator iter = icaVwV.iterator(); iter.hasNext();) {
                    ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
                    if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
                        icaVw.setDistIdInp(null);
                        icaVw.setDistName(null);
                        pForm.setNeedToSaveFl(true);
                    }
                }
            }
        } else {
            int distId = dD.getBusEntity().getBusEntityId();
            String distName = dD.getBusEntity().getShortDesc();
            if (icaVwV != null) {
                IdVector itemIdV = new IdVector();
                for (Iterator iter = icaVwV.iterator(); iter.hasNext();) {
                    ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
                    if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
                        icaVw.setDistIdInp(Integer.toString(distId));
                        icaVw.setDistName(distName);
                        pForm.setNeedToSaveFl(true);
                        int itemId = icaVw.getItemId();
                        itemIdV.add(new Integer(itemId));
                    }
                }

                //Set dist sku info
                if (pForm.getDistSkuAutoFl()) {
                    DistItemView distItemVw = null;
                    try {
                        DistItemViewVector distItemVwV =
                                distEjb.getDistItems(distId, itemIdV);
                        if (distItemVwV != null && distItemVwV.size() > 0) {
                            for (Iterator iter = icaVwV.iterator(); iter.hasNext();) {
                                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
                                if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
                                    int itemId = icaVw.getItemId();
                                    for (Iterator iter1 = distItemVwV.iterator(); iter1.hasNext();) {
                                        distItemVw = (DistItemView) iter1.next();
                                        if (itemId == distItemVw.getItemId()) {
                                            assigntDistItemInfo(request, form, distItemVw, icaVw);
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception exc) {
                        distItemVw = null;
                        exc.printStackTrace();
                    }
                }
            }
        }

        return ae;
    }

    public static void propagateDistId(BusEntityData busEntityData, ItemCatalogAggrView itemCatalogAggrView, HttpServletRequest request,
                                       ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Distributor distEjb = factory.getDistributorAPI();
        BusEntityData busEntityD = busEntityData;
        ItemCatalogAggrView icaVw = itemCatalogAggrView;
        if (busEntityData == null) {
            if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
                icaVw.setDistIdInp(null);
                icaVw.setDistName(null);
                pForm.setNeedToSaveFl(true);
            }
        } else {

            int distId = busEntityData.getBusEntityId();
            String distName = busEntityData.getShortDesc();
            IdVector itemIdV = new IdVector();
            if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
                icaVw.setDistIdInp(Integer.toString(distId));
                icaVw.setDistName(distName);
                pForm.setNeedToSaveFl(true);
                int itemId = icaVw.getItemId();
                itemIdV.add(new Integer(itemId));
            }

            //Set dist sku info
            if (pForm.getDistSkuAutoFl()) {
                DistItemView distItemVw = null;
                try {
                    DistItemViewVector distItemVwV = distEjb.getDistItems(distId, itemIdV);
                    if (distItemVwV != null && distItemVwV.size() > 0) {
                        if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
                            int itemId = icaVw.getItemId();
                            for (Iterator iter1 = distItemVwV.iterator(); iter1.hasNext();) {
                                distItemVw = (DistItemView) iter1.next();
                                if (itemId == distItemVw.getItemId()) {
                                    assigntDistItemInfo(request, form, distItemVw, icaVw);
                                }
                            }
                        }

                    }
                } catch (Exception exc) {
                    distItemVw = null;
                    exc.printStackTrace();
                }
            }
        }
    }


    public static ActionErrors setDefaulDistForCatalogs(HttpServletRequest request,
                                                        ActionForm form) throws Exception {


        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CatalogInformation catinfo = factory.getCatalogInformationAPI();
        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        CatalogAssocDataVector catAssocDV = null;
        Hashtable thBusEntity = new Hashtable();

        if (icaVwV != null) {

            IdVector errLimit = new IdVector();
            IdVector errEmpty = new IdVector();
            String except = "";
            boolean currErrLimitFl = false;
            boolean currErrEmptyFl = false;

            if (icaVwV != null)
                for (Iterator it = icaVwV.iterator(); it.hasNext();) {
                    ItemCatalogAggrView icaVw = (ItemCatalogAggrView) it.next();
                    if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
                        BusEntityData busEntityD = null;
                        int catId = icaVw.getCatalogId();
                        int orderGuideId = icaVw.getOrderGuideId();
                        log.info("Catatalog->" + catId + " OrderGuide->" + orderGuideId);

                        if (!thBusEntity.containsKey(String.valueOf(catId))) {
                            catAssocDV = catinfo.getCatalogAssoc(catId, 0, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR);

                            if (catAssocDV != null && catAssocDV.size() > 1) {
                                Integer catIdInt = new Integer(catId);
                                if (errLimit.indexOf(catIdInt) == -1)
                                    errLimit.add(catIdInt);
                                currErrLimitFl = true;
                            }
                            if (catAssocDV != null && catAssocDV.size() == 0) {
                                Integer catIdInt = new Integer(catId);
                                if (errEmpty.indexOf(catIdInt) == -1)
                                    errEmpty.add(new Integer(catId));
                                currErrEmptyFl = true;
                            }


                            if (currErrEmptyFl || currErrLimitFl || catAssocDV == null) {
                                propagateDistId(null, icaVw, request, form);
                            } else {

                                CatalogAssocData catAssocD = (CatalogAssocData) catAssocDV.get(0);
                                busEntityD = catinfo.getBusEntity(catAssocD.getBusEntityId());

                                try {
                                    propagateDistId(busEntityD, icaVw, request, form);
                                    if (busEntityD != null) {
                                        thBusEntity.put(String.valueOf(catId), busEntityD);
                                    }
                                } catch (Exception e) {
                                    except = except.concat(e.getMessage() + " ");
                                }

                            }


                        } else {
                            try {
                                propagateDistId((BusEntityData) thBusEntity.get(String.valueOf(catId)), icaVw, request, form);
                            } catch (Exception e) {
                                except = except.concat(e.getMessage() + " ");
                            }
                        }

                        currErrLimitFl = false;
                        currErrEmptyFl = false;
                    }

                }

            if (errLimit.size() > 0) {
                String errId = new String();
                for (int i = 0; i < errLimit.size(); i++) {
                    if (i > 0) errId = errId.concat(", ");
                    errId = errId.concat(String.valueOf(errLimit.get(i)));
                }
                ae.add("error1", new ActionError("error.simpleGenericError", "Catalog " + errId + " has multiple main distributors ."));
            }

            if (errEmpty.size() > 0) {
                String errId = new String();
                for (int i = 0; i < errEmpty.size(); i++) {
                    if (i > 0) errId = errId.concat(", ");
                    errId = errId.concat(String.valueOf(errEmpty.get(i)));
                }
                ae.add("error2", new ActionError("error.simpleGenericError", "Catalog " + errId + " doesn't have main distributor"));
            }
            if (!except.equals("")) ae.add("error3", new ActionError("error.simpleGenericError", except));

        }


        return ae;
    }


    public static ActionErrors propagateCost(HttpServletRequest request,
                                             ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        String costS = pForm.getCostDummy();
        if (!Utility.isSet(costS)) {
            String errorMess = "No distriutor cost value provided";
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        double costDb = -1;
        try {
            costDb = Double.parseDouble(costS);
        } catch (Exception exc) {
        }
        if (costDb < 0) {
            String errorMess = "Illegal distriutor cost value: " + costS;
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        if (icaVwV != null) {
            for (Iterator iter = icaVwV.iterator(); iter.hasNext();) {
                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
                if (icaVw.getContractFlInp() && icaVw.getSelectFl()) {
                    icaVw.setCostInp(costS);
                    pForm.setNeedToSaveFl(true);
                }
            }
        }
        return ae;
    }

    public static ActionErrors propagatePrice(HttpServletRequest request,
                                              ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        String priceS = pForm.getPriceDummy();
        if (!Utility.isSet(priceS)) {
            String errorMess = "No price value provided";
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        double priceDb = -1;
        try {
            priceDb = Double.parseDouble(priceS);
        } catch (Exception exc) {
        }
        if (priceDb < 0) {
            String errorMess = "Illegal price value: " + priceS;
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        if (icaVwV != null) {
            for (Iterator iter = icaVwV.iterator(); iter.hasNext();) {
                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
                if (icaVw.getContractFlInp() && icaVw.getSelectFl()) {
                    icaVw.setPriceInp(priceS);
                    pForm.setNeedToSaveFl(true);
                }
            }
        }
        return ae;
    }
     public static ActionErrors propagateAllPrice(HttpServletRequest request,ActionForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        if (Utility.isSet(pForm.getPriceDummy())) {
            ae = propagatePrice(request, pForm);
            if (ae.size() > 0) return ae;
            pForm.setCostDummy(pForm.getPriceDummy());
            ae = propagateCost(request, pForm);
            if (ae.size() > 0) return ae;
         }
        return ae;
    }


    public static ActionErrors propagateDistSkuNum(HttpServletRequest request,
                                                   ActionForm form) throws
            Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        String distSkuNum = pForm.getDistSkuNumDummy();
        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        if (icaVwV != null) {
            for (Iterator iter = icaVwV.iterator(); iter.hasNext();) {
                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
                if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
                    icaVw.setDistSkuNumInp(distSkuNum);
                    pForm.setNeedToSaveFl(true);
                }
            }
        }
        return ae;
    }

    private static void assigntDistItemInfo(HttpServletRequest request,
                                            ActionForm form,
                                            DistItemView distItemVw,
                                            ItemCatalogAggrView icaVw) throws
            Exception {
        HttpSession session = request.getSession();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;

        String distName = Utility.strNN(distItemVw.getDistName());
        icaVw.setDistName(distName);

        String distItemSku = Utility.strNN(distItemVw.getDistItemSku());
        if (pForm.getShowDistSkuNumFl()) {
            icaVw.setDistSkuNumInp(distItemSku);
        }
 }

    public static ActionErrors actionAdd(HttpServletRequest request, ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        boolean catalogFl = pForm.getTickItemsToCatalog();
        boolean contractFl = pForm.getTickItemsToContract();
        if (icaVwV != null) {
            for (Iterator iter = icaVwV.iterator(); iter.hasNext();) {
                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
                if (icaVw.getSelectFl()) {
                    if (catalogFl) {
                        icaVw.setCatalogFlInp(true);
                        pForm.setNeedToSaveFl(true);
                    }
                    if (contractFl && icaVw.getCatalogFlInp()) {
                        icaVw.setContractFlInp(true);
                        pForm.setNeedToSaveFl(true);
                    }
                }
            }
        }
        return ae;
    }

    public static ActionErrors actionRemove(HttpServletRequest request,
                                            ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        boolean catalogFl = pForm.getTickItemsToCatalog();
        boolean contractFl = pForm.getTickItemsToContract();
        if (icaVwV != null) {
            for (Iterator iter = icaVwV.iterator(); iter.hasNext();) {
                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
                if (icaVw.getSelectFl()) {

                    if (contractFl) {
                        icaVw.setContractFlInp(false);
                        icaVw.setOrderGuideFlInp(false);
                        pForm.setNeedToSaveFl(true);
                    }
                    if (catalogFl) {
                        icaVw.setContractFlInp(false);
                        icaVw.setOrderGuideFlInp(false);
                        icaVw.setCatalogFlInp(false);
                        pForm.setNeedToSaveFl(true);
                    }
                }
            }
        }
        return ae;
    }

    public static ActionErrors setView(HttpServletRequest request, ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        pForm.setShowDistSkuNumFlDef(pForm.getShowDistSkuNumFl());
        pForm.setShowCustSkuNumFlDef(pForm.getShowCustSkuNumFl());
        return ae;
    }

    public static ActionErrors save(HttpServletRequest request,
                                    ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        Distributor distEjb = factory.getDistributorAPI();
        Catalog catalogEjb = factory.getCatalogAPI();
        HashMap distHM = new HashMap();
        HashSet errors = new HashSet();
        IdVector distIdV = new IdVector();
        if (icaVwV == null || icaVwV.size() == 0) {
            String errorMess = "Nothing to save";
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        for (Iterator iter = icaVwV.iterator(); iter.hasNext();) {
            ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
            int itemId = icaVw.getItemId();
            int catalogId = icaVw.getCatalogId();
            Integer catalogIdInt = new Integer(catalogId);

            if (!icaVw.getCatalogFlInp()) {
                continue;
            }
            String distIdInpS = icaVw.getDistIdInp();
            int distId = 0;
            if (Utility.isSet(distIdInpS)) {
                try {
                    distId = Integer.parseInt(distIdInpS);
                } catch (Exception exc) {
                }
                if (distId <= 0) {
                    String errorMess = "Illegal distributor id: " + distIdInpS;
                    errors.add(errorMess);
                }
            }
            if (distId > 0) {
                String distSkuNumInpS = Utility.strNN(icaVw.getDistSkuNumInp());
                String distSkuNum = Utility.strNN(icaVw.getDistSkuNum());

                String distItemKey = distId + ":" + itemId;
                ItemCatalogAggrView icaVw1 = (ItemCatalogAggrView) distHM.get(distItemKey);
                if (icaVw1 == null) {
                    distHM.put(distItemKey, icaVw);
                    distIdV.add(new Integer(distId));
                } else {
                    if (!distSkuNumInpS.equals(Utility.strNN(icaVw1.getDistSkuNumInp()))) {
                        String errorMess =
                                "Distributor sku# inconsistency. DistributorId: " +
                                        distId;
                        if (!errors.contains(errorMess)) {
                            errors.add(errorMess);
                        }
                    }

                }
            }
        }
            for (Iterator iter = icaVwV.iterator(); iter.hasNext();) {
            ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();

            int itemId = icaVw.getItemId();
            int catalogId = icaVw.getCatalogId();
            Integer catalogIdInt = new Integer(catalogId);

            if (!icaVw.getCatalogFlInp()) {
                continue;
            }
            String categoryIdS = icaVw.getCategoryIdInp();
            int categoryId = 0;
            try {
                categoryId = Integer.parseInt(categoryIdS);
            } catch (Exception exc) {
            }
            if (categoryId <= 0) {
                String errorMess = "No category defined in catalog: " +
                        icaVw.getCatalogId();
                if (!errors.contains(errorMess)) {
                    errors.add(errorMess);
                }
            }
            String distIdInpS = icaVw.getDistIdInp();
            int distId = 0;
            if (Utility.isSet(distIdInpS)) {
                try {
                    distId = Integer.parseInt(distIdInpS);
                } catch (Exception exc) {
                }
                if (distId <= 0) {
                    continue;
                }
            }

            int oldDistId = icaVw.getDistId();
            if (icaVw.getContractFlInp()) {
                String costInpS = icaVw.getCostInp();
                if (Utility.isSet(costInpS)) {
                    try {
                        Double.parseDouble(costInpS);
                    } catch (Exception exc) {
                        String errorMess = "Illegal cost value: " + costInpS;
                        if (!errors.contains(errorMess)) {
                            errors.add(errorMess);
                        }
                    }
                }
                String priceInpS = icaVw.getPriceInp();
                if (Utility.isSet(priceInpS)) {
                    try {
                        Double.parseDouble(priceInpS);
                        icaVw.setCostInp(priceInpS);
                    } catch (Exception exc) {
                        String errorMess = "Illegal price value: " + priceInpS;
                        if (!errors.contains(errorMess)) {
                            errors.add(errorMess);
                        }
                    }
                }
           }
        }
        Collection distColl = distHM.values();
        DistributorDataVector distDV = distEjb.getDistributorCollectionByIdList(distIdV);

        for (Iterator iter = distColl.iterator(); iter.hasNext();) {
            ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
            String distIdS = icaVw.getDistIdInp();
            if (Utility.isSet(distIdS)) {
                try {
                    int distId = Integer.parseInt(distIdS);
                    if (distId > 0) {
                        boolean foundFl = false;
                        for (Iterator iter1 = distDV.iterator(); iter1.hasNext();) {
                            DistributorData dD = (DistributorData) iter1.next();
                            if (dD.getBusEntity().getBusEntityId() == distId) {
                                foundFl = true;
                                break;
                            }
                        }
                        if (!foundFl) {
                            String errorMess = "No distributor found. Distributor id: " +
                                    distId;
                            errors.add(errorMess);
                        }
                    }
                } catch (Exception exc) {
                }
            }
        }
        if (errors.size() > 0) {
            int ind = 0;
            for (Iterator iter = errors.iterator(); iter.hasNext();) {
                String errorMess = (String) iter.next();
                ae.add("error" + (++ind),
                        new ActionError("error.simpleGenericError", errorMess));
            }
            return ae;
        }
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        String userName = appUser.getUser().getUserName();
        try {
            catalogEjb.saveItemCatalogMgrSet1(icaVwV, userName, RefCodeNames.ITEM_TYPE_CD.SERVICE);
        } catch (Exception exc) {
            String errorMess = exc.getMessage();
            int ind1 = -1;
            int ind2 = -1;
            if (errorMess == null) throw exc;
            ind1 = errorMess.indexOf("^clw^");
            if (ind1 >= 0) {
                ind2 = errorMess.indexOf("^clw^", ind1 + 3);
                if (ind2 > 0) {
                    errorMess = errorMess.substring(ind1 + 5, ind2);
                    ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                    return ae;
                }
            }
            throw exc;
        }

        ae = reload(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        return ae;
    }

    public static ActionErrors reload(HttpServletRequest request, ActionForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        String page =
                (String) session.getAttribute(SessionAttributes.ITEM_MANAGER_PAGE.
                        CURRENT_PAGE);
        if (page == null || page.equals("service-catalog")) {
            ae = loadAggrData(request, form);
        } else if (page.equals("catalog-service")) {
            ae = loadCatalogData(request, form);
        }
        if (ae.size() > 0) {
            return ae;
        }
        return ae;
    }

    public static ActionErrors addCategoryToCatalogs(HttpServletRequest request, ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        String page =
                (String) session.getAttribute(SessionAttributes.ITEM_MANAGER_PAGE.
                        CURRENT_PAGE);
        if (page == null || page.equals("service-catalog")) {
            ae = addCategoryToSelectedCatalogs(request, form);
        } else if (page.equals("catalog-service")) {
            ae = addCategoryToTheCatalog(request, form);
        }

        return ae;
    }

    public static ActionErrors addCategoryToSelectedCatalogs(HttpServletRequest request, ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Catalog catalogEjb = factory.getCatalogAPI();
        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();

        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        IdVector allCatalogIdV = new IdVector();
        IdVector catalogIdV = new IdVector();
        if (icaVwV != null) {
            for (Iterator iter = icaVwV.iterator(); iter.hasNext();) {
                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
                int catalogId = icaVw.getCatalogId();
                allCatalogIdV.add(new Integer(catalogId));
                if (icaVw.getSelectFl()) {
                    catalogIdV.add(new Integer(catalogId));
                }
            }
        }

        int categoryId = pForm.getStoreCategoryId();
        if (categoryId == 0) {
            String errorMess = "No category selected";
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        if (catalogIdV.size() == 0) {
            String errorMess = "No catalogs selected";
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
                APP_USER);
        String userName = appUser.getUser().getUserName();
        StoreData storeD = appUser.getUserStore();
        int storeId = storeD.getBusEntity().getBusEntityId();
        catalogEjb.addCategoryToCatalogs(categoryId, catalogIdV, userName);

        int storeCatalogId = catalogInfEjb.getStoreCatalogId(storeId);
        ItemDataVector imDV = catalogInfEjb.getCatalogCategories(storeCatalogId, allCatalogIdV);
        pForm.setCategories(imDV);
        pForm.setStoreCategoryId(0);
        return ae;
    }

    public static ActionErrors addCategoryToTheCatalog(HttpServletRequest request,
                                                       ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Catalog catalogEjb = factory.getCatalogAPI();
        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();

        CatalogData catalogD = null;
        IdVector catalogIdV = new IdVector();
        CatalogDataVector catalogDV = pForm.getCatalogFilter();
        if (catalogDV != null && catalogDV.size() > 0) {
            catalogD = (CatalogData) catalogDV.get(0);
            int catalogId = catalogD.getCatalogId();
            catalogIdV.add(new Integer(catalogId));
        }

        int categoryId = pForm.getStoreCategoryId();
        if (categoryId == 0) {
            String errorMess = "No category selected";
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        if (catalogIdV.size() == 0) {
            String errorMess = "No catalogs selected";
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
                APP_USER);
        String userName = appUser.getUser().getUserName();
        StoreData storeD = appUser.getUserStore();
        int storeId = storeD.getBusEntity().getBusEntityId();
        catalogEjb.addCategoryToCatalogs(categoryId, catalogIdV, userName);

        int storeCatalogId = catalogInfEjb.getStoreCatalogId(storeId);
        ItemDataVector imDV = catalogInfEjb.getCatalogCategories(storeCatalogId,
                catalogIdV);
        pForm.setCategories(imDV);
        pForm.setStoreCategoryId(0);
        return ae;
    }

    public static ActionErrors removeCategoryFromCatalogs(HttpServletRequest
            request,
                                                          ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Catalog catalogEjb = factory.getCatalogAPI();
        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();

        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        IdVector allCatalogIdV = new IdVector();
        IdVector catalogIdV = new IdVector();
        if (icaVwV != null) {
            for (Iterator iter = icaVwV.iterator(); iter.hasNext();) {
                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
                int catalogId = icaVw.getCatalogId();
                allCatalogIdV.add(new Integer(catalogId));
                if (icaVw.getSelectFl()) {
                    catalogIdV.add(new Integer(catalogId));
                }
            }
        }

        String categoryIdS = pForm.getCategoryIdDummy();
        int categoryId = 0;
        try {
            categoryId = Integer.parseInt(categoryIdS);
        } catch (Exception exc) {
        }
        if (categoryId == 0) {
            String errorMess = "No category selected";
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        if (catalogIdV.size() == 0) {
            String errorMess = "No catalogs selected";
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
                APP_USER);
        String userName = appUser.getUser().getUserName();
        StoreData storeD = appUser.getUserStore();
        int storeId = storeD.getBusEntity().getBusEntityId();
        try {
            catalogEjb.removeCategoryFromCatalogs(categoryId, catalogIdV,RefCodeNames.ITEM_TYPE_CD.SERVICE);
        } catch (Exception exc) {
            String errorMess = exc.getMessage();
            int ind1 = -1;
            int ind2 = -1;
            if (errorMess == null) throw exc;
            ind1 = errorMess.indexOf("^clw^");
            if (ind1 >= 0) {
                ind2 = errorMess.indexOf("^clw^", ind1 + 3);
                if (ind2 > 0) {
                    errorMess = errorMess.substring(ind1 + 5, ind2);
                    ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                    return ae;
                }
            }
            throw exc;
        }

        int storeCatalogId = catalogInfEjb.getStoreCatalogId(storeId);
        ItemDataVector imDV = catalogInfEjb.getCatalogCategories(storeCatalogId,
                allCatalogIdV);
        pForm.setCategories(imDV);
        pForm.setStoreCategoryId(0);
        return ae;
    }

    public static ActionErrors cpoC(HttpServletRequest request, ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        String itemIdS = pForm.getItemCpoId();
        int itemId = Integer.parseInt(itemIdS);
        String catalogIdS = pForm.getCatalogCpoId();
        int catalogId = Integer.parseInt(catalogIdS);
        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        if (icaVwV != null) {
            for (Iterator iter = icaVwV.iterator(); iter.hasNext();) {
                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
                if (catalogId == icaVw.getCatalogId() && itemId == icaVw.getItemId()) {
                    boolean catalogFl = !icaVw.getCatalogFlInp();
                    icaVw.setCatalogFlInp(!icaVw.getCatalogFlInp());
                    pForm.setNeedToSaveFl(true);
                    if (!catalogFl) {
                        icaVw.setContractFlInp(false);
                        icaVw.setOrderGuideFlInp(false);
                    }
                }
            }
        }
        return ae;
    }

    public static ActionErrors cpoP(HttpServletRequest request, ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreServiceCatalogMgrForm pForm = (StoreServiceCatalogMgrForm) form;
        String itemIdS = pForm.getItemCpoId();
        int itemId = Integer.parseInt(itemIdS);
        String catalogIdS = pForm.getCatalogCpoId();
        int catalogId = Integer.parseInt(catalogIdS);
        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        if (icaVwV != null) {
            for (Iterator iter = icaVwV.iterator(); iter.hasNext();) {
                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
                if (catalogId == icaVw.getCatalogId() && itemId == icaVw.getItemId()) {
                    boolean contractFl = !icaVw.getContractFlInp();
                    if (contractFl) {
                        icaVw.setCatalogFlInp(true);
                    }
                    icaVw.setContractFlInp(contractFl);
                    pForm.setNeedToSaveFl(true);
                    if (!contractFl) {
                        icaVw.setOrderGuideFlInp(false);
                    }
                }
            }
        }
        return ae;
    }

       public static void clearOrderGuideOfItemCatalogAggrCollection(ItemCatalogAggrViewVector itemCatalogAggrVwV)
    {
        for (Iterator iter = itemCatalogAggrVwV.iterator(); iter.hasNext(); ) {
            ItemCatalogAggrView itemCatalogAggrV = (ItemCatalogAggrView) iter.next();
            itemCatalogAggrV.setOrderGuideId(0);
            itemCatalogAggrV.setOrderGuideName("");
        }
    }
    private static void setMultipleFilterByCatalog(ItemCatalogAggrViewVector itemCatalogAggrVwV) {
        IdVector useCatId=new IdVector();
        for (Iterator iter = itemCatalogAggrVwV.iterator(); iter.hasNext(); ) {
            ItemCatalogAggrView itemCatalogAggrV = (ItemCatalogAggrView) iter.next();
            Integer cId = new Integer(itemCatalogAggrV.getCatalogId());
            if(!useCatId.contains(cId))
            {
                useCatId.add(cId);
            }
            else
            {
                iter.remove();
            }

        }
    }
}

