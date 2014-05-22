/**
 * Title:        UserShopLogic
 * Description:  This is the business logic class for the UserShopAction and UserShopForm.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */
package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.util.I18nUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.ShoppingCartForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.*;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

import com.cleanwise.service.api.session.Order; // new
import org.apache.log4j.Category;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.XMLSerializer;

/**
 * Class description.
 *
 */
public class ShoppingCartLogic {

  static final Category log = Category.getInstance(ShoppingCartLogic.class);
	
  public static ActionErrors init(HttpServletRequest request, ShoppingCartForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }

    if (ShopTool.isModernInventoryShopping(request)
             && !ShopTool.hasDiscretionaryCartAccessOpen(request)) {

          String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.discretionaryCartUnavailable", null);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
    }

      CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(
      Constants.APP_USER);
      if (appUser.getSite() != null) {
          SelectShippingAddressLogic.setShoppingSessionObjects
          (session, appUser);
      }

      //Populate from session object
      ShoppingCartData shoppingCartD = ShopTool.getCurrentShoppingCart(request);
      ShoppingCartItemDataVector scItemDV = shoppingCartD.getItems();

      // create a list of existing order items and its quantity map to prevent double count
      // on MAX QTY
      Map itemQuantityMap = null;
      if (shoppingCartD.getPrevOrderData() != null){
    	  itemQuantityMap = new HashMap();
    	  shoppingCartD.setPrevItemsQuantityMap(itemQuantityMap);
      }
      if(scItemDV!=null) {
          for(Iterator iter= scItemDV.iterator(); iter.hasNext();) {
              ShoppingCartItemData scID = (ShoppingCartItemData) iter.next();
              int qty = scID.getQuantity();
              if(qty>0) {
                  scID.setQuantityString(""+qty);
                  if (itemQuantityMap != null)
                	  itemQuantityMap.put(new Integer(scID.getItemId()), new Integer(qty));
              }
          }
      }

      pForm.setOffset(0);
      int orderBy = pForm.getOrderBy();

      switch(orderBy) {
          case Constants.ORDER_BY_CATEGORY:
              shoppingCartD.orderByCategory();
              break;
          case Constants.ORDER_BY_CUST_SKU:
              shoppingCartD.orderBySku();
              break;
          case Constants.ORDER_BY_NAME:
              shoppingCartD.orderByName();
              break;
          default:
              orderBy = Constants.ORDER_BY_CATEGORY;
              shoppingCartD.orderByCategory();
      }

      pForm.setOrderBy(orderBy);
      pForm.setSelectBox(new long[0]);
      pForm.setCartItems(shoppingCartD.getItems());
      pForm.setShoppingCart(shoppingCartD);

