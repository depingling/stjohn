package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Manufacturer;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.StoreEnterpriseMfgForm;
import com.cleanwise.view.forms.StoreEnterpriseMgrForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.SelectableObjects;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Comparator;
import java.io.StringWriter;
import java.io.PrintWriter;


public class StoreEnterpriseMgrLogic {

    private static final String className = "StoreEnterpriseMgrLogic";

    private static final String STORE_ENTERPRISE_MGR_FORM = "STORE_ENTERPRISE_MGR_FORM";
    private static final String STORE_ENTERPRISE_MFG_FORM = "STORE_ENTERPRISE_MFG_FORM";

    static final Comparator ENTERPRISE_MANUF_ASSOC_COMPARE = new Comparator() {
        public int compare(Object o1, Object o2) {

            int id11 = (Integer) ((PairView) o1).getObject1();
            int id12 = (Integer) ((PairView) o1).getObject2();
            int id21 = (Integer) ((PairView) o2).getObject1();
            int id22 = (Integer) ((PairView) o2).getObject2();

            String key1 = id11 + "_" + id12;
            String key2 = id21 + "_" + id22;

            return Utility.compareToIgnoreCase(key1, key2);
        }
    };

    public static ActionErrors init(HttpServletRequest request, StoreEnterpriseMgrForm pForm) throws Exception {

        ActionErrors ae;
        HttpSession session = request.getSession();

        pForm = new StoreEnterpriseMgrForm();
        session.setAttribute(STORE_ENTERPRISE_MGR_FORM, pForm);

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = APIAccess.getAPIAccess();
        Store storeEjb = factory.getStoreAPI();

        CleanwiseUser clwUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        IdVector userStoreIds = Utility.toIdVector(clwUser.getStores());

        IdVector eStoreIds = storeEjb.getStoreIdsByType(RefCodeNames.STORE_TYPE_CD.ENTERPRISE);
        if (eStoreIds.isEmpty()) {
            ae.add("error", new ActionError("error.simpleGericError", "Enterprise Store not found"));
        }
        if (eStoreIds.size() > 1) {
            ae.add("error", new ActionError("error.simpleGericError", "Multiple Enterprise Store"));
        }
        if (!userStoreIds.contains(eStoreIds.get(0))) {
            ae.add("error", new ActionError("error.simpleGericError", "Unauthorized access"));
        }

        pForm.setEnterpriseStore(storeEjb.getStore((Integer) eStoreIds.get(0)));
        pForm.setManagedStore(clwUser.getUserStore());

        session.setAttribute(STORE_ENTERPRISE_MGR_FORM, pForm);

        return ae;
    }

    private static ActionErrors checkRequest(HttpServletRequest request, ActionForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = APIAccess.getAPIAccess();

        if (factory == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No Ejb Access"));
            return ae;
        }

        if (form == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "Form not initialized"));
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (appUser == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No user info"));
            return ae;
        }

