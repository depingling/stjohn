package com.cleanwise.view.forms;

/**
 *
 *@author     durval
 */

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ShoppingRestrictionsUtil;
import com.cleanwise.service.api.value.ShoppingRestrictionsView;
import com.cleanwise.view.utils.ProfileViewContainer;
import java.util.ArrayList;

public final class SiteShoppingControlForm 
  extends ShoppingControlForm {

    private int mSiteId;
    private ProfileViewContainer profile;
    private ArrayList<ShoppingRestrictionsView> mShoppingRestrictionsViews;
    private boolean mExistRestrictionDays = false;

    public void setSiteId(String pSiteId) { 
    mSiteId = Integer.parseInt(pSiteId); 
    }
    public void setSiteId(int pSiteId) { mSiteId = pSiteId; }
    public int getSiteId() { return mSiteId; }

    public ProfileViewContainer getProfile() {
        return this.profile;
    }

    /** Setter for property profile.
     * @param profile New value of property profile.
     *
     */
    public void setProfile(ProfileViewContainer profile) {
        this.profile = profile;
    }

    public Object getItemIdMaxAllowed(String key) {
        return super.getItemIdMaxAllowed(key);
    }

    public void setItemIdMaxAllowed(String key, Object value) {
        if (RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.INPUT_UNLIMITED_MAX_ORDER_QTY.equals(value)) {
           super.setItemIdMaxAllowed(key, "-1"); 
        }
        else {
            super.setItemIdMaxAllowed(key, value);
        }
    }

    public ArrayList<ShoppingRestrictionsView> getShoppingRestrictionsViews() {
        return mShoppingRestrictionsViews;
    }

    public void setShoppingRestrictionsViews(ArrayList<ShoppingRestrictionsView> items) {
        mShoppingRestrictionsViews = items;
        mExistRestrictionDays = 
            ShoppingRestrictionsUtil.existRestrictionDays(mShoppingRestrictionsViews);
    }

    private ShoppingRestrictionsView getShoppingControlItemView(int id) {
        if (mShoppingRestrictionsViews == null) {
            return null;
        }
        if (id < 0 || id >= mShoppingRestrictionsViews.size()) {
            return null;
        }
        return (ShoppingRestrictionsView) mShoppingRestrictionsViews.get(id);
    }

    public void setSkuNumInp(int id, String value) {
        ShoppingRestrictionsView item = getShoppingControlItemView(id);
        if (item == null) {
            return;
        }
        item.setItemSkuNum(value);
    }

    public String getSkuNumInp(int id) {
        ShoppingRestrictionsView item = getShoppingControlItemView(id);
        if (item == null) {
            return "";
        }
        return item.getItemSkuNum();
    }

    public void setShortDescInp(int id, String value) {
        ShoppingRestrictionsView item = getShoppingControlItemView(id);
        if (item == null) {
            return;
        }
        item.setItemShortDesc(value);
    }

    public String getShortDescInp(int id) {
        ShoppingRestrictionsView item = getShoppingControlItemView(id);
        if (item == null) {
            return "";
        }
        return item.getItemShortDesc();
    }

    public void setSizeInp(int id, String value) {
        ShoppingRestrictionsView item = getShoppingControlItemView(id);
        if (item == null) {
            return;
        }
        item.setItemSize(value);
    }

    public String getSizeInp(int id) {
        ShoppingRestrictionsView item = getShoppingControlItemView(id);
        if (item == null) {
            return "";
        }
        return item.getItemSize();
    }

    public void setUomInp(int id, String value) {
        ShoppingRestrictionsView item = getShoppingControlItemView(id);
        if (item == null) {
            return;
        }
        item.setItemUOM(value);
    }

    public String getUomInp(int id) {
        ShoppingRestrictionsView item = getShoppingControlItemView(id);
        if (item == null) {
            return "";
        }
        return item.getItemUOM();
    }

    public void setPackInp(int id, String value) {
        ShoppingRestrictionsView item = getShoppingControlItemView(id);
        if (item == null) {
            return;
        }
        item.setItemPack(value);
    }

    public String getPackInp(int id) {
        ShoppingRestrictionsView item = getShoppingControlItemView(id);
        if (item == null) {
            return "";
        }
        return item.getItemPack();
    }

    public boolean getExistRestrictionDays() {
        return mExistRestrictionDays;
    }

    public void setExistRestrictionDays(boolean existRestrictionDays) {
        return;
    }

}

