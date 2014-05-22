/*
 * LocateUploadItemLogic.java
 *
 * Created on October 13, 2005, 1:38 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.cleanwise.view.logic;

import org.apache.commons.beanutils.BeanUtils;
import java.util.List;
import java.util.Vector;
import java.util.LinkedList;
import java.util.ArrayList;
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
import java.io.FileNotFoundException;
import com.cleanwise.view.forms.LocateUploadItemForm;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author Ykupershmidt
 */
public class LocateUploadItemLogic {
 public static ActionErrors processAction(HttpServletRequest request, 
         StorePortalForm baseForm, String action)
    	throws Exception
    {
    LocateUploadItemForm pForm = null;
    try {
      ActionErrors ae = new ActionErrors(); 
      HttpSession session  = request.getSession();
      if("initSearch".equals(action)) {
        ae = initSearch(request,baseForm);
        return ae;
      }
      pForm = baseForm.getLocateUploadItemForm();
      pForm.setLocateManufFl(false);
      pForm.setLocateDistFl(false);
      int myLevel = pForm.getLevel()+1;
      //pForm.setLevel(myLevel);
      //pForm = (LocateStoreAccountForm) 
      //  session.getAttribute(SessionAttributes.SEARCH_FORM.LOCATE_STORE_ACCOUNT_FORM);
      //Process manufacturer search actions
      String submitFormIdent = request.getParameter("jspSubmitIdent");
      int ind = -1;
      if(submitFormIdent!=null) {
        for(int ii=0; ii<myLevel; ii++) {
          ind = submitFormIdent.indexOf("#",ind+1);
          if(ind<0) break;
        }
      }
      if(ind>=0 &&
        submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_MANUF_FORM,ind)>=0) {     
        ae = LocateStoreManufLogic.processAction(request,pForm, action);
        if(ae.size()>0) {
          pForm.setLocateManufFl(true);
          return ae;
        }
        if("Return Selected".equals(action)) {
        	action = "manufSearchDone";
        } else if("Cancel".equals(action)) {
          pForm.getLocateStoreManufForm().setLocateManufFl(false);
          action = "do nothing";
        } else {
          pForm.setLocateManufFl(true);
          return ae;
        }
      } 
      if(ind>=0 &&
        submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_DIST_FORM,ind)>=0) {     
        ae = LocateStoreDistLogic.processAction(request,pForm, action);
        if(ae.size()>0) {
          pForm.setLocateDistFl(true);
          return ae;
        }
        if("Return Selected".equals(action)) {
          action = "distSearchDone";
        } else if("Cancel".equals(action)) {
          pForm.getLocateStoreDistForm().setLocateDistFl(false);
          action = "do nothing";
        } else {
          pForm.setLocateDistFl(true);
          return ae;
        }
      } 
    pForm.setUploadSkusToReturn(null);    
    if("Cancel".equals(action)) {
      ae = returnNoValue(request,pForm);
    } else if("Search".equals(action)) {
      ae = search(request,pForm);
    } else if("Return Selected".equals(action)) {
      ae = returnSelected(request,pForm);
    } else if("Locate Manufacturer".equals(action)) {
      ae = LocateStoreManufLogic.processAction(request,pForm,"initSearch");
      if(ae.size()==0) {
        pForm.getLocateStoreManufForm().setLocateManufFl(true);
      }
    } else if("manufSearchDone".equals(action)) {
      ae = LocateStoreManufLogic.setFilter(request,pForm);
    } else if("Clear Manufacturer Filter".equals(action)) {
      pForm.setManufFilter(null);
    } else if("Locate Distributor".equals(action)) {
      ae = LocateStoreDistLogic.processAction(request,pForm,"initSearch");
      if(ae.size()==0) {
        pForm.getLocateStoreDistForm().setLocateDistFl(true);
      }
    } else if("distSearchDone".equals(action)) {
      ae = LocateStoreDistLogic.setFilter(request,pForm);
    } else if("Clear Distributor Filter".equals(action)) {
      pForm.setDistFilter(null);
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
    ItemInformation itemInfEjb = factory.getItemInformationAPI();

        
    CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
    StoreData storeD = appUser.getUserStore();
    if(storeD==null) {
      ae.add("error",new ActionError("error.simpleGenericError","No store info"));
      return ae;
    }
    int storeId = storeD.getBusEntity().getBusEntityId();
    
    LocateUploadItemForm pForm = baseForm.getLocateUploadItemForm();
    if(pForm==null) {
      pForm = new LocateUploadItemForm();
      pForm.setLevel(1);
      baseForm.setLocateUploadItemForm(pForm);
    }
    //baseForm.setEmbeddedForm(pForm);
    
    UploadDataVector uploadDV =
        itemInfEjb.getXlsTables(storeId,null,false);    
    pForm.setTableList(uploadDV);
    //pForm.setUploadSkus(null);
    //pForm.setUploadSkusToReturn(null);
    //pForm.setUploadTable(null);
    
    return ae;
 }

 
 public static ActionErrors returnNoValue(HttpServletRequest request,
                      LocateUploadItemForm pForm)					    
    	throws Exception
    {
    ActionErrors ae = new ActionErrors();
    HttpSession session  = request.getSession();
    pForm.setUploadSkusToReturn(null);
    return ae;
 }
 
