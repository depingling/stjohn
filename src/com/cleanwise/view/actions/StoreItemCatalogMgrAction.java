/*
 * StoreItemCatalogMgrAction.java
 *
 * Created on May 5, 2005, 4:24 PM
 */

package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.logic.StoreItemCatalogMgrLogic;
import com.cleanwise.view.forms.StoreItemCatalogMgrForm;
import com.cleanwise.view.forms.StoreItemOrderGuideMgrForm;
import com.cleanwise.view.logic.LocateStoreCatalogLogic;
import com.cleanwise.view.logic.LocateStoreAccountLogic;
import com.cleanwise.view.logic.LocateStoreDistLogic;
import com.cleanwise.view.logic.LocateStoreItemLogic;
import com.cleanwise.view.forms.LocateStoreCatalogForm;
import com.cleanwise.view.forms.LocateStoreItemForm;
import com.cleanwise.view.forms.LocateUploadItemForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Element;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.util.LocatePropertyNames;

/**
 *
 * @author Ykupershmidt
 */
public class StoreItemCatalogMgrAction  extends ActionSuper {
  public ActionForward performSub(
                 ActionMapping mapping,
                 ActionForm form,
                 HttpServletRequest request,
                 HttpServletResponse response)
                 throws IOException, ServletException {



    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }

    HttpSession session = request.getSession();
    StoreItemCatalogMgrForm sForm = (StoreItemCatalogMgrForm) form;

