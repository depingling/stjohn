package com.cleanwise.view.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cleanwise.view.forms.AccountMgrProductUITemplateForm;
import com.cleanwise.view.forms.StoreAccountMgrDetailForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.service.api.APIAccess;

import com.cleanwise.service.api.session.Account;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.ProductViewDefData;
import com.cleanwise.service.api.value.ProductViewDefDataVector;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class AccountMgrProductUITemplateLogic {
    private final static List ATTRIBUTES_NAMES = getAttributeNames();

    private final static List getAttributeNames() {
        final String[] names = { "Qty or On Hand Box Conditional", "Par value",
                "Actual Sku", "Distributor Sku", "Customer Sku", "System Sku",
                "Product Short Desc", "Price", "Line Price",
                "Delete Checkbox", "Resale Checkbox", "SPL",
                "Dist Inventory Show Flag", "Dist Inventory Show Quantity",
                "Manufacturer Sku", "Manufacturer Name", "Max Order Qty",
                "COLOR", "SCENT", "SIZE", "SHIP_WEIGHT", "WEIGHT_UNIT", "UOM",
                "PACK", "UPC_NUM", "PKG_UPC_NUM", "THUMBNAIL", "LIST_PRICE",
                "UNSPSC_CD", "OTHER_DESC", "PSN", "NSN", "HAZMAT", "CUBE_SIZE",
                "PACK_PROBLEM_SKU", "REBATE_BASE_COST", "Dist_Pack", RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.GREEN };
        List list = (List) Arrays.asList(names);
        Collections.sort(list);
        return Collections.unmodifiableList(list);
    }

    public static ActionMessages init(HttpServletRequest request,
            AccountMgrProductUITemplateForm pForm) throws Exception {
        ActionMessages ae = new ActionMessages();
        HttpSession session = request.getSession();
        StoreAccountMgrDetailForm accountForm = (StoreAccountMgrDetailForm) session
                .getAttribute("ACCOUNT_DETAIL_FORM");
        String accountIdS = accountForm.getId();
        int accountId = 0;
        try {
            accountId = Integer.parseInt(accountIdS);
        } catch (NumberFormatException exc) {
            ae.add("error", new ActionMessage("error.systemError",
                    "Wrong account number format: " + accountIdS));
            return ae;
        }
        pForm.setAccountId(accountId);
        pForm.setAccountName(accountForm.getAccountData().getBusEntity()
                .getShortDesc());
        APIAccess factory = new APIAccess();
        if (null == factory) {
            throw new Exception("No APIAccess.");
        }
        Account account = factory.getAccountAPI();
        ProductViewDefDataVector data = account
                .getProductViewDefData(accountId);
        final List buffer = new ArrayList(ATTRIBUTES_NAMES);
        ProductViewDefDataVector result = new ProductViewDefDataVector();
        for (int i = 0; data != null && i < data.size(); i++) {
            ProductViewDefData item = (ProductViewDefData) data.get(i);
            if (pForm.getShopUIProductViewCd().equals(item.getProductViewCd()) == true) {
                buffer.remove(item.getAttributename());
                result.add(item);
            }
        }
        for (int i = 0; i < buffer.size(); i++) {
            result.add(createDefault((String) buffer.get(i), pForm
                    .getShopUIProductViewCd()));
        }
        Collections.sort(result, ATTR_NAME_COMPARE);
        pForm.setData(result);
        return ae;
    }

    public static ActionMessages update(HttpServletRequest request,
            AccountMgrProductUITemplateForm pForm) throws Exception {
        ActionMessages ae = new ActionMessages();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session
                .getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("No APIAccess.");
        }
        SessionTool st = new SessionTool(request);
        CleanwiseUser userData = st.getUserData();
        String userName = userData.getUser().getUserName();
        Account account = factory.getAccountAPI();
        ProductViewDefDataVector dataReal = account.getProductViewDefData(pForm
                .getAccountId());
        ProductViewDefDataVector dataForm = pForm.getData();
        ProductViewDefDataVector dataResult = new ProductViewDefDataVector();
        Map mapReal = getAttributesMap(dataReal);
        for (int i = 0; dataForm != null && i < dataForm.size(); i++) {
            ProductViewDefData itemForm = (ProductViewDefData) dataForm.get(i);
            if (pForm.getShopUIProductViewCd().equals(
                    itemForm.getProductViewCd()) == true) {
                ProductViewDefData itemReal = (ProductViewDefData) mapReal
                        .get(pForm.getShopUIProductViewCd() + "_"
                                + itemForm.getAttributename());
                ProductViewDefData itemResult = null;
                if (itemReal != null) {
                    itemResult = populate(itemForm, itemReal);
                } else {
                    itemResult = itemForm;
                }
                dataResult.add(itemResult);
            }
        }
        account.updateProductViewDefData(pForm.getAccountId(), dataResult,
                userName);
        return init(request, pForm);
    }

    private final static ProductViewDefData populate(ProductViewDefData form,
            ProductViewDefData real) {
        real.setSortOrder(form.getSortOrder());
        real.setStatusCd(form.getStatusCd());
        real.setStyleClass(form.getStyleClass());
        real.setWidth(form.getWidth());
        return real;
    }

    private final static Map getAttributesMap(ProductViewDefDataVector data) {
        Map map = new HashMap();
        for (int i = 0; data != null && i < data.size(); i++) {
            ProductViewDefData item = (ProductViewDefData) data.get(i);
            map.put(item.getProductViewCd() + "_" + item.getAttributename(),
                    item);
        }
        return map;
    }

    private final static ProductViewDefData createDefault(String attrName,
            String uiType) {
        ProductViewDefData data = ProductViewDefData.createValue();
        data.setAttributename(attrName);
        data.setProductViewCd(uiType);
        return data;
    }

    static final Comparator ATTR_NAME_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 = ((ProductViewDefData)o1).getAttributename();
			String name2 = ((ProductViewDefData)o2).getAttributename();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};
}