      shoppingCartD.setOrderBy(orderBy);
      return ae;
  }

    public static ActionErrors inventoryInit(HttpServletRequest request, ShoppingCartForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        if(!ShopTool.hasInventoryCartAccessOpen(request)) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable",null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        // #5033 Enable Inventory checking
        if (!appUser.getSite().hasModernInventoryShopping()) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.inventoryShoppingNoAvailable",null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        pForm = new ShoppingCartForm();

        SelectShippingAddressLogic.setShoppingSessionObjects(session, appUser);


        ShoppingServices shopServEjb = factory.getShoppingServicesAPI();
        //Populate from session object
        ShoppingCartData shoppingCartD = ShopTool.getCurrentInventoryCart(session);
        /*ShoppingCartData shoppingCartD = shopServEjb.getInvShoppingCart(
                appUser.getUserStore().getStoreType().getValue(),
                appUser.getUser(), appUser.getSite(), false,
                SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));*/
        
        //STJ-4081 - the above call creates a merged cart from the IL and OG carts, but we need to store both 
        //of them separately on the form as well so we can have access to the history records of both
        ShoppingCartData shoppingCartOG = shopServEjb.getInventoryCartOG(appUser.getUser(), appUser.getSite(), appUser.getUserStore().getStoreType().getValue(),SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
        pForm.setFormCartOG(shoppingCartOG);
        ShoppingCartData shoppingCartIL = shopServEjb.getInventoryCartIL(appUser.getUserStore().getStoreType().getValue(),appUser.getUser(),appUser.getSite(), SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
        pForm.setFormCartIL(shoppingCartIL);

        pForm.setOffset(0);
        int orderBy = pForm.getOrderBy();
        Properties configProps = ClwCustomizer.getUiProperties(request);
        String mySortBy = configProps.getProperty("orderGuideSecondarySorting");

        if(mySortBy!=null && mySortBy.equalsIgnoreCase("ORDER_BY_CATEGORY_AND_SKU")){
         orderBy=Constants.ORDER_BY_CATEGORY_AND_SKU;
        }

        switch (orderBy) {
            case Constants.ORDER_BY_CATEGORY:
                shoppingCartD.orderByCategory();
                break;
            case Constants.ORDER_BY_CUST_SKU:
                shoppingCartD.orderBySku();
                break;
            case Constants.ORDER_BY_NAME:
                shoppingCartD.orderByName();
                break;
            case Constants.ORDER_BY_CATEGORY_AND_SKU:
            	shoppingCartD = orderByCategoryAndSku(shoppingCartD);
            	break;
            default:
                orderBy = Constants.ORDER_BY_CATEGORY;
                shoppingCartD.orderByCategory();
        }
        pForm.setShoppingCart(shoppingCartD);
        pForm.setOrderBy(orderBy);
        pForm.setSelectBox(new long[0]);
        pForm.setCartItems(shoppingCartD.getItems());
        shoppingCartD.setOrderBy(orderBy);

        pForm.setShoppingCart(shoppingCartD);
        session.setAttribute(Constants.INVENTORY_SHOPPING_CART_FORM, pForm);
        String cofMessage = "shoppingCart.text.actionMessage.cartUpdated";
        if (ae.size()==0){
                  pForm.setConfirmMessage(ClwI18nUtil.getMessage(request,
             getConfirmationMessageView(request), null));
                  //This form object is probably called more than once here. So we have explicitly
	                //set the message as a request attribute to avoid being overwritten.
	                request.setAttribute(Constants.XPEDX_CONFIRM_MSG,pForm.getConfirmMessage());
        }
        return ae;
    }

    private static ShoppingCartData orderByCategoryAndSku(ShoppingCartData pShoppingCartD) {
        int size = pShoppingCartD.getItems().size();
        if(size<=1) return pShoppingCartD;
        ShoppingCartItemData[] items = new ShoppingCartItemData[size];
        for(int ii=0; ii<size; ii++) {
          items[ii] = (ShoppingCartItemData) pShoppingCartD.getItem(ii);
        }
        for(int ii=0; ii<size-1; ii++) {
          boolean exit = true;
          for(int jj=1; jj<size-ii; jj++) {
            ShoppingCartItemData item1 = items[jj-1];
            ShoppingCartItemData item2 = items[jj];
            String name1 = "";
            String name2 = "";
            CatalogCategoryData ccD1 = item1.getCategory();
            if(ccD1!=null) {
              name1 = ccD1.getCatalogCategoryShortDesc();
              if(name1==null) name1 = "";
            }
            String ss = item1.getActualSkuNum();
            if(ss!=null) name1+=ss;
            CatalogCategoryData ccD2 = item2.getCategory();
            if(ccD2!=null) {
              name2 = ccD2.getCatalogCategoryShortDesc();
              if(name1==null) name1 = "";
            }
            ss = item2.getActualSkuNum();
            if(ss!=null) name2+=ss;
            if(name1.compareToIgnoreCase(name2)>0) {
              exit = false;
              items[jj-1] = item2;
              items[jj] = item1;
            }
          }
          if(exit) break;
        }
        ShoppingCartData retItemD = new ShoppingCartData();
        for(int ii=0; ii<size; ii++) {
        	retItemD.addItem(items[ii]);
        }
        return retItemD;
      }

    private static String getConfirmationMessageView(HttpServletRequest request) {
      String message = "shoppingCart.text.actionMessage.cartUpdated";
      if("showAutoDistro".equals(request.getParameter("action"))){
           message = "shoppingCart.text.actionMessage.autoDistroUpdated";
      }
      return message;
    }

  //****************************************************************************
  public static ActionErrors recalc(HttpServletRequest request,
                                    ShoppingCartForm pForm)
  {

    ActionErrors ae = new ActionErrors();
    if(ae.size()>0) {
        return ae;
    }

    /// Checking of the current cart on consolidation.
    /// The cart with consolidated items should not be changed.
    if (ShopTool.isShoppingCartConsolidated(request)) {
        String errorMsg = ClwI18nUtil.getMessage(request, "shop.errors.changeItemsInConsolidatedCart", null);
        ae.add("error", new ActionError("error.simpleGenericError", errorMsg));
        return ae;
    }

    try {
        HttpSession session = request.getSession();
        // Get the updated qty information.

        /*ShoppingCartData shoppingCartD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
        ShoppingCartItemDataVector cartItems = shoppingCartD.getItems();
        ShoppingCartItemDataVector updatedItems = new ShoppingCartItemDataVector();
        for(int ii=0; ii<cartItems.size(); ii++) {
            ShoppingCartItemData cartItem = (ShoppingCartItemData) cartItems.get(ii);
        	String quantityS = !Utility.isSet(cartItem.getQuantityString()) ? "0" : cartItem.getQuantityString();
        	if (Integer.parseInt(quantityS) != cartItem.getQuantity()){        	
        		updatedItems.add(cartItem);        	          	  
	        }  
        }*/
        ShoppingCartData shoppingCartD = pForm.getShoppingCart();
        ShoppingCartItemDataVector cartItems = pForm.getCartItems();
        ShoppingCartItemDataVector updatedItems = new ShoppingCartItemDataVector();
        if(Utility.isSet(cartItems)){
        	for(int idx=0; idx<cartItems.size();idx++) {
        		ShoppingCartItemData cartItem = (ShoppingCartItemData) cartItems.get(idx);
        		String quantityS = !Utility.isSet(cartItem.getQuantityString()) ? "0" : cartItem.getQuantityString();
            	if (Integer.parseInt(quantityS) != cartItem.getQuantity()){        	
            		updatedItems.add(cartItem);        	          	  
    	        }
        	}
        }
        long[] toDelete = pForm.getSelectBox() != null ? pForm.getSelectBox() : new long[0];

        List removedItems = new ArrayList();
        for(int ii=0; ii<cartItems.size();) {
          ShoppingCartItemData sciD = (ShoppingCartItemData)cartItems.get(ii);
          int orderNum = sciD.getOrderNumber();
          int id = sciD.getProduct().getProductId();
          long code = (long)id*10000+(long)orderNum;
          int jj = 0;
          for(; jj<toDelete.length; jj++) {
            if(code==toDelete[jj]) {
            	cartItems.remove(ii);
              removedItems.add(sciD);
              break;
            }
          }
          if(jj==toDelete.length) ii++;
        }

        ShoppingCartItemDataVector recalcItems = pForm.getCartItems();

        ShoppingCartItemDataVector recalcEntries = recalcItems.copy();
        ae = ShopTool.calcInputQuantities(request, recalcEntries);
        if (ae.size() > 0) {
            return ae;
        }

        ae = ShopTool.validateBudgetThreshold(request, recalcEntries);
        if (ae.size() > 0) {
            return ae;
        }

        //removes a possible side effects of a copy.
        updateItemsQuantity(recalcItems, recalcEntries);

        ae = ShopTool.updateShoppingCartQty(request, recalcItems);
        if (ae.size() > 0) {
            return ae;
        }

        pForm.setRemovedItems(removedItems);
        //pForm.setCartItems(cartItems);

        ae = saveShoppingCart(session, updatedItems);
        
        ShoppingCartData sCartD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
        ShoppingCartItemDataVector cItems = sCartD.getItems();
        pForm.setCartItems(cItems);
        
        if(ae.size()>0) {
            return ae;
        }  else {
            List itemMessages = ShopTool.getCurrentShoppingCart(request).getItemMessages();
            if (!Utility.isSet(itemMessages)) {
            	pForm.setConfirmMessage(ClwI18nUtil.getMessage(request,
            			"shoppingCart.text.actionMessage.cartUpdated", null));
            	//This form object is probably called more than once here. So we have explicitly
            	//set the message as a request attribute to avoid being overwritten.
            	request.setAttribute(Constants.XPEDX_CONFIRM_MSG,pForm.getConfirmMessage());
            }
            else {
            	pForm.setConfirmMessage(null);
            }
        }
    } catch (Exception e) {
        pForm.setConfirmMessage(null);
        e.printStackTrace();
    }
    return ae;
  }

    private static void updateItemsQuantity(ShoppingCartItemDataVector pItems, ShoppingCartItemDataVector pEntries) {
        if (pItems != null && pEntries != null) {
            for (Object oEntry : pEntries) {
                ShoppingCartItemData entry = (ShoppingCartItemData) oEntry;
                for (Object oItem : pItems) {
                    ShoppingCartItemData item = (ShoppingCartItemData) oItem;
                    if (item.getItemId() == entry.getItemId()) {
                        item.setQuantity(entry.getQuantity());
                        break;
                    }
                }
            }
        }
    }

    //****************************************************************************
    public static ActionErrors removeInvSelected(HttpServletRequest request, ShoppingCartForm pForm) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));

        }
        if (ae.size() > 0) {
            return ae;
        }

        try {
            ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();

            SiteData site         = ShopTool.getCurrentSite(request);
            CleanwiseUser clwUser = ShopTool.getCurrentUser(request);
            UserData user         = clwUser.getUser();
            StoreData store       = clwUser.getUserStore();

            if(!ShopTool.hasInventoryCartAccessOpen(request)) {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable",null);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
            }

            long[] removeEntries = pForm.getSelectBox();
            if(removeEntries == null || !(removeEntries.length>0) ){

          	  String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noItemsForDeletion",null);
            		ae.add("error",
                        new ActionError("error.simpleGenericError",errorMess));

          	  return ae;
            }

            ShoppingCartData currentCart = shoppingServEjb.getInventoryCartOG(user, site, store.getStoreType().getValue(),SessionTool.getCategoryToCostCenterView(session, site.getSiteId()));

            ShoppingCartItemDataVector items = currentCart.getItems();

            for (int i = 0; i < removeEntries.length; i++) {

                if (removeEntries[i] > 0) {
                	//remove the last 4 digits which have the order number to match only item id
                	String t = Long.toString(removeEntries[i]);
                	String itemId = t.substring(0, t.length()-4);

                    try {
                        if (!items.isEmpty()) {
                            Iterator it = items.iterator();
                            while (it.hasNext()) {
                                ShoppingCartItemData item = (ShoppingCartItemData) it.next();

                                int id = item.getProduct().getProductId();
                                String thisItemId = Integer.toString(id);

                                if(thisItemId.equalsIgnoreCase(itemId)){
                                    it.remove();
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            shoppingServEjb.saveInventoryCartOG(currentCart);

            ae = inventoryInit(request, pForm);

            if(ae.size()>0) {
                return ae;
              } else {
	                pForm.setConfirmMessage(ClwI18nUtil.getMessage(request,
	                          "shoppingCart.text.actionMessage.itemsDeletedFromCart", null));
	                //This form object is probably called more than once here. So we have explicitly
	                //set the message as a request attribute to avoid being overwritten.
	                request.setAttribute(Constants.XPEDX_CONFIRM_MSG,pForm.getConfirmMessage());
              }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ae;
    }

//  ****************************************************************************
    public static ActionErrors removeInvSelectedNewXpedx(HttpServletRequest request, ShoppingCartForm pForm) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));

        }
        if (ae.size() > 0) {
            return ae;
        }

        try {
            ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();

            SiteData site         = ShopTool.getCurrentSite(request);
            CleanwiseUser clwUser = ShopTool.getCurrentUser(request);
            UserData user         = clwUser.getUser();
            StoreData store       = clwUser.getUserStore();

            if(!ShopTool.hasInventoryCartAccessOpen(request)) {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable",null);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
            }

            long[] removeEntries = pForm.getSelectBox();
            if(removeEntries == null || !(removeEntries.length>0) ){

          	  String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noItemsForDeletion",null);
            		ae.add("error",
                        new ActionError("error.simpleGenericError",errorMess));

          	  return ae;
            }

            ShoppingCartData currentCart = shoppingServEjb.getInventoryCartOG(user, site, store.getStoreType().getValue(),SessionTool.getCategoryToCostCenterView(session, site.getSiteId()));

            ShoppingCartItemDataVector items = currentCart.getItems();

            for (int i = 0; i < removeEntries.length; i++) {

                if (removeEntries[i] > 0) {

                    try {
                        if (!items.isEmpty()) {
                            Iterator it = items.iterator();
                            while (it.hasNext()) {
                                ShoppingCartItemData item = (ShoppingCartItemData) it.next();
                                if ((long) item.getItemId() == removeEntries[i]) {
                                    it.remove();
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            shoppingServEjb.saveInventoryCartOG(currentCart);

            ae = inventoryInit(request, pForm);

            if(ae.size()>0) {
                return ae;
              } else {
	                pForm.setConfirmMessage(ClwI18nUtil.getMessage(request,
	                          "shoppingCart.text.actionMessage.itemsDeletedFromCart", null));
	                //This form object is probably called more than once here. So we have explicitly
	                //set the message as a request attribute to avoid being overwritten.
	                request.setAttribute(Constants.XPEDX_CONFIRM_MSG,pForm.getConfirmMessage());
              }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ae;
    }


     //****************************************************************************
    public static ActionErrors removeInvAll(HttpServletRequest request, ShoppingCartForm pForm) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));

        }
        if (ae.size() > 0) {
            return ae;
        }

        try {
            ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();

            SiteData site         = ShopTool.getCurrentSite(request);
            CleanwiseUser clwUser = ShopTool.getCurrentUser(request);
            UserData user         = clwUser.getUser();
            StoreData store       = clwUser.getUserStore();

            if(!ShopTool.hasInventoryCartAccessOpen(request)) {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable",null);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
            }

            ShoppingCartData currentCart = shoppingServEjb.getInventoryCartOG(user, site, store.getStoreType().getValue(),SessionTool.getCategoryToCostCenterView(session, site.getSiteId()));

            currentCart.setItems(null);

            shoppingServEjb.saveInventoryCartOG(currentCart);

            ae = inventoryInit(request, pForm);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ae;
    }


    public static ActionErrors calcInvOrderQty(HttpServletRequest request, ShoppingCartForm pForm) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        try {

            CleanwiseUser appUser = ShopTool.getCurrentUser(request);

            if(!ShopTool.hasInventoryCartAccessOpen(request)) {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable",null);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
            }

            ShoppingServices shopServEjb = factory.getShoppingServicesAPI();

            ae=recalInvCartQty(request,pForm);
            if(ae.size()>0){
                return ae;
            }

            ShoppingCartData shoppingCartD = shopServEjb.getInvShoppingCart(
                    appUser.getUserStore().getStoreType().getValue(),
                    appUser.getUser(), appUser.getSite(),false,
                    SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));


            ShoppingCartItemDataVector cartLines = pForm.getCartItems();

            for (int i = 0; i < cartLines.size(); i++) {
                ShoppingCartItemData n = (ShoppingCartItemData) cartLines.get(i);
                if (!n.getIsaInventoryItem()) {
                    shoppingCartD.updateItemQuantity(n);
                } else  {
                    shoppingCartD.calculateInvValue(n);
                }
            }

            int orderBy = pForm.getOrderBy();

            pForm.setShoppingCart(shoppingCartD);
            pForm.setOrderBy(orderBy);
            pForm.setSelectBox(new long[0]);
            pForm.setCartItems(shoppingCartD.getItems());
            shoppingCartD.setOrderBy(orderBy);
            pForm.setShoppingCart(shoppingCartD);
            session.setAttribute(Constants.INVENTORY_SHOPPING_CART_FORM, pForm);
            if (ae.size()==0){
                     pForm.setConfirmMessage(ClwI18nUtil.getMessage(request,
                "shoppingCart.text.actionMessage.cartUpdated", null));
                     //This form object is probably called more than once here. So we have explicitly
 	                //set the message as a request attribute to avoid being overwritten.
 	                request.setAttribute(Constants.XPEDX_CONFIRM_MSG,pForm.getConfirmMessage());
            }
        } catch (Exception e) {
            pForm.setConfirmMessage(null);
            e.printStackTrace();
        }
        return ae;
    }

    //****************************************************************************
    public static ActionErrors updateInvShoppingCart(HttpServletRequest request, ShoppingCartForm pForm) {
        ActionErrors ae = new ActionErrors();
        ae = verifyInvShoppingCart(request,pForm);
        if(ae.size()>0) {
            return ae;
        }

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        try {
            ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();

            Order orderEjb = factory.getOrderAPI(); //new statement for Shopping Control Check

            List cartItems = pForm.getCartItems();

            SiteData site         = ShopTool.getCurrentSite(request);
            CleanwiseUser clwUser = ShopTool.getCurrentUser(request);
            UserData user         = clwUser.getUser();
            StoreData store       = clwUser.getUserStore();

            if(!ShopTool.hasInventoryCartAccessOpen(request)) {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable",null);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
            }

            ShoppingCartData currentCartOG = shoppingServEjb.getInventoryCartOG(user, site, store.getStoreType().getValue(),SessionTool.getCategoryToCostCenterView(session, site.getSiteId()));
            ShoppingCartData currentCartIL = shoppingServEjb.getInventoryCartIL(store.getStoreType().getValue(),user,site, SessionTool.getCategoryToCostCenterView(session, site.getSiteId()));
            ShoppingCartItemDataVector updatedInvItems= new ShoppingCartItemDataVector();
            ShoppingCartItemDataVector updatedRegularItems= new ShoppingCartItemDataVector();

            //STJ-4081 - make sure the cart has not been updated by another user in between
            //the time this user started the update and the time they ended it.
            //Retrieve the most recent history record from the database
            ShoppingInfoData mostRecentDBUpdate = getMostRecentCartHistoryRecord(currentCartIL, 
            		currentCartOG, ShoppingCartData.CART_ITEM_UPDATE);
            //return an error if the cart was updated.
            if (mostRecentDBUpdate != null) {
                ShoppingInfoData mostRecentFormUpdate = getMostRecentCartHistoryRecord(pForm.getFormCartIL(), 
                		pForm.getFormCartOG(), ShoppingCartData.CART_ITEM_UPDATE);
                if (mostRecentFormUpdate == null ||
                		mostRecentFormUpdate.getModDate().compareTo(mostRecentDBUpdate.getModDate()) < 0) {
                        // Error message
                    String username = mostRecentDBUpdate.getModBy();
                    String[] args = new String[]{username};
                    String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.multipleUserCollision",args);
                    ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                    return ae;
                }
            }
            
            ShoppingCartItemDataVector invItems= new ShoppingCartItemDataVector();
            ShoppingCartItemDataVector regularItems= new ShoppingCartItemDataVector();
            ShoppingCartItemDataVector newCartItems= new ShoppingCartItemDataVector();
            int i=0;

            if (cartItems != null && cartItems.size() > 0) {
            	for(int ii=0; ii<cartItems.size(); ii++) {
                    ShoppingCartItemData cartItem = (ShoppingCartItemData) cartItems.get(ii);
                
                    if (cartItem.getIsaInventoryItem()) {
                        invItems.add(cartItem);
                        String quantityS = !Utility.isSet(cartItem.getQuantityString()) ? "0" : cartItem.getQuantityString();
                        if (!Utility.isEqual(quantityS, pForm.getQuantity()[ii])){
                        	updatedInvItems.add(cartItem);
                        }
                    } else {
                        regularItems.add(cartItem);
                        String quantityS = !Utility.isSet(cartItem.getQuantityString()) ? "0" : cartItem.getQuantityString();
                                if (!Utility.isEqual(quantityS, pForm.getQuantity()[ii])){
                        	updatedRegularItems.add(cartItem);
                        }
                    }
                }
            }


            long[] removeEntries = pForm.getSelectBox() != null ? pForm.getSelectBox() : new long[0];

            for(Iterator iter = regularItems.iterator(); iter.hasNext();){
            	ShoppingCartItemData scid = (ShoppingCartItemData)iter.next();
            	String qty = scid.getQuantityString();
            	int qq =0;
            	if(qty!=null && qty.length()>0){
            		try {
                        qq = Integer.parseInt(qty);
                        if(qq <= 0){
                        	iter.remove();
                        }
            		}catch (NumberFormatException exc) {
            			exc.printStackTrace();
            		}
            	}
            }

            for (int x = 0; x < removeEntries.length; x++) {
                if (removeEntries[x] > 0) {
                    try {
                        if (!regularItems.isEmpty()) {
                            Iterator it = regularItems.iterator();
                            while (it.hasNext()) {
                                ShoppingCartItemData item = (ShoppingCartItemData) it.next();
                                if ((long) item.getItemId() == removeEntries[x]) {
                                    it.remove();
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            newCartItems.addAll(invItems);
            newCartItems.addAll(regularItems);

            ae = ShopTool.validateBudgetThreshold(request, newCartItems);
            if (!ae.isEmpty()) {
                return ae;
            }

            currentCartOG.setItems(regularItems);
            currentCartIL.setItems(invItems);

            CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
            if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.OVERRIDE_SHOPPING_RESTRICTION)) {
              /*** old code for Shopping Control Check
              currentCartOG.reviseQuantities();
              currentCartIL.reviseQuantities();
              if(currentCartOG.getItemMessages().size()>0 ){
            	  currentCartOG.setWarningMessages(currentCartOG.getItemMessages());

              }
              if(currentCartIL.getItemMessages().size()>0 ){
            	  currentCartIL.setWarningMessages(currentCartIL.getItemMessages());

              }
              ***/
              //new code for Shopping Control Check: Start	
              int siteId = site.getSiteId();
              log.info("updateInvShoppingCart()=> siteId = " + siteId);
              // update regular cart item
              
              if (updatedRegularItems.size() > 0)
            	  currentCartOG = reviseShoppingCartQuantities(currentCartOG, orderEjb, siteId, updatedRegularItems); // new statement
              
              if (updatedInvItems.size() > 0)
            	  currentCartIL = reviseShoppingCartQuantities(currentCartIL, orderEjb, siteId, updatedInvItems); // new statement
              
              if(currentCartOG.getItemMessages().size()>0 ){
            	  currentCartOG.setWarningMessages(currentCartOG.getItemMessages());

              }
              if(currentCartIL.getItemMessages().size()>0 ){
            	  currentCartIL.setWarningMessages(currentCartIL.getItemMessages());

              }
              //new code for Shopping Control Check: End
            }
            boolean isUsedPhysicalInventoryAlgorithm = ShopTool.isUsedPhysicalInventoryAlgorithm(request);
            boolean isPhysicalCartAvailable = ShopTool.isPhysicalCartAvailable(request);
            boolean isPhysCart = isUsedPhysicalInventoryAlgorithm && isPhysicalCartAvailable;

            shoppingServEjb.saveInventoryShoppingCart(currentCartOG,currentCartIL, isPhysCart);

            ae = inventoryInit(request, pForm);

            ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.INVENTORY_SHOPPING_CART);

            shoppingCart.addWarningMessages(currentCartOG.getWarningMessages());
            shoppingCart.addWarningMessages(currentCartIL.getWarningMessages());
            shoppingCart.addItemMessages(currentCartOG.getItemMessages());
            shoppingCart.addItemMessages(currentCartIL.getItemMessages());

            session.setAttribute(Constants.INVENTORY_SHOPPING_CART,shoppingCart);


            for(int m=0; m<shoppingCart.getItemMessages().size(); m++){

            	ShoppingCartData.CartItemInfo cii =
                    (ShoppingCartData.CartItemInfo) shoppingCart.getItemMessages().get(m);
            	//String skuNum = cii.mCartItemData.getActualSkuNum();
            	ArrayList al = cii.getI18nItemMessageAL();
            	String key = (String) al.get(0);

            	Object[]  params = null;
            	if(al.size()>1) {
            		params = new Object[6];
            		for(int ii=0; ii<al.size()-1; ii++) {
            			params[ii] = (Object)al.get(ii+1);
            		}
            	}


            	String errorMess = ClwI18nUtil.getMessage(request,key,params);


                ae.add("error",
                        new ActionError("error.simpleGenericError",errorMess));
            }
            if(ae.size()>0){
            	return ae;
            }

        } catch (Exception e) {
            //pForm.setConfirmMessage(null);
            e.printStackTrace();
        }

        return ae;
    }

    public static ActionMessages updateJcpInvShoppingCart(HttpServletRequest request, ShoppingCartForm pForm) {

        ActionMessages am = new ActionMessages();
        am = verifyJcpInvShoppingCart(request,pForm);

        if(am.size()>0) {
            boolean errorsFl = false;
            for(Iterator iter = am.get(); iter.hasNext();) {
                ActionMessage aa = (ActionMessage) iter.next();
                if(aa instanceof ActionError) {
                    return am;
                }
            }
        }
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        try {

            ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();

            Order orderEjb = null;

            try {
            	orderEjb = factory.getOrderAPI();
            } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
                am.add("error",new ActionError("error.systemError","No Order Ejb pointer"));
                return am;
            }

            List cartItems = pForm.getCartItems();

            SiteData site         = ShopTool.getCurrentSite(request);
            CleanwiseUser clwUser = ShopTool.getCurrentUser(request);
            UserData user         = clwUser.getUser();
            StoreData store       = clwUser.getUserStore();

            if(!ShopTool.hasInventoryCartAccessOpen(request)) {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable",null);
                am.add("error", new ActionError("error.simpleGenericError", errorMess));
                return am;
            }

            ShoppingCartData currentCartOG = shoppingServEjb.getInventoryCartOG(user, site, store.getStoreType().getValue(),SessionTool.getCategoryToCostCenterView(session, site.getSiteId()));
            ShoppingCartData currentCartIL = shoppingServEjb.getInventoryCartIL(store.getStoreType().getValue(),user,site,SessionTool.getCategoryToCostCenterView(session, site.getSiteId()) );

            ShoppingCartItemDataVector invItems= new ShoppingCartItemDataVector();
            ShoppingCartItemDataVector regularItems= new ShoppingCartItemDataVector();
            if (cartItems != null) {
                for (Iterator it = cartItems.iterator(); it.hasNext();) {
                    ShoppingCartItemData cartItem = (ShoppingCartItemData) it.next();
                    if (cartItem.getIsaInventoryItem()) {
                        invItems.add(cartItem);
                    } else {
                        regularItems.add(cartItem);
                    }
                }
            }

            CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);

            ShoppingCartItemDataVector newCartItems = new ShoppingCartItemDataVector();
            newCartItems.addAll(invItems);
            newCartItems.addAll(regularItems);

            ActionErrors ae = ShopTool.validateBudgetThreshold(request, newCartItems);
            if (!ae.isEmpty()) {
                return ae;
            }

            currentCartOG.setItems(regularItems);
            currentCartIL.setItems(invItems);

            if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.OVERRIDE_SHOPPING_RESTRICTION)) {
              /*** Old code for Shopping Control Check
              currentCartOG.reviseQuantities();
              currentCartIL.reviseQuantities();
              ***/
              int siteId = site.getSiteId();
              log.info("updateJcpInvShoppingCart => siteId = " + siteId);
              currentCartOG = reviseShoppingCartQuantities(currentCartOG, orderEjb, siteId); // new statement for Shopping Control Check
              currentCartIL = reviseShoppingCartQuantities(currentCartOG, orderEjb, siteId); // new statement for Shopping Control Check
            }

            shoppingServEjb.saveInventoryShoppingCart(currentCartOG,currentCartIL);

            if (invItems.size()>0) {
                //Check first time on Hand set
                boolean updateFl = false;
                for (Iterator it = invItems.iterator(); it.hasNext();) {
                    ShoppingCartItemData cartItem = (ShoppingCartItemData) it.next();
                    String qtyStr = cartItem.getQuantityString();
                    String qtyOnHandStr = cartItem.getInventoryQtyOnHandString();
                    if (!Utility.isSet(qtyStr) && Utility.isSet(qtyOnHandStr) ) {
                        int recQty = cartItem.getInventoryParValue() - cartItem.getInventoryQtyOnHand();
                        if (recQty < 0) recQty = 0;
                        cartItem.setQuantity(recQty);
                        cartItem.setQuantityString(String.valueOf(recQty));
                        updateFl = true;
                    }
                }
                if(updateFl) {
                    currentCartIL.setItems(invItems);
                    shoppingServEjb.saveInventoryShoppingCart(currentCartOG,currentCartIL);
                }
            }



            ae = inventoryInit(request, pForm);
            am.add(ae);

            ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.INVENTORY_SHOPPING_CART);

            shoppingCart.addWarningMessages(currentCartOG.getWarningMessages());
            shoppingCart.addWarningMessages(currentCartIL.getWarningMessages());
            shoppingCart.addItemMessages(currentCartOG.getItemMessages());
            shoppingCart.addItemMessages(currentCartIL.getItemMessages());

            session.setAttribute(Constants.INVENTORY_SHOPPING_CART,shoppingCart);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return am;
    }
    //****************************************************************************
    public static ActionErrors updateAutoDistro(HttpServletRequest request, ShoppingCartForm pForm) {

        ActionErrors ae = new ActionErrors();
        ae = verifyInvShoppingCart(request,pForm);
        if(ae.size()>0) {
            return ae;
        }

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        try {
            ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();
            List cartItems = pForm.getCartItems();

            SiteData site         = ShopTool.getCurrentSite(request);
            CleanwiseUser clwUser = ShopTool.getCurrentUser(request);
            UserData user         = clwUser.getUser();

            if(!ShopTool.hasInventoryCartAccessOpen(request)) {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable",null);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
            }
            shoppingServEjb.saveAutoDistroInvShopHistory(user, site.getSiteId(), site.getCurrentInventoryPeriod(), (ShoppingCartItemDataVector)cartItems, SessionTool.getCategoryToCostCenterView(session, site.getSiteId())) ;
            shoppingServEjb.updateInventoryParValues(user.getUserName(), site.getSiteId(), site.getCurrentInventoryPeriod(), (ShoppingCartItemDataVector)cartItems);

            ae = inventoryInit(request, pForm);
/*
            ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.INVENTORY_SHOPPING_CART);

            shoppingCart.addWarningMessages(currentCartOG.getWarningMessages());
            shoppingCart.addWarningMessages(currentCartIL.getWarningMessages());
            shoppingCart.addItemMessages(currentCartOG.getItemMessages());
            shoppingCart.addItemMessages(currentCartIL.getItemMessages());

            session.setAttribute(Constants.INVENTORY_SHOPPING_CART,shoppingCart);
*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ae;
    }


    private static ActionMessages verifyJcpInvShoppingCart(HttpServletRequest request, ShoppingCartForm pForm) {

        ActionMessages am = new ActionMessages();
        ShoppingCartItemDataVector cartItems = pForm.getCartItems();
        if(cartItems==null) {
            return am;
        }
        int ind = -1;
        int inventoryCount = 0;
        int noOnHandCount = 0;
        IdVector noOnHandSkuAL = new IdVector();

        for(Iterator iter=cartItems.iterator(); iter.hasNext();) {
            ind++;
            ShoppingCartItemData cartItem = (ShoppingCartItemData) iter.next();
            int qty = 0;
            String qtyStr = Utility.strNN(cartItem.getQuantityString());
            if (qtyStr.trim().length() > 0) {
                try {
                    qty = Integer.parseInt(qtyStr);
                    if(qty<0) {
                        Object[] param = new Object[2];
                        param[0] = cartItem.getActualSkuNum();
                        param[1] = qtyStr;
                        String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.skuQuantityIsNegative", param);
                        am.add("errorQ" + ind, new ActionError("error.simpleGenericError", errorMess));
                    } else {
                        cartItem.setQuantity(qty);
                    }
                } catch (NumberFormatException exc) {
                    Object[] param = new Object[2];
                    param[0] = cartItem.getActualSkuNum();
                    param[1] = qtyStr;
                    String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.skuWrondQuantityNumberFormat", param);
                    am.add("errorQ" + ind, new ActionError("error.simpleGenericError", errorMess));
                }
            }

            if (cartItem.getIsaInventoryItem()) {
                inventoryCount++;
                int qtyOnHand = 0;
                String qtyOnHandStr = Utility.strNN(cartItem.getInventoryQtyOnHandString());
                if (qtyOnHandStr.trim().length() > 0) {
                    try {
                        qtyOnHand = Integer.parseInt(qtyOnHandStr);
                        if(qtyOnHand<0) {
                            Object[] param = new Object[2];
                            param[0] = cartItem.getActualSkuNum();
                            param[1] = qtyOnHandStr;
                            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.onHandQuantityIsNegative", param);
                            am.add("errorH" + ind, new ActionError("error.simpleGenericError", errorMess));
                        } else {

                        if(qtyOnHand != cartItem.getInventoryQtyOnHand() &&
                           qtyStr.trim().length()>0 ) {
                            int qq = cartItem.getInventoryParValue()-qtyOnHand;
                            if(qq<0) qq = 0;
                            if(qty!=qq) {
                                Object[] param = new Object[1];
                                param[0] = cartItem.getActualSkuNum();
                                String warningMess = ClwI18nUtil.getMessage(request, "shop.warnings.onHandQtyChangedModifyOrderQty", param);
                                am.add("warningH", new ActionMessage("warning.genericWarning", warningMess));

                            }
                        }
                        cartItem.setInventoryQtyOnHand(qtyOnHand);
                        }

                    } catch (NumberFormatException exc) {
                        Object[] param = new Object[2];
                        param[0] = cartItem.getActualSkuNum();
                        param[1] = qtyOnHandStr;
                        String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.skuWrondQuantityNumberFormat", param);
                        am.add("errorH" + ind, new ActionError("error.simpleGenericError", errorMess));
                    }
                } else {
                    noOnHandCount++;
                    noOnHandSkuAL.add(cartItem.getActualSkuNum());
                }
            }
        }
        if(noOnHandCount>0 && inventoryCount!=noOnHandCount) {
            Object[] param = new Object[1];
            param[0] = IdVector.toCommaString(noOnHandSkuAL);
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.onHandQuantityMissing",param);
            am.add("errorH", new ActionError("error.simpleGenericError", errorMess));
        }
        return am;
    }

      private static ActionErrors verifyInvShoppingCart(HttpServletRequest request, ShoppingCartForm pForm) {

        return verifyInvShoppingCart(request, pForm.getCartItems());
    }

    private static ActionErrors verifyInvShoppingCart(HttpServletRequest request, ShoppingCartItemDataVector cartItems) {

        ActionErrors ae = new ActionErrors();

        List<String> msgList = new ArrayList<String>();
        if(cartItems==null) {
            return ae;
        }
        int ind = -1;
        for(Iterator iter=cartItems.iterator(); iter.hasNext();) {
            ind++;
            ShoppingCartItemData cartItem = (ShoppingCartItemData) iter.next();
            int qty = 0;
            String qtyStr = Utility.strNN(cartItem.getQuantityString());
            if (qtyStr.trim().length() > 0) {
                try {
                    qty = Integer.parseInt(qtyStr);
                    if(qty<0) {
                        /*Object[] param = new Object[2];
                        param[0] = cartItem.getActualSkuNum();
                        param[1] = qtyStr;
                        String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.skuQuantityIsNegative", param);
                        ae.add("errorQ" + ind, new ActionError("error.simpleGenericError", errorMess));*/
                    	msgList.add(cartItem.getActualSkuNum());
                    }
                    cartItem.setQuantity(qty);
                } catch (NumberFormatException exc) {
                    /*Object[] param = new Object[2];
                    param[0] = cartItem.getActualSkuNum();
                    param[1] = qtyStr;
                    String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.skuWrondQuantityNumberFormat", param);
                    ae.add("errorQ" + ind, new ActionError("error.simpleGenericError", errorMess));*/
                	msgList.add(cartItem.getActualSkuNum());
                }
            }

            if (cartItem.getIsaInventoryItem()) {
                int qtyOnHand = 0;
                String qtyOnHandStr = Utility.strNN(cartItem.getInventoryQtyOnHandString());
                if (qtyOnHandStr.trim().length() > 0) {
                    try {
                        qtyOnHand = Integer.parseInt(qtyOnHandStr);
                        if(qtyOnHand<0) {
                            Object[] param = new Object[2];
                            param[0] = cartItem.getActualSkuNum();
                            param[1] = qtyOnHandStr;
                            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.onHandQuantityIsNegative", param);
                            ae.add("errorH" + ind, new ActionError("error.simpleGenericError", errorMess));
                        }
                        cartItem.setInventoryQtyOnHand(qtyOnHand);

                    } catch (NumberFormatException exc) {
                        /*Object[] param = new Object[2];
                        param[0] = cartItem.getActualSkuNum();
                        param[1] = qtyOnHandStr;
                        String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.skuWrondQuantityNumberFormat", param);
                        ae.add("errorH" + ind, new ActionError("error.simpleGenericError", errorMess));*/
                    	msgList.add(cartItem.getActualSkuNum());
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
        return ae;
    }

//    public static ActionErrors recalInvCartQty(HttpServletRequest request, ShoppingCartForm pForm) {
    public static ActionErrors recalInvCartQty(HttpServletRequest request, List  cartItems) {

        ActionErrors ae = new ActionErrors();
//        List cartItems = pForm.getCartItems();

        for (int i = 0; cartItems != null && i < cartItems.size(); i++) {
            ShoppingCartItemData cartItem = (ShoppingCartItemData) cartItems.get(i);

            int qty = 0;
            String qtyStr = Utility.strNN(cartItem.getQuantityString());

            if (qtyStr.trim().length() > 0) {

                try {
                    qty = Integer.parseInt(qtyStr);
                    cartItem.setQuantity(qty);

                } catch (NumberFormatException exc) {
                    Object[] param = new Object[2];
                    param[0] = cartItem.getActualSkuNum();
                    param[1] = qtyStr;
                    String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.skuWrondQuantityNumberFormat", param);
                    ae.add("error" + i, new ActionError("error.simpleGenericError", errorMess));
                }
            }

            if (cartItem.getIsaInventoryItem()) {
                int qtyOnHand = 0;
                String qtyOnHandStr = Utility.strNN(cartItem.getInventoryQtyOnHandString());
                if (qtyOnHandStr.trim().length() > 0) {

                    try {
                        qtyOnHand = Integer.parseInt(qtyOnHandStr);
                        if(qtyOnHand<0) {
                            Object[] param = new Object[2];
                            param[0] = cartItem.getActualSkuNum();
                            param[1] = qtyOnHandStr;
                            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.onHandQuantityIsNegative", param);
                            ae.add("error" + i, new ActionError("error.simpleGenericError", errorMess));
                        }
                        cartItem.setInventoryQtyOnHand(qtyOnHand);


                        int recQty = cartItem.getInventoryParValue() - cartItem.getInventoryQtyOnHand();
                        if (recQty < 0) recQty = 0;
                        cartItem.setQuantity(recQty);
                        cartItem.setQuantityString(String.valueOf(recQty));

                    } catch (NumberFormatException exc) {
                        Object[] param = new Object[2];
                        param[0] = cartItem.getActualSkuNum();
                        param[1] = qtyOnHandStr;
                        String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.skuWrondQuantityNumberFormat", param);
                        ae.add("error" + i, new ActionError("error.simpleGenericError", errorMess));
                    }
                }

            }
        }
         return ae;
    }
    public static ActionErrors recalInvCartQty(HttpServletRequest request, ShoppingCartForm pForm) {
        ActionErrors ae = new ActionErrors();
        List cartItems = pForm.getCartItems();
        return recalInvCartQty( request,  cartItems);
    }	
  //****************************************************************************
  public static ActionErrors sort(HttpServletRequest request, ShoppingCartForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    ShoppingCartData shoppingCartD = pForm.getShoppingCart();

    int orderBy = pForm.getOrderBy();

    switch(orderBy) {
        case Constants.ORDER_BY_CATEGORY:
            shoppingCartD.orderByCategory();
            break;
        case Constants.ORDER_BY_CUST_SKU:
            shoppingCartD.orderBySku();
            break;
        case Constants.ORDER_BY_NAME:
            shoppingCartD.orderByName();
            break;
        default:
            orderBy = Constants.ORDER_BY_CATEGORY;
            shoppingCartD.orderByCategory();
    }


          //ae = init(request, pForm);
    shoppingCartD.setOrderBy(orderBy);
    return ae;
  }
  //*****************************************************************************
  public static ActionErrors removeSelected(HttpServletRequest request, ShoppingCartForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    if(ae.size()>0) {
      return ae;
    }

    /// Checking of the current cart on consolidation.
    /// The cart with consolidated items should not be changed.
    if (ShopTool.isShoppingCartConsolidated(request)) {
        String errorMsg = ClwI18nUtil.getMessage(request, "shop.errors.changeItemsInConsolidatedCart", null);
        ae.add("error", new ActionError("error.simpleGenericError", errorMsg));
        return ae;
    }

    HttpSession session = request.getSession();
    long[] toDelete = pForm.getSelectBox();
    if(toDelete == null || (toDelete.length == 0) ){
        String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noItemsForDeletion",null);
              ae.add("error", new ActionError("error.simpleGenericError",errorMess));
        return ae;
    }

    ShoppingCartData shoppingCartD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
    ShoppingCartItemDataVector cartItems = shoppingCartD.getItems();
    List removedItems = new ArrayList();
    for(int ii=0; ii<cartItems.size();) {
      ShoppingCartItemData sciD = (ShoppingCartItemData)cartItems.get(ii);
      int orderNum = sciD.getOrderNumber();
      int id = sciD.getProduct().getProductId();
      long code = (long)id*10000+(long)orderNum;
      int jj = 0;
      for(; jj<toDelete.length; jj++) {
        if(code==toDelete[jj]) {
          cartItems.remove(ii);
          removedItems.add(sciD);
          break;
        }
      }
      if(jj==toDelete.length) ii++;
    }
    pForm.setRemovedItems(removedItems);
    shoppingCartD.setItems(cartItems);
    ae = saveShoppingCart(request);

    if(ae.size()>0) {
      return ae;
    } else {
      if (cartItems.size()>0){

          pForm.setConfirmMessage(ClwI18nUtil.getMessage(request,
                    "shoppingCart.text.actionMessage.itemsDeletedFromCart", null));
          //This form object is probably called more than once here. So we have explicitly
          //set the message as a request attribute to avoid being overwritten.
          request.setAttribute(Constants.XPEDX_CONFIRM_MSG,pForm.getConfirmMessage());
      }
    }
    pForm.setSelectBox(null);

//    ae = init(request,pForm);
    return ae;
  }
  //*****************************************************************************
  public static ActionErrors removeAll(HttpServletRequest request, ShoppingCartForm pForm)
  {
    ActionErrors ae = new ActionErrors();

    if(ae.size()>0) {
      return ae;
    }

    /// Checking of the current cart on consolidation.
    /// The cart with consolidated items should not be changed.
    if (ShopTool.isShoppingCartConsolidated(request)) {
        String errorMsg = ClwI18nUtil.getMessage(request, "shop.errors.changeItemsInConsolidatedCart", null);
        ae.add("error", new ActionError("error.simpleGenericError", errorMsg));
        return ae;
    }

    HttpSession session = request.getSession();
    ShoppingCartData shoppingCartD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
    List cartItems = shoppingCartD.getItems();
    pForm.setRemovedItems(cartItems);
    shoppingCartD.setItems(new ShoppingCartItemDataVector());
    ae = saveShoppingCart(request);
    pForm.setShoppingCart(shoppingCartD);
    if(ae.size()>0) {
      return ae;
    }

    //ae = init(request,pForm);
    return ae;
  }
  //*****************************************************************************
  public static ActionErrors merge(HttpServletRequest request, ShoppingCartForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    if(ae.size()>0) {
      return ae;
    }
    HttpSession session = request.getSession();
    //long[] toDelete = pForm.getSelectBox();

    ShoppingCartData shoppingCartD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
    ShoppingCartItemDataVector cartItems = shoppingCartD.getItems();
    List removedItems = new ArrayList();
    for(int ii=0; ii<cartItems.size()-1;ii++) {
      ShoppingCartItemData sciD = (ShoppingCartItemData)cartItems.get(ii);
      int id = sciD.getProduct().getProductId();
      int qty=sciD.getQuantity();
      boolean mergeFlag = false;
      for(int jj=ii+1; jj<cartItems.size();) {
        ShoppingCartItemData sciD1 = (ShoppingCartItemData)cartItems.get(jj);
        if(id==sciD1.getProduct().getProductId()) {
          qty+=sciD1.getQuantity();
          mergeFlag = true;
          cartItems.remove(jj);
          continue;
        }
        else {
          jj++;
        }
      }
      if(mergeFlag) sciD.setQuantity(qty);
    }
    shoppingCartD.setItems(cartItems);
    ae = saveShoppingCart(request);
    if(ae.size()>0) {
      return ae;
    }
//    ae = init(request,pForm);
    return ae;
  }
  //*****************************************************************************
  public static ActionErrors restoreQuant(HttpServletRequest request, ShoppingCartForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    if(ae.size()>0) {
      return ae;
    }
//    ae = init(request,pForm);
    return ae;
  }
  //*****************************************************************************
  public static ActionErrors undoRemove(HttpServletRequest request, ShoppingCartForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    if(ae.size()>0) {
      return ae;
    }
    HttpSession session = request.getSession();
    //long[] toDelete = pForm.getSelectBox();

    ShoppingCartData shoppingCartD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
    shoppingCartD.addItems(pForm.getRemovedItems());
    pForm.setRemovedItems(new ArrayList());
    ae = saveShoppingCart(request);
    if(ae.size()>0) {
      return ae;
    }
//    ae = init(request,pForm);
    return ae;
  }

    private static ActionErrors saveShoppingCart(HttpServletRequest request) {
        return saveShoppingCart( null, request, null, null);
    }

    public static ActionErrors saveShoppingCart(HttpSession pSession)    {
        return saveShoppingCart( null, pSession, null, null);
    }

    public static ActionErrors saveShoppingCart
        (
         SiteData pSiteData,
         HttpSession pSession,
         ProcessOrderResultData pOrderResult
         )  {
  return saveShoppingCart( null, pSession,
         pOrderResult, null);
    }

    public static ActionErrors saveShoppingCart(SiteData pSiteData, HttpServletRequest request,
        ProcessOrderResultData pOrderResult, List pScartItemPurchased ) {
        ActionErrors ae;
        HttpSession session = request.getSession();
        ae = saveShoppingCart(pSiteData, session, pOrderResult, pScartItemPurchased);
        return ae;
    }

    public static ActionErrors saveShoppingCart
    (
     SiteData pSiteData,
     HttpSession pSession,
     ProcessOrderResultData pOrderResult,
     List pScartItemPurchased)  {
    	return saveShoppingCart(pSiteData, pSession, pOrderResult, pScartItemPurchased, null);
    }
    
    public static ActionErrors saveShoppingCart(HttpSession pSession, ShoppingCartItemDataVector updatedItems)  {
    	return saveShoppingCart(null, pSession, null, null, updatedItems);
    	
    }
    public static ActionErrors saveShoppingCart
        (
         SiteData pSiteData,
         HttpSession pSession,
         ProcessOrderResultData pOrderResult,
         List pScartItemPurchased,
         ShoppingCartItemDataVector updatedItems)  {

        ActionErrors ae = new ActionErrors();
        APIAccess factory = (APIAccess)  pSession.getAttribute(Constants.APIACCESS);
        if(factory==null) {
            ae.add("error",new ActionError("error.systemError",
             "No system access 33"));
            return ae;
        }

        Order orderEjb = null;

        try {
        	orderEjb = factory.getOrderAPI();
        } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error",new ActionError("error.systemError","No Order Ejb pointer"));
            return ae;
        }

        ShoppingServices shoppingServEjb = null;

        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error",new ActionError("error.systemError","No shopping services Ejb pointer"));
            return ae;
        }

        ShoppingCartData cartD = (ShoppingCartData) pSession.getAttribute(Constants.SHOPPING_CART);
        if(cartD==null) {
            ae.add("error",new ActionError("error.systemError","No shopping cart session object found"));
            return ae;
        }
        Integer catalogIdI = (Integer) pSession.getAttribute(Constants.CATALOG_ID);
        if(catalogIdI==null) {
            ae.add("error",new ActionError("error.systemError","No CatalogId session object found"));
            return ae;
        }
        CleanwiseUser appUser = (CleanwiseUser) pSession.getAttribute(Constants.APP_USER);
        if(appUser==null) {
            ae.add("error",new ActionError("error.systemError","No ApplicationUser  session object found"));
            return ae;
        }

        if (ShopTool.isModernInventoryShopping(pSession)
              && !ShopTool.hasDiscretionaryCartAccessOpen(pSession)) {
          //String errorMess = ClwI18nUtil.getMessage(pSession, "shop.errors.discretionaryCartUnavailable", null);
          ae.add("error", new ActionError("error.simpleGenericError", "Discretionary cart is unavailable. Schedule cart period started"));
          return ae;
       }

       try {
            if ( null == pSiteData ) {
                pSiteData = appUser.getSite();
            }
            cartD.setSite(pSiteData);

      // Check cart quantities against the shopping control
      if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.OVERRIDE_SHOPPING_RESTRICTION)) {
    	  //cartD = cartD.reviseQuantities(); //<- old statement for Shopping Control Check
          int siteId = pSiteData.getSiteId();
          log.info("saveShoppingCart => siteId = " + siteId);
          cartD = reviseShoppingCartQuantities(cartD, orderEjb, siteId, updatedItems); // new statement for Shopping Control Check
      } 
      
      log.info("call to ShopTool.canSaveShoppingCart");
      boolean persistentFl = cartD.getIsPersistent();

      if(persistentFl) {
        persistentFl = ShopTool.canSaveShoppingCart(pSession);
      }
      cartD.setIsPersistent(persistentFl);

      ShoppingCartData scd =shoppingServEjb.saveShoppingCart
                (cartD,
                 catalogIdI.intValue(),
                 appUser.getUser().getUserName(),
                 pOrderResult,
                 pScartItemPurchased,
                 SessionTool.getCategoryToCostCenterView(pSession, cartD.getSite().getSiteId(), catalogIdI.intValue())
                 );

        ///

        if(scd!=cartD) {
           scd.addWarningMessages(cartD.getWarningMessages());
           scd.setItemMessages(cartD.getItemMessages());
        }
            ShopTool.setCurrentShoppingCart(pSession,scd);

            if ( pSiteData != null&& !pSiteData.hasModernInventoryShopping() ) {
                pSiteData.resetSiteInventory();
            }

 //       }catch(RemoteException exc) {
        }catch(Exception exc) {
            exc.printStackTrace();
            ae.add("error",new
                   ActionError("error.systemError",
                               "Can't save shopping cart data"));
            return ae;
        }
        return ae;
    }

    //****************************************************************************
  public static ActionErrors saveOrderGuide(HttpServletRequest request, ShoppingCartForm pForm)
  throws Exception
  {
    ActionErrors ae = new ActionErrors();
    pForm.setSelectedShoppingListId("-1"); // reset to initial value

    //Get user order guides
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

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if(appUser==null) {
      ae.add("error",new ActionError("error.systemError","No "+Constants.APP_USER+"session object found"));
      return ae;
    }
    UserData user = appUser.getUser();
    int siteId = appUser.getSite().getBusEntity().getBusEntityId();
    String roleCd = user.getUserRoleCd();
    boolean contractOnly = (roleCd.indexOf(Constants.UserRole.CONTRACT_ITEMS_ONLY)>=0)?true:false;

    Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);
    if(catalogIdI==null) {
      ae.add("error",new ActionError("error.systemError","No "+Constants.CATALOG_ID+ " session object found"));
      return ae;
    }
    int catalogId = catalogIdI.intValue();
    OrderGuideDataVector userOrderGuideDV = null;
    try {
      userOrderGuideDV = shoppingServEjb.getUserOrderGuides(user.getUserId(),catalogId,siteId);
    } catch(RemoteException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","Can't pick up order guides"));
      return ae;
    }

    //Check new order guide name
    String name = pForm.getUserOrderGuideName();
    if(name==null || name.trim().length()==0) {
      String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.provideOrderGuideName",null);
      ae.add("error",new ActionError("error.simpleGenericError",errorMess));
      return ae;
    }
    name = name.trim();
    name = I18nUtil.getUtf8Str(name);
    pForm.setUserOrderGuideName(name);
    //Check uniqueness of the name
    if(userOrderGuideDV!=null) {
      int ii=0;
      for(;ii<userOrderGuideDV.size();ii++) {
        OrderGuideData ogD = (OrderGuideData) userOrderGuideDV.get(ii);
        String ss = ogD.getShortDesc();
        if(ss!=null && ss.trim().equals(name)) {
          break;
        }
      }
      if(ii<userOrderGuideDV.size()) {
        //String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.nameExists",null);
    	  Object[] params = new Object[1];
          params[0] = name;
          String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.enterNewName", params);

          ae.add("error",new ActionError("error.simpleGenericError",errorMess));
          return ae;
      }
    }
    //Criate new order guide;
    OrderGuideData orderGuideD = OrderGuideData.createValue();
    orderGuideD.setShortDesc(name);
    orderGuideD.setCatalogId(catalogIdI.intValue());
    orderGuideD.setBusEntityId(appUser.getSite().getBusEntity().getBusEntityId());
    orderGuideD.setUserId(appUser.getUser().getUserId());
    orderGuideD.setOrderGuideTypeCd
        (RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE);

       //Pickup items
    ae = recalc(request,pForm);
    if(ae.size()>0) {
      return ae;
    }

    ValidateActionMessage am = ShopTool.validateSpecialPermission(request, pForm.getCartItems());
    if (am.hasErrors()) {
        return am.getActionErrors();
    }

    int orderGuideId = 0;
    try {
      orderGuideId = shoppingServEjb.saveUserOrderGuide
          (orderGuideD,pForm.getCartItems(),appUser.getUser().getUserName());
    } catch(Exception exc) {
      ae.add("error",new ActionError("error.systemError","Can't save order guide to database"));
      return ae;
    }
    pForm.setOrderGuideId(orderGuideId);
    if(ae.size()==0) {
      Object[] params = new Object[1];
      params[0] = name;
      pForm.setConfirmMessage(ClwI18nUtil.getMessage(request,
                "shoppingCart.text.actionMessage.listSaved", params));
      //This form object is probably called more than once here. So we have explicitly
      //set the message as a request attribute to avoid being overwritten.
      request.setAttribute(Constants.XPEDX_CONFIRM_MSG,pForm.getConfirmMessage());
   }

    return ae;
  }
  //****************************************************************************
