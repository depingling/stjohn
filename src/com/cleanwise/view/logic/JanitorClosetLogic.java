/**
 * Title:        JanitorClosetLogic
 * Description: This is the business logic class for the
 * JanitorClosetAction and JanitorClosetForm.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */

package com.cleanwise.view.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

import com.cleanwise.view.utils.*;

/**
 * Class description.
 *
 */
public class JanitorClosetLogic {

  public static ActionErrors
      init(HttpServletRequest request, JanitorClosetForm pForm)
    {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    CatalogInformation catalogInfEjb = null;
    try {
      catalogInfEjb = factory.getCatalogInformationAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No catalog Ejb pointer"));
      return ae;
    }

    ContractInformation contractInfEjb = null;
    try {
      contractInfEjb = factory.getContractInformationAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error",new ActionError("error.systemError","No contract Ejb pointer"));
      return ae;
    }

    ShoppingServices shoppingServEjb = null;
    try {
      shoppingServEjb = factory.getShoppingServicesAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error",new ActionError("error.systemError","No shopping services Ejb pointer"));
      return ae;
    }
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if(appUser==null){
      ae.add("error",new ActionError("error.systemError","No "+Constants.APP_USER+" session object"));
      return ae;
    }
    UserData user = appUser.getUser();
    if(appUser.getSite()==null){
      ae.add("error",new ActionError("error.systemError","No site information found"));
      return ae;
    }
    SiteData site = appUser.getSite();
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

    ShoppingCartItemDataVector items = null;
    try {

      items = shoppingServEjb.getJanitorCloset
    ( storeTypeProperty.getValue(), catalogId,
      contractId, contractOnly,
      0, siteId,
      SessionTool.getCategoryToCostCenterView(session, site.getSiteId()));


      items = sortItems(items,pForm.getOrderBy());
      int size = items.size();
      String[] qtyS = new String[size];
      for(int ii=0; ii<size; ii++) {

        ShoppingCartItemData sciD = (ShoppingCartItemData) items.get(ii);
        ItemShoppingHistoryData ishD = sciD.getShoppingHistory();
        int qty = 0;
        if(ishD!=null) {
          qty = ishD.getLastQty();
        }
        qtyS[ii]=""+qty;
      }
      pForm.setQuantity(qtyS);
      pForm.setItemIds(new int[size]);
      pForm.setCartItems(items);
      pForm.setOffset(0);
//    } catch(RemoteException exc) {
    } catch(Exception exc) {
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      exc.printStackTrace();
      return ae;
    }

     //Jd begin
     try{
       if("jd".equals(ClwCustomizer.getStoreDir())) {
         ae = initJd(request,pForm);
       }
     }catch(Exception exc) {
         ae.add("error",new ActionError("error.systemError","No store directory found"));
         return ae;
     }
     //Jd end
    return ae;
  }
    /***************************************************************************************/
   //Jd begin
    public static ActionErrors initJd(HttpServletRequest request, JanitorClosetForm pForm)
    {
      ActionErrors ae = new ActionErrors();
      HttpSession session = request.getSession();
      CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
      SiteData site = appUser.getSite();
      BigDecimal weightThreshold = site.getWeightThreshold();
      BigDecimal priceThreshold = site.getPriceThreshold();
      BigDecimal contractThreshold = site.getContractThreshold();
      String priceCode = site.getPriceCode();
      pForm.setPriceCode(priceCode);
      pForm.setWeightThreshold(weightThreshold);
      pForm.setPriceThreshold(priceThreshold);
      pForm.setContractThreshold(contractThreshold);
      return ae;
    }
    //Jd end


  //*****************************************************************************
  private static ShoppingCartItemDataVector sortItems(ShoppingCartItemDataVector pItems, int pSortBy){
    switch(pSortBy) {
    case Constants.ORDER_BY_CATEGORY:
      pItems = sortItemsByCategory(pItems);
      break;
    case Constants.ORDER_BY_CUST_SKU:
      pItems = sortItemsBySku(pItems);
      break;
    case Constants.ORDER_BY_NAME:
      pItems = sortItemsByName(pItems);
      break;
    }
    return pItems;
  }

