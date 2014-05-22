package com.cleanwise.service.api.util;

import java.util.ArrayList;
import com.cleanwise.service.api.value.ShoppingRestrictionsView;

public class ShoppingRestrictionsUtil {

    public static boolean existRestrictionDays(ArrayList<ShoppingRestrictionsView> items) {
        if (items == null) {
            return false;
        }
        if (items.size() == 0) {
            return false;
        }
        String restrictionDays = null;
        for (int i = 0; i < items.size(); ++i) {
            ShoppingRestrictionsView item = (ShoppingRestrictionsView)items.get(i);
            restrictionDays = item.getRestrictionDays();
            if (restrictionDays != null) {
                restrictionDays = restrictionDays.trim();
                if (restrictionDays.length() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getOrderQuantityByString(String orderQty) {
        if (orderQty == null) {
            return -1;
        }
        String orderQtyAux = orderQty.trim();
        if (orderQtyAux.length() == 0) {
            return -1;
        }
        if (orderQtyAux.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.INPUT_UNLIMITED_MAX_ORDER_QTY)) {
            return -1;
        }
        int nOrderQty = -1;
        try { 
            nOrderQty = Integer.parseInt(orderQtyAux);
        }
        catch (Exception ex) {            
        }
        return nOrderQty;
    }

    public static String getOrderQuantityToDisplay(String orderQty) {
        if (orderQty == null) {
            return "";
        }
        String orderQtyAux = orderQty.trim();
        if (orderQtyAux.length() == 0) {
            return "";
        }
        if (orderQtyAux.equals(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.INPUT_UNLIMITED_MAX_ORDER_QTY)) {
            return RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.INPUT_UNLIMITED_MAX_ORDER_QTY;
        }
        int nOrderQty = -1;
        try { 
            nOrderQty = Integer.parseInt(orderQtyAux);
        }
        catch (Exception ex) {            
        }
        if (nOrderQty < 0) {
            return RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.INPUT_UNLIMITED_MAX_ORDER_QTY;
        }
        return String.valueOf(nOrderQty);
    }

    public static int getActualOrderQuantity(Integer siteOrderQty, Integer accountOrderQty) {
        if (siteOrderQty == null && accountOrderQty == null) {
            return -1;
        }
        if (siteOrderQty != null && accountOrderQty == null) {
            return siteOrderQty.intValue();
        }
        if (siteOrderQty == null && accountOrderQty != null) {
            return accountOrderQty.intValue();
        }
        return siteOrderQty.intValue();
    }

    public static int getActualOrderQuantity(String siteOrderQty, String accountOrderQty) {
        if (siteOrderQty == null && accountOrderQty == null) {
            return -1;
        }
        if (siteOrderQty != null && accountOrderQty == null) {
            return getOrderQuantityByString(siteOrderQty);
        }
        if (siteOrderQty == null && accountOrderQty != null) {
            return getOrderQuantityByString(accountOrderQty);
        }
        return getOrderQuantityByString(siteOrderQty);
    }

    public static String getActualOrderQuantityToDisplay(String siteOrderQty, String accountOrderQty) {
        if (siteOrderQty == null && accountOrderQty == null) {
            return "";
        }
        if (siteOrderQty != null && accountOrderQty == null) {
            return getOrderQuantityToDisplay(siteOrderQty);
        }
        if (siteOrderQty == null && accountOrderQty != null) {
            return getOrderQuantityToDisplay(accountOrderQty);
        }
        int quantity = getActualOrderQuantity(siteOrderQty, accountOrderQty);
        return getOrderQuantityToDisplay(String.valueOf(quantity));
    }

}
