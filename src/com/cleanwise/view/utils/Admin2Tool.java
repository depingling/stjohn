package com.cleanwise.view.utils;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.Request;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.value.*;

import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Field;


public class  Admin2Tool {

    private static final Logger log = Logger.getLogger(Admin2Tool.class);

    public interface FORM_VECTORS {
        public static final String USER_TYPE_CODE              = "admin2.user.type.vector";
        public static final String USER_STATUS_CODE            = "admin2.user.status.vector";
        public static final String COUNTRY_CD                  = "admin2.country.vector";
        public static final String BSC                         = "admin2.list.all.bsc";
        public static final String BUS_ENTITY_STATUS_CD        = "admin2.busentity.status.vector";
        public static final String LOCALE_CD                   = "admin2.user.locale.vector";
        public static final String MANIFEST_LABEL_TYPE_CD      = "admin2.manifest_label_type.vector";
        public static final String MANIFEST_LABEL_MODE_CD      = "admin2.manifest_label_print_mode_cd.vector";
        public static final String CUSTOMER_SERVICE_ROLE_CD    = "admin2.customerService.role.vector";
        public static final String ACCOUNT_TYPE_CD             = "admin2.account.type.vector";
        public static final String CUSTOMER_SYSTEM_APPROVAL_CD = "admin2.customer_system_approval.vector";
        public static final String INVENTORY_OG_LIST_UI        = "admin2.inventory_og_list_ui.vector";
        public static final String ORDER_ITEM_DETAIL_ACTION_CD = "admin2.order_item_detail_action_cd.vector";
        public static final String BUDGET_ACCRUAL_TYPE_CD      = "admin2.budget_accural_type.vector";
        public static final String GL_TRANSFORMATION_TYPE      = "admin2.gl_transformation_type.vector";
        public static final String TIME_ZONE_CD                = "admin2.time_zone.vector";
        public static final String FREIGHT_CHARGE_CD           = "admin2.freight_charge.vector";
        public static final String SHOP_UI_TYPE                = "admin2.shop_ui_type.vector";
        public static final String DIST_INVENTORY_DISPLAY      = "admin2.dist_inventoty_display.vector";
        public static final String DISTR_PO_TYPE               = "admin2.distr_po_type";
    }

