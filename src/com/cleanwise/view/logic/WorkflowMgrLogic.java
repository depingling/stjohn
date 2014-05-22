
package com.cleanwise.view.logic;

import java.math.BigDecimal;
import java.util.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.CurrencyFormat;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.forms.WorkflowMgrSearchForm;
import com.cleanwise.view.forms.WorkflowDetailForm;
import com.cleanwise.view.forms.WorkflowSitesForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import org.apache.log4j.*;

/**
 * <code>WorkflowMgrLogic</code> implements the logic needed to manipulate
 *  account workflow records.
 *
 * @author     durval
 * @created    December 10, 2001
 */
public class WorkflowMgrLogic {
    private static final Logger log = Logger.getLogger(WorkflowMgrLogic.class);
    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pAccountId     Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void setupAccountInfo(HttpServletRequest request,
                                        int pAccountId)
    throws Exception {
        request.getSession().setAttribute
        ("Account.id", String.valueOf(pAccountId));
        request.getSession().setAttribute("Account.name", "---");
        request.getSession().setAttribute("Store.id", "0");
        request.getSession().setAttribute("Store.name", "---");
        try
        {
            // Get the Account Name
            APIAccess factory = new APIAccess();
            Account acctBean = factory.getAccountAPI();
            AccountData ad = acctBean.getAccount(pAccountId, 0);
            request.getSession().setAttribute
            ("Account.name", ad.getBusEntity().getShortDesc());

            // Get the Store Name and Id.
            request.getSession().setAttribute
            ("Store.id",
             String.valueOf(ad.getStoreAssoc().getBusEntity2Id())
            );

            Store sBean = factory.getStoreAPI();
            StoreData sd = sBean.getStore
                           (ad.getStoreAssoc().getBusEntity2Id());
            request.getSession().setAttribute
            ("Store.name", sd.getBusEntity().getShortDesc());
        }
        catch (Exception e)
        {
        }
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void initFormVectors(HttpServletRequest request)
    throws Exception {

        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        ListService lsvc = factory.getListServiceAPI();

        // Set up the workflow type list.
        if (null == session.getAttribute("Workflow.type.vector"))
        {
            RefCdDataVector v =
                lsvc.getRefCodesCollection("WORKFLOW_TYPE_CD");
            session.setAttribute("Workflow.type.vector", v);
        }

        // Set up the status list.
        if (null == session.getAttribute("Workflow.status.vector"))
        {
            RefCdDataVector v =
                lsvc.getRefCodesCollection("WORKFLOW_STATUS_CD");
            session.setAttribute("Workflow.status.vector", v);
        }

        //if (null != session.getAttribute("WORKFLOW_DETAIL_FORM"))
        {
            // Reset the detail information.
            session.setAttribute("WORKFLOW_DETAIL_FORM",
                                 new WorkflowDetailForm());
        }
        session.removeAttribute("Workflow.rulesjoin.vector");
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void listAll(HttpServletRequest request,
                               ActionForm form)
    throws Exception {

        APIAccess factory = new APIAccess();
        Workflow wkfl = factory.getWorkflowAPI();
        HttpSession session = request.getSession();
        int aid = 0;

        String s = (String) session.getAttribute("Account.id");
        if (null != s)
        {
            aid = Integer.parseInt(s);

            session.setAttribute
            ("Workflow.found.vector",
             wkfl.getWorkflowCollectionByEntity(aid)
            );
        }
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void locateListAll(HttpServletRequest request,
                                     ActionForm form)
    throws Exception {

        HttpSession session = request.getSession();
        WorkflowMgrSearchForm sForm = (WorkflowMgrSearchForm) form;

        String storeIdS = request.getParameter("storeid");
        int storeId = 0;
        if (null == storeIdS ||  "".equals(storeIdS))
        {
            if ("store".equals(sForm.getSearchBusEntityType())) {
                storeIdS = sForm.getBusEntityIdS();
            }
        }
        else
        {
            sForm.setSearchBusEntityType("store");
            sForm.setBusEntityIdS(storeIdS);
        }
        if (null != storeIdS && ! "".equals(storeIdS))
        {
            try
            {
                storeId = Integer.parseInt(storeIdS);
            }
            catch(Exception e)
            {
                log.info("can't parse storeIdS (" + storeIdS + ")");
                storeId = 0;
            }
        }

        String accountIdS = request.getParameter("accountid");
        int accountId = 0;
        if (null == accountIdS ||  "".equals(accountIdS))
        {
            if ("account".equals(sForm.getSearchBusEntityType())) {
                accountIdS = sForm.getBusEntityIdS();
            }
        }
        else
        {
            sForm.setSearchBusEntityType("account");
            sForm.setBusEntityIdS(accountIdS);
        }
        if (null != accountIdS && ! "".equals(accountIdS))
        {
            try
            {
                accountId = Integer.parseInt(accountIdS);
            }
            catch(Exception e)
            {
                log.info("can't parse accountIdS (" + accountIdS + ")");
                accountId = 0;
            }
        }

        int busEntityId = storeId;
        if (0 == busEntityId)
        {
            busEntityId = accountId;
        }

        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }
        Workflow wkfl = factory.getWorkflowAPI();

        sForm.setWorkflowDescList(wkfl.getWorkflowDescCollectionByEntity(busEntityId, false));

    }



    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void search(HttpServletRequest request,
                              ActionForm form)
    throws Exception {

        WorkflowMgrSearchForm sForm = (WorkflowMgrSearchForm) form;
        APIAccess factory = new APIAccess();
        Workflow wBean = factory.getWorkflowAPI();
        HttpSession session = request.getSession();
        int aid = 0;
        String s = (String) session.getAttribute("Account.id");
        if (null != s)
        {
            aid = Integer.parseInt(s);
        }

        String fieldValue = sForm.getSearchField();
        String fieldSearchType = sForm.getSearchType();
        WorkflowDataVector dv = new WorkflowDataVector();

        if (fieldSearchType.equals("id"))
        {
            Integer id = new Integer(fieldValue);
            WorkflowData w = wBean.getWorkflow(id.intValue());
            if (null != w)
            {
                dv.add(w);
            }
        }
        else if (fieldSearchType.equals("nameBegins"))
        {
            dv = wBean.getWorkflowCollectionByName(fieldValue, aid,
                                                   Workflow.BEGINS_WITH_IGNORE_CASE);
        }
        else if (fieldSearchType.equals("nameContains"))
        {
            dv = wBean.getWorkflowCollectionByName(fieldValue, aid,
                                                   Workflow.CONTAINS_IGNORE_CASE);
        }
        else
        {
            dv = wBean.getWorkflowCollectionByEntity(aid);
        }
        session.setAttribute("Workflow.found.vector", dv);

    }



    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void locateByBusEntity(HttpServletRequest request,
                                         ActionForm form)
    throws Exception {

        HttpSession session = request.getSession();
        WorkflowMgrSearchForm sForm = (WorkflowMgrSearchForm) form;

        String storeIdS = request.getParameter("storeid");
        int storeId = 0;
        if (null == storeIdS ||  "".equals(storeIdS))
        {
            if("store".equals(sForm.getSearchBusEntityType())) {
                storeIdS = sForm.getBusEntityIdS();
            }
        }
        else
        {
            sForm.setSearchBusEntityType("store");
            sForm.setBusEntityIdS(storeIdS);
        }
        if (null != storeIdS && ! "".equals(storeIdS))
        {
            try
            {
                storeId = Integer.parseInt(storeIdS);
            }
            catch(Exception e)
            {
                log.info("can't parse storeIdS (" + storeIdS + ")");
                storeId = 0;
            }
        }

        String accountIdS = request.getParameter("accountid");
        int accountId = 0;
        if (null == accountIdS ||  "".equals(accountIdS))
        {
            if("account".equals(sForm.getSearchBusEntityType())) {
                accountIdS = sForm.getBusEntityIdS();
            }
        }
        else
        {
            sForm.setSearchBusEntityType("account");
            sForm.setBusEntityIdS(accountIdS);
        }
        if (null != accountIdS && ! "".equals(accountIdS))
        {
            try
            {
                accountId = Integer.parseInt(accountIdS);
            }
            catch(Exception e)
            {
                log.info("can't parse accountIdS (" + accountIdS + ")");
                accountId = 0;
            }
        }


        int busEntityId = storeId;
        if (0 == busEntityId)
        {
            busEntityId = accountId;
        }

        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }
        Workflow wBean = factory.getWorkflowAPI();


        String fieldValue = sForm.getSearchField();
        String fieldSearchType = sForm.getSearchType();
        WorkflowDescDataVector dv = new WorkflowDescDataVector();

        if (fieldSearchType.equals("id"))
        {
            Integer id = new Integer(fieldValue);
            WorkflowDescData w = wBean.getWorkflowDesc(id.intValue());
            if (null != w)
            {
                if ( 0 != busEntityId )
                {
                    if (w.getWorkflow().getBusEntityId() == busEntityId)
                    {
                        dv.add(w);
                    }
                }
                else
                {
                    dv.add(w);
                }
            }
        }
        else if (fieldSearchType.equals("nameBegins"))
        {
            dv = wBean.getWorkflowDescCollectionByName(fieldValue, busEntityId,
                    Workflow.BEGINS_WITH_IGNORE_CASE, false);
        }
        else if (fieldSearchType.equals("nameContains"))
        {
            dv = wBean.getWorkflowDescCollectionByName(fieldValue, busEntityId,
                    Workflow.CONTAINS_IGNORE_CASE, false);
        }
        else
        {
            dv = wBean.getWorkflowDescCollectionByEntity(busEntityId, false);
        }
        sForm.setWorkflowDescList(dv);
    }



    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void init(HttpServletRequest request,
                            ActionForm form)
    throws Exception {

        initFormVectors(request);
        HttpSession session = request.getSession();
        int storeId = getStoreId(request);
        GroupSearchCriteriaView gcrit = GroupSearchCriteriaView.createValue();
        gcrit.setStoreId(storeId);
        gcrit.setGroupStatus(RefCodeNames.GROUP_STATUS_CD.ACTIVE);
        gcrit.setGroupType(RefCodeNames.GROUP_TYPE_CD.USER);
        APIAccess factory = new APIAccess();
        Group groupEjb = factory.getGroupAPI();
        GroupDataVector gDV = groupEjb.getGroups(gcrit,Group.NAME_CONTAINS_IGNORE_CASE,Group.ORDER_BY_NAME);
        session.setAttribute("UserGroups",gDV);
        return;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void initLocate(HttpServletRequest request,
                                  ActionForm form)
    throws Exception {

        WorkflowMgrSearchForm sForm = (WorkflowMgrSearchForm)form;

        sForm.setSearchBusEntityType("store");
        sForm.setBusEntityIdS("");

        String storeIdS = request.getParameter("storeid");
        if (null != storeIdS && ! "".equals(storeIdS))
        {
            sForm.setSearchBusEntityType("store");
            sForm.setBusEntityIdS(storeIdS);
        }

        String accountIdS = request.getParameter("accountid");
        if (null != accountIdS && ! "".equals(accountIdS))
        {
            sForm.setSearchBusEntityType("account");
            sForm.setBusEntityIdS(accountIdS);
        }

        return;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void sortWorkflows(HttpServletRequest request,
                                     ActionForm form)
    throws Exception {
        HttpSession session = request.getSession();
        WorkflowDataVector v =
            (WorkflowDataVector) session.getAttribute
            ("Workflow.found.vector");
        if (v == null)
        {
            return;
        }
        String sortField = request.getParameter("sortField");
        DisplayListSort.sort(v, sortField);

        return;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void sortSites(HttpServletRequest request,
                                 ActionForm form)
    throws Exception {
        HttpSession session = request.getSession();
        SiteDataVector v =
            (SiteDataVector) session.getAttribute
            ("Workflow.sites.vector");
        if (v == null)
        {
            return;
        }
        String sortField = request.getParameter("sortField");
        DisplayListSort.sort(v, sortField);

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
    public static ActionErrors delete(HttpServletRequest request,
                                      ActionForm pform)
    throws Exception {

        ActionErrors ae = new ActionErrors();
        WorkflowDetailForm form = (WorkflowDetailForm) pform;
        String s = form.getId();
        if (null == s)
        {
            ae.add("workflow",
                   new ActionError
                   ("Delete workflow Error: the workflow id was not specified."));
        }
        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        Workflow wkflBean = factory.getWorkflowAPI();
        int wid = Integer.parseInt(s);

        try
        {
            IdVector currentSites = wkflBean.getWorkflowSiteIds(wid);
            if (currentSites.size() > 0)
            {
                ae.add("workflow",
                       new ActionError("This Workflow cannot be deleted" +
                                       "since there are still some sites using it."));
                return ae;
            }

            wkflBean.deleteWorkflow(wid);
        }
        catch (Exception e)
        {
            ae.add("workflow",
                   new ActionError("Workflow delete error: " + e));
        }

        return ae;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pform          Description of Parameter
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
    public static ActionErrors linkSites(HttpServletRequest request,
                                         ActionForm pform)
    throws Exception {
        ActionErrors ae = new ActionErrors();
        WorkflowSitesForm form = (WorkflowSitesForm) pform;
        String s = form.getId();
        if (null == s)
        {
            ae.add("workflow",
                   new ActionError
                   ("Link Sites Error: the workflow id was not specified."));
        }

        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        Workflow wkflBean = factory.getWorkflowAPI();
        Site siteBean = factory.getSiteAPI();
        int wid = Integer.parseInt(s);

        WorkflowData w = null;
        try
        {
            w = wkflBean.getWorkflow(wid);
            form.setBusEntityId(w.getBusEntityId());
            form.setName(w.getShortDesc());
            form.setStatusCd(w.getWorkflowStatusCd());
            form.setId(String.valueOf(w.getWorkflowId()));
            String[] displayids = form.getDisplayIds();
            // Foreach id displayed. Assign or unasign the
            // site depending on whether the id is in
            // the select list.
            for (int i1 = 0; i1 < displayids.length; i1++)
            {
                String cursiteid = displayids[i1];
                String[] selectids = form.getSelectIds();
                boolean addsite = false;
                for (int i = 0; i < selectids.length; i++)
                {
                    if (cursiteid.equals(selectids[i]))
                    {
                        addsite = true;
                        break;
                    }
                }
                if (addsite)
                {
                    SessionTool lses = new SessionTool(request);

                    wkflBean.assignWorkflow
                    (wid, Integer.parseInt(cursiteid),
                     lses.getLoginName());
                }
                else
                {
                    wkflBean.unassignWorkflow
                    (wid, Integer.parseInt(cursiteid));
                }
            }
        }
        catch (Exception e)
        {
            ae.add("workflow",
                   new ActionError("Workflow was not found." +
                                   " Error: " + e));
        }

        return ae;
    }
    private static void resetSiteList
    (HttpServletRequest request) {
        request.getSession().removeAttribute("Workflow.sites.vector");
    }

    /**
     * Describe <code>assignAllSites</code> method here.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param pform an <code>ActionForm</code> value
     * @return an <code>ActionErrors</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors assignAllSites
    (HttpServletRequest request,
     ActionForm pform)
    throws Exception {
        ActionErrors ae = new ActionErrors();
        WorkflowSitesForm form = (WorkflowSitesForm) pform;
        String s = form.getId();
        if (null == s)
        {
            ae.add("workflow",
                   new ActionError
                   ("Assign All Sites Error: " +
                    "The workflow id was not specified."));
            return ae;
        }

        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        Workflow wkflBean = factory.getWorkflowAPI();
        Site siteBean = factory.getSiteAPI();
        int wid = Integer.parseInt(s);

        WorkflowData w = null;
        try
        {
            w = wkflBean.getWorkflow(wid);
            // Assign the workflow to all sites in this account.
            int acctid = w.getBusEntityId();
            SessionTool lses = new SessionTool(request);
            wkflBean.assignWorkflowToAccountSites
            (wid, acctid, lses.getLoginName());
            resetSiteList(request);
        }
        catch (Exception e)
        {
            ae.add("workflow",
                   new ActionError("Workflow Error: " + e));
        }

        return ae;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pform          Description of Parameter
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
    public static ActionErrors initSites(HttpServletRequest request,
                                         ActionForm pform)
    throws Exception {

        ActionErrors ae = new ActionErrors();
        WorkflowSitesForm form = new WorkflowSitesForm();
        String s = request.getParameter("workflowId");
        if (null == s)
        {
            ae.add("workflow",
                   new ActionError
                   ("Init Sites Error: the workflow id was not specified."));
        }

        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        Workflow wkflBean = factory.getWorkflowAPI();
        Site siteBean = factory.getSiteAPI();
        int wid = Integer.parseInt(s);

        WorkflowData w = null;
        try
        {
            w = wkflBean.getWorkflow(wid);
            form.setBusEntityId(w.getBusEntityId());
            form.setName(w.getShortDesc());
            form.setStatusCd(w.getWorkflowStatusCd());
            form.setId(String.valueOf(w.getWorkflowId()));
            IdVector sites = wkflBean.getWorkflowSiteIds(wid);
            String[] currsites = new String[sites.size()];
            for (int idx = 0; idx < currsites.length; idx++)
            {
                Integer id = (Integer) sites.get(idx);
                currsites[idx] = new String(id.toString());
            }
            form.setSelectIds(currsites);
            session.setAttribute("WORKFLOW_SITES_FORM", form);
            request.setAttribute("WORKFLOW_SITES_FORM", form);
        }
        catch (Exception e)
        {
            ae.add("workflow",
                   new ActionError("Workflow was not found." +
                                   " Error: " + e));
        }

        return ae;
    }


    /**
     * Description of the Method
     *
     * @param  request        Description of Parameter
     * @param pForm an <code>ActionForm</code> value
     * @return                Description of the Returned Value
     * @exception  Exception  Description of Exception
     */
    public static ActionErrors fetchCurrentSites(HttpServletRequest request,
            ActionForm pForm)
    throws Exception {

        ActionErrors ae = new ActionErrors();
        String s = request.getParameter("workflowId");
        if (null == s)
        {
            ae.add("workflow",
                   new ActionError
                   ("List Sites Error: the workflow id was not specified."));
            return ae;
        }

        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        int wid = Integer.parseInt(s);

        try
        {
            WorkflowSitesForm form = (WorkflowSitesForm)pForm;
            SiteDataVector sdv = siteBean.fetchSitesForWorkflow(wid);
            session.setAttribute("Workflow.sites.vector", sdv);
            String[] currsites = new String[sdv.size()];
            int idx = 0;
            for (idx = 0; idx < sdv.size(); idx++)
            {
                SiteData sd = (SiteData)sdv.get(idx);
                int sid = sd.getBusEntity().getBusEntityId();
                currsites[idx] = String.valueOf(sid);
            }
            form.setSelectIds(currsites);

        }
        catch (Exception e)
        {
            ae.add("workflow",
                   new ActionError("Problem getting the sites for Workflow:" +
                                   wid + " Error: " + e));
        }

        return ae;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pform          Description of Parameter
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
    public static ActionErrors searchForSites
    (HttpServletRequest request,
     ActionForm pform)
    throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        Workflow wkflBean = factory.getWorkflowAPI();
        String wid = "-1";

        String action = request.getParameter("action");



        try
        {
            WorkflowSitesForm form = (WorkflowSitesForm) pform;
            wid = form.getId();
            SiteViewVector dv = new SiteViewVector();
            SiteDataVector sdv = new SiteDataVector();
            int acctid = form.getBusEntityId();

            String searchType = form.getSearchType();
            String fieldValue = form.getSearchField();
            fieldValue.trim();
            boolean multiFieldSearch = true;

            if(action.equals("View All"))
            {
                sdv = siteBean.getAllSites(acctid, Site.ORDER_BY_ID, SessionTool.getCategoryToCostCenterViewByAcc(session, acctid));

            }
            else
            {
                QueryRequest qr = new QueryRequest();

                if (searchType.equals("id") &&
                        fieldValue.length() > 0 )
                {


                    Integer id = new Integer(fieldValue);
                    qr.filterBySiteId(id.intValue());
                    dv = siteBean.getSiteCollection(qr);
                }
                else
                {

                    qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);
                    String f = "";
                    if (searchType.equals("nameBegins") &&
                            fieldValue.length() > 0 )
                    {
                        qr.filterBySiteName(fieldValue,
                                            QueryRequest.BEGINS_IGNORE_CASE);
                    }
                    else if (searchType.equals("nameContains") &&
                             fieldValue.length() > 0)
                    {
                        qr.filterBySiteName(fieldValue,
                                            QueryRequest.CONTAINS_IGNORE_CASE);
                    }

                    f = form.getCity().trim();
                    if (form.getCity().trim().length() > 0)
                    {
                        qr.filterByCity(f, QueryRequest.BEGINS_IGNORE_CASE);
                    }
                    f = form.getState().trim();
                    if (form.getState().trim().length() > 0)
                    {
                        qr.filterByState(f, QueryRequest.BEGINS_IGNORE_CASE);
                    }
                    f = form.getPostalCode().trim();
                    if (form.getPostalCode().trim().length() > 0)
                    {
                        qr.filterByZip(f, QueryRequest.BEGINS_IGNORE_CASE);
                    }
                    f = form.getCounty().trim();
                    if (form.getCounty().trim().length() > 0)
                    {
                        qr.filterByCounty(f, QueryRequest.BEGINS_IGNORE_CASE);
                    }
                }

                qr.filterByAccountId(acctid);
                dv = siteBean.getSiteCollection(qr);

                for(int i=0; i<dv.size(); i++)
                {
                    SiteView sv = (SiteView)dv.get(i);
                    SiteData sd = siteBean.getSite(sv.getId(), sv.getAccountId(), false,  SessionTool.getCategoryToCostCenterView(session, sv.getId()));
                    sdv.add(sd);
                }

            }

            session.setAttribute("Workflow.sites.vector", sdv);

            IdVector sites = wkflBean.getWorkflowSiteIds(form.getIntId());

            String[] currsites = new String[sites.size()];
            form.setSelectIds(currsites);

            int idx = 0;

            for (idx = 0; idx < sites.size(); idx++)
            {
                Integer id = (Integer) sites.get(idx);
                currsites[idx] = id.toString();

            }

            form.setSelectIds(currsites);

        }
        catch (Exception e)
        {
            ae.add("workflow",
                   new ActionError("Problem getting the sites for Workflow:" +
                                   wid + " Error: " + e));
        }

        return ae;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pForm          Description of Parameter
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
    public static ActionErrors update(HttpServletRequest request,
                                      ActionForm pForm)
    throws Exception {

        WorkflowDetailForm form = (WorkflowDetailForm) pForm;
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        Workflow wkflBean = factory.getWorkflowAPI();

        WorkflowData w = form.getOriginalData();
        w.setWorkflowId(form.getIntId());
        w.setShortDesc(form.getName());
        w.setWorkflowTypeCd(form.getTypeCd());
        w.setWorkflowStatusCd(form.getStatusCd());
        w.setBusEntityId(form.getBusEntityId());
        SessionTool lses = new SessionTool(request);
        w.setModBy(lses.getLoginName());
        Date date = new Date(System.currentTimeMillis());
        w.setModDate(date);
        if (form.getId().equals("0"))
        {
            w.setAddBy(lses.getLoginName());
            w.setAddDate(date);
        }

        if (w.getWorkflowId() == 0)
        {
            try
            {
                w = wkflBean.createWorkflow(w);
                form.setOriginalData(w);
                form.setId(String.valueOf(w.getWorkflowId()));
            }
            catch (Exception e)
            {
                ae.add("workflow",
                       new ActionError("Workflow could not be created: " + e));
            }

        }
        else
        {
            try
            {
                wkflBean.updateWorkflow(w);
            }
            catch (Exception e)
            {
                ae.add("workflow",
                       new ActionError("Workflow could not be updated: " + e));
            }
        }
        return ae;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pForm          Description of Parameter
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
    public static ActionErrors detail(HttpServletRequest request,
                                      ActionForm pForm)
    throws Exception {

        // Make sure that the needed session variables are in place.
        init(request, pForm);

        WorkflowDetailForm form = new WorkflowDetailForm();
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        Workflow wkflBean = factory.getWorkflowAPI();
        String s = request.getParameter("searchField");
        if (s == null)
        {
            ae.add("workflow",
                   new ActionError
                   ("Error: the workflow id was not specified."));
            return ae;
        }
        int wid = Integer.parseInt(s);

        WorkflowData w = null;
        try
        {
            w = wkflBean.getWorkflow(wid);
            form.setOriginalData(w);
            form.setBusEntityId(w.getBusEntityId());
            // Set the needed account info.
            setupAccountInfo(request, w.getBusEntityId());

            form.setName(w.getShortDesc());
            form.setTypeCd(w.getWorkflowTypeCd());
            form.setStatusCd(w.getWorkflowStatusCd());
            form.setId(String.valueOf(w.getWorkflowId()));
            session.setAttribute("WORKFLOW_DETAIL_FORM", form);
            request.setAttribute("WORKFLOW_DETAIL_FORM", form);

            // Get the rules for this workflow.
            WorkflowRuleJoinViewVector wrjVwV =
                wkflBean.getWorkflowRules
                (wid);
            session.setAttribute("Workflow.rulesjoin.vector", wrjVwV);
        }
        catch (Exception e)
        {
            ae.add("workflow",
                   new ActionError("Workflow was not found." +
                                   " Error: " + e));
        }
        return ae;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pForm          Description of Parameter
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
    public static ActionErrors rmRule(HttpServletRequest request,
                                      ActionForm pForm)
    throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        Workflow wkflBean = factory.getWorkflowAPI();
        String s = request.getParameter("workflowRuleId");
        if (null == s)
        {
            ae.add("workflow",
                   new ActionError
                   ("Error: the workflow rule id was not specified."));
            return ae;
        }
        int wrid = Integer.parseInt(s);
        s = request.getParameter("workflowId");
        if (null == s)
        {
            ae.add("workflow",
                   new ActionError
                   ("Error: the workflow id was not specified."));
            return ae;
        }
        int wid = Integer.parseInt(s);

        try
        {
            wkflBean.deleteWorkflowRule(wrid);
            WorkflowRuleJoinViewVector wrjVwV = wkflBean.getWorkflowRules (wid);
            session.setAttribute("Workflow.rulesjoin.vector", wrjVwV);
        }
        catch (Exception e)
        {
            ae.add("workflow",
                   new ActionError("Workflow was not found." +
                                   " Error: " + e));
        }
        return ae;
    }

    /**
     * Describe <code>moveRuleUp</code> method here.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param pForm an <code>ActionForm</code> value
     * @return an <code>ActionErrors</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors moveRuleUp(HttpServletRequest request,
                                          ActionForm pForm)
    throws Exception {
        return moveRule(request, pForm,Workflow.MOVE_RULE_UP);
    }

    /**
     * Describe <code>moveRuleDown</code> method here.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param pForm an <code>ActionForm</code> value
     * @return an <code>ActionErrors</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors moveRuleDown(HttpServletRequest request,
                                            ActionForm pForm)
    throws Exception {
        return moveRule(request, pForm,Workflow.MOVE_RULE_DOWN);
    }

    /**
     * Describe <code>moveRule</code> method here.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param pForm an <code>ActionForm</code> value
     * @param pMvCmd an <code>int</code> value
     * @return an <code>ActionErrors</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors moveRule(HttpServletRequest request,
                                        ActionForm pForm, int pMvCmd)
    throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        Workflow wkflBean = factory.getWorkflowAPI();
        String s = request.getParameter("workflowRuleId");
        if (null == s)
        {
            ae.add("workflow",
                   new ActionError
                   ("Error: the workflow rule id was not specified."));
            return ae;
        }
        int wrid = Integer.parseInt(s);
        s = request.getParameter("workflowId");
        if (null == s)
        {
            ae.add("workflow",
                   new ActionError
                   ("Error: the workflow id was not specified."));
            return ae;
        }
        int wid = Integer.parseInt(s);

        try
        {
            wkflBean.updateWorkflowRuleSeq(wrid, pMvCmd);
            // Get the updated rules.
            WorkflowRuleJoinViewVector wrjVwV = wkflBean.getWorkflowRules(wid);
            session.setAttribute("Workflow.rulesjoin.vector", wrjVwV);
        }
        catch (Exception e)
        {
            ae.add("workflow",
                   new ActionError("Workflow was not found." +
                                   " Error: " + e));
        }
        return ae;
    }

    public static ActionErrors setCWSkuCheck
    (HttpServletRequest request,
     ActionForm pForm)

    throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        String s = request.getParameter("bus_entity_id");

        if (null == s || s.trim().length() == 0)
        {
            ae.add("workflow",
                   new ActionError
                   ("Error: the business entity id was not specified."));
            return ae;
        }

        int beid = Integer.parseInt(s);

        String cwSkuFVal = "off";
        s = request.getParameter("cw_sku_workflow_flag");
        if (null != s )
        {
            cwSkuFVal = s;
        }
        else
        {
            cwSkuFVal = "off";
        }
        boolean skuwkfl = cwSkuFVal.equals("on")? true : false;

        try
        {
            StoreMgrLogic.setSkuWorkflowSetting(beid, skuwkfl);
        }
        catch (Exception e)
        {
            ae.add("workflow",
                   new ActionError("CW SKU Workflow was not set for " +
                                   "business entity id: " + beid +
                                   " Error: " + e));
        }
        return ae;
    }

    public static ActionErrors orderVelocityRule(HttpServletRequest request,
                                        ActionForm pForm)
    throws Exception
    {

        ActionErrors ae = new ActionErrors();
        WorkflowDetailForm sForm = (WorkflowDetailForm) pForm;
        HttpSession session = request.getSession();
        String workflowIdS = request.getParameter("workflowId");
        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setRuleSeq(ruleSeq);
        sForm.setApplyRuleGroupId(0);
        if(ruleSeq==0) {
          sForm.setTimespanInDays("");
          sForm.setRuleGroup("");
          sForm.setRuleAction("");
//          sForm.setVelocityAction("");
          return ae;
        }
        WorkflowRuleJoinViewVector wrjVwV =
                (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
        for(int ii=0; ii<wrjVwV.size(); ii++) {
          WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
          if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_VELOCITY.equals(wrjVw.getRuleTypeCd()) &&
            ruleSeq == wrjVw.getRuleSeq()) {
            setRuleAssoc(request, sForm, wrjVw);
            WorkflowRuleDataVector wrDV = wrjVw.getRules();
            WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);
            //sForm.setVelocityAction(wrD.getRuleAction());
            sForm.setRuleAction(wrD.getRuleAction());
            sForm.setTimespanInDays(wrD.getRuleExpValue());
            sForm.setApproverGroupId(wrD.getApproverGroupId());
            sForm.setEmailGroupId(wrD.getEmailGroupId());
            sForm.setRuleGroup(wrD.getRuleGroup());
            parseNextRule(request,sForm,wrD.getNextActionCd());
            break;
          }
        }
        return ae;
    }

    public static ActionErrors breakPointRule(HttpServletRequest request,
                                        ActionForm pForm)
    throws Exception
    {

        ActionErrors ae = new ActionErrors();
        WorkflowDetailForm sForm = (WorkflowDetailForm) pForm;
        HttpSession session = request.getSession();
        String workflowIdS = request.getParameter("workflowId");
        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setRuleSeq(ruleSeq);
        sForm.setApplyRuleGroupId(0);
        if(ruleSeq==0) {
          sForm.setTotalExp("");
          sForm.setTotalValue("");
          //sForm.setTotalAction("");
          sForm.setRuleAction("");
          sForm.setRuleGroup("");
          return ae;
        }
        WorkflowRuleJoinViewVector wrjVwV =
                (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
        int approverGroupId = 0;
        int emailGroupId = 0;
        for(int ii=0; ii<wrjVwV.size(); ii++) {
          WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
          if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.BREAK_POINT.equals(wrjVw.getRuleTypeCd()) &&
            ruleSeq == wrjVw.getRuleSeq()) {
            setRuleAssoc(request, sForm, wrjVw);
            WorkflowRuleDataVector wrDV = wrjVw.getRules();
            WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);
            sForm.setTotalExp("-");
            sForm.setTotalValue("-");
            sForm.setRuleAction("-");
            sForm.setApproverGroupId(0);
            sForm.setEmailGroupId(0);
            sForm.setRuleGroup("");
            parseNextRule(request,sForm,wrD.getNextActionCd());
            break;
          }
        }
        getRuleUsers(request, sForm);
        return ae;
    }

    public static ActionErrors orderTotalRule(HttpServletRequest request,
                                        ActionForm pForm)
    throws Exception
    {

        ActionErrors ae = new ActionErrors();
        WorkflowDetailForm sForm = (WorkflowDetailForm) pForm;
        HttpSession session = request.getSession();
        String workflowIdS = request.getParameter("workflowId");
        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setRuleSeq(ruleSeq);
        sForm.setApplyRuleGroupId(0);
        if(ruleSeq==0) {
          sForm.setTotalExp("");
          sForm.setTotalValue("");
          //sForm.setTotalAction("");
          sForm.setRuleAction("");
          sForm.setRuleGroup("");
          return ae;
        }
        WorkflowRuleJoinViewVector wrjVwV =
                (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
        int approverGroupId = 0;
        int emailGroupId = 0;
        for(int ii=0; ii<wrjVwV.size(); ii++) {
          WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
          if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_TOTAL.equals(wrjVw.getRuleTypeCd()) &&
            ruleSeq == wrjVw.getRuleSeq()) {
            setRuleAssoc(request, sForm, wrjVw);
            WorkflowRuleDataVector wrDV = wrjVw.getRules();
            WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);
            sForm.setTotalExp(wrD.getRuleExp());
            sForm.setTotalValue(wrD.getRuleExpValue());
            sForm.setRuleAction(wrD.getRuleAction());
            sForm.setApproverGroupId(wrD.getApproverGroupId());
            sForm.setEmailGroupId(wrD.getEmailGroupId());
            sForm.setRuleGroup(wrD.getRuleGroup());
            parseNextRule(request,sForm,wrD.getNextActionCd());
            break;
          }
        }
        getRuleUsers(request, sForm);
        return ae;
    }

    public static ActionErrors budgetYTDRule(HttpServletRequest request,
                                        ActionForm pForm)
    throws Exception
    {

        ActionErrors ae = new ActionErrors();
        WorkflowDetailForm sForm = (WorkflowDetailForm) pForm;
        HttpSession session = request.getSession();
        String workflowIdS = request.getParameter("workflowId");
        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setRuleSeq(ruleSeq);
        sForm.setApplyRuleGroupId(0);
        if(ruleSeq==0) {
          sForm.setBudgetExp("");
          sForm.setBudgetValue("");
          //sForm.setBudgetAction("");
          sForm.setRuleAction("");
          sForm.setRuleGroup("");
          return ae;
        }
        WorkflowRuleJoinViewVector wrjVwV =
                (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
        for(int ii=0; ii<wrjVwV.size(); ii++) {
          WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
          if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_YTD.equals(wrjVw.getRuleTypeCd()) &&
            ruleSeq == wrjVw.getRuleSeq()) {
            setRuleAssoc(request, sForm, wrjVw);
            WorkflowRuleDataVector wrDV = wrjVw.getRules();
            WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);
            sForm.setBudgetExp(wrD.getRuleExp());
            sForm.setBudgetValue(wrD.getRuleExpValue());
            sForm.setRuleAction(wrD.getRuleAction());
            sForm.setRuleGroup(wrD.getRuleGroup());
            parseNextRule(request,sForm,wrD.getNextActionCd());
            break;
          }
        }
        return ae;

    }

    public static ActionErrors budgetRemainingPerCCRule(HttpServletRequest request,
                                        ActionForm pForm)
    throws Exception
    {

        ActionErrors ae = new ActionErrors();
        WorkflowDetailForm sForm = (WorkflowDetailForm) pForm;
        HttpSession session = request.getSession();
        String workflowIdS = request.getParameter("workflowId");
        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setRuleSeq(ruleSeq);
        sForm.setApplyRuleGroupId(0);
        if(ruleSeq==0) {
          sForm.setBudgetExp("");
          sForm.setBudgetValue("");
          //sForm.setBudgetAction("");
          sForm.setRuleAction("");
          sForm.setRuleGroup("");
          return ae;
        }

        WorkflowRuleJoinViewVector wrjVwV =
                (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
        for(int ii=0; ii<wrjVwV.size(); ii++) {
          WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
          if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_REMAINING_PER_CC.equals(wrjVw.getRuleTypeCd()) &&
            ruleSeq == wrjVw.getRuleSeq()) {
            setRuleAssoc(request, sForm, wrjVw);
            WorkflowRuleDataVector wrDV = wrjVw.getRules();
            WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);

            sForm.setBudgetExp(wrD.getRuleExp());
            sForm.setBudgetValue(wrD.getRuleExpValue());
            sForm.setRuleAction(wrD.getRuleAction());
            sForm.setRuleGroup(wrD.getRuleGroup());
            parseNextRule(request,sForm,wrD.getNextActionCd());
            break;
          }
        }
        //sForm.setBudgetAction("");
        return ae;

    }

    public static ActionErrors orderSkuRule(HttpServletRequest request,
                                        ActionForm pForm)
    throws Exception
    {

        ActionErrors ae = new ActionErrors();
        WorkflowDetailForm sForm = (WorkflowDetailForm) pForm;
        HttpSession session = request.getSession();
        String workflowIdS = request.getParameter("workflowId");
        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setRuleSeq(ruleSeq);
        sForm.setApplyRuleGroupId(0);
        if(ruleSeq==0) {
          sForm.setOrderSku("");
          //sForm.setSkuAction("");
          sForm.setRuleAction("");
          sForm.setRuleGroup("");
          return ae;
        }

        WorkflowRuleJoinViewVector wrjVwV =
                (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
        for(int ii=0; ii<wrjVwV.size(); ii++) {
          WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
          if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU.equals(wrjVw.getRuleTypeCd()) &&
            ruleSeq == wrjVw.getRuleSeq()) {
            setRuleAssoc(request, sForm, wrjVw);
            WorkflowRuleDataVector wrDV = wrjVw.getRules();
            WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);

            sForm.setOrderSku(wrD.getRuleExpValue());
            sForm.setRuleAction(wrD.getRuleAction());
            sForm.setRuleGroup(wrD.getRuleGroup());
            parseNextRule(request,sForm,wrD.getNextActionCd());
            break;
          }
        }
        //sForm.setSkuAction("");
        return ae;

    }

    public static ActionErrors orderSkuQtyRule(HttpServletRequest request,
                                        ActionForm pForm)
    throws Exception
    {

        ActionErrors ae = new ActionErrors();
        WorkflowDetailForm sForm = (WorkflowDetailForm) pForm;
        HttpSession session = request.getSession();
        String workflowIdS = request.getParameter("workflowId");
        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setRuleSeq(ruleSeq);
        sForm.setApplyRuleGroupId(0);
        if(ruleSeq==0) {
          sForm.setOrderSku("");
          sForm.setTotalValue("");
          //sForm.setSkuAction("");
          sForm.setRuleAction("");
          sForm.setRuleGroup("");
          return ae;
        }

        WorkflowRuleJoinViewVector wrjVwV =
                (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
        String skuList = "";
        String valueS = "";
        String compS = RefCodeNames.RULE_EXPRESSION.LESS;
        boolean nextRuleFl = false;
        for(int jj=0; jj<wrjVwV.size(); jj++) {
          WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(jj);
          if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU_QTY.equals(wrjVw.getRuleTypeCd()) &&
            ruleSeq == wrjVw.getRuleSeq()) {
            setRuleAssoc(request, sForm, wrjVw);

            WorkflowRuleDataVector wrDV = wrjVw.getRules();
            for(int ii=0; ii<wrDV.size(); ii++) {
              WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(ii);
              if(ii==0) {
                sForm.setRuleAction(wrD.getRuleAction());
              }
              String expr = wrD.getRuleExp();
              if(!nextRuleFl) {
                sForm.setRuleGroup(wrD.getRuleGroup());
                parseNextRule(request,sForm,wrD.getNextActionCd());
                nextRuleFl = true;
              }
              if(RefCodeNames.RULE_EXPRESSION.SKU_NUM.equals(expr)) {
                if(skuList.length()>0) skuList += ", ";
                skuList += wrD.getRuleExpValue();
              } else if (RefCodeNames.RULE_EXPRESSION.LESS.equals(expr)) {
                valueS = wrD.getRuleExpValue();
                compS = RefCodeNames.RULE_EXPRESSION.LESS;
              } else if (RefCodeNames.RULE_EXPRESSION.GREATER.equals(expr)) {
                valueS = wrD.getRuleExpValue();
                compS = RefCodeNames.RULE_EXPRESSION.GREATER;
              }
            }
            break;
          }
        }
        sForm.setOrderSku(skuList);
        sForm.setTotalValue(valueS);
        sForm.setTotalExp(compS);
        //sForm.setSkuAction("");
        return ae;

    }



    public static ActionErrors nonOrderGuideItemRule(HttpServletRequest request,ActionForm pForm)throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        WorkflowDetailForm sForm = (WorkflowDetailForm) pForm;
        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setApplyRuleGroupId(0);
        sForm.setRuleSeq(ruleSeq);
        sForm.setRuleAction("");
        sForm.setRuleGroup("");

        WorkflowRuleJoinViewVector wrjVwV =
                (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
        for(int ii=0; ii<wrjVwV.size(); ii++) {
          WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
          if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.NON_ORDER_GUIDE_ITEM.equals(wrjVw.getRuleTypeCd()) &&
            ruleSeq == wrjVw.getRuleSeq()) {
            setRuleAssoc(request, sForm, wrjVw);
            WorkflowRuleDataVector wrDV = wrjVw.getRules();
            WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);

            sForm.setRuleAction(wrD.getRuleAction());
            sForm.setRuleGroup(wrD.getRuleGroup());
            parseNextRule(request,sForm,wrD.getNextActionCd());
            break;
          }
        }
        return ae;

    }

    public static ActionErrors everyOrderRule(HttpServletRequest request,ActionForm pForm)throws Exception {
        ActionErrors ae = new ActionErrors();
        WorkflowDetailForm sForm = (WorkflowDetailForm) pForm;
        HttpSession session = request.getSession();
        //String workflowIdS = request.getParameter("workflowId");
        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setRuleSeq(ruleSeq);
        sForm.setApplyRuleGroupId(0);
        sForm.setRuleAction("");
        sForm.setTotalExp("");
        sForm.setTotalValue("");
        sForm.setRuleGroup("");

        WorkflowRuleJoinViewVector wrjVwV =
                (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
        for(int ii=0; ii<wrjVwV.size(); ii++) {
          WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
          if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.EVERY_ORDER.equals(wrjVw.getRuleTypeCd()) &&
            ruleSeq == wrjVw.getRuleSeq()) {
            setRuleAssoc(request, sForm, wrjVw);
            WorkflowRuleDataVector wrDV = wrjVw.getRules();
            WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);

            sForm.setRuleAction(wrD.getRuleAction());
            sForm.setTotalExp(Utility.strNN(wrD.getRuleExp()));
            sForm.setTotalValue(Utility.strNN(wrD.getRuleExpValue()));
            sForm.setRuleGroup(wrD.getRuleGroup());
            parseNextRule(request,sForm,wrD.getNextActionCd());
            break;
          }
        }
        return ae;

    }

   public static void parseNextRule(HttpServletRequest request,
                                     WorkflowDetailForm pForm, String pNextAction)
    {
      try {

        if (pNextAction==null) {
          return;
        }
        if(pNextAction.trim().startsWith("After")) {
          int ind1 = pNextAction.indexOf("days");
          if(ind1>6) {
            String intervalS = pNextAction.substring(6,ind1).trim();
            int interval = Integer.parseInt(intervalS);
            pForm.setDaysUntilNextAction(interval);
          }
          if(pNextAction.indexOf(RefCodeNames.WORKFLOW_RULE_ACTION.APPROVE_ORDER) != -1) {
            pForm.setNextActionCd(RefCodeNames.WORKFLOW_RULE_ACTION.APPROVE_ORDER);
          }
          else if(pNextAction.indexOf(RefCodeNames.WORKFLOW_RULE_ACTION.REJECT_ORDER) != -1) {
            pForm.setNextActionCd(RefCodeNames.WORKFLOW_RULE_ACTION.REJECT_ORDER);
          }
        } else if (pNextAction.trim().startsWith("To:")) {
          int ind0 = pNextAction.indexOf("Id:");
          if(ind0>-1) {
            ind0 += "Id:".length();
            int ind1 = pNextAction.indexOf(".",ind0);
            if(ind1>ind0) {
               String userIdS = pNextAction.substring(ind0,ind1).trim();
               int userId = Integer.parseInt(userIdS);
               pForm.setEmailUserId(userId);

            }
          }
        }

      } catch(Exception exc) {} //Dump exception - nothing serious
    }

        /**
     *  Adds a feature to the Rule attribute of the WorkflowMgrLogic class
     *
     *@param  request        The feature to be added to the Rule attribute
     *@param  pForm          The feature to be added to the Rule attribute
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
    public static ActionErrors saveRule(HttpServletRequest request,
                                       ActionForm pForm)
    throws Exception {

        WorkflowDetailForm form = (WorkflowDetailForm) pForm;
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        String ruleSeqOrigS = request.getParameter("ruleSeqOrig");
        if(ruleSeqOrigS==null) {
          String errorMess = "No rule number found. Call techincal support";
          ae.add("error",
          new ActionError("error.simpleGenericError",errorMess));
          return ae;
        }
        int ruleSeqOrig = Integer.parseInt(ruleSeqOrigS);
        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = 0;
        try {
          ruleSeq = Integer.parseInt(ruleSeqS);
        } catch (Exception exc) {
          String errorMess = "Wrong rule number format: "+ruleSeqS;
          ae.add("error",
          new ActionError("error.simpleGenericError",errorMess));
          return ae;
        }

        APIAccess factory = new APIAccess();
        Workflow wkflEjb = factory.getWorkflowAPI();
        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
        int wid = form.getIntId();
        WorkflowRuleDataVector wrDV = new WorkflowRuleDataVector();
        String ruleAction = "";
        try
        {
          SessionTool lses = new SessionTool(request);
          String user = lses.getLoginName();
          String rtype = form.getRuleTypeCd();
          ruleAction = form.getRuleAction();
          if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_REMAINING_PER_CC.equals
                    (rtype)) {
              WorkflowRuleData wrD = WorkflowRuleData.createValue();
              wrDV.add(wrD);
              wrD.setRuleExp(form.getBudgetExp());
              wrD.setRuleExpValue(form.getBudgetValue());
              wrD.setApproverGroupId(form.getApproverGroupId());
              wrD.setEmailGroupId(form.getEmailGroupId());
              //ruleAction = form.getBudgetAction();
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.BREAK_POINT.equals
                    (rtype))
            {
              WorkflowRuleData wrD = WorkflowRuleData.createValue();
              wrDV.add(wrD);
              wrD.setRuleExp("-");
              wrD.setRuleExpValue("-");
              wrD.setApproverGroupId(0);
              wrD.setEmailGroupId(0);
              //ruleAction = form.getBudgetAction();
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_YTD.equals
                    (rtype))
            {
              WorkflowRuleData wrD = WorkflowRuleData.createValue();
              wrDV.add(wrD);
              wrD.setRuleExp(form.getBudgetExp());
              wrD.setRuleExpValue(form.getBudgetValue());
              wrD.setApproverGroupId(form.getApproverGroupId());
              wrD.setEmailGroupId(form.getEmailGroupId());
              //ruleAction = form.getBudgetAction();
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_VELOCITY.equals
                    (rtype))
            {
              WorkflowRuleData wrD = WorkflowRuleData.createValue();
              wrDV.add(wrD);
              wrD.setRuleExp("<");
              wrD.setRuleExpValue(form.getTimespanInDays());
              wrD.setApproverGroupId(form.getApproverGroupId());
              wrD.setEmailGroupId(form.getEmailGroupId());
              //ruleAction = form.getVelocityAction();
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU.equals
                    (rtype))
            {
              WorkflowRuleData wrD = WorkflowRuleData.createValue();
              wrDV.add(wrD);
              wrD.setRuleExp("==");
              wrD.setRuleExpValue(form.getOrderSku());
              wrD.setApproverGroupId(form.getApproverGroupId());
              wrD.setEmailGroupId(form.getEmailGroupId());
              //ruleAction = form.getSkuAction();
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_TOTAL.equals
                     (rtype))
            {
              WorkflowRuleData wrD = WorkflowRuleData.createValue();
              wrDV.add(wrD);
              wrD.setRuleExp(form.getTotalExp());
              wrD.setRuleExpValue(form.getTotalValue());
              wrD.setApproverGroupId(form.getApproverGroupId());
              wrD.setEmailGroupId(form.getEmailGroupId());
              //ruleAction = form.getTotalAction();
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU_QTY.equals
                    (rtype))
            {
              //ruleAction = form.getSkuAction();
              String qtyS = form.getTotalValue();
              String compS = form.getTotalExp();
              if(!(RefCodeNames.RULE_EXPRESSION.LESS.equals(compS) ||
                 RefCodeNames.RULE_EXPRESSION.GREATER.equals(compS)))
              {
                String errorMess = "Wrong expression value: "+compS;
                ae.add("error",
                new ActionError("error.simpleGenericError",errorMess));
                return ae;
              }
              int qty = 0;
              try{
                qty = Integer.parseInt(qtyS);
              }catch(Exception exc) {
                String errorMess = "Wrong quatity value: "+qtyS;
                ae.add("error",
                new ActionError("error.simpleGenericError",errorMess));
                return ae;
              }
              String skuList = form.getOrderSku();
              ArrayList skus = new ArrayList();
              char[] skuA = skuList.toCharArray();
              String skuNumS = "";
              for(int ii=0; ii<skuA.length; ii++) {
                char cc = skuA[ii];
                if(Character.isDigit(cc)) {
                  skuNumS += cc;
                  if(ii<skuA.length-1) {
                    continue;
                  }
                } else if(Character.isLetter(cc)) {
                  String errorMess = "Wrong character in sku number: "+cc;
                  ae.add("error",
                       new ActionError("error.simpleGenericError",errorMess));
                  return ae;
                }
                if(skuNumS.length()>0) {
                  ArrayList critAL = new ArrayList();
                  SearchCriteria searchCriteria = new SearchCriteria();
                  searchCriteria.operator = SearchCriteria.EXACT_MATCH;
                  searchCriteria.value = skuNumS;
                  searchCriteria.name = SearchCriteria.CLW_SKU_NUMBER;
                  critAL.add(searchCriteria);
                  IdVector itemIdV = catalogInfEjb.searchProducts(critAL);
                  if(itemIdV.size()==0) {
                    String errorMess = "Wrong sku number: "+skuNumS;
                    ae.add("error",
                     new ActionError("error.simpleGenericError",errorMess));
                    return ae;
                  }
                  skus.add(skuNumS);
                  skuNumS = "";
                }
              }
              for(int ii=0; ii<skus.size(); ii++) {
                WorkflowRuleData wrD = WorkflowRuleData.createValue();
                wrDV.add(wrD);
                wrD.setRuleExp(RefCodeNames.RULE_EXPRESSION.SKU_NUM);
                wrD.setRuleExpValue((String) skus.get(ii));
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());
              }
              WorkflowRuleData wrD = WorkflowRuleData.createValue();
              wrDV.add(wrD);
              wrD.setRuleExp(compS);
              wrD.setRuleExpValue(qtyS);
              wrD.setApproverGroupId(form.getApproverGroupId());
              wrD.setEmailGroupId(form.getEmailGroupId());
             }else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.NON_ORDER_GUIDE_ITEM.equals(rtype))
            {
              WorkflowRuleData wrD = WorkflowRuleData.createValue();
              wrDV.add(wrD);
              wrD.setRuleExp(RefCodeNames.WORKFLOW_RULE_TYPE_CD.NON_ORDER_GUIDE_ITEM);
              wrD.setRuleExpValue("0");
              wrD.setApproverGroupId(form.getApproverGroupId());
              wrD.setEmailGroupId(form.getEmailGroupId());
              //ruleAction = form.getNonOrderGuideItemAction();
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.EVERY_ORDER.equals
                     (rtype))
            {
              WorkflowRuleData wrD = WorkflowRuleData.createValue();
              wrDV.add(wrD);
              wrD.setRuleExp(Utility.strNN(form.getTotalExp()));
              wrD.setRuleExpValue(Utility.strNN(form.getTotalValue()));
              wrD.setApproverGroupId(form.getApproverGroupId());
              wrD.setEmailGroupId(form.getEmailGroupId());
              ruleAction = form.getRuleAction();
            }
            else
            {
                String msg = "Unknown workflow rule type " +rtype ;
                ae.add("workflow",
                       new ActionError(msg));
                return ae;
            }

            String nextAction = form.getNextActionCd();
            if(ruleAction==null || ruleAction.trim().length()==0) {
               String errorMess = "No rule action found";
               ae.add("error",
                       new ActionError("error.simpleGenericError",errorMess));
               return ae;
            }
            String finalAction = "Done";

            if (ruleAction.equals(RefCodeNames.WORKFLOW_RULE_ACTION.FWD_FOR_APPROVAL) ||
                ruleAction.equals(RefCodeNames.WORKFLOW_RULE_ACTION.STOP_ORDER))
            {
                finalAction = " After " +
                              form.getDaysUntilNextAction() + " days ";
                finalAction += nextAction + ". ";
            }
            else if(ruleAction.equals(RefCodeNames.WORKFLOW_RULE_ACTION.SEND_EMAIL))
            {
                finalAction = " To: " +
                              form.getEmailUserName() + " Id: " +
                              form.getEmailUserId() + ". ";
            }

            for(int ii=0; ii<wrDV.size(); ii++) {
              WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(ii);
              wrD.setModBy(user);
              wrD.setAddBy(user);
              wrD.setWorkflowId(wid);
              wrD.setRuleSeq(form.getRuleSeq());
              wrD.setRuleGroup(form.getRuleGroup());
              wrD.setRuleAction(ruleAction);
              wrD.setNextActionCd(finalAction);
              wrD.setShortDesc(String.valueOf(wid));
              wrD.setWorkflowRuleStatusCd("ACTIVE");
              wrD.setRuleTypeCd(rtype);
              wrD.setApproverGroupId(form.getApproverGroupId());
              wrD.setEmailGroupId(form.getEmailGroupId());
            }

            WorkflowAssocDataVector waDV = null;
            int applyGroupId = form.getApplyRuleGroupId();
            if(applyGroupId>0) {
              waDV = new WorkflowAssocDataVector();
              WorkflowAssocData waD = WorkflowAssocData.createValue();
              waDV.add(waD);
              waD.setAddBy(user);
              waD.setModBy(user);
              waD.setGroupId(applyGroupId);
              waD.setWorkflowId(wid);
              if(form.getApplySkipFl()) {
                waD.setWorkflowAssocCd(RefCodeNames.WORKFLOW_ASSOC_CD.APPLY_FOR_GROUP_USERS);
              } else {
                waD.setWorkflowAssocCd(RefCodeNames.WORKFLOW_ASSOC_CD.SKIP_FOR_GROUP_USERS);
              }
            }
            wkflEjb.saveWorkflowRule(ruleSeqOrig, wrDV,waDV);

            WorkflowRuleJoinViewVector wrjVwV =
                wkflEjb.getWorkflowRules(wid);
            session.setAttribute("Workflow.rulesjoin.vector", wrjVwV);
            // Reset the rule type.
            form.setRuleTypeCd("---");

        }
        catch (Exception e)
        {
            ae.add("workflow",
                   new ActionError("Workflow was not found." +
                                   " Error: " + e));
        }
        return ae;
    }

    private static UserDataVector getGroupUsers(int groupId, int storeId)
    throws Exception
    {
      APIAccess factory = new APIAccess();
      Group groupEjb = factory.getGroupAPI();
      GroupSearchCriteriaView gcrit = GroupSearchCriteriaView.createValue();
      gcrit.setStoreId(storeId);
      gcrit.setGroupId(groupId);
      UserDataVector userDV = groupEjb.getUsersForGroup(gcrit, 0,0);
      if(userDV.size()>1) {
        DisplayListSort.sort(userDV, "lastName");
      }
      return userDV;
    }

    private static int getStoreId(HttpServletRequest request)
    throws Exception
    {
      HttpSession session = request.getSession();
      CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
      StoreData storeD = appUser.getUserStore();
      int storeId = storeD.getBusEntity().getBusEntityId();
      return storeId;
    }

    private static void setRuleAssoc(HttpServletRequest request, WorkflowDetailForm pForm,
     WorkflowRuleJoinView pWorkflowRuleJoin)
    {
      WorkflowAssocDataVector waDV = pWorkflowRuleJoin.getAssociations();
      pForm.setApplyRuleGroupId(0);
      for(Iterator iter=waDV.iterator(); iter.hasNext();) {
        WorkflowAssocData waD = (WorkflowAssocData) iter.next();
        pForm.setApplyRuleGroupId(waD.getGroupId());
        if(RefCodeNames.WORKFLOW_ASSOC_CD.APPLY_FOR_GROUP_USERS.equals(waD.getWorkflowAssocCd())) {
          pForm.setApplySkipFl(true);
        } else {
          pForm.setApplySkipFl(false);
        }
      }
    }

    private static void getRuleUsers(HttpServletRequest request, WorkflowDetailForm pForm)
    throws Exception
    {
      int storeId = getStoreId(request);
      int approverGroupId = pForm.getApproverGroupId();
      if(approverGroupId>0) {
        UserDataVector approvers = getGroupUsers(approverGroupId, storeId);
        pForm.setApproverUsers(approvers);
      } else {
        pForm.setApproverUsers(null);
      }

      int emailGroupId = pForm.getEmailGroupId();
      if(emailGroupId>0) {
        UserDataVector emailUsers = getGroupUsers(emailGroupId, storeId);
        pForm.setEmailUsers(emailUsers);
      } else {
        pForm.setEmailUsers(null);
      }
    }

}



