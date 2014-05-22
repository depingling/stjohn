
package com.cleanwise.view.logic;

import java.util.List;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.ItemMgrDistrAssignForm;
import com.cleanwise.view.forms.ItemMgrMasterForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.RefCodeNames;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * <code>CatalogMgrLogic</code> implements the logic needed to
 * manipulate catalog records.
 *
 * @author durval
 */
public class ItemMgrDistrAssignLogic {

  /**
   * <code>init</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static void init(HttpServletRequest request,
			  ActionForm form)
    throws Exception {

    ItemMgrDistrAssignForm sForm = (ItemMgrDistrAssignForm)form;
    sForm.setSearchType("distributorId");
    sForm.reset();
    
    initConstantList(request);
    return;
  }


    /**
     *  <code>initConstantList</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@exception  Exception  if an error occurs
     */    
    public static void initConstantList(HttpServletRequest request)
	throws Exception {

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }    

        ListService listServiceEjb = factory.getListServiceAPI();

        if (null == session.getAttribute("Item.uom.vector")) {
	    RefCdDataVector uomv =
		listServiceEjb.getRefCodesCollection("ITEM_UOM_CD");
	    session.setAttribute("Item.uom.vector", uomv);
	}

    }
          
  
  /**
   * <code>search</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors search(HttpServletRequest request,
			    ActionForm form)
    throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    ItemMgrDistrAssignForm sForm = (ItemMgrDistrAssignForm)form;
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (null == factory) {
        throw new Exception("Without APIAccess.");
    }

    Distributor distributorEjb   = factory.getDistributorAPI();

    String searchField = sForm.getSearchField();
    String searchType = sForm.getSearchType();
    boolean containsFlag = sForm.isContainsFlag();
    List distrList = new LinkedList();
    if(searchField != null && searchField.trim().length()>0 &&searchType != null) {
      if(searchType.equalsIgnoreCase("distrId")) {
        int distrId = 0;
        try {
          distrId = Integer.parseInt(searchField);
        } catch (NumberFormatException exc) {
          ae.add("error", new ActionError("item.search.wrong_distributor"));
          return ae;
        }
        try {
          DistributorData distrData = distributorEjb.getDistributor(distrId);
          distrList = new LinkedList();
          distrList.add(distrData);
        } catch (DataNotFoundException exc) {
        }
      } else {
      int matchType=(searchType.equalsIgnoreCase("distrNameContains"))?
          Distributor.CONTAINS_IGNORE_CASE:Distributor.BEGINS_WITH_IGNORE_CASE;
          distrList = distributorEjb.getDistributorByName(searchField,matchType,Distributor.ORDER_BY_NAME);
      }
    }
    sForm.setResultList(distrList);
    sForm.setSearchField(searchField);
    sForm.setSearchType(searchType);
    fillSkus(request, sForm);
    return ae;
  }


  /**
   * <code>search</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static void searchAll(HttpServletRequest request,
			    ActionForm form)
    throws Exception {

    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    ItemMgrDistrAssignForm sForm = (ItemMgrDistrAssignForm)form;
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (null == factory) {
        throw new Exception("Without APIAccess.");
    }

    Distributor distributorEjb   = factory.getDistributorAPI();
    List distrList = distributorEjb.getAllDistributors(Distributor.ORDER_BY_NAME);

    sForm.setResultList(distrList);
    sForm.setSearchField("");
    sForm.setSearchType("");
    fillSkus(request, sForm);
  }

  /**
   * <code>Go To Page</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static void goPage(HttpServletRequest request,
			    ActionForm form)
    throws Exception {

    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    String pageNumS = request.getParameter("page");
    int pageNum = Integer.parseInt(pageNumS);
    ItemMgrDistrAssignForm sForm = (ItemMgrDistrAssignForm)form;
    int pageSize = sForm.getPageSize();
    sForm.setOffset(pageNum*pageSize);
    fillSkus(request, sForm);

  }
  /*
  * Fill up Skus
  */
  private static void fillSkus(HttpServletRequest request,
               ItemMgrDistrAssignForm pForm)
    throws Exception
  {
    HttpSession session = request.getSession();
    ItemMgrMasterForm itemForm = (ItemMgrMasterForm) session.getAttribute("ITEM_MASTER_FORM");

    if(itemForm != null) {
      ProductData productD = itemForm.getProduct();
      ItemMappingDataVector itemMappingDV = productD.getDistributorMappings();
      List distrList = pForm.getResultList();
      int size = pForm.getPageSize();
      int offset = pForm.getOffset();
      for(int ii=0; ii<size && ii+offset<distrList.size(); ii++) {
        DistributorData distrD = (DistributorData) distrList.get(ii+offset);
        int distrId = distrD.getBusEntity().getBusEntityId();
        int jj=0;
        for(; jj<itemMappingDV.size(); jj++) {
          ItemMappingData itemMappingD = (ItemMappingData) itemMappingDV.get(jj);
          if(distrId == itemMappingD.getBusEntityId()) {
            pForm.setSkuNumElement(ii,itemMappingD.getItemNum());
            pForm.setUomElement(ii,itemMappingD.getItemUom());
            
            String uomCd = itemMappingD.getItemUom();
            if(null == uomCd) uomCd = new String("");
            RefCdDataVector uomCdV = (RefCdDataVector)session.getAttribute("Item.uom.vector");
            boolean isPredefined = false;
            for(int i = 0; i < uomCdV.size(); i++) {
                if(uomCd.equals(((RefCdData)uomCdV.get(i)).getValue())) {
                    isPredefined = true;
                    break;
                }
            }
            if(true == isPredefined || "".equals(uomCd)) {
                pForm.setProductUomElement(ii, uomCd);
            }
            else {
                pForm.setProductUomElement(ii, RefCodeNames.ITEM_UOM_CD.UOM_OTHER);
            }
            //pForm.setProductUomElement(ii,itemMappingD.getItemUom());            
            
            pForm.setPackElement(ii,itemMappingD.getItemPack());            
            break;
          }
        }
        if(jj==itemMappingDV.size()){
          pForm.setSkuNumElement(ii,"");
            pForm.setUomElement(ii,"");
            pForm.setProductUomElement(ii,"");            
            pForm.setPackElement(ii,"");                      
        }
      }
    }
  }

  /*
  */
  private static void processSelection(ItemMgrDistrAssignForm pForm) {
    List distrList = pForm.getResultList();
    String[] skus = pForm.getSkuNum();
    String[] uoms = pForm.getUom();
    String[] productUoms = pForm.getProductUom();
    String[] packs = pForm.getPack();
    int pageSize = pForm.getPageSize();
    int offset = pForm.getOffset();
    Hashtable retObject = pForm.getRetObject();
    if(retObject==null) {
      retObject = new Hashtable();
    }
    for(int ii=0; ii<pageSize && ii+offset < distrList.size(); ii++) {
      DistributorData dD = (DistributorData) distrList.get(ii+offset);
      int id = dD.getBusEntity().getBusEntityId();
      Integer idI = new Integer(id);
      Object dd = retObject.get(idI);
      if(dd != null) {
        retObject.remove(idI);
      }
      if(skus[ii]!=null && skus[ii].trim().length()>0 
        || uoms[ii]!=null && uoms[ii].trim().length()>0 
        || packs[ii]!=null && packs[ii].trim().length()>0 ) {
        
        DistributorItemDescData dItemDescD = DistributorItemDescData.createValue();
        dItemDescD.setDistributorId(id);
        dItemDescD.setItemNum(skus[ii]);
        dItemDescD.setItemUom(uoms[ii]);
        dItemDescD.setItemPack(packs[ii]);
        //retObject.put(idI,skus[ii]);
        retObject.put(idI, dItemDescD);
      }
    }
    pForm.setRetObject(retObject);
  }

   public static void addUpdate(
         HttpServletRequest request,
         ActionForm form)
         throws Exception {

    ItemMgrDistrAssignForm sForm = (ItemMgrDistrAssignForm) form;

    //System Catalog Id
    HttpSession session = request.getSession();
    processSelection(sForm);
    session.setAttribute("lookupValue",sForm.getRetObject());
    return;
  }


}





