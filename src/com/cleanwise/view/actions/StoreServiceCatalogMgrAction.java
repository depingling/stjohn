package com.cleanwise.view.actions;

import com.cleanwise.view.forms.StoreServiceCatalogMgrForm;
import com.cleanwise.view.logic.StoreServiceCatalogMgrLogic;
import com.cleanwise.view.utils.SessionAttributes;
import com.cleanwise.view.utils.SessionTool;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Element;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         15.01.2007
 * Time:         9:49:13
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class StoreServiceCatalogMgrAction extends ActionSuper {
    public ActionForward performSub(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if (st.checkSession() == false) {
            return mapping.findForward("/userportal/logon");
        }

        HttpSession session = request.getSession();
        StoreServiceCatalogMgrForm sForm = (StoreServiceCatalogMgrForm) form;

        // Get the action and the catalogId from the request.
        String action = request.getParameter("action");
        ////////////////////////////
        if ("DistSkuInfo".equals(action)) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            Element distSkuInfo = null;
            try {
                distSkuInfo = StoreServiceCatalogMgrLogic.getDistItemInfo(request, form);
            } catch (Exception exc) {
                exc.printStackTrace();
            }

            if (distSkuInfo != null) {
                //response.getWriter().write(distSkuInfo);
                OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
                XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
                serializer.serialize(distSkuInfo);
            }
            return null;
        }
        ///////////////////////
        sForm.setAction(action);

        String mappingAction = "success";
        ActionErrors ae = new ActionErrors();

        try {
            if ("Xls Update".equals(action) ||
                    "LocateAssignDistributor".equals(action)) {
                StoreServiceCatalogMgrLogic.saveSelectBox(request, sForm);
                sForm.setLastLocateAction(action);
            }
            if ("Locate Catalog".equals(action) ||
                    "Locate Account".equals(action) ||
                    "Locate Distributor".equals(action) ||
                    "Locate Item".equals(action)) {
                StoreServiceCatalogMgrLogic.clearSelectBox(request, sForm);
                sForm.setLastLocateAction(action);
            }
            if (!"Set View".equals(action)) {
                StoreServiceCatalogMgrLogic.resetView(request, sForm);
            }
            if (action == null) {
                ae = StoreServiceCatalogMgrLogic.init(request, sForm);
            } else if ("Return Selected".equals(action)) {
                String submitFormIdent = request.getParameter("jspSubmitIdent");
                if (submitFormIdent != null && submitFormIdent.indexOf("#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_SERVICE_FORM) == 0) {
                    ae = StoreServiceCatalogMgrLogic.setItemFilter(request, sForm);
                } else
                if (submitFormIdent != null && submitFormIdent.indexOf("#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_DIST_FORM) == 0) {
                    ae = StoreServiceCatalogMgrLogic.setDistFilter(request, sForm);
                } else
                if (submitFormIdent != null && submitFormIdent.indexOf("#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_CATALOG_FORM) == 0
                        && submitFormIdent.length() == SessionAttributes.SEARCH_FORM.LOCATE_STORE_CATALOG_FORM.length() + 1) {
                    ae = StoreServiceCatalogMgrLogic.setCatalogFilter(request, sForm);

                } else
                if (submitFormIdent != null && submitFormIdent.indexOf("#" + SessionAttributes.SEARCH_FORM.LOCATE_UPLOAD_ITEM_FORM) == 0) {
                    ae = StoreServiceCatalogMgrLogic.updateFromXls(request, sForm);
                } else if (!"LocateAssignDistributor".equals(sForm.getLastLocateAction()) &&
                        !"Xls Update".equals(sForm.getLastLocateAction())
                        ) {
                    ae = StoreServiceCatalogMgrLogic.loadAggrData(request, form);
                }
                StoreServiceCatalogMgrLogic.resetSelectBox(request, sForm);
            } else if ("Cancel".equals(action)) {
                StoreServiceCatalogMgrLogic.resetSelectBox(request, sForm);
            } else if (action.equals("CleanAssignDistributor")) {
                ae = StoreServiceCatalogMgrLogic.cleanAssignDist(request, sForm);
            } else if (action.equals("sort")) {
                ae = StoreServiceCatalogMgrLogic.sort(request, sForm);
            } else if (action.equals("Set View Order Guides")) {
                ae = StoreServiceCatalogMgrLogic.setViewOrderGuides(request, sForm);
            } else if (action.equals("SetAll")) {
                ae = StoreServiceCatalogMgrLogic.propagateAll(request, sForm);
            } else if (action.equals("CategoryIdInp")) {
                ae = StoreServiceCatalogMgrLogic.propagateCategoryId(request, sForm);
            } else if (action.equals("CostCenterIdInp")) {
                ae = StoreServiceCatalogMgrLogic.propagateCostCenterId(request, sForm);
            } else if (action.equals("CatalogSkuNumInp")) {
                ae = StoreServiceCatalogMgrLogic.propagateCatalogSkuNum(request, sForm);
            } else if (action.equals("DistIdInp")) {
                ae = StoreServiceCatalogMgrLogic.propagateDistId(request, sForm);
            } else if (action.equals("SetDefaultDist")) {
                ae = StoreServiceCatalogMgrLogic.setDefaulDistForCatalogs(request, sForm);
            } else if (action.equals("PriceInp")) {
                ae = StoreServiceCatalogMgrLogic.propagateAllPrice(request, sForm);
            } else if (action.equals("DistSkuNumInp")) {
                ae = StoreServiceCatalogMgrLogic.propagateDistSkuNum(request, sForm);
            } else if (action.equals("Add")) {
                ae = StoreServiceCatalogMgrLogic.actionAdd(request, sForm);
            } else if (action.equals("Remove")) {
                ae = StoreServiceCatalogMgrLogic.actionRemove(request, sForm);
            } else if (action.equals("Set View")) {
                ae = StoreServiceCatalogMgrLogic.setView(request, sForm);
            } else if (action.equals("Save")) {
                //StoreServiceCatalogMgrLogic.saveSelectBox(request,sForm);
                ae = StoreServiceCatalogMgrLogic.save(request, sForm);
                //StoreServiceCatalogMgrLogic.resetSelectBox(request,sForm);
            } else if (action.equals("Reload")) {
                StoreServiceCatalogMgrLogic.saveSelectBox(request, sForm);
                ae = StoreServiceCatalogMgrLogic.reload(request, sForm);
                StoreServiceCatalogMgrLogic.resetSelectBox(request, sForm);
            } else if (action.equals("Clear Filters")) {
                ae = StoreServiceCatalogMgrLogic.clearFilters(request, sForm);
            } else if (action.equals("Add To Catalogs")) {
                ae = StoreServiceCatalogMgrLogic.addCategoryToCatalogs(request, sForm);
            } else if (action.equals("Remove From Catalogs")) {
                ae = StoreServiceCatalogMgrLogic.removeCategoryFromCatalogs(request, sForm);
            } else if (action.equals("cpoC")) {
                ae = StoreServiceCatalogMgrLogic.cpoC(request, sForm);
            } else if (action.equals("cpoP")) {
                ae = StoreServiceCatalogMgrLogic.cpoP(request, sForm);
            }else if (action.equals("Clear Catalog Filter")) {
                ae = StoreServiceCatalogMgrLogic.clearCatalogFilter(request);
            }

        }

        // Catch all exceptions here.
        catch (Exception e) {
            request.setAttribute("errorobject", e);
            e.printStackTrace();
            return (mapping.findForward("error"));
        }
        if (ae.size() > 0) {
            saveErrors(request, ae);
            return (mapping.findForward("failure"));
        }
        return (mapping.findForward(mappingAction));

    }


}