  public static ActionErrors search(HttpServletRequest request,
            LocateUploadItemForm pForm)
             throws Exception {
    ActionErrors ae = new ActionErrors();  
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    ItemInformation itemInfEjb = factory.getItemInformationAPI();
    int uploadId = pForm.getUploadId();
    UploadData uploadD = null;
    pForm.setUploadTable(null);
    UploadDataVector tableList  = pForm.getTableList();
    if(tableList!=null) {
      for(Iterator iter=tableList.iterator(); iter.hasNext();) {
        UploadData uD = (UploadData) iter.next();
        if(uD.getUploadId()==uploadId) {
          uploadD = uD;
          break;
        }
      }
    }
    if(uploadD==null) {
      ae.add("error",new ActionError("error.simpleGenericError","No catalog selected"));
      return ae;
    }
    pForm.setUploadTable(uploadD);

    boolean catalogPriceFl = false;
    boolean categoryFl = false;
    boolean distCostFl = false;
    boolean serviceFeeCodeFl = false;
    boolean distNameFl = false;
    boolean distPackFl = false;
    boolean distSkuFl = false;
    boolean distUomFl = false;
    boolean manufNameFl = false;
    boolean manufSkuFl = false;
    boolean mfcpFl = false;
    boolean nsnFl = false;
    boolean psnFl = false;
    boolean shortDescFl = false;
    boolean skuNumFl = false;
    boolean skuPackFl = false;
    boolean skuSizeFl = false;
    boolean skuUomFl = false;
    boolean splFl = false;
    boolean unspscCodeFl = false;
    
    boolean baseCostFl = false;
    boolean genManufNameFl = false;
    boolean genManufSkuFl = false;
    boolean distUomMultFl = false;
    boolean taxExemptFl = false;
    boolean specialPermissionFl = false;
    boolean customerSkuFl = false;
    boolean allUploadDistributorsIsEmpty = true;
    
    boolean showDistCostFl = false; //SVC

    UploadSkuViewVector uploadSkus = itemInfEjb.getMatchedUploadSkus(uploadId, null);
    boolean splFilterYesFl = 
         (pForm.getSplFilter()==LocateUploadItemForm.SPL_FILTER_YES)? true:false;
    boolean splFilterNoFl = 
         (pForm.getSplFilter()==LocateUploadItemForm.SPL_FILTER_NO)? true:false;
    boolean distFilterFl = 
         (pForm.getDistFilter()!=null && pForm.getDistFilter().size()>0)? true:false;
    boolean manufFilterFl = 
         (pForm.getManufFilter()!=null && pForm.getManufFilter().size()>0)? true:false;   
   // if(pForm.getSplFilter()!=LocateUploadItemForm.SPL_FILTER_ANY ||
   //    (pForm.getDistFilter()!=null && pForm.getDistFilter().size()>0) ||
   //    (pForm.getManufFilter()!=null && pForm.getManufFilter().size()>0)) {
    
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    StoreData storeD = appUser.getUserStore();
    int storeId = storeD.getBusEntity().getBusEntityId();
    Store storeEjb = factory.getStoreAPI();
    StoreData sd = storeEjb.getStore(storeId);
    PropertyDataVector prop = sd.getMiscProperties();
    for (int ii = 0; ii < prop.size(); ii++) {
        PropertyData pD = (PropertyData) prop.get(ii);
        String propType = pD.getPropertyTypeCd();
        String propValue = pD.getValue();
        if (RefCodeNames.PROPERTY_TYPE_CD.EQUAL_COST_AND_PRICE.equals(propType)) {
        	pForm.setShowDistCostFl(Utility.isTrue(propValue));
        }
    }
    
    for(Iterator iter=uploadSkus.iterator(); iter.hasNext();) {
        UploadSkuView usVw = (UploadSkuView) iter.next();
        UploadSkuData usD = usVw.getUploadSku();
        boolean splVal = Utility.isTrue(usD.getSpl(),false);
        if(splFilterYesFl && !splVal ||
           splFilterNoFl && splVal ) {
          iter.remove();
          continue;
        }
        if(distFilterFl) {
          String distName = usD.getDistName();
          if(!Utility.isSet(distName)) {
            iter.remove();
            continue;
          }
          distName = distName.trim();
          boolean foundFl = false;          
          for(Iterator iter1=pForm.getDistFilter().iterator();iter1.hasNext();) {
            DistributorData distD = (DistributorData) iter1.next();
            String dn = distD.getBusEntity().getShortDesc().trim();
            if(distName.equalsIgnoreCase(dn)) {
              foundFl = true;
              break;
            }
          }
          if(!foundFl) {
            iter.remove();
            continue;
          }
        }
        if(manufFilterFl) {
          String manufName = usD.getManufName();
          if(!Utility.isSet(manufName)) {
            iter.remove();
            continue;
          }
          manufName = manufName.trim();
          boolean foundFl = false;          
          for(Iterator iter1=pForm.getManufFilter().iterator();iter1.hasNext();) {
            ManufacturerData manufD = (ManufacturerData) iter1.next();
            String mn = manufD.getBusEntity().getShortDesc().trim();
            if(manufName.equalsIgnoreCase(mn)) {
              foundFl = true;
              break;
            }
          }
          if(!foundFl) {
            iter.remove();
            continue;
          }
        }
        if(!catalogPriceFl && Utility.isSet(usD.getCatalogPrice())) { 
          catalogPriceFl = true;
        }
        if(!categoryFl && Utility.isSet(usD.getCategory())) { 
          categoryFl = true;
        }
        if(!distCostFl && Utility.isSet(usD.getDistCost())) { 
          distCostFl = true;
        }
        if (!serviceFeeCodeFl && Utility.isSet(usD.getServiceFeeCode())) {
            serviceFeeCodeFl = true; 
        }
        if(!distNameFl && Utility.isSet(usD.getDistName())) { 
          distNameFl = true;
        }
        if(!distPackFl && Utility.isSet(usD.getDistPack())) { 
          distPackFl = true;
        }
        if(!distSkuFl && Utility.isSet(usD.getDistSku())) { 
          distSkuFl = true;
        }
        if(!distUomFl && Utility.isSet(usD.getDistUom())) { 
          distUomFl = true;
        }
        if(!manufNameFl && Utility.isSet(usD.getManufName())) { 
          manufNameFl = true;
        }
        if(!manufSkuFl && Utility.isSet(usD.getManufSku())) { 
          manufSkuFl = true;
        }
        if(!mfcpFl && Utility.isSet(usD.getMfcp())) { 
          mfcpFl = true;
        }
        if(!nsnFl && Utility.isSet(usD.getNsn())) { 
          nsnFl = true;
        }
        if(!psnFl && Utility.isSet(usD.getPsn())) { 
          psnFl = true;
        }
        if(!shortDescFl && Utility.isSet(usD.getShortDesc())) { 
          shortDescFl = true;
        }
        if(!skuNumFl && Utility.isSet(usD.getSkuNum())) { 
          skuNumFl = true;
        }
        if(!skuPackFl && Utility.isSet(usD.getSkuPack())) { 
          skuPackFl = true;
        }
        if(!skuSizeFl && Utility.isSet(usD.getSkuSize())) { 
          skuSizeFl = true;
        }
        if(!skuUomFl && Utility.isSet(usD.getSkuUom())) { 
          skuUomFl = true;
        }
        if(!splFl && Utility.isSet(usD.getSpl())) { 
          splFl = true;
        }
        if(!unspscCodeFl && Utility.isSet(usD.getUnspscCode())) { 
          unspscCodeFl = true;
        }
        if(!baseCostFl && Utility.isSet(usD.getBaseCost())) { 
          baseCostFl = true;
        }
        if(!genManufNameFl && Utility.isSet(usD.getGenManufName())) { 
          genManufNameFl = true;
        }
        if(!genManufSkuFl && Utility.isSet(usD.getGenManufSku())) { 
          genManufSkuFl = true;
        }
        if(!distUomMultFl && Utility.isSet(usD.getDistUomMult())) { 
          distUomMultFl = true;
        }
        if(!taxExemptFl && Utility.isSet(usD.getTaxExempt())) { 
          taxExemptFl = true;
        }
        if (!specialPermissionFl && Utility.isSet(usD.getSpecialPermission())) {
            specialPermissionFl = true;
        }
        if(!customerSkuFl && Utility.isSet(usD.getCustomerSkuNum())) {
            customerSkuFl = true;
        }

        /// Set value of 'allUploadDistributorsIsEmpty' flag to 'false' 
        /// if exists non empty distributors in the upload items.
        if (usD.getDistId() > 0) {
            allUploadDistributorsIsEmpty = false;
        }
    }

    StorePortalForm baseForm = pForm.getBaseForm();

    boolean showBaseCostFlDef = true;
    boolean showDistSkuNumFlDef = true;
    boolean showCustSkuNumFlDef = true;
    boolean showDistUomPackFlDef = true;
    boolean showGenManufFlDef = true;
    boolean showGenManufSkuNumFlDef = true;
    boolean showDistSplFlDef = true;
    boolean showTaxExemptFlDef = true;
    boolean showSpecialPermissionFl = true;

    if(baseForm instanceof StoreItemCatalogMgrForm) {
      StoreItemCatalogMgrForm itemCatalogForm = (StoreItemCatalogMgrForm) baseForm;
      showBaseCostFlDef = itemCatalogForm.getShowBaseCostFlDef();
      showDistSkuNumFlDef = itemCatalogForm.getShowDistSkuNumFlDef();
      showCustSkuNumFlDef = itemCatalogForm.getShowCustSkuNumFlDef();
      showDistUomPackFlDef = itemCatalogForm.getShowDistUomPackFlDef();
      showGenManufFlDef = itemCatalogForm.getShowGenManufFlDef();
      showGenManufSkuNumFlDef = itemCatalogForm.getShowGenManufSkuNumFlDef();
      showDistSplFlDef = itemCatalogForm.getShowDistSplFlDef();
      showTaxExemptFlDef = itemCatalogForm.getShowTaxExemptFlDef();
      showSpecialPermissionFl = itemCatalogForm.getShowSpecialPermissionFlDef();
    }

    pForm.setCatalogPriceFl(catalogPriceFl);
    pForm.setCategoryFl(categoryFl);
    pForm.setDistCostFl(distCostFl);
    pForm.setServiceFeeCodeFl(serviceFeeCodeFl);
    pForm.setDistNameFl(distNameFl);
    pForm.setDistPackFl(distPackFl & showDistUomPackFlDef);
    pForm.setDistSkuFl(distSkuFl & showDistSkuNumFlDef);
    pForm.setDistUomFl(distUomFl & showDistUomPackFlDef);
    pForm.setManufNameFl(manufNameFl);
    pForm.setManufSkuFl(manufSkuFl);
    pForm.setMfcpFl(false);
    pForm.setNsnFl(false);
    pForm.setPsnFl(false);
    pForm.setShortDescFl(shortDescFl);
    pForm.setSkuNumFl(skuNumFl);
    pForm.setSkuPackFl(skuPackFl);
    pForm.setSkuSizeFl(skuSizeFl);
    pForm.setSkuUomFl(skuUomFl);
    pForm.setSplFl(splFl & showDistSplFlDef);
    pForm.setUnspscCodeFl(false);
    pForm.setBaseCostFl(baseCostFl & showBaseCostFlDef);
    pForm.setGenManufNameFl(genManufNameFl & showGenManufFlDef);
    pForm.setGenManufSkuFl(genManufSkuFl & showGenManufSkuNumFlDef);
    pForm.setDistUomMultFl(distUomMultFl & showDistUomPackFlDef);
    pForm.setTaxExemptFl(taxExemptFl & showTaxExemptFlDef);
    pForm.setSpecialPermissionFl(specialPermissionFl & showSpecialPermissionFl);
    pForm.setCustomerSkuFl(customerSkuFl);
    pForm.setUseOnlyIgnoreDistributorMatchingFl(allUploadDistributorsIsEmpty);
    if (allUploadDistributorsIsEmpty) {
        pForm.setDistProc(LocateUploadItemForm.DISTRIBUTOR_IGNORE);
    }

    pForm.setUploadSkus(uploadSkus);
    
    return ae;
    }


 
  public static ActionErrors returnSelected(HttpServletRequest request,
            LocateUploadItemForm pForm)
             throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    UploadSkuViewVector uploadSkus = pForm.getUploadSkus();
    UploadSkuViewVector uploadSkusToReturn = new UploadSkuViewVector();

