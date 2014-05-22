/*
 * LocateCatalogItemLogic.java
 *
 * Created on October 12, 2005, 12:07 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.cleanwise.view.logic;

import java.util.List;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import java.util.Iterator;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import com.cleanwise.view.forms.LocateCatalogItemForm;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author Ykupershmidt
 */
public class LocateCatalogItemLogic {
  
  /** Creates a new instance of LocateCatalogItemLogic */
  public LocateCatalogItemLogic() {
  }
  
  public static ActionErrors processAction(HttpServletRequest request, 
         StorePortalForm baseForm, String action)
    	throws Exception
    {
    LocateCatalogItemForm pForm = null;
    //int myLevel = baseForm.getLevel()+1;
    //baseForm.setLevel(myLevel);
    
    try {
    ActionErrors ae = new ActionErrors();
    HttpSession session  = request.getSession();
    if("initSearch".equals(action)) {
      ae = initSearch(request,baseForm);
      return ae;
    }
    
    pForm = baseForm.getLocateCatalogItemForm();

    if("Cancel".equals(action)) {
      ae = returnNoValue(request,pForm);
    } else if("Search".equals(action)) {
      ae = search(request,pForm);
    } else if("Return Selected".equals(action)) {
      ae = returnSelected(request,pForm);
    }
    
    return ae;
    }finally {
      if(pForm!=null)  pForm.reset(null, null);
    }
 }


 
 public static ActionErrors initSearch(HttpServletRequest request, StorePortalForm baseForm)					    
    	throws Exception
    {
    ActionErrors ae = new ActionErrors();
    HttpSession session  = request.getSession();

    APIAccess factory = new APIAccess();
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Manufacturer manufEjb = factory.getManufacturerAPI();

    CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
    StoreData storeD = appUser.getUserStore();
    if(storeD==null) {
      ae.add("error",new ActionError("error.simpleGenericError","No store info"));
      return ae;
    }
    
    LocateCatalogItemForm pForm = baseForm.getLocateCatalogItemForm();
    if(pForm==null) {
      pForm = new LocateCatalogItemForm();
      baseForm.setLocateCatalogItemForm(pForm);
    }
    //baseForm.setEmbeddedForm(pForm);
    
    pForm.setLoginStore(storeD);
    pForm.setStore(storeD);
        
    return ae;
 }

 
 public static ActionErrors returnNoValue(HttpServletRequest request,
                      LocateCatalogItemForm pForm)					    
    	throws Exception
    {
    ActionErrors ae = new ActionErrors();
    HttpSession session  = request.getSession();
    pForm.setItemsToReturn(null);
    return ae;
 }
 