public static ActionErrors updateOrderGuide(HttpServletRequest request, ShoppingCartForm pForm)
throws Exception
{
  ActionErrors ae = new ActionErrors();
  int selectedId = Integer.parseInt(pForm.getSelectedShoppingListId());
  pForm.setSelectedShoppingListId("-1"); // set to initial value

  //Get user order guides
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

  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
  if(appUser==null) {
    ae.add("error",new ActionError("error.systemError","No "+Constants.APP_USER+"session object found"));
    return ae;
  }

  UserData user = appUser.getUser();
  int siteId = appUser.getSite().getBusEntity().getBusEntityId();
  String roleCd = user.getUserRoleCd();
  boolean contractOnly = (roleCd.indexOf(Constants.UserRole.CONTRACT_ITEMS_ONLY)>=0)?true:false;

  Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);
  if(catalogIdI==null) {
    ae.add("error",new ActionError("error.systemError","No "+Constants.CATALOG_ID+ " session object found"));
    return ae;
  }
  int catalogId = catalogIdI.intValue();

  Integer contractIdI = (Integer) session.getAttribute(Constants.CONTRACT_ID);
  int contractId = 0;
  if (contractIdI != null) {
    contractId = contractIdI.intValue();
  }

  OrderGuideDataVector userOrderGuideDV = null;
  try {
    userOrderGuideDV = shoppingServEjb.getUserOrderGuides(user.getUserId(),catalogId,siteId);
  } catch(RemoteException exc) {
    exc.printStackTrace();
    ae.add("error",new ActionError("error.systemError","Can't pick up order guides"));
    return ae;
  }

  StoreData store = appUser.getUserStore();
  if (store == null) {
      ae.add("error",
              new ActionError("error.systemError",
              "No store information was loaded"));

      return ae;
  }

  PropertyData storeTypeProperty = store.getStoreType();
  if (storeTypeProperty == null) {
      ae.add("error",
              new ActionError("error.systemError",
              "No store type information was loaded"));

      return ae;
  }

  //Check  order guide id
  if(selectedId  < 0) {
    String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.provideOrderGuideName",null);
    ae.add("error",new ActionError("error.simpleGenericError",errorMess));
    return ae;
  }

  //Check uniqueness of the name
  String name = null ;
  if(userOrderGuideDV!=null) {
    int ii=0;
    for(;ii<userOrderGuideDV.size();ii++) {
      OrderGuideData ogD = (OrderGuideData) userOrderGuideDV.get(ii);
      int id = ogD.getOrderGuideId();
      if(id == selectedId ) {
        name = ogD.getShortDesc();
        break;
      }
    }
  }

  if (name== null || (name!=null && name.trim().length() == 0)) {
   String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.provideOrderGuideName", null);
   ae.add("error", new ActionError("error.simpleGenericError", errorMess));
   return ae;
 }

 // Get Shopping Items for current Order Guide
 ShoppingCartItemDataVector ogItems = null;
 if (selectedId > 0 &&
         (!appUser.getSite().hasInventoryShoppingOn()
                 ||(appUser.getSite().hasInventoryShoppingOn()
                 &&appUser.getSite().hasModernInventoryShopping()))) {
       try {

           ogItems = shoppingServEjb.getOrderGuideItems(storeTypeProperty.getValue(),
                   appUser.getSite(),
                   selectedId,
                   ShopTool.createShoppingItemRequest(request),
                   0,SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));

       } catch (RemoteException exc) {
           exc.printStackTrace();
           ae.add("error", new ActionError("error.systemError", exc.getMessage()));
           return ae;
       }
 }

  //Criate new order guide;
  OrderGuideData orderGuideD = OrderGuideData.createValue();
  orderGuideD.setShortDesc(name);
  orderGuideD.setOrderGuideId(selectedId);
  orderGuideD.setCatalogId(catalogIdI.intValue());
  orderGuideD.setBusEntityId(appUser.getSite().getBusEntity().getBusEntityId());
  orderGuideD.setUserId(appUser.getUser().getUserId());
  orderGuideD.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE);


     //Pickup items
  ae = recalc(request,pForm);
  if(ae.size()>0) {
    return ae;
  }
  //-----------
  List cartItems = pForm.getCartItems();
  List newCartItems =  ShopTool.addItemsToOgList(ogItems, cartItems);

  ValidateActionMessage am = ShopTool.validateSpecialPermission(request, newCartItems);
  if (am.hasErrors()) {
      return am.getActionErrors();
  }

  //-----------
  int orderGuideId = 0;
  try {
    orderGuideId = shoppingServEjb.saveUserOrderGuide(orderGuideD, newCartItems ,appUser.getUser().getUserName());
  } catch(Exception exc) {
    ae.add("error",new ActionError("error.systemError","Can't save order guide to database"));
    return ae;
  }

  pForm.setOrderGuideId(orderGuideId);
  if(ae.size()==0) {
    Object[] params = new Object[1];
    params[0] = name;
    pForm.setConfirmMessage(ClwI18nUtil.getMessage(request,
              "shoppingCart.text.actionMessage.listUpdated", params));
    //This form object is probably called more than once here. So we have explicitly
    //set the message as a request attribute to avoid being overwritten.
    request.setAttribute(Constants.XPEDX_CONFIRM_MSG,pForm.getConfirmMessage());
 }

  return ae;
}

  /**
   *Sets throws OrderBudgetTypeCd property of the shopping cart data object in both the passed in @see ShoppingCartForm and the
   *@see ShoppingCartForm stord in the session so it may be used later on.
   */
  public static ActionErrors setOrderBudgetTypeCd(String typeCd, HttpServletRequest request, ShoppingCartForm pForm){
      HttpSession session = request.getSession();
      ActionErrors ae = new ActionErrors();
      ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
      if(shoppingCart == null){
        ae.add("error",new ActionError("error.systemError","No shopping cart a"));
        return ae;
      }
      if(pForm.getShoppingCart() == null){
        ae.add("error",new ActionError("error.systemError","No shopping cart b"));
        return ae;
      }

      shoppingCart.setOrderBudgetTypeCd(typeCd);
      pForm.getShoppingCart().setOrderBudgetTypeCd(typeCd);
      return saveShoppingCart(request);
  }

    public static void updateInvShoppingCart(HttpServletRequest request, ShoppingCartForm pForm, HttpServletResponse response) throws IOException {

        ActionErrors ae = new ActionErrors();

        response.setContentType("application/xml");
        response.setHeader("Cache-Control", "no-cache");

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            Element rootEl = updateInvSCartXmlResponse(ae);
            OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
            XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
            serializer.serialize(rootEl);
            return;
        }

        try {
            ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();

            Order orderEjb = factory.getOrderAPI(); // new statement for Shopping Control Check


            List cartItems = pForm.getCartItems();
            ShoppingCartItemDataVector invItems = new ShoppingCartItemDataVector();

            SiteData site = ShopTool.getCurrentSite(request);
            CleanwiseUser clwUser = ShopTool.getCurrentUser(request);
            UserData user = clwUser.getUser();
            StoreData store = clwUser.getUserStore();

            if (!ShopTool.hasInventoryCartAccessOpen(request)) {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable", null);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                Element rootEl = updateInvSCartXmlResponse(ae);
                OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
                XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
                serializer.serialize(rootEl);
                return;
            }

            if (cartItems != null && cartItems.size() > 0) {
                for (Object cartItem1 : cartItems) {
                    ShoppingCartItemData cartItem = (ShoppingCartItemData) cartItem1;
                    if (cartItem.getIsaInventoryItem()) {
                        invItems.add(cartItem);
                    }
                }
            }

            ae = verifyInvShoppingCart(request, invItems);
            if (ae.size() > 0) {
                OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
                XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
                serializer.serialize(updateInvSCartXmlResponse(ae));
                return;
            }

            ae = ShopTool.validateBudgetThreshold(request, (ShoppingCartItemDataVector) cartItems);
            if (ae.size() > 0) {
                OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
                XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
                serializer.serialize(updateInvSCartXmlResponse(ae));
                return;
            }

            ShoppingCartData currentCartIL = shoppingServEjb.getInventoryCartIL(store.getStoreType().getValue(), user, site);
            currentCartIL.setItems(invItems);

            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.OVERRIDE_SHOPPING_RESTRICTION)) {            	
                //currentCartIL.reviseQuantities(); // old statement for Shopping Control Check
                int siteId = site.getSiteId();
                log.info("updateInvShoppingCart() => siteId = " + siteId);
            	currentCartIL = reviseShoppingCartQuantities(currentCartIL, orderEjb, siteId); // new statement for Shopping Control Check
                if (currentCartIL.getItemMessages().size() > 0) {
                    log.info("WARNING currentCartIL");
                    currentCartIL.setWarningMessages(currentCartIL.getItemMessages());
                }
            }
            boolean isUsedPhysicalInventoryAlgorithm = ShopTool.isUsedPhysicalInventoryAlgorithm(request);
            boolean isPhysicalCartAvailable = ShopTool.isPhysicalCartAvailable(request);
            boolean isPhysCart = isUsedPhysicalInventoryAlgorithm && isPhysicalCartAvailable;
            
            shoppingServEjb.saveInventoryCartIL(currentCartIL, isPhysCart, 
                SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));

            ShoppingCartData shoppingCartD = shoppingServEjb.getInvShoppingCart(
                    appUser.getUserStore().getStoreType().getValue(),
                    appUser.getUser(), appUser.getSite(),false ,
                    SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));

            shoppingCartD.addWarningMessages(currentCartIL.getWarningMessages());
            shoppingCartD.addItemMessages(currentCartIL.getItemMessages());

            session.setAttribute(Constants.INVENTORY_SHOPPING_CART, shoppingCartD);

            for (int m = 0; m < shoppingCartD.getItemMessages().size(); m++) {

                ShoppingCartData.CartItemInfo cii =
                        (ShoppingCartData.CartItemInfo) shoppingCartD.getItemMessages().get(m);
                //String skuNum = cii.mCartItemData.getActualSkuNum();
                ArrayList al = cii.getI18nItemMessageAL();
                String key = (String) al.get(0);

                Object[] params = null;
                if (al.size() > 1) {
                    params = new Object[6];
                    for (int ii = 0; ii < al.size() - 1; ii++) {
                        params[ii] = (Object) al.get(ii + 1);
                    }
                }

                String errorMess = ClwI18nUtil.getMessage(request, key, params);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //response.getWriter().write(updateInvSCartXmlResponse(ae));
        Element rootEl = updateInvSCartXmlResponse(ae);
        OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
        XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
        serializer.serialize(rootEl);


    }


    public static void updateInvShoppingCartLight(HttpServletRequest request, ShoppingCartForm pForm,
                    HttpServletResponse response) throws IOException {
        ActionErrors ae = new ActionErrors();

        response.setContentType("application/xml");
        response.setHeader("Cache-Control", "no-cache");

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
            XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
            serializer.serialize(updateInvSCartXmlResponse(ae));
            return;
        }

        try {
            ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();

            Order orderEjb = factory.getOrderAPI(); // new statement for Shopping Control Check

            List cartItems = pForm.getCartItems();
            ShoppingCartItemDataVector invItems = new ShoppingCartItemDataVector();

            SiteData site = ShopTool.getCurrentSite(request);
            CleanwiseUser clwUser = ShopTool.getCurrentUser(request);
            UserData user = clwUser.getUser();
            StoreData store = clwUser.getUserStore();

            if (!ShopTool.hasInventoryCartAccessOpen(request)) {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable", null);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
                XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
                serializer.serialize(updateInvSCartXmlResponse(ae));
                return;
            }

            if (cartItems != null && cartItems.size() > 0) {
                for (Object cartItem1 : cartItems) {
                    ShoppingCartItemData cartItem = (ShoppingCartItemData) cartItem1;
                    if (cartItem.getIsaInventoryItem()) {
                        invItems.add(cartItem);
                    }
                }
            }

            ae = verifyInvShoppingCart(request, invItems);
            if (ae.size() > 0) {
                OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
                XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
                serializer.serialize(updateInvSCartXmlResponse(ae));
                return;
            }

            ae = ShopTool.validateBudgetThreshold(request, (ShoppingCartItemDataVector) cartItems);
            if (ae.size() > 0) {
                OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
                XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
                serializer.serialize(updateInvSCartXmlResponse(ae));
                return;
            }

            ShoppingCartData currentCartIL = shoppingServEjb.getInventoryCartIL(store.getStoreType().getValue(), user, site);
            currentCartIL.setItems(invItems);

            //STJ-4081 - make sure the cart has not been updated by another user in between
            //the time this user started the update and the time they ended it.
            ShoppingCartData currentCartOG = shoppingServEjb.getInventoryCartOG(user, site, store.getStoreType().getValue(),SessionTool.getCategoryToCostCenterView(session, site.getSiteId()));
            ShoppingInfoData mostRecentDBUpdate = getMostRecentCartHistoryRecord(currentCartIL, 
            		currentCartOG, ShoppingCartData.CART_ITEM_UPDATE);
            //return an error if the cart was updated.
            if (mostRecentDBUpdate != null) {
                ShoppingInfoData mostRecentFormUpdate = getMostRecentCartHistoryRecord(pForm.getFormCartIL(), 
                		pForm.getFormCartOG(), ShoppingCartData.CART_ITEM_UPDATE);
                if (mostRecentFormUpdate == null ||
                		mostRecentFormUpdate.getModDate().compareTo(mostRecentDBUpdate.getModDate()) < 0) {
                        // Error message
                    String username = mostRecentDBUpdate.getModBy();
                    String[] args = new String[]{username};
                    String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.multipleUserCollision",args);
                    ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                    OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
                    XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
                    serializer.serialize(updateInvSCartXmlResponse(ae));
                    return;
                }
            }

          CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.OVERRIDE_SHOPPING_RESTRICTION)) {
                //currentCartIL.reviseQuantities(); // old statement for Shopping Control Check
                int siteId = site.getSiteId();
                log.info("updateInvShoppingCart() => siteId = " + siteId);
            	currentCartIL = reviseShoppingCartQuantities(currentCartIL, orderEjb, siteId); // new statement for Shopping Control Check
                if (currentCartIL.getItemMessages().size() > 0) {
                    log.info("WARNING currentCartIL");
                    currentCartIL.setWarningMessages(currentCartIL.getItemMessages());
                }
            }

            boolean forceSaveAllHistory = false;


            shoppingServEjb.saveInventoryCartIL(currentCartIL, forceSaveAllHistory,
                SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));

            session.setAttribute(Constants.INVENTORY_SHOPPING_CART, currentCartIL);

            //STJ-4081 - update the form to reflect the fact that a change was made to the IL cart.  Otherwise, 
            //if updateInvShoppingCart() is called it will falsely return an error because it will find this 
            //change to the database but not find that same change reflected on the form, and therefore think the 
            //cart was updated between the time this user started their change and the time they completed it.
            ShoppingCartData shoppingCartIL = shoppingServEjb.getInventoryCartIL(appUser.getUserStore().getStoreType().getValue(),appUser.getUser(),appUser.getSite(), SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
            pForm.setFormCartIL(shoppingCartIL);

            for (int m = 0; m < currentCartIL.getItemMessages().size(); m++) {

                ShoppingCartData.CartItemInfo cii =
                        (ShoppingCartData.CartItemInfo) currentCartIL.getItemMessages().get(m);
                ArrayList al = cii.getI18nItemMessageAL();
                String key = (String) al.get(0);

                Object[] params = null;
                if (al.size() > 1) {
                    params = new Object[6];
                    for (int ii = 0; ii < al.size() - 1; ii++) {
                        params[ii] = (Object) al.get(ii + 1);
                    }
                }

                String errorMess = ClwI18nUtil.getMessage(request, key, params);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Element rootEl = updateInvSCartXmlResponse(ae);
        OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
        XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
        serializer.serialize(rootEl);

    }

    private static Element updateInvSCartXmlResponse(ActionErrors ae) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;

        try {
        	docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {


        }
        Document doc = docBuilder.getDOMImplementation().createDocument("", "response", null);
        Element root = doc.getDocumentElement();

        Element body = doc.createElement("body");

        Element errors = doc.createElement("errors");
        Iterator it = ae.get();
        while (it.hasNext()) {

            ActionError oErr = (ActionError) it.next();

            Element error = doc.createElement("error");
            error.setAttribute("key", oErr.getKey());

            for (int j = 0; j < oErr.getValues().length; j++) {

                Object oVal = oErr.getValues()[j];

                Element errorstring = doc.createElement("errorstring");
                errorstring.appendChild(doc.createTextNode(oVal.toString()));

                error.appendChild(errorstring);
            }
            errors.appendChild(error);
        }

        body.appendChild(errors);
        root.appendChild(body);

        return root;

    }
    
    public static ShoppingCartData reviseShoppingCartQuantities(ShoppingCartData cartData, Order orderEjb, int pSiteId) {  // new method for Shopping Cart Control Checks
    	return reviseShoppingCartQuantities(cartData, orderEjb, pSiteId, null);
    }
	/********************************************************************************************************/
    /*** New method for Shopping Control Check                                                            ***/
	/*** find order quantity per Ordered Item, located in the Shopping Cart, for the Orders,              ***/
	/*** placed from a specific Site                                                                      ***/
	/********************************************************************************************************/
    public static ShoppingCartData reviseShoppingCartQuantities(ShoppingCartData cartData, Order orderEjb, int pSiteId, ShoppingCartItemDataVector updatedItems) {  // new method for Shopping Cart Control Checks
    	
        ActionErrors ae = new ActionErrors();

        ShoppingCartItemDataVector scItemDataVector = cartData.getItems();
        
        //STJ-5681 - refresh the shopping controls to make sure the most recent limits are used
        SiteData location = cartData.getSite();
        int accountId = location.getAccountId();
        if (accountId > 0) {
        	try {
        		Site siteBean = new APIAccess().getSiteAPI();
        		location.setShoppingControls(siteBean.getShoppingControls(accountId, pSiteId));
        	}
        	catch (Exception e) {
        		throw new RuntimeException(e);
        	}
        }

        java.util.Hashtable ctrlv = cartData.getSite().getShoppingControlsMap();

        if (null == ctrlv || ctrlv.size() <= 0) {

        } else {
        	IdVector updatedItemIds = new IdVector();
        	if (updatedItems != null){
        		for (int i = 0; i < updatedItems.size(); i++){
        			updatedItemIds.add(new Integer(((ShoppingCartItemData)updatedItems.get(i)).getItemId()));
        		}
        	}
      	    for (int j = 0; j < scItemDataVector.size(); j++) {
      	        ShoppingCartItemData sciD = cartData.getItem(j);
      	        Integer k = new Integer(sciD.getItemId());
      	        if (updatedItems != null && !updatedItemIds.contains(k)){ // do not update the quantity if item is not in the updated list
      	        		continue;      	        		
      	        }

      	        ShoppingControlData ctrl = (ShoppingControlData)ctrlv.get(k);

      	        if(ctrl == null){
      	      	  continue;
      	        }
      	        
      	        //Check if this shopping control has APPLY action set.
      	        //If WORKFLOW action is set, the order will be hit the shopping control workflow
      	        if(Utility.isSet(ctrl.getActionCd()) && 
      	        		!ctrl.getActionCd().equalsIgnoreCase(RefCodeNames.SHOPPING_CONTROL_ACTION_CD.APPLY)){
      	        	continue;
      	        }
      	        
      	        int reqQty = sciD.getQuantity(); //requested item quantity to add to the Shopping cart
      	        if (reqQty == 0)
    	        	continue;
      	        int maxQtyAllowed = sciD.getMaxOrderQty(); // Maximum Order item quantity allowed in the Shopping cart

      	        int restrictDays = ctrl.getRestrictionDays();

      	        
      	        /*************************************/
      	        /*** SVC: New piece of code: Begin ***/
      	        /*************************************/
      	        int maxOrderQty = ctrl.getMaxOrderQty(); // Maximum Order item quantity from the clw_shopping_control DB table
      	        log.info("reviseShoppingCartQuantities()=> maxOrderQty = " + maxOrderQty);
      	        //Special cases:
      	        
      	        // case 1: 
      	        // maxOrderQty = UNLIMITED (= "-1" in the Database)
      	        if (maxOrderQty == -1) {
      	        	// with ANY value of the Restriction days (>0, =0, =-1 (unlimited), < -1(bad data in the DB):
      	            // no checks for Shopping Restrictions -> customer can put any number of items with this itemId in the Shopping cart
      	        	continue; 
      	        }
      	        // case 2: 
      	        // maxOrderQty <= -1 in the DB (bad data in the Database)
      	        // allow customer to put any number of items with this itemId in the Shopping cart
      	        if (maxOrderQty < -1) {
      	        	continue; // no checks for Shopping Restrictions -> customer can put any number of items in the Shopping cart
      	        }
      	        // case 3: 
      	        // maxOrderQty = 0
      	        if (maxOrderQty == 0) { 
      	        	  // with ANY number of the Restriction days (=0, >0, =-1(unlimited), < -1(bad data in the DB):
      	        	  // Apply Shopping Restrictions: block the item -> Remove it from the Shopping cart
        	      	  sciD.setQuantity(maxOrderQty);
          	      	  sciD.setQuantityString(String.valueOf(maxOrderQty));
          	      	  
      	        	  String m = "Max Order Quantity = 0.";  
      	        	  ShoppingCartData.CartItemInfo cii = cartData.new CartItemInfo(sciD, ctrl);             	      	
          	      	  String messKey = null;
          	      	  Object[] messParams = null;
          	      	  if (restrictDays == 1 || restrictDays == -1) { // restriction days = "unlimited"(= -1) or = 1
      	        	      messKey = "shoppingMessages.text.itemHasBeenBlocked_1"; // in this message the text is different for Xpedx store and other stores 
     	      		      messParams = new Object[5];
      	      		      messParams[0] = new Integer(reqQty);
      	      		      messParams[1] = new Integer(maxQtyAllowed);
      	      		      messParams[2] = new Integer(ctrl.getMaxOrderQty());
      	      		      messParams[3] = sciD.getActualSkuNum();
      	      		      messParams[4] = sciD.getProduct().getUom();
          	      	  } else {
      	        	      messKey = "shoppingMessages.text.itemHasBeenBlocked_2"; // in this message the text is different for Xpedx store and other stores 
      	          		  messParams = new Object[6];
      	          		  messParams[0] = new Integer(reqQty);
      	          		  messParams[1] = new Integer(maxQtyAllowed);
      	          		  messParams[2] = new Integer(ctrl.getMaxOrderQty());
      	          		  messParams[3] = new Integer(restrictDays);
      	          		  messParams[4] = sciD.getActualSkuNum();
      	          		  messParams[5] = sciD.getProduct().getUom();
      	        	  }
          	      	  cii.setMessage(m);
          	      	  cii.setI18nItemMessage(messKey, messParams);

          	      	  cartData.addItemMessage(cii); 
      	        	
          	          continue;
      	        }
      	        // case 4: 
      	        // n = maxOrderQty 
      	        // n > 0 AND (Restriction days = -1 (unlimited))      	        
      	        // Apply Shopping Restrictions: allow the customer to buy number of items <= n as many times per any day as he/she wants 
      	        // and it doesn't matter how many items of this kind had been already purchased
      	        if ((maxOrderQty > 0) && (restrictDays == -1)) {
      	           if (reqQty > maxOrderQty) {

        	      	  sciD.setQuantity(maxOrderQty);
          	      	  sciD.setQuantityString(String.valueOf(maxOrderQty));  
          	      	  
      	        	  String m = "";  
      	        	  ShoppingCartData.CartItemInfo cii = cartData.new CartItemInfo(sciD, ctrl);             	      	
          	      	  String messKey = null;
          	      	  Object[] messParams = null;
      	        		m += "Maximum order quantity exceeded. The requested quantity of "
        	      			  + reqQty + " has been adjusted to " + maxOrderQty + ".";
        	      		      messKey = "shoppingMessages.text.maxOrderQuantityExceeded";
        	      		      messParams = new Object[5];
        	      		      messParams[0] = new Integer(reqQty);
        	      		      messParams[1] = new Integer(maxOrderQty);
        	      		      messParams[2] = new Integer(ctrl.getMaxOrderQty());
        	      		      messParams[3] = sciD.getActualSkuNum();
        	      		      messParams[4] = sciD.getProduct().getUom();
                  	      	  cii.setMessage(m);
                  	      	  cii.setI18nItemMessage(messKey, messParams);
                  	      	  
                  	      	  cartData.addItemMessage(cii); 
      	           }
      	           
      	           continue;
      	        }
     	        // case 5: 
      	        // Number of Restriction days is < -1 (bad data in the database)
      	        // allow customer to put any number of items with this itemId in the Shopping cart
      	        if (restrictDays < -1) {
      	        	continue; // no checks for Shopping Restrictions -> customer can put any number of items in the Shopping cart
      	        }
      	        
      	        // case 6;
      	        // Number of Restriction days = 0 AND maxOrderQty (from DB) > 0
      	        // Behavior should be the same as when a STANDARD processing is applied 
      	        // AND Restriction days = 1 
      	        if (restrictDays == 0 && maxOrderQty > 0) 
      	        { 
      	        	restrictDays = 1;
      	        }
      	        
      	        /***********************************/
      	        /*** SVC: New piece of code: End ***/
      	        /***********************************/
      	        
      	        // qtyPerOrderItem is calculated based on the previously placed Orders: new method getOrderItemQuantity() is used
      	        int qtyPerOrderItem = 0;
      	        try {
      	          qtyPerOrderItem = orderEjb.getOrderItemQuantity(restrictDays, sciD.getItemId(), pSiteId);
      	        } catch (RemoteException exc) {
      	           exc.printStackTrace();
      	           ae.add("error",new ActionError("error.systemError","No Order Ejb pointer"));
      	           return cartData;
      	        }

      	        if(!(restrictDays < 0)){
      	      	  maxQtyAllowed = ctrl.getMaxOrderQty() - qtyPerOrderItem; //Max Order Quantity - Quantity per Order item from the placed Orders
      	        }

      	        // we can put in the Shopping Cart quantity of items <= maxQtyAllowed

      	        Map prevQuantityMap = cartData.getPrevItemsQuantityMap();
      	        if (maxQtyAllowed >= 0 && prevQuantityMap != null){
      	      	  Integer preQuantity = (Integer)prevQuantityMap.get(k);

        	          if (preQuantity != null)
      	      		  maxQtyAllowed += preQuantity.intValue(); // ????? I'm not sure that we have to ADD here...
      	      	      //
      	        }

      	        if (reqQty > maxQtyAllowed) { // max quantity is exceeded
      	        	if (maxQtyAllowed < 0) maxQtyAllowed = 0;
      	        	if (maxQtyAllowed <= 0){
    	        		sciD.setQuantityString("");
      	        	}else{
      	        		sciD.setQuantityString(String.valueOf(maxQtyAllowed));
      	        	}
      	        	sciD.setQuantity(maxQtyAllowed);
	        		
      	      	  String m = "";

      	      	  ShoppingCartData.CartItemInfo cii = cartData.new CartItemInfo(sciD, ctrl);

      	      	  String messKey = null;
      	      	  Object[] messParams = null;
      	      	  /***
      	      	  if (maxQtyAllowed > 0 && restrictDays >= 0) {  
      	      		 // DO NOT BLOCK the item !
      	      	     if(restrictDays != 1 && restrictDays != 0){  // per info from "Requirements" document:
      	      	                             // max quantity is exceeded and restriction days is set to anything other than 1 
      	      			  m += "Maximum order quantity exceeded. The requested quantity of "
      	          			  + reqQty + " has been adjusted to " + maxQtyAllowed + ". " +
      	          			  ctrl.getMaxOrderQty()+" orderable every "+restrictDays+" day(s).";
      	          		  messKey = "shoppingMessages.text.maxOrderQuantityExceededRestrictionDays";
      	          		  messParams = new Object[6];
      	          		  messParams[0] = new Integer(reqQty);
      	          		  messParams[1] = new Integer(maxQtyAllowed);
      	          		  messParams[2] = new Integer(ctrl.getMaxOrderQty());
      	          		  messParams[3] = new Integer(restrictDays);
      	          		  messParams[4] = sciD.getActualSkuNum();
      	          		  messParams[5] = sciD.getProduct().getUom();

      	      		  } else { // per info from "Requirements" document:
      	      			       // max quantity is exceeded and restriction days is set to 1 or 0
      	      		      m += "Maximum order quantity exceeded. The requested quantity of "
      	      			  + reqQty + " has been adjusted to " + maxQtyAllowed + ".";
      	      		      messKey = "shoppingMessages.text.maxOrderQuantityExceeded";
      	      		      messParams = new Object[5];
      	      		      messParams[0] = new Integer(reqQty);
      	      		      messParams[1] = new Integer(maxQtyAllowed);
      	      		      messParams[2] = new Integer(ctrl.getMaxOrderQty());
      	      		      messParams[3] = sciD.getActualSkuNum();
      	      		      messParams[4] = sciD.getProduct().getUom();
      	      		  }
      	      	  } else { // maxQtyAllowed <= 0
       	      		       // BLOCK the item !
      	                   // per info from "Requirements" document:
	      			       // max quantity is set to 0 (AND requested quantity > max quantity)
      	      		  // AND it worked this way in Production before all these code changes were implemented
      	      		  messKey = "shoppingMessages.text.itemHasBeenBlocked";
      	      	  }
      	      	  ***/
   	      		  if (maxQtyAllowed >= 0 && restrictDays >= 0) {  
       	      		 // DO NOT BLOCK the item !
       	      	     if(restrictDays != 1 && restrictDays != 0){  // per info from "Requirements" document:
       	      	                             // max quantity is exceeded and restriction days is set to anything other than 1 
       	      			  m += "Maximum order quantity exceeded. The requested quantity of "
       	          			  + reqQty + " has been adjusted to " + maxQtyAllowed + ". " +
       	          			  ctrl.getMaxOrderQty()+" orderable every "+restrictDays+" day(s).";
       	          		  messKey = "shoppingMessages.text.maxOrderQuantityExceededRestrictionDays";
       	          		  messParams = new Object[6];
       	          		  messParams[0] = new Integer(reqQty);
       	          		  messParams[1] = new Integer(maxQtyAllowed);
       	          		  messParams[2] = new Integer(ctrl.getMaxOrderQty());
       	          		  messParams[3] = new Integer(restrictDays);
       	          		  messParams[4] = sciD.getActualSkuNum();
       	          		  messParams[5] = sciD.getProduct().getUom();

       	      		  } else { // per info from "Requirements" document:
       	      			       // max quantity is exceeded and restriction days is set to 1 or 0
       	      		      m += "Maximum order quantity exceeded. The requested quantity of "
       	      			  + reqQty + " has been adjusted to " + maxQtyAllowed + ".";
       	      		      messKey = "shoppingMessages.text.maxOrderQuantityExceeded";
       	      		      messParams = new Object[5];
       	      		      messParams[0] = new Integer(reqQty);
       	      		      messParams[1] = new Integer(maxQtyAllowed);
       	      		      messParams[2] = new Integer(ctrl.getMaxOrderQty());
       	      		      messParams[3] = sciD.getActualSkuNum();
       	      		      messParams[4] = sciD.getProduct().getUom();
       	      		  }
       	      	  } 
      	      	  cii.setMessage(m);
      	      	  cii.setI18nItemMessage(messKey, messParams);

      	      	  cartData.addItemMessage(cii);

      	        } // if (reqQty > maxQtyAllowed) {
      	      }  // for (int j = 0; j < scItemDataVector.size(); j++) {
      	    } // if(null == ctrlv || ctrlv.size() <= 0) {
      	    return cartData;
    } // public static ShoppingCartData reviseShoppingCartQuantities()
    
    //private method to return the most recent history record from a pair of shopping carts.
    private static ShoppingInfoData getMostRecentCartHistoryRecord(ShoppingCartData pILCart,
    		ShoppingCartData pOGCart, String pValShortDesc){
        ShoppingInfoData mostRecentILUpdate = null;
        ShoppingInfoData mostRecentOGUpdate = null;
        ShoppingInfoData returnValue = null;
        //get the most recent inventory level update
        if (pILCart != null) {
        	mostRecentILUpdate = getMostRecentCartHistoryRecord(pILCart.getItemHistory(), pValShortDesc);
        }
        //get the most recent order guide update
        if (pOGCart != null) {
        	mostRecentOGUpdate = getMostRecentCartHistoryRecord(pOGCart.getItemHistory(), pValShortDesc);
        }
        //determine the most recent overall update
        if (mostRecentILUpdate != null && mostRecentOGUpdate == null) {
        	returnValue = mostRecentILUpdate;
        }
        else if (mostRecentILUpdate == null && mostRecentOGUpdate != null) {
        	returnValue = mostRecentOGUpdate;
        }
        else if (mostRecentILUpdate != null && mostRecentOGUpdate != null) {
        	if (mostRecentILUpdate.getModDate().compareTo(mostRecentOGUpdate.getModDate()) > 0) {
            	returnValue = mostRecentILUpdate;
        	}
        	else {
            	returnValue = mostRecentOGUpdate;
        	}
        }
        return returnValue;
    }
    
    //private method to return the most recent history record from list of history records.
    private static ShoppingInfoData getMostRecentCartHistoryRecord(List historyRecords, String pValShortDesc) {
    	ShoppingInfoData returnValue = null;
    	if (Utility.isSet(historyRecords)) {
            for (int i = 0; i < historyRecords.size(); i++ ){
                ShoppingInfoData sid = (ShoppingInfoData) historyRecords.get(i);
                if (pValShortDesc.equals(sid.getShortDesc())) {
                	if (returnValue == null || returnValue.getModDate().compareTo(sid.getModDate()) < 0) {
                		returnValue = sid;
                	}
                }
            }
    	}
    	return returnValue;
    }

} // end ShoppingCartLogic class
