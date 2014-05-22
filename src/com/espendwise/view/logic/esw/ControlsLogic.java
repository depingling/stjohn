/**
 * Title: ControlsLogic
 * Description: This is the business logic class handling the ESW controls functionality.
 */
package com.espendwise.view.logic.esw;


import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ShoppingRestrictionsUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.ShoppingControlData;
import com.cleanwise.service.api.value.ShoppingControlItemView;
import com.cleanwise.service.api.value.ShoppingRestrictionsView;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteInventoryConfigView;
import com.cleanwise.service.api.value.SiteInventoryConfigViewVector;
import com.cleanwise.view.forms.ShoppingCartForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.SelectShippingAddressLogic;
import com.cleanwise.view.logic.ShoppingCartLogic;
import com.cleanwise.view.logic.SiteMgrLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.ControlsForm;

public class ControlsLogic {
    private static final Logger log = Logger.getLogger(ControlsLogic.class);

    public static ActionErrors showParValues(HttpServletRequest request,
            ControlsForm form) {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = null;
        try {
            factory = new APIAccess();
            Site sbean = factory.getSiteAPI();
            SiteInventoryConfigViewVector ov = sbean.lookupInventoryConfig(
                    ShopTool.getCurrentSiteId(request), true, SessionTool
                            .getCategoryToCostCenterView(session, ShopTool
                                    .getCurrentSiteId(request)));
            form.getInventoryForm().setInventoryItems(ov, false);
            SiteData thisSite = ShopTool.getCurrentSite(request);
            int inventoryItemsSize = ov == null ? 0 : ov.size();
            int budgetPeriodsSize = thisSite.getBudgetPeriods().size();
            if (budgetPeriodsSize == 0) {
                String errorMess = ClwI18nUtil.getMessage(request,
                        "userportal.esw.errors.invalidBudgetPeriodsSize", null);
                ae.add("error", new ActionError("error.simpleGenericError",
                        errorMess));
            }
            for (int i = 0; i < inventoryItemsSize; i++) {
                for (int bp = 1; bp <= budgetPeriodsSize; bp++) {
                    SiteInventoryConfigView item = form.getInventoryForm()
                            .getInventoryItem(i);
                    Object parValue = (item.getParValues() != null) ? item
                            .getParValues().get(bp) : null;
                    form.getInventoryItem(item.getItemId()).setParValue(bp,
                            parValue == null ? "" : parValue.toString());
                }
            }
        } catch (Exception e) {
            log.error("Error in showParValues", e);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.unExpectedError", null);
            ae.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
        return ae;
    }

    public static ActionErrors updateParValues(HttpServletRequest request,
            ControlsForm form) {
        ActionErrors ae = new ActionErrors();
        try {
            Date curDate = new Date();
            CleanwiseUser user = ShopTool.getCurrentUser(request);
            APIAccess factory = new APIAccess();
            Site sbean = factory.getSiteAPI();
            SiteData thisSite = ShopTool.getCurrentSite(request);
            SiteInventoryConfigViewVector existOV = sbean
                    .lookupInventoryConfig(thisSite.getBusEntity()
                            .getBusEntityId(), true, SessionTool
                            .getCategoryToCostCenterView(request.getSession(),
                                    ShopTool.getCurrentSiteId(request)));
            form.getInventoryForm().setInventoryItems(existOV, false);
            validateAndSetParValues(request, form, ae, existOV, thisSite, user
                    .getUserName());
            if (ae.size() == 0) {
                sbean.storeInventoryConfig(existOV);
                //------------------------------------
                recalcInvCartQty(request);
                //--------------------------------------
            }
        } catch (Exception e) {
            log.error("Error in updateParValues", e);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.unExpectedError", null);
            ae.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
        return ae;
    }

    private static void validateAndSetParValues(HttpServletRequest request,
            ControlsForm form, ActionErrors errors,
            SiteInventoryConfigViewVector existOV, SiteData thisSite,
            String pModBy) {
        List<String> wrongItemSkus = new ArrayList<String>();
        int budgetPeriodsSize = thisSite.getBudgetPeriods().size();
        for (int i = 0; existOV != null && i < existOV.size(); i++) {
            SiteInventoryConfigView item = (SiteInventoryConfigView) existOV
                    .get(i);
            String itemSku = null;
            HashMap<Integer, Integer> parValuesMap = new HashMap<Integer, Integer>();
            for (int period = 1; period <= budgetPeriodsSize; period++) {
                String parValue = form.getInventoryItem(item.getItemId())
                        .getParValue(period);
                if (Utility.isSet(parValue) == true) {
                    itemSku = item.getActualSku();
                    try {
                        int parValueI = Integer.parseInt(parValue);
                        if (parValueI < 0
                                && wrongItemSkus.contains(itemSku) == false) {
                            wrongItemSkus.add(itemSku);
                        }
                        parValuesMap.put(period, parValueI);
                    } catch (Exception e) {
                        if (wrongItemSkus.contains(itemSku) == false) {
                            wrongItemSkus.add(itemSku);
                        }
                    }
                }
            }
            if (parValuesMap.equals(form.getInventoryForm().getInventoryItem(i)
                    .getParValues()) == false) {
                form.getInventoryForm().getInventoryItem(i).setModBy(pModBy);
                form.getInventoryForm().getInventoryItem(i).setParValues(
                        parValuesMap);
            }
        }
        if (wrongItemSkus.size() > 0) {
            Object[] param = new Object[1];
            param[0] = Utility.toCommaSting(wrongItemSkus);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "userportal.esw.errors.invalidParValuesForItem", param);
            errors.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
    }

    public static ActionErrors showProductLimits(HttpServletRequest request,
            ControlsForm form) {
        ActionErrors ae = new ActionErrors();
        try {
            CleanwiseUser user = ShopTool.getCurrentUser(request);
            SiteMgrLogic.init(request, form.getSiteShoppingControlForm());
            ArrayList<ShoppingRestrictionsView> list = form
                    .getSiteShoppingControlForm()
                    .getShoppingRestrictionsViews();
            IdVector itemIds = new IdVector();
            for (ShoppingRestrictionsView i : list) {
                itemIds.add(i.getItemId());
            }
            form.setProductDataByItemIdMap(ControlsLogic
                    .prepareProductDataByItemIdMap(request, itemIds));
            String storeTypeCd = user.getUserStore().getStoreType().getValue();
            for (ShoppingRestrictionsView i : list) {
                String actualSku = getActualSku(storeTypeCd, form
                        .getProductDataByItemIdMap().get(i.getItemId()));
                i.setItemSkuNum(actualSku);
            }
        } catch (Exception e) {
            log.error("Error in showProductLimits", e);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.unExpectedError", null);
            ae.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
        return ae;
    }

    public static String getActualSku(String storeTypeCd,
            ProductData productData) {
        String custSku = productData.getCustomerSkuNum();
        if (Utility.isSet(custSku)) {
            return custSku;
        } else if (RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(storeTypeCd)) {
            if (productData.getCatalogDistrMapping() == null) {
                return productData.getManufacturerSku();
            } else {
                return productData.getCatalogDistrMapping().getItemNum();
            }
        } else {
            return String.valueOf(productData.getSkuNum());
        }
    }

    public static ActionErrors updateProductLimits(HttpServletRequest request,
            ControlsForm form) {
        ActionErrors ae = new ActionErrors();
        try {
            SiteMgrLogic.lookupShoppingControls(request, null);
            SiteData site = ShopTool.getCurrentSite(request);
            SessionTool sessionTool = new SessionTool(request);
            List shoppingControls = sessionTool.getSiteSettings(request,
                    site.getSiteId()).getShoppingControls();
            ae = validateProductLimits(request, shoppingControls, form);
            if (ae != null && ae.isEmpty() == false) {
                return ae;
            }
            form.getSiteShoppingControlForm().setSiteId(site.getSiteId());
            for (Map.Entry<String, String> entry : form
                    .getItemIdMaxAllowedEntries()) {
                form.getSiteShoppingControlForm().setItemIdMaxAllowed(
                        entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, String> entry : form
                    .getItemIdRestrictionDaysEntries()) {
                form.getSiteShoppingControlForm().setItemIdRestrictionDays(
                        entry.getKey(), entry.getValue());
            }
            ae = SiteMgrLogic.updateShoppingRestrictions(request, form
                    .getSiteShoppingControlForm());
        } catch (Exception e) {
            log.error("Error in updateProductLimits", e);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.unExpectedError", null);
            ae.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
        return ae;
    }

    private static ActionErrors validateProductLimits(
            HttpServletRequest request, List shoppingControls, ControlsForm form) {
        ActionErrors ae = new ActionErrors();
        List<String> wrongItemsSKUForQty = new ArrayList<String>();
        List<String> wrongItemsSKUForDays = new ArrayList<String>();
        for (Object shoppingControl : shoppingControls) {
            ShoppingControlItemView sciv = ((ShoppingControlItemView) shoppingControl);
            ShoppingControlData siteControl = sciv.getShoppingControlData();
            String maxqty = form.getItemIdMaxAllowed(String.valueOf(siteControl
                    .getItemId()));
            if (RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.INPUT_UNLIMITED_MAX_ORDER_QTY
                    .equals(maxqty) == false
                    && Utility.isSet(maxqty)) {
                try {
                    int maxqtyI = Integer.parseInt(maxqty.trim());
                    if (maxqtyI < 0) {
                        wrongItemsSKUForQty.add(sciv.getSkuNum());
                    }
                } catch (NumberFormatException e) {
                    wrongItemsSKUForQty.add(sciv.getSkuNum());
                }
            }
        }
        if (wrongItemsSKUForQty.isEmpty() == false) {
            Object[] param = new Object[1];
            param[0] = Utility.toCommaSting(wrongItemsSKUForQty);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "shop.errors.invalidMaxAllowedQtyMessage", param);
            ae.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
        return ae;
    }

    public static Map<Integer, ProductData> prepareProductDataByItemIdMap(
            HttpServletRequest request, IdVector itemIds) throws Exception {
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session
                .getAttribute(Constants.APIACCESS);
        CleanwiseUser user = ShopTool.getCurrentUser(request);
        int catalogId = ((Integer) session.getAttribute(Constants.CATALOG_ID))
                .intValue();
        int contractId = 0;
        Integer contractIdI = (Integer) session
                .getAttribute(Constants.CONTRACT_ID);
        if (contractIdI != null) {
            contractId = contractIdI.intValue();
        }
        String storeTypeCd = user.getUserStore().getStoreType().getValue();
        ShoppingServices shoppingServices = factory.getShoppingServicesAPI();
        ShoppingCartItemDataVector items = shoppingServices
                .prepareShoppingItems(storeTypeCd, user.getSite(), catalogId,
                        contractId, itemIds);
        Map<Integer, ProductData> result = new TreeMap<Integer, ProductData>();
        for (int i = 0; items != null && i < items.size(); i++) {
            ShoppingCartItemData item = (ShoppingCartItemData) items.get(i);
            result.put(item.getItemId(), item.getProduct());
        }
        return result;
    }

    public static ActionMessages sortParValues(final HttpServletRequest request,
            final ControlsForm form) {
        ActionMessages result = new ActionMessages();
        if (form == null) {
            return result;
        }
        if (Utility.isSet(form.getSortField()) == false) {
            form.setSortField(Constants.PAR_VALUE_SORT_FIELD_PRODUCT_NAME);
        }
        if (Utility.isSet(form.getSortOrder()) == false) {
            form.setSortOrder(Constants.PAR_VALUE_SORT_ORDER_ASCENDING);
        }
        SiteInventoryConfigViewVector list = form.getInventoryForm()
                .getInventoryItems();
        if (list == null || list.isEmpty()) {
            String message = ClwI18nUtil.getMessage(request,
                    "userportal.esw.text.noItemsWithParValues", null);
            result.add("message", new ActionMessage("error.simpleError",
                    message));
            return result;
        }
        if (list != null && list.size() > 1) {
            Collections.sort(list, new Comparator<SiteInventoryConfigView>() {
                @Override
                public int compare(SiteInventoryConfigView o1,
                        SiteInventoryConfigView o2) {
                    String s1 = o1.getItemDesc();
                    String s2 = o2.getItemDesc();
                    if (Constants.PAR_VALUE_SORT_FIELD_SKU.equals(form
                            .getSortField())) {
                        s1 = o1.getActualSku();
                        s2 = o2.getActualSku();
                    } else if (Constants.PAR_VALUE_SORT_FIELD_PACK.equals(form
                            .getSortField())) {
                        s1 = o1.getItemPack();
                        s2 = o2.getItemPack();
                    } else if (Constants.PAR_VALUE_SORT_FIELD_UOM.equals(form
                            .getSortField())) {
                        s1 = o1.getItemUom();
                        s2 = o2.getItemUom();
                    } else if (Constants.PAR_VALUE_SORT_FIELD_MODIFED
                            .equals(form.getSortField())) {
                        s1 = o1.getModBy();
                        s2 = o2.getModBy();
                    }
                    s1 = Utility.strNN(s1);
                    s2 = Utility.strNN(s2);
                    if (Constants.PAR_VALUE_SORT_ORDER_ASCENDING.equals(form
                            .getSortOrder())) {
                        return s1.compareToIgnoreCase(s2);
                    } else {
                        return s2.compareToIgnoreCase(s1);
                    }
                }
            });
            form.getInventoryForm().setInventoryItems(list, false);
        }
        return result;
    }

    public static ActionMessages sortProductLimits(final HttpServletRequest request,
            final ControlsForm form) {
        ActionMessages result = new ActionMessages(); 
        if (form == null) {
            return result;
        }
        if (Utility.isSet(form.getSortField()) == false) {
            form.setSortField(Constants.PRODUCT_LIMIT_SORT_FIELD_PRODUCT_NAME);
        }
        if (Utility.isSet(form.getSortOrder()) == false) {
            form.setSortOrder(Constants.PRODUCT_LIMIT_SORT_ORDER_ASCENDING);
        }
        ArrayList<ShoppingRestrictionsView> list = form
                .getSiteShoppingControlForm().getShoppingRestrictionsViews();
        if (list == null || list.isEmpty()) {
            String message = ClwI18nUtil.getMessage(request,
                    "userportal.esw.text.noItemsWithProductLimits", null);
            result.add("message", new ActionMessage("error.simpleError",
                    message));
            return result;
        }
        for (int i = 0; list != null && i < list.size(); i++) {
            ShoppingRestrictionsView item = list.get(i);
            String siteMaxOrderQty = item.getSiteMaxOrderQty();
            String accountMaxOrderQty = item.getAccountMaxOrderQty();
            String siteQtyToDiplay = Utility.isSet(siteMaxOrderQty) ? ShoppingRestrictionsUtil
                    .getActualOrderQuantityToDisplay(siteMaxOrderQty,
                            accountMaxOrderQty)
                    : "";
            form.setItemIdMaxAllowed("" + item.getItemId(), siteQtyToDiplay);
            form.setItemIdRestrictionDays("" + item.getItemId(), item.getRestrictionDays());
        }
        if (list != null && list.size() > 1) {
            Collections.sort(list, new Comparator<ShoppingRestrictionsView>() {
                @Override
                public int compare(ShoppingRestrictionsView o1,
                        ShoppingRestrictionsView o2) {
                    Comparable c1 = null;
                    Comparable c2 = null;
                    String s1 = o1.getItemShortDesc();
                    String s2 = o2.getItemShortDesc();
                    if (Constants.PRODUCT_LIMIT_SORT_FIELD_SKU.equals(form
                            .getSortField())) {
                        s1 = o1.getItemSkuNum();
                        s2 = o2.getItemSkuNum();
                    } else if (Constants.PRODUCT_LIMIT_SORT_FIELD_PACK
                            .equals(form.getSortField())) {
                        s1 = o1.getItemPack();
                        s2 = o2.getItemPack();
                    } else if (Constants.PRODUCT_LIMIT_SORT_FIELD_UOM
                            .equals(form.getSortField())) {
                        s1 = o1.getItemUOM();
                        s2 = o2.getItemUOM();
                    } else if (Constants.PRODUCT_LIMIT_SORT_FIELD_LOCATION_MAX_QTY
                            .equals(form.getSortField())) {
                        c1 = getIntValue(ShoppingRestrictionsUtil
                                .getActualOrderQuantityToDisplay(o1
                                        .getSiteMaxOrderQty(), o1
                                        .getAccountMaxOrderQty()));
                        c2 = getIntValue(ShoppingRestrictionsUtil
                                .getActualOrderQuantityToDisplay(o2
                                        .getSiteMaxOrderQty(), o2
                                        .getAccountMaxOrderQty()));
                    } else if (Constants.PRODUCT_LIMIT_SORT_FIELD_ACCOUNT_MAX_QTY
                            .equals(form.getSortField())) {
                        c1 = getIntValue(ShoppingRestrictionsUtil
                                .getOrderQuantityToDisplay(o1
                                        .getAccountMaxOrderQty()));
                        c2 = getIntValue(ShoppingRestrictionsUtil
                                .getOrderQuantityToDisplay(o2
                                        .getAccountMaxOrderQty()));
                    } else if (Constants.PRODUCT_LIMIT_SORT_FIELD_RESTRICTED_DAYS
                            .equals(form.getSortField())) {
                        c1 = getIntValue(getRestrictionDays(o1));
                        c2 = getIntValue(getRestrictionDays(o2));
                    } else if (Constants.PRODUCT_LIMIT_SORT_FIELD_MODIFIED
                            .equals(form.getSortField())) {
                        s1 = getControlMod(o1);
                        s2 = getControlMod(o2);
                    }
                    s1 = Utility.strNN(s1).toUpperCase();
                    s2 = Utility.strNN(s2).toUpperCase();
                    if (c1 == null || c2 == null || c1.compareTo(c2) == 0) {
                        c1 = s1;
                        c2 = s2;
                    }
                    if (Constants.PRODUCT_LIMIT_SORT_ORDER_ASCENDING
                            .equals(form.getSortOrder())) {
                        return c1.compareTo(c2);
                    } else {
                        return c2.compareTo(c1);
                    }
                }
            });
            form.getSiteShoppingControlForm()
                    .setShoppingRestrictionsViews(list);
        }
        return result;
    }

    private static Integer getIntValue(String value) {
        if ("".equals(value)) {
            return -1;
        } else if (RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.INPUT_UNLIMITED_MAX_ORDER_QTY
                .equals(value)) {
            return Integer.MAX_VALUE;
        } else {
            return Utility.parseInt(value);
        }
    }

    private static String getRestrictionDays(ShoppingRestrictionsView view) {
        String restrictionDays = view.getRestrictionDays();
        if (restrictionDays == null) {
            restrictionDays = "";
        } else {
            restrictionDays = restrictionDays.trim();
            if ("-1".equals(restrictionDays) || "-999".equals(restrictionDays)) {
                restrictionDays = RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.INPUT_UNLIMITED_MAX_ORDER_QTY;
            }
        }
        return restrictionDays;
    }

    private static String getControlMod(ShoppingRestrictionsView view) {
        int accountId = view.getAccountId();
        int siteId = view.getSiteId();
        java.util.Date controlModDate = null;
        String controlModBy = null;
        if (accountId > 0 && siteId == 0) {
            controlModBy = view.getAcctControlModBy();
            controlModDate = view.getAcctControlModDate();
        } else {
            controlModBy = view.getSiteControlModBy();
            controlModDate = view.getSiteControlModDate();
        }
        return controlModBy + "_" + controlModDate.getTime();
    }
    
    private static void recalcInvCartQty(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	SelectShippingAddressLogic.setShoppingSessionObjects(session, ShopTool.getCurrentUser(request));
        ShoppingCartData cart = ShopTool.getCurrentInventoryCart(session);
		if (cart != null) {
			ActionErrors ae = ShoppingCartLogic.recalInvCartQty(request, cart.getInventoryItemsOnly());
		}
        
        
        //ShoppingCartForm scForm = (ShoppingCartForm)session.getAttribute(Constants.INVENTORY_SHOPPING_CART_FORM);
        //scForm.setShoppingCart(cart);
        //ActionErrors ae = ShoppingCartLogic.calcInvOrderQty(request, scForm);
        
    }
}