        return ae;

    }


    public static ActionErrors init(HttpServletRequest request, StoreEnterpriseMfgForm pForm) throws Exception {

        ActionErrors ae;
        HttpSession session = request.getSession();

        pForm = new StoreEnterpriseMfgForm();
        session.setAttribute(STORE_ENTERPRISE_MFG_FORM, pForm);

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = APIAccess.getAPIAccess();
        Manufacturer manufEjb = factory.getManufacturerAPI();

        StoreEnterpriseMgrForm mForm = (StoreEnterpriseMgrForm) session.getAttribute(STORE_ENTERPRISE_MGR_FORM);
        if (mForm == null) {
            ae.add("error", new ActionError("error.simpleGenericError", STORE_ENTERPRISE_MGR_FORM + " not found"));
            return ae;
        }

        IdVector enterpriseMfgIds = new IdVector();
        HashMap<Integer, String> enterpriseMfgNames = new HashMap<Integer, String>();

        IdVector managedMfgIds = new IdVector();
        HashMap<Integer, String> managedMfgNames = new HashMap<Integer, String>();

        readStoreManufacturers(manufEjb, mForm.getEnterpriseStore().getStoreId(), enterpriseMfgNames, enterpriseMfgIds);
        readStoreManufacturers(manufEjb, mForm.getManagedStore().getStoreId(), managedMfgNames, managedMfgIds);

        pForm.setEnterpriseMfgIds(enterpriseMfgIds);
        pForm.setManagedMfgIds(managedMfgIds);
        pForm.setEnterpriseMfgNames(enterpriseMfgNames);
        pForm.setManagedMfgNames(managedMfgNames);

        PairViewVector linkedMfgIds = manufEjb.getEnterpriseAssoc(mForm.getEnterpriseStore().getStoreId(), mForm.getManagedStore().getStoreId());
        pForm.setLinkedMfgIds(linkedMfgIds);

        session.setAttribute(STORE_ENTERPRISE_MFG_FORM, pForm);

        return ae;
    }

    private static void readStoreManufacturers(Manufacturer manufEjb, int storeId, HashMap<Integer, String> nameMap, List<Integer> ids) throws Exception {


        BusEntitySearchCriteria besc = new BusEntitySearchCriteria();
        besc.setStoreBusEntityIds(Utility.toIdVector(storeId));

        BusEntityDataVector manuf = manufEjb.getManufacturerBusEntitiesByCriteria(besc);
        DisplayListSort.sort(manuf, "short_desc");

        for (Object oManuf : manuf) {
            BusEntityData bed = (BusEntityData) oManuf;
            Integer id = bed.getBusEntityId();
            String name = bed.getShortDesc();
            ids.add(id);
            nameMap.put(id, name);
        }

    }

    public static ActionErrors addEnterpriseMfgAssoc(HttpServletRequest request, StoreEnterpriseMfgForm pForm) throws Exception {

        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        int eMfgId = 0;
        int mMfgId = 0;

        try {
            eMfgId = Integer.parseInt(pForm.getSelectedEnterpriseMfg());
        } catch (NumberFormatException e) {
            error(e.getMessage(), e);
        }

        try {
            mMfgId = Integer.parseInt(pForm.getSelectedManagedMfg());
        } catch (NumberFormatException e) {
            error(e.getMessage(), e);
        }


        if (eMfgId < 1) {
            ae.add("error", new ActionError("variable.empty.error", "Enterprise Mfg."));
        }

        if (mMfgId < 1) {
            ae.add("error", new ActionError("variable.empty.error", "Store Mfg."));
        }

        if (ae.size() > 0) {
            return ae;
        }

        PairView newMfgPairId = new PairView(mMfgId, eMfgId);

        PairViewVector mfgLinks = pForm.getLinkedMfgIds();
        if (enterpriseLinkExist(mfgLinks, newMfgPairId)) {
            ae.add("error", new ActionError("error.simpleGenericError", "Link exists"));
            return ae;

        } else {
            mfgLinks.add(newMfgPairId);
            pForm.setLinkedMfgIds(mfgLinks);
        }

        session.setAttribute(STORE_ENTERPRISE_MFG_FORM, pForm);

        return ae;

    }

    public static ActionErrors dropEnterpriseMfgAssoc(HttpServletRequest request, StoreEnterpriseMfgForm pForm) throws Exception {

        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        PairViewVector selectedMfgPairs;
        String[] selectedStrLinks = pForm.getSelectedMfgLinks();
        try {
            selectedMfgPairs = parseIdToPairViewVector(selectedStrLinks);
        } catch (Exception e) {
            ae.add("error", new ActionError("error.simpleGenericError", "Wrong association data"));
            return ae;
        }

        PairViewVector linkedMfgIds = pForm.getLinkedMfgIds();

        SelectableObjects selectableObj = new SelectableObjects(selectedMfgPairs, linkedMfgIds, ENTERPRISE_MANUF_ASSOC_COMPARE);
        PairViewVector newLinkedList = new PairViewVector();
        newLinkedList.addAll(selectableObj.getValues());
        newLinkedList.removeAll(selectableObj.getCurrentlySelected());

        pForm.setLinkedMfgIds(newLinkedList);
        pForm.setSelectedMfgLinks(new String[0]);

        session.setAttribute(STORE_ENTERPRISE_MFG_FORM, pForm);

        return ae;

    }

    public static ActionErrors updateEnterpriseMfgAssoc(HttpServletRequest request, StoreEnterpriseMfgForm pForm) throws Exception {

        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        StoreEnterpriseMgrForm mForm = (StoreEnterpriseMgrForm) session.getAttribute(STORE_ENTERPRISE_MGR_FORM);
        if (mForm == null) {
            ae.add("error", new ActionError("error.simpleGenericError", STORE_ENTERPRISE_MGR_FORM + " not found"));
            return ae;
        }

        CleanwiseUser clwUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        APIAccess factory = APIAccess.getAPIAccess();
        Manufacturer manufEjb = factory.getManufacturerAPI();

        PairViewVector selectedMfgIds = pForm.getLinkedMfgIds();
        PairViewVector linkedMfgIds = manufEjb.getEnterpriseAssoc(mForm.getEnterpriseStore().getStoreId(), mForm.getManagedStore().getStoreId());

        PairViewVector aList = new PairViewVector();
        PairViewVector rList = new PairViewVector();

        for (Object selectedMfgId : selectedMfgIds) {
            if (!enterpriseLinkExist(linkedMfgIds, (PairView) selectedMfgId)) {
                aList.add(selectedMfgId);
            }
        }

        for (Object linkedMfgId : linkedMfgIds) {
            if (!enterpriseLinkExist(selectedMfgIds, (PairView) linkedMfgId)) {
                rList.add(linkedMfgId);
            }
        }

        manufEjb.addEnterpriseManufAssoc(aList, clwUser.getUserName());
        manufEjb.removeEnterpriseManufAssoc(rList);

        PairViewVector newLinkedList = manufEjb.getEnterpriseAssoc(mForm.getEnterpriseStore().getStoreId(), mForm.getManagedStore().getStoreId());
        pForm.setLinkedMfgIds(newLinkedList);
        pForm.setSelectedMfgLinks(new String[0]);

        session.setAttribute(STORE_ENTERPRISE_MFG_FORM, pForm);

        return ae;

    }

    private static PairViewVector parseIdToPairViewVector(String[] selectedStrLinks) {
        PairViewVector result = new PairViewVector();
        for (String sLink : selectedStrLinks) {
            IdVector pVector = Utility.parseIdStringToVector(sLink, "_");
            result.add(new PairView(pVector.get(0), pVector.get(1)));
        }
        return result;
    }

    private static boolean enterpriseLinkExist(PairViewVector pMfgLinkCollection, PairView pMfgPair) {
        for (Object oMfgPair : pMfgLinkCollection) {
            if (ENTERPRISE_MANUF_ASSOC_COMPARE.compare(oMfgPair, pMfgPair) == 0) {
                return true;
            }
        }
        return false;
    }


    /**
     * Error logging
     *
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
     */
    private static void error(String message, Exception e) {

        String errorMessage;
        StringWriter wr = new StringWriter();
        PrintWriter prW = new PrintWriter(wr);
        e.printStackTrace(prW);
        errorMessage = wr.getBuffer().toString();

    }
}
