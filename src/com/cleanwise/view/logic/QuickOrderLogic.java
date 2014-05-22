 /**
 * Title:        QuickOrderLogic
 * Description:  This is the business logic class for the JanitorClosetAction and JanitorClosetForm.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */

package com.cleanwise.view.logic;

 import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Order;
 import com.cleanwise.service.api.session.ShoppingServices;
 import com.cleanwise.service.api.util.DataNotFoundException;
 import com.cleanwise.service.api.util.RefCodeNames;
 import com.cleanwise.service.api.util.Utility;
 import com.cleanwise.service.api.value.*;
 import com.cleanwise.view.forms.QuickOrderForm;
 import com.cleanwise.view.i18n.ClwI18nUtil;
 import com.cleanwise.view.utils.CleanwiseUser;
 import com.cleanwise.view.utils.Constants;
 import com.cleanwise.view.utils.ShopTool;
 import com.cleanwise.view.utils.ValidateActionMessage;
 import org.apache.struts.action.ActionError;
 import org.apache.struts.action.ActionErrors;

 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import java.rmi.RemoteException;
 import java.util.ArrayList;
import java.util.HashMap;
 import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cleanwise.view.utils.SessionTool;

 /**
 * Class description.
 *
 */
public class QuickOrderLogic {
  public static ActionErrors init(HttpServletRequest request, QuickOrderForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    pForm.clear();
    return ae;
  }