    //*****************************************************************************
    private static ShoppingCartItemDataVector sortItemsByCategory(ShoppingCartItemDataVector pItems) {
        //return if nothing to sord
        if (pItems == null || pItems.size() <= 1) {
            return pItems;
        }
        //Order now
        ShoppingCartData.orderByCategory(pItems);

        return pItems;
    }

  //*****************************************************************************
  private static ShoppingCartItemDataVector sortItemsBySku(ShoppingCartItemDataVector pItems) {
    Object[] items = pItems.toArray();
    int size = items.length;
    for(int ii=0; ii<size-1; ii++) {
      boolean finishFlag = true;
      for(int jj=1; jj<size-ii; jj++) {
        ShoppingCartItemData sciD1 = ((ShoppingCartItemData)items[jj-1]);
        String name1 = sciD1.getActualSkuNum();
        ShoppingCartItemData sciD2 = ((ShoppingCartItemData)items[jj]);
        String name2 = sciD2.getActualSkuNum();
        if(name1.compareToIgnoreCase(name2)>0) {
          finishFlag = false;
          items[jj-1]=sciD2;
          items[jj]= sciD1;
        }

      }
      if(finishFlag) {
        break;
      }
    }
    pItems = new ShoppingCartItemDataVector();
    for(int ii=0; ii<size; ii++) {
      pItems.add(items[ii]);
    }
    return pItems;
  }
  //*****************************************************************************
  private static ShoppingCartItemDataVector sortItemsByName(ShoppingCartItemDataVector pItems) {
    Object[] items = pItems.toArray();
    int size = items.length;
    for(int ii=0; ii<size-1; ii++) {
      boolean finishFlag = true;
      for(int jj=1; jj<size-ii; jj++) {
        ShoppingCartItemData sciD1 = (ShoppingCartItemData) items[jj-1];
        String name1 = sciD1.getProduct().getCatalogProductShortDesc();
        ShoppingCartItemData sciD2 = (ShoppingCartItemData) items[jj];
        String name2 = sciD2.getProduct().getCatalogProductShortDesc();
        if(name1.compareToIgnoreCase(name2)>0) {
          finishFlag = false;
          items[jj-1]=sciD2;
          items[jj]= sciD1;
        }

      }
      if(finishFlag) {
        break;
      }
    }
    pItems = new ShoppingCartItemDataVector();
    for(int ii=0; ii<size; ii++) {
      pItems.add(items[ii]);
    }
    return pItems;
  }
  //*****************************************************************************
  public static ActionErrors addToCart(HttpServletRequest request, JanitorClosetForm pForm)
  {
    ActionErrors ae = new ActionErrors();

    if (ShopTool.isShoppingCartConsolidated(request)) {
        String errorMsg = ClwI18nUtil.getMessage(request, "shop.errors.addItemsIntoConsolidatedCart", null);
        ae.add("error", new ActionError("error.simpleGenericError", errorMsg));
        return ae;
    }

    HttpSession session = request.getSession();
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

    String[] quantityS = pForm.getQuantity();
    int[] itemIds = pForm.getItemIds();
    int[] quantity = new int[quantityS.length];
    if(itemIds.length<quantity.length) {
      ae.add("error",new ActionError("error.systemError","Wrong item identificator array size"));
      return ae;
    }
    List cartItems = pForm.getCartItems();
    //Check validity
    int cartItemsSize = cartItems.size();
    for(int ii=0; ii<quantityS.length; ii++) {
      String qqS = quantityS[ii];
      if(qqS!=null && qqS.trim().length()>0) {
        if(ii>=cartItemsSize) {
          String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.pageInfoDoesntMatchServer",null);
          ae.add("error",new ActionError("error.simpleGenericError",errorMess));
          return ae;
        }
        ShoppingCartItemData sciTemplateD = (ShoppingCartItemData)cartItems.get(ii);
        if(sciTemplateD.getProduct().getProductId()!=itemIds[ii]) {
          String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.pageInfoDoesntMatchServer",null);
          ae.add("error",new ActionError("error.simpleGenericError",errorMess));
          return ae;
        }
        try {
          int qq = Integer.parseInt(qqS);
          quantity[ii] = qq;
        } catch (NumberFormatException exc) {
          Object[] params = new Object[1];
          params[0] = qqS;
          String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.wrongQuantityNumberFormat",params);
          ae.add("error"+ii,new ActionError("error.simpleGenericError",errorMess));
        }
      }else{
        quantity[ii] = 0;
      }
    }
    if(ae.size()>0) {
      return ae;
    }
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if(appUser==null) {
      ae.add("error",new ActionError("error.systemError","No session user found"));
      return ae;
    }
    ShoppingCartData cartD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
    if(cartD==null) {
      cartD = new ShoppingCartData();
      cartD.setUser(appUser.getUser());
    }

    cartD.clearNewItems();
    ShoppingCartItemDataVector newItems = new ShoppingCartItemDataVector();

    for(int ii=0; ii<quantity.length; ii++) {
      if(quantity[ii]>0) {
        ShoppingCartItemData sciTemplateD = (ShoppingCartItemData)cartItems.get(ii);
        sciTemplateD.setQuantity(0);
        ShoppingCartItemData sciD = sciTemplateD.copy();
        sciD.setQuantity(quantity[ii]);
//        sciD.setCategory(null);
        newItems.add(sciD);
      }
      quantityS[ii]="";
    }

    if(newItems.isEmpty()) {
      String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noItemsSelected",null);
      ae.add("error",new ActionError("error.simpleGenericError",errorMess));
      return ae;
    }

    cartD.setSite(appUser.getSite());

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
    pForm.setQuantity(quantityS);
    return ae;
  }
 //******************************************************************************
  public static ActionErrors clear(HttpServletRequest request, JanitorClosetForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    String[] quantityS = pForm.getQuantity();
    for(int ii=0; ii<quantityS.length; ii++) {
      quantityS[ii] = "";
    }
    List sciDV = pForm.getCartItems();
    for(int ii=0; ii<sciDV.size(); ii++) {
       ShoppingCartItemData sciD = (ShoppingCartItemData) sciDV.get(ii);
       sciD.setQuantity(0);
    }
    pForm.setQuantity(quantityS);
    return ae;
  }
 //******************************************************************************
  public static ActionErrors sort(HttpServletRequest request, JanitorClosetForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    ShoppingCartItemDataVector cartItems = (ShoppingCartItemDataVector) pForm.getCartItems();
    String[] quantityS = new String[cartItems.size()];
    cartItems = sortItems(cartItems,pForm.getOrderBy());
    for(int ii=0; ii<cartItems.size(); ii++) {
       ShoppingCartItemData sciD = (ShoppingCartItemData) cartItems.get(ii);
       quantityS[ii] = ""+sciD.getShoppingHistory().getLastQty();
    }
    pForm.setQuantity(quantityS);
    pForm.setCartItems(cartItems);
    return ae;
  }
 //******************************************************************************
  public static ActionErrors recalculate(HttpServletRequest request, JanitorClosetForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    String[] quantityS = pForm.getQuantity();
    int[] quantity = new int[quantityS.length];
    List cartItems = pForm.getCartItems();
    //Check validity
    for(int ii=0; ii<quantityS.length; ii++) {
      String qqS = quantityS[ii];
      if(qqS!=null && qqS.trim().length()>0) {
        try {
          int qq = Integer.parseInt(qqS);
          quantity[ii] = qq;
        } catch (NumberFormatException exc) {
          Object[] params = new Object[1];
          params[0] = qqS;
          String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.wrongQuantityNumberFormat",params);
          ae.add("error"+ii,new ActionError("error.simpleGenericError",errorMess));
        }
      }else{
        quantity[ii] = 0;
      }
    }
    if(ae.size()>0) {
      return ae;
    }
    for(int ii=0; ii<quantity.length; ii++) {
      ShoppingCartItemData sciTemplateD = (ShoppingCartItemData)cartItems.get(ii);
      sciTemplateD.setQuantity(quantity[ii]);
    }
    return ae;
  }