  public static ActionErrors search(HttpServletRequest request,
            LocateCatalogItemForm pForm)
             throws Exception {

      HttpSession session = request.getSession();
      ActionErrors ae = new ActionErrors();
      APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
      CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
        
	     pForm.setItemsSelected(new CatalogItemDescViewVector());
       pForm.setDistInfoFlag(false);
      CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
      StoreData storeD = pForm.getStore();
      if(storeD==null) {
        ae.add("error",new ActionError("error.simpleGenericError","No store info"));
        return ae;
      }
      
      int storeId = storeD.getBusEntity().getBusEntityId();
            
      CatalogDataVector catalogDV = 
          catalogInfEjb.getCatalogsCollectionByBusEntity(storeId,RefCodeNames.CATALOG_TYPE_CD.STORE);

      CatalogData cD = null;
      for(Iterator iter=catalogDV.iterator(); iter.hasNext();) {
        cD = (CatalogData) iter.next();
        if(RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(cD.getCatalogStatusCd())) {
          break;
        }
      }
      if(cD==null) {
        ae.add("error",new ActionError("error.simpleGenericError","No store catalog found"));
        return ae;
      }

    //get the search data from the form
	    String category = pForm.getCategoryTempl();
	    String shortDesc = pForm.getShortDescTempl();
	    String longDesc = pForm.getLongDescTempl();
	    String itemPropName = pForm.getItemPropertyName();
      String itemPropTempl = pForm.getItemPropertyTempl();
	    String manuNameTempl = pForm.getManuNameTempl();
      String distNameTempl = pForm.getDistNameTempl();
     	String skuNumber = pForm.getSkuTempl();
      String skuType = pForm.getSkuType();
      if(!Utility.isSet(skuType)) skuType = SearchCriteria.STORE_SKU_NUMBER;
      if(SearchCriteria.DISTRIBUTOR_SKU_NUMBER.equals(skuType)) pForm.setDistInfoFlag(true);
      if(pForm.getDistInfoRequest())  pForm.setDistInfoFlag(true);
      pForm.setSkuType(skuType);
      //Create a set of filters
      LinkedList searchConditions = new LinkedList();

      SearchCriteria scStore = new
		        SearchCriteria(SearchCriteria.STORE_ID,
			         SearchCriteria.EXACT_MATCH,new Integer(storeId));
	         searchConditions.add(scStore);
	     //Category
	     if(Utility.isSet(category)) {
	       SearchCriteria sc = new
		        SearchCriteria(SearchCriteria.CATALOG_CATEGORY,
			         SearchCriteria.CONTAINS_IGNORE_CASE,category);
	         searchConditions.add(sc);
	     }
      //Short Desc
      if(Utility.isSet(shortDesc)){
         SearchCriteria sc = new
           SearchCriteria(SearchCriteria.PRODUCT_SHORT_DESC,
                 SearchCriteria.CONTAINS_IGNORE_CASE,shortDesc);
            searchConditions.add(sc);
      }

      //Long Desc
      if(Utility.isSet(longDesc)){
         SearchCriteria sc = new
         SearchCriteria(SearchCriteria.PRODUCT_LONG_DESC,
                 SearchCriteria.CONTAINS_IGNORE_CASE,longDesc);
            searchConditions.add(sc);
      }

      //Item Meta Property
      if(Utility.isSet(itemPropName) && Utility.isSet(itemPropTempl)) {
        SearchCriteria sc = new
        SearchCriteria(SearchCriteria.ITEM_META+itemPropName,
                SearchCriteria.CONTAINS_IGNORE_CASE,itemPropTempl);
           searchConditions.add(sc);
      }

     	//Manufacturer
	     if(Utility.isSet(manuNameTempl)) {
        SearchCriteria sc = new
        SearchCriteria(SearchCriteria.MANUFACTURER_SHORT_DESC,
                SearchCriteria.CONTAINS_IGNORE_CASE,manuNameTempl);
           searchConditions.add(sc);
     	}
        
     	//Distributor
	     if(Utility.isSet(distNameTempl)) {
        SearchCriteria sc = new
        SearchCriteria(SearchCriteria.DISTRIBUTOR_SHORT_DESC,
                SearchCriteria.CONTAINS_IGNORE_CASE,distNameTempl);
           searchConditions.add(sc);
        pForm.setDistInfoFlag(true);
     	}

     	//sku
          
     	if(Utility.isSet(skuNumber)) {
       	SearchCriteria sc = new 
		          SearchCriteria(skuType,
				              SearchCriteria.EXACT_MATCH, skuNumber);
                    searchConditions.add(sc);
	     }
          
	    CatalogItemDescViewVector items = catalogInfEjb.searchCatalogItems(searchConditions);
      pForm.setItemsSelected(items);
      return ae;
      
    }


 
  public static ActionErrors returnSelected(HttpServletRequest request,
            LocateCatalogItemForm pForm)
             throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    int[] selected = pForm.getSelectedItemIds();
    CatalogItemDescViewVector itemVwV = pForm.getItemsSelected();
    CatalogItemDescViewVector retItemVwV = new CatalogItemDescViewVector();
    for(Iterator iter=itemVwV.iterator(); iter.hasNext();) {
      ItemView iVw = (ItemView) iter.next();
      boolean found = false;
      for(int ii=0; ii<selected.length; ii++) {
        if(iVw.getItemId()==selected[ii]) {
          if(!found) {
            found = true;
            retItemVwV.add(iVw);
          }
          selected[ii]=0; 
        }
      }
    }
    pForm.setItemsToReturn(retItemVwV);
    return ae;
    }
            
  
  public static ActionErrors clearFilter(HttpServletRequest request,StorePortalForm pForm) throws Exception{
      HttpSession session = request.getSession();
      LocateCatalogItemForm form = pForm.getLocateCatalogItemForm();
      org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(form.getName()),form.getProperty(),null);
      return new ActionErrors();
  }
            
  public static ActionErrors setFilter(HttpServletRequest request,StorePortalForm pForm)
             throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    LocateCatalogItemForm form = pForm.getLocateCatalogItemForm();
    Object o = form.getItemsToReturn();
    org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(form.getName()),form.getProperty(),o);
    form.setLocateItemFl(false);
    return ae;
    }
}