    // Get the action and the catalogId from the request.
    String action  = request.getParameter("action");
    ////////////////////////////
    if("DistSkuInfo".equals(action)) {
      response.setContentType("text/xml");
      response.setHeader("Cache-Control", "no-cache");
      Element distSkuInfo = null;
      try {
        distSkuInfo = StoreItemCatalogMgrLogic.getDistItemInfo(request,form);
      } catch(Exception exc){
        exc.printStackTrace();
      }

      if(distSkuInfo!=null) {
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
      if("Xls Update".equals(action) ||
          "LocateAssignDistributor".equals(action)) {
        StoreItemCatalogMgrLogic.saveSelectBox(request,sForm);
        sForm.setLastLocateAction(action);
      }
      if("Locate Catalog".equals(action) ||
         "Locate Account".equals(action) ||
         "Locate Distributor".equals(action) ||
         "Locate Item".equals(action) ||
         LocatePropertyNames.LOCATE_ORDER_GUIDE_ACTION.equals(action)) {
        StoreItemCatalogMgrLogic.clearSelectBox(request,sForm);
        sForm.setLastLocateAction(action);
      }
      if(!"Set View".equals(action)) {
        StoreItemCatalogMgrLogic.resetView(request,sForm);
      }
      if(action==null) {
         ae = StoreItemCatalogMgrLogic.init(request,sForm);
      }
      else if("Return Selected".equals(action)){
          String submitFormIdent = request.getParameter("jspSubmitIdent");
          if(submitFormIdent!=null &&  submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_ITEM_FORM)==0){
        	  if (form instanceof StoreItemOrderGuideMgrForm) {
        		  ae = StoreItemCatalogMgrLogic.setItemOrderGuideFilter(request, sForm);
        	  }
        	  else {
        		  ae = StoreItemCatalogMgrLogic.setItemFilter(request, sForm);
        	  }
          }
          else if(submitFormIdent!=null &&  submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_DIST_FORM)==0){
        	  if (form instanceof StoreItemOrderGuideMgrForm) {
        		  ae = StoreItemCatalogMgrLogic.setDistOrderGuideFilter(request, sForm);
        	  }
        	  else {
        		  ae = StoreItemCatalogMgrLogic.setDistFilter(request, sForm);
        	  }
          }
          else if(submitFormIdent!=null &&  submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_MANUF_FORM)==0){
        	  if (form instanceof StoreItemOrderGuideMgrForm) {
        		  ae = StoreItemCatalogMgrLogic.setManufOrderGuideFilter(request, sForm);
        	  }
        	  else {
        		  ae = StoreItemCatalogMgrLogic.setManufFilter(request, sForm);
        	  }           
          }
          else if(submitFormIdent!=null &&  submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_CATALOG_FORM)==0
                  && submitFormIdent.length()==SessionAttributes.SEARCH_FORM.LOCATE_STORE_CATALOG_FORM.length()+1){
              ae = StoreItemCatalogMgrLogic.setCatalogFilter(request,sForm);
          }
          else if(submitFormIdent!=null &&  submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_ORDER_GUIDE_FORM)==0){
        	  ae = StoreItemCatalogMgrLogic.setOrderGuideFilter(request, sForm);
          }
          else if(submitFormIdent!=null &&  submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_UPLOAD_ITEM_FORM)==0){
              ae = StoreItemCatalogMgrLogic.updateFromXls(request,sForm);
              if (ae.size() > 0) {
                saveErrors(request, ae);
                return (mapping.findForward("showCat"));
              }
          }
          else if(!"LocateAssignDistributor".equals(sForm.getLastLocateAction()) &&
                  !"Xls Update".equals(sForm.getLastLocateAction())
                  ) {
            ae = StoreItemCatalogMgrLogic.loadAggrData (request,form);
          }
          StoreItemCatalogMgrLogic.resetSelectBox(request,sForm);
      }
      else if("Cancel".equals(action)){
          StoreItemCatalogMgrLogic.resetSelectBox(request,sForm);
      }
      else if(action.equals("CleanAssignDistributor")) {
         ae = StoreItemCatalogMgrLogic.cleanAssignDist(request,sForm);
      }
      else if(action.equals("sort")) {
         ae = StoreItemCatalogMgrLogic.sort(request,sForm);
      }
      else if(action.equals("Set View Order Guides")) {
         ae = StoreItemCatalogMgrLogic.setViewOrderGuides(request,sForm);
      }
      else if(action.equals("SetAll")) {
         ae = StoreItemCatalogMgrLogic.propagateAll(request,sForm);
      }
      else if(action.equals("CategoryIdInp")) {
         ae = StoreItemCatalogMgrLogic.propagateCategoryId(request,sForm);
      }
      else if(action.equals("CostCenterIdInp")) {
        ae = StoreItemCatalogMgrLogic.propagateCostCenterId(request,sForm);
      }
      else if(action.equals("CatalogSkuNumInp")) {
        ae = StoreItemCatalogMgrLogic.propagateCatalogSkuNum(request,sForm);
      }
      else if(action.equals("DistIdInp")) {
        ae = StoreItemCatalogMgrLogic.propagateDistId(request,sForm);
      }
      else if(action.equals("SetDefaultDist")){
          ae=StoreItemCatalogMgrLogic.setDefaulDistForCatalogs(request,sForm);
      }
      else if(action.equals("CostInp")) {
        ae = StoreItemCatalogMgrLogic.propagateCost(request,sForm);
      }
      else if(action.equals("PriceInp")) {
        ae = StoreItemCatalogMgrLogic.propagatePrice(request,sForm);
      }
      else if(action.equals("BaseCostInp")) {
        ae = StoreItemCatalogMgrLogic.propagateBaseCost(request,sForm);
      }
      else if(action.equals("DistSkuNumInp")) {
        ae = StoreItemCatalogMgrLogic.propagateDistSkuNum(request,sForm);
      }
      else if(action.equals("DistSkuPackInp")) {
        ae = StoreItemCatalogMgrLogic.propagateDistSkuPack(request,sForm);
      }
      else if(action.equals("DistSkuUomInp")) {
        ae = StoreItemCatalogMgrLogic.propagateDistSkuUom(request,sForm);
      }
      else if(action.equals("DistConversInp")) {
        ae = StoreItemCatalogMgrLogic.propagateDistConvers(request,sForm);
      }
      else if(action.equals("GenManufIdInp")) {
        ae = StoreItemCatalogMgrLogic.propagateGenManufId(request,sForm);
      }
      else if(action.equals("GenManufSkuNumInp")) {
        ae = StoreItemCatalogMgrLogic.propagateGenManufSkuNum(request,sForm);
      }
      else if(action.equals("MultiproductIdInp")) {
          ae = StoreItemCatalogMgrLogic.propagateMultiproductId(request,sForm);
      }
      else if(action.equals("Add")) {
        ae = StoreItemCatalogMgrLogic.actionAdd(request,sForm);
      }
      else if(action.equals("Remove")) {
        ae = StoreItemCatalogMgrLogic.actionRemove(request,sForm);
      }
      else if(action.equals("Set View")) {
        ae = StoreItemCatalogMgrLogic.setView(request,sForm);
      }
      else if(action.equals("Save")) {
        //StoreItemCatalogMgrLogic.saveSelectBox(request,sForm);
        ae = StoreItemCatalogMgrLogic.save(request,sForm);
        //StoreItemCatalogMgrLogic.resetSelectBox(request,sForm);
      }
      else if(action.equals("Reload")) {
        StoreItemCatalogMgrLogic.saveSelectBox(request,sForm);
        ae = StoreItemCatalogMgrLogic.reload(request,sForm);
        StoreItemCatalogMgrLogic.resetSelectBox(request,sForm);
      }
      else if(action.equals("Clear Filters")) {
        ae = StoreItemCatalogMgrLogic.clearFilters(request,sForm);
      }
      else if(action.equals("Add To Catalogs")) {
        ae = StoreItemCatalogMgrLogic.addCategoryToCatalogs(request,sForm);
      }
      else if(action.equals("Remove From Catalogs")) {
        ae = StoreItemCatalogMgrLogic.removeCategoryFromCatalogs(request,sForm);
      }
      else if(action.equals("cpoC")) {
        ae = StoreItemCatalogMgrLogic.cpoC(request,sForm);
      }
      else if(action.equals("cpoP")) {
        ae = StoreItemCatalogMgrLogic.cpoP(request,sForm);
      }
      else if(action.equals("cpoOg")) {
        ae = StoreItemCatalogMgrLogic.cpoOg(request,sForm);
      }
      else if(action.equals("Clear Catalog Filter")) {
        ae = StoreItemCatalogMgrLogic.clearCatalogFilter(request);
      }
      else if(action.equals(LocatePropertyNames.CLEAR_ORDER_GUIDE_FILTER_ACTION)) {
    	  ae = StoreItemCatalogMgrLogic.clearOrderGuideFilter(request);
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
//catalog link start
String catView = request.getParameter("showCatalog");       
if(catView != null && catView.equals("y")){
   		mappingAction = "showCat";
    	String catToView = request.getParameter("catalogToView");
    	ActionForward actionForward = mapping.findForward(mappingAction);
    	ActionForward newActionForward = new ActionForward(actionForward);
    	newActionForward.setPath(actionForward.getPath() + 
    			"&locateStoreCatalogForm.searchCatalogField=" + catToView +
    			"&showCatalog=yy&locateStoreCatalogForm.name=STORE_ITEM_CATALOG_FORM" +
    			"&locateStoreCatalogForm.searchCatalogType=catalogId" +
    			"&jspSubmitIdent=%23LocateStoreCatalogForm" +
    			"&locateStoreCatalogForm.property=catalogFilter");
        return newActionForward;
    }
//catalog link end
    
    return (mapping.findForward(mappingAction));

  }


}

