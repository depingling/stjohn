/**
 * Title: ControlsForm

 * Description: This is the Struts ActionForm class handling the ESW control functionality.
 *
 */
package com.espendwise.view.forms.esw;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.view.forms.InventoryForm;
import com.cleanwise.view.forms.SiteShoppingControlForm;

/**
 * Implementation of <code>ActionForm</code> that handles controls
 * functionality.
 */
public final class ControlsForm extends EswForm {
    private static final long serialVersionUID = -1L;
    private String sortField;
    private String sortOrder;
    private InventoryForm inventoryForm;
    private SiteShoppingControlForm siteShoppingControlForm;
    private Map<Integer, ProductData> productDataByItemIdMap;
    private Map<String, String> itemIdMaxAllowedMap = new HashMap<String, String>();
    private Map<String, String> itemIdRestrictionDaysMap = new HashMap<String, String>();
    private Map<Integer, ParValueHolder> parValuesMap = new HashMap<Integer, ParValueHolder>();

    public Set<Map.Entry<String, String>> getItemIdMaxAllowedEntries() {
        return itemIdMaxAllowedMap.entrySet();
    }

    public String getItemIdMaxAllowed(String key) {
        return itemIdMaxAllowedMap.get(key);
    }

    public void setItemIdMaxAllowed(String key, String value) {
        itemIdMaxAllowedMap.put(key, value);
    }

    public Set<Map.Entry<String, String>> getItemIdRestrictionDaysEntries() {
        return itemIdRestrictionDaysMap.entrySet();
    }

    public String getItemIdRestrictionDays(String key) {
        return itemIdRestrictionDaysMap.get(key);
    }

    public void setItemIdRestrictionDays(String key, String value) {
        itemIdRestrictionDaysMap.put(key, value);
    }
    
    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public InventoryForm getInventoryForm() {
        if (inventoryForm == null) {
            inventoryForm = new InventoryForm();
        }
        return inventoryForm;
    }

    public void setInventoryForm(InventoryForm inventoryForm) {
        this.inventoryForm = inventoryForm;
    }

    public SiteShoppingControlForm getSiteShoppingControlForm() {
        if (siteShoppingControlForm == null) {
            siteShoppingControlForm = new SiteShoppingControlForm();
        }
        return siteShoppingControlForm;
    }

    public void setSiteShoppingControlForm(
            SiteShoppingControlForm siteShoppingControlForm) {
        this.siteShoppingControlForm = siteShoppingControlForm;
    }

    public Map<Integer, ProductData> getProductDataByItemIdMap() {
        return productDataByItemIdMap;
    }

    public void setProductDataByItemIdMap(
            Map<Integer, ProductData> productDataByItemIdMap) {
        this.productDataByItemIdMap = productDataByItemIdMap;
    }

    public ParValueHolder getInventoryItem(int itemId) {
        ParValueHolder item = parValuesMap.get(itemId);
        if (item == null) {
            item = new ParValueHolder();
            parValuesMap.put(itemId, item);
        }
        return item;
    }

    public static class ParValueHolder {
        private Map<Integer, String> values = new HashMap<Integer, String>();

        public String getParValue(int period) {
            return values.get(period);
        }

        public void setParValue(int period, String value) {
            values.put(period, value);
        }

        public String toString() {
            return values.toString();
        }
    }
}