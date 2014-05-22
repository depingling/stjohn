
package com.cleanwise.view.logic;

import java.util.*;
import java.util.List;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.AccountConfigForm;
import com.cleanwise.view.forms.SiteFieldsConfigForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;

/**
 *  <code>AccountConfigLogic</code>
 *
 *@author     tbesser
 *@created    August 23, 2001
 */
public class AccountConfigLogic {

    /**
     *  <code>init</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void init(HttpServletRequest request,
            ActionForm form)
             throws Exception {
        HttpSession session = request.getSession();

        // Set up the account config option list.
        if (session.getAttribute("Account.config.options") == null) {
            ArrayList opts = new ArrayList();
            opts.add(new FormArrayElement("Catalogs", "Catalogs"));
            session.setAttribute("Account.config.options", opts);
        }

        session.removeAttribute("Account.config.catalog.vector");

        return;
    }


    /**
     *  Find objects related to this account.
     *
     *@param  request
     *@param  form           Description of Parameter
     *@exception  Exception
     */
    public static void search(HttpServletRequest request,
            ActionForm form)
             throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        AccountConfigForm configForm = (AccountConfigForm) form;
        if (configForm == null) {
            configForm = new AccountConfigForm();
            session.setAttribute("ACCOUNT_CONFIG_FORM", configForm);
        }

        APIAccess factory = new APIAccess();
        CatalogInformation catinfo = factory.getCatalogInformationAPI();

        int accountId =
                Integer.parseInt((String) session.getAttribute("Account.id"));

        // An account has 0 or 1 associated account catalogs
        CatalogDataVector catalogs =
                catinfo.getCatalogsCollectionByBusEntity(accountId);
        if (catalogs.size() > 0) {
            CatalogData cat = (CatalogData) catalogs.get(0);
            configForm.setCatalogId(String.valueOf(cat.getCatalogId()));
            configForm.setOldCatalogId(String.valueOf(cat.getCatalogId()));
        }
        else {
            configForm.setCatalogId("0");
            configForm.setOldCatalogId("0");
        }

        int storeid =
                Integer.parseInt((String) session.getAttribute("Store.id"));
        catalogs = catinfo.getAccountCatalogsByStoreId(storeid);

        session.setAttribute("Account.config.catalog.vector", catalogs);

        return;
    }


    /**
     *  Change the catalog associated with the account
     *
     *@param  request
     *@param  form           Description of Parameter
     *@exception  Exception
     */
    public static void save(HttpServletRequest request,
            ActionForm form)
             throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        AccountConfigForm configForm = (AccountConfigForm) form;

        APIAccess factory = new APIAccess();
        CatalogInformation catinfo = factory.getCatalogInformationAPI();

        int newCatId = Integer.parseInt(configForm.getCatalogId());
        int oldCatId = Integer.parseInt(configForm.getOldCatalogId());

        if (newCatId == oldCatId) {
            // nothing to do - hasn't changed
            return;
        }

        int accountId =
                Integer.parseInt((String) session.getAttribute("Account.id"));

        String user = (String) session.getAttribute(Constants.USER_NAME);

        // Change the associated catalog
        Catalog catalogAPI = factory.getCatalogAPI();
        if (oldCatId != 0) {
            catalogAPI.removeCatalogAssoc(oldCatId, accountId, user);
        }
        catalogAPI.addCatalogAssoc(newCatId, accountId, user);

        configForm.setOldCatalogId(configForm.getCatalogId());

        return;
    }


    /**
     *  <code>sort</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sort(HttpServletRequest request,
            ActionForm form)
             throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();

        String sortField = request.getParameter("field");

        CatalogDataVector catalogs =
                (CatalogDataVector) session.getAttribute("Account.config.catalog.vector");
        if (catalogs == null) {
            return;
        }
        DisplayListSort.sort(catalogs, sortField);

        return;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
    public static BusEntityFieldsData
            fetchSiteFields(HttpServletRequest request,
            ActionForm form) throws Exception {

        int accountId =
                Integer.parseInt
                ((String) request.getSession().getAttribute("Account.id"));
        APIAccess factory = new APIAccess();
        PropertyService psvcBean = factory.getPropertyServiceAPI();
        BusEntityFieldsData sfd = psvcBean.fetchSiteFieldsData(accountId);
        SiteFieldsConfigForm sfdf = (SiteFieldsConfigForm) form;
        sfdf.setConfig(sfd);
        sfdf.setBusEntityId(accountId);

        return sfd;
    }


    /**
     *  Description of the Method
     *
     *@param  pRequest       Description of Parameter
     *@param  pForm          Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void
            saveSiteFields(HttpServletRequest pRequest,
            ActionForm pForm)
             throws Exception {

        SiteFieldsConfigForm form = (SiteFieldsConfigForm) pForm;
        BusEntityFieldsData siteFieldsData = form.getConfig();
        int accountId = form.getBusEntityId();
        APIAccess factory = new APIAccess();
        PropertyService psvcBean = factory.getPropertyServiceAPI();
        psvcBean.updateSiteFieldsData(accountId, siteFieldsData);
        return;
    }

}



