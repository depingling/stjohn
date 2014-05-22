package com.cleanwise.view.logic;

import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;
import java.util.Enumeration;
import java.util.Hashtable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.cleanwise.view.utils.FormArrayElement;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.forms.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.ShoppingOptionsDataAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Catalog;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.Account;

/**
 *
 * @author Alexander Chikin
 * Date: 15.08.2006
 * Time: 13:43:01
 *
 */
public class StoreAccountConfigLogic {

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
        StoreAccountConfigForm configForm = (StoreAccountConfigForm) form;
        if (configForm == null) {
            configForm = new StoreAccountConfigForm();
            session.setAttribute("STORE_ACCOUNT_CONFIG_FORM", configForm);
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
        StoreAccountConfigForm configForm = (StoreAccountConfigForm) form;

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
            catalogAPI.removeCatalogAccountAssoc(oldCatId, accountId, user);
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
       HttpSession session = request.getSession();
        int accountId =
                Integer.parseInt
                ((String) request.getSession().getAttribute("Account.id"));
        APIAccess factory = new APIAccess();
        PropertyService psvcBean = factory.getPropertyServiceAPI();
        BusEntityFieldsData sfd = psvcBean.fetchSiteFieldsData(accountId);
        StoreSiteFieldsConfigForm sfdf = (StoreSiteFieldsConfigForm) form;
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
        HttpSession session = pRequest.getSession();

        StoreSiteFieldsConfigForm form = (StoreSiteFieldsConfigForm) pForm;
        BusEntityFieldsData siteFieldsData = form.getConfig();
        resetCheckBoxAndSet(pRequest,siteFieldsData);

        int accountId = form.getBusEntityId();
        APIAccess factory = new APIAccess();
        PropertyService psvcBean = factory.getPropertyServiceAPI();
        psvcBean.updateSiteFieldsData(accountId, siteFieldsData);

        return;
    }



    private static void resetCheckBoxAndSet(HttpServletRequest request,BusEntityFieldsData siteFieldsData) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {


       siteFieldsData.reset_cb_data();
       Enumeration paramsEnum = request.getParameterNames();
       Hashtable paramsTable=new Hashtable();
       while(paramsEnum.hasMoreElements())
       {
           String param=(String) paramsEnum.nextElement();
           paramsTable.put(param,request.getParameter(param));
       }
       if(siteFieldsData!=null){
       ArrayList checkBoxNames = siteFieldsData.getCheckBoxNames();
       Class resetClass=BusEntityFieldsData.class;
       Class[] argTypes={Boolean.TYPE};
       Object[] theData={Boolean.TRUE};
       for(int i=0;i<checkBoxNames.size();i++){
             if(paramsTable.containsKey(checkBoxNames.get(i))){
                 String nMethod= "setF"+((String) checkBoxNames.get(i)).substring(8);
                 Method method=resetClass.getMethod(nMethod,argTypes);
                 method.invoke(siteFieldsData,theData);
             }
       }
       }
    }

 }


