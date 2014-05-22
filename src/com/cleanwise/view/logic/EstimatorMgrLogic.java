
package com.cleanwise.view.logic;

import java.util.*;
import java.util.List;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.EstimatorMgrForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;

/**
 *  <code>SpendingEstimatorLogic</code>
 *
 *@author     YKupershmidt
 *@created    October 13, 2004
 */
public class EstimatorMgrLogic {
    /**
     *  <code>init</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void init(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       EstimatorMgrForm pForm = (EstimatorMgrForm) form;
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       int userId = appUser.getUser().getUserId();
       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
       
    }


    public static ActionErrors initCleaningProcedures(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();        
       EstimatorMgrForm pForm = (EstimatorMgrForm) form;
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       int userId = appUser.getUser().getUserId();
       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
       CleaningProcDataVector cleaningProcDV = 
           estimationEjb.getCleaningProcedures();
       pForm.setCleaningProcedures(cleaningProcDV);
       return ae;
    }
    
    public static ActionErrors loadProcedureProducts(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();        
       EstimatorMgrForm pForm = (EstimatorMgrForm) form;
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       int userId = appUser.getUser().getUserId();
       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
       int procedureId = pForm.getCleanigProcSelected();              
       ProdApplJoinViewVector prodApplJVwV = 
           estimationEjb.getProcedureProducts(procedureId);
       pForm.setProcedureProducts(prodApplJVwV);
       return ae;
    }

    
    public static ActionErrors initProducts(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();        
       EstimatorMgrForm pForm = (EstimatorMgrForm) form;
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       int userId = appUser.getUser().getUserId();
       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
       ProdUomPackJoinViewVector pupJVwV = 
           estimationEjb.getEstimatorProducts();
       pupJVwV = sortByCategorySku(pupJVwV);
       pForm.setProducts(pupJVwV);
       return ae;
    }       

    public static ActionErrors setProductFilter(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();        
       EstimatorMgrForm pForm = (EstimatorMgrForm) form;
       ProdUomPackJoinViewVector pupJVwV = pForm.getProducts();
       if(pupJVwV==null) initProducts(request,form);
       
       setFilter(ae,pForm);
       return ae;
    }


  private static void setFilter(ActionErrors ae, EstimatorMgrForm pForm)
  {
     ProdUomPackJoinViewVector pupJVwV = pForm.getProducts();
     String categoryFilter = pForm.getCategoryFilter();
     if(!Utility.isSet(categoryFilter)) { 
       categoryFilter = null;
     } else {
       categoryFilter = categoryFilter.toLowerCase().trim();
     }
     String shortDescFilter = pForm.getShortDescFilter();
     if(!Utility.isSet(shortDescFilter)) {
       shortDescFilter = null;
     } else {
       shortDescFilter = shortDescFilter.toLowerCase().trim();
     }
     String skuNumFilter = pForm.getSkuFilter();
     int skuNumFilterInt = 0;
     if(!Utility.isSet(skuNumFilter)) {
       skuNumFilter = null;
     } else {
       skuNumFilter = skuNumFilter.toLowerCase().trim();
     }
     String skuTypeFilter = pForm.getSkuTypeFilter();
     boolean cwSkuTypeFl = false;
     boolean manuSkuTypeFl = false;
     boolean itemIdFl = false;
     if(Utility.isSet(skuTypeFilter)) {
       if("System".equals(skuTypeFilter)) {
         cwSkuTypeFl =true;        
         try {
          skuNumFilterInt = Integer.parseInt(skuNumFilter);
         } catch (Exception exc) {}
       } else if ("Manufacturer".equals(skuTypeFilter)) {
         manuSkuTypeFl = true;
       } else if ("Id".equals(skuTypeFilter)) {
         itemIdFl = true;
         try {
          skuNumFilterInt = Integer.parseInt(skuNumFilter);}
         catch (Exception exc) {}
       }
     }
     String manuFilter = pForm.getManuFilter();
     if(!Utility.isSet(manuFilter)) {
       manuFilter = null;
     } else {
       manuFilter = manuFilter.toLowerCase().trim();
     }

     ProdUomPackJoinViewVector filteredProducts = new ProdUomPackJoinViewVector();
     for(Iterator iter = pupJVwV.iterator(); iter.hasNext();) {
       ProdUomPackJoinView pupJVw = (ProdUomPackJoinView) iter.next();
       OrderGuideItemDescData ogidD = pupJVw.getOrderGuideItem();
       boolean pickFl = true;
       if(categoryFilter!=null) {
         String category = ogidD.getCategoryDesc();
         if(category==null || category.toLowerCase().indexOf(categoryFilter)<0) {
           pickFl = false;
         }
       }
       if(shortDescFilter!=null) {
         String shortDesc = ogidD.getShortDesc();
         if(shortDesc==null || shortDesc.toLowerCase().indexOf(shortDescFilter)<0) {
           pickFl = false;
         }
       }
       if(manuFilter!=null) {
         String manu = ogidD.getManufacturerCd();
         if(manu==null || manu.toLowerCase().indexOf(manuFilter)<0) {
           pickFl = false;
         }
       }
       if(skuNumFilter!=null) {
         if(cwSkuTypeFl) {
           try {
             String skuNum = ogidD.getCwSKU();
             int skuNumInt = Integer.parseInt(skuNum);
             if(skuNumInt!=skuNumFilterInt) {
               pickFl = false;
             }
           }catch(Exception exc) {
             pickFl = false;
           }
         } else if(manuSkuTypeFl) {
           String skuNum = ogidD.getCwSKU();
           if(!skuNum.equalsIgnoreCase(skuNumFilter)) {
             pickFl = false;
           }            
         } else if(itemIdFl) {
           int id = ogidD.getItemId();
           if(id!=skuNumFilterInt) {
             pickFl = false;
           }
         }
       }
       if(pickFl) {
         filteredProducts.add(pupJVw);
       }
     }
     pForm.setFilteredProducts(filteredProducts);
}

    public static ActionErrors updateProducts(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();        
       EstimatorMgrForm pForm = (EstimatorMgrForm) form;
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
       ProdUomPackJoinViewVector pupJVwV = pForm.getFilteredProducts();

       ProductUomPackDataVector productsToUpdate = 
                                         new ProductUomPackDataVector();
       ArrayList newUnitSizeAL = new ArrayList();
       ArrayList newUnitCdAL = new ArrayList();
       ArrayList newPackQtyAL = new ArrayList();
       boolean errorFl = false;
       for(Iterator iter=pupJVwV.iterator(); iter.hasNext();) {
         ProdUomPackJoinView pupJVw = (ProdUomPackJoinView) iter.next();
         OrderGuideItemDescData ogidD = pupJVw.getOrderGuideItem();
         ProductUomPackData pupD = pupJVw.getProductUomPack();
         String unitSizeS = pupJVw.getUnitSizeInp();
         String unitCd = pupJVw.getUnitCdInp();
         String packQtyS = pupJVw.getPackQtyInp();
         if(!Utility.isSet(unitCd)) {
           String errorMess = "Sku "+ogidD.getCwSKU()+" has empty Size Code";
           ae.add("error",
                new ActionError("error.simpleGenericError",errorMess));
           errorFl = true;
         }
         BigDecimal unitSizeBD = null;
         try {
           double dd = Double.parseDouble(unitSizeS);
           unitSizeBD = new BigDecimal(dd);
         } catch (Exception exc) {
           String errorMess = "Sku "+ogidD.getCwSKU()+" has invalid Model Size value";
           ae.add("error",
                new ActionError("error.simpleGenericError",errorMess));
           errorFl = true;          
         }
         int packQty = 0;
         try {
           packQty = Integer.parseInt(packQtyS);
         } catch (Exception exc) {
           String errorMess = "Sku "+ogidD.getCwSKU()+" has invalid Model Pack value";
           ae.add("error",
                new ActionError("error.simpleGenericError",errorMess));
           errorFl = true;          
         }
         if(!errorFl) {
           boolean updateFl = false;
           if(!unitCd.equals(pupD.getUnitCd())) {
             updateFl = true;
           }
           BigDecimal oldUnitSizeBD = pupD.getUnitSize();
           if(oldUnitSizeBD == null || 
              unitSizeBD.subtract(oldUnitSizeBD).abs().doubleValue()>0.000001) {
             updateFl = true; 
           }
           if(packQty!=pupD.getPackQty()) {
             updateFl = true;
           }
           if(updateFl) {
             productsToUpdate.add(pupD);
             newUnitSizeAL.add(unitSizeBD);
             newUnitCdAL.add(unitCd);
             newPackQtyAL.add(new Integer(packQty));
           }
         }
       }
       if(ae.size()>0) {
         return ae;
       }
       
       for(Iterator iter=productsToUpdate.iterator(),
            unitSizeIter=newUnitSizeAL.iterator(),
            unitCdIter=newUnitCdAL.iterator(),
            packQtyIter=newPackQtyAL.iterator(); iter.hasNext(); ) {
         ProductUomPackData pupD = (ProductUomPackData) iter.next();
         BigDecimal newUnitSizeBD = (BigDecimal) unitSizeIter.next();
         String newUnitCd = (String) unitCdIter.next();
         Integer newPackQtyI = (Integer) packQtyIter.next();
         pupD.setUnitSize(newUnitSizeBD);
         pupD.setUnitCd(newUnitCd);
         pupD.setPackQty(newPackQtyI.intValue());         
       }
       estimationEjb.updateEstimatorProducts(productsToUpdate,userName);
       
       return ae;
   }       

    public static ActionErrors addItem(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();        
       EstimatorMgrForm pForm = (EstimatorMgrForm) form;

       ProdUomPackJoinViewVector pupJVwV = pForm.getProducts();
       if(pupJVwV==null) initProducts(request,form);

       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
       
       String itemIdAddS = request.getParameter("itemIdAdd");
       int itemIdAdd = 0;
       try{
         itemIdAdd = Integer.parseInt(itemIdAddS);
       } catch(Exception exc) {
         String errorMess = "Invalid item id to add. Item id: "+itemIdAddS;
         ae.add("error",
                new ActionError("error.simpleGenericError",errorMess));
         return ae;
       }
       
       pupJVwV = pForm.getProducts();

       for(Iterator iter=pupJVwV.iterator(); iter.hasNext();) {
         ProdUomPackJoinView pupJVw = (ProdUomPackJoinView) iter.next();
         OrderGuideItemDescData ogidD = pupJVw.getOrderGuideItem();
         int itemId = ogidD.getItemId();
         if(itemId==itemIdAdd) {
           String errorMess = "Product already exists. Sku:"+ogidD.getCwSKU();
           ae.add("error",
                new ActionError("error.simpleGenericError",errorMess));
           return ae;
         }
       }
       
       ProductUomPackData productUomPackD = ProductUomPackData.createValue();
       productUomPackD.setItemId(itemIdAdd);
       ProdUomPackJoinView prodUomPackJVw = estimationEjb.addEstimatorProduct(productUomPackD, userName);
       OrderGuideItemDescData orderGuideItemDescD = prodUomPackJVw.getOrderGuideItem();
       String category = orderGuideItemDescD.getCategoryDesc();
       if(category==null) category = "";
       String sku = orderGuideItemDescD.getCwSKU();
       if(sku==null) sku = "";
       int ind = -1;
       for(Iterator iter=pupJVwV.iterator(); iter.hasNext();) {
         ind++;
         ProdUomPackJoinView pupJVw = (ProdUomPackJoinView) iter.next();
         OrderGuideItemDescData ogidD = pupJVw.getOrderGuideItem();
         String cat = ogidD.getCategoryDesc();
         if(cat==null) cat = "";
         int comp = category.compareToIgnoreCase(cat);
         if(comp>0) {
           continue;
         }
         if(comp==0) {
           String ss = ogidD.getCwSKU();
           if(ss==null) ss = "";
           int comp1 = sku.compareToIgnoreCase(ss);
           if(comp1>0) {
             continue;
           }
         }
         break;
       }
       pupJVwV.add(ind,prodUomPackJVw);  
       ProdUomPackJoinViewVector filteredProducts = pForm.getFilteredProducts();
       if(filteredProducts==null) filteredProducts = new ProdUomPackJoinViewVector();
       filteredProducts.add(0,prodUomPackJVw);
       pForm.setFilteredProducts(filteredProducts);
       
       return ae;
   }       

    public static ActionErrors removeItems(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();        
       EstimatorMgrForm pForm = (EstimatorMgrForm) form;
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
       IdVector notRemovedProductIds = new IdVector();
       int[] productSelector = pForm.getProductSelector();
       for(int ii=0; ii<productSelector.length; ii++ ) {
         IdVector productIds = new IdVector();
         int itemId = productSelector[ii];
         productIds.add(new Integer(itemId));
         try {
           estimationEjb.removeEstimatorProducts(productIds);
         } catch (Exception exc) {
           notRemovedProductIds.add(new Integer(itemId));
           productSelector[ii] = 0;
         }
       }
       
       
       ProdUomPackJoinViewVector pupJVwV = pForm.getProducts();
       for(Iterator iter=pupJVwV.iterator(); iter.hasNext();) {
         ProdUomPackJoinView pupJVw = (ProdUomPackJoinView) iter.next();
         OrderGuideItemDescData ogidD = pupJVw.getOrderGuideItem();
         int itemId = ogidD.getItemId();
         boolean flag = false;
         for(int ii=0; ii<productSelector.length; ii++ ) {
           int iId = productSelector[ii];           
           if(iId==itemId) {
             flag = true;
             iter.remove();
             break;
           }
         }
         if(!flag) {
           for(Iterator iter1=notRemovedProductIds.iterator(); iter1.hasNext();) {
             Integer iIdI = (Integer) iter1.next();
             if(itemId==iIdI.intValue()) {
               String errorMess = "Can't delete sku "+ogidD.getCwSKU()+
                 " check whether it is used in models";
               ae.add("error"+itemId,
                    new ActionError("error.simpleGenericError",errorMess));
               break;
             }
           }
         }
       }
       setFilter(ae,pForm);
       return ae;
   }       


    public static ActionErrors saveProductToBuffer(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();        
       EstimatorMgrForm pForm = (EstimatorMgrForm) form;
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
       IdVector notRemovedProductIds = new IdVector();
       int[] productSelector = pForm.getProductSelector();
       if(productSelector.length==0) {
         String errorMess = "No product selected";
         ae.add("error",
              new ActionError("error.simpleGenericError",errorMess));
         return ae;
       }
       if(productSelector.length>1) {
         String errorMess = "Only one product could be selected";
         ae.add("error",
              new ActionError("error.simpleGenericError",errorMess));
         return ae;
       }
       int itemId = productSelector[0];
       ProdApplJoinViewVector prodApplJVwV = estimationEjb.getProductProcedures(itemId); 
       CleaningProcDataVector cleaningProcDV = pForm.getCleaningProcedures();
       if(cleaningProcDV==null) {
         ae = initCleaningProcedures(request,form);
         if(ae.size()>0) {
           return ae;
         }
         cleaningProcDV = pForm.getCleaningProcedures();
       }
       ProdApplJoinViewVector arrangedProdApplJVwV = new ProdApplJoinViewVector();
       CleaningProcDataVector bufferedCleaningProcDV = new CleaningProcDataVector();
       for(Iterator iter=prodApplJVwV.iterator(); iter.hasNext();) {
         ProdApplJoinView pajVw = (ProdApplJoinView) iter.next();
         ProductUomPackData pupD = pajVw.getProductUomPack();
         ProdApplData paD = pajVw.getProdAppl();
         int cleaningProcId = paD.getCleaningProcId();
         if(cleaningProcId==0) {
           arrangedProdApplJVwV.add(pajVw);
           CleaningProcData cpD = CleaningProcData.createValue();
           bufferedCleaningProcDV.add(cpD);
           cpD.setEstimatorPageCd("");
           cpD.setShortDesc("");
         }
       }
       for(Iterator iter=cleaningProcDV.iterator(); iter.hasNext(); ) {
         CleaningProcData cpD = (CleaningProcData) iter.next();
         int cleaningProcId = cpD.getCleaningProcId();
         for(Iterator iter1=prodApplJVwV.iterator(); iter1.hasNext();) {
           ProdApplJoinView pajVw = (ProdApplJoinView) iter1.next();
           ProdApplData paD = pajVw.getProdAppl();
           int cpId = paD.getCleaningProcId();
           if(cpId==cleaningProcId) {
             bufferedCleaningProcDV.add(cpD);
             arrangedProdApplJVwV.add(pajVw);
           }
         }
       }
       
       
       pForm.setBufferedProducts(prodApplJVwV);
       pForm.setBufferedProcedures(bufferedCleaningProcDV);
       pForm.setSelectedPage(111);
       return ae;
   }       

    public static ActionErrors clearBuffer(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();        
       EstimatorMgrForm pForm = (EstimatorMgrForm) form;
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();

       pForm.setBufferedProducts(null);
       pForm.setBufferedProcedures(null);
       return ae;
   }       

   public static ActionErrors loadSchedules(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();        
       EstimatorMgrForm pForm = (EstimatorMgrForm) form;
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
       
       String cleaningScheduleCd = pForm.getScheduleTypeFilter();
       ArrayList cleaningScheduleCdAL = new ArrayList();
       cleaningScheduleCdAL.add(cleaningScheduleCd);
       
       CleaningScheduleJoinViewVector  cleaningScheduleJVwV = 
               estimationEjb.getDefaultCleaningSchedules (cleaningScheduleCdAL);
       pForm.setCleaningSchedules(cleaningScheduleJVwV);
       return ae;
   }       
    
    

    public static ActionErrors saveProcedureProducts(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();        
       EstimatorMgrForm pForm = (EstimatorMgrForm) form;
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
       
       ProdApplJoinViewVector pajVwV = pForm.getProcedureProducts();
       int ind2 = 0;
       for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
         ind2++;
         ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
         ItemData iD = pajVw.getItem();
         String dilutionRateS = pajVw.getDilutionRate();
         BigDecimal dilutionRateBD =  stringToBD (dilutionRateS,false,ae, 
             new BigDecimal(0),null,
             "Dilution rate", ind2);


         String usageRateS = pajVw.getUsageRate();
         BigDecimal usageRateBD =  stringToBD (usageRateS,false,ae, 
           new BigDecimal(0),null,
           "Usage rate", ind2);

       } 

       if(ae.size()>0) {
         return ae;
       }

       ProdApplDataVector prodApplToUpdate = new ProdApplDataVector();
       ind2 = 0;
       for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
         ind2++;
         ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
         ItemData iD = pajVw.getItem();

         String dilutionRateS = pajVw.getDilutionRate();
         BigDecimal dilutionRateBD = null;
         if(Utility.isSet(dilutionRateS)) {
           dilutionRateBD =  new BigDecimal(dilutionRateS);
         }

         String usageRateS = pajVw.getUsageRate();
         BigDecimal usageRateBD =  null;
         if(Utility.isSet(usageRateS)) {
           usageRateBD =  new BigDecimal(usageRateS);
         }

         String numerator = Utility.strNN(pajVw.getUnitCdNumerator());
         String denominator = Utility.strNN(pajVw.getUnitCdDenominator());
         String denominator1 = Utility.strNN(pajVw.getUnitCdDenominator1());

         ProdApplData paD = pajVw.getProdAppl();

         BigDecimal oldDilutionRateBD = Utility.bdNN(paD.getDilutionRate());
         BigDecimal oldUsageRateBD = Utility.bdNN(paD.getUsageRate());
         String oldNumerator = paD.getUnitCdNumerator();
         String oldDenominator = paD.getUnitCdDenominator();
         String oldDenominator1 = paD.getUnitCdDenominator1();
         if(!Utility.bdEqual(usageRateBD,oldUsageRateBD) ||
            !Utility.bdEqual(dilutionRateBD,oldDilutionRateBD) ||
            !numerator.equals(oldNumerator) ||
            !denominator.equals(oldDenominator) ||
            !denominator1.equals(oldDenominator1)
            ) {
           paD.setDilutionRate(dilutionRateBD);
           paD.setUsageRate(usageRateBD);
           paD.setUnitCdNumerator(numerator);
           paD.setUnitCdDenominator(denominator);
           paD.setUnitCdDenominator1(denominator1);
           prodApplToUpdate.add(paD);  
         }
       } 
       
       estimationEjb.updateDefaultProdAppl(prodApplToUpdate, userName);
       
       ProdApplJoinViewVector bufferedPajVwV = pForm.getBufferedProducts();
       if(bufferedPajVwV!=null && bufferedPajVwV.size()>0) {
         ProdApplJoinView pajVw =  (ProdApplJoinView) bufferedPajVwV.get(0);
         ProdApplData paD = pajVw.getProdAppl();
         int itemId = paD.getItemId();
              int[] prodSelector = new int[1];
         prodSelector[0] = itemId;
         pForm.setProductSelector(prodSelector);
         ae = saveProductToBuffer(request,form);
         if(ae.size()>0) {
           return ae;
         }
       }

       return ae;
   }       

    public static ActionErrors deleteProcProducts(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();        
       EstimatorMgrForm pForm = (EstimatorMgrForm) form;
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
       
       int[] procProdSelector = pForm.getProcProdSelector();
       
       ProdApplJoinViewVector pajVwV = pForm.getProcedureProducts();

       IdVector prodApplIdsToDelete = new IdVector();
       int ind2 = -1;
       for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
         ind2++;
         ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
         for(int ii=0; ii<procProdSelector.length; ii++) {
           if(ind2==procProdSelector[ii]) {         
             ProdApplData paD = pajVw.getProdAppl();
             int prodApplId = paD.getProdApplId();
             prodApplIdsToDelete.add(new Integer(prodApplId));
           }
         }
       }
 
       
       estimationEjb.deleteDefaultProdAppl(prodApplIdsToDelete);
       ae = loadProcedureProducts(request,form);
       if(ae.size()>0) {
         pForm.setBufferedProcedures(null);
         pForm.setBufferedProducts(null);
         return ae;
       }
       
       ProdApplJoinViewVector bufferedPajVwV = pForm.getBufferedProducts();
       if(bufferedPajVwV!=null && bufferedPajVwV.size()>0) {
         ProdApplJoinView pajVw =  (ProdApplJoinView) bufferedPajVwV.get(0);
         ProdApplData paD = pajVw.getProdAppl();
         int itemId = paD.getItemId();
         int[] prodSelector = new int[1];
         prodSelector[0] = itemId;
         pForm.setProductSelector(prodSelector);
         ae = saveProductToBuffer(request,form);
         if(ae.size()>0) {
           return ae;
         }
       }

       return ae;
   }       

   public static ActionErrors addProcProduct(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();        
       EstimatorMgrForm pForm = (EstimatorMgrForm) form;
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
       
       String prodApplIdS = request.getParameter("prodApplId");
       if(!Utility.isSet(prodApplIdS)) {
         String errorMess = "No product provided";
         ae.add("error",
              new ActionError("error.simpleGenericError",errorMess));
         return ae;
       }
       int prodApplId = stringToInt(prodApplIdS,true, ae, null, null, 
             "Ivalid product application id",0); 
       if(ae.size()>0) {
         return ae;
       }
       ProdApplJoinViewVector bufferedPajVwV = pForm.getBufferedProducts();
       ProdApplData prodApplD = null;
       for(Iterator iter=bufferedPajVwV.iterator(); iter.hasNext();) {
         ProdApplJoinView pajVw = (ProdApplJoinView) iter.next();
         ProdApplData paD = pajVw.getProdAppl();
         int paId = paD.getProdApplId();
         if(paId==prodApplId) {
           prodApplD = (ProdApplData) paD.clone();
           break;
         }
       }
       if(prodApplD==null) {
         String errorMess = "Request inconsistency. Probably old page used";
         ae.add("error",
              new ActionError("error.simpleGenericError",errorMess));
         return ae;
       }

       int itemId = prodApplD.getItemId();
       
       
       ProdApplJoinViewVector pajVwV = pForm.getProcedureProducts();
       ProdApplDataVector prodApplToUpdate = new ProdApplDataVector();
       for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
         ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
         ProdApplData paD = pajVw.getProdAppl();
         int iId = paD.getItemId();
         if(iId==itemId) {
           BigDecimal dilutionRateBD = prodApplD.getDilutionRate();
           BigDecimal usageRateBD = prodApplD.getUsageRate();
           String numerator = prodApplD.getUnitCdNumerator();
           String denominator = prodApplD.getUnitCdDenominator();
           String denominator1 = prodApplD.getUnitCdDenominator1();
           paD.setDilutionRate(dilutionRateBD);
           paD.setUsageRate(usageRateBD);
           paD.setUnitCdNumerator(numerator);
           paD.setUnitCdDenominator(denominator);
           paD.setUnitCdDenominator1(denominator1);
           prodApplToUpdate.add(paD);
           break;
         }
       }
       if(prodApplToUpdate.size()>0) {    
          estimationEjb.updateDefaultProdAppl(prodApplToUpdate, userName);
       } else {
         int cleaningProcId = pForm.getCleanigProcSelected();
         CleaningProcDataVector cleaningProcDV = pForm.getCleaningProcedures();
         for(Iterator iter=cleaningProcDV.iterator(); iter.hasNext();) {
           CleaningProcData cpD = (CleaningProcData) iter.next();
           if(cleaningProcId==cpD.getCleaningProcId()) {
             prodApplD.setProdApplId(0);
             prodApplD.setCleaningProcId(cleaningProcId);
             prodApplD.setEstimatorProductCd("aaa");
             estimationEjb.insertDefaultProdAppl(prodApplD, userName);
           }
         }
       }
       ae = loadProcedureProducts(request,form);
       if(ae.size()>0) {
         pForm.setBufferedProcedures(null);
         pForm.setBufferedProducts(null);
         return ae;
       }
       int[] prodSelector = new int[1];
       prodSelector[0] = itemId;
       pForm.setProductSelector(prodSelector);
       ae = saveProductToBuffer(request,form);
       if(ae.size()>0) {
         return ae;
       }
       return ae;
   }       

   public static ActionErrors saveSchedules(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();        
       EstimatorMgrForm pForm = (EstimatorMgrForm) form;
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
       
       CleaningScheduleJoinViewVector csjVwV = pForm.getCleaningSchedules();
       int ind = -1;
       for(Iterator iter = csjVwV.iterator(); iter.hasNext();) {
         ind ++;
         CleaningScheduleJoinView csjVw = (CleaningScheduleJoinView) iter.next();
         CleaningScheduleData csD = csjVw.getSchedule();
         
         String frequencyS = csjVw.getFrequency();
         BigDecimal frequencyBD = stringToBD (frequencyS,false,ae, 
           new BigDecimal(0),null,"Process Frequency", ind);
         
       }

       if(ae.size()>0) {
         return ae;
       }

       CleaningScheduleJoinViewVector schedulesToUpdate = new CleaningScheduleJoinViewVector();
       ProdApplDataVector prodApplToUpdate = new ProdApplDataVector();
       ind = -1;
       for(Iterator iter = csjVwV.iterator(); iter.hasNext();) {
         ind++;
         boolean updateFl = false;
         CleaningScheduleJoinView csjVw = (CleaningScheduleJoinView) iter.next();
         String frequencyS = csjVw.getFrequency();
         BigDecimal frequencyBD = stringToBD (frequencyS,false,ae, 
           new BigDecimal(0),null,"Process Frequency", ind);
         String procTimePeriodCd = csjVw.getTimePeriodCd();
         if(procTimePeriodCd==null) procTimePeriodCd = "";
         CleaningScheduleData csD = csjVw.getSchedule();
         
         BigDecimal oldFrequencyBD = Utility.bdNN(csD.getFrequency());
         String oldProcTimePeriodCd = csD.getTimePeriodCd();                                        
      
         if(!procTimePeriodCd.equals(oldProcTimePeriodCd) || 
           Math.abs(frequencyBD.doubleValue()-oldFrequencyBD.doubleValue())>0.000001) {
           csD.setFrequency(frequencyBD);
           csD.setTimePeriodCd(procTimePeriodCd);
           updateFl = true;
         }         
         
         String floorMachine = csjVw.getFloorMachine();
         CleaningSchedFilterDataVector csfDV = csjVw.getFilter();
         if(csfDV==null) {
           csfDV = new CleaningSchedFilterDataVector();
           csjVw.setFilter(csfDV);
         }

         if("Rotary".equals(floorMachine) || 
            "Auto".equals(floorMachine)) {
           if(csfDV.size()>0) {
             int ii = 0;
             for(Iterator iter1 = csfDV.iterator();iter1.hasNext();) {
               if(ii==0) {
                 ii++;                
                 CleaningSchedFilterData csfD = (CleaningSchedFilterData) iter1.next();
                 if(!floorMachine.equals(csfD.getPropertyValue())) {
                   csfD.setPropertyValue(floorMachine);
                   csfD.setPropertyName("floorMachine");
                   csfD.setFilterOperatorCd(RefCodeNames.FILTER_OPERATOR_CD.EQUALS);
                   csfD.setFilterGroupNum(1);
                   csfD.setModBy(userName);
                   updateFl = true;
                 }
               } else {
                 iter1.remove();
                 updateFl = true;
               }              
             }
           } else { //create new record
             CleaningSchedFilterData csfD = CleaningSchedFilterData.createValue();
             csfDV.add(csfD);
             csfD.setCleaningScheduleId(csD.getCleaningScheduleId());
             csfD.setPropertyValue(floorMachine);
             csfD.setPropertyName("floorMachine");
             csfD.setFilterOperatorCd(RefCodeNames.FILTER_OPERATOR_CD.EQUALS);
             csfD.setFilterGroupNum(1);
             csfD.setAddBy(userName);
             csfD.setModBy(userName);
             updateFl = true;
           }
         } else {
           if(csfDV.size()>0) {
             csjVw.setFilter(null);
             updateFl = true;
           }
         }
         if(updateFl) {
           prodApplToUpdate.add(csjVw);
         }
       }
              
       estimationEjb.updateDefaultCleaningSchedules(csjVwV, userName);
       return ae;
   }       


   private static int stringToInt(String pValue, 
         boolean pErrorIfEmptyFl, 
         ActionErrors pAe,
         Integer pMin, 
         Integer pMax,
         String pNameValue,
         int pIndex) {
       int result = 0;
       if(!Utility.isSet(pValue)) {
         if(pErrorIfEmptyFl) {
           String errorMess = "Empty "+pNameValue;
           pAe.add("err"+pNameValue+pIndex,
            new ActionError("error.simpleGenericError",errorMess));
         }
         return result;
       }
       pValue = pValue.trim();
       try {
         result = Integer.parseInt(pValue);
       } catch (Exception exc) {
         if(pErrorIfEmptyFl) {
           String errorMess = "Invalid value of "+pNameValue;
           pAe.add("err"+pNameValue+pIndex,
              new ActionError("error.simpleGenericError",errorMess));
           return result;
         }
       }
       if(pMin!=null) {
         if(result<pMin.intValue()) {
           String errorMess = "Value of "+pNameValue+ " can't be less than "+pMin;
           pAe.add("err"+pNameValue+pIndex,
              new ActionError("error.simpleGenericError",errorMess));
           return result;
         }
       }
       if(pMax!=null) {
         if(result>pMax.intValue()) {
           String errorMess = "Value of "+pNameValue+ " can't be higher than "+pMax;
           pAe.add("err"+pNameValue+pIndex,
              new ActionError("error.simpleGenericError",errorMess));
           return result;
         }
       }
       return result;
    }
    
    private static BigDecimal stringToBD(String pValue, 
         boolean pErrorIfEmptyFl, 
         ActionErrors pAe,
         BigDecimal pMin, 
         BigDecimal pMax,
         String pNameValue,
         int pIndex) {
       BigDecimal result = new BigDecimal(0);
       if(!Utility.isSet(pValue)) {
         if(pErrorIfEmptyFl) {
           String errorMess = "Empty "+pNameValue;
           pAe.add("err"+pNameValue+pIndex,
            new ActionError("error.simpleGenericError",errorMess));
         }
         return result;
       }
       pValue = pValue.trim();
       try {
         double resultDb = Double.parseDouble(pValue);
         result = new BigDecimal(pValue);
       } catch (Exception exc) {
         if(pErrorIfEmptyFl) {
           String errorMess = "Invalid value of "+pNameValue;
           pAe.add("err"+pNameValue+pIndex,
              new ActionError("error.simpleGenericError",errorMess));
           return result;
         }
       }
       if(pMin!=null) {
         if(result.doubleValue()<pMin.doubleValue()) {
           String errorMess = "Value of "+pNameValue+ " can't be less than "+pMin;
           pAe.add("err"+pNameValue+pIndex,
              new ActionError("error.simpleGenericError",errorMess));
           return result;
         }
       }
       if(pMax!=null) {
         if(result.doubleValue()>pMax.doubleValue()) {
           String errorMess = "Value of "+pNameValue+ " can't be higher than "+pMax;
           pAe.add("err"+pNameValue+pIndex,
              new ActionError("error.simpleGenericError",errorMess));
           return result;
         }
       }
       return result;
    }
    

    private static ProdUomPackJoinViewVector
                      sortByCategorySku(ProdUomPackJoinViewVector pProducts) 
    {
       Object[] pupJVwA = pProducts.toArray();
       if(pupJVwA.length<=1) {
         return pProducts;
       }
       for(int ii=0; ii<pupJVwA.length-1; ii++) {
         boolean exitFl = true;
         for(int jj=0; jj<pupJVwA.length-ii-1; jj++) {
           ProdUomPackJoinView pupJVw1 = (ProdUomPackJoinView) pupJVwA[jj];
           ProdUomPackJoinView pupJVw2 = (ProdUomPackJoinView) pupJVwA[jj+1];
           OrderGuideItemDescData ogidD1 = pupJVw1.getOrderGuideItem();
           OrderGuideItemDescData ogidD2 = pupJVw2.getOrderGuideItem();
           String category1 = ogidD1.getCategoryDesc();
           if(category1==null) {
             category1="";
           }
           String category2 = ogidD2.getCategoryDesc();
           if(category2==null) {
             category2="";
           }
           int compFl = category1.compareToIgnoreCase(category2);
           if(compFl>0) {
             pupJVwA[jj] = pupJVw2;
             pupJVwA[jj+1] = pupJVw1;
             exitFl = false;
           } else if(compFl==0) {
             String skuNumS1 = ogidD1.getCwSKU();
             String skuNumS2 = ogidD2.getCwSKU();
             int skuNum1 = Integer.parseInt(skuNumS1);
             int skuNum2 = Integer.parseInt(skuNumS2);
             if(skuNum1>skuNum2) {
               pupJVwA[jj] = pupJVw2;
               pupJVwA[jj+1] = pupJVw1;
               exitFl = false;
             }
           }
         }
         if(exitFl) {
           break;
         }
       }
       pProducts = new ProdUomPackJoinViewVector();
       for(int ii=0; ii<pupJVwA.length; ii++) {
         ProdUomPackJoinView pupJVw = (ProdUomPackJoinView) pupJVwA[ii];
         pProducts.add(pupJVw);
       }
       return pProducts;
    }

    //                String errorMess = "Wrong expression value: "+compS;
//                ae.add("error",
//                new ActionError("error.simpleGenericError",errorMess));
//                return ae;


}