 //******************************************************************************
  public static ActionErrors addToCart(HttpServletRequest request, QuickOrderForm pForm)
  throws Exception
  {
    ActionErrors ae = new ActionErrors();

    //Check input information
    String[] skus = pForm.getItemSkus();
    String[] qtys = pForm.getItemQtys();
    int size = skus.length;
    ShoppingCartData scd = ShopTool.getCurrentShoppingCart(request);
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    SiteData site = appUser.getSite();
    if(site==null){
      ae.add("error",new ActionError("error.systemError","No site information found"));
      return ae;
    }

    if (ShopTool.isShoppingCartConsolidated(request)) {
        String errorMsg = ClwI18nUtil.getMessage(request, "shop.errors.addItemsIntoConsolidatedCart", null);
        ae.add("error", new ActionError("error.simpleGenericError", errorMsg));
        return ae;
    }

    Map uniqueSkuMap = new HashMap();
    
    List skusList = new ArrayList();
    for(int ii=0; ii<size; ii++) {
      if(skus[ii]==null || skus[ii].trim().length()==0) {
        if(qtys[ii]!=null && qtys[ii].trim().length()>0) {
          Object[] params = new Object[1];
          params[0] = qtys[ii];
          String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.quantityHasNoSkuNumber",params);
          ae.add("error"+ii,new ActionError("error.simpleGenericError",errorMess));
        }
      }
      if(skus[ii]!=null && skus[ii].trim().length()>0) {
        boolean noQuantity = false;
        if(qtys[ii]==null || qtys[ii].trim().length()==0) {
          Object[] params = new Object[1];
          params[0] = skus[ii];
          String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.itemHasNoQuantity",params);
          ae.add("error"+ii,new ActionError("error.simpleGenericError",errorMess));
          noQuantity = true;
        }
        int qty = 0;
        try {
          qty = Integer.parseInt(qtys[ii]);
          if(qty<0) {
            Object[] params = new Object[2];
            params[0] = qtys[ii];
            params[1] = skus[ii];
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.negativeQuantityForItem",params);
            ae.add("error"+ii,new ActionError("error.simpleGenericError",errorMess));
          } else if (qty == 0) {
              Object[] params = new Object[1];
              params[0] = skus[ii];
              String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.zeroQuantityForItem",params);
              ae.add("error"+ii,new ActionError("error.simpleGenericError",errorMess));
          }
        } catch (NumberFormatException exc) {
          if (!noQuantity) {// if we got here because no quantity was entered, no need to be repetitive
            Object[] params = new Object[2];
            params[0] = skus[ii];
            params[1] = qtys[ii];
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.wrongQuantityFormatForItem",params);
             ae.add("error"+ii,new ActionError("error.simpleGenericError",errorMess));
          }
        }
        if(qty>0) {

            String thissku = skus[ii].trim();
            skus[ii] = thissku;
            // Make sure this is not an inventory item.
            ShoppingCartItemData scid = null;
            if(pForm.getSkuType()==QuickOrderForm.DEFAULT_SKU_TYPE) {
                scid = scd.findItemBySku(thissku);
            }
            else {
                scid = scd.findItemByMfgSku(thissku);
            }
            if ( scid != null && scid.getIsaInventoryItem()
                 && scid.getInventoryParValue() > 0 && site.getInventoryShopping() != null && Utility.isTrue(site.getInventoryShopping().getValue())) {
                Object[] params = new Object[1];
                params[0] = thissku;
                String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.updateOnHandQuantity",params);
                ae.add("error"+ii,new ActionError("error.simpleGenericError",errorMess));
            }
            else {
            	
            	if(!skusList.contains(thissku)){
            		skusList.add(thissku);
            	}
            	
            	//Add all duplicated sku qtys
            	if(!uniqueSkuMap.containsKey(thissku)){
            		uniqueSkuMap.put(thissku, qty);
            	}else{
            		int mapQty = ((Integer)uniqueSkuMap.get(thissku)).intValue();
            		mapQty+=qty;
            		uniqueSkuMap.put(thissku, mapQty);
            	}
            }
        }
      }
    }
    
    if(ae.size()>0) {
        return ae;
    }
    String[] uniqueSkus = new String[uniqueSkuMap.size()];
    String[] uniqueQtys = new String[uniqueSkuMap.size()];
    
    int j=0;
    Iterator it = uniqueSkuMap.keySet().iterator();
    while(it.hasNext()){
    	String key = (String) it.next();
    	String value = ((Integer)uniqueSkuMap.get(key)).toString();
    	
    	uniqueSkus[j] = key;
    	uniqueQtys[j] = value;
    	j++;
    }
    
    pForm.setItemSkus(uniqueSkus);
    pForm.setItemQtys(uniqueQtys);
    skus = uniqueSkus;
    qtys = uniqueQtys;    
    
    //Pickup session information: user role, catalog id, contract id, etc
    if(appUser==null){
      ae.add("error",new ActionError("error.systemError","No "+Constants.APP_USER+" session object"));
      return ae;
    }

    AccountData account = appUser.getUserAccount();

    Integer catalogIdI = (Integer)session.getAttribute(Constants.CATALOG_ID);
    if(catalogIdI==null){
      ae.add("error",new ActionError("error.systemError","No "+Constants.CATALOG_ID+" session object"));
      return ae;
    }

    Integer contractIdI = (Integer)session.getAttribute(Constants.CONTRACT_ID);

    int contractId = Utility.intNN(contractIdI);
    if(appUser.isUserOnContract() && contractId==0) {
      String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noContractForTheSite",null);
      ae.add("error",new ActionError("error.simpleGenericError",errorMess));
      return ae;
    }

    int accountCatalogId = account.getAccountCatalogId();
    if (appUser.isSpecialPermissionRequired() && accountCatalogId == 0) {
        ae.add("error", new ActionError("error.simpleGenericError", "shop.errors.noAccoutCatalog"));
        return ae;
    }

    //Request cart items
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    ShoppingServices shoppingServEjb = null;
    try {
      shoppingServEjb = factory.getShoppingServicesAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error",new ActionError("error.systemError","No shopping services Ejb pointer"));
      return ae;
    }
    ShoppingCartItemDataVector items = null;
    String storeType = appUser.getUserStore().getStoreType().getValue();
    try {

        if (pForm.getSkuType() == QuickOrderForm.DEFAULT_SKU_TYPE) {
            items = shoppingServEjb.getShoppingItemsBySku(storeType,
                    appUser.getSite(),
                    skusList,
                    ShopTool.createShoppingItemRequest(request),
                    SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
        } else {
            items = shoppingServEjb.getShoppingItemsByMfgSku(appUser.getSite(),
                    skusList,
                    ShopTool.createShoppingItemRequest(request),
                    SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
        }

    } catch (DataNotFoundException exc) {
      String mess = exc.getMessage();
      String errorMess = ClwI18nUtil.formatEjbError(request,mess);
      ae.add("error",new ActionError("error.simpleGenericError",errorMess));
      return ae;
    } catch (RemoteException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }
    //Analise duplications
    pForm.setDuplicationFlag(false);
    Object[] itemsArray = items.toArray();
    size = itemsArray.length;
    for(int ii=0; ii<size-1; ii++) {
      boolean finishFlag = true;
      for(int jj=1; jj<size-ii; jj++) {
        ShoppingCartItemData item1 = (ShoppingCartItemData) itemsArray[jj-1];
        ShoppingCartItemData item2 = (ShoppingCartItemData) itemsArray[jj];
        String sku1 = "";
        String sku2 = "";
        if(pForm.getSkuType()==QuickOrderForm.DEFAULT_SKU_TYPE) {
          sku1 = item1.getActualSkuNum();
          sku2 = item2.getActualSkuNum();
        } else {
          sku1 = item1.getProduct().getManufacturerSku();
          sku2 = item2.getProduct().getManufacturerSku();
        }
        int comp = sku1.compareToIgnoreCase(sku2);
        if(comp==0) {
          if(item1.getProduct().getProductId()!=item2.getProduct().getProductId()) {
            pForm.setDuplicationFlag(true);
          }
        }
        if(comp>0) {
          finishFlag = false;
          itemsArray[jj-1] = item2;
          itemsArray[jj] = item1;
        }
      }
      if(finishFlag) {
        break;
      }
    }

      if(pForm.getDuplicationFlag()) {
        //prepare seconary screen if there were some sku duplication
        size = itemsArray.length; //size of the items that we returned from the search
        //loop through the items that were entered through the UI (skus object)
        for(int ii=0; ii<skus.length; ii++) {
          String skuNum = skus[ii];
          ArrayList ids = new ArrayList();
          ArrayList names = new ArrayList();
          ArrayList shortDesc = new ArrayList();
          if(skuNum!=null & skuNum.trim().length()>0) {
            skuNum = skuNum.trim();
            //lookup the data as it relates to this unique item
            int jj = 0;
            for(;jj<size;jj++){
              ShoppingCartItemData item = (ShoppingCartItemData) itemsArray[jj];
              //we now have to check for a match between the enetered product and the
              //products return in the search from the EJB layer.
              //how we check this is determined based off the store type and the
              //search type
              if(pForm.getSkuType()==QuickOrderForm.DEFAULT_SKU_TYPE) {
                  if(skuNum.equalsIgnoreCase(item.getActualSkuNum())) {
                    ids.add(new Integer(item.getProduct().getProductId()));
                    names.add(item.getProduct().getManufacturerName());
                    shortDesc.add(item.getProduct().getShortDesc());
                    break;
                  }
              }else{
                  if(skuNum.equalsIgnoreCase(item.getProduct().getManufacturerSku())) {
                    ids.add(new Integer(item.getProduct().getProductId()));
                    names.add(item.getProduct().getManufacturerName());
                    shortDesc.add(item.getProduct().getShortDesc());
                    break;
                  }
              }
            }
            if(jj==size) {
              String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.skuNumberError",null);
              ae.add("error",new ActionError("error.simpleGenericError",errorMess));
              return ae;
            }
            for(jj++;jj<size;jj++){
              ShoppingCartItemData item = (ShoppingCartItemData) itemsArray[jj];
              String compareSku;
              if(pForm.getSkuType()==QuickOrderForm.DEFAULT_SKU_TYPE) {
                  compareSku = item.getActualSkuNum();
              }else{
                  compareSku = item.getProduct().getManufacturerSku();
              }
              if(skuNum.equalsIgnoreCase(compareSku)) {
                ids.add(new Integer(item.getProduct().getProductId()));
                names.add(item.getProduct().getManufacturerName());
                shortDesc.add(item.getProduct().getShortDesc());
              } else {
                break;
              }
            }
            pForm.setMfgNamesElement(ii,names);
            pForm.setItemIdListsElement(ii,ids);
            pForm.setShortDescElement(ii,shortDesc);
          }
        }
        return ae;
        //END prepare seconary screen if there were some sku duplication
      }

      items = new ShoppingCartItemDataVector();
      for(int ii=0; ii<size; ii++) {
        ShoppingCartItemData item = (ShoppingCartItemData) itemsArray[ii];
        int jj=0;
        for(; jj<skus.length; jj++) {
          String skuNum = skus[jj];
          String returnedEjbSku;
          if(pForm.getSkuType()==QuickOrderForm.DEFAULT_SKU_TYPE) {
              returnedEjbSku = item.getActualSkuNum();
          }else{
              returnedEjbSku = item.getProduct().getManufacturerSku();
          }
          if(skuNum!=null &&
             skuNum.equalsIgnoreCase(returnedEjbSku)){
            item.setQuantity(Integer.parseInt(qtys[jj]));
            items.add(item);
            break;
          }
        }
        if(jj==skus.length) {
          String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.skuNumberError",null);
          ae.add("error",new ActionError("error.simpleGenericError",errorMess));
          return ae;
        }
      }

     ae = addToCart(request, appUser, items);
    if(ae.size()>0) {
      return ae;
    }else{
    	pForm.setConfirmMessage(ClwI18nUtil.getMessage(request,
                "shoppingCart.text.actionMessage.itemAdded", null));
    }
    pForm.clear();

    return ae;
  }
 //******************************************************************************
 public static ActionErrors resolveMfg(HttpServletRequest request, QuickOrderForm pForm, boolean isInvCart)
 throws Exception
 {
   ActionErrors ae = new ActionErrors();

    if (ShopTool.isShoppingCartConsolidated(request)) {
        String errorMsg = ClwI18nUtil.getMessage(request, "shop.errors.addItemsIntoConsolidatedCart", null);
        ae.add("error", new ActionError("error.simpleGenericError", errorMsg));
        return ae;
    }

    //Check input information
    int[] ids = pForm.getItemIds();
    String[] qtys = pForm.getItemQtys();
    int size = Math.min(ids.length, qtys.length);
    IdVector idList = new IdVector();
    for(int ii=0; ii<size; ii++) {
      if(ids[ii]==0) {
        if(qtys[ii]!=null && qtys[ii].trim().length()>0) {
          ae.add("error"+ii,new ActionError("error.systemError","Quantity "+qtys[ii]+" without item id"));
        }
      }
      if(ids[ii]!=0) {
        boolean noQuantity = false;
        if(qtys[ii]==null || qtys[ii].trim().length()==0) {
          ae.add("error"+ii,new ActionError("error.systemError","Item id "+ids[ii]+" without quantity"));
          noQuantity = true;
        }
        int qty = 0;
        try {
          qty = Integer.parseInt(qtys[ii]);
          if(qty<0) {
            Object[] params = new Object[2];
            params[0] = ""+ids[ii];
            params[1] = qtys[ii];
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.negativeQuantityForItemId",params);
            ae.add("error"+ii,new ActionError("error.simpleGenericError",errorMess));
          }
        } catch (NumberFormatException exc) {
          if (!noQuantity)// if we got here because no quantity was entered, no need to be repetitive
             ae.add("error"+ii,new ActionError("error.systemError","Wrong quantity format for item id "+ids[ii]+", quantity "+qtys[ii]));
        }
        if(qty>0) {
          idList.add(new Integer(ids[ii]));
        }
      }
    }
    if(ae.size()>0) {
      return ae;
    }
    //Pickup session information: user role, catalog id, contract id, etc
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if(appUser==null){
      ae.add("error",new ActionError("error.systemError","No "+Constants.APP_USER+" session object"));
      return ae;
    }
    UserData user = appUser.getUser();
    SiteData site = appUser.getSite();
    if(site==null){
      ae.add("error",new ActionError("error.systemError","No site information found"));
      return ae;
    }
    int siteId = site.getBusEntity().getBusEntityId();
    String roleCd = user.getUserRoleCd();
    if(roleCd==null) {
      roleCd = "";
    }
    boolean contractOnly = (roleCd.indexOf(Constants.UserRole.CONTRACT_ITEMS_ONLY)>=0)?true:false;
    Integer catalogIdI = (Integer)session.getAttribute(Constants.CATALOG_ID);
    if(catalogIdI==null){
      ae.add("error",new ActionError("error.systemError","No "+Constants.CATALOG_ID+" session object"));
      return ae;
    }
    int catalogId = catalogIdI.intValue();
    Integer contractIdI = (Integer)session.getAttribute(Constants.CONTRACT_ID);
    int contractId = 0;
    if(contractIdI!=null) {
      contractId = contractIdI.intValue();
    }
    if(contractOnly && contractId==0) {
      String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noContractForTheSite",null);
      ae.add("error",new ActionError("error.simpleGenericError",errorMess));
      return ae;
    }
    //Request cart items
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    ShoppingServices shoppingServEjb = null;
    try {
      shoppingServEjb = factory.getShoppingServicesAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error",new ActionError("error.systemError","No shopping services Ejb pointer"));
      return ae;
    }



    ShoppingCartItemDataVector items = null;
    StoreData store = appUser.getUserStore();
    if(store==null) {
      ae.add("error",new ActionError("error.systemError","No store information was loaded"));
      return ae;
    }
    PropertyData storeTypeProperty = store.getStoreType();
    if(storeTypeProperty==null) {
      ae.add("error",new ActionError("error.systemError","No store type information was loaded"));
      return ae;
    }
    try {
      items = shoppingServEjb.prepareShoppingItems(storeTypeProperty.getValue(),appUser.getSite(),catalogId, contractId, idList,SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
    } catch (RemoteException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }

    items.sortByProductId();
    idList.sort();


    //add items to cart if no duplications found
    for(int ii=0; ii<idList.size(); ii++) {
        Integer itemIdI = (Integer) idList.get(ii);
        int itemId = itemIdI.intValue();
        ShoppingCartItemData item = (ShoppingCartItemData) items.get(ii);
        if(itemId!=item.getProduct().getProductId()) {
          String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.skuNumberError",null);
          ae.add("error",new ActionError("error.simpleGenericError",errorMess));
          return ae;
        }

        for(int jj=0; jj<ids.length; jj++) {
            if(ids[jj]==itemId) {
                int qty = Integer.parseInt(qtys[jj]);
                item.setQuantity(qty);
                break;
            }
        }
    }

    if(isInvCart){
    	ae = addToInventoryCart(shoppingServEjb,request,appUser,items);
    }else{
    	ae = addToCart(request,appUser,items);
    }

    if(ae.size()>0) {
    	return ae;
    }
    pForm.clear();

    return ae;
 }
 //-----------------------------------------------------------------------------
 private static ActionErrors addToCart(HttpServletRequest  request , CleanwiseUser pAppUser, ShoppingCartItemDataVector pItems)
 {

   ActionErrors ae;
   HttpSession session = request.getSession();

   ShoppingCartData cartD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
   if(cartD==null) {
      cartD = new ShoppingCartData();
      cartD.setUser(pAppUser.getUser());
      cartD.setSite(pAppUser.getSite());
   }

   cartD.clearNewItems();
   ShoppingCartItemDataVector newItems = new ShoppingCartItemDataVector();
   for(int ii=0; ii<pItems.size(); ii++) {
     ShoppingCartItemData sciD = (ShoppingCartItemData)pItems.get(ii);
     newItems.add(sciD);
   }

   ValidateActionMessage am = ShopTool.addItemsToCart(request, cartD, newItems);
   if (am.hasErrors()) {
       return am.getActionErrors();
   }
   if (am.hasWarnings()) {
       cartD.addWarningMessages(am.getWarnings());
   }

  session.setAttribute(Constants.SHOPPING_CART,cartD);
  ae = ShoppingCartLogic.saveShoppingCart(session, newItems);
  if(ae.size()>0) {
    return ae;
  }
  return ae;
}
 //******************************************************************************


  public static ActionErrors clear(HttpServletRequest request, QuickOrderForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    pForm.clear();
    return ae;
  }


    private static ActionErrors addToInventoryCart(ShoppingServices shoppingServEjb, HttpServletRequest request, CleanwiseUser pAppUser, ShoppingCartItemDataVector pItems) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        Order orderEjb;
        try {
        	orderEjb = factory.getOrderAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No order Ejb pointer"));
            return ae;
        }

        if(!ShopTool.hasInventoryCartAccessOpen(request)) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable",null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        ShoppingCartData cartD = ShopTool.getCurrentInventoryCart(request.getSession());
        if (cartD == null) {

            cartD = new ShoppingCartData();
            cartD.setUser(pAppUser.getUser());
            cartD.setSite(pAppUser.getSite());

        }
        cartD.clearNewItems();
        ShoppingCartItemDataVector newItems = new ShoppingCartItemDataVector();
        for (int ii = 0; ii < pItems.size(); ii++) {
            ShoppingCartItemData sciD = (ShoppingCartItemData) pItems.get(ii);
            newItems.add(sciD);
        }

        ValidateActionMessage am = ShopTool.addItemsToCart(request, cartD, newItems);
        if (am.hasErrors()) {
            return am.getActionErrors();
        }
        if (am.hasWarnings()) {
            cartD.addWarningMessages(am.getWarnings());
        }

        if (cartD.getItemsQty() > 0) {
            try {
                CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);

                if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.OVERRIDE_SHOPPING_RESTRICTION)) {
                    //cartD.reviseQuantities();
                	int siteId = appUser.getSite().getSiteId();
            		ShoppingCartLogic.reviseShoppingCartQuantities(cartD, orderEjb, siteId, newItems);
                }

                shoppingServEjb.saveInventoryShoppingCart(cartD, null, null);
            } catch (Exception e) {
                //String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noItemsSelected",null);
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
                return ae;
            }
        } else {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.noItemsSelected", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        ae = ShopTool.reloadInvShoppingCart(shoppingServEjb,session, null, pAppUser.getSite(), pAppUser.getUser(), pAppUser.getUserStore().getStoreType().getValue());

        ShoppingCartData newShoppingCart = ShopTool.getCurrentInventoryCart(session);
        newShoppingCart.addItemMessages(cartD.getItemMessages());
        newShoppingCart.addWarningMessages(cartD.getWarningMessages());
        session.setAttribute(Constants.INVENTORY_SHOPPING_CART,newShoppingCart);

        return ae;

    }