    public static ActionErrors checkRequest(HttpServletRequest request) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No user info"));
            return ae;
        }

        if (!isUserAllowed(appUser)) {
            ae.add("error", new ActionError("error.simpleGenericError", "Unauthorized access"));
            throw new Exception("Unauthorized access");
            // return ae;
        }

        if (appUser.isaAccountAdmin() && appUser.getUserAccount() == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "error.badRequest2"));
            return ae;
        }

        if (appUser.isaStoreAdmin() && appUser.getUserStore() == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "error.badRequest2"));
            return ae;
        }

        return ae;

    }

    private static boolean isUserAllowed(CleanwiseUser pAppUser) {
        return pAppUser != null && (pAppUser.isaAccountAdmin() || pAppUser.isaAdmin());
    }

    public static void removeAllFormVectors(HttpServletRequest request) {
        log.info("removeAllFormVectors => BEGIN");
        HttpSession session = request.getSession();
        Field[] list = FORM_VECTORS.class.getFields();
        for (Field vField : list) {
            try {
                String vName = (String) vField.get(null);
                log.info("removeAllFormVectors => vName: '" + vName + "'");
                session.setAttribute(vName, null);
            } catch (IllegalAccessException e) {
                log.error(e);
            }
        }
        log.info("removeAllFormVectors => END.");
    }

    public static void initFormVectors(HttpServletRequest request, String... list) throws Exception {

        log.info("initFormVectors => BEGIN");

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        for (String vName : list) {
            if (null == session.getAttribute(vName)) {

                log.info("initFormVectors => init vName: '" + vName + "'");

                if (FORM_VECTORS.USER_TYPE_CODE.equals(vName)) {
                    User userEjb = factory.getUserAPI();
                    RefCdDataVector userTypeVector = userEjb.getManageableUserTypes(appUser.getUser().getUserTypeCd());
                    session.setAttribute(FORM_VECTORS.USER_TYPE_CODE, userTypeVector);
                }  else if (FORM_VECTORS.USER_STATUS_CODE.equals(vName)) {
                    ListService lsvc = factory.getListServiceAPI();
                    RefCdDataVector statusv = lsvc.getRefCodesCollection("USER_STATUS_CD");
                    session.setAttribute(FORM_VECTORS.USER_STATUS_CODE, statusv);
                } else if (FORM_VECTORS.BUS_ENTITY_STATUS_CD.equals(vName)) {
                    ListService lsvc = factory.getListServiceAPI();
                    RefCdDataVector statusv = lsvc.getRefCodesCollection("BUS_ENTITY_STATUS_CD");
                    session.setAttribute(FORM_VECTORS.BUS_ENTITY_STATUS_CD, statusv);
                } else if (FORM_VECTORS.COUNTRY_CD.equals(vName)) {
                    Country countryEjb = factory.getCountryAPI();
                    CountryDataVector countriesv = countryEjb.getAllCountries();
                    session.setAttribute(FORM_VECTORS.COUNTRY_CD, countriesv);
                } else if (FORM_VECTORS.BSC.equals(vName)) {
                    IdVector storeIds = appUser.getUserStoreAsIdVector();
                    Request rEjb = factory.getRequestAPI();
                    List listAll = rEjb.listAll(storeIds, RefCodeNames.BUS_ENTITY_TYPE_CD.BUILDING_SVC_CONTRACTOR);
                    DisplayListSort.sort((BuildingServicesContractorViewVector) listAll, "short_desc");
                    session.setAttribute(FORM_VECTORS.BSC, listAll);
                } else if (FORM_VECTORS.LOCALE_CD.equals(vName)) {
                    ListService lsvc = factory.getListServiceAPI();
                    RefCdDataVector locales = lsvc.getRefCodesCollection("LOCALE_CD");
                    session.setAttribute(FORM_VECTORS.LOCALE_CD, locales);
                } else if (FORM_VECTORS.MANIFEST_LABEL_TYPE_CD.equals(vName)) {
                    ListService lsvc = factory.getListServiceAPI();
                    RefCdDataVector col = lsvc.getRefCodesCollection("MANIFEST_LABEL_TYPE_CD");
                    session.setAttribute(FORM_VECTORS.MANIFEST_LABEL_TYPE_CD, col);
                } else if (FORM_VECTORS.MANIFEST_LABEL_MODE_CD.equals(vName)) {
                    ListService lsvc = factory.getListServiceAPI();
                    RefCdDataVector col = lsvc.getRefCodesCollection("MANIFEST_LABEL_MODE_CD");
                    session.setAttribute(FORM_VECTORS.MANIFEST_LABEL_MODE_CD, col);
                } else if (FORM_VECTORS.CUSTOMER_SERVICE_ROLE_CD.equals(vName)) {
                    ListService lsvc = factory.getListServiceAPI();
                    RefCdDataVector crcRolev = lsvc.getRefCodesCollection("CUSTOMER_SERVICE_ROLE_CD");
                    session.setAttribute(FORM_VECTORS.CUSTOMER_SERVICE_ROLE_CD, crcRolev);
                } else if (FORM_VECTORS.ACCOUNT_TYPE_CD.equals(vName)) {
                    ListService lsvc = factory.getListServiceAPI();
                    RefCdDataVector accountTypeCd = lsvc.getRefCodesCollection("ACCOUNT_TYPE_CD");
                    session.setAttribute(FORM_VECTORS.ACCOUNT_TYPE_CD, accountTypeCd);
                } else if (FORM_VECTORS.CUSTOMER_SYSTEM_APPROVAL_CD.equals(vName)) {
                    ListService lsvc = factory.getListServiceAPI();
                    RefCdDataVector refcds = lsvc.getRefCodesCollection("CUSTOMER_SYSTEM_APPROVAL_CD");
                    session.setAttribute(FORM_VECTORS.CUSTOMER_SYSTEM_APPROVAL_CD, refcds);
                } else if (FORM_VECTORS.INVENTORY_OG_LIST_UI.equals(vName)) {
                    ListService lsvc = factory.getListServiceAPI();
                    RefCdDataVector refcds = lsvc.getRefCodesCollection("INVENTORY_OG_LIST_UI");
                    session.setAttribute(FORM_VECTORS.INVENTORY_OG_LIST_UI, refcds);
                } else if (FORM_VECTORS.ORDER_ITEM_DETAIL_ACTION_CD.equals(vName)) {
                    ListService lsvc = factory.getListServiceAPI();
                    RefCdDataVector refcds = lsvc.getRefCodesCollection("ORDER_ITEM_DETAIL_ACTION_CD");
                    session.setAttribute(FORM_VECTORS.ORDER_ITEM_DETAIL_ACTION_CD, refcds);
                } else if (FORM_VECTORS.BUDGET_ACCRUAL_TYPE_CD.equals(vName)) {
                    ListService lsvc = factory.getListServiceAPI();
                    RefCdDataVector refcds = lsvc.getRefCodesCollection("BUDGET_ACCRUAL_TYPE_CD");
                    session.setAttribute(FORM_VECTORS.BUDGET_ACCRUAL_TYPE_CD, refcds);
                } else if (FORM_VECTORS.GL_TRANSFORMATION_TYPE .equals(vName)) {
                    ListService lsvc = factory.getListServiceAPI();
                    RefCdDataVector refcds = lsvc.getRefCodesCollection("GL_TRANSFORMATION_TYPE");
                    session.setAttribute(FORM_VECTORS.GL_TRANSFORMATION_TYPE , refcds);
                } else if (FORM_VECTORS.TIME_ZONE_CD.equals(vName)) {
                    ListService lsvc = factory.getListServiceAPI();
                    RefCdDataVector refcds = lsvc.getRefCodesCollection("TIME_ZONE_CD");
                    session.setAttribute(FORM_VECTORS.TIME_ZONE_CD , refcds);
                } else if (FORM_VECTORS.FREIGHT_CHARGE_CD.equals(vName)) {
                    ListService lsvc = factory.getListServiceAPI();
                    RefCdDataVector refcds = lsvc.getRefCodesCollection("FREIGHT_CHARGE_CD");
                    session.setAttribute(FORM_VECTORS.FREIGHT_CHARGE_CD , refcds);
                }  else if (FORM_VECTORS.SHOP_UI_TYPE.equals(vName)) {
                    ListService lsvc = factory.getListServiceAPI();
                    RefCdDataVector refcds = lsvc.getRefCodesCollection("SHOP_UI_TYPE");
                    session.setAttribute(FORM_VECTORS.SHOP_UI_TYPE , refcds);
                }  else if (FORM_VECTORS.DIST_INVENTORY_DISPLAY.equals(vName)) {
                    ListService lsvc = factory.getListServiceAPI();
                    RefCdDataVector refcds = lsvc.getRefCodesCollection("DIST_INVENTORY_DISPLAY");
                    session.setAttribute(FORM_VECTORS.DIST_INVENTORY_DISPLAY , refcds);
                }   else if (FORM_VECTORS.DISTR_PO_TYPE.equals(vName)) {
                    ListService lsvc = factory.getListServiceAPI();
                    RefCdDataVector refcds = lsvc.getRefCodesCollection("DISTR_PO_TYPE");
                    session.setAttribute(FORM_VECTORS.DISTR_PO_TYPE , refcds);
                }
            }
        }
        log.info("initFormVectors => END.");
    }

    public static List<String> toValueList(RefCdDataVector pList) {
        ArrayList<String> valueList = new ArrayList<String>();
        if (pList != null) {
            for (Object o : pList) {
                RefCdData redCd = (RefCdData) o;
                valueList.add(redCd.getValue());
            }
        }
        return valueList;
    }

    public static List getFormVector(HttpServletRequest request, String vName) throws Exception {

        log.info("getFormVector => BEGIN.vName: '" + vName + "'");

        List list = (List) request.getSession().getAttribute(vName);
        if (list == null) {
            initFormVectors(request, vName);
            list = (List) request.getSession().getAttribute(vName);
        }

        log.info("getFormVector => END.");

        return list;
    }

    public static int getRequestedId(HttpServletRequest request) {
        return getRequestedId(request, "id");
    }

    public static int getRequestedId(HttpServletRequest request, String pName) {
        try {
            String idStr = request.getParameter(pName);
            if (Utility.isSet(idStr)) {
                return Integer.parseInt(request.getParameter(pName));
            }
        } catch (NumberFormatException e) {
            log.info("getRequestedId => ERROR: " + e.getMessage());
        }
        return -1;
    }

}