    for(Iterator iter=uploadSkus.iterator(); iter.hasNext();) {
      UploadSkuView usVw = (UploadSkuView) iter.next();
      if(usVw.getSelectFlag()) {
        uploadSkusToReturn.add(usVw);
      }
    }
    pForm.setUploadSkusToReturn(uploadSkusToReturn);
    return ae;
    }
  

  
  public static ActionErrors clearFilter(HttpServletRequest request,StorePortalForm pForm) throws Exception{
      HttpSession session = request.getSession();
      //LocateStoreAccountForm acctForm = (LocateStoreAccountForm) 
      //session.getAttribute(SessionAttributes.SEARCH_FORM.LOCATE_STORE_ACCOUNT_FORM);
      LocateUploadItemForm luiForm = pForm.getLocateUploadItemForm();
      org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(luiForm.getName()),luiForm.getProperty(),null);
      return new ActionErrors();
  }
            
  public static ActionErrors setFilter(HttpServletRequest request,StorePortalForm pForm)
             throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    LocateUploadItemForm luiForm = pForm.getLocateUploadItemForm();
    UploadSkuViewVector uploadSkus = luiForm.getUploadSkusToReturn();
    BeanUtils.setProperty(session.getAttribute(luiForm.getName()),luiForm.getProperty(),uploadSkus);
    luiForm.setLocateUploadItemFl(false);
    return ae;
    }
}