    public static ActionErrors addToInventoryCart(HttpServletRequest request, JanitorClosetForm pForm) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No session user found"));
            return ae;
        }

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        ShoppingServices shoppingServEjb;
        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No shopping services Ejb pointer"));
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

        String[] quantityS = pForm.getQuantity();
        int[] itemIds = pForm.getItemIds();
        int[] quantity = new int[quantityS.length];
        if (itemIds.length < quantity.length) {
            ae.add("error", new ActionError("error.systemError", "Wrong item identificator array size"));
            return ae;
        }
        List cartItems = pForm.getCartItems();
        //Check validity
        int cartItemsSize = cartItems.size();
        for (int ii = 0; ii < quantityS.length; ii++) {
            String qqS = quantityS[ii];
            if (qqS != null && qqS.trim().length() > 0) {
                if (ii >= cartItemsSize) {
                    String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.pageInfoDoesntMatchServer", null);
                    ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                    return ae;
                }
                ShoppingCartItemData sciTemplateD = (ShoppingCartItemData) cartItems.get(ii);
                if (sciTemplateD.getProduct().getProductId() != itemIds[ii]) {
                    String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.pageInfoDoesntMatchServer", null);
                    ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                    return ae;
                }
                try {
                    int qq = Integer.parseInt(qqS);
                    quantity[ii] = qq;
                } catch (NumberFormatException exc) {
                    Object[] params = new Object[1];
                    params[0] = qqS;
                    String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.wrongQuantityNumberFormat", params);
                    ae.add("error" + ii, new ActionError("error.simpleGenericError", errorMess));
                }
            } else {
                quantity[ii] = 0;
            }
        }
        if (ae.size() > 0) {
            return ae;
        }

        ShoppingCartData cartD = ShopTool.getCurrentInventoryCart(session);
        if (cartD == null) {
            cartD = new ShoppingCartData();
            cartD.setUser(appUser.getUser());
            cartD.setSite(appUser.getSite());
        }

        ShoppingCartItemDataVector newItems = new ShoppingCartItemDataVector();
        cartD.clearNewItems();
        int i = 0;
        for (int ii = 0; ii < quantity.length; ii++) {
            if (quantity[ii] > 0) {
                ShoppingCartItemData sciTemplateD = (ShoppingCartItemData) cartItems.get(ii);
                if(!sciTemplateD.getIsaInventoryItem()){
                    sciTemplateD.setQuantity(0);
                    ShoppingCartItemData sciD = sciTemplateD.copy();
                    sciD.setQuantity(quantity[ii]);
                    newItems.add(sciD);
                  } else {
                     Object[] param = new Object[1];
                     param[0] = sciTemplateD.getActualSkuNum();
                     String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.attemptToAddInvItemToInvCart", param);
                     ae.add("error" + (++i), new ActionError("error.simpleGenericError", errorMess));
                  }
            }
            quantityS[ii] = "";
        }
        if (ae.size() > 0) {
            return ae;
        }

        if (newItems.isEmpty()) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.noItemsSelected", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
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

        ae = ShopTool.reloadInvShoppingCart(shoppingServEjb, request.getSession(), null, appUser.getSite(), appUser.getUser(), appUser.getUserStore().getStoreType().getValue());

        ShoppingCartData newShoppingCart = ShopTool.getCurrentInventoryCart(session);
        newShoppingCart.setItemMessages(new ArrayList());
        newShoppingCart.addItemMessages(cartD.getItemMessages());
        newShoppingCart.addWarningMessages(cartD.getWarningMessages());
        session.setAttribute(Constants.INVENTORY_SHOPPING_CART,newShoppingCart);

        if (ae.size() > 0) {
            return ae;
        }
        pForm.setQuantity(quantityS);
        return ae;
    }
}