    //******************************************************************************

    public static ActionErrors addToInventoryCart(HttpServletRequest request, QuickOrderForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.APP_USER + " session object"));
            return ae;
        }

        SiteData site = appUser.getSite();
        if (site == null) {
            ae.add("error", new ActionError("error.systemError", "No site information found"));
            return ae;
        }

        AccountData account = appUser.getUserAccount();
        if (account == null) {
            ae.add("error", new ActionError("error.systemError", "No account information found"));
            return ae;
        }

        if(!ShopTool.hasInventoryCartAccessOpen(request)) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable",null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        Integer catalogId = (Integer) session.getAttribute(Constants.CATALOG_ID);
        if (catalogId == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session object"));
            return ae;
        }

        Integer contractId = Utility.intNN((Integer) session.getAttribute(Constants.CONTRACT_ID));
        if (appUser.isUserOnContract() && contractId == 0) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.noContractForTheSite", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        int accountCatalogId = account.getAccountCatalogId();
        if (appUser.isSpecialPermissionRequired() && accountCatalogId == 0) {
            ae.add("error", new ActionError("error.simpleGenericError", "shop.errors.noAccoutCatalog"));
            return ae;
        }

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        ShoppingServices shoppingServEjb = null;
        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No shopping services Ejb pointer"));
            return ae;
        }

        ShoppingCartData scd = ShopTool.getCurrentInventoryCart(session);
        ae = checkInputData(request, site, scd, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        List<String> skusList = new ArrayList<String>();
        for (int i = 0; i < pForm.getItemSkus().length; i++) {
            if (pForm.getItemSkus()[i].trim().length() > 0) {
                skusList.add(pForm.getItemSkus()[i]);
            }
        }

        ShoppingCartItemDataVector items = null;
        String storeType = appUser.getUserStore().getStoreType().getValue();
        try {

            if (pForm.getSkuType() == QuickOrderForm.DEFAULT_SKU_TYPE) {
                items = shoppingServEjb.getShoppingItemsBySku(storeType,
                        appUser.getSite(),
                        skusList,
                        ShopTool.createShoppingItemRequest(request),
                        SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
            } else {
                items = shoppingServEjb.getShoppingItemsByMfgSku(appUser.getSite(),
                        skusList,
                        ShopTool.createShoppingItemRequest(request),
                        SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
            }

        } catch (DataNotFoundException exc) {
            String mess = exc.getMessage();
            String errorMess = ClwI18nUtil.formatEjbError(request, mess);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        } catch (RemoteException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", exc.getMessage()));
            return ae;
        }

        ae = dropInventoryItems(request, items);
        if (ae.size() > 0) {
            return ae;
        }

        Object[] itemArray = items.toArray();
        int size=itemArray .length;
        ae = analiseDuplications(request, pForm, itemArray);
        if (ae.size() > 0) {
            return ae;
        }

        ShoppingCartItemDataVector itemsForSave = new ShoppingCartItemDataVector();
        ae = fillItemsForSave(request, pForm, itemArray, pForm.getItemSkus(), pForm.getItemQtys(),size, itemsForSave);
        if (ae.size() > 0) {
            return ae;
        }

        if(pForm.getDuplicationFlag()){
        	return ae;
        }

        ae = addToInventoryCart(shoppingServEjb,request,appUser,itemsForSave);
        if(ae.size()>0){
            return ae;
        }

        ae = init(request,pForm);
        return ae;
    }

    private static ActionErrors dropInventoryItems(HttpServletRequest request, ShoppingCartItemDataVector items) {
        ActionErrors ae = new ActionErrors();
        int i = 0;
        if (!items.isEmpty()) {
            Iterator it = items.iterator();
            while (it.hasNext()) {
                ShoppingCartItemData cartItem = (ShoppingCartItemData) it.next();
                if (cartItem.getIsaInventoryItem()) {

                    Object[] params = new Object[1];
                    params[0] = cartItem.getActualSkuNum();
                    String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.cannotAddInvItemToInvCart", params);
                    ae.add("error" + (++i), new ActionError("error.simpleGenericError", errorMess));
                    it.remove();
                }
            }
        }
        return ae;
    }

    public static ActionErrors checkInputData(HttpServletRequest request, SiteData site, ShoppingCartData currentCart, QuickOrderForm pForm) {

        //Check input information
        String[] skus = pForm.getItemSkus();
        String[] qtys = pForm.getItemQtys();
        int size = skus.length;
        ActionErrors ae = new ActionErrors();

        for (int ii = 0; ii < size; ii++) {

            if (skus[ii] == null || skus[ii].trim().length() == 0) {
                if (qtys[ii] != null && qtys[ii].trim().length() > 0) {
                    Object[] params = new Object[1];
                    params[0] = qtys[ii];
                    String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.quantityHasNoSkuNumber", params);
                    ae.add("error" + ii, new ActionError("error.simpleGenericError", errorMess));
                }
            }

            if (skus[ii] != null && skus[ii].trim().length() > 0) {
                boolean noQuantity = false;
                if (qtys[ii] == null || qtys[ii].trim().length() == 0) {
                    Object[] params = new Object[1];
                    params[0] = skus[ii];
                    String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.itemHasNoQuantity", params);
                    ae.add("error" + ii, new ActionError("error.simpleGenericError", errorMess));
                    noQuantity = true;
                }
                int qty = 0;

                try {

                    qty = Integer.parseInt(qtys[ii]);
                    if (qty < 0) {

                        Object[] params = new Object[2];
                        params[0] = qtys[ii];
                        params[1] = skus[ii];
                        String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.negativeQuantityForItem", params);
                        ae.add("error" + ii, new ActionError("error.simpleGenericError", errorMess));

                    }

                } catch (NumberFormatException exc) {

                    if (!noQuantity) {// if we got here because no quantity was entered, no need to be repetitive
                        Object[] params = new Object[2];
                        params[0] = skus[ii];
                        params[1] = qtys[ii];
                        String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.wrongQuantityFormatForItem", params);
                        ae.add("error" + ii, new ActionError("error.simpleGenericError", errorMess));

                    }

                }

                if (qty > 0) {


                    String thissku = skus[ii].trim();
                    skus[ii] = thissku;
                    // Make sure this is not an inventory item.
                    ShoppingCartItemData scid;
                    if (pForm.getSkuType() == QuickOrderForm.DEFAULT_SKU_TYPE) {
                        scid = currentCart.findItemBySku(thissku);
                    } else {
                        scid = currentCart.findItemByMfgSku(thissku);
                    }

                    if (scid != null && scid.getIsaInventoryItem()) {
                        if (!site.hasModernInventoryShopping()) {
                            if (site.hasInventoryShoppingOn() &&
                                    scid.getInventoryParValue() > 0) {
                                Object[] params = new Object[1];
                                params[0] = thissku;
                                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.updateOnHandQuantity", params);
                                ae.add("error" + ii, new ActionError("error.simpleGenericError", errorMess));

                            }
                        }

                    }
                }
            }
        }
        return ae;
    }


    public static ActionErrors analiseDuplications(HttpServletRequest request, QuickOrderForm pForm, Object[] itemsArray) {

        ActionErrors ae = new ActionErrors();

        pForm.setDuplicationFlag(false);

        int size = itemsArray.length;
        for (int ii = 0; ii < size - 1; ii++) {
            boolean finishFlag = true;
            for (int jj = 1; jj < size - ii; jj++) {
                ShoppingCartItemData item1 = (ShoppingCartItemData) itemsArray[jj - 1];
                ShoppingCartItemData item2 = (ShoppingCartItemData) itemsArray[jj];
                String sku1 = "";
                String sku2 = "";
                if (pForm.getSkuType() == QuickOrderForm.DEFAULT_SKU_TYPE) {
                    sku1 = item1.getActualSkuNum();
                    sku2 = item2.getActualSkuNum();
                } else {
                    sku1 = item1.getProduct().getManufacturerSku();
                    sku2 = item2.getProduct().getManufacturerSku();
                }
                int comp = sku1.compareToIgnoreCase(sku2);
                if (comp == 0) {
                    if (item1.getProduct().getProductId() != item2.getProduct().getProductId()) {
                        pForm.setDuplicationFlag(true);
                    }
                }

                if (comp > 0) {
                    finishFlag = false;
                    itemsArray[jj - 1] = item2;
                    itemsArray[jj] = item1;
                }
            }

            if (finishFlag) {
                break;
            }

        }

        String[] skus = pForm.getItemSkus();
        if (pForm.getDuplicationFlag()) {
            //prepare seconary screen if there were some sku duplication
            size = itemsArray.length; //size of the items that we returned from the search
            //loop through the items that were entered through the UI (skus object)
            for (int ii = 0; ii < skus.length; ii++) {
                String skuNum = skus[ii];
                ArrayList ids = new ArrayList();
                ArrayList names = new ArrayList();
                ArrayList shortDesc = new ArrayList();
                if (skuNum != null & skuNum.trim().length() > 0) {
                    skuNum = skuNum.trim();
                    //lookup the data as it relates to this unique item
                    int jj = 0;
                    for (; jj < size; jj++) {
                        ShoppingCartItemData item = (ShoppingCartItemData) itemsArray[jj];
                        //we now have to check for a match between the enetered product and the
                        //products return in the search from the EJB layer.
                        //how we check this is determined based off the store type and the
                        //search type
                        if (pForm.getSkuType() == QuickOrderForm.DEFAULT_SKU_TYPE) {
                            if (skuNum.equalsIgnoreCase(item.getActualSkuNum())) {
                                ids.add(new Integer(item.getProduct().getProductId()));
                                names.add(item.getProduct().getManufacturerName());
                                shortDesc.add(item.getProduct().getShortDesc());
                                break;
                            }
                        } else {

                            if (skuNum.equalsIgnoreCase(item.getProduct().getManufacturerSku())) {
                                ids.add(new Integer(item.getProduct().getProductId()));
                                names.add(item.getProduct().getManufacturerName());
                                shortDesc.add(item.getProduct().getShortDesc());
                                break;
                            }
                        }

                    }

                    if (jj == size) {
                        String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.skuNumberError", null);
                        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                        return ae;
                    }

                    for (jj++; jj < size; jj++) {

                        ShoppingCartItemData item = (ShoppingCartItemData) itemsArray[jj];
                        String compareSku;
                        if (pForm.getSkuType() == QuickOrderForm.DEFAULT_SKU_TYPE) {
                            compareSku = item.getActualSkuNum();
                        } else {
                            compareSku = item.getProduct().getManufacturerSku();
                        }

                        if (skuNum.equalsIgnoreCase(compareSku)) {
                            ids.add(new Integer(item.getProduct().getProductId()));
                            names.add(item.getProduct().getManufacturerName());
                            shortDesc.add(item.getProduct().getShortDesc());
                        } else {
                            break;
                        }

                    }

                    pForm.setMfgNamesElement(ii, names);
                    pForm.setItemIdListsElement(ii, ids);
                    pForm.setShortDescElement(ii, shortDesc);
                }
            }
        }
        return ae;
    }


    public static ActionErrors fillItemsForSave(HttpServletRequest request, QuickOrderForm pForm, Object[] itemsArray, String[] skus, String[] qtys, int cSize, ShoppingCartItemDataVector items) {


        ActionErrors ae = new ActionErrors();

        for (int ii = 0; ii < cSize; ii++) {
            ShoppingCartItemData item = (ShoppingCartItemData) itemsArray[ii];
            int jj = 0;
            for (; jj < skus.length; jj++) {

                String skuNum = skus[jj];
                String returnedEjbSku;

                if (pForm.getSkuType() == QuickOrderForm.DEFAULT_SKU_TYPE) {
                    returnedEjbSku = item.getActualSkuNum();
                } else {
                    returnedEjbSku = item.getProduct().getManufacturerSku();
                }

                if (skuNum != null &&
                        skuNum.equalsIgnoreCase(returnedEjbSku)) {

                    item.setQuantity(Integer.parseInt(qtys[jj]));
                    items.add(item);
                    break;

                }

            }

            if (jj == skus.length) {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.skuNumberError", null);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
            }
        }
        return ae;
    }

    public static ActionErrors addMoreFields(HttpServletRequest request, QuickOrderForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        session.setAttribute("QUICK_ORDER_FORM", pForm);
        return ae;
    }

//  ******************************************************************************
    public static ActionErrors addToNewXpedxCart(HttpServletRequest request, QuickOrderForm pForm)
    throws Exception
    {
      ActionErrors ae = new ActionErrors();

      //Check input information
      String[] skus = pForm.getItemSkus();
      String[] qtys = pForm.getItemQtys();
      int size = skus.length;

      ShoppingCartData scd = ShopTool.getCurrentShoppingCart(request);
      HttpSession session = request.getSession();

      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

      SiteData site = appUser.getSite();
      if (site == null) {
          ae.add("error", new ActionError("error.systemError", "No site information found"));
          return ae;
      }

      AccountData account = appUser.getUserAccount();
      if (account == null) {
          ae.add("error", new ActionError("error.systemError", "No account information found"));
          return ae;
      }

      List msgList = new ArrayList();
      List skusList = new ArrayList();
      for(int ii=0; ii<size; ii++) {
        if(skus[ii]==null || skus[ii].trim().length()==0) {
          if(qtys[ii]!=null && qtys[ii].trim().length()>0) {
            Object[] params = new Object[1];
            params[0] = qtys[ii];
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.quantityHasNoSkuNumber",params);
            ae.add("error"+ii,new ActionError("error.simpleGenericError",errorMess));
          }
        }
        if(skus[ii]!=null && skus[ii].trim().length()>0) {
          boolean noQuantity = false;
          if(qtys[ii]==null || qtys[ii].trim().length()==0) {

            noQuantity = true;
          }
          int qty = 0;
          try {
            qty = Integer.parseInt(qtys[ii]);
            if(qty<0) {
            	msgList.add(skus[ii]);
            }
          } catch (NumberFormatException exc) {

        	  msgList.add(skus[ii]);
          }
          if(qty>0) {

              String thissku = skus[ii].trim();
              skus[ii] = thissku;
              // Make sure this is not an inventory item.
              ShoppingCartItemData scid = null;
              if(pForm.getSkuType()==QuickOrderForm.DEFAULT_SKU_TYPE) {
                  scid = scd.findItemBySku(thissku);
              }
              else {
                  scid = scd.findItemByMfgSku(thissku);
              }
              if ( scid != null && scid.getIsaInventoryItem()
                   && scid.getInventoryParValue() > 0 && site.getInventoryShopping() != null && Utility.isTrue(site.getInventoryShopping().getValue())) {
                  Object[] params = new Object[1];
                  params[0] = thissku;
                  String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.updateOnHandQuantity",params);
                  ae.add("error"+ii,new ActionError("error.simpleGenericError",errorMess));
              }
              else {
                  skusList.add(thissku);
              }
          }
        }
      }
      if(msgList.size()>0 && !msgList.isEmpty()){
      	Object[] param = new Object[1];
      	param[0] = Utility.toCommaSting(msgList);
          String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.invalidQtyMessage",param);
          //pForm.setInvalidQtyMessage(errorMess);
          ae.add("error",
                  new ActionError("error.simpleGenericError",errorMess));
      	return ae;
      }
      if (ae.size() > 0) {
          return ae;
      }
      //Pickup session information: user role, catalog id, contract id, etc
      if(appUser==null){
        ae.add("error",new ActionError("error.systemError","No "+Constants.APP_USER+" session object"));
        return ae;
      }

      Integer catalogId = (Integer) session.getAttribute(Constants.CATALOG_ID);
      if (catalogId == null) {
          ae.add("error", new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session object"));
          return ae;
      }

      Integer contractId = Utility.intNN((Integer) session.getAttribute(Constants.CONTRACT_ID));
      if (appUser.isUserOnContract() && contractId == 0) {
          String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.noContractForTheSite", null);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
      }

      int accountCatalogId = account.getAccountCatalogId();
      if (appUser.isSpecialPermissionRequired() && accountCatalogId == 0) {
          ae.add("error", new ActionError("error.simpleGenericError", "shop.errors.noAccoutCatalog"));
          return ae;
      }

      //Request cart items
      APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
      if (factory == null) {
          ae.add("error", new ActionError("error.systemError", "No Ejb access"));
          return ae;
      }

      ShoppingServices shoppingServEjb = null;
      try {
        shoppingServEjb = factory.getShoppingServicesAPI();
      } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
        ae.add("error",new ActionError("error.systemError","No shopping services Ejb pointer"));
        return ae;
      }
      ShoppingCartItemDataVector items = null;
      String storeType = appUser.getUserStore().getStoreType().getValue();
      try {

          if (pForm.getSkuType() == QuickOrderForm.DEFAULT_SKU_TYPE) {
              items = shoppingServEjb.getShoppingItemsBySku(storeType,
                      appUser.getSite(),
                      skusList,
                      ShopTool.createShoppingItemRequest(request),
                      SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
          } else {
              items = shoppingServEjb.getShoppingItemsByMfgSku(appUser.getSite(),
                      skusList,
                      ShopTool.createShoppingItemRequest(request),
                      SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
          }

      } catch (DataNotFoundException exc) {
        String mess = exc.getMessage();
        String errorMess = ClwI18nUtil.formatEjbError(request,mess);
        ae.add("error",new ActionError("error.simpleGenericError",errorMess));
        return ae;
      } catch (RemoteException exc) {
        exc.printStackTrace();
        ae.add("error",new ActionError("error.systemError",exc.getMessage()));
        return ae;
      }
      //Analise duplications
      pForm.setDuplicationFlag(false);
      Object[] itemsArray = items.toArray();
      size = itemsArray.length;
      for(int ii=0; ii<size-1; ii++) {
        boolean finishFlag = true;
        for(int jj=1; jj<size-ii; jj++) {
          ShoppingCartItemData item1 = (ShoppingCartItemData) itemsArray[jj-1];
          ShoppingCartItemData item2 = (ShoppingCartItemData) itemsArray[jj];
          String sku1 = "";
          String sku2 = "";
          if(pForm.getSkuType()==QuickOrderForm.DEFAULT_SKU_TYPE) {
            sku1 = item1.getActualSkuNum();
            sku2 = item2.getActualSkuNum();
          } else {
            sku1 = item1.getProduct().getManufacturerSku();
            sku2 = item2.getProduct().getManufacturerSku();
          }
          int comp = sku1.compareToIgnoreCase(sku2);
          if(comp==0) {
            if(item1.getProduct().getProductId()!=item2.getProduct().getProductId()) {
              pForm.setDuplicationFlag(true);
            }
          }
          if(comp>0) {
            finishFlag = false;
            itemsArray[jj-1] = item2;
            itemsArray[jj] = item1;
          }
        }
        if(finishFlag) {
          break;
        }
      }

        if(pForm.getDuplicationFlag()) {
          //prepare seconary screen if there were some sku duplication
          size = itemsArray.length; //size of the items that we returned from the search
          //loop through the items that were entered through the UI (skus object)
          for(int ii=0; ii<skus.length; ii++) {
            String skuNum = skus[ii];
            ArrayList ids = new ArrayList();
            ArrayList names = new ArrayList();
            ArrayList shortDesc = new ArrayList();
            if(skuNum!=null & skuNum.trim().length()>0) {
              skuNum = skuNum.trim();
              //lookup the data as it relates to this unique item
              int jj = 0;
              for(;jj<size;jj++){
                ShoppingCartItemData item = (ShoppingCartItemData) itemsArray[jj];
                //we now have to check for a match between the enetered product and the
                //products return in the search from the EJB layer.
                //how we check this is determined based off the store type and the
                //search type
                if(pForm.getSkuType()==QuickOrderForm.DEFAULT_SKU_TYPE) {
                    if(skuNum.equalsIgnoreCase(item.getActualSkuNum())) {
                      ids.add(new Integer(item.getProduct().getProductId()));
                      names.add(item.getProduct().getManufacturerName());
                      shortDesc.add(item.getProduct().getShortDesc());
                      break;
                    }
                }else{
                    if(skuNum.equalsIgnoreCase(item.getProduct().getManufacturerSku())) {
                      ids.add(new Integer(item.getProduct().getProductId()));
                      names.add(item.getProduct().getManufacturerName());
                      shortDesc.add(item.getProduct().getShortDesc());
                      break;
                    }
                }
              }
              if(jj==size) {
                String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.skuNumberError",null);
                ae.add("error",new ActionError("error.simpleGenericError",errorMess));
                return ae;
              }
              for(jj++;jj<size;jj++){
                ShoppingCartItemData item = (ShoppingCartItemData) itemsArray[jj];
                String compareSku;
                if(pForm.getSkuType()==QuickOrderForm.DEFAULT_SKU_TYPE) {
                    compareSku = item.getActualSkuNum();
                }else{
                    compareSku = item.getProduct().getManufacturerSku();
                }
                if(skuNum.equalsIgnoreCase(compareSku)) {
                  ids.add(new Integer(item.getProduct().getProductId()));
                  names.add(item.getProduct().getManufacturerName());
                  shortDesc.add(item.getProduct().getShortDesc());
                } else {
                  break;
                }
              }
              pForm.setMfgNamesElement(ii,names);
              pForm.setItemIdListsElement(ii,ids);
              pForm.setShortDescElement(ii,shortDesc);
            }
          }
          return ae;
          //END prepare seconary screen if there were some sku duplication
        }

        items = new ShoppingCartItemDataVector();
        for(int ii=0; ii<size; ii++) {
          ShoppingCartItemData item = (ShoppingCartItemData) itemsArray[ii];
          int jj=0;
          for(; jj<skus.length; jj++) {
            String skuNum = skus[jj];
            String returnedEjbSku;
            if(pForm.getSkuType()==QuickOrderForm.DEFAULT_SKU_TYPE) {
                returnedEjbSku = item.getActualSkuNum();
            }else{
                returnedEjbSku = item.getProduct().getManufacturerSku();
            }
            if(skuNum!=null &&
               skuNum.equalsIgnoreCase(returnedEjbSku)){
              item.setQuantity(Integer.parseInt(qtys[jj]));
              items.add(item);
              break;
            }
          }
          if(jj==skus.length) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.skuNumberError",null);
            ae.add("error",new ActionError("error.simpleGenericError",errorMess));
            return ae;
          }
        }

      ae = addToCart(request,appUser,items);

      ShoppingCartData cartD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
      if(cartD.getItemMessages()!=null ){
    	  pForm.setWarningMessage(cartD.getItemMessages());
      }
      if(ae.size()>0) {
        return ae;
      }else {
      	pForm.setConfirmMessage(ClwI18nUtil.getMessage(request,
                "shoppingCart.text.actionMessage.itemAdded", null));
      }
      pForm.clear();


      return ae;
    }

//  ******************************************************************************

    public static ActionErrors addToNewXpedxInvCart(HttpServletRequest request, QuickOrderForm pForm) throws Exception

    {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.APP_USER + " session object"));
            return ae;
        }


        SiteData site = appUser.getSite();
        if (site == null) {
            ae.add("error", new ActionError("error.systemError", "No site information found"));
            return ae;
        }

        AccountData account = appUser.getUserAccount();
        if (account == null) {
            ae.add("error", new ActionError("error.systemError", "No account information found"));
            return ae;
        }

        if(!ShopTool.hasInventoryCartAccessOpen(request)) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable",null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        Integer catalogId = (Integer) session.getAttribute(Constants.CATALOG_ID);
        if (catalogId == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session object"));
            return ae;
        }

        Integer contractId = Utility.intNN((Integer) session.getAttribute(Constants.CONTRACT_ID));
        if (appUser.isUserOnContract() && contractId == 0) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.noContractForTheSite", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        int accountCatalogId = account.getAccountCatalogId();
        if (appUser.isSpecialPermissionRequired() && accountCatalogId == 0) {
            ae.add("error", new ActionError("error.simpleGenericError", "shop.errors.noAccoutCatalog"));
            return ae;
        }

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        Order orderEjb;
        try {
        	orderEjb = factory.getOrderAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No order Ejb pointer"));
            return ae;
        }

        ShoppingServices shoppingServEjb = null;
        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No shopping services Ejb pointer"));
            return ae;
        }

        ShoppingCartData scd = ShopTool.getCurrentInventoryCart(session);

//      Check input information
        String[] skus = pForm.getItemSkus();
        String[] qtys = pForm.getItemQtys();

        if (skus==null && qtys==null) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.enterQty",null);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }
        int size1 = skus.length;

        List msgList = new ArrayList();
        for (int ii = 0; ii < size1; ii++) {

            if (skus[ii] == null || skus[ii].trim().length() == 0) {
                if (qtys[ii] != null && qtys[ii].trim().length() > 0) {
                    Object[] params = new Object[1];
                    params[0] = qtys[ii];
                    String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.quantityHasNoSkuNumber", params);
                    ae.add("error" + ii, new ActionError("error.simpleGenericError", errorMess));
                }
            }

            if (skus[ii] != null && skus[ii].trim().length() > 0) {
                boolean noQuantity = false;
                if (qtys[ii] == null || qtys[ii].trim().length() == 0) {
                    noQuantity = true;
                    msgList.add(skus[ii]);
                }
                int qty = 0;

                try {

                    qty = Integer.parseInt(qtys[ii]);
                    if (qty < 0) {
                    	msgList.add(skus[ii]);
                    }

                } catch (NumberFormatException exc) {

                    if (!noQuantity) {// if we got here because no quantity was entered, no need to be repetitive
                    	 msgList.add(skus[ii]);
                    }
                }

                if (qty > 0) {


                    String thissku = skus[ii].trim();
                    skus[ii] = thissku;
                    // Make sure this is not an inventory item.
                    ShoppingCartItemData scid;
                    if (pForm.getSkuType() == QuickOrderForm.DEFAULT_SKU_TYPE) {
                        scid = scd.findItemBySku(thissku);
                    } else {
                        scid = scd.findItemByMfgSku(thissku);
                    }

                    if (scid != null && scid.getIsaInventoryItem()) {
                        if (!site.hasModernInventoryShopping()) {
                            if (site.hasInventoryShoppingOn() &&
                                    scid.getInventoryParValue() > 0) {
                                Object[] params = new Object[1];
                                params[0] = thissku;
                                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.updateOnHandQuantity", params);
                                ae.add("error" + ii, new ActionError("error.simpleGenericError", errorMess));

                            }
                        }

                    }
                }
            }
        }

        if(msgList.size()>0 && !msgList.isEmpty()){
        	Object[] param = new Object[1];
        	param[0] = Utility.toCommaSting(msgList);
        	String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.invalidQtyMessage",param);

        	ae.add("error",
        			new ActionError("error.simpleGenericError",errorMess));
        	return ae;
        }

        if (ae.size() > 0) {
            return ae;
        }

        List skusList = new ArrayList();
        for (int i = 0; i < pForm.getItemSkus().length; i++) {
            if (pForm.getItemSkus()[i] != null && pForm.getItemSkus()[i].trim().length() > 0) {
                skusList.add(pForm.getItemSkus()[i]);
            }
        }

        ShoppingCartItemDataVector items = null;
        String storeType = appUser.getUserStore().getStoreType().getValue();
        try {

            if (pForm.getSkuType() == QuickOrderForm.DEFAULT_SKU_TYPE) {
                items = shoppingServEjb.getShoppingItemsBySku(storeType,
                        appUser.getSite(),
                        skusList,
                        ShopTool.createShoppingItemRequest(request),
                        SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
            } else {
                items = shoppingServEjb.getShoppingItemsByMfgSku(appUser.getSite(),
                        skusList,
                        ShopTool.createShoppingItemRequest(request),
                        SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
            }

        } catch (DataNotFoundException exc) {
            String mess = exc.getMessage();
            String errorMess = ClwI18nUtil.formatEjbError(request, mess);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        } catch (RemoteException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", exc.getMessage()));
            return ae;
        }

        // allow order forecasted items if acct property is set
        boolean allowOrderInventoryItems = true;
        if(appUser.getUserAccount().getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_INV_ITEMS).equals("false")){
			allowOrderInventoryItems = false;
			ae = dropInventoryItems(request, items);

			if(ae.size() > 0){
				return ae;
			}
        }

        Object[] itemArray = items.toArray();
        int size=itemArray .length;
        ae = analiseDuplications(request, pForm, itemArray);
        if (ae.size() > 0) {
            return ae;
        }

        ShoppingCartItemDataVector itemsForSave = new ShoppingCartItemDataVector();
        ae = fillItemsForSave(request, pForm, itemArray, pForm.getItemSkus(), pForm.getItemQtys(),size, itemsForSave);
        if (ae.size() > 0) {
            return ae;
        }

        if(pForm.getDuplicationFlag()){
        	return ae;
        }
        //ae = addToInventoryCart(shoppingServEjb,request,appUser,itemsForSave);
        if(!ShopTool.hasInventoryCartAccessOpen(request)) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable",null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        ShoppingCartData cartD = ShopTool.getCurrentInventoryCart(request.getSession());
        if (cartD == null) {

            cartD = new ShoppingCartData();
            cartD.setUser(appUser.getUser());
            cartD.setSite(appUser.getSite());

        }

        cartD.clearNewItems();
        ShoppingCartItemDataVector newItems = new ShoppingCartItemDataVector();
        for (int ii = 0; ii < items.size(); ii++) {
            ShoppingCartItemData sciD = (ShoppingCartItemData) items.get(ii);
            newItems.add(sciD);
        }

        ValidateActionMessage am = ShopTool.addItemsToCart(request, cartD, newItems);
        if (am.hasErrors()) {
            return am.getActionErrors();
        }
        if (am.hasWarnings()) {
            cartD.addWarningMessages(am.getWarnings());
        }

        if (cartD.getItemsQty() > 0) {
            try {

                if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.OVERRIDE_SHOPPING_RESTRICTION)) {
                    //cartD.reviseQuantities();
                	int siteId = appUser.getSite().getSiteId();
            		ShoppingCartLogic.reviseShoppingCartQuantities(cartD, orderEjb, siteId, newItems);
                }

                shoppingServEjb.saveInventoryShoppingCart(cartD, null, null);
            } catch (Exception e) {
                //String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noItemsSelected",null);
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
                return ae;
            }
        } else {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.noItemsSelected", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        ae = ShopTool.reloadInvShoppingCart(shoppingServEjb,session, null, appUser.getSite(), appUser.getUser(), appUser.getUserStore().getStoreType().getValue());

        ShoppingCartData newShoppingCart = ShopTool.getCurrentInventoryCart(session);
        newShoppingCart.addItemMessages(cartD.getItemMessages());
        newShoppingCart.addWarningMessages(cartD.getWarningMessages());
        session.setAttribute(Constants.INVENTORY_SHOPPING_CART,newShoppingCart);

        if(cartD.getItemMessages()!=null ){
      	  pForm.setWarningMessage(cartD.getItemMessages());
        }
        if(ae.size()>0){
            return ae;
        }else {
          	pForm.setConfirmMessage(ClwI18nUtil.getMessage(request,
                    "shoppingCart.text.actionMessage.itemAdded", null));
        }

        ae=init(request,pForm);
        return ae;


    }
}
